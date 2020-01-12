package cl.servel.gasto.repository;

import java.util.Date;
import java.util.List;

import cl.servel.gasto.transients.CambioLineaDetalle;

public interface DetDetalleRendicionRepositoryCustom {

	public void deleteByIdRendicion(int idEleccion);
	
	public void deleteByIdRendicionTipoSeccion(int idRendicion, int idTipoSeccion);
	
	public List<CambioLineaDetalle> getUltimosCambiosDetalle(int detId, Date desde, Date hasta, String historicoOriginal);
	
	public void actualizarPagoCreditoMandato(int detId);
}
