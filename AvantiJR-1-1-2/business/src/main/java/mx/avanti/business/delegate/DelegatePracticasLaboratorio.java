package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Kevin
 */
public class DelegatePracticasLaboratorio {

    /**
     * Regresa una lista de todas las practicas laboratorio
     * @return lista de Practicaslaboratorio
     */
    public List<Practicalaboratorio> findAllPracticasLaboratorio (){
        return ServiceLocator.getInstancePracticasLaboratorioDAO().findAll();
    }
    
    /**
     * Agregar nueva practica laboratorio
     * @param practicaLaboratorio objeto de practica laboratorio a agregar
     */
    public void savePracticasLaboratorio(Practicalaboratorio practicaLaboratorio){
        ServiceLocator.getInstancePracticasLaboratorioDAO().save(practicaLaboratorio);
    }
    
    /**
     * Modifica una practica laboratorio
     * @param practicaLaboratorio objeto de practica laboratorio a modificar
     */
    public void updatePracticasLaboratorio(Practicalaboratorio practicaLaboratorio){
        ServiceLocator.getInstancePracticasLaboratorioDAO().update(practicaLaboratorio);
    }
    
    /**
     * Eliminar una practica laboratorio
     * @param practicaLaboratorio objeto de practica laboratorio a eliminar
     */
    public void deletePracticasLaboratorio(Practicalaboratorio practicaLaboratorio){
        ServiceLocator.getInstancePracticasLaboratorioDAO().delete(practicaLaboratorio);
    }
    
    /**
     * Busca una practica laboratorio por su ID
     * @param id Identificar unico de practica laboratorio
     * @return objeto de Practicalaboratorio resultado de la busqueda
     */
    public Practicalaboratorio findPracticasLaboratorio(Integer id) {
        return ServiceLocator.getInstancePracticasLaboratorioDAO().find(id);
    }
    
    /**
     * Consulta de Practicalaboratorio por criterios
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @return  Lista de PracticaLaboratorio
     */
    public List<Practicalaboratorio> consultaPracticalaboratorioFromWhere(String de, String campo, String criterio, String order){
        return ServiceLocator.getInstancePracticasLaboratorioDAO().findFromWhereBExtra(de, campo, criterio,"Length("+order+"), "+order+" ASC","Practicalaboratorio");
    }

//    /**
//     * Método para obtener las unidades dependiendo de la versión
//     * @param id
//     * @param vers
//     * @return 
//     */
//    public List<Practicalaboratorio> buscarPracticaslaboratorioVersion(int id, int vers){
//        //return ServiceLocator.getInstanceUnidad().find
//        List<Practicalaboratorio> practicas = ServiceLocator.getInstancePracticasLaboratorioDAO().findByOneParameter(String.valueOf(id), "contenidoTematicoCTid");
//        List<Practicalaboratorio> auxiliar = new ArrayList<>();
//        for(Practicalaboratorio prac : practicas){
//            if(prac.getVersion()==vers)
//                auxiliar.add(prac);
//        }
//        return auxiliar;
//        //return ServiceLocator.getInstanceUnidad().findFromWhereB("contenidoTematicoCTid", "contenidoTematicoCTid", String.valueOf(id)+" AND version="+String.valueOf(vers), "version DESC");
//    } 
//    
//    public List<Practicalaboratorio> buscarPracticaslaboratorioPorCiclo(int id, int ciclo){
//        UnidadaprendizajeTieneContenidotematico sd = ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findFromWhereB("cTid", "cTid", String.valueOf(id)+" AND cicloEscolarCESid<="+String.valueOf(ciclo), "cicloEscolarCESid DESC").get(0);
//        List<Practicalaboratorio> practicas = ServiceLocator.getInstancePracticasLaboratorioDAO().findByOneParameter(String.valueOf(id), "contenidoTematicoCTid");
//        List<Practicalaboratorio> auxiliar = new ArrayList<>();
//        for(Practicalaboratorio prac : practicas){
//            if(Objects.equals(prac.getVersion(), sd.getVersion()))
//                auxiliar.add(prac);
//        }
//        return auxiliar;
//    }
}
