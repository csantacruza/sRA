package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.ConsultarFuncionamientoRespuesta;
import vos.Producto;
import vos.Restaurante;

public class DAOConsultarFuncionamiento {

	/**
	 * Arraylits de recursos que se usan para la ejecuci√≥n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi√≥n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public  DAOConsultarFuncionamiento() {
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

	public List<ConsultarFuncionamientoRespuesta> consultarFuncionamiento() throws Exception {

		ArrayList<ConsultarFuncionamientoRespuesta> respuesta = new ArrayList<ConsultarFuncionamientoRespuesta>();

		for(int i = 1; i<8; i++ ) {

			ConsultarFuncionamientoRespuesta resp = null;
			Producto masConsumido = null;
			Producto menosConsumido = null;
			Restaurante masFrecuentado = null;
			Restaurante menosFrecuentado = null;

			//Consulto el producto m·s consumido
			String sql = "SELECT  PR.NOMBRE,PR.DESCRIPCION,PR.TIEMPOP, PR.CATEGORIA ,COUNT(P.NOMBRE_PRODUCTO)AS C FROM PEDIDO P JOIN PRODUCTO PR ON P.NOMBRE_PRODUCTO = PR.NOMBRE ";
			sql += " WHERE TO_CHAR(FECHA,'D')=" + i +"AND ESTADO ='normal' GROUP BY PR.NOMBRE,PR.DESCRIPCION,PR.TIEMPOP, PR.CATEGORIA  ORDER BY C DESC";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String descripcion = rs.getString("DESCRIPCION");
				Integer tiempo = rs.getInt("TIEMPOP");
				Integer categoria = rs.getInt("CATEGORIA");
				masConsumido = new Producto(nombre, descripcion, tiempo, categoria);
			}
			//Consulto el producto menos consumido
			String sql2 = "SELECT  PR.NOMBRE,PR.DESCRIPCION,PR.TIEMPOP, PR.CATEGORIA ,COUNT(P.NOMBRE_PRODUCTO)AS C FROM PEDIDO P JOIN PRODUCTO PR ON P.NOMBRE_PRODUCTO = PR.NOMBRE ";
			sql2 += " WHERE TO_CHAR(FECHA,'D')="+ i + " AND ESTADO ='normal' GROUP BY PR.NOMBRE,PR.DESCRIPCION,PR.TIEMPOP, PR.CATEGORIA  ORDER BY C ASC";

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			if(rs2.next()) {
				String nombre = rs2.getString("NOMBRE");
				String descripcion = rs2.getString("DESCRIPCION");
				Integer tiempo = rs2.getInt("TIEMPOP");
				Integer categoria = rs2.getInt("CATEGORIA");
				menosConsumido = new Producto(nombre, descripcion, tiempo, categoria);
			}
			//Consulto el restaurante m·s frecuentado
			String sql3 = "SELECT  R.NOMBRE,R.ID_ADMINISTRADOR,R.TIPO,R.ID_ZONA ,COUNT(P.NOMBRE_RESTAURANTE)AS C FROM PEDIDO P JOIN RESTAURANTE R ON P.NOMBRE_RESTAURANTE = R.NOMBRE"; 
					sql3 += " WHERE TO_CHAR(FECHA,'D')=" + i +"AND ESTADO ='normal' GROUP BY R.NOMBRE,R.ID_ADMINISTRADOR,R.TIPO,R.ID_ZONA ORDER BY C DESC";


			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();

			if(rs3.next()) {
				String nombre = rs3.getString("NOMBRE");
				Integer id_admin = rs3.getInt("ID_ADMINISTRADOR");
				String tipo = rs3.getString("TIPO");
				Integer id_zona = rs3.getInt("ID_ZONA");
				masFrecuentado = new Restaurante(nombre, tipo, id_admin, id_zona);
			}
			//Consulto el restaurante menos frecuentado
			String sql4 = "SELECT  R.NOMBRE,R.ID_ADMINISTRADOR,R.TIPO,R.ID_ZONA ,COUNT(P.NOMBRE_RESTAURANTE)AS C FROM PEDIDO P JOIN RESTAURANTE R ON P.NOMBRE_RESTAURANTE = R.NOMBRE"; 
				sql4 += " WHERE TO_CHAR(FECHA,'D')="+ i +" AND ESTADO ='normal' GROUP BY R.NOMBRE,R.ID_ADMINISTRADOR,R.TIPO,R.ID_ZONA ORDER BY C ASC";

			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			ResultSet rs4 = prepStmt4.executeQuery();

			if(rs4.next()) {
				String nombre = rs4.getString("NOMBRE");
				Integer id_admin = rs4.getInt("ID_ADMINISTRADOR");
				String tipo = rs4.getString("TIPO");
				Integer id_zona = rs4.getInt("ID_ZONA");
				menosFrecuentado = new Restaurante(nombre, tipo, id_admin, id_zona);
			}

			if(i == 1) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Lunes");
				respuesta.add(resp);
			}else if(i == 2) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Martes");
				respuesta.add(resp);
			}else if(i == 3) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Miercoles");
				respuesta.add(resp);
			}else if(i == 4) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Jueves");
				respuesta.add(resp);
			}else if(i == 5) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Viernes");
				respuesta.add(resp);
			}else if(i == 6) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Sabado");
				respuesta.add(resp);
			}else if(i == 7) {
				resp = new ConsultarFuncionamientoRespuesta(masConsumido, menosConsumido, masFrecuentado, menosFrecuentado,"Domingo");
				respuesta.add(resp);
			}

		}
		if(respuesta.size() == 0) {
			throw new Exception("No hay datos disponibles");
		}else
			return respuesta;
	}
}
