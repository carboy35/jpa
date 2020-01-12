package cl.servel.gasto.entity;
// Generated 10-10-2018 0:44:18 by Hibernate Tools 5.2.11.Final

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
 * PacPacto generated by hbm2java
 */
@Entity
@Table(name = "pac_pacto")
public class PacPacto implements java.io.Serializable {

	private Integer pacId;
	private EleEleccion eleEleccion;
	private String pacNombre;
	private String pacCodigoOrigen;
	private String pacSigla;
	private String lista;
	private Boolean pacEliminado;
	private Date pacCreated;
	private Date pacModified;
	private Set<SpaPacto> spaPactos = new HashSet<SpaPacto>(0);
	private Set<CanCandidato> canCandidatos = new HashSet<CanCandidato>(0);
	private Set<ParPartido> parPartidos = new HashSet<ParPartido>(0);

	public PacPacto() {
	}

	public PacPacto(EleEleccion eleEleccion) {
		this.eleEleccion = eleEleccion;
	}

	public PacPacto(EleEleccion eleEleccion, String pacNombre, String pacCodigoOrigen, String pacSigla, String lista,
			Boolean pacEliminado, Date pacCreated, Date pacModified, Set<SpaPacto> spaPactos,
			Set<CanCandidato> canCandidatos, Set<ParPartido> parPartidos) {
		this.eleEleccion = eleEleccion;
		this.pacNombre = pacNombre;
		this.pacCodigoOrigen = pacCodigoOrigen;
		this.pacSigla = pacSigla;
		this.lista = lista;
		this.pacEliminado = pacEliminado;
		this.pacCreated = pacCreated;
		this.pacModified = pacModified;
		this.spaPactos = spaPactos;
		this.canCandidatos = canCandidatos;
		this.parPartidos = parPartidos;
	}

	@SequenceGenerator(name = "cl.servel.gasto.entity.PacPactoIdGenerator", sequenceName = "pac_pacto_pac_id_seq", initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.entity.PacPactoIdGenerator")

	@Column(name = "pac_id", unique = true, nullable = false)
	public Integer getPacId() {
		return this.pacId;
	}

	public void setPacId(Integer pacId) {
		this.pacId = pacId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ele_id", nullable = false)
	public EleEleccion getEleEleccion() {
		return this.eleEleccion;
	}

	public void setEleEleccion(EleEleccion eleEleccion) {
		this.eleEleccion = eleEleccion;
	}

	@Column(name = "pac_nombre", length = 50)
	public String getPacNombre() {
		return this.pacNombre;
	}

	public void setPacNombre(String pacNombre) {
		this.pacNombre = pacNombre;
	}

	@Column(name = "pac_codigo_origen", length = 5)
	public String getPacCodigoOrigen() {
		return this.pacCodigoOrigen;
	}

	public void setPacCodigoOrigen(String pacCodigoOrigen) {
		this.pacCodigoOrigen = pacCodigoOrigen;
	}

	@Column(name = "pac_sigla", length = 5)
	public String getPacSigla() {
		return this.pacSigla;
	}

	public void setPacSigla(String pacSigla) {
		this.pacSigla = pacSigla;
	}

	@Column(name = "lista", length = 5)
	public String getLista() {
		return this.lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	@Column(name = "pac_eliminado")
	public Boolean getPacEliminado() {
		return this.pacEliminado;
	}

	public void setPacEliminado(Boolean pacEliminado) {
		this.pacEliminado = pacEliminado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pac_created", length = 13)
	public Date getPacCreated() {
		return this.pacCreated;
	}

	public void setPacCreated(Date pacCreated) {
		this.pacCreated = pacCreated;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pac_modified", length = 13)
	public Date getPacModified() {
		return this.pacModified;
	}

	public void setPacModified(Date pacModified) {
		this.pacModified = pacModified;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pacPacto")
	public Set<SpaPacto> getSpaPactos() {
		return this.spaPactos;
	}

	public void setSpaPactos(Set<SpaPacto> spaPactos) {
		this.spaPactos = spaPactos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pacPacto")
	public Set<CanCandidato> getCanCandidatos() {
		return this.canCandidatos;
	}

	public void setCanCandidatos(Set<CanCandidato> canCandidatos) {
		this.canCandidatos = canCandidatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pacPacto")
	public Set<ParPartido> getParPartidos() {
		return this.parPartidos;
	}

	public void setParPartidos(Set<ParPartido> parPartidos) {
		this.parPartidos = parPartidos;
	}

}
