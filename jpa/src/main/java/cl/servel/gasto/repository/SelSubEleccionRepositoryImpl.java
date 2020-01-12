package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.NivNivel;
import cl.servel.gasto.entity.SelSubEleccion;
import cl.servel.gasto.entity.TpoNivel;

public class SelSubEleccionRepositoryImpl implements SelSubEleccionRepositoryCustom {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	@Modifying
	public void actualizarSubeleccionCandidatos(Integer idEleccion) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("UPDATE sel_sub_eleccion ");
		queryString.append("SET sel_cantidad_candidatos=tbl.suma ");
		queryString.append("FROM (select s.niv_id,count(*) suma from sel_sub_eleccion s, can_candidato c ");
		queryString.append("where s.sel_id=c.sel_id ");
		queryString.append("and s.ele_id = ? ");
		queryString.append("group by s.niv_id) AS tbl ");
		queryString.append("WHERE sel_sub_eleccion.niv_id= tbl.niv_id ");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);

		query.executeUpdate();

	}
	
	@SuppressWarnings("unchecked")
	public List<SelSubEleccion> devuelveSubEleccionesEvento(Integer eventoId,List<Integer> usuarioIds, Integer dtaId, Integer usuarioId){
		StringBuilder queryString = new StringBuilder();
		

		queryString.append("select ");
		queryString.append("s.sel_id, ");
		queryString.append("s.ele_id, ");
		queryString.append("s.niv_id, ");
		queryString.append("s.tpo_niv_codigo, ");
		queryString.append("s.eve_id, ");
		queryString.append("s.sel_nombre, ");
		queryString.append("s.sel_eliminado, ");
		queryString.append("s.sel_modified, "); 
		queryString.append("s.sel_created, ");
		queryString.append("s.sel_cantidad_electores, ");
		queryString.append("s.sel_codigo_origen, ");
		queryString.append("s.sel_cantidad_candidatos ");
		queryString.append("from ");
		queryString.append("can_candidato c, ");
		queryString.append("sel_sub_eleccion s, ");
		queryString.append("inf_instancia_flujo inf, ");
		queryString.append("ele_eleccion e, int_instancia_tarea ins ");
		queryString.append("where ");
		queryString.append("c.sel_id = s.sel_id ");
		queryString.append("and inf.can_id = c.can_id ");
		queryString.append("and e.ele_id = s.ele_id ");
		queryString.append("and ins.inf_id=inf.inf_id ");
		
		if (eventoId !=null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		if (usuarioIds !=null) {
			queryString.append("and (inf.usu_id_actual in :usuarioIds or ins.usu_id in :usuarioIds) ");
		}
		if (usuarioId !=null) {
			queryString.append("and ins.usu_id= :usuarioId ");
		}
		
		if (dtaId !=null) {
			queryString.append("and ins.dta_id= :dtaId ");
		}
		 queryString.append("group by ");
		queryString.append("s.sel_id ");
	
	
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
	
	List<SelSubEleccion> subeleccionesList= (List<SelSubEleccion>)query.getResultList();
	List<SelSubEleccion> subeleccionesResultList = new ArrayList<>();
	SelSubEleccion subeleccion= null;
	TpoNivel tipoNivel=null;
	EveEventoEleccionario evento=null;
	EleEleccion eleEleccion=null;
	NivNivel nivel=null;
	for (Object record : subeleccionesList) {
		Object[] fields = (Object[]) record;
		subeleccion= new SelSubEleccion();
		subeleccion.setSelId((Integer)fields[0]);
		if (fields[1] !=null) {
			eleEleccion= new EleEleccion();
			eleEleccion.setEleId((Integer)fields[1]);
			subeleccion.setEleEleccion(eleEleccion);
		}
		
		
		if (fields[2]!=null) {
			nivel= new NivNivel();
			nivel.setNivId((Integer)fields[2]);
			subeleccion.setNivNivel(nivel);
		}
		
		if (fields[3]!=null) {
			tipoNivel=new TpoNivel();
			tipoNivel.setTpoNivCodigo((String)fields[3]);
			subeleccion.setTpoNivel(tipoNivel);
		}
		
		if (fields[4]!=null) {
			evento= new EveEventoEleccionario();
			evento.setEveId((Integer)fields[4]);
			subeleccion.setEveEventoEleccionario(evento);
			
		}
		
		subeleccion.setSelNombre((String) fields[5]);
		subeleccion.setSelEliminado((Boolean) fields[6]);
		subeleccion.setSelModified((Date) fields[7]);
		subeleccion.setSelCreated((Date) fields[8]);
		subeleccion.setSelCantidadElectores((Integer)fields[9]);
		subeleccion.setSelCodigoOrigen((String)fields[10]);
		subeleccion.setSelCantidadCandidatos((Integer)fields[11]);
		
		subeleccionesResultList.add(subeleccion);
		
		}
	return subeleccionesResultList;
		
	} 

@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("delete from sel_sub_eleccion where ele_id= ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
}
