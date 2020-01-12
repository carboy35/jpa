package cl.servel.gasto.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DetDetalleRendicion;


@Repository("detDetalleRendicionRepository")
public interface DetDetalleRendicionRepository extends CrudRepository<DetDetalleRendicion, Integer>, DetDetalleRendicionRepositoryCustom{
	
	//@Query("SELECT tbl FROM DetDetalleRendicion tbl WHERE tbl.dreDatosRendicion.dreId = :dreId")
	//List<DetDetalleRendicion> findDetDetalleRendicionByDatosRendicion(@Param("dreId") Integer dreId);

	@Query("SELECT tbl FROM DetDetalleRendicion tbl WHERE tbl.renRendicion.renId = :renId "
			+ "order by (case when tbl.tpoSeccionRendicion.tpoNombreSeccion like 'pag_%' then	  "
			+ "TO_NUMBER(substr(tbl.tpoSeccionRendicion.tpoNombreSeccion, 5), '9999999999') "
			+ "else tbl.tpoSeccionRendicion.tpoSeccionRendicionId end) asc")
	List<DetDetalleRendicion> findDetDetalleRendicionByRendicion(@Param("renId") Integer renId);
	
	@Query("SELECT tbl FROM DetDetalleRendicion tbl WHERE tbl.renRendicion.renId = :renId AND tbl.tpoSeccionRendicion.tpoSeccionRendicionId = :tipoSeccionId")
	DetDetalleRendicion findDetDetalleRendicionByRendicionTipo(@Param("renId") Integer renId,@Param("tipoSeccionId") Integer tipoSeccionId);
	
	
	@Query("SELECT tbl FROM DetDetalleRendicion tbl WHERE  tbl.renRendicion.eveEventoEleccionario.eveId = :eventoId AND tbl.renRendicion.eleEleccion.tpoEvento.tpoEventoId = :tipoEventoId AND "
			+ "tbl.renRendicion.tpoRendicion.tpoCodigo = :tipoRendicionCodigo AND tbl.tpoSeccionRendicion.tpoSeccionRendicionId = :tipoSeccionId and tbl.renRendicion.parPartido.parId is not null ")
	List<DetDetalleRendicion> findDetDetalleRendicionByEventoTipoEventoCodigo(@Param("eventoId") Integer eventoId,@Param("tipoEventoId") Integer tipoEventoId, @Param("tipoRendicionCodigo") String tipoRendicionCodigo, @Param("tipoSeccionId") Integer tipoSeccionId);
}
