package cl.servel.gasto.repository.custom;

import java.util.List;

public interface GenericCustomRepository<E> {

	public E findByIdAndState(String entityName, Integer id, Boolean state, String campoId, String campoHabilidato);

	public List<E> findAllByState(String entityName, Boolean state, String campoHabilidato);

	public void deleteLogical(String entityName, Integer id, Boolean state, String campoId, String campoHabilidato);

	public void changeState(String entityName, Integer id, Boolean state, String campoId, String campoHabilidato);

	public E findByIdRlcAOneToOne(String entityName, String subEntityName, Integer idRlcA, String campIdRlcA);

	public List<E> findByIdRlcAOneToMany(String entityName, String subEntityName, Integer idRlcA, String campIdRlcA);
	
	public List<E> findByTipoDocumentoAndCandidatoOrPartido(String tipoDocumento,String candidatoPartido,Integer candidatoPartidoId);
}
