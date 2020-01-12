package cl.servel.gasto.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import cl.servel.gasto.entity.AdmAdministradorElectoral;
import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.DetDetalleNomina;
import cl.servel.gasto.entity.DetDetalleRendicion;
import cl.servel.gasto.entity.EbaEntidadBancaria;
import cl.servel.gasto.entity.NomNomina;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.TpoTipos;
import cl.servel.gasto.transients.PagoMultaDTO;

public class DetDetalleNominaRepositoryImpl implements DetDetalleNominaRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	DetDetalleNominaRepository detalleNominaRepository;
	@Autowired
	NomNominaRepository nominaRepository;
	
	@Autowired
	ParPartidoRepository partidoRepository;
	
	@Autowired
	CanCandidatoRepository candidatoRepository;
	
	@Autowired
	AdministradorElectoralRepository administradorElectoralRepository;
	
	@Autowired
	TpoTiposRepository tiposRepository;
	
	@Autowired
	DetDetalleRendicionRepository detalleRendicionRepository;
	
	@Autowired
	EbaEntidadBancariaRepository entidadBancariaRepository;
	
	@Autowired
	private DataSource ds;
	

	@SuppressWarnings("unchecked")
	public Optional<List<DetDetalleNomina>> leeDetalleNominaIndependientes(Integer nomId,String partidoCodigoOrigen,Integer eventoId,Integer tipoCandidato,List<String> tipoPago,List<Integer> idEleccion, Integer dtaId,List<String> seccionesPago, String tipoRendicionReembolso)
	{

		StringBuilder queryString = new StringBuilder();

		queryString.append("		select	t3.det_id, t3.par_id,	cast(t3.can_id as Integer),	t3.adm_id,	t3.det_rut,	t3.det_rut_dv,	t3.tpo_moneda_id, sum(t3.total_monto) total_monto, ");
		queryString.append("		t3.det_transaction_id,	t3.det_resultado_tgr,	t3.det_fecha_pago, t3.det_estado,	t3.det_rendicion_id, t3.cuentaPartidosVigentes ");
		queryString.append("from ");
		queryString.append("	( ");
		queryString.append("	select ");
		queryString.append("		dn.det_id,p.par_id, ");
		queryString.append("       case when dn.can_id is not null then dn.can_id else dn.can_id_ref end as can_id, ");
		queryString.append("       dn.adm_id,	dn.det_rut,	dn.det_rut_dv,	dn.tpo_moneda_id, ");
		queryString.append("		sum(dn.det_monto) total_monto, dn.det_transaction_id,	dn.det_resultado_tgr, ");
		queryString.append("		dn.det_fecha_pago,	dn.det_estado,	dn.det_rendicion_id, ");
		queryString.append("		( ");
		queryString.append("			select count(distinct p2.par_id) ");
		queryString.append("		from ");
		queryString.append("			par_partido p2, ");
		queryString.append("			rlc_partido_nivel rlc2, ");
		queryString.append("			par_partido_maestro pm2 ");
		queryString.append("		where ");
		queryString.append("			rlc2.id_par_partido_maestro = pm2.id "); 
		queryString.append("			and p2.par_codigo_origen = pm2.codigo_origen ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("			and ele_id in :idEleccion "); 
		}
		queryString.append("			and pac_id = p.pac_id ");
		queryString.append("			and niv_id = rl.niv_id_padre ");
		queryString.append("		group by "); 
		queryString.append("			p2.pac_id, "); 
		queryString.append("			rlc2.niv_id) cuentaPartidosVigentes ");
		queryString.append("	from ");
		queryString.append("		can_candidato c, ");
		queryString.append("		sel_sub_eleccion s, ");
		queryString.append("		rlc_rel_nivel rl, "); 
		queryString.append("		par_partido p , ");
		queryString.append("		tpo_tipos t, ");
		queryString.append("		nom_nomina n, ");
		queryString.append("		det_detalle_nomina dn ");
		queryString.append("	where ");
		queryString.append("		(dn.can_id= c.can_id or dn.can_id_ref=c.can_id)");
		queryString.append("		and dn.nom_id=n.nom_id ");
		queryString.append("		and n.tpo_nomina_id=t.tpo_id ");
		queryString.append("		and t.tpo_codigo in :tipoPago ");
		//		queryString.append("		and dn.det_estado = :estado ");
		if (nomId!=null) {
			queryString.append("		and dn.nom_id = :nomId ");
		}
		if(partidoCodigoOrigen!=null) {
			queryString.append("		and p.par_codigo_origen = :partidoCodigoOrigen ");
		}
		queryString.append("		and dn.det_rendicion_id is not null ");
		queryString.append("		and c.sel_id = s.sel_id ");
		queryString.append("		and c.tpo_can_id = :tipoCandidato ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and s.ele_id in  :idEleccion ");
		}
		queryString.append("		and c.sup_id is null ");
		queryString.append("		and c.pac_id is not null ");
		queryString.append("		and p.pac_id = c.pac_id ");
		queryString.append("		and rl.niv_id_hijo = s.niv_id ");
		queryString.append("		and rl.tpo_niv_codigo_padre = 'MREG' ");
		queryString.append("		and (p.par_id, ");
		queryString.append("		rl.niv_id_padre) in ( ");
		queryString.append("		select ");
		queryString.append("			p.par_id, ");
		queryString.append("			r.niv_id ");
		queryString.append("		from ");
		queryString.append("			par_partido_maestro pm, ");
		queryString.append("			par_partido p, ");
		queryString.append("			ele_eleccion e, "); 
		queryString.append("			rlc_partido_nivel r "); 
		queryString.append("		where ");
		queryString.append("			pm.codigo_origen = p.par_codigo_origen ");
		queryString.append("			and p.ele_id = e.ele_id "); 
		queryString.append("			and e.eve_id_eve = :eventoId ");
		queryString.append("			and r.id_par_partido_maestro = pm.id ");
		queryString.append("			and r.eve_id = :eventoId ) ");
		queryString.append("	group by ");
		queryString.append("		dn.det_id, p.par_id, ");
		queryString.append(" case when dn.can_id is not null then dn.can_id else dn.can_id_ref end , ");
		queryString.append("      dn.adm_id,dn.det_rut,dn.det_rut_dv,dn.tpo_moneda_id,dn.det_transaction_id,dn.det_resultado_tgr,dn.det_fecha_pago, ");
		queryString.append("		dn.det_estado,dn.det_rendicion_id,p.pac_id,	rl.niv_id_padre ");
		queryString.append("union ");
		queryString.append("		select ");
		queryString.append("		dn.det_id, t1.par_id, ");
		queryString.append("       case when dn.can_id is not null then dn.can_id else dn.can_id_ref end as can_id, ");
		queryString.append("       dn.adm_id,	dn.det_rut,	dn.det_rut_dv,	dn.tpo_moneda_id, ");
		queryString.append("		sum(dn.det_monto) total_monto, dn.det_transaction_id,	dn.det_resultado_tgr, ");
		queryString.append("		dn.det_fecha_pago,	dn.det_estado,	dn.det_rendicion_id, ");
		queryString.append("		( ");
		queryString.append("			select count(distinct p2.par_id) ");
		queryString.append("		from ");
		queryString.append("			rlc_subpacto_partido rs2, ");
		queryString.append("			rlc_partido_nivel rlc2, ");
		queryString.append("			par_partido_maestro pm2, ");
		queryString.append("			par_partido p2 ");
		queryString.append("		where ");
		queryString.append("			rlc2.id_par_partido_maestro = pm2.id ");
		queryString.append("			and p2.par_id = rs2.par_id ");
		queryString.append("			and p2.par_codigo_origen = pm2.codigo_origen ");
		queryString.append("			and rs2.spa_id = t1.spa_id ");
		queryString.append("			and rlc2.niv_id = rl.niv_id_padre ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and p2.ele_id in  :idEleccion ");
		}
		queryString.append("		group by ");
		queryString.append("			rs2.spa_id, ");
		queryString.append("			rlc2.niv_id) cuentaPartidosVigentes ");
		queryString.append("	from ");
		queryString.append("		can_candidato c, ");
		queryString.append("		sel_sub_eleccion s , ");
		queryString.append("		rlc_rel_nivel rl, ");
		queryString.append("		( ");
		queryString.append("			select rs.par_id, ");
		queryString.append("			p.par_codigo_origen, ");
		queryString.append("			rs.spa_id ");
		queryString.append("		from ");
		queryString.append("			rlc_subpacto_partido rs, ");
		queryString.append("			par_partido p ");
		queryString.append("		where ");
		queryString.append("			p.par_id = rs.par_id ");
		queryString.append("			) t1 , ");

		queryString.append("		tpo_tipos t, ");
		queryString.append("		nom_nomina n, ");

		queryString.append("		det_detalle_nomina dn ");
		queryString.append("	where ");
		queryString.append("		(dn.can_id= c.can_id or dn.can_id_ref=c.can_id)");

		//		queryString.append("		and dn.det_estado = :estado ");
		queryString.append("		and dn.nom_id=n.nom_id ");
		queryString.append("		and n.tpo_nomina_id=t.tpo_id ");
		queryString.append("		and t.tpo_codigo in :tipoPago ");
		if (nomId!=null) {
			queryString.append("		and dn.nom_id = :nomId ");
		}
		if(partidoCodigoOrigen!=null) {
			queryString.append("		and t1.par_codigo_origen = :partidoCodigoOrigen ");
		}
		queryString.append("		and dn.det_rendicion_id is not null ");
		queryString.append("		and c.sel_id = s.sel_id ");
		queryString.append("		and c.tpo_can_id = :tipoCandidato ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and s.ele_id in  :idEleccion ");
		}
		queryString.append("		and c.sup_id is not null ");
		queryString.append("		and t1.spa_id = c.sup_id ");
		queryString.append("		and rl.niv_id_hijo = s.niv_id ");
		queryString.append("		and rl.tpo_niv_codigo_padre = 'MREG' ");
		queryString.append("		and (t1.par_id, ");
		queryString.append("		rl.niv_id_padre) in ( ");
		queryString.append("		select ");
		queryString.append("			p.par_id, ");
		queryString.append("			r.niv_id ");
		queryString.append("		from ");
		queryString.append("			par_partido_maestro pm, ");
		queryString.append("			par_partido p, ");
		queryString.append("			ele_eleccion e, ");
		queryString.append("			rlc_partido_nivel r ");
		queryString.append("		where ");
		queryString.append("			pm.codigo_origen = p.par_codigo_origen ");
		queryString.append("			and p.ele_id = e.ele_id ");
		queryString.append("			and e.eve_id_eve = :eventoId ");
		queryString.append("			and r.id_par_partido_maestro = pm.id ");
		queryString.append("			and r.eve_id = :eventoId ) ");
		queryString.append("	group by ");
		queryString.append("		dn.det_id, t1.par_id, ");
		queryString.append(" case when dn.can_id is not null then dn.can_id else dn.can_id_ref end , ");
		queryString.append("      dn.adm_id,dn.det_rut,dn.det_rut_dv,dn.tpo_moneda_id,dn.det_transaction_id,dn.det_resultado_tgr,dn.det_fecha_pago, ");
		queryString.append("	   dn.det_estado,dn.det_rendicion_id,t1.spa_id,rl.niv_id_padre,rl.niv_id_hijo,c.can_id ");
		//se agregan pactos y subpactos
		queryString.append("union ");
		//pacto
		queryString.append("select ");
		queryString.append("0 det_id,p.par_id, "); 
		queryString.append("inf.can_id, ");
		queryString.append("0 adm_id,	0 det_rut,	null det_rut_dv,	0 tpo_moneda_id, "); 
		queryString.append("0 total_monto, null det_transaction_id,	cast(0 as boolean) det_resultado_tgr, "); 
		queryString.append("null det_fecha_pago,	null det_estado,	0 det_rendicion_id, ");
		queryString.append("( ");
		queryString.append("select count(distinct p2.par_id) "); 
		queryString.append("from ");
		queryString.append("par_partido p2, "); 
		queryString.append("rlc_partido_nivel rlc2, "); 
		queryString.append("par_partido_maestro pm2 ");
		queryString.append("where ");
		queryString.append("rlc2.id_par_partido_maestro = pm2.id ");  
		queryString.append("and p2.par_codigo_origen = pm2.codigo_origen "); 
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and ele_id in  :idEleccion ");
		}
		queryString.append("and pac_id = p.pac_id  ");
		queryString.append("and niv_id = rl.niv_id_padre "); 
		queryString.append("group by  ");
		queryString.append("p2.pac_id,  ");
		queryString.append("rlc2.niv_id) cuentaPartidosVigentes "); 
		queryString.append("from ");
		queryString.append("sel_sub_eleccion s, "); 
		queryString.append("rlc_rel_nivel rl,  ");
		queryString.append("par_partido p , ");
		queryString.append("can_candidato c, ");
		queryString.append("inf_instancia_flujo inf, int_instancia_tarea it ");
		queryString.append("where ");
		queryString.append("inf.inf_id=it.inf_id ");
		queryString.append("and it.dta_id= :dtaId ");
		queryString.append("and inf.eve_id = :eventoId ");
		queryString.append("and inf.can_id is not null ");
		queryString.append("and inf.can_id=c.can_id ");
		queryString.append("and c.tpo_can_id = :tipoCandidato ");
		//queryString.append("and inf.can_id not in (select can_id from det_detalle_nomina where can_id is not null) ");
		queryString.append("and c.sel_id = s.sel_id  ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and s.ele_id in  :idEleccion ");
		}
		if(partidoCodigoOrigen!=null) {
			queryString.append("		and p.par_codigo_origen = :partidoCodigoOrigen ");
		}
		queryString.append("and c.sup_id is null ");
		queryString.append("and c.pac_id is not null ");
		queryString.append("and p.pac_id = c.pac_id ");
		queryString.append("and inf.can_id not in ( ");
		queryString.append("select	can_id from ");
		queryString.append("( ");
		queryString.append("select r.can_id, ");
		queryString.append("jsonb (DR.DET_VALOR :::: json) :::: json as DETALLE ");
		queryString.append("from ");
		queryString.append("ren_rendicion r ");
		queryString.append("join det_detalle_rendicion dr on ");
		queryString.append("r.ren_id = dr.ren_id, ");
		queryString.append("tpo_seccion_rendicion ts, ");
		queryString.append("tpo_rendicion tr ");
		queryString.append("where ");
		queryString.append("ts.tpo_seccion_rendicion_id = dr.id_tipo_seccion ");
		queryString.append("and ts.tpo_nombre_seccion = :seccionesPago ");
		queryString.append("and r.tpo_rendicion_id = tr.tpo_rendicion_id ");
		queryString.append("and tr.tpo_nombre = :tipoRendicionReembolso ");
		queryString.append("and r.can_id is not null ");
		queryString.append("and r.eve_id = :eventoId ");
		queryString.append(") t1 ");
		queryString.append("where ");
		queryString.append("(coalesce(cast(DETALLE -> 'reCreditoMandato' ->> 'totalMontoAutorizado' as integer), 0) >0 ");
		queryString.append("or coalesce(cast(DETALLE -> 'rePartido' ->> 'totalMontoAutorizado' as integer), 0) >0 ");
		queryString.append("or coalesce(cast(DETALLE -> 'reCandidato' ->> 'totalMontoAutorizado' as integer), 0) >0) ");
		queryString.append(") ");
		queryString.append("and rl.niv_id_hijo = s.niv_id ");
		queryString.append("and rl.tpo_niv_codigo_padre = 'MREG' "); 
		queryString.append("and (p.par_id, ");
		queryString.append("rl.niv_id_padre) in ( "); 
		queryString.append("select ");
		queryString.append("p.par_id, ");
		queryString.append("r.niv_id ");
		queryString.append("from ");
		queryString.append("par_partido_maestro pm, "); 
		queryString.append("par_partido p, ");
		queryString.append("ele_eleccion e,  ");
		queryString.append("rlc_partido_nivel r "); 
		queryString.append("where ");
		queryString.append("pm.codigo_origen = p.par_codigo_origen "); 
		queryString.append("and p.ele_id = e.ele_id  ");
		queryString.append("and e.eve_id_eve = :eventoId ");
		queryString.append("and r.id_par_partido_maestro = pm.id "); 
		queryString.append("and r.eve_id = :eventoId ) ");
		queryString.append("group by ");
		queryString.append("det_id, p.par_id, inf.can_id, ");
		queryString.append("det_estado,det_rendicion_id,p.pac_id,	rl.niv_id_padre "); 
		queryString.append("union ");
		//subpacto
		queryString.append("select ");
		queryString.append("0 det_id, t1.par_id, "); 
		queryString.append("inf.can_id, ");
		queryString.append("0 adm_id,	0 det_rut,	null det_rut_dv,	0 tpo_moneda_id, "); 
		queryString.append("0 total_monto, null det_transaction_id,	null det_resultado_tgr, ");
		queryString.append("null det_fecha_pago,	null det_estado,	0 det_rendicion_id, ");
		queryString.append("(  ");
		queryString.append("select count(distinct p2.par_id) "); 
		queryString.append("from ");
		queryString.append("rlc_subpacto_partido rs2, ");
		queryString.append("rlc_partido_nivel rlc2, ");
		queryString.append("par_partido_maestro pm2, "); 
		queryString.append("par_partido p2 "); 
		queryString.append("where ");
		queryString.append("rlc2.id_par_partido_maestro = pm2.id ");
		queryString.append("and p2.par_id = rs2.par_id ");
		queryString.append("and p2.par_codigo_origen = pm2.codigo_origen "); 
		queryString.append("and rs2.spa_id = t1.spa_id ");
		queryString.append("and rlc2.niv_id = rl.niv_id_padre ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and p2.ele_id in  :idEleccion ");
		}
		queryString.append("group by ");
		queryString.append("rs2.spa_id, ");
		queryString.append("rlc2.niv_id) cuentaPartidosVigentes "); 
		queryString.append("from ");
		queryString.append("can_candidato c, ");
		queryString.append("sel_sub_eleccion s , ");
		queryString.append("rlc_rel_nivel rl, "); 
		queryString.append("( ");
		queryString.append("select rs.par_id, ");
		queryString.append("p.par_codigo_origen, "); 
		queryString.append("rs.spa_id ");
		queryString.append("from ");
		queryString.append("rlc_subpacto_partido rs, "); 
		queryString.append("par_partido p ");
		queryString.append("where ");
		queryString.append("p.par_id = rs.par_id "); 
		queryString.append(") t1 , ");
		queryString.append("inf_instancia_flujo inf, int_instancia_tarea it ");
		queryString.append("where ");
		queryString.append("inf.inf_id=it.inf_id ");
		queryString.append("and it.dta_id = :dtaId ");
		queryString.append("and inf.eve_id = :eventoId ");
		queryString.append("and inf.can_id is not null ");
		queryString.append("and inf.can_id=c.can_id ");
		queryString.append("and c.tpo_can_id = :tipoCandidato ");
		//queryString.append("and inf.can_id not in (select can_id from det_detalle_nomina where can_id is not null) ");
		queryString.append("and c.sel_id = s.sel_id ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("		and s.ele_id in  :idEleccion ");
		}
		if(partidoCodigoOrigen!=null) {
			queryString.append("		and t1.par_codigo_origen = :partidoCodigoOrigen ");
		}
		queryString.append("and c.sup_id is not null ");
		queryString.append("and t1.spa_id = c.sup_id ");
		queryString.append("and inf.can_id not in ( ");
		
		queryString.append("select	can_id from ");
		queryString.append("( ");
		queryString.append("select r.can_id, ");
		queryString.append("jsonb (DR.DET_VALOR :::: json) :::: json as DETALLE ");
		queryString.append("from ");
		queryString.append("ren_rendicion r ");
		queryString.append("join det_detalle_rendicion dr on ");
		queryString.append("r.ren_id = dr.ren_id, ");
		queryString.append("tpo_seccion_rendicion ts, ");
		queryString.append("tpo_rendicion tr ");
		queryString.append("where ");
		queryString.append("ts.tpo_seccion_rendicion_id = dr.id_tipo_seccion ");
		queryString.append("and ts.tpo_nombre_seccion = :seccionesPago ");
		queryString.append("and r.tpo_rendicion_id = tr.tpo_rendicion_id ");
		queryString.append("and tr.tpo_nombre = :tipoRendicionReembolso ");
		queryString.append("and r.can_id is not null ");
		queryString.append("and r.eve_id = :eventoId ");
		queryString.append(") t1 ");
		queryString.append("where ");
		queryString.append("(coalesce(cast(DETALLE -> 'reCreditoMandato' ->> 'totalMontoAutorizado' as integer), 0) >0 ");
		queryString.append("or coalesce(cast(DETALLE -> 'rePartido' ->> 'totalMontoAutorizado' as integer), 0) >0 ");
		queryString.append("or coalesce(cast(DETALLE -> 'reCandidato' ->> 'totalMontoAutorizado' as integer), 0) >0) ");
		
		queryString.append(") ");
		queryString.append("and rl.niv_id_hijo = s.niv_id "); 
		queryString.append("and rl.tpo_niv_codigo_padre = 'MREG' "); 
		queryString.append("and (t1.par_id, ");
		queryString.append("rl.niv_id_padre) in ( "); 
		queryString.append("select ");
		queryString.append("p.par_id, ");
		queryString.append("r.niv_id ");
		queryString.append("from ");
		queryString.append("par_partido_maestro pm, "); 
		queryString.append("par_partido p, ");
		queryString.append("ele_eleccion e, ");
		queryString.append("rlc_partido_nivel r "); 
		queryString.append("where ");
		queryString.append("pm.codigo_origen = p.par_codigo_origen ");
		queryString.append("and p.ele_id = e.ele_id ");
		queryString.append("and e.eve_id_eve = :eventoId ");
		queryString.append("and r.id_par_partido_maestro = pm.id "); 
		queryString.append("and r.eve_id = :eventoId ) "); 
		queryString.append("group by ");
		queryString.append("det_id, t1.par_id, inf.can_id, ");
		queryString.append("t1.spa_id,rl.niv_id_padre,rl.niv_id_hijo,c.can_id) ");
		queryString.append("	   t3 ");
		queryString.append("group by ");
		queryString.append("	t3.det_id, t3.par_id,t3.can_id,t3.adm_id,t3.det_rut,t3.det_rut_dv,t3.tpo_moneda_id,t3.det_transaction_id,t3.det_resultado_tgr, ");
		queryString.append("		t3.det_fecha_pago,t3.det_estado,t3.det_rendicion_id,t3.cuentaPartidosVigentes ");




		Query query = entityManager.createNativeQuery(queryString.toString());
		if (nomId!=null) {
			query.setParameter("nomId", nomId);
		}
		if (partidoCodigoOrigen!=null) {
			query.setParameter("partidoCodigoOrigen", partidoCodigoOrigen);
		}

		if (idEleccion!=null && idEleccion.size()>0) {
			query.setParameter("idEleccion", idEleccion);
		}
		query.setParameter("eventoId", eventoId);
		query.setParameter("tipoCandidato", tipoCandidato);
		query.setParameter("tipoPago", tipoPago);
		query.setParameter("dtaId", dtaId);
		query.setParameter("seccionesPago", seccionesPago);
		query.setParameter("tipoRendicionReembolso", tipoRendicionReembolso);

		List<DetDetalleNomina> detalleNominaList=(List<DetDetalleNomina>)query.getResultList();
		List<DetDetalleNomina> detalleNominaListResult= new ArrayList<>();
		DetDetalleNomina detalleNomina;
		ParPartido partido=null;
		CanCandidato candidato=null;
		BigDecimal monto=null;
		BigInteger partidosVigentes=null;
		NomNomina nomina=null;
		DetDetalleRendicion detDetalleRendicion=null;
		for (Object record : detalleNominaList) {
			Object[] fields = (Object[]) record;
			detalleNomina= new DetDetalleNomina();
			detalleNomina.setDetId((Integer)fields[0]);
			//			    if (fields[1]!=null) {
			//			    	nomina= new NomNomina();
			//			    	nomina.setNomId((Integer)fields[1]);
			//			    	detalleNomina.setNomNomina(nomina);
			//			    }
			if (fields[1] !=null) {
				partido= new ParPartido();
				partido.setParId((Integer) fields[1]);
				detalleNomina.setParPartido(partido);
			}
			if (fields[2] !=null) {
				candidato= new CanCandidato();
				candidato.setCanId((Integer) fields[2]);
				detalleNomina.setCanCandidato(candidato);
			}
			monto=(BigDecimal)fields[7];
			detalleNomina.setDetMonto(monto.doubleValue());
			if (fields[12]!=null) {
				detDetalleRendicion= new DetDetalleRendicion();
				detDetalleRendicion.setDetId((Integer)fields[12]);
				detalleNomina.setDetDetalleRendicion(detDetalleRendicion);
			}

			partidosVigentes=(BigInteger)fields[13];
			detalleNomina.setDetRut(partidosVigentes.intValue());

			detalleNominaListResult.add(detalleNomina);
		}

		return Optional.of(detalleNominaListResult);
	}


	@SuppressWarnings("unchecked")
	public Optional<List<DetDetalleNomina>> findDetDetalleNominaByNominaIdTipo(Integer nomId,String partidoCodigoOrigen,Integer eventoId,Integer tipoCandidato1, Integer tipoCandidato2,List<String> tipoPago,List<Integer> idEleccion, Integer dtaId,List<String> seccionesPago, String tipoRendicionReembolso)
	{

		StringBuilder queryString = new StringBuilder();
		queryString.append("select dn.det_id,dn.nom_id,dn.par_id,case when dn.can_id is not null then dn.can_id else dn.can_id_ref end as can_id,dn.adm_id,dn.det_rut,dn.det_rut_dv,dn.tpo_moneda_id, ");
		queryString.append("dn.det_monto,dn.det_transaction_id,dn.det_resultado_tgr,dn.det_fecha_pago,dn.det_estado,dn.det_rendicion_id, ");
		queryString.append("dn.eba_id,dn.can_id_ref,dn.par_id_ref,dn.man_id_ref ");
		queryString.append("from det_detalle_nomina dn, nom_nomina n, par_partido p,can_candidato c,tpo_tipos t ");
		queryString.append("where dn.nom_id=n.nom_id ");
		queryString.append("and (dn.can_id= c.can_id	or dn.can_id_ref=c.can_id) ");
		queryString.append("and p.par_id=c.par_id ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("			and p.ele_id in :idEleccion "); 
		}
		if (partidoCodigoOrigen!=null) {
			queryString.append("and p.par_codigo_origen = :partidoCodigoOrigen ");
		}
		if (nomId!=null) {
			queryString.append("and dn.nom_id = :nomId ");
		}

		queryString.append("and n.eve_id = :eventoId ");
		queryString.append("and (c.tpo_can_id = :tipoCandidato1 or c.tpo_can_id = :tipoCandidato2) ");
		queryString.append("and t.tpo_id=n.tpo_nomina_id ");
		queryString.append("and t.tpo_codigo in :tipoPago ");
		queryString.append("union ");
		queryString.append("select 0 det_id, 0 nom_id,c.par_id,c.can_id,0 adm_id,1 det_rut, null det_rut_dv, 0 tpo_moneda_id, 0 det_monto, null det_transaction_id, "); 
		queryString.append("null det_resultado_tgr, null det_fecha_pago, ");
		queryString.append("null det_estado, null det_rendicion_id, ");
		queryString.append("0 eba_id,0 can_id_ref,0 par_id_ref,0 man_id_ref ");
		queryString.append("from inf_instancia_flujo inf, int_instancia_tarea it, can_candidato c,par_partido p ");
		queryString.append("where inf.inf_id=it.inf_id ");
		queryString.append("and it.dta_id = :dtaId ");
		queryString.append("and inf.eve_id = :eventoId ");
		if (partidoCodigoOrigen!=null) {
			queryString.append("and p.par_codigo_origen = :partidoCodigoOrigen ");
		}
		queryString.append("and inf.can_id is not null ");
		queryString.append("and inf.can_id=c.can_id ");
		queryString.append("and (c.tpo_can_id = :tipoCandidato1 or c.tpo_can_id = :tipoCandidato2) ");
		//queryString.append("and inf.can_id not in (select can_id from det_detalle_nomina where can_id is not null) ");
		queryString.append("and p.par_id=c.par_id ");
		if (idEleccion!=null && idEleccion.size()>0) {
			queryString.append("			and p.ele_id in :idEleccion "); 
		}
		queryString.append("and inf.can_id not in ( ");
		
		queryString.append("select	can_id from ");
		queryString.append("( ");
		queryString.append("select r.can_id, ");
		queryString.append("jsonb (DR.DET_VALOR :::: json) :::: json as DETALLE ");
		queryString.append("from ");
		queryString.append("ren_rendicion r ");
		queryString.append("join det_detalle_rendicion dr on ");
		queryString.append("r.ren_id = dr.ren_id, ");
		queryString.append("tpo_seccion_rendicion ts, ");
		queryString.append("tpo_rendicion tr ");
		queryString.append("where ");
		queryString.append("ts.tpo_seccion_rendicion_id = dr.id_tipo_seccion ");
		queryString.append("and ts.tpo_nombre_seccion = :seccionesPago ");
		queryString.append("and r.tpo_rendicion_id = tr.tpo_rendicion_id ");
		queryString.append("and tr.tpo_nombre = :tipoRendicionReembolso ");
		queryString.append("and r.can_id is not null ");
		queryString.append("and r.eve_id = :eventoId ");
		queryString.append(") t1 ");
		queryString.append("where ");
		queryString.append("(coalesce(cast(DETALLE -> 'reCreditoMandato' ->> 'totalMontoAutorizado' as integer), 0) >0 ");
		queryString.append("or coalesce(cast(DETALLE -> 'rePartido' ->> 'totalMontoAutorizado' as integer), 0) >0 ");
		queryString.append("or coalesce(cast(DETALLE -> 'reCandidato' ->> 'totalMontoAutorizado' as integer), 0) >0) ");
		
		queryString.append(") ");
		queryString.append("group by c.can_id ");
		
		

	

		Query query = entityManager.createNativeQuery(queryString.toString());
		if (nomId!=null) {
			query.setParameter("nomId", nomId);
		}
		if (partidoCodigoOrigen!=null) {
			query.setParameter("partidoCodigoOrigen", partidoCodigoOrigen);
		}

		if (idEleccion!=null && idEleccion.size()>0) {
			query.setParameter("idEleccion", idEleccion);
		}
		query.setParameter("eventoId", eventoId);
		query.setParameter("tipoCandidato1", tipoCandidato1);
		query.setParameter("tipoCandidato2", tipoCandidato2);
		query.setParameter("tipoPago", tipoPago);
		query.setParameter("dtaId", dtaId);
		query.setParameter("seccionesPago", seccionesPago);
		query.setParameter("tipoRendicionReembolso", tipoRendicionReembolso);

		List<DetDetalleNomina> detalleNominaList=(List<DetDetalleNomina>)query.getResultList();
		List<DetDetalleNomina> detalleNominaListResult= new ArrayList<>();
//		Optional<DetDetalleNomina> detalleNomina;
		ParPartido partido=null;
		CanCandidato candidato=null;
		AdmAdministradorElectoral administrador=null;
		TpoTipos tipos=null;
		BigDecimal monto=null;
		BigInteger partidosVigentes=null;
		NomNomina nomina=null;
		DetDetalleRendicion detDetalleRendicion=null;
		EbaEntidadBancaria entidadBancaria=null;
		DetDetalleNomina detalleNomina=null;
		BigInteger bi=null;
		for (Object record : detalleNominaList) {
			Object[] fields = (Object[]) record;
			detalleNomina= new DetDetalleNomina();
			detalleNomina.setDetId((Integer)fields[0]);
			if (fields[1]!=null) {
				nomina= new NomNomina();
				nomina.setNomId((Integer)fields[1]);
				detalleNomina.setNomNomina(nomina);	
			}
			
			if (fields[2]!=null) {
				partido= new ParPartido();
				partido.setParId((Integer)fields[2]);
				detalleNomina.setParPartido(partido);	
			}
			
			if (fields[3]!=null) {
				bi= (BigInteger) fields[3];
				candidato= new CanCandidato();
				candidato.setCanId(bi.intValue());
				detalleNomina.setCanCandidato(candidato);		
				
			}
			
			if (fields[4]!=null) {
				administrador= new AdmAdministradorElectoral();
				administrador.setAdmId((Integer)fields[4]);
				detalleNomina.setAdmAdministradorElectoral(administrador);		
				
			}
			
			detalleNomina.setDetRut((Integer)fields[5]);
			detalleNomina.setDetRutDv((String)fields[6]);
			
			if (fields[7]!=null) {
//				tipos=tiposRepository.findById((Integer)fields[7]);
				tipos = new TpoTipos();
				tipos.setTpoId((Integer)fields[7]);
				detalleNomina.setTpoTipos(tipos);		
				
			}
			
			detalleNomina.setDetMonto((Double)fields[8]);
			detalleNomina.setDetTransactionId((String)fields[9]);
			detalleNomina.setDetResultadoTgr((Boolean)fields[10]);
			detalleNomina.setDetFechaPago((Date)fields[11]);
			detalleNomina.setDetEstado((String)fields[12]);
			
			
			if (fields[13]!=null) {
				detDetalleRendicion= new DetDetalleRendicion();
				detDetalleRendicion.setDetId((Integer)fields[13]);
				detalleNomina.setDetDetalleRendicion(detDetalleRendicion);		
				
			}
			
			if (fields[14]!=null) {
				entidadBancaria= new EbaEntidadBancaria();
				entidadBancaria.setEbaId((Integer)fields[14]);
				detalleNomina.setEbaEntidadBancaria(entidadBancaria);
			}
			bi=null;
			if (fields[15]!=null) {
				bi= (BigInteger)fields[15];
				detalleNomina.setCanIdRef(bi.longValue());
			}
			bi=null;
			if (fields[16]!=null) {
				bi= (BigInteger)fields[16];
				detalleNomina.setParIdRef(bi.longValue());
			}
			bi=null;
			if (fields[17]!=null) {
				bi= (BigInteger)fields[17];
				detalleNomina.setManIdRef(bi.longValue());
			}

			detalleNominaListResult.add(detalleNomina);
		}
		
		return Optional.of(detalleNominaListResult);
	}

	public LinkedList<PagoMultaDTO> getPagosMultas(int eveId, int tipoEvento, int tipoEleccion, long desde, long hasta) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null; 
		LinkedList<PagoMultaDTO> result = null;
		
		java.sql.Date fechaInicial = new java.sql.Date(desde);
		java.sql.Date fechaFinal = new java.sql.Date(hasta);
		
		try {
			con = ds.getConnection();
			preparedStatement = con.prepareStatement(getQueryPagosMultas());
			
			preparedStatement.setInt(1, eveId);
			preparedStatement.setInt(2, tipoEleccion);
			preparedStatement.setInt(3, tipoEvento);
			preparedStatement.setDate(4, fechaInicial);
			preparedStatement.setDate(5, fechaFinal);
			
			preparedStatement.setInt(6, eveId);
			preparedStatement.setInt(7, tipoEleccion);
			preparedStatement.setInt(8, tipoEvento);
			preparedStatement.setDate(9, fechaInicial);
			preparedStatement.setDate(10, fechaFinal);
			
			preparedStatement.setInt(11, tipoEleccion);
			preparedStatement.setInt(12, tipoEvento);
			preparedStatement.setInt(13, eveId);
			preparedStatement.setDate(14, fechaInicial);
			preparedStatement.setDate(15, fechaFinal);
			
			preparedStatement.setInt(16, tipoEleccion);
			preparedStatement.setInt(17, tipoEvento);
			preparedStatement.setInt(18, eveId);
			preparedStatement.setDate(19, fechaInicial);
			preparedStatement.setDate(20, fechaFinal);
			
			preparedStatement.setInt(21, tipoEleccion);
			preparedStatement.setInt(22, tipoEvento);
			preparedStatement.setInt(23, eveId);
			preparedStatement.setDate(24, fechaInicial);
			preparedStatement.setDate(25, fechaFinal);
			
			rs = preparedStatement.executeQuery();
			
			result = new LinkedList<PagoMultaDTO>();
			PagoMultaDTO detalle = null;
			
			while (rs.next()) {
				detalle = new PagoMultaDTO();
				
				detalle.setCategoria(rs.getString("CATEGORIA"));
				detalle.setTipoEleccion(rs.getString("TIPO_ELECCION"));
				detalle.setEleccion(rs.getString("ELECCION"));
				detalle.setRut(rs.getInt("RUT"));
				detalle.setDv(rs.getString("DV"));
				detalle.setNombres(rs.getString("NOMBRES"));
				detalle.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
				detalle.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
				detalle.setTipo(rs.getString("TIPO"));
				detalle.setPartido(rs.getString("PARTIDO"));
				detalle.setFolio(rs.getLong("FOLIO"));
				detalle.setTransaccion(rs.getString("TRANSACCION"));
				detalle.setCuenta(rs.getString("CUENTA"));
				detalle.setTipoCuenta(rs.getString("TIPO_CUENTA"));
				detalle.setBanco(rs.getString("BANCO"));
				detalle.setFecha(rs.getDate("FECHA"));
				detalle.setEstadoEnvio(rs.getString("ESTADO_ENVIO"));
				detalle.setTipoMoneda(rs.getString("TIPO_MONEDA"));
				detalle.setMonto(rs.getDouble("MONTO"));
				
				result.add(detalle);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private String getQueryPagosMultas() {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT T.TPO_DESCRIPCION AS CATEGORIA, ");
		query.append("	     TEV.TPO_EVENTO_NOMBRE AS TIPO_ELECCION, ");
		query.append("	     TE.TPO_ELE_NOMBRE AS ELECCION, ");
		query.append("	     C.CAN_RUT AS RUT, ");
		query.append("	     C.CAN_RUT_DV AS DV, ");
		query.append("	     C.CAN_NOMBRES AS NOMBRES, ");
		query.append("	     C.CAN_APP_PATERNO AS APELLIDO_PATERNO, ");
		query.append("	     C.CAN_APP_MATERNO AS APELLIDO_MATERNO, ");
		query.append("	     CAST (C.TPO_CAN_ID AS TEXT) AS TIPO, ");
		query.append("	     P.PAR_NOMBRE AS PARTIDO, ");
		query.append("	     N.NOM_FOLIO AS FOLIO, ");
		query.append("	     DN.DET_TRANSACTION_ID AS TRANSACCION, ");
		query.append("	     CC.CCT_NUMERO_CUENTA AS CUENTA, ");
		query.append("	     TCC.TPO_NOMBRE_CUENTA AS TIPO_CUENTA, ");
		query.append("	     EBA.EBA_NOMBRE_BANCO AS BANCO, ");
		query.append("	     DN.DET_FECHA_PAGO AS FECHA, ");
		query.append("	     DN.DET_ESTADO AS ESTADO_ENVIO, ");
		query.append("	     'CLP' AS TIPO_MONEDA, ");
		query.append("	     DN.DET_MONTO AS MONTO	    ");
		query.append("  FROM DET_DETALLE_NOMINA DN ");
		query.append("  JOIN NOM_NOMINA N ");
		query.append("    ON DN.NOM_ID = N.NOM_ID ");
		query.append("  JOIN TPO_TIPOS T ");
		query.append("    ON T.TPO_ID = N.TPO_NOMINA_ID ");
		query.append("  JOIN CAN_CANDIDATO C ");
		query.append("    ON C.CAN_ID = DN.CAN_ID ");
		query.append("  JOIN SEL_SUB_ELECCION SE ");
		query.append("    ON SE.SEL_ID = C.SEL_ID ");
		query.append("  JOIN ELE_ELECCION E ");
		query.append("    ON E.ELE_ID = SE.ELE_ID ");
		query.append("  JOIN TPO_ELECCION TE ");
		query.append("    ON TE.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("  JOIN TPO_EVENTO TEV ");
		query.append("    ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("  LEFT JOIN PAR_PARTIDO P ");
		query.append("    ON P.PAR_ID = C.PAR_ID ");
		query.append("  LEFT JOIN CCT_CUENTA_CONTABLE CC ");
		query.append("    ON CC.CAN_ID = C.CAN_ID ");
		query.append("  LEFT JOIN TPO_CTA_CONTABLE TCC ");
		query.append("    ON TCC.TPO_CTA_CONTABLE_ID = CC.TPO_CTA_CONTABLE_ID ");
		query.append("  LEFT JOIN EBA_ENTIDAD_BANCARIA EBA ");
		query.append("    ON EBA.EBA_ID = CC.EBA_ID ");
		query.append(" WHERE N.EVE_ID = ?  ");
		query.append("   AND E.TPO_ELECCION_ID = ? ");
		query.append("   AND E.TPO_EVENTO_ID = ? ");
		query.append("   AND N.NOM_FECHA_NOMINA BETWEEN ? AND ? ");
		query.append(" UNION ");
		query.append("SELECT T.TPO_DESCRIPCION AS CATEGORIA, ");
		query.append(" 	     TEV.TPO_EVENTO_NOMBRE AS TIPO_ELECCION, ");
		query.append("	     TE.TPO_ELE_NOMBRE AS ELECCION, ");
		query.append("	     CAST ((SELECT split_part(P.PAR_RUT, '-', 1)) AS INTEGER) AS RUT, ");
		query.append("	     (SELECT split_part(P.PAR_RUT, '-', 2)) AS DV, ");
		query.append("	     'N/A' AS NOMBRES, ");
		query.append("	     P.PAR_NOMBRE AS APELLIDO_PATERNO, ");
		query.append("	     'N/A' AS APELLIDO_MATERNO, ");
		query.append("	     'N/A' AS TIPO, ");
		query.append("	     'N/A' AS PARTIDO, ");
		query.append("	     N.NOM_FOLIO AS FOLIO, ");
		query.append("	     DN.DET_TRANSACTION_ID AS TRANSACCION, ");
		query.append("	     CC.CCT_NUMERO_CUENTA AS CUENTA, ");
		query.append("	     TCC.TPO_NOMBRE_CUENTA AS TIPO_CUENTA, ");
		query.append("	     EBA.EBA_NOMBRE_BANCO AS BANCO, ");
		query.append("	     DN.DET_FECHA_PAGO AS FECHA, ");
		query.append("	     DN.DET_ESTADO AS ESTADO_ENVIO, ");
		query.append("	     'CLP' AS TIPO_MONEDA, ");
		query.append("	     DN.DET_MONTO AS MONTO	    ");
		query.append("  FROM DET_DETALLE_NOMINA DN ");
		query.append("  JOIN NOM_NOMINA N ");
		query.append("    ON DN.NOM_ID = N.NOM_ID ");
		query.append("  JOIN TPO_TIPOS T ");
		query.append("    ON T.TPO_ID = N.TPO_NOMINA_ID ");
		query.append("  JOIN PAR_PARTIDO P ");
		query.append("    ON P.PAR_ID = DN.PAR_ID ");
		query.append("  JOIN ELE_ELECCION E  ");
		query.append("    ON E.ELE_ID = P.ELE_ID ");
		query.append("  JOIN TPO_ELECCION TE ");
		query.append("    ON TE.TPO_ELECCION_ID = E.TPO_ELECCION_ID ");
		query.append("  JOIN TPO_EVENTO TEV ");
		query.append("    ON TEV.TPO_EVENTO_ID = E.TPO_EVENTO_ID ");
		query.append("  LEFT JOIN CCT_CUENTA_CONTABLE CC ");
		query.append("    ON CC.PAR_ID = P.PAR_ID ");
		query.append("  LEFT JOIN TPO_CTA_CONTABLE TCC ");
		query.append("    ON TCC.TPO_CTA_CONTABLE_ID = CC.TPO_CTA_CONTABLE_ID ");
		query.append("  LEFT JOIN EBA_ENTIDAD_BANCARIA EBA ");
		query.append("    ON EBA.EBA_ID = CC.EBA_ID ");
		query.append(" WHERE N.EVE_ID = ?  ");
		query.append("   AND E.TPO_ELECCION_ID = ? ");
		query.append("   AND E.TPO_EVENTO_ID = ? ");
		query.append("   AND N.NOM_FECHA_NOMINA BETWEEN ? AND ? ");
		query.append(" UNION ");
		query.append("SELECT DETALLE.CATEGORIA,  ");
		query.append("       DETALLE.TIPO_ELECCION,  ");
		query.append("       DETALLE.ELECCION,  ");
		query.append("       CAST (DETALLE.RUT AS INTEGER) AS RUT,  ");
		query.append("       DETALLE.DV,  ");
		query.append("       DETALLE.NOMBRES,  ");
		query.append("       DETALLE.APELLIDO_PATERNO,  ");
		query.append("       DETALLE.APELLIDO_MATERNO,  ");
		query.append("       CAST (DETALLE.TIPO AS TEXT) AS TIPO,  ");
		query.append("       DETALLE.PARTIDO,  ");
		query.append("       DETALLE.FOLIO,  ");
		query.append("       DETALLE.TRANSACCION,  ");
		query.append("       DETALLE.CUENTA,  ");
		query.append("       DETALLE.TIPO_CUENTA,  ");
		query.append("       DETALLE.BANCO,  ");
		query.append("       DETALLE.FECHA,  ");
		query.append("       DETALLE.ESTADO_ENVIO,  ");
		query.append("       DETALLE.TIPO_MONEDA, ");
		query.append("       DETALLE.MONTO AS MONTO  ");
		query.append("  FROM (  ");
		query.append("        SELECT TPO.TPO_DESCRIPCION AS CATEGORIA,  ");
		query.append("               (SELECT TE.TPO_ELE_NOMBRE  ");
		query.append("                  FROM TPO_ELECCION TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("                                        JOIN TPO_TIPOS TPO  ");
		query.append("  										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')  ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS TIPO_ELECCION,  ");
		query.append("               (SELECT TE.TPO_ELECCION_ID  ");
		query.append("                  FROM TPO_ELECCION TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')   ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS TIPO_ELECCION_ID,  ");
		query.append("               (SELECT TE.TPO_EVENTO_NOMBRE  ");
		query.append("                  FROM TPO_EVENTO TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')    ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS ELECCION,  ");
		query.append("               (SELECT TE.TPO_EVENTO_ID  ");
		query.append("                  FROM TPO_EVENTO TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append(" 										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion') AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS ELECCION_ID,  ");
		query.append("               ADM.ADM_RUT AS RUT,  ");
		query.append("               ADM.ADM_RUT_DV AS DV,  ");
		query.append("               ADM.ADM_NOMBRE AS NOMBRES,  ");
		query.append("               ADM.ADM_APP_PATERNO AS APELLIDO_PATERNO,  ");
		query.append("               ADM.ADM_APP_MATERNO AS APELLIDO_MATERNO,  ");
		query.append("               'N/A' AS TIPO,  ");
		query.append("               'N/A' AS PARTIDO,  ");
		query.append("               NOM.NOM_FOLIO AS FOLIO,  ");
		query.append("               DN.DET_TRANSACTION_ID AS TRANSACCION,  ");
		query.append("               'N/A' AS CUENTA,  ");
		query.append("               'N/A' AS TIPO_CUENTA,  ");
		query.append("               'N/A' AS BANCO,  ");
		query.append("               ING.ING_CREATED AS FECHA,  ");
		query.append("               DN.DET_ESTADO AS ESTADO_ENVIO,  ");
		query.append("               CAST ((SELECT DIN2.DIN_VALOR_ATRIBUTO  ");
		query.append("                  FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                  JOIN ING_INGRESOS ING2  ");
		query.append("                    ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("				  JOIN TPO_TIPOS TPO  ");
		query.append(" 					ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                 WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                   AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('MONTO')  ");
		query.append("                   AND TPO.TPO_CODIGO ='INGRESO_MULTA' ");
		query.append("               ) AS FLOAT) AS MONTO,  ");
		query.append("               (SELECT DIN2.DIN_VALOR_ATRIBUTO  ");
		query.append("                  FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                  JOIN ING_INGRESOS ING2  ");
		query.append("                    ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("				  JOIN TPO_TIPOS TPO  ");
		query.append(" 					ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                 WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                   AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('tipoMoneda')  ");
		query.append("                   AND TPO.TPO_CODIGO ='INGRESO_MULTA' ");
		query.append("               ) AS TIPO_MONEDA, ");
		query.append("               NOM.NOM_ID,  ");
		query.append("               DN.DET_ID,   ");
		query.append("               ING.EVE_ID AS EVENTO  ");
		query.append("          FROM ING_INGRESOS ING  ");
		query.append("          LEFT JOIN DET_DETALLE_NOMINA DN ");
		query.append("            ON DN.DET_ID = ING.DET_ID  ");
		query.append("          JOIN TPO_TIPOS TPO  ");
		query.append("            ON TPO.TPO_ID = ING.TPO_INGRESO_ID  ");
		query.append("          JOIN ADM_ADMINISTRADOR_ELECTORAL ADM  ");
		query.append("            ON ADM.ADM_ID = ING.ADM_ID  ");
		query.append("          LEFT JOIN NOM_NOMINA NOM  ");
		query.append("            ON NOM.NOM_ID = DN.NOM_ID  ");
		query.append("         WHERE TPO.TPO_CODIGO = 'INGRESO_MULTA'  ");
		query.append("    ) AS DETALLE  ");
		query.append(" WHERE DETALLE.TIPO_ELECCION_ID = ?  ");
		query.append("   AND DETALLE.ELECCION_ID = ?  ");
		query.append("   AND DETALLE.EVENTO = ?  ");
		query.append("   AND DETALLE.FECHA BETWEEN ? and ? ");
		query.append(" UNION ");
		query.append("SELECT DETALLE.CATEGORIA,  ");
		query.append("       DETALLE.TIPO_ELECCION,  ");
		query.append("       DETALLE.ELECCION,  ");
		query.append("       CAST (DETALLE.RUT AS INTEGER) AS RUT,  ");
		query.append("       DETALLE.DV,  ");
		query.append("       DETALLE.NOMBRES,  ");
		query.append("       DETALLE.APELLIDO_PATERNO,  ");
		query.append("       DETALLE.APELLIDO_MATERNO,  ");
		query.append("       CAST (DETALLE.TIPO AS TEXT) AS TIPO,  ");
		query.append("       DETALLE.PARTIDO,  ");
		query.append("       DETALLE.FOLIO,  ");
		query.append("       DETALLE.TRANSACCION,  ");
		query.append("       DETALLE.CUENTA,  ");
		query.append("       DETALLE.TIPO_CUENTA,  ");
		query.append("       DETALLE.BANCO,  ");
		query.append("       DETALLE.FECHA,  ");
		query.append("       DETALLE.ESTADO_ENVIO,  ");
		query.append("       DETALLE.TIPO_MONEDA, ");
		query.append("       DETALLE.MONTO AS MONTO  ");
		query.append("  FROM (  ");
		query.append("        SELECT TPO.TPO_DESCRIPCION AS CATEGORIA,  ");
		query.append("               (SELECT TE.TPO_ELE_NOMBRE  ");
		query.append("                  FROM TPO_ELECCION TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("                                        JOIN TPO_TIPOS TPO  ");
		query.append("    									  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')  ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS TIPO_ELECCION,  ");
		query.append("               (SELECT TE.TPO_ELECCION_ID  ");
		query.append("                  FROM TPO_ELECCION TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')   ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS TIPO_ELECCION_ID,  ");
		query.append("               (SELECT TE.TPO_EVENTO_NOMBRE  ");
		query.append("                  FROM TPO_EVENTO TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')    ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS ELECCION,  ");
		query.append("               (SELECT TE.TPO_EVENTO_ID  ");
		query.append("                  FROM TPO_EVENTO TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion') AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS ELECCION_ID,  ");
		query.append("               CAN.CAN_RUT AS RUT,  ");
		query.append("               CAN.CAN_RUT_DV AS DV,  ");
		query.append("               CAN.CAN_NOMBRES AS NOMBRES,  ");
		query.append("               CAN.CAN_APP_PATERNO AS APELLIDO_PATERNO,  ");
		query.append("               CAN.CAN_APP_MATERNO AS APELLIDO_MATERNO,  ");
		query.append("               'N/A' AS TIPO,  ");
		query.append("               'N/A' AS PARTIDO,  ");
		query.append("               NOM.NOM_FOLIO AS FOLIO,  ");
		query.append("               DN.DET_TRANSACTION_ID AS TRANSACCION,  ");
		query.append("               'N/A' AS CUENTA,  ");
		query.append("               'N/A' AS TIPO_CUENTA,  ");
		query.append("               'N/A' AS BANCO,  ");
		query.append("               ING.ING_CREATED AS FECHA,  ");
		query.append("               DN.DET_ESTADO AS ESTADO_ENVIO,  ");
		query.append("               CAST ((SELECT DIN2.DIN_VALOR_ATRIBUTO  ");
		query.append("                  FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                  JOIN ING_INGRESOS ING2  ");
		query.append("                    ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("				  JOIN TPO_TIPOS TPO  ");
		query.append("				 	ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                 WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                   AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('MONTO')  ");
		query.append("                   AND TPO.TPO_CODIGO ='INGRESO_MULTA' ");
		query.append("               ) AS FLOAT) AS MONTO,  ");
		query.append("               (SELECT DIN2.DIN_VALOR_ATRIBUTO  ");
		query.append("                  FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                  JOIN ING_INGRESOS ING2  ");
		query.append("                    ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("				  JOIN TPO_TIPOS TPO  ");
		query.append("				 	ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                 WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                   AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('tipoMoneda')  ");
		query.append("                   AND TPO.TPO_CODIGO ='INGRESO_MULTA' ");
		query.append("               ) AS TIPO_MONEDA, ");
		query.append("               NOM.NOM_ID,  ");
		query.append("               DN.DET_ID,   ");
		query.append("               ING.EVE_ID AS EVENTO  ");
		query.append("          FROM ING_INGRESOS ING  ");
		query.append("          LEFT JOIN DET_DETALLE_NOMINA DN ");
		query.append("            ON DN.DET_ID = ING.DET_ID  ");
		query.append("          JOIN TPO_TIPOS TPO  ");
		query.append("            ON TPO.TPO_ID = ING.TPO_INGRESO_ID  ");
		query.append("          JOIN CAN_CANDIDATO CAN  ");
		query.append("            ON CAN.CAN_ID = ING.CAN_ID  ");
		query.append("          LEFT JOIN NOM_NOMINA NOM  ");
		query.append("            ON NOM.NOM_ID = DN.NOM_ID  ");
		query.append("         WHERE TPO.TPO_CODIGO = 'INGRESO_MULTA' ");
		query.append("           AND ING.DET_ID IS NULL  ");
		query.append("    ) AS DETALLE ");
		query.append("    WHERE DETALLE.TIPO_ELECCION_ID = ?  ");
		query.append("   AND DETALLE.ELECCION_ID = ?  ");
		query.append("   AND DETALLE.EVENTO = ?  ");
		query.append("   AND DETALLE.FECHA BETWEEN ? and ? ");
		query.append(" UNION ");
		query.append("SELECT DETALLE.CATEGORIA,  ");
		query.append("       DETALLE.TIPO_ELECCION,  ");
		query.append("       DETALLE.ELECCION,  ");
		query.append("       CAST (DETALLE.RUT AS INTEGER) AS RUT,  ");
		query.append("       DETALLE.DV,  ");
		query.append("       DETALLE.NOMBRES,  ");
		query.append("       DETALLE.APELLIDO_PATERNO,  ");
		query.append("       DETALLE.APELLIDO_MATERNO,  ");
		query.append("       CAST (DETALLE.TIPO AS TEXT) AS TIPO,  ");
		query.append("       DETALLE.PARTIDO,  ");
		query.append("       DETALLE.FOLIO,  ");
		query.append("       DETALLE.TRANSACCION,  ");
		query.append("       DETALLE.CUENTA,  ");
		query.append("       DETALLE.TIPO_CUENTA,  ");
		query.append("       DETALLE.BANCO,  ");
		query.append("       DETALLE.FECHA,  ");
		query.append("       DETALLE.ESTADO_ENVIO,  ");
		query.append("       DETALLE.TIPO_MONEDA, ");
		query.append("       DETALLE.MONTO AS MONTO  ");
		query.append("  FROM (  ");
		query.append("        SELECT TPO.TPO_DESCRIPCION AS CATEGORIA,  ");
		query.append("               (SELECT TE.TPO_ELE_NOMBRE  ");
		query.append("                  FROM TPO_ELECCION TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("                                        JOIN TPO_TIPOS TPO  ");
		query.append("    									  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')  ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS TIPO_ELECCION,  ");
		query.append("               (SELECT TE.TPO_ELECCION_ID  ");
		query.append("                  FROM TPO_ELECCION TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')   ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS TIPO_ELECCION_ID,  ");
		query.append("               (SELECT TE.TPO_EVENTO_NOMBRE  ");
		query.append("                  FROM TPO_EVENTO TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion')    ");
		query.append("                                         AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS ELECCION,  ");
		query.append("               (SELECT TE.TPO_EVENTO_ID  ");
		query.append("                  FROM TPO_EVENTO TE  ");
		query.append("                  JOIN ELE_ELECCION ELE  ");
		query.append("                    ON TE.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID  ");
		query.append("                 WHERE ELE.ELE_ID = ( SELECT CAST(DIN2.DIN_VALOR_ATRIBUTO AS INTEGER)  ");
		query.append("                                        FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                                        JOIN ING_INGRESOS ING2  ");
		query.append("                                          ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("										JOIN TPO_TIPOS TPO  ");
		query.append("										  ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                                       WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                                         AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('eleccion') AND TPO.TPO_CODIGO ='INGRESO_MULTA'  ");
		query.append("                                     )  ");
		query.append("               ) AS ELECCION_ID,  ");
		query.append("               CAST ((SELECT split_part(PAR.PAR_RUT, '-', 1)) AS INTEGER) AS RUT, ");
		query.append("	    	   (SELECT split_part(PAR.PAR_RUT, '-', 2)) AS DV,  ");
		query.append("               'N/A' AS NOMBRES,  ");
		query.append("               PAR.PAR_NOMBRE AS APELLIDO_PATERNO,  ");
		query.append("               'N/A' AS APELLIDO_MATERNO,  ");
		query.append("               'N/A' AS TIPO,  ");
		query.append("               'N/A' AS PARTIDO,  ");
		query.append("               NOM.NOM_FOLIO AS FOLIO,  ");
		query.append("               DN.DET_TRANSACTION_ID AS TRANSACCION,  ");
		query.append("               'N/A' AS CUENTA,  ");
		query.append("               'N/A' AS TIPO_CUENTA,  ");
		query.append("               'N/A' AS BANCO,  ");
		query.append("               ING.ING_CREATED AS FECHA,  ");
		query.append("               DN.DET_ESTADO AS ESTADO_ENVIO,  ");
		query.append("               CAST ((SELECT DIN2.DIN_VALOR_ATRIBUTO  ");
		query.append("                  FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                  JOIN ING_INGRESOS ING2  ");
		query.append("                    ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("				  JOIN TPO_TIPOS TPO  ");
		query.append("				 	ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                 WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                   AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('MONTO')  ");
		query.append("                   AND TPO.TPO_CODIGO ='INGRESO_MULTA' ");
		query.append("               ) AS FLOAT) AS MONTO,  ");
		query.append("               (SELECT DIN2.DIN_VALOR_ATRIBUTO  ");
		query.append("                  FROM DIN_DETALLE_INGRESOS DIN2  ");
		query.append("                  JOIN ING_INGRESOS ING2  ");
		query.append("                    ON ING2.ING_ID = DIN2.ING_ID  ");
		query.append("				  JOIN TPO_TIPOS TPO  ");
		query.append("				 	ON TPO.TPO_ID = ING2.TPO_INGRESO_ID  ");
		query.append("                 WHERE ING2.ING_ID = ING.ING_ID  ");
		query.append("                   AND LOWER(DIN2.DIN_NOMBRE_ATRIBUTO) = LOWER('tipoMoneda')  ");
		query.append("                   AND TPO.TPO_CODIGO ='INGRESO_MULTA' ");
		query.append("               ) AS TIPO_MONEDA, ");
		query.append("               NOM.NOM_ID,  ");
		query.append("               DN.DET_ID,   ");
		query.append("               ING.EVE_ID AS EVENTO  ");
		query.append("          FROM ING_INGRESOS ING  ");
		query.append("          LEFT JOIN DET_DETALLE_NOMINA DN ");
		query.append("            ON DN.DET_ID = ING.DET_ID  ");
		query.append("          JOIN TPO_TIPOS TPO  ");
		query.append("            ON TPO.TPO_ID = ING.TPO_INGRESO_ID  ");
		query.append("          JOIN PAR_PARTIDO PAR  ");
		query.append("            ON PAR.PAR_ID = ING.PAR_ID  ");
		query.append("          LEFT JOIN NOM_NOMINA NOM  ");
		query.append("            ON NOM.NOM_ID = DN.NOM_ID  ");
		query.append("         WHERE TPO.TPO_CODIGO = 'INGRESO_MULTA' ");
		query.append("           AND ING.DET_ID IS NULL  ");
		query.append("    ) AS DETALLE     ");
		query.append("    WHERE DETALLE.TIPO_ELECCION_ID = ?  ");
		query.append("   AND DETALLE.ELECCION_ID = ?  ");
		query.append("   AND DETALLE.EVENTO = ?  ");
		query.append("   AND DETALLE.FECHA BETWEEN ? and ? ");
		
		return query.toString();
	}
}
