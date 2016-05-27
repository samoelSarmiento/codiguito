package pe.pucp.edu.pe.siscomfi.controller;

import pe.pucp.edu.pe.siscomfi.bm.BD.UsuarioDB;
import pe.pucp.edu.pe.siscomfi.model.Usuario;

public class UsuarioController {
	private static UsuarioDB usrDb = new UsuarioDB();
	
	public static void addUser(Usuario usr){
		usrDb.add(usr);
	}
	
	public static void updateUser(Usuario usr){
		usrDb.update(usr);
	}
}
