package pe.pucp.edu.pe.siscomfi;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import ij.IJ;
import ij.ImagePlus;
import pe.pucp.edu.pe.siscomfi.controller.Fingerprint;

public class Main {

	public static void main(String[] args) throws IOException {
		// CARGAR OCR - COMPARAR
		OcrFinal ocr = new OcrFinal();
		ocr.cargarEntrenamiento();
		ocr.entrenarRed();
		for (int i = 1; i < 25; i++) {
			ImagePlus imp = IJ.openImage("C:\\Users\\samoel\\Desktop\\TestImage\\test\\p" + i + ".JPG");
			IJ.run(imp, "Make Binary", ""); // imp.show(); BufferedImage
			BufferedImage img = imp.getBufferedImage();
			System.out.print("resultado de p" + i + ": ");
			ocr.reconocer(img);
		}

		/*
		 * PARTE PARA CORTAR PLANILLON ImagePlus img = IJ.openImage(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\padron\\padron3.jpg");
		 * ImagePlus recortado = HelperMethods.recortarPlanillon(img,img);
		 * ImagePlus recortadoOriginal = new Duplicator().run(recortado);
		 * 
		 * List<ImagePlus> lista = HelperMethods.getFilasPlanillon(recortado);
		 * 
		 * List<ImagePlus> parteLista =
		 * HelperMethods.getPartesFila(lista.get(1), recortadoOriginal);
		 * for(ImagePlus mm : parteLista){ mm.show(); }
		 */

		/*
		 * HUELLAS ACTUAL MEJORADO double[][] graphOriginal =
		 * Fingerprint.imageGraph(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\nuevo\\002_2.jpg"); String
		 * filename = ""; for (int i = 2; i < 50; i++) { for (int j = 1; j < 3;
		 * j++) { if (i < 10) filename =
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\nuevo\\00" + i + "_" + j +
		 * ".jpg"; else filename =
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\nuevo\\0" + i + "_" + j +
		 * ".jpg";
		 * 
		 * double[][] graphSuspect = Fingerprint.imageGraph(filename); double
		 * res = Fingerprint.comparition(graphOriginal, graphSuspect);
		 * System.out.println(Fingerprint.resultado(res) + " Porcentaje: " +
		 * res); } }
		 */

		/*
		 * ImagePlus impOriginal =
		 * IJ.openImage("C:\\Users\\samoel\\Desktop\\TestImage\\f3.jpg");
		 * ImagePlus impSuspect =
		 * IJ.openImage("C:\\Users\\samoel\\Desktop\\TestImage\\ff.jpg");;
		 * double res =
		 * Signatures.compareSignatures(impOriginal.getBufferedImage(),
		 * impSuspect.getBufferedImage()); System.out.println(res);
		 */
		/*
		 * NUEVO INTENTO DE HUELLAS ImagePlus baseImage = IJ.openImage(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\nuevo\\001_1.jpg");
		 * IJ.run(baseImage, "Make Binary", ""); IJ.run(baseImage,
		 * "Skeletonize", ""); BufferedImage bfIBaseImage =
		 * baseImage.getBufferedImage(); int[][] sklBaseImage =
		 * HelperMethods.imgToMat(bfIBaseImage); List<Point> mntBaseImage =
		 * Fingerprint.getMinutiaes(sklBaseImage); mntBaseImage =
		 * Fingerprint.removeFalseMinutae2(sklBaseImage, mntBaseImage);
		 * List<MinutaePoint> mpBaseImage = new ArrayList<MinutaePoint>(); for
		 * (Point p : mntBaseImage) { Point[] vecinos =
		 * Fingerprint.getNearestNeighbourType(p, mntBaseImage); MinutaePoint
		 * pMin = Fingerprint.getMinutaePoint(p, vecinos);
		 * mpBaseImage.add(pMin); }
		 * 
		 * ImagePlus inputImage = IJ.openImage(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\nuevo\\001_2.jpg");
		 * IJ.run(inputImage, "Make Binary", ""); IJ.run(inputImage,
		 * "Skeletonize", ""); BufferedImage bfIInputImage =
		 * inputImage.getBufferedImage(); int[][] sklInputImage =
		 * HelperMethods.imgToMat(bfIInputImage); List<Point> mntInputImage =
		 * Fingerprint.getMinutiaes(sklInputImage); mntInputImage =
		 * Fingerprint.removeFalseMinutae2(sklInputImage, mntInputImage);
		 * 
		 * List<MinutaePoint> mpInputImage = new ArrayList<MinutaePoint>(); for
		 * (Point p : mntInputImage) { Point[] vecinos =
		 * Fingerprint.getNearestNeighbourType(p, mntInputImage); MinutaePoint
		 * pMin = Fingerprint.getMinutaePoint(p, vecinos);
		 * mpInputImage.add(pMin); } //CcpMinutaes ccpMinutaes =
		 * Fingerprint.compareMinutaePoint(mpBaseImage, mpInputImage);
		 * List<MinutaePoint> c2B =
		 * Fingerprint.compareMinutaePoint2(mpBaseImage, mpInputImage);
		 * List<MinutaePoint> c2I =
		 * Fingerprint.compareMinutaePoint2(mpInputImage, mpBaseImage); Point[]
		 * cordOrdBase = new Point[c2B.size()]; Point[] cordOrdInput = new
		 * Point[c2I.size()]; //base int cont = 0; for (MinutaePoint point :
		 * c2B) { cordOrdBase[cont++] = point.getCord(); } Point[] arrAux = new
		 * Point[cordOrdBase.length-1]; for(int i = 1 ; i <
		 * cordOrdBase.length;i++) arrAux[i-1] = cordOrdBase[i];
		 * Arrays.sort(arrAux); for(int i = 0 ; i < arrAux.length;i++)
		 * cordOrdBase[i+1] = arrAux[i]; for(Point point : cordOrdBase){
		 * System.out.println("Base : x = " + point.getX() + " y = " +
		 * point.getY()); } //input cont = 0; for (MinutaePoint point : c2I) {
		 * cordOrdInput[cont++] = point.getCord(); } Point[] arrAux2 = new
		 * Point[cordOrdInput.length-1]; for(int i = 1 ; i <
		 * cordOrdInput.length;i++) arrAux2[i-1] = cordOrdInput[i];
		 * Arrays.sort(arrAux2); for(int i = 0 ; i < arrAux2.length;i++)
		 * cordOrdInput[i+1] = arrAux2[i]; for(Point point : cordOrdInput){
		 * System.out.println("Input : x = " + point.getX() + " y = " +
		 * point.getY()); }
		 */

		/*
		 * Mnist mmm = new Mnist("t10k-labels.idx1-ubyte",
		 * "t10k-images.idx3-ubyte"); List<DigitImage> list =
		 * mmm.loadDigitImages();
		 * 
		 * int i = 0; for (DigitImage img : list) { BufferedImage img2 = new
		 * BufferedImage(28, 28, BufferedImage.TYPE_INT_ARGB); byte[] arr =
		 * img.getImageData(); System.out.println("numero " + img.getLabel());
		 * 
		 * int cont = 0; for (int w = 0; w < 28; w++) { for (int y = 0; y < 28;
		 * y++) { img2.setRGB(y, w, arr[cont++]); } }
		 * 
		 * ImagePlus imp = new ImagePlus("img", img2); IJ.run(imp, "Make Binary"
		 * , ""); IJ.saveAs(imp, "Jpeg",
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\mnist\\p"+i+".jpg"); i++;
		 * /*JPanel mainPanel = new JPanel(new BorderLayout()); JLabel lblimage
		 * = new JLabel(new ImageIcon(imp.getBufferedImage()));
		 * mainPanel.add(lblimage); JFrame frame = new JFrame();
		 * frame.getContentPane().add(lblimage, BorderLayout.CENTER);
		 * frame.setSize(500, 500); frame.setVisible(true);
		 * Binarizacion.writeImage(imp.getBufferedImage(), "esqueleto"); break;
		 */

		// }
		/*
		 * Ocrv2 ocr = new Ocrv2(); ocr.add_actionPerformed(); ocr.trainSOM();
		 * for(int i = 1 ; i <25;i++){ System.out.print("p" + i +": ");
		 * ocr.recognize_actionPerformed(i); }
		 * 
		 * /* BasicNetwork network = new BasicNetwork(); network.addLayer(new
		 * BasicLayer(null, true, 784)); network.addLayer(new BasicLayer(new
		 * ActivationElliott(), true, 100)); network.addLayer(new BasicLayer(new
		 * ActivationElliott(), true, 10));
		 * network.getStructure().finalizeStructure(); network.reset();
		 * 
		 * MLDataSet trainingSet = MNISTReader.getDataSet(
		 * "C:\\Users\\samoel\\Desktop\\Dp1\\t10k-labels.idx1-ubyte",
		 * "C:\\Users\\samoel\\Desktop\\Dp1\\t10k-images.idx3-ubyte");
		 * Propagation train = new
		 * org.encog.neural.networks.training.propagation.resilient.
		 * ResilientPropagation(network, trainingSet);
		 * 
		 * int epochsCount = 100; for (int epoch = 1; epoch < epochsCount;
		 * epoch++) { train.iteration(); System.out.println("i :" + epoch); }
		 * 
		 * train.finishTraining(); for (int i = 1; i < 25; i++) { ImagePlus imp
		 * = IJ.openImage("C:\\Users\\samoel\\Desktop\\TestImage\\test\\p" + 1 +
		 * ".JPG"); IJ.run(imp, "Make Binary", ""); IJ.run(imp, "XOR...",
		 * "value=11110000"); BufferedImage img = imp.getBufferedImage(); img =
		 * OCR.resizeImage(img, 28, 28, img.getType()); MLData input = new
		 * BasicMLData(OCR.imgToDouble(img)); int winner =
		 * network.winner(input); System.out.println("winner? : " + winner); }
		 * 
		 * // IJ.run(imp, "XOR...", "value=11110000"); // OCR ocr = new OCR();
		 * /* DataSet trainingSet = null; // trainingSet.save(
		 * "C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset.data");
		 * trainingSet =
		 * DataSet.load("C:\\Users\\samoel\\Desktop\\TestImage\\prep\\tset.data"
		 * ); NeuralNetwork neuralNetwork = new
		 * MultiLayerPerceptron(TransferFunctionType.SIGMOID, 784, 784, 4);
		 * BackPropagation backPropagation = new BackPropagation();
		 * backPropagation.setMaxIterations(10);
		 * //neuralNetwork.learn(trainingSet, backPropagation);
		 * 
		 * /*for (int i = 1; i < 25; i++) { BufferedImage test =
		 * ImageIO.read(new
		 * File("C:\\Users\\samoel\\Desktop\\TestImage\\test\\p" + i + ".jpg"));
		 * test = OCR.resizeImage(test, 28, 28, test.getType()); test =
		 * Binarization.binarize(test); int[][] matTest =
		 * Binarization.imgToMat(test); double testVect[] =
		 * OCR.aplastar(matTest); neuralNetwork.setInput(testVect);// vect de
		 * una imagen esperada neuralNetwork.calculate();
		 * 
		 * System.out.println("imagen: " + i + " result number: " +
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
