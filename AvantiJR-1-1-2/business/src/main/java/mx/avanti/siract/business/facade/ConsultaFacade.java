/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;
//import mx.avanti.business.delegate.DelegateConsulta;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;

/**
 *
 * @author Martin
 */
public class ConsultaFacade {
//    private final DelegateConsulta delegateConsulta;
//    
//    public ConsultaFacade(){
//        delegateConsulta= new DelegateConsulta();
//    }
//
//    
//    public List<Unidadacademica> getUnidadesacademicas() {
//        return delegateConsulta.getUnidadesacademicas();
//    }
//    
//    public List<Programaeducativo> getProgramaseducativos() {
//        return delegateConsulta.getProgramaseducativos();
//    }
//    
//    public List<Planestudio> getPlanesesestudios() {
//        return delegateConsulta.getPlanesesestudios();
//    }
//    
//    public List<Areaconocimiento> getAreasconocimiento() {
//        return delegateConsulta.getAreasconocimiento();
//        
//    }
//    
//    public List<Unidadaprendizaje> getUnidadesaprendizaje() {
//        return delegateConsulta.getUnidadesaprendizaje();
//    }
//    
//    
//    public List<Programaeducativo> getProgramaeducativoByUnidadacademica(int idUnidad){
//        return delegateConsulta.getProgramaeducativoByUnidadacademica(idUnidad);
//    }
//    
//    public List<Programaeducativo> getProgramaeducativoByUnidadacademicaClave(int claveUnidad){
//        return delegateConsulta.getProgramaeducativoByUnidadacademicaClave(claveUnidad);
//    }
//    
//    public List<Areaadministrativa> getAreaadministrativaByProgramaeducativoClave(int pedclave){
//        return delegateConsulta.getAreaadministrativaByProgramaeducativoClave(pedclave);
//    }
//    
//    public List<Planestudio> getPlanesByPrograma(int idPrograma){
//        return delegateConsulta.getPlanesByPrograma(idPrograma);
//    }
//    
//    public List<Planestudio> getPlanesByProgramaClave(int idPrograma){
//        return delegateConsulta.getPlanesByProgramaClave(idPrograma);
//    }
//    
//    public List<Areaconocimiento> getAreasByPlan(int idPlan){
//        return delegateConsulta.getAreasByPlan(idPlan);
//        
//    }
//    
//    public List<Areaconocimiento> getAreasByPlanClave(int pedclave, String pesvigenciaPlan){
//        return delegateConsulta.getAreasByPlanClave(pedclave, pesvigenciaPlan);
//    }
//    
//    
//    public List<Areaconocimiento> getAreasByProgramaEducativoList(List<String> listaProgramas){
//        return delegateConsulta.getAreasByProgramaEducativoList(listaProgramas);
//           
//    }
//    
//    public List<Unidadaprendizaje> getUnidadByArea(int idArea){
//        return delegateConsulta.getUnidadByArea(idArea);
//    }
//    
//    public List<Unidadaprendizaje> getUnidadByAreaClave(int acoclave){
//        return delegateConsulta.getUnidadByAreaClave(acoclave);
//    }
//    
//    public List<Unidadaprendizaje> getUnidadByAreaAndEtapa(int idArea, String etapa){
//        return delegateConsulta.getUnidadByAreaAndEtapa(idArea, etapa);
//    }
//
//    public List<UnidadaprendizajeImparteProfesor> getUnidadesAprendisajeImparteProf(Integer uapid) {
//        return delegateConsulta.getUnidadesAprendisajeImparteProf(uapid);
//    }
//
//    public List<Reporteavancecontenidotematico> getResporteDeAvanceContenidoByUnidadImparte(Integer uipid) {
//        return delegateConsulta.getResporteDeAvanceContenidoByUnidadImparte(uipid);
//    }
//
//    public List<Grupo> getGrupoByUnidad(int uaid) {
//        return delegateConsulta.getGrupoByUnidad(uaid);
//    }
//    
//    public List<Grupo> getGrupoByUnidadClave(int uapclave) {
//        return delegateConsulta.getGrupoByUnidadClave(uapclave);
//    }
//    
//    public List<Profesor> getProfesorByUnidadAprendisaje(int uapclave){
//        return delegateConsulta.getProfesorByUnidadAprendisaje(uapclave);
//    }
//    
//    public List<Profesor> getProfesorByUnidadAprendisajeClave(int uapclave){
//        return delegateConsulta.getProfesorByUnidadAprendisajeClave(uapclave);
//    }
//    
//    public List<Profesor> getProfesoresByUnidadAprendisajeClaves(List<String> uapclave, List<String>... param){
//        return delegateConsulta.getProfesoresByUnidadAprendisajeClaves(uapclave, param);
//    }
//    
//    public List<Grupo> getGrupoByProfesorUnidadAprendisaje(int numempleProfesor ,int uapclave) {
//        return delegateConsulta.getGrupoByProfesorUnidadAprendisaje(numempleProfesor, uapclave);
//    }
//    
//    public List<Unidadacademica> findUnidadAcademica(int idUsuario) {
//        return delegateConsulta.findUnidadAcademica(idUsuario);
//    }
//    
//    public List<Cicloescolar> getCiclosEscolares(){
//        return delegateConsulta.getCiclosEscolares();
//    }
}
