package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import cl.servel.gasto.entity.PacPacto;
import cl.servel.gasto.entity.ParPartidoMaestro;

@Repository("pacPactoRepository")
public interface PacPactoRepository extends CrudRepository<PacPacto, Integer>,PacPactoRepositoryCustom {
	@Query("SELECT tbl FROM PacPacto tbl WHERE tbl.pacEliminado = FALSE")
	List<PacPacto> findAllActivePacPacto();
	
	@Query("select pp  from EleEleccion ee join PacPacto pp on ee.eleId = pp.eleEleccion.eleId where ee.tpoEleccion.tpoEleccionId = :idTipoEleccion and ee.eveEventoEleccionario.eveId = :idEvento and ee.tpoEvento.tpoEventoId = :idTipoEvento and ee.eleEliminado = FALSE")
	List<PacPacto> findByEventoAndTipoEventoAndTipoEleccion(@Param("idEvento") Integer idEvento, @Param("idTipoEvento") Integer idTipoEvento ,@Param("idTipoEleccion") Integer idTipoEleccion);
	
	@Query("select tbl  from PacPacto tbl where tbl.pacNombre = :nombre and tbl.eleEleccion.eleId = :eleccionId")
	PacPacto findByNombreEleccion(@Param("nombre") String nombre,@Param("eleccionId") Integer eleccionId);
	
	@Query("select tbl  from PacPacto tbl where tbl.pacCodigoOrigen = :codigoOrigen and tbl.eleEleccion.eleId = :eleccionId")
	PacPacto findByCodigoOrigen(@Param("eleccionId") Integer eleccionId,@Param("codigoOrigen") String codigoOrigen);
	
	@Query("select tbl from PacPacto tbl where tbl.eleEleccion.eleId = :eleccionId")
	List<PacPacto> findByEleccion(@Param("eleccionId") Integer eleccionId);

	@Transactional
	@Modifying
	@Query("DELETE FROM PacPacto WHERE pacId IN(SELECT pacId FROM PacPacto WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
}




