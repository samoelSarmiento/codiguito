package pe.pucp.edu.pe.siscomfi.controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import pe.pucp.edu.pe.siscomfi.Binarization;
import pe.pucp.edu.pe.siscomfi.HelperMethods;
import pe.pucp.edu.pe.siscomfi.NoiseFilter;

public class Signatures {

	public static Rectangle getRectangularArea(BufferedImage img) {
		Point top = null, left = null, right = null, bot = null;
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < (img.getWidth()); j++) {
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

		double angleP = 0.0;
		for (int w = 0; w < 5; w++) {
			int y1 = 1 + 5 * w;
			Point p1 = null;
			for (int i = img.getHeight() - 1; i >= 0; i--) {
				if (img.getRGB(y1, i) == Color.BLACK.getRGB()) {
					p1 = new Point(i, y1);
					break;
				}
			}
			int y2 = img.getWidth() - 1 - 5 * w;
			Point p2 = null;
			for (int i = img.getHeight() - 1; i >= 0; i--) {
				if (img.getRGB(y2, i) == Color.BLACK.getRGB()) {
					p2 = new Point(i, y2);
					break;
				}
			}

			double angle = p1.getAnglePoint(p2) - (Math.PI / 2);
			angleP += angle;
			// pFinal = p1;
			// pFinal.setAngle(angle - Math.PI / 2);
		}
		double angleFF = angleP/5;
		Point pX = new Point(0,0);
		pX.setAngle(angleFF);
		return pX;
		
		  /*int y1 = 1; Point p1 = null; for (int i = img.getHeight() - 1; i >=
		  0; i--) { if (img.getRGB(y1, i) == Color.BLACK.getRGB()) { p1 = new
		  Point(i, y1); break; } } int y2 = img.getWidth() - 1; Point p2 =
		  null; for (int i = img.getHeight() - 1; i >= 0; i--) { if
		  (img.getRGB(y2, i) == Color.BLACK.getRGB()) { p2 = new Point(i, y2);
		  break; } }
		  
		  double angle = p1.getAnglePoint(p2); Point pFinal = p1;
		  pFinal.setAngle(angle - Math.PI / 2);
		 return pFinal;*/
		// System.out.println("angle anterior: " + Math.toDegrees(angle));
		// System.out.println("angle final:
		// "+Math.toDegrees(pFinal.getAngle()));
		
	}

	private static double compare(BufferedImage cDbimg, BufferedImage crop2) {
		int m = 0, n = 0, p = 0, pixelColor, color;
		int w = cDbimg.getWidth();
		int h = cDbimg.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pixelColor = cDbimg.getRGB(i, j);
				color = crop2.getRGB(i, j);
				if (pixelColor == Color.BLACK.getRGB()) {
					if (pixelColor == color)
						m++;
					else
						n++;
					p++;
				}
			}
		}
		double comp = (m * 1.0 / (n + p));
		return comp;
	}

	public static double compareSignatures(BufferedImage original, BufferedImage suspect) throws IOException {
		// original
		original = Binarization.binarize(original);
		Rectangle rDbimg = Signatures.getRectangularArea(original);
		BufferedImage cDbimg = original.getSubimage((int) rDbimg.getY(), (int) rDbimg.getX(), (int) rDbimg.getHeight(),
				(int) rDbimg.getWidth());
		// suspect
		suspect = Binarization.binarize(suspect);
		Rectangle rec = Signatures.getRectangularArea(suspect);
		// cortamos segun el rectangulo
		BufferedImage crop = suspect.getSubimage((int) rec.getY(), (int) rec.getX(), (int) rec.getHeight(),
				(int) rec.getWidth());
		// saca el angulo de rotacion y rotamos
		Point pRot = Signatures.getAngleImage(crop);
		System.out.println(Math.toDegrees(pRot.getAngle()));
		BufferedImage rot = crop;
		if (Math.toDegrees(pRot.getAngle()) > 11)
			rot = HelperMethods.rotate(crop, pRot.getAngle());
		if (Math.toDegrees(pRot.getAngle()) < 0)
			rot = HelperMethods.rotate(crop, pRot.getAngle());
		// formamos el rectangulo denuevo
		Rectangle rec2 = Signatures.getRectangularArea(rot);
		BufferedImage crop2 = rot.getSubimage((int) rec2.getY(), (int) rec2.getX(), (int) rec2.getHeight(),
				(int) rec2.getWidth());
		// w,h
		crop2 = Thumbnails.of(crop2).forceSize(cDbimg.getWidth(), cDbimg.getHeight()).asBufferedImage();
		double res = compare(cDbimg, crop2);
		return res;
	}
}
