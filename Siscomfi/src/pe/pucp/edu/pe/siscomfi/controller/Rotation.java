package pe.pucp.edu.pe.siscomfi.controller;

public class Rotation {
	public Point traslation;
	public double rotation;
	public Point getTraslation() {
		return traslation;
	}
	public void setTraslation(Point traslation) {
		this.traslation = traslation;
	}
	public double getRotation() {
		return rotation;
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public Rotation (Point t , double rot){
		this.traslation = t;
		this.rotation = rot;
	}
}
