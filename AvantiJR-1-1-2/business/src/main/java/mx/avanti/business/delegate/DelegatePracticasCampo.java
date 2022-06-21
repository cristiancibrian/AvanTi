package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Practicascampo;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Kevin
 */
public class DelegatePracticasCampo {

    /**
     * Regresa una lista de todas las practicas campo
     * @return lista de Practicascampo
     */
    public List<Practicascampo> findAllPracticasCampo (){
        return ServiceLocator.getInstancePracticasCampoDAO().findAll();
    }
    
    /**
     * Agregar nueva practica campo
     * @param practicaCampo objeto de practica campo a agregar
     */
    public void savePracticasCampo(Practicascampo practicaCampo){
        ServiceLocator.getInstancePracticasCampoDAO().save(practicaCampo);
    }
    
    /**
     * Modifica una practica campo
     * @param practicaCampo objeto de practica campo a modificar
     */
    public void updatePracticasCampo(Practicascampo practicaCampo){
        ServiceLocator.getInstancePracticasCampoDAO().update(practicaCampo);
    }
    
    /**
     * Eliminar una practica campo
     * @param practicasCampo objeto de practica campo a eliminar
     */
    public void deletePracticasCampo(Practicascampo practicasCampo){
        ServiceLocator.getInstancePracticasCampoDAO().delete(practicasCampo);
    }
    
    /**
     * Busca una practica campo por su ID
     * @param id Identificar unico de practica campo
     * @return objeto de Practicacampo resultado de la busqueda
     */
    public Practicascampo findPracticascampo(Integer id) {
        return ServiceLocator.getInstancePracticasCampoDAO().find(id);
    }
    
    /**
     * Consulta Practicas campor por criterios
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @return Lista de Practicascampo
     */
    public List<Practicascampo> consultaPracticascampoFromWhere(String de, String campo, String criterio, String order){
        return ServiceLocator.getInstancePracticasCampoDAO().findFromWhereBExtra(de, campo, criterio,"Length("+order+"), "+order+" ASC","Practicascampo");
    }
}
