package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarBuenosClientes {
	@JsonProperty(value = "administrador")
	private Usuario administrador;
	/**
	 * Respuesta a la consulta
	 */
	private List<Usuario> clientes;
	
	public ConsultarBuenosClientes(@JsonProperty(value = "administrador")Usuario administrador, List<Usuario> clientes) {
		super();
		this.administrador = administrador;
		this.clientes = clientes;
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
	
	
}
