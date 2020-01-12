package cl.servel.gasto.transients;

import java.io.Serializable;

public class IngresoGastoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoRendicion;
	private String eleccion;
	private String territorio;
	private Sujeto sujeto;
	private String afiliacion;
	private String partido;
	private int rutProveedor;
	private String dvProveedor;
	private String nombreProveedor;
	private long fechaDocumento;
	private String tipoDocumento;
	private String descripcionTipoDocumento;
	private int tipoCuenta;
	private String descripcionCuenta;
	private String numeroDocumento;
	private String glosaDocumento;
	private double montoDocumento;
	private String region;

	public String getTipoRendicion() {
		return tipoRendicion;
	}

	public void setTipoRendicion(String tipoRendicion) {
		this.tipoRendicion = tipoRendicion;
	}

	public String getEleccion() {
		return eleccion;
	}

	public void setEleccion(String eleccion) {
		this.eleccion = eleccion;
	}

	public String getTerritorio() {
		return territorio;
	}

	public void setTerritorio(String territorio) {
		this.territorio = territorio;
	}

	public Sujeto getSujeto() {
		return sujeto;
	}

	public void setSujeto(Sujeto sujeto) {
		this.sujeto = sujeto;
	}

	public String getAfiliacion() {
		return afiliacion;
	}

	public void setAfiliacion(String afiliacion) {
		this.afiliacion = afiliacion;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public int getRutProveedor() {
		return rutProveedor;
	}

	public void setRutProveedor(int rutProveedor) {
		this.rutProveedor = rutProveedor;
	}

	public String getDvProveedor() {
		return dvProveedor;
	}

	public void setDvProveedor(String dvProveedor) {
		this.dvProveedor = dvProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public long getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(long fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}

	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}

	public int getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getDescripcionCuenta() {
		return descripcionCuenta;
	}

	public void setDescripcionCuenta(String descripcionCuenta) {
		this.descripcionCuenta = descripcionCuenta;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getGlosaDocumento() {
		return glosaDocumento;
	}

	public void setGlosaDocumento(String glosaDocumento) {
		this.glosaDocumento = glosaDocumento;
	}

	public double getMontoDocumento() {
		return montoDocumento;
	}

	public void setMontoDocumento(double montoDocumento) {
		this.montoDocumento = montoDocumento;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
