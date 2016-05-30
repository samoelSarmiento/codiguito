package pe.pucp.edu.pe.siscomfi.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import pe.pucp.edu.pe.siscomfi.Binarization;
import pe.pucp.edu.pe.siscomfi.CcpMinutaes;
import pe.pucp.edu.pe.siscomfi.HelperMethods;
import pe.pucp.edu.pe.siscomfi.MinutaePoint;
import pe.pucp.edu.pe.siscomfi.MinutaeTuple;
import pe.pucp.edu.pe.siscomfi.Thining;

public class Fingerprint {
	private static int width;
	private static int height;
	private static int k = 6;

	private static double mayor(double m, double n) {
		if (m > n)
			return m;
		return n;
	}

	private static double menor(double m, double n) {
		if (m < n)
			return m;
		return n;
	}

	private static double getRatio(Point a, Point b, Point c) {
		double pAC = a.euclideanDistance(c);
		double pAB = a.euclideanDistance(b);
		double ratio = mayor(pAC, pAB) / menor(pAC, pAB);
		return ratio;
	}

	public static MinutaePoint getMinutaePoint(Point i, Point[] vecinos) {
		List<MinutaeTuple> tuples = new ArrayList<MinutaeTuple>();
		tuples.add(new MinutaeTuple(getRatio(i, vecinos[1], vecinos[2]),
				HelperMethods.getAngle3Point(i, vecinos[1], vecinos[2])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[1], vecinos[3]),
				HelperMethods.getAngle3Point(i, vecinos[1], vecinos[3])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[1], vecinos[4]),
				HelperMethods.getAngle3Point(i, vecinos[1], vecinos[4])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[1], vecinos[5]),
				HelperMethods.getAngle3Point(i, vecinos[1], vecinos[5])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[2], vecinos[3]),
				HelperMethods.getAngle3Point(i, vecinos[2], vecinos[3])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[2], vecinos[4]),
				HelperMethods.getAngle3Point(i, vecinos[2], vecinos[4])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[2], vecinos[5]),
				HelperMethods.getAngle3Point(i, vecinos[2], vecinos[5])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[3], vecinos[4]),
				HelperMethods.getAngle3Point(i, vecinos[3], vecinos[4])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[3], vecinos[5]),
				HelperMethods.getAngle3Point(i, vecinos[3], vecinos[5])));

		tuples.add(new MinutaeTuple(getRatio(i, vecinos[4], vecinos[5]),
				HelperMethods.getAngle3Point(i, vecinos[3], vecinos[4])));
		return new MinutaePoint(i, tuples);
	}

	private static boolean compareTuples(List<MinutaeTuple> base, List<MinutaeTuple> input) {
		int comp = 0;
		for (MinutaeTuple mtBase : base) {
			for (MinutaeTuple mtInput : input) {
				boolean mRatio = mtBase.getRatio() == mtInput.getRatio() ;
				boolean mAngle = Math.abs(mtBase.getAngle() - mtInput.getAngle()) <= 3.5;
				if (mRatio && mAngle)
					comp++;
				if (comp == 2)
					return true;
			}
		}
		return false;
	}
	public static List<MinutaePoint> compareMinutaePoint2(List<MinutaePoint> base, List<MinutaePoint> input) {
		List<MinutaePoint> ccpBase = new ArrayList<MinutaePoint>();
		boolean arrBool[] = new boolean[input.size()];
		for (MinutaePoint mpBase : base) {
			List<MinutaeTuple> mtBase = mpBase.getTuples();
			for (int i = 0; i < input.size(); i++) {
				if (!arrBool[i]) {
					MinutaePoint mpInput = input.get(i);
					boolean resultado = compareTuples(mtBase, mpInput.getTuples());
					if (resultado) {
						ccpBase.add(mpBase);
						arrBool[i] = true;
						break;
					}
				}
			}
		}
		return ccpBase;
	}
	public static CcpMinutaes compareMinutaePoint(List<MinutaePoint> base, List<MinutaePoint> input) {
		List<MinutaePoint> ccpBase = new ArrayList<MinutaePoint>();
		List<MinutaePoint> ccpInput = new ArrayList<MinutaePoint>();
		boolean arrBool[] = new boolean[input.size()];
		for (MinutaePoint mpBase : base) {
			List<MinutaeTuple> mtBase = mpBase.getTuples();
			for (int i = 0; i < input.size(); i++) {
				if (!arrBool[i]) {
					MinutaePoint mpInput = input.get(i);
					boolean resultado = compareTuples(mtBase, mpInput.getTuples());
					if (resultado) {
						ccpBase.add(mpBase);
						ccpInput.add(mpInput);
						arrBool[i] = true;
						break;
					}
				}
			}
		}
		return new CcpMinutaes(ccpBase, ccpInput);
	}

	// return the crossing number of the 3x3 matrix
	public static int CrossingNumber(int[][] CNMatrix) {
		int cn = 0, width = 3, heigth = 3;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < heigth; j++) {
				if (i != j)
					if (CNMatrix[i][j] == 1)
						cn++;
			}
		return cn;
	}

	// returns the 3x3 matrix around the x,y point
	public static int[][] getCNMatrix(int[][] skelMatrix, int x, int y) {
		int[][] cnm = new int[3][3];
		if (x > 0 && y > 0 && x < width - 1 && y < height - 1) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					cnm[i][j] = skelMatrix[i + x - 1][j + y - 1];
				}
			}
		}
		return cnm;
	}

	public static boolean isBifurcation(int[][] cnmatrix) {
		int P1, P2, P3, P4, P5, P6, P7, P8;
		P8 = cnmatrix[0][0];
		P1 = cnmatrix[0][1];
		P2 = cnmatrix[0][2];
		P7 = cnmatrix[1][0];
		P3 = cnmatrix[1][2];
		P6 = cnmatrix[2][0];
		P5 = cnmatrix[2][1];
		P4 = cnmatrix[2][2];
		if ((P3 == 0 && P5 == 0 && P7 == 0) || (P3 == 0 && P5 == 1 && P7 == 0 && P8 == 1 && P2 == 1)
				|| (P3 == 0 && P5 == 0 && P7 == 1 && P2 == 1 && P4 == 1)
				|| (P3 == 0 && P5 == 0 && P7 == 0 && P4 == 1 && P6 == 1)
				|| (P3 == 1 && P5 == 0 && P7 == 0 && P6 == 1 && P8 == 1)
				|| (P8 != P6 && P1 != P5 && P2 != P4 && P7 == P3 && P8 == P2 && P6 == P4)
				|| (P8 == P6 && P1 == P5 && P2 == P4 && P7 != P3 && P8 != P2 && P6 != P4)
				|| (P8 == P2 && P2 == P4 && P4 == P6 && P6 == P8 && P8 == 0)
				|| ((P8 + P3 + P5) == 3 || (P2 + P7 + P5) == 3 || (P1 + P4 + P7) == 3 || (P1 + P3 + P6) == 3))
			return true;
		else
			return false;
	}

	// get the list of possible minutaes from the skeletized matrix
	public static List<Point> getMinutiaes(int[][] skelMatrix) {
		height = skelMatrix[0].length;
		width = skelMatrix.length;

		List<Point> minutaes = new ArrayList<Point>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i > 0 && j > 0 && i < width - 1 && j < height - 1 && skelMatrix[i][j] == 1) {
					int[][] cnmatrix = getCNMatrix(skelMatrix, i, j);
					int cn = CrossingNumber(cnmatrix);

					if (cn == 1 || (cn == 3 && isBifurcation(cnmatrix)))
						if (skelMatrix[i][j] == 1) {
							Point point = new Point(i, j);
							point.setValue(skelMatrix[i][j]);
							point.setType(cn);
							minutaes.add(point);
							// }
						}
				}
			}
		}
		return minutaes;
	}

	// centroid calculation
	private static Point centroidCalc(List<Point> listMin) {
		int n_points = listMin.size();
		int cord_x = 0;
		int cord_y = 0;
		for (int i = 0; i < n_points; i++) {
			cord_x += listMin.get(i).getX();
			cord_y += listMin.get(i).getY();
		}
		int centr_x = cord_x / n_points;
		int centr_y = cord_y / n_points;
		return new Point(centr_x, centr_y);
	}

	// get angle
	private double getAngle(Point p1, Point p2) {
		double angle = Math.toDegrees(Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()));
		if (angle < 0)
			angle += 360;
		return angle;

	}

	// comparition
	public static double rotarionAngle(List<Point> minSource, List<Point> minDest) {
		Point centroidS = centroidCalc(minSource);
		Point centroidD = centroidCalc(minDest);

		double angle_1 = Math.asin(centroidS.getX() / centroidD.getX());
		double angle_2 = Math.acos(centroidS.getY() / centroidD.getY());

		if (Math.abs(angle_1 - angle_2) < 1.0)
			return (angle_1 + angle_2) / 2;

		return -1;
	}

	public static Point[] maxPoints(List<Point> list) {
		double dist_max = -999999;
		Point max1 = null;
		Point max2 = null;
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size() - 1; j++) {
				double dist = list.get(i).euclideanDistance(list.get(j));
				if (dist > dist_max) {
					dist_max = dist;
					max1 = list.get(i);
					max2 = list.get(j);
				}
			}
		}
		Point[] res = new Point[2];
		res[0] = max1;
		res[1] = max2;
		return res;
	}

	public static double angle(Point x, Point y) {
		return Math.atan2(y.getY() - x.getY(), y.getX() - x.getX());
	}

	public static List<Point> removeFalseMinutae(int[][] skelMat, List<Point> fullMin) {
		double inter_D = 0;
		int rows_lenght = skelMat[0].length;
		double partial_sum = 0;
		for (int i = 0; i < skelMat.length; i++) {
			int sum_ones = 0;
			for (int j = 0; j < skelMat[0].length; j++) {
				if (skelMat[i][j] == 1)
					sum_ones++;
			}
			if (sum_ones != 0) {
				partial_sum += rows_lenght * 1.0 / sum_ones;
			}

		}
		inter_D = partial_sum / skelMat.length;

		List<Point> newMins = new ArrayList<Point>();
		int[] ind = new int[fullMin.size()];

		for (int i = 0; i < fullMin.size() - 1; i++) {
			if (ind[i] == 0) {
				Point p1 = fullMin.get(i);
				for (int j = i + 1; j < fullMin.size(); j++) {
					if (ind[j] == 0) {
						Point p2 = fullMin.get(j);
						double distance = p2.euclideanDistance(p1);
						if (distance < inter_D) {
							ind[i] = 1;
							ind[j] = 1;
						}
					}
				}
			}

		}

		for (int i = 0; i < ind.length; i++) {
			if (ind[i] == 0) {
				Point aux = fullMin.get(i);
				newMins.add(new Point(aux.getX(), aux.getY()));
			}
		}
		return newMins;
	}

	public static Point[] getNearestNeighbourType(Point x, List<Point> lista) {
		Point[] retur = new Point[k];
		double fjernest = Double.MIN_VALUE;
		int index = 0;

		for (int i = 0; i < lista.size(); i++) {
			double distance = x.euclideanDistance(lista.get(i));
			if (retur[retur.length - 1] == null) {
				int j = 0;
				while (j < retur.length) {
					if (retur[j] == null) {
						retur[j] = lista.get(i);
						break;
					}
					j++;
				}
				if (distance > fjernest) {
					index = j;
					fjernest = distance;
				}
			} else {
				if (distance < fjernest) {
					retur[index] = lista.get(i);
					double f = 0.0;
					int ind = 0;
					for (int j = 0; j < retur.length; j++) {
						double dt = retur[j].euclideanDistance(x);
						if (dt > f) {
							f = dt;
							ind = j;
						}
					}
					fjernest = f;
					index = ind;
				}
			}
		}
		return retur;
	}

	public static boolean compareEdges(double[] aS, double[] aT) {
		int cont_umb = 0;
		for (int i = 0; i < aS.length; i++) {
			if (aS[i] != 0) {
				double pesoS = aS[i];
				for (int j = 0; j < aT.length; j++) {
					if (aT[j] != 0) {
						double pesosT = aT[j];
						if (Math.abs(pesoS - pesosT) < 10) {
							cont_umb++;

						} else
							break;
					}
				}
			}
		}
		// System.out.println(cont_umb);
		boolean matchNeigh = (cont_umb / k) > 0.5;
		return matchNeigh;
	}

	public static double[][] matToGraph(List<Point> minutaes) {
		double[][] grafoS = new double[minutaes.size()][k];
		for (int i = 0; i < minutaes.size(); i++) {
			Point[] vecinos = Fingerprint.getNearestNeighbourType(minutaes.get(i), minutaes);
			for (int j = 1; j < vecinos.length; j++) {
				Point p = vecinos[j];
				// int index = tMinutaes.indexOf(p);
				grafoS[i][j - 1] = p.euclideanDistance(minutaes.get(i));
			}
		}
		return grafoS;
	}

	public static double comparition(double[][] grafoS, double[][] grafoT) {
		int match = 0;
		for (int i = 0; i < grafoS.length; i++) {
			double[] vecindadS = grafoS[i];
			int[] vec_aceptada = new int[grafoT.length];
			for (int j = 0; j < grafoT.length; j++) {
				if (vec_aceptada[j] == 0) {
					double[] vecindadT = grafoT[j];
					boolean matchNeigh = Fingerprint.compareEdges(vecindadS, vecindadT);
					if (matchNeigh) {
						match++;
						vec_aceptada[j] = 1;
						break;
					}
				}

			}
		}
		// System.out.println(match);
		// System.out.println(grafoT.length);
		// System.out.println(grafoS.length);
		double comparition = (Math.pow(match, 2) / (grafoT.length * grafoS.length));
		return comparition;
	}

	public static double[][] imageGraph(String filename1) {
		try {
			BufferedImage in = ImageIO.read(new File(filename1));
			in = Thumbnails.of(in).size(600, 600).asBufferedImage();
			BufferedImage bin = Binarization.binarize(in);
			int[][] mati = Binarization.imgToMat(bin);
			int[][] ske = Thining.doZhangSuenThinning(mati, false);
			List<Point> fMinutaes = Fingerprint.getMinutiaes(ske);
			List<Point> tMinutaes = Fingerprint.removeFalseMinutae2(ske, fMinutaes);
			double[][] grafoS = Fingerprint.matToGraph(tMinutaes);
			return grafoS;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double matchFingerprints(String filename1, String filename2) {
		try {
			BufferedImage in = ImageIO.read(new File(filename1));
			in = Thumbnails.of(in).size(600, 600).asBufferedImage();

			BufferedImage test = ImageIO.read(new File(filename2));
			test = Thumbnails.of(test).size(600, 600).asBufferedImage();

			BufferedImage bin = Binarization.binarize(in);
			BufferedImage binTest = Binarization.binarize(test);

			int[][] mati = Binarization.imgToMat(bin);
			int[][] matiTest = Binarization.imgToMat(binTest);

			int[][] ske = Thining.doZhangSuenThinning(mati, false);
			int[][] skeTest = Thining.doZhangSuenThinning(matiTest, false);

			List<Point> fMinutaes = Fingerprint.getMinutiaes(ske);
			List<Point> fMinutaesTest = Fingerprint.getMinutiaes(skeTest);

			List<Point> tMinutaes = Fingerprint.removeFalseMinutae2(ske, fMinutaes);
			List<Point> tMinutaesTest = Fingerprint.removeFalseMinutae2(skeTest, fMinutaesTest);
			// System.out.println(tMinutaes.size());

			// K -1 cercanas
			// Grafo S
			double[][] grafoS = Fingerprint.matToGraph(tMinutaes);
			// Grafo T
			double[][] grafoT = Fingerprint.matToGraph(tMinutaesTest);

			double comparition = Fingerprint.comparition(grafoS, grafoT);
			return comparition;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static List<Point> removeFalseMinutae2(int[][] ske, List<Point> minutaes) {
		int[][][] matM = new int[ske.length][ske[0].length][4];
		for (Point p : minutaes) {
			int x = p.getX();
			int y = p.getY();
			int t = p.getType();
			matM[x][y][t] = 1;
		}
		int tk = 1, T = 14;
		Point P1t = null, P2t = null, P1b = null, P2b = null;
		for (int x = T; x < ske.length - T; x++) {
			for (int y = T; y < ske[0].length - T; y++) {
				if (matM[x][y][1] == 1) {
					P1t = new Point(x, y);
					P2t = new Point(-1, -1);
					P1b = new Point(-1, -1);
					tk = 1;
					for (int i = -T / 2; i < T / 2; i++) {
						for (int j = -T / 2; j < T / 2; j++) {
							if (matM[i + x][j + y][1] == 1) {
								if ((i + x) != x || (j + y) != y && tk == 1) {
									P2t.setY(j + y);
									P2t.setX(i + x);
									tk = 0;
								}
							}
							if (matM[i + x][j + y][2] == 1) {
								P1b.setY(y + j);
								P1b.setX(i + x);
							}
						}
					}
					if (P1b.getX() != -1) {
						matM[P1t.getX()][P1t.getY()][1] = 0;
						matM[P1b.getX()][P1b.getY()][2] = 0;
					}
					if (P2t.getX() != -1) {
						matM[P1t.getX()][P1t.getY()][1] = 0;
						matM[P2t.getX()][P2t.getY()][1] = 0;
					}
				}
				if (matM[x][y][2] == 1) {
					P1b = new Point(x, y);
					P2b = new Point(-1, -1);
					for (int i = -T / 2; i < T / 2; i++) {
						for (int j = -T / 2; j < T / 2; j++) {
							if (matM[i + x][j + y][2] == 1) {
								if ((i + x) != x || (j + y) != y) {
									P2b.setY(j + y);
									P2b.setX(i + x);
								}
							}
						}
					}
					if (P2b.getX() != -1) {
						matM[P1b.getX()][P1b.getY()][2] = 0;
						if (P1b.euclideanDistance(P2b) > 2) {
							matM[P2b.getX()][P2b.getY()][2] = 0;
						}
					}
				}
			}
		}
		List<Point> nueva = new ArrayList<Point>();
		for (int i = 0; i < matM.length; i++) {
			for (int j = 0; j < matM[0].length; j++) {
				if (matM[i][j][1] != 0 || matM[i][j][2] != 0 || matM[i][j][3] != 0) {
					nueva.add(new Point(i, j));
				}
			}
		}
		return nueva;
	}
}
