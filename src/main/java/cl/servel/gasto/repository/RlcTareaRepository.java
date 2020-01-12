package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.RlcTarea;

public interface RlcTareaRepository extends CrudRepository<RlcTarea, Integer>{
	
	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dtaId = :dtaId ")
	RlcTarea getRelacionTareaSiguienteByDtaId(@Param("dtaId") Integer dtaId);

	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dtaId = :dtaId ")
	List<RlcTarea> getRelacionTareaSiguienteByDtaIdList(@Param("dtaId") Integer dtaId);
	
	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dflDefinicionFlujo.dflId = :infId  and tbl.dtaDefinicionTareaByDtaId.dtaId =  tbl.dtaDefinicionTareaByRlcIdAnterior.dtaId and tbl.rlcRutaActiva = true")
	RlcTarea getRelacionTareaByinfId(@Param("infId") Integer infId);
	
	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dtaId = :dtaId AND tbl.rlcCondicion = :condicion AND tbl.rlcRutaActiva = true")
	Optional<RlcTarea> getByDtaAndCondicion(@Param("dtaId") Integer dtaId, @Param("condicion") String condicion);
	
	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dtaId = :dtaId  AND tbl.rlcRutaActiva is false and tbl.dtaDefinicionTareaByDtaId.dtaRutaDinamica is true")
	List<RlcTarea> getTareasPosiblesSiguiente(@Param("dtaId") Integer dtaId);


	@Query("SELECT tbl FROM RlcTarea tbl   WHERE tbl.rlcRutaActiva=true and tbl.dtaDefinicionTareaByDtaId.dtaId = :dtaId  AND  tbl.dtaDefinicionTareaByDtaId.dtaRutaDinamica is true")
	List<RlcTarea> getRutaActiva(@Param("dtaId") Integer dtaId);
	

	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dtaId = :dtaId  AND  tbl.dtaDefinicionTareaByRlcIdSiguiente.dtaId =:dtaIdSiguiente")
	List<RlcTarea> getByIdTareaYIdTareaSiguiente(@Param("dtaId") Integer dtaId,@Param("dtaIdSiguiente") Integer dtaIdSiguiente);
	
	@Query("SELECT tbl FROM RlcTarea tbl WHERE tbl.dtaDefinicionTareaByDtaId.dflDefinicionFlujo.dflId = :dflId")
	List<RlcTarea> getByDefinicionflujo(@Param("dflId") int dflId);
}
