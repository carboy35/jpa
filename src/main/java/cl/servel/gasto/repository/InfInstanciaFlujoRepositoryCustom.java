package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import cl.servel.gasto.entity.InfInstanciaFlujo;

public interface InfInstanciaFlujoRepositoryCustom {
	
	public Integer getUsuarioMenosCargaFromList(List<Integer> usuarios);
	public List<InfInstanciaFlujo> getByEventoUsuarios(Integer eventoId,List<Integer> usuarioIds, Integer dtaId,String estado);
	public List<InfInstanciaFlujo> getByEventoTipos(Integer idEvento,Integer idTipoEleccion,Integer idTipoEvento);

}
