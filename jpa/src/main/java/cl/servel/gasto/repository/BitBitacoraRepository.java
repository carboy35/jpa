package cl.servel.gasto.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.BitBitacora;

@Repository("bitBitacoraRepository")
public interface BitBitacoraRepository extends CrudRepository<BitBitacora, Integer>,BitBitacoraRepositoryCustom {
	
	@Query("SELECT tbl FROM BitBitacora tbl WHERE tbl.bitEntidadRegistrada = :bitEntidadRegistrada and tbl.idEntidadRegistrada = :idEntidadRegistrada ORDER BY tbl.bitFechaRegistro")
	List<BitBitacora> getByEntityAndEntityId(@Param("bitEntidadRegistrada") String bitEntidadRegistrada, @Param("idEntidadRegistrada") Long idEntidadRegistrada);
	
	@Query("SELECT tbl FROM BitBitacora tbl WHERE tbl.bitEntidadRegistrada = :bitEntidadRegistrada and tbl.idEntidadRegistrada = :idEntidadRegistrada and tbl.bitAtributoEntidad = :atributoEntidad ORDER BY tbl.bitFechaRegistro")
	List<BitBitacora> getByEntityEntityIdAndEntityAttribute(@Param("bitEntidadRegistrada") String bitEntidadRegistrada, @Param("idEntidadRegistrada") Long idEntidadRegistrada, @Param("atributoEntidad") String atributoEntidad);

	@Modifying
	@Transactional
	@Query("UPDATE BitBitacora tbl SET tbl.bitAccion='ELIMINAR' WHERE tbl.bitEntidadRegistrada = :bitEntidadRegistrada and tbl.idEntidadRegistrada = :idEntidadRegistrada AND tbl.bitId = :bitId")
	void eliminarLogico(@Param("bitEntidadRegistrada") String bitEntidadRegistrada, @Param("idEntidadRegistrada") Long idEntidadRegistrada, @Param("bitId") Integer bitId);
	
	@Query("SELECT tbl FROM BitBitacora tbl WHERE  tbl.idEntidadRegistrada = :idEntidadRegistrada  and tbl.usuUsuarios.usuId=:idUsuario AND tbl.bitEntidadRegistrada ='Checklist' ORDER BY tbl.bitFechaRegistro")
	List<BitBitacora> getByUserAndEntityIdForCheckList( @Param("idUsuario") Integer idUsuario, @Param("idEntidadRegistrada") Long idEntidadRegistrada);

	@Query("SELECT tbl FROM BitBitacora tbl WHERE  tbl.bitEntidadRegistrada=:entidad  and tbl.idEntidadRegistrada = :idEntidad and tbl.bitAccion=:bitAccion ORDER BY tbl.bitFechaRegistro")
	List<BitBitacora> obtenerListaBitacoraEntidadAccion( @Param("entidad") String entidad, @Param("idEntidad") Long idEntidad, @Param("bitAccion") String bitAccion);
}
