package cl.servel.gasto.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.servel.gasto.entity.TpoAdministrador;

@Repository("tpoAdministradorRepository")
public interface TpoAdministradorRepository extends CrudRepository<TpoAdministrador, Integer> {
	@Query("SELECT tbl FROM TpoAdministrador tbl WHERE tbl.tpoAdmEliminado = FALSE")
	List<TpoAdministrador> findAllActiveTpoAdministrador();
	
	@Query("SELECT tbl FROM TpoAdministrador tbl WHERE tbl.tpoAdmEliminado = FALSE and tbl.tpoAdministradorId =:tpoAdministradorId")
	TpoAdministrador findByIdActiveTpoAdministrador(@Param("tpoAdministradorId") Integer tpoAdministradorId);
	
	@Query( value="alter sequence tpo_administrador_tpo_administrador_id_seq restart",nativeQuery=true)
	@Modifying
	@Transactional
	void updateSecuence();
}
