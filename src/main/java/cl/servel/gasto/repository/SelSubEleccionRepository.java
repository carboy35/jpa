package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.entity.SelSubEleccion;

@Repository("selSubEleccionRepository")
public interface SelSubEleccionRepository extends CrudRepository <SelSubEleccion, Integer>, SelSubEleccionRepositoryCustom {
	@Query("SELECT tbl FROM SelSubEleccion tbl WHERE tbl.selEliminado = FALSE")
	List<SelSubEleccion> findAllActiveSelSubEleccion();
	
	@Query("SELECT tbl FROM SelSubEleccion tbl WHERE tbl.nivNivel.nivId = :niNivelId AND tbl.eleEleccion.eleId = :eleEleccionId")
	List<SelSubEleccion> findByNivNivelAndEleEleccion(@Param("niNivelId") Integer niNivelId,@Param("eleEleccionId") Integer eleEleccionId);

	@Query("SELECT tbl FROM SelSubEleccion tbl WHERE tbl.eleEleccion.eleId = :eleEleccionId")
	List<SelSubEleccion> findByEleEleccion(@Param("eleEleccionId") Integer integer);

	
	@Query("SELECT tbl FROM SelSubEleccion tbl WHERE tbl.nivNivel.nivId = :niNivelId AND tbl.eveEventoEleccionario.eveId = :eveEventoId")
	List<SelSubEleccion> findByNivNivelAndEveEventoEleccionario(@Param("niNivelId") Integer niNivelId,@Param("eveEventoId") Integer eveEventoId);

	@Transactional
	@Modifying
	@Query("DELETE FROM SelSubEleccion WHERE selId IN(SELECT selId FROM SelSubEleccion WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
}
