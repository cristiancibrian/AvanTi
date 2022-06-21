/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;
import java.util.List;
import mx.avanti.business.delegate.DelegateCalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
/**
 *
 * @author NOE_LOPEZ
 */
public class CalendarioreporteTieneAlertaFacade {

    DelegateCalendarioreporteTieneAlerta delegate;
    public CalendarioreporteTieneAlertaFacade(){
        delegate=new DelegateCalendarioreporteTieneAlerta();
    }
    
   /**
     * Metodo para consultar todos los registros de calendario tiene alerta
     * @return Regresa todos los registros de calendario reporte tiene alerta
     */
    public List<CalendarioreporteTieneAlerta> BuscarTodoCalendarioReporteTieneAlerta(){
        return delegate.getCalendarioReporteTieneAlerta();
       
    }

    /**
     * metodo para guardar un registro de calendarioAlerta
     * @param calendarioAlerta de tipo calendarioAlerta con ID 0 para crear
     * nuevo registro
     */
    public void GuardarCalendarioreporteTieneAlerta(CalendarioreporteTieneAlerta calendarioAlerta){
//       
         delegate.saveCalendarioreporteTieneAlerta(calendarioAlerta);
    }
    /**
     * Metodo para actualizar un registro de CalendarioAlerta
     * @param calendarioAlerta de tipo calendario alerta con todos sus atributos
     */
    public void ActualizarCalendarioreporteTieneAlerta(CalendarioreporteTieneAlerta calendarioAlerta){
        delegate.updateCalendarioreporteTieneAlerta(calendarioAlerta);
    }
    
    /**
     * Busca una alerta por tipo
     * @param tipo de tipo String que indica el tipo de la alerta
     * @return 
     */
    public CalendarioreporteTieneAlerta buscarPorTipo(String tipo){
        return delegate.findByTipo(tipo);
    }
    /**
     * Borra un calendario reporte tiene alerta
     * @param calendarioAlerta 
     */
    public void BorrarCalendarioAlerta(CalendarioreporteTieneAlerta calendarioAlerta){
        delegate.deleteCalendarioAlerta(calendarioAlerta);
    }
    /**
     * Metodo para buscar calendario Alerta por ID
     * @param id de tipo entero
     * @return una entidad de calendarioreporteTieneAlerta correspondiente al id
     */
    public CalendarioreporteTieneAlerta BuscarCalendarioAlertaPorID(int id){
       return delegate.findCalendarioAlertaByID(id);
    }
}
