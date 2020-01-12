package cl.servel.gasto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.servel.gasto.entity.DinDetalleIngresos;

public interface DinDetalleIngresosRepository extends CrudRepository<DinDetalleIngresos,Integer>,DinDetalleIngresosRepositoryCustom {
	@Query("SELECT tbl FROM DinDetalleIngresos tbl WHERE tbl.ingIngresos.ingId = :ingId")			
	Optional<List<DinDetalleIngresos>> getByCandidatoPartidoAdmId(@Param("ingId") int ingId);

	@Query("SELECT d FROM DinDetalleIngresos d WHERE d.ingIngresos.ingId = :ingId")
	public List<DinDetalleIngresos> getByIngreso(@Param("ingId") int ingId);

	@Query("SELECT d FROM DinDetalleIngresos d WHERE d.ingIngresos.ingId = :ingId and d.dinNombreAtributo=:atributo")
	public List<DinDetalleIngresos> getByIngresoAndAtributo(@Param("ingId") int ingId,@Param("atributo") String atributo);
}
