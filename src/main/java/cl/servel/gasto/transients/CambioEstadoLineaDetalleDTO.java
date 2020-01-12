package cl.servel.gasto.transients;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CambioEstadoLineaDetalleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8039988794977810551L;
	private Integer numeroPagina;
	private Integer numeroLinea;
	private Boolean habilitado;
	private String  codigoFormulario;
	public Integer getNumeroPagina() {
		return numeroPagina;
	}
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	public Integer getNumeroLinea() {
		return numeroLinea;
	}
	public void setNumeroLinea(Integer numeroLinea) {
		this.numeroLinea = numeroLinea;
	}
	public Boolean getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	public String getCodigoFormulario() {
		return codigoFormulario;
	}
	public void setCodigoFormulario(String codigoFormulario) {
		this.codigoFormulario = codigoFormulario;
	};
	
}
