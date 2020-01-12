package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.IntInstanciaTarea;

public interface IntInstanciaTareaRepository extends CrudRepository<IntInstanciaTarea, Integer>, IntInstanciaTareaRepositoryCustom {

	@Query("SELECT tbl FROM IntInstanciaTarea tbl where tbl.infInstanciaFlujo.infId = :inf_id and tbl.dtaDefinicionTarea.dtaId = :dtaId and tbl.tpoEstadoTarea.tpoTareaId = :tpo_tarea_id")
	Optional<IntInstanciaTarea> getInstanciaTareaByInfIdByDtaIdByTpoTareaId(@Param("inf_id") Integer inf_id,@Param("dtaId") Integer dtaId,@Param("tpo_tarea_id") Integer tpo_tarea_id);
	
	@Query("SELECT tbl FROM IntInstanciaTarea tbl where tbl.infInstanciaFlujo.infId = :inf_id and tbl.usuUsuarios.usuId = :usuId and tbl.intFechaInicio is null ")
	Optional<IntInstanciaTarea> getInstanciaTareaByinfIdIdAndusuId(@Param("inf_id") Integer inf_id, @Param("usuId") Integer usuId);

	@Query("SELECT tbl FROM IntInstanciaTarea tbl where tbl.infInstanciaFlujo.infId = :inf_id and tbl.usuUsuarios.usuId = :usuId")
	List<IntInstanciaTarea> getInstanciasTareaByinfIdIdAndusuId(@Param("inf_id") Integer inf_id, @Param("usuId") Integer usuId);
	
	@Query("select tbl from IntInstanciaTarea tbl where tbl.intId = (SELECT max(tbl1.intId) FROM IntInstanciaTarea tbl1 where tbl1.infInstanciaFlujo.infId = :infId)")
	Optional<IntInstanciaTarea> getUltimaInstanciaTarea(@Param("infId") Integer infId);
	
	@Query("select ins from IntInstanciaTarea ins where (ins.infInstanciaFlujo.infId,ins.intId) in (select i.infInstanciaFlujo.infId,max(i.intId) "
			+ "from IntInstanciaTarea i  where (i.usuUsuarios.usuId in :usuarioIds or :usuarioIds is null) and ins.infInstanciaFlujo.infId = i.infInstanciaFlujo.infId  group by i.infInstanciaFlujo.infId) and (ins.infInstanciaFlujo.eveEventoEleccionario.eveId = :eventoId or :eventoId is null) "
			+ "and (ins.dtaDefinicionTarea.dtaId = :dtaId or :dtaId is null)")
	List<IntInstanciaTarea> getUltimasInstanciasTarea(@Param("eventoId") Integer eventoId,@Param("usuarioIds") List<Integer> usuarioIds,@Param("dtaId") Integer dtaId);
	
	@Query("SELECT ins FROM IntInstanciaTarea ins where (ins.intId,ins.intFechaAsignacion) in (select i.intId,max(i.intFechaAsignacion) "
			+ "from IntInstanciaTarea i where (i.usuUsuarios.usuId in :usuarioIds or :usuarioIds is null) and ins.infInstanciaFlujo.infId = i.infInstanciaFlujo.infId group by i.intId) and (ins.usuUsuarios.usuId in :usuarioIds or :usuarioIds is null) "
			+ "and (ins.infInstanciaFlujo.eveEventoEleccionario.eveId = :eventoId or :eventoId is null) and (ins.dtaDefinicionTarea.dtaId = :dtaId or :dtaId is null) group by ins.intId")
	List<IntInstanciaTarea> getInstanciaTareaByUsuIdEvento(@Param("usuarioIds") List<Integer> usuarioIds,@Param("eventoId") Integer eventoId,@Param("dtaId") Integer dtaId);
	
	@Query("SELECT tbl FROM IntInstanciaTarea tbl where tbl.usuUsuarios.usuId = :usuId order by tbl.intFechaAsignacion desc ")
	List<IntInstanciaTarea> getInstanciasTareaByusuId( @Param("usuId") Integer usuId);
	
	@Query("SELECT tbl FROM IntInstanciaTarea tbl where tbl.infInstanciaFlujo.infId in :inf_ids  order by tbl.intFechaAsignacion desc")
	List<IntInstanciaTarea> getInstanciaTareaByinfIds(@Param("inf_ids") List<Integer> inf_ids);
	
	@Query("SELECT tbl.usuUsuarios.usuId FROM IntInstanciaTarea tbl join DeaDefinicionActividad da on(tbl.dtaDefinicionTarea.dtaId=da.dtaDefinicionTarea.dtaId) where tbl.infInstanciaFlujo.infId =:infId and da.deaCodigo=:deaCodigo order by tbl.intFechaInicio asc ")
	List<Integer> getVisadoresByinfId(@Param("infId") Integer infId,@Param("deaCodigo") String deaCodigo);
	
	@Query("SELECT tbl FROM IntInstanciaTarea tbl WHERE tbl.infInstanciaFlujo.infId = :infId AND tbl.dtaDefinicionTarea.dtaId = :dtaId")
	public Optional<IntInstanciaTarea> getByInstanciaFlujoAndDefinicionTarea(@Param("infId") int infId, @Param("dtaId") int dtaId);
	
	@Query("select ins from IntInstanciaTarea ins where (ins.infInstanciaFlujo.infId,ins.intId) in (select i.infInstanciaFlujo.infId,max(i.intId) "
			+ "from IntInstanciaTarea i where i.infInstanciaFlujo.infId = :infId group by i.infInstanciaFlujo.infId) and ins.tpoEstadoTarea.tpoTipoEstado= :tipoEstadoTarea and ins.tpoEstadoTarea.tpoCodigoEstado= :codigoEstadoTarea "
			+ "and ins.intFechaTermino is  null and ins.dtaDefinicionTarea.dtaId= :dtaId and ins.infInstanciaFlujo.infId = :infId")
	IntInstanciaTarea getUltimaInstanciaTareaByInfDtaTarea(@Param("infId") Integer infId,@Param("dtaId") Integer dtaId,@Param("tipoEstadoTarea") String tipoEstadoTarea,@Param("codigoEstadoTarea") String codigoEstadoTarea);
	
	@Query("select ins from IntInstanciaTarea ins where (ins.infInstanciaFlujo.infId,ins.intId) in (select i.infInstanciaFlujo.infId,max(i.intId) "
			+ "from IntInstanciaTarea i where i.infInstanciaFlujo.infId = :infId group by i.infInstanciaFlujo.infId) and ins.tpoEstadoTarea.tpoTipoEstado= :tipoEstadoTarea and ins.tpoEstadoTarea.tpoCodigoEstado= :codigoEstadoTarea "
			+ "and ins.dtaDefinicionTarea.dtaId in :dtaIds and ins.infInstanciaFlujo.infId = :infId")
	List<IntInstanciaTarea> getUltimasInstanciasTareaByInfDtaTarea(@Param("infId") Integer infId,@Param("dtaIds") List<Integer> dtaIds,@Param("tipoEstadoTarea") String tipoEstadoTarea,@Param("codigoEstadoTarea") String codigoEstadoTarea);

	@Query("SELECT tbl FROM IntInstanciaTarea tbl join DeaDefinicionActividad da on(tbl.dtaDefinicionTarea.dtaId=da.dtaDefinicionTarea.dtaId) where tbl.infInstanciaFlujo.infId =:infId and da.deaCodigo=:deaCodigo "
			+ "and tbl.intId in (SELECT max(ins.intId) FROM IntInstanciaTarea ins join DeaDefinicionActividad da on(ins.dtaDefinicionTarea.dtaId=da.dtaDefinicionTarea.dtaId) where ins.infInstanciaFlujo.infId =:infId and da.deaCodigo=:deaCodigo group by ins.dtaDefinicionTarea.dtaId) order by tbl.intFechaInicio asc ")
	List<IntInstanciaTarea> getVisadoresInfId(@Param("infId") Integer infId,@Param("deaCodigo") String deaCodigo);
	
	@Query("SELECT tbl FROM IntInstanciaTarea tbl WHERE tbl.intId = (SELECT MAX(tbl2.intId) FROM IntInstanciaTarea tbl2 WHERE tbl2.infInstanciaFlujo.infId = :infId AND tbl2.tpoEstadoTarea.tpoCodigoEstado = :estadoTarea)")
	public Optional<IntInstanciaTarea> getUltimaTareaFlujoByEstado(@Param("infId") int infId, @Param("estadoTarea") String estadoTarea);
	
	@Query("SELECT tbl FROM IntInstanciaTarea tbl  where tbl.infInstanciaFlujo.infId =:infId and tbl.dtaDefinicionTarea.dtaCodigoOrigen=:codigoOrigen order by tbl.intFechaInicio asc ")
	List<IntInstanciaTarea> getInstanciasPorCodigo(@Param("infId") Integer infId,@Param("codigoOrigen") String codigoOrigen);
}
