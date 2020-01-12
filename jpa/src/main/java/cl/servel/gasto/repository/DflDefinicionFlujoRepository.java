package cl.servel.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DflDefinicionFlujo;

public interface DflDefinicionFlujoRepository extends CrudRepository<DflDefinicionFlujo, Integer>{
	@Query("SELECT tbl FROM DflDefinicionFlujo tbl WHERE tbl.dflCodigo = :dflCodigo AND tbl.eveEventoEleccionario.eveId = :eveId AND tbl.version = ( SELECT MAX(tbl.version) FROM DflDefinicionFlujo tbl WHERE tbl.dflCodigo = :dflCodigo AND tbl.eveEventoEleccionario.eveId = :eveId )")
	public Optional<DflDefinicionFlujo> getByCodigo(@Param("dflCodigo") String dflCodigo, @Param("eveId") int eveId);
}
