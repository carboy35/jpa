package cl.servel.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.InbInstanciaBandeja;

public interface InbInstanciaBandejaRepository extends CrudRepository<InbInstanciaBandeja, Integer> {
	
	@Query("select tbl from InbInstanciaBandeja tbl where tbl.usuUsuarios.usuId = :usuarioId and tbl.dbnDefinicionBandeja.dbnId = :definicionBandejaId and tbl.eveEventoEleccionario.eveId = :eveId")
	public Optional<InbInstanciaBandeja> getByUsuarioAndDefinicionTareaAndEvento(@Param("usuarioId") int usuarioId, @Param("definicionBandejaId") int definicionBandejaId, @Param("eveId") int eveId);
}
