package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.DtaDefinicionTarea;
import cl.servel.gasto.entity.PerPerfil;
import cl.servel.gasto.entity.RlcPerfilDefinicionTarea;

public class RlcPerfilDefinicionTareaRepositoryImpl implements RlcPerfilDefinicionTareaRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	public List<RlcPerfilDefinicionTarea> agregarUsuarios(Integer usuarioId) {
StringBuilder queryString = new StringBuilder();
		
		queryString.append("select pt.rlc_id,pt.per_id,pt.dta_id from rlc_perfil_definicion_tarea pt, rlc_usuario_perfil up ");
		queryString.append("where pt.per_id=up.per_id "); 
		queryString.append("and up.usu_id = :usuarioId ");
		queryString.append("and (up.usu_id,pt.dta_id)  not in ( ");
		queryString.append("select ust.usu_id,asg.dta_id from asg_asignacion_carga_tarea asg ");
		queryString.append(",gut_grupo_usuario_tarea gut, ust_usuario_tarea ust ");
		queryString.append("where asg.gut_id=gut.gut_id ");
		queryString.append("and ust.gut_id=gut.gut_id ");
		queryString.append("and ust.usu_id = :usuarioId ");
		queryString.append(") ");

		
	
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("usuarioId", usuarioId);

	
	List<RlcPerfilDefinicionTarea> perfilDefinicionList= (List<RlcPerfilDefinicionTarea>)query.getResultList();
	List<RlcPerfilDefinicionTarea> perfilDefinicionResultList = new ArrayList<>();
	RlcPerfilDefinicionTarea perfilDefinicion= null;
	PerPerfil perfil=null;
	DtaDefinicionTarea definicionTarea=null;
	for (Object record : perfilDefinicionList) {
		Object[] fields = (Object[]) record;
		perfilDefinicion= new RlcPerfilDefinicionTarea();
		perfilDefinicion.setRlcId((Integer)fields[0]);
		if (fields[1]!=null) {
			perfil=new PerPerfil();
			perfil.setPerId((Integer)fields[1]);
			perfilDefinicion.setPerPerfil(perfil);
		}
		if (fields[2]!=null) {
			definicionTarea= new DtaDefinicionTarea();
			definicionTarea.setDtaId((Integer)fields[2]);
			perfilDefinicion.setDtaDefinicionTarea(definicionTarea);
		}
		
		
		perfilDefinicionResultList.add(perfilDefinicion);
		
		}
	return perfilDefinicionResultList;
		
		
	}
}
