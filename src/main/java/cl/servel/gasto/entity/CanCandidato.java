package cl.servel.gasto.entity;
// Generated 26-08-2019 10:28:58 by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CanCandidato generated by hbm2java
 */
@Entity
@Table(name = "can_candidato")
public class CanCandidato implements java.io.Serializable {

	private Integer canId;
	private EveEventoEleccionario eveEventoEleccionario;
	private PacPacto pacPacto;
	private ParPartido parPartido;
	private SelSubEleccion selSubEleccion;
	private SpaPacto spaPacto;
	private TpoCandidato tpoCandidato;
	private String canNombres;
	private String canAppPaterno;
	private String canAppMaterno;
	private String canSexo;
	private String canMail;
	private String canDirPostal;
	private String canDireccion;
	private String canComuna;
	private String canFechaNacimiento;
	private String canTelefonoFijo;
	private String canTelefonoCelu;
	private Integer canNumeroEnVoto;
	private Integer canRut;
	private String canRutDv;
	private String canEstado;
	private String canEstadoCandidatura;
	private Boolean canEliminado;
	private Date canCreated;
	private Date canModified;
	private String canCodigoOrigen;
	private Integer canCantidadVotos;
	private Boolean canHabilitado;
	private Boolean canElecto;
	private Set<DetDetalleNomina> detDetalleNominas = new HashSet<DetDetalleNomina>(0);
	private Set<InfInstanciaFlujo> infInstanciaFlujos = new HashSet<InfInstanciaFlujo>(0);
	private Set<RlcAdminCandidato> rlcAdminCandidatos = new HashSet<RlcAdminCandidato>(0);
	private Set<DarDetalleActaRecepcion> darDetalleActaRecepcions = new HashSet<DarDetalleActaRecepcion>(0);
	private Set<ExExpediente> exExpedientes = new HashSet<ExExpediente>(0);
	private Set<HisHistorico> hisHistoricos = new HashSet<HisHistorico>(0);
	private Set<RenRendicion> renRendicions = new HashSet<RenRendicion>(0);
	private Set<CctCuentaContable> cctCuentaContables = new HashSet<CctCuentaContable>(0);
	private Set<ManMandato> manMandatos = new HashSet<ManMandato>(0);
	private Set<IngIngresos> ingIngresoses = new HashSet<IngIngresos>(0);

	public CanCandidato() {
	}

	public CanCandidato(EveEventoEleccionario eveEventoEleccionario, TpoCandidato tpoCandidato) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoCandidato = tpoCandidato;
	}

	public CanCandidato(EveEventoEleccionario eveEventoEleccionario, PacPacto pacPacto, ParPartido parPartido,
			SelSubEleccion selSubEleccion, SpaPacto spaPacto, TpoCandidato tpoCandidato, String canNombres,
			String canAppPaterno, String canAppMaterno, String canSexo, String canMail, String canDirPostal,
			String canDireccion, String canComuna, String canFechaNacimiento, String canTelefonoFijo,
			String canTelefonoCelu, Integer canNumeroEnVoto, Integer canRut, String canRutDv, String canEstado,
			String canEstadoCandidatura, Boolean canEliminado, Date canCreated, Date canModified,
			String canCodigoOrigen, Integer canCantidadVotos, Boolean canHabilitado, Boolean canElecto,
			Set<DetDetalleNomina> detDetalleNominas, Set<InfInstanciaFlujo> infInstanciaFlujos,
			Set<RlcAdminCandidato> rlcAdminCandidatos, Set<DarDetalleActaRecepcion> darDetalleActaRecepcions,
			Set<ExExpediente> exExpedientes, Set<HisHistorico> hisHistoricos, Set<RenRendicion> renRendicions,
			Set<CctCuentaContable> cctCuentaContables, Set<ManMandato> manMandatos, Set<IngIngresos> ingIngresoses) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.pacPacto = pacPacto;
		this.parPartido = parPartido;
		this.selSubEleccion = selSubEleccion;
		this.spaPacto = spaPacto;
		this.tpoCandidato = tpoCandidato;
		this.canNombres = canNombres;
		this.canAppPaterno = canAppPaterno;
		this.canAppMaterno = canAppMaterno;
		this.canSexo = canSexo;
		this.canMail = canMail;
		this.canDirPostal = canDirPostal;
		this.canDireccion = canDireccion;
		this.canComuna = canComuna;
		this.canFechaNacimiento = canFechaNacimiento;
		this.canTelefonoFijo = canTelefonoFijo;
		this.canTelefonoCelu = canTelefonoCelu;
		this.canNumeroEnVoto = canNumeroEnVoto;
		this.canRut = canRut;
		this.canRutDv = canRutDv;
		this.canEstado = canEstado;
		this.canEstadoCandidatura = canEstadoCandidatura;
		this.canEliminado = canEliminado;
		this.canCreated = canCreated;
		this.canModified = canModified;
		this.canCodigoOrigen = canCodigoOrigen;
		this.canCantidadVotos = canCantidadVotos;
		this.canHabilitado = canHabilitado;
		this.canElecto = canElecto;
		this.detDetalleNominas = detDetalleNominas;
		this.infInstanciaFlujos = infInstanciaFlujos;
		this.rlcAdminCandidatos = rlcAdminCandidatos;
		this.darDetalleActaRecepcions = darDetalleActaRecepcions;
		this.exExpedientes = exExpedientes;
		this.hisHistoricos = hisHistoricos;
		this.renRendicions = renRendicions;
		this.cctCuentaContables = cctCuentaContables;
		this.manMandatos = manMandatos;
		this.ingIngresoses = ingIngresoses;
	}

	@SequenceGenerator(name = "cl.servel.gasto.temporal.CanCandidatoIdGenerator", sequenceName = "can_candidato_can_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.temporal.CanCandidatoIdGenerator")

	@Column(name = "can_id", unique = true, nullable = false)
	public Integer getCanId() {
		return this.canId;
	}

	public void setCanId(Integer canId) {
		this.canId = canId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eve_id", nullable = false)
	public EveEventoEleccionario getEveEventoEleccionario() {
		return this.eveEventoEleccionario;
	}

	public void setEveEventoEleccionario(EveEventoEleccionario eveEventoEleccionario) {
		this.eveEventoEleccionario = eveEventoEleccionario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pac_id")
	public PacPacto getPacPacto() {
		return this.pacPacto;
	}

	public void setPacPacto(PacPacto pacPacto) {
		this.pacPacto = pacPacto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "par_id")
	public ParPartido getParPartido() {
		return this.parPartido;
	}

	public void setParPartido(ParPartido parPartido) {
		this.parPartido = parPartido;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sel_id")
	public SelSubEleccion getSelSubEleccion() {
		return this.selSubEleccion;
	}

	public void setSelSubEleccion(SelSubEleccion selSubEleccion) {
		this.selSubEleccion = selSubEleccion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sup_id")
	public SpaPacto getSpaPacto() {
		return this.spaPacto;
	}

	public void setSpaPacto(SpaPacto spaPacto) {
		this.spaPacto = spaPacto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tpo_can_id", nullable = false)
	public TpoCandidato getTpoCandidato() {
		return this.tpoCandidato;
	}

	public void setTpoCandidato(TpoCandidato tpoCandidato) {
		this.tpoCandidato = tpoCandidato;
	}

	@Column(name = "can_nombres", length = 50)
	public String getCanNombres() {
		return this.canNombres;
	}

	public void setCanNombres(String canNombres) {
		this.canNombres = canNombres;
	}

	@Column(name = "can_app_paterno", length = 50)
	public String getCanAppPaterno() {
		return this.canAppPaterno;
	}

	public void setCanAppPaterno(String canAppPaterno) {
		this.canAppPaterno = canAppPaterno;
	}

	@Column(name = "can_app_materno", length = 50)
	public String getCanAppMaterno() {
		return this.canAppMaterno;
	}

	public void setCanAppMaterno(String canAppMaterno) {
		this.canAppMaterno = canAppMaterno;
	}

	@Column(name = "can_sexo", length = 15)
	public String getCanSexo() {
		return this.canSexo;
	}

	public void setCanSexo(String canSexo) {
		this.canSexo = canSexo;
	}

	@Column(name = "can_mail", length = 100)
	public String getCanMail() {
		return this.canMail;
	}

	public void setCanMail(String canMail) {
		this.canMail = canMail;
	}

	@Column(name = "can_dir_postal", length = 20)
	public String getCanDirPostal() {
		return this.canDirPostal;
	}

	public void setCanDirPostal(String canDirPostal) {
		this.canDirPostal = canDirPostal;
	}

	@Column(name = "can_direccion", length = 50)
	public String getCanDireccion() {
		return this.canDireccion;
	}

	public void setCanDireccion(String canDireccion) {
		this.canDireccion = canDireccion;
	}

	@Column(name = "can_comuna", length = 30)
	public String getCanComuna() {
		return this.canComuna;
	}

	public void setCanComuna(String canComuna) {
		this.canComuna = canComuna;
	}

	@Column(name = "can_fecha_nacimiento", length = 30)
	public String getCanFechaNacimiento() {
		return this.canFechaNacimiento;
	}

	public void setCanFechaNacimiento(String canFechaNacimiento) {
		this.canFechaNacimiento = canFechaNacimiento;
	}

	@Column(name = "can_telefono_fijo", length = 30)
	public String getCanTelefonoFijo() {
		return this.canTelefonoFijo;
	}

	public void setCanTelefonoFijo(String canTelefonoFijo) {
		this.canTelefonoFijo = canTelefonoFijo;
	}

	@Column(name = "can_telefono_celu", length = 30)
	public String getCanTelefonoCelu() {
		return this.canTelefonoCelu;
	}

	public void setCanTelefonoCelu(String canTelefonoCelu) {
		this.canTelefonoCelu = canTelefonoCelu;
	}

	@Column(name = "can_numero_en_voto")
	public Integer getCanNumeroEnVoto() {
		return this.canNumeroEnVoto;
	}

	public void setCanNumeroEnVoto(Integer canNumeroEnVoto) {
		this.canNumeroEnVoto = canNumeroEnVoto;
	}

	@Column(name = "can_rut")
	public Integer getCanRut() {
		return this.canRut;
	}

	public void setCanRut(Integer canRut) {
		this.canRut = canRut;
	}

	@Column(name = "can_rut_dv", length = 1)
	public String getCanRutDv() {
		return this.canRutDv;
	}

	public void setCanRutDv(String canRutDv) {
		this.canRutDv = canRutDv;
	}

	@Column(name = "can_estado", length = 60)
	public String getCanEstado() {
		return this.canEstado;
	}

	public void setCanEstado(String canEstado) {
		this.canEstado = canEstado;
	}

	@Column(name = "can_estado_candidatura", length = 60)
	public String getCanEstadoCandidatura() {
		return this.canEstadoCandidatura;
	}

	public void setCanEstadoCandidatura(String canEstadoCandidatura) {
		this.canEstadoCandidatura = canEstadoCandidatura;
	}

	@Column(name = "can_eliminado")
	public Boolean getCanEliminado() {
		return this.canEliminado;
	}

	public void setCanEliminado(Boolean canEliminado) {
		this.canEliminado = canEliminado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "can_created", length = 13)
	public Date getCanCreated() {
		return this.canCreated;
	}

	public void setCanCreated(Date canCreated) {
		this.canCreated = canCreated;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "can_modified", length = 13)
	public Date getCanModified() {
		return this.canModified;
	}

	public void setCanModified(Date canModified) {
		this.canModified = canModified;
	}

	@Column(name = "can_codigo_origen", length = 30)
	public String getCanCodigoOrigen() {
		return this.canCodigoOrigen;
	}

	public void setCanCodigoOrigen(String canCodigoOrigen) {
		this.canCodigoOrigen = canCodigoOrigen;
	}

	@Column(name = "can_cantidad_votos")
	public Integer getCanCantidadVotos() {
		return this.canCantidadVotos;
	}

	public void setCanCantidadVotos(Integer canCantidadVotos) {
		this.canCantidadVotos = canCantidadVotos;
	}

	@Column(name = "can_habilitado")
	public Boolean getCanHabilitado() {
		return this.canHabilitado;
	}

	public void setCanHabilitado(Boolean canHabilitado) {
		this.canHabilitado = canHabilitado;
	}

	@Column(name = "can_electo")
	public Boolean getCanElecto() {
		return this.canElecto;
	}

	public void setCanElecto(Boolean canElecto) {
		this.canElecto = canElecto;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<DetDetalleNomina> getDetDetalleNominas() {
		return this.detDetalleNominas;
	}

	public void setDetDetalleNominas(Set<DetDetalleNomina> detDetalleNominas) {
		this.detDetalleNominas = detDetalleNominas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<InfInstanciaFlujo> getInfInstanciaFlujos() {
		return this.infInstanciaFlujos;
	}

	public void setInfInstanciaFlujos(Set<InfInstanciaFlujo> infInstanciaFlujos) {
		this.infInstanciaFlujos = infInstanciaFlujos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<RlcAdminCandidato> getRlcAdminCandidatos() {
		return this.rlcAdminCandidatos;
	}

	public void setRlcAdminCandidatos(Set<RlcAdminCandidato> rlcAdminCandidatos) {
		this.rlcAdminCandidatos = rlcAdminCandidatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<DarDetalleActaRecepcion> getDarDetalleActaRecepcions() {
		return this.darDetalleActaRecepcions;
	}

	public void setDarDetalleActaRecepcions(Set<DarDetalleActaRecepcion> darDetalleActaRecepcions) {
		this.darDetalleActaRecepcions = darDetalleActaRecepcions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<ExExpediente> getExExpedientes() {
		return this.exExpedientes;
	}

	public void setExExpedientes(Set<ExExpediente> exExpedientes) {
		this.exExpedientes = exExpedientes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<HisHistorico> getHisHistoricos() {
		return this.hisHistoricos;
	}

	public void setHisHistoricos(Set<HisHistorico> hisHistoricos) {
		this.hisHistoricos = hisHistoricos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<RenRendicion> getRenRendicions() {
		return this.renRendicions;
	}

	public void setRenRendicions(Set<RenRendicion> renRendicions) {
		this.renRendicions = renRendicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<CctCuentaContable> getCctCuentaContables() {
		return this.cctCuentaContables;
	}

	public void setCctCuentaContables(Set<CctCuentaContable> cctCuentaContables) {
		this.cctCuentaContables = cctCuentaContables;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<ManMandato> getManMandatos() {
		return this.manMandatos;
	}

	public void setManMandatos(Set<ManMandato> manMandatos) {
		this.manMandatos = manMandatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "canCandidato")
	public Set<IngIngresos> getIngIngresoses() {
		return this.ingIngresoses;
	}

	public void setIngIngresoses(Set<IngIngresos> ingIngresoses) {
		this.ingIngresoses = ingIngresoses;
	}

}
