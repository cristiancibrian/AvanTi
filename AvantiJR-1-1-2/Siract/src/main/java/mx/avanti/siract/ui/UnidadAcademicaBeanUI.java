package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.helper.UnidadAcademicaBeanHelper;
import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class UnidadAcademicaBeanUI implements Serializable{
    
    private final int IDCATALOGOADMIUNIACADEMICA = 5;

    public int getIDCATALOGOADMIUNIACADEMICA() {
        return IDCATALOGOADMIUNIACADEMICA;
    }
    
    
    
    private UnidadAcademicaBeanHelper unidadAcademicaBeanHelper = null;
    private List<Unidadacademica> listaFiltrada;
    private List<Campus> listaCampus;
    private String busqueda="";
    private String header;
    private String boton;
    private String deshabilitar;
    private String message;
    private String deshabilitarBoton = "true";
    private String mensajeEliminar;
    private boolean Bandera;
    private String renderCancelar;
    private List<Unidadacademica> listaUnidadAcademica;
    String uatipo="";
    int x=0;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public List<Unidadacademica> getListaUnidadAcademica() {
        listaUnidadAcademica = unidadAcademicaBeanHelper.getListaUAGeneral();
        return listaUnidadAcademica;
    }
    public void setListaUnidadAcademica(List<Unidadacademica> listaUnidadAcademica) {
        this.listaUnidadAcademica = listaUnidadAcademica;
    }
    public List<Campus> getListaCampus() {
        return listaCampus=unidadAcademicaBeanHelper.consultaCampus();
    }
    public void setListaCampus(List<Campus> listaCampus) {
        this.listaCampus = listaCampus;
    }
    public LoginBean getLoginBean() {
        return loginBean;
    }
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    public String getRenderCancelar() {
        return renderCancelar;
    }
    public void setRenderCancelar(String renderCancelar) {
        this.renderCancelar = renderCancelar;
    }
    public String getMensajeEliminar() {
        return mensajeEliminar;
    }
    public void setMensajeEliminar(String mensajeEliminar) {
        this.mensajeEliminar = mensajeEliminar;
    }
    public String getUatipo() {
        return uatipo;
    }
    public void setUatipo(String uatipo) {
        this.uatipo = uatipo;
    }
    public boolean isBandera() {
        return Bandera;
    }
    public void setBandera(boolean Bandera) {
        this.Bandera = Bandera;
    }
    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }
    public void setDeshabilitarBoton(String deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
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
    public UnidadAcademicaBeanUI(){
        init();
    }
    private void init(){
        unidadAcademicaBeanHelper = new UnidadAcademicaBeanHelper();
        x=0;
    }
    public UnidadAcademicaBeanHelper getUnidadAcademicaBeanHelper() {
        return unidadAcademicaBeanHelper;
    }
    public String getBusqueda() {
        return busqueda;
    }
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    public List<Unidadacademica> getListaFiltrada() {
        return listaFiltrada;
    }
    public void setListaFiltrada(List<Unidadacademica> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    /**
     * Filtra la lista de Unidades academicas por el atributo busqueda
     */
    public void filtrado() {
        List<Rol> list = null;
        list= loginBean.getLogueado().getRolList();
        String seleccionado=loginBean.getSeleccionado();
        System.out.println(seleccionado+"??????????????????????????????????????????????????????????????");
        String catalogo="Administraci??n de unidad acad??mica";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        busqueda=busqueda.toLowerCase();
        listaFiltrada = unidadAcademicaBeanHelper.filtrado(busqueda);
        
    }
    /**
     * Obtiene una lista con las unidades Academicas y la guarda en el atributo
     * listaFiltrada
     */
    public void filtrado2() {
        listaFiltrada = unidadAcademicaBeanHelper.filtrado2(unidadAcademicaBeanHelper.getCampus().getCAMid());
    }
    /**
     * M??todo para Instanciar una nueva Unidad Academica
     */
    public void nuevo(){
        limpiar();
        cabecera(1);
        unidadAcademicaBeanHelper.setUnidadacademica(new Unidadacademica());
        unidadAcademicaBeanHelper.setCampus(new Campus());
    }
   
    /**
     * M??todo para mostrar el mensaje cuando se requiere Modificar una Unidad Academica
     */
    public void modificar(){
          cabecera(3);
        try{
            if(unidadAcademicaBeanHelper.getListaSeleccionUA().size()==1){
                unidadAcademicaBeanHelper.setUnidadacademica(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0));
                unidadAcademicaBeanHelper.setCampus(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0).getCampusCAMid());
            }else {
                if (unidadAcademicaBeanHelper.getListaSeleccionUA().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                } else {
                    unidadAcademicaBeanHelper.setUnidadacademica(new Unidadacademica());
                    unidadAcademicaBeanHelper.setCampus(new Campus());
                    unidadAcademicaBeanHelper.setUnidadacademica(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0));
                    unidadAcademicaBeanHelper.setCampus(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0).getCampusCAMid());
                }
            }
        }catch(NullPointerException e){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");         
            //RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }}
    /**
     * M??todo para mostrar un mensaje cuando se requiere Eliminar una Unidad Academica
     */
    public void eliminar(){
         cabecera(2);
        try{
             if(unidadAcademicaBeanHelper.getListaSeleccionUA().size()==1){
                unidadAcademicaBeanHelper.setUnidadacademica(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0));
                unidadAcademicaBeanHelper.setCampus(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0).getCampusCAMid());
            }else {
                if (unidadAcademicaBeanHelper.getListaSeleccionUA().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                   // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                } else {
                    unidadAcademicaBeanHelper.setUnidadacademica(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0));
                unidadAcademicaBeanHelper.setCampus(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0).getCampusCAMid());
                }             
             }
        }catch(NullPointerException e){
            unidadAcademicaBeanHelper.setUnidadacademica(new Unidadacademica());
            unidadAcademicaBeanHelper.setCampus(new Campus());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");         
           // RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }
    /**
     * Metodo para hacer la funcionalida de Modificar Unidad Academica
     * @return String vac??o
     */
    public String onClickSubmit(){
        Campus campus=unidadAcademicaBeanHelper.getCampus();
        setMensajeConfirmacion();
        if(deshabilitar.equals("false")){
            if(validacionVacio()){
                if(header.equals("Modificar unidad acad??mica")){
                    //RequestContext.getCurrentInstance().execute("PF('conDlgElim').show()");
                    PrimeFaces.current().executeScript("PF('conDlgElim').show()");

                }
                else if(header.equals("Agregar unidad acad??mica")){
                    if (validarRepetido() == true){
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("","Se guard?? correctamente"));
                    unidadAcademicaBeanHelper.getUnidadacademica().setCampusCAMid(unidadAcademicaBeanHelper.consultaCampusPorID(unidadAcademicaBeanHelper.getCampus().getCAMid()));
                    unidadAcademicaBeanHelper.getUnidadacademica().setCampusCAMid(unidadAcademicaBeanHelper.consultaCampusPorID(unidadAcademicaBeanHelper.getUnidadacademica().getCampusCAMid().getCAMid()));
                    unidadAcademicaBeanHelper.getUnidadacademica().setUACtipo("1");
                    unidadAcademicaBeanHelper.agregarUnidadAcademica();
                    unidadAcademicaBeanHelper.setSelecUnidadAcademica(new Unidadacademica());
                    unidadAcademicaBeanHelper.setUnidadacademica(new Unidadacademica());
                    //RequestContext.getCurrentInstance().execute("PF('dlg').show()");  
                    PrimeFaces.current().executeScript("PF('dlg').show()");

                   // RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                    PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

                   // RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                    PrimeFaces.current().executeScript("PF('dlg').hide()");

                    }else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validaci??n", "La unidad Acad??mica ya existe");
                       // RequestContext.getCurrentInstance().showMessageInDialog(message);
                        FacesContext.getCurrentInstance().addMessage(null, message);

                    }
                }
            }
        }else{          
            //RequestContext.getCurrentInstance().update("conDlgElim");
            PrimeFaces.current().ajax().update("conDlgElim");

            //RequestContext.getCurrentInstance().execute("PF('conDlgElim').show()");
            PrimeFaces.current().executeScript("PF('conDlgElim').show()");

        }   
        unidadAcademicaBeanHelper.setCampus(campus); 
       filtrado2();
       return "";
    }
    /**
     * M??todo para modificar la cabecera, S?? i=1 header="Agregar Unidad Academica",
     * S?? i=2 header="Eliminar unidad Academica", S?? i=3 header="Modificar Unidad Academica"
     * @param i tipo int
     * @return header tipo String
     */
    public String cabecera(int i){
        if(i==1){
            setHeader("Agregar unidad acad??mica");
            deshabilitar="false";
        }else{
            if(i==2){
            setHeader("Eliminar unidad acad??mica");
            deshabilitar="true";
        }else{
            if(i==3){
            header="Modificar unidad acad??mica";
            deshabilitar="false";
        }}}
        return header;
    }
    /**
     * M??todo para validar si existen campos faltantes
     * S?? existen campos faltantes regresa false, Si no existen campos faltantes regresa true
     * @return boolean
     */
    public boolean validacionVacio(){
        if(unidadAcademicaBeanHelper.getCampus().getCAMid()==0
                || unidadAcademicaBeanHelper.getUnidadacademica().getUACclave()==0
                || unidadAcademicaBeanHelper.getUnidadacademica().getUACnombre().isEmpty()){
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validaci??n", "Llenar campo(s) faltantes");
            // RequestContext.getCurrentInstance().showMessageInDialog(message);
             FacesContext.getCurrentInstance().addMessage(null, message);

             return false;
         }else{
             return true;
         }}
    /**
     * M??todo para mostrar mensajes de confirmaci??n cuando se elimina o se modifica 
     * una unidad de Aprendizaje
     */
     public void eliminConfir(){
        Campus campus=unidadAcademicaBeanHelper.getCampus();
        FacesContext context = FacesContext.getCurrentInstance();
        if(header.equals("Eliminar unidad acad??mica")){
             if(renderCancelar.equals("true")){  
                context.addMessage(null, new FacesMessage("","Se elimin?? correctamente"));   
                unidadAcademicaBeanHelper.eliminarDeLista(unidadAcademicaBeanHelper.getUnidadacademica().getUACid());
                unidadAcademicaBeanHelper.eliminarUnidadAcademica(unidadAcademicaBeanHelper.getUnidadacademica()); 
                unidadAcademicaBeanHelper.setSelecUnidadAcademica(unidadAcademicaBeanHelper.getListaUAGeneral().get(5));
                unidadAcademicaBeanHelper.setUnidadacademica(unidadAcademicaBeanHelper.getListaUAGeneral().get(5)); 
                unidadAcademicaBeanHelper.setCampus(campus);
               // RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

               // RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                PrimeFaces.current().executeScript("PF('dlg').hide()");

               
                if(unidadAcademicaBeanHelper.getListaSeleccionUA().size()>=1){
                    unidadAcademicaBeanHelper.setUnidadacademica(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0));
                    unidadAcademicaBeanHelper.setCampus(unidadAcademicaBeanHelper.getListaSeleccionUA().get(0).getCampusCAMid());
                    //RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                    PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

                   // RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                    PrimeFaces.current().executeScript("PF('dlg').hide()");

            
                }
                unidadAcademicaBeanHelper.getListaSeleccionUA().clear();
                listaFiltrada.clear();
            }else{                
               // RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

                //RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                PrimeFaces.current().executeScript("PF('dlg').hide()");

                limpiar();
                mostrarSeleccionUA();
                botones();
            }
        }else{
            if(header.equals("Modificar unidad acad??mica")){
                if(unidadAcademicaBeanHelper.getProfesoresPorUnidadAcademica().size()==0){
                    if(validarRepetido()){
                        context.addMessage(null, new FacesMessage("","Se modifico correctamente"));        
                        unidadAcademicaBeanHelper.modificarUnidadAcademica();
                       // RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                        PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

                        //RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                        PrimeFaces.current().executeScript("PF('dlg').hide()");

                    }else{
                        context.addMessage(null, new FacesMessage("","Ya esta esa clave registrada"));
                        //RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                        PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

                        //RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                        PrimeFaces.current().executeScript("PF('dlg').hide()");

                        
                    }
                }else{
//                    context.addMessage(null, new FacesMessage("","No se puede modificar el registro por que la unidad tiene profesores asociados"));
                    //RequestContext.getCurrentInstance().execute("PF('conDlgElim').hide()");
                    PrimeFaces.current().executeScript("PF('conDlgElim').hide()");

                    //RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                    PrimeFaces.current().executeScript("PF('dlg').hide()");

                }
            }
        }
        deshabilitar="False";
        unidadAcademicaBeanHelper.setCampus(campus);
        filtrado2();
     }
     
     public void habilitarBotons(){
         deshabilitarBoton = "false";
     }   
      public void deshabilitarBotons(){
          if(unidadAcademicaBeanHelper.getListaSeleccionUA().size()<1){
              deshabilitarBoton = "true";
              limpiar();
          }
      }
      /**
       * M??todo que regresa un true si existen Unidades Academicas seleccionadas
       * y un false si no hay unidades academicas seleccionadas
       * @return boolean 
       */
      public boolean botones(){
          if (unidadAcademicaBeanHelper.getListaSeleccionUA() == null || unidadAcademicaBeanHelper.getListaSeleccionUA().size() < 1) {
            return true;
        }
        return false;
      }
      /**
       * M??todo que valida si  una Unidad Academica esta repetida
       * @return Bandera tipo boolean
       */
      public boolean validarRepetido(){
        int id=0;
        if(unidadAcademicaBeanHelper.getUnidadacademica().getUACid()!=null){
            id=unidadAcademicaBeanHelper.getUnidadacademica().getUACid();
        }
        String unidadacademica = Integer.toString(unidadAcademicaBeanHelper.getUnidadacademica().getUACclave());
        Bandera = unidadAcademicaBeanHelper.Validar(unidadacademica, id);
        return Bandera;
      }
      /**
       * M??todo que instancia los atributos UnidadAcademica, y Campus de la clase
       * unidadAcademicaBeanHelper
       */
      public void limpiar(){
          unidadAcademicaBeanHelper.setUnidadacademica(new Unidadacademica()); 
          unidadAcademicaBeanHelper.setCampus(new Campus());
          unidadAcademicaBeanHelper.setSelecUnidadAcademica(new Unidadacademica());
          unidadAcademicaBeanHelper.setListaSeleccionUA(null);
          mostrarSeleccionUA();
          botones();
      }
      /**
       * M??todo que Regresa un true si no hay UnidadesAcademicas Seleccionadas 
       * y un false cuando hay UnidadesAcademicas Seleccionadas
       * @return boolean
       */
      public boolean mostrarSeleccionUA() {
        if (unidadAcademicaBeanHelper.getListaSeleccionUA() != null && unidadAcademicaBeanHelper.getListaSeleccionUA().size() > 1) {
            return true;
        } else {
            return false;
        }
    }
      
    public void setMensajeConfirmacion(){
        if (deshabilitar.equals("true")){
            if(unidadAcademicaBeanHelper.getUAasignado(unidadAcademicaBeanHelper.getUnidadacademica().getUACid()).size()>0){
                mensajeEliminar="La Unidad Acad??mica ya est?? asignada a un programa educativo, no se puede eliminar";
                renderCancelar="false";
            }else{
                mensajeEliminar="??Est??s seguro de eliminar el registro?";
                renderCancelar="true";
            }
        }else{
            if(header.equals("Modificar unidad acad??mica")){
                renderCancelar="true";
                if(unidadAcademicaBeanHelper.getUAasignado(unidadAcademicaBeanHelper.getUnidadacademica().getUACid()).size()>0){
                    mensajeEliminar="La Unidad Acad??mica ya est?? asignada a un programa educativo no puede ser modificada";
                }else{
                    mensajeEliminar="??Est?? seguro de guardar los cambios?";
                }
            }
        }
        //RequestContext.getCurrentInstance().update("confirmid");
        PrimeFaces.current().ajax().update("confirmid");

    }
}
