package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.entity.LimLimiteGasto;

public interface LimLimiteGastoRepositoryCustom {
	public List<LimLimiteGasto> leeLimitesCandidato(Integer idEleccion); 
	
	public List<LimLimiteGasto> leeLimitesPartido(Integer idEleccion,Integer tipoCandidato1, Integer tipoCandidato2);
	
	public List<LimLimiteGasto> leeLimitesPartidoIndependientes(Integer idEleccion,Integer tipoCandidato);

	public void deleteByEleccion(int idEleccion);
	
	public LimLimiteGasto leeLimiteGastoCandidatoPorEleccion(Integer idEleccion, Integer rut);
	
	public LimLimiteGasto leeLimiteGastoPartidoPorEleccion(Integer idEleccion, String rut);
}
