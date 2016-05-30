package pe.pucp.edu.pe.siscomfi;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.clustercopy.SOMClusterCopyTraining;

import ij.IJ;
import ij.ImagePlus;

public class Ocrv2 {
	private SOM net;
	private final List<String> letters = new ArrayList<String>();
	private final DefaultListModel letterListModel = new DefaultListModel();

	public void add_actionPerformed() throws IOException {
		Mnist mmm = new Mnist("t10k-labels.idx1-ubyte", "t10k-images.idx3-ubyte");
		List<DigitImage> list = mmm.loadDigitImages();

		for (int i = 0; i < list.size(); i++) {
			this.letterListModel.add(this.letterListModel.size(), list.get(i));
			letters.add(""+list.get(i).getLabel());
		}
	}

	public void trainSOM() {
		try {
			final int inputNeuron = 28 * 28;
			final int outputNeuron = this.letterListModel.size();

			final MLDataSet trainingSet = new BasicMLDataSet();
			for (int t = 0; t < this.letterListModel.size(); t++) {
				final MLData item = new BasicMLData(inputNeuron);
				int idx = 0;
				final DigitImage ds = (DigitImage) this.letterListModel.getElementAt(t);
				byte[] arr = ds.getImageData();
				for (int y = 0; y < arr.length; y++) {
					item.setData(idx++, arr[y]);
				}
				trainingSet.add(new BasicMLDataPair(item, null));
			}

			this.net = new SOM(inputNeuron, outputNeuron);
			this.net.reset();

			SOMClusterCopyTraining train = new SOMClusterCopyTraining(this.net, trainingSet);

			train.iteration();

		} catch (final Exception e) {
			e.printStackTrace();

		}

	}

	public void recognize_actionPerformed(int ind) {
		ImagePlus imp = IJ.openImage("C:\\Users\\samoel\\Desktop\\TestImage\\test\\p" + ind + ".JPG");
		IJ.run(imp, "Make Binary", "");
		IJ.run(imp, "XOR...", "value=11110000");
		BufferedImage img = imp.getBufferedImage();
		img = OCR.resizeImage(img, 28, 28, img.getType());

		MLData item = new BasicMLData(28 * 28);
		int idx = 0;
		double[] arr = OCR.imgToDouble(img);
		for (int y = 0; y < arr.length; y++) {
			item.setData(idx++, arr[y] == 0.0 ? 0 : 1);
		}
		final int best = this.net.classify(item);
		System.out.println("best: " + best);
		final String letter = letters.get(best);
		System.out.println("That Letter Is: " + letter + "(Neuron #" + best + " fired)");

	}

	public char[] mapNeurons() {
		char map[] = new char[this.letterListModel.size()];
		System.out.println("letter list model size:  " + this.letterListModel.size());
		MLData item = null;
		DigitImage ds = null;
		for (int i = 0; i < map.length; i++) {
			map[i] = '?';
		}
		for (int i = 0; i < this.letterListModel.size(); i++) {
			item = new BasicMLData(28 * 28);
			int idx = 0;
			ds = (DigitImage) this.letterListModel.getElementAt(i);
			byte[] arr = ds.getImageData();
			for (int y = 0; y < arr.length; y++) {
				item.setData(idx++, arr[y] == 0 ? .5 : -.5);
			}
			int best = this.net.classify(item);
			
			map[best] = ("" + ds.getLabel()).charAt(0);
		}

		
		return map;
	}

}
