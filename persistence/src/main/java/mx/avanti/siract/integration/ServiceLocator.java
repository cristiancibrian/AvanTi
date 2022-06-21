package mx.avanti.siract.integration;

import mx.avanti.siract.dao.AreaadministrativaDAO;
import mx.avanti.siract.dao.BaseDAO;
import mx.avanti.siract.dao.CatalogoreportesDAO;
import mx.avanti.siract.dao.EsperadosDAO;
import mx.avanti.siract.dao.GrupoDAO;
import mx.avanti.siract.dao.PlanestudioDAO;
import mx.avanti.siract.dao.ProfesorDAO;
import mx.avanti.siract.dao.ProgramaeducativoDAO;
import mx.avanti.siract.dao.ReporteDAO;
import mx.avanti.siract.dao.TemaunidadDAO;
import mx.avanti.siract.dao.UnidadDAO;
import mx.avanti.siract.dao.UnidadacademicaDAO;
import mx.avanti.siract.dao.reportesDAO;
import mx.avanti.siract.dao.RolDAO;
import mx.avanti.siract.dao.PracticasTallerDAO;
import mx.avanti.siract.dao.UnidadAprendizajeDAO;
import mx.avanti.siract.dao.AlertaDAO;
import mx.avanti.siract.dao.CalendarioReporteDAO;
import mx.avanti.siract.dao.CalendarioReporteTieneAlertaDAO;
import mx.avanti.siract.dao.CoordinadorAreaAdministrativaDAO;
import mx.avanti.siract.dao.ProfesorPertenecePEDAO;
import mx.avanti.siract.dao.RolHasPermisoDAO;
import mx.avanti.siract.dao.SubPermisoDAO;
import mx.avanti.siract.dao.SubTemaUnidadDAO;
import mx.avanti.siract.dao.CampusDAO;
import mx.avanti.siract.dao.CicloEscolarDAO;
import mx.avanti.siract.dao.MensajeDAO;
import mx.avanti.siract.dao.AreaConocimientoDAO;
import mx.avanti.siract.dao.ConfiguracionDAO;
import mx.avanti.siract.dao.PermisoDAO;
import mx.avanti.siract.dao.PracticasCampoDAO;
import mx.avanti.siract.dao.PracticasClinicaDAO;
import mx.avanti.siract.dao.PracticasLaboratorioDAO;
import mx.avanti.siract.dao.ReporteAvanceContenidoTematicoDAO;
import mx.avanti.siract.dao.UnidadAprendizajeImparteProfesorDAO;
import mx.avanti.siract.dao.UnidadAprendizajeTieneContenidotematicoDAO;
import mx.avanti.siract.dao.UsuarioDAO;
import mx.avanti.siract.dao.ResponsableProgramaEducativoDAO;
import mx.avanti.siract.dao.AlumnoDAO;
import mx.avanti.siract.dao.CriterioevaluacionDAO;
import mx.avanti.siract.dao.PoliticaevaluacionDAO;
import mx.avanti.siract.dao.PoliticaTieneCriterioDAO;
import mx.avanti.siract.dao.AlumnoPerteneceGrupoDAO;
import mx.avanti.siract.dao.PoliticaComentarioDAO;


/**
 * @author Cesar Favela
 */
public class ServiceLocator {

    private static BaseDAO baseDAO;
    private static PlanestudioDAO planestudioDAO;
    private static AreaConocimientoDAO areaconocimientoDAO;
    private static ProfesorDAO profesorDAO;
    private static ProfesorPertenecePEDAO profesorPertenecePEDAO;
    private static GrupoDAO grupoDAO;
    private static UnidadAprendizajeDAO unidadAprendizajeDAO;
    private static UnidadacademicaDAO unidadacademicaDAO;
    private static ProgramaeducativoDAO programaeducativoDAO;
    private static TemaunidadDAO temaunidadDAO;
    private static UnidadDAO unidadDAO;
    private static CatalogoreportesDAO catalogoreportesDAO;
    private static reportesDAO reportesDAO;
    private static EsperadosDAO esperadosDAO;
    private static AreaadministrativaDAO areaAdministrativaDAO;
    private static RolDAO rolDAO;
    private static ReporteDAO reporteDAO;
    private static CoordinadorAreaAdministrativaDAO coordinadorAreaAdministrativaDAO;
    private static CampusDAO campusDAO;
    private static CicloEscolarDAO cicloEscolarDAO;
    private static AlertaDAO alertaDAO;
    private static PracticasTallerDAO practicasTallerDAO;
    private static PracticasClinicaDAO practicasClinicaDAO;
    private static SubTemaUnidadDAO subTemaUnidadDAO;
    private static RolHasPermisoDAO rolHasPermisoDAO;
    private static SubPermisoDAO subPermisoDAO;
    private static CalendarioReporteDAO calendarioReporteDAO;
    private static CalendarioReporteTieneAlertaDAO calendarioReporteTieneAlertaDAO;
    private static MensajeDAO mensajeDAO;
    private static PracticasLaboratorioDAO practicasLaboratorioDAO;
    private static PracticasCampoDAO practicasCampoDAO;
    private static PermisoDAO permisoDAO;
    private static UnidadAprendizajeImparteProfesorDAO UAIPDAO;
    private static UnidadAprendizajeTieneContenidotematicoDAO UATCTDAO;
    private static UsuarioDAO usuarioDAO;
    private static ReporteAvanceContenidoTematicoDAO reporteAvanceContenidoTematicoDAO;
    private static ConfiguracionDAO configuracionDAO;
    private static ResponsableProgramaEducativoDAO responsableProgramaEducativoDAO;
    private static AlumnoDAO alumnoDAO;
    private static CriterioevaluacionDAO criterioevaluacionDao;
    private static PoliticaevaluacionDAO politicaevaluacionDao;
    private static PoliticaTieneCriterioDAO politicaTieneCriterioDAO;
    private static AlumnoPerteneceGrupoDAO alumnoPerteneceGrupoDAO;
    private static PoliticaComentarioDAO politicaComentarioDAO;

    public static CalendarioReporteDAO getInstanceCalendarioReporteDAO() {
        if (calendarioReporteDAO == null) {
            calendarioReporteDAO = new CalendarioReporteDAO();
            return calendarioReporteDAO;
        } else {
            return calendarioReporteDAO;
        }
    }

    public static CalendarioReporteTieneAlertaDAO getInstanceCalendarioReporteTieneAlertaDAO() {
        if (calendarioReporteTieneAlertaDAO == null) {
            calendarioReporteTieneAlertaDAO = new CalendarioReporteTieneAlertaDAO();
            return calendarioReporteTieneAlertaDAO;
        } else {
            return calendarioReporteTieneAlertaDAO;
        }
    }

    public static CoordinadorAreaAdministrativaDAO getInstanceCoordinadorAreaAdministrativa() {
        if (coordinadorAreaAdministrativaDAO == null) {
            coordinadorAreaAdministrativaDAO = new CoordinadorAreaAdministrativaDAO();
            return coordinadorAreaAdministrativaDAO;
        } else {
            return coordinadorAreaAdministrativaDAO;
        }
    }

    public static BaseDAO getInstanceBaseDAO() {
        if (baseDAO == null) {
            baseDAO = new BaseDAO();
            return baseDAO;
        } else {
            return baseDAO;
        }
    }

    public static CicloEscolarDAO getInstanceCicloEscolarDAO() {
        if (cicloEscolarDAO == null) {
            cicloEscolarDAO = new CicloEscolarDAO();
            return cicloEscolarDAO;
        } else {
            return cicloEscolarDAO;
        }
    }

    /**
     * @return
     */
    public static SubPermisoDAO getInstanceSubPermisoDAO() {
        if (subPermisoDAO == null) {
            subPermisoDAO = new SubPermisoDAO();
            return subPermisoDAO;
        } else {
            return subPermisoDAO;
        }
    }

    public static AlertaDAO getInstanceAlertaDAO() {
        if (alertaDAO == null) {
            alertaDAO = new AlertaDAO();
            return alertaDAO;
        } else {
            return alertaDAO;
        }
    }

    public static RolHasPermisoDAO getInstanceRolHasPermisoDAO() {
        if (rolHasPermisoDAO == null) {
            rolHasPermisoDAO = new RolHasPermisoDAO();
            return rolHasPermisoDAO;
        } else {
            return rolHasPermisoDAO;
        }
    }

    public static CampusDAO getInstanceCampus() {
        if (campusDAO == null) {
            campusDAO = new CampusDAO();
            return campusDAO;
        } else {
            return campusDAO;
        }
    }

    public static SubTemaUnidadDAO getInstanceSubTemaUnidadDAO() {
        if (subTemaUnidadDAO == null) {
            subTemaUnidadDAO = new SubTemaUnidadDAO();
            return subTemaUnidadDAO;
        } else {
            return subTemaUnidadDAO;
        }
    }

    /**
     * @return
     */
    public static PlanestudioDAO getInstancePlanestudio() {
        if (planestudioDAO == null) {
            planestudioDAO = new PlanestudioDAO();
            return planestudioDAO;
        } else {
            return planestudioDAO;
        }
    }

    public static ProfesorDAO getInstanceProfesor() {
        if (profesorDAO == null) {
            profesorDAO = new ProfesorDAO();
            return profesorDAO;
        } else {
            return profesorDAO;
        }
    }

    /**
     * @return
     */
    public static AreaConocimientoDAO getInstanceAreaconocimiento() {
        if (areaconocimientoDAO == null) {
            areaconocimientoDAO = new AreaConocimientoDAO();
            return areaconocimientoDAO;
        } else {
            return areaconocimientoDAO;
        }
    }

    public static ProfesorPertenecePEDAO getInstanceProfesorPertenecePE() {
        if (profesorPertenecePEDAO == null) {
            profesorPertenecePEDAO = new ProfesorPertenecePEDAO();
            return profesorPertenecePEDAO;
        } else {
            return profesorPertenecePEDAO;
        }
    }

    public static MensajeDAO getInstanceMensajeDAO() {
        if (mensajeDAO == null) {
            mensajeDAO = new MensajeDAO();
            return mensajeDAO;
        } else {
            return mensajeDAO;
        }
    }

    public static GrupoDAO getInstanceGrupoDAO() {
        if (grupoDAO == null) {
            grupoDAO = new GrupoDAO();
            return grupoDAO;
        } else {
            return grupoDAO;
        }
    }

    public static UnidadAprendizajeDAO getInstanceUnidadAprendizajeDAO() {
        if (unidadAprendizajeDAO == null) {
            unidadAprendizajeDAO = new UnidadAprendizajeDAO();
            return unidadAprendizajeDAO;
        } else {
            return unidadAprendizajeDAO;
        }
    }

    public static UnidadacademicaDAO getInstanceUnidadacademica() {
        if (unidadacademicaDAO == null) {
            unidadacademicaDAO = new UnidadacademicaDAO();
            return unidadacademicaDAO;
        } else {
            return unidadacademicaDAO;
        }
    }

    public static ProgramaeducativoDAO getInstanceProgramaeducativo() {
        if (programaeducativoDAO == null) {
            programaeducativoDAO = new ProgramaeducativoDAO();
            return programaeducativoDAO;
        } else {
            return programaeducativoDAO;
        }
    }

    public static UnidadAprendizajeDAO getInstanceUnidadaprendizaje() {
        if (unidadAprendizajeDAO == null) {
            unidadAprendizajeDAO = new UnidadAprendizajeDAO();
            return unidadAprendizajeDAO;
        } else {
            return unidadAprendizajeDAO;
        }
    }

    public static TemaunidadDAO getInstanceTemaunidad() {
        if (temaunidadDAO == null) {
            temaunidadDAO = new TemaunidadDAO();
            return temaunidadDAO;
        } else {
            return temaunidadDAO;
        }
    }

    public static UnidadDAO getInstanceUnidad() {
        if (unidadDAO == null) {
            unidadDAO = new UnidadDAO();
            return unidadDAO;
        } else {
            return unidadDAO;
        }
    }

    public static CatalogoreportesDAO getInstanceCatalogoreportes() {
        if (catalogoreportesDAO == null) {
            catalogoreportesDAO = new CatalogoreportesDAO();
            return catalogoreportesDAO;
        } else {
            return catalogoreportesDAO;
        }
    }

    public static reportesDAO getInstanceReportes() {
        if (reportesDAO == null) {
            reportesDAO = new reportesDAO();
            return reportesDAO;
        } else {
            return reportesDAO;
        }
    }

    public static EsperadosDAO getInstanceEsperados() {
        if (esperadosDAO == null) {
            esperadosDAO = new EsperadosDAO();
            return esperadosDAO;
        } else {
            return esperadosDAO;
        }
    }

    public static AreaadministrativaDAO getInstanceAreaadministrativa() {
        if (areaAdministrativaDAO == null) {
            areaAdministrativaDAO = new AreaadministrativaDAO();
            return areaAdministrativaDAO;
        } else {
            return areaAdministrativaDAO;
        }
    }

    public static RolDAO getInstanceRol() {
        if (rolDAO == null) {
            rolDAO = new RolDAO();
            return rolDAO;
        } else {
            return rolDAO;
        }
    }

    public static ReporteDAO getInstanceReporte() {
        if (reporteDAO == null) {
            reporteDAO = new ReporteDAO();
            return reporteDAO;
        } else {
            return reporteDAO;
        }
    }

    public static PracticasTallerDAO getInstancePracticasTallerDAO() {
        if (practicasTallerDAO == null) {
            practicasTallerDAO = new PracticasTallerDAO();
            return practicasTallerDAO;
        } else {
            return practicasTallerDAO;
        }
    }

    public static PracticasClinicaDAO getInstancePracticasClinicaDAO() {
        if (practicasClinicaDAO == null) {
            practicasClinicaDAO = new PracticasClinicaDAO();
            return practicasClinicaDAO;
        } else {
            return practicasClinicaDAO;
        }
    }

    public static PermisoDAO getInstancePermisoDAO() {
        if (permisoDAO == null) {
            permisoDAO = new PermisoDAO();
            return permisoDAO;
        } else {
            return permisoDAO;
        }
    }

    public static PracticasCampoDAO getInstancePracticasCampoDAO() {
        if (practicasCampoDAO == null) {
            practicasCampoDAO = new PracticasCampoDAO();
            return practicasCampoDAO;
        } else {
            return practicasCampoDAO;
        }
    }

    public static PracticasLaboratorioDAO getInstancePracticasLaboratorioDAO() {
        if (practicasLaboratorioDAO == null) {
            practicasLaboratorioDAO = new PracticasLaboratorioDAO();
            return practicasLaboratorioDAO;
        } else {
            return practicasLaboratorioDAO;
        }
    }

    public static UnidadAprendizajeImparteProfesorDAO getInstanceUnidadAprendizajeImparteProfesor() {
        if (UAIPDAO == null) {
            UAIPDAO = new UnidadAprendizajeImparteProfesorDAO();
            return UAIPDAO;
        } else {
            return UAIPDAO;
        }
    }

    public static UnidadAprendizajeTieneContenidotematicoDAO getInstanceUnidadAprendizajeTieneContenidotematicoDAO() {
        if (UATCTDAO == null) {
            UATCTDAO = new UnidadAprendizajeTieneContenidotematicoDAO();
            return UATCTDAO;
        } else {
            return UATCTDAO;
        }
    }

    public static UsuarioDAO getInstanceUsuario() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
            return usuarioDAO;
        } else {
            return usuarioDAO;
        }
    }

    public static ReporteAvanceContenidoTematicoDAO getInstanceReporteAvanceContenidoTematico() {
        if (reporteAvanceContenidoTematicoDAO == null) {
            reporteAvanceContenidoTematicoDAO = new ReporteAvanceContenidoTematicoDAO();
            return reporteAvanceContenidoTematicoDAO;
        } else {
            return reporteAvanceContenidoTematicoDAO;
        }
    }

    public static ConfiguracionDAO getInstanceConfiguracion() {
        if (configuracionDAO == null) {
            configuracionDAO = new ConfiguracionDAO();
            return configuracionDAO;
        } else {
            return configuracionDAO;
        }
    }

    public static ResponsableProgramaEducativoDAO getInstanceResponsableEducativo() {
        if (responsableProgramaEducativoDAO == null) {
            responsableProgramaEducativoDAO = new ResponsableProgramaEducativoDAO();
            return responsableProgramaEducativoDAO;
        } else {
            return responsableProgramaEducativoDAO;
        }
    }
    public static AlumnoDAO getInstanceAlumno(){
        if(alumnoDAO == null){
            alumnoDAO = new AlumnoDAO();
            return alumnoDAO;
        }else{
            return alumnoDAO;
        }
    }
    public static CriterioevaluacionDAO getInstanceCriterioevaluacion(){
        if(criterioevaluacionDao == null){
            criterioevaluacionDao = new CriterioevaluacionDAO();
            return criterioevaluacionDao;
        }else{
            return criterioevaluacionDao;
        }
    }
    public static PoliticaevaluacionDAO getInstancePoliticaevaluacion(){
        if(politicaevaluacionDao == null){
            politicaevaluacionDao = new PoliticaevaluacionDAO();
            return politicaevaluacionDao;
        }else{
            return politicaevaluacionDao;
        }
    }
    public static PoliticaTieneCriterioDAO getInstancePoliticaTieneCriterio(){
        if(politicaTieneCriterioDAO == null){
            politicaTieneCriterioDAO = new PoliticaTieneCriterioDAO();
            return politicaTieneCriterioDAO;
        }else{
            return politicaTieneCriterioDAO;
        }
    }
    public static AlumnoPerteneceGrupoDAO getInstanceAlumnoPerteneceGrupoDAO(){
        if(alumnoPerteneceGrupoDAO == null){
            alumnoPerteneceGrupoDAO = new AlumnoPerteneceGrupoDAO();
            return alumnoPerteneceGrupoDAO;
        }else{
            return alumnoPerteneceGrupoDAO;
        }
    }
    
    public static PoliticaComentarioDAO getInstancePoliticaComentarioDAO(){
       if(politicaComentarioDAO == null){
           politicaComentarioDAO = new PoliticaComentarioDAO();
           return politicaComentarioDAO;
       }else{
           return politicaComentarioDAO;
       }
    }
}
