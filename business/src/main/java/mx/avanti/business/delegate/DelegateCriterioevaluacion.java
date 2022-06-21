/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Adriana
 */
public class DelegateCriterioevaluacion {

   
    public List<Criterioevaluacion> findAll() {
        return ServiceLocator.getInstanceCriterioevaluacion().findcriterios();
    }
   public List<Criterioevaluacion> consultarCriterios(){
       
       try{
           return ServiceLocator
                .getInstancePoliticaevaluacion()
                .ExecuteProcedureConsultaCriterio(
                 "CALL consultarCriterios()");
           
       }catch(Exception ex){
           System.err.println(ex);
           return new ArrayList<Criterioevaluacion>();
       }
   }
       public boolean AgregarCriterioEvaluacion(String Nombre){
       
       try{
            ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL AgregarCriterioEvaluacion("
                    + " \'" + Nombre + "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
}
