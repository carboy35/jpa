package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.servel.gasto.entity.TpoCtaContable;

@Repository("tpoCtaContableRepository")
public interface TpoCtaContableRepository extends CrudRepository<TpoCtaContable, Integer>{
	

}
