package mx.avanti.siract.ui;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.helper.PlanEstudioBeanHelper;
import org.primefaces.PrimeFaces;
/**
 *
 * @author Alan-PC
 */
@ManagedBean
@ViewScoped
public class PlanEstudioBeanUI implements Serializable {
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    
    public LoginBean getLoginBean() {
        return loginBean;
    }
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    private final int IDPERMISOCATALOGOADMIPLANEST = 16;

    public int getIDPERMISOCATALOGOADMIPLANEST() {
        return IDPERMISOCATALOGOADMIPLANEST;
    }
    
    
    
    private PlanEstudioBeanHelper planEstudioBeanHelper = null;
    private List<Planestudio> listaFiltrada;
    private String busqueda = "";
    private String cabecera;
    private String textoBoton;
    private String MensajeVal = "";
    private String mensajeConfirmacion = "";
    private int x = 0;
    private String renderAceptar = "";
    private String nomBotonConfirmDlg = "";
    private String iconoConfirmDlg = "";
    public Boolean imprimir;
    public String nomImg;
    Date date = new Date();
    
    public PlanEstudioBeanUI() {
        init();
    }
    private void init() {
        planEstudioBeanHelper = new PlanEstudioBeanHelper();
    }
    
    public String getIconoConfirmDlg() {
        return iconoConfirmDlg;
    }
    public void setIconoConfirmDlg(String iconoConfirmDlg) {
        this.iconoConfirmDlg = iconoConfirmDlg;
    }
    public String getNomBotonConfirmDlg() {
        return nomBotonConfirmDlg;
    }
    public void setNomBotonConfirmDlg(String nomBotonConfirmDlg) {
        this.nomBotonConfirmDlg = nomBotonConfirmDlg;
    }
    public String getRenderAceptar() {
        return renderAceptar;
    }
    public void setRenderAceptar(String renderAceptar) {
        this.renderAceptar = renderAceptar;
    }
    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }
    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }
    public String getMensajeVal() {
        return MensajeVal;
    }
    public void setMensajeVal(String MensajeVal) {
        this.MensajeVal = MensajeVal;
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
    private String deshabilitar = "";
    public String getBusqueda() {
        return busqueda;
    }
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    public List<Planestudio> getListaFiltrada() {
        return listaFiltrada;
    }
    public void setListaFiltrada(List<Planestudio> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    public PlanEstudioBeanHelper getPlanEstudioBeanHelper() {
        return planEstudioBeanHelper;
    }
    public void setPlanEstudioBeanHelper(PlanEstudioBeanHelper planEstudioBeanHelper) {
        this.planEstudioBeanHelper = planEstudioBeanHelper;
    }
    
    @PostConstruct
    public void postConstructor() {
        System.out.println("PRIMER PRINT DEL POST CONSTRCTUR");
//        profesorBeanHelper.setListaRol(loginBean.Obtenerrol(loginBean.getUsuario().getUsuid()));
        planEstudioBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
        planEstudioBeanHelper.setUsuario(loginBean.getLogueado());
        System.out.println("rol desde el BeanUI: " + loginBean.getSeleccionado());
        System.out.println("id del usuario desde login " + loginBean.getLogueado().getUSUid());
        System.out.println("FINAL PRINT DEL POST CONSTRCTUR");
    }
    /**
     * Método que cambia el nombre de las cabeceras
     * Sí i=1 cabecera="Agregar plan de estudio", Sí i=2 cabecera="Eliminar plan de estudio"
     * y Sí i=3 cabecera="Modificar plan de estudio"
     * @param i tipo int
     * @return cabecera tipo String
     */
    public String header(int i) {
        if (i == 1) {
            cabecera = "Agregar plan de estudio";
            textoBoton = "Aceptar";
            deshabilitar = "false";
        }
        if (i == 2) {
            cabecera = "Eliminar plan de estudio";
            textoBoton = "Aceptar";
            deshabilitar = "true";
        }
        if (i == 3) {
            cabecera = "Modificar plan de estudio";
            textoBoton = "Aceptar";
            deshabilitar = "false";
        }
        return cabecera;
    }
    /**
     * Método que cambia la cabecera a "Agregar plan de estudio" e instancia el
     * planEstudio y el ProgramaEducaivo
     */
    public void nuevo() {
        header(1);
        planEstudioBeanHelper.setPlanEstudio(new Planestudio());
        planEstudioBeanHelper.setProgramaeducativo(new Programaeducativo());
    }
    /**
     * Método que Habilita el botón de modificar
     */
    public void modificar() {
        header(3);
        try {
            if (planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() == 1) {
                planEstudioBeanHelper.setPlanEstudio(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0));
                planEstudioBeanHelper.setProgramaeducativo(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0).getProgramaEducativoPEDid());
            } else {
                if (planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                   // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                } else {
                    planEstudioBeanHelper.setPlanEstudio(new Planestudio());
                    planEstudioBeanHelper.setProgramaeducativo(new Programaeducativo());
                    planEstudioBeanHelper.setPlanEstudio(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0));
                    planEstudioBeanHelper.setProgramaeducativo(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0).getProgramaEducativoPEDid());
                }
            }
        } catch (NullPointerException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
           // RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }
    /**
     * Método que Habilita el boton de eliminar
     */
    public void eliminar() {
        header(2);
        try {
            if (planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() == 1) {
                planEstudioBeanHelper.setPlanEstudio(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0));
                planEstudioBeanHelper.setProgramaeducativo(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0).getProgramaEducativoPEDid());
            } else {
                if (planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                } else {
                    planEstudioBeanHelper.setPlanEstudio(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0));
                    planEstudioBeanHelper.setProgramaeducativo(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0).getProgramaEducativoPEDid());
                }
            }
        } catch (NullPointerException e) {
            planEstudioBeanHelper.setPlanEstudio(new Planestudio());
            planEstudioBeanHelper.setProgramaeducativo(new Programaeducativo());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono; ningún registro");
            //RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, message);


        }
    }
    /**
     * Método que muestra los mensajes de confirmación de Eliminado y Guardado
     */
    public void Confirmacion() {
        if (deshabilitar.equals("true")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
            //planEstudioBeanHelper.getPlanEstudioDelegate().eliminarPlanEstudio(planEstudioBeanHelper.getPlanEstudio());
            planEstudioBeanHelper.eliminarDeLista(planEstudioBeanHelper.getPlanEstudio().getPESid());
            planEstudioBeanHelper.eliminarPlanEstudio(planEstudioBeanHelper.getPlanEstudio());
            planEstudioBeanHelper.setSelecPlanEstudio(new Planestudio());
            planEstudioBeanHelper.setPlanEstudio(new Planestudio());
            planEstudioBeanHelper.setProgramaeducativo(new Programaeducativo());
           // RequestContext.getCurrentInstance().execute("PF('confirmacion').hide()");
            PrimeFaces.current().executeScript("PF('confirmacion').hide()");

            if (planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() == 1) {
                planEstudioBeanHelper.setPlanEstudio(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0));
                planEstudioBeanHelper.setProgramaeducativo(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0).getProgramaEducativoPEDid());
            }
        } else {
            //planEstudioBeanHelper.getPlanEstudioDelegate().agregarPlanEstudio(planEstudioBeanHelper.getPlanEstudio());
            planEstudioBeanHelper.updatePE(planEstudioBeanHelper.getPlanEstudio());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
            //RequestContext.getCurrentInstance().execute("PF('confirmacion').hide()");
            PrimeFaces.current().executeScript("PF('confirmacion').hide()");

        }
        filtrado();
    }
    /**
     * Método que Agregar o modifica registros
     * @return String (No se utiliza)
     */
    public String onClickSubmit() {
        if (deshabilitar.equals("true")) {
            setMensajeConfirmacion();
            //RequestContext.getCurrentInstance().execute("PF('confirmacion').show()");
            PrimeFaces.current().executeScript("PF('confirmacion').show()");

        } else {
            if (planEstudioBeanHelper.getProgramaeducativo().getPEDid()== 0
                    || planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan().isEmpty()
                    || planEstudioBeanHelper.getPlanEstudio().getPEScreditosObligatorios() <= 0
                    || planEstudioBeanHelper.getPlanEstudio().getPEScreditosOptativos() < 0
                    || planEstudioBeanHelper.getPlanEstudio().getPEStotalCreditos() < 0) {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Favor de llenar todos los campos");
               // RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);


            } else {
                MensajeVal = planEstudioBeanHelper.Validar(cabecera);//Se manda a llamar el metodo que nos devolvera un mensaje sobre los campos repetidos
                if (MensajeVal.isEmpty()) {//En caso de que la variable este vacia se le asignara una palabra para represente que no exten campos vacios
                    MensajeVal = "nada";
                }
                // MensajeVal = "nada";
                if (MensajeVal.equals("nada")) {
                    if (cabecera.equals("Agregar plan de estudio")) {
                        HashSet setPE = new HashSet();
                        System.out.println("Antes de enviar mensaje de se guardo");
                        //planEstudioBeanHelper.getPlanEstudioDelegate().agregarPlanEstudio(planEstudioBeanHelper.getPlanEstudio());
                        planEstudioBeanHelper.agregarPlanEstudio(planEstudioBeanHelper.getPlanEstudio());
                        planEstudioBeanHelper.setSelecPlanEstudio(new Planestudio());
                        planEstudioBeanHelper.setPlanEstudio(new Planestudio());
                        planEstudioBeanHelper.setProgramaeducativo(new Programaeducativo());
                        //planEstudioBeanHelper.setProgramaeducativo(planEstudioBeanHelper.getListaSeleccionPlanEstudio().get(0).getProgramaeducativo());
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                        System.out.println("Despues de enviar mensaje de se guardo");
                    } else {
                        setMensajeConfirmacion();
                        if (x > 1) {
                           // RequestContext.getCurrentInstance().execute("PF('confirmacion').show()");
                            PrimeFaces.current().executeScript("PF('confirmacion').show()");

                        } else {
                            HashSet setPE = new HashSet();
                            //planEstudioBeanHelper.agregarPlanEstudio(planEstudioBeanHelper.getPlanEstudio());
                            planEstudioBeanHelper.updatePE(planEstudioBeanHelper.getPlanEstudio());
                            planEstudioBeanHelper.seleccionarRegistro();
                            planEstudioBeanHelper.setListaSeleccionPlanEstudio(planEstudioBeanHelper.getListaSeleccionPlanEstudio());
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "Nota  Se guardó correctamente"));
                            planEstudioBeanHelper.seleccionarRegistro();
                            //RequestContext.getCurrentInstance().update("frmProfesor:seleccionados");
                            PrimeFaces.current().ajax().update("frmProfesor:seleccionados");

                        }
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El plan de estudio ligado al programa educativo ya existe");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }
            }
        }
        filtrado();
        return "";
    }
    /**
     * Filtrado de los registros en la tabla
     */
    public void filtrado() {
        List<Rol> list = null;
        list= loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        System.out.println(seleccionado + "ÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑ");
        String catalogo = "Administración de plan de estudios";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        if (busqueda.equalsIgnoreCase(null)) {
            listaFiltrada = planEstudioBeanHelper.filtrado("Nombre", "2");
        } else {
            listaFiltrada = planEstudioBeanHelper.filtrado("Nombre", busqueda);
        }
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
     * Ruta de la imagen del PDF
     * @return 
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
     * Valida la seleccion por el usuario, si no se ha seleccionado nada regresa
     * un mensaje de error
     * @param i tipo int
     * @return String 
     */
    public String tooltip(int i) {
        if (planEstudioBeanHelper.getListaSeleccionPlanEstudio() == null || planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() < 1) {
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
     * Deshabilita el Menu
     * @return 
     */
    public boolean deshabilitarMenu() {
        if (planEstudioBeanHelper.getListaSeleccionPlanEstudio() == null || planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() < 1) {
            return true;
        }
        return false;
    }
    /**
     * Muestra la seleccion del plan de estudio
     * @return 
     */
    public boolean mostrarSeleccionPlanEstudio() {
        if (planEstudioBeanHelper.getListaSeleccionPlanEstudio() != null && planEstudioBeanHelper.getListaSeleccionPlanEstudio().size() > 1) {
            return true;
        } else {
            return false;
        }
    }
    public void limpiarSeleccion() {
        planEstudioBeanHelper.setListaSeleccionPlanEstudio(null);
        planEstudioBeanHelper.setPlanEstudio(new Planestudio());
        planEstudioBeanHelper.setProgramaeducativo(new Programaeducativo());
        mostrarSeleccionPlanEstudio();
    }
    /**
     * Calcula Calcula el total de creditos
     */
    public void horasCompletas() {
        int credOptativos = planEstudioBeanHelper.getPlanEstudio().getPEScreditosOptativos();
        int credObligatorios = planEstudioBeanHelper.getPlanEstudio().getPEScreditosObligatorios();
        int horasCompletas = credOptativos + credObligatorios;
        planEstudioBeanHelper.getPlanEstudio().setPEStotalCreditos(horasCompletas);
    }
    /**
     * Confirma la eliminacion en caso de que no este asignado a un grupo
     */
    public void setMensajeConfirmacion() {
        boolean asignado = false;
        if (deshabilitar.equals("true")) {
            //modificado
            //if (planEstudioBeanHelper.getPlanEstudioDelegate().getPlanAsignadoGrupo(planEstudioBeanHelper.getPlanEstudio().getPesid()).size() > 0) {
            if (planEstudioBeanHelper.planAsignadoGrupo(planEstudioBeanHelper.getPlanEstudio().getPESid()).size() > 0) {
                asignado = true;
                //modificado
                //if (planEstudioBeanHelper.getPlanEstudioDelegate().getPlanAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPesid()).size() > 0) {
                if (planEstudioBeanHelper.planAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPESid()).size() > 0) {
                    mensajeConfirmacion = "El plan de estudio ya esta asignado a un grupo y a una area de conocimiento";
                    x = 10;
                    nomBotonConfirmDlg = "Aceptar";
                    renderAceptar = "false";
                    iconoConfirmDlg = "ui-icon-check";
                } else {
                    mensajeConfirmacion = "El plan de estudio ya esta asignado a un grupo";
                    x = 10;
                    nomBotonConfirmDlg = "Aceptar";
                    renderAceptar = "false";
                    iconoConfirmDlg = "ui-icon-check";
                }
            } else {
                //modificado
                //if (planEstudioBeanHelper.getPlanEstudioDelegate().getPlanAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPesid()).size() > 0) {
                if (planEstudioBeanHelper.planAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPESid()).size() > 0) {
                    mensajeConfirmacion = "El plan de estudio ya esta asignado a un area de conocimiento";
                    x = 10;
                    renderAceptar = "false";
                    nomBotonConfirmDlg = "Aceptar";
                    iconoConfirmDlg = "ui-icon-check";
                } else {
                    mensajeConfirmacion = "Está seguro de eliminar este registro";
                    nomBotonConfirmDlg = "Cancelar";
                    iconoConfirmDlg = "ui-icon-close";
                    renderAceptar = "true";
                    x = 0;
                }
            }
        } else {
            renderAceptar = "true";
            nomBotonConfirmDlg = "Cancelar";
            iconoConfirmDlg = "ui-icon-close";
            //modificado
            //if (planEstudioBeanHelper.getPlanEstudioDelegate().getPlanAsignadoGrupo(planEstudioBeanHelper.getPlanEstudio().getPesid()).size() > 0) {
            if (planEstudioBeanHelper.planAsignadoGrupo(planEstudioBeanHelper.getPlanEstudio().getPESid()).size() > 0) {
                asignado = true;
                //if (planEstudioBeanHelper.getPlanEstudioDelegate().getPlanAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPesid()).size() > 0) {
                if (planEstudioBeanHelper.planAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPESid()).size() > 0) {
                    mensajeConfirmacion = "El plan de estudio ya esta asignado a un grupo y a un area de conocimiento ¿Desea continuar?";
                    x = 10;
                } else {
                    mensajeConfirmacion = "El plan de estudio ya esta asignado a un grupo ¿Deseas continuar?";
                    x = 10;
                }
            } else {
                //if (planEstudioBeanHelper.getPlanEstudioDelegate().getPlanAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPesid()).size() > 0) {
                if (planEstudioBeanHelper.planAsignadoAreaConocimiento(planEstudioBeanHelper.getPlanEstudio().getPESid()).size() > 0) {
                    mensajeConfirmacion = "El plan de estudio ya esta asignado a un area de conocimiento ¿Desea Continuar?";
                    x = 10;
                } else {
                    mensajeConfirmacion = "Está seguro de eliminar este registro";
                    x = 0;
                }
            }
        }
        //RequestContext.getCurrentInstance().update("confirmacionId");
        PrimeFaces.current().ajax().update("confirmacionId");

    }
    /**
     * Valida la vigencia del Plan
     */
    public void validacionVigenciaPlan() {
        String validacion;
        String vigencia = Character.toString(planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan().charAt(5));
        try {

            System.out.println("Vigencia : " + vigencia);
            System.out.println("Plan Estudio 1 : " + planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan().charAt(5));
            if (vigencia.equals("1") || vigencia.equals("2")) {
                System.out.println("Plan Estudio 2 : " + planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan());
            } else {
                System.out.println("Plan Estudio original: " + planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan());
                StringBuilder sb = new StringBuilder(planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan());
                sb.deleteCharAt(5);
                validacion = sb.toString();
                System.out.println("Valor de validacion : " + sb);
                planEstudioBeanHelper.getPlanEstudio().setPESvigenciaPlan(validacion);
                System.out.println("Plan Estudio Modificado: " + planEstudioBeanHelper.getPlanEstudio().getPESvigenciaPlan());

            }
        } catch (Exception e) {

        }
    }
    /**
     * Creacion del PDF
     * @param document 
     */
    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        //Sentencias para evitar duplicacion de tabla en PDF
        if(document!=null){
            pdf = new Document();
            pdf.setPageSize(PageSize.A4.rotate());
        }
    }
    /**
     * Genera el PDF
     * @param document
     * @throws IOException
     * @throws DocumentException 
     */
    public void postProcessPDF(Object document) throws IOException, DocumentException {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();

        //Tabla con UABC
        PdfPTable pdfTabletitulo = new PdfPTable(2);
        pdfTabletitulo.getDefaultCell().setBorder(PdfCell.NO_BORDER);
        //titulos de PDF
        Paragraph UABC = new Paragraph("Universidad Autónoma de Baja California", FontFactory.getFont(FontFactory.TIMES, 22, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("Catálogo de Plan de Estudio", FontFactory.getFont(FontFactory.TIMES, 18,com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(fecha(),FontFactory.getFont(FontFactory.TIMES, 16,com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        //alineacion de titulos
        UABC.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(73);
        Paragraph esp = new Paragraph(" ");
        pdf.add(UABC);
        //se agrega la imagen del logo de UABC
        try {
            Image imagenLogo = Image.getInstance(RACTBeanUI.class.getResource("imagenes/logo.jpg"));
            //Posicion de imagen (Horizontal, Vertical)
           imagenLogo.setAbsolutePosition(120f, 460f);
            //Posicion de imagen (Ancho, Largo)
           imagenLogo.scaleAbsolute(90, 120);
            pdf.add(imagenLogo);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }
        //se le da formato al documento
        pdf.add(esp);
        pdf.add(esp);
        pdf.add(titulo);
        pdf.add(fecha);
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

        pdfTabletitulo.setHorizontalAlignment(25);

        float[] columnWidthsss = new float[]{4f, 28};
        pdfTabletitulo.setWidths(columnWidthsss);
        pdf.add(pdfTabletitulo);//se agrega el titulo al documento pdf

        //Tabla Cabezera 
        PdfPTable pdftablecabezera = new PdfPTable(2);
        pdftablecabezera.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPTable pdftablecabezera2 = new PdfPTable(8);
        pdftablecabezera2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        //Tabla contenidos
        PdfPTable pdftablecont = new PdfPTable(5);
        //Columnas de la tabla
        pdftablecont.addCell(new Paragraph("Programa educativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Plan de estudio", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Créditos obligatorios", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Créditos optativos", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Total de créditos", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.setHorizontalAlignment(15);

        float[] columnWidthsss3 = new float[]{10f, 10f, 10f, 10f, 10f};
        pdftablecont.setWidths(columnWidthsss3);
        pdf.add(pdftablecont);
        PdfPTable pdftablecont2 = new PdfPTable(5);
        //filas que contendra la tabla
        for (Planestudio item : listaFiltrada) {
            //se llena la tabla por filas
            pdftablecont2.addCell(new Paragraph(item.getProgramaEducativoPEDid().getPEDnombre()));
            pdftablecont2.addCell(new Paragraph(item.getPESvigenciaPlan()));
            pdftablecont2.addCell(new Paragraph(String.valueOf(item.getPEScreditosObligatorios())));
            pdftablecont2.addCell(new Paragraph(String.valueOf(item.getPEScreditosOptativos())));
            pdftablecont2.addCell(new Paragraph(String.valueOf(item.getPEStotalCreditos())));
        }
        float[] columnWidthsss4 = new float[]{10f, 10f, 10f, 10f, 10f};
        pdftablecont2.setWidths(columnWidthsss4);
        pdf.add(pdftablecont2);
    }
    /**
     * Obtiene la fecha del sistema y da formato
     * @return fecha tipo String
     */
    public String fecha() {
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }
   /**
    * Retorna el nombre para generar el pdf
    * @return nombre tipo String
    */
    public String nombre() {
        String nombre = "Plan_Estudio_" + fecha();
        return nombre;
    }
}