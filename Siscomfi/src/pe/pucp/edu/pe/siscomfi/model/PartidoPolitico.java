package pe.pucp.edu.pe.siscomfi.model;

public class PartidoPolitico {
	private int idPartidoPolitco;
	private String nombrePartido;
	private String representante;
	private String correo;
	private String direccion;
	private String telefono;
	
	private String Provincia;
	private String Departamento;
	
	
	public int getIdPartidoPolitco() {
		return idPartidoPolitco;
	}
	public void setIdPartidoPolitco(int idPartidoPolitco) {
		this.idPartidoPolitco = idPartidoPolitco;
	}
	public String getNombrePartido() {
		return nombrePartido;
	}
	public void setNombrePartido(String nombrePartido) {
		this.nombrePartido = nombrePartido;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getProvincia() {
		return Provincia;
	}
	public void setProvincia(String provincia) {
		Provincia = provincia;
	}
	public String getDepartamento() {
		return Departamento;
	}
	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}
	

}
