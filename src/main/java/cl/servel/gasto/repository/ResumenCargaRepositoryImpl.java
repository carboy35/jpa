package cl.servel.gasto.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

public class ResumenCargaRepositoryImpl {
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("delete from resumen_carga r "); 
		queryString.append("where r.ele_id= ? ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
}
