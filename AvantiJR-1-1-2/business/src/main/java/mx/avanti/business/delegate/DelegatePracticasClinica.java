package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Practicasclinica;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author kevin
 */
public class DelegatePracticasClinica {

    /**
     * Regresa una lista de todas las practicas clinica
     * @return lista de Practicasclinica
     */
    public List<Practicasclinica> findAllPracticasClinica (){
        return ServiceLocator.getInstancePracticasClinicaDAO().findAll();
    }
    
    /**
     * Agregar nueva practica clinica
     * @param practicaClinica objeto de practica clinica a agregar
     */
    public void savePracticasClinica(Practicasclinica practicaClinica){
        ServiceLocator.getInstancePracticasClinicaDAO().save(practicaClinica);
    }
    
    /**
     * Modifica una practica clinica
     * @param practicaClinica objeto de practica clinica a modificar
     */
    public void updatePracticasClinica(Practicasclinica practicaClinica){
        ServiceLocator.getInstancePracticasClinicaDAO().update(practicaClinica);
    }
    
    /**
     * Eliminar una practica clinica
     * @param practicaClinica objeto de practica clinica a eliminar
     */
    public void deletePracticasClinica(Practicasclinica practicaClinica){
        ServiceLocator.getInstancePracticasClinicaDAO().delete(practicaClinica);
    }
    
    /**
     * Busca una practica clinica por su ID
     * @param id Identificar unico de practica clinica
     * @return objeto de Practicaclinica resultado de la busqueda
     */
    public Practicasclinica findPracticasClinica(Integer id) {
        return ServiceLocator.getInstancePracticasClinicaDAO().find(id);
    }
         
}
