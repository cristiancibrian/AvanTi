/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Adriana
 */
public class DelegatePoliticaTieneCriterio {
    public List<PoliticaTieneCriterio> findCriterios(int id) {
        return ServiceLocator.getInstancePoliticaTieneCriterio().findcriteriospolitica(id);
    }
public boolean EliminarPoliticaCriterio(PoliticaTieneCriterio politicaCriterio){
       
       try{
            ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL eliminarPoliticaCriterio("
                    + " \'" +  politicaCriterio.getPtcId()+ "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
   public boolean agregarPoliticaCriterio(PoliticaTieneCriterio politicaCriterio){
       
       try{
            ServiceLocator
                .getInstanceCriterioevaluacion()
                .storeProcedureCriterioPolitica(
                 "CALL agregarPoliticaCriterio("
                    + "\'" + politicaCriterio.getPOEId().getPOEId() + "\',"
                    + "\'" + politicaCriterio.getCEVId().getCEVId() + "\',"
                    + "\'" + politicaCriterio.getPorcentaje() + "\',"
                    + "\'" +  politicaCriterio.getPtcId() + "\',"
                    + "\'" +  politicaCriterio.getNExamen()+ "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
}