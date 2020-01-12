package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.UsuUsuarios;

@Repository("usuUsuariosRepository")
public interface UsuUsuariosRepository extends CrudRepository<UsuUsuarios, Integer>,UsuUsuariosRepositoryCustom{
	@Query("SELECT tbl FROM UsuUsuarios tbl WHERE tbl.usuEliminado = FALSE")
	List<UsuUsuarios> findAllActiveUsuUsuarios();
	
	@Query("SELECT tbl FROM UsuUsuarios tbl WHERE tbl.usuEliminado = FALSE AND tbl.usuRut = :rut AND tbl.usuRutDv = :dv")
	Optional<UsuUsuarios> findActiveByRut(@Param("rut") String rut, @Param("dv") String dv);

	@Query("SELECT tbl FROM UsuUsuarios tbl WHERE tbl.usuEliminado = FALSE AND tbl.usuNombreAcceso = :nombreAcceso")
	Optional<UsuUsuarios> findActiveByNameUserAccees(@Param("nombreAcceso") String nombreAcceso);
	
	@Query("SELECT tbl FROM UsuUsuarios tbl WHERE tbl.usuEliminado =:estado and tbl.usuId=:id")
	Optional<UsuUsuarios> findByIdAndState(@Param("id") Integer id, @Param("estado") boolean estado);

	@Query("SELECT tbl FROM UsuUsuarios tbl WHERE tbl.usuIdSubrogante=:id and tbl.usuEliminado = TRUE")
	List<UsuUsuarios> findAllUsuariosSubroganciaActiva(@Param("id") Integer id);
}
