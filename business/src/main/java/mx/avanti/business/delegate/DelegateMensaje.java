/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Eduardo
 */
public class DelegateMensaje {
    
    /**
     * Metodo para guardar un mensaje
     * @param mensaje Objeto que contiene los valores a guardar
     */
    public boolean saveMensaje(Mensaje mensaje){   
          return ServiceLocator.getInstanceMensajeDAO().executeProcedureMensaje("CALL guardarMensaje(\'" + mensaje.getMENid() + "\' , \'" + mensaje.getMENmensaje() + "\' , \'" + mensaje.getAlertaALEid().getALEid() + "\')");
    }
    
    /**
     * Metodo que retorna todas las clases
     * @return  Lista de todos los mensajes 
     */
    public List<Mensaje> findAll() {   
          return  ServiceLocator.getInstanceMensajeDAO().executeProcedureMensajes("CALL consultarMensajes()");
    }
}
