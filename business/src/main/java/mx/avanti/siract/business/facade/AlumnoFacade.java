/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateAlumno;
import mx.avanti.siract.entity.Alumno;

/**
 *
 * @author Memo
 */
public class AlumnoFacade {
    private final DelegateAlumno delegateAlumno;


    public AlumnoFacade() {
        delegateAlumno = new DelegateAlumno();
    }

    /**
     * Metodo para obtener Profesores
     *
     * @return Lista de todos los Profesores
     */
    public List<Alumno> getListaAlumnos() {
        return delegateAlumno.findAll();
    }
    public List<String> getMatriculasalumnos() {
        return delegateAlumno.findMatricula();
    }
    public List<String> getNombresAlumnos() {
        return delegateAlumno.findNombre();
    }
    public List<String> getCorreosAlumnos() {
        return delegateAlumno.findCorreo();
    }
    public Alumno getMatriculasalumnos(int id) {
        return delegateAlumno.findMatricula(id);
    }
   public List<Alumno> consultarAlumnosPorUIP(int UIPId){
       return delegateAlumno.consultarAlumnosPorUIP(UIPId);
       
   }
}
