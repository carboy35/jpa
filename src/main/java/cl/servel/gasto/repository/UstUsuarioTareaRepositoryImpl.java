package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.GutGrupoUsuarioTarea;
import cl.servel.gasto.entity.UstUsuarioTarea;

public class UstUsuarioTareaRepositoryImpl implements UstUsuarioTareaRepositoryCustom {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<UstUsuarioTarea> usuariosPorTarea(Integer eventoId, Integer dtaId){
		
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ut.gut_id,ut.usu_id,ut.ust_activo ");
		queryString.append("from usu_usuarios u,rlc_usuario_perfil rp, per_perfil p, ust_usuario_tarea ut, ");
		queryString.append("rlc_perfil_definicion_tarea rpt, gut_grupo_usuario_tarea gut, cel_celula c ");
		queryString.append("where u.usu_id=rp.usu_id ");
		queryString.append("and p.per_id=rp.per_id ");
		queryString.append("and ut.usu_id= u.usu_id ");
		queryString.append("and p.per_id=rpt.per_id ");
		queryString.append("and rpt.dta_id= :dtaId ");
		queryString.append("and gut.gut_id=ut.gut_id ");
		queryString.append("and c.cel_id=gut.cel_id ");
		queryString.append("and c.cel_activa=true ");
		queryString.append("and c.eve_id= :eventoId ");
		queryString.append("and ut.ust_activo=true ");
		queryString.append("and u.usu_eliminado=false ");
		queryString.append("group by ut.gut_id,ut.usu_id,ut.ust_activo ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId != null) {
			query.setParameter("eventoId", eventoId);
		}
		if (dtaId != null) {
			query.setParameter("dtaId", dtaId);
		}
		
		List<UstUsuarioTarea> usuarioTareaList= (List<UstUsuarioTarea>) query.getResultList();
		List<UstUsuarioTarea> usuarioTareaResultList = new ArrayList<>();
		UstUsuarioTarea usuarioTarea= null;
		GutGrupoUsuarioTarea gutGrupoUsuarioTarea=null;
		
		for (Object record : usuarioTareaList) {
			Object[] fields = (Object[]) record;
			usuarioTarea= new UstUsuarioTarea();
			if (fields[0]!=null) {
				gutGrupoUsuarioTarea= new GutGrupoUsuarioTarea();
				gutGrupoUsuarioTarea.setGutId((Integer)fields[0]);
				usuarioTarea.setGutGrupoUsuarioTarea(gutGrupoUsuarioTarea);
			}
			usuarioTarea.setUstId((Integer)fields[1]);
			usuarioTarea.setUstActivo((Boolean)fields[2]);
			
			usuarioTarea.setGutGrupoUsuarioTarea(gutGrupoUsuarioTarea);
			usuarioTareaResultList.add(usuarioTarea);
		}
		
		
		return usuarioTareaResultList;
	}

}
