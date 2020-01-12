package cl.servel.gasto.repository.custom;

import java.util.List;

import cl.servel.gasto.entity.ParPartido;

public interface ParPartidoRepositoryCustom {

	public List<ParPartido> buscarPartidos(Integer idEvento,Integer tipoEvento,Integer idEleccion, List<String> campos, String valor, Boolean estado,String definicionTarea,List<String> filtros,Integer idTipoEleccion);
	
	public List<ParPartido> devuelveConsultaPartidosInstanciaFlujo(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId);
	
	public List<ParPartido> devuelvePartidosCandidatoRendicion(Integer eventoId);
	
	public List<ParPartido> devuelvePartidosCandidatoInstanciaFlujo(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId);
}
