package cl.servel.gasto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cl.servel.gasto.transients.PagoPendiente;

public class NomNominaRepositoryImpl implements NomNominaRepositoryCustom {
	private static final Logger LOG = LoggerFactory.getLogger(NomNominaRepositoryImpl.class);
	
	@Autowired
	private DataSource ds;

	public List<PagoPendiente> getPagosPendientesMujerOrRemanente(int eveId, int idTipoSeccionRendicion) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		List<PagoPendiente> pagos = null;
		
		try {
			String queryString = null;
			
			queryString = getQueryPagosPendientesMujerOrRemanente();
			
			con = ds.getConnection();
			ps = con.prepareStatement(queryString);
			
			ps.setInt(1, idTipoSeccionRendicion);
			ps.setInt(2, eveId);
		
			ps.execute();
			
			rs = ps.getResultSet();
			
			PagoPendiente pago = null;
			pagos = new ArrayList<PagoPendiente>();
			Map<Integer, PagoPendiente> mapaPagos = new HashMap<Integer, PagoPendiente>();
			
			while (rs.next()) {
				if (!mapaPagos.containsKey(rs.getInt("par_id"))) {
					pago = new PagoPendiente();
					pago.setRenId(rs.getInt("det_id"));
					pago.setParId(rs.getInt("par_id"));
					pago.setFecha(rs.getDate("ren_fecha"));
					pago.setMontoReembolso(0D);
					pago.setSumaPas(0D);
					pago.setSumaNomina(0D);
					pago.setTotalPagar(0D);
					pago.setOficios(new ArrayList<Integer>());
					
					mapaPagos.put(rs.getInt("par_id"), pago);
				}
				
				mapaPagos.get(rs.getInt("par_id")).setTotalPagar(pago.getTotalPagar() + rs.getDouble("total_pagar"));
				mapaPagos.get(rs.getInt("par_id")).getOficios().add(rs.getInt("oficio"));
			}
			
			pagos.addAll(mapaPagos.values());
		} catch (SQLException e) {
			LOG.error("Ocurrio un error al consultar los reembolsos pendientes", e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return pagos;
	}
		
	private String getQueryPagosPendientesMujerOrRemanente() {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("SELECT t.det_id, ");
		queryString.append("	   t.par_id, ");
		queryString.append("	   t.ren_fecha,  ");
		queryString.append("	   t.total_pagar, ");
		queryString.append("	   t.pagado, ");
		queryString.append("	   t.oficio ");
		queryString.append("  FROM ( ");
		queryString.append("		SELECT r.ren_id, ");
		queryString.append("		       r.par_id, ");
		queryString.append("		       r.ren_fecha, ");
		queryString.append("		       cast(jsonb_array_elements(detalle)->>'montoTotal' as float) as total_pagar, ");
		queryString.append("		       cast(jsonb_array_elements(detalle)->>'pagado' as boolean) as pagado, ");
		queryString.append("		       cast(jsonb_array_elements(detalle)->>'oficio' as int) as oficio, ");
		queryString.append("		       x.det_id ");
		queryString.append("		 FROM ren_rendicion r ");
		queryString.append("		 JOIN ( ");
		queryString.append("		        SELECT dr.det_valor, jsonb((dr.det_valor)::jsonb) AS detalle, dr.det_id, dr.ren_id ");
		queryString.append("		          FROM det_detalle_rendicion dr ");
		queryString.append("		         WHERE id_tipo_seccion = ? ");
		queryString.append("		         ) AS x ON r.ren_id = x.ren_id ");
		queryString.append("		WHERE r.par_ID is not null and r.EVE_ID = ?) as t  ");
		queryString.append(" WHERE t.pagado = false ");
		queryString.append(" GROUP BY t.det_id, ");
		queryString.append("	      t.par_id, ");
		queryString.append("	   	  t.ren_fecha,  ");
		queryString.append("	      t.total_pagar, ");
		queryString.append("	      t.pagado, ");
		queryString.append(" 	      t.oficio ");
		
		return queryString.toString();
	}
		
		
}
