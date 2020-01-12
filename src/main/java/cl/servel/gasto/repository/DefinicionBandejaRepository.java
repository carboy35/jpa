package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DbnDefinicionBandeja;

public interface DefinicionBandejaRepository extends CrudRepository<DbnDefinicionBandeja, Integer> {

	@Query("SELECT tbl FROM DtaDefinicionTarea tbl1 join DbnDefinicionBandeja tbl on tbl.dbnId = tbl1.dbnDefinicionBandeja.dbnId WHERE tbl1.dtaId = :dtaId")
	public Optional<DbnDefinicionBandeja> getByDefinicionTarea(@Param("dtaId") Integer dtaId);
	@Query("SELECT tbl FROM InbInstanciaBandeja tbl1 join DbnDefinicionBandeja tbl on tbl.dbnId = tbl1.dbnDefinicionBandeja.dbnId WHERE tbl1.eveEventoEleccionario.eveId = :eventId and tbl1.usuUsuarios.usuId=:userId")
	public List<DbnDefinicionBandeja> findAllPorUsuario(@Param("userId") Integer userId,@Param("eventId") Integer eventId);
}
