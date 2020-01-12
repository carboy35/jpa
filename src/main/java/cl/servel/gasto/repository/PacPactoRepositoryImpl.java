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

public class PacPactoRepositoryImpl implements PacPactoRepositoryCustom {
	private static final Logger LOG = LoggerFactory.getLogger(RenRendicionRepositoryCustom.class);

	@PersistenceContext
	EntityManager entityManager;
	
	public List<PacPacto> devuelvePactos(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId){
		StringBuilder queryString= new StringBuilder();
		
		//pactos partidos
		queryString.append("select ");
		queryString.append("pa.pac_id, ");
		queryString.append("pa.pac_nombre, ");
		queryString.append("pa.pac_codigo_origen, ");
		queryString.append("pa.pac_sigla, ");
		queryString.append("pa.lista, ");
		queryString.append("pa.pac_eliminado, ");
		queryString.append("pa.pac_created, ");
		queryString.append("pa.pac_modified, ");
		queryString.append("pa.ele_id ");
		queryString.append("from ");
		queryString.append("pac_pacto pa, ");
		queryString.append("inf_instancia_flujo inf, ");
		queryString.append("par_partido p, int_instancia_tarea ins ");
		queryString.append("where ");
		queryString.append("p.par_id = inf.par_id ");
		queryString.append("and pa.pac_id = p.pac_id ");
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
		queryString.append("pa.pac_id ");
		queryString.append("union ");
		//pactos candidatos
		queryString.append("select ");
		queryString.append("pa.pac_id, ");
		queryString.append("pa.pac_nombre, ");
		queryString.append("pa.pac_codigo_origen, ");
		queryString.append("pa.pac_sigla, ");
		queryString.append("pa.lista, ");
		queryString.append("pa.pac_eliminado, ");
		queryString.append("pa.pac_created, ");
		queryString.append("pa.pac_modified, ");
		queryString.append("pa.ele_id ");
		queryString.append("from ");
		queryString.append("can_candidato c, ");
		queryString.append("pac_pacto pa, ");
		queryString.append("inf_instancia_flujo inf, int_instancia_tarea ins ");
		queryString.append("where ");
		queryString.append("inf.can_id = c.can_id ");
		queryString.append("and c.pac_id = pa.pac_id ");
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
		queryString.append("pa.pac_id ");
		
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
		List<PacPacto> pactosList= (List<PacPacto>) query.getResultList();
		List<PacPacto> pactosResultList=new ArrayList<>();
		PacPacto pacto=null;
		EleEleccion eleccion=null;
		for (Object record : pactosList) {
			Object[] fields = (Object[]) record;
			pacto= new PacPacto();
			pacto.setPacId((Integer)fields[0]);
			pacto.setPacNombre((String)fields[1]);
			pacto.setPacCodigoOrigen((String)fields[2]);
			pacto.setPacSigla((String)fields[3]);
			pacto.setLista((String)fields[4]);
			pacto.setPacEliminado((Boolean)fields[5]);
			pacto.setPacCreated((Date)fields[6]);
			pacto.setPacModified((Date)fields[7]);
			if (fields[8]!=null) {
				eleccion= new EleEleccion();
				eleccion.setEleId((Integer)fields[8]);
				pacto.setEleEleccion(eleccion);	
			}
			pactosResultList.add(pacto);
			
		}
		return pactosResultList;
	
	}
	
	public List<PacPacto> devuelvePactosPorAsignar(Integer eventoId,String estado){
		StringBuilder queryString= new StringBuilder();
		
		//pactos partidos
		queryString.append("select pa.pac_id,pa.pac_nombre, ");
		queryString.append("pa.pac_codigo_origen,pa.pac_sigla,pa.lista, ");
		queryString.append("pa.pac_eliminado,pa.pac_created,pa.pac_modified,pa.ele_id ");
		queryString.append("from ren_rendicion r, par_partido p,pac_pacto pa where r.ren_estado= :estado ");
		queryString.append("and r.par_id=p.par_id ");
		queryString.append("and pa.pac_id=p.pac_id ");
		if (eventoId !=null) {
			queryString.append("and r.eve_id= :eventoId ");
		}
		queryString.append("group by pa.pac_id ");
		//pactos candidato
		queryString.append("union ");
		queryString.append("select pa.pac_id,pa.pac_nombre, ");
		queryString.append("pa.pac_codigo_origen,pa.pac_sigla,pa.lista, ");
		queryString.append("pa.pac_eliminado,pa.pac_created,pa.pac_modified,pa.ele_id ");
		queryString.append("from ren_rendicion r, can_candidato c,pac_pacto pa where r.ren_estado= :estado ");
		queryString.append("and r.can_id=c.can_id ");
		queryString.append("and c.pac_id=pa.pac_id ");
		if (eventoId !=null) {
			queryString.append("and r.eve_id= :eventoId ");
		}
		queryString.append("group by pa.pac_id ");
		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
			}
			query.setParameter("estado", estado);
			
		List<PacPacto> pactosList= (List<PacPacto>) query.getResultList();
		List<PacPacto> pactosResultList=new ArrayList<>();
		PacPacto pacto=null;
		EleEleccion eleccion=null;
		for (Object record : pactosList) {
			Object[] fields = (Object[]) record;
			pacto= new PacPacto();
			pacto.setPacId((Integer)fields[0]);
			pacto.setPacNombre((String)fields[1]);
			pacto.setPacCodigoOrigen((String)fields[2]);
			pacto.setPacSigla((String)fields[3]);
			pacto.setLista((String)fields[4]);
			pacto.setPacEliminado((Boolean)fields[5]);
			pacto.setPacCreated((Date)fields[6]);
			pacto.setPacModified((Date)fields[7]);
			if (fields[8]!=null) {
				eleccion= new EleEleccion();
				eleccion.setEleId((Integer)fields[8]);
				pacto.setEleEleccion(eleccion);	
			}
			pactosResultList.add(pacto);
			
		}
		return pactosResultList;
	
	}
	@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("delete from pac_pacto p ");
		queryString.append("where p.ele_id= ? ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
}
