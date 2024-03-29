package cl.servel.gasto.entity;
// Generated 02-01-2019 19:29:27 by Hibernate Tools 5.2.11.Final

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

/**
 * TpoSeccionRendicion generated by hbm2java
 */
@Entity
@Table(name = "tpo_seccion_rendicion")
public class TpoSeccionRendicion implements java.io.Serializable {

	private Integer tpoSeccionRendicionId;
	private String tpoNombreSeccion;
	private String tpoCodigoSeccion;
	private Set<DefRendicion> defRendicions = new HashSet<DefRendicion>(0);
	private Set<DetDetalleRendicion> detDetalleRendicions = new HashSet<DetDetalleRendicion>(0);

	public TpoSeccionRendicion() {
	}

	public TpoSeccionRendicion(String tpoNombreSeccion, String tpoCodigoSeccion, Set<DefRendicion> defRendicions,
			Set<DetDetalleRendicion> detDetalleRendicions) {
		this.tpoNombreSeccion = tpoNombreSeccion;
		this.tpoCodigoSeccion = tpoCodigoSeccion;
		this.defRendicions = defRendicions;
		this.detDetalleRendicions = detDetalleRendicions;
	}

	@SequenceGenerator(name = "TpoSeccionRendicionIdGenerator", sequenceName = "tpo_seccion_rendicion_tpo_seccion_rendicion_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "TpoSeccionRendicionIdGenerator")

	@Column(name = "tpo_seccion_rendicion_id", unique = true, nullable = false)
	public Integer getTpoSeccionRendicionId() {
		return this.tpoSeccionRendicionId;
	}

	public void setTpoSeccionRendicionId(Integer tpoSeccionRendicionId) {
		this.tpoSeccionRendicionId = tpoSeccionRendicionId;
	}

	@Column(name = "tpo_nombre_seccion", length = 60)
	public String getTpoNombreSeccion() {
		return this.tpoNombreSeccion;
	}

	public void setTpoNombreSeccion(String tpoNombreSeccion) {
		this.tpoNombreSeccion = tpoNombreSeccion;
	}

	@Column(name = "tpo_codigo_seccion", length = 15)
	public String getTpoCodigoSeccion() {
		return this.tpoCodigoSeccion;
	}

	public void setTpoCodigoSeccion(String tpoCodigoSeccion) {
		this.tpoCodigoSeccion = tpoCodigoSeccion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoSeccionRendicion")
	public Set<DefRendicion> getDefRendicions() {
		return this.defRendicions;
	}

	public void setDefRendicions(Set<DefRendicion> defRendicions) {
		this.defRendicions = defRendicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tpoSeccionRendicion")
	public Set<DetDetalleRendicion> getDetDetalleRendicions() {
		return this.detDetalleRendicions;
	}

	public void setDetDetalleRendicions(Set<DetDetalleRendicion> detDetalleRendicions) {
		this.detDetalleRendicions = detDetalleRendicions;
	}

}
