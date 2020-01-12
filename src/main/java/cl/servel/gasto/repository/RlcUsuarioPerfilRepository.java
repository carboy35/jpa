package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.PerPerfil;
import cl.servel.gasto.entity.RlcUsuarioPerfil;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface RlcUsuarioPerfilRepository
		extends CrudRepository<RlcUsuarioPerfil, Integer>, GenericCustomRepository<RlcUsuarioPerfil> {

	@Query("SELECT tbl FROM RlcUsuarioPerfil tbl WHERE tbl.eliminado = FALSE")
	List<RlcUsuarioPerfil> findAllActive();

	@Query("SELECT tbl FROM RlcUsuarioPerfil tbl WHERE tbl.eliminado = FALSE and tbl.perPerfil.perEliminado = FALSE  AND tbl.usuUsuarios.usuId = :usuarioId")
	List<RlcUsuarioPerfil> findAllActiveByUsuarioId(@Param("usuarioId") Integer usuarioId);

	@Query("SELECT tbl.perPerfil FROM RlcUsuarioPerfil tbl WHERE tbl.eliminado = FALSE and tbl.usuUsuarios.usuId = :usuarioId ")
	List<PerPerfil> findAllPerfilByUsuarioId(@Param("usuarioId") Integer usuarioId);
	
	@Query("SELECT tbl FROM RlcUsuarioPerfil tbl WHERE tbl.eliminado = FALSE and tbl.perPerfil.perId = :perId and tbl.usuUsuarios.usuEliminado = FALSE ")
	List<RlcUsuarioPerfil> findAllPerfilByPerfilId(@Param("perId") Integer perId);
}
