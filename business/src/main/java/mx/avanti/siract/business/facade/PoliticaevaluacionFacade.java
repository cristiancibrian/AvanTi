/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegatePoliticaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.Articulos;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
/**
 *
 * @author Memo
 */
public class PoliticaevaluacionFacade {
    private final DelegatePoliticaevaluacion delegatePoliticaevaluacion;


    public PoliticaevaluacionFacade() {
        delegatePoliticaevaluacion = new DelegatePoliticaevaluacion();
    }

    /**
     * Metodo para obtener Profesores
     *
     * @return Lista de todos los Profesores
     */
    public List<Politicaevaluacion> getListapoliticasevaluacion() {
        return delegatePoliticaevaluacion.findAll();
    }
    public Politicaevaluacion getpoliticaevaluacion(int id) {
        return delegatePoliticaevaluacion.findPolitica(id);
    }
    public List<Politicaevaluacion> getPoliticasAceptadasAlumno(int alumnoId){
        return delegatePoliticaevaluacion.getPoliticasAceptadasAlumno(alumnoId);
    }
    
 
    
//    public void agregarPolitica(Politicaevaluacion politicaEvaluacion, boolean esEnvio){
//        delegatePoliticaevaluacion.agregarPolitica(politicaEvaluacion, esEnvio);
//    }
    
    public List<Politicaevaluacion> getPoliticasProfesor(int profesorId){
       return delegatePoliticaevaluacion.getPoliticasProfesor(profesorId);
    }
    
    public List<Politicaevaluacion> getPoliticasResponsable(int profesorId){
        return delegatePoliticaevaluacion.getPoliticasProfesor(profesorId);
    }
    
    public List<Politicaevaluacion> getPoliticasAlumno(int alumnoId){
        
        return delegatePoliticaevaluacion.getPoliticasAlumno(alumnoId);
    }
    
   
    // METODOS CON STORED PROCEDURES
    
   public Politicaevaluacion agregarPolitica(Politicaevaluacion politica, boolean esEnvio){
      return delegatePoliticaevaluacion.agregarPolitica(politica, esEnvio);
   }
   
   
   public Politicaevaluacion actualizarPolitica(Politicaevaluacion politica, boolean esEnvio){
      return delegatePoliticaevaluacion.actualizarPolitica(politica, esEnvio);
   }
   
   
   public boolean ActualizarAceptacionAlumno(Politicaevaluacion politica){
      
       return delegatePoliticaevaluacion.ActualizarAceptacionAlumno(politica);
   }
   
   public boolean ActualizarAceptacionResponsable(Politicaevaluacion politica){
     return delegatePoliticaevaluacion.ActualizarAceptacionResponsable(politica);
   }
   public List<Politicaevaluacion> getPoliticaPorUIP(int uaiId){
       return delegatePoliticaevaluacion.getPoliticaPorUIP(uaiId);
   }
    
   
    public boolean ActualizarRechazoAlumno(Politicaevaluacion politica){
      return delegatePoliticaevaluacion.ActualizarRechazoAlumno(politica);
   }
   
   
   public boolean ActualizarRechazoResponsable(Politicaevaluacion politica){
      return delegatePoliticaevaluacion.ActualizarRechazoResponsable(politica);
   }
   
   public List<Politicaevaluacion> getPoliticaPorResponsable(int idResponsable){
       return delegatePoliticaevaluacion.getPoliticaPorResponsable(idResponsable);
    }
    public List<Articulos> findAllArticulos(){
        return delegatePoliticaevaluacion.findAllArticulos();
    }
    
    
    
    
   
   
     
     
        /**
     * Termina una transaccion, si recibe un true realiza un commit
     * Si recibe un false realiza un rollback
     * @param exitoso Indica si la operacion fue exitosa en el momento que se llama el metodo.
     */
   public void terminarTransaccion(boolean exitoso){
       delegatePoliticaevaluacion.endTransaccion(exitoso);
   }
    

}
