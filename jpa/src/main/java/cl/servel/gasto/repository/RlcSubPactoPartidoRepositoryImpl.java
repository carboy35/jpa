package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.RlcSubpactoPartido;
import cl.servel.gasto.entity.SpaPacto;

public class RlcSubPactoPartidoRepositoryImpl implements RlcSubPactoPartidoRepositoryCustom {
	private static final Logger LOG = LoggerFactory.getLogger(RenRendicionRepositoryCustom.class);

	@PersistenceContext
	EntityManager entityManager;
	public List<RlcSubpactoPartido> devuelveSubPactoPartidos(Integer eventoId,List<Integer> usuarioIds, Integer dtaId, Integer usuarioId){
		StringBuilder queryString= new StringBuilder();
		queryString.append("select ");
		queryString.append("r.rlc_id, ");
		queryString.append("r.spa_id, ");
		queryString.append("r.par_id, ");
		queryString.append("r.ele_id ");
		queryString.append("from ");
		queryString.append("rlc_subpacto_partido r, ");
		queryString.append("inf_instancia_flujo inf, int_instancia_tarea ins ");
		queryString.append("where ");
		queryString.append("inf.par_id = r.par_id ");
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
				queryString.append("and  ins.dta_id = :dtaId ");
			}
		
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
		
		List<RlcSubpactoPartido> rlcSubpactoPartidoList=(List<RlcSubpactoPartido>)query.getResultList(); 
		List<RlcSubpactoPartido> rlcSubpactoPartidoResultList=new ArrayList<>();
		RlcSubpactoPartido rlcSubpactoPartido= null;
		ParPartido partido=null;
		SpaPacto subpacto=null;
		EleEleccion eleccion=null;
		for (Object record : rlcSubpactoPartidoList) {
			Object[] fields = (Object[]) record;
			rlcSubpactoPartido= new RlcSubpactoPartido();
			rlcSubpactoPartido.setRlcId((Integer)fields[0]);
			if (fields[1]!=null) {
				subpacto= new SpaPacto();
				subpacto.setSpaId((Integer)fields[1]);
				rlcSubpactoPartido.setSpaPacto(subpacto);
			}
			if (fields[2]!=null) {
				partido= new ParPartido();
				partido.setParId((Integer)fields[2]);
				rlcSubpactoPartido.setParPartido(partido);	
			}
			if (fields[3]!=null) {
				eleccion= new EleEleccion();
				eleccion.setEleId((Integer)fields[3]);
				rlcSubpactoPartido.setEleEleccion(eleccion);
			}
			rlcSubpactoPartidoResultList.add(rlcSubpactoPartido);
		}
		return rlcSubpactoPartidoResultList;
	}
	
	public List<RlcSubpactoPartido> devuelveSubPactoPartidosPorAsignar(Integer eventoId,String estado){
		StringBuilder queryString= new StringBuilder();
		queryString.append("select ");
		queryString.append("rl.rlc_id, ");
		queryString.append("rl.spa_id, ");
		queryString.append("rl.par_id, ");
		queryString.append("rl.ele_id ");
		queryString.append("from spa_pacto sp, ren_rendicion r, rlc_subpacto_partido rl ");
		queryString.append("where r.par_id=rl.par_id ");
		queryString.append("and sp.spa_id=rl.spa_id ");
		queryString.append("and r.ren_estado= :estado ");
		if (eventoId !=null) {
			queryString.append("and r.eve_id= :eventoId ");
		}
		
		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
		}
		query.setParameter("estado", estado);
		
		List<RlcSubpactoPartido> rlcSubpactoPartidoList=(List<RlcSubpactoPartido>)query.getResultList(); 
		List<RlcSubpactoPartido> rlcSubpactoPartidoResultList=new ArrayList<>();
		RlcSubpactoPartido rlcSubpactoPartido= null;
		ParPartido partido=null;
		SpaPacto subpacto=null;
		EleEleccion eleccion=null;
		for (Object record : rlcSubpactoPartidoList) {
			Object[] fields = (Object[]) record;
			rlcSubpactoPartido= new RlcSubpactoPartido();
			rlcSubpactoPartido.setRlcId((Integer)fields[0]);
			if (fields[1]!=null) {
				subpacto= new SpaPacto();
				subpacto.setSpaId((Integer)fields[1]);
				rlcSubpactoPartido.setSpaPacto(subpacto);
			}
			if (fields[2]!=null) {
				partido= new ParPartido();
				partido.setParId((Integer)fields[2]);
				rlcSubpactoPartido.setParPartido(partido);	
			}
			if (fields[3]!=null) {
				eleccion= new EleEleccion();
				eleccion.setEleId((Integer)fields[3]);
				rlcSubpactoPartido.setEleEleccion(eleccion);
			}
			rlcSubpactoPartidoResultList.add(rlcSubpactoPartido);
		}
		return rlcSubpactoPartidoResultList;
	}
	@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();

		
		queryString.append("delete  from rlc_subpacto_partido where ele_id= ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
}
