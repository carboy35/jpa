package cl.servel.gasto.entity;
// Generated 09-10-2018 22:08:29 by Hibernate Tools 5.2.11.Final

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
 * RlcUsuarioPerfil generated by hbm2java
 */
@Entity
@Table(name = "rlc_usuario_perfil")
public class RlcUsuarioPerfil implements java.io.Serializable {

	private Integer rlcId;
	private PerPerfil perPerfil;
	private UsuUsuarios usuUsuarios;
	private Boolean eliminado;

	public RlcUsuarioPerfil() {
	}

	public RlcUsuarioPerfil(PerPerfil perPerfil, UsuUsuarios usuUsuarios) {
		this.perPerfil = perPerfil;
		this.usuUsuarios = usuUsuarios;
	}

	public RlcUsuarioPerfil(PerPerfil perPerfil, UsuUsuarios usuUsuarios, Boolean eliminado) {
		this.perPerfil = perPerfil;
		this.usuUsuarios = usuUsuarios;
		this.eliminado = eliminado;
	}

	@SequenceGenerator(name = "cl.servel.gasto.entity.RlcUsuarioPerfilIdGenerator", sequenceName = "rlc_usuario_perfil_rlc_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.entity.RlcUsuarioPerfilIdGenerator")

	@Column(name = "rlc_id", unique = true, nullable = false)
	public Integer getRlcId() {
		return this.rlcId;
	}

	public void setRlcId(Integer rlcId) {
		this.rlcId = rlcId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "per_id", nullable = false)
	public PerPerfil getPerPerfil() {
		return this.perPerfil;
	}

	public void setPerPerfil(PerPerfil perPerfil) {
		this.perPerfil = perPerfil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usu_id", nullable = false)
	public UsuUsuarios getUsuUsuarios() {
		return this.usuUsuarios;
	}

	public void setUsuUsuarios(UsuUsuarios usuUsuarios) {
		this.usuUsuarios = usuUsuarios;
	}

	@Column(name = "eliminado")
	public Boolean getEliminado() {
		return this.eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

}
