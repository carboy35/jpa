package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.servel.gasto.entity.RlcRelNivel;

@Repository("rlcRelNivelRepository")
public interface RlcRelNivelRepository extends CrudRepository<RlcRelNivel, Integer>{


	@Query("select tbl  from RlcRelNivel tbl where tbl.eveEventoEleccionario.eveId = :idEvento")
	List<RlcRelNivel> findByEvento(@Param("idEvento") Integer idEvento);
	
	@Query("select tbl  from RlcRelNivel tbl where tbl.nivNivelByNivIdHijo.nivId = :idNivelHijo AND tbl.tpoNivelByTpoNivCodigoPadre.tpoNivCodigo = :tipoNivelPadre")
	RlcRelNivel findByNivelHijoAndTipoNivelPadre(@Param("idNivelHijo") Integer idNivelHijo, @Param("tipoNivelPadre") String tipoNivelPadre);
	
	@Query("select tbl  from RlcRelNivel tbl where tbl.tpoNivelByTpoNivCodigoHijo.tpoNivCodigo = :tipoNivelHijo AND tbl.tpoNivelByTpoNivCodigoPadre.tpoNivCodigo = :tipoNivelPadre AND tbl.nivNivelByNivIdPadre.nivId = :idNivelPadre")
	List<RlcRelNivel> findByHijosNivelPadre(@Param("tipoNivelHijo") String tipoNivelHijo, @Param("tipoNivelPadre") String tipoNivelPadre,@Param("idNivelPadre") Integer idNivelPadre);
	
	@Query("select tbl  from RlcRelNivel tbl where tbl.tpoNivelByTpoNivCodigoHijo.tpoNivCodigo = :tipoNivelHijo AND tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.nivNivelByNivIdHijo.nivId = :idNivelHijo")
	RlcRelNivel findPadreByEventoAndTipoAndNivelHijo(@Param("tipoNivelHijo") String tipoNivelHijo, @Param("idEvento") int idEvento, @Param("idNivelHijo") Integer idNivelHijo);

	@Transactional
	@Modifying
	@Query("DELETE FROM RlcRelNivel WHERE rlcId IN (SELECT rlc.rlcId FROM RlcRelNivel rlc INNER JOIN NivNivel niv ON (rlc.nivNivelByNivIdPadre.nivId=niv.nivId OR rlc.nivNivelByNivIdHijo.nivId=niv.nivId) INNER JOIN SelSubEleccion sel ON sel.nivNivel.nivId=niv.nivId WHERE sel.eveEventoEleccionario.eveId=:idEvento AND sel.eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento ) ")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
}
