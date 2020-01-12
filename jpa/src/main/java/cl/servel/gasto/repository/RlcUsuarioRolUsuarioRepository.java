package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.RlcUsuarioRolUsuario;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface RlcUsuarioRolUsuarioRepository
		extends CrudRepository<RlcUsuarioRolUsuario, Integer>, GenericCustomRepository<RlcUsuarioRolUsuario> {

}
