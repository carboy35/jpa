package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.AsgAsignacionCargaTarea;

public interface AsgAsignacionCargaTareaRepositoryCustom {
	public List<AsgAsignacionCargaTarea> agregarUsuarios(Integer usuarioId,Integer celulaId);
}
