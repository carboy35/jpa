package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoReembolso;

public interface TpoReembolsoRepository extends CrudRepository<TpoReembolso, Integer> {
	@Query("SELECT tbl FROM TpoReembolso tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tipoRendicionId AND tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId ")
	List<TpoReembolso> findByTipoRendicionAndEvento(@Param("tipoRendicionId") Integer tipoRendicionId, @Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM TpoReembolso tbl WHERE tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId ")
	List<TpoReembolso> findByEvento( @Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM TpoReembolso tbl WHERE tbl.tpoReembolsoCodigo = :codigo AND tbl.tpoRendicion.tpoRendicionId = :tpoRendicion AND tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId ")
	Optional<TpoReembolso> getTipoReembolsoByCodigoAndEvento(@Param("codigo") String codigo,  @Param("tpoRendicion") Integer tpoRendicion, @Param("eventoId") Integer eventoId);
}
