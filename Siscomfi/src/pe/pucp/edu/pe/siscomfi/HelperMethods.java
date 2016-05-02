package pe.pucp.edu.pe.siscomfi;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import pe.pucp.edu.pe.siscomfi.controller.Point;

public class HelperMethods {

	public static List<Point> VectorCaracteristicas(int[][] imagen) {
		List<Point> caracteristicas = new ArrayList<Point>();
		for (int i = 0; i < imagen.length; i++) {
			for (int j = 0; j < imagen[0].length; j++) {
				if (imagen[i][j] == 1) {
					Point negro = new Point(i, j);
					caracteristicas.add(negro);
				}
			}
		}
		return caracteristicas;
	}

	public static double[] Aplastar(int[][] matriz) {
		double[] arreglo = new double[matriz.length * matriz[0].length];
		int cont = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				arreglo[cont++] = matriz[i][j];
			}
		}
		return arreglo;
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
	
	public double angle3Points(Point p1, Point p2, Point p3){
		double p12 = p2.euclideanDistance(p1);
		double p13 = p3.euclideanDistance(p1);
		double p23 = p2.euclideanDistance(p3);
		double pp12 = Math.pow(p12, 2);
		double pp13 = Math.pow(p13, 2);
		double pp23 = Math.pow(p23, 2);
		return Math.acos((pp12 + pp13 - pp23)/(2*p12*p13));
	}
}
