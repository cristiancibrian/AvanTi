/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateProfesorPertenecePE;
import mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo;

/**
 *
 * @author Memo
 */
public class ProfesorPertenecePEFacade {

    private final DelegateProfesorPertenecePE delegateProfesorPertenecePE;

    public ProfesorPertenecePEFacade() {
        delegateProfesorPertenecePE = new DelegateProfesorPertenecePE();
    }

    public void terminarTransaccion(boolean completa){
        delegateProfesorPertenecePE.terminar(completa);
    }
    
    /**
     * Metodo para encontrar todos los profesores que pertenecen a un programa
     * educativo
     *
     * @return Regresa una lista de los profesores
     */
    public List<ProfesorPerteneceProgramaeducativo> getListaProfesorPertenecePE() {
        return delegateProfesorPertenecePE.findAll();
    }

    /**
     * Metodo para agregar un profesor a un programa educativo
     *
     * @param profesorPertenecePE Objeto de tipo
     * ProfesorPerteneceProgramaEducativo
     */
    public ProfesorPerteneceProgramaeducativo agregarProfesorPertenecePE(ProfesorPerteneceProgramaeducativo profesorPertenecePE) {
        return delegateProfesorPertenecePE.save(profesorPertenecePE);
    }

    /**
     * Metodo para eliminar un Profesor pertenece programa educativo
     *
     * @param profesorPertenecePE Objeto de tipo
     * ProfesorPerteneceProgramaEducativo
     */
    public ProfesorPerteneceProgramaeducativo eliminarProfesorPertenecePE(ProfesorPerteneceProgramaeducativo profesorPertenecePE) {
        return delegateProfesorPertenecePE.delete(profesorPertenecePE);           
    }

    /**
     * Metodo para modificar a Profesor Pertenece a Programa Educativo
     * @param profesorPertenecePE Objeto tipo Profesor Pertenece Programa 
     * Educativo
     * @deprecated 
     */
   public void modificarProfesorPertenecePE(ProfesorPerteneceProgramaeducativo profesorPertenecePE){
       delegateProfesorPertenecePE.update(profesorPertenecePE);
   }

    /**
     * Metodo para obtener los profesores por id de profesor
     *
     * @param idProfesor ID del profesor a buscar
     * @return Regresa una lista de los profesores encontrados
     */
    public List<ProfesorPerteneceProgramaeducativo> getProfesorPertenecePorProfesor(int idProfesor) {
        return delegateProfesorPertenecePE.findById(idProfesor);
    }
}
