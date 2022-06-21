package mx.avanti.siract.ui;

import com.lowagie.text.*;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.util.Objects.isNull;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.helper.ProgramaEducativoBeanHelper;
import mx.avanti.siract.helper.UnidadAcademicaBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * 
 * @author Javier Razo
 */
@ManagedBean
@ViewScoped
public class ProgramaEducativoBeanUI implements Serializable {

    private ProgramaEducativoBeanHelper programaEducativoBeanHelper = null;
    private UnidadAcademicaBeanHelper unidadAcademicaBeanHelper = null;

    private List<Programaeducativo> listaFiltrada;
    private List<Unidadacademica> listaUnidad;
    private List<Campus> listaCampus;
    private String selectedCampus;
    private StreamedContent file;
    
    private final int IDCATALOGOADMIPROGRAEDUCATIVO = 6;

    public int getIDCATALOGOADMIPROGRAEDUCATIVO() {
        return IDCATALOGOADMIPROGRAEDUCATIVO;
    }        
    

    private String header;
    private String deshabilitar;
    private String deshabilitarModificar = "false";
    private String deshabilitarAceptar;

    private String cabecera;
    private String textoBoton;
    private String busqueda = "";
    private boolean selecvisible;
    private String mensajeRep;

    private String mensajeConfirm = "";

    private String deshabilitarBoton = "true";
    private String titleElim = "Seleccione un registro de la tabla";
    private String titleMod = "Seleccione un registro de la tabla"; 

    private String renderUA = "false";
    
    public boolean imprimir=false;

    public String nomImg;

    Date date = new Date();
//Propiedad utilizada para acceder a los datos de LoginBean_2    
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public ProgramaEducativoBeanUI() {
        init();
    }

    private void init() {
        programaEducativoBeanHelper = new ProgramaEducativoBeanHelper();
        unidadAcademicaBeanHelper = new UnidadAcademicaBeanHelper();
        listaUnidad =  programaEducativoBeanHelper.consultaUnidadAcademica();
        listaCampus = programaEducativoBeanHelper.consultaCampus();
        programaEducativoBeanHelper.setTransaccion(true);
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getRenderUA() {
        return renderUA;
    }

    public void setRenderUA(String renderUA) {
        this.renderUA = renderUA;
    }

    //Post constructor necesario para usar ManagedProperty
    @PostConstruct
    public void postConstructor() {
        programaEducativoBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
        programaEducativoBeanHelper.setUsuario(loginBean.getLogueado());
        System.out.println("rol desde el BeanUI: " + loginBean.getSeleccionado());
        System.out.println("id del usuario desde login " + loginBean.getLogueado().getUSUid());

        if (programaEducativoBeanHelper.getRolSeleccionado().equalsIgnoreCase("Administrador")) {
            renderUA = "true";
        } else {
            renderUA = "false";
        }

    }

    /**
     * 
     * @param i
     * @return 
     */
    public String dlgCabecera(int i) {
        if (i == 1) {
            header = "Agregar programa educativo";
            deshabilitar = "false";
            deshabilitarModificar = "false";
        }
        if (i == 2) {
            header = "Eliminar programa educativo";
            deshabilitar = "true";
            deshabilitarModificar = "true";
        }
        if (i == 3) {
            header = "Modificar programa educativo";
            deshabilitar = "false";
        }
        return header;
    }

    /**
     * 
     */
    public void nuevo() {
        limpiarSeleccion();
        dlgCabecera(1);
        programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setTransaccion(true);
    }

    public void modificar(){
        dlgCabecera(3);
        try {
            if (programaEducativoBeanHelper.getListaSeleccionPe().size() < 1) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningun registro");
                //RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                programaEducativoBeanHelper.setProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
                programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getListaSeleccionPe().get(0).getUnidadAcademicaUACid());
                
                deshabilitarModificar = "false";
                deshabilitar = "false";
                if(!programaEducativoBeanHelper.validarModificacion(programaEducativoBeanHelper.getProgramaEducativo())){
                    deshabilitar = "true";
                }     
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    /* 
    *  Método para imprimir el mensaje indicando que no se podrán modificar.
    *  todos los campos.
    */ 
    @Deprecated
    public void mensajeMod(){
        try{
            if (header.equals("Modificar programa educativo")) {
                if(!programaEducativoBeanHelper.validarModificacion(programaEducativoBeanHelper.getProgramaEducativo())){
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("dlg", new FacesMessage("", "No se pueden modificar todos los datos"));
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @deprecated 
     */
    public void modificar2() {
        dlgCabecera(3);
        try {
            if (programaEducativoBeanHelper.getListaSeleccionPe().size() < 1) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningun registro");
               // RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                programaEducativoBeanHelper.setProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
                programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getListaSeleccionPe().get(0).getUnidadAcademicaUACid());
                if(!ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativoBeanHelper.getProgramaEducativo().getPEDid()).getPlanestudioList().isEmpty()
                        || !ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativoBeanHelper.getProgramaEducativo().getPEDid()).getAreaadministrativaList().isEmpty()){
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error de validación", mensajeTieneAsignado(programaEducativoBeanHelper.getProgramaEducativo()));
                   // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    limpiarSeleccion();
                }else{
                    if(programaEducativoBeanHelper.getListaSeleccionPe().size() > 1){
                        List<Programaeducativo> listaSinModificables = new ArrayList<Programaeducativo>();
                        for (Programaeducativo ped : programaEducativoBeanHelper.getListaSeleccionPe()){
                            if(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(ped.getPEDid()).getPlanestudioList().isEmpty()
                            && ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(ped.getPEDid()).getAreaadministrativaList().isEmpty()){
                                listaSinModificables.add(ped);
                            }    
                        }
                        programaEducativoBeanHelper.setListaSeleccionPe(listaSinModificables);
                    }
                    
                     //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                     PrimeFaces.current().executeScript("PF('dlg').show()");

                }
                
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    public void eliminar() {
        dlgCabecera(2);
        try {
            if (programaEducativoBeanHelper.getListaSeleccionPe().size() == 1) {
                programaEducativoBeanHelper.setProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
                programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getListaSeleccionPe().get(0).getUnidadAcademicaUACid());
            } else {
                if (programaEducativoBeanHelper.getListaSeleccionPe().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningún registro");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                } else {
                    programaEducativoBeanHelper.setProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
                    programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getListaSeleccionPe().get(0).getUnidadAcademicaUACid());
                }
            }           
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

   /**
    * 
    * @return cadena con parámetros faltantes
    */
    public String validacion() {
        String vacio = "";
        if (programaEducativoBeanHelper.getProgramaEducativo().getPEDclave() == 0) {
            vacio += " Clave de programa educativo";
        }
        if (programaEducativoBeanHelper.getProgramaEducativo().getPEDnombre().isEmpty()) {
            vacio += " Nombre de programa educativo";
        }
        if (programaEducativoBeanHelper.getUnidadacademica().getUACid() == 0) {
            vacio += " Unidad académica";
        }

        return vacio;
    }
   
    /**
     * @deprecated 
     */
    public void eliminacionConfirmada() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
        for(Programaeducativo pe: programaEducativoBeanHelper.getListaSeleccionPe()){
            programaEducativoBeanHelper.eliminarProgramaEducativo(pe);  
        }
        programaEducativoBeanHelper.eliminarProgramaEducativo(programaEducativoBeanHelper.getSelecProgramaEducativo());
        programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());

        if (programaEducativoBeanHelper.getListaSeleccionPe().size() >= 1) {
            programaEducativoBeanHelper.setProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
            programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getListaSeleccionPe().get(0).getUnidadAcademicaUACid());
        }
    }

    /**
     * 
     */
    public void modificacionConfirmada() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
        programaEducativoBeanHelper.agregarProgramaEducativo(programaEducativoBeanHelper.getProgramaEducativo());
        programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());
          //  RequestContext.getCurrentInstance().execute("PF('confdlgMod').hide()");
            PrimeFaces.current().executeScript("PF('confdlgMod').hide()");

        filtrado();
    }

    /**
     * 
     */
    public void confirmacionAceptada() {
        programaEducativoBeanHelper.setTransaccion(true);
        if (header.equals("Eliminar programa educativo")) {
            if (renderCancelar.equals("true")) {
                programaEducativoBeanHelper.eliminarDeLista(programaEducativoBeanHelper.getProgramaEducativo().getPEDid());
                try{
                    programaEducativoBeanHelper.eliminarProgramaEducativo(programaEducativoBeanHelper.getProgramaEducativo());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                if(programaEducativoBeanHelper.getTransaccion()){
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
                }else
                    errorTransaccion();
                programaEducativoBeanHelper.terminaTransaccion();
                programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());
                programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());         
                
               // RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

                
                if (programaEducativoBeanHelper.getListaSeleccionPe().size() < 1){
                   // RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                    PrimeFaces.current().executeScript("PF('dlg').hide()");

                    programaEducativoBeanHelper.setListaSeleccionPe(new ArrayList());
                    limpiarSeleccion();
                }else{
                    programaEducativoBeanHelper.setProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
                    programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getListaSeleccionPe().get(0).getUnidadAcademicaUACid());
                    programaEducativoBeanHelper.setSelecProgramaEducativo(programaEducativoBeanHelper.getListaSeleccionPe().get(0));
                  //  RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                    PrimeFaces.current().executeScript("PF('dlg').show()");

                    onChangeProgramaEducativo();
                }
            } else {
                //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

            }
        } else if(header.equals("Modificar programa educativo")){
            if (renderCancelar.equals("true")) {
                //modificacionConfirmada();
            }
            else{
               // RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

            }
        }else { 
            programaEducativoBeanHelper.agregarProgramaEducativo(programaEducativoBeanHelper.getProgramaEducativo());
            if(programaEducativoBeanHelper.getTransaccion()){
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
            }else
                errorTransaccion();
            programaEducativoBeanHelper.terminaTransaccion();
            programaEducativoBeanHelper.seleccionarRegistro();
            programaEducativoBeanHelper.setListaSeleccionPe(programaEducativoBeanHelper.getListaSeleccionPe());
            
            //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
            PrimeFaces.current().executeScript("PF('dlg').show()");

            //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
            PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

        }
        filtrado();

    }

   /**
    * 
    * @return 
    */
    public String onClickSubmit() {
        programaEducativoBeanHelper.setTransaccion(true);
        String resultado = "";
        if (header.equals("Eliminar programa educativo")) {
            if (programaEducativoBeanHelper.validarEliminacion(programaEducativoBeanHelper.getProgramaEducativo())) {
                mensajeConfirm = "¿Está seguro de eliminar el registro?";
                renderCancelar = "true";
            }else{
                mensajeConfirm = mensajeTieneAsignado(programaEducativoBeanHelper.getProgramaEducativo());
                renderCancelar = "false";
            }
           // RequestContext.getCurrentInstance().update("confirmdlg");
            PrimeFaces.current().ajax().update("confirmdlg");

            //RequestContext.getCurrentInstance().execute("PF('confirmdlg').show()");
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");

        } else {
            if (programaEducativoBeanHelper.getRolSeleccionado().equalsIgnoreCase("director")
                    || programaEducativoBeanHelper.getRolSeleccionado().equalsIgnoreCase("subdirector")) {
                programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.UAbyProf());
            }
            
            String vacio = validacion();
            if (!vacio.trim().isEmpty()) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validación", "Capturar campo(s) vacio(s) " + vacio);
                //RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                mensajeRep = programaEducativoBeanHelper.validarRepetidos();
                if (mensajeRep.isEmpty()) {
                    mensajeRep = "vacio";
                }
                if (mensajeRep.equals("vacio")) {

                    if (header.equals("Agregar programa educativo")) {
                        programaEducativoBeanHelper.getProgramaEducativo().setUnidadAcademicaUACid(programaEducativoBeanHelper.getUnidadacademica());

                        programaEducativoBeanHelper.agregarProgramaEducativo(programaEducativoBeanHelper.getProgramaEducativo());
                        if(programaEducativoBeanHelper.getTransaccion()){
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                        }
                        else
                            errorTransaccion();
                        programaEducativoBeanHelper.terminaTransaccion();
                        programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());
                        programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());
                        programaEducativoBeanHelper.setUnidadacademica(new Unidadacademica());
                    } else {
                        if (header.equals("Modificar programa educativo")) {
                            FacesContext context = FacesContext.getCurrentInstance();
                            if(validarCambios()){
                                programaEducativoBeanHelper.modificarProgramaEducativo(programaEducativoBeanHelper.getProgramaEducativo());
                                
                                List<Programaeducativo> listaActualizada = programaEducativoBeanHelper.getListaSeleccionPe();
                                for(int i = 0; i< programaEducativoBeanHelper.getListaSeleccionPe().size(); i++){
                                    if(programaEducativoBeanHelper.getListaSeleccionPe().get(i).getPEDid() == programaEducativoBeanHelper.getProgramaEducativo().getPEDid())
                                        listaActualizada.set(i, programaEducativoBeanHelper.getProgramaEducativo());
                                }
                                
                                programaEducativoBeanHelper.setListaSeleccionPe(listaActualizada);
                                
                                if(programaEducativoBeanHelper.getTransaccion()){
                                    context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));
                                }
                                else
                                    errorTransaccion();
                                programaEducativoBeanHelper.terminaTransaccion();
                                
                                programaEducativoBeanHelper.seleccionarRegistro();
                            }
                            else
                                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"", "No se realizaron cambios"));
                        }
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación",  mensajeRep);
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }

            }
        }
        filtrado();
        mostrarBotones();
        return resultado;
    }
    
    private boolean validarCambios(){
        boolean seCambio = true;
        Programaeducativo modificado = programaEducativoBeanHelper.getProgramaEducativo();
        Programaeducativo antiguo = programaEducativoBeanHelper.buscarPrograma(modificado.getPEDid());
        if(modificado.getPEDnombre().equals(antiguo.getPEDnombre()) && 
                modificado.getPEDclave() == antiguo.getPEDclave() &&
                modificado.getUnidadAcademicaUACid().getUACid() == antiguo.getUnidadAcademicaUACid().getUACid())
            seCambio = false;
        return seCambio;
    }
    
    /**
     * 
     */
    public void filtrado() {
        List<Rol> list = null;
        list= loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        String catalogo = "Administración de programa educativo";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        listaFiltrada = programaEducativoBeanHelper.filtrado(busqueda);
        if(listaFiltrada.size()!=0){
            imprimir = true;
        }
        else{
            imprimir = false;
        }
        if(programaEducativoBeanHelper.getTransaccion() == false)
            errorTransaccion();
        programaEducativoBeanHelper.terminaTransaccion();
    }
    
    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }
    
    /**
     * 
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
     * 
     */
    public void mostrarBotones() {
        try {
            if (programaEducativoBeanHelper.getSelecProgramaEducativo().getPEDid() > 0) {
                deshabilitarBoton = "false";
                titleElim = "Eliminar";
                titleMod = "Modificar";
            }
        } catch (NullPointerException e) {
        }
    }

    /**
     * 
     */
    public void esconderBotones() {
        deshabilitarBoton = "true";
        titleElim = "Seleccione un registro de la tabla";
        titleMod = "Seleccione un registro de la tabla";
    }

    /**
     * 
     * @return 
     */
    public String botonesSelect() {
        if (programaEducativoBeanHelper.getSelecProgramaEducativo().getPEDid() > 0) {
            return "false";
        } else {
            return "true";
        }
    }

    /**
     * 
     * @return 
     */
    public boolean mostrarSeleccionPe() {
        return programaEducativoBeanHelper.getListaSeleccionPe() != null && programaEducativoBeanHelper.getListaSeleccionPe().size() >= 1; ////
    }

    /**
     * 
     */
    public void limpiarSeleccion() {
        programaEducativoBeanHelper.setListaSeleccionPe(null);
        programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setUnidadacademica(new Unidadacademica());
        mostrarSeleccionPe();
        botonesModElim();
        if(programaEducativoBeanHelper.getTransaccion() == false)
            errorTransaccion();
        programaEducativoBeanHelper.terminaTransaccion();
        programaEducativoBeanHelper.setTransaccion(true);
    }

    /**
     * 
     * @return 
     */
    public String botonesModElim() {
        if (programaEducativoBeanHelper.getListaSeleccionPe() == null
                || programaEducativoBeanHelper.getListaSeleccionPe().size() < 1) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 
     * @param i
     * @return 
     */
    public String tooltip(int i) {
        if (programaEducativoBeanHelper.getListaSeleccionPe() == null || programaEducativoBeanHelper.getListaSeleccionPe().size() < 1) {
            return "Seleccione un registro de la tabla";
        } else {
            if (i == 1) {
                return "Eliminar";
            }
            if (i == 2) {
                return "Modificar";
            }
        }
        return "";
    }

    /**
     * 
     * @return 
     */
    public List<Unidadacademica> cargarUnidadAcademica() {
        listaUnidad = programaEducativoBeanHelper.consultaUnidadAcademica();
        return listaUnidad;
    }

    /**
     * 
     * @param event 
     */
    public void onRowUnselect(UnselectEvent event) {
        programaEducativoBeanHelper.setSelecProgramaEducativo(new Programaeducativo());
        programaEducativoBeanHelper.setSelecProgramaEducativo((Programaeducativo) event.getObject());
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public String getDeshabilitarModificar() {
        return deshabilitarModificar;
    }

    public void setDeshabilitarModificar(String deshabilitarModificar) {
        this.deshabilitarModificar = deshabilitarModificar;
    }

    
    
    /**
     * 
     * @return 
     */
    public boolean deshabilitarMenu() {
        if (programaEducativoBeanHelper.getListaSeleccionPe() == null || programaEducativoBeanHelper.getListaSeleccionPe().size() < 1) {
            return true;
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters y setters">
    public ProgramaEducativoBeanHelper getProgramaEducativoBeanHelper() {
        return programaEducativoBeanHelper;
    }

    public void setProgramaEducativoBeanHelper(ProgramaEducativoBeanHelper programaEducativoBeanHelper) {
        this.programaEducativoBeanHelper = programaEducativoBeanHelper;
    }

    public UnidadAcademicaBeanHelper getUnidadAcademicaBeanHelper() {
        return unidadAcademicaBeanHelper;
    }

    public void setUnidadAcademicaBeanHelper(UnidadAcademicaBeanHelper unidadAcademicaBeanHelper) {
        this.unidadAcademicaBeanHelper = unidadAcademicaBeanHelper;
    }

    public List<Programaeducativo> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Programaeducativo> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public void setMensajeConfirm(String mensajeConfirm) {
        this.mensajeConfirm = mensajeConfirm;
    }
    
    public List<Campus> getListaCampus() {
        return listaCampus;
    }

    public void setListaCampus(List<Campus> listaCampus) {
        this.listaCampus = listaCampus;
    }
    public List<Unidadacademica> getListaUnidad() {
        return listaUnidad;
    }

    public void setListaUnidad(List<Unidadacademica> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }
    
    public String getSelectedCampus() {
        return selectedCampus;
    }

    public void setSelectedCampus(String selectedCampus) {
        this.selectedCampus = selectedCampus;
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

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public boolean isSelecvisible() {
        return selecvisible;
    }

    public void setSelecvisible(boolean selecvisible) {
        this.selecvisible = selecvisible;
    }

    public String getMensajeRep() {
        return mensajeRep;
    }

    public void setMensajeRep(String mensajeRep) {
        this.mensajeRep = mensajeRep;
    }

    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }

    public void setDeshabilitarBoton(String deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
    }

    public String getTitleElim() {
        return titleElim;
    }

    public void setTitleElim(String titleElim) {
        this.titleElim = titleElim;
    }

    public String getTitleMod() {
        return titleMod;
    }

    public void setTitleMod(String titleMod) {
        this.titleMod = titleMod;
    }

    public String getDeshabilitarAceptar() {
        return deshabilitarAceptar;
    }

    public void setDeshabilitarAceptar(String deshabilitarAceptar) {
        this.deshabilitarAceptar = deshabilitarAceptar;
    }

    private String renderCancelar;

    public String getRenderCancelar() {
        return renderCancelar;
    }

    public void setRenderCancelar(String renderCancelar) {
        this.renderCancelar = renderCancelar;
    }
    
    public StreamedContent getFile(){
        return file;
    }
    // </editor-fold>
    
    /**
     * 
     * @param document
     * @throws IOException
     * @throws DocumentException 
     */
    public void preProcessPDF(Object document) throws IOException, DocumentException {
        Document pdf = new Document();
        
        pdf = new Document();
        pdf.setPageSize(PageSize.A4);
        
        postProcessPDF(pdf);
           //postProcessPDF(document);
    }

    /**
     * 
     * @param document
     * @throws IOException
     * @throws DocumentException 
     */
    public StreamedContent postProcessPDF(Document document) throws IOException, DocumentException {
        //final Document pdf = (Document) document;
        PdfWriter writer;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in ;
        Document pdf = document;
        writer = PdfWriter.getInstance(pdf, out);
        pdf.setPageSize(PageSize.A4);
        pdf.open();
        
        boolean uaSeleccionada = programaEducativoBeanHelper.getFiltroUAC().getUACid() != 0;
        
        //Encabezado, titulo, fecha
        Paragraph UABC = new Paragraph("UNIVERSIDAD AUTONOMA DE BAJA CALIFORNIA", FontFactory.getFont(FontFactory.TIMES, 16, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("PROGRAMAS EDUCATIVOS", FontFactory.getFont(FontFactory.TIMES, 14,com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(fecha(),FontFactory.getFont(FontFactory.TIMES, 12,com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        
        UABC.setAlignment(Element.ALIGN_CENTER);        
        titulo.setAlignment(Element.ALIGN_CENTER);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(65);

        //Espacio
        Paragraph espacio = new Paragraph(" ");
        //Imagen
        try {
            Image imagenLogo = Image.getInstance(this.getClass().getResource("/logo.png"));
            //Posicion x, y, izquierda-derecha, abajo-arriba
            imagenLogo.setAbsolutePosition(25, pdf.getPageSize().getHeight() -145);
            //Tamanio de imagen (Ancho), largo se calcula por regla de tres
            int size = 72;
            imagenLogo.scaleAbsolute(size, (size * imagenLogo.getHeight()) /imagenLogo.getWidth());
            pdf.add(imagenLogo);
        } catch (Exception exception) {
            System.out.println("****NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA");
        }
        
        pdf.add(UABC);
        //Si si selecciono una unidad academica, se agrega al encabezado
        if (uaSeleccionada) {
                    Paragraph unidadAcademica = new Paragraph( programaEducativoBeanHelper.insertarSaltoLinea(45, programaEducativoBeanHelper.unidadadAcademicaById(programaEducativoBeanHelper.getFiltroUAC().getUACid()).getUACnombre()), FontFactory.getFont(FontFactory.TIMES, 14, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
                    unidadAcademica.setAlignment(Element.ALIGN_CENTER);
                    pdf.add(unidadAcademica);
        }else{
             pdf.add(espacio);
        }
        pdf.add(titulo);
        pdf.add(fecha);
        pdf.add(espacio);
        //pdf.add(espacio);
         
        //Tabla contenidos
        PdfPTable pdftablecont;
        float[] columnWidths;
        //Unidad academica seleccionada
        if (uaSeleccionada) {
            pdftablecont = new PdfPTable(2);
            columnWidths = new float[]{14f, 86f};
        //Unidad academica no seleccionada
        }else{
            pdftablecont = new PdfPTable(3);
            columnWidths = new float[]{42f,  13f, 45f}; 
        }
        pdftablecont.setTotalWidth(pdf.getPageSize().getWidth() - (100*2) );
        pdftablecont.setLockedWidth(true);
        pdftablecont.setWidths(columnWidths);
        
        //Creación de fuentes
        //BaseFont arial = BaseFont.createFont("/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 12,  new Color(0, 0, 0));
        Font fontContent = FontFactory.getFont(FontFactory.TIMES, 10, new Color(0, 0, 0));
        
        //Agregar encabezados
        if (!uaSeleccionada) {
                    PdfPCell celdaUA = new PdfPCell();
                    celdaUA.setBackgroundColor(new Color(212, 212, 212));
                    celdaUA.addElement(new Paragraph("UNIDAD ACADÉMICA", fontHeader));
                    pdftablecont.addCell(celdaUA);
                    //pdftablecont.addCell(new Paragraph("UNIDAD ACADÉMICA", fontHeader));
        }
        PdfPCell celdaClave = new PdfPCell();
        celdaClave.setBackgroundColor(new Color(212, 212, 212));
        celdaClave.addElement(new Paragraph("CLAVE", fontHeader));
        pdftablecont.addCell(celdaClave);
        
        PdfPCell celdaPrograma = new PdfPCell();
        celdaPrograma.setBackgroundColor(new Color(212, 212, 212));
        celdaPrograma.addElement(new Paragraph("PROGRAMA", fontHeader));
        pdftablecont.addCell(celdaPrograma);
        //pdftablecont.addCell(new Paragraph("CLAVE", fontHeader));
        //pdftablecont.addCell(new Paragraph("PROGRAMA ", fontHeader));
        
        //Agregar lista de programas educativos
        if (uaSeleccionada) {
            //Unidad academica seleccionada
            for (Programaeducativo programaeducativo : listaFiltrada) {
                pdftablecont.addCell(new Paragraph(String.valueOf(programaeducativo.getPEDclave()), fontContent));
                pdftablecont.addCell(new Paragraph(programaeducativo.getPEDnombre(), fontContent));
            }
        }else{
            //Unidad academica no seleccionada
            for (Programaeducativo programaeducativo : listaFiltrada) {
                pdftablecont.addCell(new Paragraph(programaeducativo.getUnidadAcademicaUACid().getUACnombre(), fontContent));
                pdftablecont.addCell(new Paragraph(String.valueOf(programaeducativo.getPEDclave()), fontContent));
                pdftablecont.addCell(new Paragraph(programaeducativo.getPEDnombre(), fontContent));
            }
        }
        pdf.add(pdftablecont);
        pdf.close();
        
        in = new ByteArrayInputStream(out.toByteArray());
        file = new DefaultStreamedContent(in, "application/pdf", nombre() + fecha()+".pdf");
        
        return file;
    }
     
    /**
     * 
     * @return 
     */
    public String fecha(){
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        String fecha=formatoFecha.format(date);
        
        return fecha;
    }
    
    /**
     * 
     * @return 
     */
    public String nombre(){
        String nombre ="Programa_Educativo_" + fecha();
        return nombre;
    }
    
    
    /**
     * Este metodo regresa el mensaje a mostrar en caso de error de validacion al modificar o eliminar
     * @param ped
     * @return 
     *//*******************************************************************/
    public String mensajeTieneAsignado(Programaeducativo ped){
        boolean error = false;
        String mensaje = "Este programa educativo tiene asignado: ";
        
        Programaeducativo prog = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(ped.getPEDid());
        if(isNull(prog)){
            programaEducativoBeanHelper.setTransaccion(false);
            error = true;
        }else{
            if(!prog.getPlanestudioList().isEmpty()){
                mensaje += "-Plan de estudio ";
            }
            if(!prog.getAreaadministrativaList().isEmpty()){
                mensaje += "-Area administrativa ";
            }
        }
        if (header.equals("Eliminar programa educativo")) {
            List <Profesor> listaProf = ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorPE(ped.getPEDid());
            if(isNull(listaProf)){
                programaEducativoBeanHelper.setTransaccion(false);
                error = true;
            }else{
                if(!listaProf.isEmpty()){
                    mensaje += "-Profesor";
                }
            }
        }
        if(error)
            mensaje += "-Error de transacción";
        
        return mensaje;
    }
    
    public void onChangeProgramaEducativo() {
        Programaeducativo buscar = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativoBeanHelper.getSelecProgramaEducativo().getPEDid());
        if(isNull(buscar)){
            programaEducativoBeanHelper.setTransaccion(false);
            programaEducativoBeanHelper.setProgramaEducativo(new Programaeducativo());
        }else
            programaEducativoBeanHelper.setProgramaEducativo(buscar);
        
        programaEducativoBeanHelper.setUnidadacademica(programaEducativoBeanHelper.getProgramaEducativo().getUnidadAcademicaUACid());
        
        if (header.equals("Modificar programa educativo")) {
            if(programaEducativoBeanHelper.validarModificacion(programaEducativoBeanHelper.getProgramaEducativo())){
                deshabilitarModificar = "false";
                deshabilitar = "false";
            }else{ 
                deshabilitarModificar = "false";
                deshabilitar = "true";
            }
        }
        
    }
    
    /**
     * Método que se ejecuta al cambiar el valor del campus
     * Cambia los valores de lista de unidades academicas
     * Cambia lista de programas educativos que se despliegan
     */
    public void onChangeCampus(){
        if (!selectedCampus.isEmpty()) {
            listaUnidad = programaEducativoBeanHelper.buscarCampus(Integer.parseInt(selectedCampus)).getUnidadacademicaList();
            listaFiltrada = programaEducativoBeanHelper.programasEducativosByCampus(Integer.parseInt(selectedCampus));
            listaFiltrada = programaEducativoBeanHelper.ordenarProgramasEducativos(listaFiltrada);
        }else{
            programaEducativoBeanHelper.getFiltroUAC().setUACid(0);
            listaUnidad =  programaEducativoBeanHelper.consultaUnidadAcademica();
            listaFiltrada = programaEducativoBeanHelper.filtrado(busqueda);
        }
        setImprimir(!listaFiltrada.isEmpty());
    }
    
    private void errorTransaccion(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Ocurrió un error durante la transacción"));
    }
}
