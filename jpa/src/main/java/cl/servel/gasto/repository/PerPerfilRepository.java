package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.servel.gasto.entity.PerPerfil;

@Repository("perPerfilRepository")
public interface PerPerfilRepository extends CrudRepository<PerPerfil, Integer> {

	@Query("SELECT tbl FROM PerPerfil tbl WHERE tbl.perEliminado = FALSE")
	List<PerPerfil> findAllActivePerPerfil();

	@Query("SELECT tbl FROM PerPerfil tbl WHERE tbl.perEliminado =:estado and tbl.perId=:id")
	Optional<PerPerfil> findByIdAndState(@Param("id") Integer id, @Param("estado") boolean estado);

	@Query("SELECT tbl FROM PerPerfil tbl WHERE tbl.perEliminado =false and tbl.perCodigoPerfil=:codigo")
	Optional<PerPerfil> findByCodigo(@Param("codigo") String codigo);
}
