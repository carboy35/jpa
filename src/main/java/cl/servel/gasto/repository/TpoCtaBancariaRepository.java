package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.servel.gasto.entity.TpoCtaBancaria;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository("tpoCtaBancariaRepository")
public interface TpoCtaBancariaRepository extends CrudRepository<TpoCtaBancaria, Integer> {
	@Query( value="alter sequence tpo_cta_bancaria_tpo_cta_bancaria_id_seq restart  ",nativeQuery=true)
	@Modifying
	@Transactional
	void updateSecuence();
}
