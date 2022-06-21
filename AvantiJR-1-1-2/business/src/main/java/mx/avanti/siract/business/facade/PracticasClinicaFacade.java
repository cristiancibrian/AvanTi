package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegatePracticasClinica;
import mx.avanti.siract.entity.Practicasclinica;

/**
 *
 * @author kevin
 */
public class PracticasClinicaFacade {

    private DelegatePracticasClinica practicasClinicaDelegate;
    
    public PracticasClinicaFacade(){
        practicasClinicaDelegate=new DelegatePracticasClinica();
    }
    
    /**
     * Busqueda de todas los las practicas clinica
     * @return Lista de todas las practicas clinica
     */
    public List<Practicasclinica> consultaPracticasClinica(){
        return practicasClinicaDelegate.findAllPracticasClinica();
    }
    
    /**
     * Busqueda de una practica clinica por id
     * @param id Identificador unico de practica clinica a buscar
     * @return Practica clinica resultado de la busqueda
     */
    public Practicasclinica busquedaPracticaClinica(Integer id){
        return practicasClinicaDelegate.findPracticasClinica(id);
    }
    
    /**
     * Agregar una nueva practica clinica
     * @param practicasClinica Registro de Practica clinica
     */
    public void agregarPracticaClinica(Practicasclinica practicasClinica){
        practicasClinicaDelegate.savePracticasClinica(practicasClinica);
    }

    /**
     * Modificar una practica clinica
     * @param practicasClinica REgistro de practica clinica a modificar
     */
    public void modificarPracticaCampo(Practicasclinica practicasClinica){
        practicasClinicaDelegate.updatePracticasClinica(practicasClinica);
    }
    
    /**
     * Eliminar una practica clinica
     * @param practicasClinica Registro de Practica de clinica que se va a 
     * eliminar
     */
    public void eliminarPracticaCampo(Practicasclinica practicasClinica){
        practicasClinicaDelegate.deletePracticasClinica(practicasClinica);
    }
}
