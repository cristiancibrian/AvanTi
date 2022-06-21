/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateUnidad;
import mx.avanti.siract.entity.Unidad;

/**
 * 
 * @author ODOSMO
 */
public class UnidadFacade {
    DelegateUnidad delegateUnidad = new DelegateUnidad();

    /**
     * Metodo para buscar una unidad
     * @param id
     * @return unidad correspondiente al id
     */
    public Unidad BuscarUnidad(int id) {
        return delegateUnidad.getUnidad(id);
    }
    /**
     * Metodo para agregar una unidad 
     * @param unidad 
     */
    public void agregarUnidad(Unidad unidad) {
        delegateUnidad.saveUnidad(unidad);
    }
    /**
     * Metodo para eliminar una unidad
     * @param unidad 
     */
    public void eliminarUnidad(Unidad unidad) {
        delegateUnidad.deleteUnidad(unidad);
    }
    /**
     * Metodo para consultar todas las unidades
     * @return lista de todas las unidades
     */
    public List<Unidad> consultaUnidades() {
        return delegateUnidad.findAll();
    }
    /**
     * Metodo para actualizar una unidad
     * @param nuevaUnidad 
     */
    public void modificarUnidad(Unidad nuevaUnidad){
        delegateUnidad.updateUnidad(nuevaUnidad);
    }
    
    public List<Unidad> consultaUnidadFromWhere(String de, String campo, String criterio, String order){
        return delegateUnidad.getListaUnidad(de, campo, criterio, order);
    }

//    /**
//     * Método para obtener las unidades de una versión en específico
//     * @param cTid
//     * @param version
//     * @return 
//     */
//    public List<Unidad> consultaUnidadVersion(int cTid, int version){
//        return delegateUnidad.buscarUnidadVersion(cTid, version);
//    }
//    
//    /**
//     * Método para obtener las unidades del ciclo
//     * @param cTid
//     * @param ciclo
//     * @return 
//     */
//    public List<Unidad> consultaUnidadCiclo(int cTid, int ciclo){
//        return delegateUnidad.buscarUnidadPorCiclo(cTid, ciclo);
//    }
}
