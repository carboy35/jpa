package cl.servel.gasto.entity;
// Generated 16-01-2019 12:04:13 by Hibernate Tools 5.2.11.Final

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TpoReembolso generated by hbm2java
 */
@Entity
@Table(name = "tpo_reembolso")
public class TpoReembolso implements java.io.Serializable {

	private Integer tpoReembolsoId;
	private EveEventoEleccionario eveEventoEleccionario;
	private TpoRendicion tpoRendicion;
	private String tpoReembolsoCodigo;
	private String tpoReembolsoNombre;

	public TpoReembolso() {
	}

	public TpoReembolso(EveEventoEleccionario eveEventoEleccionario,TpoRendicion tpoRendicion, String tpoReembolsoCodigo) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoRendicion = tpoRendicion;
		this.tpoReembolsoCodigo = tpoReembolsoCodigo;
	}

	public TpoReembolso(EveEventoEleccionario eveEventoEleccionario,TpoRendicion tpoRendicion, String tpoReembolsoCodigo, String tpoReembolsoNombre) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoRendicion = tpoRendicion;
		this.tpoReembolsoCodigo = tpoReembolsoCodigo;
		this.tpoReembolsoNombre = tpoReembolsoNombre;
	}

	@SequenceGenerator(name = "TpoReembolsoIdGenerator", sequenceName = "tpo_reembolso_tpo_reembolso_id_seq", initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TpoReembolsoIdGenerator")

	@Column(name = "tpo_reembolso_id", unique = true, nullable = false)
	public Integer getTpoReembolsoId() {
		return this.tpoReembolsoId;
	}

	public void setTpoReembolsoId(Integer tpoReembolsoId) {
		this.tpoReembolsoId = tpoReembolsoId;
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
	@JoinColumn(name = "tpo_rendicion_id", nullable = false)
	public TpoRendicion getTpoRendicion() {
		return this.tpoRendicion;
	}

	public void setTpoRendicion(TpoRendicion tpoRendicion) {
		this.tpoRendicion = tpoRendicion;
	}

	@Column(name = "tpo_reembolso_codigo", nullable = false, length = 5)
	public String getTpoReembolsoCodigo() {
		return this.tpoReembolsoCodigo;
	}

	public void setTpoReembolsoCodigo(String tpoReembolsoCodigo) {
		this.tpoReembolsoCodigo = tpoReembolsoCodigo;
	}

	@Column(name = "tpo_reembolso_nombre", length = 100)
	public String getTpoReembolsoNombre() {
		return this.tpoReembolsoNombre;
	}

	public void setTpoReembolsoNombre(String tpoReembolsoNombre) {
		this.tpoReembolsoNombre = tpoReembolsoNombre;
	}

}