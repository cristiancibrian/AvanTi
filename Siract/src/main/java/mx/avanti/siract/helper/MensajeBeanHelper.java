/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Mensaje;


public class MensajeBeanHelper {
    public List<Mensaje> getMensajes() {
        return ServiceFacadeLocator.getInstanceMensajeFacade().getMensajes();
    }
}
