package cl.servel.gasto.repository.custom;

import java.util.List;

import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.CelCelula;

public interface CelCelulaRepositoryCustom {

	public List<CanCandidato> leerCandidatosPaginados(Integer idEleccion, Integer pageNumber, Integer pageSize,
			String orden, String campo, String valor, String estado);

	public int totalCandidatosPaginados(Integer idEleccion, Integer pageNumber, Integer pageSize, String orden,
			String campo, String valor, String estado);

	public List<CanCandidato> findCandidatosByAdministrador(@Param("rut") int rut, @Param("dv") String dv,
			@Param("idEvento") int idEvento, @Param("idTpoEvento") int idTpoEvento,
			@Param("idTpoEleccion") int idTpoEleccion);

	public List<CanCandidato> buscarCandidatos(Integer idEvento, Integer tipoEvento, Integer idEleccion,
			List<String> campos, String valor, Boolean estado,List<String> filtros);

	public List<CanCandidato> devuelveConsultaPartidosInstanciaFlujo(Integer eventoId);
	
	public List<CelCelula> getActivesByTarea(Integer dtaId,Integer eventoId);
	
	public List<CelCelula> getByCodEstadoTarea(@Param("tpoCodigoEstado") String tpoCodigoEstado);
}
