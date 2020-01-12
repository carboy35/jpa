package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DdeDetalleDocumentosExternos;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

@Repository("ddeDetalleDocumentosExternosRepository")
public interface DdeDetalleDocumentosExternosRepository extends CrudRepository<DdeDetalleDocumentosExternos, Integer>,GenericCustomRepository<DdeDetalleDocumentosExternos> {

	@Query("SELECT tbl FROM DdeDetalleDocumentosExternos tbl WHERE tbl.dexDocumentoExterno.dexId = :dexId ORDER BY tbl.ddeCreated ASC ")
	List<DdeDetalleDocumentosExternos> findAllByDexId(@Param("dexId") Integer dexId);
}
