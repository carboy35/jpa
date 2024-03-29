package cl.servel.gasto.entity;
// Generated 12-06-2019 10:30:57 by Hibernate Tools 5.2.11.Final

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
 * RolUsuario generated by hbm2java
 */
@Entity
@Table(name = "rol_usuario")
public class RolUsuario implements java.io.Serializable {

	private Integer rolId;
	private String rolDescripcion;
	private boolean rolActivo;
	private Set<RlcUsuarioRolUsuario> rlcUsuarioRolUsuarios = new HashSet<RlcUsuarioRolUsuario>(0);
	private Set<RlcRolUsuarioFuncionalidad> rlcRolUsuarioFuncionalidads = new HashSet<RlcRolUsuarioFuncionalidad>(0);
	private Set<RlcPerfilRolUsuario> rlcPerfilRolUsuarios = new HashSet<RlcPerfilRolUsuario>(0);

	public RolUsuario() {
	}

	public RolUsuario(String rolDescripcion, boolean rolActivo) {
		this.rolDescripcion = rolDescripcion;
		this.rolActivo = rolActivo;
	}

	public RolUsuario(String rolDescripcion, boolean rolActivo, Set<RlcUsuarioRolUsuario> rlcUsuarioRolUsuarios, Set<RlcRolUsuarioFuncionalidad> rlcRolUsuarioFuncionalidads, Set<RlcPerfilRolUsuario> rlcPerfilRolUsuarios) {
		this.rolDescripcion = rolDescripcion;
		this.rolActivo = rolActivo;
		this.rlcUsuarioRolUsuarios = rlcUsuarioRolUsuarios;
		this.rlcRolUsuarioFuncionalidads = rlcRolUsuarioFuncionalidads;
		this.rlcPerfilRolUsuarios = rlcPerfilRolUsuarios;
	}

	@SequenceGenerator(name = "RolUsuarioIdGenerator", sequenceName = "rol_usuario_rol_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "RolUsuarioIdGenerator")

	@Column(name = "rol_id", unique = true, nullable = false)
	public Integer getRolId() {
		return this.rolId;
	}

	public void setRolId(Integer rolId) {
		this.rolId = rolId;
	}

	@Column(name = "rol_descripcion", nullable = false, length = 100)
	public String getRolDescripcion() {
		return this.rolDescripcion;
	}

	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}

	@Column(name = "rol_activo", nullable = false)
	public boolean isRolActivo() {
		return this.rolActivo;
	}

	public void setRolActivo(boolean rolActivo) {
		this.rolActivo = rolActivo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rolUsuario")
	public Set<RlcUsuarioRolUsuario> getRlcUsuarioRolUsuarios() {
		return this.rlcUsuarioRolUsuarios;
	}

	public void setRlcUsuarioRolUsuarios(Set<RlcUsuarioRolUsuario> rlcUsuarioRolUsuarios) {
		this.rlcUsuarioRolUsuarios = rlcUsuarioRolUsuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rolUsuario")
	public Set<RlcRolUsuarioFuncionalidad> getRlcRolUsuarioFuncionalidads() {
		return this.rlcRolUsuarioFuncionalidads;
	}

	public void setRlcRolUsuarioFuncionalidads(Set<RlcRolUsuarioFuncionalidad> rlcRolUsuarioFuncionalidads) {
		this.rlcRolUsuarioFuncionalidads = rlcRolUsuarioFuncionalidads;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rolUsuario")
	public Set<RlcPerfilRolUsuario> getRlcPerfilRolUsuarios() {
		return this.rlcPerfilRolUsuarios;
	}

	public void setRlcPerfilRolUsuarios(Set<RlcPerfilRolUsuario> rlcPerfilRolUsuarios) {
		this.rlcPerfilRolUsuarios = rlcPerfilRolUsuarios;
	}

}
