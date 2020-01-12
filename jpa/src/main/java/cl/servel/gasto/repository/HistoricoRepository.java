package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.HisHistorico;

@Repository("historicoRepository")
public interface HistoricoRepository extends CrudRepository<HisHistorico, Integer> {
    
    @Query("SELECT tbl FROM HisHistorico tbl WHERE tbl.hisEliminado = FALSE ")
    List<HisHistorico> findAllActive();
	
    @Query("SELECT tbl FROM HisHistorico tbl WHERE tbl.hisEliminado = FALSE AND tbl.hisEstado = 'ok' and tbl.canCandidato.canId = :candidatoId")
    Optional<HisHistorico> findByCandidatoAndActiveAndEstado(@Param("candidatoId") Integer candidatoId);
    
    @Query("SELECT tbl FROM HisHistorico tbl WHERE tbl.hisEliminado = FALSE AND tbl.hisEstado = 'ok' and tbl.parPartido.parId = :parId")
    Optional<HisHistorico> findByPartidoAndActiveAndEstado(@Param("parId") Integer parId);
    
    @Query("SELECT tbl FROM HisHistorico tbl WHERE tbl.parPartido.parId = :parId ORDER BY tbl.hisCreated DESC")
    List<HisHistorico> findAllByPartido(@Param("parId") Integer parId);
    
    @Query("SELECT tbl FROM HisHistorico tbl WHERE tbl.canCandidato.canId = :candidatoId ORDER BY tbl.hisCreated DESC")
    List<HisHistorico> findAllByCandidato(@Param("candidatoId") Integer candidatoId);
}
