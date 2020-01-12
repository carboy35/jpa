package cl.servel.gasto.entity;
// Generated 28-05-2019 17:59:23 by Hibernate Tools 5.2.11.Final

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * DbnDefinicionBandeja generated by hbm2java
 */
@Entity
@Table(name = "dbn_definicion_bandeja")
public class DbnDefinicionBandeja implements java.io.Serializable {

	private Integer dbnId;
	private String dbnCodigo;
	private String dbnNombreBandeja;
	private Set<InbInstanciaBandeja> inbInstanciaBandejas = new HashSet<InbInstanciaBandeja>(0);
	private Set<DtaDefinicionTarea> dtaDefinicionTareas = new HashSet<DtaDefinicionTarea>(0);

	public DbnDefinicionBandeja() {
	}

	public DbnDefinicionBandeja(String dbnCodigo, String dbnNombreBandeja) {
		this.dbnCodigo = dbnCodigo;
		this.dbnNombreBandeja = dbnNombreBandeja;
	}

	public DbnDefinicionBandeja(String dbnCodigo, String dbnNombreBandeja, Set<InbInstanciaBandeja> inbInstanciaBandejas, Set<DtaDefinicionTarea> dtaDefinicionTareas) {
		this.dbnCodigo = dbnCodigo;
		this.dbnNombreBandeja = dbnNombreBandeja;
		this.inbInstanciaBandejas = inbInstanciaBandejas;
		this.dtaDefinicionTareas = dtaDefinicionTareas;
	}

	@SequenceGenerator(name = "DbnDefinicionBandejaIdGenerator", sequenceName = "dbn_definicion_bandeja_dbn_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DbnDefinicionBandejaIdGenerator")

	@Column(name = "dbn_id", unique = true, nullable = false)
	public Integer getDbnId() {
		return this.dbnId;
	}

	public void setDbnId(Integer dbnId) {
		this.dbnId = dbnId;
	}

	@Column(name = "dbn_codigo", nullable = false, length = 10)
	public String getDbnCodigo() {
		return this.dbnCodigo;
	}

	public void setDbnCodigo(String dbnCodigo) {
		this.dbnCodigo = dbnCodigo;
	}

	@Column(name = "dbn_nombre_bandeja", nullable = false, length = 100)
	public String getDbnNombreBandeja() {
		return this.dbnNombreBandeja;
	}

	public void setDbnNombreBandeja(String dbnNombreBandeja) {
		this.dbnNombreBandeja = dbnNombreBandeja;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dbnDefinicionBandeja")
	public Set<InbInstanciaBandeja> getInbInstanciaBandejas() {
		return this.inbInstanciaBandejas;
	}

	public void setInbInstanciaBandejas(Set<InbInstanciaBandeja> inbInstanciaBandejas) {
		this.inbInstanciaBandejas = inbInstanciaBandejas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dbnDefinicionBandeja")
	public Set<DtaDefinicionTarea> getDtaDefinicionTareas() {
		return this.dtaDefinicionTareas;
	}

	public void setDtaDefinicionTareas(Set<DtaDefinicionTarea> dtaDefinicionTareas) {
		this.dtaDefinicionTareas = dtaDefinicionTareas;
	}

}
