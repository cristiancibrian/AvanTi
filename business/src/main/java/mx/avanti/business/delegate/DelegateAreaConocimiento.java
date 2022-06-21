/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Oscar D. Sanchez
 */
public class DelegateAreaConocimiento {

    /**
     * Metodo para agregar un area de conocimiento
     *
     * @param areaConocimiento Objeto de la clase Areaconocimiento
     */
    public void saveAreaConocimiento(Areaconocimiento areaConocimiento) {
        ServiceLocator.getInstanceAreaconocimiento().save(areaConocimiento);
    }

    /**
     * Metodo para modificar un area de conocimiento
     *
     * @param areaConocimiento Objeto de la clase Areaconocimiento
     */
    public void updateAreaConocimiento(Areaconocimiento areaConocimiento) {
        ServiceLocator.getInstanceAreaconocimiento().update(areaConocimiento);
    }

    /**
     * Metodo para encontrar todas las areas de conocimiento
     *
     * @return Regresa una lista de Areas de conocimiento
     */
    public List<Areaconocimiento> getAreasConocimiento() {
        return ServiceLocator.getInstanceAreaconocimiento().findAll();
    }

    /**
     * Metodo para eliminar un area de conocimiento
     *
     * @param areaConocimiento Objeto de tipo Areaconocimiento
     */
    public void deleteAreaConocimiento(Areaconocimiento areaConocimiento) {
        ServiceLocator.getInstanceAreaconocimiento().delete(areaConocimiento);
    }

    public int esperadosPorAreaConocimientoRACT(List<Planestudio> planesEstudio, String idCicloEscolar, int idAreaCon){
        int respuesta=0;
        respuesta= ServiceLocator.getInstanceAreaconocimiento().EsperadosPorProgramaEducativoRACT(planesEstudio,idCicloEscolar,idAreaCon);
        return respuesta;
    }
    
    /**
     * Metodo para buscar un area de conocimiento por id
     * @param idAreaConocimiento ID del area de conocimiento a buscar
     * @return Regresa el area de conocimiento encontrado
     */
    public Areaconocimiento getAreaConocimiento(int idAreaConocimiento) {
        return ServiceLocator.getInstanceAreaconocimiento().buscarID_Areaconocimiento(idAreaConocimiento);
    }

//    public int getAreaConocimientoUnidadAprendizaje(int idUnidad){
//        return ServiceLocator.getInstanceAreaconocimiento().findByUnidadAprendizaje(idUnidad);
//    }
    
    /**
     * Metodo para regresar las areas de conocimiento de un plan de estudios
     * @param pedclave clave del programa educativo
     * @param pesvigenciaPlan vigencia del plan de estudios
     * @return lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByPlanClave(int pedclave, String pesvigenciaPlan) {
        return ServiceLocator.getInstanceAreaconocimiento().findByCriteriaClave(pedclave, pesvigenciaPlan);
    }

    /**
     * Metodo para buscar un area de conocimiento por una unidad de aprendizaje
     * @param valor
     * @return 
     */
    public Areaconocimiento findByUnidadAprendizaje(int valor){
        List<Areaconocimiento> areas = ServiceLocator.getInstanceAreaconocimiento().findAll();
        int bandera=0;
        Areaconocimiento ac = new Areaconocimiento();
        
        for(Areaconocimiento area : areas){
            for(Unidadaprendizaje ua : area.getUnidadaprendizajeList()){
                if(valor == ua.getUAPid()){
                    ac.setACOid(area.getACOid());
                    ac.setPlanEstudioPESid(area.getPlanEstudioPESid());
                    break;
                }
            }

        }        
        return ac;
    }
    
    /**
     * Metodo para regresar areas de conocimiento por programa educativo
     * @param listaProgramas
     * @return lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByProgramaEducativoList(List<String> listaProgramas) {
        List<Areaconocimiento> retListaAreas = null;
        List<Areaconocimiento> temp = null;

        for (String programa : listaProgramas) {

            retListaAreas = ServiceLocator.getInstanceAreaconocimiento().findByProgramaEducativo(Integer.parseInt(programa));
            retListaAreas.addAll(temp);
        }
        return retListaAreas;
    }
    /**
     * Metodo para buscar Areas de Conocimiento por plan de estudios y programa educativo
     * @param idPlan id del plan
     * @param idProgramaEducativo id del programa educativo
     * @return  lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoAdmin(int idPlan, String idProgramaEducativo) {
        return ServiceLocator.getInstanceAreaconocimiento().findFromWhereB("planEstudioPESid", "pESid", "'" + idPlan + "' AND a.planEstudioPESid.programaEducativoPEDid.pEDid ='" + idProgramaEducativo + "'", "aCOnombre");   
    }
    /**
     * Metodo para buscar Areas por unidad academica
     * @param idUa id de la unidad academica
     * @return lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByUnidadAcademica(int idUa) {
        List<Areaconocimiento> result = null;
        result = ServiceLocator.getInstanceAreaconocimiento().findFromWhereB("planEstudioPESid.programaEducativoPEDid.unidadAcademicaUACid", "uACid", String.valueOf(idUa), "aCOnombre");
        return result;
    }
    /**
     * Metodo para buscar Areas de Conocimiento por plan de estudios y programa educativo
     * @param idPlan id del plan de estudios
     * @param idProgramaEducativo id del programa educativo
     * @param idUsuario id del usuario
     * @return lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoCAA(int idPlan, String idProgramaEducativo, int idUsuario) {
        Profesor profesorTemporal = new Profesor();
        List<Areaconocimiento> result = null;;
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        if (lista.isEmpty()) {
            profesorTemporal = null;
        } else {
            profesorTemporal = lista.get(0);
        }
        if (profesorTemporal != null) {
            result = ServiceLocator.getInstanceAreaconocimiento().findFromWhere("areaconocimiento", "planEstudioPESid.programaEducativoPEDid.areaadministrativaList d join d.coordinadorareaadministrativaList c join a.planEstudioPESid.programaEducativoPEDid.profesorList",
                    "pROid='" + profesorTemporal.getPROid() + "' AND a.planEstudioPESid='" + idPlan + "' AND "
                    + "c.profesorPROid.pROid", "'" + profesorTemporal.getPROid() + "' ORDER BY aCOnombre");
        } else {
            return null;
        }
        return result;
    }
     /**
     * Metodo para buscar Areas de Conocimiento por plan de estudios y programa educativo
     * @param idPlan id del plan de estudios
     * @param idProgramaEducativo id del programa educativo
     * @param usuId  id del usuario
     * @return lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoRPE(int idPlan, String idProgramaEducativo, int usuId) {
        List<Areaconocimiento> result = null;
        Profesor profesorTemporal = new Profesor();
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(usuId), "pROid");
        if (lista.isEmpty()) {
            profesorTemporal = null;
        } else {
            profesorTemporal = lista.get(0);
        }
        if (profesorTemporal != null) {
            result = ServiceLocator.getInstanceAreaconocimiento().findFromWhere("areaconocimiento", "planEstudioPESid.programaEducativoPEDid.profesorList", "pROid='" + profesorTemporal.getPROid() + "' AND a.planEstudioPESid", "'" + idPlan + "' ORDER BY aCOnombre");
        } else {
            return null;
        }
        return result;
    }

    /**
     * Metodo para regresar areas de conocimiento por programa educativo
     * @param idProgramaEducativo 
     * @return lista de areas de conocimiento
     */
    public List<Areaconocimiento> getAreasByProgramaeducativoAdmin(String idProgramaEducativo) {
        List<Areaconocimiento> result = null;
        result = ServiceLocator.getInstanceAreaconocimiento().findFromWhereB("planEstudioPESid", "programaEducativoPEDid.pEDid ", "'" + idProgramaEducativo + "'", "aCOnombre");
        return result;
    }
}
