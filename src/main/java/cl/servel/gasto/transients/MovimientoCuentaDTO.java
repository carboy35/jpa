package cl.servel.gasto.transients;

import java.util.Date;

public class MovimientoCuentaDTO {
	private String tipoEvento;
	private String tipoEleccion;
	private int rut;
	private String dv;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private int tipo;
	private String partido;
	private String accion;
	private String codigoRendicion;
	private int numeroPagina;
	private int numeroLinea;
	private String atributoModificado;
	private String nuevoValor;
	private Date fecha;
	private String usuario;

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getTipoEleccion() {
		return tipoEleccion;
	}

	public void setTipoEleccion(String tipoEleccion) {
		this.tipoEleccion = tipoEleccion;
	}

	public int getRut() {
		return rut;
	}

	public void setRut(int rut) {
		this.rut = rut;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCodigoRendicion() {
		return codigoRendicion;
	}

	public void setCodigoRendicion(String codigoRendicion) {
		this.codigoRendicion = codigoRendicion;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public String getAtributoModificado() {
		return atributoModificado;
	}

	public void setAtributoModificado(String atributoModificado) {
		this.atributoModificado = atributoModificado;
	}

	public String getNuevoValor() {
		return nuevoValor;
	}

	public void setNuevoValor(String nuevoValor) {
		this.nuevoValor = nuevoValor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
