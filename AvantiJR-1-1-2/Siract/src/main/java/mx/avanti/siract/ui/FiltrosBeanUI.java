package mx.avanti.siract.ui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.common.DetalleReporte;
import mx.avanti.siract.common.EntidadSemaforo;
import mx.avanti.siract.common.ResumenReporte;
import mx.avanti.siract.common.ResultadoReporteAdapter;
import mx.avanti.siract.common.Tab;
import mx.avanti.siract.custom.entity.ReporteAux;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.FiltrosBeanHelper;
import mx.avanti.siract.helper.GeneralData;
import mx.avanti.siract.helper.RACTBeanHelper;
import mx.avanti.siract.helper.ReporteAvanceAux;
import mx.avanti.siract.helper.SemaforoProgEd;
import mx.avanti.siract.integration.ServiceLocator;
import mx.avanti.siract.util.MeterChartDemo;
import mx.avanti.siract.util.ProgramaEducativoReportes;
import mx.avanti.siract.util.SortbyPEClave;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Owner
 */
@ManagedBean
@ViewScoped
public class FiltrosBeanUI implements Serializable {

    private final int IDCATALOGOADMIGENERADOR = 1;

    public int getIDCATALOGOADMIGENERADOR() {
        return IDCATALOGOADMIGENERADOR;
    }

    // Variables realmente necesarias 2019
    private List<ResumenReporte> resumenesReporte;
    private String headerEsperados;
    private String headerTipoReporte;
    private String headerCriterio;
    private boolean renderPlanResumen;
    private boolean renderTablaResumen;
    private String headerPorcentaje;
    private boolean isAdmin;
    private boolean isCordinadorAreaAdmin;
    private Areaadministrativa areaAdminUsuario;
    private Programaeducativo PeCoordinador;
    private Programaeducativo PeResponsable;
    private boolean tieneAreaAsignada = true;
    private int idProfesorCoordinador;
    private boolean habilitarBusqueda = true;
    private boolean HabilitarCriterios = true;
    private String detalleSeleccionado;// numero de empleado
    private Boolean renderDR1;// detalle ract 1
    private Boolean renderDR2;// detalle ract 2
    private Boolean renderDR3;// detalle ract 3

    //variables Rodrigo
    // modelos que nos traeremos del sistema
    private FiltrosBeanHelper filtrosBeanHelper;
    private Unidadacademica unidadacademica;
    private Programaeducativo programaeducativo;
    private Programaeducativo pe = programaeducativo;
    private Planestudio planestudio;
    private Areaconocimiento areaconocimiento;
    private Unidadaprendizaje unidadaprendizaje;
    private Cicloescolar cicloescolar;
    private List<String> selectCicloEscolarList;
    private int idCicloEscolar = 0;
    //Atributos/Objetos de gráficas
    private CartesianChartModel barModel;
    private CartesianChartModel lineModel;

    public boolean isMostrarPie() {
        return mostrarPie;
    }

    public void setMostrarPie(boolean mostrarPie) {
        this.mostrarPie = mostrarPie;
    }

    private PieChartModel pieModel;
    private MeterGaugeChartModel gaugeModel;

    private String title = "Reportes de avance";
    private String xaxisLabel = "Ciclos";
    private String yaxisLabel = "NO. RACT'S";
    private String min = "0.0";
    private String max = "0.0";
    private String headerTabla;
    //Atributos para Consultas
    private String opcion = "";
    private String ract = "";
    private String reporte = "";
    private String criterio = " ";
    private String tipografico = "barras";
    private boolean mostrarBar = false;
    private boolean mostrarLine = false;
    private boolean mostrarPie = false;
    private boolean mostrarGauge = false;

    private List<Programaeducativo> selectProgramEducativo;
    private ArrayList<String> selectProgramEducativo2;
    private List<Planestudio> selectPlanesEstudio;
    private ArrayList<String> selectPlanesEstudio2;
    private List<Areaconocimiento> selectAreaConocimiento;
    private List<Areaadministrativa> selectAreaAdministrativa;
    private List<Unidadaprendizaje> selectUnidadAprendizaje;
    private List<String> selectGrupo;
    private List<Profesor> selectProfesor;
    private List<GeneralData> generalDataList;//Lista de objetos para tabla de reporte general
    private ArrayList<String> Plan;
    private ArrayList<Integer> Programa;
    private ArrayList<String> Ciclo;
    private ArrayList<String> RactEntEsp;//ArrayList de entregados y esperados para la grafica de interfaz
    private List<SemaforoProgEd> semProgEd;

    // Variables Rodrigo
    private String tipoMensajeGrowl = "";
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    private Usuario usuario;
    private String rolSeleccionado;

    // variables para identificacion de renglones y columnas Programas educativos  - Alex Cota
    private int rowBand = 0, colBand = 0;
    private boolean disableBtnEjecutarGrafico = false;
    private boolean habilitarBtnReporteGrl = false;
    private boolean directorOrAdmin = false;
    private Programaeducativo primerOptionPE;
    private Usuario usuarioAct;
    private List<Planestudio> planesByProgramaClave;
    private List<EntidadSemaforo> entidades; // variable para manejar las tablas

    // de resumen y las graficas en la interfaz
    private String ractSeleccionado3 = "";
    private List<Profesor> profesorTempList;
    private Programaeducativo programaeducativoByNotAdmin;

    private List<String> selectPlanesEstudioAdmva = new ArrayList<>();
    private boolean renderProgramaForm, renderAreaConForm, renderAreaAdminForm, renderUnidadForm, renderProfesorForm;
    private List<Areaadministrativa> areasAdministrativa;
    private boolean areaAdmin;
    private Unidadacademica unidadACDatos;
    //Manejador de las tabs para los reportes
    private List<Tab> tabs;
    private ResultadoReporteAdapter resultadoAdapter;
    private String consultarTabla;

    public FiltrosBeanUI() {

        renderAreaAdminForm = false;
        renderAreaConForm = false;
        renderProfesorForm = false;
        renderProgramaForm = false;
        renderUnidadForm = false;

        //variables constructor Rodrigo
        filtrosBeanHelper = new FiltrosBeanHelper();
        //si se iniciale la barras como nullo o con el metodo sin definir se da un problema
        barModel = initBarModel();
        lineModel = initLineModel();
        pieModel = initPieModel();

        //Inicializando objetos, con id en 0(cero).
        try {
            unidadacademica = new Unidadacademica();
            programaeducativo = new Programaeducativo();
            programaeducativo.setPEDid(0);
            planestudio = new Planestudio();
            planestudio.setPESid(0);
            areaconocimiento = new Areaconocimiento();
            areaconocimiento.setACOid(0);
            unidadaprendizaje = new Unidadaprendizaje();
            unidadaprendizaje.setUAPid(0);
            cicloescolar = new Cicloescolar();
            cicloescolar.setCESid(0);
            selectCicloEscolarList = new ArrayList<>();
            selectPlanesEstudio = new ArrayList<>();
            selectPlanesEstudio2 = new ArrayList<>();
            selectPlanesEstudio2.add(planestudio.getPESvigenciaPlan());
            selectProgramEducativo = new ArrayList<>();
            selectAreaConocimiento = new ArrayList<>();
            selectAreaAdministrativa = new ArrayList<>();
            selectUnidadAprendizaje = new ArrayList<>();
            selectGrupo = new ArrayList<>();
            selectProfesor = new ArrayList<>();
            generalDataList = new ArrayList<>();
            Plan = new ArrayList<>();
            Programa = new ArrayList<>();
            Ciclo = new ArrayList<>();
            Plan.add("2006-2");
            Plan.add("2008-2");
            Plan.add("2009-2");
            Plan.add("2015-2");
        } catch (Exception ex) {
            System.err.println(ex);
        }

        //variables constructor Rodrigo
        filtrosBeanHelper = new FiltrosBeanHelper();
        listaAux = new ArrayList<>();
        listaCatalogoReportes = null;
        usuario = new Usuario();
        criterio = new String();
        CTRnombre = new String();
        CTRnombre = "";
        botonCancelar = false;
        isPAGCTodosRacts = false;
        semProgEd = new ArrayList<>();
        initConsultasGuardadas();
    }

    public Unidadacademica getUnidadACDatos() {
        return unidadACDatos;
    }

    public void setUnidadACDatos(Unidadacademica unidadACDatos) {
        this.unidadACDatos = unidadACDatos;
    }

    public boolean isRenderTablaResumen() {
        return renderTablaResumen;
    }

    public void setRenderTablaResumen(boolean renderTablaResumen) {
        this.renderTablaResumen = renderTablaResumen;
    }

    public String getConsultarTabla() {
        if (criterio.equalsIgnoreCase("programa_educativo")) {
            consultarTabla = "Programa educativo";
        } else {
            if (criterio.equalsIgnoreCase("area_conocimiento")) {
                consultarTabla = "Area de conocimiento";
            } else {
                if (criterio.equalsIgnoreCase("area_administrativa")) {
                    consultarTabla = "Area administrativa";
                } else {
                    if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
                        consultarTabla = "Unidad de aprendizaje";
                    } else {
                        if (criterio.equalsIgnoreCase("profesor")) {
                            consultarTabla = "Profesor";
                        } else {
                            consultarTabla = "Programa educativo";
                        }
                    }
                }
            }
        }
        return consultarTabla;
    }

    public ResultadoReporteAdapter getResultadoAdapter() {
        return resultadoAdapter;
    }

    public String getHeaderTabla() {
        return headerTabla;
    }

    public void setHeaderTabla(String headerTabla) {
        this.headerTabla = headerTabla;
    }

    public List<String> getSelectPlanesEstudioAdmva() {
        return selectPlanesEstudioAdmva;
    }

    public void setSelectPlanesEstudioAdmva(List<String> selectPlanesEstudioAdmva) {
        this.selectPlanesEstudioAdmva = selectPlanesEstudioAdmva;
    }

    public List<String> getSelectCicloEscolarList() {
        return selectCicloEscolarList;
    }

    public void setSelectCicloEscolarList(List<String> selectCicloEscolarList) {
        this.selectCicloEscolarList = selectCicloEscolarList;

    }

    public ArrayList<String> getSelectPlanesEstudio2() {
        return selectPlanesEstudio2;
    }

    public void setSelectPlanesEstudio2(ArrayList<String> selectPlanesEstudio2) {
        this.selectPlanesEstudio2 = selectPlanesEstudio2;
    }

    public List<String> getSelectPlanesEstudioUniForm() {
        return selectPlanesEstudioUniForm;
    }

    public void setSelectPlanesEstudioUniForm(List<String> selectPlanesEstudioUniForm) {
        this.selectPlanesEstudioUniForm = selectPlanesEstudioUniForm;

    }

    public List<String> getSelectAreaConocimientoUniForm() {
        return selectAreaConocimientoUniForm;
    }

    public void setSelectAreaConocimientoUniForm(List<String> selectAreaConocimientoUniForm) {
        this.selectAreaConocimientoUniForm = selectAreaConocimientoUniForm;
    }

    public List<String> getSelectUnidadAprendisajeUniForm() {
        return selectUnidadAprendizajeUniForm;
    }

    public void setSelectUnidadAprendisajeUniForm(List<String> selectUnidadAprendizajeUniForm) {
        this.selectUnidadAprendizajeUniForm = selectUnidadAprendizajeUniForm;
    }

    public List<String> getSelectGrupoUniForm() {
        return selectGrupoUniForm;
    }

    public void setSelectGrupoUniForm(List<String> selectGrupoUniForm) {
        this.selectGrupoUniForm = selectGrupoUniForm;

    }

    public List<Tab> getTabs() {
        return tabs;
    }

    private int usuId = 0;

    public int getUsuId() {
        return usuId;
    }

    public void setUsuId(int usuId) {
        this.usuId = usuId;
    }

    @PostConstruct
    public void postConstructor() {
        List<EntidadSemaforo> entidades = new ArrayList();
        resumenesReporte = new ArrayList();
        usuario = loginBean.getLogueado();

        if (loginBean.getIdGlobal() == 1 || loginBean.getIdGlobal() == 2 || loginBean.getIdGlobal() == 3) {
            isAdmin = true;
            isCordinadorAreaAdmin = false;
        } else if (loginBean.getIdGlobal() == 4) {
            isAdmin = false;
            isCordinadorAreaAdmin = true;
            HabilitarCriterios = false;
            try {
                PeCoordinador = loginBean.getLogueado().getProfesorList().get(0).getCoordinadorareaadministrativaList().
                        get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid();
                areaAdminUsuario = loginBean.getLogueado().getProfesorList().get(0).getCoordinadorareaadministrativaList().
                        get(0).getAreaAdministrativaAADid();
                idProfesorCoordinador = loginBean.getLogueado().getProfesorList().get(0).getPROid();
            } catch (Exception ex) {
                tieneAreaAsignada = false;
                FacesContext.getCurrentInstance().addMessage("growlSticky", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin área administrativa asignada", "No tiene ningún área administrativa asignada, verificar con administrador del sistema"));
                PrimeFaces.current().ajax().update("growlSticky");
                habilitarBusqueda = false;
            }
        } else {
            isAdmin = false;
            isCordinadorAreaAdmin = false;
            try {
                List<Programaeducativo> peducativos = new ArrayList<Programaeducativo>();
                for (ResponsableProgramaEducativo rpe : loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList()) {
                    if (!peducativos.contains(rpe.getProgramaeducativo())) {
                        peducativos.add(rpe.getProgramaeducativo());
                    }
                }
                PeResponsable = peducativos.get(0);
//                PeResponsable = loginBean.getLogueado().getProfesorList().get(0).getProgramaeducativoList().get(0);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("growlSticky", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin programa educativo asignado", "No tiene ningún programa educativo asignado, verificar con administrador del sistema"));
                PrimeFaces.current().ajax().update("growlSticky");
                habilitarBusqueda = false;
            }
        }
    }

    //variables metodos Jesus Ruelas
    private ArrayList<ReporteAvanceAux> listaAux;
    private String op;
    private String tipo;
    private int calnumeroReporte;
    private int numRact;
    private String cescicloEscolar;
    private int acoclave;
    private int clavepe;
    private String pesvigencia;
    private int numProfUIPid;
    private Date fecha1;
    private int pronumeroEmpleado;
    private int gponumero;
    private int clave;
    private int uapclave;
    private int uacclave;
    private int creid;
    private int aadid = 0;
    //aqui agregue Jesus Ruelas
    private String CTRnombre = "";
    private String tipoReporteCTR;
    private List<Catalogoreportes> listaCatalogoReportes;
    private boolean botonCancelar;
    private boolean isPAGCTodosRacts;
    //aqui agregue Jesus Ruelas
    //variables metodos Jesus Ruelas
    private String tituloGraficas;
    private String tituloTabla;

    private List<String> selectAreaAdminString = new ArrayList();
    private List<String> selectProfesorString = new ArrayList();
    private List<String> selectProgramaEducativoString = new ArrayList();
    private List<String> selectPlanesEstudioUniForm = new ArrayList();
    private List<String> selectAreaConocimientoUniForm = new ArrayList();
    private List<String> selectUnidadAprendizajeUniForm = new ArrayList();
    private List<String> selectGrupoUniForm = new ArrayList();
    private String programaEducativoId = "0";
    private String unidadAcademicaId;

    //Codigo por Jesus Pio
    private BarChartModel model;

    //Francisco reyes variable para activar y desactivar boton de generar exl
    private boolean generarExl = true;

    //Unidades academicas
    private List<Unidadacademica> unidadesacademicas = new ArrayList();

    public List<Unidadacademica> getUnidadesacademicas() {
        if (isAdmin) {
            return filtrosBeanHelper.getUnidadAca(32);
        } else {
            List<Unidadacademica> lista = new ArrayList();
            lista.add(loginBean.getLogueado().getProfesorList().get(0).getProfesorPerteneceProgramaeducativoList().get(0).getProgramaeducativo().getUnidadAcademicaUACid());
            unidadAcademicaId = String.valueOf(lista.get(0).getUACid());
            return lista;
        }

    }

    public void setUnidadesacademicas(List<Unidadacademica> unidadesacademicas) {
        this.unidadesacademicas = unidadesacademicas;
    }

    //codigo Rodrigo y Ricardo
    public List<GeneralData> getGeneralDataList() {
        return generalDataList;
    }

    public Programaeducativo getProgramaeducativoByNotAdmin() {
        if (!isDirectorOrAdmin()) {
            setProgramaeducativoByNotAdmin(filtrosBeanHelper.findProgramaEducativoByClave("14006"));
        }
        return programaeducativoByNotAdmin;
    }

    public void setProgramaeducativoByNotAdmin(Programaeducativo programaeducativoByNotAdmin) {
        this.programaeducativoByNotAdmin = programaeducativoByNotAdmin;
    }

    public void CreateGeneralDataList() {
        if (!reporte.equals("entregadosatiempoenfechalimiteydespues")
                || !reporte.equals("Porcentaje de Avance Global Completo")
                || !reporte.equals("Porcentaje de Avance Global Incompleto")
                || !reporte.equals("Porcentaje de Avance Global Completo e Incompleto")) {
            ArrayList<String> entregados1 = null;
            ArrayList<String> entregados2 = null;
            ArrayList<String> entregados3 = null;
            ArrayList<String> esperados = null;
            GeneralData data;
            //<editor-fold defaultstate="collapsed" desc="llenar Data">
            generalDataList.clear();
            if (criterio.equalsIgnoreCase("programa_educativo")) {
                if (reporte.equalsIgnoreCase("entregados") || reporte.equalsIgnoreCase("noentregados") || reporte.equalsIgnoreCase("entregadosynoentregados")) {
                    entregados1 = filtrosBeanHelper.getProgramaEduEntregadosEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "1");
                    entregados2 = filtrosBeanHelper.getProgramaEduEntregadosEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "2");
                    entregados3 = filtrosBeanHelper.getProgramaEduEntregadosEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "3");
                }
                if (reporte.equalsIgnoreCase("entregadosatiempo")) {
                    System.out.println("entro a entregados a tiempo");
                    entregados1 = filtrosBeanHelper.getAtiempoProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "1", "", "");
                    entregados2 = filtrosBeanHelper.getAtiempoProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "", "2", "");
                    entregados3 = filtrosBeanHelper.getAtiempoProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "", "", "3");
                }
                if (reporte.equalsIgnoreCase("entregadosenfechalimite")) {
                    System.out.println("entro a entregados en limite");
                    entregados1 = filtrosBeanHelper.getEnLimiteProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "1", "", "");
                    entregados2 = filtrosBeanHelper.getEnLimiteProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "", "2", "");
                    entregados3 = filtrosBeanHelper.getEnLimiteProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "", "", "3");
                }
                if (reporte.equalsIgnoreCase("entregadosdespueslimite")) {
                    System.out.println("entro a entregados despues de limite");
                    entregados1 = filtrosBeanHelper.getDespuesLimiteProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "1", "", "");
                    entregados2 = filtrosBeanHelper.getDespuesLimiteProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "", "2", "");
                    entregados3 = filtrosBeanHelper.getDespuesLimiteProgramaEduEsp(unidadacademica.getUACid(), Plan, Programa, Ciclo, "", "", "3");
                }
                esperados = filtrosBeanHelper.getProgramaEduEsperados(unidadacademica.getUACid(), Plan, Programa, Ciclo);
            }
            int sumaEsperados = 0;
            int sumaEntregados = 0;
            for (String esperado1 : esperados) {
                String[] esperado = esperado1.split("-");
                sumaEsperados = sumaEsperados + Integer.parseInt(esperado[1]);
            }
            sumaEsperados = sumaEsperados / 3;
            for (String Plan1 : Plan) {
                System.out.println(Plan1);
            }
            for (String entregados11 : entregados1) {
                String[] entregado = entregados11.split("-");
                sumaEntregados = sumaEntregados + Integer.parseInt(entregado[1]);
            }
            data = null;
            if (sumaEsperados > 0) { //aqui modifique Jesus Ruelas
                if (reporte.equals("entregados")) {
                    tituloTabla = "Reporte general de Entregados por programa educativo";
                    data = new GeneralData("RACT 1", sumaEntregados, sumaEsperados);
                }
                if (reporte.equals("noentregados")) {
                    tituloTabla = "Reporte general de No entregados por programa educativo";
                    data = new GeneralData("RACT 1", sumaEsperados - sumaEntregados, sumaEsperados);
                }
                if (reporte.equals("entregadosynoentregados")) {
                    tituloTabla = "Reporte general de Entregados y No entregados por programa educativo";
                    data = new GeneralData("RACT 1", sumaEsperados, sumaEsperados);
                }
                if (reporte.equalsIgnoreCase("entregadosatiempo")) {
                    tituloTabla = "Reporte general de Entregados a tiempo por programa educativo";
                    data = new GeneralData("RACT 1", sumaEntregados, sumaEsperados);
                }
                if (reporte.equalsIgnoreCase("entregadosenfechalimite")) {
                    tituloTabla = "Reporte general de Entregados en fecha límite por programa educativo";
                    data = new GeneralData("RACT 1", sumaEntregados, sumaEsperados);
                }
                if (reporte.equalsIgnoreCase("entregadosdespueslimite")) {
                    tituloTabla = "Reporte general de Entregados despues de fecha límite por programa educativo";
                    data = new GeneralData("RACT 1", sumaEntregados, sumaEsperados);
                }
                generalDataList.add(data);
                data = null;
                sumaEntregados = 0;
                for (String entregados21 : entregados2) {
                    String[] entregado = entregados21.split("-");
                    sumaEntregados = sumaEntregados + Integer.parseInt(entregado[1]);
                }
                if (reporte.equals("noentregados")) {
                    data = new GeneralData("RACT 2", sumaEsperados - sumaEntregados, sumaEsperados);
                } else {
                    data = new GeneralData("RACT 2", sumaEntregados, sumaEsperados);
                }
                generalDataList.add(data);
                data = null;
                sumaEntregados = 0;
                for (String entregados31 : entregados3) {
                    String[] entregado = entregados31.split("-");
                    sumaEntregados = sumaEntregados + Integer.parseInt(entregado[1]);
                }
                if (reporte.equals("noentregados")) {
                    data = new GeneralData("RACT 3", sumaEsperados - sumaEntregados, sumaEsperados);
                } else {
                    data = new GeneralData("RACT 3", sumaEntregados, sumaEsperados);
                }
                generalDataList.add(data);
            }//aqui modifique Jesus Ruelas
            //</editor-fold>
        }
    }

    public void setGeneralDataList(List<GeneralData> generalDataList) {
        this.generalDataList = generalDataList;
    }

    public List<Areaadministrativa> getAreasAdministrativa() {
        areasTempList = new ArrayList<>();
        if (isCordinadorAreaAdmin && tieneAreaAsignada) {
            areasAdministrativa = new ArrayList();
            areasAdministrativa.add(loginBean.getLogueado().getProfesorList().get(0).getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid());
        } else {
            try {
                areasAdministrativa = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(Integer.parseInt(programaEducativoId)).getAreaadministrativaList();
                selectAreaAdministrativa.clear();
                selectAreaAdministrativa.add(areasAdministrativa.get(0));
                areasTempList = areasAdministrativa;
            } catch (Exception e) {
                areasAdministrativa = new ArrayList<>();
            }
        }
        return areasAdministrativa;
    }

    public void setAreasAdministrativa(Areaadministrativa areasAdmin) {
        this.areasAdministrativa = areasAdministrativa;
    }

    public int getIdCicloEscolar() {
        return idCicloEscolar;
    }

    public void setIdCicloEscolar(int idCicloEscolar) {
        this.idCicloEscolar = idCicloEscolar;
    }

    public FiltrosBeanHelper getFiltrosBeanHelper() {
        return filtrosBeanHelper;
    }

    public boolean isDisableBtnEjecutarGrafico() {
        if (CTRnombre.isEmpty()) {
            disableBtnEjecutarGrafico = false;
        } else {
            disableBtnEjecutarGrafico = true;
        }

        return disableBtnEjecutarGrafico;
    }

    //********************** Gráficas **************************
    public void setDisableBtnEjecutarGrafico(boolean disableBtnEjecutarGrafico) {
        this.disableBtnEjecutarGrafico = disableBtnEjecutarGrafico;
    }

    public boolean isHabilitarBtnReporteGrl() {
        if (ractSeleccionado3.isEmpty()) {
            habilitarBtnReporteGrl = false;
        } else {
            habilitarBtnReporteGrl = true;
        }

        return habilitarBtnReporteGrl;
    }

    public String getRactSeleccionado3() {
        return ractSeleccionado3;
    }

    public void setRactSeleccionado3(String ractSeleccionado3) {
        this.ractSeleccionado3 = ractSeleccionado3;
    }

    public void setHabilitarBtnReporteGrl(boolean habilitarBtnReporteGrl) {
        this.habilitarBtnReporteGrl = habilitarBtnReporteGrl;
    }

    public CartesianChartModel getBarModel() {
        return barModel;
    }

    public CartesianChartModel getLineModel() {
        return lineModel;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    private CartesianChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries entregados = new ChartSeries();
        entregados.setLabel("");
        entregados.set("", 0);
        this.title = "";
        model.addSeries(entregados);
        return model;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    private CartesianChartModel initLineModel() {
        CartesianChartModel model = new CartesianChartModel();
        LineChartSeries entregados = new LineChartSeries();
        entregados.setLabel("");
        entregados.set("", 0);
        this.title = "";
        model.addSeries(entregados);
        return model;
    }

    public void refrescarEntidades() {
        entidades = new ArrayList<>();
    }

    private PieChartModel initPieModel() {
        PieChartModel model = new PieChartModel();
        this.title = "Entregados";
        return model;
    }

    //Getters para los datos de la gráfica
    public String getTitle() {
        return title;
    }

    public String getXaxisLabel() {
        return xaxisLabel;
    }

    public String getYaxisLabel() {
        return yaxisLabel;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    //***************************** Métodos para Consultas Comunes ******************************//
    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public void tipoReporteComun() {
        System.out.println("Esta es la opcion: " + getOpcion());
    }

    public String getRact() {
        return ract;
    }

    public void setRact(String ract) {
        this.ract = ract;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public String getCriterio() {
        return criterio;
    }

    public boolean isGenerarExl() {
        return generarExl;
    }

    public void setGenerarExl(boolean generarExl) {
        this.generarExl = generarExl;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getTipoGrafico() {
        return tipografico;
    }

    public void setTipoGrafico(String tipografico) {
        this.tipografico = tipografico;
    }

    public List<Programaeducativo> getSelectProgramEducativo() {
        return selectProgramEducativo;
    }

    public void setSelectProgramEducativo(List<Programaeducativo> selectProgramEducativo) {
        this.selectProgramEducativo = selectProgramEducativo;
    }

    public List<Areaconocimiento> getSelectAreaConocimiento() {
        return selectAreaConocimiento;
    }

    public void setSelectAreaConocimiento(List<Areaconocimiento> selectAreaConocimiento) {
        this.selectAreaConocimiento = selectAreaConocimiento;
    }

    public List<Areaadministrativa> getSelectAreaAdministrativa() {
        return selectAreaAdministrativa;
    }

    public void setSelectAreaAdministrativa(List<Areaadministrativa> selectAreaAdministrativa) {
        this.selectAreaAdministrativa = selectAreaAdministrativa;
    }

    public List<Planestudio> getSelectPlanesEstudio() {
        return selectPlanesEstudio;
    }

    public void setSelectPlanesEstudio(List<Planestudio> selectPlanesEstudioL) {
        this.selectPlanesEstudio = selectPlanesEstudioL;
    }

    //selectUnidadAprendizaje
    public List<Unidadaprendizaje> getSelectUnidadAprendizaje() {
        return selectUnidadAprendizaje;
    }

    public void setSelectUnidadAprendizaje(List<Unidadaprendizaje> selectUnidadAprendizajeL) {
        this.selectUnidadAprendizaje = selectUnidadAprendizajeL;
    }

    //selectGrupo
    public List<String> getSelectGrupo() {
        return selectGrupo;
    }

    public void setSelectGrupo(List<String> selectGrupoL) {
        this.selectGrupo = selectGrupoL;
    }

    //selectProfesor
    public List<Profesor> getSelectProfesor() {
        return selectProfesor;
    }

    public void setSelectProfesor(List<Profesor> selectProfesorL) {
        this.selectProfesor = selectProfesorL;
    }

    //********************************* Metodos para listas actualizables *********************************
    public Unidadacademica getUnidadacademica() {
        return unidadacademica;
    }

    public void setUnidadacademica(Unidadacademica unidadacademica) {
        this.unidadacademica = unidadacademica;
    }

    public Programaeducativo getProgramaeducativo() {
        return programaeducativo;
    }

    public Planestudio getPlanestudio() {
        return planestudio;
    }

    public Areaconocimiento getAreaconocimiento() {
        return areaconocimiento;
    }

    public Unidadaprendizaje getUnidadaprendizaje() {
        return unidadaprendizaje;
    }

    public boolean getMostrarBar() {
        return mostrarBar;
    }

    public boolean getMostrarLine() {
        return mostrarLine;
    }

    public boolean getMostrarPie() {
        return mostrarPie;
    }

    public Cicloescolar getCicloescolar() {
        return cicloescolar;
    }

    public Cicloescolar setCicloescolar(Cicloescolar cicloescolar) {
        return this.cicloescolar = cicloescolar;
    }

    public List<String> getSelectCicloEscolar() {
        return selectCicloEscolarList;
    }

    public void setSelectCicloEscolar(List<String> selectCicloEscolarList) {
        this.selectCicloEscolarList = selectCicloEscolarList;
        System.out.println("Ciclo escolar en array");
        Ciclo.clear();
        for (int x = 0; x < selectCicloEscolarList.size(); x++) {
            Ciclo.add(selectCicloEscolarList.get(x));
            System.out.println(selectCicloEscolarList.get(x));
        }
    }

    //Metodo para traer la lista por filtro de Unidad Academica
    public List<Cicloescolar> getCiclosEscolares() {
        List<Cicloescolar> resultado = null;
        return resultado;
    }

    public List<Programaeducativo> getProgramasByUnidad() {
        if (unidadacademica.getUACid() != 0) {
            //Estos sets son para cuando ya esta seleccionada una opcion y se selecciona otra opcion diferente, las listas se actualicen correctamente sin inconsistencias
            programaeducativo.setPEDid(0);
            planestudio.setPESid(0);
            areaconocimiento.setACOid(0);
            unidadaprendizaje.setUAPid(0);
            return unidadacademica.getProgramaeducativoList();
        } else {
            //Se utiliza este Set para que la lista no se quede con datos seleccionados
            //al cambiar el filtro anterior.
            programaeducativo.setPEDid(0);
            return new ArrayList<>();
        }
    }

    public List<Planestudio> getPlanesByProgramaClave() {
        if (programaeducativo.getPEDclave() != 0) {
            areaconocimiento.setACOid(0);
            unidadaprendizaje.setUAPid(0);
            return programaeducativo.getPlanestudioList();
        } else {
            planestudio.setPESid(0);
            return new ArrayList<>();
        }
    }

    public List<Areaconocimiento> getAreasByPlanesEstudio() {
        List<Areaconocimiento> areasTempList;
        areasTempList = new ArrayList<>();

        //Modificacion en la busqueda de planes de estudio Francisco Reyes
        for (String planestudio : selectPlanesEstudioUniForm) {
            areasTempList.addAll(filtrosBeanHelper.getPlanEstudio(Integer.parseInt(planestudio)).getAreaconocimientoList());
        }

        return areasTempList;
    }

    public List<Areaconocimiento> getAreasByProfesor() {
        List<Areaconocimiento> areasTempList;
        areasTempList = new ArrayList<>();
        List<Areaconocimiento> areasTempList2 = new ArrayList<>();

        if (selectPlanesEstudio != null) {
            if (selectPlanesEstudio.size() > 0 && selectProfesor.size() > 0) {

                for (Profesor profesor : selectProfesor) {
                    for (UnidadaprendizajeImparteProfesor unidadAprendizajeIm : profesor.getUnidadaprendizajeImparteProfesorList()) {
                        areasTempList.addAll(unidadAprendizajeIm.getUnidadAprendizajeUAPid().getAreaconocimientoList());
                    }
                }

                for (Areaconocimiento areaConocimiento : areasTempList) {
                    for (Planestudio planEstudio : selectPlanesEstudio) {
                        if (areaConocimiento.getPlanEstudioPESid().getPESid().equals(planEstudio.getPESid())) {
                            areasTempList2.add(areaConocimiento);
                        }
                    }
                }
            }
        }

        List<Areaconocimiento> result = new ArrayList<>();
        Set<Integer> areas = new HashSet<>();

        for (Areaconocimiento areaconocimiento1 : areasTempList2) {
            if (areas.add(areaconocimiento1.getACOid())) {
                result.add(areaconocimiento1);
            }
        }

        return result;
    }

    //Saque esta propiedad del metodo para poder utilizarla despues para obtener nombres
    List<Areaadministrativa> areasTempList;

    public List<Unidadaprendizaje> getUnidadesByAreaAconocimiento() {
        List<Unidadaprendizaje> unidadesTempList;
        unidadesTempList = new ArrayList<>();

        if (selectAreaConocimiento != null) {
            if (selectAreaConocimiento.size() > 0) {
                System.out.println("Mas de 0");
                for (Areaconocimiento UnidadIDstr : selectAreaConocimiento) {
                    System.out.println(UnidadIDstr);
                    unidadesTempList.addAll(filtrosBeanHelper.getUnidadByAreaCons(UnidadIDstr.getACOid()));
                }
            } else {
                System.out.println("Vacio");
            }
        } else {
            System.out.println("Nullo");
        }

        return unidadesTempList;
    }

    public List<Unidadaprendizaje> getUnidadesByArea() {
        List<Unidadaprendizaje> unidadesTempList;
        unidadesTempList = new ArrayList<>();

        for (Areaconocimiento areaConocimiento : selectAreaConocimiento) {
            unidadesTempList.addAll(areaConocimiento.getUnidadaprendizajeList());
        }
        return unidadesTempList;
    }

    public List<Unidadaprendizaje> getUnidadesByAreaUniForm() {
        List<Unidadaprendizaje> unidadesTempList = new ArrayList();
        if (isCordinadorAreaAdmin && tieneAreaAsignada) {

            unidadesTempList = loginBean.getLogueado().getProfesorList().get(0).getCoordinadorareaadministrativaList().get(0).getUnidadaprendizajeList();
        } else {

            unidadesTempList = new ArrayList<>();
            Areaconocimiento ac;
            for (String areaConocimiento : selectAreaConocimientoUniForm) {
                ac = filtrosBeanHelper.getAreaConocimiento(Integer.parseInt(areaConocimiento));
                unidadesTempList.addAll(ac.getUnidadaprendizajeList());
            }
        }

        return unidadesTempList;
    }

    List<String> ac = new ArrayList<>();

    public List<String> getAc() {
        return ac;
    }

    public void setAc(List<String> ac) {
        this.ac = ac;
    }

    public List<Unidadaprendizaje> getUnidadesByAreaUniFormObj() {
        List<Unidadaprendizaje> unidadesTempList;
        unidadesTempList = new ArrayList<>();
        List<Areaconocimiento> areas = ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();

        for (String areaConocimiento : ac) {
            for (Areaconocimiento aC : areas) {
                if (aC.getACOid() == Integer.parseInt(areaConocimiento)) {
                    unidadesTempList.addAll(aC.getUnidadaprendizajeList());
                }
            }
        }

        return unidadesTempList;
    }

    public List<Unidadaprendizaje> getUnidadesByAreaAconocimientoProf() {
        List<Unidadaprendizaje> unidadAprendizaje = new ArrayList<>();
        List<Unidadaprendizaje> unidadesTempList;
        unidadesTempList = new ArrayList<>();

        if (selectAreaConocimiento != null) {
            if (selectAreaConocimiento.size() > 0) {
                System.out.println("Mas de 0");
                for (Areaconocimiento acoIdStr : selectAreaConocimiento) {
                    unidadAprendizaje.addAll(acoIdStr.getUnidadaprendizajeList());//se trae las unidades de aprendizaje de nuestras areas de conocimiento
                }
            } else {
                System.out.println("Vacio");
            }
        } else {
            System.out.println("Nullo");
        }

        return distinctUnidades(unidadesTempList);
    }

    public List<Grupo> getGruposByUnidadAprendizajes() {
        List<Grupo> gruposTempList;
        gruposTempList = new ArrayList<>();
        List<Grupo> gruposTempListAux;
        gruposTempListAux = new ArrayList<>();

        if (selectUnidadAprendizajeUniForm != null && selectAreaConocimientoUniForm != null) {
            if (selectUnidadAprendizajeUniForm.size() > 0) {
                for (String unidadAprendizaje : selectUnidadAprendizajeUniForm) {
                    for (UnidadaprendizajeImparteProfesor unidadAprendizajeImProf : filtrosBeanHelper.getUnidadAprendizaje(Integer.parseInt(unidadAprendizaje)).getUnidadaprendizajeImparteProfesorList()) {
                        gruposTempListAux.add(unidadAprendizajeImProf.getGrupoGPOid());
                    }
                }

                int grupoNum = 0;
                int enarray = 0;
                for (Grupo grup : gruposTempListAux) {

                    //primero vemos si esta dentro del arrayTemporal
                    for (Grupo grupt : gruposTempList) {
                        if (grupt.getGPOnumero() == grup.getGPOnumero()) {
                            enarray = 1;
                        }
                    }

                    // si no esta en el array
                    if (enarray == 0) {
                        gruposTempList.add(grup);
                    }
                    enarray = 0;
                }

            } else {
                System.out.println("Vacio");
            }
        } else {
            System.out.println("Nullo");
        }
        return gruposTempList;
    }

    public void setProfesorTempList(Programaeducativo programaeducativo) {

    }

    public void habilitarBotonEjecutar() {
        if (!CTRnombre.isEmpty()) {
            disableBtnEjecutarGrafico = true;
        } else {
            disableBtnEjecutarGrafico = false;
        }

    }

    /**
     * Metodo para validar el boton Generar Reporte
     *
     * @return
     */
    public boolean habilitarBotonGenerarReporte() {
        int band = 0;
        boolean activado;

        // if (ract.length() == 0 || reporte.length() == 0 || selectCicloEscolarList.size() == 0 || criterio.length() == 0) {
        if (ract.length() == 0 || reporte.length() == 0 || idCicloEscolar == 0 || criterio.length() == 0) {
            band++;
        } else {
            if (criterio.equalsIgnoreCase("programa_educativo")) {
                if (selectProgramaEducativoString.isEmpty()) {
                    band++;
                }
            }
            if (criterio.equalsIgnoreCase("area_conocimiento")) {
                if (programaEducativoId.isEmpty() || selectPlanesEstudioUniForm.size() == 0 || selectAreaConocimientoUniForm.size() == 0) {
                    band++;
                }
            }
            if (criterio.equalsIgnoreCase("area_administrativa")) {
                if (programaEducativoId.isEmpty() || selectAreaAdminString.isEmpty()) {
                    band++;
                }
            }

            if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
                if (isCordinadorAreaAdmin) {
                    if (programaEducativoId.isEmpty() || selectPlanesEstudioUniForm.size() == 0
                            || selectUnidadAprendizajeUniForm.size() == 0) {
                        band++;
                    }
                } else {
                    if (programaEducativoId.isEmpty() || selectPlanesEstudioUniForm.size() == 0 || selectAreaConocimientoUniForm.size() == 0
                            || selectUnidadAprendizajeUniForm.size() == 0) {
                        band++;
                    }
                }

            }
            if (criterio.equalsIgnoreCase("profesor")) {
                if (programaEducativoId.isEmpty() || selectProfesorString.size() == 0) {
                    band++;
                }
            }
        }

        if (band == 0) {
            activado = false;
        } else {
            activado = true;
        }
        return activado;
    }

    public void refrescarVariables() {
        selectProgramaEducativoString = new ArrayList<>();
        unidadAcademicaId = "";
        programaEducativoId = "";
        selectPlanesEstudioUniForm = new ArrayList<>();
        selectAreaConocimientoUniForm = new ArrayList<>();
        selectAreaAdminString = new ArrayList<>();
        selectUnidadAprendizajeUniForm = new ArrayList<>();
        selectGrupoUniForm = new ArrayList<>();

        selectProfesorString = new ArrayList<>();
        ac = new ArrayList<>();
    }

    public boolean disableBtnGenerarGrafico() {
        boolean disable = true;

        if (!(selectCicloEscolarList.isEmpty())) {
            System.out.println("ciclo escolar list no esta vacia");
        }

        if (unidadacademica.getUACclave() != 0) {
            System.out.println("unidad academica clave es diferente de cero");
        }

        if (!(ract.equalsIgnoreCase("Selecciona"))) {
            System.out.println("ract es igual a: " + ract);
        }

        if (programaeducativo.getPEDclave() != 0) {
            System.out.println("clave Pe: " + programaeducativo.getPEDclave());
        }

        if (((!selectProgramEducativo.isEmpty()) || (programaeducativo.getPEDclave() != 0)) && (!(selectCicloEscolarList.isEmpty())) && unidadacademica.getUACclave() != 0 && (!(ract.equalsIgnoreCase("Selecciona")))) {
            if (!(reporte == null)) {
                if ((!reporte.equalsIgnoreCase("")) && ((!selectProgramEducativo.isEmpty()) && ((!(selectCicloEscolarList.isEmpty())) && unidadacademica.getUACclave() != 0) && (!(ract.equalsIgnoreCase("Selecciona"))))) {
                    disable = false;
                }
            }

            if (!(reporte == null)) {
                if ((!reporte.equalsIgnoreCase("")) && (programaeducativo.getPEDclave() != 0) && (!(selectCicloEscolarList.isEmpty())) && (!(selectPlanesEstudio.isEmpty())) && (!(selectAreaConocimiento.isEmpty()))) {
                    if ((criterio.equalsIgnoreCase("area_conocimiento"))) {
                        disable = false;
                    }
                }
            }

            if (!(reporte == null)) {
                if ((!reporte.equalsIgnoreCase("")) && (programaeducativo.getPEDclave() != 0) && (!(selectCicloEscolarList.isEmpty())) && (!(selectPlanesEstudioAdmva.isEmpty())) && (!(selectAreaAdministrativa.isEmpty()))) {
                    //if((criterio.equalsIgnoreCase("area_conocimiento"))){
                    disable = false;
                    //}
                }
            }

            if (!(reporte == null)) {
                if ((!reporte.equalsIgnoreCase("")) && (programaeducativo.getPEDclave() != 0) && (!(selectCicloEscolarList.isEmpty())) && (!(selectPlanesEstudioUniForm.isEmpty())) && (!(selectAreaConocimientoUniForm.isEmpty()))) {
                    //if((criterio.equalsIgnoreCase("unidad_aprendizaje"))){
                    disable = false;
                    //}
                }
            }

            if (!(reporte == null)) {
                if (criterio.equals("programa_educativo") && !selectProgramEducativo.isEmpty()) {
                    //if((criterio.equalsIgnoreCase("unidad_aprendizaje"))){
                    disable = false;
                    //}
                }
            }
            if (reporte != null && !isDirectorOrAdmin()) {
                if (criterio.equals("programa_educativo")) {
                    disable = false;
                }
            }

            if (reporte != null && !isDirectorOrAdmin()) {
                if (criterio.equals("area_conocimiento") && !selectAreaConocimiento.isEmpty()) {
                    disable = false;
                }
            }
            if (reporte != null) {
                if (criterio.equals("profesor") && !selectGrupo.isEmpty()) {
                    disable = false;
                }
            }

            return disable;
        }
        return disable;
    }

    public void arreglarRactUnico() {

        ArrayList<ReporteAvanceAux> listaAux5 = new ArrayList<>();

        if (numRact > 0 && numRact < 4 && (!(isPAGCTodosRacts))) {
            for (ReporteAvanceAux report : listaAux) {
                if (numRact == 1) {
                    report.fechaElaboracRact1 = report.getReporteAvance().getRACfechaElaboracion();
                    report.porcentAvanceRact1 = report.getReporteAvance().getRACavanceGlobal();
                    report.fechaLimiteRact1 = report.fechaLimite;
                    report.statusRact1 = report.getReporteAvance().getRACstatus();
                    //report.tipoReporteSelecRact1=;
                }
                if (numRact == 2) {
                    report.fechaElaboracRact2 = report.getReporteAvance().getRACfechaElaboracion();
                    report.porcentAvanceRact2 = report.getReporteAvance().getRACavanceGlobal();
                    report.fechaLimiteRact2 = report.fechaLimite;
                    report.statusRact2 = report.getReporteAvance().getRACstatus();
                    //report.tipoReporteSelecRact2=;
                }
                if (numRact == 3) {
                    report.fechaElaboracRact3 = report.getReporteAvance().getRACfechaElaboracion();
                    report.porcentAvanceRact3 = report.getReporteAvance().getRACavanceGlobal();
                    report.fechaLimiteRact3 = report.fechaLimite;
                    report.statusRact3 = report.getReporteAvance().getRACstatus();
                    //report.tipoReporteSelecRact3=;
                }
            }

        }

    }

    public void generarDatosListaAux() {
        // se usa un select y podemos definir de cual id del arreglo
        this.setNumRact(Integer.parseInt(ract));
        if (!(selectCicloEscolarList.isEmpty())) {
            this.setCescicloEscolar(selectCicloEscolarList.get(0));
        }
        if (!(selectAreaConocimiento.isEmpty())) {
            this.setAconClave(selectAreaConocimiento.get(0).getACOclave());
        }
        if (!(selectAreaAdministrativa.isEmpty())) {
            this.setAadid(selectAreaAdministrativa.get(0).getAADid());
        }
        if (!(selectProgramEducativo.isEmpty())) {
            this.setClavepe(selectProgramEducativo.get(0).getPEDclave());
        }
        if (programaeducativo.getPEDclave() != 0) {
            this.setClavepe(programaeducativo.getPEDclave());
        }
        if (!(selectPlanesEstudio.isEmpty())) {
            this.setPesvigenciaR(selectPlanesEstudio.get(0).getPESvigenciaPlan());
        }
        if (!selectAreaConocimiento.isEmpty()) {
            this.setAconClave(selectAreaConocimiento.get(0).getACOclave());
        }
        if (!(selectGrupo.isEmpty())) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!! GRUPOS > 1");
            this.setGrupoNumero(selectGrupo.get(0));
        }
        if (!(selectUnidadAprendizaje.isEmpty())) {
            this.setUapclaveR(Integer.toString(selectUnidadAprendizaje.get(0).getUAPclave()));
        }
        if (!(selectProfesor.isEmpty())) {
            this.setNumeroEmpleado(Integer.toString(selectProfesor.get(0).getPROnumeroEmpleado()));
        }
        botonCancelar = false;
    }

    public void enableBotonCancelar() {
        botonCancelar = true;
        reporte = "";
        selectProgramEducativo.clear();
        selectCicloEscolarList.clear();
        ract = "Selecciona";
        programaeducativo.setPEDclave(0);
        selectPlanesEstudio.clear();
        selectAreaConocimiento.clear();
        selectUnidadAprendizaje.clear();
        selectGrupo.clear();
        selectProfesor.clear();
        criterio = " ";
    }

    public void limpiarFiltrosByCriterio() {
        programaeducativo.setPEDclave(0);
        selectProgramEducativo.clear();
        selectPlanesEstudioAdmva.clear();
        selectPlanesEstudio.clear();
        selectAreaConocimiento.clear();
        selectAreaConocimientoUniForm.clear();
        selectUnidadAprendizaje.clear();
        selectUnidadAprendizajeUniForm.clear();
        selectProfesor.clear();
        selectGrupo.clear();
        selectGrupoUniForm.clear();

    }

    public void limpiarFiltrosByProgEd() {
        selectPlanesEstudio.clear();
        selectAreaConocimiento.clear();
        selectUnidadAprendizaje.clear();
        selectProfesor.clear();
        selectGrupo.clear();
    }

    /*meotodos para el reporte grafico visual :D*/
    //Variables donde se resguardan los valores despues de actualizar la pagina
    private List<String> programasEducativosSeleccionados;
    private List<Unidadaprendizaje> unidadesAprendizajeSeleccionadas;
    private List<String> gruposSeleccionados;
    private List<String> ciclosEscolaresSeleccionados;

    /**
     * Metodo para generar la grafica de barras y la tabla de resumen en la
     * interfaz para el criterio de programa educativo
     */
    public void generarGraficoBarrasPorPE() {
        int entregadosPE;
        int esperadosPE;
        // Limpio la lista de resumenes
        resumenesReporte = new ArrayList();

        for (String programa : selectProgramaEducativoString) {
            Programaeducativo PE = filtrosBeanHelper.getProgramaeducativo(Integer.parseInt(programa));

            // Recorro los planes de estudio del programa
            for (Planestudio planE : PE.getPlanestudioList()) {
                // *** si tiene grupos en el ciclo que se busca agrego una linea
                // ** con la informacion
                if (ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().countUaipPorAreaConocimientoYcicloEscolar(planE.getPESid(), idCicloEscolar) != 0) {
                    List<Planestudio> plan = new ArrayList<>();
                    plan.add(planE);

                    esperadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados("esperados", idCicloEscolar + "", ract, plan);
                    entregadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(reporte, idCicloEscolar + "", ract, plan);

                    if (ract.equalsIgnoreCase("4") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Incompleto") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto")) {
                        esperadosPE = esperadosPE * 3;
                    }

                    ResumenReporte resumenReporte = new ResumenReporte();
                    // para la tabla que se muestra en la interfaz ocupo los siguientes datos
                    resumenReporte.setNombreProgramaEducativo(PE.getPEDnombre());
                    resumenReporte.setNombreSegunCriterio(PE.getPEDnombre());
                    resumenReporte.setPlanEstudio(planE.getPESvigenciaPlan());
                    resumenReporte.setNumEsperados(esperadosPE);
                    resumenReporte.setNumEntregados(entregadosPE);

                    resumenReporte.setPorcentaje(resumenReporte.calcularPorcentaje());
                    resumenesReporte.add(resumenReporte);
                }
            }
        }

        // mando a genrar la grafica
        tituloGraficas = "Gráfica de Barras";
        mostrarBar = true;
        mostrarPie = false;
        mostrarGauge = false;
        // este metodo genera la grafica y mando las entidadesSemaforo generadas
        barModel = graficoDeacuerdoFiltrosBarras(resumenesReporte);
        lineModel = ClearCharts(1);

    }

    public void generarGraficoBarrasPorAC() {
        int entregados = 0;
        int esperados = 0;
        resumenesReporte = new ArrayList();
        for (String areaCon : selectAreaConocimientoUniForm) {
            Areaconocimiento areaconocimiento = new Areaconocimiento();
            areaconocimiento = filtrosBeanHelper.getAreaConocimiento(Integer.parseInt(areaCon));
            //*** recorro los planes de estudio del programa
            Planestudio planE = areaconocimiento.getPlanEstudioPESid();

            /**
             * En este hago una lista de entidades semaforo para poder llenar el
             * dataTable de resumen porque sus atributos encajan con lo que
             * ocupo, en caso de hacer otro filtro se puede agregar atributos a
             * esta entidad o crear otra custom entity, que seria lo mas ideal.
             */
            esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorAreaConocimiento(Integer.parseInt(programaEducativoId), areaconocimiento.getACOid(), idCicloEscolar);//Sistemas = 18
            entregados = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorAreaConocimiento(reporte, Integer.parseInt(programaEducativoId), ract, idCicloEscolar, esperados, Integer.parseInt(areaCon));

            if (ract.equalsIgnoreCase("4")) {
                esperados = esperados * 3;
            }

            ResumenReporte resumenReporte = new ResumenReporte();
            // para la tabla que se muestra en la interfaz ocupo los siguientes datos
            resumenReporte.setIdAreaConociminto(areaconocimiento.getACOid());
            resumenReporte.setNombreAreaConocimiento(areaconocimiento.getACOnombre());
            resumenReporte.setNombreSegunCriterio(areaconocimiento.getACOnombre());
            resumenReporte.setPlanEstudio(planE.getPESvigenciaPlan());
            resumenReporte.setNumEsperados(esperados);
            resumenReporte.setNumEntregados(entregados);
            resumenReporte.calcular();

            resumenesReporte.add(resumenReporte);
        }

        // mando a genrar la grafica
        tituloGraficas = "Gráfica de Barras";
        mostrarBar = true;
        mostrarPie = false;
        mostrarGauge = false;
        // este metodo genera la grafica y mando las entidadesSemaforo generadas
        barModel = graficoDeacuerdoFiltrosBarras(resumenesReporte);
        lineModel = ClearCharts(1);
    }

    /**
     * Metodo para generar la grafica y tabla resumen por area administrativa
     */
    public void generarGraficoBarrasPorAA() {
        int entregados = 0;
        int esperados = 0;
        resumenesReporte = new ArrayList();
        for (String idAreaAdmin : selectAreaAdminString) {
            Areaadministrativa areaAdministrativa = new Areaadministrativa();
            areaAdministrativa = filtrosBeanHelper.getAreaAdministrativa(Integer.parseInt(idAreaAdmin));
            esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorAreaAdministrativa(Integer.parseInt(programaEducativoId), Integer.parseInt(idAreaAdmin), idCicloEscolar);//Sistemas = 18
            entregados = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorAreaAdministrativa(reporte, Integer.parseInt(programaEducativoId), ract, idCicloEscolar, esperados, Integer.parseInt(idAreaAdmin));

            if (ract.equalsIgnoreCase("4") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global incompleto") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo")) {
                esperados = esperados * 3;
                System.out.println(esperados);
            }

            ResumenReporte resumenReporte = new ResumenReporte();
            // para la tabla que se muestra en la interfaz ocupo los siguientes datos
            resumenReporte.setIdAreaAdministrativa(areaAdministrativa.getAADid());
            resumenReporte.setNombreAreaAdministrativa(areaAdministrativa.getAADnombre());
            resumenReporte.setNombreSegunCriterio(areaAdministrativa.getAADnombre());
            resumenReporte.setNumEsperados(esperados);
            resumenReporte.setNumEntregados(entregados);
            resumenReporte.calcular();

            resumenesReporte.add(resumenReporte);
        }

        // mando a genrar la grafica
        tituloGraficas = "Gráfica de Barras";
        mostrarBar = true;
        mostrarPie = false;
        mostrarGauge = false;
        // este metodo genera la grafica y mando las entidadesSemaforo generadas
        barModel = graficoDeacuerdoFiltrosBarras(resumenesReporte);
        lineModel = ClearCharts(1);

    }

    public void generarGraficoBarrasPorProfesor() {
        int entregados = 0;
        int esperados = 0;
        resumenesReporte = new ArrayList();

        for (String idProfesor : selectProfesorString) {
            Profesor profesor = new Profesor();
            profesor = filtrosBeanHelper.getProfesor(Integer.parseInt(idProfesor));

            if (isCordinadorAreaAdmin) {
                esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProfesorCoordinador(Integer.parseInt(idProfesor), idCicloEscolar, Integer.parseInt(programaEducativoId), idProfesorCoordinador);//Sistemas = 18
                entregados = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorProfesorCoordinador(reporte, Integer.parseInt(programaEducativoId), ract, idCicloEscolar, esperados, idProfesorCoordinador, Integer.parseInt(idProfesor));
            } else {
                esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProfesor(Integer.parseInt(idProfesor), idCicloEscolar, Integer.parseInt(programaEducativoId), isAdmin);//Sistemas = 18
                entregados = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().ConteoPorProfesor(reporte, Integer.parseInt(programaEducativoId), ract, idCicloEscolar, esperados, isAdmin, Integer.parseInt(idProfesor));
            }

            if (ract.equalsIgnoreCase("4")) {
                esperados = esperados * 3;
            }

            ResumenReporte resumenReporte = new ResumenReporte();
            // para la tabla que se muestra en la interfaz ocupo los siguientes datos
            resumenReporte.setIdProfesor(profesor.getPROid());
            resumenReporte.setNombreProfesor(profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno());
            resumenReporte.setNombreSegunCriterio(profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno());
            resumenReporte.setNumEsperados(esperados);
            resumenReporte.setNumEntregados(entregados);
            resumenReporte.calcular();

            resumenesReporte.add(resumenReporte);
        }

        // mando a genrar la grafica
        tituloGraficas = "Gráfica de Barras";
        mostrarBar = true;
        mostrarPie = false;
        mostrarGauge = false;
        // este metodo genera la grafica y mando las entidadesSemaforo generadas
        barModel = graficoDeacuerdoFiltrosBarras(resumenesReporte);
        lineModel = ClearCharts(1);

    }

    /**
     * Metodo que Genera grafica de barras por Unidad de Aprendizaje metodos
     * temporales a eliminar
     */
    public void generarGraficoBarrasPorUA() {
        int entregados = 0;
        int esperados = 0;
        resumenesReporte = new ArrayList();
        selectUnidadAprendizaje.clear();

        for (String idUa : selectUnidadAprendizajeUniForm) {
            Unidadaprendizaje unidadAprendizaje = new Unidadaprendizaje();
            unidadAprendizaje = filtrosBeanHelper.getUnidadAprendizaje(Integer.parseInt(idUa));
            esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorUnidadAprendizaje(unidadAprendizaje.getUAPid(), Integer.parseInt(programaEducativoId), idCicloEscolar);//Sistemas = 18
            entregados = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorUnidadAprendizaje(reporte, Integer.parseInt(programaEducativoId), ract, idCicloEscolar, esperados, Integer.parseInt(idUa));

            if (ract.equalsIgnoreCase("4")) {
                esperados = esperados * 3;
            }

            ResumenReporte resumenReporte = new ResumenReporte();
            // para la tabla que se muestra en la interfaz ocupo los siguientes datos
            resumenReporte.setIdUnidadAprendizaje(unidadAprendizaje.getUAPid());
            resumenReporte.setNombreUnidadAprendizaje(unidadAprendizaje.getUAPnombre());
            resumenReporte.setNombreSegunCriterio(unidadAprendizaje.getUAPnombre());
            resumenReporte.setNumEsperados(esperados);
            resumenReporte.setNumEntregados(entregados);
            resumenReporte.calcular();
            resumenesReporte.add(resumenReporte);
        }

        // mando a genrar la grafica
        tituloGraficas = "Gráfica de Barras";
        mostrarBar = true;
        mostrarPie = false;
        mostrarGauge = false;
        // este metodo genera la grafica y mando las entidadesSemaforo generadas
        barModel = graficoDeacuerdoFiltrosBarras(resumenesReporte);
        lineModel = ClearCharts(1);
    }

    /**
     * Este metodo se ejecuta cuando se genera el reporte segun los filtros
     */
    public void generarGrafico() {
        renderTablaResumen = true;
        renderPlanResumen = false;
        entidades = new ArrayList();
        headerPorcentaje = "% Entregado";
        headerEsperados = "Esperados";

        if (reporte.equalsIgnoreCase("noentregados")) {
            headerTabla = "Total no entregados";
            headerTipoReporte = "No entregados";
            headerPorcentaje = "% No entregado";
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            headerTabla = "Total entregados y parciales";
            headerTipoReporte = "Entregados";
        } else if (reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo")) {
            if (ract.equalsIgnoreCase("4")) {
                //Si son todos los RACT se agrega Esperados por periodo
                headerEsperados = "Esperados por periodo";
            }

            headerTabla = "Total con porcentaje de avance global completo";
            headerTipoReporte = "Entregados con porcentaje de avance global completo";
        } else if (reporte.equalsIgnoreCase("Porcentaje de Avance Global incompleto")) {
            if (ract.equalsIgnoreCase("4")) {
                headerEsperados = "Esperados por periodo";
            }

            headerTabla = "Total con porcentaje de avance global incompleto";
            headerTipoReporte = "Entregados con porcentaje de avance global incompleto";
        } else if (reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto")) {
            if (ract.equalsIgnoreCase("4")) {
                headerEsperados = "Esperados por periodo";
            }

            headerTipoReporte = "Entregados";
        } else {
            headerTabla = "Total entregados";
            headerTipoReporte = "Entregados";
        }

        // desplegara la tabla detalle segun sea el caso del ract
        switch (ract) {
            case "1":
                renderDR1 = true;
                renderDR2 = false;
                renderDR3 = false;
                break;
            case "2":
                renderDR1 = false;
                renderDR2 = true;
                renderDR3 = false;
                break;
            case "3":
                renderDR1 = false;
                renderDR2 = false;
                renderDR3 = true;
                break;
            case "4":
                renderDR1 = true;
                renderDR2 = true;
                renderDR3 = true;
                break;
        }

        // cada filtro debe tener si propio metodo para generar la grafica
        if (criterio.equalsIgnoreCase("programa_educativo")) {
            headerCriterio = "Progama educativo";
            generarGraficoBarrasPorPE();
            renderPlanResumen = true;
            generarDetallePorPE();
            setGenerarExl(false);
        }
        if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
            headerCriterio = "Unidad de aprendizaje";
            generarGraficoBarrasPorUA();
            generarDetallePorUA();
            setGenerarExl(false);
        }

        if (criterio.equalsIgnoreCase("area_conocimiento")) {
            headerCriterio = "Área de conocimiento";
            generarGraficoBarrasPorAC();
            generarDetallePorAC();
            setGenerarExl(false);

        }
        if (criterio.equalsIgnoreCase("area_administrativa")) {
            headerCriterio = "Área administrativa";
            generarGraficoBarrasPorAA();
            generarDetallePorAA();
            setGenerarExl(false);
        }
        if (criterio.equalsIgnoreCase("profesor")) {
            headerCriterio = "Profesor";
            generarGraficoBarrasPorProfesor();
            generarDetallePorProfesor();
            setGenerarExl(false);
        }

    }

    public void crearSemaforo() {
        mostrarBar = false;
        mostrarLine = false;
        mostrarPie = false;
        mostrarGauge = true;
        gaugeModel = createMeterGaugeModels();
    }

    public boolean getMostrarGauge() {
        return mostrarGauge;
    }

    private CartesianChartModel ClearCharts(int op) {
        CartesianChartModel model = new CartesianChartModel();

        if (op == 1) {
            LineChartSeries entregados = new LineChartSeries();
            entregados.setLabel("");
            entregados.set("", 0);
            this.title = "";
            model.addSeries(entregados);
        }

        if (op == 2) {
            ChartSeries entregados = new ChartSeries();
            entregados.setLabel("");
            entregados.set("", 0);
            this.title = "";
            model.addSeries(entregados);
        }

        return model;
    }

    public void generarDetallePorPE() {
        tabs = new ArrayList();

        for (String r : selectProgramaEducativoString) {
            Programaeducativo PE = filtrosBeanHelper.getProgramaeducativo(Integer.parseInt(r));

            for (Planestudio plan : PE.getPlanestudioList()) {
                Tab tab = new Tab();
                List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
                uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorPlanEstudio(reporte, plan.getPESid(), idCicloEscolar, ract);

                if (uaips.size() > 0) {
                    tab.setTitle(plan.getPESvigenciaPlan() + " " + PE.getPEDnombre());
                    tab.setDetalleReporte(llenarDetalleReporte(uaips, Integer.parseInt(programaEducativoId)));
                    tabs.add(tab);
                }
            }
        }
    }

    public void generarDetallePorAC() {
        tabs = new ArrayList();

        for (ResumenReporte r : resumenesReporte) {
            Tab tab = new Tab();
            Areaconocimiento area = filtrosBeanHelper.getAreaConocimiento(r.getIdAreaConociminto());
            tab.setTitle(r.getNombreAreaConocimiento());
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorAreaConocimiento(reporte, area.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid(), idCicloEscolar, area.getACOid(), ract, area.getPlanEstudioPESid().getPESid());
            tab.setDetalleReporte(llenarDetalleReporte(uaips, Integer.parseInt(programaEducativoId)));
            tabs.add(tab);
        }
    }

    public void generarDetallePorAA() {
        tabs = new ArrayList();

        for (ResumenReporte r : resumenesReporte) {
            Tab tab = new Tab();
            Areaadministrativa area = filtrosBeanHelper.getAreaAdministrativa(r.getIdAreaAdministrativa());
            tab.setTitle(r.getNombreAreaAdministrativa());
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorAreaAdministrativa(reporte, Integer.parseInt(programaEducativoId), area.getAADid(), idCicloEscolar, ract);
            tab.setDetalleReporte(llenarDetalleReporte(uaips, Integer.parseInt(programaEducativoId)));
            tabs.add(tab);
        }
    }

    public void generarDetallePorUA() {
        tabs = new ArrayList();

        for (ResumenReporte r : resumenesReporte) {
            Tab tab = new Tab();
            Unidadaprendizaje unidad = filtrosBeanHelper.getUnidadAprendizaje(r.getIdUnidadAprendizaje());
            tab.setTitle(r.getNombreUnidadAprendizaje());
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorUnidadAprendizaje(reporte, Integer.parseInt(programaEducativoId), unidad.getUAPid(), idCicloEscolar, ract);
            tab.setDetalleReporte(llenarDetalleReporte(uaips, Integer.parseInt(programaEducativoId)));
            tabs.add(tab);
        }
    }

    public void generarDetallePorProfesor() {
        tabs = new ArrayList();

        for (ResumenReporte r : resumenesReporte) {
            Tab tab = new Tab();
            Profesor profesor = filtrosBeanHelper.getProfesor(r.getIdProfesor());
            tab.setTitle(r.getNombreProfesor());
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();

            if (isCordinadorAreaAdmin) {
                uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorProfesorCoordinador(reporte, Integer.parseInt(programaEducativoId), profesor.getPROid(), idCicloEscolar, ract, idProfesorCoordinador);
            } else {
                uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorProfesor(reporte, Integer.parseInt(programaEducativoId), profesor.getPROid(), idCicloEscolar, ract, isAdmin);
            }

            tab.setDetalleReporte(llenarDetalleReporte(uaips, Integer.parseInt(programaEducativoId)));
            tabs.add(tab);
        }
    }

    /**
     * Metodo para generar grafica de barra por filtro
     *
     * @param resumenesReporte
     * @return
     */
    private CartesianChartModel graficoDeacuerdoFiltrosBarras(List<ResumenReporte> resumenesReporte) {
        BarChartModel model = new BarChartModel();
        ChartSeries entregados = new ChartSeries();
        ChartSeries resultados = new ChartSeries();
        ChartSeries noentregados = new ChartSeries();
        ChartSeries esperados = new ChartSeries();
        Axis xAxis = model.getAxis(AxisType.X), yAxis = model.getAxis(AxisType.Y);

        yAxis.setLabel("Cantidad");
        xAxis.setTickAngle(-15);
        int auxInt = 0;
        int auxInt2 = 0;
        //Etiquetas
        esperados.setLabel("Esperados");

        if (reporte.compareTo("entregados") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados";
            this.title = "Entregados";
            entregados.setLabel("Entregados");
        }

        if (reporte.compareTo("noentregados") == 0) {
            tituloGraficas = tituloGraficas + " de No entregados";
            this.title = "No entregados";
            entregados.setLabel("No entregados");
        }

        if (reporte.compareTo("entregadosynoentregados") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados ";
            this.title = "Entregados";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        if (reporte.compareTo("entregadosatiempo") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados a tiempo";
            this.title = "Entregados a tiempo";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        if (reporte.compareTo("entregadosdespueslimite") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados despues de fecha limite";
            this.title = "Entregados despues de fecha limite";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        if (reporte.compareTo("entregadosatiempoenfechalimiteydespues") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados antes y despues de fecha limite";
            this.title = "Entregados antes y despues de fecha limite";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        if (reporte.compareTo("Porcentaje de Avance Global Completo") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados con Porcentaje de Avance Global Completo";
            this.title = "Entregados con Porcentaje de Avance Global Completo";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        if (reporte.compareTo("Porcentaje de Avance Global Incompleto") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados con Porcentaje de Avance Global Incompleto";
            this.title = "Entregados con Porcentaje de Avance Global Incompleto";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        if (reporte.compareTo("Porcentaje de Avance Global Completo e Incompleto") == 0) {
            tituloGraficas = tituloGraficas + " de Entregados con Porcentaje de Avance Global Completo e Incompleto";
            this.title = "Entregados con Porcentaje de Avance Global Completo e Incompleto";
            entregados.setLabel("Entregados");
            noentregados.setLabel("No entregados");
        }

        resultados.setLabel(this.title);

        //para el criterio de programa educativo
        // *** separar este metodo tambien para cada uno de los filtros
        // los metodos deben poderse leer sin dar scroll
        if (criterio.equalsIgnoreCase("programa_educativo")) {
            this.xaxisLabel = "Programas educativos";
            tituloGraficas += " por programa educativo";
            List<Programaeducativo> programasByUnidad = new ArrayList();
            selectProgramEducativo2 = new ArrayList<>();
            int x = 0;
            int max2 = 0;

            for (ResumenReporte resumenReporte : resumenesReporte) {
                // asigino los numeros y nombre para generar la grafica, en la
                // entidad ya vienen filtrados segun el criterio
                resultados.set(resumenReporte.getNombreProgramaEducativo() + " - " + resumenReporte.getPlanEstudio(), resumenReporte.getNumEntregados());
                esperados.set(resumenReporte.getNombreProgramaEducativo() + " - " + resumenReporte.getPlanEstudio(), resumenReporte.getNumEsperados());
            }

            max2 = max2 + 10;
            max = Integer.toString(max2);
            model.addSeries(resultados);
            model.addSeries(esperados);
            xAxis.setLabel("Programa educativo");
        }

        //**** hasta aqui crear un solo metodo por criterio
        if (criterio.equalsIgnoreCase("area_conocimiento")) {
            tituloGraficas += " por area de conocimiento";
            List<Areaconocimiento> areas = new ArrayList();
            int x = 0;
            int max2 = 0;
            areas = selectAreaConocimiento;

            for (ResumenReporte resumenReporte : resumenesReporte) {
                // asigino los numeros y nombre para generar la grafica, en la
                // entidad ya vienen filtrados segun el criterio
                resultados.set(resumenReporte.getNombreAreaConocimiento(), resumenReporte.getNumEntregados());
                esperados.set(resumenReporte.getNombreAreaConocimiento(), resumenReporte.getNumEsperados());

            }
            max2 = max2 + 10;
            max = Integer.toString(max2);
            model.addSeries(resultados);
            model.addSeries(esperados);
            xAxis.setLabel("Área de conocimiento");
        }

        if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
            tituloGraficas += " por unidad de aprendizaje";
            List<Unidadaprendizaje> areas = new ArrayList();
            int max2 = 0;

            for (ResumenReporte resumenReporte : resumenesReporte) {
                // asigino los numeros y nombre para generar la grafica, en la
                // entidad ya vienen filtrados segun el criterio
                resultados.set(resumenReporte.getNombreUnidadAprendizaje(), resumenReporte.getNumEntregados());
                esperados.set(resumenReporte.getNombreUnidadAprendizaje(), resumenReporte.getNumEsperados());

            }

            max2 = max2 + 10;
            max = Integer.toString(max2);
            model.addSeries(resultados);
            model.addSeries(esperados);
            xAxis.setLabel("Unidad de aprendizaje");

        }
        if (criterio.equalsIgnoreCase("profesor")) {
            tituloGraficas += " por profesor";
            List<Profesor> profesores = new ArrayList();
            int x = 0;
            int max2 = 0;

            for (ResumenReporte resumenReporte : resumenesReporte) {
                // asigino los numeros y nombre para generar la grafica, en la
                // entidad ya vienen filtrados segun el criterio
                resultados.set(resumenReporte.getNombreProfesor(), resumenReporte.getNumEntregados());
                esperados.set(resumenReporte.getNombreProfesor(), resumenReporte.getNumEsperados());

            }

            max2 = max2 + 10;
            max = Integer.toString(max2);
            model.addSeries(resultados);
            model.addSeries(esperados);
            xAxis.setLabel("Profesor");
        }

        //Escribiendo criterio para area administrativa yyy2
        if (criterio.equalsIgnoreCase("area_administrativa")) {
            tituloGraficas += " por area administrativa";
            List<String> areas = new ArrayList();
            int x = 0;
            int max2 = 0;

            for (ResumenReporte resumenReporte : resumenesReporte) {
                // asigino los numeros y nombre para generar la grafica, en la
                // entidad ya vienen filtrados segun el criterio
                resultados.set(resumenReporte.getNombreAreaAdministrativa(), resumenReporte.getNumEntregados());
                esperados.set(resumenReporte.getNombreAreaAdministrativa(), resumenReporte.getNumEsperados());

            }
            max2 = max2 + 10;
            max = Integer.toString(max2);
            model.addSeries(resultados);
            model.addSeries(esperados);
            xAxis.setLabel("Área administrativa");
        }

        model.setExtender("GraficaBarrasExt");
        return model;
    }

    public List<SemaforoProgEd> getSemProgEd() {
        MeterProgEdu();
        return semProgEd;
    }

    public MeterGaugeChartModel getGaugeModel() {
        return gaugeModel;
    }

    //------------------------------------------------------------------------------
    private MeterGaugeChartModel createMeterGaugeModels() {
        List<Number> intervals = new ArrayList<Number>() {
            {//intervals indica los limites de los colores
                add(0);
                add(80);
                add(95);
                add(100);
            }
        };

        List<Number> ticks = new ArrayList<Number>() {
            {//thicks maneja las labels de los indicadores
                add(0);
                add(10);
                add(20);
                add(30);
                add(40);
                add(50);
                add(60);
                add(70);
                add(80);
                add(90);
                add(100);
            }
        };

        MeterGaugeChartModel semaforo = new MeterGaugeChartModel();
        semaforo.setIntervals(intervals);
        semaforo.setTicks(ticks);
        double val = filtrosBeanHelper.countPorcentajeGeneralAfechaActual(unidadacademica.getUACid());
        semaforo.setValue(val);
        return semaforo;
    }

    public void MeterProgEdu() {
        ArrayList<Object[]> progEd = filtrosBeanHelper.getSemadoroProgEdValorEsp(unidadacademica.getUACid());
        List<Number> intervals = new ArrayList<Number>() {
            {//intervals indica los limites de los colores
                add(0);
                add(80);
                add(95);
                add(100);
            }
        };
        List<Number> ticks = new ArrayList<Number>() {
            {//thicks maneja las labels de los indicadores
                add(0);
                add(10);
                add(20);
                add(30);
                add(40);
                add(50);
                add(60);
                add(70);
                add(80);
                add(90);
                add(100);
            }
        };

        MeterGaugeChartModel semaforo;
        SemaforoProgEd data;

        for (Object[] prog : progEd) {
            semaforo = new MeterGaugeChartModel();
            semaforo.setIntervals(intervals);
            semaforo.setTicks(ticks);
            semaforo.setValue((Double.parseDouble(prog[4].toString()) / Double.parseDouble(prog[3].toString())) * 100);
            data = new SemaforoProgEd(semaforo, prog[2].toString());
            semProgEd.add(data);
        }
    }

    public void mostrarProgEd() {
        mostrarBar = true;
    }

    //------------------------------------------------------------------------------
    public void setAconClave(int AconClave) {
        acoclave = AconClave;
    }

    public void setClavepe(String _Clavepe) {
        clavepe = Integer.parseInt(_Clavepe);
    }

    public void setPesvigenciaR(String _Pesvigencia) {
        pesvigencia = _Pesvigencia;
    }

    public void setUapclaveR(String _uapclave) {
        uapclave = Integer.parseInt(_uapclave);
    }

    public void setGrupoNumero(String _gponumero) {
        gponumero = Integer.parseInt(_gponumero);
    }

    public void setNumeroEmpleado(String _pronumeroEmpleado) {
        pronumeroEmpleado = Integer.parseInt(_pronumeroEmpleado);
    }

    public List<Grupo> getGruposByUnidad() {
        List<Grupo> grupos = new ArrayList<>();

        if (selectProfesorString != null) {
            if (selectUnidadAprendizajeUniForm.size() > 0) {
                for (String uapd : selectUnidadAprendizajeUniForm) {
                    for (String prof : selectProfesorString) {
                        grupos.addAll(filtrosBeanHelper.getGrupoByProfesorUnidadAprendisajeCons(Integer.parseInt(prof), Integer.parseInt(uapd)));
                    }
                }
            }
        }

        return grupos;
    }

    public List<Grupo> getGruposByUnidadAprendizajesObj() {
        List<Grupo> gruposTempList;
        gruposTempList = new ArrayList<>();
        List<Grupo> gruposTempListAux;
        gruposTempListAux = new ArrayList<>();
        Unidadaprendizaje uap = new Unidadaprendizaje();

        if (selectUnidadAprendizajeUniForm != null && ac != null) {
            if (selectUnidadAprendizajeUniForm.size() > 0) {
                for (String unidadAprendizaje : selectUnidadAprendizajeUniForm) {
                    uap = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(Integer.parseInt(unidadAprendizaje));

                    for (UnidadaprendizajeImparteProfesor unidadAprendizajeImProf : uap.getUnidadaprendizajeImparteProfesorList()) {
                        gruposTempListAux.add(unidadAprendizajeImProf.getGrupoGPOid());
                    }
                }

                int grupoNum = 0;
                int enarray = 0;

                for (Grupo grup : gruposTempListAux) {
                    //primero vemos si esta dentro del arrayTemporal
                    for (Grupo grupt : gruposTempList) {
                        if (grupt.getGPOnumero() == grup.getGPOnumero()) {
                            enarray = 1;
                        }
                    }

                    // si no esta en el array
                    if (enarray == 0) {
                        gruposTempList.add(grup);
                    }

                    enarray = 0;
                }
            } else {
                System.out.println("Vacio");
            }
        } else {
            System.out.println("Nullo");
        }

        return gruposTempList;
    }

    public List<Grupo> getGruposByProfesorUnidadAprendizaje() {
        List<Grupo> grupoTempList;
        List<Grupo> grupoTempList2;
        grupoTempList = new ArrayList<>();
        grupoTempList2 = new ArrayList<>();

        if (selectProfesorString != null) {
            if (selectUnidadAprendizajeUniForm.size() > 0) {
                for (String uapIdstr : selectUnidadAprendizajeUniForm) {
                    for (String profesor : selectProfesorString) {
                        grupoTempList2.addAll(filtrosBeanHelper.getGrupoByProfesorUnidadAprendisajeCons(Integer.parseInt(profesor), Integer.parseInt(uapIdstr)));
                    }
                }
                int grupoNum = 0;
                int enarray = 0;
                for (Grupo grup : grupoTempList2) {

                    //primero vemos si esta dentro del arrayTemporal
                    for (Grupo grupt : grupoTempList) {
                        if (grupt.getGPOnumero() == grup.getGPOnumero()) {
                            enarray = 1;
                        }
                    }

                    // si no esta en el array
                    if (enarray == 0) {
                        grupoTempList.add(grup);
                    }
                    enarray = 0;
                }
            } else {
                System.out.println("Vacio");
            }
        } else {
            System.out.println("Nullo");
        }

        return grupoTempList;
    }

    public void Export2ExcelSemaforoProgramasEd() throws Throwable {
        //variables listaux
        ReporteAux reporteUI = new ReporteAux();

        // objeto de hoja de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        String nombreLibro = "";
        //guardamos imagen UABC en Documento

        //definimos estilo a celda titulo de los encabezados con logo escuela
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);

        //definimos los bordes de celdas en tabla
        HSSFCellStyle borderstabla = workbook.createCellStyle();
        borderstabla.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        borderstabla.setBorderTop(HSSFCellStyle.BORDER_THIN);
        borderstabla.setBorderRight(HSSFCellStyle.BORDER_THIN);
        borderstabla.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        font = workbook.createFont();
        borderstabla.setFont(font);

        //definimos el estilo de las cabezeras de una tabla
        HSSFCellStyle headerTabla = workbook.createCellStyle();
        HSSFFont fontwit = workbook.createFont();
        fontwit.setColor(HSSFColor.WHITE.index);
        headerTabla.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerTabla.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerTabla.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerTabla.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerTabla.setFillForegroundColor(HSSFColor.GREEN.index);
        headerTabla.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerTabla.setFont(fontwit);

        //aqui cambie
        headerTabla.setWrapText(true);
        //aqui cambie

        HSSFSheet sheet = workbook.createSheet("Programas educativos");
        nombreLibro = "- Director";

        //Graficas
        //Generacion de Semaforos
        MeterChartDemo demo1 = new MeterChartDemo("Estadísticas de Reportes Entregados por Programa Educativo");

        //Se obtiene el número de Ract actual para la generación de los semáforos
        RACTBeanHelper ractbeanhelper = new RACTBeanHelper();
        String ractactual = ractbeanhelper.obtenerReporteEnFecha();

        demo1.addChartsToExcel(demo1.crearGraficaSemaforoProgramaEducativo(reporte, Integer.parseInt(ractSeleccionado3), 32, filtrosBeanHelper), workbook, "del RACT #" + ractSeleccionado3);

        //Se mostrará el 3er RACT en caso de estar fuera de fecha límite sin ningun RACT asignado
        if ("0".equals(ractactual)) {
            ractactual = "3";
        }

        nombreLibro = "RACT General " + nombreLibro;

        //definimos encabezado
        HSSFRow row1 = sheet.createRow(7);

        row1.setHeight((short) 600);
        HSSFCell cell = row1.createCell(2);
        cell.setCellValue("Concentrado de Reporte de Avance de Contenido Temático General  por Programa Educativo ");
        cell.setCellStyle(style);

        int pos = 0;
        int row = 11;
        int col = 1;
        rowBand = row;
        colBand = row;
        boolean bandPe = false;

        List<String> ces = new ArrayList<>();
        ces.add(filtrosBeanHelper.getCescicloEscolarAct());
        ArrayList<ProgramaEducativoReportes> listaProgEdContar = new ArrayList<>();

        for (Programaeducativo programa : filtrosBeanHelper.getProgramaeducativoByUA(32)) {
            ProgramaEducativoReportes obj = new ProgramaEducativoReportes();

            obj.setCountEntregados(filtrosBeanHelper.countReportesDeProgramaEducativo(programa.getPEDid(), 4, "Entregados", ces));
            obj.setCountEntregadosRACT1(filtrosBeanHelper.countReportesDeProgramaEducativo(programa.getPEDid(), 1, "Entregados", ces));
            obj.setCountEntregadosRACT2(filtrosBeanHelper.countReportesDeProgramaEducativo(programa.getPEDid(), 2, "Entregados", ces));
            obj.setCountEntregadosRACT3(filtrosBeanHelper.countReportesDeProgramaEducativo(programa.getPEDid(), 3, "Entregados", ces));
            obj.setCountEsperados(filtrosBeanHelper.countEsperadosProgramaEducativo(programa.getPEDid(), 4, ces));

            obj.setPe(programa);
            listaProgEdContar.add(obj);

        }

        Collections.sort(listaProgEdContar, new SortbyPEClave());

        int rowStyle = row;

        for (ProgramaEducativoReportes resultado : listaProgEdContar) {
            if (resultado.getCountEsperados() == 0) {
                continue;
            }

            // Asignacion de tamano de columna de cabeceras
            sheet.setColumnWidth(col, 5500);
            sheet.setColumnWidth(col + 1, 5500);
            sheet.setColumnWidth(col + 2, 5500);
            sheet.setColumnWidth(col + 3, 5500);
            /////////////////////////////////////////////////
            row = rowStyle;

            setExDat(sheet, row, col, "Clave");
            setExDat(sheet, row, col + 1, "Programa educativo");
            setExDat(sheet, row, col + 2, "Plan de estudios");
            setExDat(sheet, row, col + 3, "Responsable");

            String nombreCompleto = " ";
            setExDat(sheet, row + 1, col, resultado.getPe().getPEDclave());
            setExDat(sheet, row + 1, col + 1, resultado.getPe().getPEDnombre());
            setExDat(sheet, row + 1, col + 2, filtrosBeanHelper.getPlanesByProgramaCons(resultado.getPe().getPEDid()).get(0).getPESvigenciaPlan());
            setExDat(sheet, row + 1, col + 3, filtrosBeanHelper.getProfesorResponsableNombreCompleto(resultado.getPe().getPEDid(), cicloescolar));

            setExDat(sheet, row + 2, col, "Número");
            setExDat(sheet, row + 2, col + 1, "Total esperados");
            setExDat(sheet, row + 2, col + 2, "Total entregados");
            setExDat(sheet, row + 2, col + 3, "% ");

            sheet.getRow(row + 2).getCell(1);//aqui modifique jesus ruelas

            if (ractSeleccionado3.equals("1") || ractSeleccionado3.equals("4")) {
                setExDat(sheet, row + 3, col, "RACT 1");
                setExDat(sheet, row + 3, col + 1, resultado.getCountEsperados());
                setExDat(sheet, row + 3, col + 2, resultado.getCountEntregadosRACT1());
                setExDat(sheet, row + 3, col + 3, ((float) resultado.getCountEntregadosRACT1() / ((float) resultado.getCountEsperados())) * 100);
            }

            if (ractSeleccionado3.equals("2") || ractSeleccionado3.equals("4")) {
                row = ractSeleccionado3.equals("4") ? ++row : row;
                setExDat(sheet, row + 3, col, "RACT 2");
                setExDat(sheet, row + 3, col + 1, resultado.getCountEsperados());
                setExDat(sheet, row + 3, col + 2, resultado.getCountEntregadosRACT2());
                setExDat(sheet, row + 3, col + 3, ((float) resultado.getCountEntregadosRACT2() / ((float) resultado.getCountEsperados())) * 100);
            }

            if (ractSeleccionado3.equals("3") || ractSeleccionado3.equals("4")) {
                row = ractSeleccionado3.equals("4") ? ++row : row;
                setExDat(sheet, row + 3, col, "RACT 3");
                setExDat(sheet, row + 3, col + 1, resultado.getCountEsperados());
                setExDat(sheet, row + 3, col + 2, resultado.getCountEntregadosRACT3());
                setExDat(sheet, row + 3, col + 3, ((float) resultado.getCountEntregadosRACT3() / ((float) resultado.getCountEsperados())) * 100);
            }

            if (ractSeleccionado3.equals("4")) {
                row++;
                setExDat(sheet, row + 3, col, "Todos los RACTS");
                setExDat(sheet, row + 3, col + 1, resultado.getCountEsperados() * 3);
                setExDat(sheet, row + 3, col + 2, resultado.countEntregados);
                setExDat(sheet, row + 3, col + 3, ((float) resultado.getCountEntregados() / ((float) resultado.getCountEsperados() * 3)) * 100);
            }

            setStyleCell(sheet, headerTabla, rowStyle, col);
            setStyleCell(sheet, headerTabla, rowStyle, col + 1);
            setStyleCell(sheet, headerTabla, rowStyle, col + 2);
            setStyleCell(sheet, headerTabla, rowStyle, col + 3);
            setStyleCell(sheet, headerTabla, rowStyle, col + 4);

            setStyleCell(sheet, headerTabla, rowStyle + 2, col);
            setStyleCell(sheet, headerTabla, rowStyle + 2, col + 1);
            setStyleCell(sheet, headerTabla, rowStyle + 2, col + 2);
            setStyleCell(sheet, headerTabla, rowStyle + 2, col + 3);

            pos++;

            if (col >= 25) {
                rowStyle = rowStyle + 14;
                col = -4;
            }

            col = col + 5;
            colBand = col;
            rowBand = rowStyle;
        }

        int prow = 14;

        //finalizamos con
        //metodo para descargar el objeto
        sheet = cabezeraGeneralExcel(sheet, style, workbook);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreLibro + ".xls\"");
        workbook.write(externalContext.getResponseOutputStream());
        facesContext.responseComplete();
    }

    /**
     * Este es el metodo que se accede cuando se presiona el boton de generar
     * excel, aqui se se busca el criterio de busqueda y llamada al metodo segun
     * corresponda
     *
     * @throws Throwable
     */
    public void exportarExcel() throws Throwable {
        if (criterio.equalsIgnoreCase("programa_educativo")) {
            // crear un metodo con la misma nomenclatura para cada filtro
            // en base el de programa educativo segun las nececidades del excel
            if (reporte.compareTo("entregados") == 0 || reporte.compareTo("noentregados") == 0 || reporte.compareTo("entregadosynoentregados") == 0) {
                System.out.println("Se hará el reporte con el código existente");
            }

            if (reporte.compareTo("Porcentaje de Avance Global Completo") == 0 || reporte.compareTo("Porcentaje de Avance Global Incompleto") == 0 || reporte.compareTo("Porcentaje de Avance Global Completo e Incompleto") == 0) {
                System.out.println("Se hará el reporte con el código nuevo");
            }

            generarExcelProgramaEducativo();
        }

        if (criterio.equalsIgnoreCase("area_conocimiento")) {
            generarExcelAreaConocimiento();
        }

        if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
            generarExcelUnidadAprendizaje();
        }

        if (criterio.equalsIgnoreCase("profesor")) {
            generarExcelProfesor();
        }

        if (criterio.equalsIgnoreCase("area_administrativa")) {
            generarExcelAreaAdministrativa();
        }
    }

    public String nombreSegunFiltros() {
        String nombre = "Concentrado de reporte de avance de contenido temático de ";

        switch (reporte) {
            case "entregados":
                nombre += "Entregados ";
                break;
            case "noentregados":
                nombre += "No entregados ";
                break;
            case "entregadosynoentregados":
                nombre += "Entregados y No entregados ";
                break;
            case "entregadosatiempo":
                nombre += "Entregados a tiempo ";
                break;
            case "entregadosdespueslimite":
                nombre += "Entregados después de fecha límite ";
                break;
            case "entregadosatiempoenfechalimiteydespues":
                nombre += "Entregados a tiempo y después de fecha límite ";
                break;
            case "Porcentaje de Avance Global Completo":
                nombre += "Porcentaje de avance global completo ";
                break;
            case "Porcentaje de Avance Global Incompleto":
                nombre += "Porcentaje de avance global incompleto ";
                break;
            case "Porcentaje de Avance Global Completo e Incompleto":
                nombre += "Porcentaje de avance global completo e incompleto ";
                break;
        }

        nombre += "por ";

        if (criterio.equalsIgnoreCase("programa_educativo")) {
            nombre += "programa educativo";
        }

        if (criterio.equalsIgnoreCase("area_conocimiento")) {
            nombre += "área de conocimiento";
        }

        if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
            nombre += "unidad de aprendizaje";
        }

        if (criterio.equalsIgnoreCase("profesor")) {
            nombre += "profesor";
        }

        if (criterio.equalsIgnoreCase("area_administrativa")) {
            nombre += "area administrativa";
        }

        switch (ract) {
            case "1":
                nombre += " primer ract";
                break;

            case "2":
                nombre += " segundo ract";
                break;

            case "3":
                nombre += " tercer ract";
                break;
            case "4":
                nombre += " todos los ract";
                break;
        }

        nombre = nombre.toUpperCase();
        return nombre;
    }

    public String nombreArchivo() {
        String nombre = "reporte de avance de contenido temático ";

        switch (reporte) {
            case "entregados":
                nombre += "Entregados ";
                break;
            case "noentregados":
                nombre += "No entregados ";
                break;
            case "entregadosynoentregados":
                nombre += "Entregados y No entregados ";
                break;
            case "entregadosatiempo":
                nombre += "Entregados a tiempo ";
                break;
            case "entregadosdespueslimite":
                nombre += "Entregados después de fecha límite ";
                break;
            case "entregadosatiempoenfechalimiteydespues":
                nombre += "Entregados a tiempo y despues de fecha límite ";
                break;
            case "Porcentaje de Avance Global Completo":
                nombre += "Porcentaje de avance global completo ";
                break;
            case "Porcentaje de Avance Global Incompleto":
                nombre += "Porcentaje de avance global incompleto ";
                break;
            case "Porcentaje de Avance Global Completo e Incompleto":
                nombre += "Porcentaje de avance global completo e incompleto ";
                break;
        }

        nombre += "por ";

        if (criterio.equalsIgnoreCase("programa_educativo")) {
            nombre += "programa educativo";
        }

        if (criterio.equalsIgnoreCase("area_conocimiento")) {
            nombre += "área de conocimiento";
        }

        if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
            nombre += "unidad de aprendizaje";
        }

        if (criterio.equalsIgnoreCase("profesor")) {
            nombre += "profesor";
        }

        if (criterio.equalsIgnoreCase("area_administrativa")) {
            nombre += "área administrativa";
        }

        switch (ract) {
            case "1":
                nombre += " primer ract";
                break;

            case "2":
                nombre += " segundo ract";
                break;

            case "3":
                nombre += " tercer ract";
                break;
            case "4":
                nombre += " todos los ract";
                break;
        }

        nombre = nombre.toUpperCase();

        //Mover
        ServiceLocator.getInstanceBaseDAO().setTipo(Object.class);
        ArrayList<Object> temp = (ArrayList<Object>) ServiceLocator.getInstanceBaseDAO().executeSQL("select curdate()");
        Object fechaActual = temp.get(0);
        nombre += " " + fechaActual.toString();

        temp = (ArrayList<Object>) ServiceLocator.getInstanceBaseDAO().executeSQL("select CONCAT(hour(NOW()),'hr',minute(NOW()) ,'m')");
        fechaActual = " " + temp.get(0);
        nombre += fechaActual.toString();
        return nombre;
    }

    /**
     * Este metodo genera el excel cual el filtro seleccionado es por programa
     * educativo, dentro de el se llaman los datos segun el criterio de busqueda
     *
     * @throws Throwable
     */
    public void generarExcelProgramaEducativo() throws Throwable {
        List<Cicloescolar> listaCiclo = filtrosBeanHelper.getCiclosEscolares();
        int cicloEntero = 0;

        for (Cicloescolar ciclo : listaCiclo) {
            if (ciclo.getCESid() == idCicloEscolar) {
                cicloEntero = Integer.parseInt(ciclo.getCEScicloEscolar().split("-")[0]);
                break;
            }
        }

        // objeto de hoja de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        String nombreLibro;

        //definimos estilo a celda titulo de los encabezados con logo escuela
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);
        //definimos los bordes de celdas en tabla
        HSSFCellStyle borderstabla = workbook.createCellStyle();
        // gneraramos el estidlo de la celda con bordes con el sigueinte metodo generico
        borderstabla = generarEstiloExcelBordesTabla(borderstabla, workbook, font);

        // bordes con centrado
        HSSFCellStyle bordertextoCentrado = workbook.createCellStyle();
        // generamos el estilo de celda con bordes y texto centrado con
        // el siguiente metodo generico
        bordertextoCentrado = generarEstilocExcelBordesConCentrado(bordertextoCentrado, workbook, font);
        //definimos el estilo de las cabezeras de una tabla
        HSSFCellStyle headerTabla = workbook.createCellStyle();
        HSSFFont fontwit = workbook.createFont();
        // generamo el estilo para las cabeceras de las tables en color verde
        // con el siguiente metodo generico
        headerTabla = generarEstilocExcelHeaderTable(headerTabla, workbook, fontwit);

        //Variable para convertir a 2 decimales
        DecimalFormat decFor = new DecimalFormat();
        decFor.applyPattern("###.00");

        int row;
        int col;

        //
        //Se crea la hoja de Graficos que contiene la grafica de barras y el resumen de la busqueda
        //
        HSSFSheet sheet = workbook.createSheet("Graficos");

        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        sheet.setMargin(Sheet.LeftMargin, 0.3);
        sheet.setMargin(Sheet.BottomMargin, 0.6);
        sheet.setMargin(Sheet.FooterMargin, 0.3);
        sheet.setMargin(Sheet.HeaderMargin, 0.3);
        sheet.setMargin(Sheet.RightMargin, 0.3);
        sheet.setMargin(Sheet.TopMargin, 0.3);

        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 100);
        printSetup.setLandscape(true);
        printSetup.setFooterMargin(0.1);
        printSetup.setHeaderMargin(0.1);
        printSetup.setLandscape(true);
        Footer piePagina = sheet.getFooter();
        piePagina.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

        //definimos cabecera con los datos de logo, fecha actual, ciclo escolar
        //universidad, facultad, mexicali
        sheet = cabezeraGeneralExcel(sheet, style, workbook);
        //Se les da anchura a las columnas de la tabla de resumen
        sheet.setColumnWidth(1, 5800);
        sheet.setColumnWidth(2, 11350);
        sheet.setColumnWidth(3, 6300);
        sheet.setColumnWidth(4, 6800);
        sheet.setColumnWidth(5, 6800);
        sheet.setColumnWidth(6, 4000);

        //Se genera el nombre que tendra nuestro excel
        nombreLibro = nombreSegunFiltros();

        //Creacion de la tabla
        setExDat(sheet, 13, 2, "Programa Educativo");
        setExDat(sheet, 13, 3, "Plan de Estudios");

        //Se define encabezado por tipo de reporte
        if (reporte.equalsIgnoreCase("noentregados")) {
            setExDat(sheet, 13, 5, "Total no entregados y/o parciales");
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            setExDat(sheet, 13, 5, "Total entregados y parciales");
        } else {
            setExDat(sheet, 13, 5, "Total entregados");
        }

        setExDat(sheet, 13, 4, "Total esperados");
        setExDat(sheet, 13, 6, "Porcentaje");
        // estas lineas le dan estilo a la tabla de resumen
        setStyleCell(sheet, headerTabla, 13, 2);
        setStyleCell(sheet, headerTabla, 13, 3);
        setStyleCell(sheet, headerTabla, 13, 4);
        setStyleCell(sheet, headerTabla, 13, 5);
        setStyleCell(sheet, headerTabla, 13, 6);

        //empezamos en el renglon 14 para evitar la cabezera de la hoja
        int prow = 14;
        Vector entregadosRactSeleccionado = new Vector();
        List<Programaeducativo> programas = new ArrayList<>();

        // recorro los programa educativos que se seleccionaron
        for (String programa : selectProgramaEducativoString) {
            // creo un nuevo programa educativo para asignar cada uno
            Programaeducativo programaEducativo = filtrosBeanHelper.getProgramaeducativo(Integer.parseInt(programa));
            programas.add(programaEducativo);

            //*** recorro los planes de estudio del programa
            for (Planestudio planE : programaEducativo.getPlanestudioList()) {
                // *** el if siguiente es por si tiene grupos en el ciclo que se busca agrego una linea
                // ** con la informacion
                if (ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().countUaipPorAreaConocimientoYcicloEscolar(planE.getPESid(), idCicloEscolar) != 0) {
                    // de llena la tabla de detalle con la informacion de PE
                    setExDat(sheet, prow, 2, programaEducativo.getPEDnombre());
                    setExDat(sheet, prow, 3, planE.getPESvigenciaPlan());

                    List<Planestudio> plan = new ArrayList();
                    plan.add(planE);

                    int esperadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados("esperados", idCicloEscolar + "", ract, plan);
                    int entregadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(reporte, idCicloEscolar + "", ract, plan);

                    if (ract.equalsIgnoreCase("4") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Incompleto") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto")) {
                        esperadosPE = esperadosPE * 3;
                    }

                    entregadosRactSeleccionado.add(entregadosPE);

                    setExDat(sheet, prow, 4, esperadosPE);
                    setExDat(sheet, prow, 5, entregadosPE);
                    setExDat(sheet, prow, 6, String.valueOf(calcularPorcentaje(esperadosPE, entregadosPE)));

                    setStyleCell(sheet, borderstabla, prow, 4);
                    setStyleCell(sheet, borderstabla, prow, 2);
                    setStyleCell(sheet, bordertextoCentrado, prow, 3);
                    setStyleCell(sheet, bordertextoCentrado, prow, 4);
                    setStyleCell(sheet, bordertextoCentrado, prow, 5);
                    setStyleCell(sheet, bordertextoCentrado, prow, 6);
                    setStyleCell(sheet, bordertextoCentrado, prow, 7);
                    prow++;
                }
            }
        }
        //*** termino tabla de primera hoja con datos generales

        //*** mando a generar la grafica
        // el tipo grafico se deja porque en su momento se pretende que se
        // puedan generar otro tipo de graficas
        if (tipografico.equalsIgnoreCase("barras")) {
            // creo la una instance de la clase que genera el grafico
            BarChartCL demo1 = new BarChartCL("Estadísticas " + nombreLibro, "ExcelPOIGrafica1");
            // agrego la grafica al excel y mande el metodo que la crea
            demo1.addChartToExcel(demo1.crearGraficaBarrasProgramaEducativo(reporte, ract, programas, idCicloEscolar, entregadosRactSeleccionado), workbook, prow);
        }

        //
        //aqui creamos la hoja para el programa
        //
        setExDatNegrita(sheet, 8, 1, nombreLibro);

        // variable para manejarlos renglones despues de la cabezera de la hoja
        int currow = 20;
        currow++;

        //**** aqui crea una hoja por PE e inicia llenado de generales del PE
        // ** voy a reccorer los PE seleccionadoss
        for (Programaeducativo pe : programas) {
            for (Planestudio planE : pe.getPlanestudioList()) {
                // *** si tiene grupos en el ciclo que se busca agrego crea una hoja
                if (ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().countUaipPorAreaConocimientoYcicloEscolar(planE.getPESid(), idCicloEscolar) != 0) {
                    currow = 15;
                    // creo la hoja para el Programa educatico y su plan de estudio
                    sheet = workbook.createSheet(planE.getPESvigenciaPlan() + " " + pe.getPEDnombre());
                    sheet.setHorizontallyCenter(true);
                    sheet.setAutobreaks(true);

                    sheet.setMargin(Sheet.LeftMargin, 0.3);
                    sheet.setMargin(Sheet.BottomMargin, 0.6);
                    sheet.setMargin(Sheet.FooterMargin, 0.3);
                    sheet.setMargin(Sheet.HeaderMargin, 0.3);
                    sheet.setMargin(Sheet.RightMargin, 0.3);
                    sheet.setMargin(Sheet.TopMargin, 0.3);

                    HSSFPrintSetup printSetupl = sheet.getPrintSetup();
                    printSetupl.setFitWidth((short) 1);
                    printSetupl.setFitHeight((short) 100);
                    printSetupl.setLandscape(true);
                    printSetupl.setFooterMargin(0.3);
                    printSetupl.setHeaderMargin(0.3);
                    printSetupl.setLandscape(true);

                    Footer piePagina2 = sheet.getFooter();
                    piePagina2.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

                    // Tabla que muestra información de PE
                    row = currow + 1;
                    col = 1;
                    // Titulo del libro y programa educativo
                    setExDatNegrita(sheet, 8, 1, nombreLibro);
                    setExDatNegrita(sheet, 9, 2, pe.getPEDnombre());
                    // Cabecera
                    setExDat(sheet, 14, 1, "Clave");
                    setExDat(sheet, 14, 2, "Programa Educativo");
                    setExDat(sheet, 14, 3, "Plan de Estudios");
                    setExDat(sheet, 14, 4, "Responsable");
                    setStyleCell(sheet, headerTabla, 14, 1);
                    setStyleCell(sheet, headerTabla, 14, 2);
                    setStyleCell(sheet, headerTabla, 14, 3);
                    setStyleCell(sheet, headerTabla, 14, 4);

                    // Llenado de informacion
                    setExDat(sheet, 15, 1, pe.getPEDclave());
                    setExDat(sheet, 15, 2, pe.getPEDnombre());
                    setExDat(sheet, 15, 3, planE.getPESvigenciaPlan());

                    // Responsable de Programa educativo
                    List<ResponsableProgramaEducativo> listaRPE = pe.getResponsableProgramaEducativoList();
                    Profesor peProfesor = null;

                    //Se busca al responsable de programa educativo dentro de la lista de RPE del programa educativo (pe).
                    if (listaRPE.size() > 1) {
                        Collections.sort(listaRPE, (ResponsableProgramaEducativo rpe1, ResponsableProgramaEducativo rpe2) -> rpe1.getRPEid() > rpe2.getRPEid() ? 1 : -1);

                        for (ResponsableProgramaEducativo rpe : listaRPE) {
                            if (rpe.getCicloEscolar().getCESid() == idCicloEscolar) {
                                peProfesor = rpe.getProfesor();
                            }
                        }

                        if (peProfesor == null) {
                            for (ResponsableProgramaEducativo rpe : listaRPE) {
                                int cicloProfesor = Integer.parseInt(rpe.getCicloEscolar().getCEScicloEscolar().split("-")[0]);                                

                                if (cicloProfesor < cicloEntero) {
                                    peProfesor = rpe.getProfesor();
                                    break;                                   
                                }
                            }
                        }
                    } else if (pe.getResponsableProgramaEducativoList().size() == 1) {
                        peProfesor = pe.getResponsableProgramaEducativoList().get(0).getProfesor();
                    }

                    if (peProfesor != null) {
                        setExDat(sheet, 15, 4, obtenerNombreCompletoRPE(peProfesor));
                    } else {
                        setExDat(sheet, 15, 4, "Este programa educativo, no cuenta un responsable asignado");
                    }

                    setStyleCell(sheet, borderstabla, 15, 1);
                    setStyleCell(sheet, borderstabla, 15, 2);
                    setStyleCell(sheet, borderstabla, 15, 3);
                    setStyleCell(sheet, borderstabla, 15, 4);

                    // Ciclo para obtención de porcentajes completos, incompletos y mixto
                    // Inicia tabla de resumen de racts
                    setExDat(sheet, row + 2, col, "Total de RACT Entregados");
                    setExDat(sheet, row + 3, col, "No. RACT");
                    setExDat(sheet, row + 3, col + 1, "Total esperados");

                    if (reporte.equalsIgnoreCase("noentregados")) {
                        setExDat(sheet, row + 3, col + 2, "Total no entregados y/o parciales");
                    } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
                        setExDat(sheet, row + 3, col + 2, "Total entregados y parciales");
                    } else if (reporte.equalsIgnoreCase("entregados")) {
                        setExDat(sheet, row + 3, col + 2, "Total entregados");
                    } else if (reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo") || reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto")) {
                        setExDat(sheet, row + 3, col + 2, "Total con % completo");
                    } else if (reporte.equalsIgnoreCase("Porcentaje de Avance Global Incompleto")) {
                        setExDat(sheet, row + 3, col + 2, "Total con % incompleto");
                    }

                    setExDat(sheet, row + 3, col + 3, "%");
                    sheet.getRow(row + 3).getCell(1);

                    ArrayList<Planestudio> planELista = new ArrayList<>();
                    planELista.add(planE);

                    int esperadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados("esperados", idCicloEscolar + "", "1", planELista);
                    int entregadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(reporte, idCicloEscolar + "", "1", planELista);

                    setExDat(sheet, row + 4, col, "RACT 1");
                    setExDat(sheet, row + 4, col + 1, esperadosPE);
                    setExDat(sheet, row + 4, col + 2, entregadosPE);
                    setExDat(sheet, row + 4, col + 3, String.valueOf(calcularPorcentaje(esperadosPE, entregadosPE)));

                    entregadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(reporte, idCicloEscolar + "", "2", planELista);

                    setExDat(sheet, row + 5, col, "RACT 2");
                    setExDat(sheet, row + 5, col + 1, esperadosPE);
                    setExDat(sheet, row + 5, col + 2, entregadosPE);
                    setExDat(sheet, row + 5, col + 3, String.valueOf(calcularPorcentaje(esperadosPE, entregadosPE)));

                    entregadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(reporte, idCicloEscolar + "", "3", planELista);

                    setExDat(sheet, row + 6, col, "RACT 3");
                    setExDat(sheet, row + 6, col + 1, esperadosPE);
                    setExDat(sheet, row + 6, col + 2, entregadosPE);
                    setExDat(sheet, row + 6, col + 3, String.valueOf(calcularPorcentaje(esperadosPE, entregadosPE)));

                    if (!reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Incompleto") && !reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto")) {
                        esperadosPE = esperadosPE * 3;
                    }

                    entregadosPE = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(reporte, idCicloEscolar + "", "4", planELista);

                    setExDatNegrita(sheet, row + 7, col, "Total: ");
                    setExDat(sheet, row + 7, col + 1, esperadosPE);
                    setExDat(sheet, row + 7, col + 2, entregadosPE);
                    setExDat(sheet, row + 7, col + 3, String.valueOf(calcularPorcentaje(esperadosPE, entregadosPE)));

                    //*** termina llenado de tabla de RACTS
                    // se le da estilo a la tabla
                    setStyleCell(sheet, headerTabla, row, col);
                    setStyleCell(sheet, headerTabla, row, col + 1);
                    setStyleCell(sheet, headerTabla, row, col + 2);
                    setStyleCell(sheet, headerTabla, row, col + 3);
                    setStyleCell(sheet, headerTabla, row, col + 4);

                    setStyleCell(sheet, headerTabla, row + 2, col);
                    setStyleCell(sheet, headerTabla, row + 2, col + 1);
                    setStyleCell(sheet, headerTabla, row + 2, col + 2);
                    setStyleCell(sheet, headerTabla, row + 2, col + 3);

                    setStyleCell(sheet, headerTabla, row + 3, col);
                    setStyleCell(sheet, headerTabla, row + 3, col + 1);
                    setStyleCell(sheet, headerTabla, row + 3, col + 2);
                    setStyleCell(sheet, headerTabla, row + 3, col + 3);
                    setStyleCell(sheet, headerTabla, row + 3, col + 4);
                    setStyleCell(sheet, headerTabla, row + 3, col + 5);

                    setStyleCell(sheet, borderstabla, row + 4, col);
                    setStyleCell(sheet, borderstabla, row + 4, col + 1);
                    setStyleCell(sheet, borderstabla, row + 4, col + 2);
                    setStyleCell(sheet, borderstabla, row + 4, col + 3);
                    setStyleCell(sheet, borderstabla, row + 4, col + 4);
                    setStyleCell(sheet, borderstabla, row + 4, col + 5);
                    setStyleCell(sheet, borderstabla, row + 4, col + 6);
                    setStyleCell(sheet, borderstabla, row + 4, col + 7);

                    setStyleCell(sheet, borderstabla, row + 5, col);
                    setStyleCell(sheet, borderstabla, row + 5, col + 1);
                    setStyleCell(sheet, borderstabla, row + 5, col + 2);
                    setStyleCell(sheet, borderstabla, row + 5, col + 3);
                    setStyleCell(sheet, borderstabla, row + 5, col + 4);
                    setStyleCell(sheet, borderstabla, row + 5, col + 5);
                    setStyleCell(sheet, borderstabla, row + 5, col + 6);
                    setStyleCell(sheet, borderstabla, row + 5, col + 7);

                    setStyleCell(sheet, borderstabla, row + 6, col);
                    setStyleCell(sheet, borderstabla, row + 6, col + 1);
                    setStyleCell(sheet, borderstabla, row + 6, col + 2);
                    setStyleCell(sheet, borderstabla, row + 6, col + 3);
                    setStyleCell(sheet, borderstabla, row + 6, col + 4);
                    setStyleCell(sheet, borderstabla, row + 6, col + 5);
                    setStyleCell(sheet, borderstabla, row + 6, col + 6);
                    setStyleCell(sheet, borderstabla, row + 6, col + 7);

                    setStyleCell(sheet, borderstabla, row + 7, col);
                    setStyleCell(sheet, borderstabla, row + 7, col + 1);
                    setStyleCell(sheet, borderstabla, row + 7, col + 2);
                    setStyleCell(sheet, borderstabla, row + 7, col + 3);
                    setStyleCell(sheet, borderstabla, row + 7, col + 4);
                    setStyleCell(sheet, borderstabla, row + 7, col + 5);
                    setStyleCell(sheet, borderstabla, row + 7, col + 6);
                    setStyleCell(sheet, borderstabla, row + 7, col + 7);

                    sheet = cabezeraGeneralExcel(sheet, style, workbook);
                    currow = 25;

                    /**
                     * Voy a recorrer las areas de conocimiento del PE para
                     * poder separar la uiap por ellas ya que este reporte asi
                     * se genera
                     */
                    for (Areaconocimiento ac : planE.getAreaconocimientoList()) {
                        List<UnidadaprendizajeImparteProfesor> uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorAreaConocimiento(reporte, pe.getPEDid(), idCicloEscolar, ac.getACOid(), ract, planE.getPESid());
                        sheet.setColumnWidth(1, 5800);
                        sheet.setColumnWidth(2, 15100);
                        sheet.setColumnWidth(3, 6300);
                        sheet.setColumnWidth(4, 10200);
                        sheet.setColumnWidth(5, 3900);
                        sheet.setColumnWidth(6, 5600);
                        sheet.setColumnWidth(7, 7200);
                        sheet.setColumnWidth(9, 7200);
                        sheet.setColumnWidth(10, 5600);
                        sheet.setColumnWidth(11, 7200);
                        sheet.setColumnWidth(12, 10000);

                        //** si el area tiene materias se agrega si no, no
                        if (!uaips.isEmpty()) {
                            //  ********* aqui empiezo a llenar  el  detalle
                            // *** lleno la tabla del area de conocimiento
                            currow += 3;
                            setExDat(sheet, currow, 1, "Clave");
                            setExDat(sheet, currow, 2, "Área de conocimiento");

                            setStyleCell(sheet, headerTabla, currow, 1);
                            setStyleCell(sheet, headerTabla, currow, 2);
                            setStyleCell(sheet, headerTabla, currow, 3);

                            currow++;
                            setExDat(sheet, currow, 1, ac.getACOclave()); //clave

                            setExDat(sheet, currow, 2, ac.getACOnombre()); //area conociminto
                            setStyleCell(sheet, borderstabla, currow, 1);
                            setStyleCell(sheet, borderstabla, currow, 2);
                            // ** termina llenado de la tabla

                            currow += 2;
                            //titulos de las unidades de aprendizaje
                            setExDat(sheet, currow, 1, "Clave unidad de aprendizaje");
                            setExDat(sheet, currow, 2, "Unidad de aprendizaje");
                            setExDat(sheet, currow, 3, "No. de empleado");
                            setExDat(sheet, currow, 4, "Nombre del profesor");
                            setExDat(sheet, currow, 5, "Grupo");

                            //** variable para menejar las calumnas dependiendo los ract seleccinados
                            int x = 6;

                            //** si selsecciono el primer ract o todos
                            if (ract.equalsIgnoreCase("1")) {
                                setExDat(sheet, currow, x, "% Avance 1er reporte");
                                setExDat(sheet, currow, x + 1, "Fecha de elaboración 1er RACT");
                            }

                            if (ract.equalsIgnoreCase("2")) {
                                setExDat(sheet, currow, x, "% Avance 2do reporte");
                                setExDat(sheet, currow, x + 1, "Fecha de elaboración 2do RACT");
                            }

                            if (ract.equalsIgnoreCase("3")) {
                                setExDat(sheet, currow, x, "% Avance 3er reporte");
                                setExDat(sheet, currow, x + 1, "Fecha de elaboración 3er RACT");
                            }

                            // si se selcciono la opcion de todos los ract
                            // agrego las 3 columnas
                            if (ract.equalsIgnoreCase("4")) {
                                setExDat(sheet, currow, x, "% Avance 1er reporte");
                                setExDat(sheet, currow, x + 1, "Fecha de elaboración 1er RACT");
                                setExDat(sheet, currow, x + 2, "% Avance 2do reporte");
                                setExDat(sheet, currow, x + 3, "Fecha de elaboración 2do RACT");
                                setExDat(sheet, currow, x + 4, "% Avance 3er reporte");
                                setExDat(sheet, currow, x + 5, "Fecha de elaboración 3er RACT");
                                setExDat(sheet, currow, x + 6, "Responsable");
                                sheet.setColumnWidth(x + 2, 5600);
                            } // si no agrego la columna de responsable despues de la primera columna
                            else {
                                setExDat(sheet, currow, x + 2, "Responsable");
                                sheet.setColumnWidth(x + 2, 10000);
                            }

                            // para formatear toda la linea
                            for (int j = 1; j <= 13; j++) {
                                //tenemos qu aajustar texto
                                //tenemos que centrar el texto
                                //cambiar el color de fondo
                                setStyleCell(sheet, headerTabla, currow, j);
                            }

                            currow++;

                            /**
                             * Ordone los uaips para que salgan las del mismo
                             * profesor continuas, si no saldrian separadas
                             */
                            Collections.sort(uaips, new Comparator<UnidadaprendizajeImparteProfesor>() {

                                public int compare(UnidadaprendizajeImparteProfesor tu1, UnidadaprendizajeImparteProfesor tu2) {
                                    // compare two instance of `Score` and return `int` as result.
                                    return tu1.getProfesorPROid().toString().compareTo(tu2.getProfesorPROid().toString());
                                }
                            });

                            // empiezo a llenar las uaip
                            for (UnidadaprendizajeImparteProfesor uaip : uaips) {
                                setExDat(sheet, currow, 1, uaip.getUnidadAprendizajeUAPid().getUAPclave()); //clave
                                setExDat(sheet, currow, 2, uaip.getUnidadAprendizajeUAPid().getUAPnombre()); //nombre unidad
                                setExDat(sheet, currow, 3, uaip.getProfesorPROid().getPROnumeroEmpleado()); //numero empleado
                                setExDat(sheet, currow, 4, uaip.getProfesorPROid().getPROnombre() + " " + uaip.getProfesorPROid().getPROapellidoPaterno() + " " + uaip.getProfesorPROid().getPROapellidoMaterno()); //nombre maestro
                                setExDat(sheet, currow, 5, uaip.getGrupoGPOid().getGPOnumero() + "-" + uaip.getUIPsubgrupo() + "-" + uaip.getUIPtipoSubgrupo()); //grupo numero
                                //marcamos bordes
                                for (int j = 1; j <= 5; j++) {
                                    setStyleCell(sheet, borderstabla, currow, j);
                                }
                                //tienne que ser entregados/enviados
                                Format formatter = new SimpleDateFormat("dd-MM-yyyy");
                                DecimalFormat formato1 = new DecimalFormat("#.##");
                                //** si se selecciono ract 1 o todos
                                if (ract.equalsIgnoreCase("1")) {
                                    // intento obtener los datos si no tiene entrara en catch y los pondra vacios
                                    try {
                                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("1")) {
                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()));
                                            setExDat(sheet, currow, (x + 1), formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else {
                                            throw new RuntimeException("No hay datos");
                                        }
                                    } catch (Exception e) {
                                        // si no realizo el reporte los pondra vacios
                                        setExDat(sheet, currow, x, ""); //% avance 7**
                                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***

                                    }
                                    setStyleCell(sheet, borderstabla, currow, x);
                                    setStyleCell(sheet, borderstabla, currow, x + 1);

                                }
                                //Se revisa que el siguiente elemento sea el reporte 2
                                if (ract.equalsIgnoreCase("2")) {
                                    try {
                                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("2")) {
                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("2")) {
                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else {
                                            throw new RuntimeException("No hay datos");
                                        }
                                    } catch (Exception e) {
                                        // si no realizo el reporte los pondra vacios
                                        setExDat(sheet, currow, x, ""); //% avance 7**
                                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***

                                    }
                                    setStyleCell(sheet, borderstabla, currow, x);
                                    setStyleCell(sheet, borderstabla, currow, x + 1);

                                }
                                //Se revisa que el siguiente elemento sea el reporte 3
                                //** si se selecciono ract 3 o todos
                                if (ract.equalsIgnoreCase("3")) {
                                    try {
                                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("3")) {

                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("3")) {
                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else if (uaip.getReporteavancecontenidotematicoList().get(2).getRACnumero().equalsIgnoreCase("3")) {
                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else {
                                            throw new RuntimeException("No hay datos");
                                        }
                                    } catch (Exception e) {
                                        // si no realizo el reporte los pondra vacios
                                        setExDat(sheet, currow, x, ""); //% avance 7**
                                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***
                                    }

                                    setStyleCell(sheet, borderstabla, currow, x);
                                    setStyleCell(sheet, borderstabla, currow, x + 1);

                                }
                                // si es todos los ract, llenara todas las columnas de los ract
                                if (ract.equalsIgnoreCase("4")) {
                                    try {
                                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("1")) {
                                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()));
                                            setExDat(sheet, currow, (x + 1), formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else {
                                            throw new RuntimeException("No hay datos");
                                        }
                                    } catch (Exception e) {
                                        // si no realizo el reporte los pondra vacios
                                        setExDat(sheet, currow, x, "");
                                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***
                                    }

                                    setStyleCell(sheet, borderstabla, currow, x);
                                    setStyleCell(sheet, borderstabla, currow, x + 1);

                                    try {
                                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("2")) {

                                            setExDat(sheet, currow, x + 2, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 3, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("2")) {

                                            setExDat(sheet, currow, x + 2, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 3, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else {
                                            throw new RuntimeException("No hay datos");
                                        }
                                    } catch (Exception e) {
                                        // si no realizo el reporte los pondra vacios
                                        setExDat(sheet, currow, x + 2, ""); //% avance 7**
                                        setExDat(sheet, currow, x + 3, ""); //fecha elabora 8***

                                    }
                                    setStyleCell(sheet, borderstabla, currow, x + 2);
                                    setStyleCell(sheet, borderstabla, currow, x + 3);
                                    // ract 3
                                    try {
                                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("3")) {

                                            setExDat(sheet, currow, x + 4, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 5, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("3")) {

                                            setExDat(sheet, currow, x + 4, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 5, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else if (uaip.getReporteavancecontenidotematicoList().get(2).getRACnumero().equalsIgnoreCase("3")) {

                                            setExDat(sheet, currow, x + 4, formato1.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACavanceGlobal()));
                                            setExDat(sheet, currow, x + 5, formatter.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACfechaElaboracion())); //fecha elabora 8***
                                        } else {
                                            throw new RuntimeException("No hay datos");
                                        }
                                    } catch (Exception e) {
                                        // si no realizo el reporte los pondra vacios
                                        setExDat(sheet, currow, x + 4, ""); //% avance 7**
                                        setExDat(sheet, currow, x + 5, ""); //fecha elabora 8***

                                    }
                                    setStyleCell(sheet, borderstabla, currow, x + 4);
                                    setStyleCell(sheet, borderstabla, currow, x + 5);
                                }

                                String nombreCompleto;
                                Profesor p = null;
                                // busco el coordidnador que le pertenecia a la materia segun el Programa educativo
                                for (Coordinadorareaadministrativa co : uaip.getUnidadAprendizajeUAPid().getCoordinadorareaadministrativaList()) {
                                    if (co.getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid() == pe.getPEDid()) {
                                        p = co.getProfesorPROid();
                                        break;
                                    }
                                }
                                // intento poner el nombre si no tiene entrara a la exceptio y lo pondra sin asignar
                                try {
                                    nombreCompleto = p.getPROnombre() + " " + p.getPROapellidoPaterno() + " " + p.getPROapellidoMaterno();
                                } catch (Exception e) {
                                    nombreCompleto = "SIN ASIGNAR";
                                }

                                //  profesor responsable
                                if (ract.equalsIgnoreCase("4")) {
                                    setExDat(sheet, currow, 12, nombreCompleto);
                                    setStyleCell(sheet, borderstabla, currow, 12);
                                } else {
                                    setExDat(sheet, currow, x + 2, nombreCompleto);
                                    setStyleCell(sheet, borderstabla, currow, x + 2);
                                }

                                currow++;
                            }
                        }
                    }
                }
            }
        }//fin de iteracion x reusltadoAdapter

        // hasta aqui lleno la hoja de detalle
        //aqui se exporta el excel para que inicie la descarga
        if (!criterio.equals("") && !reporte.equals("")) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            nombreLibro = nombreArchivo();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreLibro + ".xls\"");

            //genera libro
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        }
    }

    /**
     * Metodo para generar estilo de celda de excel colo con bordes
     *
     * @param borderstabla
     * @param workbook
     * @param font
     * @return
     */
    public HSSFCellStyle generarEstiloExcelBordesTabla(HSSFCellStyle borderstabla, HSSFWorkbook workbook, HSSFFont font) {
        borderstabla.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        borderstabla.setBorderTop(HSSFCellStyle.BORDER_THIN);
        borderstabla.setBorderRight(HSSFCellStyle.BORDER_THIN);
        borderstabla.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        borderstabla.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        font = workbook.createFont();
        //Seteamos la letra a arial 11
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        borderstabla.setFont(font);

        return borderstabla;
    }

    /**
     * Metodo para generar estilo de celda de excel con bordes y txto centradp
     *
     * @param bordertextoCentrado
     * @param workbook
     * @param font
     * @return
     */
    public HSSFCellStyle generarEstilocExcelBordesConCentrado(HSSFCellStyle bordertextoCentrado, HSSFWorkbook workbook, HSSFFont font) {
        bordertextoCentrado.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        bordertextoCentrado.setBorderTop(HSSFCellStyle.BORDER_THIN);
        bordertextoCentrado.setBorderRight(HSSFCellStyle.BORDER_THIN);
        bordertextoCentrado.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        bordertextoCentrado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        font = workbook.createFont();
        //Seteamos la letra a arial 11
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        bordertextoCentrado.setFont(font);

        return bordertextoCentrado;
    }

    /**
     * Metodo para generar estilo de celda de cabecera de tabla con fondo verde
     *
     * @param headerTabla
     * @param workbook
     * @param fontwit
     * @return
     */
    public HSSFCellStyle generarEstilocExcelHeaderTable(HSSFCellStyle headerTabla, HSSFWorkbook workbook, HSSFFont fontwit) {
        fontwit.setColor(HSSFColor.WHITE.index);
        fontwit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontwit.setFontHeightInPoints((short) 11);
        headerTabla.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerTabla.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerTabla.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerTabla.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerTabla.setFillForegroundColor(HSSFColor.GREEN.index);
        headerTabla.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerTabla.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerTabla.setFont(fontwit);

        return headerTabla;
    }

    public void generarExcelAreaConocimiento() throws Throwable {
        // objeto de hoja de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        String nombreLibro = "";
        HSSFFont font = workbook.createFont();
        //definimos estilo a celda titulo de los encabezados con logo escuela
        HSSFCellStyle style = workbook.createCellStyle();

        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);

        //definimos los bordes de celdas en tabla
        HSSFCellStyle borderstabla = workbook.createCellStyle();
        // gneraramos el estidlo de la celda con bordes con el sigueinte metodo generico
        borderstabla = generarEstiloExcelBordesTabla(borderstabla, workbook, font);

        // bordes con centrado
        HSSFCellStyle bordertextoCentrado = workbook.createCellStyle();
        // generamos el estilo de celda con bordes y texto centrado con
        // el siguiente metodo generico
        bordertextoCentrado = generarEstilocExcelBordesConCentrado(bordertextoCentrado, workbook, font);
        //definimos el estilo de las cabezeras de una tabla
        HSSFCellStyle headerTabla = workbook.createCellStyle();
        HSSFFont fontwit = workbook.createFont();
        // generamo el estilo para las cabeceras de las tables en color verde
        // con el siguiente metodo generico
        headerTabla = generarEstilocExcelHeaderTable(headerTabla, workbook, fontwit);

        int row = 0;
        int col = 0;

        //Se crea la hoja de Graficos que contiene la grafica de barras
        // y el resumen de la busqueda
        HSSFSheet sheet = workbook.createSheet("Graficos");

        //Le damos el estilo para la impresion de manera horizontal y mergenes
        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        sheet.setMargin(Sheet.LeftMargin, 0.3);
        sheet.setMargin(Sheet.BottomMargin, 0.6);
        sheet.setMargin(Sheet.FooterMargin, 0.3);
        sheet.setMargin(Sheet.HeaderMargin, 0.3);
        sheet.setMargin(Sheet.RightMargin, 0.3);
        sheet.setMargin(Sheet.TopMargin, 0.3);

        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 100);
        printSetup.setLandscape(true);
        printSetup.setFooterMargin(0.1);
        printSetup.setHeaderMargin(0.1);
        printSetup.setLandscape(true);

        Footer piePagina = sheet.getFooter();
        piePagina.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

        //definimos cabecera con los datos de logo, fecha actual, ciclo escolar
        //universidad, facultad, mexicali
        sheet = cabezeraGeneralExcel(sheet, style, workbook);
        //Se les da anchura a las columnas de la tabla de resumen
        sheet.setColumnWidth(1, 5800);
        sheet.setColumnWidth(2, 11350);
        sheet.setColumnWidth(3, 6800);
        sheet.setColumnWidth(4, 6800);
        sheet.setColumnWidth(5, 6800);

        //Se genera el nombre que tendra nuestro excel
        nombreLibro = nombreSegunFiltros();

        //Creacion de la tabla
        setExDat(sheet, 13, 2, "Área de conocimiento");

        //Se define encabezado por tipo de reporte
        if (reporte.equalsIgnoreCase("noentregados")) {
            setExDat(sheet, 13, 3, "Total no entregados y/o parciales");
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            setExDat(sheet, 13, 3, "Total entregados y parciales");
        } else {
            setExDat(sheet, 13, 3, "Total entregados");
        }

        setExDat(sheet, 13, 4, "Total esperados");
        setExDat(sheet, 13, 5, "Porcentaje");
        // estas lineas le dan estilo a la tabla de resumen
        setStyleCell(sheet, headerTabla, 13, 2);
        setStyleCell(sheet, headerTabla, 13, 3);
        setStyleCell(sheet, headerTabla, 13, 4);
        setStyleCell(sheet, headerTabla, 13, 5);

        //empezamos en el renglon 14 para evitar la cabezera de la hoja
        int prow = 14;

        // *** empiezo tabla de datos generales en primera hoja
        List<Areaconocimiento> AreasConocimiento = new ArrayList<>();
        // recorro la lista de entidades de resumen ya que tiene todos los datos
        // que ncesito para generar la primera hoja del excel ya que es lo mismo
        // que se muestra en pantalla
        for (ResumenReporte area : resumenesReporte) {
            // creo un nuevo programa educativo para asignar cada uno
            Areaconocimiento areaConocimiento = new Areaconocimiento();
            areaConocimiento = filtrosBeanHelper.getAreaConocimiento(area.getIdAreaConociminto());
            AreasConocimiento.add(areaConocimiento);

            // de llena la tabla de detalle con la informacion de area de conocimiento
            setExDat(sheet, prow, 2, area.getNombreAreaConocimiento());

            int entregados = 0;
            int esperados = 0;
            esperados = area.getNumEsperados();
            entregados = area.getNumEntregados();

            // se agregan los datos a la tabla
            setExDat(sheet, prow, 3, entregados);
            setExDat(sheet, prow, 4, esperados);
            // se calcula el porcentaje y se agrega
            setExDat(sheet, prow, 5, df.format(area.getPorcentaje()));
            // se les da estilo al renglon de la tabla que se agrega
            // en este caso se le da los bordes
            setStyleCell(sheet, borderstabla, prow, 2);
            setStyleCell(sheet, bordertextoCentrado, prow, 3);
            setStyleCell(sheet, bordertextoCentrado, prow, 4);
            setStyleCell(sheet, bordertextoCentrado, prow, 5);

            prow++;

        }
        //*** termino tabla de primera hoja con datos generales

        //*** mando a generar la grafica
        // el tipo grafico se deja porque en su momento se pretende que se
        // puedan generar otro tipo de graficas
        if (tipografico.equalsIgnoreCase("barras")) {
            // creo la una instance de la clase que genera el grafico
            BarChartCL demo1 = new BarChartCL("Estadísticas " + nombreLibro, "ExcelPOIGrafica1");
            // agrego la grafica al excel y mande el metodo que la crea
            demo1.addChartToExcel(demo1.crearGraficaBarrasAreaConocimiento(reporte, ract, resumenesReporte, idCicloEscolar), workbook, prow);
        }

        setExDatNegrita(sheet, 8, 1, nombreLibro);
        //aqui creamos la hoja para el programa
        boolean uno = true;
        // variable para manejarlos renglones despues de la cabezera de la hoja
        int currow = 20;

        currow++;

        //**** aqui crea una hoja por Area de conocimiento
        //e inicia llenado de generales por area
        // ** voy a reccorer los PE seleccionadoss
        for (Areaconocimiento area : AreasConocimiento) {
            currow = 15;
            // creo la hoja para el Programa educatico y su plan de estudio
            sheet = workbook.createSheet(area.getACOnombre());

            //Le damos el estilo para la impresion de manera horizontal y mergenes
            sheet.setHorizontallyCenter(true);
            sheet.setAutobreaks(true);

            sheet.setMargin(Sheet.LeftMargin, 0.3);
            sheet.setMargin(Sheet.BottomMargin, 0.3);
            sheet.setMargin(Sheet.FooterMargin, 0.3);
            sheet.setMargin(Sheet.HeaderMargin, 0.3);
            sheet.setMargin(Sheet.RightMargin, 0.3);
            sheet.setMargin(Sheet.TopMargin, 0.3);

            HSSFPrintSetup printSetupl = sheet.getPrintSetup();
            printSetupl.setFitWidth((short) 1);
            printSetupl.setFitHeight((short) 100);
            printSetupl.setLandscape(true);
            printSetupl.setFooterMargin(0.3);
            printSetupl.setHeaderMargin(0.3);
            printSetupl.setLandscape(true);

            Footer piePagina2 = sheet.getFooter();
            piePagina2.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

            row = currow + 1;
            col = 1;
            // renglon 19 de hoja de excel
            setExDat(sheet, row + 2, col, "Total de RACT Entregados");
            setExDatNegrita(sheet, 8, 1, nombreLibro);
            setExDatNegrita(sheet, 9, 2, area.getACOnombre());
            //informacion de programa educativo tabla
            setExDat(sheet, 14, 1, "Clave");
            setExDat(sheet, 14, 2, " Área de conocimiento");
            setExDat(sheet, 14, 3, "Plan de Estudios");
            setExDat(sheet, 14, 4, "Programa educativo");
            // estilo a la tabla de programa educativo
            setStyleCell(sheet, headerTabla, 14, 1);
            setStyleCell(sheet, headerTabla, 14, 2);
            setStyleCell(sheet, headerTabla, 14, 3);
            setStyleCell(sheet, headerTabla, 14, 4);
            //***llenado de informacion
            // clave de programa educativo
            setExDat(sheet, 15, 1, area.getACOclave());
            //programa educativo nombre
            setExDat(sheet, 15, 2, area.getACOnombre());
            // plan de estudios
            setExDat(sheet, 15, 3, area.getPlanEstudioPESid().getPESvigenciaPlan());
            // responsable de PE
            setExDat(sheet, 15, 4, area.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDnombre());
            // stilo a la tabla de programa educativo
            setStyleCell(sheet, borderstabla, 15, 1);
            setStyleCell(sheet, borderstabla, 15, 2);
            setStyleCell(sheet, borderstabla, 15, 3);
            setStyleCell(sheet, borderstabla, 15, 4);

            int totalEsperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorAreaConocimiento(Integer.parseInt(programaEducativoId), area.getACOid(), idCicloEscolar);
            String[] totalEntregados = new String[3];

            // hago un ciclo para obtener los entregados por cada ract
            for (int j = 1; j <= 3; j++) {
                int t = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorAreaConocimiento(reporte, Integer.parseInt(programaEducativoId), String.valueOf(j), idCicloEscolar, totalEsperados, area.getACOid());
                totalEntregados[j - 1] = String.valueOf(t);
            }

            // **** tabla de resumen de racts
            colocarTablaResumenesRact(sheet, row, col, totalEntregados, totalEsperados, headerTabla, currow, borderstabla);
            sheet = cabezeraGeneralExcel(sheet, style, workbook);
            currow = currow + 7;
            currow = 25;

            // ***** termino los datos generales del
            // por cada area de conocimiento traigo sus uaip
            //prueba
            sheet.setColumnWidth(1, 5800);
            sheet.setColumnWidth(2, 14100);
            sheet.setColumnWidth(3, 7250);
            sheet.setColumnWidth(4, 10200);
            sheet.setColumnWidth(5, 3900);
            sheet.setColumnWidth(6, 5600);
            sheet.setColumnWidth(7, 7200);
            sheet.setColumnWidth(9, 7200);
            sheet.setColumnWidth(10, 5600);
            sheet.setColumnWidth(11, 7200);
            sheet.setColumnWidth(12, 10000);
            //** si el area tiene materias se agrega si no, no

            //  ********* aqui empiezo a llenar  el  detalle
            // *** lleno la tabla del area de conocimiento
            currow += 3;
            setExDat(sheet, currow, 1, "Clave");
            setExDat(sheet, currow, 2, "Área de conocimiento");

            setStyleCell(sheet, headerTabla, currow, 1);
            setStyleCell(sheet, headerTabla, currow, 2);
            setStyleCell(sheet, headerTabla, currow, 3);

            currow++;
            setExDat(sheet, currow, 1, area.getACOclave()); //clave

            setExDat(sheet, currow, 2, area.getACOnombre()); //area conociminto
            setStyleCell(sheet, borderstabla, currow, 1);
            setStyleCell(sheet, borderstabla, currow, 2);

            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorAreaConocimiento(reporte, area.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid(), idCicloEscolar, area.getACOid(), ract, area.getPlanEstudioPESid().getPESid());
            sheet = llenadoDetalleUAIPS(uaips, sheet, currow, headerTabla, borderstabla, Integer.parseInt(programaEducativoId));

        }//fin de iteracion x reusltadoAdapter

        // ***** hasta aqui lleno la hoja de detalle
        //aqui se exporta el excel para que inicie la descarga en el siguiente metodo generico
        exportarReporteExcel(nombreLibro, workbook);

    }

    public void generarExcelAreaAdministrativa() throws Throwable {
        // objeto de hoja de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        String nombreLibro = "";
        HSSFFont font = workbook.createFont();
        //definimos estilo a celda titulo de los encabezados con logo escuela
        HSSFCellStyle style = workbook.createCellStyle();

        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);

        //definimos los bordes de celdas en tabla
        HSSFCellStyle borderstabla = workbook.createCellStyle();
        // gneraramos el estidlo de la celda con bordes con el sigueinte metodo generico
        borderstabla = generarEstiloExcelBordesTabla(borderstabla, workbook, font);

        // bordes con centrado
        HSSFCellStyle bordertextoCentrado = workbook.createCellStyle();
        // generamos el estilo de celda con bordes y texto centrado con
        // el siguiente metodo generico
        bordertextoCentrado = generarEstilocExcelBordesConCentrado(bordertextoCentrado, workbook, font);
        //definimos el estilo de las cabezeras de una tabla
        HSSFCellStyle headerTabla = workbook.createCellStyle();
        HSSFFont fontwit = workbook.createFont();
        // generamo el estilo para las cabeceras de las tables en color verde
        // con el siguiente metodo generico
        headerTabla = generarEstilocExcelHeaderTable(headerTabla, workbook, fontwit);

        int row = 0;
        int col = 0;

        //Se crea la hoja de Graficos que contiene la grafica de barras
        // y el resumen de la busqueda
        HSSFSheet sheet = workbook.createSheet("Graficos");

        //Le damos el estilo para la impresion de manera horizontal y mergenes
        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        sheet.setMargin(Sheet.LeftMargin, 0.3);
        sheet.setMargin(Sheet.BottomMargin, 0.3);
        sheet.setMargin(Sheet.FooterMargin, 0.3);
        sheet.setMargin(Sheet.HeaderMargin, 0.3);
        sheet.setMargin(Sheet.RightMargin, 0.3);
        sheet.setMargin(Sheet.TopMargin, 0.3);

        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 100);
        printSetup.setLandscape(true);
        printSetup.setFooterMargin(0.1);
        printSetup.setHeaderMargin(0.1);
        printSetup.setLandscape(true);

        Footer piePagina = sheet.getFooter();
        piePagina.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

        //definimos cabecera con los datos de logo, fecha actual, ciclo escolar
        //universidad, facultad, mexicali
        sheet = cabezeraGeneralExcel(sheet, style, workbook);
        //Se les da anchura a las columnas de la tabla de resumen
        sheet.setColumnWidth(1, 5800);
        sheet.setColumnWidth(2, 11350);
        sheet.setColumnWidth(3, 6800);
        sheet.setColumnWidth(4, 6800);
        sheet.setColumnWidth(5, 6800);

        //Se genera el nombre que tendra nuestro excel
        nombreLibro = nombreSegunFiltros();

        //Creacion de la tabla
        setExDat(sheet, 13, 2, "Área de administrativa");

        //Se define encabezado por tipo de reporte
        if (reporte.equalsIgnoreCase("noentregados")) {
            setExDat(sheet, 13, 3, "Total no entregados y/o parciales");
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            setExDat(sheet, 13, 3, "Total entregados y parciales");
        } else {
            setExDat(sheet, 13, 3, "Total entregados");
        }

        setExDat(sheet, 13, 4, "Total esperados");
        setExDat(sheet, 13, 5, "Porcentaje");
        // estas lineas le dan estilo a la tabla de resumen
        setStyleCell(sheet, headerTabla, 13, 2);
        setStyleCell(sheet, headerTabla, 13, 3);
        setStyleCell(sheet, headerTabla, 13, 4);
        setStyleCell(sheet, headerTabla, 13, 5);

        //empezamos en el renglon 14 para evitar la cabezera de la hoja
        int prow = 14;

        // *** empiezo tabla de datos generales en primera hoja
        List<Areaadministrativa> areasAdministrativas = new ArrayList<>();
        // recorro la lista de entidades de resumen ya que tiene todos los datos
        // que ncesito para generar la primera hoja del excel ya que es lo mismo
        // que se muestra en pantalla
        for (ResumenReporte area : resumenesReporte) {
            // creo un nuevo programa educativo para asignar cada uno
            Areaadministrativa areaAdministrativa = new Areaadministrativa();
            areaAdministrativa = filtrosBeanHelper.getAreaAdministrativa(area.getIdAreaAdministrativa());
            areasAdministrativas.add(areaAdministrativa);

            // de llena la tabla de detalle con la informacion de area de conocimiento
            setExDat(sheet, prow, 2, area.getNombreAreaAdministrativa());

            int entregados = 0;
            int esperados = 0;
            esperados = area.getNumEsperados();
            entregados = area.getNumEntregados();

            // se agregan los datos a la tabla
            setExDat(sheet, prow, 3, entregados);
            setExDat(sheet, prow, 4, esperados);
            // se calcula el porcentaje y se agrega
            setExDat(sheet, prow, 5, df.format(area.getPorcentaje()));
            // se les da estilo al renglon de la tabla que se agrega
            // en este caso se le da los bordes
            setStyleCell(sheet, borderstabla, prow, 2);
            setStyleCell(sheet, bordertextoCentrado, prow, 3);
            setStyleCell(sheet, bordertextoCentrado, prow, 4);
            setStyleCell(sheet, bordertextoCentrado, prow, 5);

            prow++;

        }
        //*** termino tabla de primera hoja con datos generales

        //*** mando a generar la grafica
        // el tipo grafico se deja porque en su momento se pretende que se
        // puedan generar otro tipo de graficas
        if (tipografico.equalsIgnoreCase("barras")) {
            // creo la una instance de la clase que genera el grafico
            BarChartCL demo1 = new BarChartCL("Estadísticas " + nombreLibro, "ExcelPOIGrafica1");
            // agrego la grafica al excel y mande el metodo que la crea
            demo1.addChartToExcel(demo1.crearGraficaBarrasAreaAdministrativa(reporte, ract, resumenesReporte, idCicloEscolar), workbook, prow);
        }

        setExDatNegrita(sheet, 8, 1, nombreLibro);
        //aqui creamos la hoja para el programa
        boolean uno = true;
        // variable para manejarlos renglones despues de la cabezera de la hoja
        int currow = 20;

        currow++;

        //**** aqui crea una hoja por Area de conocimiento
        //e inicia llenado de generales por area
        // ** voy a reccorer los PE seleccionadoss
        for (Areaadministrativa area : areasAdministrativas) {

            currow = 15;
            // creo la hoja para el Programa educatico y su plan de estudio
            sheet = workbook.createSheet(area.getAADnombre());

            //Le damos el estilo para la impresion de manera horizontal y mergenes
            sheet.setHorizontallyCenter(true);
            sheet.setAutobreaks(true);

            sheet.setMargin(Sheet.LeftMargin, 0.3);
            sheet.setMargin(Sheet.BottomMargin, 0.3);
            sheet.setMargin(Sheet.FooterMargin, 0.3);
            sheet.setMargin(Sheet.HeaderMargin, 0.3);
            sheet.setMargin(Sheet.RightMargin, 0.3);
            sheet.setMargin(Sheet.TopMargin, 0.3);

            HSSFPrintSetup printSetupl = sheet.getPrintSetup();
            printSetupl.setFitWidth((short) 1);
            printSetupl.setFitHeight((short) 100);
            printSetupl.setLandscape(true);
            printSetupl.setFooterMargin(0.3);
            printSetupl.setHeaderMargin(0.3);
            printSetupl.setLandscape(true);

            Footer piePagina2 = sheet.getFooter();
            piePagina2.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

            row = currow + 1;
            col = 1;
            // renglon 19 de hoja de excel
            setExDat(sheet, row + 2, col, "Total de RACT Entregados");
            setExDatNegrita(sheet, 8, 1, nombreLibro);
            setExDatNegrita(sheet, 9, 2, area.getAADnombre());
            //informacion de programa educativo tabla
            setExDat(sheet, 14, 1, "Área de administrativa");
            setExDat(sheet, 14, 2, "Responsable ");
            setExDat(sheet, 14, 3, "Programa educativo");

            // estilo a la tabla de programa educativo
            setStyleCell(sheet, headerTabla, 14, 1);
            setStyleCell(sheet, headerTabla, 14, 2);
            setStyleCell(sheet, headerTabla, 14, 3);

            //***llenado de informacion
            // nombre de area administrativa
            setExDat(sheet, 15, 1, area.getAADnombre());
            //Responsable del area
            // si no tiene pondra sin asignar
            String responsableArea;
            try {
                responsableArea = area.getCoordinadorareaadministrativaList().get(0).getProfesorPROid().getPROnombre() + " " + area.getCoordinadorareaadministrativaList().get(0).getProfesorPROid().getPROapellidoMaterno()
                        + " " + area.getCoordinadorareaadministrativaList().get(0).getProfesorPROid().getPROapellidoPaterno();
            } catch (Exception e) {
                responsableArea = "Sin asignar";
            }

            setExDat(sheet, 15, 2, responsableArea);
            // programa educativo
            setExDat(sheet, 15, 3, area.getProgramaEducativoPEDid().getPEDnombre());

            // stilo a la tabla de programa educativo
            setStyleCell(sheet, borderstabla, 15, 1);
            setStyleCell(sheet, borderstabla, 15, 2);
            setStyleCell(sheet, borderstabla, 15, 3);

            int totalEsperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorAreaAdministrativa(Integer.parseInt(programaEducativoId), area.getAADid(), idCicloEscolar);
            String[] totalEntregados = new String[3];

            // hago un ciclo para obtener los entregados por cada ract
            for (int j = 1; j <= 3; j++) {
                int t = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorAreaAdministrativa(reporte, Integer.parseInt(programaEducativoId), String.valueOf(j), idCicloEscolar, totalEsperados, area.getAADid());
                totalEntregados[j - 1] = String.valueOf(t);
            }

            // **** tabla de resumen de racts
            colocarTablaResumenesRact(sheet, row, col, totalEntregados, totalEsperados, headerTabla, currow, borderstabla);
            sheet = cabezeraGeneralExcel(sheet, style, workbook);
            currow = currow + 7;
            currow = 25;

            // ***** termino los datos generales del
            // por cada area de conocimiento traigo sus uaip
            //prueba
//
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            sheet.setColumnWidth(1, 5800);
            sheet.setColumnWidth(2, 14100);
            sheet.setColumnWidth(3, 11000);
            sheet.setColumnWidth(4, 10200);
            sheet.setColumnWidth(5, 7250);
            sheet.setColumnWidth(6, 5600);
            sheet.setColumnWidth(7, 7200);
            sheet.setColumnWidth(9, 7200);
            sheet.setColumnWidth(10, 5600);
            sheet.setColumnWidth(11, 7200);
            sheet.setColumnWidth(12, 10500);
            //** si el area tiene materias se agrega si no, no

            //  ********* aqui empiezo a llenar  el  detalle
            // *** lleno la tabla del area de conocimiento
            currow += 3;
            setExDat(sheet, currow, 1, "Área Administrativa");
            setExDat(sheet, currow, 2, "Responsable");

            setStyleCell(sheet, headerTabla, currow, 1);
            setStyleCell(sheet, headerTabla, currow, 2);
            setStyleCell(sheet, headerTabla, currow, 3);

            currow++;
            setExDat(sheet, currow, 1, area.getAADnombre()); //nombre del area

            setExDat(sheet, currow, 2, responsableArea); //responsable
            setStyleCell(sheet, borderstabla, currow, 1);
            setStyleCell(sheet, borderstabla, currow, 2);
            uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorAreaAdministrativa(reporte, Integer.parseInt(programaEducativoId), area.getAADid(), idCicloEscolar, ract);
            sheet = llenadoDetalleUAIPS(uaips, sheet, currow, headerTabla, borderstabla, Integer.parseInt(programaEducativoId));

        }//fin de iteracion x reusltadoAdapter

        // ***** hasta aqui lleno la hoja de detalle
        //aqui se exporta el excel para que inicie la descarga en el siguiente metodo generico
        exportarReporteExcel(nombreLibro, workbook);

    }

    public void generarExcelUnidadAprendizaje() throws Throwable {
        // objeto de hoja de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        String nombreLibro = "";
        HSSFFont font = workbook.createFont();
        //definimos estilo a celda titulo de los encabezados con logo escuela
        HSSFCellStyle style = workbook.createCellStyle();

        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);

        //definimos los bordes de celdas en tabla
        HSSFCellStyle borderstabla = workbook.createCellStyle();
        // gneraramos el estidlo de la celda con bordes con el sigueinte metodo generico
        borderstabla = generarEstiloExcelBordesTabla(borderstabla, workbook, font);

        // bordes con centrado
        HSSFCellStyle bordertextoCentrado = workbook.createCellStyle();
        // generamos el estilo de celda con bordes y texto centrado con
        // el siguiente metodo generico
        bordertextoCentrado = generarEstilocExcelBordesConCentrado(bordertextoCentrado, workbook, font);
        //definimos el estilo de las cabezeras de una tabla
        HSSFCellStyle headerTabla = workbook.createCellStyle();
        HSSFFont fontwit = workbook.createFont();
        // generamo el estilo para las cabeceras de las tables en color verde
        // con el siguiente metodo generico
        headerTabla = generarEstilocExcelHeaderTable(headerTabla, workbook, fontwit);

        int row = 0;
        int col = 0;

        //Se crea la hoja de Graficos que contiene la grafica de barras
        // y el resumen de la busqueda
        HSSFSheet sheet = workbook.createSheet("Graficos");

        //Le damos el estilo para la impresion de manera horizontal y mergenes
        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        sheet.setMargin(Sheet.LeftMargin, 0.3);
        sheet.setMargin(Sheet.BottomMargin, 0.3);
        sheet.setMargin(Sheet.FooterMargin, 0.3);
        sheet.setMargin(Sheet.HeaderMargin, 0.3);
        sheet.setMargin(Sheet.RightMargin, 0.3);
        sheet.setMargin(Sheet.TopMargin, 0.3);

        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 100);
        printSetup.setLandscape(true);
        printSetup.setFooterMargin(0.1);
        printSetup.setHeaderMargin(0.1);
        printSetup.setLandscape(true);

        Footer piePagina = sheet.getFooter();
        piePagina.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

        //definimos cabecera con los datos de logo, fecha actual, ciclo escolar
        //universidad, facultad, mexicali
        sheet = cabezeraGeneralExcel(sheet, style, workbook);
        //Se les da anchura a las columnas de la tabla de resumen
        sheet.setColumnWidth(1, 5800);
        sheet.setColumnWidth(2, 11350);
        sheet.setColumnWidth(3, 6800);
        sheet.setColumnWidth(4, 6800);
        sheet.setColumnWidth(5, 6800);

        //Se genera el nombre que tendra nuestro excel
        nombreLibro = nombreSegunFiltros();

        //Creacion de la tabla
        setExDat(sheet, 13, 2, "Unidad de aprendizaje");

        //Se define encabezado por tipo de reporte
        if (reporte.equalsIgnoreCase("noentregados")) {
            setExDat(sheet, 13, 3, "Total no entregados y/o parciales");
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            setExDat(sheet, 13, 3, "Total entregados y parciales");
        } else {
            setExDat(sheet, 13, 3, "Total entregados");
        }

        setExDat(sheet, 13, 4, "Total esperados");
        setExDat(sheet, 13, 5, "Porcentaje");
        // estas lineas le dan estilo a la tabla de resumen
        setStyleCell(sheet, headerTabla, 13, 2);
        setStyleCell(sheet, headerTabla, 13, 3);
        setStyleCell(sheet, headerTabla, 13, 4);
        setStyleCell(sheet, headerTabla, 13, 5);

        //empezamos en el renglon 14 para evitar la cabezera de la hoja
        int prow = 14;

        // *** empiezo tabla de datos generales en primera hoja
        List<Unidadaprendizaje> unidadesAprendizaje = new ArrayList<>();
        // recorro la lista de entidades de resumen ya que tiene todos los datos
        // que ncesito para generar la primera hoja del excel ya que es lo mismo
        // que se muestra en pantalla
        for (ResumenReporte unidad : resumenesReporte) {
            // creo un nuevo unidadaprendizaje para asignar cada uno
            Unidadaprendizaje unidadAprendizaje = new Unidadaprendizaje();
            unidadAprendizaje = filtrosBeanHelper.getUnidadAprendizaje(unidad.getIdUnidadAprendizaje());
            unidadesAprendizaje.add(unidadAprendizaje);

            // de llena la tabla de detalle con la informacion de area de conocimiento
            setExDat(sheet, prow, 2, unidad.getNombreUnidadAprendizaje());

            int entregados = 0;
            int esperados = 0;
            esperados = unidad.getNumEsperados();
            entregados = unidad.getNumEntregados();

            // se agregan los datos a la tabla
            setExDat(sheet, prow, 3, entregados);
            setExDat(sheet, prow, 4, esperados);
            // se calcula el porcentaje y se agrega
            setExDat(sheet, prow, 5, df.format(unidad.getPorcentaje()));
            // se les da estilo al renglon de la tabla que se agrega
            // en este caso se le da los bordes
            setStyleCell(sheet, borderstabla, prow, 2);
            setStyleCell(sheet, bordertextoCentrado, prow, 3);
            setStyleCell(sheet, bordertextoCentrado, prow, 4);
            setStyleCell(sheet, bordertextoCentrado, prow, 5);

            prow++;
        }

        //*** termino tabla de primera hoja con datos generales
        //*** mando a generar la grafica
        // el tipo grafico se deja porque en su momento se pretende que se
        // puedan generar otro tipo de graficas
        if (tipografico.equalsIgnoreCase("barras")) {
            // creo la una instance de la clase que genera el grafico
            BarChartCL demo1 = new BarChartCL("Estadísticas " + nombreLibro, "ExcelPOIGrafica1");
            // agrego la grafica al excel y mande el metodo que la crea
            demo1.addChartToExcel(demo1.crearGraficaBarrasUnidadAprendizaje(reporte, ract, resumenesReporte, idCicloEscolar), workbook, prow);
        }

        setExDatNegrita(sheet, 8, 1, nombreLibro);
        //aqui creamos la hoja para el programa
        boolean uno = true;
        // variable para manejarlos renglones despues de la cabezera de la hoja
        int currow = 20;

        currow++;

        //**** aqui crea una hoja por unidad de aprendizaje
        //e inicia llenado de generales por area
        // ** voy a reccorer los PE seleccionadoss
        for (Unidadaprendizaje unidad : unidadesAprendizaje) {
            currow = 15;

            // creo la hoja para la unidad de aprendizaje
            try {
                sheet = workbook.createSheet(unidad.getUAPnombre());
            } catch (Exception e) {
                sheet = workbook.createSheet(String.valueOf(unidad.getUAPclave()));
            }

            //Le damos el estilo para la impresion de manera horizontal y mergenes
            sheet.setHorizontallyCenter(true);
            sheet.setAutobreaks(true);

            sheet.setMargin(Sheet.LeftMargin, 0.3);
            sheet.setMargin(Sheet.BottomMargin, 0.3);
            sheet.setMargin(Sheet.FooterMargin, 0.3);
            sheet.setMargin(Sheet.HeaderMargin, 0.3);
            sheet.setMargin(Sheet.RightMargin, 0.3);
            sheet.setMargin(Sheet.TopMargin, 0.3);

            HSSFPrintSetup printSetupl = sheet.getPrintSetup();
            printSetupl.setFitWidth((short) 1);
            printSetupl.setFitHeight((short) 100);
            printSetupl.setLandscape(true);
            printSetupl.setFooterMargin(0.3);
            printSetupl.setHeaderMargin(0.3);
            printSetupl.setLandscape(true);

            Footer piePagina2 = sheet.getFooter();
            piePagina2.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

            row = currow + 1;
            col = 1;
            // renglon 19 de hoja de excel
            setExDat(sheet, row + 2, col, "Total de RACT Entregados");
            setExDatNegrita(sheet, 8, 1, nombreLibro);
            setExDatNegrita(sheet, 9, 2, unidad.getUAPnombre());
            //informacion de programa educativo tabla
            setExDat(sheet, 14, 1, "Clave");
            setExDat(sheet, 14, 2, "Unidad de aprendizaje ");
            setExDat(sheet, 14, 3, "Plan de estudios");
            setExDat(sheet, 14, 4, "Responsable");

            // estilo a la tabla de programa educativo
            setStyleCell(sheet, headerTabla, 14, 1);
            setStyleCell(sheet, headerTabla, 14, 2);
            setStyleCell(sheet, headerTabla, 14, 3);
            setStyleCell(sheet, headerTabla, 14, 4);

            //***llenado de informacion
            // clave unidad
            setExDat(sheet, 15, 1, unidad.getUAPclave());

            setExDat(sheet, 15, 2, unidad.getUAPnombre());
            // plan de estudios
            Programaeducativo programa = filtrosBeanHelper.getProgramaeducativo(Integer.parseInt(programaEducativoId));
            Planestudio planUnidad = new Planestudio();

            for (Areaconocimiento area : unidad.getAreaconocimientoList()) {
                Planestudio plan = area.getPlanEstudioPESid();
                for (Planestudio p : programa.getPlanestudioList()) {
                    if (p.getPESid() == plan.getPESid()) {
                        planUnidad = p;
                        break;

                    }
                }
            }

            setExDat(sheet, 15, 3, planUnidad.getPESvigenciaPlan());
            //Responsable de la unidad
            // si no tiene pondra sin asignar
            String nombreCompleto;
            Profesor p = null;

            // busco el coordidnador que le pertenecia a la materia segun el Programa educativo
            for (Coordinadorareaadministrativa co : unidad.getCoordinadorareaadministrativaList()) {
                if (co.getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid() == Integer.parseInt(programaEducativoId)) {
                    p = co.getProfesorPROid();
                    break;
                }
            }

            // intento poner el nombre si no tiene entrara a la exceptio y lo pondra sin asignar
            try {
                nombreCompleto = p.getPROnombre() + " " + p.getPROapellidoPaterno() + " " + p.getPROapellidoMaterno();

            } catch (Exception e) {
                nombreCompleto = "SIN ASIGNAR";
            }

            setExDat(sheet, 15, 4, nombreCompleto);
            // stilo a la tabla de programa educativo
            setStyleCell(sheet, borderstabla, 15, 1);
            setStyleCell(sheet, borderstabla, 15, 2);
            setStyleCell(sheet, borderstabla, 15, 3);
            setStyleCell(sheet, borderstabla, 15, 4);

            int totalEsperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorUnidadAprendizaje(unidad.getUAPid(), Integer.parseInt(programaEducativoId), idCicloEscolar);
            String[] totalEntregados = new String[3];

            // hago un ciclo para obtener los entregados por cada ract
            for (int j = 1; j <= 3; j++) {
                int t = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorUnidadAprendizaje(reporte, Integer.parseInt(programaEducativoId), String.valueOf(j), idCicloEscolar, totalEsperados, unidad.getUAPid());
                totalEntregados[j - 1] = String.valueOf(t);
            }

            // **** tabla de resumen de racts
            colocarTablaResumenesRact(sheet, row, col, totalEntregados, totalEsperados, headerTabla, currow, borderstabla);
            sheet = cabezeraGeneralExcel(sheet, style, workbook);
            currow = currow + 7;
            currow = 25;

            // ***** termino los datos generales del
            // por cada area de conocimiento traigo sus uaip
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            sheet.setColumnWidth(1, 5800);
            sheet.setColumnWidth(2, 14100);
            sheet.setColumnWidth(3, 7250);
            sheet.setColumnWidth(4, 10200);
            sheet.setColumnWidth(5, 7250);
            sheet.setColumnWidth(6, 5600);
            sheet.setColumnWidth(7, 7200);
            sheet.setColumnWidth(9, 7200);
            sheet.setColumnWidth(10, 5600);
            sheet.setColumnWidth(11, 7200);
            sheet.setColumnWidth(12, 10500);
            //** si el area tiene materias se agrega si no, no

            //  ********* aqui empiezo a llenar  el  detalle
            // *** lleno la tabla del area de conocimiento
            currow += 3;
            setExDat(sheet, currow, 1, "Clave");
            setExDat(sheet, currow, 2, "Unidad de aprendizaje");

            setStyleCell(sheet, headerTabla, currow, 1);
            setStyleCell(sheet, headerTabla, currow, 2);
            setStyleCell(sheet, headerTabla, currow, 3);

            currow++;
            setExDat(sheet, currow, 1, unidad.getUAPclave()); //Clave

            setExDat(sheet, currow, 2, unidad.getUAPnombre()); //nombre
            setStyleCell(sheet, borderstabla, currow, 1);
            setStyleCell(sheet, borderstabla, currow, 2);
            uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorUnidadAprendizaje(reporte, Integer.parseInt(programaEducativoId), unidad.getUAPid(), idCicloEscolar, ract);
            sheet = llenadoDetalleUAIPS(uaips, sheet, currow, headerTabla, borderstabla, Integer.parseInt(programaEducativoId));

        }//fin de iteracion x reusltadoAdapter

        // ***** hasta aqui lleno la hoja de detalle
        //aqui se exporta el excel para que inicie la descarga en el siguiente metodo generico
        exportarReporteExcel(nombreLibro, workbook);

    }

    public void generarExcelProfesor() throws Throwable {
        // objeto de hoja de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        String nombreLibro = "";
        HSSFFont font = workbook.createFont();
        //definimos estilo a celda titulo de los encabezados con logo escuela
        HSSFCellStyle style = workbook.createCellStyle();

        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);

        //definimos los bordes de celdas en tabla
        HSSFCellStyle borderstabla = workbook.createCellStyle();
        // gneraramos el estidlo de la celda con bordes con el sigueinte metodo generico
        borderstabla = generarEstiloExcelBordesTabla(borderstabla, workbook, font);

        // bordes con centrado
        HSSFCellStyle bordertextoCentrado = workbook.createCellStyle();
        // generamos el estilo de celda con bordes y texto centrado con
        // el siguiente metodo generico
        bordertextoCentrado = generarEstilocExcelBordesConCentrado(bordertextoCentrado, workbook, font);
        //definimos el estilo de las cabezeras de una tabla
        HSSFCellStyle headerTabla = workbook.createCellStyle();
        HSSFFont fontwit = workbook.createFont();
        // generamo el estilo para las cabeceras de las tables en color verde
        // con el siguiente metodo generico
        headerTabla = generarEstilocExcelHeaderTable(headerTabla, workbook, fontwit);

        int row = 0;
        int col = 0;

        //Se crea la hoja de Graficos que contiene la grafica de barras
        // y el resumen de la busqueda
        HSSFSheet sheet = workbook.createSheet("Graficos");

        //Le damos el estilo para la impresion de manera horizontal y mergenes
        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        sheet.setMargin(Sheet.LeftMargin, 0.3);
        sheet.setMargin(Sheet.BottomMargin, 0.3);
        sheet.setMargin(Sheet.FooterMargin, 0.3);
        sheet.setMargin(Sheet.HeaderMargin, 0.3);
        sheet.setMargin(Sheet.RightMargin, 0.3);
        sheet.setMargin(Sheet.TopMargin, 0.3);

        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 100);
        printSetup.setLandscape(true);
        printSetup.setFooterMargin(0.1);
        printSetup.setHeaderMargin(0.1);
        printSetup.setLandscape(true);

        Footer piePagina = sheet.getFooter();
        piePagina.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

        //definimos cabecera con los datos de logo, fecha actual, ciclo escolar
        //universidad, facultad, mexicali
        sheet = cabezeraGeneralExcel(sheet, style, workbook);
        //Se les da anchura a las columnas de la tabla de resumen
        sheet.setColumnWidth(1, 5800);
        sheet.setColumnWidth(2, 11350);
        sheet.setColumnWidth(3, 6800);
        sheet.setColumnWidth(4, 6800);
        sheet.setColumnWidth(5, 6800);

        //Se genera el nombre que tendra nuestro excel
        nombreLibro = nombreSegunFiltros();

        //Creacion de la tabla
        setExDat(sheet, 13, 2, "Profesor");

        //Se define encabezado por tipo de reporte
        if (reporte.equalsIgnoreCase("noentregados")) {
            setExDat(sheet, 13, 3, "Total no entregados y/o parciales");
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            setExDat(sheet, 13, 3, "Total entregados y parciales");
        } else {
            setExDat(sheet, 13, 3, "Total entregados");
        }

        setExDat(sheet, 13, 4, "Total esperados");
        setExDat(sheet, 13, 5, "Porcentaje");
        // estas lineas le dan estilo a la tabla de resumen
        setStyleCell(sheet, headerTabla, 13, 2);
        setStyleCell(sheet, headerTabla, 13, 3);
        setStyleCell(sheet, headerTabla, 13, 4);
        setStyleCell(sheet, headerTabla, 13, 5);

        //empezamos en el renglon 14 para evitar la cabezera de la hoja
        int prow = 14;
        String nombreProfesor = "";
        // *** empiezo tabla de datos generales en primera hoja
        List<Profesor> profesores = new ArrayList<>();
        // recorro la lista de entidades de resumen ya que tiene todos los datos
        // que ncesito para generar la primera hoja del excel ya que es lo mismo
        // que se muestra en pantalla
        for (ResumenReporte profe : resumenesReporte) {
            // creo un nuevo programa educativo para asignar cada uno
            Profesor profesor = new Profesor();
            profesor = filtrosBeanHelper.getProfesor(profe.getIdProfesor());
            profesores.add(profesor);
            nombreProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoPaterno();
            // de llena la tabla de detalle con la informacion de area de conocimiento
            setExDat(sheet, prow, 2, nombreProfesor);

            int entregados = 0;
            int esperados = 0;
            esperados = profe.getNumEsperados();
            entregados = profe.getNumEntregados();

            // se agregan los datos a la tabla
            setExDat(sheet, prow, 3, entregados);
            setExDat(sheet, prow, 4, esperados);
            // se calcula el porcentaje y se agrega
            setExDat(sheet, prow, 5, df.format(profe.getPorcentaje()));
            // se les da estilo al renglon de la tabla que se agrega
            // en este caso se le da los bordes
            setStyleCell(sheet, borderstabla, prow, 2);
            setStyleCell(sheet, bordertextoCentrado, prow, 3);
            setStyleCell(sheet, bordertextoCentrado, prow, 4);
            setStyleCell(sheet, bordertextoCentrado, prow, 5);

            prow++;

        }
        //*** termino tabla de primera hoja con datos generales

        //*** mando a generar la grafica
        // el tipo grafico se deja porque en su momento se pretende que se
        // puedan generar otro tipo de graficas
        if (tipografico.equalsIgnoreCase("barras")) {
            // creo la una instance de la clase que genera el grafico
            BarChartCL demo1 = new BarChartCL("Estadísticas " + nombreLibro, "ExcelPOIGrafica1");
            // agrego la grafica al excel y mande el metodo que la crea
            demo1.addChartToExcel(demo1.crearGraficaBarrasProfesor(reporte, ract, resumenesReporte, idCicloEscolar), workbook, prow);
        }

        setExDatNegrita(sheet, 8, 1, nombreLibro);
        //aqui creamos la hoja para el programa
        boolean uno = true;
        // variable para manejarlos renglones despues de la cabezera de la hoja
        int currow = 20;

        currow++;

        //**** aqui crea una hoja por Area de conocimiento
        //e inicia llenado de generales por area
        // ** voy a reccorer los PE seleccionadoss
        for (Profesor profesor : profesores) {
            nombreProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoPaterno();

            currow = 15;
            // creo la hoja para el Programa educatico y su plan de estudio
            sheet = workbook.createSheet(profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno());

            //Le damos el estilo para la impresion de manera horizontal y mergenes
            sheet.setHorizontallyCenter(true);
            sheet.setAutobreaks(true);

            sheet.setMargin(Sheet.LeftMargin, 0.3);
            sheet.setMargin(Sheet.BottomMargin, 0.3);
            sheet.setMargin(Sheet.FooterMargin, 0.3);
            sheet.setMargin(Sheet.HeaderMargin, 0.3);
            sheet.setMargin(Sheet.RightMargin, 0.3);
            sheet.setMargin(Sheet.TopMargin, 0.3);

            HSSFPrintSetup printSetupl = sheet.getPrintSetup();
            printSetupl.setFitWidth((short) 1);
            printSetupl.setFitHeight((short) 100);
            printSetupl.setLandscape(true);
            printSetupl.setFooterMargin(0.3);
            printSetupl.setHeaderMargin(0.3);
            printSetupl.setLandscape(true);

            Footer piePagina2 = sheet.getFooter();
            piePagina2.setRight("Page " + HeaderFooter.page() + " de " + HeaderFooter.numPages());

            row = currow + 1;
            col = 1;
            // renglon 19 de hoja de excel
            setExDat(sheet, row + 2, col, "Total de RACT Entregados");
            setExDatNegrita(sheet, 8, 1, nombreLibro);
            setExDatNegrita(sheet, 9, 2, nombreProfesor);
            //informacion de programa educativo tabla
            setExDat(sheet, 14, 1, "Número de empleado");
            setExDat(sheet, 14, 2, "Nombre profesor");

            // estilo a la tabla de programa educativo
            setStyleCell(sheet, headerTabla, 14, 1);
            setStyleCell(sheet, headerTabla, 14, 2);

            //***llenado de informacion
            // nombre de area administrativa
            setExDat(sheet, 15, 1, profesor.getPROnumeroEmpleado());
            //Responsable del area
            // si no tiene pondra sin asignar

            setExDat(sheet, 15, 2, nombreProfesor);
            // programa educativo

            // stilo a la tabla de programa educativo
            setStyleCell(sheet, borderstabla, 15, 1);
            setStyleCell(sheet, borderstabla, 15, 2);
            int totalEsperados;

            if (isCordinadorAreaAdmin) {
                totalEsperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProfesorCoordinador(profesor.getPROid(), idCicloEscolar, Integer.parseInt(programaEducativoId), idProfesorCoordinador);
            } else {
                totalEsperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProfesor(profesor.getPROid(), idCicloEscolar, Integer.parseInt(programaEducativoId), isAdmin);
            }

            String[] totalEntregados = new String[3];
            // hago un ciclo para obtener los entregados por cada ract
            for (int j = 1; j <= 3; j++) {
                int t;
                if (isCordinadorAreaAdmin) {
                    t = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().conteoPorProfesorCoordinador(reporte, Integer.parseInt(programaEducativoId), String.valueOf(j), idCicloEscolar, totalEsperados, idProfesorCoordinador, profesor.getPROid());
                    totalEntregados[j - 1] = String.valueOf(t);
                } else {
                    t = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().ConteoPorProfesor(reporte, Integer.parseInt(programaEducativoId), String.valueOf(j), idCicloEscolar, totalEsperados, isAdmin, profesor.getPROid());
                    totalEntregados[j - 1] = String.valueOf(t);
                }

            }
            // **** tabla de resumen de racts
            colocarTablaResumenesRact(sheet, row, col, totalEntregados, totalEsperados, headerTabla, currow, borderstabla);
            sheet = cabezeraGeneralExcel(sheet, style, workbook);
            currow = currow + 7;
            currow = 25;

            // ***** termino los datos generales del
            // por cada area de conocimiento traigo sus uaip
            //prueba
            List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
            sheet.setColumnWidth(1, 5800);
            sheet.setColumnWidth(2, 14100);
            sheet.setColumnWidth(3, 11000);
            sheet.setColumnWidth(4, 10200);
            sheet.setColumnWidth(5, 7250);
            sheet.setColumnWidth(6, 5600);
            sheet.setColumnWidth(7, 7200);
            sheet.setColumnWidth(9, 7200);
            sheet.setColumnWidth(10, 5600);
            sheet.setColumnWidth(11, 7200);
            sheet.setColumnWidth(12, 10500);
            //** si el area tiene materias se agrega si no, no

            //  ********* aqui empiezo a llenar  el  detalle
            // *** lleno la tabla del area de conocimiento
            currow += 3;
            setExDat(sheet, currow, 1, "Número de empleado");
            setExDat(sheet, currow, 2, "Nombre profesor");

            setStyleCell(sheet, headerTabla, currow, 1);
            setStyleCell(sheet, headerTabla, currow, 2);
            setStyleCell(sheet, headerTabla, currow, 3);

            currow++;
            setExDat(sheet, currow, 1, profesor.getPROnumeroEmpleado()); //nombre del area

            setExDat(sheet, currow, 2, nombreProfesor); //responsable
            setStyleCell(sheet, borderstabla, currow, 1);
            setStyleCell(sheet, borderstabla, currow, 2);

            if (isCordinadorAreaAdmin) {
                uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorProfesorCoordinador(reporte, Integer.parseInt(programaEducativoId), profesor.getPROid(), idCicloEscolar, ract, idProfesorCoordinador);
            } else {
                uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipsPorProfesor(reporte, Integer.parseInt(programaEducativoId), profesor.getPROid(), idCicloEscolar, ract, isAdmin);
            }

            sheet = llenadoDetalleUAIPS(uaips, sheet, currow, headerTabla, borderstabla, Integer.parseInt(programaEducativoId));

        }//fin de iteracion x reusltadoAdapter

        // ***** hasta aqui lleno la hoja de detalle
        //aqui se exporta el excel para que inicie la descarga en el siguiente metodo generico
        exportarReporteExcel(nombreLibro, workbook);

    }

    public HSSFSheet colocarTablaResumenesRact(HSSFSheet sheet, int row, int col, String[] totalEntregados, int totalEsperados, HSSFCellStyle headerTabla, int currow, HSSFCellStyle borderstabla) {
        setExDat(sheet, row + 3, col, "No. RACT");
        setExDat(sheet, row + 3, col + 1, "Total esperados");

        if (reporte.equalsIgnoreCase("noentregados")) {
            setExDat(sheet, row + 3, col + 2, "Total no entregados y/o parciales");
        } else if (reporte.equalsIgnoreCase("entregadosynoentregados")) {
            setExDat(sheet, row + 3, col + 2, "Total entregados y parciales");
        } else {
            setExDat(sheet, row + 3, col + 2, "Total entregados");
        }

        setExDat(sheet, row + 3, col + 3, "% Porcentaje");
        sheet.getRow(row + 3).getCell(1);
        //*** inicia llenado de tabla de RACTS
        setExDat(sheet, row + 4, col, "RACT 1");
        setExDat(sheet, row + 4, col + 1, totalEsperados);
        setExDat(sheet, row + 4, col + 2, totalEntregados[0]);

        if (totalEsperados == 0) {
            setExDat(sheet, row + 4, col + 3, "0");
        } else {
            setExDat(sheet, row + 4, col + 3, "" + df.format((100 / Float.parseFloat(String.valueOf(totalEsperados))) * Float.parseFloat(totalEntregados[0])));
        }

        setExDat(sheet, row + 5, col, "RACT 2");
        setExDat(sheet, row + 5, col + 1, totalEsperados);
        setExDat(sheet, row + 5, col + 2, totalEntregados[1]);

        if (totalEsperados == 0) {
            setExDat(sheet, row + 5, col + 3, "0");
        } else {
            setExDat(sheet, row + 5, col + 3, "" + df.format((100 / Float.parseFloat(String.valueOf(totalEsperados))) * Float.parseFloat(totalEntregados[1])));
        }

        setExDat(sheet, row + 6, col, "RACT 3");
        setExDat(sheet, row + 6, col + 1, totalEsperados);
        setExDat(sheet, row + 6, col + 2, totalEntregados[2]);

        if (totalEsperados == 0) {
            setExDat(sheet, row + 6, col + 3, "0");
        } else {
            setExDat(sheet, row + 6, col + 3, "" + df.format((100 / Float.parseFloat(String.valueOf(totalEsperados))) * Float.parseFloat(totalEntregados[2])));
        }

        float total = ((Integer.parseInt(totalEntregados[0])) + (Integer.parseInt(totalEntregados[1])) + (Integer.parseInt(totalEntregados[2])));
        setExDatNegrita(sheet, row + 7, col, "Total: ");
        setExDat(sheet, row + 7, col + 1, totalEsperados * 3);
        setExDat(sheet, row + 7, col + 2, total);

        if (totalEsperados == 0) {
            setExDat(sheet, row + 7, col + 3, "0");
        } else {
            setExDat(sheet, row + 7, col + 3, "" + df.format(total / (totalEsperados * 3f) * 100));
        }

        //*** termina llenado de tabla de RACTS
        // se le da estilo a la tabla
        setStyleCell(sheet, headerTabla, row, col);
        setStyleCell(sheet, headerTabla, row, col + 1);
        setStyleCell(sheet, headerTabla, row, col + 2);
        setStyleCell(sheet, headerTabla, row, col + 3);
        setStyleCell(sheet, headerTabla, row, col + 4);

        setStyleCell(sheet, headerTabla, row + 2, col);
        setStyleCell(sheet, headerTabla, row + 2, col + 1);
        setStyleCell(sheet, headerTabla, row + 2, col + 2);
        setStyleCell(sheet, headerTabla, row + 2, col + 3);

        setStyleCell(sheet, headerTabla, row + 3, col);
        setStyleCell(sheet, headerTabla, row + 3, col + 1);
        setStyleCell(sheet, headerTabla, row + 3, col + 2);
        setStyleCell(sheet, headerTabla, row + 3, col + 3);
        setStyleCell(sheet, headerTabla, row + 3, col + 4);

        setStyleCell(sheet, borderstabla, row + 4, col);
        setStyleCell(sheet, borderstabla, row + 4, col + 1);
        setStyleCell(sheet, borderstabla, row + 4, col + 2);
        setStyleCell(sheet, borderstabla, row + 4, col + 3);
        setStyleCell(sheet, borderstabla, row + 4, col + 4);

        setStyleCell(sheet, borderstabla, row + 5, col);
        setStyleCell(sheet, borderstabla, row + 5, col + 1);
        setStyleCell(sheet, borderstabla, row + 5, col + 2);
        setStyleCell(sheet, borderstabla, row + 5, col + 3);
        setStyleCell(sheet, borderstabla, row + 5, col + 4);

        setStyleCell(sheet, borderstabla, row + 6, col);
        setStyleCell(sheet, borderstabla, row + 6, col + 1);
        setStyleCell(sheet, borderstabla, row + 6, col + 2);
        setStyleCell(sheet, borderstabla, row + 6, col + 3);
        setStyleCell(sheet, borderstabla, row + 6, col + 4);

        setStyleCell(sheet, borderstabla, row + 7, col);
        setStyleCell(sheet, borderstabla, row + 7, col + 1);
        setStyleCell(sheet, borderstabla, row + 7, col + 2);
        setStyleCell(sheet, borderstabla, row + 7, col + 3);
        setStyleCell(sheet, borderstabla, row + 7, col + 4);

        return sheet;
    }

    /**
     * Este metodo llenara la parte de los grupos de la hoja de detalle del
     * excel puesto que es algo que se repite en todos los exceles
     *
     * @return
     */
    public HSSFSheet llenadoDetalleUAIPS(List<UnidadaprendizajeImparteProfesor> uaips, HSSFSheet sheet, int currow, HSSFCellStyle headerTabla, HSSFCellStyle borderstabla, int programaId) {
        if (uaips.size() != 0) {
            // ** termina llenado de la tabla
            currow += 2;
            //titulos de las unidades de aprendizaje
            setExDat(sheet, currow, 1, "Clave unidad de aprendizaje");
            setExDat(sheet, currow, 2, "Unidad de aprendizaje");
            setExDat(sheet, currow, 3, "No. de empleado");
            setExDat(sheet, currow, 4, "Nombre del profesor");
            setExDat(sheet, currow, 5, "Grupo");
            //** variable para menejar las calumnas dependiendo los ract seleccinados
            int x = 6;
            //** si selsecciono el primer ract o todos
            if (ract.equalsIgnoreCase("1")) {
                setExDat(sheet, currow, x, "% Avance 1er reporte");
                setExDat(sheet, currow, x + 1, "Fecha de elaboración 1er RACT");
            }
            if (ract.equalsIgnoreCase("2")) {
                setExDat(sheet, currow, x, "% Avance 2do reporte");
                setExDat(sheet, currow, x + 1, "Fecha de elaboración 2do RACT");
            }
            if (ract.equalsIgnoreCase("3")) {
                setExDat(sheet, currow, x, "% Avance 3er reporte");
                setExDat(sheet, currow, x + 1, "Fecha de elaboración 3er RACT");
            }
            // si se selcciono la opcion de todos los ract
            // agrego las 3 columnas
            if (ract.equalsIgnoreCase("4")) {
                setExDat(sheet, currow, x, "% Avance 1er reporte");
                setExDat(sheet, currow, x + 1, "Fecha de elaboración 1er RACT");
                setExDat(sheet, currow, x + 2, "% Avance 2do reporte");
                setExDat(sheet, currow, x + 3, "Fecha de elaboración 2do RACT");
                setExDat(sheet, currow, x + 4, "% Avance 3er reporte");
                setExDat(sheet, currow, x + 5, "Fecha de elaboración 3er RACT");
                setExDat(sheet, currow, x + 6, "Responsable");
                sheet.setColumnWidth(x + 2, 5600);
            } // si no agrego la columna de responsable despues de la primera columna
            else {
                setExDat(sheet, currow, x + 2, "Responsable");
                sheet.setColumnWidth(x + 2, 10000);
            }

            // para formatear toda la linea
            for (int j = 1; j <= 13; j++) {
                //tenemos qu aajustar texto
                //tenemos que centrar el texto
                //cambiar el color de fondo
                setStyleCell(sheet, headerTabla, currow, j);
            }

            currow++;
            /**
             * Ordone los uaips para que salgan las del mismo profesor
             * continuas, si no saldrian separadas
             */
            Collections.sort(uaips, new Comparator<UnidadaprendizajeImparteProfesor>() {
                public int compare(UnidadaprendizajeImparteProfesor tu1, UnidadaprendizajeImparteProfesor tu2) {
                    // compare two instance of `Score` and return `int` as result.
                    return tu1.getProfesorPROid().toString().compareTo(tu2.getProfesorPROid().toString());
                }
            });

            // empiezo a llenar las uaip
            for (UnidadaprendizajeImparteProfesor uaip : uaips) {
                setExDat(sheet, currow, 1, uaip.getUnidadAprendizajeUAPid().getUAPclave()); //clave
                setExDat(sheet, currow, 2, uaip.getUnidadAprendizajeUAPid().getUAPnombre()); //nombre unidad
                setExDat(sheet, currow, 3, uaip.getProfesorPROid().getPROnumeroEmpleado()); //numero empleado
                setExDat(sheet, currow, 4, uaip.getProfesorPROid().getPROnombre() + " " + uaip.getProfesorPROid().getPROapellidoPaterno() + " " + uaip.getProfesorPROid().getPROapellidoMaterno()); //nombre maestro
                setExDat(sheet, currow, 5, uaip.getGrupoGPOid().getGPOnumero() + "-" + uaip.getUIPsubgrupo() + "-" + uaip.getUIPtipoSubgrupo()); //grupo numero
                //marcamos bordes
                for (int j = 1; j <= 5; j++) {
                    setStyleCell(sheet, borderstabla, currow, j);
                }
                //tienne que ser entregados/enviados
                Format formatter = new SimpleDateFormat("dd-MM-yyyy");
                DecimalFormat formato1 = new DecimalFormat("#.##");
                //** si se selecciono ract 1 o todos
                if (ract.equalsIgnoreCase("1")) {
                    // intento obtener los datos si no tiene entrara en catch y los pondra vacios
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("1")) {
                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, (x + 1), formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        setExDat(sheet, currow, x, ""); //% avance 7**
                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***

                    }
                    setStyleCell(sheet, borderstabla, currow, x);
                    setStyleCell(sheet, borderstabla, currow, x + 1);

                }
                //Se revisa que el siguiente elemento sea el reporte 2
                if (ract.equalsIgnoreCase("2")) {
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("2")) {

                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("2")) {

                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        setExDat(sheet, currow, x, ""); //% avance 7**
                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***

                    }
                    setStyleCell(sheet, borderstabla, currow, x);
                    setStyleCell(sheet, borderstabla, currow, x + 1);

                }
                //Se revisa que el siguiente elemento sea el reporte 3
                //** si se selecciono ract 3 o todos
                if (ract.equalsIgnoreCase("3")) {
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("3")) {

                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("3")) {

                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(2).getRACnumero().equalsIgnoreCase("3")) {

                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 1, formatter.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        setExDat(sheet, currow, x, ""); //% avance 7**
                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***

                    }
                    setStyleCell(sheet, borderstabla, currow, x);
                    setStyleCell(sheet, borderstabla, currow, x + 1);

                }
//                          // si es todos los ract, llenara todas las columnas de los ract
                if (ract.equalsIgnoreCase("4")) {
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("1")) {
                            setExDat(sheet, currow, x, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, (x + 1), formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        setExDat(sheet, currow, x, ""); //% avance 7**
                        setExDat(sheet, currow, x + 1, ""); //fecha elabora 8***

                    }
                    setStyleCell(sheet, borderstabla, currow, x);
                    setStyleCell(sheet, borderstabla, currow, x + 1);
                    //Se revisa que el siguiente elemento sea el reporte 2
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("2")) {

                            setExDat(sheet, currow, x + 2, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 3, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("2")) {

                            setExDat(sheet, currow, x + 2, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 3, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        setExDat(sheet, currow, x + 2, ""); //% avance 7**
                        setExDat(sheet, currow, x + 3, ""); //fecha elabora 8***

                    }
                    setStyleCell(sheet, borderstabla, currow, x + 2);
                    setStyleCell(sheet, borderstabla, currow, x + 3);
                    // ract 3
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("3")) {

                            setExDat(sheet, currow, x + 4, formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 5, formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("3")) {

                            setExDat(sheet, currow, x + 4, formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 5, formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(2).getRACnumero().equalsIgnoreCase("3")) {

                            setExDat(sheet, currow, x + 4, formato1.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            setExDat(sheet, currow, x + 5, formatter.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        setExDat(sheet, currow, x + 4, ""); //% avance 7**
                        setExDat(sheet, currow, x + 5, ""); //fecha elabora 8***

                    }
                    setStyleCell(sheet, borderstabla, currow, x + 4);
                    setStyleCell(sheet, borderstabla, currow, x + 5);
                }

                String nombreCompleto;
                Profesor p = null;
                // busco el coordidnador que le pertenecia a la materia segun el Programa educativo
                for (Coordinadorareaadministrativa co : uaip.getUnidadAprendizajeUAPid().getCoordinadorareaadministrativaList()) {
                    if (co.getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid() == programaId) {
                        p = co.getProfesorPROid();
                        break;
                    }
                }
                // intento poner el nombre si no tiene entrara a la exceptio y lo pondra sin asignar
                try {
                    nombreCompleto = p.getPROnombre() + " " + p.getPROapellidoPaterno() + " " + p.getPROapellidoMaterno();

                } catch (Exception e) {
                    nombreCompleto = "SIN ASIGNAR";
                }
                //  profesor responsable
                if (ract.equalsIgnoreCase("4")) {
                    setExDat(sheet, currow, 12, nombreCompleto);
                    setStyleCell(sheet, borderstabla, currow, 12);
                } else {
                    setExDat(sheet, currow, x + 2, nombreCompleto);
                    setStyleCell(sheet, borderstabla, currow, x + 2);
                }

                currow++;
//
            }
        }
        return sheet;
    }

    public void exportarReporteExcel(String nombreLibro, HSSFWorkbook workbook) throws IOException {
        //aqui se exporta el excel para que inicie la descarga
        if (criterio != "" && reporte != "") {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");

            nombreLibro = nombreArchivo();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreLibro + ".xls\"");
            //genera libro
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } else {
            System.out.println("No se Genero por: Criterio->" + criterio + " , Reporte->" + reporte);
        }
    }

    public HSSFSheet cabezeraGeneralExcel(HSSFSheet sheet, HSSFCellStyle style, HSSFWorkbook workbook) throws IOException {

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/logo.png"));

        } catch (IOException e) {
            System.out.println("No se encontro imagen");
        }
        ByteArrayOutputStream imgLogo_bytes = new ByteArrayOutputStream();
        ImageIO.write(image, "png", imgLogo_bytes);
        imgLogo_bytes.flush();

        int logoUabc = workbook.addPicture(imgLogo_bytes.toByteArray(), Workbook.PICTURE_TYPE_PNG);

        ServiceLocator
                .getInstanceBaseDAO().setTipo(Object.class
                );
        ArrayList<Object> temp = (ArrayList<Object>) ServiceLocator.getInstanceBaseDAO().executeSQL("select curdate()");

        //Se guarda el ciclo escolar mas actual que hay en la base  ////////////////////         ----Alex Cota
        ArrayList<Object> ciclo = (ArrayList<Object>) ServiceLocator.getInstanceBaseDAO().executeSQL("select CEScicloEscolar\n"
                + "from cicloescolar\n"
                + "order by CEScicloEscolar desc LIMIT 1;");
        /////////////////////////////////////////////////////////////////////////////////

        Object fechaActual = temp.get(0);
        Object cicloActual = ciclo.get(0); ////// Se guarda el dato del indice indicado a un solo objeto

        /* Create the drawing container */
        HSSFPatriarch drawing = sheet.createDrawingPatriarch();
        /* Create an anchor point */
        ClientAnchor my_anchor = new HSSFClientAnchor();
        /* Define top left corner, and we can resize picture suitable from there */
        my_anchor.setCol1(1);
        my_anchor.setRow1(1);
        /* Invoke createPicture and pass the anchor point and ID */
        HSSFPicture my_picture = drawing.createPicture(my_anchor, logoUabc);
        /* Call resize method, which resizes the image */
        double escalaRes = 0.1;
        my_picture.resize(escalaRes);
        //definiremos el estilo para estas Celdas
        //Definiremos el nombre de la escuela

        // El siguiente variable es para la resta de columnas en caso de que una hoja tenga alterada el tamaño de columna ////-- Alex Cota
        // En caso de ser asi el titulo se recorrera una columna hacia la izquierda///////////////////////////////////////////
        int x = 0;
        if (sheet.getColumnWidth(2) > 3000) {
            x = 1;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        HSSFRow row = sheet.createRow(2);
        row.setHeight((short) 600);
        HSSFCell cell = row.createCell(2);
        cell.setCellValue("Universidad Autónoma de Baja California");
        cell.setCellStyle(style);
        row = sheet.createRow(3);
        row.setHeight((short) 600);
        cell = row.createCell(2);
        cell.setCellValue("Facultad de Ingeniería");
        cell.setCellStyle(style);
        row = sheet.createRow(4);
        row.setHeight((short) 600);
        cell = row.createCell(2);
        cell.setCellValue("Mexicali ");
        cell.setCellStyle(style);

        HSSFCellStyle style2 = sheet.getWorkbook().createCellStyle();
        HSSFFont font = sheet.getWorkbook().createFont();
        font.setFontName("font fecha 1");
        font.setFontHeightInPoints((short) 17);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        //setStyleCell(sheet, style2, 3, 3);
        style2.setFont(font);

        HSSFCellStyle style3 = sheet.getWorkbook().createCellStyle();
        HSSFFont font2 = sheet.getWorkbook().createFont();
        font2.setFontName("font fecha 2");
        font2.setFontHeightInPoints((short) 16);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font2.setColor(HSSFColor.GREEN.index);
        style3.setFont(font2);

        // La variable y se encargara de recorrer la columna del valor de la fecha y del valor del ciclo hacia la derecha ///// -- Alex Cota
        // Si el tamaño de columna de la hoja no esta alterada y se cambia la columna para que los datos se muestren correctamente.
        int y = 0;
        if (x == 0) {
            y = 2;

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        row = sheet.getRow(3);
        cell = row.createCell(3);
        cell.setCellValue("Fecha: ");
        cell.setCellStyle(style2);

        cell = row.createCell(4);
        cell.setCellValue(fechaActual.toString());
        cell.setCellStyle(style3);

        row = sheet.getRow(4);
        cell = row.createCell(3);
        cell.setCellValue("Ciclo Escolar: ");
        cell.setCellStyle(style2);

        row = sheet.getRow(4);
        cell = row.createCell(4);

        //metodo para retornar ciclo escolar
        Cicloescolar ce = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar(getIdCicloEscolar());
        cell.setCellValue(String.valueOf(ce.getCEScicloEscolar()));
        //cell.setCellValue(cicloActual.toString());
        cell.setCellStyle(style3);

        return sheet;
    }

    public HSSFSheet setExDat(HSSFSheet sheet, int prow, int pcol, String valor) {
        if (sheet.getRow(prow) != null) {
            HSSFRow row = sheet.getRow(prow);
            HSSFCell cell = row.createCell(pcol);//

            /*
            Cada segunda fila de cada tabla se modifica el tamano de celda
            y el acomodo de valor en estas
             */
            HSSFCellStyle styleTamano = cell.getCellStyle();

            if (prow == rowBand + 1) {
                row.setHeight((short) 850);
                styleTamano.setWrapText(true);
                styleTamano.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            }

            styleTamano.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(styleTamano);
            cell.setCellValue(valor);

        } else {
            HSSFRow row = sheet.createRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);
        }

        return sheet;
    }

    public HSSFSheet setExDatNegrita(HSSFSheet sheet, int prow, int pcol, String valor) {
        if (sheet.getRow(prow) != null) {
            HSSFRow row = sheet.getRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);

            HSSFCellStyle style2 = sheet.getWorkbook().createCellStyle();
            HSSFFont font = sheet.getWorkbook().createFont();
            font.setFontName("font Negrita Titulo");
            font.setFontHeightInPoints((short) 11);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            style2.setFont(font);
            cell.setCellStyle(style2);
        } else {
            HSSFRow row = sheet.createRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);

            HSSFCellStyle style2 = sheet.getWorkbook().createCellStyle();
            HSSFFont font = sheet.getWorkbook().createFont();
            font.setFontName("font Negrita Titulo");
            font.setFontHeightInPoints((short) 11);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            style2.setFont(font);
            cell.setCellStyle(style2);
        }

        return sheet;
    }

    public HSSFSheet setExDat(HSSFSheet sheet, int prow, int pcol, int valor) {
        if (sheet.getRow(prow) != null) {
            HSSFRow row = sheet.getRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);
        } else {
            HSSFRow row = sheet.createRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);
        }
        return sheet;
    }

    public HSSFSheet setExDat(HSSFSheet sheet, int prow, int pcol, float valor) {
        if (sheet.getRow(prow) != null) {
            HSSFRow row = sheet.getRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);
        } else {
            HSSFRow row = sheet.createRow(prow);
            HSSFCell cell = row.createCell(pcol);
            cell.setCellValue(valor);
        }
        return sheet;
    }

    public HSSFSheet setStyleCell(HSSFSheet sheet, HSSFCellStyle style, int prow, int pcol) {
        if (sheet.getRow(prow) != null) {
            HSSFRow row = sheet.getRow(prow);
            if (row.getCell(pcol) != null) {
                HSSFCell cell = row.getCell(pcol);
                cell.setCellStyle(style);
            }
        }
        return sheet;
    }
    //codigo Rodrigo y Ricardo

    //codigo metodos Jesus Ruelas
    private void initRact() {
        filtrosBeanHelper = new FiltrosBeanHelper();
        listaAux = new ArrayList<ReporteAvanceAux>();
//        catalogoreportesDelegate = new CatalogoReportesDelegate();
        op = null;
        tipo = null;
        calnumeroReporte = 0;
        numRact = 0;
        cescicloEscolar = null;
        acoclave = 0;
        clavepe = 0;
        pesvigencia = null;
        numProfUIPid = 0;
        fecha1 = null;
        pronumeroEmpleado = 0;
        gponumero = 0;
        clave = 0;
        uapclave = 0;
        uacclave = 0;
        creid = 0;
        //aqui agregue Jesus Ruelas
        CTRnombre = null;
        tipoReporteCTR = null;
        listaCatalogoReportes = null;
        //aqui agregue Jesus Ruelas
    }

    private void initConsultasGuardadas() {

        todasConsultasGuardadas();
    }
    //Variable para decidir el numero de decimales que contiene nuestra variable
    DecimalFormat df = new DecimalFormat("###.00");

    private void mostrarMensajeGrowl(String... mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        //guardarConsultaSeleccionar
        if (tipoMensajeGrowl.equalsIgnoreCase("guardarConsulta")) {
            context.addMessage("growl", new FacesMessage("Se guardó correctamente", ""));
            PrimeFaces.current().ajax().update("growl");
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("guardarConsultaSeleccionar")) {
            context.addMessage(null, new FacesMessage("Indique un nombre a la consulta a guardar", ""));
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("consultaDuplicada")) {
            context.addMessage(null, new FacesMessage("No se guardó el reporte debido a que existe una consulta guardada con el mismo nombre", ""));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "No se guardó el reporte debido a que existe una consulta guardada con el mismo nombre");
            FacesContext.getCurrentInstance().addMessage(null, message);

            //FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_INFO, "message *****le", "message body");
            //RequestContext.getCurrentInstance().showMessageInDialog(message2);
            //RequestContext.getCurrentInstance().openDialog("Info1");
            //FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message Title", "Message body");
            //RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
        if (tipoMensajeGrowl.equals("alertaRolSinAsign")) {
            context.addMessage("growlSticky", new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje[0], ""));
            PrimeFaces.current().ajax().update("growlSticky");
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("modificarConsulta")) {
            context.addMessage(null, new FacesMessage("Se guardó correctamente", ""));
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("modificarConsultaSeleccionar")) {
            context.addMessage(null, new FacesMessage("Seleccione un nombre de consulta a modificar", ""));
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("eliminarConsulta")) {
            context.addMessage(null, new FacesMessage("Se eliminó correctamente", ""));
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("eliminarConsultaSeleccionar")) {
            context.addMessage(null, new FacesMessage("Seleccione un nombre de consulta a eliminar", ""));
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("ejecutarQueryConsultaGuardada")) {
            context.addMessage(null, new FacesMessage("Operación realizada correctamente", ""));
        }
        if (tipoMensajeGrowl.equalsIgnoreCase("ejecutarQueryConsultaGuardadaSeleccionar")) {
            context.addMessage(null, new FacesMessage("Favor de seleccionar una consulta", ""));
        }
        //context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));

    }

    public List<DetalleReporte> llenarDetalleReporte(List<UnidadaprendizajeImparteProfesor> uaips, int programaId) {

        List<DetalleReporte> detallesReporte = new ArrayList();
        if (uaips.size() != 0) {

            /**
             * Ordone los uaips para que salgan las del mismo profesor
             * continuas, si no saldrian separadas
             */
            Collections.sort(uaips, new Comparator<UnidadaprendizajeImparteProfesor>() {

                public int compare(UnidadaprendizajeImparteProfesor tu1, UnidadaprendizajeImparteProfesor tu2) {
                    // compare two instance of `Score` and return `int` as result.
                    return tu1.getProfesorPROid().toString().compareTo(tu2.getProfesorPROid().toString());
                }
            });

            // empiezo a llenar las uaip
            for (UnidadaprendizajeImparteProfesor uaip : uaips) {
                DetalleReporte detalleReporte = new DetalleReporte();
                detalleReporte.setClaveUnidadAprendizaje(uaip.getUnidadAprendizajeUAPid().getUAPclave()); //clave
                detalleReporte.setNombreUnidadAprendizaje(uaip.getUnidadAprendizajeUAPid().getUAPnombre()); //nombre unidad
                detalleReporte.setNumeroEmpleado(uaip.getProfesorPROid().getPROnumeroEmpleado()); //numero empleado
                detalleReporte.setNombreProfesor(uaip.getProfesorPROid().getPROnombre() + " " + uaip.getProfesorPROid().getPROapellidoPaterno() + " " + uaip.getProfesorPROid().getPROapellidoMaterno()); //nombre maestro
                detalleReporte.setGrupo(uaip.getGrupoGPOid().getGPOnumero() + "-" + uaip.getUIPsubgrupo() + "-" + uaip.getUIPtipoSubgrupo()); //grupo numero

                //tienne que ser entregados/enviados
                Format formatter = new SimpleDateFormat("dd-MM-yyyy");
                DecimalFormat formato1 = new DecimalFormat("#.##");
                //** si se selecciono ract 1 o todos
                if (ract.equalsIgnoreCase("1")) {
                    // intento obtener los datos si no tiene entrara en catch y los pondra vacios
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("1")) {
                            detalleReporte.setPorcentajeRact1(formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact1(formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        detalleReporte.setPorcentajeRact1(""); //% avance 7**
                        detalleReporte.setFechaRact1(""); //fecha elabora 8***

                    }
                }
                //Se revisa que el siguiente elemento sea el reporte 2
                if (ract.equalsIgnoreCase("2")) {
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("2")) {
                            detalleReporte.setPorcentajeRact2(formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact2(formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("2")) {

                            detalleReporte.setPorcentajeRact2(formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact2(formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        detalleReporte.setPorcentajeRact2(""); //% avance 7**
                        detalleReporte.setFechaRact2(""); //fecha elabora 8***

                    }
                }
                //Se revisa que el siguiente elemento sea el reporte 3
                //** si se selecciono ract 3 o todos
                if (ract.equalsIgnoreCase("3")) {
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("3")) {
                            detalleReporte.setPorcentajeRact3(formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact3(formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("3")) {

                            detalleReporte.setPorcentajeRact3(formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact3(formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(2).getRACnumero().equalsIgnoreCase("3")) {

                            detalleReporte.setPorcentajeRact3(formato1.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact3(formatter.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        detalleReporte.setPorcentajeRact3(""); //% avance 7**
                        detalleReporte.setFechaRact3(""); //fecha elabora 8***

                    }

                }
//                          // si es todos los ract, llenara todas las columnas de los ract
                if (ract.equalsIgnoreCase("4")) {
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("1")) {
                            detalleReporte.setPorcentajeRact1(formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact1(formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        detalleReporte.setPorcentajeRact1(""); //% avance 7**
                        detalleReporte.setFechaRact1(""); //fecha elabora 8***

                    }

                    //Se revisa que el siguiente elemento sea el reporte 2
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("2")) {
                            detalleReporte.setPorcentajeRact2(formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact2(formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("2")) {

                            detalleReporte.setPorcentajeRact2(formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact2(formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        detalleReporte.setPorcentajeRact2(""); //% avance 7**
                        detalleReporte.setFechaRact2(""); //fecha elabora 8***

                    }

                    // ract 3
                    try {
                        if (uaip.getReporteavancecontenidotematicoList().get(0).getRACnumero().equalsIgnoreCase("3")) {
                            detalleReporte.setPorcentajeRact3(formato1.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact3(formatter.format(uaip.getReporteavancecontenidotematicoList().get(0).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(1).getRACnumero().equalsIgnoreCase("3")) {

                            detalleReporte.setPorcentajeRact3(formato1.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact3(formatter.format(uaip.getReporteavancecontenidotematicoList().get(1).getRACfechaElaboracion())); //fecha elabora 8***
                        } else if (uaip.getReporteavancecontenidotematicoList().get(2).getRACnumero().equalsIgnoreCase("3")) {

                            detalleReporte.setPorcentajeRact3(formato1.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACavanceGlobal()).toString()
                            ); //% avance 7**
                            detalleReporte.setFechaRact3(formatter.format(uaip.getReporteavancecontenidotematicoList().get(2).getRACfechaElaboracion())); //fecha elabora 8***
                        } else {
                            throw new RuntimeException("No hay datos");
                        }
                    } catch (Exception e) {
                        // si no realizo el reporte los pondra vacios
                        detalleReporte.setPorcentajeRact3(""); //% avance 7**
                        detalleReporte.setFechaRact3(""); //fecha elabora 8***

                    }

                }

                String nombreCompleto;
                Profesor p = null;
                // busco el coordidnador que le pertenecia a la materia segun el Programa educativo
                for (Coordinadorareaadministrativa co : uaip.getUnidadAprendizajeUAPid().getCoordinadorareaadministrativaList()) {
                    if (co.getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid() == programaId) {
                        p = co.getProfesorPROid();
                        break;
                    }
                }
                // intento poner el nombre si no tiene entrara a la exceptio y lo pondra sin asignar
                try {
                    nombreCompleto = p.getPROnombre() + " " + p.getPROapellidoPaterno() + " " + p.getPROapellidoMaterno();

                } catch (Exception e) {
                    nombreCompleto = "SIN ASIGNAR";
                }
                //  profesor responsable
                detalleReporte.setResponsable(nombreCompleto);

//
                detallesReporte.add(detalleReporte);
            }
        }
        return detallesReporte;
    }

    //metodo para guardar consulta(tipo de reporte de consulta) del usuario tomando
    //los atributos que seleccione el usuario como predefinidos en esta consulta
    //guardada lo guarda en la base de datos en un atributo tipo String y
    //tiene otro atributo un nombre de la consulta guardada definido por el
    //usuario
    public void guardarConsulta() {

        initConsultasGuardadas();

        //en este atrubuto guarda el nombre del metodo al que llama
        //o es propio de la consulta guardada(uno de los 64 metodos
        //de consulta
        String tipoReporteUI;

        ReporteAux reporteUI = new ReporteAux();

        setNumRact(Integer.parseInt(ract));
        setCescicloEscolar(selectCicloEscolarList.get(0));
        //setCescicloEscolar("2009-2");
        //setClavepe(Integer.parseInt(selectProgramEducativo.get(0)));
        //setClavepe(Integer.parseInt("14006"));
        //setPesvigencia("2009-2");

        //atributos necesarios para entregados en el objeto que se manda al
        //delegate
        reporteUI.setNumRact(numRact);
        reporteUI.setCescicloEscolar(cescicloEscolar);
        reporteUI.setAcoclave(acoclave);
        reporteUI.setClavepe(clavepe);
        reporteUI.setPesvigencia(pesvigencia);
        reporteUI.setPronumeroEmpleado(pronumeroEmpleado);
        reporteUI.setGponumero(gponumero);
        reporteUI.setClave(clavepe);
        reporteUI.setUapclave(uapclave);
        reporteUI.setAadid(aadid);

        // TODO: Quitar los List<String> y adaptar los metodos prepararAtributo
        List<String> selectAreaAdministrativa = new ArrayList<String>();
        for (Areaadministrativa area : this.selectAreaAdministrativa) {
            selectAreaAdministrativa.add(Integer.toString(area.getAADid()));
        }
        List<String> selectProgramEducativo = new ArrayList<String>();
        for (Programaeducativo programa : this.selectProgramEducativo) {
            selectProgramEducativo.add(Integer.toString(programa.getPEDclave()));
        }
        List<String> selectAreaConocimiento = new ArrayList<String>();
        for (Areaconocimiento area : this.selectAreaConocimiento) {
            selectAreaConocimiento.add(Integer.toString(area.getACOclave()));
        }
        List<String> selectPlanesEstudio = new ArrayList<String>();
        for (Planestudio plan : this.selectPlanesEstudio) {
            selectPlanesEstudio.add(plan.getPESvigenciaPlan());
        }
        List<String> selectProfesor = new ArrayList<String>();
        for (Profesor profe : this.selectProfesor) {
            selectProfesor.add(Integer.toString(profe.getPROnumeroEmpleado()));
        }

        //aqui
        ArrayList<ReporteAux> listaReporte = new ArrayList<ReporteAux>();
        if (botonCancelar == false) {
            List<String> selectUnidadAprendizajeStr = new ArrayList<>();
            for (Unidadaprendizaje unidad : selectUnidadAprendizaje) {
                selectUnidadAprendizajeStr.add(Integer.toString(unidad.getUAPclave()));
            }
            if (criterio.equalsIgnoreCase("programa_educativo")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaProgEd(reporteUI, selectCicloEscolarList, selectProgramEducativo);
            }
            if ((criterio.equalsIgnoreCase("area_conocimiento"))) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaAreaCon(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaConocimiento);
            }
            if ((criterio.equalsIgnoreCase("area_administrativa"))) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaAreaAdmin(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaAdministrativa);
            }
            if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaUAprend(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaConocimiento, selectUnidadAprendizajeStr, selectGrupo);
            }
            if (criterio.equalsIgnoreCase("profesor")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaProfesor(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaConocimiento, selectUnidadAprendizajeStr, selectGrupo, selectProfesor);
            }

            //en CTRnombre se guarda el nombre que definio el usuario para la
            //consulta(el nombre que identifica la consulta guardada)
            //       CTRnombre="Septimo reporte";
            String CTRConsultaQuery = "";

            //manda reporteUI, tipoReporteUI y CTRnombre y se devuelve el formato
            //de tratamiento de cadenas con separadores "#" y ":" para el formato
            //que contiene el nombre del metodos y los atributos con los mismos
            //separadores
            // ArrayList<>
            List<Catalogoreportes> listaReportes = filtrosBeanHelper.todasConsultasGuardadasNormal();

            tipoMensajeGrowl = "guardarConsulta";
            Boolean bandera = false;

            for (Catalogoreportes cr : listaReportes) {
                if (cr.getCTRnombre().equalsIgnoreCase(CTRnombre)) {
                    tipoMensajeGrowl = "consultaDuplicada";
                    bandera = true;
                }
            }

            String CTRnombreTemp = CTRnombre.trim();

            if ((bandera == false) && !(CTRnombreTemp.equalsIgnoreCase(""))) {
                for (ReporteAux report : listaReporte) {
                    CTRConsultaQuery = filtrosBeanHelper.guardarConsulta(report, tipoReporteCTR, CTRnombre, aadid, usuId);
                }
            }

            if (CTRnombre == null || CTRnombreTemp.equalsIgnoreCase("")) {
                //if(CTRnombre.equalsIgnoreCase("")){
                tipoMensajeGrowl = "guardarConsultaSeleccionar";
                //mostrarMensajeGrowl();
                //}
            }

            //return CTRConsultaQuery;
            mostrarMensajeGrowl();
            todasConsultasGuardadas();
        }
    }

    public void modificarConsulta() {

        initConsultasGuardadas();

        ReporteAux reporteUI = new ReporteAux();

        setNumRact(Integer.parseInt(ract));
        setCescicloEscolar(selectCicloEscolarList.get(0));
        //setCescicloEscolar("2009-2");
        //setClavepe(Integer.parseInt(selectProgramEducativo.get(0)));
        //setClavepe(Integer.parseInt("14006"));
        //setPesvigencia("2009-2");

        reporteUI.setNumRact(numRact);
        reporteUI.setCescicloEscolar(cescicloEscolar);
        reporteUI.setAcoclave(acoclave);
        reporteUI.setClavepe(clavepe);
        reporteUI.setPesvigencia(pesvigencia);
        reporteUI.setPronumeroEmpleado(pronumeroEmpleado);
        reporteUI.setGponumero(gponumero);
        reporteUI.setClave(clavepe);
        reporteUI.setUapclave(uapclave);
        reporteUI.setAadid(aadid);

        // TODO: Quitar los List<String> y adaptar los metodos prepararAtributo
        List<String> selectAreaAdministrativa = new ArrayList<String>();
        for (Areaadministrativa area : this.selectAreaAdministrativa) {
            selectAreaAdministrativa.add(Integer.toString(area.getAADid()));
        }
        List<String> selectProgramEducativo = new ArrayList<String>();
        for (Programaeducativo programa : this.selectProgramEducativo) {
            selectProgramEducativo.add(Integer.toString(programa.getPEDclave()));
        }
        List<String> selectAreaConocimiento = new ArrayList<String>();
        for (Areaconocimiento area : this.selectAreaConocimiento) {
            selectAreaConocimiento.add(Integer.toString(area.getACOclave()));
        }
        List<String> selectPlanesEstudio = new ArrayList<String>();
        for (Planestudio plan : this.selectPlanesEstudio) {
            selectPlanesEstudio.add(plan.getPESvigenciaPlan());
        }
        List<String> selectProfesor = new ArrayList<String>();
        for (Profesor profe : this.selectProfesor) {
            selectProfesor.add(Integer.toString(profe.getPROnumeroEmpleado()));
        }

        //aqui
        ArrayList<ReporteAux> listaReporte = new ArrayList<ReporteAux>();
        if (botonCancelar == false) {
            List<String> selectUnidadAprendizajeStr = new ArrayList<>();
            for (Unidadaprendizaje unidad : selectUnidadAprendizaje) {
                selectUnidadAprendizajeStr.add(Integer.toString(unidad.getUAPclave()));
            }
            if (criterio.equalsIgnoreCase("programa_educativo")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaProgEd(reporteUI, selectCicloEscolarList, selectProgramEducativo);
            }
            if (criterio.equalsIgnoreCase("area_conocimiento")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaAreaCon(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaConocimiento);
            }
            if ((criterio.equalsIgnoreCase("area_administrativa"))) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaAreaAdmin(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaAdministrativa);
            }
            if (criterio.equalsIgnoreCase("unidad_aprendizaje")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaUAprend(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaConocimiento, selectUnidadAprendizajeStr, selectGrupo);
            }
            if (criterio.equalsIgnoreCase("profesor")) {
                listaReporte = filtrosBeanHelper.prepararAtribGuardarConsultaProfesor(reporteUI, selectCicloEscolarList, selectPlanesEstudio, selectAreaConocimiento, selectUnidadAprendizajeStr, selectGrupo, selectProfesor);
            }

            ArrayList<Integer> listaIdCTR = new ArrayList<Integer>();
            filtrosBeanHelper.eliminarConsulta(tipoReporteCTR, CTRnombre, usuId);
            int cont = 0;
            String CTRnombreTemp = "";

            if (!(CTRnombre == null)) {
                CTRnombreTemp = CTRnombre.trim();
            }

            if (!listaReporte.isEmpty() && !(CTRnombreTemp.equalsIgnoreCase(""))) {
                for (ReporteAux report : listaReporte) {
                    filtrosBeanHelper.guardarConsulta(report, tipoReporteCTR, CTRnombre, aadid, usuId);
                    cont++;
                }
            }

            tipoMensajeGrowl = "modificarConsulta";

            if (CTRnombre == null || CTRnombreTemp.equalsIgnoreCase("")) {
                tipoMensajeGrowl = "modificarConsultaSeleccionar";
            }

            mostrarMensajeGrowl();
            todasConsultasGuardadas();
        }
    }

    public void eliminarConsulta() {
        initConsultasGuardadas();

        filtrosBeanHelper.eliminarConsulta(tipoReporteCTR, CTRnombre, usuId);

        tipoMensajeGrowl = "eliminarConsulta";

        String CTRnombreTemp = "";

        if (!(CTRnombre == null)) {
            CTRnombreTemp = CTRnombre.trim();
        }

        if (CTRnombre == null || CTRnombreTemp.equalsIgnoreCase("")) {
            tipoMensajeGrowl = "eliminarConsultaSeleccionar";
        }

        mostrarMensajeGrowl();
        todasConsultasGuardadas();
    }

    public void todasConsultasGuardadas() {
        listaCatalogoReportes = filtrosBeanHelper.todasConsultasGuardadas();
    }

    public ArrayList<ReporteAvanceAux> getListaAux() {
        return listaAux;
    }

    public void setListaAux(ArrayList<ReporteAvanceAux> listaAux) {
        this.listaAux = listaAux;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCalnumeroReporte() {
        return calnumeroReporte;
    }

    public void setCalnumeroReporte(int calnumeroReporte) {
        this.calnumeroReporte = calnumeroReporte;
    }

    public int getNumRact() {
        return numRact;
    }

    public void setNumRact(int numRact) {
        this.numRact = numRact;
    }

    public String getCescicloEscolar() {
        return cescicloEscolar;
    }

    public void setCescicloEscolar(String cescicloEscolar) {
        this.cescicloEscolar = cescicloEscolar;
    }

    public int getAcoclave() {
        return acoclave;
    }

    public void setAcoclave(int acoclave) {
        this.acoclave = acoclave;
    }

    public int getClavepe() {
        return clavepe;
    }

    public void setClavepe(int clavepe) {
        this.clavepe = clavepe;
    }

    public String getPesvigencia() {
        return pesvigencia;
    }

    public void setPesvigencia(String pesvigencia) {
        this.pesvigencia = pesvigencia;
    }

    public int getNumProfUIPid() {
        return numProfUIPid;
    }

    public void setNumProfUIPid(int numProfUIPid) {
        this.numProfUIPid = numProfUIPid;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public int getPronumeroEmpleado() {
        return pronumeroEmpleado;
    }

    public void setPronumeroEmpleado(int pronumeroEmpleado) {
        this.pronumeroEmpleado = pronumeroEmpleado;
    }

    public int getGponumero() {
        return gponumero;
    }

    public void setGponumero(int gponumero) {
        this.gponumero = gponumero;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public int getUapclave() {
        return uapclave;
    }

    public void setUapclave(int uapclave) {
        this.uapclave = uapclave;
    }

    public int getUacclave() {
        return uacclave;
    }

    public void setUacclave(int uacclave) {
        this.uacclave = uacclave;
    }

    public int getAadid() {
        return aadid;
    }

    public void setAadid(int aadid) {
        this.aadid = aadid;
    }

    public int getCreid() {
        return creid;
    }

    public void setCreid(int creid) {
        this.creid = creid;
    }

    //aqui agregue Jesus Ruelas
    public String getCTRnombre() {
        return CTRnombre;
    }

    public void setCTRnombre(String CTRnombre) {
        this.CTRnombre = CTRnombre;
    }

    public String getTipoReporteCTR() {
        return tipoReporteCTR;
    }

    public void setTipoReporteCTR(String tipoReporteCTR) {
        this.tipoReporteCTR = tipoReporteCTR;
    }

    public List<Catalogoreportes> getListaCatalogoReportes() {
        return listaCatalogoReportes;
    }

    public void setListaCatalogoReportes(List<Catalogoreportes> listaCatalogoReportes) {
        this.listaCatalogoReportes = listaCatalogoReportes;
    }

    //aqui agregue Jesus Ruelas
    //codigo metodos Jesus Ruelas
    public String getTituloGraficas() {
        return tituloGraficas;
    }

    public String getTituloTabla() {
        return tituloTabla;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public void mostrarForma() {
        cargarPorROL();
        List<Rol> list;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();

        String catalogo = "Generar reporte";
        loginBean.TienePermiso(list, seleccionado, catalogo);
    }

    public boolean isDirectorOrAdmin() {
        if (loginBean.getRolMenu().equals("Administrador") || loginBean.getRolMenu().equals("Director")) {
            return true;
        } else {
            return false;
        }
    }

    public void setDirectorOrAdmin(boolean directorOrAdmin) {
        this.directorOrAdmin = directorOrAdmin;
    }

    public Programaeducativo getPrimerOptionPE() {
        if (isDirectorOrAdmin()) {
            primerOptionPE.setPEDnombre("Selecciona uno");
            primerOptionPE.setPEDclave(0);
        } else {
            System.out.println("ID del USUario " + getUsuarioIdProfesor());
            setPrimerOptionPE(filtrosBeanHelper.getProgramaeducativo(161));
        }
        return primerOptionPE;
    }

    public void setPrimerOptionPE(Programaeducativo primerOptionPE) {
        this.primerOptionPE = primerOptionPE;
    }

    private int getUsuarioIdProfesor() {
        return filtrosBeanHelper.getProfesorByUsu(usuarioAct.getUSUid());
    }

    public String setAdminOptPE() {
        if (isDirectorOrAdmin()) {
            programaeducativo = new Programaeducativo();
            return "Seleccione uno";
        } else {
            programaeducativo = filtrosBeanHelper.getProgramaeducativo(161);
            return programaeducativo.getPEDnombre();
        }
    }

    private List<Unidadaprendizaje> distinctUnidades(List<Unidadaprendizaje> unidadesTempList) {
        List<Unidadaprendizaje> result = new ArrayList<>();

        Set<Integer> areas = new HashSet<>();
        for (Unidadaprendizaje unidadAprendizaje : unidadesTempList) {
            if (areas.add(unidadAprendizaje.getUAPid())) {
                result.add(unidadAprendizaje);
            }
        }

        return result;
    }

    public void setAreaAdmin(boolean areaAdmin) {
        this.areaAdmin = areaAdmin;
    }

    public void renderByCriterio() {
        renderAreaAdminForm = false;
        renderAreaConForm = false;
        renderProfesorForm = false;
        renderProgramaForm = false;
        renderUnidadForm = false;

        if (criterio.equals("programa_educativo")) {
            renderProgramaForm = true;
        }

        if (criterio.equals("area_conocimiento")) {
            renderAreaConForm = true;
        }

        if (criterio.equals("area_administrativa")) {
            renderAreaAdminForm = true;
        }

        if (criterio.equals("unidad_aprendizaje")) {
            renderUnidadForm = true;
        }

        if (criterio.equals("profesor")) {
            renderProfesorForm = true;
        }

    }

    public boolean isRenderProgramaForm() {
        return renderProgramaForm;
    }

    public void setRenderProgramaForm(boolean renderProgramaForm) {
        this.renderProgramaForm = renderProgramaForm;
    }

    public boolean isRenderAreaConForm() {
        return renderAreaConForm;
    }

    public void setRenderAreaConForm(boolean renderAreaConForm) {
        this.renderAreaConForm = renderAreaConForm;
    }

    public boolean isRenderAreaAdminForm() {
        return renderAreaAdminForm;
    }

    public void setRenderAreaAdminForm(boolean renderAreaAdminForm) {
        this.renderAreaAdminForm = renderAreaAdminForm;
    }

    public boolean isRenderUnidadForm() {
        return renderUnidadForm;
    }

    public void setRenderUnidadForm(boolean renderUnidadForm) {
        this.renderUnidadForm = renderUnidadForm;
    }

    public boolean isRenderProfesorForm() {
        return renderProfesorForm;
    }

    public void setRenderProfesorForm(boolean renderProfesorForm) {
        this.renderProfesorForm = renderProfesorForm;
    }

    private void cargarPorROL() {

        setUnidadacademica(filtrosBeanHelper.findUnidadAcademica(32));

    }

    public void cargarAreaConocimiento() {
        selectAreaConocimiento.clear();
        for (Planestudio plan : selectPlanesEstudio) {
            selectAreaConocimiento.addAll(plan.getAreaconocimientoList());
        }
    }

    public void mostrarActualizacion() {
        System.out.println("Unidades " + unidadacademica.getUACnombre());
    }

    public List<Programaeducativo> getProgramasEducativos() {
        List<Programaeducativo> lista = new ArrayList();

        if (isAdmin) {
            return unidadacademica.getProgramaeducativoList();
        } else if (isCordinadorAreaAdmin) {
            lista.add(PeCoordinador);
        } else {
            lista.add(PeResponsable);
        }

        return lista;
    }

    List<Planestudio> pEL;
    List<Planestudio> vacio;

    public List<Planestudio> getPlanesEstudios() {
        try {
            if (programaeducativo.getPEDid() == 0) {
                vacio = programaeducativo.getPlanestudioList();
            }
            if (programaeducativo == null) {
                pEL = vacio;
            } else {
                pEL = programaeducativo.getPlanestudioList();
            }
        } catch (NullPointerException e) {
            pEL = vacio;
        }

        return pEL;
    }

    public Unidadacademica consultaUnidadAcademica(int unidadAcademicaId) {
        return filtrosBeanHelper.getUnidadAcademicaById(unidadAcademicaId);
    }

    List<Profesor> pL;
    List<Profesor> vacio2;

    public List<Profesor> getProfesoresPorPlan() {
        pL = new ArrayList();

        if (isCordinadorAreaAdmin) {
            pL = ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorCoordinadoryCE(loginBean.getLogueado().getProfesorList().get(0).getPROid(), String.valueOf(idCicloEscolar));
        } else {

            try {
                if (programaeducativo.getPEDid() == 0) {
//                    vacio2 = programaeducativo.getProfesorList();
                    List<Profesor> poProfesorList = new ArrayList<Profesor>();
                    for (ResponsableProgramaEducativo rpe : programaeducativo.getResponsableProgramaEducativoList()) {
                        if (!poProfesorList.contains(rpe.getProfesor())) {
                            poProfesorList.add(rpe.getProfesor());
                        }
                    }
                    vacio2 = poProfesorList;
                }
                if (programaeducativo == null) {
                    pL = vacio2;
                } else {
                    pL = ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorPEyCE(Integer.parseInt(programaEducativoId), String.valueOf(idCicloEscolar));
                }
            } catch (NullPointerException | NumberFormatException e) {
                pL = vacio2;
            }
        }

        return pL;
    }

    public double calcularPorcentaje(int numEsperados, int numEntregados) {
        double porcentEntregados = (numEntregados * 100);
        double porcentaje;

        if (numEsperados == 0) {
            porcentaje = 0;
        } else {
            porcentaje = porcentEntregados / numEsperados;
            DecimalFormat formato = new DecimalFormat("#.00");
            porcentaje = Double.parseDouble(formato.format(porcentaje));
        }

        return porcentaje;
    }

    public String obtenerNombreCompletoRPE(Profesor profe) {
        return profe.getPROnombre() + " " + profe.getPROapellidoPaterno() + " " + profe.getPROapellidoMaterno();
    }

    public String getProgramaEducativoId() {
        return programaEducativoId;
    }

    public void setProgramaEducativoId(String programaEducativoId) {
        this.programaEducativoId = programaEducativoId;
    }

    public List<String> getSelectProfesorString() {
        return selectProfesorString;
    }

    public void setSelectProfesorString(List<String> selectProfesorString) {
        this.selectProfesorString = selectProfesorString;
    }

    public List<String> getSelectProgramaEducativoString() {
        return selectProgramaEducativoString;
    }

    public void setSelectProgramaEducativoString(List<String> selectProgramaEducativoString) {
        this.selectProgramaEducativoString = selectProgramaEducativoString;
    }

    public List<String> getSelectUnidadAprendizajeUniForm() {
        return selectUnidadAprendizajeUniForm;
    }

    public void setSelectUnidadAprendizajeUniForm(List<String> selectUnidadAprendizajeUniForm) {
        this.selectUnidadAprendizajeUniForm = selectUnidadAprendizajeUniForm;

    }

    public List<String> getSelectAreaAdminString() {
        return selectAreaAdminString;
    }

    public void setSelectAreaAdminString(List<String> selectAreaAdminString) {
        this.selectAreaAdminString = selectAreaAdminString;
    }

    public String getUnidadAcademicaId() {
        return unidadAcademicaId;
    }

    public void setUnidadAcademicaId(String unidadAcademicaId) {
        this.unidadAcademicaId = unidadAcademicaId;
    }

    public void guardarPrograma() {
        programaeducativo = filtrosBeanHelper.getProgramaeducativo(Integer.parseInt(programaEducativoId));
    }

    public List<EntidadSemaforo> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<EntidadSemaforo> entidades) {
        this.entidades = entidades;
    }

    public void guardarPlan() {
        selectPlanesEstudio.clear();
        for (String plan : selectPlanesEstudioUniForm) {
            selectPlanesEstudio.add(filtrosBeanHelper.getPlanEstudio(Integer.parseInt(plan)));
        }

    }

    public void guardarAreaCon() {
        selectAreaConocimiento.clear();
        for (String areaCon : selectAreaConocimientoUniForm) {
            selectAreaConocimiento.add(filtrosBeanHelper.getAreaConocimiento(Integer.parseInt(areaCon)));
        }
    }

    public void guardarAreaAdmin() {
        selectAreaAdministrativa.clear();
        for (String areaAdministrativa : selectAreaAdminString) {
            selectAreaAdministrativa.add(filtrosBeanHelper.getAreaAdministrativa(Integer.parseInt(areaAdministrativa)));
        }
    }

    public void guardarUnidad() {
        selectUnidadAprendizaje.clear();
        for (String unidadApr : selectUnidadAprendizajeUniForm) {
            selectUnidadAprendizaje.add(filtrosBeanHelper.getUnidadAprendizaje(Integer.parseInt(unidadApr)));
        }
    }

    public void guardarGrupo() {
        selectGrupo = selectGrupoUniForm;
    }

    public void guardarProfesor() {
        selectProfesor.clear();
        for (String profesor : selectProfesorString) {
            selectProfesor.add(filtrosBeanHelper.getProfesor(Integer.parseInt(profesor)));
        }
    }

    public List<ResumenReporte> getResumenesReporte() {
        return resumenesReporte;
    }

    public void setResumenesReporte(List<ResumenReporte> resumenesReporte) {
        this.resumenesReporte = resumenesReporte;
    }

    public String getHeaderTipoReporte() {
        return headerTipoReporte;
    }

    public void setHeaderTipoReporte(String headerTipoReporte) {
        this.headerTipoReporte = headerTipoReporte;
    }

    public String getHeaderCriterio() {
        return headerCriterio;
    }

    public void setHeaderCriterio(String headerCriterio) {
        this.headerCriterio = headerCriterio;
    }

    public boolean isRenderPlanResumen() {
        return renderPlanResumen;
    }

    public void setRenderPlanResumen(boolean renderPlanResumen) {
        this.renderPlanResumen = renderPlanResumen;
    }

    public String getHeaderPorcentaje() {
        return headerPorcentaje;
    }

    public void setHeaderPorcentaje(String headerPorcentaje) {
        this.headerPorcentaje = headerPorcentaje;
    }

    public boolean isHabilitarBusqueda() {
        return habilitarBusqueda;
    }

    public void setHabilitarBusqueda(boolean habilitarBusqueda) {
        this.habilitarBusqueda = habilitarBusqueda;
    }

    public boolean isHabilitarCriterios() {
        return HabilitarCriterios;
    }

    public void setHabilitarCriterios(boolean HabilitarCriterios) {
        this.HabilitarCriterios = HabilitarCriterios;
    }

    public String getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(String detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public Boolean getRenderDR1() {
        return renderDR1;
    }

    public void setRenderDR1(Boolean renderDR1) {
        this.renderDR1 = renderDR1;
    }

    public Boolean getRenderDR2() {
        return renderDR2;
    }

    public void setRenderDR2(Boolean renderDR2) {
        this.renderDR2 = renderDR2;
    }

    public Boolean getRenderDR3() {
        return renderDR3;
    }

    public void setRenderDR3(Boolean renderDR3) {
        this.renderDR3 = renderDR3;
    }

    public String getHeaderEsperados() {
        return headerEsperados;
    }

    public void setHeaderEsperados(String headerEsperados) {
        this.headerEsperados = headerEsperados;
    }

}
