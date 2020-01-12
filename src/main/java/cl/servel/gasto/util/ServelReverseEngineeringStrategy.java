package cl.servel.gasto.util;

import java.util.Properties;

/**
 * Objetivo: Clase que customiza la estrategia de ingenieria reversa usada para la generación de entities.
 * Fecha: 14-09-2018
 * @author Alexis Tapia
 * @version 1.0
 */

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class ServelReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

    public ServelReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
        super(delegate);
    }

    @Override 

    public String getTableIdentifierStrategyName(TableIdentifier identifier) { 

        return "sequence"; 

    } 

      

    @Override 

    public Properties getTableIdentifierProperties(TableIdentifier identifier) { 

         String nombretabla=identifier.getName();
        String prefijo=nombretabla.substring(0, nombretabla.indexOf('_'));
       
         if(prefijo.equals("tpo")) {
        	 prefijo=nombretabla;
         }
         
         String secuencia=nombretabla +"_"+prefijo+"_id_seq";
        Properties properties = super.getTableIdentifierProperties(identifier); 

        if(properties == null){ 

            properties = new Properties(); 

        } 

        properties.put(org.hibernate.id.enhanced.SequenceStyleGenerator.SEQUENCE_PARAM, secuencia); 


        return properties; 

    } 
}
