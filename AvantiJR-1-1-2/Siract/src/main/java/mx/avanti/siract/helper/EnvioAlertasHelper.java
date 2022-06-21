/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;
import java.io.Serializable;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Alerta;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Usuario;
/**
 *
 * @author Eduardo
 * 
 */
public class EnvioAlertasHelper implements Serializable {
    public EnvioAlertasHelper() {
    }
    public List<Alerta> getAlertas(){
        List<Alerta> alertas=ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertas();
        return alertas;
    }
    public List<Calendarioreporte> getCalendarioreportes(){
        List<Calendarioreporte> calendarioreportes=ServiceFacadeLocator.getInstanceCalendarioReporteFacade().findAll();
        return calendarioreportes;
    }
    public List<CalendarioreporteTieneAlerta> getCalendarioreporteTieneAlertas(){
        List<CalendarioreporteTieneAlerta> calendarioreporteTieneAlertas=ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().BuscarTodoCalendarioReporteTieneAlerta();
        return calendarioreporteTieneAlertas;
    }
    public List<Cicloescolar> getCicloEscolar(){
        List<Cicloescolar> cicloescolars=ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares();
        return cicloescolars;
    }
    public List<Mensaje> getMensaje(){
        List<Mensaje> mensajes=ServiceFacadeLocator.getInstanceMensajeFacade().getMensajes();
        return mensajes;
    }
    public List<Profesor> getProfesor(){
        List<Profesor> profesors=ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
        return profesors;
    }
    public List<Usuario> getUsuario(){
        return ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
    }
}
