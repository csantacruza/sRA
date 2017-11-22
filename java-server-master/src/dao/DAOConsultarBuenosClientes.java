package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Usuario;

public class DAOConsultarBuenosClientes {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public  DAOConsultarBuenosClientes() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	public List<Usuario> consultarBuenosClientesPC() throws Exception{
		ArrayList<Usuario> resp = new ArrayList<Usuario>();

		String sql = "SELECT U.ID,U.NOMBRE,U.IDENTIFICACION,U.CORREO,U.ROL FROM PEDIDO P JOIN USUARIO U ON P.ID_USUARIO = U.ID";
		sql +="	WHERE NOMBRE_PRODUCTO IN (SELECT NOMBRE FROM PRODUCTO WHERE CATEGORIA  = 2)";
		sql +="	INTERSECT";
		sql +="	SELECT  U.ID,U.NOMBRE,U.IDENTIFICACION,U.CORREO,U.ROL FROM PEDIDO P JOIN USUARIO U ON P.ID_USUARIO = U.ID";
		sql +="	WHERE PRECIO >38000"; 
		sql +=" GROUP BY U.ID,U.NOMBRE,U.IDENTIFICACION,U.CORREO,U.ROL";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Integer idUsuario = rs.getInt("ID");
			String nombre = rs.getString("NOMBRE");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String correo = rs.getString("CORREO");
			Integer rol = rs.getInt("ROL");
			Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
			resp.add(nuevo);
		}
		if(resp.isEmpty() || resp == null)
			throw new Exception("No se han encontrado datos");
		else
			return resp;
	}
	public List<Usuario> consultarBuenosClientesNM() throws Exception{
		ArrayList<Usuario> resp = new ArrayList<Usuario>();

		String sql = "SELECT U.ID,U.NOMBRE,U.IDENTIFICACION,U.CORREO,U.ROL ";
		sql +="	FROM PEDIDO P JOIN USUARIO U ON P.ID_USUARIO = U.ID";
		sql +="	WHERE P.NOMBRE_PRODUCTO NOT IN (SELECT NOMBRE FROM MENU)";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Integer idUsuario = rs.getInt("ID");
			String nombre = rs.getString("NOMBRE");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String correo = rs.getString("CORREO");
			Integer rol = rs.getInt("ROL");
			Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
			resp.add(nuevo);

		}


		if(resp.isEmpty())
			throw new Exception("No se han encontrado datos");
		else
			return resp;
	}

}
