package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.FunFuncionalidad;

@Repository("funFuncionalidadRepository")
public interface FunFuncionalidadRepository extends CrudRepository<FunFuncionalidad, Integer>{
	@Query("SELECT tbl FROM FunFuncionalidad tbl WHERE tbl.funEliminado = FALSE")
	List<FunFuncionalidad> findAllActiveFunFuncionalidad();
	
	@Query("SELECT tbl FROM FunFuncionalidad tbl WHERE tbl.funEliminado =:estado and tbl.funId=:id")
	Optional<FunFuncionalidad> findByIdAndState(@Param("id") Integer id, @Param("estado") boolean estado);
}
