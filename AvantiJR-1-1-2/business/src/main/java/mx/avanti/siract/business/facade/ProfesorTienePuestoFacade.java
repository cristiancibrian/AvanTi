/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import mx.avanti.business.delegate.DelegateProfesorTienePuesto;

/**
 *
 * @author Memo
 */
public class ProfesorTienePuestoFacade {

    //Intancia privada y tipo final de la clase DelegateProfesorTienePuesto
    private final DelegateProfesorTienePuesto delegateProfesorTienePuesto;

    /**
     * Constructor utilizado para inicializar la variable
     * delegateProfesorTienePuesto
     */
    public ProfesorTienePuestoFacade() {
        delegateProfesorTienePuesto = new DelegateProfesorTienePuesto();
    }

    //ESTA CLASE SE CREO POR EL ORDEN DE LAS CAPAZ PERO NO SE UTILIZA YA
}
