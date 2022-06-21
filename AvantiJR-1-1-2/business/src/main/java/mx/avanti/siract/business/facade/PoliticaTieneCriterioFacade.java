/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegatePoliticaTieneCriterio;
import mx.avanti.siract.entity.PoliticaTieneCriterio;

/*
 *
 * @author Memo
 */
public class PoliticaTieneCriterioFacade {
    private final DelegatePoliticaTieneCriterio delegatePoliticaTieneCriterio;


    public PoliticaTieneCriterioFacade() {
        delegatePoliticaTieneCriterio = new DelegatePoliticaTieneCriterio();
    }

    /*
     * Metodo para obtener Profesores
     *
     * @return Lista de todos los Profesores
     */
    public List<PoliticaTieneCriterio> getListapoliticasevaluacion(int id) {
        return delegatePoliticaTieneCriterio.findCriterios(id);
    }
    public boolean EliminarPoliticaCriterio(PoliticaTieneCriterio politicaCriterio){
       
       return delegatePoliticaTieneCriterio.EliminarPoliticaCriterio(politicaCriterio);
   }
    public boolean agregarPoliticaCriterio(PoliticaTieneCriterio politicaCriterio){
       
       return delegatePoliticaTieneCriterio.agregarPoliticaCriterio(politicaCriterio);
   }
}