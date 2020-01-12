package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DeaDefinicionActividad;

public interface DeaDefinicionActividadRepository extends CrudRepository<DeaDefinicionActividad, Integer> {
	
	@Query("SELECT tbl FROM DeaDefinicionActividad tbl WHERE tbl.dtaDefinicionTarea.dtaId = :dtaId")
	public List<DeaDefinicionActividad> getByDefinicionTarea(@Param("dtaId") int dtaId);
	
	@Query("SELECT tbl FROM DeaDefinicionActividad tbl WHERE tbl.dtaDefinicionTarea.dtaId = :dtaId AND tbl.deaCodigo = :codigo")
	public List<DeaDefinicionActividad> getByCodigoAndDefinicionTarea(@Param("codigo") String codigo, @Param("dtaId") int dtaId);
}
