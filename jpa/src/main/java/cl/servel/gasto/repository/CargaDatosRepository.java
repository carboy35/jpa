package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import cl.servel.gasto.entity.CadCargaDatos;

@Repository("cargaDatosRepository")
public interface CargaDatosRepository extends CrudRepository<CadCargaDatos, Integer> {
	@Query("SELECT tbl FROM CadCargaDatos tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId ")
	List<CadCargaDatos> findAllCargaDatosByEvento(@Param("eventoId") int eventoId);
	
	@Query("SELECT tbl FROM CadCargaDatos tbl WHERE tbl.eveEventoEleccionario.eveId = :eventoId and tbl.cadTipoCarga = :nombre ")
	List<CadCargaDatos> findAllCargaDatosByNombreEvento(@Param("nombre") String nombre,@Param("eventoId") int eventoId);

	@Transactional
	@Modifying
	@Query("DELETE FROM CadCargaDatos WHERE cadId IN (SELECT cadId FROM CadCargaDatos WHERE eveEventoEleccionario.eveId=:idEvento AND cadTipoCarga LIKE CONCAT('%_',:idTipoEvento))")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);

}
