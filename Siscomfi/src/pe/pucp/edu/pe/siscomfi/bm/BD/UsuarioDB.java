package pe.pucp.edu.pe.siscomfi.bm.BD;


import pe.pucp.edu.pe.siscomfi.bm.dao.DAOFactory;
import pe.pucp.edu.pe.siscomfi.bm.dao.DAOUsuario;
import pe.pucp.edu.pe.siscomfi.bm.dao.DBConnection;
import pe.pucp.edu.pe.siscomfi.model.Usuario;


public class UsuarioDB {
	
    DAOFactory daoFactory = DAOFactory.getDAOFactory(DBConnection.dbType);
    DAOUsuario daoUsuario = daoFactory.getDAOUsuario(); // POLIMORFISMO
    
    public UsuarioDB (){
    	//queryAll();
    }
    
    public void add(Usuario p) {
    	daoUsuario.add(p);
    	
    }
    public void update(Usuario p) {
    	daoUsuario.update(p);
    	
    }
    public void delete(int idCompany) {
    	daoUsuario.delete(idCompany);
    	
    }
    
    public Usuario queryById(int usuarioId) {
    	return daoUsuario.queryById(usuarioId);
    }
}
