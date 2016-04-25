package pe.pucp.edu.pe.siscomfi;

public class FVertex {
	int x;
	int y;
	double theta;
	int type;

	public FVertex(int x, int y, double theta, int type) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
