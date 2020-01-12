package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.servel.gasto.entity.UsuUsuarios;

public class UsuUsuariosRepositoryImpl implements UsuUsuariosRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	@SuppressWarnings("unchecked")
	public List<UsuUsuarios> devuelveUsuariosTareas(Integer eventoId){
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ");
		queryString.append("u.usu_id, ");
		queryString.append("u.usu_rut, ");
		queryString.append("u.usu_rut_dv, ");
		queryString.append("u.usu_nombre, ");
		queryString.append("u.usu_eliminado, ");
		queryString.append("u.created, ");
		queryString.append("u.modified, ");
		queryString.append("u.usu_nombre_acceso ");
		queryString.append("from ");
		queryString.append("inf_instancia_flujo inf, ");
		queryString.append("int_instancia_tarea i, "); 
		queryString.append("usu_usuarios u ");
		queryString.append("where ");
		queryString.append("inf.inf_id = i.inf_id ");
		queryString.append("and u.usu_id = i.usu_id ");

		if (eventoId !=null) {
			queryString.append("and inf.eve_id= :eventoId ");
		}
		
		 Query query = entityManager.createNativeQuery(queryString.toString());
		 if (eventoId !=null) {
				query.setParameter("eventoId", eventoId);
		 }
		 
		 
		 List<UsuUsuarios> listUsuarios= (List<UsuUsuarios>) query.getResultList();
		 List<UsuUsuarios> listUsuariosResult= new ArrayList<>();
		 UsuUsuarios usuario=null;
		 for (Object record:listUsuarios ) {
			 Object[] fields = (Object[]) record;
			 usuario= new UsuUsuarios();
			 usuario.setUsuId((Integer)fields[0]);
			 usuario.setUsuRut((String) fields[1]);
			 usuario.setUsuRutDv((String)fields[2]);
			 usuario.setUsuNombre((String)fields[3]);
			 usuario.setUsuEliminado((Boolean)fields[4]);
			 usuario.setCreated((Date)fields[5]);
			 usuario.setModified((Date)fields[6]);
			 usuario.setUsuNombreAcceso((String)fields[7]);
			 listUsuariosResult.add(usuario);
		 }
		 return listUsuariosResult;
		
	}
	
	public List<UsuUsuarios> devuelveUsuariosDefinicionTarea(Integer dtaId){
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ");
		queryString.append("u.usu_id, ");
		queryString.append("u.usu_rut, ");
		queryString.append("u.usu_rut_dv, ");
		queryString.append("u.usu_nombre, ");
		queryString.append("u.usu_eliminado, ");
		queryString.append("u.created, ");
		queryString.append("u.modified, ");
		queryString.append("u.usu_nombre_acceso ");
		queryString.append("from usu_usuarios u,rlc_usuario_perfil rp, per_perfil p, ust_usuario_tarea ut, ");
		queryString.append("rlc_perfil_definicion_tarea rpt ");
		queryString.append("where u.usu_id=rp.usu_id ");
		queryString.append("and p.per_id=rp.per_id ");
		queryString.append("and ut.usu_id= u.usu_id ");
		queryString.append("and p.per_id=rpt.per_id ");
		if (dtaId !=null) {
		queryString.append("and rpt.dta_id= :dtaId ");
		}
		queryString.append("group by u.usu_id ");
		
		
		 Query query = entityManager.createNativeQuery(queryString.toString());
		 if (dtaId !=null) {
				query.setParameter("dtaId", dtaId);
		 }
		 
		 
		 List<UsuUsuarios> listUsuarios= (List<UsuUsuarios>) query.getResultList();
		 List<UsuUsuarios> listUsuariosResult= new ArrayList<>();
		 UsuUsuarios usuario=null;
		 for (Object record:listUsuarios ) {
			 Object[] fields = (Object[]) record;
			 usuario= new UsuUsuarios();
			 usuario.setUsuId((Integer)fields[0]);
			 usuario.setUsuRut((String) fields[1]);
			 usuario.setUsuRutDv((String)fields[2]);
			 usuario.setUsuNombre((String)fields[3]);
			 usuario.setUsuEliminado((Boolean)fields[4]);
			 usuario.setCreated((Date)fields[5]);
			 usuario.setModified((Date)fields[6]);
			 usuario.setUsuNombreAcceso((String)fields[7]);
			 listUsuariosResult.add(usuario);
		 }
		 return listUsuariosResult;
		
	}
	
	public List<UsuUsuarios> devuelveUsuariosInstanciaFlujo(Integer infId,String codigoTarea,Integer dtaId){
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select ");
		queryString.append("u.usu_id, ");
		queryString.append("u.usu_rut, ");
		queryString.append("u.usu_rut_dv, ");
		queryString.append("u.usu_nombre, ");
		queryString.append("u.usu_eliminado, ");
		queryString.append("u.created, ");
		queryString.append("u.modified, ");
		queryString.append("u.usu_nombre_acceso ");
		queryString.append("from inf_instancia_flujo inf, int_instancia_tarea ins,dta_definicion_tarea dta,usu_usuarios u ");
		queryString.append("where inf.inf_id=ins.inf_id ");
		queryString.append("and dta.dta_id=ins.dta_id ");
		queryString.append("and inf.inf_id= :infId ");
		if (codigoTarea !=null) {
		queryString.append("and dta.dta_codigo_origen= :codigoTarea ");
		}
		if (dtaId !=null) {
			queryString.append("and dta.dta_id= :dtaId ");
			}
		queryString.append("and u.usu_id=ins.usu_id ");
		queryString.append("group by u.usu_id ");
		
		
		 Query query = entityManager.createNativeQuery(queryString.toString());
				query.setParameter("infId", infId);
				if (codigoTarea !=null) {
				query.setParameter("codigoTarea", codigoTarea);
				}
				
				if (dtaId !=null) {
					query.setParameter("dtaId", dtaId);
				}
		 
		 
		 List<UsuUsuarios> listUsuarios= (List<UsuUsuarios>) query.getResultList();
		 List<UsuUsuarios> listUsuariosResult= new ArrayList<>();
		 UsuUsuarios usuario=null;
		 for (Object record:listUsuarios ) {
			 Object[] fields = (Object[]) record;
			 usuario= new UsuUsuarios();
			 usuario.setUsuId((Integer)fields[0]);
			 usuario.setUsuRut((String) fields[1]);
			 usuario.setUsuRutDv((String)fields[2]);
			 usuario.setUsuNombre((String)fields[3]);
			 usuario.setUsuEliminado((Boolean)fields[4]);
			 usuario.setCreated((Date)fields[5]);
			 usuario.setModified((Date)fields[6]);
			 usuario.setUsuNombreAcceso((String)fields[7]);
			 listUsuariosResult.add(usuario);
		 }
		 return listUsuariosResult;
		
	}
}
