package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.AdmAdministradorElectoral;

@Repository("administradorElectoralRepository")
public interface AdministradorElectoralRepository extends CrudRepository<AdmAdministradorElectoral,Integer>,AdministradorElectoralRepositoryCustom {
		
	@Query("SELECT ae FROM AdmAdministradorElectoral ae WHERE ae.admEliminado = FALSE ")
	List<AdmAdministradorElectoral> findAllActiveAdministrador();
	
	@Query("SELECT tbl FROM AdmAdministradorElectoral tbl  WHERE tbl.eveEventoEleccionario.eveId = :eveEventoId")
	List<AdmAdministradorElectoral> findAdmAdministradorElectoralByIdEvento(@Param("eveEventoId") Integer eveEventoId);
	
	@Query("SELECT tbl FROM AdmAdministradorElectoral tbl  WHERE tbl.parPartido IS NULL and tbl.admEstado = 'true' and tbl.admEliminado =  FALSE")
	List<AdmAdministradorElectoral> findAdministradoresSinPartido();
	
	@Query("SELECT tbl FROM AdmAdministradorElectoral tbl  WHERE tbl.parPartido.parId = :partidoId and tbl.admEstado = 'true' and tbl.admEliminado =  FALSE")
	Optional<AdmAdministradorElectoral> findAdministradorByPartido(@Param("partidoId") Integer partidoId);
	
	@Query("SELECT tbl FROM AdmAdministradorElectoral tbl  WHERE tbl.eveEventoEleccionario.eveId = :eventoId and tbl.admEstado = 'true' and tbl.admEliminado =  FALSE")
	List<AdmAdministradorElectoral> findAdministradorByEvento(@Param("eventoId") Integer eventoId);

	@Query("SELECT tbla FROM AdmAdministradorElectoral tbla  join HisHistorico tblh on tbla.admId = tblh.admAdministradorElectoral.admId WHERE  tblh.canCandidato.selSubEleccion.eleEleccion.eleId = :eleId and  tbla.admRut = :rut and  tblh.hisFechaTermino is null")
	 Optional<AdmAdministradorElectoral>  findAdministradorByEleccionAndRutElectoral(@Param("eleId") Integer eleId, @Param("rut")  String rut);
	
	@Query("SELECT tbla FROM AdmAdministradorElectoral tbla   join HisHistorico tblh on tbla.admId = tblh.admAdministradorElectoral.admId  WHERE  tblh.parPartido.eleEleccion.eleId = :eleId and   tbla.admRut = :rut and  tblh.hisFechaTermino is null")
	Optional<AdmAdministradorElectoral>  findAdministradorByEleccionAndRutGeneral(@Param("eleId") Integer eleId, @Param("rut")  String rut);
 
	@Query("SELECT tbl FROM AdmAdministradorElectoral tbl  WHERE tbl.eveEventoEleccionario.eveId = :eventoId and tbl.admEstado = 'true' and tbl.admEliminado =  FALSE and  tbl.admRut = :rut")
	Optional<List<AdmAdministradorElectoral>> findAdministradorByEventoAndRut(@Param("eventoId") Integer eventoId,@Param("rut") String rut);
}
