package pe.pucp.edu.pe.siscomfi;

public class MinutaeTuple {
	private double ratio;
	private double angle;
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public MinutaeTuple(double ratio, double angle) {
		this.ratio = ratio;
		this.angle = angle;
	}
	
}
