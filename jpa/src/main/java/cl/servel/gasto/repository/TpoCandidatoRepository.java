package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.TpoCandidato;

@Repository("tpoCandidatoRepository")
public interface TpoCandidatoRepository extends CrudRepository<TpoCandidato, Integer> {
	@Query("SELECT tbl FROM TpoCandidato tbl WHERE tbl.tpoCanEliminado = FALSE")
	public List<TpoCandidato> findAllActiveTpoCandidato();
	
	@Query("SELECT tbl FROM TpoCandidato tbl JOIN CanCandidato tbl1 ON tbl1.tpoCandidato.tpoCandidatoId = tbl.tpoCandidatoId WHERE tbl1.canId = :idCandidato")
	public Optional<TpoCandidato> getByCandidato(@Param("idCandidato") int idCandidato);	
}
