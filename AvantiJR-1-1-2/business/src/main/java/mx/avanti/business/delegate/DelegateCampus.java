/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.integration.ServiceLocator;

import java.util.List;

/**
 *
 * @author Paul Miranda
 */
public class DelegateCampus {
    public void terminar(boolean completo){
        ServiceLocator.getInstanceCampus().endTransaction(completo);
    }
    /***
     * Guarda un objeto de tipo campus
     * @param campus de tipo Campus
     */
    public void nuevoCampus(Campus campus){
        ServiceLocator.getInstanceCampus().executeProcedureTest("CALL guardarCampus(\'" + campus.getCAMnombre() + "\')");
    }
    /***
     * Antes se llamaba consultaCampus
     * Encuentra todos los campus que estan agregados en la base de datos
     * y los guarda en una lista.
     * @return Lista con objetos tipo campus
     */
    public List<Campus> findAllCampus(){
        List<Campus> resultado;
        resultado = ServiceLocator.getInstanceCampus().executeProcedureTest("CALL verCampus()");
        return resultado;
    }
    /***
     * Elimina un objeto de tipo campus
     * @param campus tipo Campus
     */
    public void deleteCampus(Campus campus){
        ServiceLocator.getInstanceCampus().executeProcedureTest("CALL eliminarCampus(\'" + campus.getCAMid() + "\')");
    }
    /***
     * Busca un campus por medio de su id y regresa el objeto encontrado
     * @param id tipo int
     * @return objeto tipo Campus
     */
    public Campus findCampusID(int id){
        Campus campus = new Campus();
        List<Campus> resultado;
        resultado = ServiceLocator.getInstanceCampus().executeProcedureTest("CALL buscarID_Campus (\'" + id + "\')");
        campus.setCAMid(resultado.get(0).getCAMid());
        campus.setCAMnombre(resultado.get(0).getCAMnombre());
        return campus;
    }
    /**
     * Método para actualizar la informacion de un campus
     * @param campus  tipo campus
     */
    public void updateCampus(Campus campus){
         ServiceLocator.getInstanceCampus().executeProcedureTest("CALL actualizarCampus(\'" + campus.getCAMid() + "\',\'" + campus.getCAMnombre() + "\')");
    }
}
