package cl.servel.gasto.entity;
// Generated 14-08-2019 18:11:24 by Hibernate Tools 5.2.11.Final

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

import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.UsuUsuarios;

/**
 * NomNomina generated by hbm2java
 */
@Entity
@Table(name = "nom_nomina")
public class NomNomina implements java.io.Serializable {

	private Integer nomId;
	private EveEventoEleccionario eveEventoEleccionario;
	private TpoTipos tpoTipos;
	private UsuUsuarios usuUsuarios;
	private Integer nomFolio;
	private Date nomFechaNomina;
	private String nomEstado;
	private Set<DetDetalleNomina> detDetalleNominas = new HashSet<DetDetalleNomina>(0);

	public NomNomina() {
	}

	public NomNomina(EveEventoEleccionario eveEventoEleccionario, TpoTipos tpoTipos, UsuUsuarios usuUsuarios,
			String nomEstado) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoTipos = tpoTipos;
		this.usuUsuarios = usuUsuarios;
		this.nomEstado = nomEstado;
	}

	public NomNomina(EveEventoEleccionario eveEventoEleccionario, TpoTipos tpoTipos, UsuUsuarios usuUsuarios,
			Integer nomFolio, Date nomFechaNomina, String nomEstado, Set<DetDetalleNomina> detDetalleNominas) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoTipos = tpoTipos;
		this.usuUsuarios = usuUsuarios;
		this.nomFolio = nomFolio;
		this.nomFechaNomina = nomFechaNomina;
		this.nomEstado = nomEstado;
		this.detDetalleNominas = detDetalleNominas;
	}

	@SequenceGenerator(name = "NomNominaIdGenerator", sequenceName = "nom_nomina_nom_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "NomNominaIdGenerator")

	@Column(name = "nom_id", unique = true, nullable = false)
	public Integer getNomId() {
		return this.nomId;
	}

	public void setNomId(Integer nomId) {
		this.nomId = nomId;
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
	@JoinColumn(name = "tpo_nomina_id", nullable = false)
	public TpoTipos getTpoTipos() {
		return this.tpoTipos;
	}

	public void setTpoTipos(TpoTipos tpoTipos) {
		this.tpoTipos = tpoTipos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usu_id", nullable = false)
	public UsuUsuarios getUsuUsuarios() {
		return this.usuUsuarios;
	}

	public void setUsuUsuarios(UsuUsuarios usuUsuarios) {
		this.usuUsuarios = usuUsuarios;
	}

	@Column(name = "nom_folio")
	public Integer getNomFolio() {
		return this.nomFolio;
	}

	public void setNomFolio(Integer nomFolio) {
		this.nomFolio = nomFolio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "nom_fecha_nomina", length = 29)
	public Date getNomFechaNomina() {
		return this.nomFechaNomina;
	}

	public void setNomFechaNomina(Date nomFechaNomina) {
		this.nomFechaNomina = nomFechaNomina;
	}

	@Column(name = "nom_estado", nullable = false, length = 20)
	public String getNomEstado() {
		return this.nomEstado;
	}

	public void setNomEstado(String nomEstado) {
		this.nomEstado = nomEstado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nomNomina")
	public Set<DetDetalleNomina> getDetDetalleNominas() {
		return this.detDetalleNominas;
	}

	public void setDetDetalleNominas(Set<DetDetalleNomina> detDetalleNominas) {
		this.detDetalleNominas = detDetalleNominas;
	}

}
