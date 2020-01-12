package cl.servel.gasto.entity;
//Generated 05-02-2019 10:41:18 by Hibernate Tools 5.2.11.Final

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
 * TpoDocGasto generated by hbm2java
 */
@Entity
@Table(name = "tpo_doc_gasto")
public class TpoDocGasto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tpoDocId;
	private EveEventoEleccionario eveEventoEleccionario;
	private TpoRendicion tpoRendicion;
	private String tpoDocCodigo;
	private String tpoDocNombre;

	public TpoDocGasto() {
	}

	public TpoDocGasto(EveEventoEleccionario eveEventoEleccionario, TpoRendicion tpoRendicion, String tpoDocCodigo) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoRendicion = tpoRendicion;
		this.tpoDocCodigo = tpoDocCodigo;
	}

	public TpoDocGasto(EveEventoEleccionario eveEventoEleccionario, TpoRendicion tpoRendicion, String tpoDocCodigo,
			String tpoDocNombre) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.tpoRendicion = tpoRendicion;
		this.tpoDocCodigo = tpoDocCodigo;
		this.tpoDocNombre = tpoDocNombre;
	}

	@SequenceGenerator(name = "TpoDocGastoIdGenerator", sequenceName = "tpo_doc_gasto_tpo_doc_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TpoDocGastoIdGenerator")

	@Column(name = "tpo_doc_id", unique = true, nullable = false)
	public Integer getTpoDocId() {
		return this.tpoDocId;
	}

	public void setTpoDocId(Integer tpoDocId) {
		this.tpoDocId = tpoDocId;
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

	@Column(name = "tpo_doc_codigo", nullable = false, length = 5)
	public String getTpoDocCodigo() {
		return this.tpoDocCodigo;
	}

	public void setTpoDocCodigo(String tpoDocCodigo) {
		this.tpoDocCodigo = tpoDocCodigo;
	}

	@Column(name = "tpo_doc_nombre", length = 100)
	public String getTpoDocNombre() {
		return this.tpoDocNombre;
	}

	public void setTpoDocNombre(String tpoDocNombre) {
		this.tpoDocNombre = tpoDocNombre;
	}

}
