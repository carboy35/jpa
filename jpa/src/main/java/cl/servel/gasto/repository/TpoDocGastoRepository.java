package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoDocGasto;

public interface TpoDocGastoRepository extends CrudRepository<TpoDocGasto, Integer> {

	@Query("SELECT tbl FROM TpoDocGasto tbl WHERE tbl.tpoDocCodigo = :codTipoDocumento AND tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId AND tbl.tpoRendicion.tpoRendicionId = :tipoRendicion")
	Optional<TpoDocGasto> findByTipoCuentaEventoAndTipoRendicion(@Param("codTipoDocumento") String codTipoDocumento, @Param("eventoId") Integer eventoId, @Param("tipoRendicion") Integer tipoRendicion);
	
	@Query("SELECT tbl FROM TpoDocGasto tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tipoRendicionId AND tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId ")
	List<TpoDocGasto> findByTipoRendicionAndEvento(@Param("tipoRendicionId") Integer tipoRendicionId, @Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM TpoDocGasto tbl WHERE  tbl.eveEventoEleccionario.eveId = :eventoId ")
	List<TpoDocGasto> tipoDoctoGastoXEvento(@Param("eventoId") Integer eventoId);
	
	@Query("SELECT t FROM TpoDocGasto t WHERE t.eveEventoEleccionario.eveId = :eveId AND t.tpoRendicion.tpoCodigo = :codigoTipoRendicion AND t.tpoDocCodigo = :codigoTipoDocumento")
	Optional<TpoDocGasto> getByEventoCodigoTipoRendicionAndCodigoTipoDocumento(@Param("eveId") int eveId, @Param("codigoTipoRendicion") String codigoTipoRendicion, @Param("codigoTipoDocumento") String codigoTipoDocumento);
}
