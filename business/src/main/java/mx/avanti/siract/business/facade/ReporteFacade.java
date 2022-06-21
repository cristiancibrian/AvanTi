/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegateReporte;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Reporte;

/**
 *
 * @author Y
 * @author Mario | Refactor
 */
public class ReporteFacade implements Serializable {
    private DelegateReporte delegate = new DelegateReporte();
    private List<Reporte> listaReporte;

    public ReporteFacade() {
        listaReporte = new ArrayList<Reporte>();
    }
    
    /**
     * Obtiene todos los Reportes
     * @return Lista de Reporte 
     */
    public List<Reporte> getListaReporte() {
        listaReporte = delegate.findAll();
        return listaReporte;
    }
 
    /**
     * Agrega Reporte
     * @param reporte 
     */
    public boolean agregarReporte(Reporte reporte) {
        return delegate.save(reporte);
    }
    
    /**
     * Obtiene el reporte especifico
     * @param id el id del reporte a buscar
     * @return 
     */
    public Reporte buscarPorId(int id){
        return delegate.findById(id);
    }
    
    /**
     * Obtiene el reporte correspondiente a un ract y un nodo
     * @param idNodo el id del nodo 
     * @param tipoNodo el tipo de nodo
     * @param ractId el id del RACT
     * @return 
     */
    public Reporte buscarPorRACTyNodo(int idNodo, String tipoNodo, int ractId){
        return delegate.findByRACTAndNode(idNodo, tipoNodo, ractId);
    }
    
    /**
     * Obtiene los reportes anteriores utilizando el puente de
     * unidad aprendizaje imparte profesor
     * @param idNodo
     * @param tipoNodo
     * @param ractId
     * @param uipId
     * @return 
     */
    public Reporte buscarPorRACTyNodo(int idNodo, String tipoNodo, int ractId, int uipId){
        return delegate.findByRACTAndNode(idNodo, tipoNodo, ractId, uipId);
    }
    
    /**
     * Borra el Reporte
     * @param repId el id del reporte a borrar
     * @return 
     */
   public boolean eliminarReporte(int repId) {
        return delegate.deleteById(repId);
    }
    
    /**
     * Elimina Reporte
     * @param reporte la instancia del reporte a borrar
     * @return
     */
    public boolean eliminarReporte(Reporte reporte) {
        return delegate.delete(reporte);
    }
     
    /**
     * Actualiza el reporte de la base de datos
     * @param reporte la instancia del reporte a actualizar
     * con sus nuevos valores
     */
    public void actualizarReporte(Reporte reporte){
        delegate.update(reporte);
    }
    
    /**
     * Obtiene los reportes actuales y anteriores
     * @param tipoGrupo el tipo de grupo (L,C,T)
     * @param ce el ciclo escolar
     * @param ractNumero el numero de ract
     * @param uip_id el id de la unidad de aprendizaje imparte profesor
     * @return 
     */
    public List<Reporte> consultarReportesActualesYAnteriores(String tipoGrupo, String ce, String ractNumero, int uip_id){
        return delegate.getLatest(tipoGrupo, ce, ractNumero, uip_id);
    }

    /**
     * Obtiene los reportes dependiendo de una práctica de laboratorio
     * @param pRLid
     * @return 
     */
    public List<Reporte> consultarReportePorPRLid(int pRLid){
        return delegate.findByPRLid(pRLid);
    }
    
    /**
     * Obteiene los reportes dependiendo de una práctica de taller
     * @param pRTid
     * @return 
     */
    public List<Reporte> consultarReportePorPRTid(int pRTid){
        return delegate.findByPRTid(pRTid);
    }
    
    /**
     * Obtiene los reportes dependiendo de una unidad
     * @param uNIid
     * @return 
     */
    public List<Reporte> consultarReportePorUNIid(int uNIid){
        return delegate.findByUNIid(uNIid);
    }
    
    /**
     * Obtiene todos los reportes registrados en una unidad de aprendizaje
     * independientemente del profesor asignado
     * @param uIPid id de la unidad de aprendizaje
     * @param tiposubgrupo
     * @return
     */
    public List<Reporte> consultarUltimosReportesPorUnidad(int uIPid, String tiposubgrupo){
        return delegate.consultarUltimosReportesPorUnidad(uIPid, tiposubgrupo);
    }
}
