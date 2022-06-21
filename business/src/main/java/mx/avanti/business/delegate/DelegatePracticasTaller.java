package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Kevin
 */
public class DelegatePracticasTaller {

    /**
     * Regresa una lista de todas las practicas taller
     * @return lista de Practicastaller
     */
    public List<Practicataller> findAllPracticasTaller (){
        return ServiceLocator.getInstancePracticasTallerDAO().findAll();
    }
    
    /**
     * Agregar nueva practica taller
     * @param practicaTaller objeto de practica taller a agregar
     */
    public void savePracticasTaller(Practicataller practicaTaller){
        ServiceLocator.getInstancePracticasTallerDAO().save(practicaTaller);
    }
    
    /**
     * Modifica una practica taller
     * @param practicaTaller objeto de practica taller a modificar
     */
    public void updatePracticasTaller(Practicataller practicaTaller){
        ServiceLocator.getInstancePracticasTallerDAO().update(practicaTaller);
    }
    
    /**
     * Eliminar una practica taller
     * @param practicasTaller objeto de practica taller a eliminar
     */
    public void deletePracticasTaller(Practicataller practicasTaller){
        ServiceLocator.getInstancePracticasTallerDAO().delete(practicasTaller);
    }
    
    /**
     * Busca una practica taller por su ID
     * @param id Identificar unico de practica taller
     * @return objeto de Practicataller resultado de la busqueda
     */
    public Practicataller findPracticasTaller(Integer id) {
        return ServiceLocator.getInstancePracticasTallerDAO().find(id);
    }
    
    /**
     * Consulta Practicataller por criterios
     * @param de 
     * @param campo
     * @param criterio
     * @param order
     * @return Lista de Practicataller
     */
    public List<Practicataller> consultaPracticatallerFromWhere(String de, String campo, String criterio, String order){
        return ServiceLocator.getInstancePracticasTallerDAO().findFromWhereBExtra(de, campo, criterio,"Length("+order+"), "+order+" ASC","Practicataller");
    }
	
    /**
     * Método para obtener los talleres dependiendo de la versión
     * @param id
     * @param vers
     * @return 
     */
//    public List<Practicataller> consultaPracticatallerVers(int id, int vers){
//        List<Practicataller> talleres = ServiceLocator.getInstancePracticasTallerDAO().findByOneParameter(String.valueOf(id), "contenidoTematicoCTid");
//        List<Practicataller> auxiliar = new ArrayList<>();
//        for(Practicataller tall : talleres){
//            if(tall.getVersion()==vers)
//                auxiliar.add(tall);
//        }
//        return auxiliar;
//    }
//    
//    public List<Practicataller> consultaPracticatallerPorCiclo(int id, int ciclo){
//        UnidadaprendizajeTieneContenidotematico sd = ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findFromWhereB("cTid", "cTid", String.valueOf(id)+" AND cicloEscolarCESid<="+String.valueOf(ciclo), "cicloEscolarCESid DESC").get(0);
//        List<Practicataller> talleres = ServiceLocator.getInstancePracticasTallerDAO().findByOneParameter(String.valueOf(id), "contenidoTematicoCTid");
//        List<Practicataller> auxiliar = new ArrayList<>();
//        for(Practicataller tall : talleres){
//            if(Objects.equals(tall.getVersion(), sd.getVersion()))
//                auxiliar.add(tall);
//        }
//        return auxiliar;
//    }
}
