package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.TpoRendicion;

public interface TpoRendicionRepositoryCustom  {
	public List<TpoRendicion> devuelveTiposRendicionInstanciaFlujo(Integer eventoId,String codigoFormulario1, String codigoFormulario2);
}
