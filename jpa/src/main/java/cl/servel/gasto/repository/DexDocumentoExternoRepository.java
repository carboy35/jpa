package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.DexDocumentoExterno;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

@Repository("dexDocumentoExternoRepository")
public interface DexDocumentoExternoRepository
		extends CrudRepository<DexDocumentoExterno, Integer>, GenericCustomRepository<DexDocumentoExterno> {

}
