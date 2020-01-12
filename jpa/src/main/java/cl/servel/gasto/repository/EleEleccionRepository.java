package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.EleEleccion;

@Repository("eleEleccionRepository")
public interface EleEleccionRepository extends CrudRepository<EleEleccion, Integer>{
	@Query("SELECT tbl FROM EleEleccion tbl WHERE tbl.eleEliminado = FALSE ")
	List<EleEleccion> findAllActiveEleEleccion();
	
	@Query("SELECT tbl FROM EleEleccion tbl  WHERE tbl.eveEventoEleccionario.eveId = :eveEventoId")
	List<EleEleccion> findEleEleccionByIdEvento(@Param("eveEventoId") Integer eveEventoId);
	
	@Query("SELECT tbl FROM EleEleccion tbl  WHERE tbl.eveEventoEleccionario.eveId = :eveEventoId and tbl.tpoEvento.tpoEventoId= :tpoEventoId")
	List<EleEleccion> findEleEleccionByIdEventoAndIdTipoEvento(@Param("eveEventoId") Integer eveEventoId,@Param("tpoEventoId") Integer tpoEventoId);
	
	@Query("SELECT tbl FROM EleEleccion tbl  WHERE tbl.eveEventoEleccionario.eveId = :eveEventoId and tbl.tpoEleccion.tpoEleccionId= :tpoEleccionId")
	List<EleEleccion> findEleEleccionByIdEventoAndIdTipoEleccion(@Param("eveEventoId") Integer eveEventoId,@Param("tpoEleccionId") Integer tpoEleccionId);
	
	@Query("SELECT tbl FROM EleEleccion tbl  WHERE tbl.eveEventoEleccionario.eveId = :eveEventoId and tbl.tpoEvento.tpoEventoId= :tpoEventoId and tbl.tpoEleccion.tpoEleccionId= :tpoEleccionId and tbl.eleEliminado = false")
	List<EleEleccion> findEleEleccionByIdEventoAndIdTipoEventoAndIdTipoEleccion(@Param("eveEventoId") Integer eveEventoId,@Param("tpoEventoId") Integer tpoEventoId,@Param("tpoEleccionId") Integer tpoEleccionId);
	
	@Query("SELECT tbl FROM EleEleccion tbl  WHERE extract(year from tbl.eveEventoEleccionario.eveFecha) = :annio and tbl.tpoEvento.tpoEventoId= :idTipoEvento and tbl.tpoEleccion.tpoEleccionId= :idTipoEleccion and tbl.eleEliminado = false")
	List<EleEleccion> findEleEleccionByFechaAndTipoEleccionAndTipoEvento(@Param("annio") Integer annio,@Param("idTipoEvento") Integer idTipoEvento,@Param("idTipoEleccion") Integer idTipoEleccion);
	
	@Query("SELECT tbl FROM EleEleccion tbl join SelSubEleccion sel on  tbl.eleId = sel.eleEleccion.eleId join CanCandidato tblc on sel.selId =tblc.selSubEleccion.selId WHERE  tblc.canRut = :rut   and tbl.eleEliminado = false and tbl.eveEventoEleccionario.eveId = :eveId")
	List<EleEleccion> findEleEleccionByEventoAndRutAndCandidato(@Param("eveId") Integer eveId,@Param("rut") Integer rut);
	
	
	@Query("SELECT tbl FROM EleEleccion tbl  join  HisHistorico tblh on tblh.eveEventoEleccionario.eveId=tbl.eveEventoEleccionario.eveId  join  AdmAdministradorElectoral tbla on tbla.admId =tblh.admAdministradorElectoral.admId  join CanCandidato tblc on tblc.canId = tblh.canCandidato.canId  join SelSubEleccion tbls on tbls.selId =tblc.selSubEleccion.selId   WHERE  tbls.eleEleccion.eleId = tbl.eleId and tbla.admRut = :rut and  tblh.hisFechaTermino is null  and  tbl.eleEliminado = false and  tbl.eveEventoEleccionario.eveId = :eveId  group by tbl.eleId")
	List<EleEleccion> findEleEleccionByEventoAndRutAndAdministradorElectoral(@Param("eveId") Integer eveId,@Param("rut") String rut);
	
	
	@Query("SELECT tbl FROM EleEleccion tbl  join  HisHistorico tblh on tblh.eveEventoEleccionario.eveId=tbl.eveEventoEleccionario.eveId  join  AdmAdministradorElectoral tbla on tbla.admId =tblh.admAdministradorElectoral.admId  WHERE  tblh.parPartido.eleEleccion.eleId = tbl.eleId and tbla.admRut = :rut and  tblh.hisFechaTermino is null  and  tbl.eleEliminado = false and  tblh.parPartido.eleEleccion.eveEventoEleccionario.eveId  = :eveId group by tbl.eleId")
	List<EleEleccion> findEleEleccionByEventoAndRutAndAdministradoreGeneral(@Param("eveId") Integer eveId,@Param("rut") String rut);
	
	@Query("SELECT tbl FROM EleEleccion tbl join ParPartido tblp on tblp.eleEleccion.eleId =tbl.eleId WHERE  tblp.parRut = :rut   and tbl.eleEliminado = false and tbl.eveEventoEleccionario.eveId = :eveId")
	List<EleEleccion> findEleEleccionByEventoAndRutAndPartido(@Param("eveId") Integer eveId,@Param("rut") String rut);
}
