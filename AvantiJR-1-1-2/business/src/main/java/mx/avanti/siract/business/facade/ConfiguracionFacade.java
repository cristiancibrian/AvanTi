/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.Comparator;
import java.util.List;
import mx.avanti.business.delegate.DelegateConfiguracion;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.Configuracion;

/**
 *
 * @author Martin
 */
public class ConfiguracionFacade {
    private final DelegateConfiguracion delegateConfiguracion;
    
    
    public ConfiguracionFacade(){
        delegateConfiguracion=new DelegateConfiguracion();
    }
    
   /**
    * Metodo para obtener todas las configuraciones registradas
    * @return lista con todas las configuraciones 
    */
    public List<Configuracion> BuscarTodasConfiguraciones(){
    
        return delegateConfiguracion.getConfiguraciones();
    }
    
    /**
     * Metodo para guardar registro en la tabla de configuracion
     * @param configuracion de tipo configuracion con todos sus datos y id 0
     * para crear nuevo registro
     */
    public boolean GuardarConfiguracion(Configuracion configuracion){
       return delegateConfiguracion.saveConfiguracion(configuracion);
    }
   /**
    * Metodo para actualizar un registro de configuracion 
    * @param configuracion  de tipo configuracion con todos sus datos
    */
    public boolean ActualizarConfiguracion(Configuracion configuracion){
        return delegateConfiguracion.updateConfiguracion(configuracion);
    }
    
    /**
     * Metodo para buscar un registro de configuracion por su id
     * @param id de tipo entero
     * @return una entidad de configuracion correspondiente al id
     */
   public Configuracion BuscarConfiguracionPorID(int id){
      return delegateConfiguracion.findConfiguracionByID(id);
   }
   
   /**
    * Metodo para eliminar una configuracion
    * @param configuracion de tipo configuracion
    */
   public void borrarConfiguracion(Configuracion configuracion){
       delegateConfiguracion.deleteConfiguracion(configuracion);
   }
//   public static Comparator<Calendarioreporte> comparadorFechasCorte = (Calendarioreporte cre1, Calendarioreporte cre2) -> {
//        if (cre1.getCrefechaCorte().before(cre2.getCrefechaCorte())){//Compara las dos fechas de corte
//            return -1;//Ordena antes
//        }else{
//            if(cre1.getCrefechaCorte().equals(cre2.getCrefechaCorte())){//Si dos fechas de corte son iguales...
//                if(cre1.getCrefechaLimite().before(cre2.getCrefechaLimite())){//Compara las fecha de límite
//                    return -1;//Ordena antes
//                }else{
//                    return 1;//Ordena después
//                }
//            }else{
//                return 1;//Ordena después
//            }
//        }
//    };
//  
   
}
