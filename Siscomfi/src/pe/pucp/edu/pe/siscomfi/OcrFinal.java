package pe.pucp.edu.pe.siscomfi;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.clustercopy.SOMClusterCopyTraining;

import ij.IJ;
import ij.ImagePlus;

public class OcrFinal {
	static final int DOWNSAMPLE_WIDTH = 28;
	static final int DOWNSAMPLE_HEIGHT = 28;
	private final Entry entry = new Entry();
	private final DefaultListModel letterListModel = new DefaultListModel();
	private final List<String> lettersL = new ArrayList<String>();
	private final Sample sample = new Sample(OcrEncog.DOWNSAMPLE_WIDTH, OcrEncog.DOWNSAMPLE_HEIGHT);
	private SOM net;

	private BufferedImage mnistToBImg(DigitImage img) {
		BufferedImage img2 = new BufferedImage(28, 28, BufferedImage.TYPE_INT_ARGB);
		byte[] arr = img.getImageData();
		int cont = 0;
		for (int w = 0; w < 28; w++) {
			for (int y = 0; y < 28; y++) {
				img2.setRGB(y, w, arr[cont++]);
			}
		}

		ImagePlus imp = new ImagePlus("img", img2);
		IJ.run(imp, "Make Binary", "");
		return imp.getBufferedImage();
	}

	private void add_actionPerformed() throws IOException {
		int i;
		Mnist mmm = new Mnist("t10k-labels.idx1-ubyte", "t10k-images.idx3-ubyte");
		List<DigitImage> list = mmm.loadDigitImages();
		this.entry.setSample(this.sample);
		for (int w = 0; w < list.size(); w++) {
			DigitImage mnistImage = list.get(w);
			String letter = mnistImage.getLabel() + "";
			lettersL.add(letter);

			this.entry.entryImage = mnistToBImg(mnistImage);
			this.entry.downSample();
			final SampleData sampleData = (SampleData) this.sample.getData().clone();
			sampleData.setLetter(letter.charAt(0));
			this.letterListModel.add(this.letterListModel.size(), sampleData);
		}
	}

	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	public void load_actionPerformed() {
		try {
			FileReader f;// the actual file stream
			BufferedReader r;// used to read the file line by line

			f = new FileReader(new File("./sample.dat"));
			r = new BufferedReader(f);
			String line;
			int i = 0;

			this.letterListModel.clear();
			this.lettersL.clear();
			while ((line = r.readLine()) != null) {
				lettersL.add("" + line.charAt(0));
				final SampleData ds = new SampleData(line.charAt(0), OcrEncog.DOWNSAMPLE_WIDTH,
						OcrEncog.DOWNSAMPLE_HEIGHT);
				this.letterListModel.add(i++, ds);
				int idx = 2;
				for (int y = 0; y < ds.getHeight(); y++) {
					for (int x = 0; x < ds.getWidth(); x++) {
						ds.setData(x, y, line.charAt(idx++) == '1');
					}
				}
			}

			r.close();
			f.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void recognize_actionPerformed(BufferedImage image) {
		if (this.net == null) {
			System.out.println("I need to be trained first!");
			return;
		}
		// SampleData sdata = new SampleData(' ', image.getWidth(),
		// image.getHeight());
		this.entry.setSample(this.sample);
		this.entry.entryImage = image;
		this.entry.downSample();

		final MLData input = new BasicMLData(OcrEncog.DOWNSAMPLE_WIDTH * OcrEncog.DOWNSAMPLE_HEIGHT);
		int idx = 0;
		final SampleData ds = this.sample.getData();
		for (int y = 0; y < ds.getHeight(); y++) {
			for (int x = 0; x < ds.getWidth(); x++) {
				input.setData(idx++, ds.getData(x, y) ? .5 : -.5);
			}
		}

		final int best = this.net.classify(input);
		final String letra = lettersL.get(best);
		System.out.println("That Letter Is " + letra + " (Neuron #" + best + " fired)");

	}

	public void trainSOM() {
		try {
			final int inputNeuron = OcrEncog.DOWNSAMPLE_HEIGHT * OcrEncog.DOWNSAMPLE_WIDTH;
			final int outputNeuron = this.letterListModel.size();

			final MLDataSet trainingSet = new BasicMLDataSet();
			for (int t = 0; t < this.letterListModel.size(); t++) {
				final MLData item = new BasicMLData(inputNeuron);
				int idx = 0;
				final SampleData ds = (SampleData) this.letterListModel.getElementAt(t);
				for (int y = 0; y < ds.getHeight(); y++) {
					for (int x = 0; x < ds.getWidth(); x++) {
						item.setData(idx++, ds.getData(x, y) ? .5 : -.5);
					}
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
}
