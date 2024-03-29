package cl.servel.gasto.entity;
// Generated 04-10-2018 19:09:57 by Hibernate Tools 5.2.11.Final

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
 * PamParametro generated by hbm2java
 */
@Entity
@Table(name = "pam_parametro")
public class PamParametro implements java.io.Serializable {

	private Integer pamId;
	private Date pamCreated;
	private Date pamModified;
	private String pamNombre;
	private String valor;
	private Boolean parametroSistema;
	private Boolean pamEliminado;
	private EveEventoEleccionario eveEventoEleccionario;
	private String pamDescripcion;
	private String pamCodTipo;
	private Set<RlcConfiguracionParametro> rlcConfiguracionParametros = new HashSet<RlcConfiguracionParametro>(0);

	public PamParametro() {
	}

	public PamParametro(EveEventoEleccionario eveEventoEleccionario, Date pamCreated, Date pamModified,
			String pamNombre, String valor, Boolean parametroSistema, Boolean pamEliminado, String pamDescripcion,
			String pamCodTipo,Set<RlcConfiguracionParametro> rlcConfiguracionParametros) {
		this.eveEventoEleccionario = eveEventoEleccionario;
		this.pamCreated = pamCreated;
		this.pamModified = pamModified;
		this.pamNombre = pamNombre;
		this.valor = valor;
		this.parametroSistema = parametroSistema;
		this.pamEliminado = pamEliminado;
		this.pamDescripcion = pamDescripcion;
		this.pamCodTipo = pamCodTipo;
		this.rlcConfiguracionParametros = rlcConfiguracionParametros;
	}

	@SequenceGenerator(name = "cl.servel.gasto.entity.PamParametroIdGenerator", sequenceName = "pam_parametro_pam_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.entity.PamParametroIdGenerator")

	@Column(name = "pam_id", unique = true, nullable = false)
	public Integer getPamId() {
		return this.pamId;
	}

	public void setPamId(Integer pamId) {
		this.pamId = pamId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pam_created", length = 13)
	public Date getPamCreated() {
		return this.pamCreated;
	}

	public void setPamCreated(Date pamCreated) {
		this.pamCreated = pamCreated;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pam_modified", length = 13)
	public Date getPamModified() {
		return this.pamModified;
	}

	public void setPamModified(Date pamModified) {
		this.pamModified = pamModified;
	}

	@Column(name = "pam_nombre", length = 50)
	public String getPamNombre() {
		return this.pamNombre;
	}

	public void setPamNombre(String pamNombre) {
		this.pamNombre = pamNombre;
	}

	@Column(name = "valor", length = 300)
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "parametro_sistema")
	public Boolean getParametroSistema() {
		return this.parametroSistema;
	}

	public void setParametroSistema(Boolean parametroSistema) {
		this.parametroSistema = parametroSistema;
	}

	@Column(name = "pam_eliminado")
	public Boolean getPamEliminado() {
		return this.pamEliminado;
	}

	public void setPamEliminado(Boolean pamEliminado) {
		this.pamEliminado = pamEliminado;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pam_eve_id", nullable = false)
	public EveEventoEleccionario getEveEventoEleccionario() {
		return this.eveEventoEleccionario;
	}

	public void setEveEventoEleccionario(EveEventoEleccionario eveEventoEleccionario) {
		this.eveEventoEleccionario = eveEventoEleccionario;
	}

	@Column(name = "pam_descripcion", length = 100)
	public String getPamDescripcion() {
		return this.pamDescripcion;
	}

	public void setPamDescripcion(String pamDescripcion) {
		this.pamDescripcion = pamDescripcion;
	}
	@Column(name = "pam_cod_tipo", length = 50)
	public String getPamCodTipo() {
		return this.pamCodTipo;
	}

	public void setPamCodTipo(String pamCodTipo) {
		this.pamCodTipo = pamCodTipo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pamParametro")
	public Set<RlcConfiguracionParametro> getRlcConfiguracionParametros() {
		return this.rlcConfiguracionParametros;
	}

	public void setRlcConfiguracionParametros(Set<RlcConfiguracionParametro> rlcConfiguracionParametros) {
		this.rlcConfiguracionParametros = rlcConfiguracionParametros;
	}

}
