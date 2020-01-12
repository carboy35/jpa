package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.DtaDefinicionTarea;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.InbInstanciaBandeja;
import cl.servel.gasto.entity.InfInstanciaFlujo;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.UsuUsuarios;

public class InfInstanciaFlujoRepositoryImpl implements InfInstanciaFlujoRepositoryCustom {
	
//	private static final Log LOG = LogFactory.getLog(InfInstanciaFlujoRepositoryImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	public Integer getUsuarioMenosCargaFromList(List<Integer> usuarios) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select res.usuario ");
		queryString.append("from ( ");
		queryString.append("select inf.usu_id_actual as usuario, ");
		queryString.append("sum(inf.inf_total_lineas) as lineas ");
		queryString.append("from inf_instancia_flujo inf  ");
		queryString.append("where inf.usu_id_actual in ( ");
		
		
		for (int count = 0; count <= (usuarios.size() -1); count++) {
			queryString.append(usuarios.get(count));
			
			if (count < (usuarios.size() - 1)) {
				queryString.append(",");
			}
		};
		
		StringUtils.removeEnd(queryString.toString(), ",");
		
		queryString.append(") ");
		queryString.append("group by usuario  ");
		queryString.append("order by lineas ");
		queryString.append("limit 1 ");
		queryString.append(") as res ");

		Query query = entityManager.createNativeQuery(queryString.toString());

		Integer usuarioId = null; 
		
		try {
			usuarioId = (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			usuarioId = usuarios.get(0);
		}
		
		return usuarioId;
	}
	

	@SuppressWarnings("unchecked")
	public List<InfInstanciaFlujo> getByEventoUsuarios(Integer eventoId,List<Integer> usuarioIds, Integer dtaId,String estado){
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("SELECT inf.inf_id,inf.eve_id,inf.inf_fecha_inicio,inf.inf_fecha_termino,inf.inf_estado_flujo,inf.can_id, inf.par_id, ");
		queryString.append("inf.inf_con_observacion,inf.inf_con_respuesta,inf.inf_fecha_asignacion,inf.usu_id_actual,inf.dta_id,inf.inf_total_lineas, ");
		queryString.append("inf.usu_usuario_subrogado,inf.inb_id ");
		queryString.append("FROM inf_instancia_flujo inf, int_instancia_tarea ins ");
		queryString.append("where inf.inf_id=ins.inf_id ");
		if (usuarioIds!=null) {
		queryString.append("and inf.usu_id_actual in :usuarioIds ");
		}
		if (eventoId != null) {
		queryString.append("and inf.eve_id = :eventoId ");
		}
		queryString.append("and (ins.inf_id,ins.int_id) in (select inf.inf_id,max(i.int_id) ");
		queryString.append("			from int_instancia_tarea i, inf_instancia_flujo inf where i.inf_id=inf.inf_id group by inf.inf_id) ");
		if (dtaId !=null) {
			queryString.append("	and  ins.dta_id = :dtaId ");
		}
		if (estado !=null) {
			queryString.append(" and inf.inf_estado_flujo = :estado ");
		}
		

		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
		}
		if (usuarioIds !=null) {
			query.setParameter("usuarioIds", usuarioIds);
		}
		if (dtaId !=null) {
			query.setParameter("dtaId", dtaId);
		}
		
		List<InfInstanciaFlujo> instanciaFlujoList=(List<InfInstanciaFlujo>) query.getResultList();
		List<InfInstanciaFlujo> instanciaFlujoResultList= new ArrayList<>();
		InfInstanciaFlujo instanciaFlujo=null;
		EveEventoEleccionario eveEventoEleccionario=null;
		CanCandidato canCandidato=null;
		ParPartido parPartido=null;
		UsuUsuarios usuUsuarios=null;
		UsuUsuarios usuUsuarioSubrogado=null;
		DtaDefinicionTarea dtaDefinicionTarea=null;
		InbInstanciaBandeja inbInstanciaBandeja=null;
		for (Object record : instanciaFlujoList) {
			Object[] fields = (Object[]) record;
			instanciaFlujo= new InfInstanciaFlujo();
			instanciaFlujo.setInfId((Integer)fields[0]);
			if (fields[1] !=null) {
				eveEventoEleccionario= new EveEventoEleccionario();
				eveEventoEleccionario.setEveId((Integer)fields[1]);
				instanciaFlujo.setEveEventoEleccionario(eveEventoEleccionario);
			}
			instanciaFlujo.setInfFechaInicio((Date)fields[2]);
			instanciaFlujo.setInfFechaTermino((Date)fields[3]);
			instanciaFlujo.setInfEstadoFlujo((String)fields[4]);
			if (fields[5] !=null) {
				canCandidato= new CanCandidato();
				canCandidato.setCanId((Integer)fields[5]);
				instanciaFlujo.setCanCandidato(canCandidato);
			}
			
			if (fields[6]!=null) {
				parPartido=new ParPartido();
				parPartido.setParId((Integer) fields[6]);
				instanciaFlujo.setParPartido(parPartido);
			}
			instanciaFlujo.setInfConObservacion((Boolean)fields[7]);
			instanciaFlujo.setInfConRespuesta((Boolean)fields[8]);
			instanciaFlujo.setInfFechaAsignacion((Date)fields[9]);
			
			if (fields[10]!=null) {
				usuUsuarios= new UsuUsuarios();
				usuUsuarios.setUsuId((Integer)fields[10]);
				instanciaFlujo.setUsuUsuarios(usuUsuarios);
			}
			if (fields[11]!=null) {
				dtaDefinicionTarea= new DtaDefinicionTarea();
				dtaDefinicionTarea.setDtaId((Integer)fields[11]);
				instanciaFlujo.setDtaDefinicionTarea(dtaDefinicionTarea);
			}
			instanciaFlujo.setInfTotalLineas((Integer)fields[12]);
			
			if (fields[13]!=null) {
				usuUsuarioSubrogado= new UsuUsuarios();
				usuUsuarioSubrogado.setUsuId((Integer)fields[13]);
				instanciaFlujo.setUsuUsuarios(usuUsuarioSubrogado);
			}
			if (fields[14]!=null) {
				inbInstanciaBandeja= new InbInstanciaBandeja();
				inbInstanciaBandeja.setInbId((Integer)fields[14]);
				instanciaFlujo.setInbInstanciaBandeja(inbInstanciaBandeja);
			}
			instanciaFlujoResultList.add(instanciaFlujo);
		}
		return instanciaFlujoResultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InfInstanciaFlujo> getByEventoTipos(Integer idEvento, Integer idTipoEleccion, Integer idTipoEvento) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT DISTINCT ");
		queryString.append("inf.inf_id,");
		queryString.append("inf.inf_fecha_inicio,");
		queryString.append("inf.inf_fecha_termino,");
		queryString.append("inf.inf_estado_flujo,");
		queryString.append("inf.can_id,");
		queryString.append("inf.par_id,");
		queryString.append("inf.inf_con_observacion,");
		queryString.append("inf.inf_con_respuesta,");
		queryString.append("inf.inf_fecha_asignacion,");
		queryString.append("inf.usu_id_actual,");
		queryString.append("inf.dta_id,");
		queryString.append("inf.inf_total_lineas,");
		queryString.append("inf.usu_usuario_subrogado,");
		queryString.append("inf.inb_id ");
		queryString.append("FROM inf_instancia_flujo inf ");
		queryString.append("INNER JOIN eve_evento_eleccionario eve ON eve.eve_id = inf.eve_id ");
		queryString.append("INNER JOIN sel_sub_eleccion sel ON eve.eve_id = sel.eve_id ");
		queryString.append("INNER JOIN ele_eleccion ele ON ele.ele_id = sel.ele_id ");
		queryString.append("WHERE inf.eve_id = :idEvento ");
		queryString.append("AND ele.tpo_eleccion_id = :idTipoEleccion ");
		queryString.append("AND ele.tpo_evento_id = :idTipoEvento ");
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("idEvento", idEvento);
		query.setParameter("idTipoEleccion", idTipoEleccion);
		query.setParameter("idTipoEvento", idTipoEvento);
		InfInstanciaFlujo instanciaFlujo=null;
		EveEventoEleccionario eveEventoEleccionario=null;
		CanCandidato canCandidato=null;
		ParPartido parPartido=null;
		UsuUsuarios usuUsuarios=null;
		UsuUsuarios usuUsuarioSubrogado=null;
		DtaDefinicionTarea dtaDefinicionTarea=null;
		InbInstanciaBandeja inbInstanciaBandeja=null;
		List<InfInstanciaFlujo> instanciaFlujoResultList= new ArrayList<InfInstanciaFlujo>();
		List<InfInstanciaFlujo> instanciaFlujoList=(List<InfInstanciaFlujo>) query.getResultList();
		for (Object record : instanciaFlujoList) {
			Object[] fields = (Object[]) record;
			instanciaFlujo= new InfInstanciaFlujo();
		
			eveEventoEleccionario= new EveEventoEleccionario();
			eveEventoEleccionario.setEveId(idEvento);
			instanciaFlujo.setEveEventoEleccionario(eveEventoEleccionario);
			
			instanciaFlujo.setInfId((Integer)fields[0]);
			instanciaFlujo.setInfFechaInicio((Date) fields[1]);
			if(fields[2]!=null) {
				instanciaFlujo.setInfFechaTermino((Date) fields[2]);	
			}
			instanciaFlujo.setInfEstadoFlujo((String) fields[3]);
			if (fields[4] !=null) {
				canCandidato= new CanCandidato();
				canCandidato.setCanId((Integer)fields[4]);
				instanciaFlujo.setCanCandidato(canCandidato);
			}
			
			if (fields[5]!=null) {
				parPartido=new ParPartido();
				parPartido.setParId((Integer) fields[5]);
				instanciaFlujo.setParPartido(parPartido);
			}
			instanciaFlujo.setInfConObservacion((Boolean)fields[6]);
			instanciaFlujo.setInfConRespuesta((Boolean)fields[7]);
			instanciaFlujo.setInfFechaAsignacion((Date)fields[8]);
			
			if (fields[9]!=null) {
				usuUsuarios= new UsuUsuarios();
				usuUsuarios.setUsuId((Integer)fields[9]);
				instanciaFlujo.setUsuUsuarios(usuUsuarios);
			}
			if (fields[10]!=null) {
				dtaDefinicionTarea= new DtaDefinicionTarea();
				dtaDefinicionTarea.setDtaId((Integer)fields[10]);
				instanciaFlujo.setDtaDefinicionTarea(dtaDefinicionTarea);
			}
			instanciaFlujo.setInfTotalLineas((Integer)fields[11]);
			
			if (fields[12]!=null) {
				usuUsuarioSubrogado= new UsuUsuarios();
				usuUsuarioSubrogado.setUsuId((Integer)fields[12]);
				instanciaFlujo.setUsuUsuarios(usuUsuarioSubrogado);
			}
			if (fields[13]!=null) {
				inbInstanciaBandeja= new InbInstanciaBandeja();
				inbInstanciaBandeja.setInbId((Integer)fields[13]);
				instanciaFlujo.setInbInstanciaBandeja(inbInstanciaBandeja);
			}
			instanciaFlujoResultList.add(instanciaFlujo);
		}
		return instanciaFlujoResultList;
	}
	
	
	
}
