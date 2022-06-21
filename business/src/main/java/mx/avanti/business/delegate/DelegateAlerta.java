/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Alerta;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Adriana
 */
public class DelegateAlerta {

    /**
     * Metodo para guardar una alerta
     *
     * @param alerta de tipo alerta con todos sus atributos y id 0 para crear
     * uno nuevo
     */
    public boolean saveAlerta(Alerta alerta) {
        return ServiceLocator.getInstanceAlertaDAO().executeProcedureAlerta("CALL guardarAlerta(\'" + alerta.getALEtipo() + 
                "\' , \'" + alerta.getALEcolor() + "\')");
    }

    /**
     * Metodo para actualizar una alerta
     *
     * @param alerta de tipo alerta con todos sus atributos
     *
     */
    public boolean updateAlerta(Alerta alerta) {
        return ServiceLocator.getInstanceAlertaDAO().executeProcedureAlerta("CALL modificarAlerta(\'" + alerta.getALEid() + "\' , \'" + alerta.getALEtipo() + 
                "\' , \'" + alerta.getALEcolor() + "\')");
    }

    /**
     * Metodo para obtener todas las alertas de la base de datos
     *
     * @return Lista de todas las alertas en la base de datos
     */
    public List<Alerta> getAlertas() {
        return ServiceLocator.getInstanceAlertaDAO().executeProcedureAlertas("CALL consultarAlertas()");
        //getInstanceAlertaDAO().findAll();
    }

    /**
     * Metodo para encontrar una alerta por tipo
     *
     * @param tipo, tipo de alerta que se quiere buscar
     * @return Alerta correspondiente al tipo que se proporciono
     */
    public Alerta findAlertaByTipo(String tipo) {
        List<Alerta> alertas;
        Alerta respuesta = new Alerta();
        alertas = ServiceLocator.getInstanceAlertaDAO().executeProcedureAlertas("CALL consultarAlertas()");
        for (Alerta alerta : alertas) {
            if (alerta.getALEtipo().equalsIgnoreCase(tipo)) {
                respuesta = alerta;
            }
        }
        return respuesta;
    }

    /**
     * Metodo para buscar una alerta por su id
     *
     * @param id de la Alerta que se va a buscar
     * @return Alerta encontrada por el id
     */
    public Alerta findByID(int id) {
        return ServiceLocator.getInstanceAlertaDAO().executeProcedureAlertas("CALL buscarAlertaID(\'" + id + "\')").get(0);
    }

    /**
     * Metodo para iniciar la transaccion
     *
     *
     */
    public void iniciarTransaccion() {
        ServiceLocator.getInstanceAlertaDAO().iniciarTransaccion();
    }

    public void endTransaccion() {
        ServiceLocator.getInstanceAlertaDAO().endTransaccion();
    }

}
