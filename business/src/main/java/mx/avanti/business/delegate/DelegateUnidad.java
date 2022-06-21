/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import mx.avanti.siract.integration.ServiceLocator;

/**
 * 
 * @author ODOSMO
 */
public class DelegateUnidad {

//    private List<Unidad> listaUnidad;
//    private List<Temaunidad> listaTemas;
//
//    public DelegateUnidad() {
//        listaUnidad = new ArrayList<Unidad>();
//    }
//
//    public List<Temaunidad> getTemasUnidad(Unidad u) {
//        return listaTemas;
//    }
//
//    public void setListaTemas(List<Temaunidad> listaTemas) {
//        this.listaTemas = listaTemas;
//    }
//
//    public void setListaUnidad(List<Unidad> listaUnidad) {
//        this.listaUnidad = listaUnidad;
//    }
//
    public List<Unidad> getListaUnidad(String de, String campo, String criterio, String order) {
//        listaUnidad = ServiceFacadeLocator.getfacadeUnidad().consultaUnidad(de, campo, criterio, order);
        return ServiceLocator.getInstanceUnidad().findFromWhereBExtra(de, campo, criterio, "Length(" + order + "), " + order + " ASC", "Unidad");       
    }
    
    /**
     * Metodo para obtener una unidad por id
     * @param id de la unidad a buscar
     * @return unidad correspondiente al id
     */
    public Unidad getUnidad(int id) {
        return ServiceLocator.getInstanceUnidad().find(id);
    }
    /**
     * Metodo para agregar una unidad
     * @param unidad de tipo Unidad con todos sus atributos
     */
    public void saveUnidad(Unidad unidad) {
        ServiceLocator.getInstanceUnidad().save(unidad);
    }
    /**
     * Metodo para eliminar una unidad
     * @param unidad de tipo Unidad con todos sus atributos 
     */
    public void deleteUnidad(Unidad unidad) {
        ServiceLocator.getInstanceUnidad().delete(unidad);
    }
    /**
     * Metodo que regresa una lista con todas las unidades
     * @return lista de todas las unidades
     */
    public List<Unidad> findAll() {
        return ServiceLocator.getInstanceUnidad().findAll();
    }
    /**
     * Metodo para modificar una unidad
     * @param nuevaUnidad de tipo Unidad con todos sus atributos
     */
    public void updateUnidad(Unidad nuevaUnidad){
        ServiceLocator.getInstanceUnidad().update(nuevaUnidad);
    }

//    /**
//     * Método para obtener las unidades dependiendo de la versión
//     * @param id
//     * @param vers
//     * @return 
//     */
//    public List<Unidad> buscarUnidadVersion(int id, int vers){
//        //return ServiceLocator.getInstanceUnidad().find
//        List<Unidad> unidades = ServiceLocator.getInstanceUnidad().findByOneParameter(String.valueOf(id), "contenidoTematicoCTid");
//        List<Unidad> auxiliar = new ArrayList<>();
//        for(Unidad unid : unidades){
//            if(unid.getVersion()==vers)
//                auxiliar.add(unid);
//        }
//        return auxiliar;
//        //return ServiceLocator.getInstanceUnidad().findFromWhereB("contenidoTematicoCTid", "contenidoTematicoCTid", String.valueOf(id)+" AND version="+String.valueOf(vers), "version DESC");
//    }
//    
//    /**
//     * Método para obtener las unidades dependiendo de la versión
//     * @param id
//     * @param ciclo
//     * @return 
//     */
//    public List<Unidad> buscarUnidadPorCiclo(int id, int ciclo){
//        return ServiceLocator.getInstanceUnidad().find
//        UnidadaprendizajeTieneContenidotematico sd = ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findFromWhereB("cTid", "cTid", String.valueOf(id)+" AND cicloEscolarCESid<="+String.valueOf(ciclo), "cicloEscolarCESid DESC").get(0);
//        List<Unidad> unidades = ServiceLocator.getInstanceUnidad().findByOneParameter(String.valueOf(id), "contenidoTematicoCTid");
//        List<Unidad> auxiliar = new ArrayList<>();
//        for(Unidad unid : unidades){
//            if(Objects.equals(unid.getVersion(), sd.getVersion()))
//                auxiliar.add(unid);
//        }
//        return auxiliar;
//        return ServiceLocator.getInstanceUnidad().findFromWhereB("contenidoTematicoCTid", "contenidoTematicoCTid", String.valueOf(id)+" AND version="+String.valueOf(vers), "version DESC");
//    }
    
//    public List<Unidad> consultaUnidades(String campo, String criterio) {
////        return ServiceFacadeLocator.getfacadeUnidad().consultaUnidades(campo, criterio);
////        ServiceLocator.getInstanceBaseDAO().setTipo(Unidad.class);
////        listaUnidad = ServiceLocator.getInstanceBaseDAO().findWhereExtra(campo, criterio, "Unidad", "uninumero");
//        return listaUnidad = ServiceLocator.getInstanceUnidad().findWhereExtra(campo, criterio, "Unidad", "uninumero");
//    }

}
