package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.RlcDetalleActaRendicion;
@Repository("RlcDetalleActaRendicionRepository")
public interface RlcDetalleActaRendicionRepository extends CrudRepository<RlcDetalleActaRendicion, Integer> {
	@Query("SELECT tbl FROM RlcDetalleActaRendicion tbl WHERE tbl.darDetalleActaRecepcion.darId = :darId")
	List<RlcDetalleActaRendicion> findRlcDetalleActaRendicionByDarId(@Param("darId") Integer darId);
	
	@Query("SELECT tbl FROM RlcDetalleActaRendicion tbl WHERE tbl.renRendicion.renId = :renId")
	List<RlcDetalleActaRendicion> findRlcDetalleActaRendicionByRendicion(@Param("renId") Integer renId);
	
}



