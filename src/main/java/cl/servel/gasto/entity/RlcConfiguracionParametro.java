package cl.servel.gasto.entity;
// Generated 04-10-2018 19:09:57 by Hibernate Tools 5.2.11.Final

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
 * RlcConfiguracionParametro generated by hbm2java
 */
@Entity
@Table(name = "rlc_configuracion_parametro")
public class RlcConfiguracionParametro implements java.io.Serializable {

	private Integer rlcId;
	private ConConfiguracion conConfiguracion;
	private PamParametro pamParametro;

	public RlcConfiguracionParametro() {
	}

	public RlcConfiguracionParametro(ConConfiguracion conConfiguracion, PamParametro pamParametro) {
		this.conConfiguracion = conConfiguracion;
		this.pamParametro = pamParametro;
	}

	@SequenceGenerator(name = "RlcConfiguracionParametroIdGenerator", sequenceName = "rlc_configuracion_parametro_rlc_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "RlcConfiguracionParametroIdGenerator")

	@Column(name = "rlc_id", unique = true, nullable = false)
	public Integer getRlcId() {
		return this.rlcId;
	}

	public void setRlcId(Integer rlcId) {
		this.rlcId = rlcId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "con_id", nullable = false)
	public ConConfiguracion getConConfiguracion() {
		return this.conConfiguracion;
	}

	public void setConConfiguracion(ConConfiguracion conConfiguracion) {
		this.conConfiguracion = conConfiguracion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pam_id", nullable = false)
	public PamParametro getPamParametro() {
		return this.pamParametro;
	}

	public void setPamParametro(PamParametro pamParametro) {
		this.pamParametro = pamParametro;
	}

}
