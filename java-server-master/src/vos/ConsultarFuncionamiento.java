package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarFuncionamiento {
	@JsonProperty(value ="administrador")
	private Usuario administrador;
	/**
	 * Datos que se veran en la respuesta(response)
	 */
	private List<ConsultarFuncionamientoRespuesta> respuesta;
	
	
	public ConsultarFuncionamiento(@JsonProperty(value ="administrador")Usuario administrador) {
		super();
		this.administrador = administrador;
		this.respuesta = new ArrayList<ConsultarFuncionamientoRespuesta>();
	}
	
	
	public Usuario getAdministrador() {
		return administrador;
	}
	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	public List<ConsultarFuncionamientoRespuesta> getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(List<ConsultarFuncionamientoRespuesta> respuesta) {
		this.respuesta = respuesta;
	}
	
	

}
