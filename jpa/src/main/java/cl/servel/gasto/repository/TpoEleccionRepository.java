package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.servel.gasto.entity.TpoEleccion;

@Repository("tpoEleccionRepository")
public interface TpoEleccionRepository extends CrudRepository<TpoEleccion, Integer> {
	@Query("SELECT tbl FROM TpoEleccion tbl WHERE tbl.tpoEleEliminado = FALSE order by tbl.tpoEleccionId asc")
	List<TpoEleccion> findAllActiveTpoEleccion();
	
	@Query("SELECT tbl FROM TpoEleccion tbl WHERE tbl.tpoEleEliminado = FALSE order by tbl.tpoEleOrden asc")
	List<TpoEleccion> findAll();
	
	@Query("SELECT tbl FROM TpoEleccion tbl WHERE tbl.tpoEleEliminado = FALSE and tbl.tpoCodigoOrigen = :codigoOrigen")
	TpoEleccion findByTipoEleccionCodigo(@Param("codigoOrigen") String codigoOrigen);
}
