package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.RlcPerfilDefinicionTarea;

public interface RlcPerfilDefinicionTareaRepository extends CrudRepository<RlcPerfilDefinicionTarea, Integer>,RlcPerfilDefinicionTareaRepositoryCustom {

	@Query("select tbl from RlcPerfilDefinicionTarea tbl where tbl.dtaDefinicionTarea.dtaId = :dtaId")
	public List<RlcPerfilDefinicionTarea> getByDefinicionTarea(@Param("dtaId") int dtaId);
	
	@Query("select tbl from RlcPerfilDefinicionTarea tbl  ")
	public List<RlcPerfilDefinicionTarea> getAllPerfiles();
}
