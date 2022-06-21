package mx.avanti.siract.ui;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.helper.CampusBeanHelper;
import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class CampusBeanUI implements Serializable {

    private CampusBeanHelper campusBeanHelper = null;
    private List<Campus> listaFiltrada;
    private String busqueda = "";
    private String header;
    private String boton;
    private String deshabilitar;
    private String message;
    private String deshabilitarBoton = "true";
    private boolean Bandera;
    private String mensajeEliminar;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private Object FacessContext;
    public Boolean imprimir;
    public String nomImg;
    
    private final int IDCATALOGOADMICAMPUS = 14;

    public int getIDCATALOGOADMICAMPUS() {
        return IDCATALOGOADMICAMPUS;
    }
        

    public String getMensajeEliminar() {
        return mensajeEliminar;
    }

    public void setMensajeEliminar(String mensajeEliminar) {
        this.mensajeEliminar = mensajeEliminar;
    }

    public boolean isBandera() {
        return Bandera;
    }

    public void setBandera(boolean Bandera) {
        this.Bandera = Bandera;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }

    public void setDeshabilitarBoton(String deshabilitarTitle) {
        this.deshabilitarBoton = deshabilitarTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(final String header) {
        this.header = header;
    }

    public void changeHeader(String titulo) {
        setHeader(titulo);
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    public String getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public CampusBeanUI() {
        init();
    }

    private void init() {
        campusBeanHelper = new CampusBeanHelper();
    }

    public CampusBeanHelper getCampusBeanHelper() {
        return campusBeanHelper;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<Campus> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Campus> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    //Post constructor necesario para usar ManagedProperty
//    @PostConstruct
//    public void postConstructor() {
//        programaEducativoBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
//        programaEducativoBeanHelper.setUsuario(loginBean.getLogueado());
//        System.out.println("rol desde el BeanUI: " + loginBean.getSeleccionado());
//        System.out.println("id del usuario desde login " + loginBean.getLogueado().getUsuid());
//        
//        if(programaEducativoBeanHelper.getRolSeleccionado().equalsIgnoreCase("Administrador")){
//            renderUA="true";
//        }else{
//            renderUA="false";
//        }
//       
//    }
    /**
     * Método para agregar al atributo listaFiltrada una lista con todos 
     * los campus que coincidan con el atributo busqueda 
     */
    public void filtrado() {
        List<Rol> list = null;
       // list= loginBean.getLogueado().getRolList();
        list= loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        System.out.println(seleccionado + "Metodo filtrado");
        String catalogo = "Administración de campus";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        listaFiltrada = campusBeanHelper.filtrado("Nombre", busqueda);
        if(listaFiltrada.size()!=0){
            imprimir = true;
        }
        else{
            imprimir = false;
        }
    }
    public boolean isImprimir() {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^IMPRIMIR="+imprimir);
        return imprimir;
    }
    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }
    /**
     * Método para cambiar la imagenPdf de habilitado a inhabilitado
     * @return String ruta de la imagen
     */
    public String imagenPDF(){
        if(imprimir == true){
            nomImg="imagenes/pdf3.png";
        }
        if(imprimir == false){
            nomImg="imagenes/pdf3false.png";
        }
        return nomImg;
    }
    /**
     * Método para cambiar el nombre de la cabecera a "Agregar Campus" y 
     * crear un nuevo Campus
     */
    public void nuevo() {
        cabecera(1);
        Campus c = new Campus();
        campusBeanHelper.setCampus(c);
    }
    /**
     * Modifica la cabecera a "Modificar Campus", y guarda el campus seleccionado
     */
    public void modificar() {
        cabecera(3);
        try {
            if (campusBeanHelper.getSelectedCampus().getCAMid()> 0) {
                campusBeanHelper.setCampus(campusBeanHelper.getSelectedCampus());
            }
        } catch (NullPointerException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningún registro");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    /**
     * Método para Cambiar cabeceza a "Eliminar Campus" y Obtener el campus
     * Seleccionado
     */
    public void eliminar() {
        cabecera(2);
        try {
            if (campusBeanHelper.getSelectedCampus().getCAMid()> 0) {
                campusBeanHelper.setCampus(campusBeanHelper.getSelectedCampus());
            }
        } catch (NullPointerException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningún registro");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    /**
     * Método que ejecuta las funcionalidades de Modificar Campus y  Agregar Campus 
     * @return String vacío
     */
    public String onClickSubmit() {
        setMensajeConfirmacion();
        if (deshabilitar.equals("false")) {
            if (validacionVacio()) {
                if (header.equals("Modificar Campus")) {
                    PrimeFaces.current().executeScript("PF('conDlgElim').show()");
//                    FacesContext context = FacesContext.getCurrentInstance();
//                    context.addMessage(null, new FacesMessage("","Se guardó correctamente"));         
//        
//                    campusBeanHelper.getCampusDelegate().agregarCampus(campusBeanHelper.getCampus());
//                    campusBeanHelper.setCampus(new Campus());
//                    campusBeanHelper.setSelectedCampus(new Campus());
//                    RequestContext.getCurrentInstance().execute("PF('dlgCapturaCampus').hide()");
                } else if (header.equals("Agregar Campus")) {
                    if (validarRepetido() == true) {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));

                        //campusBeanHelper.getCampusDelegate().agregarCampus(campusBeanHelper.getCampus());
                        campusBeanHelper.agregarCampus(campusBeanHelper.getCampus());
                        campusBeanHelper.setSelectedCampus(new Campus());
                        campusBeanHelper.setCampus(new Campus());
                    } else {

                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación", "El Campus ya existe");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                }
            }
        } else {
            PrimeFaces.current().executeScript("PF('conDlgElim').show()");
        }
        filtrado();
        return "";
    }
    /**
     * Método que cambia la cabecera, Sí i=1 header="Agregar Campus",
     * Sí i=2 header="Eliminar Campus" y Sí i=3 header="Modificar Campus"
     * @param i tipo int
     * @return header tipo String
     */
    public String cabecera(int i) {
        if (i == 1) {
            setHeader("Agregar Campus");
            deshabilitar = "false";
        } else {
            if (i == 2) {
                setHeader("Eliminar Campus");
                deshabilitar = "true";
            } else {
                if (i == 3) {
                    header = "Modificar Campus";
                    deshabilitar = "false";
                }
            }
        }
        return header;
    }
    /**
     * Método que valida si el Campus (Nombre del Campus) esta vacío
     * Sí esta vacío regresa "false", Sí no esta vacío regresa true
     * @return boolean 
     */
    public boolean validacionVacio() {
        if (campusBeanHelper.getCampus().getCAMnombre().isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "Llenar campo(s) faltantes");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        } else {
            return true;
        }
    }
    /**
     *Método para mandar mensajes de confirmación
     */
    public void eliminConfir() {
        if (header.equals("Eliminar Campus")) {
            FacesContext context = FacesContext.getCurrentInstance();
            

            //campusBeanHelper.getCampusDelegate().eliminarCampus(campusBeanHelper.getSelectedCampus());
            if(campusBeanHelper.getCampus().getUnidadacademicaList().size()!=0){
                
                System.out.println(campusBeanHelper.getCampus().getUnidadacademicaList().size());
                context.addMessage(null, new FacesMessage("", "Campus asignado a unidades académicas"));
                PrimeFaces.current().executeScript("PF('conDlgElim').hide()");
                PrimeFaces.current().executeScript("PF('dlgCapturaCampus').hide()");
            }
            else{
                campusBeanHelper.eliminarCampus(campusBeanHelper.getSelectedCampus());
                campusBeanHelper.setSelectedCampus(new Campus());
                campusBeanHelper.setCampus(new Campus());
                context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
                PrimeFaces.current().executeScript("PF('conDlgElim').hide()");
                PrimeFaces.current().executeScript("PF('dlgCapturaCampus').hide()");
            }
        } else {
            if (header.equals("Modificar Campus")) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                //campusBeanHelper.getCampusDelegate().agregarCampus(campusBeanHelper.getCampus());
                campusBeanHelper.updateCampus(campusBeanHelper.getCampus());
                PrimeFaces.current().executeScript("PF('conDlgElim').hide()");
            }
        }
        filtrado();
    }
    public void habilitarBotons() {
        deshabilitarBoton = "false";
    }
    public void deshabilitarBotons() {
        deshabilitarBoton = "true";
        limpiar();
    }
    /**
     * Método que valida si el nombre de un campus esta repetido
     * Sí esta repetido regresa "false", Si no esta repretido regresa "true"
     * @return Bandera tipo boolean
     */
    public boolean validarRepetido() {
        String campus = campusBeanHelper.getCampus().getCAMnombre();
        Bandera = campusBeanHelper.Validar(campus);
        return Bandera;
    }
    /**
     * Método para limpiar el atributo selectedCampus
     */
    public void limpiar() {
        campusBeanHelper.setSelectedCampus(new Campus());
    }
    public void setMensajeConfirmacion() {
        if (deshabilitar.equals("true")) {
            mensajeEliminar = "¿Está seguro de eliminar el registro?";
        } else {
            if (header.equals("Modificar Campus")) {
                mensajeEliminar = "¿Está seguro de modificar el registro?";
            }
        }
        PrimeFaces.current().ajax().update("confirmid");
    }
    /**
     * Método que crea un documento pdf con la medida A4
     * @param document documento que envia Campus.xhtml a un PDF
     */
    public void preProcessPDF(Object document) {
        final Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
    }
    /**
     * Método para crear un pdf con el catálogo de campus
     * @param document documento que envia Campus.xhtml a un PDF
     */
    public void postProcessPDF(Object document) throws DocumentException {
        final Document pdf = (Document) document;
        pdf.open();

        //Bloque para ingresar el titulo al documento
        PdfPTable pdfTabletitulo = new PdfPTable(2);
        pdfTabletitulo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        //agregamos los titulos al pdf con objetos tipo Paragrph.
        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 22, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("Catálogo de Campus", FontFactory.getFont(FontFactory.TIMES, 18,com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(fecha(),FontFactory.getFont(FontFactory.TIMES, 16,com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        //Alineado a los textos
        UABC.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);                
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(73);
        Paragraph esp = new Paragraph(" ");
        pdf.add(UABC);
        pdf.add(esp);
        pdf.add(titulo);
        pdf.add(fecha);
        pdf.add(esp);
        pdf.add(esp);
        //agregamos espacios para darle formato al documento.
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
        pdfTabletitulo.addCell(new Paragraph(""));

        pdfTabletitulo.setHorizontalAlignment(25);
        float[] columnWidthsss = new float[]{4f, 28};
        pdfTabletitulo.setWidths(columnWidthsss);
        pdf.add(pdfTabletitulo);//agregamos el bloque al documento

        //Agregamos la imagen del logo de la uabc
        try {
            Image imagenLogo = Image.getInstance(RACTBeanUI.class.getResource("imagenes/logo.jpg"));
            //Posicion de imagen (Horizontal, Vertiacal)
            imagenLogo.setAbsolutePosition(120f, 460f);
            //Tamaño de imagen (Ancho, largo)
            imagenLogo.scaleAbsolute(90, 120);
            pdf.add(imagenLogo);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }
        //Bloque de Contenido.
        
        //Columnas de las tablas.
        PdfPTable pdftablecont = new PdfPTable(2);
        pdftablecont.addCell(new Paragraph("ID", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.setHorizontalAlignment(15);

        float[] columnWidthsss3 = new float[]{10f, 10f};
        pdftablecont.setWidths(columnWidthsss3);
        pdf.add(pdftablecont);
        
        //Filas que contendra la tabla.
        PdfPTable pdftablecont2 = new PdfPTable(2);
        for (int i = 0; i < listaFiltrada.size(); i++) {
            //Hacer una consulta para llenar la tabla, por columna.
            pdftablecont2.addCell(new Paragraph(String.valueOf(listaFiltrada.get(i).getCAMid())));
            pdftablecont2.addCell(new Paragraph(String.valueOf(listaFiltrada.get(i).getCAMnombre())));
        }
        float[] columnWidthsss4 = new float[]{10f, 10f};
        pdftablecont2.setWidths(columnWidthsss4);
        pdf.add(pdftablecont2);
    }
    /**
     * Método que crea un String con la fecha actual
     * @return String con la fecha actual.
     */
    public String fecha(){ 
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");        
        String fecha=formatoFecha.format(new Date());        
        return fecha;
    }   
    /**
     * Crea un String con el valor "Campus_" + la fecha actual
     * @return String creado
     */
    public String nombre(){
        String nombre = "Campus_"+ fecha();
        return nombre;
    }
}