package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.RlcPerfilRolUsuario;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface RlcPerfilRolUsuarioRepository extends CrudRepository<RlcPerfilRolUsuario, Integer>,GenericCustomRepository<RlcPerfilRolUsuario> {

}
