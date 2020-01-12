package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import cl.servel.gasto.entity.PacPacto;
import cl.servel.gasto.entity.SpaPacto;

@Repository("spaPactoRepository")
public interface SpaPactoRepository extends CrudRepository<SpaPacto, Integer>,SpaPactoRepositoryCustom{
	@Query("SELECT tbl FROM SpaPacto tbl WHERE tbl.spaEliminado = FALSE")
	List<SpaPacto> findAllActiveSpaPacto();
	
	@Query("SELECT tbl.pacId FROM PacPacto tbl WHERE tbl.pacCodigoOrigen = :InCodPacOrigen")
	int FindBycodPacOrigen(@Param("InCodPacOrigen") int InCodPacOrigen);
	
	@Query("SELECT tbl FROM SpaPacto tbl WHERE tbl.spaEliminado = FALSE and tbl.pacPacto.pacId = :pactoId")
	List<SpaPacto> findAllActiveByPacto(@Param("pactoId") int pactoId);
	
	@Query("select tbl  from SpaPacto tbl where tbl.spaNombre = :nombre and tbl.eleEleccion.eleId = :eleccionId")
	SpaPacto findByNombreEleccion(@Param("nombre") String nombre,@Param("eleccionId") Integer eleccionId);
	
	@Query("select tbl  from SpaPacto tbl where tbl.spaCodigoOrigen = :codigoOrigen and tbl.eleEleccion.eleId = :eleccionId")
	SpaPacto findByCodigoOrigen(@Param("eleccionId") Integer eleccionId,@Param("codigoOrigen") String codigoOrigen);
	
	@Query("select tbl  from SpaPacto tbl where tbl.eleEleccion.eleId = :eleccionId")
	List<SpaPacto> findByEleccion(@Param("eleccionId") Integer eleccionId);

	@Transactional
	@Modifying
	@Query("DELETE FROM SpaPacto WHERE spaId IN(SELECT spaId FROM SpaPacto WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	
}
