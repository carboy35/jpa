package cl.servel.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.EbaEntidadBancaria;



@Repository("ebaEntidadBancariaRepository")
public interface EbaEntidadBancariaRepository extends CrudRepository<EbaEntidadBancaria, Integer> {

	@Query("SELECT b FROM EbaEntidadBancaria b WHERE b.ebaRut = :rut")
	Optional<EbaEntidadBancaria> getByRut(@Param("rut") String rut);
}
