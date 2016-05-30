package pe.pucp.edu.pe.siscomfi;

import java.util.List;

public class CcpMinutaes {
	private List<MinutaePoint> ccpBase;
	private List<MinutaePoint> ccpInput;

	public CcpMinutaes(List<MinutaePoint> ccpBase, List<MinutaePoint> ccpInput) {
		this.ccpBase = ccpBase;
		this.ccpInput = ccpInput;
	}

	public List<MinutaePoint> getCcpBase() {
		return ccpBase;
	}

	public void setCcpBase(List<MinutaePoint> ccpBase) {
		this.ccpBase = ccpBase;
	}

	public List<MinutaePoint> getCcpInput() {
		return ccpInput;
	}

	public void setCcpInput(List<MinutaePoint> ccpInput) {
		this.ccpInput = ccpInput;
	}

}
