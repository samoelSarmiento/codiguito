package pe.pucp.edu.pe.siscomfi;

public class FEdgeC {
	double r;
	double phi;
	double theta;

	public FEdgeC(double r, double phi, double theta) {
		this.r = r;
		this.phi = phi;
		this.theta = theta;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getPhi() {
		return phi;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

}
