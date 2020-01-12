package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.TpoEvento;

public interface TpoEventoRepositoryCustom {
	public List<TpoEvento> devuelvePartidosTipoEvento(Integer eventoId);
}
