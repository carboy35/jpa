package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.CanCandidato;

@Repository("canCandidatoRepository")
public interface CanCandidatoRepository extends CrudRepository<CanCandidato, Integer>, CanCandidatoRepositoryCustom {

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.canId = :canId and tbl.canHabilitado = TRUE")
	CanCandidato findByIdActive(@Param("canId") Integer canId);

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.canHabilitado = TRUE ")
	List<CanCandidato> findAllActiveCanCandidato();

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.pacPacto.pacId  =  :pacId and tbl.canHabilitado = TRUE ")
	List<CanCandidato> findCanCandidatoByIdPacto(@Param("pacId") Integer pacId);

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.selSubEleccion.selId  =  :subEleccionId and  tbl.canHabilitado = TRUE ")
	List<CanCandidato> findCanCandidatoByIdSelSubEleccion(@Param("subEleccionId") Integer subEleccionId);

	@Query("SELECT tb1 FROM CanCandidato tb1 join tb1.selSubEleccion tb2  WHERE tb2.eleEleccion.eleId  =  :eleId and tb2.nivNivel.nivId = :nivId and tb1.canHabilitado = TRUE ")
	List<CanCandidato> findCanCandidatoByIdEleccionAndIdNivel(@Param("eleId") Integer eleId,
			@Param("nivId") Integer nivId);

	@Query("SELECT tb1 FROM CanCandidato tb1 join tb1.selSubEleccion tb2  WHERE tb1.canRut = :canRut and tb2.eleEleccion.eleId  =  :eleId and tb1.eveEventoEleccionario.eveId = :eveId and tb1.canHabilitado = TRUE ")
	List<CanCandidato> findCanCandidatoByIdEleccionAndIdEventoAndRut(@Param("eleId") Integer eleId,
			@Param("eveId") Integer eveId, @Param("canRut") Integer canRut);

	@Query("SELECT tb1 FROM CanCandidato tb1 join tb1.selSubEleccion tb2  WHERE tb2.eleEleccion.eleId  =  :eleId and tb1.eveEventoEleccionario.eveId = :eveId and tb1.canHabilitado = TRUE ")
	List<CanCandidato> findCanCandidatoByIdEleccionAndIdEvento(@Param("eleId") Integer eleId,
			@Param("eveId") Integer eveId);

	@Query("SELECT tb1 FROM CanCandidato tb1 join tb1.selSubEleccion tb2  WHERE tb2.eleEleccion.tpoEleccion.tpoEleccionId = :tpoEleccion and tb1.eveEventoEleccionario.eveId = :eveId and tb1.canHabilitado = TRUE ")
	List<CanCandidato> findCanCandidatoByIdEventoAndTpoEleccion(@Param("tpoEleccion") Integer tpoEleccion,
			@Param("eveId") Integer eveId);

	@Query("SELECT candidato FROM CanCandidato candidato WHERE candidato.tpoCandidato.tpoCandidatoId = 4 AND candidato.selSubEleccion.eleEleccion.eleId = :idEleccion AND candidato.selSubEleccion.nivNivel.nivId = :idNivel and candidato.canHabilitado = TRUE ")
	List<CanCandidato> findCandidatosIndependientes(@Param("idEleccion") Integer idEleccion,
			@Param("idNivel") Integer idNivel);

	@Query("SELECT tb1 FROM CanCandidato tb1 join tb1.selSubEleccion tb2 WHERE tb2.eleEleccion.eleId  =  :idEleccion and tb2.nivNivel.nivId = :idNivel "
			+ "and tb1.tpoCandidato.tpoCandidatoId = 3 and tb1.canHabilitado = TRUE ")
	List<CanCandidato> findCandidatosIndependientesConPactoSubPacto(@Param("idEleccion") Integer idEleccion,
			@Param("idNivel") Integer idNivel);

	@Query("SELECT count(*) FROM CanCandidato candidato WHERE candidato.selSubEleccion.eleEleccion.eleId = :idEleccion and candidato.canHabilitado = TRUE")
	int totalCandidatosEleccion(@Param("idEleccion") Integer idEleccion);

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId AND tbl.canRut = :rut and tbl.canHabilitado = TRUE ")
	List<CanCandidato> findCandidatosByEventoAndRut(@Param("eventoId") int eventoId, @Param("rut") int rut);

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId AND tbl.canRut = :rut")
	List<CanCandidato> getCandidatosHabilitadoByEventoAndRut(@Param("eventoId") int eventoId, @Param("rut") int rut);

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId AND tbl.selSubEleccion.eleEleccion.tpoEvento.tpoEventoId = :tpoEventoId AND tbl.canRut = :rut and tbl.canHabilitado = TRUE ")
	List<CanCandidato> findCandidatosByEventoAndTpoEventoAndRut(@Param("eventoId") int eventoId,
			@Param("tpoEventoId") int tpoEventoId, @Param("rut") int rut);

	@Query("SELECT tbl FROM CanCandidato tbl WHERE tbl.selSubEleccion.eleEleccion.eleId = :eleccionId AND tbl.canRut = :rut AND tbl.canRutDv = :dv and tbl.canHabilitado = TRUE ")
	CanCandidato findCandidatosByEleccionAndRut(@Param("eleccionId") int eleccionId, @Param("rut") int rut,
			@Param("dv") String dv);

	@Query("SELECT tblc FROM CanCandidato tblc join HisHistorico tblh on  tblc.parPartido.parId = tblh.parPartido.parId where tblh.admAdministradorElectoral.admRut = :rut and tblc.selSubEleccion.eleEleccion.eleId = :eleId and tblh.hisFechaTermino is null ")
	List<CanCandidato> findCandidatoByAdministradorEleccion(@Param("rut") String rut, @Param("eleId") Integer eleId);
	
	@Query("SELECT tblc FROM CanCandidato tblc WHERE tblc.canId= :candidatoId and tblc.selSubEleccion.eleEleccion.eleId = :eleId")
	CanCandidato findCandidatoByCandidatoIdEleccion(@Param("candidatoId") Integer candidatoId, @Param("eleId") Integer eleId);
	
	@Query("SELECT c FROM CanCandidato c WHERE c.selSubEleccion.eleEleccion.eleId = :eleId AND c.canSexo = :sexo")
	List<CanCandidato> getByEleccionAndSexo(@Param("eleId") int eleId, @Param("sexo") String sexo);
	
	@Query("SELECT c FROM CanCandidato c WHERE c.selSubEleccion.eleEleccion.eleId = :eleId AND c.canSexo = :sexo and c.canId NOT IN (SELECT dr.renRendicion.canCandidato.canId FROM DetDetalleRendicion dr WHERE dr.tpoSeccionRendicion.tpoNombreSeccion = 'PAGO_MUJER' AND dr.renRendicion.canCandidato.canId IS NOT NULL)")
	List<CanCandidato> getByEleccionAndSexoSinResolucionPagoMujer(@Param("eleId") int eleId, @Param("sexo") String sexo);
	
	@Query("SELECT c FROM CanCandidato c WHERE c.eveEventoEleccionario.eveId = :eveId AND c.canElecto = true AND c.canSexo = 'MUJER' AND "
			+ "(c.selSubEleccion.eleEleccion.tpoEleccion.tpoCodigoOrigen = '5' OR c.selSubEleccion.eleEleccion.tpoEleccion.tpoCodigoOrigen = '6') AND "
			+ "(c.tpoCandidato.tpoCandidatoId = 1 OR c.tpoCandidato.tpoCandidatoId = 2) AND c.canId NOT IN ( SELECT r.canCandidato.canId FROM RenRendicion r JOIN DetDetalleRendicion dr ON dr.renRendicion.renId = r.renId WHERE r.tpoRendicion.tpoCodigo = 'PAGOS' AND dr.tpoSeccionRendicion.tpoCodigoSeccion = 'PAGO_MUJER' AND r.eveEventoEleccionario.eveId = :eveId )") 
	List<CanCandidato> getCandidatasPorEnviarPagoMujer(@Param("eveId") int eveId);
	
	@Query("SELECT c FROM CanCandidato c WHERE c.eveEventoEleccionario.eveId = :eveId AND c.canElecto = true AND c.canSexo = 'MUJER' AND "
			+ "(c.selSubEleccion.eleEleccion.tpoEleccion.tpoCodigoOrigen = '5' OR c.selSubEleccion.eleEleccion.tpoEleccion.tpoCodigoOrigen = '6') AND "
			+ "(c.tpoCandidato.tpoCandidatoId = 1 OR c.tpoCandidato.tpoCandidatoId = 2) AND c.canId IN ( SELECT r.canCandidato.canId FROM RenRendicion r JOIN DetDetalleRendicion dr ON dr.renRendicion.renId = r.renId WHERE r.tpoRendicion.tpoCodigo = 'PAGOS' AND dr.tpoSeccionRendicion.tpoCodigoSeccion = 'pagos' AND r.eveEventoEleccionario.eveId = :eveId )") 
	List<CanCandidato> getCandidatasPorEnviarPagoMujerConResolucion(@Param("eveId") int eveId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM CanCandidato WHERE canId IN (SELECT canId FROM CanCandidato WHERE selSubEleccion.eveEventoEleccionario.eveId=:idEvento AND selSubEleccion.eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipo(@Param("idEvento")Integer idEvento,@Param("idTipoEvento") Integer idTipoEvento);
	
}
