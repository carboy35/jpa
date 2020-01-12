package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.GutGrupoUsuarioTarea;

public interface GutGrupoUsuarioTareaRepository extends CrudRepository<GutGrupoUsuarioTarea, Integer>{
	
	@Query("select tbl from GutGrupoUsuarioTarea tbl where tbl.celCelula.celId = :celulaId")
	public List<GutGrupoUsuarioTarea> getByCelula(@Param("celulaId") int celulaId);

	@Query("select tbl from GutGrupoUsuarioTarea tbl join UstUsuarioTarea ust on tbl.gutId = ust.gutGrupoUsuarioTarea.gutId where ust.usuUsuarios.usuId = :usuarioId and ust.ustActivo = true")
	public List<GutGrupoUsuarioTarea> getByUsuario(@Param("usuarioId") int usuarioId);

	@Query("select tbl from GutGrupoUsuarioTarea tbl where tbl.gutId in :gruposIds and tbl.celCelula.celActiva = true")
	public List<GutGrupoUsuarioTarea> getByGrupos(@Param("gruposIds") List<Integer> gruposIds);
	
	@Query("select tbl from GutGrupoUsuarioTarea tbl join UstUsuarioTarea ust on tbl.gutId = ust.gutGrupoUsuarioTarea.gutId where ust.ustId = :idUsuarioTarea and ust.ustActivo = true")
	public Optional<GutGrupoUsuarioTarea> getByIdUsuarioTarea(@Param("idUsuarioTarea") int idUsuarioTarea);
	
}
