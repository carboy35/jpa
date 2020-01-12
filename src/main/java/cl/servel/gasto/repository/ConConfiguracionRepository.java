package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.ConConfiguracion;

@Repository("conConfiguracionRepository")
public interface ConConfiguracionRepository extends CrudRepository<ConConfiguracion, Integer> {

	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.conEliminado = FALSE ")
	List<ConConfiguracion> findAllActiveConConfiguracion();
	
	
	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.eveEventoEleccionario.eveId  =  :eveEventoId ORDER BY tbl.tpoEleccion.tpoEleOrden, tbl.tpoEvento.tpoEventoOrden")
	List<ConConfiguracion> findConConfiguracionByIdEvento(@Param("eveEventoId") Integer eveEventoId);
	
	
	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.eveEventoEleccionario.eveId  =  :eveEventoId and tbl.conEliminado = FALSE and tbl.activo= TRUE")
	List<ConConfiguracion> findConConfiguracionByIdEventoAndActivo(@Param("eveEventoId") Integer eveEventoId);
	
	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.eveEventoEleccionario.eveId  =  :eveEventoId and tbl.conEliminado = FALSE and tbl.activo= TRUE and tbl.tpoEvento.tpoEventoId= :tpoEventoId")
	List<ConConfiguracion> findConConfiguracionByIdEventoAndActivoAndTipoEvento(@Param("eveEventoId") Integer eveEventoId,@Param("tpoEventoId") Integer tpoEventoId);
	
	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.eveEventoEleccionario.eveId  =  :eveEventoId and tbl.conEliminado = FALSE and tbl.tpoEleccion.tpoEleccionId = :tpoEleccionId and tbl.tpoEvento.tpoEventoId= :tpoEventoId")
	Optional<ConConfiguracion> findConConfiguracionByIdEventoAndTipoEleccionAndTipoEvento(@Param("eveEventoId") Integer eveEventoId, @Param("tpoEleccionId") Integer tpoEleccionId, @Param("tpoEventoId") Integer tpoEventoId);
	
	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.eveEventoEleccionario.eveId  =  :eveEventoId and tbl.conEliminado = FALSE and tbl.activo= TRUE AND tbl.estado = 'true'")
	List<ConConfiguracion> findConConfiguracionByIdEventoAndActivoAndSelected(@Param("eveEventoId") Integer eveEventoId);
	
	@Query("SELECT tbl FROM ConConfiguracion tbl WHERE tbl.eveEventoEleccionario.eveId  =  :eveEventoId and tbl.tpoEvento.tpoEventoId = :tipoEventoId and tbl.conEliminado = FALSE and tbl.activo= TRUE AND tbl.estado = 'true' ORDER BY tbl.tpoEleccion.tpoEleOrden, tbl.tpoEvento.tpoEventoOrden")
	List<ConConfiguracion> findConConfiguracionByIdEventoAndTipoEventoAndActivoAndSelected(@Param("eveEventoId") Integer eveEventoId, @Param("tipoEventoId") Integer tipoEventoId);
}
