package cl.servel.gasto.entity;
// Generated 27-11-2019 11:30:22 by Hibernate Tools 5.2.11.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EveEventoEleccionario generated by hbm2java
 */
@Entity
@Table(name = "eve_evento_eleccionario")
public class EveEventoEleccionario implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer eveId;
	private int idTipoEvento;
	private String eveNombre;
	private Date eveFecha;
	private String eveEstado;
	private Date eveCreated;
	private Date eveModified;
	private Boolean eveEliminado;
	private Integer dexId;
	private Set<RenRendicion> renRendicions = new HashSet<RenRendicion>(0);
	private Set<DflDefinicionFlujo> dflDefinicionFlujos = new HashSet<DflDefinicionFlujo>(0);
	private Set<ConConfiguracion> conConfiguracions = new HashSet<ConConfiguracion>(0);
	private Set<EleEleccion> eleEleccions = new HashSet<EleEleccion>(0);
	private Set<AcrActaRecepcion> acrActaRecepcions = new HashSet<AcrActaRecepcion>(0);
	private Set<PamParametro> pamParametros = new HashSet<PamParametro>(0);
	private Set<BitBitacora> bitBitacoras = new HashSet<BitBitacora>(0);
	private Set<SelSubEleccion> selSubEleccions = new HashSet<SelSubEleccion>(0);
	private Set<TpoReembolso> tpoReembolsos = new HashSet<TpoReembolso>(0);
	private Set<CanCandidato> canCandidatos = new HashSet<CanCandidato>(0);
	private Set<HisHistorico> hisHistoricos = new HashSet<HisHistorico>(0);
	private Set<InfInstanciaFlujo> infInstanciaFlujos = new HashSet<InfInstanciaFlujo>(0);
	private Set<RlcAdminCandidato> rlcAdminCandidatos = new HashSet<RlcAdminCandidato>(0);
	private Set<CadCargaDatos> cadCargaDatoses = new HashSet<CadCargaDatos>(0);
	private Set<NivNivel> nivNivels = new HashSet<NivNivel>(0);
	private Set<TpoCtaGasto> tpoCtaGastos = new HashSet<TpoCtaGasto>(0);
	private Set<AdmAdministradorElectoral> admAdministradorElectorals = new HashSet<AdmAdministradorElectoral>(0);
	private Set<IngIngresos> ingIngresoses = new HashSet<IngIngresos>(0);
	private Set<NomNomina> nomNominas = new HashSet<NomNomina>(0);
	private Set<CelCelula> celCelulas = new HashSet<CelCelula>(0);
	private Set<TpoObservacion> tpoObservacions = new HashSet<TpoObservacion>(0);
	private Set<TpoRendicion> tpoRendicions = new HashSet<TpoRendicion>(0);
	private Set<RlcRelNivel> rlcRelNivels = new HashSet<RlcRelNivel>(0);
	private Set<TpoDocGasto> tpoDocGastos = new HashSet<TpoDocGasto>(0);
	private Set<LimLimiteGasto> limLimiteGastos = new HashSet<LimLimiteGasto>(0);
	private Set<InbInstanciaBandeja> inbInstanciaBandejas = new HashSet<InbInstanciaBandeja>(0);
	private Set<TpoConceptoCtaGasto> tpoConceptoCtaGastos = new HashSet<TpoConceptoCtaGasto>(0);

	public EveEventoEleccionario() {
	}

	public EveEventoEleccionario(int idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public EveEventoEleccionario(int idTipoEvento, String eveNombre, Date eveFecha, String eveEstado, Date eveCreated,
			Date eveModified, Boolean eveEliminado, Integer dexId, Set<RenRendicion> renRendicions,
			Set<DflDefinicionFlujo> dflDefinicionFlujos, Set<ConConfiguracion> conConfiguracions,
			Set<EleEleccion> eleEleccions, Set<AcrActaRecepcion> acrActaRecepcions, Set<PamParametro> pamParametros,
			Set<BitBitacora> bitBitacoras, Set<SelSubEleccion> selSubEleccions, Set<TpoReembolso> tpoReembolsos,
			Set<CanCandidato> canCandidatos, Set<HisHistorico> hisHistoricos, Set<InfInstanciaFlujo> infInstanciaFlujos,
			Set<RlcAdminCandidato> rlcAdminCandidatos, Set<CadCargaDatos> cadCargaDatoses, Set<NivNivel> nivNivels,
			Set<TpoCtaGasto> tpoCtaGastos, Set<AdmAdministradorElectoral> admAdministradorElectorals,
			Set<IngIngresos> ingIngresoses, Set<NomNomina> nomNominas, Set<CelCelula> celCelulas,
			Set<TpoObservacion> tpoObservacions, Set<TpoRendicion> tpoRendicions, Set<RlcRelNivel> rlcRelNivels,
			Set<TpoDocGasto> tpoDocGastos, Set<LimLimiteGasto> limLimiteGastos,
			Set<InbInstanciaBandeja> inbInstanciaBandejas, Set<TpoConceptoCtaGasto> tpoConceptoCtaGastos) {
		this.idTipoEvento = idTipoEvento;
		this.eveNombre = eveNombre;
		this.eveFecha = eveFecha;
		this.eveEstado = eveEstado;
		this.eveCreated = eveCreated;
		this.eveModified = eveModified;
		this.eveEliminado = eveEliminado;
		this.dexId = dexId;
		this.renRendicions = renRendicions;
		this.dflDefinicionFlujos = dflDefinicionFlujos;
		this.conConfiguracions = conConfiguracions;
		this.eleEleccions = eleEleccions;
		this.acrActaRecepcions = acrActaRecepcions;
		this.pamParametros = pamParametros;
		this.bitBitacoras = bitBitacoras;
		this.selSubEleccions = selSubEleccions;
		this.tpoReembolsos = tpoReembolsos;
		this.canCandidatos = canCandidatos;
		this.hisHistoricos = hisHistoricos;
		this.infInstanciaFlujos = infInstanciaFlujos;
		this.rlcAdminCandidatos = rlcAdminCandidatos;
		this.cadCargaDatoses = cadCargaDatoses;
		this.nivNivels = nivNivels;
		this.tpoCtaGastos = tpoCtaGastos;
		this.admAdministradorElectorals = admAdministradorElectorals;
		this.ingIngresoses = ingIngresoses;
		this.nomNominas = nomNominas;
		this.celCelulas = celCelulas;
		this.tpoObservacions = tpoObservacions;
		this.tpoRendicions = tpoRendicions;
		this.rlcRelNivels = rlcRelNivels;
		this.tpoDocGastos = tpoDocGastos;
		this.limLimiteGastos = limLimiteGastos;
		this.inbInstanciaBandejas = inbInstanciaBandejas;
		this.tpoConceptoCtaGastos = tpoConceptoCtaGastos;
	}

	@SequenceGenerator(name = "cl.servel.gasto.entity.EveEventoEleccionarioIdGenerator", sequenceName = "eve_evento_eleccionario_eve_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.entity.EveEventoEleccionarioIdGenerator")

	@Column(name = "eve_id", unique = true, nullable = false)
	public Integer getEveId() {
		return this.eveId;
	}

	public void setEveId(Integer eveId) {
		this.eveId = eveId;
	}

	@Column(name = "id_tipo_evento", nullable = false)
	public int getIdTipoEvento() {
		return this.idTipoEvento;
	}

	public void setIdTipoEvento(int idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	@Column(name = "eve_nombre", length = 50)
	public String getEveNombre() {
		return this.eveNombre;
	}

	public void setEveNombre(String eveNombre) {
		this.eveNombre = eveNombre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eve_fecha", length = 29)
	public Date getEveFecha() {
		return this.eveFecha;
	}

	public void setEveFecha(Date eveFecha) {
		this.eveFecha = eveFecha;
	}

	@Column(name = "eve_estado", length = 5)
	public String getEveEstado() {
		return this.eveEstado;
	}

	public void setEveEstado(String eveEstado) {
		this.eveEstado = eveEstado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "eve_created", length = 13)
	public Date getEveCreated() {
		return this.eveCreated;
	}

	public void setEveCreated(Date eveCreated) {
		this.eveCreated = eveCreated;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "eve_modified", length = 13)
	public Date getEveModified() {
		return this.eveModified;
	}

	public void setEveModified(Date eveModified) {
		this.eveModified = eveModified;
	}

	@Column(name = "eve_eliminado")
	public Boolean getEveEliminado() {
		return this.eveEliminado;
	}

	public void setEveEliminado(Boolean eveEliminado) {
		this.eveEliminado = eveEliminado;
	}

	@Column(name = "dex_id")
	public Integer getDexId() {
		return this.dexId;
	}

	public void setDexId(Integer dexId) {
		this.dexId = dexId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<RenRendicion> getRenRendicions() {
		return this.renRendicions;
	}

	public void setRenRendicions(Set<RenRendicion> renRendicions) {
		this.renRendicions = renRendicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<DflDefinicionFlujo> getDflDefinicionFlujos() {
		return this.dflDefinicionFlujos;
	}

	public void setDflDefinicionFlujos(Set<DflDefinicionFlujo> dflDefinicionFlujos) {
		this.dflDefinicionFlujos = dflDefinicionFlujos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<ConConfiguracion> getConConfiguracions() {
		return this.conConfiguracions;
	}

	public void setConConfiguracions(Set<ConConfiguracion> conConfiguracions) {
		this.conConfiguracions = conConfiguracions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<EleEleccion> getEleEleccions() {
		return this.eleEleccions;
	}

	public void setEleEleccions(Set<EleEleccion> eleEleccions) {
		this.eleEleccions = eleEleccions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<AcrActaRecepcion> getAcrActaRecepcions() {
		return this.acrActaRecepcions;
	}

	public void setAcrActaRecepcions(Set<AcrActaRecepcion> acrActaRecepcions) {
		this.acrActaRecepcions = acrActaRecepcions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<PamParametro> getPamParametros() {
		return this.pamParametros;
	}

	public void setPamParametros(Set<PamParametro> pamParametros) {
		this.pamParametros = pamParametros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<BitBitacora> getBitBitacoras() {
		return this.bitBitacoras;
	}

	public void setBitBitacoras(Set<BitBitacora> bitBitacoras) {
		this.bitBitacoras = bitBitacoras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<SelSubEleccion> getSelSubEleccions() {
		return this.selSubEleccions;
	}

	public void setSelSubEleccions(Set<SelSubEleccion> selSubEleccions) {
		this.selSubEleccions = selSubEleccions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<TpoReembolso> getTpoReembolsos() {
		return this.tpoReembolsos;
	}

	public void setTpoReembolsos(Set<TpoReembolso> tpoReembolsos) {
		this.tpoReembolsos = tpoReembolsos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<CanCandidato> getCanCandidatos() {
		return this.canCandidatos;
	}

	public void setCanCandidatos(Set<CanCandidato> canCandidatos) {
		this.canCandidatos = canCandidatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<HisHistorico> getHisHistoricos() {
		return this.hisHistoricos;
	}

	public void setHisHistoricos(Set<HisHistorico> hisHistoricos) {
		this.hisHistoricos = hisHistoricos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<InfInstanciaFlujo> getInfInstanciaFlujos() {
		return this.infInstanciaFlujos;
	}

	public void setInfInstanciaFlujos(Set<InfInstanciaFlujo> infInstanciaFlujos) {
		this.infInstanciaFlujos = infInstanciaFlujos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<RlcAdminCandidato> getRlcAdminCandidatos() {
		return this.rlcAdminCandidatos;
	}

	public void setRlcAdminCandidatos(Set<RlcAdminCandidato> rlcAdminCandidatos) {
		this.rlcAdminCandidatos = rlcAdminCandidatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<CadCargaDatos> getCadCargaDatoses() {
		return this.cadCargaDatoses;
	}

	public void setCadCargaDatoses(Set<CadCargaDatos> cadCargaDatoses) {
		this.cadCargaDatoses = cadCargaDatoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<NivNivel> getNivNivels() {
		return this.nivNivels;
	}

	public void setNivNivels(Set<NivNivel> nivNivels) {
		this.nivNivels = nivNivels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<TpoCtaGasto> getTpoCtaGastos() {
		return this.tpoCtaGastos;
	}

	public void setTpoCtaGastos(Set<TpoCtaGasto> tpoCtaGastos) {
		this.tpoCtaGastos = tpoCtaGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<AdmAdministradorElectoral> getAdmAdministradorElectorals() {
		return this.admAdministradorElectorals;
	}

	public void setAdmAdministradorElectorals(Set<AdmAdministradorElectoral> admAdministradorElectorals) {
		this.admAdministradorElectorals = admAdministradorElectorals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<IngIngresos> getIngIngresoses() {
		return this.ingIngresoses;
	}

	public void setIngIngresoses(Set<IngIngresos> ingIngresoses) {
		this.ingIngresoses = ingIngresoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<NomNomina> getNomNominas() {
		return this.nomNominas;
	}

	public void setNomNominas(Set<NomNomina> nomNominas) {
		this.nomNominas = nomNominas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<CelCelula> getCelCelulas() {
		return this.celCelulas;
	}

	public void setCelCelulas(Set<CelCelula> celCelulas) {
		this.celCelulas = celCelulas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<TpoObservacion> getTpoObservacions() {
		return this.tpoObservacions;
	}

	public void setTpoObservacions(Set<TpoObservacion> tpoObservacions) {
		this.tpoObservacions = tpoObservacions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<TpoRendicion> getTpoRendicions() {
		return this.tpoRendicions;
	}

	public void setTpoRendicions(Set<TpoRendicion> tpoRendicions) {
		this.tpoRendicions = tpoRendicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<RlcRelNivel> getRlcRelNivels() {
		return this.rlcRelNivels;
	}

	public void setRlcRelNivels(Set<RlcRelNivel> rlcRelNivels) {
		this.rlcRelNivels = rlcRelNivels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<TpoDocGasto> getTpoDocGastos() {
		return this.tpoDocGastos;
	}

	public void setTpoDocGastos(Set<TpoDocGasto> tpoDocGastos) {
		this.tpoDocGastos = tpoDocGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<LimLimiteGasto> getLimLimiteGastos() {
		return this.limLimiteGastos;
	}

	public void setLimLimiteGastos(Set<LimLimiteGasto> limLimiteGastos) {
		this.limLimiteGastos = limLimiteGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<InbInstanciaBandeja> getInbInstanciaBandejas() {
		return this.inbInstanciaBandejas;
	}

	public void setInbInstanciaBandejas(Set<InbInstanciaBandeja> inbInstanciaBandejas) {
		this.inbInstanciaBandejas = inbInstanciaBandejas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eveEventoEleccionario")
	public Set<TpoConceptoCtaGasto> getTpoConceptoCtaGastos() {
		return this.tpoConceptoCtaGastos;
	}

	public void setTpoConceptoCtaGastos(Set<TpoConceptoCtaGasto> tpoConceptoCtaGastos) {
		this.tpoConceptoCtaGastos = tpoConceptoCtaGastos;
	}

}
