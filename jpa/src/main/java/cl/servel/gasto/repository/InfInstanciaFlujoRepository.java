package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.InfInstanciaFlujo;

public interface InfInstanciaFlujoRepository extends CrudRepository<InfInstanciaFlujo, Integer>, InfInstanciaFlujoRepositoryCustom {

	@Query("SELECT tbl FROM InfInstanciaFlujo tbl WHERE tbl.canCandidato.canId = :candidatoId")
    Optional<InfInstanciaFlujo> findByCandidato(@Param("candidatoId") Integer candidatoId);
	
	@Query("SELECT tbl FROM InfInstanciaFlujo tbl WHERE tbl.parPartido.parId = :partidoId")
    Optional<InfInstanciaFlujo> findByPartido(@Param("partidoId") Integer partidoId);
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf JOIN IntInstanciaTarea it ON it.infInstanciaFlujo.infId = inf.infId WHERE it.usuUsuarios.usuId = :userId AND it.intFechaTermino IS NOT NULL AND inf.eveEventoEleccionario.eveId=:eventoId  order by inf.infFechaAsignacion asc")
    Optional<List<InfInstanciaFlujo>> getCheckedByUser(@Param("userId") Integer userId,@Param("eventoId") Integer eventoId);
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf JOIN IntInstanciaTarea it ON it.infInstanciaFlujo.infId = inf.infId WHERE it.usuUsuarios.usuId = :userId AND it.intFechaTermino IS NOT NULL AND inf.eveEventoEleccionario.eveId=:eventoId and it.dtaDefinicionTarea.dbnDefinicionBandeja.dbnId = :bandejaId order by inf.infFechaAsignacion asc")
    Optional<List<InfInstanciaFlujo>> getCheckedByUserBandeja(@Param("userId") Integer userId,@Param("eventoId") Integer eventoId,@Param("bandejaId") Integer bandejaId);
	
	@Query("SELECT tbl FROM InfInstanciaFlujo tbl WHERE tbl.usuUsuarios.usuId = :userId and tbl.infFechaTermino is null AND tbl.eveEventoEleccionario.eveId=:eventoId   order by tbl.infFechaAsignacion asc")
    Optional<List<InfInstanciaFlujo>> getNonCheckedByUser(@Param("userId") Integer userId,@Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM InfInstanciaFlujo tbl WHERE tbl.usuUsuarios.usuId = :userId and tbl.infFechaTermino is null AND tbl.eveEventoEleccionario.eveId=:eventoId and tbl.inbInstanciaBandeja.dbnDefinicionBandeja.dbnId=:bandejaId  order by tbl.infFechaAsignacion asc")
    Optional<List<InfInstanciaFlujo>> getNonCheckedByUserBandeja(@Param("userId") Integer userId,@Param("eventoId") Integer eventoId,@Param("bandejaId") Integer bandejaId);
	
	@Query("SELECT SUM(tbl.infTotalLineas) as totalLineas FROM InfInstanciaFlujo tbl where tbl.usuUsuarios.usuId = :usuId")
	Integer findByUsuid(@Param("usuId") Integer usuId);
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf JOIN IntInstanciaTarea it ON it.infInstanciaFlujo.infId = inf.infId AND it.usuUsuarios.usuId <> inf.usuUsuarios.usuId WHERE inf.usuUsuarios.usuId = :userId AND inf.eveEventoEleccionario.eveId=:eventoId and inf.inbInstanciaBandeja.dbnDefinicionBandeja.dbnId=:bandejaId and (inf.infFechaTermino is null) or (it.intFechaTermino IS NOT NULL)  order by inf.infFechaAsignacion asc")
    Optional<List<InfInstanciaFlujo>> getCheckedAndNonCheckedByUser(@Param("userId") Integer userId,@Param("eventoId") Integer eventoId,@Param("bandejaId") Integer bandejaId);
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf  where inf.infEstadoFlujo = :estado")
    Optional<List<InfInstanciaFlujo>> getByEstado(@Param("estado") String estado);
	
//	@Query("SELECT inf FROM InfInstanciaFlujo inf  where inf.infEstadoFlujo = :estado and (inf.usuUsuarios.usuId in :usuarioIds or :usuarioIds is null) and (inf.eveEventoEleccionario.eveId = :eventoId or :eventoId is null) ")
//    Optional<List<InfInstanciaFlujo>> getByEventoUsuariosEstado(@Param("estado") String estado,@Param("usuarioIds") List<Integer> usuarioIds, @Param("eventoId") Integer eventoId);
	
//	@Query("SELECT inf FROM InfInstanciaFlujo inf  where  (inf.usuUsuarios.usuId in :usuarioIds or :usuarioIds is null) and (inf.eveEventoEleccionario.eveId = :eventoId or :eventoId is null) ")
//    Optional<List<InfInstanciaFlujo>> getByEventoUsuarios(@Param("eventoId") Integer eventoId,@Param("usuarioIds") List<Integer> usuarioIds,@Param("dtaId") Integer dtaId);
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf  where inf.infFechaInicio is not null and inf.dtaDefinicionTarea.dtaSlaTarea is not null and inf.dtaDefinicionTarea.dtaSlaTarea>0 ")
    Optional<List<InfInstanciaFlujo>> getEnProcesoConSLA();
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf  where inf.eveEventoEleccionario.eveId=:eventoId and inf.inbInstanciaBandeja.dbnDefinicionBandeja.dbnId=:banId")
	Optional<List<InfInstanciaFlujo>> getPorBandejaYEvento(@Param("banId") int banId, @Param("eventoId") int eventoId);
	
	@Query("SELECT tbl FROM InfInstanciaFlujo tbl WHERE tbl.canCandidato.canId in :candidatoIds")
    List<InfInstanciaFlujo> findByCandidatos(@Param("candidatoIds") List<Integer> candidatoIds);
	
	@Query("SELECT tbl FROM InfInstanciaFlujo tbl WHERE tbl.parPartido.parId in :partidoIds")
    List<InfInstanciaFlujo> findByPartidos(@Param("partidoIds") List<Integer> partidoIds);
	
	@Query("SELECT inf FROM InfInstanciaFlujo inf  where inf.eveEventoEleccionario.eveId=:eventoId ")
	Optional<List<InfInstanciaFlujo>> getPorEvento( @Param("eventoId") int eventoId);
	
}
