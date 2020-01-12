package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.RlcRolUsuarioFuncionalidad;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface RlcRolUsuarioFuncionalidadRepository extends CrudRepository<RlcRolUsuarioFuncionalidad, Integer>,GenericCustomRepository<RlcRolUsuarioFuncionalidad>{

}
