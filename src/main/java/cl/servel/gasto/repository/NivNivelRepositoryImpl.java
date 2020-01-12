package cl.servel.gasto.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.entity.DetDetalleNomina;
import cl.servel.gasto.entity.EveEventoEleccionario;
import cl.servel.gasto.entity.NivNivel;
import cl.servel.gasto.entity.TpoNivel;

public class NivNivelRepositoryImpl {
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	@Modifying
	public void deleteByEleccionId(int eleccionId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("delete from niv_nivel n, sel_sub_eleccion s ");
		queryString.append("where n.niv_id=s.niv_id ");
		queryString.append("and s.ele_id= ? ");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, eleccionId);

		query.executeUpdate();
	}
	
	public NivNivel nivelByEventoNivel(int eventoId, int nivelId,String tipoNivelPadre) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("select n.niv_id,n.tpo_niv_codigo,n.eve_id,n.niv_descripcion,n.niv_orden,n.codigo_origen,n.niv_created,n.niv_eliminado,n.niv_modified "); 
		queryString.append("from niv_nivel n, rlc_rel_nivel rn ");
		queryString.append("WHERE  n.eve_id= :eventoId ");
		queryString.append("and n.niv_id=rn.niv_id_padre ");
		queryString.append("and rn.tpo_niv_codigo_padre= :tipoNivelPadre ");
		queryString.append("and rn.niv_id_hijo= :nivelId "); 

		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter("eventoId", eventoId);
		query.setParameter("nivelId", nivelId);
		query.setParameter("tipoNivelPadre", tipoNivelPadre);
		
		List<NivNivel> detalleNominaList=(List<NivNivel>)query.getResultList();
		List<NivNivel> detalleNominaListResult= new ArrayList<>();
		NivNivel nivel=null;
		TpoNivel tipoNivel=null;
		EveEventoEleccionario eveEventoEleccionario=null;
		for (Object record : detalleNominaList) {
			Object[] fields = (Object[]) record;
			nivel= new NivNivel();
			nivel.setNivId((Integer)fields[0]);
			if (fields[1]!=null) {
				tipoNivel= new TpoNivel();
				tipoNivel.setTpoNivCodigo((String)fields[1]);
				nivel.setTpoNivel(tipoNivel);
			}
			if (fields[2]!=null) {
				eveEventoEleccionario= new EveEventoEleccionario();
				eveEventoEleccionario.setEveId((Integer)fields[2]);
				nivel.setEveEventoEleccionario(eveEventoEleccionario);
			}
			nivel.setNivDescripcion((String)fields[3]);
			nivel.setNivOrden((Integer)fields[4]);
			nivel.setCodigoOrigen((String)fields[5]);
			nivel.setNivCreated((Date)fields[6]);
			nivel.setNivEliminado((Boolean)fields[7]);
			nivel.setNivModified((Date)fields[8]);
		}
			
		return nivel;

	}
}
