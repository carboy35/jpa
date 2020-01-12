package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.ManMandato;

@Repository("manMandatoRepository")
public interface ManMandatoRepository extends CrudRepository<ManMandato, Integer>, ManMandatoRepositoryCustom {

	@Query("SELECT tbl FROM ManMandato tbl WHERE tbl.manId = :manId and tbl.manEliminado = false")
	ManMandato findByIdActive(@Param("manId") Integer manId);

	@Query("SELECT tbl FROM ManMandato tbl WHERE tbl.canCandidato.canId = :canId and tbl.manEliminado = false")
	List<ManMandato> findActiveByCanId(@Param("canId") Integer canId);

	@Query("SELECT tbl FROM ManMandato tbl WHERE tbl.parPartido.parId = :parId and tbl.manEliminado = false")
	List<ManMandato> findActiveByParId(@Param("parId") Integer parId);

	@Query("SELECT tbl FROM ManMandato tbl WHERE tbl.canCandidato.canId IN :listCandidato OR tbl.parPartido.parId IN :listPartido ")
	List<ManMandato> findByListCandidatoPartido(@Param("listCandidato") Integer[] listCandidato, @Param("listPartido") Integer[] listPartido);

	@Query("SELECT tbl FROM ManMandato tbl WHERE "
			+ "(tbl.canCandidato.eveEventoEleccionario.eveId = :eventoId "
			+ "AND tbl.canCandidato.selSubEleccion.eleEleccion.tpoEvento.tpoEventoId = :tpoEventoId "
			+ "AND tbl.canCandidato.selSubEleccion.eleEleccion.tpoEleccion.tpoEleccionId = :tpoEleccionId) "
			+ "")
	List<ManMandato> findByEventoTpoEventoTpoEleccionCandidato(@Param("eventoId") Integer eventoId, @Param("tpoEventoId") Integer tpoEventoId, @Param("tpoEleccionId") Integer tpoEleccionId);

	@Query("SELECT tbl FROM ManMandato tbl WHERE "
			+ "(tbl.parPartido.eleEleccion.eveEventoEleccionario.eveId = :eventoId "
			+ "AND tbl.parPartido.eleEleccion.tpoEvento.tpoEventoId = :tpoEventoId "
			+ "AND tbl.parPartido.eleEleccion.tpoEleccion.tpoEleccionId = :tpoEleccionId) "
			+ "")
	List<ManMandato> findByEventoTpoEventoTpoEleccionPartido(@Param("eventoId") Integer eventoId, @Param("tpoEventoId") Integer tpoEventoId, @Param("tpoEleccionId") Integer tpoEleccionId);
	
	@Query("SELECT tbl FROM ManMandato tbl WHERE tbl.canCandidato.canId = :canId and tbl.manEliminado = false and tbl.manNumeroDocumento=:nrodocumento")
	List<ManMandato> findActiveByCanIdAndDocumento(@Param("canId") Integer canId,@Param("nrodocumento") String nrodocumento);

	@Query("SELECT tbl FROM ManMandato tbl WHERE tbl.parPartido.parId = :parId and tbl.manEliminado = false and tbl.manNumeroDocumento=:nrodocumento")
	List<ManMandato> findActiveByParIdAndDocumento(@Param("parId") Integer parId,@Param("nrodocumento") String nrodocumento);
}
