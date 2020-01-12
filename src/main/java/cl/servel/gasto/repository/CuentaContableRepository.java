package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.CctCuentaContable;

@Repository("cuentaContableRepository")
public interface CuentaContableRepository extends CrudRepository<CctCuentaContable, Integer> {
    
    @Query("SELECT tbl FROM CctCuentaContable tbl WHERE tbl.cctEliminado = FALSE ")
    List<CctCuentaContable> findAllActive();
	

    @Query("SELECT tbl FROM CctCuentaContable tbl WHERE tbl.cctEliminado = FALSE AND tbl.parPartido.parId = :partidoId")
    List<CctCuentaContable> findAllActiveByPartido(@Param("partidoId") Integer partidoId);
    
    @Query("SELECT tbl FROM CctCuentaContable tbl WHERE tbl.cctEliminado = FALSE AND tbl.canCandidato.canId = :candidatoId")
    List<CctCuentaContable> findAllActiveByCandidato(@Param("candidatoId") Integer candidatoId);
    
    @Query("SELECT tbl FROM CctCuentaContable tbl WHERE tbl.cctEliminado = FALSE AND tbl.canCandidato.canId = :candidatoId AND tbl.tpoCtaBancaria.tpoNombreCuenta = :tipoCuenta")
    CctCuentaContable getActivaByCandidatoAndTipo(@Param("candidatoId") Integer candidatoId, @Param("tipoCuenta") String tipoCuenta);
    
    @Query("SELECT tbl FROM CctCuentaContable tbl WHERE tbl.cctEliminado = FALSE AND tbl.parPartido.parId = :partidoId AND tbl.tpoCtaBancaria.tpoNombreCuenta = :tipoCuenta")
    CctCuentaContable getActivaByPartidoAndTipo(@Param("partidoId") Integer partidoId, @Param("tipoCuenta") String tipoCuenta);
}
