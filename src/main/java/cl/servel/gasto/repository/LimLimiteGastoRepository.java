package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.LimLimiteGasto;

@Repository("limLimiteGastoRepository")
public interface LimLimiteGastoRepository extends CrudRepository<LimLimiteGasto, Integer>, LimLimiteGastoRepositoryCustom{
	
	
	@Query("SELECT tbl FROM LimLimiteGasto tbl  WHERE tbl.eleEleccion.eleId = :idEleccion AND tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.limEsLimiteGastoPartido = :esLimiteGastoPartido")
	List<LimLimiteGasto> findByIdEleccionAndIdEvento(@Param("idEleccion") Integer idEleccion,@Param("idEvento") Integer idEvento,@Param("esLimiteGastoPartido") Boolean esLimiteGasto);
	
	@Query("SELECT tbl FROM LimLimiteGasto tbl  WHERE tbl.eleEleccion.eleId = :idEleccion AND tbl.limEsLimiteGastoPartido = :esLimiteGastoPartido")
	List<LimLimiteGasto> findByIdEleccion(@Param("idEleccion") Integer idEleccion,@Param("esLimiteGastoPartido") Boolean esLimiteGasto);
	
	@Query("SELECT tbl FROM LimLimiteGasto tbl  WHERE tbl.eleEleccion.eleId = :idEleccion AND tbl.eveEventoEleccionario.eveId = :idEvento AND tbl.limEsLimiteGastoPartido = :esLimiteGastoPartido AND tbl.parPartido.parCodigoOrigen = :parCodigoOrigen")
	List<LimLimiteGasto> findByIdEleccionAndIdEventoAndCodigoPartido(@Param("idEleccion") Integer idEleccion,@Param("idEvento") Integer idEvento,@Param("esLimiteGastoPartido") Boolean esLimiteGastoPartido,@Param("parCodigoOrigen") String parCodigoOrigen);
	
	@Query("SELECT count(tbl) FROM LimLimiteGasto tbl WHERE tbl.eleEleccion.eleId = :eleccionId")
	int countLimitesByEleccionNivelId(@Param("eleccionId") Integer eleccionId);
	
	@Query("SELECT tbl FROM LimLimiteGasto tbl  WHERE tbl.eleEleccion.eleId = :idEleccion AND tbl.limEsLimiteGastoPartido = :esLimiteGastoPartido AND tbl.parPartido.parId = :partidoId")
	List<LimLimiteGasto> findByIdEleccionAndIdEventoAndPartido(@Param("idEleccion") Integer idEleccion,@Param("esLimiteGastoPartido") Boolean esLimiteGastoPartido,@Param("partidoId") Integer partidoId);
}
