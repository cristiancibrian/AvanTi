package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.helper.CapturaCicloEscolarBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;
/**
 * 
 * @author Oscar D. Sanchez
 */
@ManagedBean
@ViewScoped
public class CapturaCicloEscolarBeanUI implements Serializable {
    
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final int IDCATALOGOADMICICLOESC = 15;

    public int getIDCATALOGOADMICICLOESC() {
        return IDCATALOGOADMICICLOESC;
    }
    
    

    private CapturaCicloEscolarBeanHelper CicloEscolarBeanHelper = null;
    
    /* variable para controlar los botones de eliminar y modificar */
    private String deshabilitarBoton="true";
    private String titleMod = "Seleccione un registro de la tabla";    
    private String titleElim = "Seleccione un registro de la tabla";
    
    /*variables para controlar los dialog */
    private String header;
    private String deshabilitar;
    private String deshabilitarAceptar;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public boolean isSelecvisible() {
        return selecvisible;
    }

    public void setSelecvisible(boolean selecvisible) {
        this.selecvisible = selecvisible;
    }
    private boolean selecvisible;
    
    private String mensajeConfirm;
    
    /* variables para la busqueda por filtro y la lista de resultados */
    private String busqueda="";    
    private List<Cicloescolar> listaFiltrada;  
    
    
    /* variable para obtener el mensaje de los datos repetidos */
    private String mensajeRep;
       public CapturaCicloEscolarBeanUI(){
        init();
    }

    public CapturaCicloEscolarBeanHelper getCicloEscolarBeanHelper() {
        return CicloEscolarBeanHelper;
    }

    public void setCicloEscolarBeanHelper(CapturaCicloEscolarBeanHelper CicloEscolarBeanHelper) {
        this.CicloEscolarBeanHelper = CicloEscolarBeanHelper;
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

    public String getDeshabilitarAceptar() {
        return deshabilitarAceptar;
    }

    public void setDeshabilitarAceptar(String deshabilitarAceptar) {
        this.deshabilitarAceptar = deshabilitarAceptar;
    }

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public void setMensajeConfirm(String mensajeConfirm) {
        this.mensajeConfirm = mensajeConfirm;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<Cicloescolar> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Cicloescolar> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public String getMensajeRep() {
        return mensajeRep;
    }

    public void setMensajeRep(String mensajeRep) {
        this.mensajeRep = mensajeRep;
    }
    private void init(){
        CicloEscolarBeanHelper = new CapturaCicloEscolarBeanHelper();
    }
    
    /**
     * Metodo que asigna un mensaje al dialog "confirmdlg"
     */
    public void setMensajeConfirm(){
        if(deshabilitar.equals("true")){
            mensajeConfirm = "¿Está seguro de eliminar el registro?";
        }else{
            mensajeConfirm = "¿Está seguro de guardar los cambios?";
        }
        PrimeFaces.current().ajax().update("confirmdlg");
    }
    
    /**
     * Metodo que asigna un titulo al dialog
     */
    public String dlgCabecera(int i){
        if(i == 1){
            header = "Agregar ciclo escolar";
            deshabilitar = "false";
        }
        if(i == 2){
            header = "Eliminar ciclo escolar";
            deshabilitar = "true";
        }
        if(i == 3){
            header = "Modificar ciclo escolar";
            deshabilitar = "false";
        }        
        return header;
    }
    public void nuevo(){
        limpiarSeleccion();
        dlgCabecera(1);
        CicloEscolarBeanHelper.setCicloescolar(new Cicloescolar());
        CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
    }
    
    public void limpiarSeleccion(){
        CicloEscolarBeanHelper.setListaCicloSeleccionada(null);
        CicloEscolarBeanHelper.setCicloescolar(new Cicloescolar());
        CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());           
        mostrarSeleccionCiclo();        
        botonesModElim();
    }  
    public String botonesModElim(){
        if(CicloEscolarBeanHelper.getListaCicloSeleccionada()== null || CicloEscolarBeanHelper.getListaCicloSeleccionada().size() < 1)
            return "true";
        else
            return "false";
    }
    public boolean mostrarSeleccionCiclo() {
        return CicloEscolarBeanHelper.getListaCicloSeleccionada()!= null && CicloEscolarBeanHelper.getListaCicloSeleccionada().size() > 1;
    } 
    
    public void modificar(){
        dlgCabecera(3);
        try{
            if(CicloEscolarBeanHelper.getListaCicloSeleccionada().size() == 1){
                CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));   
            } else{
                if(CicloEscolarBeanHelper.getListaCicloSeleccionada().size() < 1){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }else{
                    CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));
                    
                }
            }
        }catch(NullPointerException e){
        }     
    }
        public void eliminar(){
        dlgCabecera(2);
        try{
            if(CicloEscolarBeanHelper.getListaCicloSeleccionada().size() == 1){
                CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));   
                selecvisible=true;
//                deshabilitarAceptar = "false";
            } else{
                if(CicloEscolarBeanHelper.getListaCicloSeleccionada().size() < 1){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }else{
                    CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));

                }
            }
        }catch(NullPointerException e){
        }
    }
    /**
     * Metodo para validar si el ciclo escolar esta vacio
     * @return verdadero o falso
     */
    public boolean validacion(){
        if(CicloEscolarBeanHelper.getCicloescolar().getCEScicloEscolar().isEmpty()){
            return true;
        }else{
            return false;
        }

        
    }
    
    
    public void eliminacionConfirmada(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Eliminando", "Se eliminó correctamente"));
        CicloEscolarBeanHelper.setCicloescolar(new Cicloescolar());
        CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
        PrimeFaces.current().executeScript("PF('confdlgElim').hide()");
        
        if(CicloEscolarBeanHelper.getListaCicloSeleccionada().size() == 1){
            CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));
        }
        filtrado();                       
    }
    public void filtrado(){
        List<Rol> list = null;
        list= loginBean.getLogueado().getRolList();
        String seleccionado=loginBean.getSeleccionado();
        System.out.println("SE HA SELECCIONADO "+ seleccionado);
        String catalogo="Administración de ciclo escolar";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        listaFiltrada = CicloEscolarBeanHelper.filtrado("Nombre", busqueda);
    }
    /**
     * Metodo para validar en base al Calendario Actual 
     * que no se agreguen ciclos que aun no existen 
     * @return verdadero o falso
     */
    public boolean validacionCiclo(){
        return CicloEscolarBeanHelper.validacionCiclo();
    }
    public void modificacionConfirmada(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
        CicloEscolarBeanHelper.agregarCicloEscolar(CicloEscolarBeanHelper.getCicloescolar());
        CicloEscolarBeanHelper.setCicloescolar(new Cicloescolar());
        CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
        PrimeFaces.current().executeScript("PF('confdlgMod').hide()");
        filtrado();
    }
    
    public void confirmacionAceptada(){
        if(deshabilitar.equals("true")){
            if(CicloEscolarBeanHelper.getCicloescolar().getUnidadaprendizajeList().size() >= 1 || CicloEscolarBeanHelper.getCicloescolar().getUnidadaprendizajeImparteProfesorList().size() >= 1 && CicloEscolarBeanHelper.getCicloescolar().getUnidadaprendizajeImparteProfesorList() != null){
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar este Ciclo Escolar");
                FacesContext.getCurrentInstance().addMessage(null, message);

                limpiarSeleccion();
            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));            
                CicloEscolarBeanHelper.eliminarDeLista(CicloEscolarBeanHelper.getCicloescolar().getCESid());
                CicloEscolarBeanHelper.eliminarCicloEscolar(CicloEscolarBeanHelper.getCicloescolar());
                CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
                CicloEscolarBeanHelper.setCicloescolar(new Cicloescolar());
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

                if(CicloEscolarBeanHelper.getListaCicloSeleccionada().size()>=1){
                    CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));
                    PrimeFaces.current().executeScript("PF('dlg').show()");
                }
            }
        }
        filtrado();
    }
    public String onClickSubmit() {
        String resultado = "";
        setMensajeConfirm();

        if (deshabilitar.equals("true")) {
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");
        } else {
            if (validacion()) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validacion", "Capturar campo vacío ciclo escolar");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                mensajeRep = CicloEscolarBeanHelper.validarRepetidos();
                if (mensajeRep.isEmpty()) {
                    mensajeRep = "vacio";
                }
                if (mensajeRep.equals("vacio")) {
                    if (validacionCiclo()==true) {
                        if (header.equals("Agregar ciclo escolar")) {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                            CicloEscolarBeanHelper.agregarCicloEscolar(CicloEscolarBeanHelper.getCicloescolar());
                            CicloEscolarBeanHelper.setCicloescolar(new Cicloescolar());
                            CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
                        } else if (header.equals("Modificar ciclo escolar")) {
                            if(CicloEscolarBeanHelper.getCicloescolar().getUnidadaprendizajeList().size() >= 1 || CicloEscolarBeanHelper.getCicloescolar().getUnidadaprendizajeImparteProfesorList().size() >= 1){
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede Modificar este Ciclo Escolar");
                                FacesContext.getCurrentInstance().addMessage(null, message);
                                limpiarSeleccion();
                            }else{
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                                CicloEscolarBeanHelper.modificarCicloEscolar(CicloEscolarBeanHelper.getCicloescolar());
                                CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
                                CicloEscolarBeanHelper.setCicloescolar(CicloEscolarBeanHelper.getListaCicloSeleccionada().get(0));
                            }
                        }
                    }
                    else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ciclo escolar no válido");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Campos repetidos en:" + mensajeRep);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }
        filtrado();
        mostrarBotones();
        return resultado;
    }
    public void mostrarBotones(){
        try{
            if(CicloEscolarBeanHelper.getSelecCiclo().getCESid()> 0){
                deshabilitarBoton = "false";
                titleElim = "Eliminar";
                titleMod = "Modificar";
            }
        }catch(NullPointerException e){}
    }
   public void esconderBotones(){
        deshabilitarBoton = "true";
        titleElim = "Seleccione un registro de la tabla";
        titleMod = "Seleccione un registro de la tabla";
    }
    
    public String botonesSelect(){
        if(CicloEscolarBeanHelper.getSelecCiclo().getCESid()>0){
            return "false";
        }
        else{
            return "true";
        }
    }
    public boolean mostrarSeleccionGrupo() {
        return CicloEscolarBeanHelper.getListaCicloSeleccionada()!= null && CicloEscolarBeanHelper.getListaCicloSeleccionada().size() > 1;
    }
    
    public String toolTip(int i){
        if(CicloEscolarBeanHelper.getListaCicloSeleccionada()== null || CicloEscolarBeanHelper.getListaCicloSeleccionada().size() < 1)
            return "Seleccione un registro de la tabla";
        else{
            if(i == 1)
                return "Eliminar";
            else if(i == 2)
                return "Modificar";
        }
        return "";
    } 
     
    public void onRowUnselect(UnselectEvent event){
        CicloEscolarBeanHelper.setSelecCiclo(new Cicloescolar());
        CicloEscolarBeanHelper.setSelecCiclo((Cicloescolar)event.getObject());
    }
}
