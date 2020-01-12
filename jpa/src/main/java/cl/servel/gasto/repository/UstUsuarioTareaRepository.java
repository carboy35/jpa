package cl.servel.gasto.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.UstUsuarioTarea;


public interface UstUsuarioTareaRepository extends CrudRepository<UstUsuarioTarea, Integer>,UstUsuarioTareaRepositoryCustom {
	
	@Query("SELECT tbl FROM UstUsuarioTarea tbl WHERE tbl.gutGrupoUsuarioTarea.gutId = :gutId and tbl.ustActivo = true")
	List<UstUsuarioTarea> getUsuariosTareaByGutId(@Param("gutId") Integer gutId);
	
	@Query("SELECT tbl FROM UstUsuarioTarea tbl WHERE tbl.gutGrupoUsuarioTarea.gutId = :gutId")
	List<UstUsuarioTarea> getAllByGutId(@Param("gutId") Integer gutId);
	
	@Query("SELECT tbl FROM UstUsuarioTarea tbl join GutGrupoUsuarioTarea tbl3 on tbl3.gutId = tbl.gutGrupoUsuarioTarea.gutId join AsgAsignacionCargaTarea tbl2 on tbl2.gutGrupoUsuarioTarea.gutId = tbl3.gutId WHERE tbl.usuUsuarios.usuId = :usuarioId and tbl.gutGrupoUsuarioTarea.celCelula.celId=:celulaId and tbl2.dtaDefinicionTarea.dtaId = :dtaId")
	Optional<UstUsuarioTarea> getByUsuarioAndCelulaAndDefinicionTarea(@Param("usuarioId") Integer usuarioId,@Param("celulaId") Integer celulaId, @Param("dtaId") int dtaId);

	@Query("SELECT tbl FROM UstUsuarioTarea tbl WHERE tbl.gutGrupoUsuarioTarea.celCelula.celId = :celulaId")
	List<UstUsuarioTarea> getByCelula(@Param("celulaId") Integer celulaId);
	
	@Query("SELECT ust FROM DtaDefinicionTarea dta JOIN RlcPerfilDefinicionTarea pdt ON pdt.dtaDefinicionTarea.dtaId = dta.dtaId JOIN IntInstanciaTarea int ON int.dtaDefinicionTarea.dtaId = dta.dtaId JOIN UstUsuarioTarea ust ON ust.ustId = int.ustUsuarioTarea.ustId WHERE int.infInstanciaFlujo.infId = :infId AND pdt.perPerfil.perId in (SELECT pdt2.perPerfil.perId FROM RlcPerfilDefinicionTarea pdt2 WHERE pdt2.dtaDefinicionTarea.dtaId = :dtaId) AND ust.ustActivo = true ORDER BY int.intId DESC ")
	LinkedList<UstUsuarioTarea> getUltimoRevisorByDefinicionTarea(@Param("dtaId") Integer dtaId, @Param("infId") int infId);
	
	@Query("SELECT tbl FROM UstUsuarioTarea tbl WHERE tbl.gutGrupoUsuarioTarea.gutId = (SELECT tbl2.gutGrupoUsuarioTarea.gutId FROM UstUsuarioTarea tbl2 WHERE tbl2.ustId = :ustId)")
	List<UstUsuarioTarea> getUsuariosGrupoByUsuarioTarea(@Param("ustId") int ustId);
	
	@Query("SELECT tbl FROM UstUsuarioTarea tbl WHERE tbl.usuUsuarios.usuId = :usuId AND tbl.ustActivo = true AND tbl.gutGrupoUsuarioTarea.celCelula.eveEventoEleccionario.eveId = :eveId")
	List<UstUsuarioTarea> getByUsuarioAndEvento(@Param("usuId") int usuId, @Param("eveId") int eveId);
}
