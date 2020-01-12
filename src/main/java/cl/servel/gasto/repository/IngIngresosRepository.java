package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.IngIngresos;

public interface IngIngresosRepository extends CrudRepository<IngIngresos,Integer> {
	@Query("SELECT tbl FROM IngIngresos tbl WHERE (tbl.canCandidato.canId = :candidatoPartidoAdmId or tbl.parPartido.parId = :candidatoPartidoAdmId or tbl.admAdministradorElectoral.admId = :candidatoPartidoAdmId) "
			+ "and tbl.tpoTipos.tpoId = :tipoIngresoId")
	Optional<List<IngIngresos>> getByCandidatoPartidoAdmId(@Param("candidatoPartidoAdmId") int candidatoPartidoAdmId,@Param("tipoIngresoId") int tipoIngresoId);
	
	@Query("SELECT tbl FROM IngIngresos tbl WHERE tbl.tpoTipos.tpoCodAgrupacion = :tpoCodAgrupacion")
	Optional<List<IngIngresos>> getAllIngresos(@Param("tpoCodAgrupacion") String tpoCodAgrupacion);
	
	@Query("SELECT tbl from IngIngresos tbl WHERE tbl.eveEventoEleccionario.eveId = :eveId AND tbl.tpoTipos.tpoCodigo = :tpoCodigo AND tbl.detDetalleNomina IS NULL")
	List<IngIngresos> getIngresosPorEnviarPagoByCodigoTipoAndEvento(@Param("eveId") int eveId, @Param("tpoCodigo") String tipoCodigo);
	
	@Query("SELECT tbl from IngIngresos tbl WHERE  tbl.tpoTipos.tpoCodigo = :tpoCodigo AND tbl.canCandidato.canId=:candidatoId AND tbl.estado=:estadoIngreso")
	List<IngIngresos> getIngresosCandidato(@Param("candidatoId") int candidatoId, @Param("tpoCodigo") String tipoCodigo, @Param("estadoIngreso") String estadoIngreso);
	
	@Query("SELECT tbl from IngIngresos tbl WHERE  tbl.tpoTipos.tpoCodigo = :tpoCodigo AND tbl.parPartido.parId=:partidoId AND tbl.estado=:estadoIngreso")
	List<IngIngresos> getIngresosPartido(@Param("partidoId") int partidoId, @Param("tpoCodigo") String tipoCodigo, @Param("estadoIngreso") String estadoIngreso);
	
	@Query("SELECT tbl FROM IngIngresos tbl WHERE tbl.ingRut = :rut and tbl.ingRutDv = :rutDv "
			+ "and tbl.tpoTipos.tpoId = :tipoIngresoId")
	Optional<List<IngIngresos>> getByCandidatoPartidoAdmByRut(@Param("rut") Integer rut, @Param("rutDv") String rutDv,@Param("tipoIngresoId") int tipoIngresoId);
	
	@Query("SELECT tbl from IngIngresos tbl WHERE  tbl.tpoTipos.tpoCodigo = :tpoCodigo AND tbl.canCandidato.canId=:candidatoId ")
	List<IngIngresos> getIngresosCandidato(@Param("candidatoId") int candidatoId, @Param("tpoCodigo") String tipoCodigo);
	
	@Query("SELECT tbl from IngIngresos tbl WHERE  tbl.tpoTipos.tpoCodigo = :tpoCodigo AND tbl.parPartido.parId=:partidoId ")
	List<IngIngresos> getIngresosPartido(@Param("partidoId") int partidoId, @Param("tpoCodigo") String tipoCodigo);
	
	@Query("SELECT tbl from IngIngresos tbl WHERE tbl.eveEventoEleccionario.eveId = :eveId AND tbl.tpoTipos.tpoCodigo = :tpoCodigo")
	Optional<List<IngIngresos>> getIngresosByCodigoTipoAndEvento( @Param("tpoCodigo") String tipoCodigo,@Param("eveId") int eveId);
}
