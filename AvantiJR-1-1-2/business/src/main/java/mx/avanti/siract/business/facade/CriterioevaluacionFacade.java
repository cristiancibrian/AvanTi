/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateCriterioevaluacion;
import mx.avanti.siract.entity.Criterioevaluacion;

/**
 *
 * @author Memo
 */
public class CriterioevaluacionFacade {
    private final DelegateCriterioevaluacion delegateCriterioevaluacion;


    public CriterioevaluacionFacade() {
        delegateCriterioevaluacion = new DelegateCriterioevaluacion();
    }

    /**
     * Metodo para obtener Profesores
     *
     * @return Lista de todos los Profesores
     */
    public List<Criterioevaluacion> getListacriterioevaluacion() {
        return delegateCriterioevaluacion.findAll();
    }
public List<Criterioevaluacion> consultarCriterios(){
       
       return delegateCriterioevaluacion.consultarCriterios();
   }
public boolean AgregarCriterioEvaluacion(String Nombre){
       
       return delegateCriterioevaluacion.AgregarCriterioEvaluacion(Nombre);
   }
}
