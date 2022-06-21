/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.Articulos;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
import mx.avanti.siract.integration.ServiceLocator;


/**
 *
 * @author Adriana
 */
public class DelegatePoliticaevaluacion {

   
    public List<Politicaevaluacion> findAll() {
        return ServiceLocator.getInstancePoliticaevaluacion().findallpoliticas();
    }
    public Politicaevaluacion findPolitica(int id) {
        return ServiceLocator.getInstancePoliticaevaluacion().findpolitica(id);
    }
    //public void agregarPolitica(Politicaevaluacion politicaEvaluacion){
      //  ServiceLocator.getInstancePoliticaevaluacion().agregarPolitica(politicaEvaluacion);
    //}
    
    public List<Politicaevaluacion> getPoliticasProfesor(int profesorId){
        List<Politicaevaluacion> result =  new ArrayList<Politicaevaluacion>();
 
        try{
            // opcion 1 es para consultar por profesor
            result = ServiceLocator
                    .getInstancePoliticaevaluacion()
                    .ExecuteProcedureConsulta(
                     "CALL buscarPolitica(\'" + profesorId + "\', \'" + 1 + "\')");
            
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    
    public List<Politicaevaluacion> getPoliticasResponsable(int profesorId){
        List<Politicaevaluacion> result =  new ArrayList<Politicaevaluacion>();
 
        try{
            // opcion 2 es para consultar por Responsable de politica
            result = ServiceLocator
                    .getInstancePoliticaevaluacion()
                    .ExecuteProcedureConsulta(
                    "CALL buscarPolitica(\'" + profesorId + "\', \'" + 2 + "\')");
            
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    
    public List<Politicaevaluacion> getPoliticasAlumno(int alumnoId){
        List<Politicaevaluacion> result =  new ArrayList<Politicaevaluacion>();
 
        try{
            // opcion 3 es para consultar por alumno de politica
            result = ServiceLocator
                    .getInstancePoliticaevaluacion()
                    .ExecuteProcedureConsulta(
                     "CALL buscarPolitica(\'" + alumnoId + "\', \'" + 3 + "\')");
            
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    
    public List<Politicaevaluacion> getPoliticaPorUIP(int uaiId){
        List<Politicaevaluacion> result =  new ArrayList<Politicaevaluacion>();
 
        try{
            // opcion 4 es para consultar por uip de politica
            result = ServiceLocator
                    .getInstancePoliticaevaluacion()
                    .ExecuteProcedureConsulta(
                     "CALL buscarPolitica(\'" + uaiId + "\', \'" + 4 + "\')");
            
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    
   
    
   public Politicaevaluacion agregarPolitica(Politicaevaluacion politica, boolean esEnvio){
       Date fecha;
      
       try {
        fecha = (esEnvio) ? new Date(): new SimpleDateFormat("dd/MM/yyyy").parse("11/11/2011"); 

       }catch(Exception e){
           fecha= null;
           System.err.println("e");
       }
       Timestamp timestamp = new Timestamp(fecha.getTime());
       try{
           return ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL agregarPolitica("
                    + "\'" + politica.getAlumnoALId().getALId() + "\',"
                    + "\'" + politica.getResponsablePROId().getPROid()+ "\',"
                    + "\'" + timestamp + "\',"
                    + "\'" + politica.getUIPId().getUIPid() + "\',"
                    + "\'" + politica.getPOEComentario() + "\',"
                    + " \'" +  politica.getPOECriterioExentar()+ "\')").get(0);
          
       }catch(Exception ex){
           System.err.println(ex);
           return new Politicaevaluacion();
       }
   }
   
   
   public Politicaevaluacion actualizarPolitica(Politicaevaluacion politica, boolean esEnvio){
       Date fecha;
      
       try {
        fecha = (esEnvio) ? new Date(): new SimpleDateFormat("dd/MM/yyyy").parse("11/11/2011"); 

       }catch(Exception e){
           fecha= null;
           System.err.println("e");
       }
       Timestamp timestamp = new Timestamp(fecha.getTime());
       try{
            return ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL ActualizarPolitica("
                    + "\'" + timestamp+ "\',"
                    + "\'" + politica.getPOEId()+ "\',"
                    + "\'" + politica.getPOEComentario()+ "\',"
                    + "\'" + politica.getPOECriterioExentar()+ "\',"
                    + " \'" + 1 + "\')").get(0);
        
       }catch(Exception ex){
           System.err.println(ex);
           return new Politicaevaluacion();
       }
   }
   
   
   public boolean ActualizarAceptacionAlumno(Politicaevaluacion politica){
       Date fecha;
      
       try {
        fecha =  new Date(); 

       }catch(Exception e){
           fecha= null;
           System.err.println("e");
       }
       Timestamp timestamp = new Timestamp(fecha.getTime());
       try{
             ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL ActualizarPolitica("
                    + "\'" + timestamp+ "\',"
                    + "\'" + politica.getPOEId()+ "\',"
                    + "\'" + politica.getPOEComentario()+ "\',"
                    + "\'" + politica.getPOECriterioExentar()+ "\',"     
                    + " \'" + 2 + "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
   
   public boolean ActualizarAceptacionResponsable(Politicaevaluacion politica){
      Date fecha;
      
       try {
        fecha =  new Date(); 

       }catch(Exception e){
           fecha= null;
           System.err.println("e");
       }
       Timestamp timestamp = new Timestamp(fecha.getTime());
       try{
             ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL ActualizarPolitica("
                    + "\'" + timestamp+ "\',"
                    + "\'" + politica.getPOEId()+ "\',"
                    + "\'" + politica.getPOEComentario()+ "\',"
                    + "\'" + politica.getPOECriterioExentar()+ "\',"  
                    + " \'" + 3 + "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
   
   
   
   public boolean ActualizarRechazoAlumno(Politicaevaluacion politica){
      Date fecha;
      
       try {
        fecha =  new Date(); 

       }catch(Exception e){
           fecha= null;
           System.err.println("e");
       }
       Timestamp timestamp = new Timestamp(fecha.getTime());
       try{
             ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL ActualizarPolitica("
                    + "\'" + timestamp+ "\',"
                    + "\'" + politica.getPOEId()+ "\',"
                    + "\'" + politica.getPOEComentario()+ "\',"
                    + "\'" + politica.getPOECriterioExentar()+ "\',"  
                    + " \'" + 4 + "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
   
   
   public boolean ActualizarRechazoResponsable(Politicaevaluacion politica){
      Date fecha;
      
       try {
        fecha =  new Date(); 

       }catch(Exception e){
           fecha= null;
           System.err.println("e");
       }
       Timestamp timestamp = new Timestamp(fecha.getTime());
       try{
             ServiceLocator
                .getInstancePoliticaevaluacion()
                .storeProcedurePolitica(
                 "CALL ActualizarPolitica("
                    + "\'" + timestamp+ "\',"
                    + "\'" + politica.getPOEId()+ "\',"
                    + "\'" + politica.getPOEComentario()+ "\',"
                    + "\'" + politica.getPOECriterioExentar()+ "\',"  
                    + " \'" + 5 + "\')");
           return true;
       }catch(Exception ex){
           System.err.println(ex);
           return false;
       }
   }
   
    public List<Politicaevaluacion> getPoliticaPorResponsable(int idResponsable){
        List<Politicaevaluacion> result =  new ArrayList<Politicaevaluacion>();
 
        try{
            // opcion 4 es para consultar por uip de politica
            result = ServiceLocator
                    .getInstancePoliticaevaluacion()
                    .ExecuteProcedureConsulta(
                     "CALL buscarPolitica(\'" + idResponsable + "\', \'" + 5 + "\')");
            
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    
    public List<Articulos> findAllArticulos(){
        return ServiceLocator.getInstancePoliticaevaluacion().findAllArticulos();
    }
    public List<Politicaevaluacion> getPoliticasAceptadasAlumno(int alumnoId){
        List<Politicaevaluacion> result = new ArrayList<Politicaevaluacion>();
        try{
            result = ServiceLocator
                    .getInstancePoliticaevaluacion()
                    .ExecuteProcedureConsulta(
                     "CALL buscarPoliticaAceptadaPorAlumno(\'" + alumnoId +"\')");
            
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    
    }

   
    
    

    
    

   
   
   
   
      /**
     * Termina una transaccion
     * @param exitoso indica si la operacion fue exitosa al llamar este metodo
     */
    public void endTransaccion(boolean exitoso){
        ServiceLocator.getInstancePoliticaevaluacion().endTransaction(exitoso);
    }
    

}
