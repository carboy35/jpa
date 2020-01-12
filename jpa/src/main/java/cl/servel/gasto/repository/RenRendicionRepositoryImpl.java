package cl.servel.gasto.repository;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.RenRendicion;
import cl.servel.gasto.entity.TpoRendicion;
import cl.servel.gasto.transients.IngresoGastoDTO;
import cl.servel.gasto.transients.PagoPendiente;
import cl.servel.gasto.transients.Sujeto; 

@SuppressWarnings("unchecked")
public class RenRendicionRepositoryImpl implements RenRendicionRepositoryCustom {
	private static final Logger LOG = LoggerFactory.getLogger(RenRendicionRepositoryCustom.class);

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private DataSource ds;

	public RenRendicion getRendicionByRut(@Param("rut") Integer rut, @Param("dv") String dv,
			@Param("eventoId") Integer eventoId, @Param("tpoEventoId") Integer tpoEventoId,
			@Param("tpoEleccionId") Integer tpoEleccionId, @Param("tpoRendicionId") Integer tpoRendicionId) {

		StringBuilder queryString = new StringBuilder();

		queryString.append("select * from ren_rendicion  ");
		queryString.append("where ren_id in ");
		queryString.append("( ");
		queryString.append("select ren.ren_id from ren_rendicion ren ");
		queryString.append("left join can_candidato can on (ren.can_id = can.can_id) ");
		queryString.append("left join ele_eleccion ele on (ele.ele_id = ren.ele_id) ");
		queryString.append("where can.can_id is not null ");
		queryString.append("and can.can_rut = ? ");
		queryString.append("and can.can_rut_dv = ? ");
		queryString.append("and ele.eve_id_eve = ? ");
		queryString.append("and ele.tpo_evento_id = ? ");
		queryString.append("and ele.tpo_eleccion_id = ? ");
		queryString.append("and ren.tpo_rendicion_id = ? ");		
		queryString.append("union ");
		queryString.append("select ren.ren_id from ren_rendicion ren ");
		queryString.append("left join par_partido par on (ren.par_id = par.par_id) ");
		queryString.append("left join ele_eleccion ele on (par.ele_id = ele.ele_id) ");
		queryString.append("where par.par_id is not null ");
		queryString.append("and par.par_rut = ? ");
		queryString.append("and ele.eve_id_eve = ? ");
		queryString.append("and ele.tpo_evento_id = ? ");
		queryString.append("and ele.tpo_eleccion_id = ? ");
		queryString.append("and ren.tpo_rendicion_id = ? ");		
		queryString.append(") ");

		String rutCompleto = rut + "-" + dv;

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, rut);
		query.setParameter(2, dv);
		query.setParameter(3, eventoId);
		query.setParameter(4, tpoEventoId);
		query.setParameter(5, tpoEleccionId);
		query.setParameter(6, tpoRendicionId);

		query.setParameter(7, rutCompleto);
		query.setParameter(8, eventoId);
		query.setParameter(9, tpoEventoId);
		query.setParameter(10, tpoEleccionId);
		query.setParameter(11, tpoRendicionId);

		List<RenRendicion> rendicionList = (List<RenRendicion>) query.getResultList();
		List<RenRendicion> rendicionListResult = new ArrayList<>();

		RenRendicion rendicion;
		CanCandidato candidato;
		EveEventoEleccionario evento;
		EleEleccion eleccion;
		TpoRendicion tpoRendicion;
		ParPartido partido;

		Integer candidatoId;
		Integer eveId;
		Integer eleId;
		Integer tipoRendicionId;
		Integer partidoId;

		for (Object record : rendicionList) {
			Object[] fields = (Object[]) record;
			rendicion = new RenRendicion();

			rendicion.setRenId((Integer) fields[0]);

			candidatoId = (Integer) fields[1];
			if (candidatoId != null) {
				candidato = new CanCandidato();
				candidato.setCanId((Integer) fields[1]);
				rendicion.setCanCandidato(candidato);
			}
			eveId = (Integer) fields[2];
			if (eveId != null) {
				evento = new EveEventoEleccionario();
				evento.setEveId((Integer) fields[2]);
				rendicion.setEveEventoEleccionario(evento);
			}
			eleId = (Integer) fields[3];
			if (eleId != null) {
				eleccion = new EleEleccion();
				eleccion.setEleId((Integer) fields[3]);
				rendicion.setEleEleccion(eleccion);
			}
			tipoRendicionId = (Integer) fields[4];
			if (tipoRendicionId != null) {
				tpoRendicion = new TpoRendicion();
				tpoRendicion.setTpoRendicionId((Integer) fields[4]);
				rendicion.setTpoRendicion(tpoRendicion);
			}
			rendicion.setRenNumeroRendicion((Integer) fields[5]);
			if (fields[6] != null) {
				rendicion.setRenFecha((Date) fields[6]);
			}
			rendicion.setRenEstado((String) fields[7]);
			rendicion.setRenTotalRendicion(((BigInteger) fields[8]).longValue());
			rendicion.setRenNumeroDocumento((Integer) fields[9]);

			partidoId = (Integer) fields[10];
			if (partidoId != null) {
				partido = new ParPartido();
				partido.setParId((Integer) fields[10]);
				rendicion.setParPartido(partido);
			}

			rendicionListResult.add(rendicion);
		}

		if (rendicionListResult.size() > 0) {
			rendicion = rendicionListResult.get(0);
		} else {
			rendicion = null;
		}

		return rendicion;
	}
	
	public List<RenRendicion> getRendicionInstanciaFlujoByEvento(Integer eventoId, List<Integer> usuarioIds, Integer dtaId,Integer usuarioId) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("select r.ren_id,r.can_id,r.eve_id,r.ele_id,r.tpo_rendicion_id,r.ren_numero_rendicion, ");
		queryString.append("r.ren_fecha,r.ren_estado,r.ren_total_rendicion,r.ren_numero_documento,r.par_id,r.ren_total_lineas,r.ren_lineas_revisadas ");
		queryString.append("from ren_rendicion r, inf_instancia_flujo inf, int_instancia_tarea ins,  tpo_rendicion tr ");
		queryString.append("where r.can_id=inf.can_id ");
		
		if (eventoId != null) {
			queryString.append("and (r.eve_id= :eventoId) ");
		}
		
		if (usuarioIds != null) {
			queryString.append("and (usu_id_actual in :usuarioIds or ins.usu_id in :usuarioIds) ");
		}
		if (usuarioId !=null) {
			queryString.append("and ins.usu_id= :usuarioId ");
		}
		
		queryString.append("and ins.inf_id=inf.inf_id ");
		
		if (dtaId != null) {
			queryString.append("and ins.dta_id = :dtaId ");
		}
		queryString.append("and tr.tpo_rendicion_id=r.tpo_rendicion_id ");
		queryString.append("and (tr.tpo_codigo='F87' or tr.tpo_codigo='F88') ");
		queryString.append("and tr.eve_id=r.eve_id ");

		queryString.append("union ");
		queryString.append("select r.ren_id,r.can_id,r.eve_id,r.ele_id,r.tpo_rendicion_id,r.ren_numero_rendicion, ");
		queryString.append("r.ren_fecha,r.ren_estado,r.ren_total_rendicion,r.ren_numero_documento,r.par_id,r.ren_total_lineas,r.ren_lineas_revisadas ");
		queryString.append("from ren_rendicion r, inf_instancia_flujo inf, int_instancia_tarea ins,  tpo_rendicion tr ");
		queryString.append("where r.par_id=inf.par_id ");
		
		if (eventoId != null) {
			queryString.append("and (r.eve_id= :eventoId) ");
		}

		if (usuarioIds != null) {
			queryString.append("and (usu_id_actual in :usuarioIds or ins.usu_id in :usuarioIds) ");
		}
		if (usuarioId !=null) {
			queryString.append("and ins.usu_id= :usuarioId ");
		}
		
		queryString.append("and ins.inf_id=inf.inf_id ");
		
		if (dtaId != null) {
			queryString.append("and ins.dta_id = :dtaId ");
		}
		queryString.append("and tr.tpo_rendicion_id=r.tpo_rendicion_id ");
		queryString.append("and (tr.tpo_codigo='F87' or tr.tpo_codigo='F88') ");
		queryString.append("and tr.eve_id=r.eve_id ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		if (eventoId != null) {
			query.setParameter("eventoId", eventoId);
		}

		if (usuarioIds != null) {
			query.setParameter("usuarioIds", usuarioIds);
		}
		
		if (usuarioId !=null) {
			query.setParameter("usuarioId", usuarioId);
		}

		if (dtaId != null) {
			query.setParameter("dtaId", dtaId);
		}

		List<RenRendicion> rendicionList = (List<RenRendicion>) query.getResultList();
		List<RenRendicion> rendicionListResult = new ArrayList<>();
		RenRendicion rendicion;
		CanCandidato candidato;
		EveEventoEleccionario evento;
		EleEleccion eleccion;
		TpoRendicion tpoRendicion;
		ParPartido partido;

		Integer candidatoId;
		Integer eveId;
		Integer eleId;
		Integer tipoRendicionId;
		Integer partidoId;
		for (Object record : rendicionList) {
			Object[] fields = (Object[]) record;
			rendicion = new RenRendicion();

			rendicion.setRenId((Integer) fields[0]);

			candidatoId = (Integer) fields[1];
			if (candidatoId != null) {
				candidato = new CanCandidato();
				candidato.setCanId((Integer) fields[1]);
				rendicion.setCanCandidato(candidato);
			}
			eveId = (Integer) fields[2];
			if (eveId != null) {
				evento = new EveEventoEleccionario();
				evento.setEveId((Integer) fields[2]);
				rendicion.setEveEventoEleccionario(evento);
			}
			eleId = (Integer) fields[3];
			if (eleId != null) {
				eleccion = new EleEleccion();
				eleccion.setEleId((Integer) fields[3]);
				rendicion.setEleEleccion(eleccion);
			}
			tipoRendicionId = (Integer) fields[4];
			if (tipoRendicionId != null) {
				tpoRendicion = new TpoRendicion();
				tpoRendicion.setTpoRendicionId((Integer) fields[4]);
				rendicion.setTpoRendicion(tpoRendicion);
			}
			rendicion.setRenNumeroRendicion((Integer) fields[5]);
			if (fields[6] != null) {
				rendicion.setRenFecha((Date) fields[6]);
			}
			rendicion.setRenEstado((String) fields[7]);
			rendicion.setRenTotalRendicion(((BigInteger) fields[8]).longValue());
			rendicion.setRenNumeroDocumento((Integer) fields[9]);

			partidoId = (Integer) fields[10];
			if (partidoId != null) {
				partido = new ParPartido();
				partido.setParId((Integer) fields[10]);
				rendicion.setParPartido(partido);
			}
			rendicion.setRenTotalLineas((Integer) fields[11]);
			rendicion.setRenLineasRevisadas((String) fields[12]);
			rendicionListResult.add(rendicion);
		}
		return rendicionListResult;
	}
	
	public List<RenRendicion> getRendicionDinamica(Optional<Integer> idEvento,Optional<Integer> idCandidato,Optional<Integer> idPartido,Optional<Integer> eleccionId,Optional<Integer> idTipoRendicion) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("select r ");
		queryString.append("from RenRendicion r ");
		queryString.append("where 1=1 ");
		
		if (idEvento != null && idEvento.isPresent()) {
			queryString.append("and (r.eveEventoEleccionario.eveId= :idEvento) ");
		}
		
		if (idCandidato != null && idCandidato.isPresent()) {
			queryString.append("and (r.canCandidato.canId =:idCandidato) ");
		}
 
		if (idPartido != null && idPartido.isPresent()) {
			queryString.append("and r.parPartido.parId = :idPartido ");
		}
		if (eleccionId != null && eleccionId.isPresent()) {
			queryString.append("and r.eleEleccion.eleId = :eleccionId ");
		}
		if (idTipoRendicion != null && idTipoRendicion.isPresent()) {
			queryString.append("and r.tpoRendicion.tpoRendicionId = :idTipoRendicion ");
		}

		Query query = entityManager.createQuery(queryString.toString(),RenRendicion.class);
		
		if (idEvento != null && idEvento.isPresent()) {
			query.setParameter("idEvento", idEvento.get());
		}

		if (idCandidato != null && idCandidato.isPresent()) {
			query.setParameter("idCandidato", idCandidato.get());
		}

		if (idPartido != null && idPartido.isPresent()) {
			query.setParameter("idPartido", idPartido.get());
		}
		if (eleccionId != null && eleccionId.isPresent()) {
			query.setParameter("eleccionId", eleccionId.get());
		}

		if (idTipoRendicion != null && idTipoRendicion.isPresent()) {
			query.setParameter("idTipoRendicion", idTipoRendicion.get());
		}
		return (List<RenRendicion>) query.getResultList();
	}
	
	@Transactional
	@Modifying
	public void deleteByRendicion(int idRendicion) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("DELETE FROM ren_rendicion WHERE ren_id = ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idRendicion);
		
		query.executeUpdate();
	}
	
	private String getQueryReembolsoCandidatoPartido(String nombreAtributo) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append(" SELECT r.ren_id, ");
		queryString.append("        r.can_id,  ");
		queryString.append("        r.par_id,  ");
		queryString.append("        r.ren_fecha,  ");
		
		if (StringUtils.stripToEmpty(nombreAtributo).equalsIgnoreCase("rePartido")) {
			queryString.append("     (C.CAN_NOMBRES || ' ' ||  C.CAN_APP_PATERNO || ' ' || C.CAN_APP_MATERNO) AS NOMBRE_CANDIDATO, ");    
			queryString.append("     (C.CAN_RUT || '-' || C.CAN_RUT_DV) AS CAN_RUT, ");
			queryString.append("     C.CAN_ID AS can_id_concepto, ");
		}
		
		queryString.append("        detalle -> '").append(nombreAtributo).append("' ->> 'totalMontoAutorizado' AS monto_reembolso,  ");
		queryString.append("        COALESCE((detalle -> '").append(nombreAtributo).append("' ->> 'montoPas') :: INTEGER, 0) AS suma_pas,  ");
		queryString.append("        COALESCE(SUM(dn.det_monto), 0) AS suma_nomina,  ");
		queryString.append("        (  ");
		queryString.append("              ((detalle ->'").append(nombreAtributo).append("' ->> 'totalMontoAutorizado') :: integer) - (COALESCE((detalle -> '").append(nombreAtributo).append("' ->> 'montoPas') :: INTEGER, 0) + COALESCE(SUM(dn.det_monto),0))  ");
		queryString.append("        ) AS total_pagar,  ");
		queryString.append("        x.det_id,   ");
		queryString.append("        detalle -> '").append(nombreAtributo).append("' ->> 'rePartidoSeleccionado' AS partido_seleccionado   ");
		queryString.append("   FROM ren_rendicion r  ");
		queryString.append("   JOIN (  ");
		queryString.append("          	SELECT JSONB((dr.det_valor) :: JSON) AS detalle,  ");
		queryString.append("          	       dr.det_id,  ");
		queryString.append("          	       dr.ren_id   ");
		queryString.append("          	  FROM det_detalle_rendicion dr  ");
		queryString.append("              WHERE id_tipo_seccion IN (?)  ");
		queryString.append("        ) AS x  ");
		queryString.append("     ON r.ren_id = x.ren_id ");
		queryString.append("   LEFT JOIN (SELECT * ");
		queryString.append("       	        FROM DET_DETALLE_NOMINA DET_NOM ");
		queryString.append("     		    JOIN nom_nomina n  ");
		queryString.append("     		      ON DET_NOM.nom_id = n.nom_id "); 
		queryString.append("               WHERE n.tpo_nomina_id = ? ");
		queryString.append("              ) AS DN  ");
		
		if (StringUtils.stripToEmpty(nombreAtributo).equalsIgnoreCase("rePartido")) {
			queryString.append("     ON DN.PAR_ID = CAST (detalle -> 'rePartido' ->> 'rePartidoSeleccionado' AS INTEGER) ");
			queryString.append("    AND DN.CAN_ID_REF = R.CAN_ID ");
		} else {
			queryString.append("     ON DN.PAR_ID = R.PAR_ID "); 
			queryString.append("     OR DN.CAN_ID = R.CAN_ID ");
		}
		
		queryString.append("  INNER JOIN INF_INSTANCIA_FLUJO inf  ");
		queryString.append("     ON inf.CAN_ID = r.CAN_ID  ");
		queryString.append("     OR inf.PAR_ID = r.PAR_ID  ");
		
		if (StringUtils.stripToEmpty(nombreAtributo).equalsIgnoreCase("rePartido")) {
			queryString.append("     JOIN CAN_CANDIDATO C ");  
			queryString.append("       ON C.CAN_ID = r.CAN_ID ");
		}
		
		queryString.append("  WHERE r.eve_id = ? ");
		queryString.append("    AND inf.INF_ESTADO_FLUJO = 'EJECUTORIADA'  ");
		queryString.append("    AND detalle -> '").append(nombreAtributo).append("' ->> 'estado' <> 'CONGELADO' ");
		queryString.append("  GROUP BY   ");
		queryString.append("          r.ren_id,   ");
		queryString.append("          r.can_id,  ");
		queryString.append("          r.par_id,  ");
		
		if (StringUtils.stripToEmpty(nombreAtributo).equalsIgnoreCase("rePartido")) {
			queryString.append("          (C.CAN_NOMBRES || ' ' ||  C.CAN_APP_PATERNO || ' ' || C.CAN_APP_MATERNO), ");
			queryString.append("          (C.CAN_RUT || '-' || C.CAN_RUT_DV), ");
			queryString.append("     	  C.CAN_ID, ");
		}
		
		queryString.append("          x.det_id,  ");
		queryString.append("          detalle -> '").append(nombreAtributo).append("' ->> 'totalMontoAutorizado',  ");
		queryString.append("          detalle -> '").append(nombreAtributo).append("' ->> 'rePartidoSeleccionado', ");
		queryString.append("          detalle -> '").append(nombreAtributo).append("' ->> 'montoPas' ");
		queryString.append(" HAVING (   ");
		queryString.append("              ((detalle -> '").append(nombreAtributo).append("' ->> 'totalMontoAutorizado') :: integer) - (COALESCE((detalle -> '").append(nombreAtributo).append("' ->> 'montoPas') :: INTEGER, 0) + COALESCE(SUM(dn.det_monto),0))  ");
		queryString.append("        ) > 0  ");
		
		
		
		return queryString.toString();
	}
	
	private String getQueryReembolsoMandato() {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("SELECT DETALLES ->> 'canId' AS CAN_ID, ");
		queryString.append("	   DETALLES ->> 'montoAutorizado' AS monto_reembolso, ");
		queryString.append("	   (CAST (DETALLES ->> 'montoAutorizado' AS INTEGER) - CAST (DETALLES ->> 'montoPas' AS INTEGER)) AS TOTAL_PAGAR, ");
		queryString.append("	   COALESCE (CAST (DETALLES ->> 'montoPas' AS INTEGER), 0) AS MONTO_PAS, ");
		queryString.append("	   CAST (DETALLES ->> 'manId' AS BIGINT) AS MANDATO, ");
		queryString.append("	   CAST(DETALLES ->> 'ebaId' AS INTEGER) AS EBA_ID, ");
		queryString.append("	   DETALLES ->> 'parId' AS PAR_ID, ");
		queryString.append("	   CAST (DETALLES ->> 'manCuentaBancaria' AS BIGINT) AS CUENTA,  ");
		queryString.append("	   DETALLES ->> 'manNumeroDocumento' AS NUMERO_DOCUMENTO, ");
		queryString.append("	   EB.EBA_RUT AS RUT, ");
		queryString.append("	   EB.EBA_NOMBRE_BANCO AS NOMBRE, ");
		queryString.append("	   DETALLE.DET_ID ");
		queryString.append("  FROM (  ");
		queryString.append("		SELECT JSON_ARRAY_ELEMENTS ((JSONB (DR.DET_VALOR :: JSON) ->> 'reCreditoMandatoDetalle') :: JSON) AS DETALLES, ");
		queryString.append("			   DR.DET_ID, ");
		queryString.append("			   DR.REN_ID ");
		queryString.append("		  FROM DET_DETALLE_RENDICION DR ");
		queryString.append("		 WHERE DR.ID_TIPO_SECCION IN (?)  ");
		queryString.append("       ) as DETALLE ");
		queryString.append("  JOIN EBA_ENTIDAD_BANCARIA EB ");
		queryString.append("    ON EB.EBA_ID = CAST (DETALLES ->> 'ebaId' AS INTEGER) ");
		queryString.append("  JOIN REN_RENDICION R ");
		queryString.append("    ON R.REN_ID = DETALLE.REN_ID ");
		queryString.append("  JOIN INF_INSTANCIA_FLUJO INF  ");
		queryString.append("    ON INF.CAN_ID = R.CAN_ID ");
		queryString.append("    OR INF.PAR_ID = R.PAR_ID ");
		queryString.append(" WHERE DETALLES ->> 'estado' <> 'CONGELADO' ");
		queryString.append("   AND UPPER (DETALLES ->> 'pagado') = 'FALSE' ");
		queryString.append("   AND INF.INF_ESTADO_FLUJO = 'EJECUTORIADA' ");
		queryString.append("   AND INF.EVE_ID = ? ");
		queryString.append(" GROUP BY  ");
		queryString.append("  	   DETALLES ->> 'canId', ");
		queryString.append("	   DETALLES ->> 'montoAutorizado', ");
		queryString.append("	   COALESCE (CAST (DETALLES ->> 'montoPas' AS INTEGER), 0), ");
		queryString.append("	   CAST (DETALLES ->> 'manId' AS BIGINT), ");
		queryString.append("	   DETALLES ->> 'ebaId', ");
		queryString.append("	   DETALLES ->> 'parId', ");
		queryString.append("	   CAST (DETALLES ->> 'manCuentaBancaria' AS BIGINT),  ");
		queryString.append("	   DETALLES ->> 'manNumeroDocumento', ");
		queryString.append("	   EB.EBA_RUT, ");
		queryString.append("	   EB.EBA_NOMBRE_BANCO, ");
		queryString.append("	   DETALLE.DET_ID, ");
		queryString.append("	   (CAST (DETALLES ->> 'montoAutorizado' AS INTEGER) - CAST (DETALLES ->> 'montoPas' AS INTEGER)) ");
		queryString.append("HAVING (CAST (DETALLES ->> 'montoAutorizado' AS INTEGER) - CAST (DETALLES ->> 'montoPas' AS INTEGER)) > 0 ");
		
		return queryString.toString();
	}
	
	public List<PagoPendiente> getReembolsosPendientes(int eveId, int tipoSeccion, String tipoCodigo, String nombreAtributo, int tipoNomina, boolean creditoMandato) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		List<PagoPendiente> pagos = null;
		
		try {
			String queryString = null;
			
			LOG.info("parametros para consulta de reembolsos pendientes ==> eveId {} ==> tipoSeccion {} ==> tipoCodigo {} ==> nombreAtributo {} ==> tipoNomina {} ==> creditoMandato {}", eveId, tipoSeccion, tipoCodigo, nombreAtributo, tipoNomina, creditoMandato);
			
			if (!creditoMandato) {
				queryString = getQueryReembolsoCandidatoPartido(nombreAtributo);
			} else {
				queryString = getQueryReembolsoMandato();
			}
			

			LOG.info("Query al obtener reembolsos pendientes \n{}" , queryString.toString());

			con = ds.getConnection();
			ps = con.prepareStatement(queryString);
			
			ps.setInt(1, tipoSeccion);
			ps.setInt(!creditoMandato ? 3 : 2, eveId);
			
			if (!creditoMandato) {
				ps.setInt(2, tipoNomina);
			}
			
			ps.execute();
			
			rs = ps.getResultSet();
			
			PagoPendiente pago = null;
			pagos = new ArrayList<PagoPendiente>();
			
			while (rs.next()) {
				pago = new PagoPendiente();
				pago.setRenId(rs.getInt("det_id"));
				pago.setCanId(rs.getInt("can_id"));
				pago.setParId(rs.getInt("par_id"));
				pago.setMontoReembolso(rs.getDouble("monto_reembolso"));
				
				if (!creditoMandato) {
					pago.setSumaPas(rs.getDouble("suma_pas"));
					pago.setPartidoSeleccionado(rs.getInt("partido_seleccionado"));
					pago.setSumaNomina(rs.getDouble("suma_nomina"));
					pago.setFecha(rs.getDate("ren_fecha"));
				} else {
					pago.setRutBanco(rs.getString("rut"));
					pago.setNumeroCuenta(rs.getLong("CUENTA"));
					pago.setEbaId(rs.getInt("EBA_ID"));
					pago.setIdMandato(rs.getLong("MANDATO"));
					
					pago.setSujeto(new Sujeto());
					
					if (pago.getCanId() != null && pago.getCanId() > 0) {
						pago.getSujeto().setId(pago.getCanId());
						pago.getSujeto().setTipo("candidato");
					} else {
						pago.getSujeto().setId(pago.getParId());
						pago.getSujeto().setTipo("partido");
					}
				}
			
				if (StringUtils.stripToEmpty(nombreAtributo).equalsIgnoreCase("rePartido")) {
					pago.setSujeto(new Sujeto());
					pago.getSujeto().setId(rs.getInt("can_id_concepto"));
					pago.getSujeto().setNombre(rs.getString("NOMBRE_CANDIDATO"));
					pago.getSujeto().setRut(rs.getString("CAN_RUT"));
					pago.getSujeto().setTipo("candidato");
				}
				
				pago.setTotalPagar(rs.getDouble("total_pagar"));
				
				pagos.add(pago);
			}
		} catch (Exception e) {
			LOG.error("Ocurrio un error al consultar los reembolsos pendientes", e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return pagos;
	}
	
	public LinkedList<IngresoGastoDTO> getIngresosGasto(int eveId, int tipoEvento, int tipoEleccion, long desde, long hasta) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null; 
		LinkedList<IngresoGastoDTO> result = null;
		java.sql.Date fechaInicio = new java.sql.Date(desde);
		java.sql.Date fechaFin = new java.sql.Date(hasta);
		
		try {
			con = ds.getConnection();
			preparedStatement = con.prepareStatement(getQueryIngresosGastosCandidatos());
			
			preparedStatement.setInt(1, eveId);
			preparedStatement.setInt(2, tipoEvento);
			preparedStatement.setInt(3, tipoEleccion);
			preparedStatement.setDate(4, fechaInicio);
			preparedStatement.setDate(5, fechaFin);
			
			rs = preparedStatement.executeQuery();
			
			result = new LinkedList<IngresoGastoDTO>();
			
			obtenerDatosIngresosGastos(rs, result);		
			rs.close();
			preparedStatement.close();
			
			preparedStatement = con.prepareStatement(getQueryIngresosGastosPartidos());
			
			preparedStatement.setInt(1, eveId);
			preparedStatement.setInt(2, tipoEvento);
			preparedStatement.setInt(3, tipoEleccion);
			preparedStatement.setDate(4, fechaInicio);
			preparedStatement.setDate(5, fechaFin);
			
			rs = preparedStatement.executeQuery();
			
			obtenerDatosIngresosGastos(rs, result);
		} catch (Exception e) {
			LOG.error("Ocurrio un error al obtener la información de ingresos y gastos", e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (preparedStatement != null) preparedStatement.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private void obtenerDatosIngresosGastos(ResultSet rs, LinkedList<IngresoGastoDTO> result) throws Exception {
		IngresoGastoDTO ingresoGasto = new IngresoGastoDTO();
		Map<Integer, List<IngresoGastoDTO>> ingresosGastos = new HashMap<Integer, List<IngresoGastoDTO>>();
				
		while (rs.next()) {
			ingresoGasto = new IngresoGastoDTO();
			ingresoGasto.setSujeto(new Sujeto());
			
			ingresoGasto.setTipoRendicion(rs.getString("TIPO_RENDICION")); 
			ingresoGasto.setEleccion(rs.getString("ELECCION"));
			ingresoGasto.setTerritorio(rs.getString("TERRITORIO"));
			ingresoGasto.getSujeto().setId(rs.getInt("CODIGO_SUJETO"));
			ingresoGasto.getSujeto().setRut(rs.getString("RUT_SUJETO"));
			ingresoGasto.getSujeto().setDv(rs.getString("RUT_DV_SUJETO"));
			ingresoGasto.getSujeto().setNombre(rs.getString("NOMBRE_SUJETO"));
		   	ingresoGasto.setAfiliacion(rs.getString("AFILIACION"));
		   	ingresoGasto.setPartido(rs.getString("PARTIDO"));
		   	ingresoGasto.setRutProveedor(rs.getInt("RUT_PROVEEDOR")); 
		   	ingresoGasto.setDvProveedor(rs.getString("DV_PROVEEDOR")); 
		   	ingresoGasto.setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
		   	ingresoGasto.setFechaDocumento(rs.getLong("FECHA_DOCUMENTO"));
		   	ingresoGasto.setTipoDocumento(rs.getString("TIPO_DOCUMENTO")); 
		   	ingresoGasto.setDescripcionTipoDocumento(rs.getString("DESCRIPCION_TIPO_DOCUMENTO"));
		   	ingresoGasto.setTipoCuenta(rs.getInt("TIPO_CUENTA"));
		   	ingresoGasto.setDescripcionCuenta(rs.getString("DESCRIPCION_CUENTA"));
		   	ingresoGasto.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
		   	ingresoGasto.setGlosaDocumento(rs.getString("GLOSA_DOCUMENTO"));
		   	ingresoGasto.setMontoDocumento(rs.getDouble("MONTO_DOCUMENTO"));
		   	ingresoGasto.setRegion(rs.getString("REGION"));
		   	
		   	if (!ingresosGastos.containsKey(ingresoGasto.getSujeto().getId())) {
		   		ingresosGastos.put(ingresoGasto.getSujeto().getId(), new ArrayList<IngresoGastoDTO>());
		   	}
		   	
		   	ingresosGastos.get(ingresoGasto.getSujeto().getId()).add(ingresoGasto);
		}
		
		for (Entry<Integer, List<IngresoGastoDTO>> entry : ingresosGastos.entrySet()) {
			result.addAll(entry.getValue());
		}
	}
	
	private String getQueryIngresosGastosCandidatos() {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT TIPO_RENDICION, ");
		query.append("       ELECCION, ");
		query.append("       TERRITORIO, ");
		query.append("       CODIGO_SUJETO, ");
		query.append("       CAST(RUT_SUJETO AS VARCHAR),");
		query.append("       RUT_DV_SUJETO, ");
		query.append("       NOMBRE_SUJETO, ");
		query.append("       AFILIACION, ");
		query.append("       PARTIDO, ");
		query.append("       CAST(DETALLE ->> 'rut' AS INTEGER) AS RUT_PROVEEDOR, ");
		query.append("       DETALLE ->> 'dv' AS DV_PROVEEDOR, ");
		query.append("       DETALLE ->> 'nombreProveedor' AS NOMBRE_PROVEEDOR, ");
		query.append("       CAST(DETALLE ->> 'fechaDocumentoTmstp' AS BIGINT) AS FECHA_DOCUMENTO, ");
		query.append("       DETALLE -> 'tipoDocumento' ->> 'tpoDocCodigo' AS TIPO_DOCUMENTO, ");
		query.append("       DETALLE -> 'tipoDocumento' ->> 'tpoDocNombre' AS DESCRIPCION_TIPO_DOCUMENTO, ");
		query.append("       CAST(DETALLE -> 'numeroCuenta' ->> 'tpoCtaCodigo' AS INTEGER) AS TIPO_CUENTA, ");
		query.append("       DETALLE -> 'numeroCuenta' ->> 'tpoCtaNombre' AS DESCRIPCION_CUENTA, ");
		query.append("       DETALLE ->> 'numeroDocumento' AS NUMERO_DOCUMENTO, ");
		query.append("       DETALLE ->> 'glosa' AS GLOSA_DOCUMENTO, ");
		query.append("       CAST(DETALLE ->> 'monto' AS DECIMAL) AS MONTO_DOCUMENTO, ");
		query.append("       REGION ");
		query.append("  FROM ( ");
		query.append("        SELECT TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append("                E.ELE_NOMBRE AS ELECCION, ");
		query.append("                NIV.NIV_DESCRIPCION AS TERRITORIO, ");
		query.append("                C.CAN_ID AS CODIGO_SUJETO, ");
		query.append("                C.CAN_RUT AS RUT_SUJETO, ");
		query.append("                C.CAN_RUT_DV AS RUT_DV_SUJETO, ");
		query.append("                C.CAN_NOMBRES || ' ' || C.CAN_APP_PATERNO || ' ' || C.CAN_APP_MATERNO AS NOMBRE_SUJETO, ");
		query.append("                TC.TPO_NOMBRE AS AFILIACION, ");
		query.append("                P.PAR_NOMBRE PARTIDO, ");
		query.append("                JSON_ARRAY_ELEMENTS (JSONB (DR.DET_VALOR :: JSON) :: JSON) AS DETALLE, ");
		query.append("                NIV2.NIV_DESCRIPCION AS REGION ");
		query.append("            FROM REN_RENDICION R ");
		query.append("            JOIN TPO_RENDICION TR ");
		query.append("              ON R.TPO_RENDICION_ID = TR.TPO_RENDICION_ID ");
		query.append("            JOIN CAN_CANDIDATO C ");
		query.append("              ON C.CAN_ID = R.CAN_ID ");
		query.append("            JOIN ELE_ELECCION E ");
		query.append("              ON E.ELE_ID = R.ELE_ID ");
		query.append("            JOIN TPO_NIVEL TN ");
		query.append("              ON E.TPO_NIV_ID = TN.TPO_NIV_CODIGO ");
		query.append("            JOIN TPO_CANDIDATO TC ");
		query.append("              ON TC.TPO_CANDIDATO_ID = C.TPO_CAN_ID ");
		query.append("            LEFT JOIN PAR_PARTIDO P ");
		query.append("              ON P.PAR_ID = C.PAR_ID ");
		query.append("            JOIN DET_DETALLE_RENDICION DR ");
		query.append("              ON DR.REN_ID = R.REN_ID ");
		query.append("            JOIN SEL_SUB_ELECCION SEL ");
		query.append("              ON C.SEL_ID = SEL.SEL_ID ");
		query.append("            JOIN NIV_NIVEL NIV ");
		query.append("              ON NIV.NIV_ID = SEL.NIV_ID ");
		query.append("            JOIN RLC_REL_NIVEL RNI ");
		query.append("              ON RNI.NIV_ID_HIJO = NIV.NIV_ID ");
		query.append("            JOIN NIV_NIVEL NIV2 ");
		query.append("              ON NIV2.NIV_ID = RNI.NIV_ID_PADRE ");
		query.append("           WHERE R.EVE_ID = ? ");
		query.append("             AND TR.TPO_CODIGO IN ('F87', 'F88') ");
		query.append("             AND E.TPO_EVENTO_ID = ? ");
		query.append("             AND E.TPO_ELECCION_ID = ? ");
		query.append("             AND DR.DET_VALOR LIKE '%\"rut\"%' ");
		query.append("             AND R.REN_FECHA BETWEEN ? AND ? ");
		query.append("           ORDER BY R.CAN_ID ");
		query.append("        ) AS INFO ");
		
		return query.toString();
	}
	
	private String getQueryIngresosGastosPartidos() {
		StringBuilder query = new StringBuilder();
		
		query.append("  SELECT TIPO_RENDICION, ");
		query.append(" 	   	   ELECCION, ");
		query.append(" 	   	   TERRITORIO, ");
		query.append(" 	   	   CODIGO_SUJETO, ");
		query.append(" 	   	   CAST(RUT_SUJETO AS VARCHAR), ");
		query.append(" 	   	   RUT_DV_SUJETO, ");
		query.append(" 	   	   NOMBRE_SUJETO, ");
		query.append(" 	   	   AFILIACION, ");
		query.append(" 	   	   PARTIDO, ");
		query.append(" 	   	   CAST(DETALLE ->> 'rut' AS INTEGER) AS RUT_PROVEEDOR, ");
		query.append(" 	   	   DETALLE ->> 'dv' AS DV_PROVEEDOR, ");
		query.append(" 	   	   DETALLE ->> 'nombreProveedor' AS NOMBRE_PROVEEDOR, ");
		query.append(" 	   	   CAST(DETALLE ->> 'fechaDocumentoTmstp' AS BIGINT) AS FECHA_DOCUMENTO, ");
		query.append(" 	   	   DETALLE -> 'tipoDocumento' ->> 'tpoDocCodigo' AS TIPO_DOCUMENTO, ");
		query.append(" 	   	   DETALLE -> 'tipoDocumento' ->> 'tpoDocNombre' AS DESCRIPCION_TIPO_DOCUMENTO, ");
		query.append(" 	   	   CAST(DETALLE -> 'numeroCuenta' ->> 'tpoCtaCodigo' AS INTEGER) AS TIPO_CUENTA, ");
		query.append(" 	   	   DETALLE -> 'numeroCuenta' ->> 'tpoCtaNombre' AS DESCRIPCION_CUENTA, ");
		query.append(" 	   	   DETALLE ->> 'numeroDocumento' AS NUMERO_DOCUMENTO, ");
		query.append(" 	   	   DETALLE ->> 'glosa' AS GLOSA_DOCUMENTO, ");
		query.append(" 	   	   CAST(DETALLE ->> 'monto' AS DECIMAL) AS MONTO_DOCUMENTO, ");
		query.append(" 	   	   REGION ");
		query.append("    FROM ( ");
		query.append(" 	 	SELECT TR.TPO_CODIGO AS TIPO_RENDICION, ");
		query.append(" 			   E.ELE_NOMBRE AS ELECCION, ");
		query.append(" 			   TN.TPO_NIV_DESCRIPCION AS TERRITORIO, ");
		query.append(" 			   P.PAR_ID AS CODIGO_SUJETO, ");
		query.append(" 			   (SELECT split_part(P.PAR_RUT, '-', 1)) AS RUT_SUJETO, ");
		query.append(" 			   (SELECT split_part(P.PAR_RUT, '-', 2)) AS RUT_DV_SUJETO, ");
		query.append(" 			   P.PAR_NOMBRE AS NOMBRE_SUJETO, ");
		query.append(" 			   'N/A' AS AFILIACION, ");
		query.append(" 			   P.PAR_NOMBRE PARTIDO, ");
		query.append(" 			   JSON_ARRAY_ELEMENTS (JSONB (DR.DET_VALOR :: JSON) :: JSON) AS DETALLE, ");
		query.append(" 			   '' AS REGION ");
		query.append(" 		  FROM REN_RENDICION R ");
		query.append(" 		  JOIN TPO_RENDICION TR ");
		query.append(" 		    ON R.TPO_RENDICION_ID = TR.TPO_RENDICION_ID ");
		query.append(" 		  JOIN PAR_PARTIDO P ");
		query.append(" 		    ON P.PAR_ID = R.PAR_ID ");
		query.append(" 		  JOIN ELE_ELECCION E ");
		query.append(" 		    ON E.ELE_ID = R.ELE_ID ");
		query.append(" 		  JOIN TPO_NIVEL TN ");
		query.append(" 		    ON E.TPO_NIV_ID = TN.TPO_NIV_CODIGO ");
		query.append(" 		  JOIN DET_DETALLE_RENDICION DR ");
		query.append(" 		    ON DR.REN_ID = R.REN_ID ");
		query.append(" 		 WHERE R.EVE_ID = ? ");
		query.append(" 		   AND TR.TPO_CODIGO IN ('F87', 'F88') ");
		query.append(" 		   AND E.TPO_EVENTO_ID = ? ");
		query.append(" 		   AND E.TPO_ELECCION_ID = ? ");
		query.append(" 		   AND DR.DET_VALOR LIKE '%\"rut\"%' ");
		query.append("         AND R.REN_FECHA BETWEEN ? AND ? ");
		query.append(" 		 ORDER BY R.PAR_ID ");
		query.append("  	   ) AS INFO ");
		
		return query.toString();
	}
}
