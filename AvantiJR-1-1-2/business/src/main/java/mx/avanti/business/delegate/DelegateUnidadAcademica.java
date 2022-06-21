/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.integration.ServiceLocator;
import static java.util.Objects.isNull;



    /**
     *
     * @author Axel Valenzuela. Avanti 2018.
     * Refactor Eduardo Cardona
     */

  public class DelegateUnidadAcademica {
    public void terminar(boolean completo){
        ServiceLocator.getInstanceUnidadacademica().endTransaction(completo);
    }

    /**
     * Metodo para obtener todas las unidades academicas registradas
     * @return Lista de todas las unidades academicas
     */
    public List<Unidadacademica> getUnidadesAcademicas() {
        List<Unidadacademica> result;
        result = ServiceLocator.getInstanceUnidadacademica().executeProcedure("CALL obtenerunidadesacademicas()");
        if(isNull(result)){
            System.out.println("<Error en la consulta general de unidad academica>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
    /**
     * Metodo para agregar una unidad academica
     * @param unidadAcademica de tipo unidad academica con Id 0 para crear nuevo
     * registro
     */
    public void saveUnidadAcademica(Unidadacademica unidadAcademica) {

        //ServiceLocator.getInstanceUnidadacademica().save(unidadAcademica);
        List<Unidadacademica> result;
        try{
            result = ServiceLocator.getInstanceUnidadacademica().executeProcedure("CALL agregarunidadacademica(\'" + unidadAcademica.getUACclave() + "\', \'" + unidadAcademica.getUACnombre() + "\', \'" + unidadAcademica.getCampusCAMid().getCAMid() + "\', \'" + unidadAcademica.getUACtipo() + "\')");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    /**
     * Metodo para modificar un registro de unidad academica
     * @param unidadAcademica de tipo unidad academica con todos sus atributos
     */
    public void updateUnidadAcademica(Unidadacademica unidadAcademica){
        //ServiceLocator.getInstanceUnidadacademica().update(unidadAcademica);
        List<Unidadacademica> result;
        try{
            result = ServiceLocator.getInstanceUnidadacademica().executeProcedure("CALL modificarunidadacademica(\'" + unidadAcademica.getUACid() + "\', \'" + unidadAcademica.getUACclave() + "\', \'" + unidadAcademica.getUACnombre() + "\', \'" + unidadAcademica.getCampusCAMid().getCAMid() + "\', \'" + unidadAcademica.getUACtipo() + "\')");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    /**
     * Metodo para eliminar una unidad academica
     * @param unidadAcademica de tipo unidad academica con todos sus atributos
     */
    public void deleteUnidadAcademica(Unidadacademica unidadAcademica) {

        //ServiceLocator.getInstanceUnidadacademica().delete(unidadAcademica);
        List<Unidadacademica> result;
        try{
            result = ServiceLocator.getInstanceUnidadacademica().executeProcedure("CALL eliminarunidadacademica(\'" + unidadAcademica.getUACid() + "\')");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    /**
     * Metodo para buscar una unidad Academica por Id
     * @param id
     * @return Objeto de tipo Unidadacademica
     */
    public Unidadacademica findUnidadAcademicaById(int id) {
        List<Unidadacademica> result;
        result = ServiceLocator.getInstanceUnidadacademica().executeProcedure("CALL buscarunidadacademica (\'"
                + id + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta por ID de unidad academica>");
            return null;
        }else if(result.isEmpty()){
            return new Unidadacademica();
        }else{
            return result.get(0);
        }
    }

//    public List<Unidadacademica> getProfUAC(int uacid) {
////            return ServiceFacadeLocator.getInstanceFacadeUnidadAcademica().consultaProfUAC(uacid);
////        List<Unidadacademica> listaUAC = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Unidadacademica.class);
////        listaUAC = ServiceLocator.getInstanceBaseDAO().findFromWhere("profesors", "proid", "'" + String.valueOf(uacid) + "'");
//        return ServiceLocator.getInstanceUnidadacademica().findFromWhere("profesors", "proid", "'" + String.valueOf(uacid) + "'");
//    }

//    public List<Unidadacademica> findUAByUsuario(int usuaroId) {
//        List<Unidadacademica> listaUAC = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Unidadacademica.class);
////        listaUAC = ServiceLocator.getInstanceBaseDAO().findFromWhere("profesors", "usuario.usuid", String.valueOf(usuaroId));
//        listaUAC = ServiceLocator.getInstanceUnidadacademica().findFromWhere("profesors", "usuario.usuid", String.valueOf(usuaroId));
//        return listaUAC;
//    }

//    public List<Unidadacademica> getUAasignado(Integer uacid) {
//        List<Unidadacademica> listaUAC;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Programaeducativo.class);
//        listaUAC = ServiceLocator.getInstanceBaseDAO().findFromWhereB("unidadacademica", "uacid", String.valueOf(uacid), "pedid");
//        return listaUAC;
//        //return ServiceLocator.getInstanceUnidadacademica().findFromWhereB("unidadacademica", "uacid", String.valueOf(uacid), "pedid");
//    }
}
