package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.RlcSubpactoPartido;

public interface RlcSubPactoPartidoRepositoryCustom {
	public List<RlcSubpactoPartido> devuelveSubPactoPartidos(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId);
	public List<RlcSubpactoPartido> devuelveSubPactoPartidosPorAsignar(Integer eventoId,String estado);
	public void deleteByEleccionId(int eleccionId);
}
