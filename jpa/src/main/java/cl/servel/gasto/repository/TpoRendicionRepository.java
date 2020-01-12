package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.TpoRendicion;

@Repository("tpoRendicionRepository")
public interface TpoRendicionRepository extends CrudRepository<TpoRendicion, Integer>,TpoRendicionRepositoryCustom    {
	@Query("SELECT tbl FROM TpoRendicion tbl WHERE tbl.tpoCodigo = :codigo and tbl.eveEventoEleccionario.eveId = :eventoId")
	TpoRendicion findTpoRendicionByCodigoEvento(@Param("codigo") String codigo,@Param("eventoId") Integer eventoId);
	
	@Query("SELECT tbl FROM TpoRendicion tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId")
	List<TpoRendicion> findTpoRendicionByEvento(@Param("eventoId") Integer eventoId);

	
}


