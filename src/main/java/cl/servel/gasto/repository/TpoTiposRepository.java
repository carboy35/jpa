package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoTipos;

public interface TpoTiposRepository extends CrudRepository<TpoTipos,Integer> {
	
	@Query("select tbl from TpoTipos tbl where tpoCodAgrupacion = :codigoAgrupacion")
	Optional<List<TpoTipos>> getByCodAgrupacion(@Param("codigoAgrupacion") String codigoAgrupacion);

	@Query("select tbl from TpoTipos tbl where tpoCodigo = :codigo")
	Optional<TpoTipos> getByCodigo(@Param("codigo") String codigo);
}
