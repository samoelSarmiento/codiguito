package pe.pucp.edu.pe.siscomfi;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.Duplicator;
import pe.pucp.edu.pe.siscomfi.controller.Point;

public class HelperMethods {

	public static ImagePlus recortarPlanillon(ImagePlus img){
		ImagePlus planillon = new Duplicator().run(img);
		IJ.run(planillon, "Rotate 90 Degrees Right", "");
		IJ.run(planillon, "Make Binary", "");
		int y = planillon.getHeight()/2, x = 0, r = 0, g, b;
		while (r == 0) {
			r = planillon.getPixel(x, y)[0];
			x++;
		}
		planillon.setRoi(x + 1, 0, planillon.getWidth() - x, planillon.getHeight());
		IJ.run(planillon, "Crop", "");
		y = planillon.getHeight();
		
		x = 20; r= 0;
		while (r == 0) {
			r = planillon.getPixel(x, y)[0];
			y--;
		}
		
		planillon.setRoi(0, 0, planillon.getWidth(), y);
		IJ.run(planillon, "Crop", "");
		return planillon;
	}
	
	public static void quickSort(Point[] arr, int low, int high, Point ref) {
		if (arr == null || arr.length == 0)
			return;

		if (low >= high)
			return;

		// pick the pivot
		int middle = low + (high - low) / 2;
		Point pivot = arr[middle];

		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (ref.euclideanDistance(arr[i]) < ref.euclideanDistance(pivot)) {
				i++;
			}

			while ((ref.euclideanDistance(arr[j]) > ref.euclideanDistance(pivot))) {
				j--;
			}

			if (i <= j) {
				Point temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j, ref);

		if (high > i)
			quickSort(arr, i, high, ref);
	}

	public static int[][] removeNoise(int[][] mat) {
		// Mean filter
		float[][] filter = { { 0.0625f, 0.1250f, 0.0625f }, { 0.1250f, 0.2500f, 0.1250f },
				{ 0.0625f, 0.1250f, 0.0625f } };

		// Variables
		int limWidth = mat.length - 1;
		int limHeight = mat[0].length - 1;
		int[][] newmap = new int[mat.length][mat[0].length];
		float val;

		for (int i = 1; i < limWidth; ++i) {
			for (int j = 1; j < limHeight; ++j) {
				val = 0;

				// Apply the filter
				for (int ik = -1; ik <= 1; ++ik) {
					for (int jk = -1; jk <= 1; ++jk) {
						val += mat[i + ik][j + jk] * filter[1 + ik][1 + jk];
					}
				}
				newmap[i][j] = (val > 0.5) ? 1 : 0;
			}
		}

		return mat;
	}
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}

	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}

}
