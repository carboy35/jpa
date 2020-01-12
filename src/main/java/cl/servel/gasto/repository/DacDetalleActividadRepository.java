package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DacDetalleActividad;

public interface DacDetalleActividadRepository extends CrudRepository<DacDetalleActividad, Integer> {

	@Query("SELECT tbl FROM DacDetalleActividad tbl WHERE tbl.actActividad.actId = :actividadId")
	public List<DacDetalleActividad> getByActividad(@Param("actividadId") int actividadId);
	
	@Query("SELECT COUNT(tbl) from DacDetalleActividad tbl WHERE tbl.actActividad.actId = :actividadId AND tbl.dacActividadCompletada = false")
	public Integer getCantidadDetallesNoCompletados(@Param("actividadId") int actividadId);
}
