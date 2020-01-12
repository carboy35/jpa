package cl.servel.gasto.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.ResResumenCalculos;

public interface ResResumenCalculosRepositoryCustom {
	public List<ResResumenCalculos> leerCandidatosIndendientesPdf(Integer idEvento,Integer idEleccion,Integer tipoCandidato);

	public List<ResResumenCalculos> getResumenCalculosIniciales( Integer idEventoAnterior,Integer idEleccionAnterior,Integer idEleccion,Integer tipoCandidato1, Integer tipoCandidato2,Integer partidoId);
	
	public List<ResResumenCalculos> getResumenCalculosCandidatosIndependientes(Integer idEleccionAnterior,Integer idEleccion,Integer tipoCandidato,Integer partidoId);
	
	public List<ResResumenCalculos> getResumenCalculosFase2(Integer idEleccion,Integer tipoCandidato);
	
	public List<ResResumenCalculos> leerFinanciamientoInicialPartidosPdf(Integer idEleccion);
	
	public void eliminarFinanciamientoInicialPorEleccion(Integer idEleccion);
	
	public Integer getCalculoEleccionAnterior(@Param("eleccionId") Integer eleccionId);
}
