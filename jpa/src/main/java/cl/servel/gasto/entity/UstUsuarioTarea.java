package cl.servel.gasto.entity;
// Generated 01-07-2019 12:09:34 by Hibernate Tools 5.2.12.Final

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

/**
 * UstUsuarioTarea generated by hbm2java
 */
@Entity
@Table(name = "ust_usuario_tarea")
public class UstUsuarioTarea implements java.io.Serializable {

	private Integer ustId;
	private GutGrupoUsuarioTarea gutGrupoUsuarioTarea;
	private UsuUsuarios usuUsuarios;
	private boolean ustActivo;
	private Integer usuUsuarioSubrogante;
	private Set<IntInstanciaTarea> intInstanciaTareas = new HashSet<IntInstanciaTarea>(0);

	public UstUsuarioTarea() {
	}

	public UstUsuarioTarea(GutGrupoUsuarioTarea gutGrupoUsuarioTarea, UsuUsuarios usuUsuarios, boolean ustActivo) {
		this.gutGrupoUsuarioTarea = gutGrupoUsuarioTarea;
		this.usuUsuarios = usuUsuarios;
		this.ustActivo = ustActivo;
	}

	public UstUsuarioTarea(GutGrupoUsuarioTarea gutGrupoUsuarioTarea, UsuUsuarios usuUsuarios, boolean ustActivo,
			Integer usuUsuarioSubrogante, Set<IntInstanciaTarea> intInstanciaTareas) {
		this.gutGrupoUsuarioTarea = gutGrupoUsuarioTarea;
		this.usuUsuarios = usuUsuarios;
		this.ustActivo = ustActivo;
		this.usuUsuarioSubrogante = usuUsuarioSubrogante;
		this.intInstanciaTareas = intInstanciaTareas;
	}

	@SequenceGenerator(name = "cl.servel.gasto.entity.UstUsuarioTareaIdGenerator", sequenceName = "ust_usuario_tarea_ust_id_seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cl.servel.gasto.entity.UstUsuarioTareaIdGenerator")

	@Column(name = "ust_id", unique = true, nullable = false)
	public Integer getUstId() {
		return this.ustId;
	}

	public void setUstId(Integer ustId) {
		this.ustId = ustId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gut_id", nullable = false)
	public GutGrupoUsuarioTarea getGutGrupoUsuarioTarea() {
		return this.gutGrupoUsuarioTarea;
	}

	public void setGutGrupoUsuarioTarea(GutGrupoUsuarioTarea gutGrupoUsuarioTarea) {
		this.gutGrupoUsuarioTarea = gutGrupoUsuarioTarea;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usu_id", nullable = false)
	public UsuUsuarios getUsuUsuarios() {
		return this.usuUsuarios;
	}

	public void setUsuUsuarios(UsuUsuarios usuUsuarios) {
		this.usuUsuarios = usuUsuarios;
	}

	@Column(name = "ust_activo", nullable = false)
	public boolean isUstActivo() {
		return this.ustActivo;
	}

	public void setUstActivo(boolean ustActivo) {
		this.ustActivo = ustActivo;
	}

	@Column(name = "usu_usuario_subrogante")
	public Integer getUsuUsuarioSubrogante() {
		return this.usuUsuarioSubrogante;
	}

	public void setUsuUsuarioSubrogante(Integer usuUsuarioSubrogante) {
		this.usuUsuarioSubrogante = usuUsuarioSubrogante;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ustUsuarioTarea")
	public Set<IntInstanciaTarea> getIntInstanciaTareas() {
		return this.intInstanciaTareas;
	}

	public void setIntInstanciaTareas(Set<IntInstanciaTarea> intInstanciaTareas) {
		this.intInstanciaTareas = intInstanciaTareas;
	}

}
