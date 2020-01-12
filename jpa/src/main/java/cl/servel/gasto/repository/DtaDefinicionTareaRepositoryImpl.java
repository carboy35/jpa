package cl.servel.gasto.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.DtaDefinicionTarea;

public class DtaDefinicionTareaRepositoryImpl implements DtaDefinicionTareaRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	
	public DtaDefinicionTarea getTareaInicioRevisionRespuestaObservacion(Integer idEvento,String codigoDfl) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select dta.*  ");
		queryString.append("  from dta_definicion_tarea dta  ");
		queryString.append("  join dea_definicion_actividad dea  ");
		queryString.append("    on dea.dta_id = dta.dta_id  ");
		queryString.append("  join dfl_definicion_flujo dfl  ");
		queryString.append("    on dta.dfl_id=dfl.dfl_id  ");
		queryString.append(" where dea.dea_codigo = 'INICIO_RESPUESTA_OBSERVACION'  ");
		queryString.append("  and dfl.eve_id=:idEvento ");
		queryString.append("  and dfl.dfl_codigo=:codigoDfl ");
		queryString.append(" group by dta.dta_id ");
		
		Query query = entityManager.createNativeQuery(queryString.toString(), DtaDefinicionTarea.class);
		query.setParameter("idEvento", idEvento);
		query.setParameter("codigoDfl", codigoDfl);
		
		DtaDefinicionTarea definicion = (DtaDefinicionTarea) query.getSingleResult();
		 
		 return definicion;
	}
	
	public DtaDefinicionTarea devuelveTareaInicial(String dflCodigo) {
		DtaDefinicionTarea definicionTarea=new DtaDefinicionTarea();
		StringBuilder queryString = new StringBuilder();

		
		queryString.append("select dt.dta_id,dt.dta_codigo_origen ");
		queryString.append("from dta_definicion_tarea dt, rlc_tarea rt, dfl_definicion_flujo df ");
		queryString.append("where df.dfl_id=dt.dfl_id ");
		queryString.append("and df.dfl_codigo= :dflCodigo ");
		queryString.append("and dt.dta_id=rt.dta_id ");
		queryString.append("and rt.dta_id=rt.rlc_id_anterior ");
		queryString.append("group by dt.dta_id ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		 query.setParameter("dflCodigo", dflCodigo);
		 
		 List<DtaDefinicionTarea> definicionTareaList=(List<DtaDefinicionTarea>)query.getResultList();
		 
		 for (Object record : definicionTareaList) {
			    Object[] fields = (Object[]) record;
			    definicionTarea.setDtaId((Integer)fields[0]);
		 }
		 return definicionTarea;
	}
	
	public List<DtaDefinicionTarea> devuelveTareasTotalCuentas(Integer eventoId) {
		DtaDefinicionTarea definicionTarea=null;
		StringBuilder queryString = new StringBuilder();

		
		queryString.append("select dt.dta_id,dt.dta_codigo_origen,dt.dta_nombre_tarea,count(it.dta_id) "); 
		queryString.append("from int_instancia_tarea it, inf_instancia_flujo inf, dta_definicion_tarea dt ");
		queryString.append("where inf.inf_id=it.inf_id  ");
		queryString.append("and dt.dta_id= it.dta_id ");
		if (eventoId !=null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		queryString.append("and it.int_fecha_termino is null ");
		queryString.append("group by dt.dta_id,dt.dta_codigo_origen,dt.dta_nombre_tarea ");
		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
		 query.setParameter("eventoId", eventoId);
		}
		 
		 List<DtaDefinicionTarea> definicionTareaList=(List<DtaDefinicionTarea>)query.getResultList();
		 List<DtaDefinicionTarea> definicionTareaResultList= new ArrayList<>();
		 for (Object record : definicionTareaList) {
			 definicionTarea=new DtaDefinicionTarea();
			    Object[] fields = (Object[]) record;
			    definicionTarea.setDtaId((Integer)fields[0]);
			    definicionTarea.setDtaCodigoOrigen((String) fields[1]);
			    definicionTarea.setDtaNombreTarea((String) fields[2]);
				 BigInteger bi;
				 bi=(BigInteger)fields[3];
				 
			    definicionTarea.setDtaSlaTarea(bi.intValue());
			    definicionTareaResultList.add(definicionTarea);
		 }
		 return definicionTareaResultList;
	}
	
	public List<DtaDefinicionTarea> devuelveDefinicionTareaOrdenadasFlujo(String dflCodigo, int eveId){
		DtaDefinicionTarea definicionTarea=null;
		StringBuilder queryString = new StringBuilder();

		queryString.append("select dta.dta_id,dta.dta_codigo_origen,dta.dta_nombre_tarea from dta_definicion_tarea dta, rlc_tarea r,dfl_definicion_flujo df ");
		queryString.append("where dta.dfl_id=df.dfl_id ");
		queryString.append("and dta.dta_id=r.dta_id ");
		queryString.append("and df.dfl_codigo= :dflCodigo ");
		queryString.append("and df.EVE_ID = :eveId ");
		queryString.append("order by r.rlc_id_siguiente asc ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		query.setParameter("dflCodigo", dflCodigo);
		query.setParameter("eveId", eveId);
		 
		 List<DtaDefinicionTarea> definicionTareaList=(List<DtaDefinicionTarea>)query.getResultList();
		 List<DtaDefinicionTarea> definicionTareaResultList= new ArrayList<>();
		 
		 for (Object record : definicionTareaList) {
			 definicionTarea=new DtaDefinicionTarea();
			    Object[] fields = (Object[]) record;
			    definicionTarea.setDtaId((Integer)fields[0]);
			    definicionTarea.setDtaCodigoOrigen((String) fields[1]);
			    definicionTarea.setDtaNombreTarea((String) fields[2]);
				    definicionTareaResultList.add(definicionTarea);
		 }
		 return definicionTareaResultList;
	}
}
