package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoObservacion;

public interface TpoObservacionRepository extends CrudRepository<TpoObservacion, Integer>
{
	
	@Query("SELECT tbl FROM TpoObservacion tbl where tbl.eveEventoEleccionario.eveId = :eveId ORDER BY tbl.tpoObsId DESC ")
	List<TpoObservacion> getAllByEvento(Integer eveId);
	
	@Query("SELECT tbl FROM TpoObservacion tbl where tbl.tpoCtaGasto.tpoCtaId = :tpoCtaId ORDER BY tbl.tpoObsId DESC ")
	List<TpoObservacion> getAllByCtaGasto(@Param("tpoCtaId") Integer tpoCtaId);
	
	@Query("SELECT tbl FROM TpoObservacion tbl where tbl.tpoCtaGasto.tpoCtaId = :tpoCtaId and tbl.eveEventoEleccionario.eveId = :eventoId and tbl.tpoNombreCorto = :nombreCorto ")
	Optional<List<TpoObservacion>> getByCtaGastoEventoNombre(@Param("tpoCtaId") Integer tpoCtaId,@Param("eventoId") Integer eventoId, @Param("nombreCorto") String nombreCorto);
	
}
