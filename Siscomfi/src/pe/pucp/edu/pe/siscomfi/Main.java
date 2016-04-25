package pe.pucp.edu.pe.siscomfi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.neuroph.contrib.graphml.Edge;

import net.coobird.thumbnailator.Thumbnails;
import pe.pucp.edu.pe.siscomfi.controller.Fingerprint;
import pe.pucp.edu.pe.siscomfi.controller.Point;

public class Main {

	public static void main(String[] args) {
		/*
		 * BufferedImage in = null; try { in = ImageIO.read(new
		 * File("C:/Users/samoel/Desktop/TestImage/test1.jpg")); int x = 14; int
		 * y = 70; int w = 39; int h = 36;
		 * 
		 * BufferedImage cut = in.getSubimage(x, y, w, h); Graphics g =
		 * cut.createGraphics();
		 * 
		 * 
		 * g.drawImage(cut, x, y, null);
		 * 
		 * JPanel mainPanel = new JPanel(new BorderLayout()); JLabel lblimage =
		 * new JLabel(new ImageIcon(cut)); mainPanel.add(lblimage); JFrame frame
		 * = new JFrame(); frame.getContentPane().add(lblimage,
		 * BorderLayout.CENTER); frame.setSize(500, 500);
		 * frame.setVisible(true); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		// C:/Users/samoel/Downloads/DB1_B
		// "C:/Users/samoel/Desktop/TestImage/nuevo/001_1.jpg"
		String filename1 = "C:/Users/samoel/Desktop/TestImage/nuevo/001_2.jpg";
		// String filename2 =
		// "C:/Users/samoel/Desktop/TestImage/nuevo/002_1.jpg";
		int cont = 0;
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.DOWN);
		int fp = 0;
		long promTime = 0;
		long timTotI = System.nanoTime();
		double[][] imgIn = Fingerprint.imageGraph(filename1);
		for (int i = 2; i < 51; i++) {
			for (int j = 1; j < 3; j++) {
				cont++;
				String filename2 = "";
				if (i < 10)
					filename2 = "C:/Users/samoel/Desktop/TestImage/nuevo/00" + i + "_" + j + ".jpg";
				else
					filename2 = "C:/Users/samoel/Desktop/TestImage/nuevo/0" + i + "_" + j + ".jpg";

				// filename2 =
				// "C:/Users/samoel/Desktop/TestImage/nuevo/001_2.jpg";
				double[][]imgTest = Fingerprint.imageGraph(filename2);
				long startTime = System.nanoTime();
				//double comparition = Fingerprint.matchFingerprints(filename1, filename2);
				double comparition = Fingerprint.comparition(imgIn, imgTest);
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000000;
				promTime += duration;
				System.out.print("Comp " + (cont) + ": ");
				System.out.print("match%-> " + df.format(comparition));
				System.out.print(" same?-> " + (comparition > 0.975));
				if (comparition > 0.975)
					fp++;
				System.out.println(" time-> " + duration);
				if (cont == 30)
					break;
			}
			if (cont == 30)
				break;
		}
		long timTotF = System.nanoTime();
		System.out.println("                   fp -> " + fp);
		System.out.println("                   totTime -> " + (promTime));
		System.out.println("                   promTime -> " + (promTime*1.0 / 30));
		System.out.println("                   promTime -> " + (timTotF-timTotI)/1000000000);
		/*
		 * try {
		 * 
		 * /*BufferedImage in = ImageIO.read(new
		 * File("C:/Users/samoel/Desktop/TestImage/f1.jpg")); in =
		 * Thumbnails.of(in).size(600, 600).asBufferedImage();
		 * 
		 * BufferedImage test = ImageIO.read(new
		 * File("C:/Users/samoel/Desktop/TestImage/f1.jpg")); test =
		 * Thumbnails.of(test).size(600, 600).asBufferedImage();
		 * 
		 * BufferedImage bin = Binarizacion.binarize(in); BufferedImage binTest
		 * = Binarizacion.binarize(test);
		 * 
		 * int[][] mati = Binarizacion.imgToMat(bin); int[][] matiTest =
		 * Binarizacion.imgToMat(binTest);
		 * 
		 * int[][] ske = thining1.doZhangSuenThinning(mati, false); int[][]
		 * skeTest = thining1.doZhangSuenThinning(matiTest, false);
		 * 
		 * List<Point> fMinutaes = Fingerprint.getMinutiaes(ske); List<Point>
		 * fMinutaesTest = Fingerprint.getMinutiaes(skeTest);
		 * 
		 * List<Point> tMinutaes = Fingerprint.removeFalseMinutae(ske,
		 * fMinutaes); List<Point> tMinutaesTest =
		 * Fingerprint.removeFalseMinutae(skeTest, fMinutaesTest); //
		 * System.out.println(tMinutaes.size());
		 * 
		 * // K -1 cercanas int k = 5; // Grafo S double[][] grafoS =
		 * Fingerprint.matToGraph(tMinutaes, k); // Grafo T double[][] grafoT =
		 * Fingerprint.matToGraph(tMinutaesTest, k);
		 * 
		 * double comparition = Fingerprint.comparition(grafoS, grafoT, k);
		 * System.out.println(comparition); /*
		 * DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge> grafoT = new
		 * DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(
		 * DefaultWeightedEdge.class); // llenar matriz adyacencia for (int i =
		 * 0; i < tMinutaesTest.size(); i++) { Point[] vecinos =
		 * Fingerprint.getNearestNeighbourType(tMinutaesTest.get(i),
		 * tMinutaesTest, k); grafoS.addVertex(tMinutaesTest.get(i)); for (int j
		 * = 1; j < vecinos.length; j++) { DefaultWeightedEdge edge =
		 * grafoT.addEdge(tMinutaesTest.get(i), vecinos[j]);
		 * grafoS.setEdgeWeight(edge,
		 * tMinutaesTest.get(i).euclideanDistance(vecinos[j])); } }
		 * 
		 * double umbral = 10.00; int matchs = 0; /*Set<Point> vertexSetS =
		 * grafoS.vertexSet(); Set<Point> vertexSetT = grafoT.vertexSet(); for
		 * (Point point1 : vertexSetS) { //vecindad de point1
		 * Set<DefaultWeightedEdge> edgesSetS = grafoS.edgesOf(point1); for
		 * (Point point2 : vertexSetT) { //vecindad de point2
		 * Set<DefaultWeightedEdge> edgesSetT = grafoT.edgesOf(point2); //para
		 * los pesos de la vecindad de point1 for (DefaultWeightedEdge edgeS :
		 * edgesSetS) { //asumimos que son match boolean cumple_vecindad = true;
		 * //saco el peso de la arista de point1 double pesoS =
		 * grafoS.getEdgeWeight(edgeS); //para todas las aristas de point2 for
		 * (DefaultWeightedEdge edgeT : edgesSetT) { //sacmos el peso double
		 * pesoT = grafoT.getEdgeWeight(edgeT); //cumple el umbral if
		 * (Math.abs(pesoS - pesoT) > umbral) { cumple_vecindad = false; break;
		 * } } } }
		 * 
		 * }
		 * 
		 * /* double[][] grafoT = new
		 * double[tMinutaesTest.size()][tMinutaesTest.size()]; // llenar matriz
		 * adyacencia for (int i = 0; i < tMinutaesTest.size(); i++) { Point[]
		 * vecinos = Fingerprint.getNearestNeighbourType(tMinutaesTest.get(i),
		 * tMinutaesTest, k); for (int j = 1; j < vecinos.length; j++) { Point p
		 * = vecinos[j]; int index = tMinutaesTest.indexOf(p); grafoT[i][index]
		 * = p.euclideanDistance(tMinutaesTest.get(i)); } } // Algoritomo raro
		 * 
		 * /* System.out.println(tMinutaes.get(0).getX() + " " +
		 * tMinutaes.get(0).getY()); System.out.println(); for (int i = 0; i <
		 * cercanos.length; i++) { //System.out.println("Vecinos: " +
		 * cercanos[i].getX() + " " + cercanos[i].getY()); System.out.println(
		 * "Distancia: " + tMinutaes.get(0).euclideanDistance(cercanos[i])); }
		 */

		/*
		 * for(int i = 0; i< tMinutaes.size();i++){ System.out.println(
		 * "Minutiae " + (i+1) + ": " + tMinutaes.get(i).getX() + " " +
		 * tMinutaes.get(i).getY()); }
		 */
		/*
		 * double inter_D = 0; int rows = ske[0].length; double partial_sum = 0;
		 * for (int i = 0; i < ske.length; i++) { int sum_ones = 0; for (int j =
		 * 0; j < ske[0].length; j++) { if (ske[i][j] == 1) sum_ones++; }
		 * partial_sum += rows * 1.0 / sum_ones; } inter_D = partial_sum / rows;
		 * /* for(int m = 0; m < ske.length;m++){ for(int n =
		 * 0;n<ske[0].length;n++){ System.out.print(""+ske[m][n]); }
		 * System.out.println(); } /*BufferedImage bufferedImage = new
		 * BufferedImage(ske.length, ske[0].length,
		 * BufferedImage.TYPE_BYTE_BINARY); for (int i = 0; i < ske.length; i++)
		 * { for (int j = 0; j < ske[0].length; j++) { int pixel = ske[i][j];
		 * pixel = (pixel == 0) ? 0xFFFFFFFF : 0x0; // System.out.println(
		 * "The pixel in Matrix: "+pixel); bufferedImage.setRGB(i, j, pixel); //
		 * System.out.println("The pixel in BufferedImage: //
		 * "+bufferedImage.getRGB(i, j)); } } // Flip the image vertically
		 * AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		 * tx.translate(-bufferedImage.getWidth(null), 0); AffineTransformOp op
		 * = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		 * bufferedImage = op.filter(bufferedImage, null);
		 * 
		 * bufferedImage = new AffineTransformOp(
		 * AffineTransform.getQuadrantRotateInstance( 3,
		 * bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2),
		 * AffineTransformOp.TYPE_BILINEAR).filter(bufferedImage, null);
		 * 
		 * 
		 * 
		 * JPanel mainPanel = new JPanel(new BorderLayout()); JLabel lblimage =
		 * new JLabel(new ImageIcon(bufferedImage)); mainPanel.add(lblimage);
		 * JFrame frame = new JFrame(); frame.getContentPane().add(lblimage,
		 * BorderLayout.CENTER); frame.setSize(500, 500);
		 * frame.setVisible(true); Binarizacion.writeImage(bufferedImage,
		 * "esqueleto");
		 */

		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}
