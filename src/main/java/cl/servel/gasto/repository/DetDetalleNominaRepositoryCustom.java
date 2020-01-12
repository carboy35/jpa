package cl.servel.gasto.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import cl.servel.gasto.entity.DetDetalleNomina;
import cl.servel.gasto.transients.PagoMultaDTO;

public interface DetDetalleNominaRepositoryCustom {
	public Optional<List<DetDetalleNomina>> leeDetalleNominaIndependientes(Integer nomId,String partidoCodigoOrigen,Integer eventoId,Integer tipoCandidato,List<String> tipoPago,List<Integer> idEleccion, Integer dtaId,List<String> seccionesPago, String tipoRendicionReembolso);
	public Optional<List<DetDetalleNomina>> findDetDetalleNominaByNominaIdTipo(Integer nomId,String partidoCodigoOrigen,Integer eventoId,Integer tipoCandidato1, Integer tipoCandidato2,List<String> tipoPago,List<Integer> idEleccion, Integer dtaId,List<String> seccionesPago, String tipoRendicionReembolso);
	
	public LinkedList<PagoMultaDTO> getPagosMultas(int eveId, int tipoEvento, int tipoEleccion, long desde, long hasta);
}
