/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateTemaUnidad;
import mx.avanti.siract.entity.Temaunidad;

/**
 *
 * @author Eduardo
 */
public class TemaUnidadFacade {
    private final DelegateTemaUnidad delegate;
    
    public TemaUnidadFacade() {
        delegate = new DelegateTemaUnidad();
    }
    /**
     * Metodo para buscar un TemaUnidad
     * @param idTemaunidad
     * @return Tema unidad correspondiente al ID
     */
    public Temaunidad findTemauUnidad(String idTemaunidad){
        return delegate.getTemaUnidad(Integer.parseInt(idTemaunidad));
    }
    /**
     * Metodo para agregar un temaUnidad
     * @param temaunidad 
     */
    public void agregarTemaUnidad(Temaunidad temaunidad){
        delegate.saveTemaunidad(temaunidad);
    }
    /**
     *  Metodo para eliminar Tema unidad 
     * @param temaunidad 
     */
    public void eliminarTemaUnidad(Temaunidad temaunidad){
        delegate.deleteTemaunidad(temaunidad);
    }
    /**
     * MEtodo para buscar todos los TemaUnidad
     * @return Lista de TemaUnidad
     */
    public List<Temaunidad> buscarTodasTemaUnidad(){
        return delegate.findAll();
    }
    /**
     * Metodo para modificar un TemaUnidad
     * @param temaunidad 
     */
    public void modificarTemaUnidad(Temaunidad temaunidad){
        delegate.updateTemaunidad(temaunidad);
    }
    
    public List<Temaunidad> consultaTemaunidadFromWhere(String de, String campo, String criterio, String order){
        return delegate.consultaTemaunidadFromWhere(de, campo, criterio, order);
    }
}
