package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.PacPacto;

public interface PacPactoRepositoryCustom {
	public List<PacPacto> devuelvePactos(Integer eventoId,List<Integer> usuarioIds, Integer dtaId,Integer usuarioId);
	public List<PacPacto> devuelvePactosPorAsignar(Integer eventoId,String estado);
	public void deleteByEleccionId(int eleccionId);
}
