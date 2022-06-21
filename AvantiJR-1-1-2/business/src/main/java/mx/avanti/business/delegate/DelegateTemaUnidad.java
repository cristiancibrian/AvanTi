/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Oscar
 */
public class DelegateTemaUnidad {

    public DelegateTemaUnidad() {
    }
    /**
     * Metodo para agregar temaUnidad
     * @param temaunidad de tipo Temaunidad con todos sus atributos
     */
    public void saveTemaunidad(Temaunidad temaunidad) {
        ServiceLocator.getInstanceTemaunidad().save(temaunidad);
    }
    /**
     * Metodo para actualizar temaUnidad
     * @param temaunidad de tipo Temaunidad con todos sus atributos
     */
    public void updateTemaunidad(Temaunidad temaunidad) {
        ServiceLocator.getInstanceTemaunidad().update(temaunidad);
    }
    /**
     * Metodo para eliminar temaUnidad
     * @param temaunidad de tipo Temaunidad con tdos sus atributos
     */
    public void deleteTemaunidad(Temaunidad temaunidad) {
       ServiceLocator.getInstanceTemaunidad().delete(temaunidad);
    }
    /**
     * Metodo para consultar temaUnidad
     * @param id de temaUnidad a buscar
     * @return temaUnidad correspondiennte al id
     */
    public Temaunidad getTemaUnidad(int id){
        return ServiceLocator.getInstanceTemaunidad().find(id);       
    }
    /**
     * Metodo para consultar los temaUnidad
     * @return Lista de los temaUnidad
     */
    public List<Temaunidad> findAll(){
        return ServiceLocator.getInstanceTemaunidad().findAll();
    }
    
    public List<Temaunidad> consultaTemaunidadFromWhere(String de, String campo, String criterio, String order) {
        List<Temaunidad> resultado = null;
        ServiceLocator.getInstanceBaseDAO().setTipo(Temaunidad.class);

        resultado = ServiceLocator.getInstanceTemaunidad().findFromWhereBExtra(de, campo, criterio,"Length("+order+"), "+order+" ASC","Temaunidad");
        return resultado;
    }
//
//    public void agregarTemaunidadCatalogo(int numero, Unidad uni){
//        Float horas = (float)0.0;
//        float porcentaje = (float) 0.0;
//        for(int i=0;i<numero;i++){
//            Temaunidad temaunidad = new Temaunidad(uni,"0","Tema " + (i+1),porcentaje,horas,true,null);
//            //ServiceLocator.getInstanceBaseDAO().save(temaunidad);
//            ServiceLocator.getInstanceTemaunidad().save(temaunidad);
//        }
//    }
}
