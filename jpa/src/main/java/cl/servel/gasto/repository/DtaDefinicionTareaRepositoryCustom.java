package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.DtaDefinicionTarea;

public interface DtaDefinicionTareaRepositoryCustom {
	public DtaDefinicionTarea devuelveTareaInicial(String dflCodigo);
	public List<DtaDefinicionTarea> devuelveTareasTotalCuentas(Integer eventoId);
	public List<DtaDefinicionTarea> devuelveDefinicionTareaOrdenadasFlujo(String dflCodigo, int eveId);
	public DtaDefinicionTarea getTareaInicioRevisionRespuestaObservacion(Integer eventoId,String codigoDfl);
}
