package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import cl.servel.gasto.entity.RlcPartidoNivel;

@Repository("rlcPartidoNivelRepository")
public interface RlcPartidoNivelRepository extends CrudRepository<RlcPartidoNivel, Integer>{

	@Query("select tbl  from RlcPartidoNivel tbl where tbl.nivNivel.eveEventoEleccionario.eveId = :idEvento")
	List<RlcPartidoNivel> findByEvento(@Param("idEvento") Integer idEvento);
	
	@Query("select tbl  from RlcPartidoNivel tbl where tbl.nivNivel.eveEventoEleccionario.eveId = :idEvento AND tbl.nivNivel.nivId = :idNivel and tbl.rlcPartidoNivelFechaVigencia is not null")
	List<RlcPartidoNivel> findByEventoAndNivel(@Param("idEvento") Integer idEvento, @Param("idNivel") Integer idNivel);
	
	@Query("select tbl  from RlcPartidoNivel tbl where tbl.nivNivel.eveEventoEleccionario.eveId = :idEvento AND tbl.parPartidoMaestro.id = :idPartidoMaestro and tbl.rlcPartidoNivelFechaVigencia is not null")
	List<RlcPartidoNivel> findByEventoAndPartidoMaestro(@Param("idEvento") Integer idEvento, @Param("idPartidoMaestro") Integer idPartidoMaestro);

	@Transactional
	@Modifying
	@Query("DELETE FROM RlcPartidoNivel WHERE rlcId IN (SELECT rlc.rlcId FROM RlcPartidoNivel rlc INNER JOIN SelSubEleccion sel ON sel.nivNivel.nivId=rlc.nivNivel.nivId WHERE sel.eveEventoEleccionario.eveId=:idEvento AND sel.eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento ) ")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
}
