package pe.pucp.edu.pe.siscomfi;


import java.util.List;

import pe.pucp.edu.pe.siscomfi.controller.Point;

public class MinutaePoint {
	private Point cord;
	private List<MinutaeTuple> tuples;
	
	
	public MinutaePoint(Point cord, List<MinutaeTuple> tuples) {
		this.cord = cord;
		this.tuples = tuples;
	}
	public Point getCord() {
		return cord;
	}
	public void setCord(Point cord) {
		this.cord = cord;
	}
	public List<MinutaeTuple> getTuples() {
		return tuples;
	}
	public void setTuples(List<MinutaeTuple> tuples) {
		this.tuples = tuples;
	}
	
}
