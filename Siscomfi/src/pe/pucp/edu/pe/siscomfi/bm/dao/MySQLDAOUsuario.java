package pe.pucp.edu.pe.siscomfi.bm.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.jdbc.Driver;

import pe.pucp.edu.pe.siscomfi.model.Usuario;

public class MySQLDAOUsuario implements DAOUsuario {

	@Override
	public void add(Usuario u) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user, DBConnection.password);

			String sql = "INSERT INTO Usuario "
					+ "(nombre, apellidoPaterno, apellidoMaterno,   correoElectronico, contrasenia, fechaRegistro,   dni, idRol)"
					+ "VALUES (?,?,?,?,'1234',Now(),?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getNombre());
			pstmt.setString(2, u.getApellidoPaterno());
			pstmt.setString(3, u.getApellidoPaterno());
			pstmt.setString(4, u.getCorreoElectronico());
			pstmt.setString(5, u.getDni());
			pstmt.setInt(6, u.getIdRol());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Usuario u) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user, DBConnection.password);

			String sql = "UPDATE Usuario "
					+ "SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?,   correoElectronico = ?, dni = ?, idRol = ?"
					+ "WHERE id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getNombre());
			pstmt.setString(2, u.getApellidoPaterno());
			pstmt.setString(3, u.getApellidoPaterno());
			pstmt.setString(4, u.getCorreoElectronico());
			pstmt.setString(5, u.getDni());
			pstmt.setInt(6, u.getIdRol());
			pstmt.setInt(7, u.getIdUsuario());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(int idUsuario) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user, DBConnection.password);
			String sql = "DELETE FROM Usuario WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(7, idUsuario);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Usuario queryById(int idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

}
