package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.servel.gasto.entity.TpoNivel;

@Repository("tpoNivelRepository")
public interface TpoNivelRepository extends CrudRepository<TpoNivel, String> {
	@Query("SELECT tbl FROM TpoNivel tbl WHERE tbl.tpoNivEliminado = FALSE")
	List<TpoNivel> findAllActiveTpoNivel();
}
