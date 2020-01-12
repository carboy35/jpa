package cl.servel.gasto.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.RenRendicion;
import cl.servel.gasto.transients.IngresoGastoDTO;
import cl.servel.gasto.transients.PagoPendiente;

public interface RenRendicionRepositoryCustom {
	public RenRendicion getRendicionByRut(@Param("rut") Integer rut, @Param("dv") String dv,
			@Param("eventoId") Integer eventoId, @Param("tpoEventoId") Integer tpoEventoId,
			@Param("tpoEleccionId") Integer tpoEleccionId, @Param("tpoRendicionId") Integer tpoRendicionId);
	public List<RenRendicion> getRendicionInstanciaFlujoByEvento(Integer eventoId,List<Integer> usuarioIds,Integer dtaId,Integer usuarioId);
	public List<RenRendicion> getRendicionDinamica(Optional<Integer> idEvento,Optional<Integer> idCandidato,Optional<Integer> idPartido,Optional<Integer> eleccionId,Optional<Integer> idTipoRendicion);
	public void deleteByRendicion(int idRendicion);
	
	public List<PagoPendiente> getReembolsosPendientes(int eveId, int tipoSeccion, String tipoCodigo, String nombreAtributo, int tipoNomina, boolean creditoMandato);
	
	public LinkedList<IngresoGastoDTO> getIngresosGasto(int eveId, int tipoEvento, int tipoEleccion, long desde, long hasta);
}
