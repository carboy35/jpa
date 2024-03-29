package cl.servel.gasto.entity;
// Generated 09-04-2019 10:17:53 by Hibernate Tools 5.2.12.Final

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

/**
 * TpoRendicion generated by hbm2java
 */
@Entity
@Table(name = "tpo_rendicion")
public class TpoRendicion implements java.io.Serializable {

	private Integer tpoRendicionId;
	private EveEventoEleccionario eveEventoEleccionario;
	private String tpoNombre;
	private String tpoCodigo;
	private Set<TpoDocGasto> tpoDocGastos = new HashSet<TpoDocGasto>(0);
	private Set<TpoConceptoCtaGasto> tpoConceptoCtaGastos = new HashSet<TpoConceptoCtaGasto>(0);
	private Set<RenRendicion> renRendicions = new HashSet<RenRendicion>(0);
	private Set<DefRendicion> defRendicions = new HashSet<DefRendicion>(0);
	private Set<TpoCtaGasto> tpoCtaGastos = new HashSet<TpoCtaGasto>(0);
	private Set<TpoReembolso> tpoReembolsos = new HashSet<TpoReembolso>(0);

	public TpoRendicion() {
	}

	public TpoRendicion(EveEventoEleccionario eveEventoEleccionario) {
		this.eveEventoEleccionario = eveEventoEleccionario;
	}

	public TpoRendicion(EveEventoEleccionario eveEventoEleccionario, String tpoNombre, String tpoCodigo,
			Set<TpoDocGasto> tpoDocGastos, Set<TpoConceptoCtaGasto> tpoConceptoCtaGastos,
			Set<RenRendicion> renRendicions, Set<DefRendicion> defRendicions, Set<TpoCtaGasto> tpoCtaGastos,
			Set<TpoReembolso> tpoReembolsos) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoNombre = tpoNombre;
		this.tpoCodigo = tpoCodigo;
		this.tpoDocGastos = tpoDocGastos;
		this.tpoConceptoCtaGastos = tpoConceptoCtaGastos;
		this.renRendicions = renRendicions;
		this.defRendicions = defRendicions;
		this.tpoCtaGastos = tpoCtaGastos;
		this.tpoReembolsos = tpoReembolsos;
	}

	@SequenceGenerator(name = "TpoRendicionIdGenerator", sequenceName = "tpo_rendicion_tpo_rendicion_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TpoRendicionIdGenerator")

	@Column(name = "tpo_rendicion_id", unique = true, nullable = false)
	public Integer getTpoRendicionId() {
		return this.tpoRendicionId;
	}

	public void setTpoRendicionId(Integer tpoRendicionId) {
		this.tpoRendicionId = tpoRendicionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eve_id", nullable = false)
	public EveEventoEleccionario getEveEventoEleccionario() {
		return this.eveEventoEleccionario;
	}

	public void setEveEventoEleccionario(EveEventoEleccionario eveEventoEleccionario) {
		this.eveEventoEleccionario = eveEventoEleccionario;
	}

	@Column(name = "tpo_nombre", length = 60)
	public String getTpoNombre() {
		return this.tpoNombre;
	}

	public void setTpoNombre(String tpoNombre) {
		this.tpoNombre = tpoNombre;
	}

	@Column(name = "tpo_codigo", length = 15)
	public String getTpoCodigo() {
		return this.tpoCodigo;
	}

	public void setTpoCodigo(String tpoCodigo) {
		this.tpoCodigo = tpoCodigo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoRendicion")
	public Set<TpoDocGasto> getTpoDocGastos() {
		return this.tpoDocGastos;
	}

	public void setTpoDocGastos(Set<TpoDocGasto> tpoDocGastos) {
		this.tpoDocGastos = tpoDocGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoRendicion")
	public Set<TpoConceptoCtaGasto> getTpoConceptoCtaGastos() {
		return this.tpoConceptoCtaGastos;
	}

	public void setTpoConceptoCtaGastos(Set<TpoConceptoCtaGasto> tpoConceptoCtaGastos) {
		this.tpoConceptoCtaGastos = tpoConceptoCtaGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoRendicion")
	public Set<RenRendicion> getRenRendicions() {
		return this.renRendicions;
	}

	public void setRenRendicions(Set<RenRendicion> renRendicions) {
		this.renRendicions = renRendicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoRendicion")
	public Set<DefRendicion> getDefRendicions() {
		return this.defRendicions;
	}

	public void setDefRendicions(Set<DefRendicion> defRendicions) {
		this.defRendicions = defRendicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoRendicion")
	public Set<TpoCtaGasto> getTpoCtaGastos() {
		return this.tpoCtaGastos;
	}

	public void setTpoCtaGastos(Set<TpoCtaGasto> tpoCtaGastos) {
		this.tpoCtaGastos = tpoCtaGastos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoRendicion")
	public Set<TpoReembolso> getTpoReembolsos() {
		return this.tpoReembolsos;
	}

	public void setTpoReembolsos(Set<TpoReembolso> tpoReembolsos) {
		this.tpoReembolsos = tpoReembolsos;
	}

}
