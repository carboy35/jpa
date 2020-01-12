package cl.servel.gasto.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.BitBitacora;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.UsuUsuarios;
import cl.servel.gasto.transients.MovimientoCuentaDTO;

public class BitBitacoraRepositoryImpl implements BitBitacoraRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<BitBitacora> devuelveBitacorasInstanciaFlujo(Integer eventoId,String entidad,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId){
	StringBuilder queryString = new StringBuilder();
		
		
		 queryString.append("select b.bit_id,b.eve_id,b.usu_id,b.bit_entidad_registrada,b.id_entidad_registrada, ");
		 queryString.append("b.bit_fecha_registro,b.bit_accion,b.bit_atributo_entidad,b.bit_valor_registrado ");
		 queryString.append("from ren_rendicion r, inf_instancia_flujo inf, bit_bitacora b,int_instancia_tarea ins ");
		 queryString.append("where r.can_id=inf.can_id ");
		 queryString.append("and b.id_entidad_registrada=r.ren_id ");
		 queryString.append("and b.bit_entidad_registrada= :entidad ");
		 queryString.append("and ins.inf_id=inf.inf_id ");
		 if (usuarioId ==null) {
				queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
				queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
			}
		 if (eventoId !=null) {
				queryString.append("and inf.eve_id= :eventoId ");
		 }
		 if (usuarioIds!=null) {
				queryString.append("and inf.usu_id_actual in :usuarioIds ");
		 }
			if (usuarioId !=null) {
				queryString.append("and ins.usu_id= :usuarioId ");
			}
		 if (dtaId !=null) {
				queryString.append("and  ins.dta_id = :dtaId ");
			}
		 queryString.append("group by b.bit_id ");
		 
		 queryString.append("union ");
		 queryString.append("select b.bit_id,b.eve_id,b.usu_id,b.bit_entidad_registrada,b.id_entidad_registrada, ");
		 queryString.append("b.bit_fecha_registro,b.bit_accion,b.bit_atributo_entidad,b.bit_valor_registrado ");
		 queryString.append("from ren_rendicion r, inf_instancia_flujo inf, bit_bitacora b,int_instancia_tarea ins ");
		 queryString.append("where r.par_id=inf.par_id ");
		 queryString.append("and b.id_entidad_registrada=r.ren_id ");
		 queryString.append("and b.bit_entidad_registrada= :entidad ");
		 queryString.append("and ins.inf_id=inf.inf_id ");
		if (usuarioId ==null) {
			queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
			queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
		}
		 if (eventoId !=null) {
				queryString.append("and inf.eve_id= :eventoId ");
		 }
		 if (usuarioIds!=null) {
				queryString.append("and inf.usu_id_actual in :usuarioIds ");
		 }
		 if (usuarioId !=null) {
		    	queryString.append("and ins.usu_id= :usuarioId ");
		 }
		 if (dtaId !=null) {
				queryString.append("and  ins.dta_id = :dtaId ");
			}
		 queryString.append("group by b.bit_id ");
		 queryString.append("order by bit_fecha_registro asc ");
		 
		 Query query = entityManager.createNativeQuery(queryString.toString());
		 if (eventoId !=null) {
				query.setParameter("eventoId", eventoId);
		 }
		 if (usuarioIds!=null) {
			 query.setParameter("usuarioIds", usuarioIds);
		 }
		 if (usuarioId !=null) {
				query.setParameter("usuarioId", usuarioId);
			}
			if (dtaId !=null) {
				query.setParameter("dtaId", dtaId);
			}
		 query.setParameter("entidad", entidad);
		 
		 List<BitBitacora> bitacoraList=(List<BitBitacora>)query.getResultList();
		 List<BitBitacora> bitacoraResultList=new ArrayList<>();
		 BitBitacora bitacora=null;
		 EveEventoEleccionario evento=null;
		 UsuUsuarios usuario=null;
		 for (Object record:bitacoraList ) {
			 Object[] fields = (Object[]) record;
			 bitacora= new BitBitacora();
			 bitacora.setBitId((Integer)fields[0]);
			 if (fields[1]!=null) {
				 evento= new EveEventoEleccionario();
				 evento.setEveId((Integer)fields[1]);
				 bitacora.setEveEventoEleccionario(evento);	 
			 }
			 if (fields[2]!=null) {
				 usuario=new UsuUsuarios();
				 usuario.setUsuId((Integer)fields[2]);
				 bitacora.setUsuUsuarios(usuario);
			 }
			 BigInteger bi;
			 bi=(BigInteger)fields[4];
			 
			 bitacora.setBitEntidadRegistrada((String)fields[3]);
			 bitacora.setIdEntidadRegistrada(bi.longValue());
			 bitacora.setBitFechaRegistro((Date)fields[5]);
			 bitacora.setBitAccion((String)fields[6]);
			 bitacora.setBitAtributoEntidad((String)fields[7]);
			 bitacora.setBitValorRegistrado((String)fields[8]);
			 bitacoraResultList.add(bitacora);
		 }
			 return bitacoraResultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<BitBitacora> devuelveBitacorasObservacion(Integer candidatoPartidoId,String entidad,String atributoEntidad){
	StringBuilder queryString = new StringBuilder();
		
	    queryString.append("select b.bit_id,b.eve_id,b.usu_id,b.bit_entidad_registrada,b.id_entidad_registrada, ");
	    queryString.append("b.bit_fecha_registro,b.bit_accion,b.bit_atributo_entidad,b.bit_valor_registrado ");
	    queryString.append("from bit_bitacora b, obr_observacion_rendicion ob,ren_rendicion r where (id_entidad_registrada,bit_id) in ( ");
		queryString.append("select  id_entidad_registrada,max(bit_id) from obr_observacion_rendicion ob, bit_bitacora b ");
		queryString.append("where b.id_entidad_registrada=ob.obr_id ");
		queryString.append("and b.bit_entidad_registrada= :entidad ");
		queryString.append("and b.bit_atributo_entidad= :atributoEntidad ");
		queryString.append("group by id_entidad_registrada ");
		queryString.append(") ");
	    queryString.append("and  b.id_entidad_registrada=ob.obr_id ");
	    queryString.append("and r.ren_id=ob.ren_id ");
	    queryString.append("and (r.can_id= :candidatoPartidoId or r.par_id= :candidatoPartidoId) ");
		 
		 Query query = entityManager.createNativeQuery(queryString.toString());
			 query.setParameter("candidatoPartidoId", candidatoPartidoId);
			 query.setParameter("entidad", entidad);
			 query.setParameter("atributoEntidad", atributoEntidad);
		 
		 List<BitBitacora> bitacoraList=(List<BitBitacora>)query.getResultList();
		 List<BitBitacora> bitacoraResultList=new ArrayList<>();
		 BitBitacora bitacora=null;
		 EveEventoEleccionario evento=null;
		 UsuUsuarios usuario=null;
		 for (Object record:bitacoraList ) {
			 Object[] fields = (Object[]) record;
			 bitacora= new BitBitacora();
			 bitacora.setBitId((Integer)fields[0]);
			 if (fields[1]!=null) {
				 evento= new EveEventoEleccionario();
				 evento.setEveId((Integer)fields[1]);
				 bitacora.setEveEventoEleccionario(evento);	 
			 }
			 if (fields[2]!=null) {
				 usuario=new UsuUsuarios();
				 usuario.setUsuId((Integer)fields[2]);
				 bitacora.setUsuUsuarios(usuario);
			 }
			 BigInteger bi;
			 bi=(BigInteger)fields[4];
			 
			 bitacora.setBitEntidadRegistrada((String)fields[3]);
			 bitacora.setIdEntidadRegistrada(bi.longValue());
			 bitacora.setBitFechaRegistro((Date)fields[5]);
			 bitacora.setBitAccion((String)fields[6]);
			 bitacora.setBitAtributoEntidad((String)fields[7]);
			 bitacora.setBitValorRegistrado((String)fields[8]);
			 bitacoraResultList.add(bitacora);
		 }
			 return bitacoraResultList;
	}
	
	private String getQueryMovimientosCandidatos() {
		StringBuilder query = new StringBuilder();
		
		query.append("  	 SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
	    query.append(" 				TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION, ");
		query.append(" 				C.CAN_RUT AS RUT, ");
		query.append("		 		C.CAN_RUT_DV AS DV, ");
		query.append("		 		C.CAN_NOMBRES AS NOMBRE, ");
		query.append("		 		C.CAN_APP_PATERNO AS APELLIDO_PATERNO, ");
		query.append("		 		C.CAN_APP_MATERNO AS APELLIDO_MATERNO,  ");
		query.append("		 		C.TPO_CAN_ID AS TIPO, ");
		query.append("		 		P.PAR_NOMBRE AS PARTIDO, ");
		query.append("		 		X.BIT_ACCION || ' LÍNEA' AS ACCION, ");
		query.append("		 		TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append("		 		CAST (X.DETALLE ->>'numeroPagina' AS INTEGER) AS NUMERO_PAGINA, ");
		query.append("		 		CAST (X.DETALLE ->>'numeroLinea' AS INTEGER) AS NUMERO_LINEA, ");
		query.append("		 		X.BIT_ATRIBUTO_ENTIDAD AS ATRIBUTO_MODIFICADO, ");
		query.append("		        X.DETALLE ->>'valorModificado' as NUEVO_VALOR, ");
		query.append("		        X.BIT_FECHA_REGISTRO AS FECHA_REGISTRO, ");
		query.append("		        U.USU_NOMBRE || ' ' || U.USU_APELLIDO_PATERNO || ' ' || U.USU_APELLIDO_MATERNO AS USUARIO ");
		query.append("		   FROM ( ");
		query.append("		           SELECT JSONB ((B.BIT_VALOR_REGISTRADO) :::: JSON) AS DETALLE, ");
		query.append("		                  B.BIT_ID, ");
		query.append("		                  B.BIT_FECHA_REGISTRO, ");
		query.append("		                  B.USU_ID, ");
		query.append("		                  B.ID_ENTIDAD_REGISTRADA, ");
		query.append("		                  B.BIT_ENTIDAD_REGISTRADA, ");
		query.append("		                  B.BIT_ATRIBUTO_ENTIDAD, ");
		query.append("		                  B.BIT_ACCION ");
		query.append("		             FROM BIT_BITACORA B ");
		query.append("		            WHERE B.BIT_ENTIDAD_REGISTRADA = 'DetRendicion' ");
		query.append("		              AND BIT_ATRIBUTO_ENTIDAD <> 'valor'   ");
		query.append("		              AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		query.append("		        ) AS X ");
		query.append("		   JOIN DET_DETALLE_RENDICION DR ");
		query.append("		     ON DR.DET_ID = X.ID_ENTIDAD_REGISTRADA ");
		query.append("		   JOIN REN_RENDICION R ");
		query.append("		     ON R.REN_ID = DR.REN_ID ");
		query.append("		   JOIN TPO_RENDICION TR ");
		query.append("		     ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		query.append("		   JOIN CAN_CANDIDATO C ");
		query.append("		     ON C.CAN_ID = R.CAN_ID ");
		query.append("		   LEFT JOIN PAR_PARTIDO P ");
		query.append("		     ON P.PAR_ID = C.PAR_ID ");
		query.append("		   JOIN SEL_SUB_ELECCION S ");
		query.append("		     ON S.SEL_ID = C.SEL_ID ");
		query.append("		   JOIN ELE_ELECCION E ");
		query.append("		     ON E.ELE_ID = S.ELE_ID ");
		query.append("		   JOIN TPO_ELECCION TEL ");
		query.append("		     ON TEL.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("		   JOIN TPO_EVENTO TEV ");
		query.append("		     ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("		   JOIN USU_USUARIOS U ");
		query.append("		     ON U.USU_ID = X.USU_ID ");
		query.append("		  WHERE R.EVE_ID = ? ");
		query.append("		    AND TEV.TPO_EVENTO_ID = ? ");
		query.append("			AND TEL.TPO_ELECCION_ID = ? ");
//		query.append("			AND C.CAN_ID = 1303823 ");
		query.append("UNION         ");
		query.append("  	 SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
	    query.append(" 				TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION, ");
		query.append(" 				C.CAN_RUT AS RUT, ");
		query.append("		 		C.CAN_RUT_DV AS DV, ");
		query.append("		 		C.CAN_NOMBRES AS NOMBRE, ");
		query.append("		 		C.CAN_APP_PATERNO AS APELLIDO_PATERNO, ");
		query.append("		 		C.CAN_APP_MATERNO AS APELLIDO_MATERNO,  ");
		query.append("		 		C.TPO_CAN_ID AS TIPO, ");
		query.append("		 		P.PAR_NOMBRE AS PARTIDO, ");
		query.append("		 		CASE ");
		query.append("				  WHEN (B.BIT_VALOR_REGISTRADO IS NULL OR B.BIT_VALOR_REGISTRADO = '') THEN 'ELIMINAR OBSERVACIÓN' ");
		query.append("				  ELSE B.BIT_ACCION || ' OBSERVACIÓN' ");
		query.append("				END AS ACCION, ");
		query.append("		 		TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append("		 		O.OBR_NUMERO_PAGINA as NUMERO_PAGINA, ");
		query.append("		 		O.OBR_NUMERO_LINEA as NUMERO_LINEA, ");
		query.append("		 		B.BIT_ATRIBUTO_ENTIDAD AS ATRIBUTO_MODIFICADO, ");
		query.append("		        CASE ");
		query.append("				  WHEN (B.BIT_VALOR_REGISTRADO IS NULL OR B.BIT_VALOR_REGISTRADO = '') THEN 'ELIMINADO' ");
		query.append("				  ELSE B.BIT_VALOR_REGISTRADO ");
		query.append("				END AS NUEVO_VALOR, ");
		query.append("		        B.BIT_FECHA_REGISTRO, ");
		query.append("		        U.USU_NOMBRE || ' ' || U.USU_APELLIDO_PATERNO || ' ' || U.USU_APELLIDO_MATERNO AS USUARIO  ");
		query.append("		   FROM BIT_BITACORA B ");
		query.append("		   JOIN OBR_OBSERVACION_RENDICION O ");
		query.append("		     ON O.OBR_ID = B.ID_ENTIDAD_REGISTRADA ");
		query.append("		   JOIN REN_RENDICION R ");
		query.append("		     ON R.REN_ID = O.REN_ID ");
		query.append("		   JOIN TPO_RENDICION TR ");
		query.append("		     ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		query.append("		   JOIN CAN_CANDIDATO C ");
		query.append("		     ON C.CAN_ID = R.CAN_ID ");
		query.append("		   LEFT JOIN PAR_PARTIDO P ");
		query.append("		     ON P.PAR_ID = C.PAR_ID ");
		query.append("		   JOIN SEL_SUB_ELECCION S ");
		query.append("		     ON S.SEL_ID = C.SEL_ID ");
		query.append("		   JOIN ELE_ELECCION E ");
		query.append("		     ON E.ELE_ID = S.ELE_ID ");
		query.append("		   JOIN TPO_ELECCION TEL ");
		query.append("		     ON TEL.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("		   JOIN TPO_EVENTO TEV ");
		query.append("		     ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("		   JOIN USU_USUARIOS U ");
		query.append("		     ON U.USU_ID = B.USU_ID      ");
		query.append("		  WHERE B.BIT_ENTIDAD_REGISTRADA = 'ObrObservacionRendicion' ");
		query.append("		    AND LOWER(BIT_ATRIBUTO_ENTIDAD) = 'glosa' ");
		query.append("		    AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		query.append("		    AND R.EVE_ID = ? ");
		query.append("		    AND TEV.TPO_EVENTO_ID = ? ");
		query.append("		    AND TEL.TPO_ELECCION_ID = ? ");
//		query.append("		    AND C.CAN_ID = 1303823 ");
		query.append("UNION ");
		query.append("  SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
	    query.append(" 		   TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION, ");
		query.append(" 		   C.CAN_RUT AS RUT, ");
		query.append("		   C.CAN_RUT_DV AS DV, ");
		query.append("	 	   C.CAN_NOMBRES AS NOMBRE, ");
		query.append("	 	   C.CAN_APP_PATERNO AS APELLIDO_PATERNO, ");
		query.append("	 	   C.CAN_APP_MATERNO AS APELLIDO_MATERNO,  ");
		query.append("	 	   C.TPO_CAN_ID AS TIPO, ");
		query.append("	 	   P.PAR_NOMBRE AS PARTIDO, ");
		query.append("		   X.BIT_ACCION || ' RESPUESTA' AS ACCION, ");
		query.append("		   TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append("		   O.OBR_NUMERO_PAGINA AS NUMERO_PAGINA, ");
		query.append("		   O.OBR_NUMERO_LINEA AS NUMERO_LINEA, ");
		query.append("		   'RESPUESTA OBSERVACIÓN' AS ATRIBUTO_MODIFICADO, ");
		query.append("		   X.DETALLE ->> 'obrRespuesta' AS NUEVO_VALOR, ");
		query.append("		   X.BIT_FECHA_REGISTRO AS FECHA_REGISTRO, ");
		query.append("		   U.USU_NOMBRE || ' ' || U.USU_APELLIDO_PATERNO || ' ' || U.USU_APELLIDO_MATERNO AS USUARIO ");
		query.append("	  FROM ( ");
		query.append("	      	   SELECT JSONB ((B.BIT_VALOR_REGISTRADO) :::: JSON) AS DETALLE, ");
		query.append("	                  B.BIT_ID, ");
		query.append("	                  B.BIT_FECHA_REGISTRO, ");
		query.append("	                  B.USU_ID, ");
		query.append("	                  B.ID_ENTIDAD_REGISTRADA, ");
		query.append("	                  B.BIT_ENTIDAD_REGISTRADA, ");
		query.append("	                  B.BIT_ATRIBUTO_ENTIDAD, ");
		query.append("	                  B.BIT_ACCION ");
		query.append("	             FROM BIT_BITACORA B ");
		query.append("	            WHERE B.BIT_ENTIDAD_REGISTRADA = 'ObrObservacionRendicion' ");
		query.append("	   			  AND UPPER (BIT_ATRIBUTO_ENTIDAD) = 'RESPUESTA_OBSERVACION'   ");
		query.append("	              AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		query.append("	 		) AS X ");
		query.append("	   JOIN OBR_OBSERVACION_RENDICION O ");
		query.append("	     ON O.OBR_ID = X.ID_ENTIDAD_REGISTRADA ");
		query.append("	   JOIN REN_RENDICION R ");
		query.append("	     ON R.REN_ID = O.REN_ID ");
		query.append("	   JOIN TPO_RENDICION TR ");
		query.append("	     ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		query.append("	   JOIN CAN_CANDIDATO C ");
		query.append("	     ON C.CAN_ID = R.CAN_ID ");
		query.append("	   LEFT JOIN PAR_PARTIDO P ");
		query.append("	     ON P.PAR_ID = C.PAR_ID ");
		query.append("	   JOIN SEL_SUB_ELECCION S ");
		query.append("	     ON S.SEL_ID = C.SEL_ID ");
		query.append("	   JOIN ELE_ELECCION E ");
		query.append("	     ON E.ELE_ID = S.ELE_ID ");
		query.append("	   JOIN TPO_ELECCION TEL ");
		query.append("	     ON TEL.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("	   JOIN TPO_EVENTO TEV ");
		query.append("	     ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("	   JOIN USU_USUARIOS U ");
		query.append("	     ON U.USU_ID = X.USU_ID       ");
		query.append("	  WHERE R.EVE_ID = ? ");
		query.append("	    AND TEV.TPO_EVENTO_ID = ? ");
		query.append("		AND TEL.TPO_ELECCION_ID = ? ");
//		query.append("		AND C.CAN_ID = 1303823 ");
		
		return query.toString();
	}
	
	private String getQueryMovimientosPartidos() {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
		query.append(" 		 TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION,  ");
		query.append("		 CAST ((SELECT SPLIT_PART(P.PAR_RUT, '-', 1)) AS INTEGER) AS RUT, ");
		query.append(" 		 (SELECT SPLIT_PART(P.PAR_RUT, '-', 2)) AS DV, ");
		query.append(" 		 ''AS NOMBRE, ");
		query.append(" 		 P.PAR_NOMBRE AS APELLIDO_PATERNO, ");
		query.append(" 		 '' AS APELLIDO_MATERNO,  ");
		query.append(" 		 0 AS TIPO, ");
		query.append(" 		 P.PAR_NOMBRE AS PARTIDO, ");
		query.append(" 		 X.BIT_ACCION || ' LÍNEA' AS ACCION, ");
		query.append(" 		 TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append(" 		 CAST (X.DETALLE ->>'numeroPagina' AS INTEGER) AS NUMERO_PAGINA, ");
		query.append(" 		 CAST (X.DETALLE ->>'numeroLinea' AS INTEGER) AS NUMERO_LINEA, ");
		query.append(" 		 X.BIT_ATRIBUTO_ENTIDAD AS ATRIBUTO_MODIFICADO, ");
		query.append("       X.DETALLE ->>'valorModificado' as NUEVO_VALOR, ");
		query.append("       X.BIT_FECHA_REGISTRO AS FECHA_REGISTRO, ");
		query.append("       U.USU_NOMBRE || ' ' || U.USU_APELLIDO_PATERNO || ' ' || U.USU_APELLIDO_MATERNO AS USUARIO ");
		query.append("   FROM ( ");
		query.append("           SELECT JSONB ((B.BIT_VALOR_REGISTRADO) :::: JSON) AS DETALLE, ");
		query.append("                  B.BIT_ID, ");
		query.append("                  B.BIT_FECHA_REGISTRO, ");
		query.append("                  B.USU_ID, ");
		query.append("                  B.ID_ENTIDAD_REGISTRADA, ");
		query.append("                  B.BIT_ENTIDAD_REGISTRADA, ");
		query.append("                  B.BIT_ATRIBUTO_ENTIDAD, ");
		query.append("                  B.BIT_ACCION ");
		query.append("             FROM BIT_BITACORA B ");
		query.append("            WHERE B.BIT_ENTIDAD_REGISTRADA = 'DetRendicion' ");
		query.append("              AND BIT_ATRIBUTO_ENTIDAD <> 'valor'   ");
		query.append("              AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		query.append("        ) AS X ");
		query.append("   JOIN DET_DETALLE_RENDICION DR ");
		query.append("     ON DR.DET_ID = X.ID_ENTIDAD_REGISTRADA ");
		query.append("   JOIN REN_RENDICION R ");
		query.append("     ON R.REN_ID = DR.REN_ID ");
		query.append("   JOIN TPO_RENDICION TR ");
		query.append("     ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		query.append("   JOIN PAR_PARTIDO P ");
		query.append("     ON P.PAR_ID = R.PAR_ID ");
		query.append("   JOIN ELE_ELECCION E ");
		query.append("     ON E.ELE_ID = P.ELE_ID ");
		query.append("   JOIN TPO_ELECCION TEL ");
		query.append("     ON TEL.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("   JOIN TPO_EVENTO TEV ");
		query.append("     ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("   JOIN USU_USUARIOS U ");
		query.append("     ON U.USU_ID = X.USU_ID ");
		query.append("  WHERE R.EVE_ID = ? ");
		query.append("    AND TEV.TPO_EVENTO_ID = ? ");
		query.append("    AND TEL.TPO_ELECCION_ID = ? ");
		//query.append("    AND P.PAR_ID = 1303823     ");
		query.append("UNION ");
		query.append("SELECT  TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
		query.append(" 		  TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION,  ");
		query.append("		  CAST ((SELECT SPLIT_PART(P.PAR_RUT, '-', 1)) AS INTEGER) AS RUT, ");
		query.append(" 		  (SELECT SPLIT_PART(P.PAR_RUT, '-', 2)) AS DV, ");
		query.append(" 		  '' AS NOMBRE, ");
		query.append(" 		  P.PAR_NOMBRE AS APELLIDO_PATERNO, ");
		query.append(" 		  '' AS APELLIDO_MATERNO,  ");
		query.append(" 		  0 AS TIPO, ");
		query.append(" 		  P.PAR_NOMBRE AS PARTIDO, ");
		query.append(" 		  CASE ");
		query.append("		    WHEN (B.BIT_VALOR_REGISTRADO IS NULL OR B.BIT_VALOR_REGISTRADO = '') THEN 'ELIMINAR OBSERVACIÓN' ");
		query.append("		    ELSE B.BIT_ACCION || ' OBSERVACIÓN' ");
		query.append("		  END AS ACCION, ");
		query.append(" 		  TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append(" 		  O.OBR_NUMERO_PAGINA as NUMERO_PAGINA, ");
		query.append(" 		  O.OBR_NUMERO_LINEA as NUMERO_LINEA, ");
		query.append(" 		  B.BIT_ATRIBUTO_ENTIDAD AS ATRIBUTO_MODIFICADO, ");
		query.append("        CASE ");
		query.append("		    WHEN (B.BIT_VALOR_REGISTRADO IS NULL OR B.BIT_VALOR_REGISTRADO = '') THEN 'ELIMINADO' ");
		query.append("		    ELSE B.BIT_VALOR_REGISTRADO ");
		query.append("		  END AS NUEVO_VALOR, ");
		query.append("        B.BIT_FECHA_REGISTRO, ");
		query.append("        U.USU_NOMBRE || ' ' || U.USU_APELLIDO_PATERNO || ' ' || U.USU_APELLIDO_MATERNO AS USUARIO  ");
		query.append("   FROM BIT_BITACORA B ");
		query.append("   JOIN OBR_OBSERVACION_RENDICION O ");
		query.append("     ON O.OBR_ID = B.ID_ENTIDAD_REGISTRADA ");
		query.append("   JOIN REN_RENDICION R ");
		query.append("     ON R.REN_ID = O.REN_ID ");
		query.append("   JOIN TPO_RENDICION TR ");
		query.append("     ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		query.append("   JOIN PAR_PARTIDO P ");
		query.append("     ON P.PAR_ID = R.PAR_ID ");
		query.append("   JOIN ELE_ELECCION E ");
		query.append("     ON E.ELE_ID = P.ELE_ID ");
		query.append("   JOIN TPO_ELECCION TEL ");
		query.append("     ON TEL.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("   JOIN TPO_EVENTO TEV ");
		query.append("     ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("   JOIN USU_USUARIOS U ");
		query.append("     ON U.USU_ID = B.USU_ID      ");
		query.append("  WHERE B.BIT_ENTIDAD_REGISTRADA = 'ObrObservacionRendicion' ");
		query.append("    AND LOWER(BIT_ATRIBUTO_ENTIDAD) = 'glosa' ");
		query.append("	  AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		query.append("    AND R.EVE_ID = ? ");
		query.append("	  AND TEV.TPO_EVENTO_ID = ? ");
		query.append("	  AND TEL.TPO_ELECCION_ID = ? ");
		//query.append("	  AND P.PAR_ID = 1303823 ");
		query.append("UNION ");
		query.append("SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
		query.append(" 		 TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION,  ");
		query.append("		 CAST ((SELECT SPLIT_PART(P.PAR_RUT, '-', 1)) AS INTEGER) AS RUT, ");
		query.append(" 	     (SELECT SPLIT_PART(P.PAR_RUT, '-', 2)) AS DV, ");
		query.append(" 	     '' AS NOMBRE, ");
		query.append(" 	     P.PAR_NOMBRE AS APELLIDO_PATERNO, ");
		query.append(" 	     '' AS APELLIDO_MATERNO,  ");
		query.append(" 	     0 AS TIPO, ");
		query.append(" 	     P.PAR_NOMBRE AS PARTIDO, ");
		query.append("	     X.BIT_ACCION || ' RESPUESTA' AS ACCION, ");
		query.append("	     TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append("	     O.OBR_NUMERO_PAGINA AS NUMERO_PAGINA, ");
		query.append("	     O.OBR_NUMERO_LINEA AS NUMERO_LINEA, ");
		query.append("	     'RESPUESTA OBSERVACIÓN' AS ATRIBUTO_MODIFICADO, ");
		query.append("	     X.DETALLE ->> 'obrRespuesta' AS NUEVO_VALOR, ");
		query.append("	     X.BIT_FECHA_REGISTRO AS FECHA_REGISTRO, ");
		query.append("	     U.USU_NOMBRE || ' ' || U.USU_APELLIDO_PATERNO || ' ' || U.USU_APELLIDO_MATERNO AS USUARIO ");
		query.append("  FROM ( ");
		query.append("      	   SELECT JSONB ((B.BIT_VALOR_REGISTRADO) :::: JSON) AS DETALLE, ");
		query.append("                  B.BIT_ID, ");
		query.append("                  B.BIT_FECHA_REGISTRO, ");
		query.append("                  B.USU_ID, ");
		query.append("                  B.ID_ENTIDAD_REGISTRADA, ");
		query.append("                  B.BIT_ENTIDAD_REGISTRADA, ");
		query.append("                  B.BIT_ATRIBUTO_ENTIDAD, ");
		query.append("                  B.BIT_ACCION ");
		query.append("             FROM BIT_BITACORA B ");
		query.append("            WHERE B.BIT_ENTIDAD_REGISTRADA = 'ObrObservacionRendicion' ");
		query.append("   			      AND UPPER (BIT_ATRIBUTO_ENTIDAD) = 'RESPUESTA_OBSERVACION'   ");
		query.append("              AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		query.append(" 		) AS X ");
		query.append("   JOIN OBR_OBSERVACION_RENDICION O ");
		query.append("     ON O.OBR_ID = X.ID_ENTIDAD_REGISTRADA ");
		query.append("   JOIN REN_RENDICION R ");
		query.append("     ON R.REN_ID = O.REN_ID ");
		query.append("   JOIN TPO_RENDICION TR ");
		query.append("     ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		query.append("   JOIN PAR_PARTIDO P ");
		query.append("     ON P.PAR_ID = R.PAR_ID ");
		query.append("   JOIN ELE_ELECCION E ");
		query.append("     ON E.ELE_ID = P.ELE_ID ");
		query.append("   JOIN TPO_ELECCION TEL ");
		query.append("     ON TEL.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("   JOIN TPO_EVENTO TEV ");
		query.append("     ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("   JOIN USU_USUARIOS U ");
		query.append("     ON U.USU_ID = X.USU_ID       ");
		query.append("  WHERE R.EVE_ID = ? ");
		query.append("    AND TEV.TPO_EVENTO_ID = ? ");
		query.append("	  AND TEL.TPO_ELECCION_ID = ? ");
		//query.append("	  AND P.PAR_ID = 1303823 ");"
		
		return query.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<MovimientoCuentaDTO> getDataReporteMovimientos(int eveId, int tipoEvento, int tipoEleccion, Date desde, Date hasta, Integer idCandidato, Integer idPartido) {
		Query query = null;
		List<Object[]> result = new ArrayList<Object[]>();
		
		if ((idCandidato == null && idPartido == null) || idCandidato != null) {
			query = entityManager.createNativeQuery(getQueryMovimientosCandidatos());
			
			query.setParameter(1, desde);
			query.setParameter(2, hasta);
			query.setParameter(3, eveId);
			query.setParameter(4, tipoEvento);
			query.setParameter(5, tipoEleccion);
			
			query.setParameter(6, desde);
			query.setParameter(7, hasta);
			query.setParameter(8, eveId);
			query.setParameter(9, tipoEvento);
			query.setParameter(10, tipoEleccion);
			
			query.setParameter(11, desde);
			query.setParameter(12, hasta);
			query.setParameter(13, eveId);
			query.setParameter(14, tipoEvento);
			query.setParameter(15, tipoEleccion);
			
			result.addAll((List<Object[]>) query.getResultList());
		}
		
		if ((idCandidato == null && idPartido == null) || idPartido != null) {
			query = entityManager.createNativeQuery(getQueryMovimientosPartidos());
			
			query.setParameter(1, desde);
			query.setParameter(2, hasta);
			query.setParameter(3, eveId);
			query.setParameter(4, tipoEvento);
			query.setParameter(5, tipoEleccion);
			
			query.setParameter(6, desde);
			query.setParameter(7, hasta);
			query.setParameter(8, eveId);
			query.setParameter(9, tipoEvento);
			query.setParameter(10, tipoEleccion);
			
			query.setParameter(11, desde);
			query.setParameter(12, hasta);
			query.setParameter(13, eveId);
			query.setParameter(14, tipoEvento);
			query.setParameter(15, tipoEleccion);
			
			result.addAll((List<Object[]>) query.getResultList());
		}
		
		List<MovimientoCuentaDTO> movimientos = new ArrayList<MovimientoCuentaDTO>();
		MovimientoCuentaDTO movimiento = null;
		
		for (Object[] aResult : result) {
			movimiento = new MovimientoCuentaDTO();
			
			movimiento.setTipoEvento((String)aResult[0]);
			movimiento.setTipoEleccion((String)aResult[1]);
			movimiento.setRut((int)aResult[2]);
			movimiento.setDv((String)aResult[3]);
			movimiento.setNombre((String)aResult[4]);
			movimiento.setApellidoPaterno((String)aResult[5]);
			movimiento.setApellidoMaterno((String)aResult[6]);
			movimiento.setTipo((int)aResult[7]);
			movimiento.setPartido((String)aResult[8]);
			movimiento.setAccion((String)aResult[9]);
			movimiento.setCodigoRendicion((String)aResult[10]);
			movimiento.setNumeroPagina((int)aResult[11]);
			movimiento.setNumeroLinea((int)aResult[12]);
			movimiento.setAtributoModificado((String)aResult[13]);
			movimiento.setNuevoValor((String)aResult[14]);
			movimiento.setFecha((Date)aResult[15]);
			movimiento.setUsuario((String)aResult[16]);
			
			movimientos.add(movimiento);
		}
		
		return movimientos;
	}
}
