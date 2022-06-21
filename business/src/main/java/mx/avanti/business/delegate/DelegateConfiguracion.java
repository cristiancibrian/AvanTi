/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import mx.avanti.siract.entity.Configuracion;
//import mx.avanti.siract.integration.ServiceLocator;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Martin
 */
public class DelegateConfiguracion implements Serializable{
   
//    public Configuracion bConfPorCiclo(int id){
//         Configuracion configuracion=null;
//        //ServiceLocator.getInstanceBaseDAO().setTipo(Configuracion.class);
//        configuracion=(Configuracion) ServiceLocator.getInstanceConfiguracion().bConfigPorCiclo(id);
//        return configuracion;
//    }
   /**
    * Metodo para obtener todas las configuraciones registradas
    * @return lista con todas las configuraciones 
    */
    public List<Configuracion> getConfiguraciones(){
    
        return ServiceLocator.getInstanceConfiguracion().executeProcedureConfiguraciones
        ("CALL consultarConfiguraciones()");
    }
    
    /**
     * Metodo para guardar registro en la tabla de configuracion
     * @param configuracion de tipo configuracion con todos sus datos y id 0
     * para crear nuevo registro
     */
    public boolean saveConfiguracion(Configuracion configuracion){   
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return ServiceLocator.getInstanceConfiguracion().executeProcedureConfiguracion
        ("CALL guardarConfiguracion(\'" + dt1.format(configuracion.getCONfechaInicioSemestre()) + "\' , \'" + configuracion.getCONnumeroSemanas() + 
                "\' , \'" + configuracion.getCicloEscolarCESid().getCESid() + "\' , \'" + configuracion.getAlertaALEid().getALEid() + "\')");
    } 
   /**
    * Metodo para actualizar un registro de configuracion 
    * @param configuracion  de tipo configuracion con todos sus datos
    */
    public boolean updateConfiguracion(Configuracion configuracion){
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return ServiceLocator.getInstanceConfiguracion().executeProcedureConfiguracion
        ("CALL modificarConfiguracion(\'" + dt1.format(configuracion.getCONfechaInicioSemestre()) + "\' , \'" + configuracion.getCONnumeroSemanas() + 
                "\' , \'" + configuracion.getCicloEscolarCESid().getCESid() + "\' , \'" + configuracion.getAlertaALEid().getALEid() + "\' , \'" + configuracion.getCONid() + "\')");
    }
    
    /**
     * Metodo para buscar un registro de configuracion por su id
     * @param id de tipo entero
     * @return una entidad de configuracion correspondiente al id
     */
   public Configuracion findConfiguracionByID(int id){
     return ServiceLocator.getInstanceConfiguracion().executeProcedureConfiguraciones
        ("CALL buscarConfiguracion(\'" + id + "\')").get(0) ;
   }
   
   /**
    * Metodo para eliminar una configuracion
    * @param configuracion de tipo configuracion
    */
   public boolean deleteConfiguracion(Configuracion configuracion){
       return ServiceLocator.getInstanceConfiguracion().executeProcedureConfiguracion
        ("CALL eliminarConfiguracion(\'" + configuracion.getCONid() + "\')");
   }
}
