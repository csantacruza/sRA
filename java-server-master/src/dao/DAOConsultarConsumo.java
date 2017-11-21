package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	public String cambiarFechaSql (String fecha) {
		String fechaConFormato = "";
		String[] partes = fecha.split("/");
		fechaConFormato = partes[2] + "/" + partes[1] +"/" + partes[0].substring(2,4);
		return fechaConFormato;
	}


	public List<Usuario> consultarConsumoV1UsuarioRestaurante(Integer id, String restaurante, String fechaIni, String fechaFin,String ordenar, String agrupar) throws Exception {
		ArrayList<Usuario> resp = new ArrayList<>();

		String fechaInic = fechaIni.toString().replace("-", "/");
		String fechaInicial = cambiarFechaSql(fechaInic);
		String fechaFina = fechaFin.toString().replace("-", "/");
		String fechaFinal = cambiarFechaSql(fechaFina);

		if(agrupar.equals("Datos del cliente")) {
			String sql = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS";
			sql += 	" FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO"; 
			sql += 	" WHERE NOMBRE_RESTAURANTE = '" +  restaurante + "'"; 
			sql += 	" AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
			sql += 	" AND FECHA BETWEEN '" + fechaInicial + "' AND '"+ fechaFinal +"'"; 
			sql += 	" GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL";
			sql += 	" ORDER BY "+ ordenar +"";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				Integer idUsuario = rs.getInt("ID");
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
		}else if(agrupar.equals("Producto")) {
			String sql2 = "SELECT U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,COUNT(P.ID_USUARIO) AS PRODUCTOS_CONSUMIDOS,P.NOMBRE_PRODUCTO"; 

			sql2 +=" FROM USUARIO U JOIN PEDIDO P ON U.ID = P.ID_USUARIO ";
			sql2 +=" AND NOMBRE_RESTAURANTE = '"+ restaurante + "'" ;
			sql2 +=" AND NOMBRE_PRODUCTO = '"+ ordenar.replace("'", "") + "'" ;
			sql2 +=" AND SERVIDO = 'T' AND ESTADO = 'normal'"; 
			sql2 +=" AND FECHA BETWEEN '" +fechaInicial + "' AND '" + fechaFinal +"'";
			sql2 +=" GROUP BY U.ID,NOMBRE,IDENTIFICACION,CORREO,ROL,P.NOMBRE_PRODUCTO";
			sql2 +=" ORDER BY P.NOMBRE_PRODUCTO";

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			while (rs2.next()) {

				Integer idUsuario = rs2.getInt("ID");
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
		}else {
			throw new Exception("AgrupaciÛn no disponible");
		}

		if(resp.isEmpty())
			throw new Exception("No se han encontrado datos");
		else
			return resp;
	}

	public List<Usuario> consultarConsumoV2UsuarioRestaurante(Integer id, String restaurante, String fechaIni, String fechaFin,String ordenar, String agrupar) throws Exception {
		ArrayList<Usuario> resp = new ArrayList<>();

		String fechaInic = fechaIni.toString().replace("-", "/");
		String fechaInicial = cambiarFechaSql(fechaInic);
		String fechaFina = fechaFin.toString().replace("-", "/");
		String fechaFinal = cambiarFechaSql(fechaFina);

		if(agrupar.equals("Datos del cliente")) {
			String sql = "SELECT DISTINCT ID,NOMBRE,IDENTIFICACION,CORREO,ROL ";
			sql +=" FROM USUARIO"; 
			sql +=" WHERE ID NOT IN (SELECT ID_USUARIO FROM PEDIDO WHERE NOMBRE_RESTAURANTE = '"+restaurante +"'";
			sql +=" AND SERVIDO = 'T'";
			sql +=" AND ESTADO = 'normal'"; 
			sql +=" AND FECHA BETWEEN '"+ fechaInicial +"' AND '"+ fechaFinal +"')";
			sql +=" AND ROL = 1"; 
			sql +=" ORDER BY "+ ordenar;

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			Integer a = 0;

			while (rs.next()) {
				System.out.println(a++);
				Integer idUsuario = rs.getInt("ID");
				String nombre = rs.getString("NOMBRE");
				Integer identificacion = rs.getInt("IDENTIFICACION");
				String correo = rs.getString("CORREO");
				Integer rol = rs.getInt("ROL");
				Usuario nuevo = new Usuario(idUsuario, nombre,identificacion,correo,rol,"");
				resp.add(nuevo);
				
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
