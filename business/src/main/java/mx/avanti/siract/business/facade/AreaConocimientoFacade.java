/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegateAreaConocimiento;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Planestudio;

/**
 *
 * @author Oscar D. Sanchez
 */
public class AreaConocimientoFacade {

    //Inicializacion de las variables
    private final DelegateAreaConocimiento delegateAreaConocimiento;
    private List<Areaconocimiento> listaAreaConocimiento;

    /**
     * Constructor para inicializar la variable delegateAreaConocimiento
     */
    public AreaConocimientoFacade() {
        delegateAreaConocimiento = new DelegateAreaConocimiento();
    }

    /**
     * Metodo para obtener la lista de areas de conocimiento
     * @return Regresa una lista de areas de conocimiento
     */
    public List<Areaconocimiento> getListaAreaConocimiento() {
        return delegateAreaConocimiento.getAreasConocimiento();
        
    }

    /**
     * Metodo para agregar un area de conocimiento
     *
     * @param areaConocimiento Objeto de tipo Areaconocimiento
     */
    public void agregarAreaConocimiento(Areaconocimiento areaConocimiento) {
        delegateAreaConocimiento.saveAreaConocimiento(areaConocimiento);
    }
    
    public int esperadosPorAreaConocimientoRACT(List<Planestudio> planesEstudio, String idCicloEscolar, int idAreaCon){
        int respuesta=0;
        
        respuesta= delegateAreaConocimiento.esperadosPorAreaConocimientoRACT(planesEstudio, idCicloEscolar, idAreaCon);
        return respuesta;
    }
    
    /**
     * Metodo para modificar un area de conocimiento
     * @param areaConocimiento Objeto de tipo area de conocimiento
     */
    public void modificarAreaConocimiento(Areaconocimiento areaConocimiento){
        delegateAreaConocimiento.updateAreaConocimiento(areaConocimiento);
    }
    /**
     * Metodo para buscar un area de conocimiento por id
     *
     * @param id ID del area de conocimiento a buscar
     * @return Regresa el area de conocimiento encontrado
     */
    public Areaconocimiento findAreaConocimientoById(int id) {
        return delegateAreaConocimiento.getAreaConocimiento(id);
    }
    
    /**
     * Metodo para buscar un area de conocimiento por una unidad de aprendizaje
     * @param valor
     * @return 
     */
    public Areaconocimiento findByUnidadAprendizaje(int valor){
        return delegateAreaConocimiento.findByUnidadAprendizaje(valor);
    }
    
//    public int getAreaConocimientoUnidadAprendizaje(int idUnidad){
//        return delegateAreaConocimiento.getAreaConocimientoUnidadAprendizaje(idUnidad);
//    }

    /**
     * Metodo para eliminar un area de conocimiento por objeto
     * @param areaConocimiento Objeto de tipo Areaconocimiento
     */
    public void eliminarAreaConocimiento(Areaconocimiento areaconocimiento) {
        delegateAreaConocimiento.deleteAreaConocimiento(areaconocimiento);
    }

    /**
     * 
     * @param pedclave clave de plan educativo
     * @param pesvigenciaPlan vigencia del plan
     * @return Lista de areas de conocimiento corresponiente  al plan educativo
     */
    public List<Areaconocimiento> getAreasByPlanClave(int pedclave, String pesvigenciaPlan) {       
        return delegateAreaConocimiento.getAreasByPlanClave(pedclave, pesvigenciaPlan);
    }
    /**
     * 
     * @param listaProgramas lista de programas educativos
     * @return Lista de area de conocmiento por lista de programas educativos
     */
    public List<Areaconocimiento> getAreasByProgramaEducativoList(List<String> listaProgramas) {

        return delegateAreaConocimiento.getAreasByProgramaEducativoList(listaProgramas);
    }

    /** 
     * @param idPlan
     * @param idProgramaEducativo
     * @return 
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoAdmin(int idPlan, String idProgramaEducativo) {       
        return delegateAreaConocimiento.getAreasByPlanYProgramaeducativoAdmin(idPlan, idProgramaEducativo);
    }
    
    /**
     * @param idUa
     * @return  Lista de areas de conocimiento correspondientes a una unidad academica 
     */
    public List<Areaconocimiento> getAreasByUnidadAcademica(int idUa) {       
        return delegateAreaConocimiento.getAreasByUnidadAcademica(idUa);
    }
    
    /**
     * @param idPlan id de plan de estudios
     * @param idProgramaEducativo 
     * @param idUsuario
     * @return 
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoCAA(int idPlan, String idProgramaEducativo, int idUsuario) {
        return delegateAreaConocimiento.getAreasByPlanYProgramaeducativoCAA(idPlan, idProgramaEducativo, idUsuario);
    }
    
    /**
      * @param idPlan
      * @param idProgramaEducativo
      * @param usuId
      * @return Lista de area de conocimiento correspondientes al plan de estudios, programa educativo y al profesor 
      */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoRPE(int idPlan, String idProgramaEducativo, int usuId) {       
        return delegateAreaConocimiento.getAreasByPlanYProgramaeducativoRPE(idPlan, idProgramaEducativo, usuId);       
    }
    
    /** 
     * @param idProgramaEducativo
     * @return 
     */
    public List<Areaconocimiento> getAreasByProgramaeducativoAdmin(String idProgramaEducativo) { 
        return delegateAreaConocimiento.getAreasByProgramaeducativoAdmin(idProgramaEducativo);
    }
}
