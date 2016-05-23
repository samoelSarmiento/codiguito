package pe.pucp.edu.pe.siscomfi.controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import pe.pucp.edu.pe.siscomfi.Binarization;
import pe.pucp.edu.pe.siscomfi.NoiseFilter;

public class Signatures {

	public static Rectangle getRectangularArea(BufferedImage img) {
		// System.out.println("w: " + img.getWidth() + " h: " +
		// img.getHeight());
		// System.out.println(img.getRGB(0, 246));
		Point top = null, left = null, right = null, bot = null;
		for (int i = 0; i < img.getHeight(); i++) {
			// System.out.println("i: "+i );
			for (int j = 0; j < (img.getWidth()); j++) {
				// System.out.println("j: "+ j);
				if (img.getRGB(j, i) == Color.BLACK.getRGB()) {
					top = new Point(i - 1, j);
					break;
				}
			}
			if (top != null)
				break;
		}

		for (int j = 0; j < img.getWidth(); j++) {
			for (int i = 0; i < img.getHeight(); i++) {
				if (img.getRGB(j, i) == Color.BLACK.getRGB()) {
					left = new Point(i, j - 1);
					break;
				}
			}
			if (left != null)
				break;
		}

		for (int i = img.getHeight() - 1; i >= 0; i--) {
			for (int j = 0; j < img.getWidth(); j++) {
				if (img.getRGB(j, i) == Color.BLACK.getRGB()) {
					bot = new Point(i + 1, j);
					break;
				}
			}
			if (bot != null)
				break;
		}

		for (int j = img.getWidth() - 1; j >= 0; j--) {
			for (int i = 0; i < img.getHeight(); i++) {
				if (img.getRGB(j, i) == Color.BLACK.getRGB()) {
					right = new Point(i, j + 1);
					break;
				}
			}
			if (right != null)
				break;
		}
		Rectangle rec = new Rectangle(top.getX(), left.getY(), bot.getX() - top.getX(), right.getY() - left.getY());
		return rec;
	}

	public static BufferedImage removeNoise(BufferedImage img) {
		BufferedImage binImg = Binarization.binarize(img);
		BufferedImage output = new BufferedImage(binImg.getWidth(), binImg.getHeight(), binImg.getType());
		NoiseFilter nsf = new NoiseFilter();
		nsf.setNoiseType(NoiseFilter.GAUSSIAN);
		nsf.filter(binImg, output);
		return output;
	}

	public static Point getAngleImage(BufferedImage img) {
		int y1 = 1;
		Point p1 = null;
		for (int i = img.getHeight() - 1; i >= 0; i--) {
			if (img.getRGB(y1, i) == Color.BLACK.getRGB()) {
				p1 = new Point(i, y1);
				break;
			}
		}
		int y2 = img.getWidth() - 1;
		Point p2 = null;
		for (int i = img.getHeight() - 1; i >= 0; i--) {
			if (img.getRGB(y2, i) == Color.BLACK.getRGB()) {
				p2 = new Point(i, y2);
				break;
			}
		}
		
		double angle = p1.getAnglePoint(p2);
		Point pFinal = p1;
		pFinal.setAngle(angle - Math.PI/2);
		//System.out.println("angle anterior: " + Math.toDegrees(angle));
		//System.out.println("angle final: "+Math.toDegrees(pFinal.getAngle()));
		return pFinal;
	}

	public static BufferedImage rotateImage2(BufferedImage img,Point pRot) {
		BufferedImage img2 = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Point pointRotated = rotatePoint(pRot,i,j,pRot.getAngle());
				if(img.getRGB(i, j) == Color.BLACK.getRGB())
					img2.setRGB(Math.abs(pointRotated.getX()), Math.abs(pointRotated.getY()), Color.BLACK.getRGB());
				else
					img2.setRGB(Math.abs(pointRotated.getX()), Math.abs(pointRotated.getY()), Color.WHITE.getRGB());
			}
		}
		return img2;
	}

	private static Point rotatePoint(Point p, int x, int y, double angle) {
		Point2D point = new Point2D.Double(p.getX(), p.getY());
		Point2D result = new Point2D.Double();
		AffineTransform rotation = new AffineTransform();
		rotation.rotate(angle, x, y);
		rotation.transform(point, result);
		Point pointRotated = new Point((int) result.getX(), (int) result.getY());
		return pointRotated;
	}
}
