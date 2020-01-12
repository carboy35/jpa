package cl.servel.gasto.repository;

import org.springframework.data.repository.CrudRepository;

import cl.servel.gasto.entity.ExExpediente;
import cl.servel.gasto.repository.custom.GenericCustomRepository;

public interface ExExpedienteRepository extends CrudRepository<ExExpediente, Integer>,GenericCustomRepository<ExExpediente> {

}
