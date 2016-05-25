package pe.pucp.edu.pe.siscomfi.bm.dao;

public abstract class DAOFactory {
	public static DAOFactory getDAOFactory(int dbType){
		switch (dbType) {
			case DBConnection.MYSQL:
				return new MySQLDAOFactory();
		}
		return null;
	}
	public abstract DAOUsuario getDAOUsuario();
	public abstract DAORol getDAORol();
	
}
