package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.NivNivel;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.ResResumenCalculos;
import cl.servel.gasto.entity.TpoEleccion;
import cl.servel.gasto.entity.TpoNivel;

public class ResResumenCalculosRepositoryImpl implements ResResumenCalculosRepositoryCustom {

	private static final Logger LOG = LoggerFactory.getLogger(ResResumenCalculosRepositoryCustom.class);
	
	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<ResResumenCalculos> leerCandidatosIndendientesPdf(Integer idEvento,Integer idEleccion,Integer tipoCandidato) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select c.can_id,  ");
		queryString.append("c.par_id,  ");
		queryString.append("s.niv_id,  ");
		queryString.append("s.tpo_niv_codigo,  ");
		queryString.append("s.ele_id,  ");
		queryString.append("CAST(r.res_monto_financiamiento as DOUBLE PRECISION)  ");
		queryString.append("from  ");
		queryString.append("can_candidato c,  ");
		queryString.append("sel_sub_eleccion s,  ");
		queryString.append("res_resumen_calculos r,  ");
		queryString.append("rlc_rel_nivel re,  ");
		queryString.append("niv_nivel n  ");
		queryString.append("where  ");
		queryString.append("c.tpo_can_id = ?  ");
		queryString.append("and c.eve_id = ?  ");
		queryString.append("and c.sel_id = s.sel_id  ");
		queryString.append("and s.ele_id = ?  ");
		queryString.append("and r.can_indep_id = c.can_id  ");
		queryString.append("and r.ele_id = s.ele_id  ");
		queryString.append("and re.niv_id_hijo = s.niv_id  ");
		queryString.append("and n.niv_id = re.niv_id_padre  ");
		queryString.append("order by  ");
		queryString.append("1 asc ");
		
		

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, tipoCandidato);
		query.setParameter(2, idEvento);
		query.setParameter(3, idEleccion);

		
		List<ResResumenCalculos> resumenList = (List<ResResumenCalculos>)query.getResultList();
		List<ResResumenCalculos> resumenListResult = new ArrayList<>();
		ResResumenCalculos resumen;


		for (Object record : resumenList) {
			Object[] fields = (Object[]) record;
			resumen = new ResResumenCalculos();
			
			
			resumen.setResId(0);
			resumen.setCanIndepId((Integer) fields[0]);
			
			NivNivel nivel = new NivNivel();
			nivel.setNivId((Integer) fields[2]);
			resumen.setNivNivel(nivel);


			TpoNivel tipoNivel = new TpoNivel();
			tipoNivel.setTpoNivCodigo((String) fields[3]);
			resumen.setTpoNivel(tipoNivel);
			
			EleEleccion ele = new EleEleccion();
			ele.setEleId((Integer) fields[4]);
			resumen.setEleEleccion(ele);
			
			resumen.setResMontoFinanciamiento((Double) fields[5]);


			resumenListResult.add(resumen);
		}


		return resumenListResult;
	}

	@SuppressWarnings("unchecked")
	public List<ResResumenCalculos> getResumenCalculosIniciales(Integer idEventoAnterior,Integer idEleccionAnterior,
			Integer idEleccion,Integer tipoCandidato1, Integer tipoCandidato2,Integer partidoId)
	{

		StringBuilder queryString = new StringBuilder();

		queryString.append("select  ");
		queryString.append("	se.niv_id niv_id_anterior, ");
		queryString.append(" partidoActual.par_id par_id_actual, ");
		queryString.append("  nivelActual.niv_id niv_id_actual,");
		queryString.append("  se.sel_codigo_origen,");
		queryString.append(" se.tpo_niv_codigo,");
		queryString.append("	c.par_id par_id_anterior, ");
		queryString.append("	se.ele_id ele_id_anterior, ");
		queryString.append("	cast(sum(c.can_cantidad_votos) as double precision) total_votos ");
		queryString.append("from can_candidato c  ");
		queryString.append(" left join sel_sub_eleccion se on c.sel_id = se.sel_id ");
		queryString.append(" join rlc_rel_nivel rn on se.niv_id = rn.niv_id_hijo ");
		queryString.append(" join par_partido p on p.par_id = c.par_id ");
		queryString.append(" left join (  ");
		queryString.append("	        select SEE.niv_id,SEE.sel_codigo_origen ");
		queryString.append("	      from sel_sub_eleccion SEE "); 
		queryString.append("	       where SEE.ele_id= ? ");
		queryString.append("	  ) nivelActual ");
		queryString.append("			on nivelActual.sel_codigo_origen= se.sel_codigo_origen ");
		queryString.append(" left join ( ");
		queryString.append("	   select pp.par_id,pp.par_codigo_origen ");
		queryString.append("	      from par_partido pp ");
		queryString.append("	      where pp.ele_id= ? ");
		queryString.append("	      group by  pp.par_id,pp.par_codigo_origen ");
		queryString.append("	    ) partidoActual ");
		queryString.append("	on partidoActual.par_codigo_origen=p.par_codigo_origen ");
		queryString.append("where c.eve_id=? ");
		queryString.append("and se.ele_id = ? ");
		queryString.append("and (c.tpo_can_id=? or c.tpo_can_id=?)  ");
		queryString.append("and c.sel_id = se.sel_id ");
		queryString.append("and c.can_habilitado is true ");
		if (partidoId!=null) {
			queryString.append("and c.par_id= :partidoId ");
		}
		queryString.append("group by  ");
		queryString.append("  se.niv_id, ");
		queryString.append("	se.sel_codigo_origen, ");
		queryString.append("	p.par_codigo_origen, ");
		queryString.append("	se.tpo_niv_codigo, ");
		queryString.append("	c.par_id, ");
		queryString.append("	partidoActual.par_id, ");
		queryString.append("	nivelActual.niv_id, ");
		queryString.append("	se.ele_id ");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);
		query.setParameter(2, idEleccion);
		query.setParameter(3, idEventoAnterior);
		query.setParameter(4, idEleccionAnterior);
		query.setParameter(5, tipoCandidato1);
		query.setParameter(6, tipoCandidato2);
		if (partidoId!=null) {
			query.setParameter("partidoId", partidoId);
		}

	
		List<ResResumenCalculos> resumenList = (List<ResResumenCalculos>)query.getResultList();
		List<ResResumenCalculos> resumenListResult = new ArrayList<>();
		ResResumenCalculos resumen;
		ParPartido partido=null;
		NivNivel nivel =null;
		TpoNivel tipoNivel=null;

		for (Object record : resumenList) {
			Object[] fields = (Object[]) record;
			resumen = new ResResumenCalculos();
			EleEleccion ele = new EleEleccion();
			ele.setEleId(idEleccion);
			resumen.setEleEleccion(ele);
			
			resumen.setResId(0);

			resumen.setNivIdAnt((Integer) fields[0]);
			
			if (fields[1] !=null) {
			partido = new ParPartido();
			partido.setParId((Integer) fields[1]);
			resumen.setParPartido(partido);
			}
			
			
			if (fields[2] !=null) {
			nivel = new NivNivel();
			nivel.setNivId((Integer) fields[2]);
			resumen.setNivNivel(nivel);
			nivel.setCodigoOrigen((String) fields[3]);
			tipoNivel = new TpoNivel();
			tipoNivel.setTpoNivCodigo((String) fields[4]);
			resumen.setTpoNivel(tipoNivel);
			}

			

		

			resumen.setParIdAnt((Integer) fields[5]);

			resumen.setEleIdAnterior((Integer) fields[6]);

			resumen.setResTotalVotos((Double) fields[7]);

			resumenListResult.add(resumen);
		}

		return resumenListResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResResumenCalculos> getResumenCalculosCandidatosIndependientes(Integer idEleccionAnterior,
			Integer idEleccion,Integer tipoCandidato, Integer partidoId)
	{

		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select s.niv_id niv_id_anterior, partidoActual.par_id par_id_actual,nivelActual.niv_id niv_id_actual,s.sel_codigo_origen,s.tpo_niv_codigo, ");   
		queryString.append("p.par_id par_id_anterior, s.ele_id ele_id_anterior,sum(cast(c.can_cantidad_votos as double precision)) total_votos, ");
		queryString.append("(select count(distinct p2.par_id)  from par_partido p2, rlc_partido_nivel rlc2, par_partido_maestro pm2 ");
		queryString.append("where rlc2.id_par_partido_maestro= pm2.id  and p2.par_codigo_origen=pm2.codigo_origen   ");
		queryString.append("and ele_id= :idEleccionAnterior ");
		queryString.append("and pac_id=p.pac_id and niv_id=rl.niv_id_padre ");  
		queryString.append("group by p2.pac_id,rlc2.niv_id) cuentaPartidosVigentes ");
		queryString.append("from can_candidato c left join sel_sub_eleccion s ");
		queryString.append("on  c.sel_id=s.sel_id ");    
		queryString.append("left join (select niv_id,sel_codigo_origen from sel_sub_eleccion where ele_id= :idEleccion) nivelActual ");  
		queryString.append("on nivelActual.sel_codigo_origen= s.sel_codigo_origen ");
		queryString.append("left join par_partido p ");
		queryString.append("on  p.pac_id=c.pac_id  ");
		queryString.append("left join (select par_id,par_codigo_origen from par_partido where ele_id= :idEleccion group by par_id,par_codigo_origen) partidoActual "); 
		queryString.append("on partidoActual.par_codigo_origen=p.par_codigo_origen ");
		queryString.append(", rlc_rel_nivel rl ");
		queryString.append("where c.tpo_can_id= :tipoCandidato ");
		queryString.append("and s.ele_id= :idEleccionAnterior "); 
		queryString.append("and c.sup_id is null and c.pac_id is not null ");   
		queryString.append("and rl.niv_id_hijo=s.niv_id ");
		queryString.append("and  rl.tpo_niv_codigo_padre='MREG' ");  
		queryString.append("and (p.par_id,rl.niv_id_padre) in ( ");
		queryString.append("select p.par_id,r.niv_id from par_partido_maestro pm, par_partido p, ele_eleccion e,rlc_partido_nivel r "); 
		queryString.append("where pm.codigo_origen=p.par_codigo_origen "); 
		queryString.append("and p.ele_id=e.ele_id "); 
		queryString.append("and e.ele_id= :idEleccionAnterior ");
		queryString.append("and r.id_par_partido_maestro=pm.id ");
		queryString.append("and r.eve_id=e.eve_id_eve "); 
		queryString.append(") ");
		queryString.append("and c.can_habilitado  is true ");
		if (partidoId!=null) {
			queryString.append("and partidoActual.par_id= :partidoId ");
		}
		queryString.append("group by s.niv_id, ");   
		queryString.append("s.sel_codigo_origen,  "); 
		queryString.append("s.tpo_niv_codigo, ");
		queryString.append("p.par_id, ");   
		queryString.append("partidoActual.par_id, ");   
		queryString.append("nivelActual.niv_id, ");
		queryString.append("s.ele_id,rl.niv_id_padre ");
		queryString.append("union ");    
		queryString.append("select s.niv_id niv_id_anterior, partidoActual.par_id par_id_actual,nivelActual.niv_id niv_id_actual,s.sel_codigo_origen,s.tpo_niv_codigo, ");      
		queryString.append("t1.par_id par_id_anterior, s.ele_id ele_id_anterior,sum(cast(c.can_cantidad_votos as double precision)) total_votos, ");     
		queryString.append("(select count(distinct p2.par_id)  from rlc_subpacto_partido rs2, rlc_partido_nivel rlc2, par_partido_maestro pm2, par_partido p2 ");     
		queryString.append("where rlc2.id_par_partido_maestro= pm2.id  and p2.par_id= rs2.par_id ");      
		queryString.append("and p2.par_codigo_origen=pm2.codigo_origen ");      
		queryString.append("and rs2.spa_id=t1.spa_id and rlc2.niv_id=rl.niv_id_padre ");
		queryString.append("and p2.ele_id= :idEleccionAnterior ");    
		queryString.append("group by rs2.spa_id,rlc2.niv_id) cuentaPartidosVigentes ");  
		queryString.append("from can_candidato c left join sel_sub_eleccion s ");    
		queryString.append("on  c.sel_id=s.sel_id ");     
		queryString.append("left join (select rs.par_id,p.par_codigo_origen,rs.spa_id from rlc_subpacto_partido rs, par_partido p where p.par_id=rs.par_id and p.ele_id= :idEleccionAnterior) t1 ");  
		queryString.append("on t1.spa_id=c.sup_id ");  
		queryString.append("left join (select niv_id,sel_codigo_origen from sel_sub_eleccion where ele_id= :idEleccion) nivelActual ");  
		queryString.append("on nivelActual.sel_codigo_origen= s.sel_codigo_origen ");    
		queryString.append("left join (select par_id,par_codigo_origen from par_partido where ele_id= :idEleccion group by par_id,par_codigo_origen) partidoActual ");  
		queryString.append("on partidoActual.par_codigo_origen=t1.par_codigo_origen ");   
		queryString.append(", rlc_rel_nivel rl ");   
		queryString.append("where c.tpo_can_id= :tipoCandidato ");      
		queryString.append("and s.ele_id= :idEleccionAnterior "); 
		queryString.append("and c.sup_id is not null ");      
		queryString.append("and rl.niv_id_hijo=s.niv_id ");      
		queryString.append(" and rl.tpo_niv_codigo_padre='MREG' ");
		queryString.append("and (t1.par_id,rl.niv_id_padre) in ( ");
		queryString.append("select p.par_id,r.niv_id from par_partido_maestro pm, par_partido p, ele_eleccion e,rlc_partido_nivel r "); 
		queryString.append("where pm.codigo_origen=p.par_codigo_origen "); 
		queryString.append("and p.ele_id=e.ele_id "); 
		queryString.append("and e.ele_id= :idEleccionAnterior ");
		queryString.append("and r.id_par_partido_maestro=pm.id ");
		queryString.append("and r.eve_id=e.eve_id_eve ");
		queryString.append(") ");
		queryString.append("and c.can_habilitado is true ");
		if (partidoId!=null) {
			queryString.append("and partidoActual.par_id= :partidoId ");
		}
		queryString.append("group by s.niv_id, ");      
		queryString.append("s.sel_codigo_origen, ");     
		queryString.append("s.tpo_niv_codigo, ");      
		queryString.append("t1.par_id, ");      
		queryString.append("partidoActual.par_id, ");      
		queryString.append("nivelActual.niv_id, ");
		queryString.append("s.ele_id,t1.spa_id,rl.niv_id_padre ");
		
		
		


		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("idEleccionAnterior", idEleccionAnterior);
		query.setParameter("idEleccion", idEleccion);
		query.setParameter("tipoCandidato", tipoCandidato);
		if (partidoId!=null) {
			query.setParameter("partidoId", partidoId);
		}
		
	
		List<ResResumenCalculos> resumenList = (List<ResResumenCalculos>)query.getResultList();
		List<ResResumenCalculos> resumenListResult = new ArrayList<>();
		ResResumenCalculos resumen;
		ParPartido partido=null;
		TpoNivel tipoNivel=null;
		NivNivel nivel= null;
		for (Object record : resumenList) {
			Object[] fields = (Object[]) record;
			resumen = new ResResumenCalculos();
			EleEleccion ele = new EleEleccion();
			ele.setEleId(idEleccion);
			resumen.setEleEleccion(ele);
			
			resumen.setResId(0);

			resumen.setNivIdAnt((Integer) fields[0]);
			
			
			
			if (fields[1] !=null) {
				partido = new ParPartido();
				partido.setParId((Integer) fields[1]);
				resumen.setParPartido(partido);
			}
			
			
			if (fields[2] !=null) {
				nivel = new NivNivel();
				nivel.setNivId((Integer) fields[2]);
				resumen.setNivNivel(nivel);

				nivel.setCodigoOrigen((String) fields[3]);

				tipoNivel = new TpoNivel();
				tipoNivel.setTpoNivCodigo((String) fields[4]);
				resumen.setTpoNivel(tipoNivel);
			}
			

			resumen.setParIdAnt((Integer) fields[5]);

			resumen.setEleIdAnterior((Integer) fields[6]);
			resumen.setResTotalVotos(Double.valueOf(fields[7].toString())/Double.valueOf(fields[8].toString()));

			resumenListResult.add(resumen);
		}

		return resumenListResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResResumenCalculos> getResumenCalculosFase2(Integer idEleccion,Integer tipoCandidato)
	{

		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select	p.par_id,s.niv_id,s.tpo_niv_codigo,s.ele_id,0 res_total_votos,0 res_monto_financiamiento,0 ele_id_anterior ,false par_nuevo,null can_indep_id,0 par_id_ant,	0 niv_id_ant ");
		queryString.append("from ");
		queryString.append("par_partido p, ");
		queryString.append("sel_sub_eleccion s, ");
		queryString.append("can_candidato c ");
		queryString.append("where ");
		queryString.append("s.ele_id=? ");
		queryString.append("and p.ele_id = s.ele_id ");
		queryString.append("and c.par_id = p.par_id ");
		queryString.append("and s.sel_id = c.sel_id ");
		queryString.append("and c.can_habilitado is true ");
		queryString.append("group by p.par_id, ");
		queryString.append("s.niv_id, ");
		queryString.append("s.tpo_niv_codigo, ");
		queryString.append("s.ele_id ");
		queryString.append("union ");
		queryString.append("select	p.par_id,s.niv_id,s.tpo_niv_codigo,s.ele_id,0 res_total_votos,0 res_monto_financiamiento,0 ele_id_anterior ,false par_nuevo,null can_indep_id,0 par_id_ant,	0 niv_id_ant ");
		queryString.append("from ");
		queryString.append("par_partido p, ");
		queryString.append("sel_sub_eleccion s ");
		queryString.append("where ");
		queryString.append("p.ele_id = s.ele_id ");
		queryString.append("and (p.pac_id, ");
		queryString.append("s.niv_id) in ( ");
		queryString.append("select ");
		queryString.append("p.pac_id, ");
		queryString.append("s.niv_id ");
		queryString.append("from ");
		queryString.append("can_candidato c, ");
		queryString.append("sel_sub_eleccion s, ");
		queryString.append("par_partido p ");
		queryString.append("where ");
		queryString.append("c.sel_id = s.sel_id ");
		queryString.append("and s.ele_id=? ");
		queryString.append("and c.pac_id = p.pac_id ");
		queryString.append("and c.sup_id is null ");
		queryString.append("and c.tpo_can_id = ? ");
		queryString.append("and c.can_habilitado is true ");
		queryString.append("group by ");
		queryString.append("p.pac_id, ");
		queryString.append("s.niv_id ) ");
		queryString.append("group by ");
		queryString.append("p.par_id, "); 
		queryString.append("s.niv_id, ");
		queryString.append("s.tpo_niv_codigo, ");
		queryString.append("s.ele_id ");
		queryString.append("union ");
		queryString.append("select	p.par_id,s.niv_id,s.tpo_niv_codigo,s.ele_id,0 res_total_votos,0 res_monto_financiamiento,0 ele_id_anterior ,false par_nuevo,null can_indep_id,0 par_id_ant,	0 niv_id_ant ");
		queryString.append("from ");
		queryString.append("par_partido p, ");
		queryString.append("sel_sub_eleccion s, ");
		queryString.append("rlc_subpacto_partido rs ");
		queryString.append("where ");
		queryString.append("p.ele_id = s.ele_id ");
		queryString.append("and p.par_id = rs.par_id ");
		queryString.append("and (rs.spa_id, ");
		queryString.append("s.niv_id) in ( ");
		queryString.append("select ");
		queryString.append("rss.spa_id, ");
		queryString.append("s.niv_id ");
		queryString.append("from ");
		queryString.append("can_candidato c, ");
		queryString.append("sel_sub_eleccion s, ");
	//	queryString.append("par_partido p, ");
		queryString.append("rlc_subpacto_partido rss ");
		queryString.append("where ");
		queryString.append("c.sel_id = s.sel_id ");
		queryString.append("and s.ele_id=? ");
		queryString.append("and c.sup_id = rss.spa_id ");
		queryString.append("and c.sup_id is not null ");
		queryString.append("and c.tpo_can_id=? ");
		//queryString.append("and p.par_id = rss.par_id ");
		queryString.append("and c.can_habilitado is true ");
		queryString.append("group by ");
		queryString.append("rss.spa_id, ");
		queryString.append("s.niv_id ) ");
		queryString.append("group by ");
		queryString.append("p.par_id, ");
		queryString.append("s.niv_id, ");
		queryString.append("s.tpo_niv_codigo, ");
		queryString.append("s.ele_id ");



		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);
		query.setParameter(2, idEleccion);
		query.setParameter(3, tipoCandidato);
		query.setParameter(4, idEleccion);
		query.setParameter(5, tipoCandidato);
				

	
		List<ResResumenCalculos> resumenList = (List<ResResumenCalculos>)query.getResultList();
		List<ResResumenCalculos> resumenListResult = new ArrayList<>();
		ResResumenCalculos resumen;
		ParPartido partido=null;
		NivNivel nivel =null;
		TpoNivel tipoNivel=null;

		for (Object record : resumenList) {
			Object[] fields = (Object[]) record;
			resumen = new ResResumenCalculos();
			EleEleccion ele = new EleEleccion();
			ele.setEleId(idEleccion);
			resumen.setEleEleccion(ele);
			
			resumen.setResId(0);

			
			if (fields[0] !=null) {
			partido = new ParPartido();
			partido.setParId((Integer) fields[0]);
			resumen.setParPartido(partido);
			}
			
			
			if (fields[1] !=null) {
			nivel = new NivNivel();
			nivel.setNivId((Integer) fields[1]);
			resumen.setNivNivel(nivel);
			tipoNivel = new TpoNivel();
			tipoNivel.setTpoNivCodigo((String) fields[2]);
			resumen.setTpoNivel(tipoNivel);
			}

			

		

			resumen.setParIdAnt((Integer) fields[9]);

			resumen.setEleIdAnterior((Integer) fields[6]);

			resumen.setResTotalVotos((Double) 0.0);

			resumenListResult.add(resumen);
		}

		return resumenListResult;
	}
	
	/**
	 * Metodo que llena obtiene el monto del financiamiento Inicial acumulado para los distintos partidos
	 * @param idEleccion Id de la eleccion
	 * @return Devuelve Data para PDF de reporte inicial para partidos.  
	 */
	public List<ResResumenCalculos> leerFinanciamientoInicialPartidosPdf(Integer idEleccion)
	{
		StringBuilder queryString = new StringBuilder();

		queryString.append("select p.par_id,  ");
		queryString.append("to_char(sum(r.res_monto_financiamiento), '9999999999999900.99') monto, ");
		queryString.append("to_char(sum(r.res_total_votos), '9999999999999900.99') voto ");
		queryString.append("from  ");
		queryString.append("res_resumen_calculos r  ");
		queryString.append("inner join  par_partido p ");		
		queryString.append("ON r.par_id=p.par_id ");
		queryString.append("where  ");
		queryString.append("r.ele_id= ?  ");
		queryString.append("group by p.par_id ");
		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);
		
		List<ResResumenCalculos> resumenList = (List<ResResumenCalculos>)query.getResultList();
		List<ResResumenCalculos> resumenListResult = new ArrayList<>();
		ResResumenCalculos resumen;
		Object[] fields;


		for (Object record : resumenList) 
		{
			fields = (Object[]) record;
			resumen = new ResResumenCalculos();		
			resumen.setResId(0);
			
			ParPartido parPartido = new ParPartido();
			parPartido.setParId((Integer)fields[0]);
			resumen.setParPartido(parPartido);
			
			
			double doble = Double.parseDouble((String) fields[1]);
			resumen.setResMontoFinanciamiento(doble);


			double doble1 = Double.parseDouble((String) fields[2]);
			resumen.setResTotalVotos(doble1);
			resumenListResult.add(resumen);
		}


		return resumenListResult;
	}
	
	public Integer getCalculoEleccionAnterior(@Param("eleccionId") Integer eleccionId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select (case when (select count(*) from res_resumen_calculos where ele_id=?) >0 then ");  
		queryString.append(" (select distinct ele_id_anterior from res_resumen_calculos where ele_id=?) else ");
		queryString.append(" (select count(*) from res_resumen_calculos where ele_id=?)  end) ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);
		query.setParameter(2, eleccionId);
		query.setParameter(3, eleccionId);
		
		Integer result = Integer.parseInt(query.getSingleResult().toString());
		
		
		return result;
	}
	
	@Transactional
	@Modifying
	public void eliminarFinanciamientoInicialPorEleccion(Integer idEleccion) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("DELETE FROM res_resumen_calculos WHERE ele_id = ?");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);

		query.executeUpdate();

	}	
}
