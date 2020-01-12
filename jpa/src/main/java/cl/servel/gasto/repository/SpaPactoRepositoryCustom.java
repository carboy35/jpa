package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.SpaPacto;

public interface SpaPactoRepositoryCustom {
	public List<SpaPacto> devuelveSubpactos(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId);
	public List<SpaPacto> devuelveSubpactosPorAsignar(Integer eventoId,String estado);
	public void deleteByEleccionId(int eleccionId);
}
