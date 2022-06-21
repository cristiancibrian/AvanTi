
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import static com.lowagie.text.Row.NULL;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import static com.lowagie.text.xml.simpleparser.EntitiesToSymbol.map;
import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.AlumnoPerteneceGrupo;
import mx.avanti.siract.entity.Articulos;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.entity.PoliticaTieneCriterio;

import mx.avanti.siract.helper.UsuarioBeanHelper;
import mx.avanti.siract.helper.PoliticaEvaluacionBeanHelper;

import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.helper.MensajeBeanHelper;
import org.primefaces.PrimeFaces;

/**
 *
 * @author balta
 */
@ManagedBean
@ViewScoped
public class PoliticaEvaluacionBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")

    private LoginBean loginBean;
    private int IDCATALOGOPoliticaEval = 21;

    //<editor-fold defaultstate="collapsed" desc="VARIABLES STRING.">
    private String fechaActual = "";
    private String fechaSistema;
    private String comentarioOpcional = "";
    String nombreCompletoProfesor = "";
    String programaEducativoSeleccionado = "0";
    String claveUnidadAprendizajeSeleccionada = "0";
    String criterioExentar = "";
    String cicloEscolar = "";
    private String estiloSumaPor = "";
    private String mensajePorcentajes = "";
    private String fechaRegistroPOL;
    private String alertasCriterios = "";
    String cicloEscolarActual = "";
    String gpo = "";
    private String fileNamePdf = "";
    private String nombreProgEdu = "";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="VARIABLES BOOLEANS.">
    Boolean disablePDF = false;
    private Boolean disableUnidades = true;
    Boolean disableRepresentante = true;
    private Boolean disableBotonGE = true;
    private Boolean disableBotonGC = true;
    private Boolean disableBotonAC = true;
    private Boolean disableMenuCriterios = true;
    private Boolean disablePE = false;
    private Boolean disableCriterioEX = true;
    private Boolean disableComentarios = true;
    private Boolean disablePoliticasIn = false;
    private Boolean disableCriterios = true;
    Boolean bandSumaPor = false;
    Boolean disableTextoExamen = false;
    Boolean disableSpinerExamen = false;
    String renderSpinerExamen = "none";
    int porcentajeMax = 100;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="VARIABLES TIPO LISTA.">
    List<Articulos> articulosEstatuto;
    List<Criterioevaluacion> criteriosEvaluacion;
    List<Alumno> alumnos;
    private List<Alumno> alumnosClase;
    private List<PoliticaTieneCriterio> listaPoliticaCriterio;
    List<AlumnoPerteneceGrupo> alumnopertenecegrupo;
    List<Alumno> alumnosfiltrados;
    List<Programaeducativo> programasEducativos;
    List<UnidadaprendizajeImparteProfesor> unidadesProfesor;
    private List<PoliticaTieneCriterio> politicaTieneCriterio;
    private List<String> criterioSelect;
    private List<String> nombreCriterios;
    private List<Politicaevaluacion> politicasIncompletas;
    private List<Calendarioreporte> fechasPoliticas;
    List<Politicaevaluacion> PoliticasRegistradas;
    List<PoliticaTieneCriterio> ptcEliminar;
    private List<PoliticaTieneCriterio> listaaux = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="VARIABLES INT.">
    private int IdCriteriosEvaluacion = 0;
    private int sumaPorcentajes = 0;
    int unidadImparteProfesorSeleccionada = 0;
    int unidadAprendizajeSeleccionada = 0;
    int grupoid;
    private int representanteGrupo = 0;
    private int stepFactor = 5;
    private int politicaIncompletaSelect = 0;
    private int NExamen = 0;
    int gponum = 0;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="VARIABLES TIPO ENTIDAD.">
    private Politicaevaluacion politica;
    Alumno al;
    Profesor profesor;
    Programaeducativo programaEducativoPolitica;
    private Cicloescolar cicloActual;
    private Programaeducativo nombreProgramaEducativo;
    private Unidadaprendizaje nombreUnidadAprendisaje;
    private Alumno nombreMatriculaAlumno;
    private PoliticaTieneCriterio porcenjajes;
    private Profesor RPE;
    UnidadaprendizajeImparteProfesor unidadImparte;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="VARIABLES HASHMAP.">
    HashMap<Integer, Criterioevaluacion> mapCriterioEvaluacion;
    HashMap<Integer, PoliticaTieneCriterio> mapPoliticaTieneCriterio;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="VARIABLES BEAN HELPER.">
    private PoliticaEvaluacionBeanHelper politicaevaluacionBeanHelper = null;
    private UsuarioBeanHelper usuarioBeanHelper = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor y posConstructor.">
    public PoliticaEvaluacionBeanUI() {
        init();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaSistema = sdf.format(new Date());
        ptcEliminar = new ArrayList<PoliticaTieneCriterio>();
        articulosEstatuto = politicaevaluacionBeanHelper.findAllArticulos();
        listaPoliticaCriterio = new ArrayList<>();
        criteriosEvaluacion = politicaevaluacionBeanHelper.getAllcriterios();
        Comparator<Criterioevaluacion> criteriosComparator = Comparator.comparing(Criterioevaluacion::getCEVNombre);
        criteriosEvaluacion.sort(criteriosComparator);
        mapCriterioEvaluacion = new HashMap<>();
        mapPoliticaTieneCriterio = new HashMap<>();
        cicloActual = politicaevaluacionBeanHelper.cicloEscolarActual();
        cicloEscolar = cicloActual.getCEScicloEscolar();
        politica = new Politicaevaluacion();
        unidadImparte = new UnidadaprendizajeImparteProfesor();

        for (Criterioevaluacion criterio : criteriosEvaluacion) {
            mapCriterioEvaluacion.put(criterio.getCEVId(), criterio);
        }
        cicloEscolarActual = politicaevaluacionBeanHelper.cicloEscolarActual().getCEScicloEscolar();

    }

    @PostConstruct
    public void postConstructor() {

        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            try {
                profesor = loginBean.getLogueado().getProfesorList().get(0);
                PoliticasRegistradas = politicaevaluacionBeanHelper
                        .politicasProfesor(loginBean.getLogueado().getProfesorList().get(0).getPROid());
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            }

            if (profesor == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            }
        }
        nombreProfesor();

    }
    //</editor-fold>
    private MensajeBeanHelper mensajeBeanHelper = null;
    private List<Mensaje> message;

    //Metodos.
    public void init() {
        politica = new Politicaevaluacion();
        politicaevaluacionBeanHelper = new PoliticaEvaluacionBeanHelper();
        mensajeBeanHelper = new MensajeBeanHelper();

    }

    public List<Programaeducativo> programasEducativosProfesor() {
        programasEducativos = politicaevaluacionBeanHelper.programasEducativosProfesor(profesor, cicloEscolar);
        return politicaevaluacionBeanHelper.programasEducativosProfesor(profesor, cicloEscolar);
    }

    public Programaeducativo programaEducativoProfesorPolitica() {
        programaEducativoPolitica = politicaevaluacionBeanHelper.programaEducativoProfesorPolitica(profesor, cicloEscolar);
        programaEducativoSeleccionado = programaEducativoPolitica.getPEDid().toString();
        return programaEducativoPolitica;
    }

    public String nombreProfesor() {
        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            //profesor = ractBeanHelper.getProfesorFromUsuario(loginBean.getLogueado());
            profesor = loginBean.getLogueado().getProfesorList().get(0);
            if (profesor == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            } else {
                nombreCompletoProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();

            }
        }
        return nombreCompletoProfesor;
    }

    public UnidadaprendizajeImparteProfesor ObtenerUnidadAprendizajeImparteProfesor(int idUnidad) {
        UnidadaprendizajeImparteProfesor unidadImparte = new UnidadaprendizajeImparteProfesor();
        List<UnidadaprendizajeImparteProfesor> lista = politicaevaluacionBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));

        for (UnidadaprendizajeImparteProfesor uiap : lista) {
            if (uiap.getUIPid() == idUnidad) {
                unidadImparte = uiap;
                break;
            }
        }

        return unidadImparte;
    }

    public void reiniciarDatos() {
        disableBotonAC = true;
        disableBotonGC = true;
        disableBotonGE = true;
        disableCriterios = true;
        disableMenuCriterios = true;
        disableRepresentante = true;
        disableCriterioEX = true;
        disableComentarios = true;
        sumaPorcentajes = 0;
        mensajePorcentajes = "";
        criterioExentar = "";
        comentarioOpcional = "";
        representanteGrupo = 0;
        unidadAprendizajeSeleccionada = 0;
        estiloSumaPor = "color:black;";
        alertasCriterios = "";
        listaPoliticaCriterio.clear();
        mapPoliticaTieneCriterio.clear();
        disableTextoExamen = false;
        disableSpinerExamen = false;

        PrimeFaces.current().ajax().update("formId:gs");
        PrimeFaces.current().ajax().update("formId:criterioExentar");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:representante");
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:cAvance");
        PrimeFaces.current().ajax().update("formId:mensajeSum");
        PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
        PrimeFaces.current().ajax().update("formId:agregarCriterio");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:alertaCriterios");

        PrimeFaces.current().ajax().update("formId:UnidadesAprendisaje");
        onSelectPE();
    }

    public void onSelectUA() {
        PoliticasRegistradas = politicaevaluacionBeanHelper
                .politicasProfesor(loginBean.getLogueado().getProfesorList().get(0).getPROid());
        if (unidadAprendizajeSeleccionada == Integer.parseInt("00")) {
            disableRepresentante = true;
            disableBotonGE = true;
            representanteGrupo = 0;
            disableBotonAC = true;
            disableMenuCriterios = true;
            disableComentarios = true;
            disableCriterioEX = true;
            sumaPorcentajes = 0;
            estiloSumaPor = "color:black;";
            listaPoliticaCriterio.clear();
            criterioExentar = "";
            comentarioOpcional = "";
            IdCriteriosEvaluacion = 0;
            mapPoliticaTieneCriterio.clear();

            disableGuardarCambios();

        } else {
//            // limpio politica evaluacion

            politica = new Politicaevaluacion();
            //  politica.setResponsablePROId(profesor);
            ptcEliminar = new ArrayList<PoliticaTieneCriterio>();
            mapPoliticaTieneCriterio.clear();

            for (UnidadaprendizajeImparteProfesor uip : unidadesProfesor) {
                if (uip.getUIPid() == unidadAprendizajeSeleccionada) {
                    politica.setUIPId(uip);
                    gpo = String.valueOf(politica.getUIPId().getGrupoGPOid().getGPOnumero());
                    String ng = String.valueOf(gpo.charAt(5)) + String.valueOf(gpo.charAt(6)) + String.valueOf(gpo.charAt(7));
                    gponum = Integer.parseInt(ng);
                    fileNamePdf = "Políticas de evaluación" + "--" + uip.getUnidadAprendizajeUAPid().getUAPnombre() + "--" + gponum;
                    break;
                }
            }
            alumnosClase = politicaevaluacionBeanHelper.AlumnosPorUIP(unidadAprendizajeSeleccionada);

            comentarioOpcional = "";
            criterioExentar = "";
            representanteGrupo = 0;
            listaPoliticaCriterio = new ArrayList<PoliticaTieneCriterio>();
            listaPoliticaCriterio.clear();
            listaaux.clear();
            for (Politicaevaluacion po : PoliticasRegistradas) {
                if (po.getUIPId().getUIPid() == unidadAprendizajeSeleccionada) {

                    politica = po;
                    setComentarioOpcional(po.getPOEComentario());
                    setCriterioExentar(po.getPOECriterioExentar());
                    if (po.getResponsablePROId() != null) {
                        setRepresentanteGrupo(po.getAlumnoALId().getALId());
                        disableBotonGE = false;
                    } else {
                        disableBotonGE = true;
                    }
                    setListaPoliticaCriterio(po.getPoliticaTieneCriterioList());
                    criterioExentar = po.getPOECriterioExentar();
                    break;
                }
            }
            for (PoliticaTieneCriterio ptc : listaPoliticaCriterio) {
                listaaux.add(ptc);
            }

            disableTextoExamen = false;
            disableSpinerExamen = false;
            alertasCriterios = "";
            disableBotonAC = false;
            disableMenuCriterios = false;
            disableComentarios = false;
            disableCriterioEX = false;
            disableRepresentante = false;

            for (int cont = 0; cont < listaPoliticaCriterio.size(); cont++) {
                mapPoliticaTieneCriterio.put(listaPoliticaCriterio.get(cont).getCEVId().getCEVId(), listaPoliticaCriterio.get(cont));
                if (listaPoliticaCriterio.get(cont).getCEVId().getCEVNombre().equalsIgnoreCase("Examenes Parciales")) {
                    disableTextoExamen = true;
                    disableSpinerExamen = true;
                    NExamen = listaPoliticaCriterio.get(cont).getNExamen();
                }
            }
            actualizarSumaPorcentajes();
        }
        alumnosClase = politicaevaluacionBeanHelper.AlumnosPorUIP(unidadAprendizajeSeleccionada);
        PrimeFaces.current().ajax().update("formId:textoExamen");
        PrimeFaces.current().ajax().update("formId:spinerExamen");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:exporter");
        PrimeFaces.current().ajax().update("formId:alertaCriterios");
        PrimeFaces.current().ajax().update("formId:representante");
        //PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:agregarCriterio");
        PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentar");
        PrimeFaces.current().ajax().update("formId:cAvance");
        disableGuardarCambios();
        //Mostrar mensaje de rechazos
        String mensaje = "";
        List<Politicaevaluacioncomentario> pol = new ArrayList<Politicaevaluacioncomentario>();
        if (politica.getEstadoPolEva() != null) {
            switch (politica.getEstadoPolEva()) {
                case 3:
                    pol = politica.getPoliticaevaluacioncomentarioList();
                    for (Politicaevaluacioncomentario p : pol) {
                        mensaje = p.getPECComentario();
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Rechazado por el alumno: " + mensaje));
                    break;
                case 5:
                    pol = politica.getPoliticaevaluacioncomentarioList();
                    for (Politicaevaluacioncomentario p : pol) {
                        mensaje = p.getPECComentario();
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Rechazado por RPE: " + mensaje));
                    break;
                default:
                    break;
            }
        }
    }

    public void onSelectRepresentate() {
        politica.setAlumnoALId(new Alumno(representanteGrupo));
        if (representanteGrupo == Integer.parseInt("00")) {
            disableBotonGE = true;
            disableGuardarCambios();

        } else {
            for (Alumno al : alumnosClase) {
                if (al.getALId() == representanteGrupo) {
                    politica.setAlumnoALId(al);
                }
            }
            disableBotonGE = false;
            disableGuardarCambios();

        }
    }

    public Boolean disableBotones() {

        if (!disableCriterios && !disableBotonGE && !programaEducativoSeleccionado.equals("00") && unidadAprendizajeSeleccionada != 0) {
            return false;
        } else {
            return true;
        }

    }

    public void disableGuardarCambios() {
        if (!programaEducativoSeleccionado.equals("00") && unidadAprendizajeSeleccionada != 0 && !listaPoliticaCriterio.isEmpty() && representanteGrupo != 0 && sumaPorcentajes <= 100) {
            disableBotonGC = false;
        } else if (programaEducativoSeleccionado.equals("00") || unidadAprendizajeSeleccionada == 0 || listaPoliticaCriterio.isEmpty() || representanteGrupo == 0 || sumaPorcentajes > 100) {
            disableBotonGC = true;
        }
        PrimeFaces.current().ajax().update("formId:gs");
    }

    public void guardarPolitica(boolean esEnvio, boolean tipoBoton) {

        boolean guardadoExitoso;
        FacesContext contexto = FacesContext.getCurrentInstance();

        politica.setPOECriterioExentar(criterioExentar);
        politica.setPOEComentario(comentarioOpcional);
        politica.setPoliticaTieneCriterioList(listaPoliticaCriterio);
        politica.setResponsablePROId(RPE);
        guardadoExitoso = politicaevaluacionBeanHelper.guardarPolitica(politica, esEnvio, ptcEliminar);

        if (esEnvio) {
            generarFecha(true);

            enviarCorreo();
            PrimeFaces.current().executeScript("pdf();");
            contexto.addMessage(null, new FacesMessage("Guardado exitoso", "Política de evaluación enviada y guardada correctamente "));

            //PrimeFaces.current().executeScript("location.reload()");
        } else {
            contexto.addMessage(null, new FacesMessage("Guardado exitoso", "Política de evaluación se ha guardada correctamente "));
            PrimeFaces.current().ajax().update("formId:growl");
            if (tipoBoton) {

                generarFecha(false);
                PrimeFaces.current().executeScript("pdf();");
            }

        }

        reiniciarDatos();

    }

    public void generarFecha(boolean esEnvio) {
        SimpleDateFormat formato = new SimpleDateFormat();
        if (esEnvio) {
            fechaActual = formato.format(new Date());
        } else {
            fechaActual = "";
        }
    }

    public void onSelectPE() {
        PoliticasRegistradas = politicaevaluacionBeanHelper
                .politicasProfesor(loginBean.getLogueado().getProfesorList().get(0).getPROid());
        if (programaEducativoSeleccionado.equals("00")) {
            disableUnidades = true;
            disableBotonGE = true;
            disableRepresentante = true;
            disableCriterios = true;
            disableMenuCriterios = true;
            disableCriterioEX = true;
            disableComentarios = true;
            sumaPorcentajes = 0;
            mensajePorcentajes = "";
            criterioExentar = "";
            comentarioOpcional = "";
            unidadAprendizajeSeleccionada = 0;
            estiloSumaPor = "color:black;";
            alertasCriterios = "";
            listaPoliticaCriterio.clear();
            mapPoliticaTieneCriterio.clear();

            PrimeFaces.current().ajax().update("formId:criterioExentar");
            PrimeFaces.current().ajax().update("formId:textCom");
            PrimeFaces.current().ajax().update("formId:representante");
            PrimeFaces.current().ajax().update("formId:tablaCriterios");
            PrimeFaces.current().ajax().update("formId:cAvance");
            PrimeFaces.current().ajax().update("formId:mensajeSum");
            PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
            PrimeFaces.current().ajax().update("formId:agregarCriterio");
            PrimeFaces.current().ajax().update("formId:ge");
            PrimeFaces.current().ajax().update("formId:alertaCriterios");

            PrimeFaces.current().ajax().update("formId:UnidadesAprendisaje");
            FacesContext contexto = FacesContext.getCurrentInstance();

            disableGuardarCambios();

        } else {
            RPE = politicaevaluacionBeanHelper.RPEporCEyPE(Integer.parseInt(programaEducativoSeleccionado));
            politica.setResponsablePROId(RPE);
            unidadesProfesor = politicaevaluacionBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));
            int programaEducativoSeleccionado2 = Integer.parseInt(programaEducativoSeleccionado);
            for (int x = 0; x < programasEducativos.size(); x++) {
                if (programasEducativos.get(x).getPEDid() == programaEducativoSeleccionado2) {
                    nombreProgEdu = programasEducativos.get(x).getPEDnombre();
                }
            }
            //-------------------------------------------------FILTRADO-------------------------------------------------

            List<UnidadaprendizajeImparteProfesor> listaFiltrada = new ArrayList();
            /* Quitare las unidades de aprendizaje que no tienen contenido tematico*/
            for (UnidadaprendizajeImparteProfesor up : unidadesProfesor) {
                boolean claseylab = politicaevaluacionBeanHelper.alumnosengrupo(up.getCicloEscolarCESid().getCESid(), up.getUnidadAprendizajeUAPid().getUAPid(), up.getGrupoGPOid().getGPOid());
                boolean existe = false;
                UnidadaprendizajeImparteProfesor cambio = null;
                if (listaFiltrada != null) {
                    for (UnidadaprendizajeImparteProfesor filtro : listaFiltrada) {
                        if (claseylab && up.getUnidadAprendizajeUAPid().getUAPid() == filtro.getUnidadAprendizajeUAPid().getUAPid() && up.getProfesorPROid() == filtro.getProfesorPROid() && up.getGrupoGPOid() == filtro.getGrupoGPOid()) {
                            existe = true;
                            if (up.getUIPtipoSubgrupo().equals("C")) {
                                existe = false;
                                cambio = filtro;
                                break;
                            }
                        }
                    }
                } else {
                    listaFiltrada.add(up);
                }
                if ((!existe && cambio != null)) {
                    listaFiltrada.add(up);
                    listaFiltrada.remove(cambio);
                } else if (!existe) {
                    listaFiltrada.add(up);
                }
            }
            /*Filtrar politica dependiendo el estado*/
            for (Politicaevaluacion politicasFiltradas : PoliticasRegistradas) {
                if (politicasFiltradas.getEstadoPolEva() == 1 || politicasFiltradas.getEstadoPolEva() == 2 || politicasFiltradas.getEstadoPolEva() == 4) {
                    listaFiltrada.remove(politicasFiltradas.getUIPId());
                }
            }
            unidadesProfesor = listaFiltrada;

            //fillTree();
            //enviado = true;
            disablePDF = false;
            disableUnidades = false;
            disableGuardarCambios();
            unidadImparteProfesorSeleccionada = 0;
            /*asigno valor para que desaperezcan los iconos de seleccionado parcial
        y completo */

//        RequestContext.getCurrentInstance().update("formId:message");
            //   validarEnviado();
        }
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {
        final Document pdf = (Document) document;
    }

    public void postProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

//        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
        String nombrep = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
        String numEmpPro = Integer.toString(profesor.getPROnumeroEmpleado());

        //Obtener el nombre del programa educativo seleccionado
        int programaEducativoSeleccionado2 = Integer.parseInt(programaEducativoSeleccionado);
        for (int x = 0; x < programasEducativos.size(); x++) {
            if (programasEducativos.get(x).getPEDid() == programaEducativoSeleccionado2) {
                nombreProgEdu = programasEducativos.get(x).getPEDnombre();
            }
        }
        //----------------------------------------------------------------------------------

        //Obtener el nombre de la unidad de aprendizaje y clave seleccionada
        String nombreUniApr = "";
        String nombreclave = "";

        // List<UnidadaprendizajeImparteProfesor> lista = politicaevaluacionBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));
        for (UnidadaprendizajeImparteProfesor uiap : unidadesProfesor) {
            if (uiap.getUIPid() == unidadAprendizajeSeleccionada) {
                unidadImparte = uiap;
                nombreUniApr = uiap.getUnidadAprendizajeUAPid().getUAPnombre();
                nombreclave = Integer.toString(uiap.getUnidadAprendizajeUAPid().getUAPclave());
                break;
            }
        }

        //int uniAprselec2 = unidadImparte.getUnidadAprendizajeUAPid().getUAPid();
        try {
//           //Imagen logo de uabc parte superior izquierda
            Image imagenLogo = Image.getInstance(RACTBeanUI.class.getResource("/logo.png"));
//
            //Posicion de imagen (Horizontal, Vertiacal)
            imagenLogo.setAbsolutePosition(45f, 770f); //10,460

            //Tamaño de imagen (Ancho, largo)
            imagenLogo.scaleAbsolute(60, 60);//120

            pdf.add(imagenLogo);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }

        try {
            //Imagen logo FIM, parte superior derecha
            Image imagenFIM = Image.getInstance(RACTBeanUI.class.getResource("/FIM.png"));
////
//            //Posicion de imagen (Horizontal, Vertiacal)
            imagenFIM.setAbsolutePosition(20f, 130f); //10,460
//
//            //Tamaño de imagen (Ancho, largo)
            imagenFIM.scaleAbsolute(60, 60);//120
            pdf.add(imagenFIM);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }
        //Tabla con UABC y Nombre del profesor(a)
        PdfPTable pdfTabletitulo = new PdfPTable(2);
        pdfTabletitulo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdfTabletitulo2 = new PdfPTable(4);
        pdfTabletitulo2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdfTabletitulo3 = new PdfPTable(2);
        pdfTabletitulo3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 20, Font.BOLD, new Color(0, 0, 0)));
        UABC.setAlignment(Element.ALIGN_CENTER);
        Paragraph Facultad = new Paragraph("Facultad de Ingeniería ", FontFactory.getFont(FontFactory.TIMES, 20, Font.BOLD, new Color(0, 0, 0)));
        Facultad.setAlignment(Element.ALIGN_CENTER);
        Paragraph textoPoliticas = new Paragraph("Políticas para la evaluación permanente ordinaria que prevé el articulo 68 del Estatuto Escolar ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0)));
        textoPoliticas.setAlignment(Element.ALIGN_CENTER);
        pdf.add(UABC);
        pdf.add(Facultad);
        pdf.add(textoPoliticas);
        pdf.add(new Phrase("\n"));

        //pdfTabletitulo3.addCell(new Paragraph("Fecha registro: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        // pdfTabletitulo3.addCell(new Paragraph(politica.getFechaCreacion().toString(), FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo.addCell(new Paragraph("Programa educativo: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo.addCell(new Paragraph(nombreProgEdu, FontFactory.getFont(FontFactory.TIMES, 12)));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));

        pdfTabletitulo2.addCell(new Paragraph("Nombre del maestro: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD)));
        pdfTabletitulo2.addCell(new Paragraph(nombrep, FontFactory.getFont(FontFactory.TIMES, 12)));

        pdfTabletitulo2.addCell(new Paragraph("No. de empleado: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo2.addCell(new Paragraph("" + profesor.getPROnumeroEmpleado(), FontFactory.getFont(FontFactory.TIMES, 12)));

        pdfTabletitulo3.setHorizontalAlignment(25);
        float[] columnWidthsss3 = new float[]{15f, 50};//4,28
        pdfTabletitulo3.setWidths(columnWidthsss3);
        pdf.add(pdfTabletitulo3);

        pdfTabletitulo.setHorizontalAlignment(25);

        float[] columnWidthsss = new float[]{19f, 50};//4,28
        pdfTabletitulo.setWidths(columnWidthsss);
        pdf.add(pdfTabletitulo);

        pdfTabletitulo2.setHorizontalAlignment(25);
        float[] columnWidthss = new float[]{4.5f, 7, 3f, 2}; //10,38
        pdfTabletitulo2.setWidths(columnWidthss);
        pdf.add(pdfTabletitulo2);

//        pdf.add(new Phrase(" "));
        //----------------------------------------------------------------------
        //Tabla Cabezera
        PdfPTable pdftablecabezera = new PdfPTable(2);
        pdftablecabezera.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdftablecabezera2 = new PdfPTable(4);
        pdftablecabezera2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));
//
        pdftablecabezera.addCell(new Paragraph("Unidad de aprendizaje: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(politica.getUIPId().getUnidadAprendizajeUAPid().getUAPnombre(), FontFactory.getFont(FontFactory.TIMES, 12)));

        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));

        pdftablecabezera2.addCell(new Paragraph("Clave: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("" + politica.getUIPId().getUnidadAprendizajeUAPid().getUAPclave(), FontFactory.getFont(FontFactory.TIMES, 12)));

        String gpo = String.valueOf(politica.getUIPId().getGrupoGPOid().getGPOnumero());
        char[] digitos = gpo.toCharArray();
        int len = digitos.length;

        pdftablecabezera2.addCell(new Paragraph("Grupo: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("" + gponum, FontFactory.getFont(FontFactory.TIMES, 12)));

        String tipoGrupo = "";
//        switch (unidadImparte.getUIPtipoSubgrupo()) {
//            case "C":
//                tipoGrupo = "Clase";
//                break;
//            case "L":
//                tipoGrupo = "Laboratorio";
//                break;
//            case "T":
//                tipoGrupo = "Taller";
//                break;
//            case "P":
//                tipoGrupo = "Practica";
//                break;
//            case "CL":
//                tipoGrupo = "Practicas clínica";
//                break;
//        }
//        pdftablecabezera2.addCell(new Paragraph("    " + tipoGrupo));
        pdftablecabezera2.addCell(new Paragraph(" "));

        pdftablecabezera.setHorizontalAlignment(25);
        float[] columnWidths = new float[]{14f, 31};
        pdftablecabezera.setWidths(columnWidths);
        pdf.add(pdftablecabezera);

        pdftablecabezera2.setHorizontalAlignment(25);
        float[] columnWidthss2 = new float[]{.9f, 2, .9f, 5};
        pdftablecabezera2.setWidths(columnWidthss2);
        pdf.add(pdftablecabezera2);

        pdf.add(new Phrase(" "));

        Paragraph textoAspectos = new Paragraph("Aspectos que integran la calificación del alumno : ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD));
        textoAspectos.setAlignment(Element.ALIGN_CENTER);
        pdf.add(textoAspectos);

        Paragraph textoEstosAspectos = new Paragraph("Estos aspectos deberán ser acordes a lo establecido en el estatuto escolar y", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD));
        textoEstosAspectos.setAlignment(Element.ALIGN_CENTER);
        pdf.add(textoEstosAspectos);

        Paragraph textoSubra = new Paragraph(" Considerando el programa de la unidad de aprendizaje de la unidad de aprendizaje:", FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE));
        textoSubra.setAlignment(Element.ALIGN_CENTER);
        pdf.add(textoSubra);

        pdf.add(new Phrase(" "));
        //----------------------------------------------------------------------
        PdfPTable pdftablecabezera3 = new PdfPTable(2);
        pdftablecabezera3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftablecabezera3.addCell(new Paragraph("Criterios ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera3.addCell(new Paragraph("Porcentaje ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        int suma = 0;
        for (PoliticaTieneCriterio ptc : getListaaux()) {
            suma += ptc.getPorcentaje();
            if (ptc.getCEVId().getCEVNombre().equalsIgnoreCase("examenes parciales")) {

                pdftablecabezera3.addCell(new Paragraph(ptc.getCEVId().getCEVNombre() + "(" + ptc.getNExamen() + ")", FontFactory.getFont(FontFactory.TIMES, 12)));

            } else {
                pdftablecabezera3.addCell(new Paragraph(ptc.getCEVId().getCEVNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
            }
            pdftablecabezera3.addCell(new Paragraph(ptc.getPorcentaje() + "%", FontFactory.getFont(FontFactory.TIMES, 12)));

        }

        pdftablecabezera3.addCell(new Paragraph(""));
        pdftablecabezera3.addCell(new Paragraph("Total: " + suma + "%", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD)));
        pdftablecabezera3.setHorizontalAlignment(Element.ALIGN_CENTER);
        float[] columnWidths3 = new float[]{1f, 2f};
        pdftablecabezera3.setWidths(columnWidths3);
        pdf.add(pdftablecabezera3);

        pdf.add(new Phrase(" "));
        pdf.add(new Phrase(" "));

        PdfPTable pdftablecabezera4 = new PdfPTable(1);
        pdftablecabezera4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftablecabezera4.addCell(new Paragraph("Criterios para exentar la unidad de aprendizaje: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        //pdftablecabezera4.addCell(new Paragraph(""));
        pdftablecabezera4.setHorizontalAlignment(25);
        float[] columnWidths4 = new float[]{20f};
        pdftablecabezera4.setWidths(columnWidths4);
        pdf.add(pdftablecabezera4);

        PdfPTable pdftableCriEx = new PdfPTable(1);
        pdftableCriEx.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        if (!politica.getPOECriterioExentar().isEmpty()) {
            pdftableCriEx.addCell(new Paragraph(politica.getPOECriterioExentar(), FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));
        } else {
            pdftableCriEx.addCell(new Paragraph("-" + "\n" + "-", FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));
        }
        pdftableCriEx.setHorizontalAlignment(25);
        float[] columnWidthsCriEx = new float[]{20f};
        pdftableCriEx.setWidths(columnWidthsCriEx);
        pdf.add(pdftableCriEx);
        pdf.add(new Phrase("\n"));

        PdfPTable pdftablecabezera5 = new PdfPTable(1);
        pdftablecabezera5.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftablecabezera5.addCell(new Paragraph("Comentarios adicionales: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        pdftablecabezera5.setHorizontalAlignment(25);
        float[] columnWidths5 = new float[]{1f};
        pdftablecabezera5.setWidths(columnWidths5);
        pdf.add(pdftablecabezera5);

        PdfPTable pdftablecabezera9 = new PdfPTable(1);
        pdftablecabezera9.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        if (!politica.getPOEComentario().isEmpty()) {
            pdftablecabezera9.addCell(new Paragraph(politica.getPOEComentario(), FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));
        } else {
            pdftablecabezera9.addCell(new Paragraph("-" + "\n" + "-", FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));
        }
        pdftablecabezera9.setHorizontalAlignment(25);
        float[] columnWidths9 = new float[]{1f};
        pdftablecabezera9.setWidths(columnWidths9);
        pdf.add(pdftablecabezera9);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableArticulos = new PdfPTable(1);
        pdftableArticulos.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        for (Articulos ar : articulosEstatuto) {

            pdftableArticulos.addCell(new Paragraph("Articulo " + ar.getNumeroArticulo() + ". " + ar.getContenido() + "\n", FontFactory.getFont(FontFactory.TIMES, 10)));

        }

        pdftableArticulos.setHorizontalAlignment(25);
        float[] columnWidthsArticulos = new float[]{20f};
        pdftableArticulos.setWidths(columnWidthsArticulos);
        pdf.add(pdftableArticulos);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftablecabezera6 = new PdfPTable(2);
        pdftablecabezera6.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftablecabezera6.addCell(new Paragraph("Representante de grupo: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        pdftablecabezera6.addCell(new Paragraph(politica.getAlumnoALId().getALNombre() + " " + politica.getAlumnoALId().getALApellidoPater() + " " + politica.getAlumnoALId().getALApellidoMaterno() + " -- " + politica.getAlumnoALId().getALMatricula(), FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));

        pdftablecabezera6.setHorizontalAlignment(25);
        float[] columnWidths6 = new float[]{4.7f, 10};
        pdftablecabezera6.setWidths(columnWidths6);
        pdf.add(pdftablecabezera6);
        pdf.add(new Phrase("\n"));

        PdfPTable pdftableFechaAl = new PdfPTable(2);
        pdftableFechaAl.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableFechaAl.addCell(new Paragraph("Fecha de aceptación representante de grupo: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftableFechaAl.addCell(new Paragraph("", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        pdftableFechaAl.setHorizontalAlignment(25);
        float[] columnWidthsAl = new float[]{2.7f, 1};
        pdftableFechaAl.setWidths(columnWidthsAl);
        pdf.add(pdftableFechaAl);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableRPE = new PdfPTable(2);
        pdftableRPE.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableRPE.addCell(new Paragraph("RPE: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftableRPE.addCell(new Paragraph(RPE.getPROnombre() + " " + RPE.getPROapellidoPaterno() + " " + RPE.getPROapellidoMaterno(), FontFactory.getFont(FontFactory.TIMES, 12)));

        pdftableRPE.setHorizontalAlignment(25);
        float[] columnWidthsRPE2 = new float[]{.9f, 10};
        pdftableRPE.setWidths(columnWidthsRPE2);
        pdf.add(pdftableRPE);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableFechaRPE = new PdfPTable(2);
        pdftableFechaRPE.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableFechaRPE.addCell(new Paragraph("Fecha de aceptación RPE: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftableFechaRPE.addCell(new Paragraph("", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        pdftableFechaRPE.setHorizontalAlignment(25);
        float[] columnWidthsRPE = new float[]{2.7f, 1};
        pdftableFechaRPE.setWidths(columnWidthsRPE);
        pdf.add(pdftableFechaRPE);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableFechaCre = new PdfPTable(2);
        pdftableFechaCre.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftableFechaCre.addCell(new Paragraph("Fecha de Creación: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        pdftableFechaCre.addCell(new Paragraph(fechaActual, FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));

        pdftableFechaCre.setHorizontalAlignment(25);
        float[] columnWidthsCre = new float[]{4f, 10};
        pdftableFechaCre.setWidths(columnWidthsCre);
        pdf.add(pdftableFechaCre);
    }

    public void onSelectCriterio() {
        renderSpinerExamen = "none";

        for (int x = 0; x < criteriosEvaluacion.size(); x++) {
            if (criteriosEvaluacion.get(x).getCEVId() == IdCriteriosEvaluacion && criteriosEvaluacion.get(x).getCEVNombre().equalsIgnoreCase("Examenes parciales")) {
                disableTextoExamen = true;
                disableSpinerExamen = true;
                renderSpinerExamen = "";

            }
        }
        PrimeFaces.current().ajax().update("formId:spinerExamen2");
        PrimeFaces.current().ajax().update("formId:textoSpinnerExam");
        PrimeFaces.current().ajax().update("formId:agregarCriterio");

    }

    public void actualizarTablaCriterios() {
        if (IdCriteriosEvaluacion == Integer.parseInt("00")) {
            alertasCriterios = "";

        } else if (mapPoliticaTieneCriterio.containsKey(IdCriteriosEvaluacion)) {
            alertasCriterios = "Criterio ya agregado";
            IdCriteriosEvaluacion = 0;
            disableTextoExamen = true;
            disableSpinerExamen = true;
            renderSpinerExamen = "none";
            PrimeFaces.current().ajax().update("formId:spinerExamen2");
            PrimeFaces.current().ajax().update("formId:textoSpinnerExam");
            PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
        } else {
            PoliticaTieneCriterio politicaCriterio = new PoliticaTieneCriterio();
            // politicaCriterio.setPtcId(IdCriteriosEvaluacion);
            politicaCriterio.setCEVId(mapCriterioEvaluacion.get(IdCriteriosEvaluacion));
            politicaCriterio.setPorcentaje(0);
            if (politicaCriterio.getCEVId().getCEVNombre().equalsIgnoreCase("Examenes Parciales")) {
                disableTextoExamen = true;
                disableSpinerExamen = true;
                renderSpinerExamen = "none";
                PrimeFaces.current().ajax().update("formId:spinerExamen2");
                PrimeFaces.current().ajax().update("formId:textoSpinnerExam");
                politicaCriterio.setNExamen(NExamen);

            } else {
                NExamen = 0;
                politicaCriterio.setNExamen(NExamen);
            }
            mapPoliticaTieneCriterio.put(IdCriteriosEvaluacion, politicaCriterio);
            listaPoliticaCriterio.add(politicaCriterio);
            listaaux.add(politicaCriterio);
            IdCriteriosEvaluacion = 0;

            PrimeFaces.current().ajax().update("formId:textoExamen");
            PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
            PrimeFaces.current().ajax().update("formId:spinerExamen");
            PrimeFaces.current().ajax().update("formId:tablaCriterios");

            alertasCriterios = "";
            disableGuardarCambios();

        }
        PrimeFaces.current().ajax().update("formId:alertaCriterios");

    }

    public void keyupSpinner(int porcentaje) {

    }

    public void keyupComentarios() {

    }

    public void quitarCriterios(PoliticaTieneCriterio politicaCriterio) {
        if (politicaCriterio.getCEVId().getCEVId() == 2) {
            disableTextoExamen = false;
            disableSpinerExamen = false;
        }
        listaaux.clear();
        PrimeFaces.current().ajax().update("formId:textoExamen");
        PrimeFaces.current().ajax().update("formId:spinerExamen");
        listaPoliticaCriterio.clear();
        try {
            mapPoliticaTieneCriterio.remove(politicaCriterio.getCEVId().getCEVId());
            org.apache.commons.collections.MapUtils.invertMap(mapPoliticaTieneCriterio);
            for (Map.Entry entry : mapPoliticaTieneCriterio.entrySet()) {
                listaPoliticaCriterio.add((PoliticaTieneCriterio) entry.getValue());
            }
        } catch (Exception e) {
        }

        sumaPorcentajes -= politicaCriterio.getPorcentaje();

        ptcEliminar.add(politicaCriterio);
        bandSumaPor = false;

        if (sumaPorcentajes < 100) {
            estiloSumaPor = "color:red;";

            disableCriterios = true;
            disableBotonAC = false;
            disableMenuCriterios = false;
            PrimeFaces.current().ajax().update("formId:agregarCriterio");
            PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
        }

        if (sumaPorcentajes == 0) {
            sumaPorcentajes = 0;
            estiloSumaPor = "color:black;";
            mensajePorcentajes = "";
            disableGuardarCambios();
        }
        if (listaPoliticaCriterio.isEmpty()) {
            sumaPorcentajes = 0;
            estiloSumaPor = "color:black;";
            mensajePorcentajes = "";
            disableGuardarCambios();
        }
        for(PoliticaTieneCriterio lista: listaPoliticaCriterio){
            listaaux.add(lista);
        }
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:cAvance");
    }

    public void actualizarSumaPorcentajes() {

        sumaPorcentajes = 0;

        for (int y = 0; y < listaPoliticaCriterio.size(); y++) {
            sumaPorcentajes += listaPoliticaCriterio.get(y).getPorcentaje();
        }

        if (sumaPorcentajes != 0) {

            if (sumaPorcentajes < 100) {
                disableGuardarCambios();
                mensajePorcentajes = "";
                disableBotonAC = false;
                disableMenuCriterios = false;
                disableCriterios = true;

                estiloSumaPor = "color:red;";

            } else if (sumaPorcentajes > 100) {
                disableBotonAC = true;
                disableMenuCriterios = true;
                IdCriteriosEvaluacion = 0;

                estiloSumaPor = "color:red;";
                disableCriterios = true;
                disableGuardarCambios();

            } else if (sumaPorcentajes == 100) {
                disableBotonAC = true;
                disableMenuCriterios = true;
                IdCriteriosEvaluacion = 0;
                disableCriterios = false;

                estiloSumaPor = "color:green;";
                disableGuardarCambios();

            }

        } else {
            mensajePorcentajes = "";
            estiloSumaPor = "color:black;";
            disableCriterios = true;
        }

        PrimeFaces.current().ajax().update("formId:ge");

        PrimeFaces.current().ajax().update("formId:agregarCriterio");
        PrimeFaces.current().ajax().update("formId:tablaCriterios");

        PrimeFaces.current().ajax().update("formId:criteriosAlumnos");
        PrimeFaces.current().ajax().update("formId:cAvance");
        PrimeFaces.current().ajax().update("formId:mensajeSum");
        PrimeFaces.current().ajax().update("formId:sumaPorcentajes");
    }

    public List<Alumno> obteneralumnos() {
        alumnos = politicaevaluacionBeanHelper.getAllalumnos();
        alumnosfiltrados = politicaevaluacionBeanHelper.getAllalumnos();
        alumnosfiltrados.removeAll(alumnos);
        System.out.println(alumnosfiltrados);
        if (disableRepresentante == false) {
            for (int x = 0; x < alumnopertenecegrupo.size(); x++) {
                for (int y = 0; y < alumnos.size(); y++) {
                    if (alumnos.get(y).getALMatricula() == alumnopertenecegrupo.get(x).getAlumnoALId().getALMatricula()) {
                        al = alumnos.get(y);
                        alumnosfiltrados.add(al);
                    }
                }
                System.out.println(x + " es menor que " + alumnopertenecegrupo.size());
            }
            return alumnosfiltrados;
        } else {
            return alumnos;
        }
    }

    public String rendered() {
        //if (!unidadAprendizajeSeleccionada.equals("00") && disablePDF ) {
        if (unidadImparteProfesorSeleccionada == 0 && disablePDF) {
            return "visibility: visible;";
        }
        if (!disablePDF) {
            return "visibility:hidden";

        }
        return "visibility:hidden";
    }

    public void enviarCorreo() {
        String nombreAlumno = "";
        String destinatario = "";
        String unidadAprendizaje = "";
        String nombreProfesor = "";

        message = mensajeBeanHelper.getMensajes();
        System.out.println("Entro al metodo");
        String correoEnvia = "siract.fim@uabc.edu.mx";
        String contrasena = "3aMKAPJt&g";
        String TituloAlerta = "Política de evaluación asignada";
        int grupo = politica.getUIPId().getGrupoGPOid().getGPOnumero();
        String gpo = String.valueOf(grupo);
        String gpro = String.valueOf(gpo.charAt(5)) + String.valueOf(gpo.charAt(6)) + String.valueOf(gpo.charAt(7));
        nombreAlumno = politica.getAlumnoALId().getALNombre() + " " + politica.getAlumnoALId().getALApellidoPater() + " " + politica.getAlumnoALId().getALApellidoMaterno();
        destinatario = politica.getAlumnoALId().getALCorreo();
        nombreProfesor = politica.getUIPId().getProfesorPROid().getPROnombre() + " " + politica.getUIPId().getProfesorPROid().getPROapellidoPaterno() + " " + politica.getUIPId().getProfesorPROid().getPROapellidoMaterno();
        String destinatario2 = "ivan.rosas@uabc.edu.mx";
        String destinatario3 = politica.getUIPId().getProfesorPROid().getUsuarioUSUid().getUSUusuario() + "@uabc.edu.mx";
        System.out.println("Correo de maestro: " + destinatario3);
        unidadAprendizaje = politica.getUIPId().getUnidadAprendizajeUAPid().getUAPnombre();

        String MENSAJE_HTML = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
                + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
                + "            <table style=\"margin: 0 auto; width: 480px; margin-top: 20px; border-radius: 10px; border: 4px green solid;\">\n"
                + "                <tr>\n"
                + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
                + "                        \n" + TituloAlerta
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <td>\n"
                + "                        <p style=\"text-align: center; color:black; font-family: arial; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
                + "                                Facultad de Ingeniería\n"
                + "                        </p>\n"
                + "                        <p style=\"font-family: arial; color:black;  font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
                + "                        <br>\n"
                + "                        <b>Estimado(a): name "
                + "                        <br>\n"
                + "</p>"
                // + "   <b> NOTA: Debido al corte de energía eléctrica en el campus, SIRACT estará disponible para realizar el tercer reporte de avance hasta el 4 de enero de 2021.  Por lo que, la fecha límite se extenderá hasta el 8 de enero. </b> <br><br> "
                + "</p>"
                + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "
                + "                      Mensajee\n"
                + "</p>"
                + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br><br>"
                + "</div>"
                + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                + "                        Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
                + "                        al Administrador del Sistema siract.fim@uabc.edu.mx.<br>"
                //                + "                        Asimismo, si requiere conocer los pasos a seguir para realizar el reporte de avance consultar el\n"
                //                + "                        <a href=\"https://www.youtube.com/watch?v=PogX3JBdISI\" target=\"_blank\">TUTORIAL SIRACT</a>. \n"
                + "                        </div>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "            <p style=\"text-align: center; font-family: arial; color:black; font-size: 12px;\">\n"
                + "                ©SIRACT\n"
                + "            </p>\n"
                + "        </div>";
        //Alumno
        Properties propiedad = new Properties();
        propiedad.put("mail.smtp.host", "smtp.gmail.com");
        propiedad.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.user", correoEnvia);

        Session sesion;
        sesion = Session.getDefaultInstance(propiedad);

        MimeMessage mail = new MimeMessage(sesion);
        MimeMultipart mp = new MimeMultipart("related");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        //Profesor
        Properties propiedadProfesor = new Properties();
        propiedadProfesor.put("mail.smtp.host", "smtp.gmail.com");
        propiedadProfesor.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        propiedadProfesor.setProperty("mail.smtp.port", "587");
        propiedadProfesor.setProperty("mail.smtp.auth", "true");
        propiedadProfesor.setProperty("mail.smtp.starttls.enable", "true");
        propiedadProfesor.setProperty("mail.smtp.user", correoEnvia);

        Session sesionProfesor;
        sesionProfesor = Session.getDefaultInstance(propiedadProfesor);

        MimeMessage mailProfesor = new MimeMessage(sesionProfesor);
        MimeMultipart mpProfesor = new MimeMultipart("related");
        MimeBodyPart messageBodyPartProfesor = new MimeBodyPart();

        //Alumno
        try {
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject("Politicas de Evaluacion");

            MENSAJE_HTML = MENSAJE_HTML.replace("name", nombreAlumno);
            MENSAJE_HTML = MENSAJE_HTML.replace("Mensajee", message.get(11).getMENmensaje());
            MENSAJE_HTML = MENSAJE_HTML.replace("[UA]", "<b>" + unidadAprendizaje + "</b>");
            MENSAJE_HTML = MENSAJE_HTML.replace("[gponum]", "<b>" + gpro + "</b>");

            messageBodyPart.setText(MENSAJE_HTML, "UTF-8", "html");
            mp.addBodyPart(messageBodyPart);
            mail.setContent(mp);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia, contrasena);
            transporte.sendMessage(mail, mail.getAllRecipients());
            transporte.close();
            System.out.println("Correo enviado");
        } catch (AddressException ex) {
            Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Correo enviado");
        MENSAJE_HTML = MENSAJE_HTML.replace(nombreAlumno, "name");
        MENSAJE_HTML = MENSAJE_HTML.replace("<b>" + unidadAprendizaje + "</b>", "[UA]");
        MENSAJE_HTML = MENSAJE_HTML.replace("<b>" + gpro + "</b>", "[gponum]");
        // MENSAJE_HTML = MENSAJE_HTML.replace(message.get(11).getMENmensaje(),"mm");

        //Profesor
        try {
            mailProfesor.setFrom(new InternetAddress(correoEnvia));
            mailProfesor.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario3));
            mailProfesor.setSubject("Politicas de Evaluacion");

            MENSAJE_HTML = MENSAJE_HTML.replace(TituloAlerta, "Política de evaluación entregada");
            MENSAJE_HTML = MENSAJE_HTML.replace("name", nombreProfesor);
            MENSAJE_HTML = MENSAJE_HTML.replace(message.get(11).getMENmensaje(), message.get(12).getMENmensaje());
            MENSAJE_HTML = MENSAJE_HTML.replace("[gpo]", "<b>" + gpro + "</b>");
            MENSAJE_HTML = MENSAJE_HTML.replace("[UA]", "<b>" + unidadAprendizaje + "</b>");
            MENSAJE_HTML = MENSAJE_HTML.replace("[NOMBRE DEL ALUMNO]", "<b>" + nombreAlumno + "</b>");

            messageBodyPartProfesor.setText(MENSAJE_HTML, "UTF-8", "html");
            mpProfesor.addBodyPart(messageBodyPartProfesor);
            mailProfesor.setContent(mpProfesor);

            Transport transporteProfesor = sesionProfesor.getTransport("smtp");
            transporteProfesor.connect(correoEnvia, contrasena);
            transporteProfesor.sendMessage(mailProfesor, mailProfesor.getAllRecipients());
            transporteProfesor.close();
            System.out.println("Correo enviado");
        } catch (AddressException ex) {
            Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Correo enviado");

    }

    public List<PoliticaTieneCriterio> obtenercriterios(int id) {
        politicaTieneCriterio = politicaevaluacionBeanHelper.getAllPoliticaTieneCriterio(id);
        return politicaTieneCriterio;
    }

//*******       SETTERS Y GETTERS.   *********
    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS STRING.">
    public String getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(String fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public String getFileNamePdf() {
        return fileNamePdf;
    }

    public void setFileNamePdf(String fileNamePdf) {
        this.fileNamePdf = fileNamePdf;
    }

    public String getCicloEscolarActual() {
        return cicloEscolarActual;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public String getAlertasCriterios() {
        return alertasCriterios;
    }

    public void setAlertasCriterios(String alertasCriterios) {
        this.alertasCriterios = alertasCriterios;
    }

    public String getCicloEscolar() {
        return cicloEscolar;
    }

    public void setCicloEscolar(String cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public String getCriterioExentar() {
        return criterioExentar;
    }

    public void setCriterioExentar(String criterioExentar) {
        this.criterioExentar = criterioExentar;
    }

    public String getFechaRegistroPOL() {
        return fechaRegistroPOL;
    }

    public void setFechaRegistroPOL(String fechaRegistroPOL) {
        this.fechaRegistroPOL = fechaRegistroPOL;
    }

    public String getEstiloSumaPor() {
        return estiloSumaPor;
    }

    public void setEstiloSumaPor(String estiloSumaPor) {
        this.estiloSumaPor = estiloSumaPor;
    }

    public String getProgramaEducativoSeleccionado() {
        return programaEducativoSeleccionado;
    }

    public void setProgramaEducativoSeleccionado(String programaEducativoSeleccionado) {
        if (!programaEducativoSeleccionado.equals(this.programaEducativoSeleccionado)) {
            setUnidadAprendizajeSeleccionada(0);
        }
        this.programaEducativoSeleccionado = programaEducativoSeleccionado;
        if (programaEducativoSeleccionado.equals("00")) {
            System.out.println("PROGRAMA EDUCATIV0 SELECCIONADO ");
            claveUnidadAprendizajeSeleccionada = "0";
            unidadAprendizajeSeleccionada = 0;
            representanteGrupo = 0;

        }
    }

    public String getClaveUnidadAprendizajeSeleccionada() {
        return claveUnidadAprendizajeSeleccionada;
    }

    public void setClaveUnidadAprendizajeSeleccionada(String claveUnidadAprendizajeSeleccionada) {
        this.claveUnidadAprendizajeSeleccionada = claveUnidadAprendizajeSeleccionada;
    }

    public String getComentarioOpcional() {
        return comentarioOpcional;
    }

    public void setComentarioOpcional(String comentarioOpcional) {
        this.comentarioOpcional = comentarioOpcional;
    }

    public String getNombreCompletoProfesor() {
        return nombreCompletoProfesor;
    }

    public void setNombreCompletoProfesor(String nombreCompletoProfesor) {
        this.nombreCompletoProfesor = nombreCompletoProfesor;
    }

    public String getMensajePorcentajes() {
        return mensajePorcentajes;
    }

    public void setMensajePorcentajes(String mensajePorcentajes) {
        this.mensajePorcentajes = mensajePorcentajes;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS BOOLEANS.">
    public Boolean getDisablePoliticasIn() {
        return disablePoliticasIn;
    }

    public void setDisablePoliticasIn(Boolean disablePoliticasIn) {
        this.disablePoliticasIn = disablePoliticasIn;
    }

    public Boolean getDisableComentarios() {
        return disableComentarios;
    }

    public void setDisableComentarios(Boolean disableComentarios) {
        this.disableComentarios = disableComentarios;
    }

    public Boolean getDisableCriterioEX() {
        return disableCriterioEX;
    }

    public void setDisableCriterioEX(Boolean disableCriterioEX) {
        this.disableCriterioEX = disableCriterioEX;
    }

    public Boolean getDisablePE() {
        return disablePE;
    }

    public void setDisablePE(Boolean disablePE) {
        this.disablePE = disablePE;
    }

    public Boolean getDisableMenuCriterios() {
        return disableMenuCriterios;
    }

    public void setDisableMenuCriterios(Boolean disableMenuCriterios) {
        this.disableMenuCriterios = disableMenuCriterios;
    }

    public Boolean getDisableBotonAC() {
        return disableBotonAC;
    }

    public void setDisableBotonAC(Boolean disableBotonAC) {
        this.disableBotonAC = disableBotonAC;
    }

    public Boolean getDisableBotonGC() {
        return disableBotonGC;
    }

    public void setDisableBotonGC(Boolean disableBotonGC) {
        this.disableBotonGC = disableBotonGC;
    }

    public Boolean getDisableCriterios() {
        return disableCriterios;
    }

    public void setDisableCriterios(Boolean disableCriterios) {
        this.disableCriterios = disableCriterios;
    }

    public Boolean getDisableBotonGE() {
        return disableBotonGE;
    }

    public void setDisableBotonGE(Boolean disableBotonGE) {
        this.disableBotonGE = disableBotonGE;
    }

    public Boolean getDisableRepresentante() {
        return disableRepresentante;
    }

    public void setDisableRepresentante(Boolean disableRepresentante) {
        this.disableRepresentante = disableRepresentante;
    }

    public Boolean getDisableUnidades() {
        return disableUnidades;
    }

    public void setDisableUnidades(Boolean disableUnidades) {
        this.disableUnidades = disableUnidades;
    }

    public Boolean getDisablePDF() {
        return disablePDF;
    }

    public void setDisablePDF(Boolean disablePDF) {
        this.disablePDF = disablePDF;
    }

    public Boolean getDisableTextoExamen() {
        return disableTextoExamen;
    }

    public void setDisableTextoExamen(Boolean disableTextoExamen) {
        this.disableTextoExamen = disableTextoExamen;
    }

    public Boolean getDisableSpinerExamen() {
        return disableSpinerExamen;
    }

    public void setDisableSpinerExamen(Boolean disableSpinerExamen) {
        this.disableSpinerExamen = disableSpinerExamen;
    }

    //</editor-fold>
    public int getNExamen() {
        return NExamen;
    }

    public void setNExamen(int NExamen) {
        this.NExamen = NExamen;
    }

    public int getIDCATALOGOPoliticaEval() {
        return IDCATALOGOPoliticaEval;
    }

    public String getRenderSpinerExamen() {
        return renderSpinerExamen;
    }

    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS INT.">
    public void setIDCATALOGOPoliticaEval(int IDCATALOGOPoliticaEval) {
        this.IDCATALOGOPoliticaEval = IDCATALOGOPoliticaEval;
    }

    public int getPoliticaIncompletaSelect() {
        return politicaIncompletaSelect;
    }

    public void setPoliticaIncompletaSelect(int politicaIncompletaSelect) {
        this.politicaIncompletaSelect = politicaIncompletaSelect;
    }

    public int getStepFactor() {
        return stepFactor;
    }

    public void setStepFactor(int stepFactor) {
        this.stepFactor = stepFactor;
    }

    public int getRepresentanteGrupo() {
        return representanteGrupo;
    }

    public void setRepresentanteGrupo(int representanteGrupo) {
        this.representanteGrupo = representanteGrupo;
    }

    public int getUnidadAprendizajeSeleccionada() {
        return unidadAprendizajeSeleccionada;
    }

    public void setUnidadAprendizajeSeleccionada(int unidadAprendizajeSeleccionada) {

        this.unidadAprendizajeSeleccionada = unidadAprendizajeSeleccionada;

    }

    public int getUnidadImparteProfesorSeleccionada() {
        return unidadImparteProfesorSeleccionada;
    }

    public void setUnidadImparteProfesorSeleccionada(int unidadImparteProfesorSeleccionada) {
        this.unidadImparteProfesorSeleccionada = unidadImparteProfesorSeleccionada;
        unidadAprendizajeSeleccionada = unidadImparteProfesorSeleccionada;
    }

    public int getSumaPorcentajes() {
        return sumaPorcentajes;
    }

    public void setSumaPorcentajes(int sumaPorcentajes) {
        this.sumaPorcentajes = sumaPorcentajes;
    }

    public int getIdCriteriosEvaluacion() {
        return IdCriteriosEvaluacion;
    }

    public void setIdCriteriosEvaluacion(int IdCriteriosEvaluacion) {
        this.IdCriteriosEvaluacion = IdCriteriosEvaluacion;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS TIPO LISTA.">
    public List<Alumno> getAlumnosClase() {
        return alumnosClase;
    }

    public void setAlumnosClase(List<Alumno> alumnosClase) {
        this.alumnosClase = alumnosClase;
    }

    public List<Calendarioreporte> getFechasPoliticas() {
        return fechasPoliticas;
    }

    public void setFechasPoliticas(List<Calendarioreporte> fechasPoliticas) {
        this.fechasPoliticas = fechasPoliticas;
    }

    public List<Politicaevaluacion> getPoliticasIncompletas() {
        return politicasIncompletas;
    }

    public void setPoliticasIncompletas(List<Politicaevaluacion> politicasIncompletas) {
        this.politicasIncompletas = politicasIncompletas;
    }

    public List<Alumno> getAlumnosfiltrados() {
        return alumnosfiltrados;
    }

    public void setAlumnosfiltrados(List<Alumno> alumnosfiltrados) {
        this.alumnosfiltrados = alumnosfiltrados;
    }

    public List<PoliticaTieneCriterio> getPoliticaTieneCriterio() {
        return politicaTieneCriterio;
    }

    public void setPoliticaTieneCriterio(List<PoliticaTieneCriterio> politicaTieneCriterio) {
        this.politicaTieneCriterio = politicaTieneCriterio;
    }

    public List<String> getNombreCriterios() {
        return nombreCriterios;
    }

    public void setNombreCriterios(List<String> nombreCriterios) {
        this.nombreCriterios = nombreCriterios;
    }

    public List<UnidadaprendizajeImparteProfesor> getUnidadesProfesor() {
        return unidadesProfesor;
    }

    public void setUnidadesProfesor(List<UnidadaprendizajeImparteProfesor> unidadesProfesor) {
        this.unidadesProfesor = unidadesProfesor;
    }

    public List<Programaeducativo> getProgramasEducativos() {

        return programasEducativos;
    }

    public void setProgramasEducativos(List<Programaeducativo> programasEducativos) {
        this.programasEducativos = programasEducativos;
    }

    public List<Criterioevaluacion> getCriteriosEvaluacion() {
        return criteriosEvaluacion;
    }

    public void setCriteriosEvaluacion(List<Criterioevaluacion> criteriosEvaluacion) {
        this.criteriosEvaluacion = criteriosEvaluacion;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<PoliticaTieneCriterio> getListaPoliticaCriterio() {
        return listaPoliticaCriterio;
    }

    public void setListaPoliticaCriterio(List<PoliticaTieneCriterio> listaPoliticaCriterio) {
        this.listaPoliticaCriterio = listaPoliticaCriterio;
    }

    public int getPorcentajeMax() {
        return porcentajeMax;
    }

    public void setPorcentajeMax(int porcentajeMax) {
        this.porcentajeMax = porcentajeMax;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS TIPO ENTIDAD.">
    public Politicaevaluacion getPolitica() {
        return politica;
    }

    public void setPolitica(Politicaevaluacion politica) {
        this.politica = politica;
    }

    public Programaeducativo getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(Programaeducativo nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }

    public Programaeducativo getProgramaEducativoPolitica() {
        return programaEducativoPolitica;
    }

    public void setProgramaEducativoPolitica(Programaeducativo programaEducativoPolitica) {
        this.programaEducativoPolitica = programaEducativoPolitica;
    }

    public Unidadaprendizaje getNombreUnidadAprendisaje() {
        return nombreUnidadAprendisaje;
    }

    public void setNombreUnidadAprendisaje(Unidadaprendizaje nombreUnidadAprendisaje) {
        this.nombreUnidadAprendisaje = nombreUnidadAprendisaje;
    }

    public Alumno getNombreMatriculaAlumno() {
        return nombreMatriculaAlumno;
    }

    public void setNombreMatriculaAlumno(Alumno nombreMatriculaAlumno) {
        this.nombreMatriculaAlumno = nombreMatriculaAlumno;
    }

    public PoliticaTieneCriterio getPorcenjajes() {
        return porcenjajes;
    }

    public void setPorcenjajes(PoliticaTieneCriterio porcenjajes) {
        this.porcenjajes = porcenjajes;
    }

    public Cicloescolar getCicloActual() {
        return cicloActual;
    }

    public void setCicloActual(Cicloescolar cicloActual) {
        this.cicloActual = cicloActual;
    }

    public Politicaevaluacion getPoliticaevaluacion() {
        return politica;
    }

    public void setPoliticaevaluacion(Politicaevaluacion politica) {
        this.politica = politica;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS TIPO BEAN HELPER.">
    public PoliticaEvaluacionBeanHelper getPoliticaEvaluacionBeanHelper() {
        return politicaevaluacionBeanHelper;
    }

    public void setPoliticaEvaluacionBeanHelper(PoliticaEvaluacionBeanHelper politicaevaluacionBeanHelper) {
        this.politicaevaluacionBeanHelper = politicaevaluacionBeanHelper;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS Y GETTERS LoginBean.">
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    //</editor-fold>

    /**
     * @return the listaaux
     */
    public List<PoliticaTieneCriterio> getListaaux() {
        return listaaux;
    }

    /**
     * @param listaaux the listaaux to set
     */
    public void setListaaux(List<PoliticaTieneCriterio> listaaux) {
        this.listaaux = listaaux;
    }
}
