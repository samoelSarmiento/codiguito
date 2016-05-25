package pe.pucp.edu.pe.siscomfi.bm.BD;

import java.util.ArrayList;

import pe.pucp.edu.pe.siscomfi.model.Rol;
import pe.pucp.edu.pe.siscomfi.model.Usuario;

public class siscomfiManager {
	private static UsuarioDB usuarioDB = new UsuarioDB();
	private static RolDB rolDB = new RolDB();
	
	
	public static void addUsuario(Usuario u)
    {
		usuarioDB.add(u);
    }
	
	public static ArrayList<Rol> queryAllRoles()
    {
		return rolDB.queryAll();
    }
}
