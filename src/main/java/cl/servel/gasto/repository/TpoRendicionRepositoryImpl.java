package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.TpoRendicion;

public class TpoRendicionRepositoryImpl implements TpoRendicionRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	@SuppressWarnings("unchecked")
	public List<TpoRendicion> devuelveTiposRendicionInstanciaFlujo(Integer eventoId,String codigoFormulario1, String codigoFormulario2){
		StringBuilder queryString = new StringBuilder();
		queryString.append("select ");
		queryString.append("tr.tpo_rendicion_id, ");
		queryString.append("tr.tpo_nombre, ");
		queryString.append("tr.tpo_codigo, ");
		queryString.append("tr.eve_id ");
		queryString.append("from ");
		queryString.append("tpo_rendicion tr, ");
		queryString.append("inf_instancia_flujo inf ");
		queryString.append("where ");
		queryString.append("tr.eve_id = inf.eve_id ");
		queryString.append("and tpo_codigo in (:codigoFormulario1, :codigoFormulario2) ");
		
		if (eventoId !=null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		queryString.append("group by tr.tpo_rendicion_id");
		
		 Query query = entityManager.createNativeQuery(queryString.toString());
		 if (eventoId !=null) {
				query.setParameter("eventoId", eventoId);
		 }
		 query.setParameter("codigoFormulario1", codigoFormulario1);
		 query.setParameter("codigoFormulario2", codigoFormulario2);
		 
		 
		 List<TpoRendicion> tipoRendicionList= (List<TpoRendicion>)query.getResultList();
		 List<TpoRendicion> tipoRendicionResultList= new ArrayList<>();
		 TpoRendicion tipoRendicion=null;
		 EveEventoEleccionario evento=null;
		 for (Object record:tipoRendicionList ) {
			 Object[] fields = (Object[]) record;
			 tipoRendicion=new TpoRendicion();
			 tipoRendicion.setTpoRendicionId((Integer)fields[0]);
			 tipoRendicion.setTpoNombre((String)fields[1]);
			 tipoRendicion.setTpoCodigo((String)fields[2]);
			 if (fields[3]!=null) {
				 evento= new EveEventoEleccionario();
				evento.setEveId((Integer)fields[3]);
				 tipoRendicion.setEveEventoEleccionario(evento);
			 }
			 tipoRendicionResultList.add(tipoRendicion);
		 }
		 return tipoRendicionResultList;
		 
	}
}
