/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.integration.ServiceLocator;
/**
 *
 * @author NOE_LOPEZ
 */
public class DelegateAsignarProfesor implements Serializable {
    private List<Profesor> listaProfesor;
    private List<Profesor> listacriteriaProfesor;
    private List<Areaconocimiento> listaAreaConocimiento;
    private List<Planestudio> listaPlanEstudio;
    private List<Programaeducativo> listaProgramaEducativo;

    public DelegateAsignarProfesor() {
        listaProfesor = new ArrayList<Profesor>();
        listaAreaConocimiento = new ArrayList<Areaconocimiento>();
        listaPlanEstudio = new ArrayList<Planestudio>();
        listaProgramaEducativo = new ArrayList<Programaeducativo>();
    }
    /**
     * Regresa La lista de profesores
     *
     * @return
     */
    public List<Profesor> getListaProfesor() {
        return ServiceLocator.getInstanceProfesor().findAll();
    }
    /**
     * Asiganar lista de profesores
     *
     * @param listaProfesor
     */
    public void setListaProfesor(List<Profesor> listaProfesor) {
        this.listaProfesor = listaProfesor;
    }
    /**
     * Agregar Profesor
     *
     * @param profesor
     */
    public void agregarProfesor(Profesor profesor) {
        Profesor resultado = null;
        if (profesor.getPROid() != null) {
            resultado = ServiceLocator.getInstanceProfesor().find(profesor.getPROid());
        }
        if (resultado != null) {
            profesor.setPROid(resultado.getPROid());
            ServiceLocator.getInstanceProfesor().saveOrUpdate(profesor);
        } else {
            ServiceLocator.getInstanceProfesor().saveOrUpdate(profesor);
        }
    }
    /**
     * Eliminar Profesor
     *
     * @param profesor
     */
    public void eliminarProfesor(Profesor profesor) {
        ServiceLocator.getInstanceProfesor().delete(profesor);
    }
    /**
     * Regresa todas las areas de conocimiento
     *
     * @return
     */
    public List<Areaconocimiento> getListaAreaConocimiento() {
        return ServiceLocator.getInstanceAreaconocimiento().findAll();
    }
    /**
     * Set Area de conociemiento
     *
     * @param listaAreaConocimiento
     */
    public void setListaAreaConocimiento(List<Areaconocimiento> listaAreaConocimiento) {
        this.listaAreaConocimiento = listaAreaConocimiento;
    }
    /**
     * Agregar Area de conocimiento
     *
     * @param areaconocimiento
     */
    public void agregarAreaConocimiento(Areaconocimiento areaconocimiento) {
        Areaconocimiento resultado = null;
        if (areaconocimiento.getACOid() != null) {
            resultado=ServiceLocator.getInstanceAreaconocimiento().find(areaconocimiento.getACOid());
        }
        if (resultado != null) {
            areaconocimiento.setACOid(resultado.getACOid());
            ServiceLocator.getInstanceAreaconocimiento().saveOrUpdate(areaconocimiento);
        } else {
            ServiceLocator.getInstanceAreaconocimiento().save(areaconocimiento);
        }
    }
    /**
     * Eliminar Area de conocimiento
     * @param areaconocimiento 
     */
    public void eliminarAreaConocimiento(Areaconocimiento areaconocimiento) {
        ServiceLocator.getInstanceAreaconocimiento().delete(areaconocimiento);
    }
    /**
     * Get Lista de Plan de estudios
     * @return 
     */
    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }
    /**
     * Set lista de plan de estudios
     * @param listaPlanEstudio 
     */
    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }
    /**
     * getLista Criteria Profesor
     * @return 
     */
    public List<Profesor> getListacriteriaProfesor() {
        return listacriteriaProfesor;
    }
    /**
     * setLista criteria profesor
     * @param listacriteriaProfesor 
     */
    public void setListacriteriaProfesor(List<Profesor> listacriteriaProfesor) {
        this.listacriteriaProfesor = listacriteriaProfesor;
    }
    //CRITERIA
    /**
     * Obtner los planes por programa 
     * @param idPrograma
     * @return 
     */
//    public List<Planestudio> getPlanesByPrograma(int idPrograma) {
//        listaPlanEstudio=ServiceLocator.getInstancePlanestudio().findByCriteria(idPrograma);
//        return listaPlanEstudio;
//    }
    /**
     * Obtener profesor por programa educativo
     * @param idPrograma
     * @return 
     */
    public List<Profesor> getProfesor(int idPrograma) {
        List<Profesor> result = null;
        ServiceLocator.getInstanceBaseDAO().setTipo(Profesor.class);
        result = ServiceLocator.getInstanceProfesor().findFromWhere("programaeducativos", "pedid", String.valueOf(idPrograma));
        return result;
        //return ServiceLocator.getInstanceProfesor().getProfesor(idPrograma);
    }
}