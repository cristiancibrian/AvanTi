/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateCoordinadorAreaAdministrativa;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;

/**
 *
 * @author Paul Miranda
 */
public class CoordinadorAreaAdministrativaFacade {
    public final DelegateCoordinadorAreaAdministrativa delegateCAA;
    public CoordinadorAreaAdministrativaFacade(){
        delegateCAA = new DelegateCoordinadorAreaAdministrativa();
    }
    /***
     * Este método guarda el registro que se envia como parametro en la base de 
     * datos
     * @param coordinador Recibe un registro de Coordinadorareaadministrativa sin
     * id 
     */
    public boolean agregarCoordinadorAreaAdministrativa(Coordinadorareaadministrativa coordinador){
        return delegateCAA.save(coordinador);

    }
    /***
     * Método para ver todos los registros que hay en la base de datos
     * @return Lista con todos los registros de coordinador de área 
     * administrativa
     */
   public List<Coordinadorareaadministrativa> consultaGeneralCoordinadorAreaAdministrativa(){
       return delegateCAA.findAll();
   }
   /***
    * Método para eliminar un registro especifico de la base de datos
    * @param coordinador Registro completo de coordinador de área administrativa
    */
   public boolean eliminarCoordinadorAreaAdministrativa(Coordinadorareaadministrativa coordinador){
       return delegateCAA.delete(coordinador);
   }
   /***
     * Método para modificar un registro de la base de datos
     * @param coorninador Registro completo con una diferencia con el original
     */
    public boolean modificarCoordinadorAreaAdministrativa(Coordinadorareaadministrativa coordinador){
        return delegateCAA.update(coordinador);
    }
   /**
    * Método para encontrar un registro especifico de coordinador de área 
    * administrativa
    * @param idCoordinador Id del registro que se quiere recuperar
    * @return registro de coordinador de área administrativa
    */
   public Coordinadorareaadministrativa consultaCoordinadorAreaAdministrativaPorID(int idCoordinador){
       return delegateCAA.find(idCoordinador);
   }
}
