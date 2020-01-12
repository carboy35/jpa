package cl.servel.gasto.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.EveEventoEleccionario;

@Repository("eveEventoEleccionarioRepository")
public interface EveEventoEleccionarioRepository extends CrudRepository<EveEventoEleccionario, Integer> {
    @Query("SELECT tbl FROM EveEventoEleccionario tbl WHERE tbl.eveEliminado = FALSE ")
    List<EveEventoEleccionario> findAllActiveEveEventoEleccionario();

    @Query("SELECT tbl FROM EveEventoEleccionario tbl WHERE tbl.eveEliminado = FALSE AND date_part('year', tbl.eveFecha) = :anio ")
    Optional<EveEventoEleccionario> findByAnioAndActive(@Param("anio") int anio);

    @Query("SELECT COUNT(tbl) FROM EveEventoEleccionario tbl WHERE tbl.eveEliminado = FALSE AND date_part('year', tbl.eveFecha) = :anio ")
    int countByAnioAndActive(@Param("anio") int anio);
    
    @Query("SELECT tbl FROM EveEventoEleccionario tbl WHERE tbl.eveEliminado = FALSE and tbl.eveEstado=:estado ")
    List<EveEventoEleccionario> findAllEventoEleccionarioPorEstado(@Param("estado") String estado);
    
    @Query("SELECT tbl FROM EveEventoEleccionario tbl WHERE tbl.eveEliminado = FALSE ")
    List<EveEventoEleccionario> findAllActiveEveEventoEleccionarioUsuario(@Param("usuarioId") int usuarioId);
}
