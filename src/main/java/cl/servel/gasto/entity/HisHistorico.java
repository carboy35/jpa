package cl.servel.gasto.entity;
// Generated 18-10-2018 17:36:13 by Hibernate Tools 5.2.11.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HisHistorico generated by hbm2java
 */
@Entity
@Table(name = "his_historico")
public class HisHistorico implements java.io.Serializable {

	private Integer hisId;
	private AdmAdministradorElectoral admAdministradorElectoral;
	private CanCandidato canCandidato;
	private EveEventoEleccionario eveEventoEleccionario;
	private ParPartido parPartido;
	private Date hisFechaInicio;
	private Date hisFechaTermino;
	private String hisEstado;
	private Boolean hisEliminado;
	private Date hisCreated;
	private Date hisModified;
	private Date hisFechaRenuncia;

	public HisHistorico() {
	}

	public HisHistorico(AdmAdministradorElectoral admAdministradorElectoral, CanCandidato canCandidato,
			EveEventoEleccionario eveEventoEleccionario, ParPartido parPartido, Date hisFechaRenuncia) {
		this.admAdministradorElectoral = admAdministradorElectoral;
		this.canCandidato = canCandidato;
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.parPartido = parPartido;
		this.hisFechaRenuncia = hisFechaRenuncia;
	}

	public HisHistorico(AdmAdministradorElectoral admAdministradorElectoral, CanCandidato canCandidato,
			EveEventoEleccionario eveEventoEleccionario, ParPartido parPartido, Date hisFechaInicio, Date hisFechaTermino, String hisEstado,
			Boolean hisEliminado, Date hisCreated, Date hisModified, Date hisFechaRenuncia) {
		this.admAdministradorElectoral = admAdministradorElectoral;
		this.canCandidato = canCandidato;
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.parPartido = parPartido;
		this.hisFechaInicio = hisFechaInicio;
		this.hisFechaTermino = hisFechaTermino;
		this.hisEstado = hisEstado;
		this.hisEliminado = hisEliminado;
		this.hisCreated = hisCreated;
		this.hisModified = hisModified;
		this.hisFechaRenuncia = hisFechaRenuncia;
	}

	@SequenceGenerator(name = "cl.servel.gasto.entity.HisHistoricoIdGenerator", sequenceName = "his_historico_his_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.entity.HisHistoricoIdGenerator")

	@Column(name = "his_id", unique = true, nullable = false)
	public Integer getHisId() {
		return this.hisId;
	}

	public void setHisId(Integer hisId) {
		this.hisId = hisId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adm_id", nullable = false)
	public AdmAdministradorElectoral getAdmAdministradorElectoral() {
		return this.admAdministradorElectoral;
	}

	public void setAdmAdministradorElectoral(AdmAdministradorElectoral admAdministradorElectoral) {
		this.admAdministradorElectoral = admAdministradorElectoral;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "can_id", nullable = false)
	public CanCandidato getCanCandidato() {
		return this.canCandidato;
	}

	public void setCanCandidato(CanCandidato canCandidato) {
		this.canCandidato = canCandidato;
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
	@JoinColumn(name = "par_id", nullable = false)
	public ParPartido getParPartido() {
		return this.parPartido;
	}

	public void setParPartido(ParPartido parPartido) {
		this.parPartido = parPartido;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "his_fecha_inicio", length = 13)
	public Date getHisFechaInicio() {
		return this.hisFechaInicio;
	}

	public void setHisFechaInicio(Date hisFechaInicio) {
		this.hisFechaInicio = hisFechaInicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "his_fecha_termino", length = 13)
	public Date getHisFechaTermino() {
		return this.hisFechaTermino;
	}

	public void setHisFechaTermino(Date hisFechaTermino) {
		this.hisFechaTermino = hisFechaTermino;
	}
	
	
	@Column(name = "his_estado", length = 10)
	public String getHisEstado() {
		return this.hisEstado;
	}

	public void setHisEstado(String hisEstado) {
		this.hisEstado = hisEstado;
	}

	@Column(name = "his_eliminado")
	public Boolean getHisEliminado() {
		return this.hisEliminado;
	}

	public void setHisEliminado(Boolean hisEliminado) {
		this.hisEliminado = hisEliminado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "his_created", length = 13)
	public Date getHisCreated() {
		return this.hisCreated;
	}

	public void setHisCreated(Date hisCreated) {
		this.hisCreated = hisCreated;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "his_modified", length = 13)
	public Date getHisModified() {
		return this.hisModified;
	}

	public void setHisModified(Date hisModified) {
		this.hisModified = hisModified;
	}

	public Date getHisFechaRenuncia() {
		return hisFechaRenuncia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "his_fecha_renuncia", length = 13)
	public void setHisFechaRenuncia(Date hisFechaRenuncia) {
		this.hisFechaRenuncia = hisFechaRenuncia;
	}

}
