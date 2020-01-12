package cl.servel.gasto.entity;
// Generated 29-05-2019 14:43:54 by Hibernate Tools 5.2.11.Final

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

/**
 * RlcPerfilDefinicionTarea generated by hbm2java
 */
@Entity
@Table(name = "rlc_perfil_definicion_tarea")
public class RlcPerfilDefinicionTarea implements java.io.Serializable {

	private Integer rlcId;
	private DtaDefinicionTarea dtaDefinicionTarea;
	private PerPerfil perPerfil;

	public RlcPerfilDefinicionTarea() {
	}

	public RlcPerfilDefinicionTarea(DtaDefinicionTarea dtaDefinicionTarea, PerPerfil perPerfil) {
		this.dtaDefinicionTarea = dtaDefinicionTarea;
		this.perPerfil = perPerfil;
	}

	@SequenceGenerator(name = "RlcPerfilDefinicionTareaIdGenerator", sequenceName = "rlc_perfil_definicion_tarea_rlc_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "RlcPerfilDefinicionTareaIdGenerator")

	@Column(name = "rlc_id", unique = true, nullable = false)
	public Integer getRlcId() {
		return this.rlcId;
	}

	public void setRlcId(Integer rlcId) {
		this.rlcId = rlcId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dta_id", nullable = false)
	public DtaDefinicionTarea getDtaDefinicionTarea() {
		return this.dtaDefinicionTarea;
	}

	public void setDtaDefinicionTarea(DtaDefinicionTarea dtaDefinicionTarea) {
		this.dtaDefinicionTarea = dtaDefinicionTarea;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "per_id", nullable = false)
	public PerPerfil getPerPerfil() {
		return this.perPerfil;
	}

	public void setPerPerfil(PerPerfil perPerfil) {
		this.perPerfil = perPerfil;
	}

}