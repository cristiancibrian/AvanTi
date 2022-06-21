/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.Articulos;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.entity.ResponsableProgramaEducativo;
import mx.avanti.siract.helper.PoliticaEvaluacionBeanHelper;
import org.primefaces.PrimeFaces;

/**
 *
 * @author eduardocardona
 */
@ManagedBean
@ViewScoped
public class ConsultarPoliticasBeanUI {

    @ManagedProperty(value = "#{loginBean}")

    private LoginBean loginBean;
    private PoliticaEvaluacionBeanHelper politicaevaluacionBeanHelper;
    private int IDCATALOGOCONSULTAPOLITICA = 22;

    // variables de interfaz
    private String cicloEscolarSeleccionado = "00";
    private List<Cicloescolar> listaCiclosEscolares;
    List<Unidadaprendizaje> unidadesAprendizaje;
    List<Programaeducativo> programasEducativos;
    String programaEducativoSeleccionado = "0";
    List<Profesor> profesores;
    Integer profesorSeleccionado;
    List<UnidadaprendizajeImparteProfesor> unidadesProfesor;
    List<Programaeducativo> programasEducativosConsultas;
    List<Politicaevaluacion> politicasaceptadasRPE;
    String unidadAprendizajeSeleccionada = "00";
    Boolean disable = true;
    private Boolean disable3 = false;
    Boolean disableimp = true;
    String nombreCompletoProfesor = " ";
    int IdUaipSeleccionada = 0;
    Politicaevaluacion politicaSeleccionada;
    Politicaevaluacion politica;
    UnidadaprendizajeImparteProfesor unidadImparte;
    List<Articulos> articulosEstatuto;
    // internas
    private Profesor profesor;
    private Alumno alumno;
    private String fileNamePdf = "";
    String fechaActual = "DD/MM/AAAA";
    String fechaCreacion = "";
    String fechaAlumno = "";
    String fechaResponsable = "";
    String displayComentario = "none";
    String displayFirmas = "none";
    String displaybotonPDF = "none";
    String gpo = "";
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    int gponum = 0;

    // Constructor
    public ConsultarPoliticasBeanUI() {
        init();

    }

    public void init() {

        politicaevaluacionBeanHelper = new PoliticaEvaluacionBeanHelper();
        articulosEstatuto = politicaevaluacionBeanHelper.findAllArticulos();
    }

    @PostConstruct
    public void postConstructor() {

        profesores = new ArrayList<Profesor>();
        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            if (loginBean.getLogueado().getRolList().get(0).getROLid() == 8) {
                alumno = loginBean.getLogueado().getAlumnoList().get(0);
                if (alumno == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin alumno", "Este usuario no tiene ligado ningun alumno"));
                } else {
                    nombreCompletoProfesor = alumno.getALNombre() + " " + alumno.getALApellidoPater() + " " + alumno.getALApellidoMaterno();
                    politicasaceptadasRPE = politicaevaluacionBeanHelper.politicasaceptadasAlumno(alumno.getALId());
                }
            } else {
                if(loginBean.getLogueado().getProfesorList() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));
                }
                else{
                    if(profesor != null){
                profesor = loginBean.getLogueado().getProfesorList().get(0);
                    }
                if (profesor == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

                } else {
                    nombreCompletoProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
                    // nombreProfesor = profesor.getPROnombre();
                    //ractConsultasBeanHelper.setProfesor(profesor);
                }
                }
            }
        }

    }

    public void onSelectCE() {
        //limpiar programas educativos y unidades de aprendizaje
        //refresh();
        //limpia el checkbox de unidad de aprendizaje
        if (unidadesAprendizaje != null && unidadesAprendizaje.size() > 0) {
            unidadesAprendizaje = Collections.EMPTY_LIST;
        }
        if (cicloEscolarSeleccionado.equals("00")) {
            programaEducativoSeleccionado = "00";
            unidadAprendizajeSeleccionada = "00";
            IdUaipSeleccionada = 00;
            politicaSeleccionada = new Politicaevaluacion();
            disableimp = true;
            profesores = Collections.EMPTY_LIST;
            politicaSeleccionada = null;
            fechaCreacion = null;
            fechaAlumno = null;
            fechaResponsable = null;
            actualizaPolitica();
            disableimp = true;
            unidadesProfesor.clear();
            if (!esProfesor()){
                profesorSeleccionado = 0;
                profesores = Collections.EMPTY_LIST;
            }
            actualizaPolitica();
        } else {
            if (!cicloEscolarSeleccionado.equals("00")) {
                
                if(loginBean.getLogueado().getRolList().get(0).getROLid() == 8){
                  unidadesAprendizaje = politicaevaluacionBeanHelper.unidadesAlumno(loginBean.getLogueado().getAlumnoList().get(0).getALId(), Integer.parseInt(cicloEscolarSeleccionado) );
                }else{
                    
                // para llenar los programas eduvativos del profesor.
                profesor = loginBean.getLogueado().getProfesorList().get(0);
                programasEducativos = politicaevaluacionBeanHelper.programasEducativosProfesor2(profesor, cicloEscolarSeleccionado);

                //pone el check box de Programa Educativo en "Seleccione una opcion"
                if (esProfesor() || loginBean.getSeleccionado().equals("Administrador")) {
                    programaEducativoSeleccionado = "00";
                } else {
                    programaEducativoSeleccionado = String.valueOf(programasEducativos.get(0).getPEDid());
                }
                if (esProfesor()) {
                    System.out.println("Hola");
                    Profesor profesor = loginBean.getLogueado().getProfesorList().get(0);
                    programasEducativos = politicaevaluacionBeanHelper.programasEducativosProfesor2(profesor, cicloEscolarSeleccionado);
                    setProgramasEducativosConsultas(programasEducativos);
                }
                if (!esProfesor()) {
                    if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")) {
                        profesores = politicaevaluacionBeanHelper.getProfesoresPorCoordinadoryCE(profesor.getPROid(), cicloEscolarSeleccionado);
                        profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                        System.out.println(profesorSeleccionado);
                        unidadesProfesor = politicaevaluacionBeanHelper.uaipPorcoordinadorAreaYCicloEscolar(profesor.getPROid(), Integer.parseInt(cicloEscolarSeleccionado));
                    } else if (loginBean.getSeleccionado().equals("Responsable de Programa Educativo")) {
                        List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
                        for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList()) {
                            if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo())) {
                                peProgramaeducativoList.add(rpe.getProgramaeducativo());
                            }
                        }
                        programasEducativosConsultas = peProgramaeducativoList;
                        System.out.println(programasEducativos);
                        programaEducativoSeleccionado = programasEducativos.get(0).getPEDid().toString();
                        IdUaipSeleccionada = programasEducativos.get(0).getPEDid();
                        profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                        System.out.println(profesorSeleccionado + " a");
                        List<Profesor> profesoressinfiltro = new ArrayList<Profesor>();
                        profesores = new ArrayList<Profesor>();
                        profesoressinfiltro = politicaevaluacionBeanHelper.getProfesoresPorRPEYCE(profesor.getPROid(), cicloEscolarSeleccionado);
                        for (Profesor pro : profesoressinfiltro) {
                            System.out.println(pro);
                            if (!profesores.contains(pro)) {
                                profesores.add(pro);
                            }
                        }
                        System.out.println(profesorSeleccionado);
                        unidadesProfesor = politicaevaluacionBeanHelper.getUNIdadesaprendisajeConGrupos(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
                        unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
                        //profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                        //profesores = politicaevaluacionBeanHelper.getProfesoresPorRPEYCE(profesor.getPROid(), cicloEscolarSeleccionado);
//                    unidadesProfesor = ractConsultasBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), loginBean.getLogueado().getProfesorList().get(0).getProgramaeducativoList().get(0).getPEDid());

//                    peProgramaeducativoList.clear();
//                        if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
//                            peProgramaeducativoList.add(rpe.getProgramaeducativo());
//                    unidadesProfesor.clear();
//                    for (Programaeducativo peProgramaeducativo : peProgramaeducativoList)
                        //                       unidadesProfesor.addAll(politicaevaluacionBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), peProgramaeducativo.getPEDid()));
                    } else if (loginBean.getSeleccionado().equals("Administrador")) {
                        programasEducativos = politicaevaluacionBeanHelper.traerTodosProgramasEducativos();
                    }
                }
            }
            }
        }

    }

    public void onSelectPE() {
        //refresh();
        // si es profesor 
        profesorSeleccionado = 0;
        IdUaipSeleccionada = 0;
        if (programaEducativoSeleccionado.equals("00")) {
            unidadAprendizajeSeleccionada = "00";
            IdUaipSeleccionada = 00;
            politicaSeleccionada = new Politicaevaluacion();
            disableimp = true;
            politicaSeleccionada = null;
            fechaCreacion = null;
            fechaAlumno = null;
            fechaResponsable = null;
            actualizaPolitica();
            disableimp = true;
            unidadesProfesor.clear();
            if (!esProfesor()) {
                profesorSeleccionado = 0;
                profesores = Collections.EMPTY_LIST;
            }
            actualizaPolitica();
        }
        if (loginBean.getSeleccionado().equalsIgnoreCase("Profesor")) {
            System.out.println("RACTConsultasBeanUI:loginBeanEqualsProfesor:Entro" + programaEducativoSeleccionado);
            unidadesAprendizaje = politicaevaluacionBeanHelper.UnidadesAprendizajePorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado), cicloEscolarSeleccionado);
            unidadesProfesor = politicaevaluacionBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado), cicloEscolarSeleccionado);
            unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
        } else if (loginBean.getSeleccionado().equals("Administrador")) {
            if (!cicloEscolarSeleccionado.equals("00")) {
                System.out.println(profesorSeleccionado);
                profesorSeleccionado = 0;
                List<Profesor> profesoressinfiltro = new ArrayList<Profesor>();
                profesores = new ArrayList<Profesor>();
                profesoressinfiltro = politicaevaluacionBeanHelper.getProfesoresPorRPEYCE(profesor.getPROid(), cicloEscolarSeleccionado);
                for (Profesor pro : profesoressinfiltro) {
                    System.out.println(pro);
                    if (!profesores.contains(pro)) {
                        profesores.add(pro);
                    }
                }
                if (!programaEducativoSeleccionado.equals("00")) {
                    unidadesProfesor = politicaevaluacionBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado));
                    unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
                }
            }

        } else {
            if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
                System.out.println("RACTConsultasBeanUI:else:cicloEscolarSeleccionadoEqual00:Entro");
                unidadesAprendizaje = politicaevaluacionBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado, loginBean.getSeleccionado(), profesor.getPROid().toString(), getCicloEscolarSeleccionado());
            } else {
                System.out.println("SALINAS ENTRO AL ELSE");
                List<Profesor> profesoressinfiltro = new ArrayList<Profesor>();
                profesores = new ArrayList<Profesor>();
                profesoressinfiltro = politicaevaluacionBeanHelper.getProfesoresPorRPEYCE(profesor.getPROid(), cicloEscolarSeleccionado);
                for (Profesor pro : profesoressinfiltro) {
                    System.out.println(pro);
                    if (!profesores.contains(pro)) {
                        profesores.add(pro);
                    }
                }
                System.out.println(programaEducativoSeleccionado + " " + cicloEscolarSeleccionado + " " + profesorSeleccionado);
                unidadesProfesor = politicaevaluacionBeanHelper.getUNIdadesaprendisajeConGrupos(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
                unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
            }
        }

        if (unidadesAprendizaje != null) {
            for (int x = 0; x < unidadesAprendizaje.size(); x++) {
                System.out.println("Unidades de aprendizaje: " + unidadesAprendizaje.get(x).getUAPnombre());
                System.out.println("Unidad id:" + unidadesAprendizaje.get(x).getUAPid());
            }
        }
        //setEstadoUnidadDeAprendizaje(false);
        disable = false;
        //fillTree();
        //enviado = true;
        setEnable(false);
    }

    public void onSelectPROFESOR() {
        //reinit stuff
        // refresh();
        if (profesorSeleccionado == 0) {
            unidadAprendizajeSeleccionada = "00";
            IdUaipSeleccionada = 00;
            politicaSeleccionada = null;
            politicaSeleccionada = new Politicaevaluacion();
            disableimp = true;
            fechaCreacion = null;
            fechaAlumno = null;
            fechaResponsable = null;
            disableimp = true;
            actualizaPolitica();
        }
        if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")) {
            unidadesProfesor = politicaevaluacionBeanHelper.uaipAreaProfesor(loginBean.getLogueado().getProfesorList().get(0).getPROid(), Integer.parseInt(cicloEscolarSeleccionado), profesorSeleccionado);
            unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
        } else if (loginBean.getSeleccionado().equals("Administrador")) {
            unidadesAprendizaje = Collections.EMPTY_LIST;
            unidadesProfesor = politicaevaluacionBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado), profesorSeleccionado);
            unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);

        } else {
//            unidadesProfesor = ractConsultasBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), profesor.getProgramaeducativoList().get(0).getPEDid(), profesorSeleccionado);
            List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
            for (ResponsableProgramaEducativo rpe : loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList()) {
                if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo())) {
                    peProgramaeducativoList.add(rpe.getProgramaeducativo());
                }
            }
            unidadesProfesor.clear();
            /*for (Programaeducativo peProgramaeducativo : peProgramaeducativoList) {
                unidadesProfesor.addAll(politicaevaluacionBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), peProgramaeducativo.getPEDid(), profesorSeleccionado));
                unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
            }*/
            System.out.println(profesorSeleccionado + " ID DEL PROFESOR " + programaEducativoSeleccionado + " Programa educativo " + cicloEscolarSeleccionado + " Ciclo escolar");
            unidadesProfesor = politicaevaluacionBeanHelper.getUNIdadesaprendisajeConGrupos(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
            unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
        }
    }

   public void onSelectUA() {
        // disableBotones = false;
        String mensaje;
        if (IdUaipSeleccionada == 0) {
            politicaSeleccionada = new Politicaevaluacion();
            disableimp = true;
            politicaSeleccionada = null;
            fechaCreacion = null;
            fechaAlumno = null;
            fechaResponsable = null;
            actualizaPolitica();
            // disableBotones = true;
        } else {
            try {
                if(loginBean.getLogueado().getRolList().get(0).getROLid() == 8){
                    
                }else{
                
                for (UnidadaprendizajeImparteProfesor uni : unidadesProfesor){
                    if(uni.getUIPid() == IdUaipSeleccionada){
                        profesorSeleccionado = uni.getProfesorPROid().getPROid();
                        break;
                    }
                }
                }
                politicaSeleccionada = politicaevaluacionBeanHelper.getPoliticaPorUIP(IdUaipSeleccionada).get(0);
                politica = politicaSeleccionada;
                gpo = String.valueOf(politica.getUIPId().getGrupoGPOid().getGPOnumero());
                String ng = String.valueOf(gpo.charAt(5)) + String.valueOf(gpo.charAt(6)) + String.valueOf(gpo.charAt(7));
                gponum = Integer.parseInt(ng);
                displayFirmas = "";
                displaybotonPDF = "block";
                disableimp = false;
                fileNamePdf = "Politicas de evaluacion" + "--" + politica.getUIPId().getUnidadAprendizajeUAPid().getUAPnombre() + "--" + gponum;
                if (politicaSeleccionada.getPoliticaevaluacioncomentarioList().size() > 0) {
                    displayComentario = "block";

                } else {
                    displayComentario = "none";
                }
            } catch (Exception e) {
                politicaSeleccionada = new Politicaevaluacion();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta política no ha sido creada."));
                fechaCreacion = "DD/MM/AAAA";
                fechaAlumno = "DD/MM/AAAA";
                fechaResponsable = "DD/MM/AAAA";
                actualizaPolitica();
                displayComentario = "none";
                displayFirmas = "none";
                displaybotonPDF = "none";
                return;
            }
            try {
                fechaCreacion = formatter.format(politicaSeleccionada.getFechaCreacion());
            } catch (Exception ex) {
                fechaCreacion = "DD/MM/AAAA";
            }
            try {
                fechaAlumno = formatter.format(politicaSeleccionada.getFechaAceptacionAlumno());
            } catch (Exception ex) {
                fechaAlumno = "DD/MM/AAAA";
            }
            try {
                fechaResponsable = formatter.format(politicaSeleccionada.getFechaAceptacionResponsable());
            } catch (Exception ex) {
                fechaResponsable = "DD/MM/AAAA";
            }

            switch (politicaSeleccionada.getEstadoPolEva()) {
                case 0:
                    mensaje = "Política guardada sin enviar";
                    break;
                case 1:
                    mensaje = "Política enviada a alumno para aprobación";
                    break;
                case 2:
                    mensaje = "Política aceptada por alumno en espera de ser aceptada por RPE";
                    break;
                case 3:
                    mensaje = "Política rechazada por alumno";
                    break;
                case 4:
                    mensaje = "Política completamente aceptada";
                    break;
                case 5:
                    mensaje = "Política rechazado por el RPE";
                    break;
                default:
                    mensaje = "";
                    break;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje));
            actualizaPolitica();
        }

    }

    public void actualizaPolitica() {
        //comentarioPolitica = new Politicaevaluacioncomentario();
        PrimeFaces.current().ajax().update("formId:representante");
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentarPO");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:gs");
        PrimeFaces.current().ajax().update("formId:firmas");
        PrimeFaces.current().ajax().update("formId:tablaComentarios");
        PrimeFaces.current().ajax().update("formId:imprimir");
        PrimeFaces.current().ajax().update("formId:nomprof");
        PrimeFaces.current().ajax().update("formId:UnidadesAprendizaje");
    }

    public boolean esProfesor() {
        String rol = loginBean.getSeleccionado();
        return rol.equalsIgnoreCase("Profesor");
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
    }

    public void postProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
        String nombrep = politica.getUIPId().getProfesorPROid().getPROnombre() + " " + politica.getUIPId().getProfesorPROid().getPROapellidoPaterno() + " " + politica.getUIPId().getProfesorPROid().getPROapellidoMaterno();
        String numEmpPro = Integer.toString(politica.getUIPId().getProfesorPROid().getPROnumeroEmpleado());

        //Obtener el nombre del programa educativo seleccionado
        String nombreProgEdu = "";
        int programaEducativoSeleccionado2 = Integer.parseInt(programaEducativoSeleccionado);

        /*for (int x = 0; x < programasEducativos.size(); x++) {
            if (programasEducativos.get(x).getPEDid() == programaEducativoSeleccionado2) {
                nombreProgEdu = programasEducativos.get(x).getPEDnombre();
            }
        }*/
        //----------------------------------------------------------------------------------
        //Obtener el nombre de la unidad de aprendizaje y clave seleccionada
        String nombreUniApr = "";
        String nombreclave = "";
        unidadImparte = politica.getUIPId();
        int uniAprselec2 = unidadImparte.getUnidadAprendizajeUAPid().getUAPid();

        nombreUniApr = unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre();
        nombreclave = Integer.toString(unidadImparte.getUnidadAprendizajeUAPid().getUAPclave());

        try {
//           //Imagen logo de uabc parte superior izquierda
            Image imagenLogo = Image.getInstance(RACTBeanUI.class.getResource("/logo.png"));
//
            //Posicion de imagen (Horizontal, Vertiacal)
            imagenLogo.setAbsolutePosition(45f, 495f); //10,460

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
        Paragraph textoPoliticas = new Paragraph("Políticas Para la evaluación permanente ordinaria que prevé el articulo 68 del Estatuto Escolar ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0)));
        textoPoliticas.setAlignment(Element.ALIGN_CENTER);
        pdf.add(UABC);
        pdf.add(Facultad);
        pdf.add(textoPoliticas);
        pdf.add(new Phrase("\n"));

        pdfTabletitulo3.addCell(new Paragraph("Fecha registro: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        // pdfTabletitulo3.addCell(new Paragraph(politica.getFechaCreacion().toString(), FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));

        pdfTabletitulo.addCell(new Paragraph("Programa educativo: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo.addCell(new Paragraph(politica.getUIPId().getGrupoGPOid().getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDnombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));

        pdfTabletitulo2.addCell(new Paragraph("Nombre del maestro: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD)));
        pdfTabletitulo2.addCell(new Paragraph(nombrep, FontFactory.getFont(FontFactory.TIMES, 12)));

        pdfTabletitulo2.addCell(new Paragraph("No. de empleado: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo2.addCell(new Paragraph("" + politica.getUIPId().getProfesorPROid().getPROnumeroEmpleado(), FontFactory.getFont(FontFactory.TIMES, 12)));

        pdfTabletitulo3.setHorizontalAlignment(25);
        float[] columnWidthsss3 = new float[]{15f, 50};//4,28
        pdfTabletitulo3.setWidths(columnWidthsss3);
        pdf.add(pdfTabletitulo3);

        pdfTabletitulo.setHorizontalAlignment(25);

        float[] columnWidthsss = new float[]{12f, 50};//4,28
        pdfTabletitulo.setWidths(columnWidthsss);
        pdf.add(pdfTabletitulo);

        pdfTabletitulo2.setHorizontalAlignment(25);
        float[] columnWidthss = new float[]{2.5f, 7, 2f, 1}; //10,38
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
        switch (unidadImparte.getUIPtipoSubgrupo()) {
            case "C":
                tipoGrupo = "Clase";
                break;
            case "L":
                tipoGrupo = "Laboratorio";
                break;
            case "T":
                tipoGrupo = "Taller";
                break;
            case "P":
                tipoGrupo = "Practica";
                break;
            case "CL":
                tipoGrupo = "Practicas clínica";
                break;
        }
//        pdftablecabezera2.addCell(new Paragraph("    " + tipoGrupo));
        pdftablecabezera2.addCell(new Paragraph(" "));

        pdftablecabezera.setHorizontalAlignment(25);
        float[] columnWidths = new float[]{8f, 30};
        pdftablecabezera.setWidths(columnWidths);
        pdf.add(pdftablecabezera);

        pdftablecabezera2.setHorizontalAlignment(25);
        float[] columnWidthss2 = new float[]{.6f, 2, .6f, 5};
        pdftablecabezera2.setWidths(columnWidthss2);
        pdf.add(pdftablecabezera2);

        pdf.add(new Phrase(" "));

        Paragraph textoAspectos = new Paragraph("Aspectos que integran la calificación del alumno : ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD));
        textoAspectos.setAlignment(Element.ALIGN_CENTER);
        pdf.add(textoAspectos);

        Paragraph textoEstosAspectos = new Paragraph("Estos aspectos deberán ser acordes a lo establecido estatuto escolar y", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD));
        textoEstosAspectos.setAlignment(Element.ALIGN_CENTER);
        pdf.add(textoEstosAspectos);

        Paragraph textoSubra = new Paragraph(" Considerando las cartas descriptivas de la unidad de aprendizaje:", FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE));
        textoSubra.setAlignment(Element.ALIGN_CENTER);
        pdf.add(textoSubra);

        pdf.add(new Phrase(" "));
        //----------------------------------------------------------------------
        PdfPTable pdftablecabezera3 = new PdfPTable(2);
        pdftablecabezera3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftablecabezera3.addCell(new Paragraph("Criterios ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera3.addCell(new Paragraph("Porcentaje ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        for (int x = 0; x < politica.getPoliticaTieneCriterioList().size(); x++) {
            if (politica.getPoliticaTieneCriterioList().get(x).getCEVId().getCEVNombre().equalsIgnoreCase("examenes parciales")) {

                pdftablecabezera3.addCell(new Paragraph(politica.getPoliticaTieneCriterioList().get(x).getCEVId().getCEVNombre() + "(" + politica.getPoliticaTieneCriterioList().get(x).getNExamen() + ")", FontFactory.getFont(FontFactory.TIMES, 12)));

            } else {
                pdftablecabezera3.addCell(new Paragraph(politica.getPoliticaTieneCriterioList().get(x).getCEVId().getCEVNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
            }
            pdftablecabezera3.addCell(new Paragraph(politica.getPoliticaTieneCriterioList().get(x).getPorcentaje() + "%", FontFactory.getFont(FontFactory.TIMES, 12)));
        }

        pdftablecabezera3.addCell(new Paragraph(""));
        pdftablecabezera3.addCell(new Paragraph("Total:  100 %", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD)));
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
        pdftableCriEx.addCell(new Paragraph(politica.getPOECriterioExentar(), FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));

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
        pdftablecabezera9.addCell(new Paragraph(politica.getPOEComentario(), FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));

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
        float[] columnWidths6 = new float[]{2.7f, 10};
        pdftablecabezera6.setWidths(columnWidths6);
        pdf.add(pdftablecabezera6);
        pdf.add(new Phrase("\n"));

        PdfPTable pdftableFechaAl = new PdfPTable(2);
        pdftableFechaAl.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableFechaAl.addCell(new Paragraph("Fecha de aceptación representante de grupo: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        if(politica.getFechaAceptacionAlumno() == null){
            pdftableFechaAl.addCell(new Paragraph("", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        }else{
            pdftableFechaAl.addCell(new Paragraph(politica.getFechaAceptacionAlumno().toString(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        }
    

        pdftableFechaAl.setHorizontalAlignment(25);
        float[] columnWidthsAl = new float[]{2.7f, 1};
        pdftableFechaAl.setWidths(columnWidthsAl);
        pdf.add(pdftableFechaAl);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableRPE = new PdfPTable(2);
        pdftableRPE.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableRPE.addCell(new Paragraph("RPE: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        
        pdftableRPE.addCell(new Paragraph(politica.getResponsablePROId().getPROnombre() + " " + politica.getResponsablePROId().getPROapellidoPaterno() + " " + politica.getResponsablePROId().getPROapellidoMaterno(), FontFactory.getFont(FontFactory.TIMES, 12)));

        pdftableRPE.setHorizontalAlignment(25);
        float[] columnWidthsRPE2 = new float[]{.6f, 10};
        pdftableRPE.setWidths(columnWidthsRPE2);
        pdf.add(pdftableRPE);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableFechaRPE = new PdfPTable(2);
        pdftableFechaRPE.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableFechaRPE.addCell(new Paragraph("Fecha de aceptación RPE: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        if(politica.getFechaAceptacionResponsable() == null){
            pdftableFechaRPE.addCell(new Paragraph("", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        }else{
             pdftableFechaRPE.addCell(new Paragraph(politica.getFechaAceptacionResponsable().toString(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));
        }
       

        pdftableFechaRPE.setHorizontalAlignment(25);
        float[] columnWidthsRPE = new float[]{2.7f, 1};
        pdftableFechaRPE.setWidths(columnWidthsRPE);
        pdf.add(pdftableFechaRPE);

        pdf.add(new Phrase("\n"));

        PdfPTable pdftableFechaCre = new PdfPTable(2);
        pdftableFechaCre.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftableFechaCre.addCell(new Paragraph("Fecha de Creación: ", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, new Color(0, 0, 0))));

        if (politica.getFechaCreacion() == null) {
            pdftableFechaCre.addCell(new Paragraph(""));
        } else {
            pdftableFechaCre.addCell(new Paragraph(politica.getFechaCreacion().toString(), FontFactory.getFont(FontFactory.TIMES, 12, new Color(0, 0, 0))));
        }
        pdftableFechaCre.setHorizontalAlignment(25);
        float[] columnWidthsCre = new float[]{2.1f, 10};
        pdftableFechaCre.setWidths(columnWidthsCre);
        pdf.add(pdftableFechaCre);
    }

    public List<UnidadaprendizajeImparteProfesor> filtrarUnidadAprendisajePorProfesor(List<UnidadaprendizajeImparteProfesor> listaSinFiltrar) {
        List<UnidadaprendizajeImparteProfesor> listaFiltrada = new ArrayList();
        for (UnidadaprendizajeImparteProfesor up : listaSinFiltrar) {
            boolean claseylab = politicaevaluacionBeanHelper.alumnosengrupo(up.getCicloEscolarCESid().getCESid(),up.getUnidadAprendizajeUAPid().getUAPid(),up.getGrupoGPOid().getGPOid());
            boolean existe = true;
            UnidadaprendizajeImparteProfesor cambio = null;
            if (listaFiltrada != null) {
                for (UnidadaprendizajeImparteProfesor filtro : listaFiltrada) {
                    if (claseylab && up.getUnidadAprendizajeUAPid().getUAPid() == filtro.getUnidadAprendizajeUAPid().getUAPid() && up.getProfesorPROid() == filtro.getProfesorPROid() && up.getGrupoGPOid() == filtro.getGrupoGPOid()) {
                        existe = false;
                        if (up.getUIPtipoSubgrupo().equals("C")) {
                            cambio = filtro;
                            existe = true;
                            break;
                        }
                    }
                }
            } else {
                listaFiltrada.add(up);
            }
            if (existe && cambio != null) {
                listaFiltrada.add(up);
                listaFiltrada.remove(cambio);
            } else if (existe) {
                listaFiltrada.add(up);
            }
        }
        return listaFiltrada;
    }

    // ------- GETTER and SETTERS ----------//
    public String getCicloEscolarSeleccionado() {
        return cicloEscolarSeleccionado;
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    public void setCicloEscolarSeleccionado(String cicloEscolarSeleccionado) {
        this.cicloEscolarSeleccionado = cicloEscolarSeleccionado;
    }

    public void setEnable(boolean enable) {
        if (programaEducativoSeleccionado.equalsIgnoreCase("00")) {
            enable = true;
            setEnable2(true);
        } else {
            enable = false;
            setEnable2(true);
        }
        setDisable(enable);
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public void setEnable2(boolean enable2) {
        if (unidadAprendizajeSeleccionada.equalsIgnoreCase("00")) {
            enable2 = true;
        } else {
            enable2 = false;
        }
        disable3 = false;
//        disable2=enviado;
    }

    public List<Cicloescolar> getListaCiclosEscolares() {
        List<Cicloescolar> filtro = new ArrayList<Cicloescolar>();
        listaCiclosEscolares = null;
        listaCiclosEscolares = politicaevaluacionBeanHelper.getAllCiclos();

        for (Cicloescolar ciclo : listaCiclosEscolares) {
            if (ciclo.getCESid() >= 24) {
                filtro.add(ciclo);
            }
        }

        listaCiclosEscolares.clear();
        listaCiclosEscolares = filtro;
        //orden de mayor a menor 
        Collections.sort(listaCiclosEscolares, new Comparator<Cicloescolar>() {
            @Override
            public int compare(Cicloescolar o1, Cicloescolar o2) {
                if (o1.getCESid() > o2.getCESid()) {
                    return -1;
                } else if (o1.getCESid() < o2.getCESid()) {
                    return 1;
                }
                return 0;
            }
        });
        return listaCiclosEscolares;
    }

    public Integer getProfesorSeleccionado() {
        return profesorSeleccionado;
    }

    public void setProfesorSeleccionado(Integer profesorSeleccionado) {
        this.profesorSeleccionado = profesorSeleccionado;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public String getUnidadAprendizajeSeleccionada() {
        return unidadAprendizajeSeleccionada;
    }

    public void setUnidadAprendizajeSeleccionada(String unidadAprendizajeSeleccionada) {
        this.unidadAprendizajeSeleccionada = unidadAprendizajeSeleccionada;
    }

    public String getProgramaEducativoSeleccionado() {
        return programaEducativoSeleccionado;
    }

    public void setProgramaEducativoSeleccionado(String programaEducativoSeleccionado) {
        this.programaEducativoSeleccionado = programaEducativoSeleccionado;
    }

    public List<Programaeducativo> getProgramasEducativos() {

        programasEducativos = politicaevaluacionBeanHelper.getProgramaesEducativos(profesor);

        return programasEducativos;
    }

    public List<Programaeducativo> getProgramasEducativosConsultas() {
        return programasEducativosConsultas;
    }

    public void setProgramasEducativosConsultas(List<Programaeducativo> programasEducativosConsultas) {
        this.programasEducativosConsultas = programasEducativosConsultas;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDisable3() {
        return disable3;
    }

    public void setDisable3(Boolean disable3) {
        this.disable3 = disable3;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getNombreCompletoProfesor() {
        return nombreCompletoProfesor;
    }

    public void setNombreCompletoProfesor(String nombreCompletoProfesor) {
        this.nombreCompletoProfesor = nombreCompletoProfesor;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public int getIdUaipSeleccionada() {
        return IdUaipSeleccionada;
    }

    public void setIdUaipSeleccionada(int IdUaipSeleccionada) {
        this.IdUaipSeleccionada = IdUaipSeleccionada;
    }

    public List<Unidadaprendizaje> getUnidadesAprendizaje() {
        return unidadesAprendizaje;
    }

    public List<UnidadaprendizajeImparteProfesor> getUnidadesProfesor() {
        return unidadesProfesor;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Politicaevaluacion getPoliticaSeleccionada() {
        return politicaSeleccionada;
    }

    public void setPoliticaSeleccionada(Politicaevaluacion politicaSeleccionada) {
        this.politicaSeleccionada = politicaSeleccionada;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaAlumno() {
        return fechaAlumno;
    }

    public void setFechaAlumno(String fechaAlumno) {
        this.fechaAlumno = fechaAlumno;
    }

    public String getFechaResponsable() {
        return fechaResponsable;
    }

    public void setFechaResponsable(String fechaResponsable) {
        this.fechaResponsable = fechaResponsable;
    }

    public int getIDCATALOGOCONSULTAPOLITICA() {
        return IDCATALOGOCONSULTAPOLITICA;
    }

    public void setIDCATALOGOCONSULTAPOLITICA(int IDCATALOGOCONSULTAPOLITICA) {
        this.IDCATALOGOCONSULTAPOLITICA = IDCATALOGOCONSULTAPOLITICA;
    }

    public String getDisplayComentario() {
        return displayComentario;
    }

    public void setDisplayComentario(String displayComentario) {
        this.displayComentario = displayComentario;
    }

    public String getDisplayFirmas() {
        return displayFirmas;
    }

    public void setDisplayFirmas(String displayFirmas) {
        this.displayFirmas = displayFirmas;
    }

    public String getDisplaybotonPDF() {
        return displaybotonPDF;
    }

    public void setDisplaybotonPDF(String displaybotonPDF) {
        this.displaybotonPDF = displaybotonPDF;
    }

    public List<Politicaevaluacion> getPoliticasaceptadasRPE() {
        return politicasaceptadasRPE;
    }

    public void setPoliticasaceptadasRPE(List<Politicaevaluacion> politicasaceptadasRPE) {
        this.politicasaceptadasRPE = politicasaceptadasRPE;
    }

    public boolean esAlumno() {
        return loginBean.getSeleccionado().equalsIgnoreCase("Alumno");
    }

    public boolean esAlumno2() {
        System.out.println(loginBean.getSeleccionado());
        if(loginBean.getSeleccionado() == null){
            return false;
        }else
        {
        return !loginBean.getSeleccionado().equalsIgnoreCase("Alumno");
        }
    }

    public String rolLogin() {
        String rol = "";
        if (loginBean.getSeleccionado().equalsIgnoreCase("Profesor") || loginBean.getSeleccionado().equalsIgnoreCase("Alumno")) {
            rol = "Nombre del " + loginBean.getSeleccionado() + ":";
        } else {
            rol = "Nombre del Profesor:";
        }
        return rol;
    }

    public String PEAlumno() {
        String pe;
        pe = "";
        return pe;
    }

    public boolean esProfesoroAlumno() {
        String rol = loginBean.getSeleccionado();
        return rol.equalsIgnoreCase("Profesor") || rol.equalsIgnoreCase("Alumno");
    }

    public String getFileNamePdf() {
        return fileNamePdf;
    }

    public void setFileNamePdf(String fileNamePdf) {
        this.fileNamePdf = fileNamePdf;

    }

    public Boolean getDisableimp() {
        return disableimp;
    }

    public void setDisableimp(Boolean disableimp) {
        this.disableimp = disableimp;
    }

}
