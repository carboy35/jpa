package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.AdmAdministradorElectoral;
import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.RlcAdminCandidato;
import cl.servel.gasto.entity.SelSubEleccion;

@Repository("relAdminCandidatoRepository")
public interface RelAdminCandidatoRepository extends CrudRepository<RlcAdminCandidato, Integer> {
	@Query("SELECT tbl FROM RlcAdminCandidato tbl WHERE tbl.rlcAdminEliminado = FALSE")
	List<RlcAdminCandidato> findAllActiveRlcAdminCandidato();
	
	//@Query("SELECT tbl FROM RelAdminCandidato tbl WHERE tbl.canCandidato =:canCandidatoId")
	//List<RelAdminCandidato> findByCanCandidato(@Param("canCandidatoId") CanCandidato canCandidatoId);
	
	//CCA1
	@Query("SELECT tbl FROM RlcAdminCandidato tbl WHERE tbl.canCandidato.canId = :canId AND tbl.rlcAdminEliminado = FALSE")
	List<RlcAdminCandidato> findAllActiveRlcAdminCandidatoPorCanId(@Param("canId") Integer canId);
}
