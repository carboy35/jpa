package cl.servel.gasto.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.AsgAsignacionCargaTarea;
import cl.servel.gasto.entity.DtaDefinicionTarea;
import cl.servel.gasto.entity.GutGrupoUsuarioTarea;
import cl.servel.gasto.entity.TpoEleccion;


public class AsgAsignacionCargaTareaRepositoryImpl implements AsgAsignacionCargaTareaRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	
	
	public List<AsgAsignacionCargaTarea> agregarUsuarios(Integer usuarioId,Integer celulaId) {
		StringBuilder queryString = new StringBuilder();
		
		
				queryString.append("select asg.asg_cantidad_lineas,asg.dta_id,asg.tpo_eleccion_id,asg.gut_id, asg.asg_id_asignacion_de_grupo,asg.asg_alta_complejidad, asg.asg_id ");
				queryString.append("from asg_asignacion_carga_tarea asg, ");
				queryString.append("rlc_perfil_definicion_tarea pt, rlc_usuario_perfil up ");
				queryString.append(",gut_grupo_usuario_tarea gut ");
				queryString.append("where pt.per_id=up.per_id ");
				queryString.append("and asg.dta_id=pt.dta_id ");
				queryString.append("and gut.gut_id=asg.gut_id ");
				queryString.append("and up.usu_id = :usuarioId ");
				queryString.append("and gut.cel_id= :celulaId ");
				
			
				Query query = entityManager.createNativeQuery(queryString.toString());
				query.setParameter("usuarioId", usuarioId);
				query.setParameter("celulaId", celulaId);

			
			List<AsgAsignacionCargaTarea> asignacionCargaList= (List<AsgAsignacionCargaTarea>)query.getResultList();
			List<AsgAsignacionCargaTarea> asignacionCargaResultList = new ArrayList<>();
			AsgAsignacionCargaTarea asignacionCarga=null;
			DtaDefinicionTarea definicionTarea=null;
			TpoEleccion tipoEleccion=null;
			GutGrupoUsuarioTarea gutGrupoUsuarioTarea=null;
			BigInteger cantidadLineas;
			for (Object record : asignacionCargaList) {
				Object[] fields = (Object[]) record;
				asignacionCarga= new AsgAsignacionCargaTarea();
				cantidadLineas=(BigInteger)fields[0];
				asignacionCarga.setAsgCantidadLineas(cantidadLineas.longValue());
				if (fields[1]!=null) {
					definicionTarea= new DtaDefinicionTarea();
					definicionTarea.setDtaId((Integer)fields[1]);
					asignacionCarga.setDtaDefinicionTarea(definicionTarea);
				}
				if(fields[2]!=null) {
					tipoEleccion= new TpoEleccion();
					tipoEleccion.setTpoEleccionId((Integer)fields[2]);
					asignacionCarga.setTpoEleccion(tipoEleccion);
				}
				if (fields[3]!=null) {
					gutGrupoUsuarioTarea= new GutGrupoUsuarioTarea();
					gutGrupoUsuarioTarea.setGutId((Integer)fields[3]);
					asignacionCarga.setGutGrupoUsuarioTarea(gutGrupoUsuarioTarea);
				}
				
				asignacionCarga.setAsgIdAsignacionDeGrupo((Long)fields[4]);
				
				asignacionCarga.setAsgAltaComplejidad((Boolean)fields[5]);
				
				asignacionCarga.setAsgId((Integer)fields[6]);
				
				
				asignacionCargaResultList.add(asignacionCarga);
				
				}
			return asignacionCargaResultList;
				
				
			}
}
