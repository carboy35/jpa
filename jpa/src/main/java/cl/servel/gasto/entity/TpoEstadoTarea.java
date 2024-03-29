package cl.servel.gasto.entity;
// Generated 21-03-2019 14:36:24 by Hibernate Tools 5.2.11.Final

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
 * TpoEstadoTarea generated by hbm2java
 */
@Entity
@Table(name = "tpo_estado_tarea")
public class TpoEstadoTarea implements java.io.Serializable {

	private Integer tpoTareaId;
	private String tpoCodigoEstado;
	private String tpoNombreEstado;
	private String tpoTipoEstado;
	private Set<DtaDefinicionTarea> dtaDefinicionTareas = new HashSet<DtaDefinicionTarea>(0);
	private Set<IntInstanciaTarea> intInstanciaTareas = new HashSet<IntInstanciaTarea>(0);

	public TpoEstadoTarea() {
	}

	public TpoEstadoTarea(String tpoTipoEstado) {
		this.tpoTipoEstado = tpoTipoEstado;
	}

	public TpoEstadoTarea(String tpoCodigoEstado, String tpoNombreEstado, String tpoTipoEstado, Set<DtaDefinicionTarea> dtaDefinicionTareas, Set<IntInstanciaTarea> intInstanciaTareas) {
		this.tpoCodigoEstado = tpoCodigoEstado;
		this.tpoNombreEstado = tpoNombreEstado;
		this.tpoTipoEstado = tpoTipoEstado;
		this.dtaDefinicionTareas = dtaDefinicionTareas;
		this.intInstanciaTareas = intInstanciaTareas;
	}

	@SequenceGenerator(name = "TpoEstadoTareaIdGenerator", sequenceName = "tpo_estado_tarea_tpo_tarea_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TpoEstadoTareaIdGenerator")

	@Column(name = "tpo_tarea_id", unique = true, nullable = false)
	public Integer getTpoTareaId() {
		return this.tpoTareaId;
	}

	public void setTpoTareaId(Integer tpoTareaId) {
		this.tpoTareaId = tpoTareaId;
	}

	@Column(name = "tpo_codigo_estado", length = 50)
	public String getTpoCodigoEstado() {
		return this.tpoCodigoEstado;
	}

	public void setTpoCodigoEstado(String tpoCodigoEstado) {
		this.tpoCodigoEstado = tpoCodigoEstado;
	}

	@Column(name = "tpo_nombre_estado", length = 50)
	public String getTpoNombreEstado() {
		return this.tpoNombreEstado;
	}

	public void setTpoNombreEstado(String tpoNombreEstado) {
		this.tpoNombreEstado = tpoNombreEstado;
	}

	@Column(name = "tpo_tipo_estado", nullable = false, length = 1)
	public String getTpoTipoEstado() {
		return this.tpoTipoEstado;
	}

	public void setTpoTipoEstado(String tpoTipoEstado) {
		this.tpoTipoEstado = tpoTipoEstado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoEstadoTarea")
	public Set<DtaDefinicionTarea> getDtaDefinicionTareas() {
		return this.dtaDefinicionTareas;
	}

	public void setDtaDefinicionTareas(Set<DtaDefinicionTarea> dtaDefinicionTareas) {
		this.dtaDefinicionTareas = dtaDefinicionTareas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoEstadoTarea")
	public Set<IntInstanciaTarea> getIntInstanciaTareas() {
		return this.intInstanciaTareas;
	}

	public void setIntInstanciaTareas(Set<IntInstanciaTarea> intInstanciaTareas) {
		this.intInstanciaTareas = intInstanciaTareas;
	}

}
