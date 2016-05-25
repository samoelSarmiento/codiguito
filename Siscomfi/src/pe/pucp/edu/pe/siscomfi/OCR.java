package pe.pucp.edu.pe.siscomfi;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

import net.coobird.thumbnailator.Thumbnails;

public class OCR {
	public static DataSet tSet;

	public static double[] aplastar(int[][] matriz) {
		double[] arreglo = new double[matriz.length * matriz[0].length];
		int cont = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				arreglo[cont++] = matriz[i][j];
			}
		}

		return arreglo;
	}

	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	public static double[] byteTodouble(byte[] arr) {
		double[] res = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i] * 1.0;
		}
		return res;
	}

	public static void train() throws IOException {
		DataSet trainingSet = new DataSet(784);

		Mnist mmm = new Mnist("t10k-labels.idx1-ubyte", "t10k-images.idx3-ubyte");
		List<DigitImage> list = mmm.loadDigitImages();
		int tam = list.size();
		for (int i = 0; i < tam; i++) {// for each number
			byte[] arr = list.get(i).getImageData();
			double vect[] = byteTodouble(arr);
			// neural network
			int li = list.get(i).getLabel();
			if (li == 0)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 0 }));
			if (li == 1)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 1 }));
			if (li == 2)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 1, 0 }));
			if (li == 3)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 1, 1 }));
			if (li == 4)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 0 }));
			if (li == 5)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 1 }));
			if (li == 6)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 1, 0 }));
			if (li == 7)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 1, 1 }));
			if (li == 8)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 0 }));
			if (li == 9)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 1 }));
		}
		tSet = trainingSet;
	}

	public static void main(String[] args) throws IOException {
		// TODO code application logic here

		DataSet trainingSet = new DataSet(784);

		Mnist mmm = new Mnist("t10k-labels.idx1-ubyte", "t10k-images.idx3-ubyte");
		List<DigitImage> list = mmm.loadDigitImages();
		int tam = list.size();
		for (int i = 0; i < tam; i++) {// for each number
			byte[] arr = list.get(i).getImageData();
			double vect[] = byteTodouble(arr);
			// neural network
			int li = list.get(i).getLabel();
			if (li == 0)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 0 }));
			if (li == 1)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 1 }));
			if (li == 2)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 1, 0 }));
			if (li == 3)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 1, 1 }));
			if (li == 4)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 0 }));
			if (li == 5)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 1 }));
			if (li == 6)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 1, 0 }));
			if (li == 7)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 1, 1 }));
			if (li == 8)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 0 }));
			if (li == 9)
				trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 1 }));
		}

		/*
		 * for (int i = 9; i >= 0; i--) {// for each number for (int j = 0; j <
		 * 5; j++) {// training // read image BufferedImage img; File route =
		 * new File( "C:\\Users\\samoel\\Desktop\\TestImage\\numbers\\" + i + "_
		 * " + (j + 1) + ".jpg"); img = ImageIO.read(route); img =
		 * resizeImage(img,30,30,img.getType()); // binarize BufferedImage
		 * binImg = Binarization.binarize(img);
		 * 
		 * int[][] mati = new int[binImg.getWidth()][]; for (int x = 0; x <
		 * binImg.getWidth(); x++) { mati[x] = new int[binImg.getHeight()]; for
		 * (int y = 0; y < binImg.getHeight(); y++) { mati[x][y] = (byte)
		 * (binImg.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1); } } // skeletize int
		 * skelImg[][] = Thining.doZhangSuenThinning(mati, false); // vector
		 * double vect[] = aplastar(skelImg); // neural network if (i == 0)
		 * trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 0, 0, 0
		 * })); if (i == 1) trainingSet.addRow(new DataSetRow(vect, new double[]
		 * { 0, 0, 0, 1 })); if (i == 2) trainingSet.addRow(new DataSetRow(vect,
		 * new double[] { 0, 0, 1, 0 })); if (i == 3) trainingSet.addRow(new
		 * DataSetRow(vect, new double[] { 0, 0, 1, 1 })); if (i == 4)
		 * trainingSet.addRow(new DataSetRow(vect, new double[] { 0, 1, 0, 0
		 * })); if (i == 5) trainingSet.addRow(new DataSetRow(vect, new double[]
		 * { 0, 1, 0, 1 })); if (i == 6) trainingSet.addRow(new DataSetRow(vect,
		 * new double[] { 0, 1, 1, 0 })); if (i == 7) trainingSet.addRow(new
		 * DataSetRow(vect, new double[] { 0, 1, 1, 1 })); if (i == 8)
		 * trainingSet.addRow(new DataSetRow(vect, new double[] { 1, 0, 0, 0
		 * })); if (i == 9) trainingSet.addRow(new DataSetRow(vect, new double[]
		 * { 1, 0, 0, 1 })); System.out.println("imagen " + (j + 1) + " de " +
		 * i); } }
		 */

		// System.out.println(trainingSet);
		// trainingSet.saveAsTxt("C:\\Users\\samoel\\Desktop\\TestImage\\prep\\a.txt",
		// " ");
		tSet = trainingSet;
		NeuralNetwork neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 784, 784, 4);
		BackPropagation backPropagation = new BackPropagation();
		backPropagation.setMaxIterations(10);
		neuralNetwork.learn(trainingSet, backPropagation);

		// test
		/*
		 * for(int i = 1 ; i <25;i++){ BufferedImage test = ImageIO.read(new
		 * File("C:\\Users\\samoel\\Desktop\\TestImage\\test\\p"+i+".jpg"));
		 * test = resizeImage(test, 30, 30, test.getType()); test =
		 * Binarization.binarize(test); int [][] matTest =
		 * Binarization.imgToMat(test); double testVect[] = aplastar(matTest);
		 * neuralNetwork.setInput(testVect);// vect de una imagen esperada
		 * neuralNetwork.calculate();
		 * 
		 * System.out.println("imagen: " + i +" result number: " +
		 * (neuralNetwork.getOutput()[0]) + " " + neuralNetwork.getOutput()[1] +
		 * " " + neuralNetwork.getOutput()[2] + " " +
		 * neuralNetwork.getOutput()[3]); }
		 */
	}

}
