package cl.servel.gasto.entity;
// Generated 03-01-2019 18:24:12 by Hibernate Tools 5.2.11.Final

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
 * DefRendicion generated by hbm2java
 */
@Entity
@Table(name = "def_rendicion")
public class DefRendicion implements java.io.Serializable {

	private Integer defId;
	private TpoRendicion tpoRendicion;
	private TpoSeccionRendicion tpoSeccionRendicion;

	public DefRendicion() {
	}

	public DefRendicion(TpoRendicion tpoRendicion, TpoSeccionRendicion tpoSeccionRendicion) {
		this.tpoRendicion = tpoRendicion;
		this.tpoSeccionRendicion = tpoSeccionRendicion;
	}

	@SequenceGenerator(name = "DefRendicionIdGenerator", sequenceName = "def_rendicion_def_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DefRendicionIdGenerator")

	@Column(name = "def_id", unique = true, nullable = false)
	public Integer getDefId() {
		return this.defId;
	}

	public void setDefId(Integer defId) {
		this.defId = defId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tpo_rendicion_id", nullable = false)
	public TpoRendicion getTpoRendicion() {
		return this.tpoRendicion;
	}

	public void setTpoRendicion(TpoRendicion tpoRendicion) {
		this.tpoRendicion = tpoRendicion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tpo_seccion_rendicion_id", nullable = false)
	public TpoSeccionRendicion getTpoSeccionRendicion() {
		return this.tpoSeccionRendicion;
	}

	public void setTpoSeccionRendicion(TpoSeccionRendicion tpoSeccionRendicion) {
		this.tpoSeccionRendicion = tpoSeccionRendicion;
	}

}
