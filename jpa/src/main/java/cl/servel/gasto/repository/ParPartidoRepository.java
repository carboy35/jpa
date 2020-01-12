package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.repository.custom.ParPartidoRepositoryCustom;

@Repository("parPartidoRepository")
public interface ParPartidoRepository extends CrudRepository<ParPartido, Integer>,ParPartidoRepositoryCustom {
	
	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.parEliminado = FALSE")
	List<ParPartido> findAllActiveParPartido();

	@Query("SELECT tbl.pacId FROM PacPacto tbl WHERE tbl.pacCodigoOrigen = :InCodPacOrigen")
	int FindBycodPacOrigen(@Param("InCodPacOrigen") int InCodPacOrigen);

	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.parEliminado = FALSE AND tbl.eleEleccion.eleId = :eleccionId")
	List<ParPartido> findActiveByEleccion(@Param("eleccionId") int eleccionId);

	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.pacPacto.pacId = :pactoId and tbl.parEliminado = FALSE")
	List<ParPartido> findPartidosPorPacto(@Param("pactoId") Integer pactoId);

	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.parEliminado = FALSE AND tbl.eleEleccion.eveEventoEleccionario.eveId = :eventoId")
	List<ParPartido> findActiveByEvento(@Param("eventoId") int eventoId);
	
	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.pacPacto.pacId = :pactoId and tbl.eleEleccion.eleId = :eleccionId")
	List<ParPartido> findByPacto(@Param("pactoId") int pactoId, @Param("eleccionId") int eleccionId);
	
	@Query("SELECT p1 FROM ParPartido p1 WHERE p1.eleEleccion.eveEventoEleccionario.eveId = :idEventoActual AND p1.parCodigoOrigen NOT IN ( SELECT p2.parCodigoOrigen FROM ParPartido p2 WHERE p2.eleEleccion.eveEventoEleccionario.eveId = :idEventoAnterior )")
	List<ParPartido> getPartidosNuevosEntreEventos(@Param("idEventoAnterior") int idEventoAnterior, @Param("idEventoActual") int idEventoActual);
	
	@Query("Select tbl FROM ParPartido tbl WHERE tbl.eleEleccion.eveEventoEleccionario.eveId = :idEventoActual AND tbl.pacPacto.pacId = :idPacto AND tbl.eleEleccion.tpoEleccion.tpoEleccionId = :idTipoEleccion")
	List<ParPartido> getPartidosXEventoPactoTipoEleccion(@Param("idEventoActual") int idEventoActual, @Param("idPacto") int idPacto, @Param("idTipoEleccion") int idTipoEleccion);
	
	@Query("Select tbl FROM ParPartido tbl WHERE tbl.eleEleccion.eveEventoEleccionario.eveId = :eventoId AND tbl.parRut = :rutPartido")
	List<ParPartido> getPartidosByEventoAndRut(@Param("eventoId") int eventoId, @Param("rutPartido") String rutPartido);
	
	@Query("Select tbl FROM ParPartido tbl WHERE tbl.eleEleccion.eveEventoEleccionario.eveId = :eventoId AND tbl.eleEleccion.tpoEvento.tpoEventoId = :tpoEventoId AND tbl.parRut = :rutPartido")
	List<ParPartido> getPartidosByEventoAndTipoEventoAndRut(@Param("eventoId") int eventoId, @Param("tpoEventoId") int tpoEventoId, @Param("rutPartido") String rutPartido);
	
	@Query("Select tbl FROM ParPartido tbl WHERE tbl.eleEleccion.eleId = :eleccionId AND tbl.parRut = :rutPartido")
	List<ParPartido> getPartidosByEleccionAndRut(@Param("eleccionId") int eleccionId, @Param("rutPartido") String rutPartido);
	
	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.eleEleccion.eleId = :eleccionId")
	List<ParPartido> findByEleccion(@Param("eleccionId") int eleccionId);
	
	@Query("SELECT tbl.parPartido FROM RlcSubpactoPartido tbl WHERE tbl.spaPacto.spaId = :subPactoId AND tbl.parPartido.parEliminado = false")
	List<ParPartido> findPartidosPorSubPacto(@Param("subPactoId") Integer subPactoId);
	
	@Query("SELECT tbl FROM ParPartido tbl WHERE tbl.eleEleccion.eveEventoEleccionario.eveId = :eventoId AND tbl.eleEleccion.tpoEvento.tpoEventoId = :tipoEventoId")
	List<ParPartido> findByEventoTipoEvento(@Param("eventoId") int eventoId,@Param("tipoEventoId") int tipoEventoId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ParPartido WHERE parId IN(SELECT parId FROM ParPartido WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
}
