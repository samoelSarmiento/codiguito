/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.pucp.edu.pe.siscomfi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author Alexis
 */
public class OCR {

	/**
	 * @param args
	 *            the command line arguments
	 */

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

	public static void main(String[] args) throws IOException {
		// TODO code application logic here
		NeuralNetwork neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 900, 900, 4);
		DataSet trainingSet = new DataSet(900, 4);

		double testVect[] = new double[900];

		for (int i = 9; i >= 0; i--) {// for each number

			for (int j = 0; j < 5; j++) {// training

				// read image
				BufferedImage img;
				File route = new File(
						"C:/Users/Alexis/Dropbox/Codigo/Java/opencv/OCR/numbers30/" + i + "_" + (j + 1) + ".jpg");
				img = ImageIO.read(route);

				// binarize
				BufferedImage binImg = Binarizacion.binarize(img);

				int[][] mati = new int[binImg.getWidth()][];
				for (int x = 0; x < binImg.getWidth(); x++) {
					mati[x] = new int[binImg.getHeight()];
					for (int y = 0; y < binImg.getHeight(); y++) {
						mati[x][y] = (byte) (binImg.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
					}
				}

				// skeletize
				int skelImg[][] = thining1.doZhangSuenThinning(mati, false);

				// vector
				double vect[] = Aplastar(skelImg);
				
				// neural network
				if (i == 0)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 0 }));
				if (i == 1)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 1 }));
				if (i == 2)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 1, 0 }));
				if (i == 3)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 1, 1 }));
				if (i == 4)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 0 }));
				if (i == 5)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 1 }));
				if (i == 6)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 1, 0 }));
				if (i == 7)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 1, 1 }));
				if (i == 8)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 0 }));
				if (i == 9)
					trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 1 }));

				System.out.println("imagen " + (j + 1) + " de " + i);
			}
		}

		// System.out.println(trainingSet);
		trainingSet.saveAsTxt("C:\\Users\\Alexis\\Desktop\\tset\\tset.txt", " ");
		BackPropagation backPropagation = new BackPropagation();
		backPropagation.setMaxIterations(10);
		neuralNetwork.learn(trainingSet, backPropagation);

		// test
		neuralNetwork.setInput(testVect);// vect de una imagen esperada
		neuralNetwork.calculate();

		double output = neuralNetwork.getOutput()[0];
		// System.out.println("number: " + output);
		System.out.println("number: " + (neuralNetwork.getOutput()[0]) + " " + neuralNetwork.getOutput()[1] + " "
				+ neuralNetwork.getOutput()[2] + " " + neuralNetwork.getOutput()[3]);
	}

}
