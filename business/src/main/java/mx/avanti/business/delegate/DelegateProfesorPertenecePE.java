/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Adriana
 */
public class DelegateProfesorPertenecePE {
    public void terminar(boolean completo){
        ServiceLocator.getInstanceProfesorPertenecePE().endTransaction(completo);
    }
    /**
     * Metodo para agregar un profesor pertenece programa educativo
     *
     * @param profesorPE recibe objeto profesor pertenece prog educativo
     */ 
    public ProfesorPerteneceProgramaeducativo save(ProfesorPerteneceProgramaeducativo profesorPE) {
        List<ProfesorPerteneceProgramaeducativo> result;           
        result = ServiceLocator.getInstanceProfesorPertenecePE().executeProcedure("CALL siract.guardar_ProfesorPertenecePE (\'" 
                + profesorPE.getProfesor().getPROid() + "\', \'" + 
                profesorPE.getProfesorPerteneceProgramaeducativoPK().getProgramaEducativoPEDid() +  "\', \'" + 
                profesorPE.getCicloescolar().getCESid() + "\')");   
        if(isNull(result)){
            System.out.println("<Error en la captura de Profesor pertenece PE>" );
            return null;
        }else if(result.isEmpty()){
            return new ProfesorPerteneceProgramaeducativo();
        }else{
            return result.get(0);
        }
    }

    /**
     * Metodo para actualizar un profesor pertenece PE
     * 
     * @param profesorPE objeto profesor pertenece prg educativo
     * @deprecated 
     */
    public void update(ProfesorPerteneceProgramaeducativo profesorPE) {
        ServiceLocator.getInstanceProfesorPertenecePE().update(profesorPE);
    }

    /**
     * Metodo para eliminar un profesor pertenece programa educativo
     *
     * @param profesorPE objeto profesor pertenece programa educativo
     */
    public ProfesorPerteneceProgramaeducativo delete(ProfesorPerteneceProgramaeducativo profesorPE) {
        List<ProfesorPerteneceProgramaeducativo> result;
        result = ServiceLocator.getInstanceProfesorPertenecePE().executeProcedure("CALL eliminar_ProfesorPertenecePE (\'" 
                + profesorPE.getProfesor().getPROid() + "\', \'" + 
                profesorPE.getProgramaeducativo().getPEDid() +  "\', \'" + 
                profesorPE.getCicloescolar().getCESid() + "\')");
        if(isNull(result)){
            System.out.println("<Error en la eliminación de Profesor pertenece PE>");
            return null;
        }else if(result.isEmpty()){
            return new ProfesorPerteneceProgramaeducativo();
        }else{
            return result.get(0);
        }
    }

    /**
     * Metodo para obtener un profesor pertenecec programa educativo por id
     *
     * @param id id del profesor pertenece programa educativo
     * @return profesor pertenece programa educativo con el id deseado
     */
    public List<ProfesorPerteneceProgramaeducativo> findById(int id) {
        List<ProfesorPerteneceProgramaeducativo> profesores = new ArrayList<ProfesorPerteneceProgramaeducativo>();
        profesores = ServiceLocator.getInstanceProfesorPertenecePE().executeProcedure("CALL buscarIdProfesor_ProfesorPertenecePE (\'" 
                + id + "\')");
        if(isNull(profesores)){
            System.out.println("<Error en la consulta por ID de Profesor pertenece PE>");
            return null;
        }else if(profesores.isEmpty()){
            return new ArrayList();
        }else{
            return profesores;
        }  
    }

    /**
     * Metodo para obtener todos los profesores pertenecen a un programa
     * educativo
     *
     * @return lista tipo profesor pertenece programa educativo
     */
    public List<ProfesorPerteneceProgramaeducativo> findAll() {
        List<ProfesorPerteneceProgramaeducativo> result = null;
        result = ServiceLocator.getInstanceProfesorPertenecePE().executeProcedure("CALL verTodos_ProfesorPertenecePE");
        if(isNull(result)){
            System.out.println("<Error en la consulta general de Profesor pertenece PE>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
}
