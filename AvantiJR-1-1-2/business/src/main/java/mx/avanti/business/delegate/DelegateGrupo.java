/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.integration.ServiceLocator;
import mx.avanti.siract.persistence.HibernateUtil;

/**
 *
 * @author ODOSMO
 */
public class DelegateGrupo {

    public DelegateGrupo() {
    }

    /**
     * Metodo para registrar un Grupo
     *
     * @param grupo Objeto de tipo Grupo
     */
//    public void saveGrupo(Grupo grupo) {
//        ServiceLocator.getInstanceGrupoDAO().save(grupo);
//    }
    public void saveGrupo(Grupo grupo) {
        List<Grupo> result;
        try{         
        result = ServiceLocator.getInstanceGrupoDAO().executeProcedureText ("CALL agregarGrupo ( \'" +
             grupo.getGPOnumero() +
                "\', \'" + grupo.getPlanEstudioPESid().getPESid() +"\')");
        }catch(Exception e){
            System.err.println("Error en agregar grupo" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo para actualizar un Grupo
     *
     * @param grupo Objeto de tipo Grupo
     */
    public void updateGrupo(Grupo grupo) {
       List<Grupo> result;
       try{
        result = ServiceLocator.getInstanceGrupoDAO().executeProcedureText ("CALL modificarGrupo ( \'" + grupo.getGPOid() +
              "\', \'" + grupo.getGPOnumero() +
                "\', \'" + grupo.getPlanEstudioPESid().getPESid() +"\')");
         }catch(Exception e){
            System.err.println("Error en agregar grupo" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo para eliminar Grupo
     *
     * @param grupo Objeto de tipo Grupo
     */
    public void deleteGrupo(Grupo grupo) {
        List<Grupo> result;
        try{
        result = ServiceLocator.getInstanceGrupoDAO().executeProcedureText ("CALL eliminarGrupo ( \'" + grupo.getGPOid() +
              "\', \'" + grupo.getGPOnumero() +
                "\', \'" + grupo.getPlanEstudioPESid().getPESid() +"\')");
         }catch(Exception e){
            System.err.println("Error en eliminar grupo" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo para consultar Grupos
     *
     * @return Lista de Grupos
     */
    public List<Grupo> getGrupos() {
        List<Grupo> result = null;
        try{
        result = ServiceLocator.getInstanceGrupoDAO().executeProcedureText ("CALL consultarGrupos ()");
         }catch(Exception e){
            System.err.println("Error en consultar grupo" + e.getMessage());
            e.printStackTrace();
        }
        
//        List<Grupo> resultado = null;
//        resultado = ServiceLocator.getInstanceGrupoDAO().findAll();
//        return resultado;
    return result;
    }

    /**
     * Metodo para consultar un Grupo por Id
     *
     * @param id Id del Grupo
     * @return Grupo encontrado
     */
    public Grupo getGrupoById(int id) {
        
        List<Grupo> result = null;
        Grupo resultado = null;
       try{
        result = ServiceLocator.getInstanceGrupoDAO().executeProcedureText ("CALL consultarGPOid( \'" + id +"\')");
        resultado = result.get(0);
         }catch(Exception e){
            System.err.println("Error en consultar grupopor ID" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n\n\n\n GRUPO ID $$$$$$$$$$$$$$$$$$$$$$$$: " + result);
        return resultado;
    }

    /**
     * Metodo para obtener un Grupo por Unidad de Aprendizaje
     *
     * @param UAClave Clave de la Unidad de Aprendizaje
     * @param PEId Id del Programa Educativo
     * @param CE Ciclo escolar
     * @return Lista de grupo consultados por unidad academica
     */
    public List<Grupo> getGruposByUA(int UAClave, String PEId, String CE) {
        return ServiceLocator.getInstanceGrupoDAO().consultaGruposporUA(UAClave, PEId, CE);
    }

    /**
     * @deprecated No utilizado en otras capas
     * @param idGrupo Id del Grupo
     * @return Lista de Grupos de un Plan de Estudio
     */
//    public List<Grupo> getGpoMismoPlan(int idGrupo) {
//        List<Grupo> listaGpo = null;
//        listaGpo = ServiceLocator.getInstanceGrupoDAO().findFromWhere("planestudio", "pesid", String.valueOf(idGrupo));
//        return listaGpo;
//    }

    /**
     * Metodo para obtener Grupos asignados
     *
     * @param idGrupo Id del Grupo
     * @return Lista de Grupos encontrados
     */
//    public List<Grupo> getGpoAsignado(int idGrupo) {
//        List<Grupo> listaGpo = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        listaGpo = ServiceLocator.getInstanceBaseDAO().findFromWhereB("grupo", "gpoid", String.valueOf(idGrupo), "uipid");
//        return listaGpo;
//    }
}
