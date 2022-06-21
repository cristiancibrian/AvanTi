package mx.avanti.siract.ui;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.helper.AreaAdministrativaBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Paco
 */
@ManagedBean
@ViewScoped
public class AreaAdministrativaBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final int IDCATALOGOADMIAREAADMINISTRATIVA = 18;

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">
    private AreaAdministrativaBeanHelper areaAdministrativaHelper;
    private Areaadministrativa areaAdministrativa;
    private StreamedContent file;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo lista">
    private List<Areaadministrativa> listaAreaAdSeleccion;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos String">
    private String cabecera;
    private String deshabilitar;
    private String mensajeConfirmacion;
    private String mensajeAccionExitosa;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables primitivas">
    private int areaAdSeleccID;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor y postConstructor">
    public AreaAdministrativaBeanUI() {
        areaAdministrativaHelper = new AreaAdministrativaBeanHelper();
        areaAdministrativa = new Areaadministrativa();
        listaAreaAdSeleccion = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {
        areaAdministrativaHelper.setRol(loginBean.getSeleccionado());
        areaAdministrativaHelper.setUsuario(loginBean.getLogueado());
        loginBean.TienePermiso(loginBean.getLogueado().getRolList(), loginBean.getSeleccionado(), "Administración de área administrativa");
        iniciarVariables();
    }
    //</editor-fold>

    public void iniciarAgregado() {
        cabecera = "Agregar";
        deshabilitar = "false";
        mensajeConfirmacion = "¿Está seguro de agregar el área de conocimiento?";
        mensajeAccionExitosa = "Área administrativa agregada correctamente.";
        listaAreaAdSeleccion = new ArrayList<>();
    }

    public void iniciarEliminado() {
        cabecera = "Eliminar";
        deshabilitar = "true";
        mensajeConfirmacion = "¿Está seguro de eliminar el área de conocimiento?";
        mensajeAccionExitosa = "Área administrativa eliminada correctamente.";
        areaAdministrativa = listaAreaAdSeleccion.get(0);
    }

    public void iniciarActualizado() {
        cabecera = "Actualizar";
        deshabilitar = "false";
        mensajeConfirmacion = "¿Está seguro de actualizar el área de conocimiento?";
        mensajeAccionExitosa = "Área administrativa actualizada correctamente.";
        areaAdministrativa = listaAreaAdSeleccion.get(0);
    }

    /**
     * Método que ejecuta un agregado, eliminado o actualizado al presionar el
     * botón de aceptar.
     */
    public void ejecutarAceptado() {
        switch (cabecera) {
            case "Agregar":
                areaAdministrativaHelper.agregarAreaAdministrativa(areaAdministrativa);
                break;
            case "Eliminar":
                areaAdministrativaHelper.eliminarAreaAdministrativa(areaAdministrativa);
                break;
            default:
                areaAdministrativaHelper.actualizarAreaAdministrativa(areaAdministrativa);
                listaAreaAdSeleccion.set(listaAreaAdSeleccion.indexOf(areaAdministrativa), areaAdministrativa);
        }

        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensajeAccionExitosa));
        cerrarDialogLlenarFormulario();
    }

    /**
     * Método que cierra el dialog si ya no hay mas unidades de aprendizaje en
     * la lista seleccionada, o llena el formulario si aun hay unidades en la
     * lista.
     */
    public void cerrarDialogLlenarFormulario() {
        if (listaAreaAdSeleccion.size() > 1) {
            if (cabecera.equals("Eliminar")) {
                listaAreaAdSeleccion.remove(areaAdministrativa);
                areaAdministrativa = listaAreaAdSeleccion.get(0);
                areaAdSeleccID = areaAdministrativa.getAADid();
                ejecutarMultiseleccion();
            }

            PrimeFaces.current().ajax().update("form2:cap", "form2:registrosSelecs");
        } else {
            iniciarVariables();
            PrimeFaces.current().ajax().update("formAreaAd:areaad");
            PrimeFaces.current().executeScript("PF('dlg').hide()");
        }
    }

    /**
     * Método que se ejecuta cuando se cambia una unidad de aprendizaje
     * seleccionada, al actualizar o eliminar mas de un registro.
     */
    public void ejecutarMultiseleccion() {
        areaAdministrativa = areaAdministrativaHelper.buscarAreaAdministrativaPorId(areaAdSeleccID);
    }

    public void iniciarVariables() {
        areaAdSeleccID = 0;
        areaAdministrativa = new Areaadministrativa();
        areaAdministrativa.setProgramaEducativoPEDid(new Programaeducativo(0));
        listaAreaAdSeleccion = new ArrayList<>();
        areaAdministrativaHelper.filtrarPorRol();
    }

    public boolean validarAlAgregarOActualizar() {
        String mensajeCamposVacios = "";

        mensajeCamposVacios += areaAdministrativa.getProgramaEducativoPEDid().getPEDid() == 0 ? "<br>- Programa Educativo." : "";
        mensajeCamposVacios += areaAdministrativa.getAADnombre().isEmpty() ? "<br>- Nombre área administrativa." : "";

        if (!mensajeCamposVacios.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Campos Obligatorios: " + mensajeCamposVacios));
            return false;
        } else if (areaAdministrativaHelper.validarNombrerRepetido(areaAdministrativa)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre de área administrativa esta repetido."));
            return false;
        }

        return true;
    }

    public boolean validarAlEliminar() {
        if (!areaAdministrativa.getCoordinadorareaadministrativaList().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El área tiene un coordinador asignado."));
            return false;
        }

        return true;
    }

    /**
     * Método que valida que no se encuentren espacios en blanco.
     */
    public void validarAceptado() {
        boolean valido;

        if (cabecera.equals("Agregar") || cabecera.equals("Actualizar")) {
            valido = validarAlAgregarOActualizar();
        } else {
            valido = validarAlEliminar();
        }

        if (valido) {
            PrimeFaces.current().executeScript("PF('confirmacion').show()");
        }
    }

    public boolean deshabilitarMenu() {
        return listaAreaAdSeleccion == null || listaAreaAdSeleccion.size() < 1 || listaAreaAdSeleccion.isEmpty();
    }

    public boolean mostrarSeleccionAA() {
        return listaAreaAdSeleccion != null && listaAreaAdSeleccion.size() > 1;
    }

    //<editor-fold defaultstate="collapsed" desc="Métodos para imprimir PDF">
    /**
     * documento que se envia a areaadministrativa.xhtml
     *
     * @throws IOException
     * @throws DocumentException
     */
    public void asignarDatosPDF() throws IOException, DocumentException {
        FacesContext context = FacesContext.getCurrentInstance();
        Document pdf = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(pdf, baos);
        pdf.open();

        //Tabla con UABC
        PdfPTable pdfTabletitulo = new PdfPTable(2);
        pdfTabletitulo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        //titulos de PDF
        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 18, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph facultad = new Paragraph("Facultad de Ingeniería Mexicali", FontFactory.getFont(FontFactory.TIMES, 17, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("Catálogo de Áreas Administrativas", FontFactory.getFont(FontFactory.TIMES, 16, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(fecha(), FontFactory.getFont(FontFactory.TIMES, 11, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));

        //alineacion de titulos
        UABC.setAlignment(Element.ALIGN_CENTER);
        facultad.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(73);
        Paragraph esp = new Paragraph(" ");
        pdf.add(UABC);

        try {
            Image imagenLogo = Image.getInstance(getClass().getResource("/logo.png"));
            imagenLogo.setAbsolutePosition(25f, 720f);
            imagenLogo.scaleAbsolute(50, 80);
            pdf.add(imagenLogo);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }
        //se le da formato al documento
        pdf.add(facultad);
        pdf.add(titulo);
        pdf.add(esp);
        pdf.add(fecha);
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

        pdfTabletitulo.setHorizontalAlignment(25);
        float[] columnWidthsss = new float[]{4f, 28};
        pdfTabletitulo.setWidths(columnWidthsss);
        pdf.add(pdfTabletitulo);

        //Tabla Cabezera
        PdfPTable pdftablecabezera = new PdfPTable(2);
        pdftablecabezera.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdftablecabezera2 = new PdfPTable(8);
        pdftablecabezera2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        //Tabla Contenido
        PdfPTable pdftablecont = new PdfPTable(2);
        //columnas de la tabla
        pdftablecont.addCell(new Paragraph("Área administrativa", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(67, 67, 67))));
        pdftablecont.addCell(new Paragraph("Programa educativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(67, 67, 67))));
        pdftablecont.setHorizontalAlignment(15);

        float[] columnWidthsss3 = new float[]{10f, 10f};
        pdftablecont.setWidths(columnWidthsss3);
        pdf.add(pdftablecont);
        PdfPTable pdftablecont3 = new PdfPTable(2);

        //filas que contendra la tabla
        for (Areaadministrativa areaAd : areaAdministrativaHelper.getListaAreaAdFiltrada()) {
            //se llena la tabla por filas
            pdftablecont3.addCell(new Paragraph(String.valueOf(areaAd.getAADnombre())));
            pdftablecont3.addCell(new Paragraph(String.valueOf(areaAd.getProgramaEducativoPEDid().getPEDnombre())));
        }

        float[] columnWidthsss4 = new float[]{10f, 10f};
        pdftablecont3.setWidths(columnWidthsss4);
        pdf.add(pdftablecont3);
        pdf.close();

        //Método para poder generar el documento para descargar.
        writePDFToResponse(context.getExternalContext(), baos, "Area_Administrativa_");
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
            System.err.println(e);
        }
    }

    //metodo para obtener la fecha del sistema
    public String fecha() {
        Date date = new Date();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public AreaAdministrativaBeanHelper getAreaAdministrativaHelper() {
        return areaAdministrativaHelper;
    }

    public void setAreaAdministrativaHelper(AreaAdministrativaBeanHelper areaAdministrativaHelper) {
        this.areaAdministrativaHelper = areaAdministrativaHelper;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Areaadministrativa getAreaAdministrativa() {
        return areaAdministrativa;
    }

    public void setAreaAdministrativa(Areaadministrativa areaAdministrativa) {
        this.areaAdministrativa = areaAdministrativa;
    }

    public StreamedContent getFile() {
        return file;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo lista">
    public List<Areaadministrativa> getListaAreaAdSeleccion() {
        return listaAreaAdSeleccion;
    }

    public void setListaAreaAdSeleccion(List<Areaadministrativa> listaAreaAdSeleccion) {
        this.listaAreaAdSeleccion = listaAreaAdSeleccion;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos String">
    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de variables primitivas">
    public int getIDCATALOGOADMIAREAADMINISTRATIVA() {
        return IDCATALOGOADMIAREAADMINISTRATIVA;
    }

    public int getAreaAdSeleccID() {
        return areaAdSeleccID;
    }

    public void setAreaAdSeleccID(int areaAdSeleccID) {
        this.areaAdSeleccID = areaAdSeleccID;
    }
    //</editor-fold>
}
