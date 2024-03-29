package cl.servel.gasto.entity;
// Generated 12-06-2019 10:30:57 by Hibernate Tools 5.2.11.Final

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
 * RlcRolUsuarioFuncionalidad generated by hbm2java
 */
@Entity
@Table(name = "rlc_rol_usuario_funcionalidad")
public class RlcRolUsuarioFuncionalidad implements java.io.Serializable {

	private Integer rlcId;
	private FunFuncionalidad funFuncionalidad;
	private RolUsuario rolUsuario;

	public RlcRolUsuarioFuncionalidad() {
	}

	public RlcRolUsuarioFuncionalidad(FunFuncionalidad funFuncionalidad, RolUsuario rolUsuario) {
		this.funFuncionalidad = funFuncionalidad;
		this.rolUsuario = rolUsuario;
	}

	@SequenceGenerator(name = "RlcRolUsuarioFuncionalidadIdGenerator", sequenceName = "rlc_rol_usuario_funcionalidad_rlc_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "RlcRolUsuarioFuncionalidadIdGenerator")

	@Column(name = "rlc_id", unique = true, nullable = false)
	public Integer getRlcId() {
		return this.rlcId;
	}

	public void setRlcId(Integer rlcId) {
		this.rlcId = rlcId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fun_id", nullable = false)
	public FunFuncionalidad getFunFuncionalidad() {
		return this.funFuncionalidad;
	}

	public void setFunFuncionalidad(FunFuncionalidad funFuncionalidad) {
		this.funFuncionalidad = funFuncionalidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_id", nullable = false)
	public RolUsuario getRolUsuario() {
		return this.rolUsuario;
	}

	public void setRolUsuario(RolUsuario rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

}
