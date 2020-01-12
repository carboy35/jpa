package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.transients.PagoPendiente;

public interface NomNominaRepositoryCustom {

	public List<PagoPendiente> getPagosPendientesMujerOrRemanente(int eveId, int idTipoSeccionRendicion);
}
