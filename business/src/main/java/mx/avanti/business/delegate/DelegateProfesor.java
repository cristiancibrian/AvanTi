/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.integration.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Usuario;

/**
 *
 * @author Adriana
 */
public class DelegateProfesor{
    
    public void terminar(boolean completo){
        ServiceLocator.getInstanceProfesor().endTransaction(completo);
    }
    /**
     * Metodo para agregar un Profesor
     *
     * @param profesor Objeto de tipo Profesor
     */
    public Profesor save(Profesor profesor) {
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("{CALL siract.guardar_Profesor (\'" 
                + profesor.getPROnumeroEmpleado() + "\', \'" + 
                profesor.getPROnombre() +  "\', \'" + 
                profesor.getPROapellidoPaterno() + "\', \'" + 
                profesor.getPROapellidoMaterno() +  "\', \'" + 
                profesor.getPROrfc() + "\', \'" + 
                profesor.getUsuarioUSUid().getUSUid() + "\')}");
        if(isNull(result)){
            System.out.println("<Error en la captura de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new Profesor();
        }else{
            return result.get(0);
        }
    }

    /**
     * Metodo para actualizar un Profesor
     *
     * @param profesor Objeto de tipo Profesor
     */
    public Profesor update(Profesor profesor) {
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL actualizar_Profesor (\'" 
                + profesor.getPROid() + "\', \'" + profesor.getPROnumeroEmpleado() + "\', \'" + 
                profesor.getPROnombre() +  "\', \'" + profesor.getPROapellidoPaterno() + "\', \'" + 
                profesor.getPROapellidoMaterno() +  "\', \'" + profesor.getPROrfc() + "\', \'" + 
                profesor.getUsuarioUSUid().getUSUid() + "\')");
        if(isNull(result)){
            System.out.println("<Error en la modificacion de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new Profesor();
        }else{
            return result.get(0);
        }
    }

    /**
     * Metodo para obtener a todos los profesores
     *
     * @return Regresa una lista de los profesores encontrados
     */
    public List<Profesor> findAll() {
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL verTodos_Profesor()");
        if(isNull(result)){
            System.out.println("<Error en la consulta de profesores>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }

    /**
     * Metodo para eliminar un profesor
     *
     * @param profesor Objeto de tipo profesor
     */
    public Profesor delete(Profesor profesor) {
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("{CALL siract.eliminar_Profesor (\'" 
                + profesor.getPROid() + "\')}");
        if(isNull(result)){
            System.out.println("<Error en la eliminación de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new Profesor();
        }else{
            return result.get(0);
        }
    }

    /**
     * Metodo para encontrar un profesor por id
     *
     * @param idProfesor El id del profesor a buscar
     * @return Regresa el profesor que dicha ID
     */
    public Profesor findById(int idProfesor) {
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL buscarID_Profesor (\'" 
                + idProfesor + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta por ID de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new Profesor();
        }else{
            return result.get(0);
        }
    }
    
    /**
     * Metodo para buscar a un Profesor por Id del Usuario
     * @param userId Id de usuario
     * @return Profesor encontrado
     * @author Mario
     */
    public Profesor findByUserId(int userId){
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL buscarUsuario_Profesor (\'" 
                + userId + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta por usuario de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new Profesor();
        }else{
            return result.get(0);
        }
    }
  
    /**
     * Obtiene los profesores correspondientes a un
     * coordinador de area administrativa y un ciclo escolar
     * determinado
     * @param coordinadorProId Id del coordinador
     * @param ce Ciclo escolar
     * @author Mario
     * @return Lista de profesores encontrados
     */
    public List<Profesor> findByCoordinadorYCE(int coordinadorProId, String ce){
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL buscarCoordinadorYCE_Profesor (\'" 
                + coordinadorProId + "\', \'" + ce + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta de profesor coordinador>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
    
    /**
     * Obtiene los profesores que pertenecen a un programa educativo en un
     * determinado ciclo escolar
     * @param pe Id del programa educativo
     * @param ce Ciclo escolar
     * @return Lista de Profesores encontrados
     * @author Mario
     */
    public List<Profesor> findByPEYCE(int pe, String ce) {
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL buscarPEYCE_Profesor (\'" 
                + pe + "\', \'" + ce + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta por PEYCE de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
    
    /**
     * Obtiene los Profesores que pertenecen a un Programa Educativo en un
     * determinado Ciclo Escolar
     * @param rpeId Id del Responsable del Programa Educativo
     * @param ce Ciclo Escolar
     * @return Lista de Profesores encontrados
     * @author Mario
     */
    public List<Profesor> findByRPEYCE(int rpeId, String ce){
        List<Profesor> result;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL buscarRPEYCE_Profesor (\'" 
                + rpeId + "\', \'" + ce + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta por RPEYCE de profesor>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
    
    /**
     * Obtiene los Profesores que pertenecen a un Programa Educativo 
     * @param pe Id del programa educativo
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> findByPE(int pe) {
        List<Profesor> result = null;
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL buscarPE_Profesor (\'" 
                + pe + "\')");
        if(isNull(result)){
            System.out.println("Error en la consulta de profesor");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }

    public void removerRpe(Profesor viejoRPE) {
        List<Profesor> result;
        ServiceLocator.getInstanceProfesor().executeProcedure("CALL actualizarProfesorResponsablePE (\'"
                + viejoRPE.getPROid() + "\', \'" + viejoRPE.getProgramaeducativoList().get(0).getPEDid() + "\')");
//        if(isNull(result)) {
//            System.out.println("<Error en la consulta por RPEYCE de profesor>");
//        }
    }
    public Profesor findbyPEandCE(int PE){
        Profesor profe = new Profesor();
        try{
           return ServiceLocator
                .getInstanceProfesor()
                .executeProcedurefindProfesor(
                 "CALL consultarRPEporCicloyProgramaEducativo("
                    + "'" + PE+ "')");

       }catch(Exception ex){
           System.err.println(ex);
           return new Profesor();
       }
    }
    public Profesor guardarProfesorUAC(Profesor profesor, Unidadacademica unidad) {
        List<Profesor> result;           
        result = ServiceLocator.getInstanceProfesor().executeProcedure("CALL siract.guardar_ProfesorPerteneceUAC (\'" 
                + profesor.getPROid() + "\', \'" + unidad.getUACid() + "\')");   
        if(isNull(result)){
            System.out.println("<Error en la captura de Profesor pertenece UAC>" );
            return null;
        }else if(result.isEmpty()){
            return new Profesor();
        }else{
            return result.get(0);
        }
    }
    
     public Usuario findByUsuarioId(int idUsu){
         return ServiceLocator.getInstanceProfesor().findByUsuarioId(idUsu);
     }

}
