package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.AcrActaRecepcion;
import cl.servel.gasto.entity.AdmAdministradorElectoral;

public interface AcrActaRecepcionRepository extends CrudRepository<AcrActaRecepcion, Integer> {

	@Query("SELECT tbl FROM AcrActaRecepcion tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId")
	List<AcrActaRecepcion> findAllByEventoElectoral(@Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM AcrActaRecepcion tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId AND tbl.tpoEvento.tpoEventoId = :tipoEventoId")
	List<AcrActaRecepcion> findAllByEventoElectoralTipoEvento(@Param("eventoId") Integer eventoId, @Param("tipoEventoId") Integer tipoEventoId);
}
