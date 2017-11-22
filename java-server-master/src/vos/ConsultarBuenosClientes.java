package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarBuenosClientes {
	@JsonProperty(value = "administrador")
	private Usuario administrador;
	/**
	 * El tipo puede ser una vez a la semana o producto costos o nunca consumen menus.
	 */
	@JsonProperty(value = "tipo")
	private String tipo;
	/**
	 * Respuesta a la consulta
	 */
	private List<Usuario> clientes;
	
	public ConsultarBuenosClientes(@JsonProperty(value = "administrador")Usuario administrador,@JsonProperty(value = "tipo")String tipo) {
		super();
		this.administrador = administrador;
		this.tipo = tipo;
		this.clientes = new ArrayList<Usuario>();
	}
	public Usuario getAdministrador() {
		return administrador;
	}
	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	public List<Usuario> getClientes() {
		return clientes;
	}
	public void setClientes(List<Usuario> clientes) {
		this.clientes = clientes;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
