package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.RenRendicion;
import cl.servel.gasto.entity.ResumenCarga;

@Repository("resumenCargaRepository")
public interface ResumenCargaRepository extends CrudRepository<ResumenCarga,Integer>,ResumenCargaRepositoryCustom{
	
	@Query("SELECT tbl FROM ResumenCarga tbl WHERE tbl.eleEleccion.eleId = :eleccionId")
	List<ResumenCarga> findByEleccionResumenCarga(@Param("eleccionId") Integer eleccionId);
	
	@Query("SELECT tbl FROM ResumenCarga tbl WHERE tbl.cadCargaDatos.cadId = :cargaId order by tbl.eleEleccion.eleId asc ")
	List<ResumenCarga> findByCargaResumen(@Param("cargaId") Integer cargaId);

	@Transactional
	@Modifying
	@Query("DELETE FROM ResumenCarga WHERE recId IN(SELECT recId FROM ResumenCarga WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipoAsEleccion(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ResumenCarga WHERE recId IN( SELECT recId FROM ResumenCarga WHERE cadCargaDatos.eveEventoEleccionario.eveId=:idEvento AND cadCargaDatos.cadTipoCarga LIKE CONCAT('%_',:idTipoEvento))")
	void deleteByEventoYTipoAsCarga(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
	}
