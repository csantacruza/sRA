package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.Usuario;

public class DAOConsultarConsumo {
	/**
	 * Arraylits de recursos que se usan para la ejecuci√≥n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi√≥n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurantes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOConsultarConsumo() {
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexi√≥n que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	public List<Usuario> consultarConsumoV1Cliente(Integer id,String restaurante,Date fechaIni, Date fechaFin,String ordenar,String agrupar) throws Exception {
		
		ArrayList<Usuario> resp = new ArrayList<>();
		String[] parte = agrupar.split(",");

		if(parte[0].equals("Datos del cliente")) {
			String sql = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS";
			sql += 	"FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO"; 
			sql += 	"WHERE ID_USUARIO = " + id; 
			sql += 	"AND NOMBRE_RESTAURANTE = '" +  restaurante + "'"; 
			sql += 	"AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
			sql += 	"AND FECHA BETWEEN '" + fechaIni+ "' AND '"+ fechaFin +"'"; 
			sql += 	"GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
			sql += 	"ORDER BY "+ ordenar +"";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				Integer idUsuario = rs.getInt("ID_USUARIO");
				String nombre = rs.getString("NOMBRE");
				Integer identificacion = rs.getInt("IDENTIFICACION");
				String correo = rs.getString("CORREO");
				Integer rol = rs.getInt("ROL");
				Integer prodConsumidos = rs.getInt("PRODUCTOS_CONSUMIDOS");
				if(prodConsumidos > 0) {
					Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
					resp.add(nuevo);
				}
			}
		}else if(parte[0].equals("Producto")) {
			String sql2 = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS"; 
			sql2 += "FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO";
			sql2 +="WHERE ID_USUARIO ="+ id;
			sql2 +="AND NOMBRE_RESTAURANTE = '"+ restaurante + "'" ;
			sql2 +="AND NOMBRE_PRODUCTO = '"+ parte[1]+ "'" ;
			sql2 +="AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
			sql2 +="AND FECHA BETWEEN '" +fechaIni + "' AND '" +fechaFin +"'";
			sql2 +="GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
			sql2 +="ORDER BY "+ ordenar;

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			while (rs2.next()) {

				Integer idUsuario = rs2.getInt("ID_USUARIO");
				String nombre = rs2.getString("NOMBRE");
				Integer identificacion = rs2.getInt("IDENTIFICACION");
				String correo = rs2.getString("CORREO");
				Integer rol = rs2.getInt("ROL");
				Integer prodConsumidos = rs2.getInt("PRODUCTOS_CONSUMIDOS");
				if(prodConsumidos > 0) {
					Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
					resp.add(nuevo);
				}
			}
		}else if(parte[0].equals("Tipo de producto")) {
			
			ArrayList<String> productos = new ArrayList<String>();
			
			String sql3 = "SELECT P.NOMBRE FROM PRODUCTO P JOIN CATEGORIAS C ON P.CATEGORIA = C.ID WHERE C.NOMBRE = '"+ parte[1] +"'";
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();

			while (rs3.next()) {
				productos.add(rs3.getString("NOMBRE"));
			}

			for(int i = 0; i< productos.size(); i++) {
				
				String sql2 = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS"; 
				sql2 += "FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO";
				sql2 +="WHERE ID_USUARIO ="+ id;
				sql2 +="AND NOMBRE_RESTAURANTE = '"+ restaurante + "'" ;
				sql2 +="AND NOMBRE_PRODUCTO = '"+ productos.get(i) + "'" ;
				sql2 +="AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
				sql2 +="AND FECHA BETWEEN '" +fechaIni + "' AND '" +fechaFin +"'";
				sql2 +="GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
				sql2 +="ORDER BY "+ ordenar;

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();

				while (rs2.next()) {

					Integer idUsuario = rs2.getInt("ID_USUARIO");
					String nombre = rs2.getString("NOMBRE");
					Integer identificacion = rs2.getInt("IDENTIFICACION");
					String correo = rs2.getString("CORREO");
					Integer rol = rs2.getInt("ROL");
					Integer prodConsumidos = rs2.getInt("PRODUCTOS_CONSUMIDOS");
					if(prodConsumidos > 0) {
						Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
						resp.add(nuevo);
					}
				}
			}
		}else {
			throw new Exception("AgrupaciÛn no disponible");
		}

		if(resp.isEmpty())
			throw new Exception("No se han encontrado datos");
		else
			return resp;
	}

	public List<Usuario> consultarConsumoV1UsuarioRestaurante(Integer id, String restaurante, Date fechaIni, Date fechaFin,String ordenar, String agrupar) throws Exception {
		ArrayList<Usuario> resp = new ArrayList<>();
		String[] parte = agrupar.split(",");

		if(parte[0].equals("Datos del cliente")) {
			String sql = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS";
			sql += 	"FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO"; 
			sql += 	"WHERE NOMBRE_RESTAURANTE = '" +  restaurante + "'"; 
			sql += 	"AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
			sql += 	"AND FECHA BETWEEN '" + fechaIni+ "' AND '"+ fechaFin +"'"; 
			sql += 	"GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
			sql += 	"ORDER BY "+ ordenar +"";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				Integer idUsuario = rs.getInt("ID_USUARIO");
				String nombre = rs.getString("NOMBRE");
				Integer identificacion = rs.getInt("IDENTIFICACION");
				String correo = rs.getString("CORREO");
				Integer rol = rs.getInt("ROL");
				Integer prodConsumidos = rs.getInt("PRODUCTOS_CONSUMIDOS");
				if(prodConsumidos > 0) {
					Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
					resp.add(nuevo);
				}
			}
		}else if(parte[0].equals("Producto")) {
			String sql2 = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS"; 
			sql2 += "FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO";
			sql2 +="WHERE NOMBRE_RESTAURANTE = '"+ restaurante + "'" ;
			sql2 +="AND NOMBRE_PRODUCTO = '"+ parte[1]+ "'" ;
			sql2 +="AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
			sql2 +="AND FECHA BETWEEN '" +fechaIni + "' AND '" +fechaFin +"'";
			sql2 +="GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
			sql2 +="ORDER BY "+ ordenar;

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			while (rs2.next()) {

				Integer idUsuario = rs2.getInt("ID_USUARIO");
				String nombre = rs2.getString("NOMBRE");
				Integer identificacion = rs2.getInt("IDENTIFICACION");
				String correo = rs2.getString("CORREO");
				Integer rol = rs2.getInt("ROL");
				Integer prodConsumidos = rs2.getInt("PRODUCTOS_CONSUMIDOS");
				if(prodConsumidos > 0) {
					Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
					resp.add(nuevo);
				}
			}
		}else if(parte[0].equals("Tipo de producto")) {
			
			ArrayList<String> productos = new ArrayList<String>();
			
			String sql3 = "SELECT P.NOMBRE FROM PRODUCTO P JOIN CATEGORIAS C ON P.CATEGORIA = C.ID WHERE C.NOMBRE = '"+ parte[1] +"'";
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();

			while (rs3.next()) {
				productos.add(rs3.getString("NOMBRE"));
			}

			for(int i = 0; i< productos.size(); i++) {
				
				String sql2 = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS"; 
				sql2 += "FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO";
				sql2 +="WHERE NOMBRE_RESTAURANTE = '"+ restaurante + "'" ;
				sql2 +="AND NOMBRE_PRODUCTO = '"+ productos.get(i) + "'" ;
				sql2 +="AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
				sql2 +="AND FECHA BETWEEN '" +fechaIni + "' AND '" +fechaFin +"'";
				sql2 +="GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
				sql2 +="ORDER BY "+ ordenar;

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();

				while (rs2.next()) {

					Integer idUsuario = rs2.getInt("ID_USUARIO");
					String nombre = rs2.getString("NOMBRE");
					Integer identificacion = rs2.getInt("IDENTIFICACION");
					String correo = rs2.getString("CORREO");
					Integer rol = rs2.getInt("ROL");
					Integer prodConsumidos = rs2.getInt("PRODUCTOS_CONSUMIDOS");
					if(prodConsumidos > 0) {
						Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
						resp.add(nuevo);
					}
				}
			}
		}else {
			throw new Exception("AgrupaciÛn no disponible");
		}

		if(resp.isEmpty())
			throw new Exception("No se han encontrado datos");
		else
			return resp;
	}

}
