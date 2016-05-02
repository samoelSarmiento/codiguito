package pe.pucp.edu.pe.siscomfi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
import java.security.Signature;
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
import pe.pucp.edu.pe.siscomfi.controller.Constants;
import pe.pucp.edu.pe.siscomfi.controller.Fingerprint;
import pe.pucp.edu.pe.siscomfi.controller.Point;
import pe.pucp.edu.pe.siscomfi.controller.Signatures;

public class Main {

	public static void main(String[] args) {
		BufferedImage in = null;
		try {
			String file = "C:/Users/samoel/Desktop/TestImage/ff3.jpg";
			in = ImageIO.read(new File(file));
			in = Binarization.binarize(in);
			// in = Signatures.removeNoise(in);
			Rectangle rec = Signatures.getRectangularArea(in);

			// cortamos segun el rectangulo
			BufferedImage crop = in.getSubimage((int) rec.getY(), (int) rec.getX(), (int) rec.getHeight(),
					(int) rec.getWidth());
			// saca el angulo de rotacion
			Point pRot = Signatures.getAngleImage(crop);
			// rota la imagen denuevo
			//System.out.println(Math.toDegrees(pRot.getAngle()));
			BufferedImage rot = null;
			if(pRot.getAngle() > 0 ) 
				rot = HelperMethods.rotate(crop,-pRot.getAngle()/2+Math.PI/18);
			else
				rot = HelperMethods.rotate(crop,-pRot.getAngle()/2);
			// calculo denuevo el rectanuglo
			Rectangle rec2 = Signatures.getRectangularArea(rot);

			/// ---ver imagenes---///
			JPanel mainPanel = new JPanel(new BorderLayout());
			BufferedImage crop2 = rot.getSubimage((int) rec2.getY(), (int) rec2.getX(), (int) rec2.getHeight(),
					(int) rec2.getWidth());
			JLabel lblimage = new JLabel(new ImageIcon(crop2));
			mainPanel.add(lblimage);
			JFrame frame = new JFrame();
			frame.getContentPane().add(lblimage, BorderLayout.CENTER);
			frame.setSize(500, 500);
			frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
