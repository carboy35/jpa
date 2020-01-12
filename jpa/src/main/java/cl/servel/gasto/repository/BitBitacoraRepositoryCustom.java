package cl.servel.gasto.repository;

import java.util.Date;
import java.util.List;

import cl.servel.gasto.entity.BitBitacora;
import cl.servel.gasto.transients.MovimientoCuentaDTO;

public interface BitBitacoraRepositoryCustom {
	public List<BitBitacora> devuelveBitacorasInstanciaFlujo(Integer eventoId,String entidad, List<Integer> usuarioIds,Integer dtaId,Integer usuarioId);
	public List<BitBitacora> devuelveBitacorasObservacion(Integer candidatoPartidoId, String entidad,String atributoEntidad);
	
	public List<MovimientoCuentaDTO> getDataReporteMovimientos(int eveId, int tipoEvento, int tipoEleccion, Date desde, Date hasta, Integer idCandidato, Integer idPartido);
}
