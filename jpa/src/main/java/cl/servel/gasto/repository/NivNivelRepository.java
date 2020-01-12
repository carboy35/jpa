package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import cl.servel.gasto.entity.NivNivel;

@Repository("nivNivelRepository")
public interface NivNivelRepository extends CrudRepository <NivNivel, Integer>, NivNivelRepositoryCustom {
	
	@Query("SELECT tbl FROM NivNivel tbl WHERE tbl.nivEliminado = FALSE")
	List<NivNivel> findAllActiveNivNivel();
	
	//@Query("SELECT tbl FROM NivNivel tbl WHERE tbl.nivEliminado = FALSE")
	@Query("SELECT tbl.nivNivel FROM SelSubEleccion tbl WHERE tbl.eleEleccion.eleId = :eleEleccionId")
	List<NivNivel> findAllActiveNivNivelByIdEleccion(@Param("eleEleccionId") Integer idEleccion);
	
	@Query("SELECT tbl FROM NivNivel tbl WHERE tbl.nivEliminado = FALSE AND tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.tpoNivel.tpoNivCodigo = :tipoNivel")
	List<NivNivel> findByEventoAndTipoNivel(@Param("idEvento") Integer idEvento, @Param("tipoNivel") String tipoNivel);
	
	@Query("SELECT tbl FROM NivNivel tbl WHERE tbl.nivEliminado = FALSE AND tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.tpoNivel.tpoNivCodigo in ('MREG','MCOM','MDIS','MCSE','MPRO','MCPR')")
	List<NivNivel> findByEventoAndTipoNivelMaestros(@Param("idEvento") Integer idEvento);
	
	@Query("SELECT tbl FROM NivNivel tbl WHERE tbl.nivEliminado = FALSE AND tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.tpoNivel.tpoNivCodigo = :tipoNivel and tbl.codigoOrigen = :codigoOrigen")
	NivNivel findByEventoAndTipoNivelAndCodOrigen(@Param("idEvento") Integer idEvento, @Param("tipoNivel") String tipoNivel,@Param("codigoOrigen")String codigoOrigen);

	@Transactional
	@Modifying
	@Query("DELETE FROM NivNivel WHERE nivId IN (SELECT niv.nivId FROM NivNivel niv  INNER JOIN SelSubEleccion sel ON sel.nivNivel.nivId=niv.nivId WHERE sel.eveEventoEleccionario.eveId=:idEvento AND sel.eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento  ) ")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
		
}
