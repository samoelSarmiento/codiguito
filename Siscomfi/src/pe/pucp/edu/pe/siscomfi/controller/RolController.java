package pe.pucp.edu.pe.siscomfi.controller;

import java.util.ArrayList;

import pe.pucp.edu.pe.siscomfi.bm.BD.RolDB;
import pe.pucp.edu.pe.siscomfi.model.Rol;

public class RolController {
	private static RolDB rolDb = new RolDB();
	
	public static ArrayList<Rol> queryAllRoles(){
		return rolDb.queryAll();
	}
}
