package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarConsumo {
	/**
	 * Usuario puede ser un cliente o un usuario restaurante o un administrador(gerente) de la rotonda.
	 */
	@JsonProperty(value = "usuario")
	private Usuario usuario;
	/**
	 * Restaurante del cual se quiere buscar
	 */
	@JsonProperty(value = "restaurante")
	private String restaurante;
	/**
	 * Rango de fecha - fecha inicial
	 */
	@JsonProperty(value = "fechaInicial")
	private String fechaInicial;
	/**
	 * Rango de fecha - fecha final
	 */
	@JsonProperty(value = "fechaFinal")
	private String fechaFinal;
	/**
	 * Ordenar atributo ascendente o descendente
	 * EJ:	(Nombre DESC)
	 * 		(Nombre ASC)
	 */
	@JsonProperty(value= "ordenar")
	private String ordenar;
	/**
	 * Agrupar por clasificación(Documento),elemento a grupar 
	 * EJ: (Producto,tamal )
	 */
	@JsonProperty(value = "agrupar")
	private String agrupar;
	
	/**
	 * Respuesta a la busqueda.
	 */
	private List<Usuario> clientes;

	public ConsultarConsumo(@JsonProperty(value = "usuario")Usuario usuario,
			@JsonProperty(value = "restaurante")String restaurante,
			@JsonProperty(value = "fechaInicial") String fechaInicial,
			@JsonProperty(value = "fechaFinal") String fechaFinal,
			@JsonProperty(value= "ordenar") String ordenar,
			@JsonProperty(value = "agrupar")String agrupar) {

		super();
		this.usuario = usuario;
		this.restaurante = restaurante;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.clientes = new ArrayList<Usuario>();
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

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public List<Usuario> getClientes() {
		return clientes;
	}

	public void setClientes(List<Usuario> clientes) {
		this.clientes = clientes;
	}



}
