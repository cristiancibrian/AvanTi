/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateCalendarioReporte;
import mx.avanti.siract.entity.Calendarioreporte;

/**
 *
 * @author NOE_LOPEZ
 */
public class CalendarioReporteFacade {

    private final DelegateCalendarioReporte delegate;

    public CalendarioReporteFacade() {

        delegate = new DelegateCalendarioReporte();
    }

    public boolean saveCalendarioReporte(Calendarioreporte calendarioreporte) {
        return delegate.saveCalendarioReporte(calendarioreporte);
    }
    
    public boolean modificarCalendarioReporte(Calendarioreporte calendarioreporte) {
        return delegate.modificarCalendarioReporte(calendarioreporte);
    }

    public List<Calendarioreporte> findAll() {
        return delegate.getCalendariosReporte();
    }

    public Calendarioreporte find(int id) {
        return delegate.find(id);
    }

    public boolean eliminarCalendarioReporte(Calendarioreporte calendarioreporte) {
        return delegate.deleteCalendarioreporte(calendarioreporte);
    }

    public Calendarioreporte calendarioreporteFechaActual() {
        return delegate.calendarioFechaActual();
    }

    public Calendarioreporte siguienteReporte() {
        return delegate.siguienteReporte();
    }

    public Calendarioreporte ultimoReporte() {
        return delegate.ultimoReporte();
    }
//    public List<Calendarioreporte> getCon_cre(int conid){
//        return delegate.getCon_cre(conid);
//    }
}
