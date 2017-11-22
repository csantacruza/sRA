package vos;

public class ConsultarFuncionamientoRespuesta {
	private String dia;
	private Producto masConsumido;
	private Producto menosConsumido;
	private Restaurante masFrecuentado;
	private Restaurante menosFrecuentado;
	

	public ConsultarFuncionamientoRespuesta(Producto masConsumido, Producto menosConsumido, Restaurante masFrecuentado,
			Restaurante menosFrecuentado,String dia) {
		super();
		this.masConsumido = masConsumido;
		this.menosConsumido = menosConsumido;
		this.masFrecuentado = masFrecuentado;
		this.menosFrecuentado = menosFrecuentado;
		this.dia = dia;
	}
	
	
	public String getDia() {
		return dia;
	}


	public void setDia(String dia) {
		this.dia = dia;
	}


	public Producto getMasConsumido() {
		return masConsumido;
	}
	public void setMasConsumido(Producto masConsumido) {
		this.masConsumido = masConsumido;
	}
	public Producto getMenosConsumido() {
		return menosConsumido;
	}
	public void setMenosConsumido(Producto menosConsumido) {
		this.menosConsumido = menosConsumido;
	}
	public Restaurante getMasFrecuentado() {
		return masFrecuentado;
	}
	public void setMasFrecuentado(Restaurante masFrecuentado) {
		this.masFrecuentado = masFrecuentado;
	}
	public Restaurante getMenosFrecuentado() {
		return menosFrecuentado;
	}
	public void setMenosFrecuentado(Restaurante menosFrecuentado) {
		this.menosFrecuentado = menosFrecuentado;
	}
	
}
