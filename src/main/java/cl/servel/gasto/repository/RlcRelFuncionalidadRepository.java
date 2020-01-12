package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.RlcRelFuncionalidad;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface RlcRelFuncionalidadRepository
		extends CrudRepository<RlcRelFuncionalidad, Integer>, GenericCustomRepository<RlcRelFuncionalidad> {

}
