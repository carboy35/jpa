package cl.servel.gasto.repository.custom.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import cl.servel.gasto.repository.custom.GenericCustomRepository;

public class GenericCustomRepositoryImpl<E> implements GenericCustomRepository<E> {

	@PersistenceContext
	private EntityManager entityManager;

	public Class<?> clazz(int position) {
		try {
			Type sooper = getClass().getGenericSuperclass();
			Type t = ((ParameterizedType) sooper).getActualTypeArguments()[position];

			return Class.forName(t.toString().split(" ")[1]);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByState(String entityName, Boolean state, String campoHabilidato) {
		String query = "SELECT e FROM " + entityName + " e WHERE e." + campoHabilidato + "=:state";
		return this.entityManager.createQuery(query).setParameter("state", state).getResultList();
	}

	@Override
	@Modifying
	@Transactional
	public void deleteLogical(String entityName, Integer id, Boolean state, String campoId, String campoHabilidato) {
		String queryUpdate = "UPDATE " + entityName + " e SET e." + campoHabilidato + "=:state WHERE e." + campoId
				+ "=:id";
		this.entityManager.createQuery(queryUpdate).setParameter("state", state).setParameter("id", id).executeUpdate();
	}

	@Override
	@Modifying
	@Transactional
	public void changeState(String entityName, Integer id, Boolean state, String campoId, String campoHabilidato) {
		String queryUpdate = "UPDATE " + entityName + " e SET e." + campoHabilidato + "=:state WHERE e." + campoId
				+ "=:id";
		this.entityManager.createQuery(queryUpdate).setParameter("id", id).setParameter("state", state).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findByIdRlcAOneToOne(String entityName, String subEntityName, Integer idRlcA, String campIdRlcA) {
		String query = "SELECT e FROM " + entityName + " e WHERE e." + subEntityName + "." + campIdRlcA + "=:idRlcA";
		return (E) this.entityManager.createQuery(query).setParameter("idRlcA", idRlcA).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByIdRlcAOneToMany(String entityName, String subEntityName, Integer idRlcA, String campIdRlcA) {
		String query = "SELECT e FROM " + entityName + " e WHERE e." + subEntityName + "." + campIdRlcA + "=:idRlcA";
		return this.entityManager.createQuery(query).setParameter("idRlcA", idRlcA).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findByIdAndState(String entityName, Integer id, Boolean state, String campoId, String campoHabilidato) {
		String query = "SELECT e FROM " + entityName + " e WHERE e." + campoHabilidato + " =:state and e." + campoId
				+ "=:id";
		return (E) this.entityManager.createQuery(query).setParameter("id", id).setParameter("state", state)
				.getSingleResult();
	}

	@Override
	public List<E> findByTipoDocumentoAndCandidatoOrPartido(String tipoDocumento, String candidatoPartido,
			Integer candidatoPartidoId) {
		String campo="canCandidato.canId";
		if(candidatoPartido.equals("partido")) campo="parPartido.parId";
		String query = "select dde from DdeDetalleDocumentosExternos dde inner join ExExpediente expe on dde.dexDocumentoExterno.dexId=expe.dexDocumentoExterno.dexId " + 
				"where expe."+campo+"=:candidatoPartidoId and dde.ddeTipoDocumento=:tipoDocumento ";
		return this.entityManager.createQuery(query).setParameter("candidatoPartidoId", candidatoPartidoId).setParameter("tipoDocumento", tipoDocumento)
				.getResultList();
	}

}
