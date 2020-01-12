package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.UstUsuarioTarea;

public interface UstUsuarioTareaRepositoryCustom {
	public List<UstUsuarioTarea> usuariosPorTarea(Integer eventoId, Integer dtaId);
}
