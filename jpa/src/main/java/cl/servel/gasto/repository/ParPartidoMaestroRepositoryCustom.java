package cl.servel.gasto.repository;
import java.util.List;

import cl.servel.gasto.entity.ParPartidoMaestro;

public interface ParPartidoMaestroRepositoryCustom {

	public List<ParPartidoMaestro> obtenerPartidosMaestrosVigencia(Integer idEvento);
}
