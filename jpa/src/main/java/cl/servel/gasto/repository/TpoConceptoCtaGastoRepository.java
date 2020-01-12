package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.TpoConceptoCtaGasto;

public interface TpoConceptoCtaGastoRepository extends CrudRepository<TpoConceptoCtaGasto, Integer> {
	
	@Query("SELECT tbl FROM TpoConceptoCtaGasto tbl WHERE tpoRendicion.tpoCodigo = :codigoTipoRendicion and tbl.eveEventoEleccionario.eveId = :eventoId ")
	List<TpoConceptoCtaGasto> findTipoConceptoGastoByCodigoEvento(@Param("codigoTipoRendicion") String codigoTipoRendicion,@Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM TpoConceptoCtaGasto tbl WHERE tpoRendicion.tpoCodigo = :codigoTipoRendicion and tbl.eveEventoEleccionario.eveId = :eventoId and tbl.tpoConceptoCodigo = :codigoConcepto")
	List<TpoConceptoCtaGasto> findTipoConceptoGastoByCodigoEventoConcepto(@Param("codigoTipoRendicion") String codigoTipoRendicion,@Param("eventoId") Integer eventoId,@Param("codigoConcepto") String codigoConcepto);
	
	@Query("SELECT tbl FROM TpoConceptoCtaGasto tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId")
	List<TpoConceptoCtaGasto> findTipoConceptoPorEvento(@Param("eventoId") Integer eventoId);
	
	
	
}
