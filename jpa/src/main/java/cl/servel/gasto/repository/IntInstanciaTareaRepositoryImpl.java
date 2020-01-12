package cl.servel.gasto.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import cl.servel.gasto.transients.ResumenInstanciaFlujoDTO;

public class IntInstanciaTareaRepositoryImpl implements IntInstanciaTareaRepositoryCustom {
	@Autowired
	private DataSource ds;
	
	private String getQueryReporteWorkflow() {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
		query.append(" 	     TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION, ");
		query.append("	     CAST(CAN.CAN_RUT AS TEXT) AS RUT,");
		query.append("	     CAN.CAN_RUT_DV AS DV, ");
		query.append("	     CAN.CAN_NOMBRES AS NOMBRES, ");
		query.append("	     CAN.CAN_APP_PATERNO AS APELLIDO_PATERNO, ");
		query.append("	     CAN.CAN_APP_MATERNO AS APELLIDO_MATERNO, ");
		query.append("	     CAN.TPO_CAN_ID AS TIPO_CANDIDATO, ");
		query.append("	     PAR.PAR_NOMBRE AS PARTIDO, ");
		query.append("	     DBA.DBN_NOMBRE_BANDEJA AS GRUPO, ");
		query.append("	     DTA.DTA_NOMBRE_TAREA AS ETAPA, ");
		query.append("	     IT.INT_FECHA_ASIGNACION AS FECHA_ASIGNACION, ");
		query.append("	     IT.INT_FECHA_TERMINO AS FECHA_TERMINO, ");
		query.append("	     USU.USU_NOMBRE || ' ' || USU.USU_APELLIDO_PATERNO || ' ' || USU.USU_APELLIDO_MATERNO AS USUARIO, ");
		query.append("       USU.USU_RUT || '-' || USU.USU_RUT_DV AS RUT_USUARIO ");
		query.append("  FROM INT_INSTANCIA_TAREA IT ");
		query.append("  JOIN INF_INSTANCIA_FLUJO INF ");
		query.append("    ON IT.INF_ID = INF.INF_ID   ");
		query.append("  JOIN DTA_DEFINICION_TAREA DTA ");
		query.append("    ON DTA.DTA_ID = IT.DTA_ID ");
		query.append("  JOIN DBN_DEFINICION_BANDEJA DBA ");
		query.append("    ON DBA.DBN_ID = DTA.DBN_ID ");
		query.append("  JOIN USU_USUARIOS USU ");
		query.append("    ON USU.USU_ID = IT.USU_ID ");
		query.append("  JOIN CAN_CANDIDATO CAN ");
		query.append("    ON CAN.CAN_ID = INF.CAN_ID ");
		query.append("  LEFT JOIN PAR_PARTIDO PAR ");
		query.append("    ON PAR.PAR_ID = CAN.PAR_ID ");
		query.append("  JOIN SEL_SUB_ELECCION SEL ");
		query.append("    ON SEL.SEL_ID = CAN.SEL_ID ");
		query.append("  JOIN ELE_ELECCION ELE ");
		query.append("    ON ELE.ELE_ID = SEL.ELE_ID ");
		query.append("  JOIN TPO_ELECCION TEL ");
		query.append("    ON TEL.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID ");
		query.append("  JOIN TPO_EVENTO TEV ");
		query.append("    ON TEV.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID ");
		query.append(" WHERE DTA.DFL_ID = ? ");
		query.append("   AND TEV.TPO_EVENTO_ID = ? ");
		query.append("   AND TEL.TPO_ELECCION_ID = ? ");
		query.append("   AND IT.INT_FECHA_ASIGNACION BETWEEN ? AND ? ");
//		query.append(" ORDER BY INF.INF_ID DESC,  ");
//		query.append(" 	     IT.INT_FECHA_ASIGNACION ASC ");  
		query.append(" UNION ");
		query.append("SELECT TEV.TPO_EVENTO_NOMBRE AS TIPO_EVENTO, ");
		query.append("	     TEL.TPO_ELE_NOMBRE AS TIPO_ELECCION, ");
		query.append("	     (SELECT split_part(PAR.PAR_RUT, '-', 1)) AS RUT, ");
		query.append("	     (SELECT split_part(PAR.PAR_RUT, '-', 2)) AS DV, ");
		query.append("	     '' AS NOMBRES, ");
		query.append("	     PAR.PAR_NOMBRE AS APELLIDO_PATERNO, ");
		query.append("	     '' AS APELLIDO_MATERNO, ");
		query.append("	     0 AS TIPO_CANDIDATO, ");
		query.append("	     '' AS PARTIDO, ");
		query.append("	     DBA.DBN_NOMBRE_BANDEJA AS GRUPO, ");
		query.append("	     DTA.DTA_NOMBRE_TAREA AS ETAPA, ");
		query.append("	     IT.INT_FECHA_ASIGNACION AS FECHA_ASIGNACION, ");
		query.append("	     IT.INT_FECHA_TERMINO AS FECHA_TERMINO, ");
		query.append("	     USU.USU_NOMBRE || ' ' || USU.USU_APELLIDO_PATERNO || ' ' || USU.USU_APELLIDO_MATERNO AS USUARIO, ");
		query.append("       USU.USU_RUT || '-' || USU.USU_RUT_DV AS RUT_USUARIO ");
		query.append("  FROM INT_INSTANCIA_TAREA IT ");
		query.append("  JOIN INF_INSTANCIA_FLUJO INF ");
		query.append("    ON IT.INF_ID = INF.INF_ID   ");
		query.append("  JOIN DTA_DEFINICION_TAREA DTA ");
		query.append("    ON DTA.DTA_ID = IT.DTA_ID ");
		query.append("  JOIN DBN_DEFINICION_BANDEJA DBA ");
		query.append("    ON DBA.DBN_ID = DTA.DBN_ID ");
		query.append("  JOIN USU_USUARIOS USU ");
		query.append("    ON USU.USU_ID = IT.USU_ID ");
		query.append("  JOIN PAR_PARTIDO PAR ");
		query.append("    ON PAR.PAR_ID = INF.PAR_ID ");
		query.append("  JOIN ELE_ELECCION ELE ");
		query.append("    ON ELE.ELE_ID = PAR.ELE_ID ");
		query.append("  JOIN TPO_ELECCION TEL ");
		query.append("    ON TEL.TPO_ELECCION_ID = ELE.TPO_ELECCION_ID ");
		query.append("  JOIN TPO_EVENTO TEV ");
		query.append("    ON TEV.TPO_EVENTO_ID = ELE.TPO_EVENTO_ID ");
		query.append("  WHERE DTA.DFL_ID = ? ");
		query.append("   AND TEV.TPO_EVENTO_ID = ? ");
		query.append("   AND TEL.TPO_ELECCION_ID = ? ");
		query.append("   AND IT.INT_FECHA_ASIGNACION BETWEEN  ? AND ? ");
//		query.append(" ORDER BY INF.INF_ID DESC,  ");
//		query.append("	     IT.INT_FECHA_ASIGNACION ASC ");  
	
		return query.toString();
	}
	
	public LinkedList<ResumenInstanciaFlujoDTO> getDataReporteWorkflow(int dflId, int tipoEvento, int tipoEleccion, long fechaInicio, long fechaFin) {
		LinkedList<ResumenInstanciaFlujoDTO> data = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Date desde = new Date(fechaInicio);
		Date hasta = new Date(fechaFin);
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(getQueryReporteWorkflow());
			ps.setInt(1, dflId);
			ps.setInt(2, tipoEvento);
			ps.setInt(3, tipoEleccion);
			ps.setDate(4, desde);
			ps.setDate(5, hasta);
			
			ps.setInt(6, dflId);
			ps.setInt(7, tipoEvento);
			ps.setInt(8, tipoEleccion);
			ps.setDate(9, desde);
			ps.setDate(10, hasta);
			
			rs = ps.executeQuery();
			
			ResumenInstanciaFlujoDTO resumen = null;
			data = new LinkedList<ResumenInstanciaFlujoDTO>();
			
			while (rs.next()) {
				resumen = new ResumenInstanciaFlujoDTO();
				
				resumen.setTipoEvento(rs.getString("TIPO_EVENTO"));
				resumen.setTipoEleccion(rs.getString("TIPO_ELECCION"));
				resumen.setRut(rs.getInt("RUT"));
				resumen.setDv(rs.getString("DV"));
				resumen.setNombres(rs.getString("NOMBRES"));
				resumen.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
				resumen.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
				resumen.setTipoCandidato(rs.getInt("TIPO_CANDIDATO"));
				resumen.setPartido(rs.getString("PARTIDO"));
				resumen.setGrupo(rs.getString("GRUPO"));
				resumen.setEtapa(rs.getString("ETAPA"));
				resumen.setFechaAsignacion(rs.getTimestamp("FECHA_ASIGNACION"));
				resumen.setFechaTermino(rs.getTimestamp("FECHA_TERMINO"));
				resumen.setUsuario(rs.getString("USUARIO"));
				resumen.setRutUsuario(rs.getString("RUT_USUARIO"));
				
				data.add(resumen);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return data;
	}
}
