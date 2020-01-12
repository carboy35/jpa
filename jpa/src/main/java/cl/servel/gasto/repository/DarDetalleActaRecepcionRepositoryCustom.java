package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.DarDetalleActaRecepcion;

public interface DarDetalleActaRecepcionRepositoryCustom {

	public void deleteRlcDetalleActaRendicionByDetalleActaRecepcion(int darId);
	
	public void deleteByActaRecepcion(int actaRecepcionId);
	
	public void deleteByPartido(int partidoId);
	
	public void deleteByCandidato(int candidatoId);
	
	public List<DarDetalleActaRecepcion> obtenerActasPorRut(String rut, Integer eventoId, Integer tipoEventoId, String arcEstado);
	
}
