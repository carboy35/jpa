package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoCtaGasto;

public interface TpoCtaGastoRepository extends CrudRepository<TpoCtaGasto, Integer> {

	@Query("SELECT tbl FROM TpoCtaGasto tbl WHERE tbl.tpoCtaCodigo = :codTipoCuenta AND tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId AND tbl.tpoRendicion.tpoRendicionId = :tipoRendicion")
	Optional<TpoCtaGasto> findByTipoCuentaEventoAndTipoRendicion(@Param("codTipoCuenta") Integer codTipoCuenta, @Param("eventoId") Integer eventoId, @Param("tipoRendicion") Integer tipoRendicion);
	
	@Query("SELECT tbl FROM TpoCtaGasto tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tipoRendicionId AND tbl.tpoRendicion.eveEventoEleccionario.eveId = :eventoId ")
	List<TpoCtaGasto> findByTipoRendicionAndEvento(@Param("tipoRendicionId") Integer tipoRendicionId, @Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM TpoCtaGasto tbl WHERE  tbl.eveEventoEleccionario.eveId = :eventoId order by tpoCtaCodigo")
	List<TpoCtaGasto> tipocuentagastoXEvento(@Param("eventoId") Integer eventoId);
	
	@Query("SELECT t FROM TpoCtaGasto t WHERE t.eveEventoEleccionario.eveId = :eveId AND t.tpoRendicion.tpoCodigo = :codigoRendicion AND t.tpoCtaCodigo = :codigoCuenta")
	Optional<TpoCtaGasto> getByEventocodigoTipoRendicionAndCodigoTipoCuenta(@Param("eveId") int eveId, @Param("codigoRendicion") String codigoRendicion, @Param("codigoCuenta") int codigoCuenta);
}
