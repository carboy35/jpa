package cl.servel.gasto.repository;

import java.util.List;

import cl.servel.gasto.transients.AdministradorElectoralCandidatoPartido;

public interface AdministradorElectoralRepositoryCustom {
	public List<AdministradorElectoralCandidatoPartido> buscarAdministradores(Integer idEvento, Integer tipoEvento, Integer idEleccion, List<String> campos, String valor, Boolean estado, List<String> filtros);
}
