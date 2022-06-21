package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegatePracticasCampo;
import mx.avanti.siract.entity.Practicascampo;

/**
 *
 * @author kevin
 */
public class PracticasCampoFacade {
    private DelegatePracticasCampo practicasCampoDelegate;
    
    public PracticasCampoFacade(){
        practicasCampoDelegate=new DelegatePracticasCampo();
    }
    
    /**
     * 
     * @return Lista de todas las practicas campo
     */
    public List<Practicascampo> consultaPracticasCampo(){
        return practicasCampoDelegate.findAllPracticasCampo();
    }
    
    /**
     * Busqueda de una practica campo por id
     * @param id Identificador unico de practica campo a buscar
     * @return Practica campo resultado de la busqueda
     */
    public Practicascampo busquedaPracticaCampo(Integer id){
        return practicasCampoDelegate.findPracticascampo(id);
    }
    
    /**
     * Agregar una nueva practica campo
     * @param practicasCampo Registro de Practica campo
     */
    public void agregarPracticaCampo(Practicascampo practicasCampo){
        practicasCampoDelegate.savePracticasCampo(practicasCampo);
    }

    /**
     * Modificar una practica campo
     * @param practicasCampo REgistro de practica campo a modificar
     */
    public void modificarPracticaCampo(Practicascampo practicasCampo){
        practicasCampoDelegate.updatePracticasCampo(practicasCampo);
    }
    
    /**
     * Eliminar una practica campo
     * @param practicasCampo Registro de Practica de campo que se va a 
     * eliminar
     */
    public void eliminarPracticaCampo(Practicascampo practicasCampo){
        practicasCampoDelegate.deletePracticasCampo(practicasCampo);
    }
    
    /**
     * Consulta de Practicascampo por criterios
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @return Lista de Practicascampo
     */
    public List<Practicascampo> consultaPracticascampoFromWhere(String de, String campo, String criterio, String order){
        return practicasCampoDelegate.consultaPracticascampoFromWhere(de, campo, criterio, order);
    }

}
