package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegatePracticasLaboratorio;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Unidadaprendizaje;

/**
 *
 * @author kevin
 */
public class PracticasLaboratorioFacade {
    DelegatePracticasLaboratorio practicasLaboratorioDelegate;
    
    public PracticasLaboratorioFacade(){
        practicasLaboratorioDelegate=new DelegatePracticasLaboratorio();
    }

    
    /***
     * Agregar una nueva practica laboratorio
     * @param practicasLab Registro de Practica de Laboratorio
     */
    public void agregarPracticaLab(Practicalaboratorio practicasLab){
        practicasLaboratorioDelegate.savePracticasLaboratorio(practicasLab);
    }
    
        /**
     * Modificar una practica laboratorio
     * @param practicasLab Registro de practica laboratorio a modificar
     */
    public void modificarPracticaCampo(Practicalaboratorio practicasLab){
        practicasLaboratorioDelegate.updatePracticasLaboratorio(practicasLab);
    }
    
    /***
     * Eliminar un registro de practica laboratorio
     * @param practicasLab Registro de Practica de Laboratorio que se va a eliminar
     */
    public void eliminarPracticaLab(Practicalaboratorio practicasLab){
        practicasLaboratorioDelegate.deletePracticasLaboratorio(practicasLab);
    }

    /***
     * Consultar todas la practicas laboratorio
     * @return lista con todas las Practicas de Laboratorio  
     */
    public List<Practicalaboratorio> consultaPracticasLaboratorio(){
        return practicasLaboratorioDelegate.findAllPracticasLaboratorio();
    }
    
    /**
     * Busqueda de una practica laboratorio por id
     * @param id Identificador unico de practica laboratorio
     * @return  Registro de practica laboratorio resultado de la busqueda
     */
    public Practicalaboratorio busquedaPracticaLaboratorio(int id){
        return practicasLaboratorioDelegate.findPracticasLaboratorio(id);
    }
    
    /**
     * Consula de Practicalaboratorio
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @return Lista de Practicalaboratorio
     */
    public List<Practicalaboratorio> consultaPracticalaboratorioFromWhere(String de, String campo, String criterio, String order){
        return practicasLaboratorioDelegate.consultaPracticalaboratorioFromWhere(de, campo, criterio, order);
    }

//    /**
//     * Método para obtener las unidades dependiendo de la versión
//     * @param id
//     * @param vers
//     * @return 
//     */
//    public List<Practicalaboratorio> consultaPracticalaboratorioVers(int id, int vers){
//        return practicasLaboratorioDelegate.buscarPracticaslaboratorioVersion(id, vers);
//    }
//    
//    public List<Practicalaboratorio> consultaPracticalaboratorioPorCiclo(int id, int ciclo){
//        return practicasLaboratorioDelegate.buscarPracticaslaboratorioPorCiclo(id, ciclo);
//    }
}
