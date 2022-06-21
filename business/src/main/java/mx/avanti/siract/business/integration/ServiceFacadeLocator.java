package mx.avanti.siract.business.integration;

import mx.avanti.siract.business.facade.ProgramaEducativoFacade;
import mx.avanti.siract.business.facade.ReporteFacade;
import mx.avanti.siract.business.facade.RolFacade;
import mx.avanti.siract.business.facade.EsperadosFacade;
import mx.avanti.siract.business.facade.TemaUnidadFacade;
import mx.avanti.siract.business.facade.MensajeFacade;
import mx.avanti.siract.business.facade.GrupoFacade;
import mx.avanti.siract.business.facade.AlertaFacade;
import mx.avanti.siract.business.facade.AreaAdministrativaFacade;
import mx.avanti.siract.business.facade.AsignarGrupoUnidadAprendizajeProfesorFacade;
import mx.avanti.siract.business.facade.CalendarioReporteFacade;
import mx.avanti.siract.business.facade.UnidadAprendizajeFacade;
import mx.avanti.siract.business.facade.PracticasTallerFacade;
import mx.avanti.siract.business.facade.CampusFacade;
import mx.avanti.siract.business.facade.CatalogoReportesFacade;
import mx.avanti.siract.business.facade.CicloEscolarFacade;
import mx.avanti.siract.business.facade.ConfiguracionFacade;
import mx.avanti.siract.business.facade.ConfiguracionSistemaFacade;
import mx.avanti.siract.business.facade.ConsultaFacade;
import mx.avanti.siract.business.facade.CoordinadorAreaAdministrativaFacade;
import mx.avanti.siract.business.facade.CalendarioreporteTieneAlertaFacade;
import mx.avanti.siract.business.facade.PermisoFacade;
import mx.avanti.siract.business.facade.PlanestudioFacade;
import mx.avanti.siract.business.facade.PracticasLaboratorioFacade;
import mx.avanti.siract.business.facade.ProfesorFacade;
import mx.avanti.siract.business.facade.ProfesorPertenecePEFacade;
import mx.avanti.siract.business.facade.AreaConocimientoFacade;
import mx.avanti.siract.business.facade.AsignarProfesorFacade;
import mx.avanti.siract.business.facade.PracticasCampoFacade;
import mx.avanti.siract.business.facade.PracticasClinicaFacade;
import mx.avanti.siract.business.facade.RolHasPermisoFacade;
import mx.avanti.siract.business.facade.SubPermisoFacade;
import mx.avanti.siract.business.facade.SubTemaUnidadFacade;
import mx.avanti.siract.business.facade.UsuarioFacade;
import mx.avanti.siract.business.facade.UnidadFacade;
import mx.avanti.siract.business.facade.UnidadAcademicaFacade;
import mx.avanti.siract.business.facade.UnidadAprendizajeImparteProfesorFacade;
import mx.avanti.siract.business.facade.ReporteAvanceContenidoTematicoFacade;
import mx.avanti.siract.business.facade.UnidadAprendizajeTieneContenidotematicoFacade;
import mx.avanti.business.delegate.ResponsableProgramaEducativoFacade;
import mx.avanti.siract.business.facade.AlumnoFacade;
import mx.avanti.siract.business.facade.CriterioevaluacionFacade;
import mx.avanti.siract.business.facade.PoliticaevaluacionFacade;
import mx.avanti.siract.business.facade.PoliticaTieneCriterioFacade;
import mx.avanti.siract.business.facade.AlumnoPerteneceGrupoFacade;
import mx.avanti.siract.business.facade.PoliticaComentarioFacade;

/**
 *
 * @author Cesar Favela
 */
public class ServiceFacadeLocator {

    private static ReporteFacade reporteFacade;
    private static RolFacade rolFacade;
    private static ProgramaEducativoFacade programaEducativoFacade;
    private static PlanestudioFacade planEstudioFacade;
    private static ProfesorFacade profesorFacade;
    private static ConfiguracionFacade configuracionFacade;
    private static ConfiguracionSistemaFacade configuracionSistemaFacade;
    private static ConsultaFacade consultaFacade;
    private static ReporteAvanceContenidoTematicoFacade reporteAvanceContenidoTematicoFacade;

    private static EsperadosFacade esperadosFacade;
    private static GrupoFacade grupoFacade;
    private static TemaUnidadFacade temaUnidadFacade;
    private static MensajeFacade mensajeFacade;
    private static AlertaFacade alertaFacade;
    private static PracticasTallerFacade practicasTallerFacade;
    private static UnidadAprendizajeFacade unidadAprendizajeFacade;
    private static AsignarGrupoUnidadAprendizajeProfesorFacade asignarGrupoUnidadAprendizajeProfesorFacade;
    private static CampusFacade campusFacade;
    private static CatalogoReportesFacade catalogoReportesFacade;
    private static CicloEscolarFacade cicloEscolarFacade;
    private static CoordinadorAreaAdministrativaFacade coordinadorAreaAdministrativaFacade;
    private static RolHasPermisoFacade rolHasPermisoFacade;
    private static SubTemaUnidadFacade subTemaUnidadFacade;
    private static SubPermisoFacade subPermisoFacade;
    private static PermisoFacade permisoFacade;
    private static AreaAdministrativaFacade areaAdministrativaFacade;
    private static ResponsableProgramaEducativoFacade responsableProgramaEducativoFacade;

    private static UsuarioFacade usuarioFacade;
    private static UnidadFacade unidadFacade;
    private static UnidadAcademicaFacade unidadAcademicaFacade;
    private static UnidadAprendizajeImparteProfesorFacade unidadAprendizajeImparteProfesorFacade;
    private static UnidadAprendizajeTieneContenidotematicoFacade unidadAprendizajeTieneContenidoTematicoFacade;
    private static ProfesorPertenecePEFacade profesorPertenecePEFacade;
    private static AreaConocimientoFacade areaConocimientoFacade;
    private static PracticasCampoFacade practicasCampoFacade;
    private static PracticasClinicaFacade practicasClinicaFacade;
    private static PracticasLaboratorioFacade practicasLaboratorioFacade;
    private static CalendarioReporteFacade calendarioReporteFacade;
    private static AlumnoFacade alumnoFacade;
    private static CriterioevaluacionFacade criterioevaluacionFacade;
    private static PoliticaevaluacionFacade politicaevaluacionFacade;
    private static PoliticaTieneCriterioFacade politicaTieneCriterioFacade;
    private static AlumnoPerteneceGrupoFacade alumnoPerteneceGrupoFacade;
    private static PoliticaComentarioFacade politicaComentarioFacade;
    private static AsignarProfesorFacade asignarProfesorFacade;
    private static CalendarioreporteTieneAlertaFacade calendarioReporteTieneAlertaFacade;

    public static UnidadAprendizajeImparteProfesorFacade getInstanceUnidadAprendizajeImparteProfesorFacade() {
        if (unidadAprendizajeImparteProfesorFacade == null) {
            unidadAprendizajeImparteProfesorFacade = new UnidadAprendizajeImparteProfesorFacade();
            return unidadAprendizajeImparteProfesorFacade;
        } else {
            return unidadAprendizajeImparteProfesorFacade;
        }
    }

    public static UnidadAprendizajeTieneContenidotematicoFacade getInstanceUnidadAprendizajeTieneContenidotematicoFacade() {
        if (unidadAprendizajeTieneContenidoTematicoFacade == null) {
            unidadAprendizajeTieneContenidoTematicoFacade = new UnidadAprendizajeTieneContenidotematicoFacade();
            return unidadAprendizajeTieneContenidoTematicoFacade;
        } else {
            return unidadAprendizajeTieneContenidoTematicoFacade;
        }
    }

    public static UnidadAcademicaFacade getInstanceUnidadAcademicaFacade() {
        if (unidadAcademicaFacade == null) {
            unidadAcademicaFacade = new UnidadAcademicaFacade();
            return unidadAcademicaFacade;
        } else {
            return unidadAcademicaFacade;
        }
    }

    public static UnidadFacade getInstanceUnidadFacade() {
        if (unidadFacade == null) {
            unidadFacade = new UnidadFacade();
            return unidadFacade;
        } else {
            return unidadFacade;
        }
    }

    public static UsuarioFacade getInstanceUsuarioFacade() {
        if (usuarioFacade == null) {
            usuarioFacade = new UsuarioFacade();
            return usuarioFacade;
        } else {
            return usuarioFacade;
        }
    }

    public static ProfesorFacade getInstanceProfesorFacade() {
        if (profesorFacade == null) {
            profesorFacade = new ProfesorFacade();
            return profesorFacade;
        } else {
            return profesorFacade;
        }
    }

    public static AreaAdministrativaFacade getInstanceAreaAdministrativaFacade() {
        if (areaAdministrativaFacade == null) {
            areaAdministrativaFacade = new AreaAdministrativaFacade();
            return areaAdministrativaFacade;
        } else {
            return areaAdministrativaFacade;
        }
    }

    public static ProgramaEducativoFacade getInstanceProgramaEducativoFacade() {
        if (programaEducativoFacade == null) {
            programaEducativoFacade = new ProgramaEducativoFacade();
            return programaEducativoFacade;
        } else {
            return programaEducativoFacade;

        }
    }

    public static EsperadosFacade getInstanceEsperadosFacade() {
        if (esperadosFacade == null) {
            esperadosFacade = new EsperadosFacade();
            return esperadosFacade;
        } else {
            return esperadosFacade;
        }
    }

    public static RolFacade getInstanceRolFacade() {
        if (rolFacade == null) {
            rolFacade = new RolFacade();
            return rolFacade;
        } else {
            return rolFacade;
        }
    }

    public static MensajeFacade getInstanceMensajeFacade() {
        if (mensajeFacade == null) {
            mensajeFacade = new MensajeFacade();
            return mensajeFacade;
        } else {
            return mensajeFacade;
        }
    }

    public static ReporteFacade getInstanceReporteFacade() {
        if (reporteFacade == null) {
            reporteFacade = new ReporteFacade();
            return reporteFacade;
        } else {
            return reporteFacade;
        }

    }

    public static GrupoFacade getInstancGrupoFacade() {
        if (grupoFacade == null) {
            grupoFacade = new GrupoFacade();
            return grupoFacade;
        } else {
            return grupoFacade;
        }
    }

    public static TemaUnidadFacade getInstancTemaUnidadFacade() {
        if (temaUnidadFacade == null) {
            temaUnidadFacade = new TemaUnidadFacade();
            return temaUnidadFacade;
        } else {
            return temaUnidadFacade;
        }

    }

    public static AlertaFacade getInstanceAlertaFacade() {
        if (alertaFacade == null) {
            alertaFacade = new AlertaFacade();
            return alertaFacade;
        } else {
            return alertaFacade;
        }

    }

    public static PracticasTallerFacade getInstancePracticasTallerFacade() {
        if (practicasTallerFacade == null) {
            practicasTallerFacade = new PracticasTallerFacade();
            return practicasTallerFacade;
        } else {
            return practicasTallerFacade;
        }

    }

    public static UnidadAprendizajeFacade getInstanceUnidadAprendizajeFacade() {
        if (unidadAprendizajeFacade == null) {
            unidadAprendizajeFacade = new UnidadAprendizajeFacade();
            return unidadAprendizajeFacade;
        } else {
            return unidadAprendizajeFacade;
        }

    }

    public static AsignarGrupoUnidadAprendizajeProfesorFacade getInstanceAsignarGrupoUnidadAprendizajeProfesorFacade() {
        if (asignarGrupoUnidadAprendizajeProfesorFacade == null) {
            asignarGrupoUnidadAprendizajeProfesorFacade = new AsignarGrupoUnidadAprendizajeProfesorFacade();
            return asignarGrupoUnidadAprendizajeProfesorFacade;
        } else {
            return asignarGrupoUnidadAprendizajeProfesorFacade;
        }

    }

    public static CampusFacade getInstanceCampusFacade() {
        if (campusFacade == null) {
            campusFacade = new CampusFacade();
            return campusFacade;
        } else {
            return campusFacade;
        }

    }

    public static CatalogoReportesFacade getInstanceCatalogoReportesFacade() {
        if (catalogoReportesFacade == null) {
            catalogoReportesFacade = new CatalogoReportesFacade();
            return catalogoReportesFacade;
        } else {
            return catalogoReportesFacade;
        }

    }

    public static CicloEscolarFacade getInstanceCicloEscolarFacade() {
        if (cicloEscolarFacade == null) {
            cicloEscolarFacade = new CicloEscolarFacade();
            return cicloEscolarFacade;
        } else {
            return cicloEscolarFacade;
        }

    }

    public static CoordinadorAreaAdministrativaFacade getInstanceCoordinadorAreaAdministrativaFacade() {
        if (coordinadorAreaAdministrativaFacade == null) {
            coordinadorAreaAdministrativaFacade = new CoordinadorAreaAdministrativaFacade();
            return coordinadorAreaAdministrativaFacade;
        } else {
            return coordinadorAreaAdministrativaFacade;
        }

    }

    public static RolHasPermisoFacade getInstanceRolHasPermisoFacade() {
        if (rolHasPermisoFacade == null) {
            rolHasPermisoFacade = new RolHasPermisoFacade();
            return rolHasPermisoFacade;
        } else {
            return rolHasPermisoFacade;
        }
    }

    public static ConfiguracionFacade getInstanceConfiguracionFacade() {
        if (configuracionFacade == null) {
            configuracionFacade = new ConfiguracionFacade();
            return configuracionFacade;
        } else {
            return configuracionFacade;
        }

    }

    public static ConfiguracionSistemaFacade getInstanceConfiguracionSistemaFacade() {
        if (configuracionSistemaFacade == null) {
            configuracionSistemaFacade = new ConfiguracionSistemaFacade();
            return configuracionSistemaFacade;
        } else {
            return configuracionSistemaFacade;
        }

    }

    public static ConsultaFacade getInstanceConsultaFacade() {
        if (consultaFacade == null) {
            consultaFacade = new ConsultaFacade();
            return consultaFacade;
        } else {
            return consultaFacade;
        }

    }

    public static SubTemaUnidadFacade getInstanceSubTemaUnidadFacade() {
        if (subTemaUnidadFacade == null) {
            subTemaUnidadFacade = new SubTemaUnidadFacade();
            return subTemaUnidadFacade;
        } else {
            return subTemaUnidadFacade;
        }
    }

    public static ReporteAvanceContenidoTematicoFacade getReporteAvanceContenidoTematicoFacade() {
        if (reporteAvanceContenidoTematicoFacade == null) {
            reporteAvanceContenidoTematicoFacade = new ReporteAvanceContenidoTematicoFacade();
            return reporteAvanceContenidoTematicoFacade;
        } else {
            return reporteAvanceContenidoTematicoFacade;
        }

    }

    public static SubPermisoFacade getInstanceSubPermisoFacade() {
        if (subPermisoFacade == null) {
            subPermisoFacade = new SubPermisoFacade();
            return subPermisoFacade;
        } else {
            return subPermisoFacade;
        }
    }

    public static PlanestudioFacade getInstancePlanEstudioFacade() {
        if (planEstudioFacade == null) {
            planEstudioFacade = new PlanestudioFacade();
            return planEstudioFacade;
        } else {
            return planEstudioFacade;
        }
    }

    public static PermisoFacade getInstancePermisoFacade() {
        if (permisoFacade == null) {
            permisoFacade = new PermisoFacade();
            return permisoFacade;
        } else {
            return permisoFacade;
        }
    }

    public static ProfesorPertenecePEFacade getInstanceProfesorPertenecePEFacade() {
        if (profesorPertenecePEFacade == null) {
            profesorPertenecePEFacade = new ProfesorPertenecePEFacade();
            return profesorPertenecePEFacade;
        } else {
            return profesorPertenecePEFacade;
        }
    }

    public static AreaConocimientoFacade getInstanceAreaConocimientoFacade() {
        if (areaConocimientoFacade == null) {
            areaConocimientoFacade = new AreaConocimientoFacade();
            return areaConocimientoFacade;
        } else {
            return areaConocimientoFacade;
        }
    }

    public static PracticasCampoFacade getInstancePracticasCampoFacade() {
        if (practicasCampoFacade == null) {
            practicasCampoFacade = new PracticasCampoFacade();
            return practicasCampoFacade;
        } else {
            return practicasCampoFacade;
        }
    }

    public static PracticasLaboratorioFacade getInstancePracticasLaboratorioFacade() {
        if (practicasLaboratorioFacade == null) {
            practicasLaboratorioFacade = new PracticasLaboratorioFacade();
            return practicasLaboratorioFacade;
        } else {
            return practicasLaboratorioFacade;
        }
    }

    public static PracticasClinicaFacade getInstancePracticasClinicaFacade() {
        if (practicasClinicaFacade == null) {
            practicasClinicaFacade = new PracticasClinicaFacade();
            return practicasClinicaFacade;
        } else {
            return practicasClinicaFacade;
        }
    }

    public static CalendarioReporteFacade getInstanceCalendarioReporteFacade() {
        if (calendarioReporteFacade == null) {
            calendarioReporteFacade = new CalendarioReporteFacade();
            return calendarioReporteFacade;
        } else {
            return calendarioReporteFacade;
        }
    }

    public static CalendarioreporteTieneAlertaFacade getInstanceCalendarioreporteTieneAlertaFacade() {
        if (calendarioReporteTieneAlertaFacade == null) {
            calendarioReporteTieneAlertaFacade = new CalendarioreporteTieneAlertaFacade();
            return calendarioReporteTieneAlertaFacade;
        } else {
            return calendarioReporteTieneAlertaFacade;
        }
    }

    public static AsignarProfesorFacade getInstanceAsignarProfesorFacade() {
        if (asignarProfesorFacade == null) {
            asignarProfesorFacade = new AsignarProfesorFacade();
            return asignarProfesorFacade;
        } else {
            return asignarProfesorFacade;
        }
    }
    public static ResponsableProgramaEducativoFacade getInstanceResponsableProgramaEducativoFacade() {
        if (responsableProgramaEducativoFacade == null) {
            responsableProgramaEducativoFacade = new ResponsableProgramaEducativoFacade();
            return responsableProgramaEducativoFacade;
        } else {
            return responsableProgramaEducativoFacade;
        }
    }
    public static AlumnoFacade getInstanceAlumnoFacade() {
        if (alumnoFacade == null) {
            alumnoFacade = new AlumnoFacade();
            return alumnoFacade;
        } else {
            return alumnoFacade;
        }
    }
    public static CriterioevaluacionFacade getInstanceCriterioevaluacionFacade() {
        if (criterioevaluacionFacade == null) {
            criterioevaluacionFacade = new CriterioevaluacionFacade();
            return criterioevaluacionFacade;
        } else {
            return criterioevaluacionFacade;
        }
    }
    public static PoliticaevaluacionFacade getInstancePoliticaevaluacionFacade() {
        if (politicaevaluacionFacade == null) {
            politicaevaluacionFacade = new PoliticaevaluacionFacade();
            return politicaevaluacionFacade;
        } else {
            return politicaevaluacionFacade;
        }
    }
    public static PoliticaTieneCriterioFacade getInstancePoliticaTieneCriterioFacade() {
        if (politicaTieneCriterioFacade == null) {
            politicaTieneCriterioFacade = new PoliticaTieneCriterioFacade();
            return politicaTieneCriterioFacade;
        } else {
            return politicaTieneCriterioFacade;
        }
    }
    public static AlumnoPerteneceGrupoFacade getInstanceAlumnoPerteneceGrupoFacade() {
        if (alumnoPerteneceGrupoFacade == null) {
            alumnoPerteneceGrupoFacade = new AlumnoPerteneceGrupoFacade();
            return alumnoPerteneceGrupoFacade;
        } else {
            return alumnoPerteneceGrupoFacade;
        }
    }
    
    public static PoliticaComentarioFacade getInstancePoliticaComentarioFacade() {
        if (politicaComentarioFacade == null) {
            politicaComentarioFacade = new PoliticaComentarioFacade();
            return politicaComentarioFacade;
        } else {
            return politicaComentarioFacade;
        }
    }
}
