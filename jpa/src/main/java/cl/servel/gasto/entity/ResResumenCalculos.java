package cl.servel.gasto.entity;
// default package
// Generated 06-11-2018 16:46:19 by Hibernate Tools 5.2.11.Final

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
 * ResResumenCalculos generated by hbm2java
 */
@Entity
@Table(name = "res_resumen_calculos")
public class ResResumenCalculos implements java.io.Serializable {

	private Integer resId;
	private EleEleccion eleEleccion;
	private NivNivel nivNivel;
	private ParPartido parPartido;
	private TpoNivel tpoNivel;
	private Double resTotalVotos;
	private Double resMontoFinanciamiento;
	private int eleIdAnterior;
	private boolean parNuevo;
	private Integer canIndepId;
	private Integer parIdAnt;
	private Integer nivIdAnt;

	public ResResumenCalculos() {
	}

	public ResResumenCalculos(EleEleccion eleEleccion, TpoNivel tpoNivel, int eleIdAnterior,
			boolean parNuevo) {
		this.eleEleccion = eleEleccion;
		this.tpoNivel = tpoNivel;
		this.eleIdAnterior = eleIdAnterior;
		this.parNuevo = parNuevo;
	}

	public ResResumenCalculos(EleEleccion eleEleccion, NivNivel nivNivel, ParPartido parPartido, TpoNivel tpoNivel,
			Double resTotalVotos, Double resMontoFinanciamiento, int eleIdAnterior, boolean parNuevo,
			Integer canIndepId, Integer parIdAnt, Integer nivIdAnt) {
		this.eleEleccion = eleEleccion;
		this.nivNivel = nivNivel;
		this.parPartido = parPartido;
		this.tpoNivel = tpoNivel;
		this.resTotalVotos = resTotalVotos;
		this.resMontoFinanciamiento = resMontoFinanciamiento;
		this.eleIdAnterior = eleIdAnterior;
		this.parNuevo = parNuevo;
		this.canIndepId = canIndepId;
		this.parIdAnt = parIdAnt;
		this.nivIdAnt = nivIdAnt;
	}

	@SequenceGenerator(name = "ResResumenCalculosIdGenerator", sequenceName = "res_resumen_calculos_res_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "ResResumenCalculosIdGenerator")

	@Column(name = "res_id", unique = true, nullable = false)
	public Integer getResId() {
		return this.resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ele_id", nullable = false)
	public EleEleccion getEleEleccion() {
		return this.eleEleccion;
	}

	public void setEleEleccion(EleEleccion eleEleccion) {
		this.eleEleccion = eleEleccion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "niv_id")
	public NivNivel getNivNivel() {
		return this.nivNivel;
	}

	public void setNivNivel(NivNivel nivNivel) {
		this.nivNivel = nivNivel;
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
	@JoinColumn(name = "tpo_niv_codigo")
	public TpoNivel getTpoNivel() {
		return this.tpoNivel;
	}

	public void setTpoNivel(TpoNivel tpoNivel) {
		this.tpoNivel = tpoNivel;
	}

	@Column(name = "res_total_votos", precision = 17, scale = 17)
	public Double getResTotalVotos() {
		return this.resTotalVotos;
	}

	public void setResTotalVotos(Double resTotalVotos) {
		this.resTotalVotos = resTotalVotos;
	}

	@Column(name = "res_monto_financiamiento", precision = 17, scale = 17)
	public Double getResMontoFinanciamiento() {
		return this.resMontoFinanciamiento;
	}

	public void setResMontoFinanciamiento(Double resMontoFinanciamiento) {
		this.resMontoFinanciamiento = resMontoFinanciamiento;
	}

		
	@Column(name = "ele_id_anterior")
	public Integer getEleIdAnterior() {
		return eleIdAnterior;
	}

	public void setEleIdAnterior(Integer eleIdAnterior) {
		this.eleIdAnterior = eleIdAnterior;
	}
	
	@Column(name = "can_indep_id")
	public Integer getCanIndepId() {
		return canIndepId;
	}

	public void setCanIndepId(Integer canIndepId) {
		this.canIndepId = canIndepId;
	}

	@Column(name = "par_nuevo")
	public boolean getParNuevo() {
		return parNuevo;
	}

	public void setParNuevo(boolean parNuevo) {
		this.parNuevo = parNuevo;
	}
	
	@Column(name = "par_id_ant")
	public Integer getParIdAnt() {
		return parIdAnt;
	}

	public void setParIdAnt(Integer parIdAnt) {
		this.parIdAnt = parIdAnt;
	}

	@Column(name = "niv_id_ant")
	public Integer getNivIdAnt() {
		return nivIdAnt;
	}

	public void setNivIdAnt(Integer nivIdAnt) {
		this.nivIdAnt = nivIdAnt;
	}

}
