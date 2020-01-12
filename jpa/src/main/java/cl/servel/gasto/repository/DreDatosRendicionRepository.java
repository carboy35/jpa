package cl.servel.gasto.repository;

import java.util.List;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DreDatosRendicion;


@Repository("dreDatosRendicionRepository")
public interface DreDatosRendicionRepository extends CrudRepository<DreDatosRendicion, Integer>{
	
	
	
}
