package mx.avanti.siract.helper;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.common.NodoCT;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Practicascampo;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Reporte;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Edgar
 */
public class CatalogoCTBeanHelper implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">
    private Profesor profe;
    private Unidadaprendizaje unidadAprendizaje;
    DecimalFormat df;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo lista">
    private final List<Profesor> profesores;
    private List<Unidad> unidades;
    private List<Practicalaboratorio> practicasLab;
    private List<Practicataller> practicasTaller;
    private List<NodoCT> listaPDF;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables primitivas">
    private int hrsIncompletasClase;
    private int hrsIncompletasLab;
    private int hrsIncompletasTaller;
    private boolean hrsCompletas;
    private int hrsCompletasUnidad;
    private int claveProgramaEd;
    private final int SEMANASSEMESTRE;
    //</editor-fold>

    public CatalogoCTBeanHelper() {
        SEMANASSEMESTRE = 16;
        profesores = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
        profe = profesores.get(0);
        listaPDF = new ArrayList();
        df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);
    }
    public void update(UnidadaprendizajeTieneContenidotematico ct){
        ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().modificarContenidoTematico(ct);
    }
    
    public List<UnidadaprendizajeTieneContenidotematico> unidadesTieneContenidoPorPE(int peid, int cesid){
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().unidadesTieneContenidoPorPE(peid, cesid);
    }

    public void agregarContenidoTematico(NodoCT nodoCT, UnidadaprendizajeTieneContenidotematico ct) {
        switch (nodoCT.getTipo()) {
            case 0:
                ServiceFacadeLocator.getInstanceUnidadFacade().agregarUnidad(construirUnidad(nodoCT, 1, ct));
                break;
            case 1:
                ServiceFacadeLocator.getInstancTemaUnidadFacade().agregarTemaUnidad(construirTema(nodoCT, 1));
                break;
            case 2:
                ServiceFacadeLocator.getInstanceSubTemaUnidadFacade().agregarSubtemaunidad(construirSubtema(nodoCT, 1));
                break;
            case 3:
                ServiceFacadeLocator.getInstancePracticasLaboratorioFacade().agregarPracticaLab(construirLaboratorio(nodoCT, 1, ct));
                break;
            default:
                ServiceFacadeLocator.getInstancePracticasTallerFacade().agregarPracticasTall(construirTaller(nodoCT, 1, ct));
        }
    }

    public void eliminarContenidoTematico(Object contenidoTematico, int tipoContenido) {
        switch (tipoContenido) {
            case 0:
                ServiceFacadeLocator.getInstanceUnidadFacade().eliminarUnidad((Unidad) contenidoTematico);
                break;
            case 1:
                ServiceFacadeLocator.getInstancTemaUnidadFacade().eliminarTemaUnidad((Temaunidad) contenidoTematico);
                break;
            case 2:
                ServiceFacadeLocator.getInstanceSubTemaUnidadFacade().eliminarSubtemaunidad((Subtemaunidad) contenidoTematico);
                break;
            case 3:
                ServiceFacadeLocator.getInstancePracticasLaboratorioFacade().eliminarPracticaLab((Practicalaboratorio) contenidoTematico);
                break;
            default:
                ServiceFacadeLocator.getInstancePracticasTallerFacade().eliminarPracticasTall((Practicataller) contenidoTematico);
        }
    }

    public void actualizarContenidoTematico(NodoCT nodoCT, UnidadaprendizajeTieneContenidotematico ct) {
        switch (nodoCT.getTipo()) {
            case 0:
                ServiceFacadeLocator.getInstanceUnidadFacade().modificarUnidad(construirUnidad(nodoCT, 3, ct));
                break;
            case 1:
                ServiceFacadeLocator.getInstancTemaUnidadFacade().modificarTemaUnidad(construirTema(nodoCT, 3));
                break;
            case 2:
                ServiceFacadeLocator.getInstanceSubTemaUnidadFacade().modificarSubTenaUnidad(construirSubtema(nodoCT, 3));
                break;
            case 3:
                ServiceFacadeLocator.getInstancePracticasLaboratorioFacade().modificarPracticaCampo(construirLaboratorio(nodoCT, 3, ct));
                break;
            default:
                ServiceFacadeLocator.getInstancePracticasTallerFacade().modificarPracticasTaller(construirTaller(nodoCT, 3, ct));
        }
    }

    public void clonarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoOriginal, UnidadaprendizajeTieneContenidotematico contenidoClon, String tipoGrupo, String tipoClon) {
        switch (tipoGrupo) {
            case "C":
                contenidoClon.setUnidadList(clonarContenidoClase(contenidoOriginal, contenidoClon, !tipoClon.equals("1")));
                break;
            case "L":
                contenidoClon.setPracticalaboratorioList(clonarContenidoLab(contenidoOriginal, contenidoClon, !tipoClon.equals("1")));
                break;
            default:
                contenidoClon.setPracticatallerList(clonarContenidoTaller(contenidoOriginal, contenidoClon, !tipoClon.equals("1")));
        }

        ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().clonarContenidoTematico(contenidoClon, tipoGrupo);
    }

    public void versionarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoActual, String[] tipoVers) {
        UnidadaprendizajeTieneContenidotematico contenidoNuevo = new UnidadaprendizajeTieneContenidotematico();
        contenidoNuevo.setCTid(ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().obtenerMaximoID() + 1);
        contenidoNuevo.setCicloEscolarCESid(ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual());
        contenidoNuevo.setUAPhorasCampoCompletas(Boolean.FALSE);
        contenidoNuevo.setUAPhorasLaboratorioCompletas(Boolean.FALSE);
        contenidoNuevo.setUAPhorasTallerCompletas(Boolean.FALSE);
        contenidoNuevo.setUAPhorasClaseCompletas(Boolean.FALSE);
        contenidoNuevo.setVersion(contenidoActual.getVersion() + 1);
        contenidoNuevo.setUnidadAprendizajeUAPid(contenidoActual.getUnidadAprendizajeUAPid());

        contenidoNuevo.setUnidadList(clonarContenidoClase(contenidoActual, contenidoNuevo, !tipoVers[0].equals("1")));
        contenidoNuevo.setPracticalaboratorioList(clonarContenidoLab(contenidoActual, contenidoNuevo, !tipoVers[1].equals("1")));
        contenidoNuevo.setPracticatallerList(clonarContenidoTaller(contenidoActual, contenidoNuevo, !tipoVers[2].equals("1")));

        ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().versionarContenidoTematico(contenidoNuevo);
    }

    public void eliminarVersionContenidoTematico(UnidadaprendizajeTieneContenidotematico contenido) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().eliminarContenidoTematico(contenido);
    }

    /**
     * @param contenidoOriginal
     * @param contenidoClon
     * @param conHoras
     * @return
     */
    public List<Unidad> clonarContenidoClase(UnidadaprendizajeTieneContenidotematico contenidoOriginal, UnidadaprendizajeTieneContenidotematico contenidoClon, boolean conHoras) {
        List<Unidad> unidadOrigenCT = contenidoOriginal.getUnidadList();
        List<Unidad> unidadDestinoCT = new ArrayList<>();

        for (Unidad unidad : unidadOrigenCT) {
            Unidad unidadClon = new Unidad(0, unidad.getUNInumero(), unidad.getUNInombre(), conHoras ? unidad.getUNIvalorPorcentaje() : 0);
            unidadClon.setUNIhoras(conHoras ? unidad.getUNIhoras() : 0);
            unidadClon.setContenidoTematicoCTid(contenidoClon);
            List<Temaunidad> temaDestinoCT = new ArrayList<>();

            for (Temaunidad tema : unidad.getTemaunidadList()) {
                Temaunidad temaClon = new Temaunidad(0, tema.getTUNnumero(), tema.getTUNnombre(), conHoras ? tema.getTUNvalorPorcentaje() : 0);
                temaClon.setTUNhoras(conHoras ? tema.getTUNhoras() : 0);
                temaClon.setUnidadUNIid(unidadClon);
                List<Subtemaunidad> subtemaDestinoCT = new ArrayList<>();

                for (Subtemaunidad subtema : tema.getSubtemaunidadList()) {
                    Subtemaunidad subtemaClon = new Subtemaunidad(0, subtema.getSUTnumero(), subtema.getSUTnombre(), conHoras ? subtema.getSUTvalorPorcentaje() : 0);
                    subtemaClon.setSUThoras(conHoras ? subtema.getSUThoras() : 0);
                    subtemaClon.setTemaUnidadTUNid(temaClon);
                    subtemaDestinoCT.add(subtemaClon);
                }

                temaClon.setSubtemaunidadList(subtemaDestinoCT);
                temaDestinoCT.add(temaClon);
            }

            unidadClon.setTemaunidadList(temaDestinoCT);
            unidadDestinoCT.add(unidadClon);
        }

        return unidadDestinoCT;
    }

    /**
     * Metodo para clonar unidad de aprendizaje tipo practicas laboratorio
     *
     * @param contenidoOriginal
     * @param contenidoClon
     * @param conHoras
     * @return
     */
    public List<Practicalaboratorio> clonarContenidoLab(UnidadaprendizajeTieneContenidotematico contenidoOriginal, UnidadaprendizajeTieneContenidotematico contenidoClon, boolean conHoras) {
        List<Practicalaboratorio> origenCT = contenidoOriginal.getPracticalaboratorioList();
        List<Practicalaboratorio> destinoCT = new ArrayList<>();

        for (Practicalaboratorio lab : origenCT) {
            Practicalaboratorio labClon = new Practicalaboratorio(0, lab.getPRLnumero(), lab.getPRLnombre(), conHoras ? lab.getPRLvalorPorcentaje() : 0);
            labClon.setPRLhoras(conHoras ? lab.getPRLhoras() : 0);
            labClon.setContenidoTematicoCTid(contenidoClon);
            destinoCT.add(labClon);
        }

        return destinoCT;
    }

    /**
     * Metodo para clonar unidad de aprendizaje tipo practicas taller.
     *
     * @param contenidoOriginal
     * @param contenidoClon
     * @param conHoras
     * @return
     */
    public List<Practicataller> clonarContenidoTaller(UnidadaprendizajeTieneContenidotematico contenidoOriginal, UnidadaprendizajeTieneContenidotematico contenidoClon, boolean conHoras) {
        List<Practicataller> origenCT = contenidoOriginal.getPracticatallerList();
        List<Practicataller> destinoCT = new ArrayList<>();

        for (Practicataller taller : origenCT) {
            Practicataller tallerClon = new Practicataller(0, taller.getPRTnumero(), taller.getPRTnombre(), conHoras ? taller.getPRTvalorPorcentaje() : 0);
            tallerClon.setPRThoras(conHoras ? taller.getPRThoras() : 0);
            tallerClon.setContenidoTematicoCTid(contenidoClon);
            destinoCT.add(tallerClon);
        }

        return destinoCT;
    }

    public Unidad construirUnidad(NodoCT nodoCT, int tipoAccion, UnidadaprendizajeTieneContenidotematico ct) {
        Unidad unidad = new Unidad(nodoCT.getId(), Integer.parseInt(nodoCT.getNumero()), nodoCT.getNombre(), (float) nodoCT.getPorcentajeAvance());
        unidad.setContenidoTematicoCTid(tipoAccion == 1 ? ct : ((Unidad) nodoCT.getContenidoTem()).getContenidoTematicoCTid());
        unidad.setUNIhoras((float) nodoCT.getHoras());
        unidad.setUNIhorasCompletas(true);
        return unidad;
    }

    public Temaunidad construirTema(NodoCT nodoCT, int tipoAccion) {
        Temaunidad tema = new Temaunidad(nodoCT.getId(), nodoCT.getNumero(), nodoCT.getNombre(), (float) nodoCT.getPorcentajeAvance());
        tema.setUnidadUNIid(tipoAccion == 1 ? (Unidad) nodoCT.getContenidoTem() : ((Temaunidad) nodoCT.getContenidoTem()).getUnidadUNIid());
        tema.setTUNhoras((float) nodoCT.getHoras());
        tema.setTUNhorasCompletas(true);
        return tema;
    }

    public Subtemaunidad construirSubtema(NodoCT nodoCT, int tipoAccion) {
        Subtemaunidad subtema = new Subtemaunidad(nodoCT.getId(), nodoCT.getNumero(), nodoCT.getNombre(), (float) nodoCT.getPorcentajeAvance());
        subtema.setTemaUnidadTUNid(tipoAccion == 1 ? (Temaunidad) nodoCT.getContenidoTem() : ((Subtemaunidad) nodoCT.getContenidoTem()).getTemaUnidadTUNid());
        subtema.setSUThoras((float) nodoCT.getHoras());
        return subtema;
    }

    public Practicalaboratorio construirLaboratorio(NodoCT nodoCT, int tipoAccion, UnidadaprendizajeTieneContenidotematico ct) {
        Practicalaboratorio lab = new Practicalaboratorio(nodoCT.getId(), Integer.parseInt(nodoCT.getNumero()), nodoCT.getNombre(), (float) nodoCT.getPorcentajeAvance());
        lab.setContenidoTematicoCTid(tipoAccion == 1 ? ct : ((Practicalaboratorio) nodoCT.getContenidoTem()).getContenidoTematicoCTid());
        lab.setPRLhoras((float) nodoCT.getHoras());
        return lab;
    }

    public Practicataller construirTaller(NodoCT nodoCT, int tipoAccion, UnidadaprendizajeTieneContenidotematico ct) {
        Practicataller taller = new Practicataller(nodoCT.getId(), Integer.parseInt(nodoCT.getNumero()), nodoCT.getNombre(), (float) nodoCT.getPorcentajeAvance());
        taller.setContenidoTematicoCTid(tipoAccion == 1 ? ct : ((Practicataller) nodoCT.getContenidoTem()).getContenidoTematicoCTid());
        taller.setPRThoras((float) nodoCT.getHoras());
        return taller;
    }

    /**
     * Metodo para traer los programas educativos de la unidad academica a la
     * que pertence el director y/o administrador
     *
     * @param usuarioId
     * @return
     */
    public List<Programaeducativo> programasEducativosDeUsuario(int usuarioId) {
        List<Unidadacademica> unidadesAcademicas;
        List<Programaeducativo> programasEducativos = new ArrayList<>();
        Profesor profesorTemporal = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuarioId).getProfesorList().get(0);
        unidadesAcademicas = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesorTemporal.getPROid()).getUnidadacademicaList();

        //Agregar todos los programas educativos de cada unidad academica
        for (Unidadacademica unidadAcademicaTemporal : unidadesAcademicas) {
            List<Programaeducativo> aux = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(unidadAcademicaTemporal.getUACid()).getProgramaeducativoList();
            programasEducativos.addAll(aux);
        }

        return programasEducativos;
    }

    /**
     * Calcula el numero de semanas del Ciclo escolar
     *
     * @return Numero de semanas del Ciclo Escolar
     */
    public int semanasCicloEscolar() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual().getConfiguracionList().get(0).getCONnumeroSemanas();
    }

    //Metodo que retorna el Id de un profesor en base a su usuario
    public List<Programaeducativo> programasEducativosDeAreaConocimiento(int idAco) {
        return ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(idAco).getProgramaeducativoList();
    }

    public Programaeducativo programaEducativoDeResponsable(int usuarioId) {
        Profesor profesorTemporal = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuarioId).getProfesorList().get(0);
        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesorTemporal.getPROid()).getProfesorPerteneceProgramaeducativoList().get(0).getProgramaeducativo();
    }

    public List<Planestudio> getPlanesestudio(int idPE) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(idPE);
    }

    public Programaeducativo getProgramaEducativoPorID(int idPE) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idPE);
    }

    public Programaeducativo getProgramaEducativoPorClave(int clavePE) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorClave(clavePE);
    }

    public List<Planestudio> getPlanesestudioByUnidadAcademica(int idUa) {
        return ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(idUa).getProgramaeducativoList().get(0).getPlanestudioList();
    }

    public List<Planestudio> getPlanesestudioByUnidadAprendizaje(int idUA) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().getListaPlanesEstudioByUnidadAprendizaje(idUA);
    }

    public List<Planestudio> getPlanesestudioByAreaConocimiento(String idAco) {
        List<Planestudio> planestudios = new ArrayList<>();
        planestudios.add(ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(Integer.valueOf(idAco)).getPlanEstudioPESid());

        return planestudios;
    }

    //Metodos nuevos
    public List<Programaeducativo> programaEducativoDeCoordinadorAreaAdmin(int usuarioId) {
        List<Coordinadorareaadministrativa> coordinadores = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
        List<Programaeducativo> programaeducativo = new ArrayList<>();
        Profesor profesorTemporal = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuarioId).getProfesorList().get(0);
        Coordinadorareaadministrativa temp = null;

        for (Coordinadorareaadministrativa caa : coordinadores) {
            if (Objects.equals(caa.getProfesorPROid().getPROid(), profesorTemporal.getPROid())) {
                temp = caa;
            }
        }

        programaeducativo.add(temp.getAreaAdministrativaAADid().getProgramaEducativoPEDid());

        return programaeducativo;
    }

    /**
     * Método para validar que un contenido temático no tenga reportes.
     *
     * @param contenidoActual
     * @return
     */
    public int validarEliminacionContenido(UnidadaprendizajeTieneContenidotematico contenidoActual) {
        if (!contenidoActual.getUnidadList().isEmpty()) {
            for (Unidad unidad : contenidoActual.getUnidadList()) {
                List<Reporte> reportes = ServiceFacadeLocator.getInstanceReporteFacade().consultarReportePorUNIid(unidad.getUNIid());                

                if (!reportes.isEmpty()) {                    
                    return 1;
                }
            }
        }

        if (!contenidoActual.getPracticalaboratorioList().isEmpty()) {
            for (Practicalaboratorio lab : contenidoActual.getPracticalaboratorioList()) {
                List<Reporte> reportes = ServiceFacadeLocator.getInstanceReporteFacade().consultarReportePorPRLid(lab.getPRLid());                

                if (!reportes.isEmpty()) {                    
                    return 1;
                }
            }
        }

        if (!contenidoActual.getPracticatallerList().isEmpty()) {
            for (Practicataller taller : contenidoActual.getPracticatallerList()) {
                List<Reporte> reportes = ServiceFacadeLocator.getInstanceReporteFacade().consultarReportePorPRTid(taller.getPRTid());

                if (!reportes.isEmpty()) {                    
                    return 1;
                }
            }
        }

        return 0;
    }

    /**
     *
     * @param idPrograma
     * @return Lista de planes de estudio correspondientes a un programa
     * educativo
     */
    public List<Planestudio> getPlanesByPrograma(int idPrograma) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idPrograma).getPlanestudioList();
    }

    /**
     *
     * @param idPlan
     * @param idProgramaEducativo
     * @return
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoAdmin(int idPlan, int idProgramaEducativo) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getAreasByPlanYProgramaeducativoAdmin(idPlan, Integer.toString(idProgramaEducativo));
    }

    /**
     *
     * @param idProgramaEducativo
     * @return
     */
    public List<Areaconocimiento> getAreasByProgramaeducativoAdmin(String idProgramaEducativo) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getAreasByProgramaeducativoAdmin(idProgramaEducativo);
    }

    /**
     *
     * @param idPlan
     * @param idProgramaEducativo
     * @param usuId
     * @return Lista de area de conocimiento correspondientes al plan de
     * estudios, programa educativo y al profesor
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoRPE(int idPlan, int idProgramaEducativo, int usuId) {
        List<Areaconocimiento> areas = ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();
        List<Areaconocimiento> areasDeRPE = new ArrayList<>();

        for (Areaconocimiento area : areas) {
            if (Objects.equals(area.getPlanEstudioPESid().getPESid(), ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuId).getProfesorList().get(0).getProfesorPerteneceProgramaeducativoList().get(0).getProgramaeducativo().getPlanestudioList().get(0).getPESid())) {
                if (Objects.equals(area.getPlanEstudioPESid(), ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(idPlan))) {
                    areasDeRPE.add(area);
                }
            }
        }

        return areasDeRPE;
    }

    /**
     *
     * @param idProgramaEducativo
     * @param usuId
     * @return Lista de areas de conocmiento correspondiente al programa
     * educativo
     */
    public List<Areaconocimiento> getAreasByProgramaeducativoRPE(int idProgramaEducativo, int usuId) {
        List<Areaconocimiento> areas = ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();
        List<Areaconocimiento> areasDeRPE = new ArrayList<>();

        for (Areaconocimiento area : areas) {
            if (Objects.equals(area.getPlanEstudioPESid().getPESid(), ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idProgramaEducativo).getPlanestudioList().get(0).getPESid())) {
                areasDeRPE.add(area);
            }
        }

        return areasDeRPE;
    }

    /**
     *
     * @param idUsuario
     * @return Lista de unidades academicas correspondientes profesor
     */
    public List<Unidadacademica> findUnidadAcademica(int idUsuario) {
        return ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(idUsuario).getProfesorList().get(0).getUnidadacademicaList();
    }

    /**
     * Obtiene unidades de aprendizaje por Unidad Academica
     *
     * @param idUsuario
     * @return lista de unidades de aprendizaje correspondientes al profesor
     */
    public List<Unidadaprendizaje> getUnidadByUnidadAcademica(int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByUnidadAcademica(idUsuario));
    }

    /**
     * Obtiene unidades de aprendizaje por Responsable de Programa Educativo
     *
     * @param usuId
     * @return Lista de unidades de aprendizaje correspondientes el reponsable
     * de programa educativo
     */
    public List<Unidadaprendizaje> getUnidadByRPE(int usuId) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuId).getProfesorList().get(0).getCoordinadorareaadministrativaList().get(0).getUnidadaprendizajeList());
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento, Etapa y
     * Programa Educativo
     *
     * @param idArea
     * @param etapa
     * @param idPE
     * @return Lista de unidades de aprendizaje por area de conocmiento, etapa y
     * programa educativo
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaAndPE(int idArea, String etapa, int idPE) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByAreaAndEtapaAndPE(idArea, etapa, Integer.toString(idPE)));
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento y Etapa
     *
     * @param idArea
     * @param etapa
     * @return Lista de unidades de aprendizaje correspondientes a un area de
     * conocmiento y una etapa
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapa(int idArea, String etapa) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(idArea).getUnidadaprendizajeList());
    }

    /**
     * Obtiene areas de conocimiento por Unidad Academica
     *
     * @param idUa
     * @return Lista de areas de conocimiento correspondientes a una unidad
     * academica
     */
    public List<Areaconocimiento> getAreasByUnidadAcademica(int idUa) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getAreasByUnidadAcademica(idUa);
    }

    /**
     * Metodo para obtener Areas de Conocimiento por Unidad de Aprendizaje
     *
     * @param id Id de la Unidad de Aprendizaje
     * @return Lista de Areas de Conocimiento encontradas
     */
    public List<Areaconocimiento> getAreaPorUAP(int id) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(id).getAreaconocimientoList();
    }

    /**
     * Obtiene areas de conocimiento por Responsable de Programa Educativo
     *
     * @param proId
     * @return Lista de areas de conocmiento corresponientes programa educativo
     */
    public List<Areaconocimiento> getAreasByRPE(int proId) {
        List<Areaconocimiento> areas = ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();
        List<Areaconocimiento> areasDeRPE = new ArrayList<>();

        for (Areaconocimiento area : areas) {
            if (Objects.equals(area.getPlanEstudioPESid().getPESid(), ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(proId).getProfesorList().get(0).getProfesorPerteneceProgramaeducativoList().get(0).getProgramaeducativo().getPlanestudioList().get(0).getPESid())) {
                areasDeRPE.add(area);
            }
        }

        return areasDeRPE;
    }

    /**
     * Obtiene unidades de aprendizaje por Responsable de Programa Educativo
     *
     * @param idUsuario
     * @return Lista de unidad de aprendizaje correspondientes a un responsable
     * de programa educativo
     */
    public List<Unidadaprendizaje> getUnidadByResponsable(int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByResponsable(idUsuario));
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento y Administrador
     *
     * @param idArea id de area de conocimiento
     * @return Lista de unidades de aprendizaje correspondiente a un area de
     * conocmiento
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoAdmin(int idArea) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(idArea).getUnidadaprendizajeList());
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento y Responsable de
     * Programa Educativo
     *
     * @param idArea id de area de conocimiento
     * @param usuId id de responsable de programa educativo
     * @return Lista de unidades de aprendizaje correspondiente a un area de
     * conocmiento y a responsoable de programa educativo
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoRPE(int idArea, int usuId) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(idArea).getUnidadaprendizajeList());
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimieto y Programa
     * Educativo
     *
     * @param idArea id de area de conocmiento
     * @param idPE id de programa educativo
     * @return Lista de unidades de aprendizaje correspondientes al id de area
     * de conocmiento y id de programa educativo
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoAndPE(int idArea, int idPE) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(idArea).getUnidadaprendizajeList());
    }

    /**
     * Obtiene unidades de aprendizaje por Etapa, Programa Educativo y
     * Administrador
     *
     * @param etapa
     * @param idPE id de programa educativo
     * @return Lista de unidades de aprendizaje correspondiente a programa
     * educativo y etapa
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPEAdmin(String etapa, int idPE) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByEtapaAndPEAdmin(etapa, Integer.toString(idPE)));
    }

    /**
     * Obtiene unidades de aprendizaje por Programa Educativo
     *
     * @param idPE id de programa educativo
     * @return Lista de unidades de aprendizaje correspondiente a un programa
     * educativo
     */
    public List<Unidadaprendizaje> getUnidadByPE(int idPE) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByPE(Integer.toString(idPE)));
    }

    /**
     * Obtiene unidades de aprendizaje por Etapa
     *
     * @param etapa
     * @return Lista de unidades de aprendizaje correspondiente a una etapa
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAdmin(String etapa) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByEtapaAdmin(etapa));
    }

    /**
     * Obtiene unidades de aprendizaje por Etapa
     *
     * @param etapa
     * @param usuId Id de profesor
     * @return Lista de unidades de aprendizaje correspondiente a una etapa y a
     * un profesor
     */
    public List<Unidadaprendizaje> getUnidadByEtapa(String etapa, String usuId) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByEtapa(etapa, usuId));
    }

    /**
     * Obtiene unidades de aprendizaje por plan de estudio
     *
     * @param idPlan id de plan de estudios
     * @return Lista de unidades de aprendizaje correspondiente al id de plan de
     * estudios
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudio(int idPlan) {
        List<Unidadaprendizaje> nuevaLista = new ArrayList();

        for (Areaconocimiento ob : ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(idPlan).getAreaconocimientoList()) {
            nuevaLista.addAll(ob.getUnidadaprendizajeList());
        }

        return adecuarUnidades(nuevaLista);
    }

    /**
     * Obtiene unidades de aprendizaje por plan de estudio y etapa
     *
     * @param idPlan id de plan de estudios
     * @param etapa
     * @return Lista de unidades de aprendizaje correspondientes a id de plan de
     * estudios y etapa
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioAndEtapa(int idPlan, String etapa) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByPlanEstudioAndEtapa(idPlan, etapa));
    }

    /**
     * Obtiene areas de conocimiento por Programa Educativo y Admin
     *
     * @param idProgramaEducativo
     * @param usuId
     * @return
     */
    public List<Areaconocimiento> getAreasByCoordinadorAreaAdmin(String idProgramaEducativo, int usuId) {
        return ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuId).getProfesorList().get(0).getAreaconocimientoList();
    }

    /**
     * Obtiene unidades de aprendizaje por Etapa y Coordinador de Area
     * Administrativa
     *
     * @param etapa
     * @param usuId
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByEtapaCAA(String etapa, String usuId) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByEtapaCAA(etapa, usuId));
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento y Coordinador de
     * Area Administrativa
     *
     * @param idArea
     * @param usuId
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoCAA(int idArea, int usuId) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByAreaConocimientoCAA(idArea, usuId));
    }

    /**
     * Obtiene unidades de aprendizaje por Etapa, Programa Educativo y
     * Coordinador de Area Administrativa
     *
     * @param etapa
     * @param idPE
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPECAA(String etapa, int idPE, int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByEtapaAndPECAA(etapa, Integer.toString(idPE), idUsuario));
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento, Programa
     * Educativo y Coordinador de Area Administrativa
     *
     * @param idArea
     * @param idPE
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoAndCAA(int idArea, int idPE, int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByAreaConocimientoAndCAA(idArea, Integer.toString(idPE), idUsuario));
    }

    /**
     * Obtiene unidades de aprendizaje por Coordinador de Area Administrativa
     *
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByCAA(int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(idUsuario).getProfesorList().get(0).getCoordinadorareaadministrativaList().get(0).getUnidadaprendizajeList());
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento, Etapa y
     * Coordinador de Area de Administrativa
     *
     * @param idArea
     * @param etapa
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaCAA(int idArea, String etapa, int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByAreaAndEtapaCAA(idArea, etapa, idUsuario));
    }

    /**
     * Obtiene unidades de aprendizaje por Area de Conocimiento, Etapa, Programa
     * educativo y Coordinador de Area Administrativa
     *
     * @param idArea id area de conocimiento
     * @param etapa etapa
     * @param idPE id de programa educativo
     * @param idUsuario id del usuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaAndPECAA(int idArea, String etapa, int idPE, int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByAreaAndEtapaAndPECAA(idArea, etapa, Integer.toString(idPE), idUsuario));
    }

    /**
     * Obtiene unidades de aprendizaje por Plan de Estudio y Coordinador de Area
     * Administrativa
     *
     * @param idPlan id de plan de estudios
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioCAA(int idPlan, int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByPlanEstudioCAA(idPlan, idUsuario));
    }

    /**
     *
     * @param idPlan
     * @param etapa
     * @param usuId
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioAndEtapaCAA(int idPlan, String etapa, int usuId) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByPlanEstudioAndEtapaCAA(idPlan, etapa, usuId));
    }

    /**
     * Obtiene areas de conocimiento por Plan de Estudios, Programa Educativo y
     * Coordinador de Area Administrativa
     *
     * @param idPlan id de plan de estudios
     * @param idProgramaEducativo
     * @param idUsuario
     * @return
     */
    public List<Areaconocimiento> getAreasByPlanYProgramaeducativoCAA(int idPlan, int idProgramaEducativo, int idUsuario) {
        List<Unidadaprendizaje> listaUnidad = getUnidadByPlanEstudioCAA(idPlan, idUsuario);
        List<Areaconocimiento> areas = new ArrayList<>();

        for (Unidadaprendizaje uap : listaUnidad) {
            if (!areas.contains(uap.getAreaconocimientoList().get(0))) {
                areas.add(uap.getAreaconocimientoList().get(0));
            }
        }

        return areas;
    }

    /**
     * Obtiene areas de conocimiento por Programa Educativo y Coordinador de
     * Area Administrativa
     *
     * @param idProgramaEducativo
     * @param idUsuario
     * @return
     */
    public List<Areaconocimiento> getAreasByProgramaeducativoCAA(int idProgramaEducativo, int idUsuario) {
        List<Unidadaprendizaje> listaUnidad = getUnidadByPECAA(idProgramaEducativo, idUsuario);
        List<Areaconocimiento> areas = new ArrayList<>();

        for (Unidadaprendizaje uap : listaUnidad) {
            if (!areas.contains(uap.getAreaconocimientoList().get(0))) {
                areas.add(uap.getAreaconocimientoList().get(0));
            }
        }

        return areas;
    }

    /**
     * Obtiene areas de conocimiento por Coordinador de Area Administrativa
     *
     * @param proId id de profesor
     * @return
     */
    public List<Areaconocimiento> getAreasByCAA(int proId) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(proId).getAreaconocimientoList();
    }

    /**
     * Obtiene unidades de aprendizaje por Programa Educativo y Coordinador de
     * Area Administrativa
     *
     * @param idPE id de programa educativo
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByPECAA(int idPE, int idUsuario) {
        return adecuarUnidades(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByPECAA(Integer.toString(idPE), idUsuario));
    }

    /**
     * Método para obtener la versión actual de una unidad
     *
     * @param UAPid
     * @return
     */
    public int getVersionActual(int UAPid) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().busquedaContenidoTematico(UAPid).getVersion();
    }

    public String horasAFormato(double horas) {
        int valorEntero = (int) horas;
        double minutos = horas - valorEntero;
        minutos = (minutos * 30 / 0.5);
        minutos = Math.round(Math.abs(minutos));

        if (minutos == 60) {
            minutos = 0;
            valorEntero += 1;
        }

        String horasFormato = minutos < 10 ? valorEntero + ":0" + (int) minutos : valorEntero + ":" + (int) minutos;

        if (valorEntero < 10) {
            return "0" + horasFormato;
        }

        return horasFormato;
    }

    /**
     * Recibe un formato de horas 00:00 y lo convierte a flotante
     *
     * @param formato
     * @return
     */
    public double formatoAHoras(String formato) {
        String[] formatoHora = formato.split(":");
        double horas = Double.parseDouble(formatoHora[0]);
        double minutos = (Double.parseDouble(formatoHora[1]) * 0.5) / 30;

        return Double.parseDouble(df.format(horas + minutos));
    }

    /**
     * Método para
     *
     * @param tipoG
     * @param version
     * @return
     */
    public TreeNode getNodosVers(String tipoG, int version) {
        UnidadaprendizajeTieneContenidotematico contenido = unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(version - 1);

        switch (tipoG) {
            case "C":
                obtenerPracticasLaboratorio(contenido.getPracticalaboratorioList());
                obtenerPracticasTaller(contenido.getPracticatallerList());
                return obtenerUnidadesPorVersion(contenido.getUnidadList());
            case "L":
                obtenerUnidadesPorVersion(contenido.getUnidadList());
                obtenerPracticasTaller(contenido.getPracticatallerList());
                return obtenerPracticasLaboratorio(contenido.getPracticalaboratorioList());
            default:
                obtenerUnidadesPorVersion(contenido.getUnidadList());
                obtenerPracticasLaboratorio(contenido.getPracticalaboratorioList());
                return obtenerPracticasTaller(contenido.getPracticatallerList());
        }
    }

    /**
     *
     * @param listaUnidad
     * @return
     */
    public TreeNode obtenerUnidadesPorVersion(List<Unidad> listaUnidad) {
        unidades = ordenarUnidades(listaUnidad);
        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        int posicionUnidad = 0;
        int sumaHrsUnidades = 0;
        hrsIncompletasClase = 0;
        listaPDF = new ArrayList();

        for (Unidad unidadA : unidades) {
            listaPDF.add(new NodoCT(unidadA, posicionUnidad));
            nodoRaiz.getChildren().add(obtenerTemasPorUnidad(unidadA, posicionUnidad));
            sumaHrsUnidades += unidadA.getUNIhoras();
            posicionUnidad++;
        }

        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasClase() * SEMANASSEMESTRE);
        ((NodoCT) nodoRaiz.getData()).setSumaHoras(sumaHrsUnidades);
        hrsIncompletasClase += (sumaHrsUnidades < unidadAprendizaje.getUAPhorasClase() * SEMANASSEMESTRE) ? 1 : 0;
        hrsCompletas = hrsIncompletasClase == 0;

        return nodoRaiz;
    }

    public TreeNode obtenerTemasPorUnidad(Unidad unidad, int posicionUnidad) {
        TreeNode nodoUnidad = new DefaultTreeNode(new NodoCT(unidad, posicionUnidad));
        int posicionTema = 0;
        double sumaHrsTemas = 0;
        hrsCompletasUnidad = 0;

        if (unidad.getTemaunidadList().size() > 0) {
            for (Temaunidad temaUnidad : ordenarTemas(unidad.getTemaunidadList())) {
                listaPDF.add(new NodoCT(temaUnidad, posicionTema));
                nodoUnidad.getChildren().add(obtenerSubTemaPorTema(temaUnidad, posicionTema));
                sumaHrsTemas += formatoAHoras(horasAFormato(temaUnidad.getTUNhoras()));
                posicionTema++;
            }

            sumaHrsTemas = formatoAHoras(horasAFormato(sumaHrsTemas));
            hrsCompletasUnidad += (sumaHrsTemas < unidad.getUNIhoras()) ? 1 : 0;
        } else {
            sumaHrsTemas = unidad.getUNIhoras();
        }

        ((NodoCT) nodoUnidad.getData()).setSumaHoras(sumaHrsTemas);
        ((NodoCT) nodoUnidad.getData()).setHorasCompletas(hrsCompletasUnidad == 0);
        hrsIncompletasClase += (sumaHrsTemas < unidad.getUNIhoras()) ? 1 : 0;

        return nodoUnidad;
    }

    public TreeNode obtenerSubTemaPorTema(Temaunidad tema, int posicionTema) {
        double hrsTema = Double.parseDouble(df.format(formatoAHoras(horasAFormato(tema.getTUNhoras()))));
        TreeNode nodoTema = new DefaultTreeNode(new NodoCT(tema, posicionTema));
        int posicionSubtema = 0;
        double sumaHrsSubtemas = 0;

        if (tema.getSubtemaunidadList().size() > 0) {
            for (Subtemaunidad subTema : ordenarSubtemas(tema.getSubtemaunidadList())) {
                nodoTema.getChildren().add(new DefaultTreeNode(new NodoCT(subTema, posicionSubtema)));
                listaPDF.add(new NodoCT(subTema, posicionSubtema));
                sumaHrsSubtemas += formatoAHoras(horasAFormato(subTema.getSUThoras()));
                posicionSubtema++;
            }

            sumaHrsSubtemas = formatoAHoras(horasAFormato(sumaHrsSubtemas));
            hrsCompletasUnidad += (sumaHrsSubtemas < hrsTema) ? 1 : 0;
        } else {
            sumaHrsSubtemas = hrsTema;
        }

        ((NodoCT) nodoTema.getData()).setSumaHoras(sumaHrsSubtemas);
        ((NodoCT) nodoTema.getData()).setHorasCompletas(sumaHrsSubtemas >= hrsTema);
        hrsIncompletasClase += (sumaHrsSubtemas < hrsTema) ? 1 : 0;

        return nodoTema;
    }

    /**
     * Obtiene practicas de laboratorio de una unidad de aprendizaje.
     *
     * @param laboratorio
     * @return
     */
    public TreeNode obtenerPracticasLaboratorio(List<Practicalaboratorio> laboratorio) {
        practicasLab = ordenarPracticasLaboratorio(laboratorio);
        listaPDF = new ArrayList();
        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        hrsIncompletasLab = 0;
        int posicionPract = 0;
        int sumaHrsLab = 0;

        for (Practicalaboratorio practica : practicasLab) {
            nodoRaiz.getChildren().add(new DefaultTreeNode(new NodoCT(practica, posicionPract)));
            listaPDF.add(new NodoCT(practica, posicionPract));
            sumaHrsLab += (double) practica.getPRLhoras();
            posicionPract++;
        }

        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasLaboratorio() * SEMANASSEMESTRE);
        ((NodoCT) nodoRaiz.getData()).setSumaHoras(sumaHrsLab);
        hrsIncompletasLab = (sumaHrsLab < unidadAprendizaje.getUAPhorasLaboratorio() * SEMANASSEMESTRE) ? 1 : 0;
        hrsCompletas = hrsIncompletasLab == 0;

        return nodoRaiz;
    }

    /**
     * Obtiene practicas de taller de una Unidad Aprendizaje
     *
     * @param listaTaller
     * @return
     */
    public TreeNode obtenerPracticasTaller(List<Practicataller> listaTaller) {
        practicasTaller = ordenarPracticasTaller(listaTaller);
        listaPDF = new ArrayList();
        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        hrsIncompletasTaller = 0;
        int posicionTaller = 0;
        int sumaHrsTaller = 0;

        for (Practicataller taller : practicasTaller) {
            NodoCT nct = new NodoCT(taller, posicionTaller);
            nodoRaiz.getChildren().add(new DefaultTreeNode(nct));
            listaPDF.add(new NodoCT(taller, posicionTaller));
            sumaHrsTaller += taller.getPRThoras();
            posicionTaller++;
        }

        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasTaller() * SEMANASSEMESTRE);
        ((NodoCT) nodoRaiz.getData()).setSumaHoras(sumaHrsTaller);
        hrsIncompletasTaller = (sumaHrsTaller < unidadAprendizaje.getUAPhorasTaller() * SEMANASSEMESTRE) ? 1 : 0;
        hrsCompletas = hrsIncompletasTaller == 0;

        return nodoRaiz;
    }

    public double dosDecimales(double numero) {
        return Math.ceil(numero * 100.00) / 100.00;
    }

    /**
     *
     * @param buscar
     * @return
     */
    public boolean minMayor(String buscar) {
        buscar = buscar.replace("_", "0");
        String[] parts = buscar.split(":");
        String part2 = parts[1];
        int parte2Int = Integer.parseInt(part2);

        return parte2Int > 59;
    }

    /**
     * Adaptar las lista de unidad de aprendizaje a datatable.
     *
     * @param listaUnidadAprendizaje
     * @return
     */
    public List<Unidadaprendizaje> adecuarUnidades(List<Unidadaprendizaje> listaUnidadAprendizaje) {
        List<Unidadaprendizaje> nuevaLista = new ArrayList();

        for (Unidadaprendizaje unidadAP : listaUnidadAprendizaje) {
            if (!nuevaLista.contains(unidadAP)) {
                if (unidadAP.getUAPhorasClase() != 0) {
                    nuevaLista.add(regresarUnidad(unidadAP, " -- C -- "));
                }

                if (unidadAP.getUAPhorasLaboratorio() != 0) {
                    nuevaLista.add(regresarUnidad(unidadAP, " -- L -- "));
                }

                if (unidadAP.getUAPhorasTaller() != 0) {
                    nuevaLista.add(regresarUnidad(unidadAP, " -- T -- "));
                }
            }
        }

        return nuevaLista;
    }

    public Unidadaprendizaje regresarUnidad(Unidadaprendizaje unidadOriginal, String tipo) {
        int clavePED = claveProgramaEd == 0 ? unidadOriginal.getAreaconocimientoList().get(0).getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDclave() : claveProgramaEd;
        String nuevoNombre = unidadOriginal.getUAPnombre() + tipo + clavePED;
        Unidadaprendizaje unidadNueva = new Unidadaprendizaje(unidadOriginal);
        unidadNueva.setUAPnombre(nuevoNombre);

        return unidadNueva;
    }

    /**
     * Metodo para Unidad Aprendizaje por Clave
     *
     * @param clave Clace de Unidad Aprendizaje
     * @return
     */
    public Unidadaprendizaje getUnidadAprendizajeByClave(String clave) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadaprendizajeByClave(Integer.parseInt(clave));
    }

    /**
     * Metodo para ordenar una lista de unidades por su número de unidad
     *
     * @param unidades
     * @return Unidades ordenadas por numero
     */
    public List<Unidad> ordenarUnidades(List<Unidad> unidades) {
        Collections.sort(unidades, (Unidad u1, Unidad u2) -> u1.getUNInumero() > u2.getUNInumero() ? 1 : -1);
        return unidades;
    }

    /**
     * Metodo para ordenar una lista de temas por su número de tema
     *
     * @param temas
     * @return Unidades ordenados por numero
     */
    public List<Temaunidad> ordenarTemas(List<Temaunidad> temas) {
        Collections.sort(temas, (Temaunidad tu1, Temaunidad tu2) -> tu1.getTUNnumero().compareTo(tu2.getTUNnumero()));
        return temas;
    }

    /**
     * Metodo para ordenar una lista de subtemas por su número de subtema
     *
     * @param subtemas
     * @return Subtemas ordenanos por numero
     */
    public List<Subtemaunidad> ordenarSubtemas(List<Subtemaunidad> subtemas) {
        Collections.sort(subtemas, (Subtemaunidad stu1, Subtemaunidad stu2) -> stu1.getSUTnumero().compareTo(stu2.getSUTnumero()));
        return subtemas;
    }

    /**
     * Metodo para ordenar una lista de practicas de taller por su número de
     * practica
     *
     * @param practicasTaller
     * @return Practicas de taller ordenadas por numero
     */
    public List<Practicataller> ordenarPracticasTaller(List<Practicataller> practicasTaller) {
        Collections.sort(practicasTaller, (Practicataller pt1, Practicataller pt2) -> pt1.getPRTnumero() > pt2.getPRTnumero() ? 1 : -1);
        return practicasTaller;
    }

    /**
     * Metodo para ordenar una lista de practicas de laboratorio por su número
     * de practica
     *
     * @param practicasLaboratorio
     * @return Practicas de laboratorio ordenadas por numero
     */
    public List<Practicalaboratorio> ordenarPracticasLaboratorio(List<Practicalaboratorio> practicasLaboratorio) {
        Collections.sort(practicasLaboratorio, (Practicalaboratorio pl1, Practicalaboratorio pl2) -> pl1.getPRLnumero() > pl2.getPRLnumero() ? 1 : -1);
        return practicasLaboratorio;
    }

    /**
     * Metodo para ordenar una lista de practicas de campo por su número de
     * practica
     *
     * @param practicasCampo
     * @return Practicas de campo ordenadas por numero
     */
    public List<Practicascampo> ordenarPracticasCampo(List<Practicascampo> practicasCampo) {
        Collections.sort(practicasCampo, (Practicascampo pc1, Practicascampo pc2) -> pc1.getPRCnumero() > pc2.getPRCnumero() ? 1 : -1);

        return practicasCampo;
    }

    /**
     * Metodo que retorna los numeros de unidades no registradas
     *
     * @return
     */
    public String unidadesFaltantes() {
        String unidadesFaltantes = "", temasFaltantes = "";
        ArrayList<Integer> numeros = new ArrayList<>();

        if (!unidades.isEmpty()) {
            for (Unidad unidad : unidades) {
                temasFaltantes += temasUnidadFaltantes(unidad);
                numeros.add(unidad.getUNInumero());
            }

            for (int i = 1; i < numeros.get(numeros.size() - 1); i++) {
                if (!numeros.contains(i)) {
                    unidadesFaltantes += "<br/>Unidad " + i;
                }
            }

            unidadesFaltantes += !unidadesFaltantes.isEmpty() ? "<br/>" + temasFaltantes : temasFaltantes;
        }

        return unidadesFaltantes;
    }

    /**
     * Metodo que retorna los numeros de temas no registrados
     *
     * @param unidad
     * @return
     */
    public String temasUnidadFaltantes(Unidad unidad) {
        String temasFaltantes = "", subTemasFaltantes = "";
        int numUni = unidad.getUNInumero();
        ArrayList<Integer> numeros = new ArrayList<>();

        if (!unidad.getTemaunidadList().isEmpty()) {
            for (Temaunidad temaunidad : unidad.getTemaunidadList()) {
                subTemasFaltantes += subtemasUnidadesFaltantes(temaunidad);
                numeros.add(Integer.parseInt(temaunidad.getTUNnumero().split("\\.")[1]));
            }

            for (int j = 1; j < numeros.get(numeros.size() - 1); j++) {
                if (!numeros.contains(j)) {
                    temasFaltantes += "<br/>Tema " + numUni + "." + j;
                }
            }

            temasFaltantes += !temasFaltantes.isEmpty() ? "<br/>" + subTemasFaltantes : subTemasFaltantes;
        }

        return temasFaltantes;
    }

    /**
     * Metodo que retorna los numeros de subtemas no registrados
     *
     * @param temaUnidad
     * @return
     */
    public String subtemasUnidadesFaltantes(Temaunidad temaUnidad) {
        String subtemasUnidadesFaltantes = "";
        ArrayList<Integer> numeros = new ArrayList<>();
        String numTema = temaUnidad.getTUNnumero();

        if (!temaUnidad.getSubtemaunidadList().isEmpty()) {
            for (Subtemaunidad subtemaUnidad : temaUnidad.getSubtemaunidadList()) {
                numeros.add(Integer.parseInt(subtemaUnidad.getSUTnumero().split("\\.")[2]));
            }

            for (int i = 1; i < numeros.get(numeros.size() - 1); i++) {
                if (!numeros.contains(i)) {
                    subtemasUnidadesFaltantes += "<br/>Subtema " + numTema + "." + i;
                }
            }
        }

        return subtemasUnidadesFaltantes;
    }

    /**
     * Metodo que retorna los numeros de las practicas labotarorio no
     * registradas
     *
     * @return
     */
    public String practicasLaboratorioFaltantes() {
        String practicasLaboratorioFaltantes = "";
        ArrayList<Integer> numeros = new ArrayList<>();

        if (!practicasLab.isEmpty()) {
            for (Practicalaboratorio practicalaboratorio : practicasLab) {
                numeros.add(practicalaboratorio.getPRLnumero());
            }

            for (int i = 1; i < numeros.get(numeros.size() - 1); i++) {
                if (!numeros.contains(i)) {
                    practicasLaboratorioFaltantes += "<br/>Practica " + i;
                }
            }
        }

        return practicasLaboratorioFaltantes;
    }

    /**
     * Metodo que retorna los numeros de las practicas taller no registradas
     *
     * @return
     */
    public String practicasTallerFaltantes() {
        String practicasTallerFaltantes = "";
        ArrayList<Integer> numeros = new ArrayList<>();

        if (!practicasTaller.isEmpty()) {
            for (Practicataller practicataller : practicasTaller) {
                numeros.add(practicataller.getPRTnumero());
            }

            for (int i = 1; i < numeros.get(numeros.size() - 1); i++) {
                if (!numeros.contains(i)) {
                    practicasTallerFaltantes += "<br/>Taller " + i;
                }
            }
        }

        return practicasTallerFaltantes;
    }

    public List<NodoCT> recupearNodosHijo(TreeNode nodoPadre) {
        List<NodoCT> nodosHijo = new ArrayList<>();

        for (TreeNode nodeHijo : nodoPadre.getChildren()) {
            nodosHijo.add((NodoCT) nodeHijo.getData());
        }

        return nodosHijo;
    }

    public String seleccionarEtapa(int etapaSeleccionada) {
        switch (etapaSeleccionada) {
            case 1:
                return "Básica";
            case 2:
                return "Disciplinaria";
            default:
                return "Terminal";
        }
    }

    public String obtenerTipoGrupo(String tipoGrupo) {
        switch (tipoGrupo) {
            case "C":
                return "Clase";
            case "L":
                return "Laboratorio";
            default:
                return "Taller";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public Profesor getProfe() {
        return profe;
    }

    public void setProfe(Profesor profe) {
        this.profe = profe;
    }

    public Unidadaprendizaje getUnidadaprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadaprendizaje(Unidadaprendizaje unidadaprendizaje) {
        this.unidadAprendizaje = unidadaprendizaje;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo lista">
    public List<Unidad> getUnidades() {
        return this.unidades;
    }

    public List<Practicalaboratorio> getPracticasLab() {
        return practicasLab;
    }

    public void setPracticasLab(List<Practicalaboratorio> practicasLab) {
        this.practicasLab = practicasLab;
    }

    public List<Practicataller> getPracticasTaller() {
        return practicasTaller;
    }

    public void setPracticasTaller(List<Practicataller> practicasTaller) {
        this.practicasTaller = practicasTaller;
    }

    public List<NodoCT> getListaPDF() {
        return listaPDF;
    }

    public void setListaPDF(List<NodoCT> listaPDF) {
        this.listaPDF = listaPDF;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de variables primitivas">
    public int getHrsIncompletasClase() {
        return hrsIncompletasClase;
    }

    public void setHrsIncompletasClase(int hrsIncompletasClase) {
        this.hrsIncompletasClase = hrsIncompletasClase;
    }

    public int getHrsIncompletasLab() {
        return hrsIncompletasLab;
    }

    public void setHrsIncompletasLab(int hrsIncompletasLab) {
        this.hrsIncompletasLab = hrsIncompletasLab;
    }

    public int getHrsIncompletasTaller() {
        return hrsIncompletasTaller;
    }

    public void setHrsIncompletasTaller(int hrsIncompletasTaller) {
        this.hrsIncompletasTaller = hrsIncompletasTaller;
    }

    public boolean isHrsCompletas() {
        return hrsCompletas;
    }

    public void setHrsCompletas(boolean hrsCompletas) {
        this.hrsCompletas = hrsCompletas;
    }

    public int getClaveProgramaEd() {
        return claveProgramaEd;
    }

    public void setClaveProgramaEd(int claveProgramaEd) {
        this.claveProgramaEd = claveProgramaEd;
    }
    //</editor-fold>
}
