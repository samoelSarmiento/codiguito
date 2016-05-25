package pe.pucp.edu.pe.siscomfi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import pe.pucp.edu.pe.siscomfi.controller.Fingerprint;
import pe.pucp.edu.pe.siscomfi.controller.Point;
import pe.pucp.edu.pe.siscomfi.controller.Signatures;

public class Main {

	public static void main(String[] args) {
		BufferedImage in = null;
		BufferedImage dbimg = null;

		// try {
		ImagePlus img = IJ.openImage("C:\\Users\\samoel\\Desktop\\TestImage\\padron.jpg");
		IJ.run(img, "Rotate 90 Degrees Right", "");
		IJ.run(img, "Make Binary", "");
		int y = 522, x = 0, r = 0, g, b;
		while (r == 0) {
			r = img.getPixel(x, y)[0];
			x++;
		}
		img.setRoi(x + 1, 0, img.getWidth() - x, img.getHeight());
		IJ.run(img, "Crop", "");

		y = img.getHeight();
		System.out.println("antes: " + y);
		x = 20; r= 0;
		while (r == 0) {
			r = img.getPixel(x, y)[0];
			y--;
		}
		System.out.println("despues: " + y);
		img.setRoi(0, 0, img.getWidth(), y);
		IJ.run(img, "Crop", "");
		
		x = 20; r = 0; y = img.getHeight();
		while (r != 0) {
			r = img.getPixel(x, y)[0];
			y--;
		}
		while (r == 0) {
			r = img.getPixel(x, y)[0];
			y--;
		}
		
		int fila = img.getHeight() - y;
		img.show();
		/*
		 * OCR ocr = new OCR(); //ocr.train(); DataSet trainingSet = null;
		 * //trainingSet.save(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset.data");
		 * //trainingSet.saveAsTxt(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset.txt", ",");
		 * //String tstring = trainingSet.toCSV(); //PrintWriter out = new
		 * PrintWriter(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset2.data");
		 * //out.println(tstring); //out.close(); trainingSet = DataSet.load(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset.data");
		 * //trainingSet = DataSet.createFromFile(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset.txt", 10000, 784,
		 * " "); //trainingSet = DataSet.createFromFile(filePath, 10000, 784,
		 * " "); NeuralNetwork neuralNetwork = new
		 * MultiLayerPerceptron(TransferFunctionType.SIGMOID, 784, 784, 4);
		 * BackPropagation backPropagation = new BackPropagation();
		 * backPropagation.setMaxIterations(10);
		 * neuralNetwork.learn(trainingSet, backPropagation);
		 * 
		 * 
		 * for(int i = 1 ; i <25;i++){ BufferedImage test = ImageIO.read(new
		 * File("C:\\Users\\samoel\\Desktop\\TestImage\\test\\p"+i+".jpg"));
		 * test = OCR.resizeImage(test, 28, 28, test.getType()); test =
		 * Binarization.binarize(test); int [][] matTest =
		 * Binarization.imgToMat(test); double testVect[] =
		 * ocr.aplastar(matTest); neuralNetwork.setInput(testVect);// vect de
		 * una imagen esperada neuralNetwork.calculate();
		 * 
		 * System.out.println("imagen: " + i +" result number: " +
		 * (neuralNetwork.getOutput()[0]) + " " + neuralNetwork.getOutput()[1] +
		 * " " + neuralNetwork.getOutput()[2] + " " +
		 * neuralNetwork.getOutput()[3]); }
		 */

		/*
		 * String file = "C:/Users/samoel/Desktop/TestImage/ff3.jpg"; in =
		 * ImageIO.read(new File(file)); // sospechosa file =
		 * "C:/Users/samoel/Desktop/TestImage/f3.jpg"; dbimg = ImageIO.read(new
		 * File(file)); //original double res =
		 * Signatures.compareSignatures(dbimg, in); System.out.println(
		 * "resultado: " + res * 100);
		 */

		/*
		 * JPanel mainPanel = new JPanel(new BorderLayout()); JLabel lblimage =
		 * new JLabel(new ImageIcon(in)); mainPanel.add(lblimage); JFrame frame
		 * = new JFrame(); frame.getContentPane().add(lblimage,
		 * BorderLayout.CENTER); frame.setSize(300, 300);
		 * frame.setVisible(true);
		 */

		/*
		 * } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * /* BufferedImage in = null; try { in = ImageIO.read(new
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
		/*
		 * 
		 * 
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
