package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DarDetalleActaRecepcion;

public interface DarDetalleActaRecepcionRepository
		extends CrudRepository<DarDetalleActaRecepcion, Integer>, DarDetalleActaRecepcionRepositoryCustom {

	@Query("SELECT tbl FROM DarDetalleActaRecepcion tbl WHERE tbl.acrActaRecepcion.acrId = :actaRecepcionId order by tbl.darId desc")
	List<DarDetalleActaRecepcion> findByActaRecepcionId(@Param("actaRecepcionId") Integer actaRecepcionId);

	@Query("SELECT tbl FROM DarDetalleActaRecepcion tbl WHERE tbl.acrActaRecepcion.acrId = :acrId AND tbl.parPartido.parId = :partidoId order by tbl.darId desc")
	Optional<DarDetalleActaRecepcion> findByPartido(@Param("acrId") Integer acrId,
			@Param("partidoId") Integer partidoId);

	@Query("SELECT tbl FROM DarDetalleActaRecepcion tbl WHERE tbl.acrActaRecepcion.acrId = :acrId AND tbl.canCandidato.canId = :candidatoId order by tbl.darId desc")
	Optional<DarDetalleActaRecepcion> findByCandidato(@Param("acrId") Integer acrId,
			@Param("candidatoId") Integer candidatoId);

	@Query("SELECT tbl FROM DarDetalleActaRecepcion tbl left join tbl.parPartido par left join tbl.canCandidato can WHERE tbl.acrActaRecepcion.acrId = :actaRecepcionId and ( (can.canRut = :rut and can.canRutDv = :dv) or par.parRut = :rutCompleto) order by tbl.darId desc")
	List<DarDetalleActaRecepcion> findByActaRecepcionAndRut(@Param("actaRecepcionId") Integer actaRecepcionId,
			@Param("rut") Integer rut, @Param("dv") String dv, @Param("rutCompleto") String rutCompleto);

}
