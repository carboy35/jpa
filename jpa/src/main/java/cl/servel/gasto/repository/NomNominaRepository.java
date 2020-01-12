package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.NomNomina;

public interface NomNominaRepository extends CrudRepository<NomNomina,Integer>, NomNominaRepositoryCustom {
	
	@Query("SELECT n from NomNomina n WHERE n.eveEventoEleccionario.eveId = :eveId AND n.nomEstado = :estado")
	List<NomNomina> getByEstado(@Param("eveId") int eveId, @Param("estado") String estado);
	
	@Query("SELECT SUM(detMonto) FROM DetDetalleNomina WHERE nomNomina.nomId = :nomId ")
	Integer getMontoNomina(@Param("nomId") int nomId);
}
