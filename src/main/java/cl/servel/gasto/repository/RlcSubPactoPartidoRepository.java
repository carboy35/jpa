package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.entity.RlcRelNivel;
import cl.servel.gasto.entity.RlcSubpactoPartido;

@Repository("rlcSubPactoPartidoRepository")
public interface RlcSubPactoPartidoRepository extends CrudRepository<RlcSubpactoPartido, Integer>, RlcSubPactoPartidoRepositoryCustom{


	@Query("select tbl  from RlcSubpactoPartido tbl where tbl.eleEleccion.eleId = :idEleccion")
	List<RlcSubpactoPartido> findByEleccion(@Param("idEleccion") Integer idEleccion);
	
	
	@Query("select tbl  from RlcSubpactoPartido tbl where tbl.eleEleccion.eleId = :idEleccion AND tbl.spaPacto.spaId = :idSubPacto")
	List<RlcSubpactoPartido> findBySubPactoAndEleccion(@Param("idEleccion") Integer idEleccion, @Param("idSubPacto") Integer idSubPacto);


	@Transactional
	@Modifying
	@Query("DELETE FROM RlcSubpactoPartido WHERE rlcId IN(SELECT rlcId FROM RlcSubpactoPartido WHERE eleEleccion.eveEventoEleccionario.eveId=:idEvento AND eleEleccion.tpoEvento.tpoEventoId=:idTipoEvento)")
	void deleteByEventoYTipo(@Param("idEvento") Integer idEvento, @Param("idTipoEvento")Integer idTipoEvento);
	

}
