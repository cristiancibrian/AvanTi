/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author NOE_LOPEZ
 */
public class DelegateCalendarioReporte implements Serializable {

    Calendarioreporte calendarioreporte;
    private List<Calendarioreporte> calendariosReporte;
    private List<Calendarioreporte> con_cre;

    public DelegateCalendarioReporte() {
    }

    /**
     * Busca el calendario de la fecha actual
     *
     * @return
     */
    public Calendarioreporte calendarioFechaActual() {
        ServiceLocator.getInstanceBaseDAO().setTipo(Calendarioreporte.class);
        // Calendarioreporte calendario= ServiceLocator.getInstanceBaseDAO().reporteFechaActual();
        List<Calendarioreporte> calendario = ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendarios("CALL calendarioActual()");

        // return (Calendarioreporte)ServiceLocator.getInstanceBaseDAO().reporteFechaActual();
        try {
            if (calendario != null || calendario.size() >= 1) {
                return calendario.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("no hay fecha actualmente");
            return null;
        }
    }

    /**
     * Busca el siguiente reporte
     *
     * @return
     */
    public Calendarioreporte siguienteReporte() {
        return (Calendarioreporte) ServiceLocator.getInstanceBaseDAO().siguienteReporte();
    }

    /**
     * Busca el ultimo reporte
     *
     * @return
     */
    public Calendarioreporte ultimoReporte() {
        return (Calendarioreporte) ServiceLocator.getInstanceBaseDAO().ultimoReporte();
    }

    /**
     * Buscar todos los reportes
     *
     * @return
     */
    public List<Calendarioreporte> getCalendariosReporte() {
        return ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendarios("CALL consultarCalendarios()");
    }

    /**
     * Buscar calendario reporte por id
     *
     * @param id
     * @return
     */
    public Calendarioreporte find(int id) {
        //return ServiceLocator.getInstanceCalendarioReporteDAO().find(id);
        return ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendarios("CALL buscarCalendario(\'" + id + "\')").get(0);
    }

    /**
     * Eliminar el calendario reporte
     *
     * @param c
     */
    public boolean deleteCalendarioreporte(Calendarioreporte c) {
        return ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendario("CALL eliminarCalendario(\'" + c.getCREid() + "\')");
    }

    /**
     * Guardar Caledario Reporte
     *
     * @param calendarioreporte
     */
    public boolean saveCalendarioReporte(Calendarioreporte calendarioreporte) {
        Calendarioreporte result = null;
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
        if (calendarioreporte == null) {
            System.out.println("Puesto nulo");
        }
        if (calendarioreporte.getCREid() != 0) {
            ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendarios("CALL buscarCalendario(\'" + calendarioreporte.getCREid() + "\')").get(0);
        }
        if (result != null) {
            calendarioreporte.setCREid(result.getCREid());
            return ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendario("CALL modificarCalendario(\'" + dt1.format(calendarioreporte.getCREfechaCorte()) + "\' , \'" + dt2.format(calendarioreporte.getCREfechaLimite()) + "\' , \'" + calendarioreporte.getCREid() + "\')");
        } else {
            ServiceLocator.getInstanceCalendarioReporteDAO().save(calendarioreporte);
            return true;
//            return ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendario
//                ("CALL agregarCalendario(\'" + dt1.format(calendarioreporte.getCREfechaCorte()) + "\' , \'" + 
//                dt2.format(calendarioreporte.getCREfechaLimite()) + "\')");
        }
    }

    public boolean modificarCalendarioReporte(Calendarioreporte calendarioreporte) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return ServiceLocator.getInstanceCalendarioReporteDAO().executeProcedureCalendario("CALL modificarCalendario(\'" + dt1.format(calendarioreporte.getCREfechaCorte()) + "\' , \'" + dt1.format(calendarioreporte.getCREfechaLimite()) + "\' , \'" + calendarioreporte.getCREid() + "\')");

    }

}
