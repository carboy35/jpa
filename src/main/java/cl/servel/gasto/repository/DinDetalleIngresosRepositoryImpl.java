package cl.servel.gasto.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public class DinDetalleIngresosRepositoryImpl implements DinDetalleIngresosRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	
	@Transactional
	@Modifying
	public void deleteByIngreso(int ingId) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("DELETE FROM din_detalle_ingresos WHERE ing_id = :ingId");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("ingId", ingId);
		
		query.executeUpdate();
	}
}
