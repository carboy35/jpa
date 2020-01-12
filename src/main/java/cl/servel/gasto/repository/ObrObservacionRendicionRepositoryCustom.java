package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.ObrObservacionRendicion;

public interface ObrObservacionRendicionRepositoryCustom {
	public List<ObrObservacionRendicion> obtenerObervaciones(Integer candidatoPartidoId,String codigoEntidad);
	public List<ObrObservacionRendicion> obtenerTotalRespuestas(Integer infId,List<String> tipoRespuesta);
}
