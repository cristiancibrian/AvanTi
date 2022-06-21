/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Adriana
 */
public class DelegateAlumno {

   
    public List<Alumno> findAll() {
        return ServiceLocator.getInstanceAlumno().findalumnos();
    }
    
    public List<String> findNombre() {
        return ServiceLocator.getInstanceAlumno().findalumnosnombre();
    }
    public List<String> findCorreo() {
        return ServiceLocator.getInstanceAlumno().findalumnosnombre();
    }
    public List<String> findMatricula() {
        return ServiceLocator.getInstanceAlumno().findalumnosnombre();
    }
    public Alumno findMatricula(int id) {
        return ServiceLocator.getInstanceAlumno().findalumnopormatricula(id);
    }
public List<Alumno> consultarAlumnosPorUIP(int UIPId){
       
       try{
           return ServiceLocator
                .getInstanceAlumno()
                .ExecuteProcedureConsultaAlumno(
                 "CALL buscarAlumnos("
                   + " \'" + UIPId + "\')");
           
       }catch(Exception ex){
           System.err.println(ex);
           return new ArrayList<Alumno>();
       }
   }


}
