package cl.servel.gasto.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.ParPartidoMaestro;

@Repository("parPartidoMaestroRepository")
public interface ParPartidoMaestroRepository extends CrudRepository<ParPartidoMaestro, Integer>,ParPartidoMaestroRepositoryCustom {
	
	@Query("select tbl  from ParPartidoMaestro tbl where tbl.nombre = :nombre")
	ParPartidoMaestro findByNombre(@Param("nombre") String nombre);
	
	@Query("select tbl  from ParPartidoMaestro tbl where tbl.codigoOrigen = :codigoOrigen")
	ParPartidoMaestro findByCodigoOrigen(@Param("codigoOrigen") String codigoOrigen);
	
	@Query("select tbl  from ParPartidoMaestro tbl where tbl.codigoOrigen = :eventoId")
	ParPartidoMaestro findPartidoMaestroVigencia(@Param("eventoId") String eventoId);
}
