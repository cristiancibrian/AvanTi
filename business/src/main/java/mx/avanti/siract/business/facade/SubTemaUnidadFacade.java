/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateSubTemaUnidad;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Y
 */
public class SubTemaUnidadFacade {
   DelegateSubTemaUnidad subTemaUnidadDelegate;
    
    public SubTemaUnidadFacade(){
        subTemaUnidadDelegate = new DelegateSubTemaUnidad();
    }
    
    public void agregarSubtemaunidad(Subtemaunidad subtemaunidad){
   
       subTemaUnidadDelegate.saveSubtemaunidad(subtemaunidad);
    }
    
//    public void agregarSubtemaunidadCatalogo(int numero, Temaunidad tema){
//        float porcentaje = (float) 0.0;
//        float horas = (float) 0.0;
//        for(int i=0;i<numero;i++){
//            Subtemaunidad subtemaunidad = new Subtemaunidad(tema,"0","Subtema " + (i+1),porcentaje,horas);
//            subTemaUnidadDelegate.agregarSubtemaunidad(subtemaunidad);
//        }
//    }
    
//    public List<Subtemaunidad> consultaSubtemasUnidad(String de,String campo,String criterio,String order){
//        List<Subtemaunidad> resultado = null;
//        resultado =  subTemaUnidadDelegate.listaTemasUnidadDe(de, campo, criterio, order);
//        return resultado;
//    }
//    
//      public List<Subtemaunidad> obtenerUnidadesDeTema(String de,String campo,String criterio,String order){
//        List<Subtemaunidad> resultado = null;
//        resultado = subTemaUnidadDelegate.obtenerUnidadesDeTema(de, campo, criterio, order);
//        return resultado;
//    }
    
    public List<Subtemaunidad> consultaSubtemaunidadFromWhere(String de, String campo, String criterio, String order){
        return subTemaUnidadDelegate.consultaSubtemasFromWhere(de, campo, criterio, order);
    }
    
    public void eliminarSubtemaunidad(Subtemaunidad subtemaunidad){
        subTemaUnidadDelegate.deleteUnidad(subtemaunidad);
    }

    public List<Subtemaunidad> findAll(){
        return subTemaUnidadDelegate.findAll();       
    }
    public Subtemaunidad find(String id){
       
        return subTemaUnidadDelegate.find(id);
  
    }
    
    public void modificarSubTenaUnidad(Subtemaunidad subtemaunidad){
        subTemaUnidadDelegate.updateSubTemaUnidad(subtemaunidad);
    }
    
}
