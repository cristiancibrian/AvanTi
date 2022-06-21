/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.CapturaAreaConocimientoBeanHelper;
import mx.avanti.siract.integration.ServiceLocator;
import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Oscar D. Sanchez
 */
@ManagedBean
@ViewScoped
public class AreaConocimientoBeanUI implements Serializable {

    //Declaracion de variables privadas
    private CapturaAreaConocimientoBeanHelper areaBeanHelper = null;
    private CapturaAreaConocimientoBeanHelper areaBeanHelper2 = null;
    private List<Areaconocimiento> listaFiltrada;
    private List<Planestudio> listaPlanEstudio;
    private String busqueda = "";
    private String cabecera;
    private String textoBoton;
    private String deshabilitar = "";
    private String mensajeConfirm;
    private String mensajeRep;
    private String deshabilitarBoton = "true";
    private String titleMod = "Seleccione un registro de la tabla";
    private String titleElim = "Seleccione un registro de la tabla";
    private int IdAreaEliminar = 0;

    private final int IDCATALOGOADMIAREACONOCIMIENTO = 7;

    public int getIDCATALOGOADMIAREACONOCIMIENTO() {
        return IDCATALOGOADMIAREACONOCIMIENTO;
    }

    /**
     * Propiedad para inyectar el loginbean
     */
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    /**
     * Contrusctor que manda a llamar el metodo init
     */
    public AreaConocimientoBeanUI() {
        init();
    }

    /**
     * Post constructor donde se inicializa el rol y el usuario
     */
    @PostConstruct
    public void postConstructor() {
        areaBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
        areaBeanHelper.setUsuario(loginBean.getLogueado());
        filtrado();
    }

    //Getters y Setters de las variables
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }

    public void setDeshabilitarBoton(String deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
    }

    public String getTitleMod() {
        return titleMod;
    }

    public void setTitleMod(String titleMod) {
        this.titleMod = titleMod;
    }

    public String getTitleElim() {
        return titleElim;
    }

    public void setTitleElim(String titleElim) {
        this.titleElim = titleElim;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }

    public String getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<Areaconocimiento> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Areaconocimiento> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public void planfiltrado() {
        listaFiltrada = areaBeanHelper.filtrado("Nombre", busqueda);
    }

    public CapturaAreaConocimientoBeanHelper getAreaBeanHelper() {
        return areaBeanHelper;
    }

    public void setAreaBeanHelper(CapturaAreaConocimientoBeanHelper areaBeanHelper) {
        this.areaBeanHelper = areaBeanHelper;
    }

    public CapturaAreaConocimientoBeanHelper getAreaBeanHelper2() {
        return areaBeanHelper2;
    }

    public void setAreaBeanHelper2(CapturaAreaConocimientoBeanHelper areaBeanHelper2) {
        this.areaBeanHelper2 = areaBeanHelper2;
    }

    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }

    public void setMensajeConfirm() {
        if (deshabilitar.equals("true")) {
            mensajeConfirm = "¿Está seguro de eliminar el registro?";
        } else {
            mensajeConfirm = "¿Está seguro de guardar los cambios?";
        }

        PrimeFaces.current().ajax().update("confirmdlg");
    }

    /**
     * Metodo utilizado para inicializar las variables del bean helper
     */
    public void init() {
        areaBeanHelper = new CapturaAreaConocimientoBeanHelper();
        areaBeanHelper2 = new CapturaAreaConocimientoBeanHelper();
    }

    /**
     * Metodo utilizado para modificar un registro
     */
    public void modificar() {
        header1(3);
        cargarPlan();
        try {
            if (areaBeanHelper.getListaSeleccionAcon().size() == 1) {
                areaBeanHelper.setAreaConocimiento(areaBeanHelper.getListaSeleccionAcon().get(0));
                areaBeanHelper.setProgramaeducativo(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid().getProgramaEducativoPEDid());
                areaBeanHelper.filtrarPlanPorPE();
                areaBeanHelper.setPlanestudio(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid());
            } else {
                areaBeanHelper.setAreaConocimiento(new Areaconocimiento());
                areaBeanHelper.setProgramaeducativo(new Programaeducativo());
                areaBeanHelper.setPlanestudio(new Planestudio());
                areaBeanHelper.setAreaConocimiento(areaBeanHelper.getListaSeleccionAcon().get(0));
                areaBeanHelper.setProgramaeducativo(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid().getProgramaEducativoPEDid());
                areaBeanHelper.filtrarPlanPorPE();
                areaBeanHelper.setPlanestudio(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid());
            }
        } catch (NullPointerException e) {

        }
    }

    /**
     * Metodo utilizado para la eliminacion de un registro
     */
    public void eliminar() {
        header1(2);
        cargarPlan();
        try {
            if (areaBeanHelper.getListaSeleccionAcon().size() == 1) {
                areaBeanHelper.setAreaConocimiento(areaBeanHelper.getListaSeleccionAcon().get(0));
                areaBeanHelper.setProgramaeducativo(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid().getProgramaEducativoPEDid());
                areaBeanHelper.filtrarPlanPorPE();
                areaBeanHelper.setPlanestudio(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid());
            } else {
                areaBeanHelper.setAreaConocimiento(new Areaconocimiento());
                areaBeanHelper.setProgramaeducativo(new Programaeducativo());
                areaBeanHelper.setPlanestudio(new Planestudio());
                areaBeanHelper.setAreaConocimiento(areaBeanHelper.getListaSeleccionAcon().get(0));
                areaBeanHelper.setProgramaeducativo(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid().getProgramaEducativoPEDid());
                areaBeanHelper.filtrarPlanPorPE();
                areaBeanHelper.setPlanestudio(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid());
            }
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

    /**
     * Metodo utilizado para confirmar la eliminacion de un registro
     */
    public void eliminacionConfirmada() {
        cargarPlan();
        //Areaconocimiento areaConocimiento= areaBeanHelper.buscarAreaConocimientoPorID(areaBeanHelper.getAreaConocimiento().getACOid());
        //areaBeanHelper.eliminarDeLista(areaConocimiento.getACOid());
        //areaBeanHelper.eliminarDeLista(areaBeanHelper.getAreaConocimiento().getACOid());
        //areaBeanHelper.eliminarDeLista(IdAreaEliminar);
            for (int x = 0; x < areaBeanHelper.getListaSeleccionAcon().size(); x++) {
                if (areaBeanHelper.getAreaConocimiento().getACOid() == areaBeanHelper.getListaSeleccionAcon().get(x).getACOid()) {
                    areaBeanHelper.getListaSeleccionAcon().remove(x);
                    break;
                }
            }
            
        

        areaBeanHelper.eliminarAreaConocimiento(areaBeanHelper.getAreaConocimiento());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
        areaBeanHelper.setAreaConocimiento(new Areaconocimiento());
        if (areaBeanHelper.getListaSeleccionAcon().size() > 0) {
            areaBeanHelper.setAreaConocimiento(areaBeanHelper.getListaSeleccionAcon().get(0));
            areaBeanHelper.setProgramaeducativo(areaBeanHelper.getListaSeleccionAcon().get(0).getPlanEstudioPESid().getProgramaEducativoPEDid());
            areaBeanHelper.filtrarPlanPorPE();
        } else {
            areaBeanHelper.getListaSeleccionAcon().clear();
            PrimeFaces.current().executeScript("PF('dlg').hide()");
            filtrado();
        }
        PrimeFaces.current().executeScript("PF('confdlgElim').hide()");
    }

    /**
     * Metodo para crear un nuevo registro
     */
    public void nuevo() {
        limpiarSeleccion();
        header1(1);
        areaBeanHelper.setAreaConocimiento(new Areaconocimiento());
        areaBeanHelper.setSelecAreaconocimiento(new Areaconocimiento());
        areaBeanHelper.getPlanestudio()
                .setProgramaEducativoPEDid(new Programaeducativo());
    }

    /**
     * Metodo para validar si existe
     *
     * @return Regresa un true si no existe o un falsae si existe
     */
    public boolean validacion() {
        if (areaBeanHelper.getAreaConocimiento()
                .getACOnombre().equalsIgnoreCase("")
                || areaBeanHelper.getPlanestudio().getPESid() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo para validar el area de conocimiento
     *
     * @return Regresa una variable string con una leyenda de lo que falto
     * capturar
     */
    public String validacion2() {
        if (areaBeanHelper.getAreaConocimiento().getACOnombre()
                .equalsIgnoreCase("") && areaBeanHelper.getPlanestudio()
                .getPESid() == 0 && areaBeanHelper.getProgramaeducativo()
                        .getPEDid() == 0) {
            return "Capturar campo(s) vacío(s) nombre, programa educativo "
                    + "y plan de estudios";
        }
        if (areaBeanHelper.getAreaConocimiento().getACOnombre()
                .equalsIgnoreCase("") && areaBeanHelper.getProgramaeducativo()
                .getPEDid() == 0) {
            return "Capturar campo(s) vacío(s) plan de estudios";
        }
        if (areaBeanHelper.getAreaConocimiento().getACOnombre()
                .equalsIgnoreCase("") && areaBeanHelper.getPlanestudio()
                .getPESid() == 0) {
            return "Capturar campo(s) vacío(s) nombre y plan de estudios";
        }
        if (areaBeanHelper.getProgramaeducativo().getPEDid() == 0
                && areaBeanHelper.getPlanestudio().getPESid() == 0) {
            return "Capturar campo(s) vacío(s) programa educativo y plan "
                    + "de estudios";
        }
        if (areaBeanHelper.getAreaConocimiento().getACOnombre()
                .equalsIgnoreCase("")) {
            return "Capturar campo(s) vacío(s) nombre";
        }
        if (areaBeanHelper.getPlanestudio().getPESid() == 0) {
            return "Capturar campo(s) vacío(s) plan de estudios";
        }
        if (areaBeanHelper.getProgramaeducativo().getPEDid() == 0) {
            return "Capturar campo(s) vacío(s) programa educativo";
        }
        return "nada";
    }

    /**
     * Metodo utilizado para la decision del click
     *
     * @return Regresa una variable string con un resultado
     */
    public String onClickSubmit() {
        String resultado = "";
        setMensajeConfirm();

        // Areaconocimiento areaConocimiento= areaBeanHelper.buscarAreaConocimientoPorID(areaBeanHelper.getAreaConocimiento().getACOid());
        if (deshabilitar.equals("true")) {
            Areaconocimiento areaConocimiento = areaBeanHelper.buscarAreaConocimientoPorID(areaBeanHelper.getAreaConocimiento().getACOid());
            IdAreaEliminar = areaBeanHelper.getAreaConocimiento().getACOid();
//            if (getAreaUnidad(areaBeanHelper.getAreaConocimiento()
//                    .getACOid()).size() < 1) {
            //if(areaBeanHelper.getAreaConocimiento().getUnidadaprendizajeList().size() <1){
            if (areaConocimiento.getUnidadaprendizajeList().size() < 1) {
                mensajeConfirm = "¿Está seguro de eliminar el registro?";
                areaBeanHelper.seleccionarRegistro();
                areaBeanHelper.setListaSeleccionAcon(areaBeanHelper.getListaSeleccionAcon());
                PrimeFaces.current().executeScript("PF('confdlgElim').show()");
                System.out.println("wefqewfqwe");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de "
                        + "validación", "Esta área de "
                        + "conocimiento esta asignada a una unidad de "
                        + "aprendizaje, no se puede eliminar");
                FacesContext.getCurrentInstance().addMessage(null, message);
                PrimeFaces.current().executeScript("PF('dlg').hide()");
            }
        } else {
            if (!validacion2().equalsIgnoreCase("nada")) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de " + "validación", validacion2());
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                String nom = areaBeanHelper.getAreaConocimiento().getACOnombre();
                String prog = areaBeanHelper.buscarNomProedu(areaBeanHelper.getProgramaeducativo().getPEDid());
                String plan = areaBeanHelper.buscarVigPlan(areaBeanHelper.getPlanestudio().getPESid());
                String todo = nom + "--" + prog + "--" + plan;
                mensajeRep = areaBeanHelper.validarRepetidos(todo);
                if (mensajeRep.isEmpty()) {
                    mensajeRep = "vacio";
                }
                if (mensajeRep.equals("vacio")) {
                    if (cabecera.equals("Agregar área de conocimiento")) {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "Se guardó " + "correctamente"));
                        areaBeanHelper.agregarAreaConocimiento(areaBeanHelper.getAreaConocimiento());
                        areaBeanHelper.setAreaConocimiento(new Areaconocimiento());
                        areaBeanHelper.setSelecAreaconocimiento(new Areaconocimiento());
                        areaBeanHelper.setPlanestudio(new Planestudio());
                        areaBeanHelper.setProgramaeducativo(new Programaeducativo());
                        limpiarSeleccion();
                    } else {
                        if (cabecera.equals("Modificar área de conocimiento")) {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "Se " + "guardó correctamente"));
                            areaBeanHelper.modificarAreaConocimiento(areaBeanHelper.getAreaConocimiento());
                            areaBeanHelper.seleccionarRegistro();
                            areaBeanHelper.setListaSeleccionAcon(areaBeanHelper.getListaSeleccionAcon());
                            PrimeFaces.current().executeScript("PF('dlg').show()");
                        }
                    }
                } else {
                    FacesMessage message
                            = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error"
                                    + " de validación", "Campos repetidos en "
                                    + mensajeRep);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }
        mostrarBotones();
        return resultado;
    }

    /**
     * Metodo para mostrar los botones
     */
    public void mostrarBotones() {
        try {
            if (areaBeanHelper.getSelecAreaconocimiento().getACOid() > 0) {
                deshabilitarBoton = "false";
                titleElim = "Eliminar";
                titleMod = "Modificar";
            }
        } catch (NullPointerException e) {
        }
    }

    /**
     * Metodo para la decision de la accion a realizar
     *
     * @param i variable int para la decision
     * @return Regresa una variable string con lo que se decidio
     */
    public String tooltip(int i) {
        if (areaBeanHelper.getListaSeleccionAcon() == null || areaBeanHelper
                .getListaSeleccionAcon().size() < 1) {
            return "Seleccione un registro de la tabla";
        } else {
            if (i == 2) {
                return "Eliminar";
            }
            if (i == 3) {
                return "Modificar";
            }
        }
        return "nada";
    }

    /**
     * Metodo para deshabilitar el menu
     *
     * @return Regresa un boleano como true si se desabilita y un false si esta
     * habilitado
     */
    public boolean deshabilitarMenu() {
        if (areaBeanHelper.getListaSeleccionAcon() == null
                || areaBeanHelper.getListaSeleccionAcon().size() < 1
                || areaBeanHelper.getListaSeleccionAcon().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo para limpiar elementos de la lista el momento de cambiar un
     * programa educativo
     */
    public void deshabilitarCambio() {
        try {
            areaBeanHelper.setListaSeleccionAcon(null);
            deshabilitarMenu();
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Metodo para asignar la cabecera de la accion que se realizara
     *
     * @param i Variable int para la desicion
     * @return Regresa una variable string de la accion a realizar
     */
    public String header1(int i) {
        if (i == 1) {
            setCabecera("Agregar área de conocimiento");
            deshabilitar = "false";
        }
        if (i == 2) {
            setCabecera("Eliminar área de conocimiento");
            deshabilitar = "true";
        }
        if (i == 3) {
            setCabecera("Modificar área de conocimiento");
            deshabilitar = "false";
        }
        return cabecera;
    }

    /**
     * Metodo para mostrar las areas de conocimiento
     *
     * @return Regresa un boleano si se encuentran
     */
    public boolean mostrarSeleccionArea() {
        if (areaBeanHelper.getListaSeleccionAcon() != null && areaBeanHelper
                .getListaSeleccionAcon().size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo para seleccionar una tupla
     *
     * @param event Objeto de tipo UnselectEvent
     */
    public void onRowUnselect(UnselectEvent event) {
        areaBeanHelper.setSelecAreaconocimiento(new Areaconocimiento());
        areaBeanHelper.setSelecAreaconocimiento((Areaconocimiento) event
                .getObject());
    }

    /**
     * Metodo para habilitar el boton eliminar
     *
     * @return Regresa una variable string para saber si esta habilitado o no
     */
    public String botonesModElim() {
        if (areaBeanHelper.getListaSeleccionAcon() == null) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Metodo para limpear la seleccion
     */
    public void limpiarSeleccion() {
        areaBeanHelper.setListaSeleccionAcon(null);
        areaBeanHelper.setAreaConocimiento(new Areaconocimiento());
        areaBeanHelper.setSelecAreaconocimiento(new Areaconocimiento());
        areaBeanHelper.setPlanestudio(new Planestudio());
        areaBeanHelper.getPlanestudio()
                .setProgramaEducativoPEDid(new Programaeducativo());
        areaBeanHelper.setProgramaeducativo(new Programaeducativo());
        filtrado();
        mostrarSeleccionArea();
        botonesModElim();
    }

    /**
     * Metodo para cargar el plan de estudio
     *
     * @return Regresa una lista de los planes de estudios encontrados
     */
    public List<Planestudio> cargarPlan() {
        listaPlanEstudio = areaBeanHelper.getListaPlanEstudio();
        return listaPlanEstudio;
    }

    /**
     * Metodo para obtener un area de conocimiento por ID
     *
     * @param idACON ID del area de conocimiento a buscar
     * @return Regresa una lista de las areas de conocimientos encontradas
     */
//    public List<Areaconocimiento> getAreaUnidad(int idACON) {
//        return areaBeanHelper.findFromWhere("areaconocimientos", "acoid",String.valueOf(idACON));
//    }
    /**
     * Metodo para filtrar
     */
    public void filtrado() {
        deshabilitarCambio();
        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        String catalogo = "Administración de área de conocimiento";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        String busqProg = "";
        String busqProgPlan = "";
        areaBeanHelper.getUsuarioTienePE();
        try {
            busqProg = areaBeanHelper.buscarNomProedu(areaBeanHelper.getProgramaeducativo().getPEDid());
            busqProgPlan = areaBeanHelper.buscarVigPlan(areaBeanHelper.getPlanestudio().getPESid());
        } catch (Exception e) {

        }
        if (busqProg.equalsIgnoreCase("")) {
            listaFiltrada = areaBeanHelper.filtrado("Nombre", busqueda);
        } else {
            if (busqProgPlan.equalsIgnoreCase("")) {
                if (busqueda.equalsIgnoreCase("")) {
                    listaFiltrada = areaBeanHelper.filtrado("Progedu", busqProg);
                } else {
                    String busqProgNom = busqueda + "--" + busqProg;
                    listaFiltrada = areaBeanHelper.filtrado("ProgeduNom",
                            busqProgNom);
                }
            } else {
                if (busqueda.equalsIgnoreCase("")) {
                    String busqProgPlan2 = busqProg + "--" + busqProgPlan;
                    listaFiltrada = areaBeanHelper.filtrado("ProgPlan",
                            busqProgPlan2);
                } else {
                    String todo = busqueda + "--" + busqProg + "--"
                            + busqProgPlan;
                    listaFiltrada = areaBeanHelper.filtrado("todo", todo);
                }
            }
        }
        System.out.println("edagfasegqa");
    }

    /**
     * Método para crear PDF
     */
    public Document crearPDF(Object document) {
        final Document pdf = (Document) document;
        return pdf;
    }

    /**
     * Método para anadir la información al archivo PDF
     */
    public void asignarDatosPDF() throws DocumentException {
        FacesContext context = FacesContext.getCurrentInstance();
        Document pdf = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(pdf, baos); //Método para obtener la instancia del pdf usando ByteArray's como OutputStream.
        pdf.open();

        //Obtener la fecha actual del sistema
        String fecha = obtenerFechaActual();

        //Titulo "Universidad Autonoma de Baja California" en el PDF.
        Paragraph uabcParagraph = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 113, 65)));
        Paragraph facultadParagraph = new Paragraph("Facultad de Ingeniería Mexicali", FontFactory.getFont(FontFactory.TIMES, 17, Font.BOLD, new Color(0, 113, 65)));
        Paragraph tituloParagraph = new Paragraph("Área de Conocimiento", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, new Color(0, 113, 65)));
        Paragraph fechaParagraph = new Paragraph(fecha, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new Color(0, 113, 65)));

        //Alineación del titulo.
        uabcParagraph.setAlignment(Element.ALIGN_CENTER);
        facultadParagraph.setAlignment(Element.ALIGN_CENTER);
        tituloParagraph.setAlignment(Element.ALIGN_CENTER);
        fechaParagraph.setAlignment(Element.ALIGN_RIGHT);
        fechaParagraph.setIndentationRight(73);

        //Anadir todos los elementos anteriormente declarados al archivo PDF.
        pdf.add(uabcParagraph);
        pdf.add(facultadParagraph);
        pdf.add(tituloParagraph);
        pdf.add(fechaParagraph);

        //Agregar espacio
        pdf.add(Chunk.NEWLINE);

        //Agregar logo UABC
        try {
            Image logoUABC = Image.getInstance(getClass().getClassLoader().getResource("logo.png"));
            //Posición de la imagen
            logoUABC.setAbsolutePosition(25, 720);
            logoUABC.scaleAbsolute(50, 80);
            //Agregar imagen al pdf
            pdf.add(logoUABC);
        } catch (Exception exception) {
            System.out.println("**** NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA ****");
        }

        //Agregar espacios al archivo para un mejor orden.
        pdf.add(Chunk.NEWLINE);

        //Tabla principal del documento (solo los titulos) con las "Áreas de Conocimiento".
        PdfPTable tituloACPdfPTable = new PdfPTable(3);
        tituloACPdfPTable.setWidths(new int[]{170, 70, 210});
        tituloACPdfPTable.setWidthPercentage(80);

        //Declaración de los titulos de la tabla de Área de Conocimiento.
        PdfPCell programaEducativoPdfPCell = new PdfPCell(new Paragraph("Programa educativo", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));
        PdfPCell planEstudiosPdfPCell = new PdfPCell(new Paragraph("Plan de Estudios", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));
        PdfPCell areaConocimientoPdfPCell = new PdfPCell(new Paragraph("Área de Conocimiento", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));

        //Alineación de los titulos de la tabla.
        programaEducativoPdfPCell.setHorizontalAlignment(1);
        planEstudiosPdfPCell.setHorizontalAlignment(1);
        areaConocimientoPdfPCell.setHorizontalAlignment(1);

        //Columnas de la tabla de "ACPdf"
        tituloACPdfPTable.addCell(programaEducativoPdfPCell);
        tituloACPdfPTable.addCell(planEstudiosPdfPCell);
        tituloACPdfPTable.addCell(areaConocimientoPdfPCell);

        //Tabla principal del documento (el contenido) con las "Áreas de Conocimiento".
        PdfPTable contenidoACPdfPTable = new PdfPTable(3);
        contenidoACPdfPTable.setWidths(new int[]{170, 70, 210});
        contenidoACPdfPTable.setWidthPercentage(80);

        //Recorrer lista RPEFiltrado que contiene los datos de la tabla que se muestra en el XHTML.
        for (Areaconocimiento areaconocimiento : listaFiltrada) {
            contenidoACPdfPTable.addCell(new Paragraph(areaconocimiento.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            PdfPCell planEstudiosNamePdfPCell = new PdfPCell(new Paragraph(String.valueOf(areaconocimiento.getPlanEstudioPESid().getPESvigenciaPlan()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            planEstudiosNamePdfPCell.setHorizontalAlignment(1);
            contenidoACPdfPTable.addCell(planEstudiosNamePdfPCell);
            contenidoACPdfPTable.addCell(new Paragraph(areaconocimiento.getACOnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
        }

        pdf.add(tituloACPdfPTable);
        pdf.add(contenidoACPdfPTable);
        pdf.close();

        //Método para poder generar el documento para descargar.
        writePDFToResponse(context.getExternalContext(), baos, asignarNombreArchivoPDF());
    }

    private void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) {
        try {
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Expires", "5");
            externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            externalContext.setResponseHeader("Pragma", "public");
            externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
            externalContext.setResponseContentLength(baos.size());
            OutputStream out = externalContext.getResponseOutputStream();
            baos.writeTo(out);
            externalContext.responseFlushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtenerFechaActual() {
        Date date = new Date();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }

    private String obtenerFechaActual(Date date) {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }

    public String asignarNombreArchivoPDF() {
        String nombreArchivoPDF = "Área de Conocimiento - " + obtenerFechaActual();
        return nombreArchivoPDF;
    }
}
