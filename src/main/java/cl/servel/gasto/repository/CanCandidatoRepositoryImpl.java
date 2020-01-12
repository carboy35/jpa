package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.EleEleccion;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.PacPacto;
import cl.servel.gasto.entity.ParPartido;
import cl.servel.gasto.entity.SelSubEleccion;
import cl.servel.gasto.entity.SpaPacto;
import cl.servel.gasto.entity.TpoCandidato;
import cl.servel.gasto.entity.TpoEvento;

@SuppressWarnings("unchecked")
public class CanCandidatoRepositoryImpl implements CanCandidatoRepositoryCustom {

	private static final Logger LOG = LoggerFactory.getLogger(ResResumenCalculosRepositoryCustom.class);

	@PersistenceContext
	EntityManager entityManager;

	public List<CanCandidato> leerCandidatosPaginados(Integer idEleccion, Integer pageNumber, Integer pageSize, String orden, String campo, String valor, String estado) {

		Query query = entityManager.createNativeQuery(this.devuelveConsultaCandidatos(orden, campo, valor, estado));
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		query.setParameter(1, idEleccion);

		List<CanCandidato> candidatosList = (List<CanCandidato>) query.getResultList();

		List<CanCandidato> candidatosListResult = new ArrayList<>();
		CanCandidato candidato;
		EveEventoEleccionario eveEventoEleccionario = null;
		PacPacto pacPacto = null;
		ParPartido parPartido = null;
		SpaPacto spaPacto = null;
		SelSubEleccion selSubEleccion = null;
		TpoCandidato tpoCandidato = null;

		for (Object record : candidatosList) {
			Object[] fields = (Object[]) record;
			candidato = new CanCandidato();

			// Falta realizar el resto del ampping de los campos
			candidato.setCanId((Integer) fields[0]);
			candidato.setCanNombres((String) fields[12]);
			candidato.setCanAppPaterno((String) fields[13]);
			candidato.setCanAppMaterno((String) fields[14]);

			candidato.setCanRut((Integer) fields[24]);
			candidato.setCanRutDv((String) fields[25]);

			eveEventoEleccionario = new EveEventoEleccionario();
			eveEventoEleccionario.setEveId((Integer) fields[1]);
			candidato.setEveEventoEleccionario(eveEventoEleccionario);

			pacPacto = new PacPacto();
			pacPacto.setPacId((Integer) fields[4]);
			pacPacto.setPacNombre((String) fields[5]);
			candidato.setPacPacto(pacPacto);

			parPartido = new ParPartido();
			parPartido.setParId((Integer) fields[10]);
			parPartido.setParNombre((String) fields[11]);
			candidato.setParPartido(parPartido);

			selSubEleccion = new SelSubEleccion();
			selSubEleccion.setSelId((Integer) fields[3]);
			candidato.setSelSubEleccion(selSubEleccion);
			candidato.setCanCodigoOrigen((String) fields[2]);

			spaPacto = new SpaPacto();
			spaPacto.setSpaId((Integer) fields[8]);
			spaPacto.setSpaNombre((String) fields[9]);
			candidato.setSpaPacto(spaPacto);

			tpoCandidato = new TpoCandidato();
			tpoCandidato.setTpoCandidatoId((Integer) fields[6]);
			tpoCandidato.setTpoNombre((String) fields[7]);
			candidato.setTpoCandidato(tpoCandidato);

			candidato.setCanSexo((String) fields[15]);
			candidato.setCanMail((String) fields[16]);
			candidato.setCanDirPostal((String) fields[17]);
			candidato.setCanDireccion((String) fields[18]);
			candidato.setCanComuna((String) fields[19]);
			candidato.setCanFechaNacimiento((String) fields[20]);
			candidato.setCanTelefonoFijo((String) fields[21]);
			candidato.setCanTelefonoCelu((String) fields[22]);
			candidato.setCanNumeroEnVoto((Integer) fields[23]);
			candidato.setCanEstado((String) fields[26]);
			candidato.setCanEstadoCandidatura((String) fields[27]);
			candidato.setCanEliminado((Boolean) fields[28]);
			candidato.setCanCreated((Date) fields[29]);
			candidato.setCanModified((Date) fields[30]);
			candidato.setCanCantidadVotos((Integer) fields[31]);
			candidato.setCanHabilitado((Boolean) fields[32]);

			candidatosListResult.add(candidato);
		}

		return candidatosListResult;
	}

	private String devuelveConsultaCandidatos(String orden, String campo, String valor, String estado) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select ");
		queryString.append("c.can_id, "); // 0
		queryString.append("c.eve_id, "); // 1
		queryString.append("c.can_codigo_origen, "); // 2
		queryString.append("c.sel_id, "); // 3
		queryString.append("c.pac_id, "); // 4
		queryString.append("p.pac_nombre, "); // 5
		queryString.append("c.tpo_can_id, "); // 6
		queryString.append("t.tpo_nombre, "); // 7
		queryString.append("c.sup_id, "); // 8
		queryString.append("spa.spa_nombre, "); // 9
		queryString.append("c.par_id, "); // 10
		queryString.append("pa.par_nombre, "); // 11
		queryString.append("c.can_nombres, "); // 12
		queryString.append("c.can_app_paterno, "); // 13
		queryString.append("c.can_app_materno, "); // 14
		queryString.append("can_sexo, ");// 15
		queryString.append("can_mail, ");// 16
		queryString.append("can_dir_postal, "); // 17
		queryString.append("can_direccion, "); // 18
		queryString.append("can_comuna, "); // 19
		queryString.append("can_fecha_nacimiento, "); // 20
		queryString.append("can_telefono_fijo, "); // 21
		queryString.append("can_telefono_celu, "); // 22
		queryString.append("can_numero_en_voto, "); // 23
		queryString.append("c.can_rut, "); // 24
		queryString.append("can_rut_dv, "); // 25
		queryString.append("can_estado, "); // 26
		queryString.append("can_estado_candidatura, "); // 27
		queryString.append("can_eliminado, "); // 28
		queryString.append("can_created, "); // 29
		queryString.append("can_modified, "); // 30
		queryString.append("can_cantidad_votos, "); // 31
		queryString.append("can_habilitado, "); // 32
		queryString.append("n.niv_descripcion, "); // 33
		queryString.append("concat( ad.adm_rut, '-', ad.adm_rut_dv) adm_rut, "); // 34
		queryString.append("concat(ad.adm_nombre,' ',ad.adm_app_paterno,' ',ad.adm_app_materno) adm_nombre, "); // 35
		queryString.append("ad.adm_mail "); // 36
		queryString.append("from ");
		queryString.append("can_candidato c  ");
		queryString.append("left join pac_pacto p ");
		queryString.append("on c.pac_id=p.pac_id ");
		queryString.append("left join tpo_candidato t ");
		queryString.append("on c.tpo_can_id=t.tpo_candidato_id ");
		queryString.append("left join spa_pacto spa ");
		queryString.append("on c.sup_id=spa.spa_id ");
		queryString.append("left join par_partido pa ");
		queryString.append("on c.par_id=pa.par_id ");
		queryString.append("left join his_historico h ");
		queryString.append("on c.can_id= h.can_id and h.his_estado = 'ok' ");
		queryString.append("left join adm_administrador_electoral  ad ");
		queryString.append("on ad.adm_id=h.adm_id ");
		queryString.append("join sel_sub_eleccion s ");
		queryString.append("on c.sel_id=s.sel_id ");
		queryString.append("join niv_nivel n ");
		queryString.append("on s.niv_id= n.niv_id ");
		queryString.append("where ");
		queryString.append("s.ele_id = ? ");

		if (estado.equalsIgnoreCase("true")) {
			queryString.append("and c.can_habilitado = TRUE ");
		} else if (estado.equalsIgnoreCase("false")) {
			queryString.append("and c.can_habilitado = FALSE ");
		}

		if (campo != null) {
			switch (campo) {
			case "can_rut":
				queryString.append("and concat(cast(" + campo + " as varchar),'-', can_rut_dv) like '%" + valor + "%'");
				break;
			case "adm_rut":
				queryString.append("and concat(cast(" + campo + " as varchar),'-', adm_rut_dv) like '%" + valor + "%'");
				break;
			default:
				queryString.append("and upper(cast(" + campo + " as varchar)) like upper('%" + valor + "%')");
				break;
			}
		}
		if (orden != null) {
			queryString.append("order by ");
			queryString.append(orden);
		}
		return queryString.toString();

	}

	private String devuelveConsultaCandidatosByAdministrador(int rut, String dv, int idEvento, int idTpoEvento, int idTpoEleccion) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select ");
		queryString.append("c.can_id, "); // 0
		queryString.append("c.eve_id, "); // 1
		queryString.append("c.can_codigo_origen, "); // 2
		queryString.append("c.sel_id, "); // 3
		queryString.append("c.pac_id, "); // 4
		queryString.append("p.pac_nombre, "); // 5
		queryString.append("c.tpo_can_id, "); // 6
		queryString.append("t.tpo_nombre, "); // 7
		queryString.append("c.sup_id, "); // 8
		queryString.append("spa.spa_nombre, "); // 9
		queryString.append("c.par_id, "); // 10
		queryString.append("pa.par_nombre, "); // 11
		queryString.append("c.can_nombres, "); // 12
		queryString.append("c.can_app_paterno, "); // 13
		queryString.append("c.can_app_materno, "); // 14
		queryString.append("can_sexo, ");// 15
		queryString.append("can_mail, ");// 16
		queryString.append("can_dir_postal, "); // 17
		queryString.append("can_direccion, "); // 18
		queryString.append("can_comuna, "); // 19
		queryString.append("can_fecha_nacimiento, "); // 20
		queryString.append("can_telefono_fijo, "); // 21
		queryString.append("can_telefono_celu, "); // 22
		queryString.append("can_numero_en_voto, "); // 23
		queryString.append("c.can_rut, "); // 24
		queryString.append("can_rut_dv, "); // 25
		queryString.append("can_estado, "); // 26
		queryString.append("can_estado_candidatura, "); // 27
		queryString.append("can_eliminado, "); // 28
		queryString.append("can_created, "); // 29
		queryString.append("can_modified, "); // 30
		queryString.append("can_cantidad_votos, "); // 31
		queryString.append("can_habilitado, "); // 32
		queryString.append("n.niv_descripcion, "); // 33
		queryString.append("concat( ad.adm_rut, '-', ad.adm_rut_dv) adm_rut, "); // 34
		queryString.append("concat(ad.adm_nombre,' ',ad.adm_app_paterno,' ',ad.adm_app_materno) adm_nombre, "); // 35
		queryString.append("ad.adm_mail "); // 36

		queryString.append("from ");
		queryString.append("can_candidato c  ");

		queryString.append("left join pac_pacto p ");
		queryString.append("on c.pac_id=p.pac_id ");
		queryString.append("left join tpo_candidato t ");
		queryString.append("on c.tpo_can_id=t.tpo_candidato_id ");
		queryString.append("left join spa_pacto spa ");
		queryString.append("on c.sup_id=spa.spa_id ");
		queryString.append("left join par_partido pa ");
		queryString.append("on c.par_id=pa.par_id ");
		queryString.append("left join his_historico h ");
		queryString.append("on c.can_id= h.can_id ");
		queryString.append("left join adm_administrador_electoral  ad ");
		queryString.append("on ad.adm_id=h.adm_id ");
		queryString.append("join sel_sub_eleccion s ");
		queryString.append("on c.sel_id=s.sel_id ");
		queryString.append("join ele_eleccion ele ");
		queryString.append("on ele.ele_id = s.ele_id ");
		queryString.append("join niv_nivel n ");
		queryString.append("on s.niv_id= n.niv_id ");

		queryString.append("where ");
		queryString.append("h.his_id is not null ");
		queryString.append("and ad.adm_id is not null ");
		queryString.append("and ad.adm_rut = '" + rut + "' ");
		queryString.append("and ad.adm_rut_dv = '" + dv + "' ");
		queryString.append("and ele.eve_id_eve = " + idEvento + " ");
		queryString.append("and ele.tpo_evento_id = " + idTpoEvento + " ");
		queryString.append("and ele.tpo_eleccion_id = " + idTpoEleccion + " ");
		queryString.append("and c.can_habilitado = TRUE ");
		queryString.append("and ad.adm_eliminado = FALSE ");

		return queryString.toString();
	}

	public int totalCandidatosPaginados(Integer idEleccion, Integer pageNumber, Integer pageSize, String orden, String campo, String valor, String estado) {

		Query query = entityManager.createNativeQuery(this.devuelveConsultaCandidatos(orden, campo, valor, estado));
		query.setParameter(1, idEleccion);
		return query.getResultList().size();
	}

	public List<CanCandidato> findCandidatosByAdministrador(@Param("rut") int rut, @Param("dv") String dv, @Param("idEvento") int idEvento, @Param("idTpoEvento") int idTpoEvento, @Param("idTpoEleccion") int idTpoEleccion) {

		Query query = entityManager.createNativeQuery(this.devuelveConsultaCandidatosByAdministrador(rut, dv, idEvento, idTpoEvento, idTpoEleccion));
		// query.setParameter(1, idEleccion);

		List<CanCandidato> candidatosList = (List<CanCandidato>) query.getResultList();

		List<CanCandidato> candidatosListResult = new ArrayList<>();
		CanCandidato candidato;
		EveEventoEleccionario eveEventoEleccionario = null;
		PacPacto pacPacto = null;
		ParPartido parPartido = null;
		SpaPacto spaPacto = null;
		SelSubEleccion selSubEleccion = null;
		TpoCandidato tpoCandidato = null;

		for (Object record : candidatosList) {
			Object[] fields = (Object[]) record;
			candidato = new CanCandidato();

			candidato.setCanId((Integer) fields[0]);
			candidato.setCanNombres((String) fields[12]);
			candidato.setCanAppPaterno((String) fields[13]);
			candidato.setCanAppMaterno((String) fields[14]);

			candidato.setCanRut((Integer) fields[24]);
			candidato.setCanRutDv((String) fields[25]);

			eveEventoEleccionario = new EveEventoEleccionario();
			eveEventoEleccionario.setEveId((Integer) fields[1]);
			candidato.setEveEventoEleccionario(eveEventoEleccionario);

			pacPacto = new PacPacto();
			pacPacto.setPacId((Integer) fields[4]);
			pacPacto.setPacNombre((String) fields[5]);
			candidato.setPacPacto(pacPacto);

			parPartido = new ParPartido();
			parPartido.setParId((Integer) fields[10]);
			parPartido.setParNombre((String) fields[11]);
			candidato.setParPartido(parPartido);

			selSubEleccion = new SelSubEleccion();
			selSubEleccion.setSelId((Integer) fields[3]);
			candidato.setSelSubEleccion(selSubEleccion);
			candidato.setCanCodigoOrigen((String) fields[2]);

			spaPacto = new SpaPacto();
			spaPacto.setSpaId((Integer) fields[8]);
			spaPacto.setSpaNombre((String) fields[9]);
			candidato.setSpaPacto(spaPacto);

			tpoCandidato = new TpoCandidato();
			tpoCandidato.setTpoCandidatoId((Integer) fields[6]);
			tpoCandidato.setTpoNombre((String) fields[7]);
			candidato.setTpoCandidato(tpoCandidato);

			candidato.setCanSexo((String) fields[15]);
			candidato.setCanMail((String) fields[16]);
			candidato.setCanDirPostal((String) fields[17]);
			candidato.setCanDireccion((String) fields[18]);
			candidato.setCanComuna((String) fields[19]);
			candidato.setCanFechaNacimiento((String) fields[20]);
			candidato.setCanTelefonoFijo((String) fields[21]);
			candidato.setCanTelefonoCelu((String) fields[22]);
			candidato.setCanNumeroEnVoto((Integer) fields[23]);
			candidato.setCanEstado((String) fields[26]);
			candidato.setCanEstadoCandidatura((String) fields[27]);
			candidato.setCanEliminado((Boolean) fields[28]);
			candidato.setCanCreated((Date) fields[29]);
			candidato.setCanModified((Date) fields[30]);
			candidato.setCanCantidadVotos((Integer) fields[31]);
			candidato.setCanHabilitado((Boolean) fields[32]);

			candidatosListResult.add(candidato);
		}

		return candidatosListResult;
	}

	@Override
	public List<CanCandidato> buscarCandidatos(Integer idEvento, Integer tipoEvento, Integer idEleccion, List<String> campos, String valor, Boolean estado,String definicionTarea, List<String> filtros,Integer idTipoEleccion) {
		Query query = null;
		
		if (filtros.contains("respuestaObservacion")) {
			query = getQueryConsultaCandidatosRespuestaObservaciones(idEvento, tipoEvento, campos, valor, filtros);
		}else if (filtros.contains("cuentaCandidatoPartido")) {
			query = entityManager.createNativeQuery(this.devuelveConsultaBusquedaCandidatos(idEvento, tipoEvento,  campos, valor, estado,idTipoEleccion, filtros));
		} else {
			query = entityManager.createNativeQuery(this.devuelveConsultaBusquedaCandidatos(idEvento, tipoEvento, idEleccion, campos, valor, estado,definicionTarea, filtros));
		}
		List<Object[]> candidatosList = (List<Object[]>) query.getResultList();

		List<CanCandidato> candidatosListResult = new ArrayList<>();
		CanCandidato candidato;
		EveEventoEleccionario eveEventoEleccionario = null;
		EleEleccion eleEleccion = null;
		TpoEvento tpoEvento = null;
		SelSubEleccion selSubEleccion = null;

//		0 q.can_id, 
//		1 q.eve_id,
//		2 q.eve_nombre,
//		3 q.tpo_evento_id,
//		4 q.tpo_evento_nombre,
//		5 q.ele_id,
//      6 q.tpo_ele_nombre, 
//		7 q.can_nombres, 
//		8 q.can_app_paterno, 
//		9 q.can_app_materno, 
//		10 q.can_rut,  
//		11 q.can_rut_dv, 
//		12 q.can_habilitado 
//		13 q.sel_id

		for (Object[] fields : candidatosList) {
			candidato = new CanCandidato();

			candidato.setCanId((Integer) fields[0]);
			candidato.setCanNombres((String) fields[7]);
			candidato.setCanAppPaterno((String) fields[8]);
			candidato.setCanAppMaterno((String) fields[9]);

			candidato.setCanRut((Integer) fields[10]);
			candidato.setCanRutDv((String) fields[11]);

			eveEventoEleccionario = new EveEventoEleccionario();
			eveEventoEleccionario.setEveId((Integer) fields[1]);
			eveEventoEleccionario.setEveNombre((String) fields[2]);

			tpoEvento = new TpoEvento();
			tpoEvento.setTpoEventoId((Integer) fields[3]);
			tpoEvento.setTpoEventoNombre((String) fields[4]);

			eleEleccion = new EleEleccion();
			eleEleccion.setEleId((Integer) fields[5]);
			eleEleccion.setEleNombre((String) fields[6]);
			eleEleccion.setTpoEvento(tpoEvento);

			selSubEleccion = new SelSubEleccion();
			selSubEleccion.setSelId((Integer) fields[13]);
			selSubEleccion.setEleEleccion(eleEleccion);

			candidato.setSelSubEleccion(selSubEleccion);
			candidato.setEveEventoEleccionario(eveEventoEleccionario);

			candidato.setCanHabilitado((Boolean) fields[12]);

			candidatosListResult.add(candidato);
		}

		return candidatosListResult;
	}

	private String devuelveConsultaBusquedaCandidatos(Integer idEvento, Integer tipoEvento, Integer idEleccion, List<String> campos, String valor, Boolean estado,String definicionTarea, List<String> filtros) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select  ");
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
		queryString.append("tpo_evento te ");

		if (filtros.contains("rendicion")) {
			queryString.append(", ren_rendicion r ");
		}else if (filtros.contains("ingresoRecursoCandidato") || filtros.contains("ingresoSentenciasTricel")) {
			queryString.append(", int_instancia_tarea ins,dta_definicion_tarea dta,inf_instancia_flujo inf,ren_rendicion ren, det_detalle_rendicion detren,tpo_seccion_rendicion tposecc ");
		}
		

		queryString.append("where   ");
		queryString.append("c.eve_id = ev.eve_id and  ");
		queryString.append("c.sel_id = s.sel_id and  ");
		queryString.append("el.ele_id = s.ele_id  and  ");
		queryString.append("tel.tpo_eleccion_id = el.tpo_eleccion_id and ");
		queryString.append("te.tpo_evento_id = el.tpo_evento_id ");

		if (filtros.contains("rendicion")) {
			queryString.append(" and r.can_id = c.can_id ");
		}else if (filtros.contains("ingresoRecursoCandidato") || filtros.contains("ingresoRecursoCandidatoPartido") || filtros.contains("ingresoSentenciasTricel")) {
			queryString.append(" and ins.dta_id=dta.dta_id ");
			if (definicionTarea !=null) {
				queryString.append(" and dta.dta_codigo_origen= '" + definicionTarea + "'"); 
			}
			queryString.append(" and inf.inf_id= ins.inf_id ");
			queryString.append(" and inf.can_id=c.can_id ");
			
			queryString.append(" and ren.can_id=c.can_id ");
			queryString.append(" and ren.ren_id=detren.ren_id ");
			queryString.append(" and detren.id_tipo_seccion=tposecc.tpo_seccion_rendicion_id ");
			queryString.append(" and tposecc.tpo_nombre_seccion= 'totalReembolso'");
		}

		if (idEvento != null && tipoEvento == null && idEleccion == null)
			queryString.append(" and c.eve_id = " + idEvento);

		if (idEvento != null && tipoEvento != null)
			queryString.append(" and c.eve_id = " + idEvento + "  and el.tpo_evento_id = " + tipoEvento);

		if (idEvento != null && tipoEvento != null && idEleccion != null)
			queryString.append("and c.eve_id = " + idEvento + "  and el.tpo_evento_id = " + tipoEvento + "and el.ele_id =" + idEleccion);

		queryString.append(" and c.can_habilitado = " + estado);

		queryString.append(") q ");
		queryString.append("where  ");

		campos.forEach(campo -> {

			if (campo != null) {

				if (campo.equalsIgnoreCase("can_rut")) {
					queryString.append(" concat(cast(q." + campo + " as varchar),'-', q.can_rut_dv) like '%" + valor + "%'");
				} else {
					if (campo.equalsIgnoreCase("can_nombres")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q." + campo + " as varchar),' ',cast(q.can_app_paterno as varchar),' ',cast(q.can_app_materno as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					if (campo.equalsIgnoreCase("can_app_paterno")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q.can_nombres as varchar),' ',cast(q." + campo + "  as varchar),' ',cast(q.can_app_materno as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					if (campo.equalsIgnoreCase("can_app_materno")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q.can_nombres as varchar),' ',cast(q.can_app_paterno as varchar),' ',cast(q." + campo + "  as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					queryString.append(" or REGEXP_REPLACE(upper(cast(q." + campo + " as varchar)), '\\s+', ' ') like upper('%" + valor + "%')");
				}

			}

		});

		return queryString.toString();

	}
	private String devuelveConsultaBusquedaCandidatos(Integer idEvento, Integer tipoEvento, List<String> campos, String valor, Boolean estado,Integer idTipoEleccion, List<String> filtros) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select  ");
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
		queryString.append("tpo_evento te ");


		queryString.append("where   ");
		queryString.append("c.eve_id = ev.eve_id and  ");
		queryString.append("c.sel_id = s.sel_id and  ");
		queryString.append("el.ele_id = s.ele_id  and  ");
		queryString.append("tel.tpo_eleccion_id = el.tpo_eleccion_id and ");
		queryString.append("te.tpo_evento_id = el.tpo_evento_id ");


		if (idEvento != null )
			queryString.append(" and c.eve_id = " + idEvento);

		if ( tipoEvento != null)
			queryString.append(" and el.tpo_evento_id = " + tipoEvento);

		if ( idTipoEleccion != null)
			queryString.append( " and el.tpo_eleccion_id =" + idTipoEleccion);

		queryString.append(" and c.can_habilitado = " + estado);

		queryString.append(") q ");
		queryString.append("where  ");

		campos.forEach(campo -> {

			if (campo != null) {

				if (campo.equalsIgnoreCase("can_rut")) {
					queryString.append(" concat(cast(q." + campo + " as varchar),'-', q.can_rut_dv) like '%" + valor + "%'");
				} else {
					if (campo.equalsIgnoreCase("can_nombres")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q." + campo + " as varchar),' ',cast(q.can_app_paterno as varchar),' ',cast(q.can_app_materno as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					if (campo.equalsIgnoreCase("can_app_paterno")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q.can_nombres as varchar),' ',cast(q." + campo + "  as varchar),' ',cast(q.can_app_materno as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					if (campo.equalsIgnoreCase("can_app_materno")) {
						queryString.append(" or REGEXP_REPLACE(upper(trim(concat(cast(q.can_nombres as varchar),' ',cast(q.can_app_paterno as varchar),' ',cast(q." + campo + "  as varchar)))), '\\s+', ' ') like  upper('%" + valor + "%')");
					}

					queryString.append(" or REGEXP_REPLACE(upper(cast(q." + campo + " as varchar)), '\\s+', ' ') like upper('%" + valor + "%')");
				}

			}

		});

		return queryString.toString();

	}
	private Query getQueryConsultaCandidatosRespuestaObservaciones(Integer idEvento, Integer idTipoEvento, List<String> campos, String valor, List<String> filtros) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT "); 
		queryString.append("		Q.CAN_ID,  ");
		queryString.append("		Q.EVE_ID, ");
		queryString.append("		Q.EVE_NOMBRE, ");
		queryString.append("		Q.TPO_EVENTO_ID, ");
		queryString.append("		Q.TPO_EVENTO_NOMBRE, ");
		queryString.append("		Q.ELE_ID, ");
		queryString.append("		Q.TPO_ELE_NOMBRE, "); 
		queryString.append("		Q.CAN_NOMBRES,  ");
		queryString.append("		Q.CAN_APP_PATERNO,  ");
		queryString.append("		Q.CAN_APP_MATERNO, ");
		queryString.append("		Q.CAN_RUT,   ");
		queryString.append("		Q.CAN_RUT_DV,  ");
		queryString.append("		Q.CAN_HABILITADO,   ");
		queryString.append("		Q.SEL_ID   ");
		queryString.append("   FROM ( ");
		queryString.append("		   SELECT CAN.CAN_ID, ");
		queryString.append("		  		  EVE.EVE_ID, ");
		queryString.append("		  		  EVE.EVE_NOMBRE, ");
		queryString.append("		  		  TEV.TPO_EVENTO_ID, ");
		queryString.append("		  		  TEV.TPO_EVENTO_NOMBRE, ");
		queryString.append("		  		  ELE.ELE_ID, ");
		queryString.append("		  		  TEL.TPO_ELE_NOMBRE, ");
		queryString.append("		  		  CAN.CAN_NOMBRES, ");
		queryString.append("		  		  CAN.CAN_APP_PATERNO, ");
		queryString.append("		  		  CAN.CAN_APP_MATERNO, ");
		queryString.append("		  		  CAN.CAN_RUT, ");
		queryString.append("		  		  CAN.CAN_RUT_DV, ");
		queryString.append("		  		  CAN.CAN_HABILITADO, ");
		queryString.append("		  		  CAN.SEL_ID ");
		queryString.append("		    FROM CAN_CANDIDATO CAN,  ");
		queryString.append("		    	 EVE_EVENTO_ELECCIONARIO EVE, "); 
		queryString.append("		    	 TPO_EVENTO TEV, ");
		queryString.append("		    	 ELE_ELECCION ELE, ");
		queryString.append("		    	 SEL_SUB_ELECCION SEL, ");
		queryString.append("		    	 TPO_ELECCION TEL,  ");
		queryString.append("		    	 INF_INSTANCIA_FLUJO INF, "); 
		queryString.append("		    	 INT_INSTANCIA_TAREA INT ");
		queryString.append("		   WHERE EVE.EVE_ID = :eveId ");
		queryString.append("		   	 AND CAN.SEL_ID = SEL.SEL_ID ");
		queryString.append("		   	 AND SEL.ELE_ID = ELE.ELE_ID ");
		queryString.append("		   	 AND ELE.EVE_ID_EVE = EVE.EVE_ID ");
		queryString.append("		   	 AND ELE.TPO_ELECCION_ID = TEL.TPO_ELECCION_ID ");
		queryString.append("		   	 AND ELE.TPO_EVENTO_ID = TEV.TPO_EVENTO_ID ");
		queryString.append("		     AND INF.CAN_ID = CAN.CAN_ID ");
		queryString.append("		     AND INF.DTA_ID NOT IN ( ");
		queryString.append("		   								SELECT DEA.DTA_ID ");
		queryString.append("		     							  FROM DEA_DEFINICION_ACTIVIDAD DEA ");
		queryString.append("		    							 WHERE DEA.DEA_CODIGO = 'NO_RECIBE_RESPUESTAS' "); 
		queryString.append("		    						) ");
		queryString.append("		     GROUP BY CAN.CAN_ID, ");
		queryString.append("		  		  EVE.EVE_ID, ");
		queryString.append("		  		  EVE.EVE_NOMBRE, ");
		queryString.append("		  		  TEV.TPO_EVENTO_ID, ");
		queryString.append("		  		  TEV.TPO_EVENTO_NOMBRE, ");
		queryString.append("		  		  ELE.ELE_ID, ");
		queryString.append("		  		  TEL.TPO_ELE_NOMBRE, ");
		queryString.append("		  		  CAN.CAN_NOMBRES, ");
		queryString.append("		  		  CAN.CAN_APP_PATERNO, ");
		queryString.append("		  		  CAN.CAN_APP_MATERNO, ");
		queryString.append("		  		  CAN.CAN_RUT, ");
		queryString.append("		  		  CAN.CAN_RUT_DV, ");
		queryString.append("		  		  CAN.CAN_HABILITADO, ");
		queryString.append("		  		  CAN.SEL_ID ");
		queryString.append(") Q ");
		queryString.append("WHERE Q.TPO_EVENTO_ID = :idTipoEvento ");
		queryString.append("  AND ((Q.CAN_RUT || '-' || Q.CAN_RUT_DV) LIKE :valor OR (Q.CAN_NOMBRES || ' ' || Q.CAN_APP_PATERNO || ' ' || Q.CAN_APP_MATERNO) LIKE UPPER (:valor) )");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		query.setParameter("eveId", idEvento);
		query.setParameter("valor", "%" + valor + "%");
		query.setParameter("idTipoEvento", idTipoEvento);
		
		return query;
	}

	public List<CanCandidato> devuelveConsultaPartidosInstanciaFlujo(Integer eventoId, List<Integer> usuarioIds, Integer dtaId, Integer usuarioId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("select ");
		queryString.append("c.can_id, ");
		queryString.append("c.can_nombres, ");
		queryString.append("c.can_app_paterno, ");
		queryString.append("c.can_app_materno, ");
		queryString.append("c.can_sexo, ");
		queryString.append("c.can_mail, ");
		queryString.append("c.can_dir_postal, ");
		queryString.append("c.can_direccion, ");
		queryString.append("c.can_comuna, ");
		queryString.append("c.can_fecha_nacimiento, ");
		queryString.append("c.can_telefono_fijo, ");
		queryString.append("c.can_telefono_celu, ");
		queryString.append("c.can_numero_en_voto, ");
		queryString.append("c.can_rut, ");
		queryString.append("c.can_rut_dv, ");
		queryString.append("c.can_estado, ");
		queryString.append("c.can_estado_candidatura, ");
		queryString.append("c.can_eliminado, ");
		queryString.append("c.can_created, ");
		queryString.append("c.can_modified, ");
		queryString.append("c.eve_id, ");
		queryString.append("c.can_codigo_origen, ");
		queryString.append("c.sel_id, ");
		queryString.append("c.pac_id, ");
		queryString.append("c.tpo_can_id, ");
		queryString.append("c.sup_id, ");
		queryString.append("c.par_id, ");
		queryString.append("c.can_cantidad_votos, ");
		queryString.append("c.can_habilitado ");
		queryString.append("from can_candidato c,inf_instancia_flujo inf, int_instancia_tarea ins ");
		queryString.append("where c.can_id=inf.can_id ");
		queryString.append("and ins.inf_id=inf.inf_id ");
		if (usuarioId == null) {
			queryString.append("and (ins.inf_id,ins.int_id) in (select inf2.inf_id,max(i.int_id) ");
			queryString.append("from int_instancia_tarea i, inf_instancia_flujo inf2 where i.inf_id=inf2.inf_id and inf2.inf_id=inf.inf_id group by inf2.inf_id) ");
		}

		if (eventoId != null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		if (usuarioIds != null) {
			queryString.append("and inf.usu_id_actual in :usuarioIds ");
		}
		if (usuarioId != null) {
			queryString.append("and ins.usu_id= :usuarioId ");
		}
		if (dtaId != null) {
			queryString.append("and ins.dta_id = :dtaId ");
		}

		Query query = entityManager.createNativeQuery(queryString.toString());
		if (eventoId != null) {
			query.setParameter("eventoId", eventoId);
		}
		if (usuarioIds != null) {
			query.setParameter("usuarioIds", usuarioIds);
		}
		if (dtaId != null) {
			query.setParameter("dtaId", dtaId);
		}
		if (usuarioId != null) {
			query.setParameter("usuarioId", usuarioId);
		}

		List<CanCandidato> candidatoList = (List<CanCandidato>) query.getResultList();
		List<CanCandidato> candidatoListResult = new ArrayList<>();
		CanCandidato candidato = null;
		SelSubEleccion subeleccion = null;
		EveEventoEleccionario evento = null;
		PacPacto pacto = null;
		TpoCandidato tipoCandidato = null;
		SpaPacto subpacto = null;
		ParPartido partido = null;
		for (Object record : candidatoList) {
			Object[] fields = (Object[]) record;
			candidato = new CanCandidato();
			candidato.setCanId((Integer) fields[0]);
			candidato.setCanNombres((String) fields[1]);
			candidato.setCanAppPaterno((String) fields[2]);
			candidato.setCanAppMaterno((String) fields[3]);
			candidato.setCanSexo((String) fields[4]);
			candidato.setCanMail((String) fields[5]);
			candidato.setCanDirPostal((String) fields[6]);
			candidato.setCanDireccion((String) fields[7]);
			candidato.setCanComuna((String) fields[8]);
			candidato.setCanFechaNacimiento((String) fields[9]);
			candidato.setCanTelefonoFijo((String) fields[10]);
			candidato.setCanTelefonoCelu((String) fields[11]);
			candidato.setCanNumeroEnVoto((Integer) fields[12]);
			candidato.setCanRut((Integer) fields[13]);
			candidato.setCanRutDv((String) fields[14]);
			candidato.setCanEstado((String) fields[15]);
			candidato.setCanEstadoCandidatura((String) fields[16]);
			candidato.setCanEliminado((Boolean) fields[17]);
			candidato.setCanCreated((Date) fields[18]);
			candidato.setCanModified((Date) fields[19]);
			if (fields[20] != null) {
				evento = new EveEventoEleccionario();
				evento.setEveId((Integer) fields[20]);
				candidato.setEveEventoEleccionario(evento);

			}

			candidato.setCanCodigoOrigen((String) fields[21]);
			if (fields[22] != null) {
				subeleccion = new SelSubEleccion();
				subeleccion.setSelId((Integer) fields[22]);
				candidato.setSelSubEleccion(subeleccion);
			}

			if (fields[23] != null) {
				pacto = new PacPacto();
				pacto.setPacId((Integer) fields[23]);
				candidato.setPacPacto(pacto);

			}

			if (fields[24] != null) {
				tipoCandidato = new TpoCandidato();
				tipoCandidato.setTpoCandidatoId((Integer) fields[24]);
				candidato.setTpoCandidato(tipoCandidato);
			}

			if (fields[25] != null) {
				subpacto = new SpaPacto();
				subpacto.setSpaId((Integer) fields[25]);
				candidato.setSpaPacto(subpacto);
			}

			if (fields[26] != null) {
				partido = new ParPartido();
				partido.setParId((Integer) fields[26]);
				candidato.setParPartido(partido);
			}

			candidato.setCanCantidadVotos((Integer) fields[27]);
			candidato.setCanHabilitado((Boolean) fields[28]);
			candidatoListResult.add(candidato);
		}
		
		return candidatoListResult;
	}
}
