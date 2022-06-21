/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateUnidadAcademica;
import mx.avanti.siract.entity.Unidadacademica;

/**
 *
 * @author Maby_
 */
public class UnidadAcademicaFacade {
    DelegateUnidadAcademica delegateUnidadAcademica = new DelegateUnidadAcademica();
    public void terminarTransaccion(boolean completa){
        delegateUnidadAcademica.terminar(completa);
    }
     /**
     * Metodo para obtener todas las unidades academicas registradas
     * @return Lista de todas las unidades academicas
     */
    public List<Unidadacademica> BuscarUnidadesAcademicas() {
    
        return delegateUnidadAcademica.getUnidadesAcademicas();
    }
    /**
     * Metodo para agregar una unidad academica
     * @param unidadAcademica de tipo unidad academica con Id 0 para crear nuevo
     * registro
     */
    public void agregarUnidadAcademica(Unidadacademica unidadAcademica) {
        
        delegateUnidadAcademica.saveUnidadAcademica(unidadAcademica);
    }
    /**
     * Metodo para modificar un registro de unidad academica 
     * @param unidadAcademica de tipo unidad academica con todos sus atributos
     */
    public void actualizarUnidadAcademica(Unidadacademica unidadAcademica){
        delegateUnidadAcademica.updateUnidadAcademica(unidadAcademica);
    }
    /**
     * Metodo para eliminar una unidad academica
     * @param unidadAcademica de tipo unidad academica con todos sus atributos
     */
    public void eliminarUnidadAcademica(Unidadacademica unidadAcademica) {

        delegateUnidadAcademica.deleteUnidadAcademica(unidadAcademica);
    }
    /**
     * Metodo para buscar una unidad Academica por Id
     * @param id
     * @return Objeto de tipo Unidadacademica
     */
    public Unidadacademica buscarUnidadAcademicaPorId(int id) {
        return delegateUnidadAcademica.findUnidadAcademicaById(id);
    }
}
