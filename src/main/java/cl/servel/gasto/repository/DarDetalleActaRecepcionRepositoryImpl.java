package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.entity.AcrActaRecepcion;
import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.DarDetalleActaRecepcion;
import cl.servel.gasto.entity.ParPartido;

public class DarDetalleActaRecepcionRepositoryImpl implements DarDetalleActaRecepcionRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	@Modifying
	public void deleteRlcDetalleActaRendicionByDetalleActaRecepcion(int darId) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("delete from dar_detalle_acta_recepcion where acr_id = ?");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, darId);

		query.executeUpdate();
	}

	@Transactional
	@Modifying
	public void deleteByActaRecepcion(int actaRecepcionId) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("delete from dar_detalle_acta_recepcion where acr_id = ?");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, actaRecepcionId);

		query.executeUpdate();
	}

	@Transactional
	@Modifying
	public void deleteByPartido(int partidoId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("delete from dar_detalle_acta_recepcion where par_id = ?");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, partidoId);

		query.executeUpdate();
	}

	@Transactional
	@Modifying
	public void deleteByCandidato(int candidatoId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("delete from dar_detalle_acta_recepcion where can_id = ?");

		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, candidatoId);

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<DarDetalleActaRecepcion> obtenerActasPorRut(String rut, Integer eventoId, Integer tipoEventoId, String arcEstado) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select da.dar_id,da.acr_id,da.par_id,da.can_id,da.dar_created ");
		queryString.append("from can_candidato c ");
		queryString.append("left join dar_detalle_acta_recepcion da ");
		queryString.append("on c.can_id=da.can_id ");		
		queryString.append("left join acr_acta_recepcion ac ");
		queryString.append("on ac.acr_id = da.acr_id ");				
		queryString.append("join ele_eleccion e ");
		queryString.append("on e.eve_id_eve=c.eve_id ");
		queryString.append("left join ren_rendicion r ");
		queryString.append("on r.can_id=c.can_id ");
		queryString.append("where UPPER(CONCAT(c.can_rut,'-', c.can_rut_dv))=UPPER('" + rut + "')");
		queryString.append("and e.eve_id_eve= ? ");
		queryString.append("and e.tpo_evento_id= ? ");
		queryString.append("and da.acr_id is not null ");		
		queryString.append("and ac.acr_estado = '" + arcEstado + "' ");		
		queryString.append("group by da.dar_id,da.acr_id,da.par_id,da.can_id,da.dar_created ");	
		queryString.append("union ");
		queryString.append("select da.dar_id,da.acr_id,da.par_id,da.can_id,da.dar_created ");
		queryString.append("from par_partido p ");
		queryString.append("left join dar_detalle_acta_recepcion da ");
		queryString.append("on p.par_id= da.par_id ");		
		queryString.append("left join acr_acta_recepcion ac ");
		queryString.append("on ac.acr_id = da.acr_id ");				
		queryString.append("join ele_eleccion e ");
		queryString.append("on e.ele_id=p.ele_id ");
		queryString.append("left join ren_rendicion r ");
		queryString.append("on r.par_id= p.par_id ");
		queryString.append("where UPPER(p.par_rut)=UPPER('" + rut + "')");
		queryString.append("and e.eve_id_eve= ? ");
		queryString.append("and e.tpo_evento_id= ? ");
		queryString.append("and da.acr_id is not null ");
		queryString.append("and ac.acr_estado = '" + arcEstado + "' ");
		queryString.append("group by da.dar_id,da.acr_id,da.par_id,da.can_id,da.dar_created ");	
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eventoId);
		query.setParameter(2, tipoEventoId);
		query.setParameter(3, eventoId);
		query.setParameter(4, tipoEventoId);

		List<DarDetalleActaRecepcion> detalleList = (List<DarDetalleActaRecepcion>) query.getResultList();
		List<DarDetalleActaRecepcion> detalleListResult = new ArrayList<>();
		DarDetalleActaRecepcion detalle;
		AcrActaRecepcion acrActaRecepcion;
		CanCandidato candidato;
		ParPartido partido;
		Integer candidatoId;
		Integer partidoId;

		for (Object record : detalleList) {
			Object[] fields = (Object[]) record;
			detalle = new DarDetalleActaRecepcion();

			detalle.setDarId((Integer) fields[0]);
			acrActaRecepcion = new AcrActaRecepcion();
			acrActaRecepcion.setAcrId((Integer) fields[1]);
			detalle.setAcrActaRecepcion(acrActaRecepcion);

			partidoId = (Integer) fields[2];
			if (partidoId != null) {
				partido = new ParPartido();
				partido.setParId((Integer) fields[2]);
				detalle.setParPartido(partido);
			}

			candidatoId = (Integer) fields[3];
			if (candidatoId != null) {
				candidato = new CanCandidato();
				candidato.setCanId((Integer) fields[3]);
				detalle.setCanCandidato(candidato);
			}

			detalle.setDarCreated((Date) fields[4]);
			detalleListResult.add(detalle);
		}

		return detalleListResult;
	}
}
