package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DtaDefinicionTarea;

public interface DtaDefinicionTareaRepository extends CrudRepository<DtaDefinicionTarea, Integer>,DtaDefinicionTareaRepositoryCustom {

	@Query("select tbl from DtaDefinicionTarea tbl where tbl.dflDefinicionFlujo.dflId = :definicionFlujoId")
	public List<DtaDefinicionTarea> getAllByDefinicionFlujo(@Param("definicionFlujoId") Integer definicionFlujoId);
	
	@Query("select tbl from DtaDefinicionTarea tbl where tbl.tpoEstadoTarea.tpoCodigoEstado = :descTipoTarea")
	public Optional<DtaDefinicionTarea> getByDescripcionTipoTarea(@Param("descTipoTarea") String descTipoTarea);
	
	@Query("select tbl from DtaDefinicionTarea tbl where tbl.dtaCodigoOrigen = :codigoOrigen AND tbl.dflDefinicionFlujo.eveEventoEleccionario.eveId = :eveId")
	public Optional<DtaDefinicionTarea> getByCodigoOrigen(@Param("codigoOrigen") String codigoOrigen, @Param("eveId") int eveId);
	
	@Query("select tbl from DtaDefinicionTarea tbl inner join RlcPerfilDefinicionTarea rlc on tbl.dtaId=rlc.dtaDefinicionTarea.dtaId where rlc.perPerfil.perId=:idPerfil")
	public List<DtaDefinicionTarea> getAllByPerfil(@Param("idPerfil") Integer idPerfil);
	
	@Query("select tbl from DtaDefinicionTarea tbl where tbl.dtaCodigoOrigen in :codigoOrigenIds")
	public List<DtaDefinicionTarea> getByCodigoOrigenIds(@Param("codigoOrigenIds") List<String> codigoOrigenIds);

}
