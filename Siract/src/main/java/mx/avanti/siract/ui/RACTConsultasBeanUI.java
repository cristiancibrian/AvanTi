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
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.NodoMultiClass;
import mx.avanti.siract.helper.RACTConsultasBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * @author Mosh
 */
@ManagedBean
@ViewScoped
public class RACTConsultasBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    /**
     * Creates a new instance of RACTConsultasBeanUI
     */
    //Declaracion de variables para el manejo de la UI de RactConsultas

    private final int IDCATALOGORACTCONSULTA = 13;

    public int getIDCATALOGORACTCONSULTA() {
        return IDCATALOGORACTCONSULTA;
    }

    RACTConsultasBeanHelper ractConsultasBeanHelper = new RACTConsultasBeanHelper();
    List<Unidad> unidades;
    List<Temaunidad> temas;
    List<Unidadaprendizaje> unidadesaprendisaje;
    List<Profesor> profesores;
    List<Grupo> grupos = new ArrayList();
    Profesor profesor;
    String claveUnidadAprendizajeSeleccionada = "0";
    List<String> as = new ArrayList();
    List<Programaeducativo> programasEducativos;
    String clave = "000000";
    String nombreProfesor = "@Profesor";
    String fechaActual = "";
    String grupo = "00";
    String nombreUnidadAprendisaje;
    List<Areaconocimiento> areasConocimiento;
    String programaEducativoSeleccionado = "0";
    TreeNode root;
    private TreeNode[] selectedNodes;
    boolean pintar = true;
    float porcentajeAvance;
    String numeroReporte = "0";
    String tipoUnidadAprendizaje = "N";
    String subGrupo = "00";
    Boolean botones = false;
    String unidadAprendizajeSeleccionada = "00";
    Boolean disable = true;
    Boolean disable2 = true;
    String nombreUnidadSeleccionado;
    String arbolEmptyMessage = EMPTY_NO_DATA_GIVEN;
    static final String EMPTY_NO_RESULTS = "<span style='color:red'>No se encontraron resultados </span>";
    static final String EMPTY_NO_DATA_GIVEN = "Seleccione los conceptos para realizar su reporte de avance contenido temático.";
    //Variable para saber si estoy en Ract.xhtml(1) o RACTConsultas(2)
    private int archivoActual = 0;
    String tipo = "";
    String[] num;
    //Variable temporal borrra despues de uso para pruebas. 2 de sep de 2016
    boolean esCoordinador;
    private Boolean disable3 = false;
    Boolean deshabilitar = true;
    String nombreCompletoProfesor = " ";
    Profesor profesorPorUA = null;
    Integer profesorSeleccionado;
    String nombreProfesorPorUA = " ";
    List<Programaeducativo> programasEducativosConsultas;
    List<Unidadaprendizaje> unidadesAprendizajeConsultas = null;
    Areaadministrativa areaadministrativaCoordina = null;
    int idUnidadAprendizajeSeleccionada;
    private boolean estadoCicloEscolar = true; //Esta variable manipula la visibilidad del componente ciclo escolar
    private boolean estadoUnidadDeAprendizaje = true; //Esta variable manipula la visibilidad del componente Unidad de aprendizaje
    private boolean estadoNumeroDeReporte = true; //Esta variable manipula la visibilidad del componente Numero de Reporte
    private List<Reporte> listaReportes = null;
    //Variable con el nombre del reporte
    String nombreReporte = "";
    String cicloEscolar;

    private List<Cicloescolar> listaCiclosEscolares;

    List<UnidadaprendizajeImparteProfesor> unidadesProfesor;
    int IdUaipSeleccionada = 0;
    String reporteActual = "Reporte #" + numeroReporte;
    private String reporteSeleccionado = "00";

    //Lista utilizada para llenar datatable y generarPDF
    List<NodoMultiClass> filaTabla;

    //Supongo que es para seleccionar el ciclo escolar
    private String cicloEscolarSeleccionado = "00";
    String cicloEscolarActual = ractConsultasBeanHelper.cicloescolarActual().getCEScicloEscolar();
    String cicloEscolarClave = ractConsultasBeanHelper.cicloescolarActual().getCESid().toString();

    //setter para manipular el estado del componente ciclo escolar
    public void setEstadoCicloEscolar(boolean estado) {
        this.estadoCicloEscolar = estado;
    }

    //getter para manipular el estado del componente ciclo escolar
    public boolean getEstadoCicloEscolar() {
        return this.estadoCicloEscolar;
    }

    //setter para manipular el estado del componente ciclo escolar
    public void setEstadoNumeroDeReporte(boolean estado) {
        this.estadoNumeroDeReporte = estado;
    }

    //getter para manipular el estado del componente ciclo escolar
    public boolean getEstadoNumeroDeReporte() {
        return this.estadoCicloEscolar;
    }

    //setter para manipular el estado del componente Unidad de aprendizaje
    public void setEstadoUnidadDeAprendizaje(boolean estado) {
        this.estadoUnidadDeAprendizaje = estado;
    }

    //getter para manipular el estado del componente Unidad de aprendizaje
    public boolean getEstadoUnidadDeAprendizaj() {
        return this.estadoUnidadDeAprendizaje;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public List<UnidadaprendizajeImparteProfesor> getUnidadesProfesor() {
        return unidadesProfesor;
    }

    public void setUnidadesProfesor(List<UnidadaprendizajeImparteProfesor> unidadesProfesor) {
        this.unidadesProfesor = unidadesProfesor;
    }

    public int getIdUaipSeleccionada() {
        return IdUaipSeleccionada;
    }

    public void setIdUaipSeleccionada(int IdUaipSeleccionada) {
        this.IdUaipSeleccionada = IdUaipSeleccionada;
    }

    public String getCicloEscolarSeleccionado() {
        return cicloEscolarSeleccionado;
    }

    public void setCicloEscolarSeleccionado(String cicloEscolarSeleccionado) {
        System.out.println("RACTConsultasBeanUI:setCicloEscolarSeleccionado:185:Ciclo escolar seleccionado:" + cicloEscolarSeleccionado);
        this.cicloEscolarSeleccionado = cicloEscolarSeleccionado;
    }

    public Boolean getDisable3() {
        return disable3;
    }
    

    public String getCicloEscolarActual() {
        return cicloEscolarActual;
    }

    public List<Cicloescolar> getListaCiclosEscolares() {
        listaCiclosEscolares = null;
        listaCiclosEscolares = ractConsultasBeanHelper.getAllCiclos();


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

    public void setDeshabilitar(boolean deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public boolean getDeshabilitar() {
        return deshabilitar;
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

    public List<Unidadaprendizaje> getUnidadesaprendisaje() {

        return unidadesaprendisaje;
    }

    public void setUnidadesaprendisaje(List<Unidadaprendizaje> unidadesaprendisaje) {
        this.unidadesaprendisaje = unidadesaprendisaje;
    }

    public void setUnidadAprendizajeSeleccionada(String seleccionado) {
        if (!unidadAprendizajeSeleccionada.equals(seleccionado) && !seleccionado.equals("00")) {
            unidadAprendizajeSeleccionada = seleccionado;
            System.out.println("OK " + seleccionado);
            idUnidadAprendizajeSeleccionada = Integer.parseInt(unidadAprendizajeSeleccionada);
            System.out.println("ID" + idUnidadAprendizajeSeleccionada);
            for (Unidadaprendizaje u : unidadesaprendisaje) {
                int idUnidad = u.getUAPid();
                System.out.println("ID UNIDADA " + idUnidad);
                if (u.getUAPid() == idUnidadAprendizajeSeleccionada) {
                    if (seleccionado != null && !seleccionado.isEmpty() && !seleccionado.equals("00")) {
                        String[] valores = u.getUAPnombre().split(" -- ");
                        this.claveUnidadAprendizajeSeleccionada = String.valueOf(u.getUAPclave());
                        tipoUnidadAprendizaje = valores[3];
                        this.grupo = valores[1];
                        subGrupo = valores[2];
                        nombreUnidadSeleccionado = valores[0];
                    }

                }
            }
            pintar = true;
            auxSelectedNodes = false;
            System.out.println("@@@QUITANDO BLOQUEO DE SELECTEDNODES");
        } else {
            pintar = false;
            if (selectedNodes != null) {
//                for (TreeNode nodosSeleccionado : selectedNodesInitial) {
//                    nodosSeleccionado.setSelected(true);
//                }
            }
            System.out.println("@@@SE CONSERVA BLOQUEO DE NODOS SELECCIONADOS");
        }
//        
        if (seleccionado.equals("00")) {
            root = new DefaultTreeNode();
            selectedNodes = null;
            claveUnidadAprendizajeSeleccionada = "0";
            this.unidadAprendizajeSeleccionada = seleccionado;
        }
        setEnable2(true);
    }

    public void setProgramaEducativoSeleccionado(String programaEducativoSeleccionado) {

        if (!programaEducativoSeleccionado.equals(this.programaEducativoSeleccionado)) {
            setUnidadAprendizajeSeleccionada("00");
        }
        this.programaEducativoSeleccionado = programaEducativoSeleccionado;
        if (programaEducativoSeleccionado.equals("00")) {
            System.out.println("PROGRAMA EDUCATIV0 SELECCIONADO ");
            claveUnidadAprendizajeSeleccionada = "0";
            unidadAprendizajeSeleccionada = "0";
            porcentajeAvance = 0;

        }

    }

    public String getProgramaEducativoSeleccionado() {
        return programaEducativoSeleccionado;
    }

    public Integer getProfesorSeleccionado() {
        return profesorSeleccionado;
    }

    public void setProfesorSeleccionado(Integer profesorSeleccionado) {
        this.profesorSeleccionado = profesorSeleccionado;
    }

    //Constructor de la clase
    public RACTConsultasBeanUI() {
        root = new DefaultTreeNode();
        programasEducativos = new ArrayList();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = sdf.format(date);

        //Obtener CicloEscolarActual
        Cicloescolar auxCicloEscolar;
        auxCicloEscolar = ractConsultasBeanHelper.cicloescolarActual();
        cicloEscolar = "N/A";
        if (auxCicloEscolar == null) {
            System.out.println("CICLO ESCOLAR NULO");
        } else {
            cicloEscolar = auxCicloEscolar.getCEScicloEscolar();
        }
        //Obteniendo CicloEscolarActual

//        numeroReporte = ractConsultasBeanHelper.obtenerReporteSiguiente();
//        reporteActual = "Reporte #" + numeroReporte;
        System.out.println("CREANDO BEANUI ");
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Programaeducativo> getProgramasEducativos() {

        programasEducativos = ractConsultasBeanHelper.getProgramaesEducativos(loginBean.getSeleccionado());

        return programasEducativos;
    }
    

    public void onSelectCE() {
        //limpiar programas educativos y unidades de aprendizaje
        refresh();
        //limpia el checkbox de unidad de aprendizaje
        if (unidadesaprendisaje != null && unidadesaprendisaje.size() > 0) {
            unidadesaprendisaje = Collections.EMPTY_LIST;
        }

        if (!cicloEscolarSeleccionado.equals("00")) {
            // para llenar los programas eduvativos del profesor.
            Profesor profesor = loginBean.getLogueado().getProfesorList().get(0);

            programasEducativos = ractConsultasBeanHelper.programasEducativosProfesor(profesor, cicloEscolarSeleccionado);

            System.out.println("RACTConsultasBeanUI:onSelectCE:327:Ciclo escolar seleccionado: " + cicloEscolarSeleccionado + " Rol: " + loginBean.getSeleccionado());
            //pone el check box de Programa Educativo en "Seleccione una opcion"
            if (esProfesor() || loginBean.getSeleccionado().equals("Administrador")) {
                programaEducativoSeleccionado = "00";
            } else {
                programaEducativoSeleccionado = String.valueOf(programasEducativos.get(0).getPEDid());
            }
            if (!esProfesor()) {
                if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")) {
                    profesores = ractConsultasBeanHelper.getProfesoresPorCoordinadoryCE(profesor.getPROid(), cicloEscolarSeleccionado);
                    profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                    unidadesProfesor = ractConsultasBeanHelper.uaipPorcoordinadorAreaYCicloEscolar(profesor.getPROid(), Integer.parseInt(cicloEscolarSeleccionado));
                } else if (loginBean.getSeleccionado().equals("Responsable de Programa Educativo")) {
//                    programasEducativos= profesor.getProgramaeducativoList();
                    List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
                    for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList()){
                        System.out.println(peProgramaeducativoList + "dentro del for");
                        if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                            peProgramaeducativoList.add(rpe.getProgramaeducativo());
                    }
                    programasEducativos = peProgramaeducativoList;
                    System.out.println(programasEducativos);
                    programaEducativoSeleccionado = programasEducativos.get(0).getPEDid().toString();
                    profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                    List<Profesor> profesoressinfiltro = new ArrayList<Profesor>();
                    profesoressinfiltro = ractConsultasBeanHelper.getProfesoresPorRPEYCE(profesor.getPROid(), cicloEscolarSeleccionado);
                    System.out.println(profesoressinfiltro.size());
                    for (Profesor pro: profesoressinfiltro){
                        System.out.println(pro);
                            if(!profesores.contains(pro)){
                            profesores.add(pro);
                            }
                    }
                    System.out.println("Imprimir el logeado "+loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList().get(0).getProgramaeducativo().getPEDid());
                    System.out.println(Integer.parseInt(cicloEscolarSeleccionado));
                    unidadesProfesor = ractConsultasBeanHelper.getUNIdadesaprendisajeConGrupos2(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
                    unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
                    //unidadesProfesor = ractConsultasBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList().get(0).getProgramaeducativo().getPEDid());

                    //peProgramaeducativoList.clear();
                    //for (ResponsableProgramaEducativo rpe : loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList())
                    //    if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                    //        peProgramaeducativoList.add(rpe.getProgramaeducativo());
                    //unidadesProfesor.clear();
                    //System.out.println("Aqui muestro las unidades" +unidadesProfesor);
                    //for (Programaeducativo peProgramaeducativo : peProgramaeducativoList)
                    //   unidadesProfesor.addAll(ractConsultasBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), peProgramaeducativo.getPEDid()));

                } else if (loginBean.getSeleccionado().equals("Administrador")) {
                    programasEducativos = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
                }

            }
            setProgramasEducativosConsultas(programasEducativos);
            for (int x = 0; x <= programasEducativos.size() - 1; x++) {
                System.out.println("Programas Educativos de ciclo escolar: " + cicloEscolarSeleccionado + " Son: " + programasEducativos.get(x).getPEDnombre());
            }

            //limpia el arbol de reportes y el porcenteje de avance
            if (root != null && root.getChildCount() > 0) {
                root = new DefaultTreeNode();
                porcentajeAvance = 0f;
            }

            profesorSeleccionado = 0;
            unidadAprendizajeSeleccionada = "00";
            arbolEmptyMessage = EMPTY_NO_DATA_GIVEN;

        } else {
            programaEducativoSeleccionado = "00";
            unidadAprendizajeSeleccionada = "00";
            arbolEmptyMessage = EMPTY_NO_DATA_GIVEN;
            root = new DefaultTreeNode();
            if (!esProfesor()) {
                profesorSeleccionado = 0;
                profesores = Collections.EMPTY_LIST;
            }
        }

    }

    @PostConstruct
    public void postConstructor() {
        profesores = new ArrayList<Profesor>();

        //else {
        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            profesor = loginBean.getLogueado().getProfesorList().get(0);
            if (profesor == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            } else {
                nombreCompletoProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
                nombreProfesor = profesor.getPROnombre();
                ractConsultasBeanHelper.setProfesor(profesor);
            }
        }
        //}

        if (profesor == null) {
            System.out.println("//////////////////////////////////////////////PROFESOR ES NULL");
        }
        //System.out.println(profesor.getPronombre()+" "+profesor.getProapellidoPaterno()+" "+profesor.getProapellidoMaterno()+" /////////////////////////////////////////nombre");

        //(ReporteId id, RonSeeporteavancecontenidotematico reporteavancecontenidotematico, String repobservacion)
//        listaReportes=new ArrayList<>();
//        try {
//            listaReportes.add(new Reporte(null,null,"Reporte #1",null));
//            listaReportes.add(new Reporte(null,null,"Reporte #2",null));
//            listaReportes.add(new Reporte(null,null,"Reporte #3",null));
//        } catch (Exception e) {
//            System.out.println(e+" ////////////////////////////////////EXCEPCION");
//        }
        if (reporteActual.equalsIgnoreCase("Reporte #0")) {
            reporteActual = "Reporte #1";
        }

        /*
        if(reporteActual.equalsIgnoreCase("Reporte #0")){
            reporteActual = "Reporte #1";
            for(int y=0;y<listaReportes.size();y++){
                if(listaReportes.get(y).getRepobservacion().equalsIgnoreCase(reporteSeleccionado)){
                    listaReportes.remove(y);
                    //listaReportes.add(new Reporte(null,null,reporteSeleccionado,null));
                }
            }
        }else{    */
//        for(int x=0;x<listaReportes.size();x++){
//            if(listaReportes.get(x).getRepobservacion().equalsIgnoreCase(reporteActual)){
//                listaReportes.remove(x);
//            }
//        }
        //}
    }

    //ManagedBean idk
    boolean auxSelectedNodes = false;

    String observacion;

    /*Metodo que se utilizaba en RactBeanUI para seleccionar si trabajar
	  con ract o ractConsultas. Se Modificara al finalizar el refactory de 
	  RactConsultas */
    public void refrescarForma(int num) {
        archivoActual = num;
        List<Rol> list = null;
        Profesor profe = loginBean.getLogueado().getProfesorList().get(0);
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        String catalogo = "Actualizar porcentaje de contenido temático";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        System.out.println("ejecucion del metodo:");
        //verificarMaestroCoordinador();
        deshabilitaPE();
        if (loginBean.getSeleccionado().equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
            //  programaEducativoSeleccionado = ractConsultasBeanHelper.getProgramaesEducativosPorRol("Coordinador de Área de Conocimiento", cicloEscolarClave).get(0).getPEDid().toString();
            programaEducativoSeleccionado = profesor.getCoordinadorareaadministrativaList().
                    get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid().toString();
            disable = false;

            if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
                //System.out.println("parametros:"+programaEducativoSeleccionado+" "+profesor.getProid().toString()+" "+cicloEscolarClave+"00");
//                unidadesaprendisaje = ractConsultasBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado, "Coordinador de Área de Conocimiento", profesor.getPROid().toString(), cicloEscolarSeleccionado);
            } else {
                //System.out.println("parametros:"+programaEducativoSeleccionado+" "+profesor.getProid().toString()+" "+cicloEscolarSeleccionado);
                //  unidadesaprendisaje = ractConsultasBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado, "Coordinador de Área de Conocimiento", profesor.getPROid().toString(), cicloEscolarSeleccionado);
            }
        }
        System.out.println(loginBean.getSeleccionado() + "//////////////////////////////////////////////////////////ENTRO A ESTE EN REFRESCAR FORMA(ROL)");

    }

    public void deshabilitaPE() {
        if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")
                || loginBean.getSeleccionado().equals("Responsable de Programa Educativo")) {

            setDeshabilitar(true);
        } else {
            setDeshabilitar(false);
        }
    }

    public List<Programaeducativo> getProgramasEducativosConsultas() {
        System.out.println("Entro al get de programas educativos, esto es para ver si cambia:" + getCicloEscolarSeleccionado());
        return programasEducativosConsultas;
    }

    public void setProgramasEducativosConsultas(List<Programaeducativo> programasEducativosConsultas) {
        this.programasEducativosConsultas = programasEducativosConsultas;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
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

    public void onSelectPE(){
        refresh();
        // si es profesor 
        if (loginBean.getSeleccionado().equalsIgnoreCase("Profesor")) {
            System.out.println("RACTConsultasBeanUI:loginBeanEqualsProfesor:Entro" + programaEducativoSeleccionado);
            unidadesaprendisaje = ractConsultasBeanHelper.UnidadesAprendizajePorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado), cicloEscolarSeleccionado);
            unidadesProfesor = ractConsultasBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado), cicloEscolarSeleccionado);
        } else if (loginBean.getSeleccionado().equals("Administrador")) {
            if (!cicloEscolarSeleccionado.equals("00")) {
                profesorSeleccionado = 0;
                profesores = ractConsultasBeanHelper.getProfesoresByPEYCE(programaEducativoSeleccionado, cicloEscolarSeleccionado);
                if (!programaEducativoSeleccionado.equals("00")) {
                    unidadesProfesor = ractConsultasBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado));
                }
            }
        } else {
            if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
                System.out.println("RACTConsultasBeanUI:else:cicloEscolarSeleccionadoEqual00:Entro");
                unidadesaprendisaje = ractConsultasBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado, loginBean.getSeleccionado(), profesor.getPROid().toString(), getCicloEscolarSeleccionado());
            } else {
                    System.out.println("SALINAS ENTRO AL ELSE");
                    List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
                    for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList()){
                        if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                            peProgramaeducativoList.add(rpe.getProgramaeducativo());
                    }
                    programasEducativosConsultas = peProgramaeducativoList;
                    System.out.println(programasEducativos);
                    int idrpe = 0;
                    for(int x = 0;x<= profesor.getResponsableProgramaEducativosList().size();x++){
                        int s = profesor.getResponsableProgramaEducativosList().get(x).getProgramaeducativo().getPEDid();
                        String pedid = String.valueOf(s);
                        if(programaEducativoSeleccionado.equals("00")){
                            idrpe = 0;
                            unidadAprendizajeSeleccionada = "00";
                            profesorSeleccionado = 0;
                            refresh();
                            break;
                        }else{
                        if(programaEducativoSeleccionado.equals(pedid)){
                            idrpe = profesor.getResponsableProgramaEducativosList().get(x).getRPEid();
                            break;
                        }
                        }
                    }
                    
                    profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                    List<Profesor> profesoressinfiltro = new ArrayList<Profesor>();
                    profesores = new ArrayList<Profesor>();
                    profesoressinfiltro = ractConsultasBeanHelper.getProfesoresPorRPEYCE(idrpe, cicloEscolarSeleccionado);
                    for (Profesor pro: profesoressinfiltro){
                        System.out.println(pro);
                            if(!profesores.contains(pro)){
                            profesores.add(pro);
                            }
                    }
                    System.out.println(profesorSeleccionado);
                    unidadesProfesor = ractConsultasBeanHelper.getUNIdadesaprendisajeConGrupos2(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
                    unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
            }
        }
        if (unidadesaprendisaje != null) {
            for (int x = 0; x < unidadesaprendisaje.size(); x++) {
                System.out.println("Unidades de aprendizaje: " + unidadesaprendisaje.get(x).getUAPnombre());
                System.out.println("Unidad id:" + unidadesaprendisaje.get(x).getUAPid());
            }
        }
        setEstadoUnidadDeAprendizaje(false);

        /*if(loginBean.getSeleccionado().equalsIgnoreCase("Profesor")){
            if(archivoActual== 1){
                unidadesaprendisaje = ractBeanHelper.getUnidadesaprendisajeConGrupos(programaEducativoSeleccionado);
            }
            if(archivoActual == 2){
                if(cicloEscolarSeleccionado.equalsIgnoreCase("00")){
                    unidadesaprendisaje = ractBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado,loginBean.getSeleccionado(),profesor.getProid().toString(),cicloEscolarClave);
                }else{
                    unidadesaprendisaje = ractBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado,loginBean.getSeleccionado(),profesor.getProid().toString(),cicloEscolarSeleccionado);
                }
            }
            
        }else{
            unidadesaprendisaje = ractBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado,loginBean.getSeleccionado(),profesor.getProid().toString(),cicloEscolarClave);
            //unidadesaprendisaje = ractBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado,loginBean.getSeleccionado(),profesor.getProid().toString(),cicloEscolarSeleccionado);
            
        }  */
        disable = false;
        //fillTree();
        //enviado = true;
        setEnable(false);
    }

    public void refresh() {
        unidadAprendizajeSeleccionada = "00";
        IdUaipSeleccionada = 0;
        porcentajeAvance = 0.0f;
        root = new DefaultTreeNode();

    }

    public void onSelectPROFESOR() {
        //reinit stuff
        refresh();
        if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")) {
            unidadesProfesor = ractConsultasBeanHelper.uaipAreaProfesor(loginBean.getLogueado().getProfesorList().get(0).getPROid(), Integer.parseInt(cicloEscolarSeleccionado), profesorSeleccionado);
        } else if (loginBean.getSeleccionado().equals("Administrador")) {
            if(profesorSeleccionado == 0){
             unidadesProfesor = ractConsultasBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado));
            }else
            {
            unidadesaprendisaje = Collections.EMPTY_LIST;
            unidadesProfesor = ractConsultasBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado), profesorSeleccionado);
            }
        } else {
//            unidadesProfesor = ractConsultasBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), profesor.getProgramaeducativoList().get(0).getPEDid(), profesorSeleccionado);
            if(profesorSeleccionado == 0){
                unidadesProfesor.clear();
                unidadesProfesor = ractConsultasBeanHelper.getUNIdadesaprendisajeConGrupos2(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
                refresh();
            }
            else{
            List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
            for (ResponsableProgramaEducativo rpe : loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList())
                if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                    peProgramaeducativoList.add(rpe.getProgramaeducativo());
            unidadesProfesor.clear();
            /*for (Programaeducativo peProgramaeducativo : peProgramaeducativoList)
                unidadesProfesor.addAll(ractConsultasBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), peProgramaeducativo.getPEDid(), profesorSeleccionado));*/
            
            unidadesProfesor = ractConsultasBeanHelper.getUNIdadesaprendisajeConGrupos2(programaEducativoSeleccionado, cicloEscolarSeleccionado, profesorSeleccionado);
            //unidadesProfesor = filtrarUnidadAprendisajePorProfesor(unidadesProfesor);
            }
        }
    }

    /**
     * public void onSelectCE() { root = new DefaultTreeNode();
     * nombreProfesorPorUA = " "; disable = true; selectedNodes = null;
     * claveUnidadAprendizajeSeleccionada = "0"; cicloEscolarClave =
     * cicloEscolarSeleccionado; programaEducativoSeleccionado = "-- Seleccione
     * una opción --"; unidadAprendizajeSeleccionada = "-- Seleccione una opción
     * --";
     * <p>
     * if(cicloEscolarSeleccionado.equalsIgnoreCase("00")){ cicloEscolarClave =
     * ractConsultasBeanHelper.cicloescolarActual().getCesid().toString();
     * cicloEscolarSeleccionado =
     * ractConsultasBeanHelper.cicloescolarActual().getCesid().toString();
     * programasEducativosConsultas =
     * ractConsultasBeanHelper.getProgramaesEducativosPorRol(loginBean.getSeleccionado(),cicloEscolarClave);
     * }else{ programasEducativosConsultas =
     * ractConsultasBeanHelper.getProgramaesEducativosPorRol(loginBean.getSeleccionado(),cicloEscolarSeleccionado);
     * }
     * <p>
     * }*
     */
    public String getReporteSeleccionado() {
        return reporteSeleccionado;
    }

    public void setReporteSeleccionado(String reporteSeleccionado) {
        this.reporteSeleccionado = reporteSeleccionado;
    }

    public float getPorcentajeAvance() {
        System.out.println("CONSIGUIENDO PORCENTAJE AVANCE");
        return porcentajeAvance;
    }

    public String getUnidadAprendizajeSeleccionada() {
        return unidadAprendizajeSeleccionada;
    }

    /*
      Este metodo toma los parametros del xhtml y los envia al fillTree()  
     */
    public void onSelectREPORTE() {
        // Nuevo check point cuatro 2020
        if (IdUaipSeleccionada != 0) {
            fillTree();
            porcentajeAvance = ractConsultasBeanHelper.getPorcentajeAvance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(reporteSeleccionado);
            if (reporteSeleccionado.equals("00")) {
                arbolEmptyMessage = EMPTY_NO_RESULTS;

            }
            if (root.getChildCount() > 0) {
                fechaActual = sdf.format(ractConsultasBeanHelper.getFechaDeReporte());
                porcentajeAvance = ractConsultasBeanHelper.getPorcentajeAvance();
            } else {
                fechaActual = "";
                porcentajeAvance = 0.0f;
            }
        }
        System.out.println("--------------reporte seleccionado: " + reporteSeleccionado);
    }

    public boolean renderBtnEnviar = true;

    public boolean isRenderBtnEnviar() {
        return renderBtnEnviar;
    }

    public void setRenderBtnEnviar(boolean renderBtnEnviar) {
        this.renderBtnEnviar = renderBtnEnviar;
    }

    public void btnEnviarRender() {
        if (selectedNodes != null && selectedNodes.length > 0 && !enviado) {
            renderBtnEnviar = false;
        } else {
            renderBtnEnviar = true;
        }
    }

    public boolean enviado = false;

    //    public void validarEnviado() {
//        System.out.println("SE HA SELECCIONADO UNA UA TIPO" + tipoUnidadAprendizaje);
//        System.out.println(profesor.getProid() + " " + Integer.parseInt(grupo) + " " + subGrupo + " " + claveUnidadAprendizajeSeleccionada + " " + tipoUnidadAprendizaje);
//        if (ractConsultasBeanHelper.validarReporteEnviado(profesor.getProid(), Integer.parseInt(grupo), subGrupo, Integer.parseInt(claveUnidadAprendizajeSeleccionada), tipoUnidadAprendizaje,cicloEscolarSeleccionado,idUnidadAprendizajeSeleccionada)) {
//            System.out.println("AGREGANDO MENSAJE DE ENVIADOS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//            FacesContext.getCurrentInstance().addMessage("validarEnviado", new FacesMessage(FacesMessage.SEVERITY_WARN, "El reporte ya ha sido enviado", ""));
//            enviado = true;
//            disable2 = true;
//            RequestContext.getCurrentInstance().update("formId:message");
//        } else {
//            enviado = false;
//            if (selectedNodes != null && selectedNodes.length > 0) {
//                disable2 = false;
//            }
//            System.out.println("NO AGREGAR MENSAJE DE ENVIADOS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//            RequestContext.getCurrentInstance().update("formId:message");
//        }
//        //Los nodos se deshabilitan si el reporte ya esta enviado
//        if (enviado) {
//            for (TreeNode nodo : root.getChildren()) {
//                nodo.setSelectable(false);
//                for (TreeNode nodo2 : nodo.getChildren()) {
//                    nodo2.setSelectable(false);
//                    for (TreeNode nodo3 : nodo2.getChildren()) {
//                        nodo3.setSelectable(false);
//                    }
//                }
//            }
//        }
//
//        btnEnviarRender();
//    }
    TreeNode[] selectedNodesInitial = new DefaultTreeNode[10];

    //    public void validarEnviadoConsultas() {
//        System.out.println("SE HA SELECCIONADO UNA UA TIPO" + tipoUnidadAprendizaje);
//        System.out.println(profesor.getProid() + " " + Integer.parseInt(grupo) + " " + subGrupo + " " + claveUnidadAprendizajeSeleccionada + " " + tipoUnidadAprendizaje);
//        if (ractConsultasBeanHelper.validarReporteEnviado(profesor.getProid(), Integer.parseInt(grupo), subGrupo, Integer.parseInt(claveUnidadAprendizajeSeleccionada), tipoUnidadAprendizaje,cicloEscolarSeleccionado,idUnidadAprendizajeSeleccionada)) {
//            System.out.println("AGREGANDO MENSAJE DE ENVIADOS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//            FacesContext.getCurrentInstance().addMessage("validarEnviado", new FacesMessage(FacesMessage.SEVERITY_WARN, "El reporte ya ha sido enviado", ""));
//            enviado = true;
//            disable2 = true;
//            RequestContext.getCurrentInstance().update("formId:message");
//        } else {
//            enviado = false;
//            if (selectedNodes != null && selectedNodes.length > 0) {
//                disable2 = false;
//            }
//            System.out.println("NO AGREGAR MENSAJE DE ENVIADOS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//            RequestContext.getCurrentInstance().update("formId:message");
//        }
//        //Los nodos se deshabilitan si el reporte ya esta enviado
//        if (enviado) {
//            for (TreeNode nodo : root.getChildren()) {
//                nodo.setSelectable(false);
//                for (TreeNode nodo2 : nodo.getChildren()) {
//                    nodo2.setSelectable(false);
//                    for (TreeNode nodo3 : nodo2.getChildren()) {
//                        nodo3.setSelectable(false);
//                    }
//                }
//            }
//        }
//
//        btnEnviarRender();
//    }
    public void sumarNodoaPorcentaje(TreeNode nodo) {
        float sumar = 0;
        if (nodo.isSelected()) {
            sumar = Float.parseFloat(nodo.getData().toString().split("-//-")[5]);
            System.out.println("SE SUMARA" + sumar + "////////////////////////////////////////SUMAR NODO PORCENTAJE");
            porcentajeAvance += sumar;
        } else {
            for (TreeNode node : nodo.getChildren()) {
                sumarNodoaPorcentaje(node);
            }
        }
    }

    public void calculo() {
        porcentajeAvance = 0;

        boolean inValidación = false;
        boolean seleccionCompleta = true;
        for (TreeNode node : root.getChildren()) {
            inValidación = true;
            if (!node.isSelected()) {
                seleccionCompleta = false;
            }
        }
        if (seleccionCompleta && inValidación) {
            porcentajeAvance = 100;
        } else {
            for (TreeNode node : root.getChildren()) {
                sumarNodoaPorcentaje(node);
            }
        }

        System.out.println("FIN DEL CALCULO DE PORCENTAJE DE AVANCE");

        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        DecimalFormat formato = new DecimalFormat("####.##", simbolo);

        System.out.println(porcentajeAvance + "/////////////////////////////////////PORCENTAJE");
        porcentajeAvance = Float.parseFloat(formato.format(porcentajeAvance));
        //Actualizar etiqueta de porcentaje de avance
        PrimeFaces.current().executeInitScript("avance(" + porcentajeAvance + ")");
        //RequestContext.getCurrentInstance().execute("avance(" + porcentajeAvance + ")");
    }

    public String actualizarTablaCRPE() {
        List<Programaeducativo> lt;
        if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")
                || loginBean.getSeleccionado().equals("Responsable de Programa Educativo")) {
            lt = getProgramasEducativos();
            return lt.get(0).getPEDnombre();
        } else {
            return "-- Seleccione una opción  --";
        }

    }

    /*
    Este metodo llena la tabla en el xhtml de RactConsultas
     */
    public void fillTree() {
        int nSeEncontro = 0;
        boolean esta = false;
        UnidadaprendizajeImparteProfesor uip = ServiceFacadeLocator
                .getInstanceUnidadAprendizajeImparteProfesorFacade()
                .findUnidadAprendizajeImparteProfesorByID(IdUaipSeleccionada);
        System.out.println("//////////////////////Este es el numero de reporte seleccionado:" + reporteSeleccionado);


        try {
            //Verifica si se encontro un reporte con el numero seleccionado
            for (int i = 0; i <= uip.getReporteavancecontenidotematicoList().size() - 1; i++) {
                if (uip.getReporteavancecontenidotematicoList().get(i).getRACnumero().equalsIgnoreCase(reporteSeleccionado)) {
                    nSeEncontro = i;
                    esta = true;
                }
            }
            if (reporteSeleccionado.equals("00") || uip.getReporteavancecontenidotematicoList().get(nSeEncontro).getRACstatus().equalsIgnoreCase("Parcial") || esta == false) {

                TreeNode root = new DefaultTreeNode();
                root = null;
                this.root = new DefaultTreeNode();
                arbolEmptyMessage = EMPTY_NO_RESULTS;

            } else {
                // Else de filltree Checkpoint
                TreeNode root = new DefaultTreeNode();
                root = null;
                System.out.println("//////////////////////Este es el numero de reporte seleccionado:" + reporteSeleccionado);
                selectedNodes = null;
                auxSelectedNodes = false;

                System.out.println("//////////////////////Este es el numero de reporte seleccionado: " + reporteSeleccionado);

                if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
                    cicloEscolarSeleccionado = cicloEscolarActual;
                }
                ractConsultasBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());

                root = ractConsultasBeanHelper.TraerNodosTree(uip, uip.getUnidadAprendizajeUAPid(), reporteSeleccionado, String.valueOf(uip.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().size()));
                pintar = false;
                //PONER NODOS SELECCIONADOS
                if (auxSelectedNodes) {
                } else {
                    List<TreeNode> auxSelected = ractConsultasBeanHelper.getListaSeleccionados();
                    selectedNodes = new TreeNode[auxSelected.size()];
                    selectedNodesInitial = new TreeNode[auxSelected.size()];
                    int x = 0;
                    for (TreeNode node : auxSelected) {
                        if (node != null) {
                            if (node.isSelected()) {
                                selectedNodes[x] = node;
                                selectedNodesInitial[x] = node;
                                x++;
                            }
                        }
                    }
                    auxSelectedNodes = true;

                }


                //FIN NODOS SELECCIONADOS
                //GUARDAR INFORMACION PARA DATATABLE DE IMPRESION PDF
                filaTabla = ractConsultasBeanHelper.getListaPDF();

                this.root = root;
                UnidadaprendizajeImparteProfesor unidadImparte = ractConsultasBeanHelper.BuscarUaipById(IdUaipSeleccionada);
                //El nombre con el que se dara al PDF impreso
                Profesor profe = null;
                if (!esProfesor()) {
                    profe = ractConsultasBeanHelper.getProfesorById(profesorSeleccionado);
                }
                if (unidadesaprendisaje != null) {
                    for (Unidadaprendizaje ua : unidadesaprendisaje) {
                        if (claveUnidadAprendizajeSeleccionada.equals(String.valueOf(ua.getUAPclave()))) {
                            if (esProfesor()) {
                                nombreReporte = "RACT " + reporteSeleccionado + " -- " + profesor.getPROnumeroEmpleado() + " " + profesor.getPROnombre() + " -- " + unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre() + " -- " + unidadImparte.getGrupoGPOid().getGPOnumero() + " -- " + unidadImparte.getUIPsubgrupo() + " -- " + unidadImparte.getUIPtipoSubgrupo();

                            } else if (profe != null) {
                                nombreReporte = "RACT " + reporteSeleccionado + " -- " + profesor.getPROnumeroEmpleado() + " " + profesor.getPROnombre() + " -- " + unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre() + " -- " + unidadImparte.getGrupoGPOid().getGPOnumero() + " -- " + unidadImparte.getUIPsubgrupo() + " -- " + unidadImparte.getUIPtipoSubgrupo();

                            }
                            //el ultimo es un no breaking space
                            nombreReporte = nombreReporte.replace(" ", " ");

                        }
                    }
                }

                if (archivoActual == 1) {
                    calculo();
                }
                nombreReporte = "RACT " + reporteSeleccionado + " -- " + profesor.getPROnumeroEmpleado() + " " + profesor.getPROnombre() + " -- " + unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre() + " -- " + unidadImparte.getGrupoGPOid().getGPOnumero() + " -- " + unidadImparte.getUIPsubgrupo() + " -- " + unidadImparte.getUIPtipoSubgrupo();
            }
        } catch (Exception e) {
            this.root = new DefaultTreeNode();
            arbolEmptyMessage = EMPTY_NO_RESULTS;
        }
    }

    public boolean isTreeEmpty() {
        return root.getChildCount() <= 0;
    }

    public String unselectOnEnviado() {
        if (enviado) {
            return "";
        } else if (!loginBean.getSeleccionado().equals("Profesor")) {
            return "";
        } else {
            return "checkbox";
        }
    }
    
    
    public List<UnidadaprendizajeImparteProfesor> filtrarUnidadAprendisajePorProfesor(List<UnidadaprendizajeImparteProfesor> listaSinFiltrar) {
        List<UnidadaprendizajeImparteProfesor> listaFiltrada = new ArrayList();
        for (UnidadaprendizajeImparteProfesor up : listaSinFiltrar) {
            boolean existe = true;
            UnidadaprendizajeImparteProfesor cambio = null;
            if (listaFiltrada != null) {
                for (UnidadaprendizajeImparteProfesor filtro : listaFiltrada) {
                    if (up.getUnidadAprendizajeUAPid().getUAPid() == filtro.getUnidadAprendizajeUAPid().getUAPid() && up.getProfesorPROid() == filtro.getProfesorPROid()) {
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

    public void onSelectUA() {
        UnidadaprendizajeImparteProfesor uaip;
        int seEncontro = 0;
        boolean esta = false;
        uaip = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(IdUaipSeleccionada);
        // este try es para marcar que no hay resultados si aun no se ha hecho el reporte seleccionado
        try {

            for (int i = 0; i <= uaip.getReporteavancecontenidotematicoList().size() - 1; i++) {
                if (uaip.getReporteavancecontenidotematicoList().get(i).getRACnumero().equalsIgnoreCase(reporteSeleccionado)) {
                    seEncontro = i;
                    esta = true;
                }
            }

            if (reporteSeleccionado.equals("00") || uaip.getReporteavancecontenidotematicoList().get(seEncontro).getRACstatus().equalsIgnoreCase("Parcial") || esta == false) {
                arbolEmptyMessage = EMPTY_NO_RESULTS;
                this.root = new DefaultTreeNode();

            } else {
                //OBTENER NOMBRE DEL PROFESOR POR UNIDAD DE APRENDIZAJE
                if (loginBean.getSeleccionado().equalsIgnoreCase("Profesor")) {

                } else {

                    if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
                        profesorPorUA = ractConsultasBeanHelper.getProfesorUA(unidadAprendizajeSeleccionada);
                    } else {
                        profesorPorUA = uaip.getProfesorPROid();
                    }
                    nombreProfesorPorUA = profesorPorUA.getPROnombre() + " " + profesorPorUA.getPROapellidoPaterno() + " " + profesorPorUA.getPROapellidoMaterno();
                    ractConsultasBeanHelper.setIdProfesorporUASelect(profesorPorUA.getPROid());
                    profesorSeleccionado = profesorPorUA.getPROid();
                }
                setEnable2(true);
                fillTree();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (root.getChildCount() > 0) {
                    arbolEmptyMessage = EMPTY_NO_DATA_GIVEN;
                    porcentajeAvance = ractConsultasBeanHelper.getPorcentajeAvance();
                    fechaActual = sdf.format(ractConsultasBeanHelper.getFechaDeReporte());
                } else {
                    arbolEmptyMessage = EMPTY_NO_RESULTS;
                    porcentajeAvance = 0.0f;
                    fechaActual = "";
                }
            }
        } catch (Exception e) {
            this.root = new DefaultTreeNode();
            arbolEmptyMessage = EMPTY_NO_RESULTS;

        }
    }

    public String getArbolEmptyMessage() {
        return arbolEmptyMessage;
    }

    public String getNombreCompletoProfesor() {
        return nombreCompletoProfesor;
    }

    public String getObservacion() {
        return observacion;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String renderedPanelIconos() {
        if (tipoUnidadAprendizaje.equalsIgnoreCase("C") && !unidadAprendizajeSeleccionada.equals("00")) {
            return "visibility: visible;";
        }
        return "visibility:hidden";
    }

    public TreeNode pintarArbol() {
        return root;
    }

    public TreeNode[] getSelectedNodes() {
        System.out.println("OBTENIENDO NODOS");
        //Prueba de contenido
        if (selectedNodes != null && selectedNodes.length >= 1) {
            for (TreeNode nodo : selectedNodes) {

            }
        } else {
            System.out.println("NO HAY SELECCION ");
        }
        //
        return selectedNodes;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        System.out.println("SELECCIONADO");
        calculo();
        if (selectedNodes == null || selectedNodes.length == 0) {
            disable2 = true;
        } else {
            disable2 = false;
        }
        if (enviado) {
            disable2 = enviado;
        }
        if (event != null && event.getTreeNode() != null) {
            event.getTreeNode().setSelected(true);
        }
        btnEnviarRender();
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        System.out.println("DESSELECCIONADO");
        if (selectedNodes == null || selectedNodes.length == 0) {
            disable2 = true;
        }
        if (enviado) {
            disable2 = enviado;
        }

        if (event != null && event.getTreeNode() != null) {

        }
        calculo();
        btnEnviarRender();
    }

    public String selectableToImageConsultas(NodoMultiClass nodo) {
        if (nodo.isImpartido() && nodo.isDeReporteAnterior() == false) {
            return "true";
        }
        return "false";
    }

    public String checkWarningImageConsultas(NodoMultiClass nodo) {

        if (nodo.getParcialSelected()) {

            return "true";
        } else {
            if (nodo.isDeReporteAnterior()) {
                return "false";
            }

        }
        return "false";
    }

    public String horasAFormato(float horas) {
        //Mover variables a globarl y simbolo.setDecimalSeparator a CONSTRUCTOR
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        DecimalFormat formato = new DecimalFormat("##", simbolo);
        String formatoDeHora;
        int valorEntero = (int) horas;
        float minutos = horas - valorEntero;
        //Se convierten los decimales a minutos y se multiplica por 100 para poder despreciar la fraccion
        minutos = (float) (minutos * 30 / 0.5);

        if (String.valueOf((int) minutos).length() < 2) {
            formatoDeHora = String.valueOf(valorEntero + ":0" + formato.format(minutos));
        } else {
            formatoDeHora = String.valueOf(valorEntero + ":" + formato.format(minutos));
        }
        return formatoDeHora;
    }

    //trunca un valor a dos decimales 
    public float dosDecimales(float dosDecimales) {
        float convertido = 0;
        try {
            DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
            simbolo.setDecimalSeparator('.');
            DecimalFormat formato = new DecimalFormat("####.##", simbolo);
            convertido = Float.parseFloat(formato.format(dosDecimales));
        } catch (Exception e) {

        }
        return convertido;
    }

    public NodoMultiClass nodoObservacion = new NodoMultiClass();

    public void setNodoObservacion(NodoMultiClass nodoObservacion) {
        this.nodoObservacion = nodoObservacion;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public void verObservacion() {
        NodoMultiClass nodo = nodoObservacion;
        String tipo = nodo.toString().split("-//-")[0];
        String unidad = nodo.toString().split("-//-")[1];
        String tema = nodo.toString().split("-//-")[2];
        String subtema = nodo.toString().split("-//-")[3];
        for (TreeNode nod : root.getChildren()) {
            if (tipo.equals("unidad") || tipo.equals("practicalaboratorio") || tipo.equals("practicataller") || tipo.equals("practicaCampo")) {
                if (nod.getData().toString().split("-//-")[1].equals(unidad)) {
                    observacion = ((NodoMultiClass) nod.getData()).getObservaciones();
                }
            } else {
                for (TreeNode nod2 : nod.getChildren()) {
                    if (tipo.equals("temaunidad")) {
                        if (nod2.getData().toString().split("-//-")[2].equals(tema)) {
                            observacion = ((NodoMultiClass) nod2.getData()).getObservaciones();

                        }
                    } else {
                        for (TreeNode nod3 : nod2.getChildren()) {
                            if (tipo.equals("subtemaunidad")) {
                                if (nod3.getData().toString().split("-//-")[3].equals(subtema)) {
                                    observacion = ((NodoMultiClass) nod3.getData()).getObservaciones();
                                }
                            }
                        }
                    }
                }
            }
        }
        observacion = observacion.trim();
    }

    public void prepararObservacion(NodoMultiClass nodoMultiClass) {
        setNodoObservacion(nodoMultiClass);
        verObservacion();
    }

    public String observacionVacia(NodoMultiClass nodo) {
        String tipo = nodo.toString().split("-//-")[0];
        String unidad = nodo.toString().split("-//-")[1];
        String tema = nodo.toString().split("-//-")[2];
        String subtema = nodo.toString().split("-//-")[3];
        for (TreeNode nod : root.getChildren()) {
            if (tipo.equals("unidad") || tipo.equals("practicalaboratorio") || tipo.equals("practicataller") || tipo.equals("practicaCampo")) {
                if (nod.getData().toString().split("-//-")[1].equals(unidad)) {
                    observacion = ((NodoMultiClass) nod.getData()).getObservaciones();
                }
            } else {
                for (TreeNode nod2 : nod.getChildren()) {
                    if (tipo.equals("temaunidad")) {
                        if (nod2.getData().toString().split("-//-")[2].equals(tema)) {
                            observacion = ((NodoMultiClass) nod2.getData()).getObservaciones();

                        }
                    } else {
                        for (TreeNode nod3 : nod2.getChildren()) {
                            if (tipo.equals("subtemaunidad")) {
                                if (nod3.getData().toString().split("-//-")[3].equals(subtema)) {
                                    observacion = ((NodoMultiClass) nod3.getData()).getObservaciones();
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!observacion.trim().isEmpty()) {

            return "ui-icon-NoteBlank";
        } else {
            return "ui-icon-NoteWrite";

        }
    }

    public String rendered() {
        if (root.getChildCount() > 0) {
            return "visibility: visible;";
        }

        return "visibility:hidden";
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
    }

    //Impresion de archivo PDF
    //Impresion de archivo PDF
    public void postProcessPDF(Object document) throws IOException, DocumentException {
        final Document pdf = (Document) document;
        //TreeNode[] selectedNodesPDF = selectedNodes;
        UnidadaprendizajeImparteProfesor uaip = ractConsultasBeanHelper.BuscarUaipById(IdUaipSeleccionada);
        // cambie el array por una lista para poder agregar unidades no seleccionadas pero comentadas en pdf  
        List<TreeNode> selectedNodesPDF = new ArrayList<TreeNode>();
        //mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico uaptct = ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().buscarContenidoPorIdCiclo(uaip.getUnidadAprendizajeUAPid().getUAPid(), cicloEscolarActual);

        // lleno la lista de la misma manera que se igualaba anteriormente el array con este for
//        for(int x=0; x< selectedNodes.length;x++){
//            selectedNodesPDF.add(selectedNodes[x]);
//        }
//        
        /**
         * los siguientes for each van a reemplazar el llenado de la lista ahora
         * se va a llenar con las unidades,temaUnidad,subtemaUnidad
         * taller,laboratorio o practica de campo segun sea el caso que este
         * seleccionada o tenga algun comentario pero no este seleccionada si se
         * vuelve lento el generar el pdf buscar forma de comparar sin los for
         * todo los nodos son tomados desde la variable root que contiene la
         * informacion que esta el treeTable del ract
         */
        // for para unidad,taller,laboratorio o practicas campo
        System.out.println("Probando jejej");
        for (TreeNode unidad : root.getChildren()) {

            // se convierte en array para verificar que tenga comentario
            String[] datos = unidad.getData().toString().split("-//-");
            // si esta seleccionado se agregara a la lista del pdf
            if (unidad.isSelected()) {

                selectedNodesPDF.add(unidad);
            }
            // datos anteriormente separados con split y para ver comentario que es el 6 y evaluo si esta vacio
            // si tiene un comentario y la unidad no esta seleccionada se agregara
            if (!datos[6].equalsIgnoreCase(" ") && !unidad.isSelected()) {
                selectedNodesPDF.add(unidad);

            }

            //    for para los temas
            for (TreeNode tema : unidad.getChildren()) {
                if (tema.isSelected()) {
                    selectedNodesPDF.add(tema);
                }
                String[] dato = tema.getData().toString().split("-//-");
                if (!dato[6].equalsIgnoreCase(" ") && !tema.isSelected() && !tema.getParent().isSelected()) {
                    selectedNodesPDF.add(tema);
                }

                // for para los subtemas
                for (TreeNode subtema : tema.getChildren()) {
                    if (subtema.isSelected()) {
                        selectedNodesPDF.add(subtema);
                    }
                    String[] dat = subtema.getData().toString().split("-//-");
                    if (!dat[6].equalsIgnoreCase(" ") && !subtema.isSelected() && !subtema.getParent().isSelected()) {
                        selectedNodesPDF.add(subtema);
                    }

                }
            }
        }

        if (selectedNodes != null) {
            for (TreeNode nodo : selectedNodes) {
                System.out.println("*********************" + ((NodoMultiClass) nodo.getData()).getNombre());
            }
        }
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
        String nombrep = "N/A", numEmpPro = "N/A";
        if (esProfesor()) {
            nombrep = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
            numEmpPro = Integer.toString(profesor.getPROnumeroEmpleado());
        } else if (profesorSeleccionado != null) {
            Profesor profe = ractConsultasBeanHelper.getProfesorById(profesorSeleccionado);
            nombrep = profe.getPROnombre() + " " + profe.getPROapellidoPaterno() + " " + profe.getPROapellidoMaterno();
            numEmpPro = Integer.toString(profe.getPROnumeroEmpleado());
        }
        String porAv = Float.toString(porcentajeAvance);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        //Obtener el nombre del programa educativo seleccionado
        String nombreProgEdu = "";
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
        int uniAprselec2 = Integer.parseInt(unidadAprendizajeSeleccionada);
//        for (int x = 0; x < unidadesaprendisaje.size(); x++) {
//            if (unidadesaprendisaje.get(x).getUAPclave() == uniAprselec2) {
//                nombreUniApr = unidadesaprendisaje.get(x).getUAPnombre();
//                nombreclave = Integer.toString(unidadesaprendisaje.get(x).getUAPclave());
//            }
//        }
        //----------------------------------------------------------------------

        // rutaImagen es la ruta para acceder a la imagen guardada en el folder resources del proyecto
        try {
//            Image imagenLogo = Image.getInstance("C:\\Users\\Y\\Desktop\\RACT 22-01-2015\\RACT\\build\\web\\resources\\imagenes\\logo.jpg");
            // System.out.println(mx.avanti.siract.ui.imagenes);
            Image imagenLogo = Image.getInstance(RACTBeanUI.class.getResource("/logo.jpg"));
            // Image imagenLogo = Image.getInstance("http://ed.uabc.mx/sed/images/logo.jpg");

            //Posicion de imagen (Horizontal, Vertiacal)
            imagenLogo.setAbsolutePosition(120f, 460f);

            //Tamaño de imagen (Ancho, largo)
            imagenLogo.scaleAbsolute(90, 120);
            //imagenLogo.scaleAbsoluteWidth(100f);
            //imagenLogo.scaleAbsoluteHeight(150f);
            //imagenLogo.scaleToFit(100f, 120f); Esta es la buena
            //Image imagenLogo = Image.getInstance("http://ed.uabc.mx/sed/images/logo.jpg");
            //imagenLogo.scaleAbsolute(70f, 90f);
            pdf.add(imagenLogo);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }
        //Tabla con UABC y Nombre del profesor(a)
        PdfPTable pdfTabletitulo = new PdfPTable(2);
        pdfTabletitulo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdfTabletitulo2 = new PdfPTable(2);
        pdfTabletitulo2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 22, Font.BOLD, new Color(0, 113, 65)));
        UABC.setAlignment(Element.ALIGN_CENTER);
        Paragraph esp = new Paragraph(" ");
        pdf.add(UABC);
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(esp);
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));
        pdfTabletitulo.addCell(new Paragraph(""));

        pdfTabletitulo.addCell(new Paragraph("Profesor(a): ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo.addCell(new Paragraph(nombrep));

        pdfTabletitulo2.addCell(new Paragraph("Num. de empleado: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdfTabletitulo2.addCell(new Paragraph(numEmpPro));

        pdfTabletitulo.setHorizontalAlignment(25);
        float[] columnWidthsss = new float[]{4f, 28};
        pdfTabletitulo.setWidths(columnWidthsss);
        pdf.add(pdfTabletitulo);

        float[] columnWidthss = new float[]{10f, 38};
        pdfTabletitulo2.setWidths(columnWidthss);
        pdf.add(pdfTabletitulo2);

        pdf.add(new Phrase(" "));

        //----------------------------------------------------------------------
        //Tabla Cabezera
        PdfPTable pdftablecabezera = new PdfPTable(4);
        pdftablecabezera.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdftablecabezera2 = new PdfPTable(5);
        pdftablecabezera2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        pdftablecabezera.addCell(new Paragraph("Programa educativo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph("Fecha: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph("Ciclo escolar: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph("Avance global: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(nombreProgEdu));
        pdftablecabezera.addCell(new Paragraph(formato.format(ractConsultasBeanHelper.getFechaDeReporte())));
        pdftablecabezera.addCell(new Paragraph("        " + getSelectedCE(cicloEscolarSeleccionado).getCEScicloEscolar()));
        pdftablecabezera.addCell(new Paragraph("       " + porAv + "%"));

        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));

        pdftablecabezera2.addCell(new Paragraph("Unidad de aprendizaje: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Clave: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Grupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Subgrupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Tipo grupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph(uaip.getUnidadAprendizajeUAPid().getUAPnombre()));
        pdftablecabezera2.addCell(new Paragraph(" " + uaip.getUnidadAprendizajeUAPid().getUAPclave()));
        pdftablecabezera2.addCell(new Paragraph("" + uaip.getGrupoGPOid().getGPOnumero()));
        pdftablecabezera2.addCell(new Paragraph("       " + uaip.getUIPsubgrupo()));
        String tipoGrupo = "";
        switch (uaip.getUIPtipoSubgrupo()) {
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
                tipoGrupo = "Practicas Clinica";
                break;
        }
        pdftablecabezera2.addCell(new Paragraph("       " + tipoGrupo));
        pdftablecabezera2.addCell(new Paragraph(" "));

        pdftablecabezera.setHorizontalAlignment(25);
        float[] columnWidths = new float[]{38f, 10f, 14f, 18f};
        pdftablecabezera.setWidths(columnWidths);
        pdf.add(pdftablecabezera);

        pdftablecabezera2.setHorizontalAlignment(25);
        float[] columnWidthss2 = new float[]{38f, 10f, 8f, 11f, 14f};
        pdftablecabezera2.setWidths(columnWidthss2);
        pdf.add(pdftablecabezera2);

        pdf.add(new Phrase(" "));

        //----------------------------------------------------------------------
        Paragraph numrep = new Paragraph("Reporte # " + reporteSeleccionado, FontFactory.getFont(FontFactory.TIMES, 16, Font.BOLD, new Color(0, 0, 0)));
        numrep.setAlignment(Element.ALIGN_CENTER);
        pdf.add(numrep);
        pdf.add(new Paragraph(" "));
        //Tabla con Datos 
        PdfPTable pdfTable = new PdfPTable(4);

        pdfTable.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTable.addCell(new Phrase("Porcentaje", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTable.addCell(new Phrase("Observaciones", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTable.addCell(new Phrase("No. Ract", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));

        ractConsultasBeanHelper.llenarTablaDeReportes(pdfTable, this.tipoUnidadAprendizaje, cicloEscolarSeleccionado, reporteSeleccionado, unidadAprendizajeSeleccionada, IdUaipSeleccionada);
        //Evitar Null pointer exception por no tener nodoes en selectedNodes
        //Se utiliza un auxiliar de selectedNodes por que hay posibilidad de que selectedNodes se modifique durante el proceso de generacion del PDF
        if (selectedNodesPDF != null && selectedNodesPDF.size() > 0 && false) {

            //Regresar a lo basicoString
            int numeroDeNodos = selectedNodesPDF.size();
            String[] nodosSeleccionados = new String[numeroDeNodos];
            //--- anteriormente se recorria solo los nodos seleccionados se cambio para imprimir mensajes en unidades no completadas

            for (int x = 0; x < selectedNodesPDF.size(); x++) {
                System.out.println(((NodoMultiClass) selectedNodesPDF.get(x).getData()).getObservaciones());
                nodosSeleccionados[x] = ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getNumero() + "--" + ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getNombre() + "--" + ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getPorcentajeAvance() + "--" + ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getObservaciones() + " -- ";

            }
            String[] filas = new String[5];
            String[] nstr = new String[4];

            String[] filas2 = new String[5];
            String[] nstr2 = new String[4];

            for (int x = 1; x < nodosSeleccionados.length; x++) {
                for (int y = 0; y < nodosSeleccionados.length - x; y++) {
                    //Obtengo los valores para hacer las comparaciones
                    int[] n = new int[4];
                    int[] n2 = new int[4];
                    filas = nodosSeleccionados[y].split("--");
                    nstr = filas[0].split("\\.");
                    for (int z = 0; z < nstr.length; z++) {
                        if (nstr == null) {
                            n[z] = 0;
                        } else {
                            n[z] = Integer.parseInt(nstr[z]);
                        }
                    }
                    filas2 = nodosSeleccionados[y + 1].split("--");
                    nstr2 = filas2[0].split("\\.");
                    for (int z = 0; z < nstr2.length; z++) {
                        if (nstr2 == null) {
                            n2[z] = 0;
                        } else {
                            n2[z] = Integer.parseInt(nstr2[z]);
                        }
                    }
                    //-------------------------------------------------comparacion
                    if (n[0] == n2[0]) {
                        if (n[1] == n2[1]) {
                            if (n[2] > n2[2]) {
                                String aux = nodosSeleccionados[y];
                                nodosSeleccionados[y] = nodosSeleccionados[y + 1];
                                nodosSeleccionados[y + 1] = aux;
                            }
                        } else {
                            if (n[1] > n2[1]) {
                                String aux = nodosSeleccionados[y];
                                nodosSeleccionados[y] = nodosSeleccionados[y + 1];
                                nodosSeleccionados[y + 1] = aux;
                            }
                        }
                    } else {
                        if (n[0] > n2[0]) {
                            String aux = nodosSeleccionados[y];
                            nodosSeleccionados[y] = nodosSeleccionados[y + 1];
                            nodosSeleccionados[y + 1] = aux;
                        }
                    }
                    //-------------------------------------------------------------
                }
            }

            String[] auxNum = new String[5];
            for (int x = 0; x < nodosSeleccionados.length; x++) {
                if (x + 1 < nodosSeleccionados.length) {
                    NodoMultiClass auxNodo = ((NodoMultiClass) selectedNodesPDF.get(x).getData());
                    NodoMultiClass auxNodoSiguiente = ((NodoMultiClass) selectedNodesPDF.get(x + 1).getData());
                    System.out.println(auxNodo.getId() + " -- " + auxNodo.getNumero() + " -- " + auxNodo.getNombre());
                    System.out.println(auxNodoSiguiente.getId() + " -- " + auxNodoSiguiente.getNumero() + " -- " + auxNodoSiguiente.getNombre());
                    if (auxNodo.getNumero().equals(auxNodoSiguiente.getNumero()))
                        continue;
                }

                filas = nodosSeleccionados[x].split("--");
                num = filas[0].split("\\.");

                //Auxiliar para agregar padre de tema/subtema
                int auxMargen = 1;
                //Agregar unidad a la que pertenece el tema/subtema

                String[] auxNum2 = auxNum;
                int tamanoArbol = root.getChildCount();
                //AGREGAR A TEMA
                if (Integer.parseInt(num[0]) > tamanoArbol) {
                    auxMargen = Integer.parseInt(num[0]) - tamanoArbol;
                }
                if (num.length >= 2 && auxNum2[0] != null && !auxNum2[0].equals(num[0])) {
                    if (!num[1].equals("0")) {
                        while (!num[0].equals(auxNum2[0]) && auxMargen > 0) {
                            System.out.println("XXXXXXIteracion  para encontrar padre XXXXXXX 0" + auxMargen);
                            auxNum2 = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");
                            if (num[0].equals(auxNum2[0])) {

                            } else {
                                auxMargen--;
                            }

                        }
                        System.out.println("***Unidad Padre" + ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNombre());
                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData());
                        //UTIL PARA PONER TEXTO TACHADO
//                     Chunk strikethrough = new Chunk("Strikethrough.");
//                      strikethrough.setUnderline(0.1f, 3f); //0.1 thick, 2 y-location
//                     document.add(strikethrough);
                        pdfTable.addCell(new Phrase(auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());

                    }
                } else {
                    if (auxNum2[0] == null && num.length >= 2) {

                        auxNum2 = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");
                        while (!num[0].equals(auxNum2[0]) && auxMargen > 0) {
                            System.out.println("XXXXXXIteracion  para encontrar padre XXXXXXX 0" + auxMargen);
                            auxNum2 = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");
                            if (num[0].equals(auxNum2[0])) {

                            } else {
                                auxMargen--;
                            }
                        }
                        System.out.println("***Unidad Padre" + ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNombre());
                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData());
                        //UTIL PARA PONER TEXTO TACHADO
//                     Chunk strikethrough = new Chunk("Strikethrough.");
//                      strikethrough.setUnderline(0.1f, 3f); //0.1 thick, 2 y-location
//                     document.add(strikethrough);
                        pdfTable.addCell(new Phrase(auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());

                    }
                }

                //AGREGAR A SUBTEMA
                String[] auxNumeroUnidad = new String[5];
                int auxMargen2 = 1;
                if (num.length == 3 && auxNum[1] != null && !auxNum[1].equals(num[1])) {

                    if (!num[2].equals("0")) {

                        while (!num[0].equals(auxNum[0]) && auxMargen >= 0) {
                            NodoMultiClass auxNodoUnidad = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData());
                            auxNumeroUnidad = auxNodoUnidad.getNumero().split("\\.");
                            try {
                                tamanoArbol = root.getChildren().get(Integer.parseInt(num[0])).getChildCount();
                            } catch (Exception e) {

                            }
                            if (Integer.parseInt(num[1]) > tamanoArbol) {
                                auxMargen2 = Integer.parseInt(num[1]) - tamanoArbol;
                            }

                            while (auxNumeroUnidad[0].equals(num[0]) && !num[1].equals(auxNum[1]) && auxMargen > 0) {
                                System.out.println("XXXXXXIteracion  para encontrar padre XXXXXXX 0" + auxMargen);
                                auxNum = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData()).getNumero()).split("\\.");
                                if (num[1].equals(auxNum[1])) {

                                } else {
                                    auxMargen2--;
                                }

                            }
                            if (num[0].equals(auxNum[0])) {
                            } else {
                                auxMargen--;
                            }
                        }

                        System.out.println("*****Unidad Padre(SUBTEMA)" + ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData()).getNombre());
                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData());
                        //UTIL PARA PONER TEXTO TACHADO
//                     Chunk strikethrough = new Chunk("Strikethrough.");
//                      strikethrough.setUnderline(0.1f, 3f); //0.1 thick, 2 y-location
//                     document.add(strikethrough);
                        pdfTable.addCell(new Phrase("   " + auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());

                    }
                } else {

                    if (auxNum[0] == null && num.length == 3) {
                        auxNum = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");
                        while (!num[0].equals(auxNum[0]) && auxMargen > 0) {
                            NodoMultiClass auxNodoUnidad = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData());
                            auxNumeroUnidad = auxNodoUnidad.getNumero().split("\\.");
                            tamanoArbol = root.getChildren().get(Integer.parseInt(num[0])).getChildCount();
                            if (Integer.parseInt(num[1]) > tamanoArbol) {
                                auxMargen2 = Integer.parseInt(num[1]) - tamanoArbol;
                            }
                            while (auxNumeroUnidad[0].equals(num[0]) && !num[1].equals(auxNum[1]) && auxMargen > 0) {
                                System.out.println("Iteracion  para encontrar padre XXXXXXX 0" + auxMargen);
                                auxNum = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData()).getNumero()).split("\\.");
                                if (num[1].equals(auxNum[1])) {

                                } else {
                                    auxMargen2--;
                                }
                            }
                            if (num[0].equals(auxNum[0])) {
                            } else {
                                auxMargen--;
                            }
                        }

                        System.out.println("***Unidad Padre(SUBTEMA)" + ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData()).getNombre());
                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData());
                        //UTIL PARA PONER TEXTO TACHADO
//                     Chunk strikethrough = new Chunk("Strikethrough.");
//                      strikethrough.setUnderline(0.1f, 3f); //0.1 thick, 2 y-location
//                     document.add(strikethrough);

                        pdfTable.addCell(new Phrase("   " + auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());
                        System.out.println("hice algo linea 1886 ");

                    }

                }

                auxNum = num;
                //FIN AGREGAR PADRE DE TEMA/SUBTEMA 
                boolean banpor = true;

                /**
                 * aqui voy a verificar que el nodo X en el ciclo que se esta
                 * recorriendo no este seleccionado y ademas tenga un comentario
                 * para agregarlo al pdf pero en un todo gris
                 */
                String[] StringNode = selectedNodesPDF.get(x).getData().toString().split("-//-");
                // separo el nodo selecciondo

                String[] particionNode = nodosSeleccionados[x].split("--");

                // si el nodo de la lista del pdf no esta seleccionado y coincide con el nombre
                // de la unidad de el nodo seleccionado que se esta recorriendo entrare aqui
                if (!selectedNodesPDF.get(x).isSelected() && particionNode[2].equalsIgnoreCase(StringNode[5])) {

                    /**
                     * si es una unidad o practicas lo pondre en gris pero con
                     * letra bold
                     */
                    if (StringNode[0].equalsIgnoreCase("unidad") || StringNode[0].equalsIgnoreCase("practicataller") || StringNode[0].equalsIgnoreCase("practicataller") || StringNode[0].equalsIgnoreCase("practicalaboratorio")) {
                        NodoMultiClass auxNodo = ((NodoMultiClass) selectedNodesPDF.get(x).getData());
                        pdfTable.addCell(new Phrase(auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(String.valueOf(dosDecimales(Float.parseFloat(auxNodo.getPorcentajeAvance()))), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());
                        pdfTable.addCell(reporteSeleccionado);
                    }
                    /**
                     * si es un tema unidad lo pondre con letra normal para en
                     * gris
                     */
                    if (StringNode[0].equalsIgnoreCase("temaunidad")) {
                        NodoMultiClass auxNodo = ((NodoMultiClass) selectedNodesPDF.get(x).getData());
                        pdfTable.addCell(new Phrase("   " + auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(String.valueOf(dosDecimales(Float.parseFloat(auxNodo.getPorcentajeAvance()))), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());
                        pdfTable.addCell(reporteSeleccionado);
                    }
                    /**
                     * si es un subtema lo pondre en italica y gris
                     */
                    if (StringNode[0].equalsIgnoreCase("subtemaunidad")) {
                        NodoMultiClass auxNodo = ((NodoMultiClass) selectedNodesPDF.get(x).getData());
                        pdfTable.addCell(new Phrase("       " + auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(String.valueOf(dosDecimales(Float.parseFloat(auxNodo.getPorcentajeAvance()))), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, Font.NORMAL, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());
                        pdfTable.addCell(reporteSeleccionado);
                    }

                } /**
                 * si no tiene comentario y ya esta seleccionada hara el
                 * procidimiento que ya se realizaba antes
                 */
                else {
                    if (num.length == 1) {
                        System.out.println(filas[0] + ".- " + filas[1]);
                        pdfTable.addCell(new Phrase(filas[0] + ".- " + filas[1], FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
                        pdfTable.addCell(new Phrase(String.valueOf(dosDecimales(Float.parseFloat(filas[2]))), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
                        banpor = false;
                    }
                    if (num.length == 2) {
                        System.out.println(filas[0] + ".- " + filas[1]);

                        pdfTable.addCell(new Phrase("   " + filas[0] + ".- " + filas[1]));
                    }
                    if (num.length == 3) {
                        System.out.println(filas[0] + ".- " + filas[1]);

                        pdfTable.addCell(new Phrase("       " + filas[0] + ".- " + filas[1], FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
                        pdfTable.addCell(new Phrase(String.valueOf(dosDecimales(Float.parseFloat(filas[2]))), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
                        banpor = false;
                    }
                    if (banpor) {
                        pdfTable.addCell(String.valueOf(dosDecimales(Float.parseFloat(filas[2]))));
                        pdfTable.addCell(filas[3]);
                        pdfTable.addCell(reporteSeleccionado);
                    } else {
                        pdfTable.addCell(filas[3]);
                        pdfTable.addCell(reporteSeleccionado);
                    }
                }
            }
        } else {
            pdfTable.addCell("No hay nada seleccionado");
            pdfTable.addCell("");
            pdfTable.addCell("");
        }

        pdfTable.setHorizontalAlignment(15);
        float[] columnWidths2 = new float[]{32f, 8f, 14f, 8f};
        pdfTable.setWidths(columnWidths2);
        pdf.add(pdfTable);
        //----------------------------------------------------------------------
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public List<NodoMultiClass> getFilaTabla() {
        return filaTabla;
    }

    public boolean esProfesor() {
        String rol = loginBean.getSeleccionado();
        return rol.equalsIgnoreCase("Profesor");
    }

    private Cicloescolar getSelectedCE(String ce) {
        int ceid = 0;
        try {
            ceid = Integer.parseInt(ce);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (Cicloescolar cicloe : listaCiclosEscolares) {
            if (cicloe.getCESid() == ceid) {
                return cicloe;
            }
        }
        return null;
    }

}