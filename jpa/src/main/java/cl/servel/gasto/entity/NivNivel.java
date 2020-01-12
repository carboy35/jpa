package cl.servel.gasto.entity;
// Generated 03-12-2018 11:52:46 by Hibernate Tools 5.2.11.Final

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
 * NivNivel generated by hbm2java
 */
@Entity
@Table(name = "niv_nivel")
public class NivNivel implements java.io.Serializable {

	private Integer nivId;
	private EveEventoEleccionario eveEventoEleccionario;
	private TpoNivel tpoNivel;
	private String nivDescripcion;
	private Integer nivOrden;
	private String codigoOrigen;
	private Date nivCreated;
	private Boolean nivEliminado;
	private Date nivModified;
	private Set<LimLimiteGasto> limLimiteGastosForNivHijoId = new HashSet<LimLimiteGasto>(0);
	private Set<ResResumenCalculos> resResumenCalculoses = new HashSet<ResResumenCalculos>(0);
	private Set<LimLimiteGasto> limLimiteGastosForNivPadreId = new HashSet<LimLimiteGasto>(0);
	private Set<RlcRelNivel> rlcRelNivelsForNivIdPadre = new HashSet<RlcRelNivel>(0);
	private Set<SelSubEleccion> selSubEleccions = new HashSet<SelSubEleccion>(0);
	private Set<RlcRelNivel> rlcRelNivelsForNivIdHijo = new HashSet<RlcRelNivel>(0);
	private Set<RlcPartidoNivel> rlcPartidoNivels = new HashSet<RlcPartidoNivel>(0);

	public NivNivel() {
	}

	public NivNivel(EveEventoEleccionario eveEventoEleccionario, TpoNivel tpoNivel) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoNivel = tpoNivel;
	}

	public NivNivel(EveEventoEleccionario eveEventoEleccionario, TpoNivel tpoNivel, String nivDescripcion,
			Integer nivOrden, String codigoOrigen, Date nivCreated, Boolean nivEliminado, Date nivModified,
			Set<LimLimiteGasto> limLimiteGastosForNivHijoId, Set<ResResumenCalculos> resResumenCalculoses,
			Set<LimLimiteGasto> limLimiteGastosForNivPadreId, Set<RlcRelNivel> rlcRelNivelsForNivIdPadre,
			Set<SelSubEleccion> selSubEleccions, Set<RlcRelNivel> rlcRelNivelsForNivIdHijo,
			Set<RlcPartidoNivel> rlcPartidoNivels) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoNivel = tpoNivel;
		this.nivDescripcion = nivDescripcion;
		this.nivOrden = nivOrden;
		this.codigoOrigen = codigoOrigen;
		this.nivCreated = nivCreated;
		this.nivEliminado = nivEliminado;
		this.nivModified = nivModified;
		this.limLimiteGastosForNivHijoId = limLimiteGastosForNivHijoId;
		this.resResumenCalculoses = resResumenCalculoses;
		this.limLimiteGastosForNivPadreId = limLimiteGastosForNivPadreId;
		this.rlcRelNivelsForNivIdPadre = rlcRelNivelsForNivIdPadre;
		this.selSubEleccions = selSubEleccions;
		this.rlcRelNivelsForNivIdHijo = rlcRelNivelsForNivIdHijo;
		this.rlcPartidoNivels = rlcPartidoNivels;
	}

	@SequenceGenerator(name = "NivNivelIdGenerator", sequenceName = "niv_nivel_niv_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "NivNivelIdGenerator")

	@Column(name = "niv_id", unique = true, nullable = false)
	public Integer getNivId() {
		return this.nivId;
	}

	public void setNivId(Integer nivId) {
		this.nivId = nivId;
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
	@JoinColumn(name = "tpo_niv_codigo", nullable = false)
	public TpoNivel getTpoNivel() {
		return this.tpoNivel;
	}

	public void setTpoNivel(TpoNivel tpoNivel) {
		this.tpoNivel = tpoNivel;
	}

	@Column(name = "niv_descripcion", length = 100)
	public String getNivDescripcion() {
		return this.nivDescripcion;
	}

	public void setNivDescripcion(String nivDescripcion) {
		this.nivDescripcion = nivDescripcion;
	}

	@Column(name = "niv_orden")
	public Integer getNivOrden() {
		return this.nivOrden;
	}

	public void setNivOrden(Integer nivOrden) {
		this.nivOrden = nivOrden;
	}

	@Column(name = "codigo_origen", length = 30)
	public String getCodigoOrigen() {
		return this.codigoOrigen;
	}

	public void setCodigoOrigen(String codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "niv_created", length = 13)
	public Date getNivCreated() {
		return this.nivCreated;
	}

	public void setNivCreated(Date nivCreated) {
		this.nivCreated = nivCreated;
	}

	@Column(name = "niv_eliminado")
	public Boolean getNivEliminado() {
		return this.nivEliminado;
	}

	public void setNivEliminado(Boolean nivEliminado) {
		this.nivEliminado = nivEliminado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "niv_modified", length = 13)
	public Date getNivModified() {
		return this.nivModified;
	}

	public void setNivModified(Date nivModified) {
		this.nivModified = nivModified;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivelByNivHijoId")
	public Set<LimLimiteGasto> getLimLimiteGastosForNivHijoId() {
		return this.limLimiteGastosForNivHijoId;
	}

	public void setLimLimiteGastosForNivHijoId(Set<LimLimiteGasto> limLimiteGastosForNivHijoId) {
		this.limLimiteGastosForNivHijoId = limLimiteGastosForNivHijoId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivel")
	public Set<ResResumenCalculos> getResResumenCalculoses() {
		return this.resResumenCalculoses;
	}

	public void setResResumenCalculoses(Set<ResResumenCalculos> resResumenCalculoses) {
		this.resResumenCalculoses = resResumenCalculoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivelByNivPadreId")
	public Set<LimLimiteGasto> getLimLimiteGastosForNivPadreId() {
		return this.limLimiteGastosForNivPadreId;
	}

	public void setLimLimiteGastosForNivPadreId(Set<LimLimiteGasto> limLimiteGastosForNivPadreId) {
		this.limLimiteGastosForNivPadreId = limLimiteGastosForNivPadreId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivelByNivIdPadre")
	public Set<RlcRelNivel> getRlcRelNivelsForNivIdPadre() {
		return this.rlcRelNivelsForNivIdPadre;
	}

	public void setRlcRelNivelsForNivIdPadre(Set<RlcRelNivel> rlcRelNivelsForNivIdPadre) {
		this.rlcRelNivelsForNivIdPadre = rlcRelNivelsForNivIdPadre;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivel")
	public Set<SelSubEleccion> getSelSubEleccions() {
		return this.selSubEleccions;
	}

	public void setSelSubEleccions(Set<SelSubEleccion> selSubEleccions) {
		this.selSubEleccions = selSubEleccions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivelByNivIdHijo")
	public Set<RlcRelNivel> getRlcRelNivelsForNivIdHijo() {
		return this.rlcRelNivelsForNivIdHijo;
	}

	public void setRlcRelNivelsForNivIdHijo(Set<RlcRelNivel> rlcRelNivelsForNivIdHijo) {
		this.rlcRelNivelsForNivIdHijo = rlcRelNivelsForNivIdHijo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivNivel")
	public Set<RlcPartidoNivel> getRlcPartidoNivels() {
		return this.rlcPartidoNivels;
	}

	public void setRlcPartidoNivels(Set<RlcPartidoNivel> rlcPartidoNivels) {
		this.rlcPartidoNivels = rlcPartidoNivels;
	}

}
