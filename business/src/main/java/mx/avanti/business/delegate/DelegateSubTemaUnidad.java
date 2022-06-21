/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Y
 */
public class DelegateSubTemaUnidad {
    
//    private List<Subtemaunidad> listsubtemas;
//    
//    public DelegateSubTemaUnidad(){
//        listsubtemas=new ArrayList<Subtemaunidad> ();
//    }
//    
//    public List<Subtemaunidad> getTemasUnidad(Subtemaunidad s) {
//        return listsubtemas;
//    }
//
//    public void setListaTemas(List<Subtemaunidad> listaTemas) {
//        this.listsubtemas = listaTemas;
//    }
//
//    public void setListaUnidad(List<Subtemaunidad> listaUnidad){
//        this.listsubtemas=listaUnidad;
//    }
    
    /**
     * @author Mario
     * @return 
     */
    public List<Subtemaunidad> consultaSubtemasFromWhere(String de,String campo,String criterio,String order){
        List<Subtemaunidad> resultado = null;
        
        resultado = ServiceLocator.getInstanceSubTemaUnidadDAO().findFromWhereBExtra(de, campo, criterio,"Length("+order+"), "+order+" ASC","Subtemaunidad");
        return resultado;
    }
//     public List<Subtemaunidad> obtenerUnidadesDeTema(String de,String campo,String criterio,String order){
//        List<Subtemaunidad> resultado = null;
//       
//        resultado = ServiceLocator.getInstanceSubTemaUnidadDAO().findFromWhereB(de, campo, criterio, order);
//        return resultado;
//    }
    
    public List<Subtemaunidad> findAll(){

        return ServiceLocator.getInstanceSubTemaUnidadDAO().findAll();
        
    }
    public void saveUnidad(Subtemaunidad subtemaunidad){
       
            ServiceLocator.getInstanceSubTemaUnidadDAO().save(subtemaunidad);
    }
//        ServiceFacadeLocator.getFacadeSubtemaunidad().agregarSubtemaunidad(subtemaunidad);
    
    public void deleteUnidad(Subtemaunidad subtemaunidad){
        ServiceLocator.getInstanceSubTemaUnidadDAO().delete(subtemaunidad);

    }
    
      public void saveSubtemaunidad(Subtemaunidad subtemaunidad){
          
            ServiceLocator.getInstanceSubTemaUnidadDAO().save(subtemaunidad);
        }
    
      
      public Subtemaunidad find(String id){
          return (Subtemaunidad) ServiceLocator.getInstanceSubTemaUnidadDAO().find(Integer.parseInt(id));
    }
    
      public void updateSubTemaUnidad(Subtemaunidad subtemaunidad){
          ServiceLocator.getInstanceSubTemaUnidadDAO().update(subtemaunidad);
      }
}
