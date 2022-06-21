package mx.avanti.siract.ui;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.common.NodoCT;
import mx.avanti.siract.helper.CatalogoCTBeanHelper;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Practicascampo;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Edgar
 */
@ManagedBean
@ViewScoped
public class CatalogoCTBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final int IDCATALOGOADMICONTENIDO = 10;

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">
    private CatalogoCTBeanHelper cTBeanHelper;
    private Programaeducativo programaEducativo;
    private Planestudio planEstudio;
    private Areaconocimiento areaConocimiento;
    private Unidadaprendizaje unidadAprendizaje;
    private TreeNode nodosContenidoTematico;
    private TreeNode nodoSeleccionado;
    private UnidadaprendizajeTieneContenidotematico contenidoClon, contenidoTematicoActual, contenidoActualizar;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo lista">
    private List<Programaeducativo> listaProgramaEducativo = new ArrayList<>();
    private List<Planestudio> listaPlanEstudio;
    private List<Areaconocimiento> listaAreaConocimiento = new ArrayList();
    private List<Unidadaprendizaje> listaUnidadAprendizaje = new ArrayList<>();
    private List<Integer> listaVersiones = new ArrayList();
    private List<NodoCT> listaNodoCT;
    private List<NodoCT> listaNodosUnidad;
    private List<NodoCT> listaNodosTema;
    private Programaeducativo prograEdu;
    private List<UnidadaprendizajeTieneContenidotematico> unidadesCTPorPE;
    private List<Cicloescolar> listaCiclo2;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos String">
    private String seleccionarUA = "0";
    private String preNumero = "";
    private String mascara = "99:99";
    private String tipoG = "";
    private String mensajeConfirm;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables primitivas">
    private int programaESeleccionado = 0;
    private int planESeleccionado = 0;
    private int areaConSeleccionada = 0;
    private int etapaSeleccionada = 0;
    private int versionSeleccionada = 0;
    private int unidadSeleccionada = 0;
    private int temaSeleccionado = 0;
    private int horasClase = 0;
    private int horasLaboratorio = 0;
    private int horasTaller = 0;
    private int totalHoras = 0;
    private int semanas;
    private int tipoAccion;

    private boolean listaVersionesDesactivada = true;
    private boolean camposDeshabilitados;
    private boolean cambioCampo = false;
    //</editor-fold>

    // variable para nombrar el reporte en pdf
    private String nombreReporte;

    //************************\Variables para clonacion/***********************
    private boolean clonacionDesactivada = true;
    private String destinoPEDId = "0";
    private String destinoPESId = "0";
    private String origenUAPClave = "0";
    private String origenPEDNombre = "";
    private String origenPESVigenciaPlan = "";
    private String origenUAPNombre = "";
    private List<Planestudio> destinoListaPES;
    private List<Unidadaprendizaje> destinoListaUPA;
    private boolean ableOrigenSomPES = true;
    private boolean ableOrigenSomUAP = true;
    private boolean ableTipoClonacion = true;
    private boolean ableBotonClonar = true;
    private String destinoPEDNombre = "";
    private String destinoUAPClave = "";
    private String destinoUAPNombre = "";
    private String tipoUnidadAprendizajeClonar = "";
    private String tipoClonacion = "";

    //**************VER
    private boolean versionamientoDesactivado = true;
    private String programaEducativoVER;
    private String planEstudioVER;
    private String UAPVER;
    private String verActual;
    private String tipoVersClase;
    private String tipoVersTaller;
    private String tipoVersLab;
    private boolean ableBotonVers = true;
    private boolean ableEliminarVersion = false;

    public boolean impresionDesactivada = true;
    private boolean isCoordinadorAreaAdmin = false;
    private boolean isAdmin = false;
    private boolean isRPE = false;
    //Variable que guarda el Id del profesor del usuario en Sesion
    int usuId = 0;

    String estiloHorasC = "";
    String estiloHorasL = "";
    String estiloHorasT = "";

    /* ------------------------------ */
    private String hrsSolicitadas = "00:00";
    private double hrsDisponiblesConv = 0.0;
    private NodoCT contenidoUnidadAP, contenidoSeleccionado;

    //<editor-fold defaultstate="collapsed" desc="Constructor y postConstructor">
    public CatalogoCTBeanUI() {
        cTBeanHelper = new CatalogoCTBeanHelper();
        programaEducativo = new Programaeducativo(0);
        planEstudio = new Planestudio(0);
        areaConocimiento = new Areaconocimiento(0);
        unidadAprendizaje = new Unidadaprendizaje(0);
        nodosContenidoTematico = new DefaultTreeNode();
        contenidoUnidadAP = new NodoCT();
        contenidoSeleccionado = new NodoCT();
        prograEdu = new Programaeducativo();
        unidadesCTPorPE = new ArrayList<UnidadaprendizajeTieneContenidotematico>();
        listaCiclo2 = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();

    }

    @PostConstruct
    public void postConstructor() {
        if (loginBean == null && loginBean.getLogueado() != null) {
        } else {
            usuId = loginBean.getLogueado().getUSUid();

            if (loginBean.getSeleccionado().equals("Director") || loginBean.getSeleccionado().equals("Subdirector") || loginBean.getSeleccionado().equals("Administrador")) {
                isAdmin = true;
                listaProgramaEducativo = cTBeanHelper.programasEducativosDeUsuario(loginBean.getLogueado().getUSUid());
            }

            if (loginBean.getSeleccionado().equals("Responsable de Programa Educativo")) {
                Programaeducativo programaeducativoTemporal = cTBeanHelper.programaEducativoDeResponsable(loginBean.getLogueado().getUSUid());

                if (programaeducativoTemporal == null) {
                    PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación",
                            "No ha sido asignado como responsable de algun programa educativo"));
                } else {
                    isRPE = true;
                    setPrograEdu(programaeducativoTemporal);
                    unidadesCTPorPE = cTBeanHelper.unidadesTieneContenidoPorPE(prograEdu.getPEDid(), listaCiclo2.get(0).getCESid());
                    System.out.println("filtrado prueba" + unidadesCTPorPE.size());
                    listaProgramaEducativo.add(programaeducativoTemporal);
                    programaESeleccionado = programaeducativoTemporal.getPEDid();
                }
            }

            if (loginBean.getSeleccionado().equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
                isCoordinadorAreaAdmin = true;
                List<Programaeducativo> programaeducativoTemporal = cTBeanHelper.programaEducativoDeCoordinadorAreaAdmin(loginBean.getLogueado().getUSUid());

                if (programaeducativoTemporal == null) {
                    PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación",
                            "No ha sido asignado como coordinador de un area administrativa"));
                } else {
                    listaProgramaEducativo = programaeducativoTemporal;
                    programaESeleccionado = programaeducativoTemporal.get(0).getPEDid();
                }
            }
        }

        listaAreaConocimiento = getAreasByPlan();
        listaUnidadAprendizaje = getUnidadByArea();
        listaPlanEstudio = new ArrayList<>();

        try {
            semanas = cTBeanHelper.semanasCicloEscolar();
        } catch (Exception e) {
            PrimeFaces.current().executeScript("PF('dlgSemanas').show()");
        }

    }
    //</editor-fold>

    public void preProcessPDFReporte(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
    }

    public void postProcessPDFReporte(Object document) throws IOException, DocumentException {
        Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();

        try {
            Image logoUABC = Image.getInstance(this.getClass().getResource("/logo.png"));
            logoUABC.setAbsolutePosition(25, pdf.getPageSize().getHeight() - 145);
            logoUABC.scaleAbsolute(72, (72 * logoUABC.getHeight()) / logoUABC.getWidth());
            pdf.add(logoUABC);
        } catch (Exception e) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }

        //pdf = imprimirCabeceraPDF(pdf);
         DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 22, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
         Paragraph PE = new Paragraph(prograEdu.getPEDnombre(), FontFactory.getFont(FontFactory.TIMES, 14, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("CONTENIDO TEMÁTICO", FontFactory.getFont(FontFactory.TIMES, 14, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(formatoFecha.format(new Date()), FontFactory.getFont(FontFactory.TIMES, 12, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        UABC.setAlignment(Element.ALIGN_CENTER);
        PE.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(65);
        
        pdf.add(UABC);
        pdf.add(PE);
        pdf.add(titulo);
        pdf.add(fecha);
        Paragraph esp = new Paragraph(" ");
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(esp);

        PdfPTable pdftablecabezera1 = new PdfPTable(2);
        pdftablecabezera1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

//        pdftablecabezera1.addCell(new Paragraph("Programa educativo: ", FontFactory.getFont(FontFactory.TIMES, 12, com.lowagie.text.Font.BOLD)));
//        pdftablecabezera1.addCell(new Paragraph(prograEdu.getPEDnombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
//        pdftablecabezera1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        float[] columnWidths1 = new float[]{.7f, 3f};
//        pdftablecabezera1.setWidths(columnWidths1);
//        pdf.add(pdftablecabezera1);
//
//        pdf.add(new Phrase(" "));
//        pdf.add(new Phrase(" "));

        PdfPTable pdftablecont = new PdfPTable(3);
        //Columnas de la tabla

        pdftablecont.addCell(new Paragraph("Unidad de aprendizaje", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Tipo", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Estado", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.setHorizontalAlignment(15);

        float[] columnWidthsss3 = new float[]{10f, 10f, 7f};
        pdftablecont.setWidths(columnWidthsss3);
        pdf.add(pdftablecont);
        PdfPTable pdftablecont2 = new PdfPTable(3);

        boolean ban = false;
        //filas que contendra la tabla
        for (UnidadaprendizajeTieneContenidotematico utc : unidadesCTPorPE) {
            //se llena la tabla por filas
            for (int x = 0; x < 3; x++) {
                if (utc.getUnidadAprendizajeUAPid().getUAPhorasClase() != 0 && !utc.getUAPhorasClaseCompletas() && x == 0) {

                    pdftablecont2.addCell(new Paragraph(utc.getUnidadAprendizajeUAPid().getUAPnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                    pdftablecont2.addCell(new Paragraph("Clase", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                    pdftablecont2.addCell(new Paragraph("Horas incompletas", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));

                } else if (utc.getUnidadAprendizajeUAPid().getUAPhorasTaller() != 0 && !utc.getUAPhorasTallerCompletas() && x == 1) {

                    pdftablecont2.addCell(new Paragraph(utc.getUnidadAprendizajeUAPid().getUAPnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                    pdftablecont2.addCell(new Paragraph("Taller", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                    pdftablecont2.addCell(new Paragraph("Horas incompletas", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));

                } else if (utc.getUnidadAprendizajeUAPid().getUAPhorasLaboratorio() != 0 && !utc.getUAPhorasLaboratorioCompletas() && x == 2) {

                    pdftablecont2.addCell(new Paragraph(utc.getUnidadAprendizajeUAPid().getUAPnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                    pdftablecont2.addCell(new Paragraph("Laboratorio", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                    pdftablecont2.addCell(new Paragraph("Horas incompletas", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));

                }
            }

        }

        float[] columnWidthsss4 = new float[]{10f, 10f, 7f};
        pdftablecont2.setWidths(columnWidthsss4);
        pdf.add(pdftablecont2);
    }

    public void iniciarAgregado() {
        tipoAccion = 1;
        mascara = "99:00";
        camposDeshabilitados = false;
        mensajeConfirm = "¿Está seguro de agregar el contenido temático?";

        if (versionSeleccionada != listaVersiones.size()) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "La version seleccionada no es la actual."));
        } else if (tipoG.equalsIgnoreCase("C")) {
            contenidoSeleccionado.setTipo(0);
            hrsDisponiblesConv = calcularHorasDisponibles();
            listaNodoCT = new ArrayList<>(listaNodosUnidad);
            PrimeFaces.current().executeScript("PF('dlgUnidades').show()");
        } else if (tipoG.equalsIgnoreCase("L")) {
            contenidoSeleccionado.setTipo(3);
            hrsDisponiblesConv = calcularHorasDisponibles();
            PrimeFaces.current().executeScript("PF('dlgPracticaslaboratorio').show()");
        } else if (tipoG.equalsIgnoreCase("T")) {
            contenidoSeleccionado.setTipo(4);
            hrsDisponiblesConv = calcularHorasDisponibles();
            PrimeFaces.current().executeScript("PF('dlgPracticastaller').show()");
        } else {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "Debes elegir una unidad."));
        }
    }

    public void iniciarEliminado() {
        tipoAccion = 2;
        mensajeConfirm = "¿Está seguro de eliminar el contenido temático?";

        if (versionSeleccionada != listaVersiones.size()) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "La version seleccionada no es la actual."));
        } else if (nodoSeleccionado != null) {
            llenarEliminadoActualizado();
            PrimeFaces.current().executeScript("PF('dlgEliminar').show()");
        } else {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "Debes elegir una unidad."));
        }
    }

    /**
     * Verifica si hay un nodo seleccionado para abrir el dialogo de actualizar.
     */
    public void iniciarActualizado() {
        tipoAccion = 3;
        mensajeConfirm = "¿Está seguro de actualizar el contenido temático?";

        if (versionSeleccionada != listaVersiones.size()) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "La version seleccionada no es la actual."));
        } else if (nodoSeleccionado != null) {
            llenarEliminadoActualizado();
            mascara = "99:00";

            if (contenidoSeleccionado.getTipo() == 1) {
                unidadSeleccionada = ((NodoCT) nodoSeleccionado.getParent().getData()).getPosicion();
                preNumero = contenidoSeleccionado.getNumero().split("\\.")[0] + ".";
                contenidoSeleccionado.setNumero(contenidoSeleccionado.getNumero().split("\\.")[1]);
                mascara = "99:99";
            } else if (contenidoSeleccionado.getTipo() == 2) {
                unidadSeleccionada = ((NodoCT) nodoSeleccionado.getParent().getParent().getData()).getPosicion();
                temaSeleccionado = ((NodoCT) nodoSeleccionado.getParent().getData()).getPosicion();
                preNumero = contenidoSeleccionado.getNumero().split("\\.")[0] + "." + contenidoSeleccionado.getNumero().split("\\.")[1] + ".";
                contenidoSeleccionado.setNumero(contenidoSeleccionado.getNumero().split("\\.")[2]);
                mascara = "99:99";
            }

            hrsDisponiblesConv = calcularHorasDisponibles();
            PrimeFaces.current().executeScript("PF('dlgActualizacion').show()");
        } else {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "Debes elegir una unidad primero."));
        }
    }
    
    

    /**
     * Metodo para abrir el dialogo de clonar
     */
    public void iniciarClonar() {
        tipoAccion = 4;
        mensajeConfirm = "¿Está seguro de clonar el contenido temático?";
        limpiarVariablesClonacion();
        origenPEDNombre = programaEducativo.getPEDnombre();
        origenPESVigenciaPlan = planEstudio.getPESvigenciaPlan();
        origenUAPClave = seleccionarUA.split(" -- ")[0];
        origenUAPNombre = isAdmin ? seleccionarUA.split(" -- ")[0] + " -- " + seleccionarUA.split(" -- ")[1] + " -- " + seleccionarUA.split(" -- ")[2] + " -- " + seleccionarUA.split(" -- ")[4]
                : seleccionarUA;
        PrimeFaces.current().executeScript("PF('dlgClonar').show()");
    }

    /**
     * Metodo para abrir el dialogo de versionar
     */
    public void iniciarVersionar() {
        tipoAccion = 5;
        mensajeConfirm = "¿Está seguro de versionar el contenido temático?";
        limpiarVariablesVersionamiento();
        programaEducativoVER = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativo.getPEDid()).getPEDnombre();
        planEstudioVER = planEstudio.getPESvigenciaPlan();
        verActual = String.valueOf(listaVersiones.size());
        UAPVER = isAdmin ? seleccionarUA.split(" -- ")[0] + " -- " + seleccionarUA.split(" -- ")[1] + " -- " + seleccionarUA.split(" -- ")[4]
                : seleccionarUA.split(" -- ")[0] + " -- " + seleccionarUA.split(" -- ")[1] + " -- " + seleccionarUA.split(" -- ")[3];
        PrimeFaces.current().executeScript("PF('dlgVersionar').show()");
    }

    /**
     * Método para abrir el dialogo de eliminar versión
     */
    public void iniciarEliminarVersion() {
        tipoAccion = 6;
        mensajeConfirm = "¿Está seguro de elminar la versión seleccionada de contenido temático?";
        limpiarVariablesVersionamiento();
        programaEducativoVER = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativo.getPEDid()).getPEDnombre();
        planEstudioVER = planEstudio.getPESvigenciaPlan();
        verActual = String.valueOf(listaVersiones.size());
        UAPVER = isAdmin ? seleccionarUA.split(" -- ")[0] + " -- " + seleccionarUA.split(" -- ")[1] + " -- " + seleccionarUA.split(" -- ")[4]
                : seleccionarUA.split(" -- ")[0] + " -- " + seleccionarUA.split(" -- ")[1] + " -- " + seleccionarUA.split(" -- ")[3];
        PrimeFaces.current().executeScript("PF('dlgEliminarVer').show()");
    }

    public void ejecutarAceptado() {
        actualizarContenido();
        switch (tipoAccion) {
            case 1:
                contenidoSeleccionado.setNumero(preNumero + contenidoSeleccionado.getNumero());
                contenidoSeleccionado.setHoras(cTBeanHelper.formatoAHoras(hrsSolicitadas));
                cTBeanHelper.agregarContenidoTematico(contenidoSeleccionado, contenidoTematicoActual);
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Contenido temático agregado correctamente."));
                break;
            case 2:
                cTBeanHelper.eliminarContenidoTematico(contenidoSeleccionado.getContenidoTem(), contenidoSeleccionado.getTipo());
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Contenido temático eliminado correctamente."));
                PrimeFaces.current().executeScript("PF('dlgEliminar').hide()");
                break;
            case 3:
                contenidoSeleccionado.setNumero(preNumero + contenidoSeleccionado.getNumero());
                contenidoSeleccionado.setHoras(cTBeanHelper.formatoAHoras(hrsSolicitadas));
                cTBeanHelper.actualizarContenidoTematico(contenidoSeleccionado, contenidoTematicoActual);
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Contenido temático actualizado correctamente."));
                PrimeFaces.current().executeScript("PF('dlgActualizacion').hide()");
                break;
            case 4:
                cTBeanHelper.clonarContenidoTematico(contenidoTematicoActual, contenidoClon, tipoG, tipoClonacion);
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Contenido temático clonado correctamente."));
                PrimeFaces.current().executeScript("PF('dlgClonar').hide()");
                break;
            case 5:
                cTBeanHelper.versionarContenidoTematico(contenidoTematicoActual, new String[]{tipoVersClase, tipoVersLab, tipoVersTaller});
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Contenido temático versionado correctamente."));
                PrimeFaces.current().executeScript("PF('dlgVersionar').hide()");
                break;
            default:
                cTBeanHelper.eliminarVersionContenidoTematico(contenidoTematicoActual);
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El contenido de la versión " + versionSeleccionada + " ha sido eliminado"));
                PrimeFaces.current().executeScript("PF('dlgEliminarVer').hide()");
        }

        onChangeUnidadAP();
        limpiarAlCambiarTipo();
    }
    
    

    public void actualizarContenido() {
        float suma = 0;
        contenidoActualizar = contenidoTematicoActual;
        for (Unidad calcular : contenidoActualizar.getUnidadList()) {
            if(calcular.getUNIid()!=contenidoSeleccionado.getId())
                suma = suma + calcular.getUNIvalorPorcentaje();
        }
        if(tipoAccion!=2)
            suma = suma + (float) (contenidoSeleccionado.getPorcentajeAvance());
        else
            suma = suma - (float) (contenidoSeleccionado.getPorcentajeAvance());
        if (suma >= 100.00) {
            if (tipoG.equalsIgnoreCase("C")) {
                contenidoActualizar.setUAPhorasClaseCompletas(true);
            } else if (tipoG.equalsIgnoreCase("L")) {
                contenidoActualizar.setUAPhorasLaboratorioCompletas(true);
            } else if (tipoG.equalsIgnoreCase("T")) {
                contenidoActualizar.setUAPhorasTallerCompletas(true);
            } else {
                contenidoActualizar.setUAPhorasCampoCompletas(true);
            }
        } else if (suma < 100.00) {
            if (tipoG.equalsIgnoreCase("C")) {
                contenidoActualizar.setUAPhorasClaseCompletas(false);
            } else if (tipoG.equalsIgnoreCase("L")) {
                contenidoActualizar.setUAPhorasLaboratorioCompletas(false);
            } else if (tipoG.equalsIgnoreCase("T")) {
                contenidoActualizar.setUAPhorasTallerCompletas(false);
            } else {
                contenidoActualizar.setUAPhorasCampoCompletas(false);
            }
        }
        cTBeanHelper.update(contenidoActualizar);
    }

    public void llenarEliminadoActualizado() {
        contenidoSeleccionado = new NodoCT((NodoCT) nodoSeleccionado.getData());
        hrsSolicitadas = cTBeanHelper.horasAFormato(contenidoSeleccionado.getHoras());
    }

    public void cambiarUnidad() {
        limpiarCamposDialog();
        temaSeleccionado = -1;
        preNumero = "";

        if (unidadSeleccionada != -1) {
            contenidoSeleccionado.setContenidoTem(((NodoCT) nodosContenidoTematico.getChildren().get(unidadSeleccionada).getData()).getContenidoTem());
            listaNodosTema = cTBeanHelper.recupearNodosHijo(nodosContenidoTematico.getChildren().get(unidadSeleccionada));
            listaNodoCT = contenidoSeleccionado.getTipo() == 1 ? new ArrayList(listaNodosTema) : new ArrayList();
            preNumero = listaNodosUnidad.get(unidadSeleccionada).getNumero() + ".";
            hrsDisponiblesConv = calcularHorasDisponibles();
        }
    }

    public void cambiarTema() {
        limpiarCamposDialog();

        if (temaSeleccionado != -1) {
            contenidoSeleccionado.setContenidoTem(((NodoCT) nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildren().get(temaSeleccionado).getData()).getContenidoTem());
            listaNodoCT = cTBeanHelper.recupearNodosHijo(nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildren().get(temaSeleccionado));
            preNumero = listaNodosTema.get(temaSeleccionado).getNumero() + ".";
            hrsDisponiblesConv = calcularHorasDisponibles();
        }
    }

    public void limpiarAlCambiarTipo() {
        unidadSeleccionada = -1;
        temaSeleccionado = -1;
        limpiarCamposDialog();
        mascara = contenidoSeleccionado.getTipo() == 0 ? "99:00" : "99:99";
        listaNodoCT = contenidoSeleccionado.getTipo() == 0 ? new ArrayList(listaNodosUnidad) : new ArrayList();
        listaNodosTema = new ArrayList<>();
    }

    public void validarHorasAgregar() {
        hrsDisponiblesConv = 0;
        validarHorasSolicitadas();
    }

    public void validarHorasActualizar() {
        hrsDisponiblesConv = contenidoSeleccionado.getHoras();
        validarHorasSolicitadas();
    }

    public void validarHorasSolicitadas() {
        double hrsSolicitadasConv = cTBeanHelper.formatoAHoras(hrsSolicitadas);
        hrsDisponiblesConv += calcularHorasDisponibles();

        if ((cTBeanHelper.formatoAHoras(cTBeanHelper.horasAFormato(hrsDisponiblesConv)) < (cTBeanHelper.formatoAHoras(cTBeanHelper.horasAFormato(hrsSolicitadasConv))))) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación",
                    "Horas solicitadas exceden horas disponibles. Horas disponibles: " + cTBeanHelper.horasAFormato(hrsDisponiblesConv)));
            hrsSolicitadas = "00:00";
            contenidoSeleccionado.setPorcentajeAvance(0);
            PrimeFaces.current().ajax().update("formDlg:horasS", "formActualizacion:horasA", "formAgregarLab:horasPL", "formAgregarTaller:horasPT");
        } else if (cTBeanHelper.minMayor(hrsSolicitadas)) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Formato de minutos incorrecto"));
            hrsSolicitadas = "00:00";
            contenidoSeleccionado.setPorcentajeAvance(0);
            PrimeFaces.current().ajax().update("formDlg:horasS", "formActualizacion:horasA", "formAgregarLab:horasPL", "formAgregarTaller:horasPT");
        } else {
            contenidoSeleccionado.setPorcentajeAvance(cTBeanHelper.dosDecimales(hrsSolicitadasConv * 100 / contenidoUnidadAP.getHoras()));
            hrsDisponiblesConv -= hrsSolicitadasConv;
        }
    }

    public double calcularHorasDisponibles() {
        if (contenidoSeleccionado.getTipo() == 0 || contenidoSeleccionado.getTipo() == 3 || contenidoSeleccionado.getTipo() == 4) {
            return nodosContenidoTematico.getChildCount() > 0 ? (contenidoUnidadAP.getHoras() - contenidoUnidadAP.getSumaHoras()) : contenidoUnidadAP.getHoras();
        } else if (contenidoSeleccionado.getTipo() == 1 && unidadSeleccionada != -1) {
            NodoCT nct = (NodoCT) nodosContenidoTematico.getChildren().get(unidadSeleccionada).getData();
            return (nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildCount() > 0) ? (nct.getHoras() - nct.getSumaHoras()) : nct.getHoras();
        } else if (contenidoSeleccionado.getTipo() == 2 && unidadSeleccionada != -1 && temaSeleccionado != -1) {
            NodoCT nct = (NodoCT) nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildren().get(temaSeleccionado).getData();
            return (nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildren().get(temaSeleccionado).getChildCount() > 0) ? (nct.getHoras() - nct.getSumaHoras()) : nct.getHoras();
        }

        return 0;
    }

    public int validarCamposVacios() {
        String mensajeCamposVacios = "";

        if (contenidoSeleccionado.getTipo() == 1 || contenidoSeleccionado.getTipo() == 2) {
            mensajeCamposVacios += (unidadSeleccionada == -1) ? "<br>Unidad" : "";
            mensajeCamposVacios += (temaSeleccionado == -1 && contenidoSeleccionado.getTipo() == 2) ? "<br>Tema Unidad" : "";
        }

        if (!camposDeshabilitados) {
            mensajeCamposVacios += Integer.parseInt(contenidoSeleccionado.getNumero().trim()) == 0 ? "<br>- Numero no puede ser 0" : "";
            mensajeCamposVacios += contenidoSeleccionado.getNombre().trim().isEmpty() ? "<br>- Nombre." : "";
            mensajeCamposVacios += hrsSolicitadas.equals("00:00") ? "<br>- Horas no pueden ser 0." : "";
        }

        if (!mensajeCamposVacios.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Campos Obligatorios: " + mensajeCamposVacios));
            return 1;
        }

        return 0;
    }

    public int validarHorasHijos() {
        if (tipoAccion == 3 && (contenidoSeleccionado.getTipo() == 0 || contenidoSeleccionado.getTipo() == 1)) {
            if (cTBeanHelper.formatoAHoras(hrsSolicitadas) < calcularHorasDisponibles()) {
                String tipoHijo = ((NodoCT) nodoSeleccionado.getData()).getTipo() == 0 ? "temas" : "subtemas";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las horas ingresadas, no deben ser menores a \nlas horas colocadas en " + tipoHijo + "."));
                return 1;
            }
        }

        return 0;
    }

    public int validarNumeroONombreRepetido() {
        String numeroCompleto = preNumero + contenidoSeleccionado.getNumero();
        List<TreeNode> listaNodo;

        switch (contenidoSeleccionado.getTipo()) {
            case 0:
            case 3:
            case 4:
                listaNodo = nodosContenidoTematico.getChildren();
                break;
            case 1:
                listaNodo = nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildren();
                break;
            default:
                listaNodo = nodosContenidoTematico.getChildren().get(unidadSeleccionada).getChildren().get(temaSeleccionado).getChildren();
        }

        for (TreeNode node : listaNodo) {
            NodoCT nodo = (NodoCT) node.getData();

            if ((nodo.getNombre().equalsIgnoreCase(contenidoSeleccionado.getNombre()) || nodo.getNumero().equals(numeroCompleto)) && nodo.getId() != contenidoSeleccionado.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Valor de nombre o número repetido"));
                return 1;
            }
        }

        return 0;
    }

    public int validarClonacion() {
        Unidadaprendizaje unidadAPClonacion = cTBeanHelper.getUnidadAprendizajeByClave(destinoUAPClave);
        int versionDestino = unidadAPClonacion.getUnidadaprendizajeTieneContenidotematicoList().size();
        contenidoClon = unidadAPClonacion.getUnidadaprendizajeTieneContenidotematicoList().get(versionDestino - 1);

        if (contenidoClon.getUnidadList().isEmpty() && tipoG.equals("C")) {
            return 0;
        } else if (contenidoClon.getPracticalaboratorioList().isEmpty() && tipoG.equals("L")) {
            return 0;
        } else if (contenidoClon.getPracticatallerList().isEmpty() && tipoG.equals("T")) {
            return 0;
        }

        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La unidad de aprendizaje tiene contenido temático asignado"));
        return 1;
    }

    public int validarEliminacionContenido() {
        if (cTBeanHelper.validarEliminacionContenido(contenidoTematicoActual) == 1) {
            PrimeFaces.current().executeScript("PF('status').hide()");
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No es posible eliminar el contenido porque tiene reportes registrados"));
            return 1;
        }

        PrimeFaces.current().executeScript("PF('status').hide()");
        return 0;
    }

    /**
     * Método que valida todas las funciones.
     */
    public void validarTodo() {
        int valido = 0;

        switch (tipoAccion) {
            case 1:
            case 3:
                valido += validarCamposVacios();
                valido += valido == 0 ? validarNumeroONombreRepetido() : 0;
                valido += validarHorasHijos();
                break;
            case 4:
                valido += validarClonacion();
                break;
            case 6:
                valido += validarEliminacionContenido();
        }

        if (valido == 0) {
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");
        }
    }

    public void limpiarCamposDialog() {
        camposDeshabilitados = deshabilitarCampos();
        preNumero = "";
        contenidoSeleccionado.setNumero("0");
        contenidoSeleccionado.setNombre("");
        hrsSolicitadas = "00:00";
        hrsDisponiblesConv = calcularHorasDisponibles();
        contenidoSeleccionado.setPorcentajeAvance(0);
        listaNodoCT = contenidoSeleccionado.getTipo() == 0 ? new ArrayList(listaNodosUnidad) : new ArrayList();
    }

    /**
     * Metodo para limpiar las variables de clonacion
     */
    public void limpiarVariablesClonacion() {
        destinoPEDId = "0";
        destinoPESId = "0";
        origenUAPClave = "0";
        destinoListaPES = null;
        destinoListaUPA = null;
        ableOrigenSomPES = true;
        ableOrigenSomUAP = true;
        ableBotonClonar = true;
        ableTipoClonacion = true;
        destinoPEDNombre = "0";
        destinoUAPClave = "0";
        destinoUAPNombre = "0";
        tipoUnidadAprendizajeClonar = "";
        tipoClonacion = "";
        origenPEDNombre = "";
        origenPESVigenciaPlan = "";
        origenUAPNombre = "";
        habilitarClonacion();
    }

    /**
     * Método para limpiar las variables de versionamiento
     */
    public void limpiarVariablesVersionamiento() {
        versionamientoDesactivado = ((!estiloHorasC.equals("color:green;") && horasClase > 0) || (!estiloHorasL.equals("color:green;") && horasLaboratorio > 0)
                || (!estiloHorasT.equals("color:green;") && horasTaller > 0));
        programaEducativoVER = "0";
        planEstudioVER = "0";
        UAPVER = "0";
        verActual = "0";
        tipoVersClase = "0";
        tipoVersTaller = "0";
        tipoVersLab = "0";
        ableBotonVers = true;
    }

    public String cabeceraColumna() {
        return (tipoG.equals("T") || tipoG.equals("L")) ? "Nombre de la práctica" : "Nombre de la unidad";
    }

    public boolean deshabilitarCampos() {
        if (unidadSeleccionada != -1 && temaSeleccionado != -1 && contenidoSeleccionado.getTipo() == 2) {
            return false;
        } else if (unidadSeleccionada != -1 && contenidoSeleccionado.getTipo() == 1) {
            return false;
        } else if (contenidoSeleccionado.getTipo() == 0) {
            return false;
        }

        return true;
    }

    public boolean habilitarListaUnidad() {
        return contenidoSeleccionado.getTipo() == 1 || contenidoSeleccionado.getTipo() == 2;
    }

    public String horasCompletasNodo(boolean horasCompletas, int tipo) {
        if (tipo == 0 || tipo == 1) {
            if (!horasCompletas) {
                return "color:red;";
            }
        }

        return "color:black;";
    }

    private void obtenerVersiones() {
        versionSeleccionada = unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().size();
        listaVersiones.clear();

        for (int i = 0; i < versionSeleccionada; i++) {
            listaVersiones.add(i + 1);
        }
    }

    /**
     * Metodo para validar que la UAP seleccionada este vacia y pueda ser un
     * destino para clonar Se actualiza habilitacion del boton clonar
     */
    public void habilitarClonacion() {
        switch (tipoG) {
            case "C":
                clonacionDesactivada = contenidoTematicoActual.getUnidadList().isEmpty() || versionSeleccionada == 0;
                break;
            case "L":
                clonacionDesactivada = contenidoTematicoActual.getPracticalaboratorioList().isEmpty() || versionSeleccionada == 0;
                break;
            case "T":
                clonacionDesactivada = contenidoTematicoActual.getPracticatallerList().isEmpty() || versionSeleccionada == 0;
                break;
        }

        tipoUnidadAprendizajeClonar = tipoG;
    }

    /**
     * Método para verificar si es posible eliminar una versión
     *
     * @return
     */
    public boolean habilitarEliminarContenido() {
        return !listaVersiones.isEmpty() && versionSeleccionada > 1 && versionSeleccionada == listaVersiones.size();
    }

    public boolean tieneClase() {
        return horasClase != 0;
    }

    public boolean tieneTaller() {
        return horasTaller != 0;
    }

    public boolean tieneLaboratorio() {
        return horasLaboratorio != 0;
    }

    public void mostrarRegistrosFaltantes() {
        String respuesta;

        switch (tipoG) {
            case "C":
                respuesta = cTBeanHelper.unidadesFaltantes();
                break;
            case "L":
                respuesta = cTBeanHelper.practicasLaboratorioFaltantes();
                break;
            default:
                respuesta = cTBeanHelper.practicasTallerFaltantes();
                break;
        }

        if (!respuesta.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "No ha sido agregado el siguiente contenido:<br/>" + respuesta));
        }
    }

    public void onChangeUnidadAP() {
        establecerUnidadAprendizaje();

        if (!seleccionarUA.equals("0")) {
            setDeCampos();
            habilitarClonacion();
            listaVersionesDesactivada = false;
            versionamientoDesactivado = ((!estiloHorasC.equals("color:green;") && horasClase > 0) || (!estiloHorasL.equals("color:green;") && horasLaboratorio > 0)
                    || (!estiloHorasT.equals("color:green;") && horasTaller > 0));
            impresionDesactivada = !(!seleccionarUA.equals("0") && nodosContenidoTematico.getChildCount() != 0);
            nombreReporte = seleccionarUA;
            nodoSeleccionado = null;
        }
    }

    public void establecerUnidadAprendizaje() {
        if (!seleccionarUA.equals("0")) {
            String[] valores = seleccionarUA.split(" -- ");
            unidadAprendizaje = cTBeanHelper.getUnidadAprendizajeByClave(valores[0]);
            cTBeanHelper.setUnidadaprendizaje(unidadAprendizaje);
            tipoG = valores[2].trim();
            obtenerVersiones();
            establecerHoras();
        } else {
            limpiarTodo();
        }
    }

    public void establecerHoras() {
        horasClase = unidadAprendizaje.getUAPhorasClase() * 16;
        horasLaboratorio = unidadAprendizaje.getUAPhorasLaboratorio() * 16;
        horasTaller = unidadAprendizaje.getUAPhorasTaller() * 16;
        totalHoras = (horasClase + horasTaller + horasLaboratorio);
        nodosContenidoTematico = cTBeanHelper.getNodosVers(tipoG, versionSeleccionada);
        contenidoUnidadAP = (NodoCT) nodosContenidoTematico.getData();
        listaNodosUnidad = cTBeanHelper.recupearNodosHijo(nodosContenidoTematico);
        contenidoTematicoActual = unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(versionSeleccionada - 1);
        mostrarRegistrosFaltantes();
        establecerColorHoras();
    }

    public void establecerColorHoras() {
        limpiarColorHoras();

        if (horasClase != 0) {
            estiloHorasC = cTBeanHelper.getHrsIncompletasClase() > 0 ? "color:red;" : "color:green;";
        }

        if (horasLaboratorio != 0) {
            estiloHorasL = cTBeanHelper.getHrsIncompletasLab() > 0 ? "color:red;" : "color:green;";
        }

        if (horasTaller != 0) {
            estiloHorasT = cTBeanHelper.getHrsIncompletasTaller() > 0 ? "color:red;" : "color:green;";
        }

        if (!cTBeanHelper.isHrsCompletas()) {
            FacesContext.getCurrentInstance().addMessage("horas", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Las horas disponibles no han sido completamente asignadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage("horas", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Las horas disponibles ya han sido completamente asignadas"));
        }
    }

    public void setDeCampos() {
        switch (seleccionarUA.split(" -- ")[4]) {
            case "Básica":
                etapaSeleccionada = 1;
                break;
            case "Disciplinaria":
                etapaSeleccionada = 2;
                break;
            default:
                etapaSeleccionada = 3;
        }

        if (programaESeleccionado == 0 || !cambioCampo) {
            programaEducativo = cTBeanHelper.getProgramaEducativoPorClave(Integer.parseInt(seleccionarUA.split(" -- ")[3]));
            programaESeleccionado = programaEducativo.getPEDid();
        } else {
            programaEducativo = cTBeanHelper.getProgramaEducativoPorID(programaESeleccionado);
        }

        if (areaConSeleccionada == 0 || !cambioCampo) {
            for (Areaconocimiento areaCon : unidadAprendizaje.getAreaconocimientoList()) {
                if (areaCon.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid() == programaESeleccionado) {
                    areaConocimiento = areaCon;
                    areaConSeleccionada = areaConocimiento.getACOid();
                    planEstudio = areaConocimiento.getPlanEstudioPESid();
                    planESeleccionado = planEstudio.getPESid();
                    listaPlanEstudio = new ArrayList<>();
                    listaPlanEstudio.add(planEstudio);
                }
            }
        } else {
            obtenerAreaConPorID();
        }

        cambioCampo = false;
    }

    public void limpiarCamposUA() {
        limpiarColorHoras();
        horasClase = 0;
        horasTaller = 0;
        horasLaboratorio = 0;
        totalHoras = 0;
        versionSeleccionada = 0;
        listaVersiones.clear();
        listaVersionesDesactivada = true;
        versionamientoDesactivado = true;
        clonacionDesactivada = true;
        impresionDesactivada = true;
        tipoG = "";
        nodosContenidoTematico = new DefaultTreeNode();
        PrimeFaces.current().ajax().update("form:messages");
    }

    public void limpiarTodo() {
        programaESeleccionado = 0;
        planESeleccionado = 0;
        areaConSeleccionada = 0;
        etapaSeleccionada = 0;
        seleccionarUA = "0";
        unidadAprendizaje = new Unidadaprendizaje(0);
        unidadAprendizaje.setUAPclave(0);
        limpiarCamposUA();
    }

    public void limpiarColorHoras() {
        estiloHorasC = "color:black";
        estiloHorasL = "color:black";
        estiloHorasT = "color:black";
    }

    /**
     * Método que se ejecuta al cambiar programa educativo para deseleccionar
     * plan de estudio.
     *
     */
    public void onChangeProgramaEducativo() {
        cambioCampo = true;

        if (programaESeleccionado != 0) {
            programaEducativo = cTBeanHelper.getProgramaEducativoPorID(programaESeleccionado);
            listaPlanEstudio = cTBeanHelper.getPlanesByPrograma(programaESeleccionado);
        }

        planESeleccionado = 0;
        areaConSeleccionada = 0;
        seleccionarUA = "0";
        etapaSeleccionada = 0;
        limpiarCamposUA();
        listaPlanEstudio = obtenerPlanesEstudio();
        listaAreaConocimiento = getAreasByPlan();
        listaUnidadAprendizaje = getUnidadByArea();
    }

    /**
     * Método para obtener unidades de aprendizaje al seleccionar plan de
     * estudio.
     */
    public void onChangePlanEstudio() {
        cambioCampo = true;
        areaConSeleccionada = 0;
        etapaSeleccionada = 0;
        limpiarCamposUA();
        listaAreaConocimiento = getAreasByPlan();
        listaUnidadAprendizaje = getUnidadByArea();
    }

    /**
     * Método para obtener unidades de aprendizaje al seleccionar area de
     * conocimiento.
     */
    public void onChangeAreaConocimiento() {
        cambioCampo = true;
        obtenerAreaConPorID();
        listaUnidadAprendizaje = getUnidadByArea();
        etapaSeleccionada = 0;
        limpiarCamposUA();
    }

    /**
     * Método obtener unidades de aprendizaje al seleccionar una etapa.
     */
    public void onChangeEtapa() {
        listaUnidadAprendizaje = getUnidadByArea();
        listaVersiones.clear();
        versionSeleccionada = 0;
        listaVersionesDesactivada = true;
        versionamientoDesactivado = false;
    }

    /**
     * Método que cambia la versión del contenido tematico de una unidad de
     * aprendizaje.
     */
    public void onChangeVersion() {
        if (versionSeleccionada == 0) {
            limpiarColorHoras();
            habilitarClonacion();
            versionamientoDesactivado = true;
            nodosContenidoTematico = new DefaultTreeNode();
            FacesContext.getCurrentInstance().addMessage("horas", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "No ha seleccionado una versión."));
        } else {
            habilitarClonacion();
            establecerHoras();
            versionamientoDesactivado = ((!estiloHorasC.equals("color:green;") && horasClase > 0) || (!estiloHorasL.equals("color:green;") && horasLaboratorio > 0)
                    || (!estiloHorasT.equals("color:green;") && horasTaller > 0));
        }
    }

    /**
     * Metodo para obtener los Planes de Estudio
     *
     * @return Lista de planes de estudio
     */
    public List<Planestudio> obtenerPlanesEstudio() {
        if (isAdmin) {
            if (!seleccionarUA.equals("0")) {
                listaPlanEstudio = new ArrayList<>();
                listaPlanEstudio.add(cTBeanHelper.getPlanesestudioByUnidadAprendizaje(unidadAprendizaje.getUAPid()).get(0));

                if (listaPlanEstudio != null && listaPlanEstudio.size() >= 1) {
                    planESeleccionado = listaPlanEstudio.get(0).getPESid();
                    planEstudio = listaPlanEstudio.get(0);
                }

                return listaPlanEstudio;
            }

            return programaESeleccionado != 0 ? programaEducativo.getPlanestudioList() : new ArrayList();
        }

        return programaESeleccionado != 0 ? cTBeanHelper.getPlanesestudio(programaEducativo.getPEDid()) : new ArrayList();
    }

    /**
     * Metodo al cambiar el programa educativo para mostrar plan de estudios
     */
    public void onChangeProgramaEducativoClonar() {
        if (!destinoPEDId.equals("0")) {
            destinoListaPES = cTBeanHelper.getPlanesByPrograma(Integer.parseInt(destinoPEDId));
            ableOrigenSomPES = false;
            ableTipoClonacion = true;
            ableOrigenSomUAP = true;
            ableBotonClonar = true;
        }
    }

    /**
     * Metodo al cambiar plan de estudios para mostrar unidades de aprendizaje
     */
    public void onChangePlanEstudioClonar() {
        if (!destinoPESId.equals("0")) {
            destinoListaUPA = cTBeanHelper.getUnidadByPlanEstudio(Integer.parseInt(destinoPESId));
            String tipoUnidad = tipoG;
            List<Unidadaprendizaje> listaTemporal = new ArrayList();

            for (Unidadaprendizaje unidad : destinoListaUPA) {
                if (unidad.getUAPnombre().split(" -- ")[1].equalsIgnoreCase(tipoUnidad)) {
                    listaTemporal.add(unidad);
                }
            }

            destinoListaUPA = listaTemporal;
            ableOrigenSomUAP = false;
            ableTipoClonacion = true;
            ableBotonClonar = true;
        }
    }

    public void onChangeUnidadAprendizajeClonar() {
        ableTipoClonacion = destinoUAPClave.equals("0");
        ableBotonClonar = true;
        tipoClonacion = "0";
    }

    public void onChangeTipoClonacion() {
        ableBotonClonar = tipoClonacion.equals("0");
    }

    public void onChangeTipoVersionamiento() {
        ableBotonVers = (tipoVersClase.equals("0") && horasClase != 0 || tipoVersTaller.equals("0") && horasTaller != 0 || tipoVersLab.equals("0") && horasLaboratorio != 0);
    }

    public void obtenerAreaConPorID() {
        for (Areaconocimiento areaCon : listaAreaConocimiento) {
            if (areaCon.getACOid() == areaConSeleccionada) {
                areaConocimiento = areaCon;
                programaESeleccionado = areaConocimiento.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid();
                listaPlanEstudio = areaConocimiento.getPlanEstudioPESid().getProgramaEducativoPEDid().getPlanestudioList();
                planESeleccionado = areaConocimiento.getPlanEstudioPESid().getPESid();
                break;
            }
        }
    }

    /**
     * Metodo para obtener las areas de conocimiento por Plan Educativo
     *
     * @return
     */
    public List<Areaconocimiento> getAreasByPlan() {
        if (isAdmin) {
            if (planESeleccionado != 0) {
                seleccionarUA = "0";
                unidadAprendizaje = new Unidadaprendizaje(0);

                return cTBeanHelper.getAreasByPlanYProgramaeducativoAdmin(planESeleccionado, programaESeleccionado);
            } else {
                if (isCoordinadorAreaAdmin) {
                    return cTBeanHelper.getAreasByCoordinadorAreaAdmin(String.valueOf(programaESeleccionado), usuId);
                } else if (programaESeleccionado != 0) {
                    return cTBeanHelper.getAreasByProgramaeducativoAdmin(String.valueOf(programaESeleccionado));
                }
            }

            Unidadacademica uaAux = cTBeanHelper.findUnidadAcademica(loginBean.getLogueado().getUSUid()).get(0);
            return cTBeanHelper.getAreasByUnidadAcademica(uaAux.getUACid());
        }

        if (planESeleccionado != 0) {
            seleccionarUA = "0";
            unidadAprendizaje.setUAPid(0);

            if (isCoordinadorAreaAdmin) {
                return cTBeanHelper.getAreasByPlanYProgramaeducativoCAA(planESeleccionado, programaESeleccionado, usuId);
            }

            return cTBeanHelper.getAreasByPlanYProgramaeducativoRPE(planESeleccionado, programaESeleccionado, usuId);
        } else {
            if (programaESeleccionado != 0) {
                return isCoordinadorAreaAdmin ? cTBeanHelper.getAreasByProgramaeducativoCAA(programaESeleccionado, usuId) : cTBeanHelper.getAreasByProgramaeducativoRPE(programaESeleccionado, usuId);
            } else {
                return isCoordinadorAreaAdmin ? listaAreaConocimiento : cTBeanHelper.getAreasByRPE(usuId);
            }
        }
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Area de conocimiento
     *
     * @return Lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> getUnidadByArea() {
        establecerClavePED();

        if (isAdmin && etapaSeleccionada == 0 && areaConSeleccionada == 0 && programaESeleccionado == 0 && planESeleccionado == 0) {
            Unidadacademica aux = cTBeanHelper.findUnidadAcademica(loginBean.getLogueado().getUSUid()).get(0);
            listaUnidadAprendizaje = cTBeanHelper.getUnidadByUnidadAcademica(aux.getUACid());

            return listaUnidadAprendizaje;
        } else {
            // ID 7 = PROFESOR
            if (!isAdmin && etapaSeleccionada == 0 && areaConSeleccionada == 0 && programaESeleccionado == 0 && planESeleccionado == 0 && !(loginBean.getIdGlobal() == 7)) {
                listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByCAA(usuId) : cTBeanHelper.getUnidadByRPE(usuId);
                return listaUnidadAprendizaje;
            }

            if (etapaSeleccionada != 0 && areaConSeleccionada != 0) {
                setSeleccionarUnidadAprendizaje("0");
                unidadAprendizaje.setUAPid(0);

                /*Este if es para introducir la etapa, se cambio a entero para poder utilizar la propiedad "Disabled"
                 en los componentes SelectOneMenu en la vista xhtml*/
                String etapa = cTBeanHelper.seleccionarEtapa(etapaSeleccionada);

                if (programaESeleccionado == 0) {
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByAreaAndEtapaCAA(areaConSeleccionada, etapa, usuId)
                            : cTBeanHelper.getUnidadByAreaAndEtapa(areaConSeleccionada, etapa);
                } else {
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByAreaAndEtapaAndPECAA(areaConSeleccionada, etapa, programaESeleccionado, usuId)
                            : cTBeanHelper.getUnidadByAreaAndEtapaAndPE(areaConSeleccionada, etapa, programaESeleccionado);
                }

                return listaUnidadAprendizaje;
            } else {
                setSeleccionarUnidadAprendizaje("0");
                unidadAprendizaje.setUAPid(0);

                //condicion para validar si hay un programa educativo seleccionado
                if (programaESeleccionado == 0) {
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByCAA(usuId) : cTBeanHelper.getUnidadByResponsable(loginBean.getLogueado().getUSUid());
                } else {
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByPECAA(programaESeleccionado, usuId)
                            : cTBeanHelper.getUnidadByPE(programaESeleccionado);
                }

                //Validacion para caso de elegir Programa educativo y area de conocimiento
                if (areaConSeleccionada != 0 && programaESeleccionado != 0) {
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByAreaConocimientoAndCAA(areaConSeleccionada, programaESeleccionado, usuId)
                            : cTBeanHelper.getUnidadByAreaConocimientoAndPE(areaConSeleccionada, programaESeleccionado);
                }

                //Seleccion de programa educativo y etapa
                if (etapaSeleccionada != 0 && programaESeleccionado != 0) {
                    String etapa = cTBeanHelper.seleccionarEtapa(etapaSeleccionada);
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByEtapaAndPECAA(etapa, programaESeleccionado, usuId)
                            : cTBeanHelper.getUnidadByEtapaAndPEAdmin(etapa, programaESeleccionado);
                }

                //Validacion para caso de seleccionar solo area de conocimiento
                if (areaConSeleccionada != 0 && programaESeleccionado != 0) {
                    if (isAdmin) {
                        listaUnidadAprendizaje = cTBeanHelper.getUnidadByAreaConocimientoAdmin(areaConSeleccionada);
                    } else {
                        listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByAreaConocimientoCAA(areaConSeleccionada, usuId)
                                : cTBeanHelper.getUnidadByAreaConocimientoRPE(areaConSeleccionada, usuId);
                    }
                }

                //Caso de solo seleccionar etapa
                if (etapaSeleccionada != 0 && areaConSeleccionada == 0 && programaESeleccionado == 0) {
                    String etapa = cTBeanHelper.seleccionarEtapa(etapaSeleccionada);

                    if (isAdmin) {
                        listaUnidadAprendizaje = cTBeanHelper.getUnidadByEtapaAdmin(etapa);
                    } else {
                        listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByEtapaCAA(etapa, String.valueOf(usuId))
                                : cTBeanHelper.getUnidadByEtapa(etapa, String.valueOf(usuId));
                    }
                }

                //Caso de seleccionar solo plan de estudio
                if (planESeleccionado != 0 && areaConSeleccionada == 0 && etapaSeleccionada == 0) {
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByPlanEstudioCAA(planESeleccionado, usuId)
                            : cTBeanHelper.getUnidadByPlanEstudio(planESeleccionado);
                }

                //Caso de seleccionar solo plan de estudio y etapa
                if (planESeleccionado != 0 && areaConSeleccionada == 0 && etapaSeleccionada != 0) {
                    String etapa = cTBeanHelper.seleccionarEtapa(etapaSeleccionada);
                    listaUnidadAprendizaje = isCoordinadorAreaAdmin ? cTBeanHelper.getUnidadByPlanEstudioAndEtapaCAA(planESeleccionado, etapa, usuId)
                            : cTBeanHelper.getUnidadByPlanEstudioAndEtapa(planESeleccionado, etapa);
                }

                return listaUnidadAprendizaje;
            }
        }
    }

    public void establecerClavePED() {
        if (isCoordinadorAreaAdmin || isRPE) {
            cTBeanHelper.setClaveProgramaEd(programaESeleccionado == 0 ? listaProgramaEducativo.get(0).getPEDclave() : programaEducativo.getPEDclave());
        } else if (isAdmin) {
            cTBeanHelper.setClaveProgramaEd(programaESeleccionado == 0 ? 0 : programaEducativo.getPEDclave());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Métodos para imprimir PDF">
    public void postProcessPDF(Object document) throws IOException, DocumentException {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();

        try {
            Image logoUABC = Image.getInstance(this.getClass().getResource("/logo.png"));
            logoUABC.setAbsolutePosition(25, pdf.getPageSize().getHeight() - 145);
            logoUABC.scaleAbsolute(72, (72 * logoUABC.getHeight()) / logoUABC.getWidth());
            pdf.add(logoUABC);
        } catch (Exception e) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }

        pdf = imprimirCabeceraPDF(pdf);
        pdf.add(imprimirTablaDetallesPDF());
        pdf.add(new Phrase(" "));
        pdf.add(new Phrase(" "));
        pdf.add(new Paragraph(" "));
        pdf.add(new Paragraph(" "));
        pdf.add(new Paragraph(" "));
        pdf.add(imprimirDetalleHorasPDF());
        pdf.add(new Phrase(" "));
        pdf.add(imprimirContenidoPDF());
    }

    public Document imprimirCabeceraPDF(Document pdf) throws DocumentException {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 22, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("CONTENIDO TEMÁTICO", FontFactory.getFont(FontFactory.TIMES, 14, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(formatoFecha.format(new Date()), FontFactory.getFont(FontFactory.TIMES, 12, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        UABC.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(65);

        pdf.add(UABC);
        pdf.add(titulo);
        pdf.add(fecha);
        Paragraph esp = new Paragraph(" ");
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(esp);

        PdfPTable pdfTabletitulo = new PdfPTable(2);
        pdfTabletitulo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdfTabletitulo.setHorizontalAlignment(25);
        pdfTabletitulo.setWidths(new float[]{4f, 28});

        for (int i = 0; i < 10; i++) {
            pdfTabletitulo.addCell(new Paragraph(""));
        }

        pdf.add(pdfTabletitulo);

        return pdf;
    }

    public PdfPTable imprimirTablaDetallesPDF() throws DocumentException {
        PdfPTable pdftablecabezera = new PdfPTable(2);
        pdftablecabezera.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftablecabezera.addCell(new Paragraph("Unidad de aprendizaje: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(unidadAprendizaje.getUAPnombre()));
        pdftablecabezera.addCell(new Paragraph("Programa educativo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(programaEducativo.getPEDnombre()));
        pdftablecabezera.addCell(new Paragraph("Plan de estudio: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(planEstudio.getPESvigenciaPlan()));
        pdftablecabezera.addCell(new Paragraph("Área de conocimiento: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(areaConocimiento.getACOnombre()));
        pdftablecabezera.addCell(new Paragraph("Etapa: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(cTBeanHelper.seleccionarEtapa(etapaSeleccionada)));
        pdftablecabezera.addCell(new Paragraph("Tipo grupo: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph(cTBeanHelper.obtenerTipoGrupo(tipoG)));
        pdftablecabezera.addCell(new Paragraph("Versión: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera.addCell(new Paragraph("" + versionSeleccionada));
        pdftablecabezera.setHorizontalAlignment(25);
        float[] columnWidthss = new float[]{35f, 40f};
        pdftablecabezera.setWidths(columnWidthss);

        return pdftablecabezera;
    }

    public PdfPTable imprimirDetalleHorasPDF() throws DocumentException {
        PdfPTable pdftablecabezera2 = new PdfPTable(8);
        pdftablecabezera2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        pdftablecabezera2.addCell(new Paragraph("HC: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph(horasClase + ""));
        pdftablecabezera2.addCell(new Paragraph("HL: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph(horasLaboratorio + ""));
        pdftablecabezera2.addCell(new Paragraph("HT: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph(horasTaller + ""));
        pdftablecabezera2.addCell(new Paragraph("Horas semestre: ", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new Color(0, 0, 0))));
        pdftablecabezera2.addCell(new Paragraph(totalHoras + ""));
        pdftablecabezera2.setHorizontalAlignment(25);
        pdftablecabezera2.setWidths(new float[]{10f, 10f, 10f, 10f, 10f, 10f, 23f, 10f});

        return pdftablecabezera2;
    }

    public PdfPTable imprimirContenidoPDF() throws DocumentException {
        PdfPTable pdftablecont = new PdfPTable(3);
        pdftablecont.addCell(new Paragraph(cabeceraColumna(), FontFactory.getFont(FontFactory.TIMES_BOLD, 13, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Horas", FontFactory.getFont(FontFactory.TIMES_BOLD, 13, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Porcentaje de horas", FontFactory.getFont(FontFactory.TIMES_BOLD, 13, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));

        for (NodoCT nodo : cTBeanHelper.getListaPDF()) {
            switch (nodo.getTipo()) {
                case 0:
                case 3:
                case 4:
                    pdftablecont.addCell(new Paragraph(nodo.getNumero() + ".- " + nodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
                    pdftablecont.addCell(new Paragraph("" + cTBeanHelper.horasAFormato(nodo.getHoras()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
                    pdftablecont.addCell(new Paragraph("" + cTBeanHelper.dosDecimales(nodo.getPorcentajeAvance()) + "%", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
                    break;
                case 1:
                    pdftablecont.addCell(new Paragraph("    " + nodo.getNumero() + ".- " + nodo.getNombre()));
                    pdftablecont.addCell(new Paragraph("" + cTBeanHelper.horasAFormato(nodo.getHoras())));
                    pdftablecont.addCell(new Paragraph("" + cTBeanHelper.dosDecimales(nodo.getPorcentajeAvance()) + "%"));
                    break;
                default:
                    pdftablecont.addCell(new Paragraph("        " + nodo.getNumero() + ".- " + nodo.getNombre(), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
                    pdftablecont.addCell(new Paragraph("" + cTBeanHelper.horasAFormato(nodo.getHoras()), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
                    pdftablecont.addCell(new Paragraph("" + cTBeanHelper.dosDecimales(nodo.getPorcentajeAvance()) + "%", FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
            }
        }

        pdftablecont.setHorizontalAlignment(15);
        pdftablecont.setWidths(new float[]{40f, 12f, 20f});

        return pdftablecont;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos para imprimir Excel">
    public void exportarExcel() throws Throwable {
        exportarReporteExcel(nombreReporte, postProcessXLS());
    }

    public void exportarReporteExcel(String nombreLibro, HSSFWorkbook workbook) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-excel");

        nombreLibro = nombreReporte;
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreLibro + ".xls\"");
        workbook.write(externalContext.getResponseOutputStream());
        facesContext.responseComplete();
    }

    public HSSFWorkbook postProcessXLS() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet hoja = wb.createSheet();
        hoja.addMergedRegion(new CellRangeAddress(2, 4, 1, 2));
        hoja.addMergedRegion(new CellRangeAddress(2, 2, 3, 7));
        hoja.addMergedRegion(new CellRangeAddress(3, 3, 3, 7));
        hoja.addMergedRegion(new CellRangeAddress(4, 4, 4, 7));
        hoja.setColumnWidth(3, 85 * 256);
        HashMap<Integer, CellStyle> estilosExcel = iniciarEstilosExcel(wb);

        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/logo.png"));
            ByteArrayOutputStream imgLogo_bytes = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imgLogo_bytes);
            imgLogo_bytes.flush();

            HSSFPatriarch drawing = hoja.createDrawingPatriarch();
            ClientAnchor my_anchor = new HSSFClientAnchor();
            my_anchor.setCol1(1);
            my_anchor.setRow1(1);

            HSSFPicture my_picture = drawing.createPicture(my_anchor, wb.addPicture(imgLogo_bytes.toByteArray(), Workbook.PICTURE_TYPE_PNG));
            my_picture.resize(0.12);
        } catch (IOException e) {
            System.err.println(e);
        }

        crearCabeceraExcel(estilosExcel, hoja);
        crearDetallesIzquierdaExcel(estilosExcel, hoja);
        crearDetallesDerechaExcel(estilosExcel, hoja);
        crearContenidoExcel(estilosExcel, hoja);

        return wb;
    }

    public HSSFSheet crearCabeceraExcel(HashMap<Integer, CellStyle> estilosExcel, HSSFSheet hoja) {
        HSSFRow row = hoja.createRow(2);
        row.setHeight((short) 600);
        HSSFCell cell = row.createCell(3);
        cell.setCellValue("Universidad Autónoma de Baja California");
        cell.setCellStyle(estilosExcel.get(4));

        row = hoja.createRow(3);
        row.setHeight((short) 600);
        cell = row.createCell(3);
        cell.setCellValue("Facultad de Ingeniería Mexicali");
        cell.setCellStyle(estilosExcel.get(3));

        row = hoja.createRow(4);
        row.setHeight((short) 600);
        cell = row.createCell(3);
        cell.setCellValue("CONTENIDO TEMÁTICO");
        cell.setCellStyle(estilosExcel.get(3));

        cell = row.createCell(4);
        cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        cell.setCellStyle(estilosExcel.get(3));

        return hoja;
    }

    public HSSFSheet crearDetallesIzquierdaExcel(HashMap<Integer, CellStyle> estilosExcel, HSSFSheet hoja) {
        HSSFRow row = hoja.createRow(6);
        row.setHeight((short) 600);
        HSSFCell cell = row.createCell(3);
        cell.setCellValue("Unidad de aprendizaje:        " + unidadAprendizaje.getUAPnombre());
        cell.setCellStyle(estilosExcel.get(2));

        hoja.addMergedRegion(new CellRangeAddress(6, 6, 4, 5));
        hoja.addMergedRegion(new CellRangeAddress(6, 6, 6, 7));
        cell = row.createCell(4);
        cell.setCellValue("Plan de estudio: ");
        cell.setCellStyle(estilosExcel.get(2));
        cell = row.createCell(6);
        cell.setCellValue(planEstudio.getPESvigenciaPlan());
        cell.setCellStyle(estilosExcel.get(2));

        row = hoja.createRow(7);
        row.setHeight((short) 600);
        cell = row.createCell(3);
        cell.setCellValue("Programa educativo:        " + programaEducativo.getPEDnombre());
        cell.setCellStyle(estilosExcel.get(2));

        cell = row.createCell(4);
        cell.setCellValue("Área de conocimiento: ");
        cell.setCellStyle(estilosExcel.get(2));
        hoja.addMergedRegion(new CellRangeAddress(7, 7, 4, 5));
        cell = row.createCell(6);
        cell.setCellValue(areaConocimiento.getACOnombre());
        hoja.addMergedRegion(new CellRangeAddress(7, 7, 6, 7));
        cell.setCellStyle(estilosExcel.get(2));

        row = hoja.createRow(8);
        row.setHeight((short) 600);
        cell = row.createCell(3);
        cell.setCellValue("Etapa:        " + cTBeanHelper.seleccionarEtapa(etapaSeleccionada));
        cell.setCellStyle(estilosExcel.get(2));

        return hoja;
    }

    public HSSFSheet crearDetallesDerechaExcel(HashMap<Integer, CellStyle> estilosExcel, HSSFSheet hoja) {
        HSSFRow row = hoja.createRow(9);
        row.setHeight((short) 600);
        HSSFCell cell = row.createCell(3);
        cell.setCellValue("Tipo de grupo:        " + cTBeanHelper.obtenerTipoGrupo(tipoG));
        cell.setCellStyle(estilosExcel.get(2));

        cell = row.createCell(4);
        cell.setCellValue("Horas semestre");
        cell.setCellStyle(estilosExcel.get(2));
        cell = row.createCell(5);
        cell.setCellValue("HC");
        cell.setCellStyle(estilosExcel.get(2));
        cell = row.createCell(6);
        cell.setCellValue("HL");
        cell.setCellStyle(estilosExcel.get(2));
        cell = row.createCell(7);
        cell.setCellValue("HT");
        cell.setCellStyle(estilosExcel.get(2));

        row = hoja.createRow(10);
        row.setHeight((short) 600);
        cell = row.createCell(3);
        cell.setCellValue("Version:        " + versionSeleccionada);
        cell.setCellStyle(estilosExcel.get(2));

        cell = row.createCell(4);
        cell.setCellValue("" + totalHoras);
        cell.setCellStyle(estilosExcel.get(1));
        cell = row.createCell(5);
        cell.setCellValue("" + horasClase);
        cell.setCellStyle(estilosExcel.get(1));
        cell = row.createCell(6);
        cell.setCellValue("" + horasTaller);
        cell.setCellStyle(estilosExcel.get(1));
        cell = row.createCell(7);
        cell.setCellValue(horasLaboratorio);
        cell.setCellStyle(estilosExcel.get(1));

        return hoja;
    }

    public HSSFSheet crearContenidoExcel(HashMap<Integer, CellStyle> estilosExcel, HSSFSheet hoja) {
        HSSFRow row = hoja.createRow(12);
        row.setHeight((short) 600);
        HSSFCell cell = row.createCell(3);
        cell.setCellValue("Nombre de la unidad");
        cell.setCellStyle(estilosExcel.get(1));
        cell = row.createCell(4);
        cell.setCellValue("Horas");
        cell.setCellStyle(estilosExcel.get(1));
        cell = row.createCell(6);
        cell.setCellValue("Porcentaje de horas");
        hoja.addMergedRegion(new CellRangeAddress(12, 12, 6, 7));
        cell.setCellStyle(estilosExcel.get(1));
        int r = 13;

        for (NodoCT nodo : cTBeanHelper.getListaPDF()) {
            hoja.addMergedRegion(new CellRangeAddress(r, r, 4, 5));
            hoja.addMergedRegion(new CellRangeAddress(r, r, 6, 7));
            row = hoja.createRow(r);
            cell = row.createCell(3);
            r++;

            switch (nodo.getTipo()) {
                case 0:
                case 3:
                case 4:
                    cell.setCellValue("" + nodo.getNumero() + ".- " + nodo.getNombre());
                    cell.setCellStyle(estilosExcel.get(2));
                    cell = row.createCell(4);
                    cell.setCellValue("" + cTBeanHelper.horasAFormato(nodo.getHoras()));
                    cell.setCellStyle(estilosExcel.get(2));
                    cell = row.createCell(6);
                    cell.setCellValue("" + cTBeanHelper.dosDecimales(nodo.getPorcentajeAvance()));
                    cell.setCellStyle(estilosExcel.get(2));
                    break;
                case 1:
                    cell.setCellValue("     " + nodo.getNumero() + ".- " + nodo.getNombre());
                    cell.setCellStyle(estilosExcel.get(1));
                    cell = row.createCell(4);
                    cell.setCellValue("" + cTBeanHelper.horasAFormato(nodo.getHoras()));
                    cell.setCellStyle(estilosExcel.get(1));
                    cell = row.createCell(6);
                    cell.setCellValue("" + cTBeanHelper.dosDecimales(nodo.getPorcentajeAvance()) + "%");
                    break;
                default:
                    cell.setCellValue("        " + nodo.getNumero() + ".- " + nodo.getNombre());
                    cell.setCellStyle(estilosExcel.get(5));
                    cell = row.createCell(4);
                    cell.setCellValue("" + cTBeanHelper.horasAFormato(nodo.getHoras()));
                    cell.setCellStyle(estilosExcel.get(5));
                    cell = row.createCell(6);
                    cell.setCellValue("" + cTBeanHelper.dosDecimales(nodo.getPorcentajeAvance()) + "%");
                    cell.setCellStyle(estilosExcel.get(5));
            }
        }

        return hoja;
    }

    public HashMap<Integer, CellStyle> iniciarEstilosExcel(HSSFWorkbook wb) {
        HashMap<Integer, CellStyle> estilosExcel = new HashMap();

        CellStyle estilo1 = wb.createCellStyle();
        estilo1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        estilo1.setWrapText(true);
        estilosExcel.put(1, estilo1);

        CellStyle estilo2 = wb.createCellStyle();
        estilo2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        org.apache.poi.ss.usermodel.Font cellFont = wb.createFont();
        cellFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        estilo2.setFont(cellFont);
        estilo2.setWrapText(true);
        estilosExcel.put(2, estilo2);

        CellStyle estilo3 = wb.createCellStyle();
        estilo3.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        org.apache.poi.ss.usermodel.Font cellFont22 = wb.createFont();
        cellFont22.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        estilo3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estilo3.setFont(cellFont22);
        estilo3.setWrapText(true);
        estilosExcel.put(3, estilo3);

        HSSFCellStyle estilo4 = wb.createCellStyle();
        HSSFFont f = wb.createFont();
        f.setFontName(HSSFFont.FONT_ARIAL);
        f.setFontHeightInPoints((short) 20);
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        f.setColor(HSSFColor.GREEN.index);
        estilo4.setFont(f);
        estilo4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estilosExcel.put(4, estilo4);

        CellStyle estilo5 = wb.createCellStyle();
        estilo5.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        org.apache.poi.ss.usermodel.Font cellFont3 = wb.createFont();
        cellFont3.setItalic(true);
        estilo5.setFont(cellFont3);
        estilo5.setWrapText(true);
        estilosExcel.put(5, estilo5);

        return estilosExcel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public CatalogoCTBeanHelper getCTBeanHelper() {
        return cTBeanHelper;
    }

    public Programaeducativo getPrograEdu() {
        return prograEdu;
    }

    public void setPrograEdu(Programaeducativo prograEdu) {
        this.prograEdu = prograEdu;
    }

    public void setCTBeanHelper(CatalogoCTBeanHelper cTBeanHelper) {
        this.cTBeanHelper = cTBeanHelper;
    }

    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public Planestudio getSelectedPlanestudio() {
        return planEstudio;
    }

    public void setSelectedPlanestudio(Planestudio selectedPlanestudio) {
        this.planEstudio = selectedPlanestudio;
    }

    public Areaconocimiento getSelectedAreaconocimiento() {
        return areaConocimiento;
    }

    public void setSelectedAreaconocimiento(Areaconocimiento selectedAreaconocimiento) {
        this.areaConocimiento = selectedAreaconocimiento;
    }

    public Unidadaprendizaje getSelectedUnidadaprendizaje() {
        return unidadAprendizaje;
    }

    public void setSelectedUnidadaprendizaje(Unidadaprendizaje selectedUnidadaprendizaje) {
        this.unidadAprendizaje = selectedUnidadaprendizaje;
    }

    public TreeNode getSelectedNode() {
        return nodoSeleccionado;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.nodoSeleccionado = selectedNode;
    }

    public TreeNode getUnidades() {
        return nodosContenidoTematico;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo lista">
    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }

    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    public List<Areaconocimiento> getListaAreaConocimiento() {
        return listaAreaConocimiento;
    }

    public void setListaAreaConocimiento(List<Areaconocimiento> listaAreaConocimiento) {
        this.listaAreaConocimiento = listaAreaConocimiento;
    }

    public List<Unidadaprendizaje> getListaUnidadAprendizaje() {
        return listaUnidadAprendizaje;
    }

    public void setListaUnidadAprendizaje(List<Unidadaprendizaje> listaUnidadAprendizaje) {
        this.listaUnidadAprendizaje = listaUnidadAprendizaje;
    }

    public List<Integer> getListaVersiones() {
        return listaVersiones;
    }

    public void setListaVersiones(List<Integer> listaVersiones) {
        this.listaVersiones = listaVersiones;
    }

    public List<Planestudio> getDestinoListaPES() {
        return destinoListaPES;
    }

    public void setDestinoListaPES(List<Planestudio> destinoListaPES) {
        this.destinoListaPES = destinoListaPES;
    }

    public List<NodoCT> getListaNodosUnidad() {
        return listaNodosUnidad;
    }

    public void setListaNodosUnidad(List<NodoCT> listaNodosUnidad) {
        this.listaNodosUnidad = listaNodosUnidad;
    }

    public List<NodoCT> getListaNodosTema() {
        return listaNodosTema;
    }

    public void setListaNodosTema(List<NodoCT> listaNodosTema) {
        this.listaNodosTema = listaNodosTema;
    }

    public List<NodoCT> getListaNodoCT() {
        return listaNodoCT;
    }

    public void setListaNodoCT(List<NodoCT> listaNodoCT) {
        this.listaNodoCT = listaNodoCT;
    }

    public List<Unidadaprendizaje> getDestinoListaUPA() {
        return destinoListaUPA;
    }

    public void setDestinoListaUPA(List<Unidadaprendizaje> destinoListaUPA) {
        this.destinoListaUPA = destinoListaUPA;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos String">
    public String getSeleccionarUnidadAprendizaje() {
        return seleccionarUA;
    }

    public void setSeleccionarUnidadAprendizaje(String seleccionarUA) {
        this.seleccionarUA = seleccionarUA;
    }

    public String getDestinoPEDNombre() {
        return destinoPEDNombre;
    }

    public void setDestinoPEDNombre(String destinoPEDNombre) {
        this.destinoPEDNombre = destinoPEDNombre;
    }

    public String getDestinoUAPClave() {
        return destinoUAPClave;
    }

    public void setDestinoUAPClave(String destinoUAPClave) {
        this.destinoUAPClave = destinoUAPClave;
    }

    public String getDestinoUAPNombre() {
        return destinoUAPNombre;
    }

    public void setDestinoUAPNombre(String destinoUAPNombre) {
        this.destinoUAPNombre = destinoUAPNombre;
    }

    public String getTipoUnidadAprendizajeClonar() {
        return tipoUnidadAprendizajeClonar;
    }

    public void setTipoUnidadAprendizajeClonar(String tipoUnidadAprendizajeClonar) {
        this.tipoUnidadAprendizajeClonar = tipoUnidadAprendizajeClonar;
    }

    public String getTipoClonacion() {
        return tipoClonacion;
    }

    public void setTipoClonacion(String tipoClonacion) {
        this.tipoClonacion = tipoClonacion;
    }

    public String getProgramaEducativoVER() {
        return programaEducativoVER;
    }

    public void setProgramaEducativoVER(String programaEducativoVER) {
        this.programaEducativoVER = programaEducativoVER;
    }

    public String getPlanEstudioVER() {
        return planEstudioVER;
    }

    public void setPlanEstudioVER(String planEstudioVER) {
        this.planEstudioVER = planEstudioVER;
    }

    public String getUAPVER() {
        return UAPVER;
    }

    public void setUAPVER(String UAPVER) {
        this.UAPVER = UAPVER;
    }

    public String getVerActual() {
        return verActual;
    }

    public void setVerActual(String verActual) {
        this.verActual = verActual;
    }

    public String getTipoVersClase() {
        return tipoVersClase;
    }

    public void setTipoVersClase(String tipoVersClase) {
        this.tipoVersClase = tipoVersClase;
    }

    public String getTipoVersTaller() {
        return tipoVersTaller;
    }

    public void setTipoVersTaller(String tipoVersTaller) {
        this.tipoVersTaller = tipoVersTaller;
    }

    public String getTipoVersLab() {
        return tipoVersLab;
    }

    public void setTipoVersLab(String tipoVersLab) {
        this.tipoVersLab = tipoVersLab;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getDestinoPEDId() {
        return destinoPEDId;
    }

    public void setDestinoPEDId(String destinoPEDId) {
        this.destinoPEDId = destinoPEDId;
    }

    public String getDestinoPESId() {
        return destinoPESId;
    }

    public void setDestinoPESId(String destinoPESId) {
        this.destinoPESId = destinoPESId;
    }

    public String getOrigenUAPClave() {
        return origenUAPClave;
    }

    public void setOrigenUAPClave(String origenUAPClave) {
        this.origenUAPClave = origenUAPClave;
    }

    public String getOrigenPEDNombre() {
        return origenPEDNombre;
    }

    public void setOrigenPEDNombre(String origenPEDNombre) {
        this.origenPEDNombre = origenPEDNombre;
    }

    public String getOrigenPESVigenciaPlan() {
        return origenPESVigenciaPlan;
    }

    public void setOrigenPESVigenciaPlan(String origenPESVigenciaPlan) {
        this.origenPESVigenciaPlan = origenPESVigenciaPlan;
    }

    public String getOrigenUAPNombre() {
        return origenUAPNombre;
    }

    public void setOrigenUAPNombre(String origenUAPNombre) {
        this.origenUAPNombre = origenUAPNombre;
    }

    public boolean isDesactivarListaVersiones() {
        return listaVersionesDesactivada;
    }

    public void setDesactivarListaVersiones(boolean desactivarListaVersiones) {
        this.listaVersionesDesactivada = desactivarListaVersiones;
    }

    public String getEstiloHorasC() {
        return estiloHorasC;
    }

    public void setEstiloHorasC(String estiloHorasC) {
        this.estiloHorasC = estiloHorasC;
    }

    public String getEstiloHorasL() {
        return estiloHorasL;
    }

    public void setEstiloHorasL(String estiloHorasL) {
        this.estiloHorasL = estiloHorasL;
    }

    public String getEstiloHorasT() {
        return estiloHorasT;
    }

    public void setEstiloHorasT(String estiloHorasT) {
        this.estiloHorasT = estiloHorasT;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de variables primitivas">
    public int getIDCATALOGOADMICONTENIDO() {
        return IDCATALOGOADMICONTENIDO;
    }

    public boolean isClonar() {
        return clonacionDesactivada;
    }

    public void setClonar(boolean clonar) {
        this.clonacionDesactivada = clonar;
    }

    public boolean isAbleOrigenSomPES() {
        return ableOrigenSomPES;
    }

    public void setAbleOrigenSomPES(boolean ableOrigenSomPES) {
        this.ableOrigenSomPES = ableOrigenSomPES;
    }

    public boolean isAbleOrigenSomUAP() {
        return ableOrigenSomUAP;
    }

    public void setAbleOrigenSomUAP(boolean ableOrigenSomUAP) {
        this.ableOrigenSomUAP = ableOrigenSomUAP;
    }

    public boolean isAbleBotonClonar() {
        return ableBotonClonar;
    }

    public void setAbleBotonClonar(boolean ableBotonClonar) {
        this.ableBotonClonar = ableBotonClonar;
    }

    public boolean isAbleTipoClonacion() {
        return ableTipoClonacion;
    }

    public void setAbleTipoClonacion(boolean ableTipoClonacion) {
        this.ableTipoClonacion = ableTipoClonacion;
    }

    public boolean isDesactivarVersionamiento() {
        return versionamientoDesactivado;
    }

    public void setDesactivarVersionamiento(boolean desactivarVersionamiento) {
        this.versionamientoDesactivado = desactivarVersionamiento;
    }

    public boolean getAbleBotonVers() {
        return ableBotonVers;
    }

    public void setAbleBotonVers(boolean ableBotonVers) {
        this.ableBotonVers = ableBotonVers;
    }

    public boolean getAbleEliminarVersion() {
        return ableEliminarVersion;
    }

    public void setAbleEliminarVersion(boolean ableEliminarVersion) {
        this.ableEliminarVersion = ableEliminarVersion;
    }

    public int getHc() {
        return horasClase;
    }

    public int getHt() {
        return horasTaller;
    }

    public int getHl() {
        return horasLaboratorio;
    }

    public int getCalculoHoras() {
        return totalHoras;
    }

    public boolean isFiltroPE() {
        return isAdmin;
    }

    public void setFiltroPE(boolean filtroPE) {
        this.isAdmin = filtroPE;
    }

    public int getSelectedEtapa() {
        return etapaSeleccionada;
    }

    public void setSelectedEtapa(int SelectedEtapa) {
        this.etapaSeleccionada = SelectedEtapa;
    }

    public int getVersionSeleccionada() {
        return versionSeleccionada;
    }

    public void setVersionSeleccionada(int versionSeleccionada) {
        this.versionSeleccionada = versionSeleccionada;
    }

    public boolean isImprimir() {
        return impresionDesactivada;
    }

    public void setImprimir(boolean imprimir) {
        this.impresionDesactivada = imprimir;
    }

    public int getSelectedPE() {
        return programaESeleccionado;
    }

    public void setSelectedPE(int selectedPE) {
        this.programaESeleccionado = selectedPE;
    }

    public int getSelectedPlanE() {
        return planESeleccionado;
    }

    public void setSelectedPlanE(int selectedPlanE) {
        this.planESeleccionado = selectedPlanE;
    }

    public int getSelectedAreaCon() {
        return areaConSeleccionada;
    }

    public void setSelectedAreaCon(int selectedAreaCon) {
        this.areaConSeleccionada = selectedAreaCon;
    }

    public int getSelectedUnidad() {
        return unidadSeleccionada;
    }

    public void setSelectedUnidad(int selectedUnidad) {
        this.unidadSeleccionada = selectedUnidad;
    }

    public int getSelectedTema() {
        return temaSeleccionado;
    }

    public void setSelectedTema(int selectedTema) {
        this.temaSeleccionado = selectedTema;
    }

    //</editor-fold>
    public String getPreNumero() {
        return preNumero;
    }

    public void setPreNumero(String preNumero) {
        this.preNumero = preNumero;
    }

    public String getHrsSolicitadas() {
        return hrsSolicitadas;
    }

    public void setHrsSolicitadas(String hrsSolicitadas) {
        this.hrsSolicitadas = hrsSolicitadas;
    }

    public double getHrsDisponiblesConv() {
        return hrsDisponiblesConv;
    }

    public void setHrsDisponiblesConv(double hrsDisponiblesConv) {
        this.hrsDisponiblesConv = hrsDisponiblesConv;
    }

    public boolean isCamposDeshabilitados() {
        return camposDeshabilitados;
    }

    public void setCamposDeshabilitados(boolean camposDeshabilitados) {
        this.camposDeshabilitados = camposDeshabilitados;
    }

    public NodoCT getContenidoSeleccionado() {
        return contenidoSeleccionado;
    }

    public void setContenidoSeleccionado(NodoCT contenidoSeleccionado) {
        this.contenidoSeleccionado = contenidoSeleccionado;
    }

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public void setMensajeConfirm(String mensajeConfirm) {
        this.mensajeConfirm = mensajeConfirm;
    }
}
