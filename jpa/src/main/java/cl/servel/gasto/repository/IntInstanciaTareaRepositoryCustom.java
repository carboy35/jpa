package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.transients.ResumenInstanciaFlujoDTO;

public interface IntInstanciaTareaRepositoryCustom {
	
	public List<ResumenInstanciaFlujoDTO> getDataReporteWorkflow(int dflId, int tipoEvento, int tipoEleccion, long fechaInicio, long fechaFin);

}
