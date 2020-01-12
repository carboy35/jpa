package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.ObrObservacionRendicion;

public interface ObrObservacionRendicionRepository extends CrudRepository<ObrObservacionRendicion, Integer>,ObrObservacionRendicionRepositoryCustom{

	@Query("SELECT tbl FROM ObrObservacionRendicion tbl WHERE tbl.renRendicion.renId = :renId AND tbl.obrNumeroPagina = :nPag AND tbl.obrNumeroLinea = :nLin")
	List<ObrObservacionRendicion> findByRenIdNPagNLinea(@Param("renId") int renId, @Param("nPag") int nPag, @Param("nLin") int nLin);

	@Query("SELECT tbl FROM ObrObservacionRendicion tbl WHERE tbl.renRendicion.renId = :renId ORDER BY tbl.obrNumeroPagina, tbl.obrNumeroLinea")
	List<ObrObservacionRendicion> findByRenId(@Param("renId") int renId);
}
