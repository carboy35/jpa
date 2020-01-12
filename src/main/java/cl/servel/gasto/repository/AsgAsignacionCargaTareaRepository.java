package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.AsgAsignacionCargaTarea;

public interface AsgAsignacionCargaTareaRepository extends CrudRepository<AsgAsignacionCargaTarea, Integer>,AsgAsignacionCargaTareaRepositoryCustom{
	
	@Query("SELECT tbl FROM AsgAsignacionCargaTarea tbl WHERE tbl.dtaDefinicionTarea.dtaId = :dtaId and tbl.gutGrupoUsuarioTarea.celCelula.celActiva = true")
	List<AsgAsignacionCargaTarea> getAsignacionTareaByDtaId(@Param("dtaId") Integer dtaId);
	
	@Query("SELECT tbl FROM AsgAsignacionCargaTarea tbl WHERE tbl.gutGrupoUsuarioTarea.gutId = :gutId ")
	List<AsgAsignacionCargaTarea> getByGrupoUsuarioTarea(@Param("gutId") Integer gutId);
	
	@Query("select tbl from AsgAsignacionCargaTarea tbl where tbl.gutGrupoUsuarioTarea.celCelula.celId = :celulaId and tbl.dtaDefinicionTarea.dtaId = :definicionTareaId")
	List<AsgAsignacionCargaTarea> getByCelulaAndDefinicionTarea(@Param("celulaId") Integer celulaId, @Param("definicionTareaId") Integer definicionTareaId);
	
	@Query("select tbl from AsgAsignacionCargaTarea tbl where tbl.gutGrupoUsuarioTarea.celCelula.celId = :celulaId")
	List<AsgAsignacionCargaTarea> getByCelula(@Param("celulaId") Integer celulaId);
}
