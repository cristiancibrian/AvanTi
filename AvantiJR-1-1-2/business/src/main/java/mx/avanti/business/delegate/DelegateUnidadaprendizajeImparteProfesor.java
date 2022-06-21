/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.math.BigInteger;
import java.util.List;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.integration.ServiceLocator;
/**
 *
 * @author Eduardo
 */
public class DelegateUnidadaprendizajeImparteProfesor {

    /**
     * Metodo para agregar una asignacion de Unidad Aprendizaje Imparte Profesor
     *
     * @param uaip Objeto tipo Unidad Aprendizaje Imparte Profesor
     */
    public void save(UnidadaprendizajeImparteProfesor uaip) {
//        ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().save(uaip);
        
         List<UnidadaprendizajeImparteProfesor> result;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL agregarAsignacion ( \'" +
             
               uaip.getProfesorPROid().getPROid()+  "\', \'" + uaip.getUnidadAprendizajeUAPid().getUAPid() +
                     "\', \'" + uaip.getGrupoGPOid().getGPOid() +  "\', \'" + uaip.getUIPtipoSubgrupo()+
                     "\', \'" + uaip.getUIPsubgrupo()+  "\', \'" + uaip.getCicloEscolarCESid().getCESid()+"\')");
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n Result " + result);
        }catch(Exception e){
            System.err.println("Error en agregar asignacion" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo para actualizar una asignacion de Unidad Aprendizaje Imparte 
     * Profesor
     *
     * @param uaip Objeto tipo Unidad Aprendizaje Imparte Profesor
     */
    public void update(UnidadaprendizajeImparteProfesor uaip) {
        //ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().update(uaip);
        
        
         List<UnidadaprendizajeImparteProfesor> result;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL modificarAsignacion ( \'" +  uaip.getUIPid() +  "\', \'" +
             
               uaip.getProfesorPROid().getPROid()+  "\', \'" + uaip.getUnidadAprendizajeUAPid().getUAPid() +
                     "\', \'" + uaip.getGrupoGPOid().getGPOid() +  "\', \'" + uaip.getUIPtipoSubgrupo()+
                     "\', \'" + uaip.getUIPsubgrupo()+  "\', \'" + uaip.getCicloEscolarCESid().getCESid()+"\')");
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n Result " + result);
        }catch(Exception e){
            System.err.println("Error en modificar asignacion" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo para eliminar una asignacion de Unidad Aprendizaje Imparte 
     * Profesor
     *
     * @param uaip Objeto tipo Unidad Aprendizaje Imparte Profesor
     */
    public void delete(UnidadaprendizajeImparteProfesor uaip) {
        //ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().delete(uaip);
        
        List<UnidadaprendizajeImparteProfesor> result;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL eliminarAsignacion ( \'" +  uaip.getUIPid() +  "\', \'" +
             
               uaip.getProfesorPROid().getPROid()+  "\', \'" + uaip.getUnidadAprendizajeUAPid().getUAPid() +
                     "\', \'" + uaip.getGrupoGPOid().getGPOid() +  "\', \'" + uaip.getUIPtipoSubgrupo()+
                     "\', \'" + uaip.getUIPsubgrupo()+  "\', \'" + uaip.getCicloEscolarCESid().getCESid()+"\')");
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n Result " + result);
        }catch(Exception e){
            System.err.println("Error en eliminar asignacion" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo para obtener una asignacion de Unidad Aprendizaje Imparte Profesor
     * por Id
     *
     * @param idUAIP Id de la Unidad Aprendizaje Imprarte Profesor
     * @return Objeto tipo Unidad Aprendizaje Imparte Profesor encontrado
     */
    public UnidadaprendizajeImparteProfesor findById(int idUAIP) {
       // return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().find(idUAIP);
        
        List<UnidadaprendizajeImparteProfesor> result = null;
        UnidadaprendizajeImparteProfesor resultado = null;
       try{
        result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL consultarUAIPid( \'" + idUAIP +"\')");
        resultado = result.get(0);
         }catch(Exception e){
            System.err.println("Error en consultar asignaciones por ID" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n GRUPO ID $$$$$$$$$$$$$$$$$$$$$$$$: " + result);
        return resultado;
    }

    /**
     * Lista de todas las asignaciones de Unidad Aprendizaje Imparte Profesor
     *
     * @return Lista tipo Unidad Aprendizaje Imparte Profesor
     */
    public List<UnidadaprendizajeImparteProfesor> findAll() {
        //return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().findAll();
        
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL consultarAsignaciones ()");
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n Result " + result);
        }catch(Exception e){
            System.err.println("Error en consultar asignaciones" + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Metodo para obtener asignaciones de Unidad Aprendizaje Imparte Profesor
     * por Id de Profesor, Unidad de Aprendizaje y Ciclo Escolar
     * @param proId Id del Profesor
     * @param uapId Id de la Unidad de Aprendizaje 
     * @param ce Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    //***
    public List<UnidadaprendizajeImparteProfesor> findByProfesorUAAndCE(int proId, int uapId, String ce){
       // return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().findByProfesorUAAndCE(proId, uapId, ce);
        
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL consultarProfesorUAAndCE ( \'" +  proId +  "\', \'" + 
              uapId +  "\', \'" + ce +"\')");
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n Result " + result);
        }catch(Exception e){
            System.err.println("Error en consultar asignaciones" + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @param ce Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    //***
    public List<UnidadaprendizajeImparteProfesor> consultaFFW(String de, String campo, String criterio, String order, String ce) {
//        List<UnidadaprendizajeImparteProfesor> resultado = null;
      // resultado = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeQuery("select distinct * from unidadaprendizaje_imparte_profesor as u  where u.Profesor_PROid=" +
//                criterio +" AND u.UnidadAprendizaje_UAPid=" +order +" AND u.CicloEscolar_CESid='" +ce +"'" +" order by u.uipid");
//        return resultado;
        
          List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL consultaFFW ( \'" +  criterio +  "\', \'" + 
              order +  "\', \'" + ce +"\')");
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n Result " + result);
        }catch(Exception e){
            System.err.println("Error en consultar asignaciones" + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Metodo para obtener Unidad Aprendizaje Imparte Profesor por Plan de 
     * Estudio seleccionado, Id de la Unidad de Aprendizaje y Ciclo Escolar
     * @param PESeleccionado Plan de Estudio seleccionado
     * @param idUA Id de la unidad de aprendizaje
     * @param CE Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    //***
    public List<UnidadaprendizajeImparteProfesor> consultaUIPParaUA(String PESeleccionado, String idUA, String CE) {
//return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().consultaUIPParaUA(PESeleccionado, idUA, CE);
        //****
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL consultaUIPParaUA ( \'" +  PESeleccionado+  "\', \'" +
              idUA +  "\', \'" + CE+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<UnidadaprendizajeImparteProfesor> findByProfesorUA(int profId, int uapId) {
    //  return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().findByProfesorUAAndCE(profId, uapId); //To change body of generated methods, choose Tools | Templates.
   
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL findByProfesorUA ( \'" +  
              profId +  "\', \'" + uapId+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    // PARA SEMAFOROS 
    /**
     * Con este metodo se obtienen tanto entregados como esperados de todas las
     * tablas en semaforos.
     *
     * @param tipoReporte El tipo de reporte que se selecciono
     * @param cicloEscolar El ciclo escolar seleccionado
     * @param numRACT El numero de RACT
     * @param planesEstudio Los planes de estudio de un programa educativo
     * @return
     */
    public int obtenerEnviadosOEsperados(String tipoReporte, String cicloEscolar, String numRACT, List<Planestudio> planesEstudio) {
        return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().obtenerEnviadosOEsperados(tipoReporte, cicloEscolar, numRACT, planesEstudio);
    }

  //***
//    public int enviadosPorRactGeneral(String idCicloEscolar){
//         int respuesta=0;
//         
//        respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EnviadosPorRactGeneral(idCicloEscolar);
//         
//         return respuesta;
//         
//     }
    //***
//     public int esperadosPorRACTGeneral(String idCicloEscolar){
//        int respuesta=0;
//        respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorRACTGeneral(idCicloEscolar);
//        return respuesta;
//     }
     //***
//     public int esperadosPorRACT(String idCicloEscolar){
//        int respuesta=0;
//        respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorRACT(idCicloEscolar);
//        return respuesta;
//     }
     //***
//     public int enviadosPorNumeroRACTGeneral(String numRACT,String idCicloEscolar){
//         int respuesta=0;
//         
//        respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EnviadosPorNumeroRACTGeneral(numRACT, idCicloEscolar);
//         return respuesta;
//         
//     }
      
     public int esperadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar){
//         int respuesta=0;
//        
//        respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorProgramaEducativoGeneral(planesEstudio,idCicloEscolar);
//        return respuesta; 
        
        String idPlanst = null;
       List<BigInteger> result = null;      
       int resultado = 0;
         try{    
  
             String idPlan =  "0";
            
        for (Planestudio plan : planesEstudio) {
            idPlanst = plan.getPESid().toString();
        }
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL EsperadosPorProgramaEducativoRACT ( \'" +  
             idPlan+  "\', \'" + idPlanst+  "\', \'"+ idCicloEscolar+ "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
            resultado=  resultado * 3;
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Esperados General" + resultado);
        return resultado;
         
         
     }
     //***
//     public int esperadosPorAreaConocimientoRACT( String idCicloEscolar, int idAreaCon){
//        int respuesta=0;
//        respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorAreaConocimientoRACT(idCicloEscolar, idAreaCon);
//        return respuesta;
//    }
     
     public int esperadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar){
//         int respuesta=0;
//        
     // respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorProgramaEducativoRACT(planesEstudio,idCicloEscolar);
//         System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@" + respuesta);
//        return respuesta; 
//        
        String idPlanst = null;
       List<BigInteger> result = null;      
       int resultado = 0;
         try{    
  
             String idPlan =  "0";
            
        for (Planestudio plan : planesEstudio) {
            idPlanst = plan.getPESid().toString();
        }
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL EsperadosPorProgramaEducativoRACT ( \'" +  
             idPlan+  "\', \'" + idPlanst+  "\', \'"+ idCicloEscolar+ "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ >>>" + resultado);
        return resultado;
         
         
     }
     
     /**
     * Metodo para encontrar el numero de reportes esperados por Unidad de 
     * Aprendizaje
     * @param planesEstudio
     * @param idCicloEscolar
     * @param grupo
     * @return 
     */
     //*** 
//     public int EsperadosPorUnidadAprendizajeRACT(String planesEstudio, String idCicloEscolar, String grupo) {
//         return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorUnidadAprendizajeRACT(planesEstudio, idCicloEscolar, grupo);
//     }

    public int enviadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar, String numRact){
//         int resultado = 0;
 //       resultado = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EnviadosPorProgramaEducativoRACT(planesEstudio, numRact, idCicloEscolar);
//                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ >>>" + resultado);
//        return  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EnviadosPorProgramaEducativoRACT(planesEstudio, numRact, idCicloEscolar);
        
     
        String idPlanst = null;
       List<BigInteger> result = null;      
       int resultado = 0;
         try{    
  
             String idPlan =  "0";
            
        for (Planestudio plan : planesEstudio) {
            idPlanst = plan.getPESid().toString();
        }
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL EnviadosPorProgramaEducativoRACT ( \'" +  
             idPlan+  "\', \'" + idPlanst+  "\', \'" + numRact +  "\', \'"+ idCicloEscolar+ "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ RACT" + resultado);
        return resultado;
//       
    }
    //***
//    public int EsperadosPorUnidadAprendizajeNumeroRACTGeneral(String planesEstudio, String numRACT, String idCicloEscolar, String grupo){
//        
//        return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EsperadosPorUnidadAprendizajeNumeroRACTGeneral(planesEstudio, numRACT, idCicloEscolar, grupo);
//    }
//    
    public int enviadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar ){
        
//        int respuesta=0;
//        
       // respuesta= ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().EnviadosPorProgramaEducativoGeneral(planesEstudio,idCicloEscolar);
//        return respuesta; 
         String idPlanst = null;
       List<BigInteger> result = null;      
       int resultado = 0;
         try{    
  
             String idPlan =  "0";
            
        for (Planestudio plan : planesEstudio) {
            idPlanst = plan.getPESid().toString();
        }
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL EnviadosPorProgramaEducativoGeneral ( \'" +  
             idPlan+  "\', \'" + idPlanst+  "\', \'" + idCicloEscolar+ "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ GENERAL" + resultado);
        return resultado;
    }
    
    /**
     * MEtodo para traer las unidades de aprendizaje que pertecen a un area
     * administrativa que coordina el profesor recibido
     * @param idProfCoordinador
     * @param idCiclo
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorcoordinadorAreaYCicloEscolar(int idProfCoordinador, int idCiclo) {
       // return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipPorcoordinadorAreaYCicloEscolar(idProfCoordinador, idCiclo);
        
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipPorcoordinadorAreaYCicloEscolar ( \'" +  
              idProfCoordinador +  "\', \'" + idCiclo+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     * Trae las uiap del area administrativa del profesor que es coordinados 
     * y aparte que pertenescan al profesor ( idProfesor)
     * @param idProfCoordinador
     * @param idCiclo
     * @param idProfesor
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipAreaProfesor(int idProfCoordinador, int idCiclo, int idProfesor) {
        
//        List<UnidadaprendizajeImparteProfesor>respuesta= null;
//        
//        respuesta=  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipAreaProfesor(idProfCoordinador, idCiclo, idProfesor);
//        return respuesta; 
        
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipAreaProfesor ( \'" +  
              idProfCoordinador +  "\', \'" + idProfesor +  "\', \'" + idCiclo+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
        
    }
    
    /**
     *  Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     * @param idCiclo
     * @param idPE
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorPEyCE(int idCiclo, int idPE) {
       // return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipPorPEyCE(idCiclo, idPE);
        
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipPorPEyCE ( \'" +  
              idCiclo +  "\', \'" + idPE+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     *  Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     *  que pertenezcan al profesor recibido
     * @param idCiclo
     * @param idPE
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorProfesorPEyCE(int idCiclo, int idPE, int idProfesor) {
        //return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipPorProfesorPEyCE(idCiclo, idPE, idProfesor);
       
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipPorProfesorPEyCE ( \'" +  
              idCiclo +  "\', \'" + idPE +  "\', \'" + idProfesor+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    
      /**
     * Metodo para traer las uaip de un area de conocimiento, es asi porque 
     * asi se divide el reporte por programa educativo
     * 
     * @param criterio entregados, no entregados.. etc
     * @param idProgramaEducativo
     * @param idCicloEscolar
     * @param idAreaConocimiento
     * @param noRact numero de ract
     * @param idPlan id de plan de estudios
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorAreaConocimiento(String criterio, int idProgramaEducativo, int idCicloEscolar, int idAreaConocimiento, String noRact, int idPlan) {
        //return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipsPorAreaConocimiento(criterio, idProgramaEducativo, idCicloEscolar, idAreaConocimiento, noRact, idPlan);
       
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipsPorAreaConocimiento ( \'" +  
              criterio +  "\', \'" + idProgramaEducativo +  "\', \'" + idCicloEscolar +  "\', \'" 
                    + idAreaConocimiento +  "\', \'" + noRact +  "\', \'" +idPlan+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
      /**
     * Metodo para saber cuantos registros de uaip tiene un area de conocimiento en un ciclo escolar;
     * @param idPlan
     * @param idCicloEscolar
     * @return 
     */
    public int countUaipPorAreaConocimientoYcicloEscolar(int idPlan, int idCicloEscolar){
        
        
       String idPlanst = null;
       List<BigInteger> result = null;      
       int resultado = 0;
         try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL countUaipPorAreaConocimientoYcicloEscolar ( \'" +  
             idPlan+  "\', \'" + idCicloEscolar+ "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Count " + resultado);
        return resultado;
    }
//    
//    /**
//     * Metodo para saber cuantos registros de uaip tiene una unidad de aprendizaje en un ciclo escolar
//     * @param idPlan plan de estudios
//     * @param idCicloEscolar cilo escolar
//     * @param idACO area de conocimiento
//     * @param idUAP unidad de aprendizaje
//     * @param idGrupo grupo
//     * @return 
//     */
//    public int countUaiPorUnidadAprendizaje(int idPlan, int idCicloEscolar,int idACO, int idUAP, int idGrupo){
//        return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countUaiPorUnidadAprendizaje(idPlan,idCicloEscolar,idACO,idUAP,idGrupo);
//    }
    
    
    /***
     * 
     * @param idPrograma 
     * @param idArea
     * @param idCiclo
     * @return conteo de esperados por Area de cocimiento segun el ciclo escolar
     */
    public int esperadosPorAreaConocimiento(int idPrograma,int idArea,int idCiclo){
  
       
        List<BigInteger> result = null;      
       int resultado = 0;
         try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL esperadosPorAreaConocimiento ( \'" +  
             idPrograma +  "\', \'" + idArea+  "\', \'" + idCiclo+  "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Count Area Conocimiento" + resultado);
        return resultado;
        
    }
    
    /**
     * Metodo para traer los RACT esperados por un area administrativa en cada ract individual
     * en caso de que se quiera saber por los 3 racts
     * debera multiplicarse el resultado x3
     * @param idCicloEscolar
     * @param idAreaCon
     * @param  idPrograma 
     * @return 
     */
    public int esperadosPorAreaAdministrativa(int idPrograma,int idArea,int idCiclo) {
        //return  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().esperadosPorAreaAdministrativa(idPrograma, idArea, idCiclo);
        
         List<BigInteger> result = null;      
       int resultado = 0;
         try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL esperadosPorAreaAdministrativa ( \'" +  
             idPrograma +  "\', \'" + idArea+  "\', \'" + idCiclo+  "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Count Area Administrativa" + resultado);
        return resultado;

    }
    /***
     * Metodo para traer esperados por unidad de aprendizaje segun el ciclo escolar
     * @param idUnidad
     * @param idPrograma
     * @param idCiclo
     * @return 
     */
    public int esperadosPorUnidadAprendizaje(int idUnidad, int idPrograma, int idCiclo){
        //return  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().esperadosPorUnidadAprendizaje(idUnidad, idPrograma, idCiclo);
        
        
         List<BigInteger> result = null;      
       int resultado = 0;
         try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL esperadosPorUnidadAprendizaje ( \'" +  
             idUnidad +  "\', \'" + idPrograma+  "\', \'" + idCiclo+  "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Count Unidad de Aprendizaje" + resultado);
        return resultado;
       
    }
    /***
     * Metodo para traer conteo de esperados por unidad profesor dependiendo
     * del ciclo escolar y si el usuario que consulta es Admin u otro usuario
     * @param idProfesor
     * @param idCiclo
     * @param IdPrograma
     * @param isAdmin
     * @return 
     */
    public int esperadosPorProfesor(int idProfesor, int idCiclo, int IdPrograma, boolean isAdmin){
       // return  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().esperadosPorProfesor(idProfesor, idCiclo, IdPrograma, isAdmin);
        
        List<BigInteger> result = null;      
       int resultado = 0;
         try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL esperadosPorProfesor ( \'" +  
             idProfesor+  "\', \'" + idCiclo+  "\', \'" + IdPrograma+  "\', \'" + isAdmin + "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Count Esperados PC " + resultado);
        return resultado;
    }
    
     /**
     * Esperados por profesor segun el area que coordina el usuario
     * @param idProfesor
     * @param idCiclo
     * @param idPrograma
     * @param idProfesorCoordinador
     * @return 
     */
    public int esperadosPorProfesorCoordinador(int idProfesor, int idCiclo, int idPrograma, int idProfesorCoordinador){
      //  return  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().esperadosPorProfesorCoordinador(idProfesor, idCiclo, idPrograma, idProfesorCoordinador);
//        
//        int respuesta= 0;
//        
//        respuesta=  ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().esperadosPorProfesorCoordinador(idProfesor, idCiclo, idPrograma, idProfesorCoordinador);
//        return respuesta; 
       
       List<BigInteger> result = null;      
       int resultado = 0;
         try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureTextCount ("CALL esperadosPorProfesorCoordinador ( \'" +  
             idProfesor+  "\', \'" + idCiclo+  "\', \'" + idPrograma+  "\', \'" + idProfesorCoordinador + "\')");
            resultado =  Integer.parseInt(result.get(0).toString());
    
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n @@@@@@@@@@@@@@@@@@@@@@@@@@@@ Count Esperados PC " + resultado);
        return resultado;
    }
    
    /**
     * Trae las unidadAprendizajeImparteProfesor segun el criterio de busqueda
     * @param criterio
     * @param idPrograma
     * @param idArea
     * @param idCiclo
     * @param noRact
     * @param idPlan
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorAreaAdministrativa(String criterio, int idPrograma,int idArea,int idCiclo, String noRact) {
        
        //return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipsPorAreaAdministrativa(criterio, idPrograma, idArea, idCiclo, noRact);
        
        
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipsPorAreaAdministrativa ( \'" +  
              criterio +  "\', \'" + idPrograma+  "\', \'" + idArea+  "\', \'" 
                    + idCiclo +  "\', \'" + noRact+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Traera la lista de uaips de aprendizaje que corresponden a una unidad
     * de aprendizaje segun el criterio recibido
     * @param criterio
     * @param idPrograma programa seleccionado
     * @param idUnidad unidad seleccionado
     * @param idCiclo ciclo escolar seleccionado 
     * @param noRact numero de ract 
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorUnidadAprendizaje(String criterio, int idPrograma,int idUnidad,int idCiclo, String noRact) {
       // return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipsPorUnidadAprendizaje(criterio, idPrograma, idUnidad, idCiclo, noRact);
        
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipsPorUnidadAprendizaje ( \'" +  
              criterio +  "\', \'" + idPrograma+  "\', \'" + idUnidad+  "\', \'" 
                    + idCiclo +  "\', \'" + noRact+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Traera la lista de uaips que corresponde a un profesor y al criterio 
     * recibido
     * @param criterio
     * @param idPrograma
     * @param idProfesor
     * @param idCiclo
     * @param noRact
     * @param isAdmin
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorProfesor(String criterio, int idPrograma,int idProfesor,int idCiclo, String noRact,boolean isAdmin) {
        //return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipsPorProfesor(criterio, idPrograma, idProfesor, idCiclo, noRact, isAdmin);
        
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipsPorProfesor ( \'" +  
              criterio +  "\', \'" + idPrograma+  "\', \'" + idProfesor+  "\', \'" 
                    + idCiclo +  "\', \'" + noRact+ "\', \'" + isAdmin+ "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Traera la lista de uaips que corresponden a un coordinador de area
     * segun el criterio recibido
     * @param criterio
     * @param idPrograma
     * @param idProfesor
     * @param idCiclo
     * @param noRact
     * @param idProfesorCoordinador
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorProfesorCoordinador(String criterio, int idPrograma,int idProfesor,int idCiclo, String noRact,int idProfesorCoordinador) {
       // return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipsPorProfesorCoordinador(criterio, idPrograma, idProfesor, idCiclo, noRact, idProfesorCoordinador);

        List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipsPorProfesorCoordinador ( \'" +  
              criterio +  "\', \'" + idPrograma+  "\', \'" + idProfesor+  "\', \'" 
                    + idCiclo +  "\', \'" + noRact+ "\', \'" + idProfesorCoordinador + "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    public List<UnidadaprendizajeImparteProfesor> uaipsPorPlanEstudio(String criterio, int idPlan,int idCiclo, String noRact) {
        //return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().uaipsPorPlanEstudio(criterio, idPlan, idCiclo, noRact);
        
         List<UnidadaprendizajeImparteProfesor> result = null;
        try{    
            result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeProcedureText ("CALL uaipsPorPlanEstudio ( \'" +  
              criterio +  "\', \'" + idPlan+  "\', \'" + idCiclo+  "\', \'" 
                    + noRact +  "\')");
            
           
        }catch(Exception e){
            System.err.println("Error en consulta" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
        
        
}
