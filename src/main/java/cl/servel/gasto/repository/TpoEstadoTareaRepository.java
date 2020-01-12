package cl.servel.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoEstadoTarea;

public interface TpoEstadoTareaRepository extends CrudRepository<TpoEstadoTarea, Integer>{

	@Query("select tbl from TpoEstadoTarea tbl where tbl.tpoCodigoEstado = :codigoEstadoTarea")
	public Optional<TpoEstadoTarea> getByCodigo(@Param("codigoEstadoTarea") String codigoEstadoTarea);
}
