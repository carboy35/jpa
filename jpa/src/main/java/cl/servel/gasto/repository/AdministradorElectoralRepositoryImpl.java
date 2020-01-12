package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.AdmAdministradorElectoral;
import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.PacPacto;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.SelSubEleccion;
import cl.servel.gasto.entity.TpoEvento;
import cl.servel.gasto.transients.AdministradorElectoralCandidatoPartido;

public class AdministradorElectoralRepositoryImpl implements AdministradorElectoralRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<AdministradorElectoralCandidatoPartido> buscarAdministradores(Integer idEvento, Integer tipoEvento,
			Integer idEleccion, List<String> campos, String valor, Boolean estado, List<String> filtros) {

		Query queryAdminCandidatos = null;
		Query queryAdminPartidos = null;

		queryAdminCandidatos = entityManager.createNativeQuery(this.devuelveConsultaBusquedaAdministradoresCandidatos(
				idEvento, tipoEvento, idEleccion, campos, valor, estado, filtros));
		queryAdminPartidos = entityManager.createNativeQuery(this.devuelveConsultaBusquedaAdministradoresPartidos(
				idEvento, tipoEvento, idEleccion, campos, valor, estado, filtros));

		List<Object[]> administradoresCandidatosList = (List<Object[]>) queryAdminCandidatos.getResultList();
		List<Object[]> administradoresPartidosList = (List<Object[]>) queryAdminPartidos.getResultList();

		List<AdministradorElectoralCandidatoPartido> administradoresListResult = new ArrayList<>();
		
		administradoresListResult.addAll(this.procesarAdminPartido(administradoresPartidosList));
		administradoresListResult.addAll(this.procesarAdminCandidato(administradoresCandidatosList));

		return administradoresListResult;
	}

	private List<AdministradorElectoralCandidatoPartido> procesarAdminCandidato(List<Object[]> administradoresList) {
		List<AdministradorElectoralCandidatoPartido> administradoresListResult = new ArrayList<>();

		AdmAdministradorElectoral administrador;

		CanCandidato candidato;
		EveEventoEleccionario eveEventoEleccionario = null;
		EleEleccion eleEleccion = null;
		TpoEvento tpoEvento = null;
		SelSubEleccion selSubEleccion = null;

		// 0 q.adm_id,
		// 1 q.adm_nombre,
		// 2 q.adm_app_paterno,
		// 3 q.adm_app_materno,
		// 4 q.adm_rut,
		// 5 q.adm_rut_dv ,
		// 6 q.can_id,
		// 7 q.eve_id,
		// 8 q.eve_nombre,
		// 9 q.tpo_evento_id,
		// 10 q.tpo_evento_nombre,
		// 11 q.ele_id,
		// 12 q.tpo_ele_nombre,
		// 13 q.can_nombres,
		// 14 q.can_app_paterno,
		// 15 q.can_app_materno,
		// 16 q.can_rut,
		// 17 q.can_rut_dv,
		// 18 q.can_habilitado,
		// 19 q.sel_id

		for (Object[] fields : administradoresList) {
			administrador = new AdmAdministradorElectoral();

			administrador.setAdmId((Integer) fields[0]);
			administrador.setAdmNombre((String) fields[1]);
			administrador.setAdmAppPaterno((String) fields[2]);
			administrador.setAdmAppMaterno((String) fields[3]);

			administrador.setAdmRut((String) fields[4]);
			administrador.setAdmRutDv((Character) fields[5]);

			candidato = new CanCandidato();

			candidato.setCanId((Integer) fields[0+6]);
			candidato.setCanNombres((String) fields[7+6]);
			candidato.setCanAppPaterno((String) fields[8+6]);
			candidato.setCanAppMaterno((String) fields[9+6]);

			candidato.setCanRut((Integer) fields[10+6]);
			candidato.setCanRutDv((String) fields[11+6]);

			eveEventoEleccionario = new EveEventoEleccionario();
			eveEventoEleccionario.setEveId((Integer) fields[1+6]);
			eveEventoEleccionario.setEveNombre((String) fields[2+6]);

			tpoEvento = new TpoEvento();
			tpoEvento.setTpoEventoId((Integer) fields[3+6]);
			tpoEvento.setTpoEventoNombre((String) fields[4+6]);

			eleEleccion = new EleEleccion();
			eleEleccion.setEleId((Integer) fields[5+6]);
			eleEleccion.setEleNombre((String) fields[6+6]);
			eleEleccion.setTpoEvento(tpoEvento);

			selSubEleccion = new SelSubEleccion();
			selSubEleccion.setSelId((Integer) fields[13+6]);
			selSubEleccion.setEleEleccion(eleEleccion);

			candidato.setSelSubEleccion(selSubEleccion);
			candidato.setEveEventoEleccionario(eveEventoEleccionario);

			candidato.setCanHabilitado((Boolean) fields[12+6]);

			administrador.setEveEventoEleccionario(eveEventoEleccionario);
			
			administradoresListResult.add(AdministradorElectoralCandidatoPartido.builder()
			.admAdministradorElectoral(administrador).canCandidato(candidato).build());
		}

		return administradoresListResult;
	}

	private List<AdministradorElectoralCandidatoPartido> procesarAdminPartido(List<Object[]> administradoresList) {
		List<AdministradorElectoralCandidatoPartido> administradoresListResult = new ArrayList<>();

		AdmAdministradorElectoral administrador;
		EveEventoEleccionario eveEventoEleccionario = null;
		EleEleccion eleEleccion = null;
		TpoEvento tpoEvento = null;

		ParPartido parPartido = null;
		PacPacto pacto = null;

		// 0 q.adm_id,
		// 1 q.adm_nombre,
		// 2 q.adm_app_paterno,
		// 3 q.adm_app_materno,
		// 4 q.adm_rut,
		// 5 q.adm_rut_dv ,

		// 6 q.par_id,
		// 7 q.eve_id,
		// 8 q.eve_nombre,
		// 9 q.tpo_evento_id,
		// 10 q.tpo_evento_nombre,
		// 11 q.ele_id,
		// 12 q.tpo_ele_nombre,
		// 13 q.par_nombre,
		// 14 q.par_sigla,
		// 15 q.par_eliminado,
		// 16 q.par_rut,
		// 17 q.pac_id

		for (Object[] fields : administradoresList) {
			administrador = new AdmAdministradorElectoral();

			administrador.setAdmId((Integer) fields[0]);
			administrador.setAdmNombre((String) fields[1]);
			administrador.setAdmAppPaterno((String) fields[2]);
			administrador.setAdmAppMaterno((String) fields[3]);

			administrador.setAdmRut((String) fields[4]);
			administrador.setAdmRutDv((Character) fields[5]);

			parPartido = new ParPartido();

			parPartido.setParId((Integer) fields[0 + 6]);

			eveEventoEleccionario = new EveEventoEleccionario();
			eveEventoEleccionario.setEveId((Integer) fields[1 + 6]);
			eveEventoEleccionario.setEveNombre((String) fields[2 + 6]);

			tpoEvento = new TpoEvento();
			tpoEvento.setTpoEventoId((Integer) fields[3 + 6]);
			tpoEvento.setTpoEventoNombre((String) fields[4 + 6]);

			eleEleccion = new EleEleccion();
			eleEleccion.setEleId((Integer) fields[5 + 6]);
			eleEleccion.setEleNombre((String) fields[6 + 6]);
			eleEleccion.setTpoEvento(tpoEvento);
			eleEleccion.setEveEventoEleccionario(eveEventoEleccionario);

			parPartido.setEleEleccion(eleEleccion);

			parPartido.setParNombre((String) fields[7 + 6]);
			parPartido.setParSigla((String) fields[8 + 6]);
			parPartido.setParEliminado((Boolean) fields[9 + 6]);
			parPartido.setParRut((String) fields[10 + 6]);
			if (fields[11 + 6] != null) {
				pacto = new PacPacto();
				pacto.setPacId((Integer) fields[11 + 6]);
				parPartido.setPacPacto(pacto);
			}

			administrador.setEveEventoEleccionario(eveEventoEleccionario);

			administradoresListResult.add(AdministradorElectoralCandidatoPartido.builder()
					.admAdministradorElectoral(administrador).parPartido(parPartido).build());
		}

		return administradoresListResult;
	}

	private String devuelveConsultaBusquedaAdministradoresCandidatos(Integer idEvento, Integer tipoEvento,
			Integer idEleccion, List<String> campos, String valor, Boolean estado, List<String> filtros) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select  ");

		queryString.append("q.adm_id,");
		queryString.append("q.adm_nombre,");
		queryString.append("q.adm_app_paterno,");
		queryString.append("q.adm_app_materno,");
		queryString.append("q.adm_rut,");
		queryString.append("q.adm_rut_dv,");

		queryString.append("q.can_id,  ");
		queryString.append("q.eve_id, ");
		queryString.append("q.eve_nombre, ");
		queryString.append("q.tpo_evento_id, ");
		queryString.append("q.tpo_evento_nombre, ");
		queryString.append("q.ele_id,q.tpo_ele_nombre,  ");
		queryString.append("q.can_nombres,  ");
		queryString.append("q.can_app_paterno,  ");
		queryString.append("q.can_app_materno, ");
		queryString.append("q.can_rut,   ");
		queryString.append("q.can_rut_dv,  ");
		queryString.append("q.can_habilitado,   ");
		queryString.append("q.sel_id   ");
		queryString.append("from (  ");
		queryString.append("select   ");
		queryString.append("distinct a.adm_id,");
		queryString.append("a.adm_nombre,");
		queryString.append("a.adm_app_paterno,");
		queryString.append("a.adm_app_materno,");
		queryString.append("a.adm_rut,");
		queryString.append("a.adm_rut_dv,");
		queryString.append("c.can_id,   ");
		queryString.append("c.eve_id,ev.eve_nombre, ");
		queryString.append("el.tpo_evento_id, ");
		queryString.append("te.tpo_evento_nombre,  ");
		queryString.append("c.sel_id,el.ele_id, ");
		queryString.append("el.ele_nombre, ");
		queryString.append("tel.tpo_ele_nombre, ");
		queryString.append("c.can_nombres, ");
		queryString.append("c.can_app_paterno,  ");
		queryString.append("c.can_app_materno,  ");
		queryString.append("c.can_rut,   ");
		queryString.append("can_rut_dv, ");
		queryString.append("can_habilitado  ");
		queryString.append("from   ");
		queryString.append("can_candidato c, ");
		queryString.append("eve_evento_eleccionario ev, ");
		queryString.append("sel_sub_eleccion s, ");
		queryString.append("ele_eleccion el, ");
		queryString.append("tpo_eleccion tel, ");
		queryString.append("tpo_evento te ,");

		queryString.append("adm_administrador_electoral a,");
		queryString.append("his_historico h ");

		queryString.append("where   ");
		queryString.append("c.eve_id = ev.eve_id and  ");
		queryString.append("c.sel_id = s.sel_id and  ");
		queryString.append("el.ele_id = s.ele_id  and  ");
		queryString.append("tel.tpo_eleccion_id = el.tpo_eleccion_id and ");
		queryString.append("te.tpo_evento_id = el.tpo_evento_id and ");

		queryString.append("a.adm_id = h.adm_id and ");
		queryString.append("c.can_id = h.can_id ");

		if (idEvento != null && tipoEvento == null && idEleccion == null)
			queryString.append(" and c.eve_id = " + idEvento);

		if (idEvento != null && tipoEvento != null)
			queryString.append(" and c.eve_id = " + idEvento + "  and el.tpo_evento_id = " + tipoEvento);

		if (idEvento != null && tipoEvento != null && idEleccion != null)
			queryString.append("and c.eve_id = " + idEvento + "  and el.tpo_evento_id = " + tipoEvento
					+ "and el.ele_id =" + idEleccion);

		queryString.append(") q ");
		queryString.append("where  ");

		campos.forEach(campo -> {

			if (campo != null) {

				if (campo.equalsIgnoreCase("adm_rut")) {
					queryString.append(
							" concat(cast(q." + campo + " as varchar),'-', q.adm_rut_dv) like '%" + valor + "%'");
				} else {
					if (campo.equalsIgnoreCase("adm_nombre")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q." + campo
								+ " as varchar),' ',cast(q.adm_app_paterno as varchar),' ',cast(q.adm_app_materno as varchar)))), '\\s+', ' ') like  upper('%"
								+ valor + "%')");
					}

					if (campo.equalsIgnoreCase("adm_app_paterno")) {
						queryString.append(
								" or REGEXP_REPLACE(upper(trim(concat(cast(q.adm_nombre as varchar),' ',cast(q." + campo
										+ "  as varchar),' ',cast(q.adm_app_materno as varchar)))), '\\s+', ' ') like  upper('%"
										+ valor + "%')");
					}

					if (campo.equalsIgnoreCase("adm_app_materno")) {
						queryString.append(
								" or REGEXP_REPLACE(upper(trim(concat(cast(q.adm_nombre as varchar),' ',cast(q.adm_app_paterno as varchar),' ',cast(q."
										+ campo + "  as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					queryString.append(" or REGEXP_REPLACE(upper(cast(q." + campo
							+ " as varchar)), '\\s+', ' ') like upper('%" + valor + "%')");
				}

			}

		});

		return queryString.toString();

	}

	private String devuelveConsultaBusquedaAdministradoresPartidos(Integer idEvento, Integer tipoEvento,
			Integer idEleccion, List<String> campos, String valor, Boolean estado, List<String> filtros) {

		StringBuilder queryString = new StringBuilder();

		queryString.append("select  ");
		queryString.append("q.adm_id,");
		queryString.append("q.adm_nombre,");
		queryString.append("q.adm_app_paterno,");
		queryString.append("q.adm_app_materno,");
		queryString.append("q.adm_rut,");
		queryString.append("q.adm_rut_dv,");
		queryString.append("q.par_id, ");
		queryString.append("q.eve_id,");
		queryString.append("q.eve_nombre,");
		queryString.append("q.tpo_evento_id,");
		queryString.append("q.tpo_evento_nombre,");
		queryString.append("q.ele_id,");
		queryString.append("q.tpo_ele_nombre, ");
		queryString.append("q.par_nombre, ");
		queryString.append("q.par_sigla, ");
		queryString.append("q.par_eliminado, ");
		queryString.append("q.par_rut,  ");
		queryString.append("q.pac_id  ");
		queryString.append("from ( ");
		queryString.append("select  ");
		queryString.append("distinct a.adm_id,");
		queryString.append("a.adm_nombre,");
		queryString.append("a.adm_app_paterno,");
		queryString.append("a.adm_app_materno,");
		queryString.append("a.adm_rut,");
		queryString.append("a.adm_rut_dv,");
		queryString.append("p.par_id, ");
		queryString.append("ev.eve_id,");
		queryString.append("ev.eve_nombre,");
		queryString.append("el.tpo_evento_id,");
		queryString.append("te.tpo_evento_nombre, ");
		queryString.append("el.ele_id,");
		queryString.append("el.ele_nombre,");
		queryString.append("tel.tpo_ele_nombre,");
		queryString.append("p.par_nombre, ");
		queryString.append("p.par_sigla, ");
		queryString.append("p.par_eliminado, ");
		queryString.append("p.par_rut, ");
		queryString.append("p.pac_id ");
		queryString.append("FROM  ");
		queryString.append("par_partido p, ");
		queryString.append("ele_eleccion el,");
		queryString.append("eve_evento_eleccionario ev,");
		queryString.append("tpo_eleccion tel,");
		queryString.append("tpo_evento te,");
		queryString.append("adm_administrador_electoral a,");
		queryString.append("his_historico h ");
		queryString.append("where  ");
		queryString.append("p.ele_id = el.ele_id and ");
		queryString.append("el.eve_id_eve = ev.eve_id and ");
		queryString.append("el.tpo_eleccion_id = tel.tpo_eleccion_id and ");
		queryString.append("el.tpo_evento_id = te.tpo_evento_id and ");
		queryString.append("a.adm_id = h.adm_id and ");
		queryString.append("p.par_id = h.par_id ");

		if (idEvento != null && tipoEvento == null && idEleccion == null)
			queryString.append(" and el.eve_id_eve =" + idEvento + " and a.eve_id =" + idEvento);

		if (idEvento != null && tipoEvento != null && idEleccion == null)
			queryString.append(" and el.eve_id_eve =" + idEvento + "and a.eve_id = " + idEvento
					+ " and el.tpo_evento_id =" + tipoEvento);

		if (idEvento != null && tipoEvento != null && idEleccion != null)
			queryString.append(" and el.eve_id_eve =" + idEvento + "and a.eve_id = " + idEvento
					+ " and el.tpo_evento_id = " + tipoEvento + " and el.ele_id =" + idEleccion);

		queryString.append(") q ");
		queryString.append("where ");

		campos.forEach(campo -> {

			if (campo != null) {

				if (campo.equalsIgnoreCase("adm_rut")) {
					queryString.append(
							" concat(cast(q." + campo + " as varchar),'-', q.adm_rut_dv) like '%" + valor + "%'");
				} else {
					if (campo.equalsIgnoreCase("adm_nombre")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q." + campo
								+ " as varchar),' ',cast(q.adm_app_paterno as varchar),' ',cast(q.adm_app_materno as varchar)))), '\\s+', ' ') like  upper('%"
								+ valor + "%')");
					}

					if (campo.equalsIgnoreCase("adm_app_paterno")) {
						queryString.append(
								" or REGEXP_REPLACE(upper(trim(concat(cast(q.adm_nombre as varchar),' ',cast(q." + campo
										+ "  as varchar),' ',cast(q.adm_app_materno as varchar)))), '\\s+', ' ') like  upper('%"
										+ valor + "%')");
					}

					if (campo.equalsIgnoreCase("adm_app_materno")) {
						queryString.append(
								" or REGEXP_REPLACE(upper(trim(concat(cast(q.adm_nombre as varchar),' ',cast(q.adm_app_paterno as varchar),' ',cast(q."
										+ campo + "  as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					queryString.append(" or REGEXP_REPLACE(upper(cast(q." + campo
							+ " as varchar)), '\\s+', ' ') like upper('%" + valor + "%')");
				}

			}

		});

		return queryString.toString();

	}

}
