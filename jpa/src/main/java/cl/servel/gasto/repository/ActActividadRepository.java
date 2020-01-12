package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.ActActividad;

public interface ActActividadRepository extends CrudRepository<ActActividad, Integer> {
	
	@Query("SELECT tbl FROM ActActividad tbl WHERE tbl.intInstanciaTarea.intId = :intId AND tbl.actCodigo = :codigo")
	public Optional<ActActividad> getByCodigoAndInstanciaTarea(@Param("codigo") String codigo, @Param("intId") int intId);
	
	@Query("SELECT tbl FROM ActActividad tbl WHERE tbl.intInstanciaTarea.intId = :intId")
	public List<ActActividad> getByInstanciaTarea(@Param("intId") int intId);
}