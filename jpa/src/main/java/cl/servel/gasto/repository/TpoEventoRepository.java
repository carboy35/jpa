package cl.servel.gasto.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.TpoEvento;

@Repository("tpoEventoRepository")
public interface TpoEventoRepository extends CrudRepository<TpoEvento, Integer>,TpoEventoRepositoryCustom {
	@Query("SELECT tbl FROM TpoEvento tbl  order by tbl.tpoEventoId asc")
	List<TpoEvento> findAllActiveTpoEvento();

	@Query("SELECT tbl FROM TpoEvento tbl order by tbl.tpoEventoOrden asc")
	List<TpoEvento> findAll();
	
	@Query("SELECT tbl FROM TpoEvento tbl WHERE tbl.tpoCodigoOrigen = :codigoOrigen")
	TpoEvento findByTipoEventoCodigo(@Param("codigoOrigen") String codigoOrigen);
}
