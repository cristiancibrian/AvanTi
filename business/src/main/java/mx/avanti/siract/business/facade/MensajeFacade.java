/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateMensaje;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Alerta;
import mx.avanti.siract.entity.Mensaje;

/**
 *
 * @author Eduardo
 */
public class MensajeFacade {
    private final DelegateMensaje delegate;
    
    public MensajeFacade() {
        delegate = new DelegateMensaje();
    }
    
    /**
     * Metodo que retorna todos los mensajes 
     * @return Lista de mensajes 
     */
    public List<Mensaje> getMensajes(){
        return delegate.findAll();
    }
    /**
     * Metodo para guardar un mensaje
     * @param mensaje Objeto que contiene los valores a guardar
     */
    public boolean saveMensaje(Mensaje mensaje){
        return delegate.saveMensaje(mensaje);
    }
    
}
