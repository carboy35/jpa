package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.CelCelula;
import cl.servel.gasto.repository.custom.CelCelulaRepositoryCustom;

public interface CelCelulaRepository extends CrudRepository<CelCelula, Integer>, CelCelulaRepositoryCustom {

	@Query("select tbl from CelCelula tbl where tbl.celActiva = true")
	List<CelCelula> getActives();

	@Query("select tbl from CelCelula tbl where tbl.celActiva = true and tbl.eveEventoEleccionario.eveId = :eventoId")
	List<CelCelula> getActivesByEvento(Integer eventoId);
	
	/*
	@Query("select tbl from CelCelula tbl where tbl.celActiva = true "
			+ "and tbl.gutGrupoUsuarioTareas.asgAsignacionCargaTareas.dtaDefinicionTarea.tpoEstadoTarea.tpoCodigoEstado = :tpoCodigoEstado ")
	List<CelCelula> getByCodEstadoTarea(@Param("tpoCodigoEstado") String tpoCodigoEstado);
	 */
}
