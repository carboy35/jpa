package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DocDocumentoAdjunto;

public interface DocDocumentoAdjuntoRepository extends CrudRepository<DocDocumentoAdjunto, Integer> {

	@Query("SELECT tbl FROM DocDocumentoAdjunto tbl WHERE tbl.docIdRepositorioDocumental = :idRepositorioDocumental")
	Optional<DocDocumentoAdjunto> findByIdGestorDocumental(@Param("idRepositorioDocumental") String idRepositorioDocumental);
	
	@Query("SELECT tbl FROM DocDocumentoAdjunto tbl WHERE tbl.renRendicion.renId = :idRendicion")
	List<DocDocumentoAdjunto> findByIdRendicion(@Param("idRendicion") int idRendicion);
}
