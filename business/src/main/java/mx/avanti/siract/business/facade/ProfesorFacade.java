/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import mx.avanti.business.delegate.DelegateProfesor;
import mx.avanti.siract.entity.Profesor;

import java.util.List;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Usuario;

/**
 *
 * @author Memo
 */
public class ProfesorFacade {
    private final DelegateProfesor delegateProfesor;


    public ProfesorFacade() {
        delegateProfesor = new DelegateProfesor();
    }
    
     public Usuario findByUsuarioId(int idUsu){
        return delegateProfesor.findByUsuarioId(idUsu);
     }

    public void terminarTransaccion(boolean completa){
        delegateProfesor.terminar(completa);
    }
    
    /**
     * Metodo para obtener Profesores
     *
     * @return Lista de todos los Profesores
     */
    public List<Profesor> getListaProfesor() {
        return delegateProfesor.findAll();
    }
    

    /**
     * Metodo para encontrar un Profesor por Id
     *
     * @param id Id del Profesor
     * @return El profesor encontrado
     */
    public Profesor findProfesorById(int id) {
        return delegateProfesor.findById(id);
    }

    /**
     * Metodo para agregar un Profesor
     *
     * @param profesor Objeto de tipo Profesor
     */
    public Profesor agregarProfesor(Profesor profesor) {
        return delegateProfesor.save(profesor);
    }
    
    public Profesor modificarListaUA(Profesor profesor, Unidadacademica unidad){
        return delegateProfesor.guardarProfesorUAC(profesor, unidad);
    }

    /**
     * Metodo para modificar un Profesor
     *
     * @param profesor Objeto de tipo Profesor
     */
    public Profesor modificarProfesor(Profesor profesor) {
        return delegateProfesor.update(profesor);
    }

    /**
     * Metodo para eliminar Profesor
     *
     * @param profesor Objeto de tipo Profesor
     */
    public Profesor eliminarProfesor(Profesor profesor) {
        return delegateProfesor.delete(profesor);
    }
    
    /**
     * Metodo para obtener un Profesor por Id del Usuario
     * @param usuarioId Id del Usuario
     * @return Profesor encontrado
     */
    public Profesor buscarProfesorPorUsuario(int usuarioId){
        return delegateProfesor.findByUserId(usuarioId); 
    }
    
    /**
     * Metodo para encontrar Profesores por Responsable de Programa Educativo
     * y Ciclo Escolar
     * @param rpeId Id del Responsable del Programa Educativo
     * @param ce Ciclo Escolar
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> buscarPorRPEyCE(int rpeId, String ce){
        return delegateProfesor.findByRPEYCE(rpeId, ce);
    }
    
    /**
     * Metodo para buscar Profesores por Programa Educativo y Ciclo Escolar
     * @param pe Id del Programa Educativo
     * @param ce Ciclo escolar
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> buscarPorPEyCE(int pe, String ce){
        return delegateProfesor.findByPEYCE(pe, ce);
    }
    
    /**
     * Metodo para obtener Profesores por Id del Coordinador y Ciclo Escolar
     * @param coordinadorId Id del coordinador
     * @param ce Ciclo Escolar
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> buscarPorCoordinadoryCE(int coordinadorId, String ce){
        return delegateProfesor.findByCoordinadorYCE(coordinadorId, ce);
    }

    /**
     * Obtiene los Profesores que pertenecen a un Programa Educativo 
     * @param pe Id del programa educativo
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> buscarPorPE(int pe){
        return delegateProfesor.findByPE(pe);
    }

    public void modificarProfesorRpeList(Profesor viejoRPE) {
        delegateProfesor.removerRpe(viejoRPE);
    }
    public Profesor buscarRPEporPEyCE(int PE){
        return delegateProfesor.findbyPEandCE(PE);
    } 

}
