/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.integration.ServiceLocator;
/**
 *
 * @author NOE_LOPEZ
 * Refactor: Eduardo Cardona
 */
public class DelegateCalendarioreporteTieneAlerta implements Serializable{
    
    /**
     * Metodo para consultar todos los registros de calendario tiene alerta
     * @return Regresa todos los registros de calendario reporte tiene alerta
     */
    public List<CalendarioreporteTieneAlerta> getCalendarioReporteTieneAlerta(){
        return ServiceLocator.getInstanceCalendarioReporteTieneAlertaDAO().executeProcedureCalendarioreporteTieneAlertas("CALL consultarCalendariosTienenAlerta()");
       
    }

    /**
     * metodo para guardar un registro de calendarioAlerta
     * @param calendarioAlerta de tipo calendarioAlerta con ID 0 para crear
     * nuevo registro
     */
    public void saveCalendarioreporteTieneAlerta(CalendarioreporteTieneAlerta calendarioAlerta){
//       
         ServiceLocator.getInstanceCalendarioReporteTieneAlertaDAO().executeProcedureCalendarioreporteTieneAlerta("CALL  guardarCalendarioTieneAlerta(\'" + calendarioAlerta.getCalendarioreporte().getCREid() + "\' , \'" + calendarioAlerta.getAlerta().getALEid() + 
                "\' , \'" + calendarioAlerta.getCALdias() + "\' , \'" + calendarioAlerta.getCALnumeroReporte() + "\')");
    }
    /**
     * Metodo para actualizar un registro de CalendarioAlerta
     * @param calendarioAlerta de tipo calendario alerta con todos sus atributos
     */
    public void updateCalendarioreporteTieneAlerta(CalendarioreporteTieneAlerta calendarioAlerta){
         ServiceLocator.getInstanceCalendarioReporteTieneAlertaDAO().executeProcedureCalendarioreporteTieneAlerta("CALL  modificarCalendarioTieneAlerta(\'" + calendarioAlerta.getCalendarioreporte().getCREid() + "\' , \'" + calendarioAlerta.getAlerta().getALEid() + 
                "\' , \'" + calendarioAlerta.getCALdias() + "\' , \'" + calendarioAlerta.getCALnumeroReporte() + "\')");
    }

    /**
     * Busca una alerta por tipo
     * @param tipo de tipo String que indica el tipo de la alerta
     * @return 
     */
    public CalendarioreporteTieneAlerta findByTipo(String tipo){
        ServiceLocator.getInstanceBaseDAO().setTipo(CalendarioreporteTieneAlerta.class);
        CalendarioreporteTieneAlerta calendarioAlerta=(CalendarioreporteTieneAlerta) ServiceLocator.getInstanceBaseDAO().findAlerta(tipo);
        return calendarioAlerta;
    }
    /**
     * Borra un calendario reporte tiene alerta
     * @param calendarioAlerta 
     */
    public void deleteCalendarioAlerta(CalendarioreporteTieneAlerta calendarioAlerta){
        ServiceLocator.getInstanceCalendarioReporteTieneAlertaDAO().executeProcedureCalendarioreporteTieneAlerta
        ("CALL eliminarCalendarioTieneAlerta(\'" + calendarioAlerta.getCalendarioreporte().getCREid() + "\' , \'" + calendarioAlerta.getAlerta().getALEid() + "\')");
    }
    /**
     * Metodo para buscar calendario Alerta por ID
     * @param id de tipo entero
     * @return una entidad de calendarioreporteTieneAlerta correspondiente al id
     */
    public CalendarioreporteTieneAlerta findCalendarioAlertaByID(int id){
       return ServiceLocator.getInstanceCalendarioReporteTieneAlertaDAO().executeProcedureCalendarioreporteTieneAlertas("CALL buscarCalendarioTieneAlerta(\'" + id + "\')").get(0);
    }

}
