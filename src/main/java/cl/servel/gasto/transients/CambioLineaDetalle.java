package cl.servel.gasto.transients;

import java.io.Serializable;
import java.util.Date;

public class CambioLineaDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String formulario;
	private Integer hoja;
	private Integer linea;
	private String concepto;
	private String detalleInicial;
	private String detalleFinal;
	private Date fecha;

	public String getFormulario() {
		return formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	public Integer getHoja() {
		return hoja;
	}

	public void setHoja(Integer hoja) {
		this.hoja = hoja;
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getDetalleInicial() {
		return detalleInicial;
	}

	public void setDetalleInicial(String detalleInicial) {
		this.detalleInicial = detalleInicial;
	}

	public String getDetalleFinal() {
		return detalleFinal;
	}

	public void setDetalleFinal(String detalleFinal) {
		this.detalleFinal = detalleFinal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
