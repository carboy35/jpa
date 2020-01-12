package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.PamParametro;


@Repository("pamParametroRepository")
public interface PamParametroRepository extends CrudRepository<PamParametro, Integer> {
	@Query("SELECT tbl FROM PamParametro tbl WHERE tbl.pamEliminado = FALSE")
	List<PamParametro> findAllActivePamParametro();
	
	@Query("SELECT tbl FROM PamParametro tbl  WHERE tbl.eveEventoEleccionario.eveId = :eveEventoId")
	List<PamParametro> findPamParametroByIdEvento(@Param("eveEventoId") Integer eveEventoId);
	
	@Query("SELECT tbl FROM PamParametro tbl  WHERE tbl.pamNombre = :nombre and tbl.eveEventoEleccionario.eveId = :eveEventoId")
	PamParametro findPamParametroByNombre(@Param("nombre") String nombre,@Param("eveEventoId") Integer eveEventoId);

	@Query("SELECT tbl FROM PamParametro tbl  WHERE tbl.pamNombre = :nombre")
	List<PamParametro> findPamParametroOnlyByNombre(@Param("nombre") String nombre);
	
	@Query("SELECT tbl FROM PamParametro tbl  WHERE tbl.pamEliminado=false and tbl.pamCodTipo = :codigo and tbl.eveEventoEleccionario.eveId = :eveEventoId")
	List<PamParametro> findPamParametroByCodigo(@Param("codigo") String codigo,@Param("eveEventoId") Integer eveEventoId);
	
	@Query("SELECT tbl FROM PamParametro tbl  WHERE tbl.pamEliminado = false AND tbl.pamNombre LIKE (:nombre) AND tbl.eveEventoEleccionario.eveId = :idEvento")
	List<PamParametro> findLikeNombreAndEvento(@Param("nombre") String nombre, @Param("idEvento") Integer idEvento);
	
	@Query("SELECT tbl FROM PamParametro tbl  WHERE tbl.pamEliminado = false AND tbl.pamNombre =:nombre AND tbl.eveEventoEleccionario.eveId = :idEvento")
	Optional<PamParametro> findNombreAndEvento(@Param("nombre") String nombre, @Param("idEvento") Integer idEvento);
}
