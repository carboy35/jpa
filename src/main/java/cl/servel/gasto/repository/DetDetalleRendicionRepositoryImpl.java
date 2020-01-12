package cl.servel.gasto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.servel.gasto.transients.CambioEstadoLineaDetalleDTO;
import cl.servel.gasto.transients.CambioLineaDetalle;

@SuppressWarnings("unchecked")
public class DetDetalleRendicionRepositoryImpl implements DetDetalleRendicionRepositoryCustom {
	private static final Log LOG = LogFactory.getLog(DetDetalleRendicionRepositoryImpl.class);
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private DataSource ds;
	
	@Transactional
	@Modifying
	public void deleteByIdRendicion(int idRendicion) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("DELETE FROM det_detalle_rendicion WHERE ren_id = ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idRendicion);
		
		query.executeUpdate();
	}
	
	@Transactional
	@Modifying
	public void deleteByIdRendicionTipoSeccion(int idRendicion,int idTipoSeccion) {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("DELETE FROM det_detalle_rendicion WHERE ren_id = ? and id_tipo_seccion= ?");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		query.setParameter(1, idRendicion);
		query.setParameter(2, idTipoSeccion);
		
		query.executeUpdate();
	}
	private HashMap<String,Boolean> getLineaDeshabilitada( int detId,Date desde, Date hasta) {
		Query query = entityManager.createNativeQuery("SELECT bit_id, bit_entidad_registrada,bit_valor_registrado,bit_fecha_registro "
				+ " FROM bit_bitacora WHERE bit_entidad_registrada = 'DetRendicion' AND  bit_accion='CB_ESTADO' AND id_entidad_registrada=? AND bit_fecha_registro BETWEEN ? AND ?  ORDER by bit_id asc");
		query.setParameter(1,detId);
		query.setParameter(2, desde);
		query.setParameter(3, hasta);
		HashMap<String,Boolean> cambiosEstado=new HashMap<String,Boolean>();
		List<Object[]> resultEstado = query.getResultList();
		CambioEstadoLineaDetalleDTO cambioEstado=null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			for (Object[] obj : resultEstado) {
				cambioEstado = objectMapper.readValue((String)obj[2],CambioEstadoLineaDetalleDTO.class);
				if(cambioEstado.getHabilitado()==false) {
					cambiosEstado.put(cambioEstado.getNumeroLinea().toString()+"-"+cambioEstado.getNumeroPagina()+"-"+cambioEstado.getCodigoFormulario().replace("F",""), cambioEstado.getHabilitado());
				}
			}
		}catch(Exception ex){
			LOG.info("Error al serializar linea detalle cambio estado ");
			ex.printStackTrace();
		}
		return cambiosEstado;
	}
	public List<CambioLineaDetalle> getUltimosCambiosDetalle(int detId, Date desde, Date hasta, String historicoOriginal) {
		
		Query query = entityManager.createNativeQuery(getQueryCambiosDetalle());
		
		query.setParameter(1, historicoOriginal);
		query.setParameter(2, detId);
		query.setParameter(3, detId);
		query.setParameter(4, desde);
		query.setParameter(5, hasta);
		
		List<Object[]> result = query.getResultList();
		CambioLineaDetalle cambio = null;
		HashMap<String,Boolean> lineasDeshabilitadas=getLineaDeshabilitada(detId,  desde,  hasta);
		HashMap<String,Boolean> lineasDeshabilitadasAgregadas=new HashMap<String,Boolean>();
		HashMap<String,CambioLineaDetalle> lineasFinales=new HashMap<String,CambioLineaDetalle>();
		String key="";
		for (Object[] obj : result) {
			cambio = new CambioLineaDetalle();
			cambio.setFormulario((String) obj[0]);
			cambio.setHoja(Integer.parseInt((String)obj[1]));
			cambio.setLinea(Integer.parseInt((String)obj[2]));
			cambio.setFecha((Date) obj[4]);
			
			key=Integer.parseInt((String)obj[2])+"-"+Integer.parseInt((String)obj[1])+"-"+((String) obj[0].toString().replace("FORMULARIO ", ""));
			
			if(lineasDeshabilitadas.containsKey(key)) {
				if(lineasDeshabilitadasAgregadas.containsKey(key)==false) {
					cambio.setConcepto("Deshabilidata");
					lineasFinales.put(key,cambio);
				}
				lineasDeshabilitadasAgregadas.put(key,true);
			}else {
				cambio.setConcepto((String) obj[3]);
				cambio.setDetalleInicial((String) obj[5]);
				cambio.setDetalleFinal((String) obj[6]);
				lineasFinales.put(key,cambio);
			}
		}
		
		return new ArrayList<CambioLineaDetalle>(lineasFinales.values());
	}
	
	private String getQueryCambiosDetalle() {
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("SELECT DISTINCT ");
		queryString.append("	            TR.TPO_NOMBRE AS FORMULARIO, ");
		queryString.append("	            Y.NUMERO_PAGINA AS PAGINA, ");
		queryString.append("	            Y.NUMERO_LINEA AS LINEA, ");
		queryString.append("	            Y.ATRIBUTO_MODIFICADO AS CONCEPTO, ");
		queryString.append("	            Y.BIT_FECHA_REGISTRO AS FECHA_MODIFICACION, ");
		queryString.append("	            ( ");
		queryString.append("	               SELECT DETALLE_INICIAL.DET ->> Y.ATRIBUTO_MODIFICADO ");
		queryString.append("	                FROM ( ");
		queryString.append("	                        SELECT JSON_ARRAY_ELEMENTS (B2.BIT_VALOR_REGISTRADO :::: JSON) AS DET ");
		queryString.append("	                          FROM BIT_BITACORA B2 ");
		queryString.append("	                         WHERE B2.BIT_ENTIDAD_REGISTRADA = 'DetRendicion' ");
		queryString.append("	                           AND B2.BIT_ACCION = ?  AND B2.ID_ENTIDAD_REGISTRADA=? ");
		queryString.append("	                     ) AS DETALLE_INICIAL ");
		queryString.append("	                WHERE DETALLE_INICIAL.DET ->> 'numeroLinea' = Y.NUMERO_LINEA ");
		queryString.append("	                  AND DETALLE_INICIAL.DET ->> 'numeroPagina' = Y.NUMERO_PAGINA ");
		queryString.append("	            ) AS VALOR_ORIGINAL, ");
		queryString.append("	            MAX(Y.NUEVO_VALOR) AS VALOR_FINAL ");
		queryString.append("	       FROM DET_DETALLE_RENDICION DR ");
		queryString.append("	       JOIN ( ");
		queryString.append("	             SELECT X.DETALLE ->>'numeroLinea' as NUMERO_LINEA, ");
		queryString.append("	                    X.DETALLE ->>'numeroPagina' as NUMERO_PAGINA, ");
		queryString.append("	                    X.DETALLE ->>'valorModificado' as NUEVO_VALOR, ");
		queryString.append("	                    X.BIT_ID, ");
		queryString.append("	                    X.BIT_FECHA_REGISTRO, ");
		queryString.append("	                    X.USU_ID, ");
		queryString.append("	                    X.ID_ENTIDAD_REGISTRADA, ");
		queryString.append("	                    X.BIT_ATRIBUTO_ENTIDAD AS ATRIBUTO_MODIFICADO ");
		queryString.append("	               FROM ( ");
		queryString.append("	                           SELECT JSONB ((B.BIT_VALOR_REGISTRADO) :::: JSON) AS DETALLE, ");
		queryString.append("	                                  B.BIT_ID, ");
		queryString.append("	                                  B.BIT_FECHA_REGISTRO, ");
		queryString.append("	                                  B.USU_ID, ");
		queryString.append("	                                  B.ID_ENTIDAD_REGISTRADA, ");
		queryString.append("	                                  B.BIT_ATRIBUTO_ENTIDAD ");
		queryString.append("	                             FROM BIT_BITACORA B ");
		queryString.append("	                            WHERE B.BIT_ENTIDAD_REGISTRADA = 'DetRendicion' ");
		queryString.append("	                              AND BIT_ATRIBUTO_ENTIDAD <> 'valor' ");
		queryString.append("	                              AND B.ID_ENTIDAD_REGISTRADA = ? ");
		queryString.append("	                              AND B.BIT_FECHA_REGISTRO BETWEEN ? AND ? ");
		queryString.append("	                       ) AS X ");
		queryString.append("	             ) AS Y ");
		queryString.append("	         ON Y.ID_ENTIDAD_REGISTRADA = DR.DET_ID ");
		queryString.append("	       JOIN REN_RENDICION R ");
		queryString.append("	         ON R.REN_ID = DR.REN_ID ");
		queryString.append("	       JOIN TPO_RENDICION TR ");
		queryString.append("	         ON TR.TPO_RENDICION_ID = R.TPO_RENDICION_ID ");
		queryString.append("	GROUP BY FORMULARIO, ");
		queryString.append("	       PAGINA, ");
		queryString.append("	       LINEA, ");
		queryString.append("	       CONCEPTO, ");
		queryString.append("	       FECHA_MODIFICACION, ");
		queryString.append("	       VALOR_ORIGINAL ");
		
		return queryString.toString();
	}
	
	public void actualizarPagoCreditoMandato(int detId) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("UPDATE DET_DETALLE_RENDICION DET_REN ");
		queryString.append("SET DET_VALOR = ( ");
		queryString.append("					SELECT REPLACE (CAST (JSONB (D.DET_VALOR :: JSON) AS TEXT),  ");
		queryString.append("									JSONB (D.DET_VALOR :: JSON) ->> 'reCreditoMandatoDetalle',  ");
		queryString.append("									( ");
		queryString.append("										SELECT REPLACE (JSONB (DR.DET_VALOR :: JSON) ->> 'reCreditoMandatoDetalle', '\"pagado\": false', '\"pagado\": true') ");
		queryString.append("										  FROM DET_DETALLE_RENDICION DR  ");
		queryString.append("										 WHERE DET_ID = ?)  ");
		queryString.append("									) AS DET_UPDATED ");
		queryString.append("					  FROM DET_DETALLE_RENDICION D ");
		queryString.append("					 WHERE D.DET_ID = ? ");
		queryString.append("				) ");
		queryString.append(" WHERE DET_REN.DET_ID = ? ");

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(queryString.toString());
			
			ps.setInt(1, detId);
			ps.setInt(2, detId);
			ps.setInt(3, detId);
			
			ps.executeUpdate();
		} catch (Exception e) {
			LOG.error("Ocurrio un error al actualizar el pago de los créditos con mandato", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
