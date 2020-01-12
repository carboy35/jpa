package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.RlcConceptoCtaGasto;
import cl.servel.gasto.entity.TpoConceptoCtaGasto;

public interface RlcConceptoCtaGastoRepository extends CrudRepository<RlcConceptoCtaGasto, Integer> {
	@Query("SELECT tbl FROM RlcConceptoCtaGasto tbl WHERE tbl.tpoConceptoCtaGasto.tpoConceptoId = :tipoConceptoId ")
	List<RlcConceptoCtaGasto> findRelTipoConceptoGastoByConcepto(@Param("tipoConceptoId") Integer tipoConceptoId);
	
	
}
