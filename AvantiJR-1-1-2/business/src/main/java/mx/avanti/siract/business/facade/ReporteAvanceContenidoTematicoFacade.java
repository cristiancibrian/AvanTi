/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.Date;
import java.util.List;
import mx.avanti.business.delegate.DelegateReporteAvanceContenidoTematico;
import mx.avanti.siract.custom.entity.ReporteAux;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.entity.Reporte;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;

/**
 *
 * @author Martin
 * @author Mario | Refactor
 */
public class ReporteAvanceContenidoTematicoFacade {
   private final DelegateReporteAvanceContenidoTematico delegateReporteAvanceContenidoTematico;
   
   public ReporteAvanceContenidoTematicoFacade(){
       delegateReporteAvanceContenidoTematico=new DelegateReporteAvanceContenidoTematico();
   }
   //CHIN
   /**
     * Metodo para obtener todos los RACTs
     * @return lista de Racts
     */
   public List<Reporteavancecontenidotematico> getListaReporteavancecontenidotematico() {
       return delegateReporteAvanceContenidoTematico.findAllRACTS();
    }
   
   /**
     * Guarda un RACT a la base de datos
     * @param reporteavancecontenidotematico la instancia RACT a guardar
     */
    public Reporteavancecontenidotematico agregarReporteavancecontenidotematico(Reporteavancecontenidotematico reporteavancecontenidotematico) {
       return delegateReporteAvanceContenidoTematico.saveRACT(reporteavancecontenidotematico);
        
    }
   //CHIN
   /**
    * Obtiene un RACT en especifico por id
    * @param id el id del RACT a buscar
    * @return 
    */
   public Reporteavancecontenidotematico buscarPorId(int id){
       return delegateReporteAvanceContenidoTematico.findRACTById(id);
   }
   //CHIN
   /**
     * Elimina un RACT de la base de datos
     * @param ract a eliminar
     * @return verdadero o falso dependiendo si se elimino correctamente
     */
   public boolean borrarRACT(Reporteavancecontenidotematico ract){
       return delegateReporteAvanceContenidoTematico.deleteRACT(ract);
   }
   //CHIN
   /**
     * Elimina un RACT de la base de datos
     * @param ractId id del ract a eliminar
     * @return verdadero o falso dependiendo si se elimino correctamente
     */
   public boolean borrarRACTporId(int ractId){
       return delegateReporteAvanceContenidoTematico.deleteRACTById(ractId);
   }
   
   /**
     * Actualiza un RACT de la base de datos
     * @param ract la instancia de RACT a actualizar
     * @return verdadero o falso dependiendo si se actualizo correctamente
     */
   public Reporteavancecontenidotematico actualizarRACT(Reporteavancecontenidotematico ract){
       return delegateReporteAvanceContenidoTematico.updateRACT(ract);
   }
   
      /**
     * Termina una transaccion, si recibe un true realiza un commit
     * Si recibe un false realiza un rollback
     * @param exitoso Indica si la operacion fue exitosa en el momento que se llama el metodo.
     */
   public void terminarTransaccion(boolean exitoso){
       delegateReporteAvanceContenidoTematico.endTransaccion(exitoso);
   }
   //TELLES
  /**
     * obtiene los reportes hechos en por un programa educativo
     * @param programasEducativos los programas educativos
     * @param ract el numero de ract a buscar o todos
     * @param reporte 
     * @param cicloEscolar los ciclos escolares a buscar
     * @return lista de racts
     */
    public List<Object[]> getReportesPrograma(List<String> programasEducativos,String ract
                        ,String reporte, List<String> cicloEscolar){
        return delegateReporteAvanceContenidoTematico.getReportesPrograma(programasEducativos, ract, reporte, cicloEscolar);
    }
    
    //CHIN
    public List<Reporteavancecontenidotematico> buscarPorNumeroYUAIP(int unidadAprendizaje,String numero, int proId, int gpoNum, String tipo, String ce, int uaip){
        return delegateReporteAvanceContenidoTematico.findRACTByNumeroYUAIP(unidadAprendizaje, numero, proId, gpoNum, tipo, ce, uaip);
    }
    
    
//    public List<Reporteavancecontenidotematico> getListaUnidadAprendisajeFFWD(String de, String campo, String criterio, String de2, String campo2, String criterio2, String order) {
//       return delegateReporteAvanceContenidoTematico.getListaUnidadAprendisajeFFWD(de, campo, criterio, de2, campo2, criterio2, order);
//    }
//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematico(int id_unidadAprendizaje, int id_profesor, int id_grupo, String subGrupo, String tipo,String ce, int idUnidadSeleccionada) {
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematico(id_unidadAprendizaje, id_profesor, id_grupo, subGrupo, tipo,ce,idUnidadSeleccionada);
//    }
    
    
//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumero(int id_unidadAprendizaje, String num, int id_profesor, int id_grupo, String subGrupo, String tipo,String ce,int idUnidadSeleccionada) {
//        System.out.println("ENTRE AL FACADE " + id_unidadAprendizaje + num + id_profesor + id_grupo + subGrupo + tipo + ce + idUnidadSeleccionada);
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematicoNumero(id_unidadAprendizaje, num, id_profesor, id_grupo, subGrupo, tipo,ce,idUnidadSeleccionada);
//    }
    
    
//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumeroConsultas(int id_unidadAprendizaje, String num, int id_profesor, int id_grupo, String subGrupo, String tipo,String ce, int idUnidadSeleccionada) {
//               System.out.println("ENTRE AL FACADE " + "clave unidad"+id_unidadAprendizaje + "reporte "+num + "profesor" +id_profesor + "grupo " +id_grupo + "subgrupo "+subGrupo +"tipo" + tipo + "CE "+ce + "id unidad " +idUnidadSeleccionada);
//
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematicoNumeroConsultas(id_unidadAprendizaje, num, id_profesor, id_grupo, subGrupo, tipo,ce,idUnidadSeleccionada);
//     
//    }

//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematico(int id_unidadAprendizaje, int id_profesor, int id_grupo, String subGrupo, String tipo) {
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematico(id_unidadAprendizaje, id_profesor, id_grupo, subGrupo, tipo);
//    }
//
//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumero(int id_unidadAprendizaje, String num, int id_profesor, int id_grupo, String subGrupo, String tipo) {
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematicoNumero(id_unidadAprendizaje, num, id_profesor, id_grupo, subGrupo, tipo);
//    }
//    
//   public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumeroConsultas(int id_unidadAprendizaje, String num, int id_profesor, int id_grupo, String subGrupo, String tipo) {
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematicoNumeroConsultas(id_unidadAprendizaje, num, id_profesor, id_grupo, subGrupo, tipo);
//     
//    }
 

//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoUltimo(int id_unidadAprendizaje, int id_profesor, int id_grupo, String tipo, String subGrupo) {
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematicoUltimo(id_unidadAprendizaje, id_profesor, id_grupo, tipo, subGrupo);
//    }
//
//    public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoIncompleto(int id_unidadAprendizaje, int id_profesor, int id_grupo) {
//        return delegateReporteAvanceContenidoTematico.buscarReporteavancecontenidotematicoIncompleto(id_unidadAprendizaje, id_profesor, id_grupo);
//    }

//    private List<Reporteavancecontenidotematico> listReporteAvanceContTem;
//    private List<Areaconocimiento> listAreaCon;
//    private List<Calendarioreporte> listCalendarioreporte;
//    private List<Configuracion> listConfiguracion;
//    private List<CalendarioreporteTieneAlerta> listCalendarioreporteTieneAlerta;
//    private List<UnidadaprendizajeImparteProfesor> listUnidadaprendizajeImparteProfesor;
    
    public List<Reporteavancecontenidotematico> getReporteAvanceContenidoTematico(ReporteAux reporte) {
        return delegateReporteAvanceContenidoTematico.getReporteAvanceContenidoTematico(reporte);
    }
//
    public List<Areaconocimiento> getAreaConocimiento(int uapclave) {
        return delegateReporteAvanceContenidoTematico.getAreaConocimiento(uapclave);
    }
//
    public List<Calendarioreporte> getCalendarioreporte(Date confechainiciosemestre) {
        return delegateReporteAvanceContenidoTematico.getCalendarioreporte(confechainiciosemestre);
    }
//
    public List<Configuracion> getConfiguracion(ReporteAux reporte) {
        return delegateReporteAvanceContenidoTematico.getConfiguracion(reporte);
    }
//
    public List<CalendarioreporteTieneAlerta> getCalendarioreporteTieneAlerta(ReporteAux reporte) {
        return delegateReporteAvanceContenidoTematico.getCalendarioreporteTieneAlerta(reporte);
    }
//
    public List<UnidadaprendizajeImparteProfesor> getUnidadaprendizajeImparteProfesor(ReporteAux reporte) {
        return delegateReporteAvanceContenidoTematico.getUnidadaprendizajeImparteProfesor(reporte);

    }
//
    public List<Coordinadorareaadministrativa> getCoordAreaAdminProfUAprend(int uapclave) {
        return delegateReporteAvanceContenidoTematico.getCoordAreaAdminProfUAprend(uapclave);
    }
//
    public List<Coordinadorareaadministrativa> getCoordAreaAdminProfUAprend(int uapclave, int pedclave, int aadid) {
        return delegateReporteAvanceContenidoTematico.getCoordAreaAdminProfUAprend(uapclave, pedclave, aadid);
    }
//
//    //Agregado 02-11-2015
//    //noReporte 0=todos
    public int countReportesDeProgramaEducativo(int idProgramaEducativ, int noReporte, String estado, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countReportesDeProgramaEducativo(idProgramaEducativ, noReporte, estado, selectCicloEscolarList);
    }
//
    public int countEsperadosProgramaEducativo(int idProgramaEducativ, int noReporte, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countEsperadosProgramaEducativo(idProgramaEducativ, noReporte, selectCicloEscolarList);
    }
//
    public int countReportesDeAreaConocimientos(int areaConId, int noReporte, String estado, String PlanE, String idCicloEscolar) {
        return delegateReporteAvanceContenidoTematico.countReportesDeAreaConocimientos(areaConId, noReporte, estado, PlanE, idCicloEscolar);
    }

    public int countEsperadosAreaConocimientos(int idProgramaEducativ, int noReporte, String PlanE, String pedClave) {
        return delegateReporteAvanceContenidoTematico.countEsperadosAreaConocimientos(idProgramaEducativ, noReporte, PlanE, pedClave);
    }

    public int countReportesDeUnidadAprendizaje(int idProgramaEducativ, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
        return delegateReporteAvanceContenidoTematico.countReportesDeUnidadAprendizaje(idProgramaEducativ, noReporte, estado, selectCicloEscolarList, pedClave);
    }

    public int countEsperadosUnidadAprendizaje(int idProgramaEducativ, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
        return delegateReporteAvanceContenidoTematico.countEsperadosUnidadAprendizaje(idProgramaEducativ, noReporte, selectCicloEscolarList, pedClave);
    }

    public int countReportesDeUnidadAprendizaje(int idProgramaEducativ, int noReporte, String estado, List<String> grupos, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countReportesDeUnidadAprendizaje(idProgramaEducativ, noReporte, estado, grupos, selectCicloEscolarList);
    }

    public int countEsperadosUnidadAprendizaje(int idProgramaEducativ, int noReporte, List<String> grupos, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countEsperadosUnidadAprendizaje(idProgramaEducativ, noReporte, grupos, selectCicloEscolarList);
    }
//
    public int countReportesDeProfesor(String idProfesor, int noReporte, String estado, String extra, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countReportesDeProfesor(idProfesor, noReporte, estado, extra, selectCicloEscolarList);
    }

    public int countEsperadosProfesor(int idProfesor, int noReporte, String extra, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countEsperadosProfesor(idProfesor, noReporte, extra, selectCicloEscolarList);
   }

    public int countReportesDeAreaAdmin(int idAreaAdmin, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
       return delegateReporteAvanceContenidoTematico.countReportesDeAreaAdmin(idAreaAdmin, noReporte, estado, selectCicloEscolarList, pedClave);
    }

    public int countEsperadosAreaAdmin(int idAreaAdmin, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
        return delegateReporteAvanceContenidoTematico.countEsperadosAreaAdmin(idAreaAdmin, noReporte, selectCicloEscolarList, pedClave);
    }

    public double countPorcentajeGeneralAfechaActual(int idUnidadAcademica) {
        return delegateReporteAvanceContenidoTematico.countPorcentajeGeneralAfechaActual(idUnidadAcademica);
    }
//    /**
//     * 
//     * @param id
//     * @return 
//     */
////    public boolean esCoordinadorAC(int id){
////        return delegateReporteAvanceCont
////        boolean esCoordinador=ServiceFacadeLocator.getFacadeReporteavancecontenidotematico().esCoordinadorAC(id);
////        return esCoordinador;
////    }
    

    public int countEsperadosProfesor(Integer idProfesor, int noReporte, List<String> selectGrupo, List<String> selectUnidadAprendizaje, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countEsperadosProfesor(idProfesor, noReporte,selectGrupo,selectUnidadAprendizaje, selectCicloEscolarList);
    }

    public int countReportesDeProfesor(int idProfesor, int noReporte, String estado, List<String> selectGrupo, List<String> selectUnidadAprendizaje, List<String> selectCicloEscolarList) {
        return delegateReporteAvanceContenidoTematico.countReportesDeProfesor(idProfesor, noReporte, estado, selectGrupo,selectUnidadAprendizaje , selectCicloEscolarList);
    }

       /**
     * Metodo para obtener los reportes entregados por programa educativo
     *  para el generador de reportes
     * segun el criterio de busqueda que se establecio
     *
     * @param criterio tipo de busqueda, entregados, no entregados, porcentaje
     * completo...etc
     * 
     * @param idPlan id del plan de estudios del programa educativo, puesto que
     * tambien se separa por plan de estudios en el caso del filtro de PE
     * 
     * @param noRact numero de ract al que corresponde si es 4 seran todos 
     * 
     * @param idCicloEscolar id del ciclo escolar de la busqueda
     * 
     * @param esperados el numero de esperados para calculos internos
     * @return
     */
    public int conteoPorProgramaEducativo(String criterio, int idPlan, String noRact, int idCicloEscolar,int esperados) {
        return delegateReporteAvanceContenidoTematico.ConteoPorProgramaEducativo(criterio, idPlan, noRact, idCicloEscolar, esperados);
    }
    
    /**
     * Metodo para traer el conteo de reportes por criterio Unidad de aprendizaje
     * @param criterio
     * @param idPlan
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param grupo
     * @return 
     */
    public int conteoPorUnidadAprendizaje(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados, int idUnidad) {
        return delegateReporteAvanceContenidoTematico.ConteoPorUnidadAprendizaje(criterio, idPrograma, noRact, idCicloEscolar, esperados,idUnidad);
    }
    
    public int conteoPorAreaConocimiento(String criterio, int idPrograma, String noRact, int idCicloEscolar, int espedados, int areaConid){
      
        return delegateReporteAvanceContenidoTematico.ConteoPorAreaConocimineto(criterio, idPrograma, noRact, idCicloEscolar, espedados, areaConid);
        
    }
    /***
     * MEtodo para obtener los entregados segun el criterio por area Admin
     * si es tronco comun incluira grupos que no son de tronco comun
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idArea
     * @return 
     */
    public int conteoPorAreaAdministrativa(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados, int idArea) {
       return delegateReporteAvanceContenidoTematico.conteoPorAreaAdministrativa(criterio, idPrograma, noRact, idCicloEscolar, esperados, idArea);
    }
    

    /***
     * Metodo para traer conteo de racts segun el criterio, si isAdmin is true
     * contara todos los grupos que le pertencen al profesor de lo contrario
     * solo contara los grupos del programa educativo 
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param isAdmin
     * @param idProfesor
     * @return 
     */
    public int ConteoPorProfesor(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados, boolean isAdmin, int idProfesor) {
        return delegateReporteAvanceContenidoTematico.ConteoPorProfesor(criterio, idPrograma, noRact, idCicloEscolar, esperados, isAdmin, idProfesor);

    }
    
    /**
     *  Conteo de reporte entregados por profesor que pertenecen al area administrativa
     * que coordina el profesor 
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idProfesorCoordinador id del profesor que es coordinador
     * @param idProfesor
     * @return 
     */
    public int conteoPorProfesorCoordinador(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados,int idProfesorCoordinador, int idProfesor){
       return delegateReporteAvanceContenidoTematico.conteoPorProfesorCoordinador(criterio, idPrograma, noRact, idCicloEscolar, esperados, idProfesorCoordinador, idProfesor);
    }
    
}
