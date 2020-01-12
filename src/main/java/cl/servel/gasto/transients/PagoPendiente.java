package cl.servel.gasto.transients;

import java.util.Date;
import java.util.List;

public class PagoPendiente {

	private Integer renId;
	private Integer canId;
	private Integer parId;
	private Double montoReembolso;
	private Double sumaPas;
	private Double sumaNomina;
	private Double totalPagar;
	private Date fecha;
	private String rutBanco;
	private List<Integer> oficios;
	private Integer partidoSeleccionado;
	private Sujeto sujeto;
	private Long numeroCuenta;
	private Long idMandato;
	private Integer ebaId;
	private boolean poseeCuenta;

	public Integer getRenId() {
		return renId;
	}

	public void setRenId(Integer renId) {
		this.renId = renId;
	}

	public Integer getCanId() {
		return canId;
	}

	public void setCanId(Integer canId) {
		this.canId = canId;
	}

	public Integer getParId() {
		return parId;
	}

	public void setParId(Integer parId) {
		this.parId = parId;
	}

	public Double getMontoReembolso() {
		return montoReembolso;
	}

	public void setMontoReembolso(Double montoReembolso) {
		this.montoReembolso = montoReembolso;
	}

	public Double getSumaPas() {
		return sumaPas;
	}

	public void setSumaPas(Double sumaPas) {
		this.sumaPas = sumaPas;
	}

	public Double getSumaNomina() {
		return sumaNomina;
	}

	public void setSumaNomina(Double sumaNomina) {
		this.sumaNomina = sumaNomina;
	}

	public Double getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(Double totalPagar) {
		this.totalPagar = totalPagar;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRutBanco() {
		return rutBanco;
	}

	public void setRutBanco(String rutBanco) {
		this.rutBanco = rutBanco;
	}

	public List<Integer> getOficios() {
		return oficios;
	}

	public void setOficios(List<Integer> oficios) {
		this.oficios = oficios;
	}

	public Integer getPartidoSeleccionado() {
		return partidoSeleccionado;
	}

	public void setPartidoSeleccionado(Integer partidoSeleccionado) {
		this.partidoSeleccionado = partidoSeleccionado;
	}

	public Sujeto getSujeto() {
		return sujeto;
	}

	public void setSujeto(Sujeto sujeto) {
		this.sujeto = sujeto;
	}

	public Long getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Long getIdMandato() {
		return idMandato;
	}

	public void setIdMandato(Long idMandato) {
		this.idMandato = idMandato;
	}

	public Integer getEbaId() {
		return ebaId;
	}

	public void setEbaId(Integer ebaId) {
		this.ebaId = ebaId;
	}

	public boolean isPoseeCuenta() {
		return poseeCuenta;
	}

	public void setPoseeCuenta(boolean poseeCuenta) {
		this.poseeCuenta = poseeCuenta;
	}
}
