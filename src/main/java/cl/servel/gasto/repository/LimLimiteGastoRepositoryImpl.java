package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.LimLimiteGasto;
import cl.servel.gasto.entity.NivNivel;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.TpoEleccion;

public class LimLimiteGastoRepositoryImpl implements LimLimiteGastoRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<LimLimiteGasto> leeLimitesCandidato(Integer idEleccion) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select 0 lim_id,  ");
		queryString.append("s.ele_id,  ");
		queryString.append("s.eve_id,  ");
		queryString.append("e.tpo_eleccion_id,  ");
		queryString.append("0 par_id,  ");
		queryString.append("s.niv_id niv_hijo_id ,  ");	
		queryString.append("rn.niv_id_padre niv_padre_id,  ");
		queryString.append("s.sel_cantidad_electores, ");
		queryString.append("false lim_es_limite_gasto_partido,  ");
		queryString.append("cast(0.0 AS DOUBLE PRECISION) lim_total_maximo_aporte,  ");
		queryString.append("CAST(0.0 AS DOUBLE PRECISION) lim_total_limite  ");
		queryString.append("from  ");
		queryString.append("sel_sub_eleccion s,  ");
		queryString.append("rlc_rel_nivel rn,  ");
		queryString.append("ele_eleccion e  ");
		queryString.append("where  ");
		queryString.append("s.ele_id = ?  ");
		queryString.append("and s.niv_id = rn.niv_id_hijo  ");
		queryString.append("and e.ele_id = s.ele_id  ");
		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		 query.setParameter(1, idEleccion);
		 
		 List<LimLimiteGasto> limiteList=(List<LimLimiteGasto>)query.getResultList();
		 List<LimLimiteGasto> limiteListResult= new ArrayList<>();
		 LimLimiteGasto limite;
		 
		 for (Object record : limiteList) {
			    Object[] fields = (Object[]) record;
			    limite= new LimLimiteGasto();

				   limite.setLimId((Integer) fields[0]);
				   
				   EleEleccion ele= new EleEleccion();
				   ele.setEleId((Integer) fields[1]);
				   limite.setEleEleccion(ele);
				   
				   EveEventoEleccionario eve = new EveEventoEleccionario();
				   eve.setEveId((Integer) fields[2]);
				   limite.setEveEventoEleccionario(eve);
				   
				   TpoEleccion tipoE= new TpoEleccion();
				   tipoE.setTpoEleccionId((Integer) fields[3]);
				   limite.setTpoEleccion(tipoE);
				   
				   ParPartido partido= new ParPartido();
				   partido.setParId((Integer) fields[4]);
				   limite.setParPartido(partido);
				   
				   NivNivel nivel= new NivNivel();
				   nivel.setNivId((Integer) fields[5]);
				   limite.setNivNivelByNivHijoId(nivel);
				   
  			       nivel= new NivNivel();
				   nivel.setNivId((Integer) fields[6]);
				   limite.setNivNivelByNivPadreId(nivel);
				   
				   limite.setNumeroElectores((Integer) fields[7]);
				   
				   limite.setLimEsLimiteGastoPartido((Boolean) fields[8]);
				   limite.setLimMaxPersona((Double) fields[9]);
				   limite.setLimTotalLimite((Double) fields[10]);
			    
			    
			    limiteListResult.add(limite);
			}
		 
		 return limiteListResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LimLimiteGasto> leeLimitesPartido(Integer idEleccion,Integer tipoCandidato1, Integer tipoCandidato2 )
	{

		StringBuilder queryString = new StringBuilder();
		
		
		queryString.append("select eve_id,tpo_eleccion_id, sum(lim_total_limite*cuenta) total_candidatos,par_id from (  ");
		queryString.append("select s.eve_id,l.tpo_eleccion_id,s.niv_id,c.par_id,count(c.can_id) cuenta, l.lim_total_limite  ");
		queryString.append("from can_candidato c, sel_sub_eleccion s, lim_limite_gasto l  ");
		queryString.append("where c.sel_id=s.sel_id  ");
		queryString.append("and s.ele_id= ?  ");
		queryString.append("and (c.tpo_can_id= ? or c.tpo_can_id=?)  ");
		queryString.append("and l.niv_hijo_id=s.niv_id  ");
		queryString.append("and c.can_habilitado is true ");
		queryString.append("group by s.eve_id,l.tpo_eleccion_id,s.niv_id,c.par_id, l.lim_total_limite  ");
		queryString.append("order by 1 asc  ");
		queryString.append(") t  ");
		queryString.append("group by eve_id,tpo_eleccion_id,par_id  ");


		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);
		query.setParameter(2, tipoCandidato1);
		query.setParameter(3, tipoCandidato2);

	
		 List<LimLimiteGasto> limiteList=(List<LimLimiteGasto>)query.getResultList();
		 List<LimLimiteGasto> limiteListResult= new ArrayList<>();
		 LimLimiteGasto limite;
		 
		 for (Object record : limiteList) {
			    Object[] fields = (Object[]) record;
			    limite= new LimLimiteGasto();

				   limite.setLimId(0);
				   
				   EleEleccion ele= new EleEleccion();
				   ele.setEleId(idEleccion);
				   limite.setEleEleccion(ele);
				   
				   EveEventoEleccionario eve = new EveEventoEleccionario();
				   eve.setEveId((Integer) fields[0]);
				   limite.setEveEventoEleccionario(eve);
				   
				   TpoEleccion tipoE= new TpoEleccion();
				   tipoE.setTpoEleccionId((Integer) fields[1]);
				   limite.setTpoEleccion(tipoE);
				   
				   ParPartido partido= new ParPartido();
				   partido.setParId((Integer) fields[3]);
				   limite.setParPartido(partido);
				   
				   limite.setLimTotalLimite((Double) fields[2]/3);
			    
			    
			    limiteListResult.add(limite);
			}
		 
		 return limiteListResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<LimLimiteGasto> leeLimitesPartidoIndependientes(Integer idEleccion,Integer tipoCandidato)
	{

		StringBuilder queryString = new StringBuilder();
		
	    queryString.append("select l.eve_id,l.tpo_eleccion_id,p.par_id,true "); 
	    queryString.append(",cast(sum(l.lim_total_limite) as double precision) total_limite "); 
	    queryString.append(",cast(sum(s.sel_cantidad_electores) as integer) cantidad_electores "); 
	    queryString.append(",(select count(distinct p2.par_id)  from par_partido p2, rlc_partido_nivel rlc2, par_partido_maestro pm2 "); 
	    queryString.append("where rlc2.id_par_partido_maestro= pm2.id  and p2.par_codigo_origen=pm2.codigo_origen ");   
	    queryString.append("and ele_id= :idEleccion ");
	    queryString.append("and pac_id=p.pac_id and niv_id=rl.niv_id_padre ");
	    queryString.append("group by p2.pac_id,rlc2.niv_id) cuentaPartidosVigentes "); 
	    queryString.append(",c.can_id "); 
	    queryString.append("from can_candidato c, sel_sub_eleccion s ");   
	    queryString.append(", rlc_rel_nivel rl,par_partido p ");   
	    queryString.append(",lim_limite_gasto l "); 
	    queryString.append("where c.sel_id=s.sel_id ");
	    queryString.append("and c.tpo_can_id= :tipoCandidato ");
	    queryString.append("and s.ele_id= :idEleccion ");
	    queryString.append("and c.sup_id is null and c.pac_id is not null ");   
	    queryString.append("and p.pac_id=c.pac_id ");   
	    queryString.append("and rl.niv_id_hijo=s.niv_id ");
	    queryString.append("and rl.tpo_niv_codigo_padre='MREG' ");
	    queryString.append("and (p.par_id,rl.niv_id_padre) in ( ");
	    queryString.append("select p.par_id,r.niv_id from par_partido_maestro pm, par_partido p, ele_eleccion e,rlc_partido_nivel r "); 
		queryString.append("where pm.codigo_origen=p.par_codigo_origen "); 
		queryString.append("and p.ele_id=e.ele_id "); 
		queryString.append("and e.ele_id= :idEleccion ");
		queryString.append("and r.id_par_partido_maestro=pm.id ");
		queryString.append("and r.eve_id=e.eve_id_eve ");  
	    queryString.append(") ");
	    queryString.append("and l.niv_hijo_id=s.niv_id ");
	    queryString.append("and c.can_habilitado is true "); 
	    queryString.append("group by ");
	    queryString.append("l.eve_id,l.tpo_eleccion_id,p.par_id,p.pac_id,rl.niv_id_hijo,c.can_id,rl.niv_id_padre ");    
	    queryString.append("union ");
	    queryString.append("select l.eve_id,l.tpo_eleccion_id,t1.par_id,true ");
		queryString.append(",cast(sum(l.lim_total_limite) as double precision) total_limite "); 
		queryString.append(",cast(sum(s.sel_cantidad_electores) as integer) cantidad_electores ");
		queryString.append(",(select count(distinct p2.par_id)  from rlc_subpacto_partido rs2, rlc_partido_nivel rlc2, par_partido_maestro pm2, par_partido p2 ");     
		queryString.append("where rlc2.id_par_partido_maestro= pm2.id  and p2.par_id= rs2.par_id ");      
		queryString.append("and p2.par_codigo_origen=pm2.codigo_origen ");      
		queryString.append("and rs2.spa_id=t1.spa_id and rlc2.niv_id=rl.niv_id_padre "); 
		queryString.append("and p2.ele_id= :idEleccion ");    
		queryString.append("group by rs2.spa_id,rlc2.niv_id) cuentaPartidosVigentes ");
		queryString.append(",c.can_id ");  
		queryString.append("from can_candidato c, sel_sub_eleccion s ");   
		queryString.append(", rlc_rel_nivel rl,rlc_partido_nivel rlc,(select rs.par_id,p.par_codigo_origen,rs.spa_id ");  
		queryString.append("from rlc_subpacto_partido rs, par_partido p where p.par_id=rs.par_id and p.ele_id= :idEleccion) t1 "); 
		queryString.append(",lim_limite_gasto l ");   
		queryString.append("where c.sel_id=s.sel_id ");    
		queryString.append("and c.tpo_can_id= :tipoCandidato ");
		queryString.append("and s.ele_id= :idEleccion ");  
		queryString.append("and c.sup_id is not null ");   
		queryString.append("and t1.spa_id=c.sup_id ");  
		queryString.append("and rl.niv_id_hijo=s.niv_id ");   
		queryString.append("and rl.tpo_niv_codigo_padre='MREG' ");
		queryString.append("and (t1.par_id,rl.niv_id_padre) in ( "); 
		queryString.append("select p.par_id,r.niv_id from par_partido_maestro pm, par_partido p, ele_eleccion e,rlc_partido_nivel r "); 
		queryString.append("where pm.codigo_origen=p.par_codigo_origen "); 
		queryString.append("and p.ele_id=e.ele_id "); 
		queryString.append("and e.ele_id= :idEleccion ");
		queryString.append("and r.id_par_partido_maestro=pm.id ");
		queryString.append("and r.eve_id=e.eve_id_eve "); 
		queryString.append(") "); 
		queryString.append("and l.niv_hijo_id=s.niv_id ");
		queryString.append("and c.can_habilitado is true "); 
		queryString.append("group by ");   
		queryString.append("l.eve_id,l.tpo_eleccion_id,t1.par_id,t1.spa_id,rl.niv_id_hijo,c.can_id,rl.niv_id_padre ");   
	    		
	    		


		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("idEleccion", idEleccion);
		query.setParameter("tipoCandidato", tipoCandidato);
	
		 List<LimLimiteGasto> limiteList=(List<LimLimiteGasto>)query.getResultList();
		 List<LimLimiteGasto> limiteListResult= new ArrayList<>();
		 LimLimiteGasto limite;
		 
		 for (Object record : limiteList) {
			    Object[] fields = (Object[]) record;
			    limite= new LimLimiteGasto();

				   limite.setLimId(0);
				   
				   EleEleccion ele= new EleEleccion();
				   ele.setEleId(idEleccion);
				   limite.setEleEleccion(ele);
				   
				   EveEventoEleccionario eve = new EveEventoEleccionario();
				   eve.setEveId((Integer) fields[0]);
				   limite.setEveEventoEleccionario(eve);
				   
				   TpoEleccion tipoE= new TpoEleccion();
				   tipoE.setTpoEleccionId((Integer) fields[1]);
				   limite.setTpoEleccion(tipoE);
				   
				   ParPartido partido= new ParPartido();
				   partido.setParId((Integer) fields[2]);
				   limite.setParPartido(partido);
				   
				   limite.setLimEsLimiteGastoPartido(true);
				   
				   
				   limite.setLimTotalLimite(Double.valueOf(fields[4].toString())/3 /Double.valueOf(fields[6].toString()));
			    
				   limite.setNumeroElectores((Integer) fields[5]);
			    
			    limiteListResult.add(limite);
			}
		 
		 return limiteListResult;
	}
	
	@Transactional
	@Modifying
	public void deleteByEleccion(int idEleccion) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("DELETE FROM lim_limite_gasto WHERE ele_id = ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEleccion);
		
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public LimLimiteGasto leeLimiteGastoCandidatoPorEleccion(Integer idEleccion, Integer rut) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select l.lim_id,l.ele_id,l.eve_id,l.tpo_eleccion_id,l.par_id,l.niv_hijo_id, l.niv_padre_id, l.lim_es_limite_gasto_partido ");
		queryString.append(",l.lim_max_persona,l.lim_total_limite,l.numero_electores, l.lim_max_aporte_sin_pub, l.lim_max_aporte_per_can ");
		queryString.append("from can_candidato c, sel_sub_eleccion s, lim_limite_gasto l ");
		queryString.append("where c.sel_id=s.sel_id ");
		queryString.append("and s.ele_id = ? ");
		queryString.append("and c.can_rut = ? ");
		queryString.append("and l.niv_hijo_id=s.niv_id ");

		Query query = entityManager.createNativeQuery(queryString.toString());
		 query.setParameter(1, idEleccion);
		 query.setParameter(2, rut);
		 
		 List<LimLimiteGasto> limiteList=(List<LimLimiteGasto>)query.getResultList();
		 LimLimiteGasto limite=null;
		 
		 for (Object record : limiteList) {
			    Object[] fields = (Object[]) record;
			    limite= new LimLimiteGasto();

				   limite.setLimId((Integer) fields[0]);
				   
				   EleEleccion ele= new EleEleccion();
				   ele.setEleId((Integer) fields[1]);
				   limite.setEleEleccion(ele);
				   
				   EveEventoEleccionario eve = new EveEventoEleccionario();
				   eve.setEveId((Integer) fields[2]);
				   limite.setEveEventoEleccionario(eve);
				   
				   TpoEleccion tipoE= new TpoEleccion();
				   tipoE.setTpoEleccionId((Integer) fields[3]);
				   limite.setTpoEleccion(tipoE);
				   
				   ParPartido partido= new ParPartido();
				   partido.setParId((Integer) fields[4]);
				   limite.setParPartido(partido);
				   
				   NivNivel nivel= new NivNivel();
				   nivel.setNivId((Integer) fields[5]);
				   limite.setNivNivelByNivHijoId(nivel);
				   
  			       nivel= new NivNivel();
				   nivel.setNivId((Integer) fields[6]);
				   limite.setNivNivelByNivPadreId(nivel);
				   
				   limite.setLimEsLimiteGastoPartido((Boolean) fields[7]);
				   
				   limite.setLimMaxPersona((Double) fields[8]);
				   
				   limite.setLimTotalLimite((Double) fields[9]);
				   
				   limite.setNumeroElectores((Integer) fields[10]);
				   
				   limite.setLimMaxAporteSinPub((Double) fields[11]);
				   
				   limite.setLimMaxAportePerCan((Double) fields[12]);
			
				
				   
			    
			    
			}
		 
		 return limite;
	}
	
	@SuppressWarnings("unchecked")
	public LimLimiteGasto leeLimiteGastoPartidoPorEleccion(Integer idEleccion, String rut) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select  l.lim_id,l.ele_id,l.eve_id,l.tpo_eleccion_id,l.par_id,l.niv_hijo_id, l.niv_padre_id, l.lim_es_limite_gasto_partido ");
		queryString.append(",l.lim_max_persona,l.lim_total_limite,l.numero_electores, l.lim_max_aporte_sin_pub, l.lim_max_aporte_per_can ");
		queryString.append("from par_partido p, lim_limite_gasto l ");
		queryString.append("where p.ele_id= ? ");
		queryString.append("and p.par_rut= ? ");
		queryString.append("and p.par_id=l.par_id ");

		Query query = entityManager.createNativeQuery(queryString.toString());
		 query.setParameter(1, idEleccion);
		 query.setParameter(2, rut);
		 
		 List<LimLimiteGasto> limiteList=(List<LimLimiteGasto>)query.getResultList();
		 LimLimiteGasto limite=null;
		 
		 for (Object record : limiteList) {
			    Object[] fields = (Object[]) record;
			    limite= new LimLimiteGasto();

				   limite.setLimId((Integer) fields[0]);
				   
				   EleEleccion ele= new EleEleccion();
				   ele.setEleId((Integer) fields[1]);
				   limite.setEleEleccion(ele);
				   
				   EveEventoEleccionario eve = new EveEventoEleccionario();
				   eve.setEveId((Integer) fields[2]);
				   limite.setEveEventoEleccionario(eve);
				   
				   TpoEleccion tipoE= new TpoEleccion();
				   tipoE.setTpoEleccionId((Integer) fields[3]);
				   limite.setTpoEleccion(tipoE);
				   
				   ParPartido partido= new ParPartido();
				   partido.setParId((Integer) fields[4]);
				   limite.setParPartido(partido);
				   
				   NivNivel nivel= new NivNivel();
				   nivel.setNivId((Integer) fields[5]);
				   limite.setNivNivelByNivHijoId(nivel);
				   
  			       nivel= new NivNivel();
				   nivel.setNivId((Integer) fields[6]);
				   limite.setNivNivelByNivPadreId(nivel);
				   
				   limite.setLimEsLimiteGastoPartido((Boolean) fields[7]);
				   
				   limite.setLimMaxPersona((Double) fields[8]);
				   
				   limite.setLimTotalLimite((Double) fields[9]);
				   
				   limite.setNumeroElectores((Integer) fields[10]);
				   
				   limite.setLimMaxAporteSinPub((Double) fields[11]);
				   
				   limite.setLimMaxAportePerCan((Double) fields[12]);
			
				
				   
			    
			    
			}
		 
		 return limite;
	}

}
