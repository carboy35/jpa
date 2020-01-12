package cl.servel.gasto.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.TpoEvento;

public class TpoEventoRepositoryImpl implements TpoEventoRepositoryCustom{
	@PersistenceContext
	EntityManager entityManager;
	public List<TpoEvento> devuelvePartidosTipoEvento(Integer eventoId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select tte.tpo_evento_id,tte.tpo_evento_nombre,count(*),t1.tpo_codigo_origen from ( ");
		queryString.append("select p.par_codigo_origen,te.tpo_codigo_origen,p.par_nombre from par_partido_maestro pm, ");
		queryString.append("par_partido p, ");
		queryString.append("cad_carga_datos cd, "); 
		queryString.append("tpo_evento te, ");
		queryString.append("ele_eleccion e ");
		queryString.append("where pm.codigo_origen=p.par_codigo_origen ");
		queryString.append("and p.ele_id=e.ele_id ");
		queryString.append("and e.eve_id_eve = :eventoId ");
		queryString.append("and e.tpo_evento_id=te.tpo_evento_id ");
		queryString.append("group by p.par_codigo_origen,te.tpo_codigo_origen,p.par_nombre ");
		queryString.append(") t1, tpo_evento tte ");
		queryString.append("where t1.tpo_codigo_origen=tte.tpo_codigo_origen ");
		queryString.append("group by tte.tpo_evento_id,t1.tpo_codigo_origen,tte.tpo_evento_nombre ");

		
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId !=null) {
			query.setParameter("eventoId", eventoId);
		}
		
		
		List<TpoEvento> tipoEventoList= (List<TpoEvento>) query.getResultList();
		List<TpoEvento> tipoEventoListResult=	new ArrayList<>();
		TpoEvento tipoEvento=null;
		
		for (Object record : tipoEventoList) {
			Object[] fields = (Object[]) record;
			tipoEvento = new TpoEvento();
			tipoEvento.setTpoEventoId((Integer)fields[0]);
			tipoEvento.setTpoEventoNombre((String)fields[1]);
			BigInteger bgTipoEvento=null;
			bgTipoEvento=(BigInteger)fields[2];
			tipoEvento.setTpoEventoOrden(bgTipoEvento.intValue());
			tipoEvento.setTpoCodigoOrigen((String)fields[3]);
			
			tipoEventoListResult.add(tipoEvento);
		}
		return tipoEventoListResult;
	}
}
