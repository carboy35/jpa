package cl.servel.gasto.entity;
// default package
// Generated 06-11-2018 16:46:19 by Hibernate Tools 5.2.11.Final

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
 * TpoNivel generated by hbm2java
 */
@Entity
@Table(name = "tpo_nivel")
public class TpoNivel implements java.io.Serializable {

	private String tpoNivCodigo;
	private String tpoNivDescripcion;
	private Date tpoNivCreated;
	private Date tpoNivModified;
	private Boolean tpoNivEliminado;
	private Set<RlcRelNivel> rlcRelNivelsForTpoNivCodigoHijo = new HashSet<RlcRelNivel>(0);
	private Set<EleEleccion> eleEleccions = new HashSet<EleEleccion>(0);
	private Set<ResResumenCalculos> resResumenCalculoses = new HashSet<ResResumenCalculos>(0);
	private Set<RlcRelNivel> rlcRelNivelsForTpoNivCodigoPadre = new HashSet<RlcRelNivel>(0);
	private Set<SelSubEleccion> selSubEleccions = new HashSet<SelSubEleccion>(0);
	private Set<NivNivel> nivNivels = new HashSet<NivNivel>(0);
	private Set<RlcPartidoNivel> rlcPartidoNivels = new HashSet<RlcPartidoNivel>(0);

	public TpoNivel() {
	}

	public TpoNivel(String tpoNivDescripcion, Date tpoNivCreated, Date tpoNivModified, Boolean tpoNivEliminado,
			Set<RlcRelNivel> rlcRelNivelsForTpoNivCodigoHijo, Set<EleEleccion> eleEleccions,
			Set<ResResumenCalculos> resResumenCalculoses, Set<RlcRelNivel> rlcRelNivelsForTpoNivCodigoPadre,
			Set<SelSubEleccion> selSubEleccions, Set<NivNivel> nivNivels,
			Set<RlcPartidoNivel> rlcPartidoNivels) {
		this.tpoNivDescripcion = tpoNivDescripcion;
		this.tpoNivCreated = tpoNivCreated;
		this.tpoNivModified = tpoNivModified;
		this.tpoNivEliminado = tpoNivEliminado;
		this.rlcRelNivelsForTpoNivCodigoHijo = rlcRelNivelsForTpoNivCodigoHijo;
		this.eleEleccions = eleEleccions;
		this.resResumenCalculoses = resResumenCalculoses;
		this.rlcRelNivelsForTpoNivCodigoPadre = rlcRelNivelsForTpoNivCodigoPadre;
		this.selSubEleccions = selSubEleccions;
		this.nivNivels = nivNivels;
		this.rlcPartidoNivels = rlcPartidoNivels;
	}

	
	@Id
	@Column(name = "tpo_niv_codigo", unique = true, nullable = false, length = 4)
	public String getTpoNivCodigo() {
		return this.tpoNivCodigo;
	}
	
	
	

	public void setTpoNivCodigo(String tpoNivCodigo) {
		this.tpoNivCodigo = tpoNivCodigo;
	}

	@Column(name = "tpo_niv_descripcion", length = 50)
	public String getTpoNivDescripcion() {
		return this.tpoNivDescripcion;
	}

	public void setTpoNivDescripcion(String tpoNivDescripcion) {
		this.tpoNivDescripcion = tpoNivDescripcion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "tpo_niv_created", length = 13)
	public Date getTpoNivCreated() {
		return this.tpoNivCreated;
	}

	public void setTpoNivCreated(Date tpoNivCreated) {
		this.tpoNivCreated = tpoNivCreated;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "tpo_niv_modified", length = 13)
	public Date getTpoNivModified() {
		return this.tpoNivModified;
	}

	public void setTpoNivModified(Date tpoNivModified) {
		this.tpoNivModified = tpoNivModified;
	}

	@Column(name = "tpo_niv_eliminado")
	public Boolean getTpoNivEliminado() {
		return this.tpoNivEliminado;
	}

	public void setTpoNivEliminado(Boolean tpoNivEliminado) {
		this.tpoNivEliminado = tpoNivEliminado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivelByTpoNivCodigoHijo")
	public Set<RlcRelNivel> getRlcRelNivelsForTpoNivCodigoHijo() {
		return this.rlcRelNivelsForTpoNivCodigoHijo;
	}

	public void setRlcRelNivelsForTpoNivCodigoHijo(Set<RlcRelNivel> rlcRelNivelsForTpoNivCodigoHijo) {
		this.rlcRelNivelsForTpoNivCodigoHijo = rlcRelNivelsForTpoNivCodigoHijo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivel")
	public Set<EleEleccion> getEleEleccions() {
		return this.eleEleccions;
	}

	public void setEleEleccions(Set<EleEleccion> eleEleccions) {
		this.eleEleccions = eleEleccions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivel")
	public Set<ResResumenCalculos> getResResumenCalculoses() {
		return this.resResumenCalculoses;
	}

	public void setResResumenCalculoses(Set<ResResumenCalculos> resResumenCalculoses) {
		this.resResumenCalculoses = resResumenCalculoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivelByTpoNivCodigoPadre")
	public Set<RlcRelNivel> getRlcRelNivelsForTpoNivCodigoPadre() {
		return this.rlcRelNivelsForTpoNivCodigoPadre;
	}

	public void setRlcRelNivelsForTpoNivCodigoPadre(Set<RlcRelNivel> rlcRelNivelsForTpoNivCodigoPadre) {
		this.rlcRelNivelsForTpoNivCodigoPadre = rlcRelNivelsForTpoNivCodigoPadre;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivel")
	public Set<SelSubEleccion> getSelSubEleccions() {
		return this.selSubEleccions;
	}

	public void setSelSubEleccions(Set<SelSubEleccion> selSubEleccions) {
		this.selSubEleccions = selSubEleccions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivel")
	public Set<NivNivel> getNivNivels() {
		return this.nivNivels;
	}

	public void setNivNivels(Set<NivNivel> nivNivels) {
		this.nivNivels = nivNivels;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoNivel")
	public Set<RlcPartidoNivel> getRlcPartidoNivels() {
		return this.rlcPartidoNivels;
	}

	public void setRlcPartidoNivels(Set<RlcPartidoNivel> rlcPartidoNivels) {
		this.rlcPartidoNivels = rlcPartidoNivels;
	}

}
