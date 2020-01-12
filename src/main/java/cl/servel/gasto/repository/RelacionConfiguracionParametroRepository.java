package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.servel.gasto.entity.RlcConfiguracionParametro;


@Repository("relacionConfiguracionParametroRepository")
public interface RelacionConfiguracionParametroRepository extends CrudRepository<RlcConfiguracionParametro, Long> {

}
