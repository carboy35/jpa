package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.UsuUsuarios;

public interface UsuUsuariosRepositoryCustom {
	public List<UsuUsuarios> devuelveUsuariosTareas(Integer eventoId);
	public List<UsuUsuarios> devuelveUsuariosDefinicionTarea(Integer dtaId);
	public List<UsuUsuarios> devuelveUsuariosInstanciaFlujo(Integer infId,String codigoTarea,Integer dtaId);
}
