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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.avanti.siract.common.NodoCT;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.helper.NodoMultiClass;
import mx.avanti.siract.helper.RACTBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Y Refactor Eduardo Cardona
 */
@ManagedBean
@ViewScoped
public class RACTBeanUI implements Serializable {

    private final int IDCATALOGORACT = 13;

    public int getIDCATALOGORACT() {
        return IDCATALOGORACT;
    }

//-------------------------------ATRIBUTOS----------------------------------------//
    RACTBeanHelper ractBeanHelper = new RACTBeanHelper();
    int unidadImparteProfesorSeleccionada = 0;
    List<Unidad> unidades;
    List<Temaunidad> temas;
    List<Unidadaprendizaje> unidadesaprendizaje = null;
    List<Profesor> profesores;
    List<Grupo> grupos = new ArrayList();
    Profesor profesor;
    UnidadaprendizajeImparteProfesor uaips;
    String claveUnidadAprendizajeSeleccionada = "0";
    List<String> as = new ArrayList();
    List<Programaeducativo> programasEducativos;
    String clave = "000000";
    String nombreProfesor = "@Profesor";
    String fechaActual = "DD/MM/AAAA";
    String grupo = "00";
    String nombreUnidadAprendizaje;
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
    int unidadAprendizajeSeleccionada = 0;
    Boolean disable = true;
    Boolean disable2 = true;
    int nodosSeleccionados = 0;
    int numeroNodosSeleccionados;
    // Variable para deshabilitar  poder guardar comentario cuando el ract ya ha sido enviado
    Boolean disableComentario = false;
    Boolean disablePDF = false;// variable para deshabilitar el boton de pdf si el ract no ha sido enviando
    Boolean disableBtnPDF = false;
    String nombreUnidadSeleccionado;
    //Variable para saber si estoy en Ract.xhtml(1) o RACTConsultas(2)
    private int archivoActual = 0;
    int numeroObservacionesActuales = 0;
    String tipo = "";
    String[] num;
    //Variable temporal borrra despues de uso para pruebas. 2 de sep de 2016
    boolean esCoordinador;
    private Boolean disable3 = false;
    Boolean deshabilitar = true;
    String nombreCompletoProfesor = " ";
    Profesor profesorPorUA = null;
    String nombreProfesorPorUA = " ";
    List<Programaeducativo> programasEducativosConsultas;
    List<Unidadaprendizaje> unidadesAprendizajeConsultas = null;
    Areaadministrativa areaadministrativaCoordina = null;
    boolean observacionAnterior = false; //Utilizado para deshabilitar el guardado de observaciones de reportes pasados    
    String reporteActual = "Reporte #" + numeroReporte;
    private String reporteSeleccionado = "00";
    String tipoGrupo;
    String cicloEscolar;
    String cicloEscolarClave = ractBeanHelper.cicloescolarActual().getCESid().toString();
    String cicloEscolarActual = ractBeanHelper.cicloescolarActual().getCEScicloEscolar();
    private String cicloEscolarSeleccionado = "00";
    private List<Cicloescolar> listaCiclosEscolares;
    private boolean disablePE = false;
    // variables para pruebas version 2.0
    private UnidadaprendizajeImparteProfesor unidadImpartida;
    private List<UnidadaprendizajeImparteProfesor> unidadesProfesor;

    public int getUnidadImparteProfesorSeleccionada() {
        return unidadImparteProfesorSeleccionada;
    }

    public void setUnidadImparteProfesorSeleccionada(int unidadImparteProfesorSeleccionada) {
        this.unidadImparteProfesorSeleccionada = unidadImparteProfesorSeleccionada;
        unidadAprendizajeSeleccionada = unidadImparteProfesorSeleccionada;
    }

    public List<UnidadaprendizajeImparteProfesor> getUnidadesProfesor() {
        return unidadesProfesor;
    }

    public void setUnidadesProfesor(List<UnidadaprendizajeImparteProfesor> unidadesProfesor) {
        this.unidadesProfesor = unidadesProfesor;
    }

    public UnidadaprendizajeImparteProfesor getUnidadImpartida() {
        return unidadImpartida;
    }

    public void setUnidadImpartida(UnidadaprendizajeImparteProfesor unidadImpartida) {
        this.unidadImpartida = unidadImpartida;
    }

    public RACTBeanHelper getRactBeanHelper() {
        return ractBeanHelper;
    }

    public void setRactBeanHelper(RACTBeanHelper ractBeanHelper) {
        this.ractBeanHelper = ractBeanHelper;
    }

    public boolean getDisablePE() {
        return disablePE;
    }

    public Boolean getDisableBtnPDF() {
        return disableBtnPDF;
    }

    public void setDisableBtnPDF(Boolean disableBtnPDF) {
        this.disableBtnPDF = disableBtnPDF;
    }

    public void setDisablePE(boolean disablePE) {
        this.disablePE = disablePE;
    }

    public boolean getObservacionAnterior() {
        return observacionAnterior;
    }

    public void setObservacionAnterior(boolean observacionAnterior) {
        this.observacionAnterior = observacionAnterior;
    }

    public String getReporteActual() {
        return reporteActual;
    }

    public void setReporteActual(String reporteActual) {
        this.reporteActual = reporteActual;
    }

    public String getCicloEscolarClave() {
        return cicloEscolarClave;
    }

    public void setCicloEscolarClave(String cicloEscolarClave) {
        this.cicloEscolarClave = cicloEscolarClave;
    }

    public List<Unidadaprendizaje> getUnidadesAprendizajeConsultas() {
        return unidadesAprendizajeConsultas;
    }

    public void setUnidadesAprendizajeConsultas(List<Unidadaprendizaje> unidadesAprendizajeConsultas) {
        this.unidadesAprendizajeConsultas = unidadesAprendizajeConsultas;
    }

    public List<Programaeducativo> getProgramasEducativosConsultas() {        
        return programasEducativosConsultas;
    }

    public void setProgramasEducativosConsultas(List<Programaeducativo> programasEducativosConsultas) {
        this.programasEducativosConsultas = programasEducativosConsultas;
    }

    public Profesor getProfesorPorUA() {
        return profesorPorUA;
    }

    public void setProfesorPorUA(Profesor profesorPorUA) {
        this.profesorPorUA = profesorPorUA;
    }

    public Boolean getDisablePDF() {
        return disablePDF;
    }

    public String getNombreProfesorPorUA() {//GRUPOCICLOID ESCOLARID UAID
        return nombreProfesorPorUA;
    }

    public void setNombreProfesorPorUA(String nombreProfesorPorUA) {
        this.nombreProfesorPorUA = nombreProfesorPorUA;
    }

    public String getCicloEscolarActual() {
        return cicloEscolarActual;
    }

    public void setCicloEscolarActual(String cicloEscolarActual) {
        this.cicloEscolarActual = cicloEscolarActual;
    }

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    boolean auxSelectedNodes = false;

    //Lista utilizada para llenar datatable y generarPDF
    List<NodoMultiClass> filaTabla;

    boolean excesoDeReportes = false;

    //////PARTE DE OBSEVACIONES - - FALTA DE OPTIMIZAR
    String observacion;

    public NodoMultiClass nodoObservacion = new NodoMultiClass();
   
    //Variable con el nombre del reporte
    String nombreReporte = "";

    public String getReporteSeleccionado() {
        return reporteSeleccionado;
    }

    public void setReporteSeleccionado(String reporteSeleccionado) {
        this.reporteSeleccionado = reporteSeleccionado;
    }

    public String getNombreCompletoProfesor() {
        return nombreCompletoProfesor;
    }

    public void setNombreCompletoProfesor(String nombreCompletoProfesor) {
        this.nombreCompletoProfesor = nombreCompletoProfesor;
    }

    public String getCicloEscolarSeleccionado() {
        return cicloEscolarSeleccionado;
    }

    public void setCicloEscolarSeleccionado(String cicloEscolarSeleccionado) {
        this.cicloEscolarSeleccionado = cicloEscolarSeleccionado;
    }

    public List<Cicloescolar> getListaCiclosEscolares() {
        listaCiclosEscolares = null;
        listaCiclosEscolares = ractBeanHelper.getAllCiclos();
        for (int x = 0; x < listaCiclosEscolares.size(); x++) {
            if (listaCiclosEscolares.get(x).getCESid().toString().equalsIgnoreCase(ractBeanHelper.cicloescolarActual().getCESid().toString())) {
                listaCiclosEscolares.remove(x);
            }
        }
        return listaCiclosEscolares;
    }

    public void setListaCiclosEscolares(List<Cicloescolar> listaCiclosEscolares) {
        this.listaCiclosEscolares = listaCiclosEscolares;
    }

    /*public List<Cicloescolar> ciclosEscolaresPorUA() {
        listaCiclosEscolares = ractBeanHelper.getCiclosEscolaresporUAProid(profesor.getProid().toString(), unidadAprendizajeSeleccionada);
        return listaCiclosEscolares;
    }*/
    public Boolean getDisable3() {
        return disable3;
    }

    public void setDisable3(Boolean disable3) {
        this.disable3 = disable3;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    //-------------------------------ATRIBUTOS----------------------------------------//
    //-------------------------------sets/gets----------------------------------------//
    public int getUnidadAprendizajeSeleccionada() {
        return unidadAprendizajeSeleccionada;
    }

    /**
     * este metodo es solo un set de la variable de unidadAprendizaje
     * seleccionada pero en se validan algunas cosas cada que cambia la unidad
     *
     * @param seleccionado
     */
    public void setUnidadAprendizajeSeleccionada(int seleccionado) {
        // if (!unidadAprendizajeSeleccionada.equals(seleccionado) && !seleccionado.equals("00")) {
        unidadAprendizajeSeleccionada = seleccionado;
        if (unidadAprendizajeSeleccionada == 0) {
            //    unidadAprendizajeSeleccionada = seleccionado;
            //  int idUnidadAprendizajeSeleccionada = Integer.parseInt(unidadAprendizajeSeleccionada);
            int idUnidadAprendizajeSeleccionada = seleccionado;
            pintar = true;
            auxSelectedNodes = false;
            System.out.println("@@@QUITANDO BLOQUEO DE SELECTEDNODES");
        } else {
            pintar = false;
            if (selectedNodes != null) {
            }
            System.out.println("@@@SE CONSERVA BLOQUEO DE NODOS SELECCIONADOS");
        }
//
        //if (seleccionado.equals("00")) {
        if (unidadAprendizajeSeleccionada == 0) {
            root = new DefaultTreeNode();
            selectedNodes = null;
            claveUnidadAprendizajeSeleccionada = "0";
            this.unidadAprendizajeSeleccionada = seleccionado;
        }
        setEnable2(true);
    }

    public String getClaveUnidadAprendizajeSeleccionada() {
        return claveUnidadAprendizajeSeleccionada;
    }

    public void setClaveUnidadAprendizajeSeleccionada(String claveUnidadAprendizajeSeleccionada) {
        this.claveUnidadAprendizajeSeleccionada = claveUnidadAprendizajeSeleccionada;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void setEsCoordinador(boolean esCoordinador) {
        this.esCoordinador = esCoordinador;
    }

    public boolean getEsCoordinador() {
        return esCoordinador;
    }

    public String getCicloEscolar() {
        return cicloEscolar;
    }

    public void setCicloEscolar(String cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public void setBotones(Boolean botones) {
        this.botones = botones;
    }

    public List<NodoMultiClass> getFilaTabla() {
        return filaTabla;
    }

    public void setFilaTabla(List<NodoMultiClass> filaTabla) {
        this.filaTabla = filaTabla;
    }

    public void setExcesoDeReportes(boolean excesoDeReportes) {
        this.excesoDeReportes = excesoDeReportes;
    }

    public void setNumeroReporte(String numeroReporte) {
        this.numeroReporte = numeroReporte;
    }

    public float getPorcentajeAvance() {
        System.out.println("CONSIGUIENDO PORCENTAJE AVANCE");
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(float porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
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

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        System.out.println("SET SELECTED NODES TRUE");

        this.selectedNodes = selectedNodes;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
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
            porcentajeAvance = 0;
        }
    }

    public String getProgramaEducativoSeleccionado() {
        return programaEducativoSeleccionado;
    }

    public List<Programaeducativo> getProgramasEducativos() {
        return programasEducativos;
    }

    public void setProgramasEducativos(List<Programaeducativo> programasEducativos) {
        this.programasEducativos = programasEducativos;
    }

    public List<Areaconocimiento> getAreasConocimiento() {
        return areasConocimiento;
    }

    public void setAreasConocimiento(List<Areaconocimiento> areasConocimiento) {
        this.areasConocimiento = areasConocimiento;
    }

    public String getNombreUnidadAprendizaje() {
        return nombreUnidadAprendizaje;
    }

    public void setNombreUnidadAprendizaje(String nombreUnidadAprendizaje) {
        this.nombreUnidadAprendizaje = nombreUnidadAprendizaje;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void setUnidadesaprendizaje(List<Unidadaprendizaje> unidadesaprendizaje) {
        this.unidadesaprendizaje = unidadesaprendizaje;
    }

    public List<Temaunidad> getTemas() {
        return temas;
    }

    public void setTemas(List<Temaunidad> temas) {
        this.temas = temas;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public List<Unidadaprendizaje> getUnidadesaprendizaje() {
        unidadesaprendizaje = ractBeanHelper.getUnidadesaprendizajeConGrupos(programaEducativoSeleccionado);
        return unidadesaprendizaje;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    public String getNumeroReporte() {
        return "Reporte #" + numeroReporte;
    }

    public NodoMultiClass getNodoObservacion() {
        return nodoObservacion;
    }

    public void setNodoObservacion(NodoMultiClass nodoObservacion) {
        this.nodoObservacion = nodoObservacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisable2() {
        return disable2;
    }

    //Seccion encargada renderizar o no boton enviar
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

    public void setDisable2(boolean disable2) {
        this.disable2 = disable2;
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

    public void setEnable2(boolean enable2) {
        if (unidadAprendizajeSeleccionada == 0) {
            enable2 = true;
        } else {
            enable2 = false;
        }

        disable3 = false;
    }

    //-------------------------------sets/gets----------------------------------------//
    //-------------------------------CONSTRUCTORES------------------------------------//
    public RACTBeanUI() {
        root = new DefaultTreeNode();
        unidadesProfesor = new ArrayList();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = sdf.format(date);
        programasEducativos = new ArrayList();

        //Obtener CicloEscolarActual
        Cicloescolar auxCicloEscolar;
        auxCicloEscolar = ractBeanHelper.cicloescolarActual();
        cicloEscolar = "N/A";
        if (auxCicloEscolar == null) {
            System.out.println("CICLO ESCOLAR NULO");
        } else {
            cicloEscolar = auxCicloEscolar.getCEScicloEscolar();
        }
        //Obteniendo CicloEscolarActual

        numeroReporte = ractBeanHelper.obtenerReporteEnFecha();
        reporteActual = "Reporte #" + numeroReporte;

        ractBeanHelper.obtenerFechas();

        System.out.println("CREANDO BEANUI ");
    }
   
    //AQUI SE MODIFICO PARA DETENER QUE EL PROGRAMA TRUENE
    @PostConstruct
    public void postConstructor() {
        for(int x=0;x<10000;x++){
            System.out.println(x);
        }
        if (numeroReporte.equals("0") || numeroReporte == null) {
            // se e agrega un mensaje de tipo sticky para que el profesor
            // tenga que cerrarlo, es el mensaje de que no hay fechas
            FacesContext.getCurrentInstance().addMessage("growlSticky", new FacesMessage(FacesMessage.SEVERITY_INFO, "Fuera de fechas de envío", "Verifique las fechas para realizar reportes en la página de inicio, dentro de la sección de anuncios del sistema"));
            PrimeFaces.current().ajax().update("growlSticky");
            disablePE = true;
        }
        if ( (loginBean == null && loginBean.getLogueado() != null ) /*|| loginBean.getLogueado().getProfesorList().size() <1*/ ) {
            System.out.println("No hay loginbean");
            profesor = new Profesor();
        } else {
            if(loginBean.getLogueado().getProfesorList() == null){
                profesor = null;
            }
            else{
               if(loginBean.getLogueado().getProfesorList().get(0) != null)
               {
                profesor = loginBean.getLogueado().getProfesorList().get(0);
               }
            }
            if (profesor == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            } else {
                nombreCompletoProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
                nombreProfesor = profesor.getPROnombre();
                ractBeanHelper.setProfesor(profesor);
            }
        }

        if (profesor == null || profesor.getPROid() == null) {
            System.out.println("//////////////////////////////////////////////PROFESOR ES NULL");
        }else{
           loginBean.getLogueado().getProfesorList().get(0);
           programasEducativos = ractBeanHelper.programasEducativosProfesor(profesor, cicloEscolarActual);
        }
        if (reporteActual.equalsIgnoreCase("Reporte #0")) {
            reporteActual = "Reporte #1";
        }
        unidadImpartida = new UnidadaprendizajeImparteProfesor();
        tipoGrupo = "z";
    }

   
   
   
   
   
   
    //-------------------------------CONSTRUCTORES------------------------------------//
    //--------------------------------METODOS----------------------------------------//
    public Boolean isBotones() {
        if (programaEducativoSeleccionado.equals("0")) {
            botones = false;
        } else {
            botones = true;
        }
        return botones;
    }

    public void refrescarForma(int num) {
        archivoActual = num;
        List<Rol> list;
        //list= loginBean.getLogueado().getRolList();
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        String catalogo = "Actualizar porcentaje de contenido temático";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        System.out.println("ejecucion del metodo:");
    }

    public boolean isExcesoDeReportes() {
        return excesoDeReportes;
    }

    TreeNode[] selectedNodesInitial = new DefaultTreeNode[10];

    /**
     * MEtodo para obtener la entidad completa de unidad aprendizajeImparteProfe
     * para adaptarlos a los metodos de siract 2.0
     *
     * @param idUnidad
     * @return
     */
    public UnidadaprendizajeImparteProfesor ObtenerUnidadAprendizajeImparteProfesor(int idUnidad) {
        UnidadaprendizajeImparteProfesor unidadImparte = new UnidadaprendizajeImparteProfesor();
        List<UnidadaprendizajeImparteProfesor> lista = ractBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));

        for (UnidadaprendizajeImparteProfesor uiap : lista) {
            if (uiap.getUIPid() == idUnidad) {
                unidadImparte = uiap;
                break;
            }
        }

        return unidadImparte;
    }

    /**
     * Metodo para llener el arbol segun la unidad de aprendizaje que se haya
     * seleccionado
     */
    public void fillTree() {
        UnidadaprendizajeImparteProfesor unidadImparte;
        unidadImparte = ObtenerUnidadAprendizajeImparteProfesor(unidadAprendizajeSeleccionada);
        tipoGrupo = unidadImparte.getUIPtipoSubgrupo();
        selectedNodes = null;
        auxSelectedNodes = false;
        System.out.println("RACTBeanUI.java:fillTree(): reporteSeleccionado = " + reporteSeleccionado);

        if (reporteSeleccionado.equalsIgnoreCase("00") || reporteSeleccionado.equalsIgnoreCase("null")) {
            reporteSeleccionado = numeroReporte;
        }

        if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
            cicloEscolarSeleccionado = cicloEscolarActual;
        }

        root = ractBeanHelper.TraerNodosTree(unidadImparte, unidadImparte.getUnidadAprendizajeUAPid(), reporteSeleccionado, String.valueOf(unidadImparte.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().size()));
        pintar = false;

        //PONER NODOS SELECCIONADOS
        if (auxSelectedNodes) {
            System.out.println("NO RECALCULAR SELECCIONES");
        } else {
            System.out.println("SELECCIONANDO NODOS DE BASE DE DATOS ");
            List<TreeNode> auxSelected = ractBeanHelper.getListaSeleccionados();
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
        filaTabla = ractBeanHelper.getListaPDF();

        //El nombre con el que se dara al PDF impreso
        // for (Unidadaprendizaje ua : profesor.getUnidadaprendizajeImparteProfesorList()) {
        // if (claveUnidadAprendizajeSeleccionada.equals(String.valueOf(ua.getUAPclave()))) {
        System.out.println("CON CLAVE-------------: " + profesor.getPROnombre() + " -- " + unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre());
        /*Se le asigna el nombre el reporte */
        nombreReporte = "RACT " + numeroReporte + " -- " + profesor.getPROnumeroEmpleado() + " " + profesor.getPROnombre() + " -- " + unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre() + " -- " + unidadImparte.getGrupoGPOid().getGPOnumero() + " -- " + unidadImparte.getUIPsubgrupo() + " -- " + unidadImparte.getUIPtipoSubgrupo();

        if (archivoActual == 1) {
            calculo();
        }

        //Validar si el RACT de esta unidad de aprendizaje ya ha sido enviado
        if (archivoActual == 1) {
            validarEnviado();
        }
    }

    public void setDeshabilitar(boolean deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public boolean getDeshabilitar() {
        return deshabilitar;
    }

    /**
     * Metodo para deshabilitar los nodos que ya han sido enviados en reportes
     * anteriores
     */
    public void deshabilitarEnviados() {
        //Deshabilitando nodos que ya han sido enviados
        selectedNodesInitial = new TreeNode[selectedNodes.length];

        for (int i = 0; i < selectedNodes.length; i++) {
            selectedNodesInitial[i] = selectedNodes[i];
        }

        for (TreeNode nodo : root.getChildren()) {
            try {
                for (TreeNode seleccionado : selectedNodesInitial) {

                    //Posible linea del error
                    if (((NodoMultiClass) seleccionado.getData()).getTipo().equals(((NodoMultiClass) nodo.getData()).getTipo())
                            && ((NodoMultiClass) seleccionado.getData()).getId().equals(((NodoMultiClass) nodo.getData()).getId())) {
                        nodo.setSelectable(false);
                        nodo.setSelected(true);
                    }

                }

                for (TreeNode nodo2 : nodo.getChildren()) {
                    for (TreeNode seleccionado : selectedNodesInitial) {
                        if (((NodoMultiClass) seleccionado.getData()).getTipo().equals(((NodoMultiClass) nodo2.getData()).getTipo()) && ((NodoMultiClass) seleccionado.getData()).getId().equals(((NodoMultiClass) nodo2.getData()).getId())) {
                            nodo2.setSelectable(false);
                            nodo2.setSelected(true);
                        }
                    }

                    for (TreeNode nodo3 : nodo2.getChildren()) {
                        for (TreeNode seleccionado : selectedNodesInitial) {
                            if (((NodoMultiClass) seleccionado.getData()).getTipo().equals(((NodoMultiClass) nodo3.getData()).getTipo()) && ((NodoMultiClass) seleccionado.getData()).getId().equals(((NodoMultiClass) nodo3.getData()).getId())) {
                                nodo3.setSelectable(false);
                                nodo3.setSelected(true);
                            }
                        }

                    }
                }
            } catch (NullPointerException e) {
                System.out.println("ERROR POR **************" + e);
            }
        }
    }

    public TreeNode pintarArbol() {
        return root;
    }

    public Unidadaprendizaje obtenerUnidadesA(UnidadaprendizajeImparteProfesor p) {
        // unidades = new ArrayList(p.getUnidadaprendizaje().getUnidads());
        unidades = p.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().get(p.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().size() - 1).getUnidadList();
        if (claveUnidadAprendizajeSeleccionada.equals("0")) {
            claveUnidadAprendizajeSeleccionada = p.getUnidadAprendizajeUAPid().getUAPid().toString();
        }
        nombreUnidadAprendizaje = p.getUnidadAprendizajeUAPid().getUAPnombre();

        return p.getUnidadAprendizajeUAPid();
    }

    /**
     * Metodo para manejar evento de primefaces
     *
     * @param event
     */
    public void onNodeCollapse(NodeCollapseEvent event) {
        if (event != null && event.getTreeNode() != null) {
            event.getTreeNode().setExpanded(false);
        }
    }

    /**
     * Metodo para manejar evento cuando un nodo es seleccionado en la vista
     *
     * @param event
     */
    public void onNodeSelect(NodeSelectEvent event) {
        System.out.println("SELECCIONADO");
        System.err.println("nodosseleccionado:" + nodosSeleccionados + "nummero de nodos " + numeroNodosSeleccionados);

        calculo();
        disable2 = false;

        if (enviado) {
            disable2 = enviado;
        }

        if (event != null && event.getTreeNode() != null) {
            event.getTreeNode().setSelected(true);
        }

        btnEnviarRender();

    }

    /**
     * Metodo para manejar evento cuando un nodo es seleccionado en la vista
     *
     * @param event
     */
    public void onNodeUnselect(NodeUnselectEvent event) {
        System.out.println("DESSELECCIONADO");
        calcularNumeroDeSeleccionados();
        calculo();
        btnEnviarRender();
    }

    public void guardarReporte(boolean generarpdf) {
        UnidadaprendizajeImparteProfesor unidadImpar;
        unidadImpar = ObtenerUnidadAprendizajeImparteProfesor(unidadAprendizajeSeleccionada);

        /**
         * se agrego un ciclo que enviara los nodos a con algun comentario al
         * metodo de guardar comentarios en para actualizar si esta seleccionado
         * o no en caso que haya sido cambiado
         */
        for (TreeNode unidad : root.getChildren()) {

            // se convierte en array para verificar que tenga comentario
            String[] datos = unidad.getData().toString().split("-//-");

            if (!datos[6].equalsIgnoreCase(" ")) {
                ractBeanHelper.guardarComentario(unidad);
            }

            for (TreeNode tema : unidad.getChildren()) {

                // se convierte en array para verificar que tenga comentario
                datos = unidad.getData().toString().split("-//-");

                if (!datos[6].equalsIgnoreCase(" ")) {
                    ractBeanHelper.guardarComentario(tema);
                }

                // for para los subtemas
                for (TreeNode subtema : tema.getChildren()) {
                    // se convierte en array para verificar que tenga comentario
                    datos = unidad.getData().toString().split("-//-");

                    if (!datos[6].equalsIgnoreCase(" ")) {
                        ractBeanHelper.guardarComentario(subtema);
                    }

                }
            }
        }

        pintar = true;
        auxSelectedNodes = true;
        if (selectedNodes == null || selectedNodes.length < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte no guardado", "Seleccione al menos una unidad"));
        } else {
            excesoDeReportes = !ractBeanHelper.guardarReporteAvance(unidadImpar, reporteSeleccionado, selectedNodes, porcentajeAvance, profesor.getPROid(), Integer.parseInt(grupo), subGrupo, tipoUnidadAprendizaje, Integer.parseInt(claveUnidadAprendizajeSeleccionada), false);
            if (excesoDeReportes) {
            }
            if (ractBeanHelper.isTransaccionCorrecta() && generarpdf == true) {
                PrimeFaces.current().executeScript("pdf();");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte guardado", "El reporte ha sido guardado"));
            } else if (ractBeanHelper.isTransaccionCorrecta() && generarpdf == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte guardado", "El reporte ha sido guardado"));
            }
            //deshabilitarEnviados();
            validarEnviado();
            fillTree();
        }
    }

    public void enviarReporte() {
        UnidadaprendizajeImparteProfesor unidadImparte;
        unidadImparte = ObtenerUnidadAprendizajeImparteProfesor(unidadAprendizajeSeleccionada);

        /**
         * se agrego un ciclo que enviara los nodos a con algun comentario al
         * metodo de guardar comentarios en para actualizar si esta seleccionado
         * o no en caso que haya sido cambiado
         */
        disableBtnPDF = false;
        PrimeFaces.current().ajax().update("formId:imprimir");

        for (TreeNode unidad : root.getChildren()) {

            // se convierte en array para verificar que tenga comentario
            String[] datos = unidad.getData().toString().split("-//-");

            if (!datos[6].equalsIgnoreCase(" ")) {
                ractBeanHelper.guardarComentario(unidad);
            }

            for (TreeNode tema : unidad.getChildren()) {

                // se convierte en array para verificar que tenga comentario
                datos = tema.getData().toString().split("-//-");

                if (!datos[6].equalsIgnoreCase(" ")) {
                    ractBeanHelper.guardarComentario(tema);
                }

                // for para los subtemas
                for (TreeNode subtema : tema.getChildren()) {
                    // se convierte en array para verificar que tenga comentario
                    datos = subtema.getData().toString().split("-//-");

                    if (!datos[6].equalsIgnoreCase(" ")) {
                        ractBeanHelper.guardarComentario(subtema);
                    }

                }
            }
        }
        // Termina el ciclo para enviar comentarios

        pintar = true;
        auxSelectedNodes = true;
        System.out.println("Metodo de enviar reporte ha sido activado");
        if (selectedNodes == null || selectedNodes.length < 1) {
            disableBtnPDF = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte no enviado", "Seleccione al menos una unidad"));
        } else {
            disableBtnPDF = false;
            excesoDeReportes = !ractBeanHelper.guardarReporteAvance(unidadImparte, reporteSeleccionado, selectedNodes, porcentajeAvance, profesor.getPROid(), Integer.parseInt(grupo), subGrupo, tipoUnidadAprendizaje, Integer.parseInt(claveUnidadAprendizajeSeleccionada), true);
            System.out.println(excesoDeReportes);
            if (excesoDeReportes) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte no enviado", "Ya se han enviado los reportes correspondientes"));
            } else {
                fillTree();
            }
            if (ractBeanHelper.isTransaccionCorrecta()) {
                PrimeFaces.current().executeScript("pdf();");
            }
            deshabilitarEnviados();
        }
        setEnable2(true);
    }

    /**
     * Sumara los porcentajes de los nodos seleccionados
     *
     * @param nodo
     */
    public void sumarNodoaPorcentaje(TreeNode nodo) {
        if (nodo.isSelected()) {
            porcentajeAvance += Float.parseFloat(((NodoMultiClass) nodo.getData()).getHoras()) * 100 / ((NodoCT) root.getData()).getHoras();
        } else {
            for (TreeNode node : nodo.getChildren()) {
                sumarNodoaPorcentaje(node);
            }
        }
    }

    /**
     * Calcula el porcentaje de avance de acuerdo a los seleccionados en
     * reportes anteriores
     */
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
        PrimeFaces.current().executeScript("avance(" + porcentajeAvance + ")");
    }

    public void cambio(TreeNode[] nodes) {
        if (nodes != null && nodes.length > 0) {
            for (TreeNode node : nodes) {
                String[] todo = node.getData().toString().split("-//-");
                porcentajeAvance += Float.parseFloat(todo[5]);
            }
        }
    }

    public void ponerObservacion(String numero) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        observacion = request.getParameter(":formId:arbol:tabla");
        //PARA USAR OBSERVACIONES DE PRUEBA
        observacion = request.getParameter("formId:observacion");
    }

    /**
     * Metodo para verificar si un observacion esta vacia o tiene una y asi
     * colocar el icono en el boton segun sea el caso
     *
     * @param nodo
     * @return
     */
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

    public void prepararObservacion(NodoMultiClass nodoMultiClass) {
        setNodoObservacion(nodoMultiClass);
        verObservacion();
    }

    /**
     * Metodo para traer la observacion segun el nodo que se selecciono
     */
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
                    observacionAnterior = ((NodoMultiClass) nod.getData()).isDeReporteAnterior();

                    // validacion para deshabilitar el boton de guardar en el comentario si el ract actual ya ha sido enviado
                    if (((NodoMultiClass) nod.getData()).isDeReporteAnterior() || disableComentario || !nod.isSelectable()) {
                        observacionAnterior = true;
                    } else {
                        observacionAnterior = false;
                    }
                    //  System.out.println("################## Observacion anterior: " + observacionAnterior + " isDeReporteAnterior " + ((NodoMultiClass) nod.getData()).isDeReporteAnterior() + " Disable2" + disable2 + " disableComentaro:  " + disableComentario) ;
                }
            } else {
                for (TreeNode nod2 : nod.getChildren()) {
                    if (tipo.equals("temaunidad")) {
                        if (nod2.getData().toString().split("-//-")[2].equals(tema)) {
                            observacion = ((NodoMultiClass) nod2.getData()).getObservaciones();
                            observacionAnterior = ((NodoMultiClass) nod2.getData()).isDeReporteAnterior();
                            // validacion para deshabilitar el boton de guardar en el comentario si el ract actual ya ha sido enviado
                            if (((NodoMultiClass) nod.getData()).isDeReporteAnterior() || disableComentario || !nod2.isSelectable()) {
                                observacionAnterior = true;
                            } else {
                                observacionAnterior = false;
                            }
                        }
                    } else {
                        for (TreeNode nod3 : nod2.getChildren()) {
                            if (tipo.equals("subtemaunidad")) {
                                if (nod3.getData().toString().split("-//-")[3].equals(subtema)) {
                                    observacion = ((NodoMultiClass) nod3.getData()).getObservaciones();
                                    observacionAnterior = ((NodoMultiClass) nod3.getData()).isDeReporteAnterior();
                                    // validacion para deshabilitar el boton de guardar en el comentario si el ract actual ya ha sido enviado
                                    if (((NodoMultiClass) nod.getData()).isDeReporteAnterior() || disableComentario || !nod3.isSelectable()) {
                                        observacionAnterior = true;
                                    } else {
                                        observacionAnterior = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        setObservacionAnterior(observacionAnterior); //Atributo para deshabilitar el botón de guardado en temas de reportes anteriores
        observacion = observacion.trim();
    }

    public void agregarObservacion() {
        pintar = false;
        NodoMultiClass nodo = nodoObservacion;

        if (observacion.trim().isEmpty()) {
            //System.err.println("##################### entre en validacion de vacio bean ui");
            numeroObservacionesActuales--;
        } else {
            numeroObservacionesActuales++;
        }
        if (observacion != null) {
            nodo.setObservaciones(observacion);

            String tipo = nodo.toString().split("-//-")[0];
            String unidad = nodo.toString().split("-//-")[1];
            String tema = nodo.toString().split("-//-")[2];
            String subtema = nodo.toString().split("-//-")[3];
            if (tema == null) {
                tema = " ";
            }
            if (subtema == null) {
                subtema = " ";
            }
            for (TreeNode nod : root.getChildren()) {
                if (tipo.equals("unidad") || tipo.equals("practicalaboratorio") || tipo.equals("practicataller")) {
                    if (nod.getData().toString().split("-//-")[1].equals(unidad)) {
                        ((NodoMultiClass) nod.getData()).setObservaciones(observacion);
                        ((NodoMultiClass) nod.getData()).setImpartido(nod.isSelected());

                        ractBeanHelper.guardarComentario(nod);
                    }
                } else {
                    List<TreeNode> nodos2 = nod.getChildren();
                    if (!nodos2.isEmpty() && nodos2 != null) {
                        for (TreeNode nod2 : nodos2) {
                            if (tipo.equals("temaunidad")) {
                                if (nod2.getData().toString().split("-//-")[2].equals(tema)) {
                                    ((NodoMultiClass) nod2.getData()).setObservaciones(observacion);
                                    ((NodoMultiClass) nod2.getData()).setImpartido(nod2.isSelected());
                                    ractBeanHelper.guardarComentario(nod2);
                                }
                            } else {
                                for (TreeNode nod3 : nod2.getChildren()) {
                                    if (tipo.equals("subtemaunidad")) {
                                        if (nod3.getData().toString().split("-//-")[3].equals(subtema)) {
                                            ((NodoMultiClass) nod3.getData()).setObservaciones(observacion);
                                            ((NodoMultiClass) nod3.getData()).setImpartido(nod3.isSelected());
                                            ractBeanHelper.guardarComentario(nod3);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        //Volver true la bandera de actualizacion para el arbol de contenido tematico
        calcularNumeroDeSeleccionados();
    }

    //CREAR DE PDF
    //Es necesario el JAR de PDFBox 1.8.6
    public List<NodoMultiClass> crearPDF() {
        System.out.println("CREANDO PDF");
        return ractBeanHelper.getListaPDF();
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {
        final Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
    }

    //Impresion de archivo PDF
    public void postProcessPDF(Object document) throws IOException, DocumentException {
        final Document pdf = (Document) document;
        // cambie el array por una lista para poder agregar unidades no seleccionadas pero comentadas en pdf
        List<TreeNode> selectedNodesPDF = new ArrayList<>();

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
                //   System.out.println("*********************" + ((NodoMultiClass) nodo.getData()).getNombre());
            }
        }
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
        String nombrep = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
        String numEmpPro = Integer.toString(profesor.getPROnumeroEmpleado());
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
        UnidadaprendizajeImparteProfesor unidadImparte;
        unidadImparte = ObtenerUnidadAprendizajeImparteProfesor(unidadAprendizajeSeleccionada);
        int uniAprselec2 = unidadImparte.getUnidadAprendizajeUAPid().getUAPid();
        nombreUniApr = unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre();
        nombreclave = Integer.toString(unidadImparte.getUnidadAprendizajeUAPid().getUAPclave());
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

        pdfTabletitulo2.addCell(new Paragraph("Num. de Empleado: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
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
        pdftablecabezera.addCell(new Paragraph(formato.format(new Date())));
        pdftablecabezera.addCell(new Paragraph("        " + cicloEscolar));
        pdftablecabezera.addCell(new Paragraph("        " + porAv + " %"));

        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));
        pdftablecabezera.addCell(new Paragraph(" "));

        pdftablecabezera2.addCell(new Paragraph("Unidad de aprendizaje: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Clave: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Grupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Subgrupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph("Tipo grupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph(unidadImparte.getUnidadAprendizajeUAPid().getUAPnombre()));
        pdftablecabezera2.addCell(new Paragraph(" " + unidadImparte.getUnidadAprendizajeUAPid().getUAPclave()));
        pdftablecabezera2.addCell(new Paragraph(" " + unidadImparte.getGrupoGPOid().getGPOnumero()));
        pdftablecabezera2.addCell(new Paragraph("       " + unidadImparte.getUIPsubgrupo()));
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
                tipoGrupo = "Practicas clinica";
                break;
        }
        pdftablecabezera2.addCell(new Paragraph("    " + tipoGrupo));
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
        Paragraph numrep = new Paragraph("Reporte # " + numeroReporte, FontFactory.getFont(FontFactory.TIMES, 16, Font.BOLD, new Color(0, 0, 0)));
        numrep.setAlignment(Element.ALIGN_CENTER);
        pdf.add(numrep);
        pdf.add(new Paragraph(" "));
        //Tabla con Datos
        PdfPTable pdfTable = new PdfPTable(3);
        pdfTable.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTable.addCell(new Phrase("Porcentaje", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));
        pdfTable.addCell(new Phrase("Observaciones", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(0, 0, 0))));

        //Evitar Null pointer exception por no tener nodoes en selectedNodes
        //Se utiliza un auxiliar de selectedNodes por que hay posibilidad de que selectedNodes se modifique durante el proceso de generacion del PDF
        if (selectedNodesPDF != null && selectedNodesPDF.size() > 0) {

            //Regresar a lo basicoString
            int numeroDeNodos = selectedNodesPDF.size();
            String[] nodosSeleccionados = new String[numeroDeNodos];
            //--- anteriormente se recorria solo los nodos seleccionados se cambio para imprimir mensajes en unidades no completadas

            for (int x = 0; x < selectedNodesPDF.size(); x++) {

                System.out.println(((NodoMultiClass) selectedNodesPDF.get(x).getData()).getObservaciones());
                nodosSeleccionados[x] = ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getNumero() + "--" + ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getNombre() + "--" + ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getPorcentajeAvance() + "--" + ((NodoMultiClass) selectedNodesPDF.get(x).getData()).getObservaciones() + " -- ";

            }
            String[] filas;
            String[] nstr;

            String[] filas2;
            String[] nstr2;

            for (int x = 1; x < nodosSeleccionados.length; x++) {
                for (int y = 0; y < nodosSeleccionados.length - x; y++) {
                    //Obtengo los valores para hacer las comparaciones
                    int[] n = new int[3];
                    int[] n2 = new int[3];
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

            String[] auxNum = new String[4];
            for (int x = 0; x < nodosSeleccionados.length; x++) {

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
                            auxNum2 = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");

                            if (num[0].equals(auxNum2[0])) {

                            } else {
                                auxMargen--;
                            }

                        }

                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData());
                        pdfTable.addCell(new Phrase(auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());

                    }
                } else {
                    if (auxNum2[0] == null && num.length >= 2) {

                        auxNum2 = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");
                        while (!num[0].equals(auxNum2[0]) && auxMargen > 0) {
                            auxNum2 = (((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData()).getNumero()).split("\\.");

                            if (num[0].equals(auxNum2[0])) {

                            } else {
                                auxMargen--;
                            }
                        }

                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getData());
                        pdfTable.addCell(new Phrase(auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());

                    }
                }

                //AGREGAR A SUBTEMA
                String[] auxNumeroUnidad;
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

                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData());
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

                        NodoMultiClass auxNodo = ((NodoMultiClass) root.getChildren().get(Integer.parseInt(num[0]) - auxMargen).getChildren().get(Integer.parseInt(num[1]) - auxMargen2).getData());

                        pdfTable.addCell(new Phrase("   " + auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(auxNodo.getPorcentajeAvance(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());
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
                    }
                    /**
                     * si es un subtema lo pondre en italica y gris
                     */
                    if (StringNode[0].equalsIgnoreCase("subtemaunidad")) {
                        NodoMultiClass auxNodo = ((NodoMultiClass) selectedNodesPDF.get(x).getData());
                        pdfTable.addCell(new Phrase("       " + auxNodo.getNumero() + ".- " + auxNodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, new Color(154, 151, 151))));
                        pdfTable.addCell(new Phrase(String.valueOf(dosDecimales(Float.parseFloat(auxNodo.getPorcentajeAvance()))), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12, Font.NORMAL, new Color(154, 151, 151))));
                        pdfTable.addCell(auxNodo.getObservaciones());
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
                    } else {
                        pdfTable.addCell(filas[3]);
                    }
                }
            }
        } else {
            pdfTable.addCell("No hay nada seleccionado");
            pdfTable.addCell("");
            pdfTable.addCell("");
        }

        pdfTable.setHorizontalAlignment(15);
        float[] columnWidths2 = new float[]{32f, 8f, 14f};
        pdfTable.setWidths(columnWidths2);
        pdf.add(pdfTable);
        //----------------------------------------------------------------------
    }

    //activar y desactivar el boton
    public boolean isDisable() {
        return disable;
    }

    public void onSelectREPORTE() {
        fillTree();
    }

    public void onSelectUA() {
        numeroObservacionesActuales = 0;
        setEnable2(false);
        setEnable2(true);
        calcularNumeroDeSeleccionados();
        //Se agrego este IF para que permita enviar el RACT aun que ya se hayan enviado todas las unidades en algun RACT anterior
        if (numeroReporte.equalsIgnoreCase("3") || numeroReporte.equalsIgnoreCase("2")) {
            disable2 = false;
        }

        fillTree();

        System.out.println("####id UnidadAprendizajeImparteProfesor seleccionado:" + unidadAprendizajeSeleccionada);
    }

    /**
     * Metodo para calcular el numero de nodos seleccionados de este reporte o
     * de reportes anteriores para habilitar o deshabilitar los botones de
     * guardar y guardar y enviar segun sea el caso
     */
    public void calcularNumeroDeSeleccionados() {
        nodosSeleccionados = 0;
        int nodosGuardadosReporteActual = 0;
        numeroNodosSeleccionados = 0;
        int NodosConComentario = 0;
        // for para unidad,taller,laboratorio o practicas campo
        for (TreeNode unidad : root.getChildren()) {
            // verifico si el nodo esta selecciado pero es de reporte anterior
            if (unidad.isSelected() && !unidad.isSelectable()) {

                nodosSeleccionados++;
            }

            // verifico los nodos que no estan selecciados pero tiene un comentario
            // para habilitar el envio aun y cuando no se selcciono pero se
            // se hizo el comentario
            if (!unidad.getData().toString().split("-//-")[6].trim().isEmpty() && unidad.isSelectable()) {

                NodosConComentario++;
            }
            // verifico si esta seleccionado en el reporte actual
            if (unidad.isSelected()) {
                nodosGuardadosReporteActual++;
            }
            // hare lo mismo pero para temas
            for (TreeNode tema : unidad.getChildren()) {
                if (tema.isSelected() && !tema.isSelectable()) {
                    nodosSeleccionados++;
                }
                if (tema.isSelected()) {
                    nodosGuardadosReporteActual++;
                }
                if (!tema.getData().toString().split("-//-")[6].trim().isEmpty() && tema.isSelectable()) {

                    NodosConComentario++;
                }

                // hare lo mismo para subtemas
                for (TreeNode subtema : tema.getChildren()) {
                    if (subtema.isSelected() && !subtema.isSelectable()) {
                        nodosSeleccionados++;
                    }

                    if (subtema.isSelected()) {
                        nodosGuardadosReporteActual++;
                    }
                    if (!subtema.getData().toString().split("-//-")[6].trim().isEmpty() && subtema.isSelectable()) {

                        NodosConComentario++;
                    }

                }
            }
        }
        if (numeroReporte.equalsIgnoreCase("1")) {
            numeroNodosSeleccionados = nodosGuardadosReporteActual;
            if (numeroNodosSeleccionados == 0) {
                disable2 = true;
            } else {
                disable2 = false;
            }

        }
        if (!numeroReporte.equalsIgnoreCase("1")) {

            numeroNodosSeleccionados = nodosSeleccionados - nodosGuardadosReporteActual;
            // PUEDE QUE AQUI SEA EL BUG QUE NO DEJA ENVIAR SI SE ENVIO TODO en el segundo o primer ract
            if (((selectedNodes == null || nodosSeleccionados == numeroNodosSeleccionados || numeroNodosSeleccionados == 0 || enviado) && NodosConComentario == 0) || (numeroObservacionesActuales == 0 && (selectedNodes == null || nodosSeleccionados == numeroNodosSeleccionados || numeroNodosSeleccionados == 0 || enviado) && NodosConComentario == 0)) {
                disable2 = true;
            } else {
                disable2 = false;
            }
            if (enviado) {
                disable2 = true;
            }
        }
    }

//
    /**
     * Este metodo se activa al seleccionar el programa educativo traera las
     * unidades con su grupo segun el programa educativo que se selecciono
     */
    public void onSelectPE() {
        unidadesProfesor = ractBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));
        List<UnidadaprendizajeImparteProfesor> listaFiltrada = new ArrayList();
        boolean banderaIncompletos = true;
        /* Quitare las unidades de aprendizaje que no tienen contenido tematico*/
        for (UnidadaprendizajeImparteProfesor up : unidadesProfesor) {
            String tipoClase = up.getUIPtipoSubgrupo();
            switch (tipoClase) {
                case "C":
                    if (up.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasClaseCompletas()) {
                        listaFiltrada.add(up);
                    } else {
                        banderaIncompletos = false;
                    }
                    break;
                case "T":
                    if (up.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasTallerCompletas()) {
                        listaFiltrada.add(up);
                    } else {
                        banderaIncompletos = false;
                    }
                    break;
                case "L":
                    if (up.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasLaboratorioCompletas()) {
                        listaFiltrada.add(up);
                    } else {
                        banderaIncompletos = false;
                    }
                    break;
                case "PC":
                    if (up.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasCampoCompletas()) {
                        listaFiltrada.add(up);
                    } else {
                        banderaIncompletos = false;
                    }
                    break;

            }
            if (!banderaIncompletos) {
                FacesContext.getCurrentInstance().addMessage("growlSticky", new FacesMessage(FacesMessage.SEVERITY_INFO, "Unidades faltantes", "Algunas unidades que imparte en este programa educativo no tienen contenido temático registrado"));
            }
        }
        unidadesProfesor = listaFiltrada;
        disable = false;

        setEnable(false);
        disablePDF = false;
        calculo();

        unidadImparteProfesorSeleccionada = 0;
        /*asigno valor para que desaperezcan los iconos de seleccionado parcial
        y completo */
        tipoGrupo = "z";
        PrimeFaces.current().ajax().update("formId:message");
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

    public String horasAFormato(String horasString) {

        //Mover variables a globarl y simbolo.setDecimalSeparator a CONSTRUCTOR
        String formatoDeHora = "0:00";
        try {
            float horas = Float.parseFloat(horasString);
            DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
            simbolo.setDecimalSeparator('.');
            DecimalFormat formato = new DecimalFormat("##", simbolo);

            int valorEntero = (int) horas;
            float minutos = horas - valorEntero;
            System.out.println("A/C HORAS" + valorEntero + "Minutos:" + minutos);
            //Se convierten los decimales a minutos y se multiplica por 100 para poder despreciar la fraccion
            minutos = (float) (minutos * 30 / 0.5);
            System.out.println("D/C HORAS" + valorEntero + "Minutos:" + minutos);

            if (String.valueOf((int) minutos).length() < 2) {
                formatoDeHora = String.valueOf(valorEntero + ":0" + formato.format(minutos));
            } else {
                formatoDeHora = String.valueOf(valorEntero + ":" + formato.format(minutos));
            }
        } catch (Exception e) {

        }
        return formatoDeHora;
    }

    public boolean enviado = false;

    public void validarEnviado() {
        UnidadaprendizajeImparteProfesor unidadImparte;
        unidadImparte = ObtenerUnidadAprendizajeImparteProfesor(unidadAprendizajeSeleccionada);

        System.out.println("SE HA SELECCIONADO UNA UA TIPO" + tipoUnidadAprendizaje);
        System.out.println(profesor.getPROid() + " " + Integer.parseInt(grupo) + " " + subGrupo + " " + claveUnidadAprendizajeSeleccionada + " " + tipoUnidadAprendizaje);
        if (ractBeanHelper.validarReporteEnviado(unidadImparte)) {
            System.out.println("AGREGANDO MENSAJE DE ENVIADOS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            FacesContext.getCurrentInstance().addMessage("validarEnviado", new FacesMessage(FacesMessage.SEVERITY_WARN, "El reporte ya ha sido enviado", "El reporte ha sido enviado correctamente"));
            enviado = true;
            disable2 = true;
            disableComentario = true;
            disablePDF = true;
            PrimeFaces.current().ajax().update("formId:message");
        } else {
            enviado = false;
            disableComentario = false;
            disablePDF = false;
            System.out.println("NO AGREGAR MENSAJE DE ENVIADOS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            PrimeFaces.current().ajax().update("formId:message");
        }
        //Los nodos se deshabilitan si el reporte ya esta enviado
        if (enviado) {
            for (TreeNode nodo : root.getChildren()) {
                nodo.setSelectable(false);
                for (TreeNode nodo2 : nodo.getChildren()) {
                    nodo2.setSelectable(false);
                    for (TreeNode nodo3 : nodo2.getChildren()) {
                        nodo3.setSelectable(false);
                    }
                }
            }
        }

        btnEnviarRender();
    }

    public String selectableToImageConsultas(NodoMultiClass nodo) {
        if (nodo.isImpartido() && nodo.isDeReporteAnterior() == false) {
            System.out.println(nodo.isImpartido() + "///////////////////////////////////NODO IS IMPARTIDO");
            return "true";
        }
        return "false";

    }

    public String checkWarningImageConsultas(NodoMultiClass nodo) {
        if (nodo.getParcialSelected()) {
            return "false";
        } else {
            if ((nodo.getParcialSelected())) {
                return "false";
            }
            return "false";
        }
    }

    public String unselectOnEnviado() {
        if (enviado) {
            return "";
        } else if (!loginBean.getSeleccionado().equals("Profesor") && !loginBean.getSeleccionado().equals("Administrador") && !loginBean.getSeleccionado().equals("Responsable de Programa Educativo") && !loginBean.getSeleccionado().equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
            return "";
        } else {
            return "checkbox";
        }
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

    //returna none para usar en selectioMode
    public String selectableToImage(NodoMultiClass nodo) {
        if (nodo.isDeReporteAnterior() && !nodo.getParcialSelected()) {
            return "true";
        }

        return "false";
    }

    public String checkWarningImage(NodoMultiClass nodo) {

        if (nodo.getParcialSelected()) {
            return "true";
        }

        return "false";
    }

    public Boolean disableBtnImprimir() {

        if (enviado && unidadAprendizajeSeleccionada == 0) {
            return false;
        }

        return true;
    }

    /**
     * Con esta metodo regresare un propiedad de css para ocultar el boton de
     * generar pdf , si ya ha sido enviado el ract y se selecciono una unidad de
     * aprendizaje.
     *
     * no se deshabilito porque en el xhtml los botonoes de guardar y guardar y
     * enviar llaman a ese boton con un script y al deshabilitarlo causa
     * conflictos
     *
     * @return
     */
    public String rendered() {        
        if (unidadImparteProfesorSeleccionada == 0 && disablePDF) {
            return "visibility: visible;";
        }
       
        if (!disablePDF) {
            return "visibility:hidden";

        }
       
        return "visibility:hidden";
    }

    public String renderedPanelIconos() {
        if (tipoGrupo.equalsIgnoreCase("C")) {
            return "visibility: visible;";
        }

        return "visibility:hidden";
    }

    public Boolean habilitarPanelProfesor() {
        if (loginBean.getSeleccionado().equalsIgnoreCase("Profesor")) {
            return true;
        }
       
        return false;
    }

}