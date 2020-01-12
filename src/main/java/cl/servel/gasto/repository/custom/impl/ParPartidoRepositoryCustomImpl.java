package cl.servel.gasto.repository.custom.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.PacPacto;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.TpoEvento;
import cl.servel.gasto.repository.custom.ParPartidoRepositoryCustom;


@SuppressWarnings("unchecked")
public class ParPartidoRepositoryCustomImpl implements ParPartidoRepositoryCustom {
	private static final Log LOG = LogFactory.getLog(ParPartidoRepositoryCustomImpl.class);
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<ParPartido> buscarPartidos(Integer idEvento, Integer tipoEvento, Integer idEleccion, List<String> campos, String valor, Boolean estado,String definicionTarea, List<String> filtros,Integer idTipoEleccion) {
		Query query = null;
		
		if (filtros.contains("respuestaObservacion")) {
			query = queryPartidosRegistroRespuestasObservaciones(idEvento, tipoEvento, idEleccion, campos, valor, estado, filtros);
		} else if (filtros.contains("cuentaCandidatoPartido")) {
			query = entityManager.createNativeQuery(this.devuelveConsultaPartidos(idEvento, tipoEvento,  campos, valor, estado,idTipoEleccion, filtros));
		}else {
			query = entityManager.createNativeQuery(this.devuelveConsultaPartidos(idEvento, tipoEvento, idEleccion, campos, valor, estado, definicionTarea,filtros));
		}
		
		List<Object[]> partidosList = (List<Object[]>) query.getResultList();

		List<ParPartido> partidosListResult = new ArrayList<>();
		ParPartido parPartido = null;
		EveEventoEleccionario eveEventoEleccionario = null;
		EleEleccion eleEleccion = null;
		TpoEvento tpoEvento = null;
		PacPacto pacto=null;
//		0 q.par_id, 
//		1 q.eve_id,
//		2 q.eve_nombre,
//		3 q.tpo_evento_id,
//		4 q.tpo_evento_nombre,
//		5 q.ele_id,
//		6 q.tpo_ele_nombre, 
//		7 q.par_nombre, 
//		8 q.par_sigla, 
//		9 q.par_eliminado, 
//		10 q.par_rut 
//		11 q.pac_id

		for (Object[] fields : partidosList) {
			parPartido = new ParPartido();

			parPartido.setParId((Integer) fields[0]);

			eveEventoEleccionario = new EveEventoEleccionario();
			eveEventoEleccionario.setEveId((Integer) fields[1]);
			eveEventoEleccionario.setEveNombre((String) fields[2]);

			tpoEvento = new TpoEvento();
			tpoEvento.setTpoEventoId((Integer) fields[3]);
			tpoEvento.setTpoEventoNombre((String) fields[4]);

			eleEleccion = new EleEleccion();
			eleEleccion.setEleId((Integer) fields[5]);
			eleEleccion.setEleNombre((String) fields[6]);
			eleEleccion.setTpoEvento(tpoEvento);
			eleEleccion.setEveEventoEleccionario(eveEventoEleccionario);

			parPartido.setEleEleccion(eleEleccion);

			parPartido.setParNombre((String) fields[7]);
			parPartido.setParSigla((String) fields[8]);
			parPartido.setParEliminado((Boolean) fields[9]);
			parPartido.setParRut((String) fields[10]);
			if(fields[11]!=null) {
				pacto=new PacPacto();
				pacto.setPacId((Integer) fields[11]);
				parPartido.setPacPacto(pacto);
			}
			partidosListResult.add(parPartido);
		}

		return partidosListResult;
	}
	
	private Query queryPartidosRegistroRespuestasObservaciones(Integer idEvento, Integer idTipoEvento, Integer idEleccion, List<String> campos, String valor, Boolean estado,List<String> filtros) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("SELECT Q.PAR_ID,  ");
		queryString.append("	   Q.EVE_ID, ");
		queryString.append("	   Q.EVE_NOMBRE, ");
		queryString.append("  	   Q.TPO_EVENTO_ID, ");
		queryString.append("	   Q.TPO_EVENTO_NOMBRE, ");
		queryString.append("	   Q.ELE_ID, ");
		queryString.append("       Q.TPO_ELE_NOMBRE,  ");
		queryString.append("  	   Q.PAR_NOMBRE,  ");
		queryString.append("	   Q.PAR_SIGLA,  ");
		queryString.append("	   Q.PAR_ELIMINADO, "); 
		queryString.append("	   Q.PAR_RUT,   ");
		queryString.append("	   Q.PAC_ID   ");
		queryString.append("  FROM (SELECT PAR.PAR_ID, ");
		queryString.append("	  		   EVE.EVE_ID, ");
		queryString.append("	  		   EVE.EVE_NOMBRE, ");
		queryString.append("	  		   TEV.TPO_EVENTO_ID, ");
		queryString.append("	  		   TEV.TPO_EVENTO_NOMBRE, ");
		queryString.append("	  		   ELE.ELE_ID, ");
		queryString.append("	  		   TEL.TPO_ELE_NOMBRE, ");
		queryString.append("	  		   PAR.PAR_NOMBRE, ");
		queryString.append("	  		   PAR.PAR_SIGLA, ");
		queryString.append("	  		   PAR.PAR_ELIMINADO, ");
		queryString.append("	  		   PAR.PAR_RUT, ");
		queryString.append("	  		   PAR.PAC_ID ");
		queryString.append("	      FROM PAR_PARTIDO PAR,  ");
		queryString.append("	    	   EVE_EVENTO_ELECCIONARIO EVE, "); 
		queryString.append("	    	   TPO_EVENTO TEV, ");
		queryString.append("	    	   ELE_ELECCION ELE,  ");
		queryString.append("	    	   TPO_ELECCION TEL,  ");
		queryString.append("	    	   INF_INSTANCIA_FLUJO INF,  ");
		queryString.append("	    	   INT_INSTANCIA_TAREA INT ");
		queryString.append("	     WHERE EVE.EVE_ID = :eveId ");
		queryString.append("	   	   AND PAR.ELE_ID = ELE.ELE_ID ");
		queryString.append("	   	   AND ELE.EVE_ID_EVE = EVE.EVE_ID ");
		queryString.append("	   	   AND ELE.TPO_ELECCION_ID = TEL.TPO_ELECCION_ID ");
		queryString.append("	   	   AND ELE.TPO_EVENTO_ID = TEV.TPO_EVENTO_ID ");
		queryString.append("	       AND INF.PAR_ID = PAR.PAR_ID ");
		queryString.append("	       AND INF.DTA_ID NOT IN ( ");
		queryString.append("	   								SELECT DEA.DTA_ID ");
		queryString.append("	     							  FROM DEA_DEFINICION_ACTIVIDAD DEA ");
		queryString.append("	    							 WHERE DEA.DEA_CODIGO = 'NO_RECIBE_RESPUESTAS'  ");
		queryString.append("	    						  ) ");
		queryString.append("	      GROUP BY 	PAR.PAR_ID, ");
		queryString.append("	  		  	   	EVE.EVE_ID, ");
		queryString.append("	  		  		EVE.EVE_NOMBRE, ");
		queryString.append("	  		  		TEV.TPO_EVENTO_ID, ");
		queryString.append("	  		  		TEV.TPO_EVENTO_NOMBRE, ");
		queryString.append("	  		  		ELE.ELE_ID, ");
		queryString.append("	  		  		TEL.TPO_ELE_NOMBRE, ");
		queryString.append("	  		  		PAR.PAR_NOMBRE, ");
		queryString.append("	  		  		PAR.PAR_SIGLA, ");
		queryString.append("	  		  		PAR.PAR_ELIMINADO, ");
		queryString.append("	  		  		PAR.PAR_RUT, ");
		queryString.append("	  		  		PAR.PAC_ID ");
		queryString.append("	    ) Q ");
		queryString.append("	WHERE Q.TPO_EVENTO_ID = :idTipoEvento");
		queryString.append("	  AND (");
		
		AtomicInteger count = new AtomicInteger(0);
		campos.forEach(campo -> {
			if (count.getAndIncrement() > 0) {
				queryString.append("OR ");
			}
			
			queryString.append("UPPER (Q.").append(campo).append(") LIKE UPPER (:valor) ");
		});
		
		queryString.append(")");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		query.setParameter("eveId", idEvento);
		query.setParameter("valor", "%" + valor + "%");
		query.setParameter("idTipoEvento", idTipoEvento);
		
		return query;
	}

	private String devuelveConsultaPartidos(Integer idEvento, Integer tipoEvento, Integer idEleccion,
			List<String> campos, String valor, Boolean estado,String definicionTarea,List<String> filtros) {
		StringBuilder queryString = new StringBuilder();
				
		queryString.append("select  ");
		queryString.append("q.par_id, ");
		queryString.append("q.eve_id,");
		queryString.append("q.eve_nombre,");
		queryString.append("q.tpo_evento_id,");
		queryString.append("q.tpo_evento_nombre,");
		queryString.append("q.ele_id,");
		queryString.append("q.tpo_ele_nombre, ");
		queryString.append("q.par_nombre, ");
		queryString.append("q.par_sigla, ");
		queryString.append("q.par_eliminado, ");
		queryString.append("q.par_rut,  ");
		queryString.append("q.pac_id  ");
		queryString.append("from ( ");
		queryString.append("select  ");
		queryString.append("p.par_id, ");
		queryString.append("ev.eve_id,");
		queryString.append("ev.eve_nombre,");
		queryString.append("el.tpo_evento_id,");
		queryString.append("te.tpo_evento_nombre, ");
		queryString.append("el.ele_id,");
		queryString.append("el.ele_nombre,");
		queryString.append("tel.tpo_ele_nombre,");
		queryString.append("p.par_nombre, ");
		queryString.append("p.par_sigla, ");
		queryString.append("p.par_eliminado, ");
		queryString.append("p.par_rut, ");
		queryString.append("p.pac_id ");
		queryString.append("FROM  ");
		queryString.append("par_partido p, ");
		queryString.append("ele_eleccion el,");
		queryString.append("eve_evento_eleccionario ev,");
		queryString.append("tpo_eleccion tel,");
		queryString.append("tpo_evento te ");
		
		if (filtros.contains("rendicion")) {
			queryString.append(", ren_rendicion r ");
		}else if (filtros.contains("ingresoRecursoPartido") || filtros.contains("ingresoSentenciasTricel")) {
			queryString.append(", int_instancia_tarea ins,dta_definicion_tarea dta,inf_instancia_flujo inf,ren_rendicion ren, det_detalle_rendicion detren,tpo_seccion_rendicion tposecc ");
		}
		
		queryString.append("where  ");
		queryString.append("p.ele_id = el.ele_id and ");
		queryString.append("el.eve_id_eve = ev.eve_id and ");
		queryString.append("el.tpo_eleccion_id = tel.tpo_eleccion_id and ");
		queryString.append("el.tpo_evento_id = te.tpo_evento_id");
		
		if (filtros.contains("rendicion")) {
			queryString.append(" and r.par_id = p.par_id ");
		}else if (filtros.contains("ingresoRecursoPartido") || filtros.contains("ingresoSentenciasTricel")) {		
			queryString.append(" and ins.dta_id=dta.dta_id ");
			if (definicionTarea !=null) {
				queryString.append(" and dta.dta_codigo_origen= '" + definicionTarea + "'"); 
			}
			queryString.append(" and inf.inf_id= ins.inf_id ");
			queryString.append(" and inf.par_id=p.par_id ");
			
			queryString.append(" and ren.par_id=p.par_id ");
			queryString.append(" and ren.ren_id=detren.ren_id ");
			queryString.append(" and detren.id_tipo_seccion=tposecc.tpo_seccion_rendicion_id ");
			queryString.append(" and tposecc.tpo_nombre_seccion= 'totalReembolso'");
		}

		queryString.append(" and p.par_eliminado = " + !estado);

		if (idEvento != null && tipoEvento == null && idEleccion == null)
			queryString.append(" and el.eve_id_eve =" + idEvento);

		if (idEvento != null && tipoEvento != null && idEleccion == null)
			queryString.append(" and el.eve_id_eve =" + idEvento + " and el.tpo_evento_id =" + tipoEvento);

		if (idEvento != null && tipoEvento != null && idEleccion != null)
			queryString.append(" and el.eve_id_eve =" + idEvento + " and el.tpo_evento_id = " + tipoEvento + " and el.ele_id =" + idEleccion);

		queryString.append(") q ");
		queryString.append("where ");

		AtomicInteger c = new AtomicInteger(0);
		campos.forEach(campo -> {
			if (c.get() == 0)
				queryString.append(" upper(cast(" + campo + " as varchar)) like upper('%" + valor + "%')");
			else
				queryString.append(" OR upper(cast(" + campo + " as varchar)) like upper('%" + valor + "%')");

			c.getAndIncrement();
		});

		return queryString.toString();

	}
	private String devuelveConsultaPartidos(Integer idEvento, Integer tipoEvento, 
			List<String> campos, String valor, Boolean estado,Integer idTipoEleccion,List<String> filtros) {
		StringBuilder queryString = new StringBuilder();
				
		queryString.append("select  ");
		queryString.append("q.par_id, ");
		queryString.append("q.eve_id,");
		queryString.append("q.eve_nombre,");
		queryString.append("q.tpo_evento_id,");
		queryString.append("q.tpo_evento_nombre,");
		queryString.append("q.ele_id,");
		queryString.append("q.tpo_ele_nombre, ");
		queryString.append("q.par_nombre, ");
		queryString.append("q.par_sigla, ");
		queryString.append("q.par_eliminado, ");
		queryString.append("q.par_rut,  ");
		queryString.append("q.pac_id  ");
		queryString.append("from ( ");
		queryString.append("select  ");
		queryString.append("p.par_id, ");
		queryString.append("ev.eve_id,");
		queryString.append("ev.eve_nombre,");
		queryString.append("el.tpo_evento_id,");
		queryString.append("te.tpo_evento_nombre, ");
		queryString.append("el.ele_id,");
		queryString.append("el.ele_nombre,");
		queryString.append("tel.tpo_ele_nombre,");
		queryString.append("p.par_nombre, ");
		queryString.append("p.par_sigla, ");
		queryString.append("p.par_eliminado, ");
		queryString.append("p.par_rut, ");
		queryString.append("p.pac_id ");
		queryString.append("FROM  ");
		queryString.append("par_partido p, ");
		queryString.append("ele_eleccion el,");
		queryString.append("eve_evento_eleccionario ev,");
		queryString.append("tpo_eleccion tel,");
		queryString.append("tpo_evento te ");

		
		queryString.append("where  ");
		queryString.append("p.ele_id = el.ele_id and ");
		queryString.append("el.eve_id_eve = ev.eve_id and ");
		queryString.append("el.tpo_eleccion_id = tel.tpo_eleccion_id and ");
		queryString.append("el.tpo_evento_id = te.tpo_evento_id");
		
		queryString.append(" and p.par_eliminado = " + !estado);

		if (idEvento != null )
			queryString.append(" and el.eve_id_eve =" + idEvento);

		if ( tipoEvento != null)
			queryString.append(" and el.tpo_evento_id = " + tipoEvento);

		if ( idTipoEleccion != null)
			queryString.append( " and el.tpo_eleccion_id =" + idTipoEleccion);
		
		queryString.append(") q ");
		queryString.append("where ");

		AtomicInteger c = new AtomicInteger(0);
		campos.forEach(campo -> {
			if (c.get() == 0)
				queryString.append(" upper(cast(" + campo + " as varchar)) like upper('%" + valor + "%')");
			else
				queryString.append(" OR upper(cast(" + campo + " as varchar)) like upper('%" + valor + "%')");

			c.getAndIncrement();
		});

		return queryString.toString();

	}
	public List<ParPartido> devuelveConsultaPartidosInstanciaFlujo(Integer eventoId, List<Integer> usuarioIds,Integer dtaId,Integer usuarioId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ");
		queryString.append("p.par_id, ");
		queryString.append("p.pac_id, ");
		queryString.append("p.ele_id, ");
		queryString.append("p.par_nombre, ");
		queryString.append("p.par_sigla, ");
		queryString.append("p.par_created, ");
		queryString.append("p.par_codigo_origen, ");
		queryString.append("p.par_fecha_constitucion, ");
		queryString.append("p.par_fecha_disolucion, ");
		queryString.append("p.par_eliminado, ");
		queryString.append("p.par_rut, ");
		queryString.append("p.par_nombre_adm, ");
		queryString.append("p.par_mail, ");
		queryString.append("p.par_direccion, ");
		queryString.append("p.par_ciudad, ");
		queryString.append("p.par_telefono_celu, ");
		queryString.append("p.par_estado ");
		queryString.append("from par_partido p, ");
		queryString.append("inf_instancia_flujo inf,int_instancia_tarea ins ");
		queryString.append("where p.par_id=inf.par_id ");
		queryString.append("and ins.inf_id=inf.inf_id ");
		if (usuarioId ==null) {
			queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
			queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
		}
		
		if (eventoId !=null) {
			queryString.append("and  inf.eve_id= :eventoId ");
		}
		if (usuarioIds !=null) {
			queryString.append("and  inf.usu_id_actual in :usuarioIds ");
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
		
		List<ParPartido> partidoList= (List<ParPartido>) query.getResultList();
		List<ParPartido> partidoListResult=	new ArrayList<>();
		ParPartido parPartido=null;
		EleEleccion eleEleccion=null;
		PacPacto pacto =null;
		
		for (Object record : partidoList) {
			Object[] fields = (Object[]) record;
			parPartido = new ParPartido();

			parPartido.setParId((Integer) fields[0]);
			
			
			if (fields[1] !=null) {
				pacto= new PacPacto();
				pacto.setPacId((Integer)fields[1]);
				parPartido.setPacPacto(pacto);
				parPartido.setPacPacto(pacto);
			}
			
			if (fields[2] !=null) {
				eleEleccion = new EleEleccion();
				eleEleccion.setEleId((Integer) fields[2]);
				parPartido.setEleEleccion(eleEleccion);
			}
			
			parPartido.setParNombre((String)fields[3]);
			
			parPartido.setParSigla((String)fields[4]);
			
			parPartido.setParCreated((Date)fields[5]);
			
			parPartido.setParCodigoOrigen((String)fields[6]);
			
			parPartido.setParFechaConstitucion((Date) fields[7]);
			
			parPartido.setParFechaDisolucion((Date) fields[8]);
			
			parPartido.setParEliminado((Boolean) fields[9]);
			
			parPartido.setParRut((String) fields[10]);
			
			parPartido.setParNombreAdm((String) fields[11]);
			
			parPartido.setParMail((String) fields[12]);
			
			parPartido.setParDireccion((String) fields[13]);
			
			parPartido.setParCiudad((String) fields[14]);
			
			parPartido.setParTelefonoCelu((String) fields[15]);
			
			parPartido.setParEstado((String) fields[16]);
			
			partidoListResult.add(parPartido);
		}
		return partidoListResult;
	}
	
	public List<ParPartido> devuelvePartidosCandidatoRendicion(Integer eventoId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ");
		queryString.append("p.par_id, ");
		queryString.append("p.pac_id, ");
		queryString.append("p.ele_id, ");
		queryString.append("p.par_nombre, ");
		queryString.append("p.par_sigla, ");
		queryString.append("p.par_created, ");
		queryString.append("p.par_codigo_origen, ");
		queryString.append("p.par_fecha_constitucion, ");
		queryString.append("p.par_fecha_disolucion, ");
		queryString.append("p.par_eliminado, ");
		queryString.append("p.par_rut, ");
		queryString.append("p.par_nombre_adm, ");
		queryString.append("p.par_mail, ");
		queryString.append("p.par_direccion, ");
		queryString.append("p.par_ciudad, ");
		queryString.append("p.par_telefono_celu, ");
		queryString.append("p.par_estado ");
		queryString.append("from ren_rendicion r,can_candidato c, par_partido p ");
		queryString.append("where r.can_id=c.can_id ");
		queryString.append("and  c.par_id= p.par_id ");
		if (eventoId !=null) {
			queryString.append("and  r.eve_id= :eventoId ");
		}
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
		}
		
		List<ParPartido> partidoList= (List<ParPartido>) query.getResultList();
		List<ParPartido> partidoListResult=	new ArrayList<>();
		ParPartido parPartido=null;
		EleEleccion eleEleccion=null;
		PacPacto pacto =null;
		
		for (Object record : partidoList) {
			Object[] fields = (Object[]) record;
			parPartido = new ParPartido();

			parPartido.setParId((Integer) fields[0]);
			
			
			if (fields[1] !=null) {
				pacto= new PacPacto();
				pacto.setPacId((Integer)fields[1]);
				parPartido.setPacPacto(pacto);
				parPartido.setPacPacto(pacto);
			}
			
			if (fields[2] !=null) {
				eleEleccion = new EleEleccion();
				eleEleccion.setEleId((Integer) fields[2]);
				parPartido.setEleEleccion(eleEleccion);
			}
			
			parPartido.setParNombre((String)fields[3]);
			
			parPartido.setParSigla((String)fields[4]);
			
			parPartido.setParCreated((Date)fields[5]);
			
			parPartido.setParCodigoOrigen((String)fields[6]);
			
			parPartido.setParFechaConstitucion((Date) fields[7]);
			
			parPartido.setParFechaDisolucion((Date) fields[8]);
			
			parPartido.setParEliminado((Boolean) fields[9]);
			
			parPartido.setParRut((String) fields[10]);
			
			parPartido.setParNombreAdm((String) fields[11]);
			
			parPartido.setParMail((String) fields[12]);
			
			parPartido.setParDireccion((String) fields[13]);
			
			parPartido.setParCiudad((String) fields[14]);
			
			parPartido.setParTelefonoCelu((String) fields[15]);
			
			parPartido.setParEstado((String) fields[16]);
			
			partidoListResult.add(parPartido);
		}
		return partidoListResult;
	}
	
	public List<ParPartido> devuelvePartidosCandidatoInstanciaFlujo(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ");
		queryString.append("p.par_id, ");
		queryString.append("p.pac_id, ");
		queryString.append("p.ele_id, ");
		queryString.append("p.par_nombre, ");
		queryString.append("p.par_sigla, ");
		queryString.append("p.par_created, ");
		queryString.append("p.par_codigo_origen, ");
		queryString.append("p.par_fecha_constitucion, ");
		queryString.append("p.par_fecha_disolucion, ");
		queryString.append("p.par_eliminado, ");
		queryString.append("p.par_rut, ");
		queryString.append("p.par_nombre_adm, ");
		queryString.append("p.par_mail, ");
		queryString.append("p.par_direccion, ");
		queryString.append("p.par_ciudad, ");
		queryString.append("p.par_telefono_celu, ");
		queryString.append("p.par_estado ");
		queryString.append("from inf_instancia_flujo inf,can_candidato c, par_partido p, int_instancia_tarea ins ");
		queryString.append("where inf.can_id=c.can_id ");
		queryString.append("and  c.par_id= p.par_id ");
		queryString.append("and ins.inf_id=inf.inf_id ");
		if (usuarioId ==null) {
		queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
		queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
		}
		if (eventoId !=null) {
			queryString.append("and  inf.eve_id= :eventoId ");
		}
		if (usuarioIds!=null) {
			queryString.append("and  inf.usu_id_actual in :usuarioIds ");
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
		
		
		List<ParPartido> partidoList= (List<ParPartido>) query.getResultList();
		List<ParPartido> partidoListResult=	new ArrayList<>();
		ParPartido parPartido=null;
		EleEleccion eleEleccion=null;
		PacPacto pacto =null;
		
		for (Object record : partidoList) {
			Object[] fields = (Object[]) record;
			parPartido = new ParPartido();

			parPartido.setParId((Integer) fields[0]);
			
			
			if (fields[1] !=null) {
				pacto= new PacPacto();
				pacto.setPacId((Integer)fields[1]);
				parPartido.setPacPacto(pacto);
				parPartido.setPacPacto(pacto);
			}
			
			if (fields[2] !=null) {
				eleEleccion = new EleEleccion();
				eleEleccion.setEleId((Integer) fields[2]);
				parPartido.setEleEleccion(eleEleccion);
			}
			
			parPartido.setParNombre((String)fields[3]);
			
			parPartido.setParSigla((String)fields[4]);
			
			parPartido.setParCreated((Date)fields[5]);
			
			parPartido.setParCodigoOrigen((String)fields[6]);
			
			parPartido.setParFechaConstitucion((Date) fields[7]);
			
			parPartido.setParFechaDisolucion((Date) fields[8]);
			
			parPartido.setParEliminado((Boolean) fields[9]);
			
			parPartido.setParRut((String) fields[10]);
			
			parPartido.setParNombreAdm((String) fields[11]);
			
			parPartido.setParMail((String) fields[12]);
			
			parPartido.setParDireccion((String) fields[13]);
			
			parPartido.setParCiudad((String) fields[14]);
			
			parPartido.setParTelefonoCelu((String) fields[15]);
			
			parPartido.setParEstado((String) fields[16]);
			
			partidoListResult.add(parPartido);
		}
		return partidoListResult;
	}
}
