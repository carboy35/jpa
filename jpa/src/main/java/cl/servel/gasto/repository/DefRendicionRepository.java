package cl.servel.gasto.repository;

import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DefRendicion;
import cl.servel.gasto.entity.DetDetalleRendicion;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository("defRendicionRepository")
public interface DefRendicionRepository extends CrudRepository<DefRendicion, Integer> {
	@Query("SELECT tbl FROM DefRendicion tbl WHERE tbl.tpoRendicion.tpoRendicionId = :tpoRendicionId")
	List<DefRendicion> findDefRendicionByTpoRendicion(@Param("tpoRendicionId") Integer tpoRendicionId);

}




