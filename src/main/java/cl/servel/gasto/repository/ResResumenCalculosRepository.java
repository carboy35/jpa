package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.ResResumenCalculos;

@Repository("resResumenCalculosRepository")
public interface ResResumenCalculosRepository extends CrudRepository<ResResumenCalculos, Integer>,ResResumenCalculosRepositoryCustom {
	
	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.eleEleccion.eleId = :eleccionId ORDER BY tbl.parPartido.parId desc")
	List<ResResumenCalculos> findCalculosByEleccion(@Param("eleccionId") Integer eleccionId);
	
	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.eleEleccion.eleId = :eleccionId AND tbl.nivNivel.nivId is not null ORDER BY tbl.parPartido.parId desc, tbl.nivNivel.nivOrden")
	List<ResResumenCalculos> findCalculosByEleccionFase2(@Param("eleccionId") Integer eleccionId);
	
	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.eleEleccion.eleId = :eleccionId and tbl.nivNivel.nivId = :nivelId ORDER BY tbl.nivNivel.nivId")
	List<ResResumenCalculos> findCalculosByEleccionNivelId(@Param("eleccionId") Integer eleccionId,@Param("nivelId") Integer nivelId);
	
	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.eleEleccion.eleId = :eleccionId and tbl.nivNivel.nivId = :nivelId and tbl.parPartido.parId = :partidoId")
	List<ResResumenCalculos> findCalculosByEleccionNivelIdPartido(@Param("eleccionId") Integer eleccionId,@Param("nivelId") Integer nivelId,@Param("partidoId") Integer partidoId);
	
	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.eleEleccion.eleId = :eleccionId and tbl.nivNivel.nivId = :nivelId and tbl.parPartido.parId is null and tbl.canIndepId = :candidatoId")
	List<ResResumenCalculos> findCalculosByEleccionNivelIdPartidoCandidato(@Param("eleccionId") Integer eleccionId,@Param("nivelId") Integer nivelId,@Param("candidatoId") Integer candidatoId);
	
	@Query("SELECT count(tbl) FROM ResResumenCalculos tbl WHERE tbl.eleEleccion.eleId = :eleccionId")
	int countCalculosByEleccionNivelId(@Param("eleccionId") Integer eleccionId);
	
	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.eleIdAnterior = :eleccionIdAnt and tbl.nivIdAnt = :nivIdAnt and tbl.parIdAnt = :partidoIdAnt and tbl.eleEleccion.eleId = :idEleccion")
	ResResumenCalculos findCalculosByEleccionNivelIdPartidoAnterior(@Param("eleccionIdAnt") Integer eleccionIdAnt,@Param("nivIdAnt") Integer nivIdAnt,@Param("partidoIdAnt") Integer partidoIdAnt,@Param("idEleccion") Integer idEleccion);

	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.canIndepId = :canId ")
	List<ResResumenCalculos> obtenerResumenCalculoCandidato(@Param("canId") Integer canId);

	@Query("SELECT tbl FROM ResResumenCalculos tbl WHERE tbl.parPartido.parId = :parId ")
	List<ResResumenCalculos> obtenerResumenCalculoPartido(@Param("parId") Integer parId);
}
