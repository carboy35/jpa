package cl.servel.gasto.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.Modifying;

public class RlcRelNivelRepositoryImpl {
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();

		
		queryString.append("delete from rlc_rel_nivel rn, sel_sub_eleccion s ");
		queryString.append("where rn.niv_id_hijo=s.niv_id ");
		queryString.append("and s.ele_id= ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
}
