package cl.servel.gasto.entity;
// Generated 04-10-2018 19:09:57 by Hibernate Tools 5.2.11.Final

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BitBitacora generated by hbm2java
 */
@Entity
@Table(name = "bit_bitacora")
public class BitBitacora implements java.io.Serializable {

	private Integer bitId;
	private EveEventoEleccionario eveEventoEleccionario;
	private UsuUsuarios usuUsuarios;
	private String bitEntidadRegistrada;
	private Long idEntidadRegistrada;
	private Date bitFechaRegistro;
	private String bitAccion;
	private String bitAtributoEntidad;
	private String bitValorRegistrado;

	public BitBitacora() {
	}

	public BitBitacora(EveEventoEleccionario eveEventoEleccionario, UsuUsuarios usuUsuarios) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.usuUsuarios = usuUsuarios;
	}

	public BitBitacora(EveEventoEleccionario eveEventoEleccionario, UsuUsuarios usuUsuarios,
		String bitEntidadRegistrada, Long idEntidadRegistrada, Date bitFechaRegistro, String bitAccion,
		String bitAtributoEntidad, String bitValorRegistrado) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.usuUsuarios = usuUsuarios;
		this.bitEntidadRegistrada = bitEntidadRegistrada;
		this.idEntidadRegistrada = idEntidadRegistrada;
		this.bitFechaRegistro = bitFechaRegistro;
		this.bitAccion = bitAccion;
		this.bitAtributoEntidad = bitAtributoEntidad;
		this.bitValorRegistrado = bitValorRegistrado;
	}

	@SequenceGenerator(name = "BitBitacoraIdGenerator", sequenceName = "bit_bitacora_bit_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BitBitacoraIdGenerator")
	@Column(name = "bit_id", unique = true, nullable = false)
	public Integer getBitId() {
		return this.bitId;
	}

	public void setBitId(Integer bitId) {
		this.bitId = bitId;
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
	@JoinColumn(name = "usu_id", nullable = false)
	public UsuUsuarios getUsuUsuarios() {
		return this.usuUsuarios;
	}

	public void setUsuUsuarios(UsuUsuarios usuUsuarios) {
		this.usuUsuarios = usuUsuarios;
	}

	@Column(name = "bit_entidad_registrada", length = 50)
	public String getBitEntidadRegistrada() {
		return this.bitEntidadRegistrada;
	}

	public void setBitEntidadRegistrada(String bitEntidadRegistrada) {
		this.bitEntidadRegistrada = bitEntidadRegistrada;
	}

	@Column(name = "id_entidad_registrada")
	public Long getIdEntidadRegistrada() {
		return this.idEntidadRegistrada;
	}

	public void setIdEntidadRegistrada(Long idEntidadRegistrada) {
		this.idEntidadRegistrada = idEntidadRegistrada;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bit_fecha_registro", length = 29)
	public Date getBitFechaRegistro() {
		return this.bitFechaRegistro;
	}

	public void setBitFechaRegistro(Date bitFechaRegistro) {
		this.bitFechaRegistro = bitFechaRegistro;
	}

	@Column(name = "bit_accion", length = 10)
	public String getBitAccion() {
		return this.bitAccion;
	}

	public void setBitAccion(String bitAccion) {
		this.bitAccion = bitAccion;
	}

	@Column(name = "bit_atributo_entidad", length = 30)
	public String getBitAtributoEntidad() {
		return this.bitAtributoEntidad;
	}

	public void setBitAtributoEntidad(String bitAtributoEntidad) {
		this.bitAtributoEntidad = bitAtributoEntidad;
	}

	@Column(name = "bit_valor_registrado")
	public String getBitValorRegistrado() {
		return this.bitValorRegistrado;
	}

	public void setBitValorRegistrado(String bitValorRegistrado) {
		this.bitValorRegistrado = bitValorRegistrado;
	}
}