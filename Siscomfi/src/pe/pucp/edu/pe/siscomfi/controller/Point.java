package pe.pucp.edu.pe.siscomfi.controller;

import java.util.Comparator;

public class Point implements Comparable<Point>{
	private int x;
	private int y;
	private int value;
	private int type;
	private int angle;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	public int compareTo (Point other) {
	    if (this.x == other.x) {
	        return (this.y < other.y) ? -1 : ((this.y == other.y) ? 0 : 1);
	    } else {
	        return (this.x < other.x) ? -1 : 1;
	    }
	}
	
	public double euclideanDistance(Point px){
		double x_2 = Math.pow(this.x - px.getX(), 2);
		double y_2 = Math.pow(this.y - px.getY(), 2);
		return Math.sqrt(x_2 + y_2);
		
	}
}
