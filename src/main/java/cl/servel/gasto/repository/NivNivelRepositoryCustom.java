package cl.servel.gasto.repository;

import cl.servel.gasto.entity.NivNivel;

public interface NivNivelRepositoryCustom {
	public void deleteByEleccionId(int eleccionId);
	public NivNivel nivelByEventoNivel(int eventoId, int nivelId,String tipoNivelPadre);
}
