package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DetDetalleNomina;

public interface DetDetalleNominaRepository extends CrudRepository<DetDetalleNomina,Integer>,DetDetalleNominaRepositoryCustom {
	@Query("SELECT tbl FROM DetDetalleNomina tbl WHERE tbl.nomNomina.nomId = :nomId")
	Optional<List<DetDetalleNomina>> findDetDetalleNominaByNominaId(@Param("nomId") Integer nomId);
	
	
	@Query("SELECT tbl FROM DetDetalleNomina tbl WHERE tbl.nomNomina.tpoTipos.tpoCodigo = :codigoTipo and tbl.nomNomina.eveEventoEleccionario.eveId = :eventoId")
	Optional<List<DetDetalleNomina>> findDetDetalleNominaByCodigoTipo(@Param("codigoTipo") String codigoTipo,@Param("eventoId") Integer eventoId);
	
	@Query("SELECT n from DetDetalleNomina n WHERE n.nomNomina.eveEventoEleccionario.eveId = :eveId AND n.nomNomina.tpoTipos.tpoId = :codigo AND n.detEstado = :estado")
	List<DetDetalleNomina> getByEstadoAndTipo(@Param("eveId") int eveId, @Param("codigo") int codigo, @Param("estado") String estado);
	
	@Query("SELECT n FROM DetDetalleNomina n WHERE n.nomNomina.tpoTipos.tpoCodigo = :codigoTipoNomina AND n.parPartido.parId IN (:patidosIds)")
	List<DetDetalleNomina> getByTipoAndListaPartidos(@Param("codigoTipoNomina") String codigoTipoNomina, @Param("patidosIds") List<Integer> patidosIds);
	
	@Query("SELECT tbl FROM DetDetalleNomina tbl WHERE (tbl.canCandidato.canId = :candidatoPartidoId or tbl.parPartido.parId = :candidatoPartidoId) and  tbl.nomNomina.tpoTipos.tpoCodigo = :codigoTipoNomina")
	Optional<List<DetDetalleNomina>> getByCandidatoPartidoTipo(@Param("candidatoPartidoId") Integer candidatoPartidoId,@Param("codigoTipoNomina") String codigoTipoNomina);
}
