package cl.servel.gasto.entity;
// Generated 21-06-2019 12:42:33 by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TpoEleccion generated by hbm2java
 */
@Entity
@Table(name = "tpo_eleccion")
public class TpoEleccion implements java.io.Serializable {

	private Integer tpoEleccionId;
	private String tpoEleNombre;
	private Boolean tpoEleEliminado;
	private Date created;
	private Date modified;
	private Integer tpoEleOrden;
	private String tpoCodigoOrigen;
	private Set<AsgAsignacionCargaTarea> asgAsignacionCargaTareas = new HashSet<AsgAsignacionCargaTarea>(0);
	private Set<ConConfiguracion> conConfiguracions = new HashSet<ConConfiguracion>(0);
	private Set<LimLimiteGasto> limLimiteGastos = new HashSet<LimLimiteGasto>(0);
	private Set<EleEleccion> eleEleccions = new HashSet<EleEleccion>(0);

	public TpoEleccion() {
	}

	public TpoEleccion(String tpoEleNombre, Boolean tpoEleEliminado, Date created, Date modified, Integer tpoEleOrden,
			String tpoCodigoOrigen, Set<AsgAsignacionCargaTarea> asgAsignacionCargaTareas,
			Set<ConConfiguracion> conConfiguracions, Set<LimLimiteGasto> limLimiteGastos,
			Set<EleEleccion> eleEleccions) {
		this.tpoEleNombre = tpoEleNombre;
		this.tpoEleEliminado = tpoEleEliminado;
		this.created = created;
		this.modified = modified;
		this.tpoEleOrden = tpoEleOrden;
		this.tpoCodigoOrigen = tpoCodigoOrigen;
		this.asgAsignacionCargaTareas = asgAsignacionCargaTareas;
		this.conConfiguracions = conConfiguracions;
		this.limLimiteGastos = limLimiteGastos;
		this.eleEleccions = eleEleccions;
	}

	@Id
	@Column(name = "tpo_eleccion_id", unique = true, nullable = false)
	public Integer getTpoEleccionId() {
		return this.tpoEleccionId;
	}

	public void setTpoEleccionId(Integer tpoEleccionId) {
		this.tpoEleccionId = tpoEleccionId;
	}

	@Column(name = "tpo_ele_nombre", length = 50)
	public String getTpoEleNombre() {
		return this.tpoEleNombre;
	}

	public void setTpoEleNombre(String tpoEleNombre) {
		this.tpoEleNombre = tpoEleNombre;
	}

	@Column(name = "tpo_ele_eliminado")
	public Boolean getTpoEleEliminado() {
		return this.tpoEleEliminado;
	}

	public void setTpoEleEliminado(Boolean tpoEleEliminado) {
		this.tpoEleEliminado = tpoEleEliminado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created", length = 13)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified", length = 13)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(name = "tpo_ele_orden")
	public Integer getTpoEleOrden() {
		return this.tpoEleOrden;
	}

	public void setTpoEleOrden(Integer tpoEleOrden) {
		this.tpoEleOrden = tpoEleOrden;
	}

	@Column(name = "tpo_codigo_origen", length = 15)
	public String getTpoCodigoOrigen() {
		return this.tpoCodigoOrigen;
	}

	public void setTpoCodigoOrigen(String tpoCodigoOrigen) {
		this.tpoCodigoOrigen = tpoCodigoOrigen;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoEleccion")
	public Set<AsgAsignacionCargaTarea> getAsgAsignacionCargaTareas() {
		return this.asgAsignacionCargaTareas;
	}

	public void setAsgAsignacionCargaTareas(Set<AsgAsignacionCargaTarea> asgAsignacionCargaTareas) {
		this.asgAsignacionCargaTareas = asgAsignacionCargaTareas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoEleccion")
	public Set<ConConfiguracion> getConConfiguracions() {
		return this.conConfiguracions;
	}

	public void setConConfiguracions(Set<ConConfiguracion> conConfiguracions) {
		this.conConfiguracions = conConfiguracions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoEleccion")
	public Set<LimLimiteGasto> getLimLimiteGastos() {
		return this.limLimiteGastos;
	}

	public void setLimLimiteGastos(Set<LimLimiteGasto> limLimiteGastos) {
		this.limLimiteGastos = limLimiteGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoEleccion")
	public Set<EleEleccion> getEleEleccions() {
		return this.eleEleccions;
	}

	public void setEleEleccions(Set<EleEleccion> eleEleccions) {
		this.eleEleccions = eleEleccions;
	}

}
