package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarConsumo {
	/**
	 * Usuario puede ser un usuario restaurante o un cliente de la rotonda
	 */
	@JsonProperty(value = "usuario")
	private Usuario usuario;
	/**
	 * Orden ascendente o descendente
	 */
	@JsonProperty(value= "ordenar")
	private String ordenar;
	/**
	 * Agrupar por clasificación(Documento)
	 */
	@JsonProperty(value = "agrupar")
	private String agrupar;

	@JsonProperty(value = "restaurante")
	private String restaurante;
	@JsonProperty(value = "fechaInicial")
	private Date fechaInicial;
	@JsonProperty(value = "fechaFinal")
	private Date fechaFinal;

	private List<Usuario> clientes;

	public ConsultarConsumo(@JsonProperty(value = "usuario")Usuario usuario,
			@JsonProperty(value = "restaurante")String restaurante,
			@JsonProperty(value = "fechaInicial") Date fechaInicial,
			@JsonProperty(value = "fechaFinal") Date fechaFinal,
			@JsonProperty(value= "ordenar") String ordenar,
			@JsonProperty(value = "agrupar")String agrupar,
			List<Usuario> clientes) {

		super();
		this.usuario = usuario;
		this.restaurante = restaurante;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.clientes = clientes;
		this.ordenar = ordenar;
		this.agrupar = agrupar;
	}



	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getOrdenar() {
		return ordenar;
	}

	public void setOrdenar(String ordenar) {
		this.ordenar = ordenar;
	}

	public String getAgrupar() {
		return agrupar;
	}

	public void setAgrupar(String agrupar) {
		this.agrupar = agrupar;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public List<Usuario> getClientes() {
		return clientes;
	}

	public void setClientes(List<Usuario> clientes) {
		this.clientes = clientes;
	}



}
