package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.PacPacto;
import cl.servel.gasto.entity.SpaPacto;

public class SpaPactoRepositoryImpl implements SpaPactoRepositoryCustom {
	private static final Logger LOG = LoggerFactory.getLogger(RenRendicionRepositoryCustom.class);

	@PersistenceContext
	EntityManager entityManager;
	public List<SpaPacto> devuelveSubpactos(Integer eventoId, List<Integer> usuarioIds,Integer dtaId,Integer usuarioId){
		StringBuilder queryString= new StringBuilder();
		
		//subpactos partidos
		queryString.append("select ");
		queryString.append("s.spa_id, ");
		queryString.append("s.pac_codigo, ");
		queryString.append("s.spa_sigla, ");
		queryString.append("s.spa_eliminado, ");
		queryString.append("s.created, ");
		queryString.append("s.modified, ");
		queryString.append("s.spa_nombre, ");
		queryString.append("s.spa_codigo_origen, ");
		queryString.append("s.ele_id ");
		queryString.append("from ");
		queryString.append("spa_pacto s, ");
		queryString.append("inf_instancia_flujo inf, ");
		queryString.append("par_partido p, ");
		queryString.append("rlc_subpacto_partido r, int_instancia_tarea ins ");
		queryString.append("where ");
		queryString.append("p.par_id = inf.par_id ");
		queryString.append("and inf.par_id = r.par_id ");
		queryString.append("and s.spa_id = r.spa_id ");
		queryString.append("and ins.inf_id=inf.inf_id ");
		if (usuarioId ==null) {
			queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
			queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
		}
		
		if (eventoId !=null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		if (usuarioIds !=null) {
			queryString.append("and inf.usu_id_actual in :usuarioIds ");
		}
		if (usuarioId !=null) {
			queryString.append("and ins.usu_id= :usuarioId ");
		}
		if (dtaId !=null) {
			queryString.append("and ins.dta_id= :dtaId ");
		}
		queryString.append("group by ");
		queryString.append("s.spa_id "); 
		queryString.append("union "); 
		//subpactos candidatos
		queryString.append("select ");
		queryString.append("s.spa_id, ");
		queryString.append("s.pac_codigo, ");
		queryString.append("s.spa_sigla, ");
		queryString.append("s.spa_eliminado, ");
		queryString.append("s.created, ");
		queryString.append("s.modified, ");
		queryString.append("s.spa_nombre, ");
		queryString.append("s.spa_codigo_origen, ");
		queryString.append("s.ele_id ");
		queryString.append("from ");
		queryString.append("can_candidato c, ");
		queryString.append("spa_pacto s, ");
		queryString.append("inf_instancia_flujo inf, int_instancia_tarea ins ");
		queryString.append("where ");
		queryString.append("inf.can_id = c.can_id ");
		queryString.append("and s.spa_id = c.sup_id ");
		queryString.append("and ins.inf_id=inf.inf_id ");
		if (usuarioId ==null) {
			queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
			queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
		}
		
		if (eventoId !=null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		if (usuarioIds !=null) {
			queryString.append("and inf.usu_id_actual in :usuarioIds ");
		}
		if (usuarioId !=null) {
			queryString.append("and ins.usu_id= :usuarioId ");
		}
		if (dtaId !=null) {
			queryString.append("and ins.dta_id= :dtaId ");
		}
		queryString.append("group by ");
		queryString.append("s.spa_id ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
		}
		if (usuarioIds !=null) {
			query.setParameter("usuarioIds", usuarioIds);
		}
		if (usuarioId !=null) {
			query.setParameter("usuarioId", usuarioId);
		}
		if (dtaId !=null) {
			query.setParameter("dtaId", dtaId);
		}
		
		List<SpaPacto> subPactosList= (List<SpaPacto>) query.getResultList();
		List<SpaPacto> subPactosResultList= new ArrayList<>();
		SpaPacto subpacto=null;
		PacPacto pacto= null;
		EleEleccion eleccion=null;
		for (Object record : subPactosList) {
			Object[] fields = (Object[]) record;
			subpacto= new SpaPacto();
			subpacto.setSpaId((Integer) fields[0]);
			if (fields[1]!=null) {
				pacto=new PacPacto();
				pacto.setPacId((Integer)fields[1]);
				subpacto.setPacPacto(pacto);
			}
			subpacto.setSpaSigla((String)fields[2]);
			subpacto.setSpaEliminado((Boolean)fields[3]);
			subpacto.setCreated((Date)fields[4]);
			subpacto.setModified((Date)fields[5]);
			subpacto.setSpaNombre((String)fields[6]);
			subpacto.setSpaCodigoOrigen((String)fields[7]);
			if (fields[8] !=null) {
				eleccion= new EleEleccion();
				eleccion.setEleId((Integer)fields[8]);
				subpacto.setEleEleccion(eleccion);
			}
			subPactosResultList.add(subpacto);
		}
			return subPactosResultList;
	}
	
	public List<SpaPacto> devuelveSubpactosPorAsignar(Integer eventoId,String estado){
		StringBuilder queryString= new StringBuilder();
		
		//subpactos partidos
		queryString.append("select sp.spa_id,sp.pac_codigo,sp.spa_sigla,sp.spa_eliminado,sp.created,sp.modified,sp.spa_nombre ");
		queryString.append(",sp.spa_codigo_origen,sp.ele_id ");
		queryString.append("from spa_pacto sp, ren_rendicion r, rlc_subpacto_partido rl ");
		queryString.append("where r.par_id=rl.par_id ");
		queryString.append("and sp.spa_id=rl.spa_id ");
		queryString.append("and r.ren_estado= :estado ");
		if (eventoId !=null) {
			queryString.append("and r.eve_id= :eventoId ");
		}
		queryString.append("group by sp.spa_id ");
		queryString.append("union ");
		//subpactos candidato
		queryString.append("select sp.spa_id,sp.pac_codigo,sp.spa_sigla,sp.spa_eliminado,sp.created,sp.modified,sp.spa_nombre ");
		queryString.append(",sp.spa_codigo_origen,sp.ele_id ");
		queryString.append("from spa_pacto sp,can_candidato c, ren_rendicion r ");
		queryString.append("where r.par_id=c.par_id ");
		queryString.append("and r.ren_estado= :estado ");
		queryString.append("and c.sup_id=sp.spa_id ");
		if (eventoId !=null) {
			queryString.append("and r.eve_id= :eventoId ");
		}
		queryString.append("group by sp.spa_id ");
		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
		}
		query.setParameter("estado", estado);
		
		List<SpaPacto> subPactosList= (List<SpaPacto>) query.getResultList();
		List<SpaPacto> subPactosResultList= new ArrayList<>();
		SpaPacto subpacto=null;
		PacPacto pacto= null;
		EleEleccion eleccion=null;
		for (Object record : subPactosList) {
			Object[] fields = (Object[]) record;
			subpacto= new SpaPacto();
			subpacto.setSpaId((Integer) fields[0]);
			if (fields[1]!=null) {
				pacto=new PacPacto();
				pacto.setPacId((Integer)fields[1]);
				subpacto.setPacPacto(pacto);
			}
			subpacto.setSpaSigla((String)fields[2]);
			subpacto.setSpaEliminado((Boolean)fields[3]);
			subpacto.setCreated((Date)fields[4]);
			subpacto.setModified((Date)fields[5]);
			subpacto.setSpaNombre((String)fields[6]);
			subpacto.setSpaCodigoOrigen((String)fields[7]);
			if (fields[8] !=null) {
				eleccion= new EleEleccion();
				eleccion.setEleId((Integer)fields[8]);
				subpacto.setEleEleccion(eleccion);
			}
			subPactosResultList.add(subpacto);
		}
			return subPactosResultList;
	}

@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("delete from spa_pacto where ele_id= ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
	
}
