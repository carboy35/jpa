package cl.servel.gasto.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DecDetalleCarga;

@Repository("decDetalleCargaRepository")
public interface DecDetalleCargaRepository extends CrudRepository<DecDetalleCarga,Integer>,DecDetalleCargaRepositoryCustom {
	@Query("SELECT tbl FROM DecDetalleCarga tbl WHERE tbl.resumenCarga.recId = :resumenId ")
	List<DecDetalleCarga> findAllDetalleCargaByResumen(@Param("resumenId") Integer resumenId);

	@Transactional
	@Modifying
	@Query("DELETE FROM DecDetalleCarga  WHERE resumenCarga.recId IN(SELECT recId FROM ResumenCarga WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipoAsEleccion(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM DecDetalleCarga WHERE resumenCarga.recId IN( SELECT recId FROM ResumenCarga WHERE cadCargaDatos.eveEventoEleccionario.eveId=:idEvento AND cadCargaDatos.cadTipoCarga LIKE CONCAT('%_',:idTipoEvento))")
	void deleteByEventoYTipoAsCarga(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
}
