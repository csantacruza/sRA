package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido{


	@JsonProperty(value = "numero_pedido")
	private Integer numero_pedido;
	private Integer precio;
	private Date fecha;
	@JsonProperty(value = "nombreProducto")
	private String nombreProducto;
	@JsonProperty(value = "nombreRestaurante")
	private String nombreRestaurante;
	@JsonProperty(value = "componentes")
	private List<String> componentes;
	boolean servido;
	String estado;

	public Pedido( @JsonProperty(value = "numero_pedido")Integer numero_pedido,
			@JsonProperty(value = "nombreProducto")String nombreProducto,
			@JsonProperty(value = "nombreRestaurante")String nombreRestaurante,
			@JsonProperty(value = "componentes")List<String> componentes)
	{
		this.numero_pedido = numero_pedido;
		this.precio = 0;
		this.fecha = new Date();
		this.nombreRestaurante=nombreRestaurante;
		this.nombreProducto=nombreProducto;
		this.servido=false;
		this.componentes =componentes;
		this.estado = "normal";
	}

	public Pedido( Integer numero_pedido,
			Usuario usuario,
			Integer precio,
			Date fecha,
			String nombreProducto,
			String nombreRestaurante,
			Boolean servido,
			List<String> componentes){



		this.numero_pedido = numero_pedido;
		this.precio = null;
		this.fecha = null;
		this.nombreRestaurante=nombreRestaurante;
		this.nombreProducto=nombreProducto;
		this.servido=servido;
		this.componentes = componentes;
		this.estado = "normal";
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<String> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<String> componentes) {
		this.componentes = componentes;
	}

	public boolean isServido() {
		return servido;
	}


	public void setServido(boolean servido) {
		this.servido = servido;
	}


	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public Integer getNumero_pedido() {
		return numero_pedido;
	}

	public void setNumero_pedido(Integer numero_pedido) {
		this.numero_pedido = numero_pedido;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}