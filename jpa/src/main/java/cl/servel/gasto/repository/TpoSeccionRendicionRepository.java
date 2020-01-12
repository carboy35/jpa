package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.TpoRendicion;
import cl.servel.gasto.entity.TpoSeccionRendicion;

@Repository("tpoSeccionRendicionRepository")
public interface TpoSeccionRendicionRepository extends CrudRepository<TpoSeccionRendicion, Integer>  {
	@Query("SELECT tbl FROM TpoSeccionRendicion tbl WHERE tbl.tpoCodigoSeccion = :codigo")
	List<TpoSeccionRendicion> findTpoSeccionRendicionByCodigo(@Param("codigo") String codigo);
	
	@Query("SELECT tbl FROM TpoSeccionRendicion tbl WHERE tbl.tpoNombreSeccion = :nombre")
	TpoSeccionRendicion findTpoSeccionRendicionByNombre(@Param("nombre") String nombre);
	
	@Query("SELECT tbl FROM TpoSeccionRendicion tbl inner join DetDetalleRendicion det on tbl.tpoSeccionRendicionId=det.tpoSeccionRendicion.tpoSeccionRendicionId WHERE det.renRendicion.renId = :idRendicion")
	List<TpoSeccionRendicion> findTpoSeccionRendicionByRendicion(@Param("idRendicion") Integer idRendicion);
}

