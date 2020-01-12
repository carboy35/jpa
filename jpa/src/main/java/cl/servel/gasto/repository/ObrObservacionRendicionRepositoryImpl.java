package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.DexDocumentoExterno;
import cl.servel.gasto.entity.ObrObservacionRendicion;
import cl.servel.gasto.entity.RenRendicion;
import cl.servel.gasto.entity.TpoRecomendacion;

public class ObrObservacionRendicionRepositoryImpl implements ObrObservacionRendicionRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	public List<ObrObservacionRendicion> obtenerObervaciones(Integer candidatoPartidoId,String codigoEntidad) {
		
		StringBuilder queryString = new StringBuilder();
		queryString.append("select ob.obr_id, ob.ren_id, ob.obr_numero_linea,ob.obr_numero_pagina, ");
		queryString.append("ob.obr_habilitada,ob.obr_respuesta,ob.dex_id,ob.obr_fecha_respuesta,ob.obr_tipo_ingreso_respuesta, ");
		queryString.append("ob.obr_analisis_respuesta,ob.tpo_rec_id ");
		queryString.append("from bit_bitacora b, obr_observacion_rendicion ob, ren_rendicion r where (id_entidad_registrada,bit_id) in ( ");
		queryString.append("select  id_entidad_registrada,max(bit_id) from obr_observacion_rendicion ob, bit_bitacora b ");
		queryString.append("where b.id_entidad_registrada=ob.obr_id ");
		queryString.append("and b.bit_entidad_registrada= :codigoEntidad ");
		queryString.append("group by id_entidad_registrada ");
		queryString.append(") ");
		queryString.append("and  b.id_entidad_registrada=ob.obr_id ");
		queryString.append("and r.ren_id=ob.ren_id ");
		queryString.append("and (r.can_id= :candidatoPartidoId or r.par_id= :candidatoPartidoId) ");
		 
		

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("candidatoPartidoId", candidatoPartidoId);
		query.setParameter("codigoEntidad", codigoEntidad);

		
		List<ObrObservacionRendicion> observacionesList = (List<ObrObservacionRendicion>)query.getResultList();
		List<ObrObservacionRendicion> observacionesListResult = new ArrayList<>();
		ObrObservacionRendicion observacion=null;
		RenRendicion rendicion=null;
		DexDocumentoExterno dexDocumentoExterno=null;
		TpoRecomendacion tpoRecomendacion=null;
		for (Object record : observacionesList) {
			Object[] fields = (Object[]) record;
			observacion = new ObrObservacionRendicion();
			
			observacion.setObrId((Integer)fields[0]);
			
			if (fields[1]!=null) {
				rendicion= new RenRendicion();
				rendicion.setRenId((Integer)fields[1]);
				observacion.setRenRendicion(rendicion);
			}
			
			observacion.setObrNumeroLinea((Integer)fields[2]);
			
			observacion.setObrNumeroPagina((Integer)fields[3]);
			
			observacion.setObrHabilitada((Boolean)fields[4]);
			
			observacion.setObrRespuesta((String)fields[5]);
			
			if (fields[6]!=null) {
				dexDocumentoExterno= new DexDocumentoExterno();
				dexDocumentoExterno.setDexId((Integer)fields[6]);
				observacion.setDexDocumentoExterno(dexDocumentoExterno);
				
			}
			
			observacion.setObrFechaRespuesta((Date)fields[7]);
			
			observacion.setObrTipoIngresoRespuesta((String)fields[8]);
			
			observacion.setObrAnalisisRespuesta((String)fields[9]);
			
			if (fields[10]!=null) {
				tpoRecomendacion= new TpoRecomendacion();
				tpoRecomendacion.setTpoRecId((Integer)fields[10]);
				observacion.setTpoRecomendacion(tpoRecomendacion);	
			}
			
			observacionesListResult.add(observacion);
		}


		return observacionesListResult;
		
	}
	
public List<ObrObservacionRendicion> obtenerTotalRespuestas(Integer infId,List<String> tipoRespuesta) {
		
		StringBuilder queryString = new StringBuilder();
		
		
		queryString.append("select t.obr_tipo_ingreso_respuesta,t.obr_fecha_respuesta,cast(max(t.total) AS INTEGER) total from ");
		queryString.append("( ");
		queryString.append("SELECT ");
		queryString.append("   ob.obr_tipo_ingreso_respuesta, ");
		queryString.append("   ob.obr_fecha_respuesta, ");
		queryString.append("   ROW_NUMBER () OVER ( ");
	    queryString.append("      PARTITION BY ob.obr_tipo_ingreso_respuesta,ob.obr_fecha_respuesta ");
		queryString.append("      ORDER BY ");
		queryString.append("         ob.obr_tipo_ingreso_respuesta desc ");
		queryString.append("   ) as total,inf.inf_id ");
		queryString.append("FROM ");
		queryString.append("   obr_observacion_rendicion ob,ren_rendicion r, inf_instancia_flujo inf ");
		queryString.append("   where ob.obr_fecha_respuesta is not null ");
		queryString.append("and ob.obr_tipo_ingreso_respuesta in :tipoRespuesta ");
		queryString.append("   and ob.ren_id=r.ren_id ");
	    queryString.append("   and (inf.can_id=r.can_id or inf.par_id=r.par_id) ");
		queryString.append("   and (inf.inf_id= :infId) ");
		queryString.append(")  t ");
		queryString.append("group by t.obr_tipo_ingreso_respuesta,t.obr_fecha_respuesta ");
		queryString.append("order by t.obr_tipo_ingreso_respuesta desc ");
		
		 
		

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("infId", infId);
		query.setParameter("tipoRespuesta", tipoRespuesta);

		
		List<ObrObservacionRendicion> observacionesList = (List<ObrObservacionRendicion>)query.getResultList();
		List<ObrObservacionRendicion> observacionesListResult = new ArrayList<>();
		ObrObservacionRendicion observacion=null;
		for (Object record : observacionesList) {
			Object[] fields = (Object[]) record;
			observacion = new ObrObservacionRendicion();
			
			observacion.setObrTipoIngresoRespuesta((String)fields[0]);
			observacion.setObrFechaRespuesta((Date)fields[1]);
			observacion.setObrNumeroLinea((Integer) fields[2]);
			
			observacionesListResult.add(observacion);
		}


		return observacionesListResult;
		
	}
}
