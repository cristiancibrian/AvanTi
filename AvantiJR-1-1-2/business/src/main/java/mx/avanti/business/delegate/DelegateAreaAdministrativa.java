/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;
import java.util.List;
import mx.avanti.siract.dao.BaseDAO;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Maby_
 */
public class DelegateAreaAdministrativa {
    
    public DelegateAreaAdministrativa(){}
    /***
     * Método para registrar un área administrativa atravez del DAO
     * @param areaAdministrativa Registro de Area administrativa que contiene 
     * todo excepto id y coordinador de area administrativa
     */
    public boolean save(Areaadministrativa areaAdministrativa){
           return ServiceLocator.getInstanceAreaadministrativa().executeProcedureAreaAdministrativa("CALL agregarAreaAdministrativa"
                   + "(\'" + areaAdministrativa.getAADnombre() + "\' , \'" + areaAdministrativa.getProgramaEducativoPEDid().getPEDid() + "\')");
    }
    /****
     * Método para consultar todos los registros de área administrativa
     * @return lista de Areas Administrativas
     */
   public List<Areaadministrativa> findAll(){
       return ServiceLocator.getInstanceAreaadministrativa().executeProcedureAreaAdministrativas("CALL consultarAreasAdministrativas()");
   }
   /***
    * Método para eliminar un registro de área administrativa
    * @param areaAdministrativa Registro a eliminar de Area Administrativa
    */
   public boolean delete(Areaadministrativa areaAdministrativa){
        return ServiceLocator.getInstanceAreaadministrativa().executeProcedureAreaAdministrativa("CALL eliminarAreaAdministrativa"
                   + "(\'" + areaAdministrativa.getAADid() + "\')");
   }
   /***
    * Método que regresa un solo registro de área administrativa con el id
    * de esa área
    * @param idArea ID de un Area Administrativa
    * @return el Area Administrativa que le ertenece el ID del parametro
    */
   public Areaadministrativa find(int idArea){
        return ServiceLocator.getInstanceAreaadministrativa().executeProcedureAreaAdministrativas("CALL buscarAreaAdministrativa"
                   + "(\'" + idArea + "\')").get(0);
    }
    /**
     * Este método actualiza el registro de área administrativa 
     * @param areaAdministrativa Registro de Área administrativa que se va a actualizar
     */
    public boolean update(Areaadministrativa areaAdministrativa){
        return ServiceLocator.getInstanceAreaadministrativa().executeProcedureAreaAdministrativa("CALL modificarAreaAdministrativa"
                   + "(\'" + areaAdministrativa.getAADnombre() + "\' , \'" + areaAdministrativa.getProgramaEducativoPEDid().getPEDid() + "\' , \'" + areaAdministrativa.getAADid() + "\')");
    }
}
