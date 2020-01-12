package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.ParPartidoMaestro;

public class ParPartidoMaestroRepositoryImpl implements ParPartidoMaestroRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	public List<ParPartidoMaestro> obtenerPartidosMaestrosVigencia (Integer idEvento) {
		ParPartidoMaestro partidoMaestro=null;
		
		StringBuilder queryString = new StringBuilder();
		queryString.append("select id,nombre,estado,codigo_origen from par_partido_maestro pm, par_partido p, ele_eleccion e ");
		queryString.append("where pm.codigo_origen=p.par_codigo_origen ");
		queryString.append("and p.ele_id=e.ele_id ");
		queryString.append("and e.eve_id_eve= ? ");
		queryString.append("group by id,nombre,estado,codigo_origen ");
		 
		 
		

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idEvento);

		
		List<ParPartidoMaestro> partidosList = (List<ParPartidoMaestro>)query.getResultList();
		List<ParPartidoMaestro> partidosListResult = new ArrayList<>();

		for (Object record : partidosList) {
			Object[] fields = (Object[]) record;
			partidoMaestro = new ParPartidoMaestro();
			
			partidoMaestro.setId((Integer) fields[0]);
			
			partidoMaestro.setNombre((String)fields[1]);
			
			partidoMaestro.setEstado((Integer)fields[2]);
			
			partidoMaestro.setCodigoOrigen((String) fields[3]);


			partidosListResult.add(partidoMaestro);
		}


		return partidosListResult;
		
	}
}
