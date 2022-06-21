package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegatePracticasTaller;
import mx.avanti.siract.entity.Practicataller;

/**
 *
 * @author kevin
 */
public class PracticasTallerFacade {
    DelegatePracticasTaller  delegatePracticasTaller = new DelegatePracticasTaller();

    /**
     * Guardar practica taller
     * @param practicaTall Objeto de practica taller a agregar
     */
    public void agregarPracticasTall(Practicataller practicaTall) {
        delegatePracticasTaller.savePracticasTaller(practicaTall);
    }
    
    /**
     * Eliminar una practica taller
     * @param practicasTall que se va a eliminar
     */
    public void eliminarPracticasTall(Practicataller practicasTall) {
        delegatePracticasTaller.deletePracticasTaller(practicasTall);
    }

    /**
     * Modifica una practica taller
     * @param practicasTall Registro de practica taller a modificar
     */
    public void modificarPracticasTaller(Practicataller practicasTall) {
        delegatePracticasTaller.updatePracticasTaller(practicasTall);
    }

    /**
     * Buscar todos los registros de practicas taller
     * @return  Lista de todas las practicas de taller
     */
    public List<Practicataller> consultaPracticasTaller() {
        return delegatePracticasTaller.findAllPracticasTaller();
    }
    
    /**
     * Busqueda de una practica taller por id
     * @param id Identificador unico de practica taller
     * @return  Registro de practica taller resultado de la busqueda
     */
    public Practicataller busquedaPracticaTaller(int id){
        return delegatePracticasTaller.findPracticasTaller(id);
    }
    
    /**
     * Consulta PracticaTaller por criterios
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @return Lista de Practicataller
     */
    public List<Practicataller> consultaPracticatallerFromWhere(String de, String campo, String criterio, String order){
        return delegatePracticasTaller.consultaPracticatallerFromWhere(de, campo, criterio, order);
    }

//    /**
//     * Método para obtener los talleres dependiendo de la versión
//     * @param id
//     * @param vers
//     * @return 
//     */
//    public List<Practicataller> consultaPracticatallerVers(int id, int vers){
//        return delegatePracticasTaller.consultaPracticatallerVers(id,vers);
//    }
//    
   
}
