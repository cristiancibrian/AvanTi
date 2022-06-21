/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;
import java.util.List;
import mx.avanti.business.delegate.DelegateAreaAdministrativa;
import mx.avanti.siract.entity.Areaadministrativa;
/**
 *
 * @author Maby_
 */
public class AreaAdministrativaFacade {
    DelegateAreaAdministrativa areaAdministrativaDelegate;

    public AreaAdministrativaFacade() {
        areaAdministrativaDelegate=new DelegateAreaAdministrativa();
    }
    /***
     * Método para registrar un área administrativa atravez del DAO
     * @param area Registro de Area administrativa que contiene 
     * todo excepto id y coordinador de area administrativa
     */
    public void agregarAreaAdministrativa(Areaadministrativa area){
        areaAdministrativaDelegate.save(area);
    }
    /***
     * Método para eliminar un registro de área administrativa
     * @param area Registro a eliminar de Area Administrativa
     */
    public void eliminarAreaAdministrativa(Areaadministrativa area){
        areaAdministrativaDelegate.delete(area);
    }
    /***
     * Método para consultar todos los registros de área administrativa
     * @return lista de Areas Administrativas
     */
    public List<Areaadministrativa> consultaGeneralAreaAdministrativa(){
        return areaAdministrativaDelegate.findAll();
    }
    /***
     * Método que regresa un solo registro de área administrativa con el id
     * de esa área
     * @param id ID de un Area Administrativa
     * @return el Area Administrativa que le ertenece el ID del parametro
     */
    public Areaadministrativa consultaAreaAdministrativaPorId(int id){
        return areaAdministrativaDelegate.find(id);
    }
    /**
     * Este método actualiza el registro de área administrativa 
     * @param areaAdministrativa Registro de Área administrativa que se va a actualizar
     */
    public void modificarAreaAdministrativa(Areaadministrativa areaAdministrativa){
        areaAdministrativaDelegate.update(areaAdministrativa);
    }
}
