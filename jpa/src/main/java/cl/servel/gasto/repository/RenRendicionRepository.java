package cl.servel.gasto.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DetDetalleRendicion;
import cl.servel.gasto.entity.RenRendicion;

@Repository("renRendicionRepository")
public interface RenRendicionRepository extends CrudRepository<RenRendicion, Integer>, RenRendicionRepositoryCustom{
	
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.canCandidato.canId = :canId")
	List<RenRendicion> findRenRendicionByCandidato(@Param("canId") Integer canId);

	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tpoRendicionId AND tbl.canCandidato.canId = :canId order by tbl.renFecha DESC ")
	List<RenRendicion> findRenRendicionByTpoRendicionOrderByFecha(@Param("tpoRendicionId") Integer tpoRendicionId,@Param("canId") Integer canId);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tpoRendicionId AND tbl.canCandidato.canId = :canId")
	List<RenRendicion> findRenRendicionByTpoRendicion(@Param("tpoRendicionId") Integer tpoRendicionId,@Param("canId") Integer canId);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tpoRendicionId AND tbl.canCandidato.canId = :canId order by tbl.renId DESC")
	List<RenRendicion> findRenRendicionByTpoRendicionOrderbyIdDesc(@Param("tpoRendicionId") Integer tpoRendicionId,@Param("canId") Integer canId);
		
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tpoRendicionId AND tbl.parPartido.parId = :parId")
	List<RenRendicion> findRenRendicionByPartidoTpoRendicion(@Param("tpoRendicionId") Integer tpoRendicionId, @Param("parId") Integer parId);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tpoRendicionId AND tbl.parPartido.parId = :parId order by tbl.renId DESC")
	List<RenRendicion> findRenRendicionByPartidoTpoRendicionOrderbyIdDesc(@Param("tpoRendicionId") Integer tpoRendicionId, @Param("parId") Integer parId);

	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoCodigo = :tpoRendicionCodigo AND tbl.parPartido.parId = :parId order by tbl.renId DESC")
	List<RenRendicion> findRenRendicionPartidoByCodigoEvento(@Param("tpoRendicionCodigo") String tpoRendicionCodigo, @Param("parId") Integer parId);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoCodigo = :tpoRendicionCodigo AND tbl.canCandidato.canId = :canId order by tbl.renId DESC")
	List<RenRendicion> findRenRendicionCandidatoByCodigoEvento(@Param("tpoRendicionCodigo") String tpoRendicionCodigo,@Param("canId") Integer canId);
	
	@Modifying
	@Transactional
	@Query("UPDATE RenRendicion tbl SET tbl.renEstado=:renEstado WHERE tbl.renId = :renId")
	void updateRenRendicionEstadoById(@Param("renEstado") String renEstado,@Param("renId") Integer renId);

	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.parPartido.parId = :partidoId")
	List<RenRendicion> findRenRendicionByPartido(@Param("partidoId") Integer partidoId);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.renEstado = :esado")
	List<RenRendicion> findByEstado(@Param("esado") String estado);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.renEstado = :estado AND tbl.eveEventoEleccionario.eveId = :idEvento")
	List<RenRendicion> findByEstadoAndEvento(@Param("estado") String estado, @Param("idEvento") int idEvento);
	
	@Modifying
	@Transactional
	@Query("UPDATE RenRendicion tbl SET tbl.renFecha=:renFecha WHERE tbl.renId = :renId")
	void updateRenRendicionFechaById(@Param("renFecha") Date renFecha,@Param("renId") Integer renId);
	
	@Query("SELECT r FROM RenRendicion r WHERE r.eveEventoEleccionario.eveId = :idEvento AND r.eleEleccion.tpoEleccion.tpoEleccionId = :idTipoEleccion AND r.eleEleccion.tpoEvento.tpoEventoId = :idTipoEvento AND (r.renNumeroDocumento IS NOT NULL AND r.renNumeroDocumento > 0) AND (r.tpoRendicion.tpoCodigo = 'F87' OR r.tpoRendicion.tpoCodigo = 'F88')")
	List<RenRendicion> getRendicionesConRespuestasManualesObservaciones(@Param("idEvento") int idEvento, @Param("idTipoEleccion") int idTipoEleccion, @Param("idTipoEvento") int idTipoEvento);
	
	@Query("SELECT r FROM RenRendicion r WHERE r.eveEventoEleccionario.eveId = :eveId AND r.tpoRendicion.tpoCodigo = :codigoRendicion AND r.renEstado = :estadoRendicion")
	List<RenRendicion> getByEventoAndTipoAndEstado(@Param("eveId") int eveId, @Param("codigoRendicion") String codigoRendicion, @Param("estadoRendicion") String estadoRendicion);

	@Query("SELECT r FROM RenRendicion r inner join DetDetalleRendicion det on r.renId=det.renRendicion.renId  WHERE r.canCandidato.canId = :candidatoId and  r.tpoRendicion.tpoCodigo = :tipoRendicionCodigo AND det.tpoSeccionRendicion.tpoNombreSeccion = :tipoSeccionRendicionCodigo")
	List<RenRendicion> findRenRendicionByTpoRendicionAndTpoSeccionCandidato(@Param("candidatoId") Integer candidatoId,@Param("tipoRendicionCodigo")  String tipoRendicionCodigo,
			@Param("tipoSeccionRendicionCodigo")  String tipoSeccionRendicionCodigo);
	@Query("SELECT r FROM RenRendicion r inner join DetDetalleRendicion det on r.renId=det.renRendicion.renId  WHERE r.parPartido.parId = :partidoId and  r.tpoRendicion.tpoCodigo = :tipoRendicionCodigo AND det.tpoSeccionRendicion.tpoNombreSeccion = :tipoSeccionRendicionCodigo")
	List<RenRendicion> findRenRendicionByTpoRendicionAndTpoSeccionPartido(@Param("partidoId") Integer partidoId,@Param("tipoRendicionCodigo")  String tipoRendicionCodigo,
			@Param("tipoSeccionRendicionCodigo")  String tipoSeccionRendicionCodigo);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE tbl.tpoRendicion.tpoCodigo = :tpoRendicionCodigo AND tbl.parPartido.parCodigoOrigen = :parCodigoOrigen order by tbl.renId DESC")
	List<RenRendicion> findRenRendicionCodigoPartidoByCodigoEvento(@Param("tpoRendicionCodigo") String tpoRendicionCodigo, @Param("parCodigoOrigen") String parCodigoOrigen);
	
	@Query("SELECT tbl FROM RenRendicion tbl WHERE  tbl.eveEventoEleccionario.eveId = :eventoId AND tbl.eleEleccion.tpoEvento.tpoEventoId = :tipoEventoId AND "
			+ "tbl.tpoRendicion.tpoCodigo = :tipoRendicionCodigo and tbl.parPartido.parId is not null ")
	List<RenRendicion> findRendicionByEventoTipoEventoCodigo(@Param("eventoId") Integer eventoId,@Param("tipoEventoId") Integer tipoEventoId, @Param("tipoRendicionCodigo") String tipoRendicionCodigo);
	
	@Query("SELECT COUNT(tbl) FROM RenRendicion tbl WHERE  tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.eleEleccion.tpoEvento.tpoEventoId = :idTipoEvento ")
	Integer countRendicion(@Param("idEvento") Integer idEvento,@Param("idTipoEvento") Integer idTipoEvento);
}
