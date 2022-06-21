/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateAlerta;
import mx.avanti.siract.entity.Alerta;

/**
 *
 * @author Adriana
 */
public class AlertaFacade {

    DelegateAlerta delegateAlerta = new DelegateAlerta();

    /**
     * Metodo para guardar una alerta
     *
     * @param alerta de tipo alerta con todos sus atributos y id 0 para crear
     * uno nuevo
     */
    public boolean guardarAlerta(Alerta alerta) {
        return delegateAlerta.saveAlerta(alerta);
    }

    /**
     * Metodo para actualizar una alerta
     *
     * @param alerta de tipo alerta con todos sus atributos
     *
     */
    public boolean ActualizarAlerta(Alerta alerta) {
        return delegateAlerta.updateAlerta(alerta);
    }

    /**
     * Meto para obtener todas las alertas
     *
     * @return Lista de todas las alertas en la base de datos
     */
    public List<Alerta> BuscarAlertas() {
        return delegateAlerta.getAlertas();
    }

    /**
     * Metodo para buscar una alerta por su tipo
     *
     * @param tipo, tipo de alerta que se quiere buscar
     * @return Alerta correspondiente al tipo que se proporciono
     */
    public Alerta BuscarAlertaPorTipo(String tipo) {

        return delegateAlerta.findAlertaByTipo(tipo);
    }

    /**
     * Metodo para buscar una alerta por su id
     *
     * @param id de la Alerta que se va a buscar
     * @return Alerta encontrada por el id
     */
    public Alerta buscarAlertaPorId(int id) {
        return delegateAlerta.findByID(id);
    }

    /**
     * Metodo para iniciar una transaccion
     *
     * @param alerta de tipo alerta con todos sus atributos
     *
     */
    public void iniciarTransaccion() {
        delegateAlerta.iniciarTransaccion();
    }

    public void endTransaccion() {
        delegateAlerta.endTransaccion();
    }

}
