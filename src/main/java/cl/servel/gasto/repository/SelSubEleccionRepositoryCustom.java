package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.SelSubEleccion;

public interface SelSubEleccionRepositoryCustom {
	public void actualizarSubeleccionCandidatos(Integer idEleccion);
	public List<SelSubEleccion> devuelveSubEleccionesEvento(Integer eventoId,List<Integer> usuarioIds, Integer dtaId, Integer usuarioId);
	public void deleteByEleccionId(int eleccionId);
}
