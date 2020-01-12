package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.RolUsuario;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface RolUsuarioRepository extends CrudRepository<RolUsuario, Integer>,GenericCustomRepository<RolUsuario>  {

}
