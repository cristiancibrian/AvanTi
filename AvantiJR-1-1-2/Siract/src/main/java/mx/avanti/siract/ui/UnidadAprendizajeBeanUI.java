package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.helper.UnidadAprendizajeBeanHelper;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Edgar
 */
@ManagedBean
@ViewScoped
public class UnidadAprendizajeBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final int IDCATALOGOADMIUNIDADAPRENDIZAJE = 8;

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">
    private UnidadAprendizajeBeanHelper uAPBeanHelper;
    private Unidadaprendizaje unidadAprendizaje;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo lista">
    private List<Unidadaprendizaje> listaUapSeleccionadas;
    private List<Programaeducativo> listaProgramaEd;
    private List<Planestudio> listaPlanEstudio;
    private List<Areaconocimiento> listaAreaCon;
    private List<Integer> listaIdProgramaEd;
    private List<Integer> listaIdPlanEstudio;
    private List<Integer> listaIdAreaCon;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos String">
    private String cabeceraDialog;
    private String mensajeConfirmar;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables primitivas">
    private boolean mostrarListaPE = true;
    private boolean mostrarListaPlan = true;
    private boolean mostrarListaArea = true;
    private boolean deshabilitarBtnAceptar = false;
    private boolean mensajeCiclo = false;
    private boolean deshabilitarCampos;
    private boolean deshabilitarClaveUA;

    private int unidadAPSeleccionada;
    int contListaIdPlan = 0;
    int contListaIdPE = 0;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor y postConstructor">
    public UnidadAprendizajeBeanUI() {
        uAPBeanHelper = new UnidadAprendizajeBeanHelper();
    }

    @PostConstruct
    public void postConstructor() {
        uAPBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
        uAPBeanHelper.setUsuario(loginBean.getLogueado());
        loginBean.TienePermiso(loginBean.getLogueado().getRolList(), loginBean.getSeleccionado(), "Administración de unidad de aprendizaje");
        limpiarCamposDialog();
        listaProgramaEd = uAPBeanHelper.consultarProgramasEducativos();
    }
    //</editor-fold>

    /**
     * Método que inicia los campos en el dialog de agregar.
     */
    public void iniciarAgregado() {
        cabeceraDialog = "Agregar";
        mensajeConfirmar = "¿Está seguro de agregar la unidad de aprendizaje?";
        deshabilitarCampos = false;
        deshabilitarClaveUA = false;
        listaUapSeleccionadas = new ArrayList<>();
    }

    /**
     * Método que inicia los campos en el dialog de eliminar
     */
    public void iniciarEliminado() {
        cabeceraDialog = "Eliminar";
        mensajeConfirmar = "¿Está seguro de eliminar la unidad de aprendizaje?";
        deshabilitarCampos = true;
        deshabilitarClaveUA = true;
        unidadAprendizaje = listaUapSeleccionadas.get(0);
        llenarAreasPlanesProgramas();
    }

    /**
     * Método que inicia los campos en el dialog de actualizar.
     */
    public void iniciarActualizado() {
        cabeceraDialog = "Actualizar";
        mensajeConfirmar = "¿Está seguro de actualizar la unidad de aprendizaje?";
        deshabilitarCampos = false;
        deshabilitarClaveUA = true;
        unidadAprendizaje = listaUapSeleccionadas.get(0);
        llenarAreasPlanesProgramas();
    }

    /**
     * Método que ejecuta un agregado, eliminado o actualizado al presionar
     * el botón de aceptar.
     */
    public void ejecutarAceptado() {
        switch (cabeceraDialog) {
            case "Agregar":
                agregarAreasConocimiento();
                uAPBeanHelper.agregarUnidadAprendizaje(uAPBeanHelper.asignarContenidoTematico(unidadAprendizaje));
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Unidad de aprendizaje agregada correctamente."));
                break;
            case "Eliminar":
                uAPBeanHelper.eliminarUnidadAprendizaje(unidadAprendizaje);
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Unidad de aprendizaje eliminada correctamente."));
                break;
            default:
                agregarAreasConocimiento();
                uAPBeanHelper.actualizarUnidadAprendizaje(unidadAprendizaje);
                listaUapSeleccionadas.set(listaUapSeleccionadas.indexOf(unidadAprendizaje), unidadAprendizaje);
                if(listaAreaCon.size()>unidadAprendizaje.getAreaconocimientoList().size())
                    PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Error, seleccionar programa educativo, plan de estudios y área de conocimientos"));
                else
                    PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Unidad de aprendizaje actualizada correctamente."));
        }

        cerrarDialogLlenarFormulario();
    }

    /**
     * Método para agregar en la entidad de unidad de aprendizaje, las areas de
     * conocimiento asociadas.
     */
    public void agregarAreasConocimiento() {
        unidadAprendizaje.getAreaconocimientoList().clear();
        for (int i = 0; i < listaAreaCon.size(); i++) {
            for (int j = 0; j < listaIdAreaCon.size(); j++) {
                if (listaAreaCon.get(i).getACOid().intValue() == listaIdAreaCon.get(j).intValue()) {
                    unidadAprendizaje.getAreaconocimientoList().add(listaAreaCon.get(i));
                    break;
                }
            }
        }
    }

    /**
     * Método que cierra el dialog si ya no hay mas unidades de aprendizaje en
     * la lista seleccionada, o llena el formulario si aun hay unidades en la
     * lista.
     */
    public void cerrarDialogLlenarFormulario() {
        if (listaUapSeleccionadas.size() > 1) {
            if (cabeceraDialog.equals("Eliminar")) {
                listaUapSeleccionadas.remove(unidadAprendizaje);
                unidadAprendizaje = listaUapSeleccionadas.get(0);
                unidadAPSeleccionada = unidadAprendizaje.getUAPid();
                ejecutarMultiseleccion();
            }

            PrimeFaces.current().ajax().update("form3:cap", "form3:selecs");
        } else {
            limpiarCamposDialog();
            PrimeFaces.current().ajax().update("frmUnidApren:unidad");
            PrimeFaces.current().executeScript("PF('dlg').hide()");
        }
    }

    /**
     * Método para llenar areas de conocimiento, planes de estudio y programas
     * educativos al actualizar y eliminar.
     */
    public void llenarAreasPlanesProgramas() {
        if (!unidadAprendizaje.getAreaconocimientoList().isEmpty()) {
            for (Areaconocimiento areaconocimiento : unidadAprendizaje.getAreaconocimientoList()) {
                int idProgramaED = areaconocimiento.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid();
                int idPlanE = areaconocimiento.getPlanEstudioPESid().getPESid();
                listaIdAreaCon.add(areaconocimiento.getACOid());
                listaIdProgramaEd.add(idProgramaED);
                listaIdPlanEstudio.add(idPlanE);
            }

            filtrarPlanPorPE();
            filtrarAreaPorPlan();
        }
    }

    /**
     * Método que se ejecuta cuando se cambia una unidad de aprendizaje
     * seleccionada, al actualizar o eliminar mas de un registro.
     */
    public void ejecutarMultiseleccion() {
        unidadAprendizaje = uAPBeanHelper.buscarUnidadAprendizajePorID(unidadAPSeleccionada);
        listaPlanEstudio = new ArrayList<>();
        listaAreaCon = new ArrayList<>();
        listaIdProgramaEd = new ArrayList<>();
        listaIdPlanEstudio = new ArrayList<>();
        listaIdAreaCon = new ArrayList<>();
        llenarAreasPlanesProgramas();
    }

    /**
     * Método para asignarle clave de programa educativo a area de conocimiento.
     *
     * @param uAP Objeto tipo Unidad de Aprendizaje
     * @return area de conocimiento encontrada
     */
    public String consultaACDeUA(Unidadaprendizaje uAP) {
        String AreaConUA = "";

        if (!uAP.getAreaconocimientoList().isEmpty()) {
            for (Areaconocimiento areaCon : uAP.getAreaconocimientoList()) {
                int claveProgramaED = areaCon.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDclave();
                AreaConUA += "\n" + claveProgramaED + "-" + areaCon.getACOnombre() + ",\n";
            }

            AreaConUA = AreaConUA.substring(0, AreaConUA.length() - 2) + ".";

            return AreaConUA;
        } else {
            return "No tiene área de conocimiento asignada.";
        }
    }

    /**
     * Método para filtrar planes de estudio por programa educativo.
     */
    public void filtrarPlanPorPE() {
        listaPlanEstudio = new ArrayList<>();
        if (!listaIdProgramaEd.isEmpty()) {
            for (int idPrograma : listaIdProgramaEd) {
                listaPlanEstudio.addAll(uAPBeanHelper.buscarPlanPorProgramaeducativo(idPrograma));
            }
            if (listaIdProgramaEd.size() < contListaIdPE) {
                for (int i = 0; i < listaIdPlanEstudio.size(); i++) {
                    boolean planSelec = false;
                    for (Planestudio planE : listaPlanEstudio) {
                        if (planE.getPESid().intValue() == listaIdPlanEstudio.get(i).intValue()) {
                            planSelec = true;
                            break;
                        }
                    }
                    if (!planSelec) {
                        listaIdPlanEstudio.remove(i);
                    }
                }
            }
            obtenerClavePEDDePlan();
        } else {
            listaIdPlanEstudio.clear();
        }
        filtrarAreaPorPlan();
        contListaIdPE = listaIdProgramaEd.size();
        
    }

    /**
     * Método que filtra areas de conocimiento por planes de estudio.
     */
    public void filtrarAreaPorPlan() {
        listaAreaCon = new ArrayList<>();
        if (!listaIdPlanEstudio.isEmpty()) {
            for (int idPlan : listaIdPlanEstudio) {
                listaAreaCon.addAll(uAPBeanHelper.obtenerAreaMismoPlan(idPlan));
            }
            if (listaIdPlanEstudio.size() < contListaIdPlan) {
                for (int i = 0; i < listaIdAreaCon.size(); i++) {
                    boolean areaSelec = false;

                    for (Areaconocimiento areaCon : listaAreaCon) {
                        if (areaCon.getACOid().intValue() == listaIdAreaCon.get(i).intValue()) {
                            areaSelec = true;
                            break;
                        }
                    }

                    if (!areaSelec) {
                        listaIdAreaCon.remove(i);
                    }
                }
            }

            obtenerClavePEDDeAC();
        } else {
            listaIdAreaCon.clear();
        }
        contListaIdPlan = listaIdPlanEstudio.size();
        comprobarPrograma();
    }
    public void comprobarPrograma(){
        if(listaIdProgramaEd.size()>listaIdAreaCon.size()||listaIdProgramaEd.size()>listaIdPlanEstudio.size()){
            deshabilitarBtnAceptar = true;
             PrimeFaces.current().ajax().update("form3:BtnAceptar");
             PrimeFaces.current().ajax().update("frmUnidApren:growl");
        }
        else{
            deshabilitarBtnAceptar = false;
             PrimeFaces.current().ajax().update("form3:BtnAceptar");
             PrimeFaces.current().ajax().update("frmUnidApren:growl");
        }
    }
    /**
     * Metodo que permite establecer la clave de programa educativo de los
     * planes de estudio.
     */
    public void obtenerClavePEDDePlan() {
        for (int i = 0; i < listaPlanEstudio.size(); i++) {
            listaPlanEstudio.get(i).setPESvigenciaPlan(listaPlanEstudio.get(i).getProgramaEducativoPEDid().getPEDclave() + "-" + listaPlanEstudio.get(i).getPESvigenciaPlan());
        }
    }

    /**
     * Método que permite establecer la clave de programa educativo de las areas
     * de conocimiento.
     */
    public void obtenerClavePEDDeAC() {
        for (int i = 0; i < listaAreaCon.size(); i++) {
            int claveProgramaED = listaAreaCon.get(i).getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDclave();
            listaAreaCon.get(i).setACOnombre(claveProgramaED + "-" + listaAreaCon.get(i).getACOclave() + "-" + listaAreaCon.get(i).getACOnombre());
        }
    }

    /**
     * Método para obtener horas extra clase de unidad de aprendizaje.
     */
    public void horasExtraClase() {
        unidadAprendizaje.setUAPhorasExtraClase(unidadAprendizaje.getUAPhorasClase());
        establecerCreditos();
    }

    /**
     * Método para establecer los creditos de una unidad de aprendizaje.
     */
    public void establecerCreditos() {
        unidadAprendizaje.setUAPcreditos(unidadAprendizaje.getUAPhorasClase() + unidadAprendizaje.getUAPhorasTaller() + unidadAprendizaje.getUAPhorasLaboratorio()
                + unidadAprendizaje.getUAPhorasCampo() + unidadAprendizaje.getUAPhorasClinica() + unidadAprendizaje.getUAPhorasExtraClase());
    }

    /**
     * Método para limpiar los campos del dialog.
     */
    public void limpiarCamposDialog() {
        unidadAPSeleccionada = 0;
        unidadAprendizaje = new Unidadaprendizaje();
        unidadAprendizaje.setCicloEscolarCESid(new Cicloescolar(0));        
        unidadAprendizaje.setAreaconocimientoList(new ArrayList());
        iniciarHorasEnCero();

        listaUapSeleccionadas = new ArrayList<>();
        listaIdProgramaEd = new ArrayList<>();
        listaIdPlanEstudio = new ArrayList<>();
        listaIdAreaCon = new ArrayList<>();
        listaPlanEstudio = new ArrayList<>();
        listaAreaCon = new ArrayList<>();
        deshabilitarBtnAceptar = false;
        mensajeCiclo = false;
        uAPBeanHelper.filtrarPorRol();
    }

    /**
     * Método para iniciar en cero las horas de una unidad de aprendizaje.
     */
    public void iniciarHorasEnCero() {
        unidadAprendizaje.setUAPhorasClase(0);
        unidadAprendizaje.setUAPhorasTaller(0);
        unidadAprendizaje.setUAPhorasLaboratorio(0);
        unidadAprendizaje.setUAPhorasCampo(0);
        unidadAprendizaje.setUAPhorasClinica(0);
        unidadAprendizaje.setUAPhorasExtraClase(0);
    }

    /**
     * Método para deshabilitar los botones de actualizar y eliminar.
     *
     * @return true si la lista de Unidades de Aprendizaje es null o menor a 1,
     * false en cualquier otro caso.
     */
    public boolean deshabilitarMenu() {
        return listaUapSeleccionadas.isEmpty();
    }

    /**
     * Método para mostrar la seleccion de unidad de aprendizaje.
     *
     * @return true
     */
    public boolean mostrarSeleccionUA() {
        return !listaUapSeleccionadas.isEmpty() && listaUapSeleccionadas.size() > 1;
    }

    /**
     * Método para validar que solo haya un area de conocimiento por programa
     * educativo.
     */
    public void validarAreaConocimiento() {
        HashSet<Integer> listaProgramaE = new HashSet<>();
        deshabilitarBtnAceptar = false;
        if (!listaIdAreaCon.isEmpty()) {
            for (int idAreaCon : listaIdAreaCon) {
                int idProgramaED = uAPBeanHelper.buscarPEDeAreaConocimiento(idAreaCon).getPEDid();

                if (!listaProgramaE.contains(idProgramaED)) {
                    listaProgramaE.add(idProgramaED);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Solo un área de conocimiento por Programa Educativo."));
                    deshabilitarBtnAceptar = true;
                    break;
                }
            }
        }
        comprobarPrograma();
    }

    /**
     * Método para validar campos vacios.
     *
     * @return Regresa true si hay un campo vacio, false en cualquier otro caso.
     */
    public boolean validarCamposVacios() {
        return (unidadAprendizaje.getUAPnombre().isEmpty() || unidadAprendizaje.getUAPetapaFormacion().equals("0")
                || unidadAprendizaje.getUAPtipoCaracter().equals("0") || unidadAprendizaje.getCicloEscolarCESid().getCESid() == 0);
    }

    /**
     * Método que valida al eliminar una unidad de aprendizaje.
     *
     * @return bandera que indica si todo esta validado.
     */
    public boolean validarAlEliminar() {
        if (!uAPBeanHelper.validarUATieneAsignacion(unidadAprendizaje.getUAPid())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La unidad de aprendizaje \"" + unidadAprendizaje.getUAPnombre()
                    + "\", no se puede eliminar debido a que ya tiene grupos asignados."));
            return false;
        }

        return true;
    }

    /**
     * Método que valida al agregar o actualizar una unidad de aprendizaje.
     *
     * @return bandera que indica si todo esta validado.
     */
    public boolean validarAlAgregarOActualizar() {
        if (validarCamposVacios()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Favor de llenar todos los campos vacios."));
            return false;
        }

        if (listaIdAreaCon.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Falta asignar area de conocimiento."));
            return false;
        }

        if (cabeceraDialog.equals("Agregar") && uAPBeanHelper.buscarUnidadAprendizajePorClave(Integer.toString(unidadAprendizaje.getUAPclave())) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La clave de unidad de aprendizaje, ya existe."));
            return false;
        } else if (cabeceraDialog.equals("Agregar") && unidadAprendizaje.getUAPclave() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La clave de unidad de aprendizaje, no puede ser 0."));
            return false;
        }

        return true;
    }

    /**
     * Método que permite validar todos los campos de unidad de aprendizaje.
     *
     */
    public void validarAceptado() {
        boolean valido;

        if (cabeceraDialog.equals("Agregar") || cabeceraDialog.equals("Actualizar")) {
            valido = validarAlAgregarOActualizar();
        } else {
            valido = validarAlEliminar();
        }

        if (valido) {
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");
        }
    }

    /**
     * Método para establecer mensaje en el boton de ciclo escolar de unidad de
     * aprendizaje.
     */
    public void mensajeNuevo() {
        if (!mensajeCiclo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Si desea agregar un nuevo ciclo escolar, por favor contacte al Administrador."));
            mensajeCiclo = true;
        }
    }
    
    public void keyupClaveUA(){
        UnidadAprendizajeBeanHelper ua  = new UnidadAprendizajeBeanHelper();
        Unidadaprendizaje unidad =  new Unidadaprendizaje();
       
        
        unidad = ua.buscarUnidadAprendizajePorClave(String.valueOf(unidadAprendizaje.getUAPclave()));
        if(unidad != null){
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La clave de unidad de aprendizaje, ya existe."));
            PrimeFaces.current().ajax().update("frmUnidApren:growl");
            deshabilitarBtnAceptar = true;
            PrimeFaces.current().ajax().update("form3:BtnAceptar");
            
            
        }else{
             deshabilitarBtnAceptar = false;
            PrimeFaces.current().ajax().update("form3:BtnAceptar");
             PrimeFaces.current().ajax().update("frmUnidApren:growl");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public UnidadAprendizajeBeanHelper getUAPBeanHelper() {
        return uAPBeanHelper;
    }

    public void setUABeanHelper(UnidadAprendizajeBeanHelper UAPBeanHelper) {
        this.uAPBeanHelper = UAPBeanHelper;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Unidadaprendizaje getUnidadAprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        this.unidadAprendizaje = unidadAprendizaje;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo lista">
    public List<Unidadaprendizaje> getListaUapSeleccionadas() {
        return listaUapSeleccionadas;
    }

    public void setListaUapSeleccionadas(List<Unidadaprendizaje> listaUapSeleccionadas) {
        this.listaUapSeleccionadas = listaUapSeleccionadas;
    }

    public List<Programaeducativo> getListaProgramaEd() {
        return listaProgramaEd;
    }

    public void setListaPE(List<Programaeducativo> listaProgramaEd) {
        this.listaProgramaEd = listaProgramaEd;
    }

    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    public void setListaPlan(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }

    public List<Areaconocimiento> getListaAC() {
        return listaAreaCon;
    }

    public void setListaAreaCon(List<Areaconocimiento> listaAreaCon) {
        this.listaAreaCon = listaAreaCon;
    }

    public List<Integer> getListaIdProgramaEd() {
        return listaIdProgramaEd;
    }

    public void setListaIdProgramaEd(List<Integer> listaIdProgramaEd) {
        this.listaIdProgramaEd = listaIdProgramaEd;
    }

    public List<Integer> getListaIdPlanEstudio() {
        return listaIdPlanEstudio;
    }

    public void setListaIdPlanEstudio(List<Integer> listaIdPlan) {
        this.listaIdPlanEstudio = listaIdPlan;
    }

    public List<Integer> getListaIdAreaCon() {
        return listaIdAreaCon;
    }

    public void setListaIdAreaCon(List<Integer> listaIdAreaCon) {
        this.listaIdAreaCon = listaIdAreaCon;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos String">
    public String getCabeceraDialog() {
        return cabeceraDialog;
    }

    public void setCabeceraDialog(String cabeceraDialog) {
        this.cabeceraDialog = cabeceraDialog;
    }

    public String getMensajeConfirmar() {
        return mensajeConfirmar;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de variables primitivas">
    public int getIDCATALOGOADMIUNIDADAPRENDIZAJE() {
        return IDCATALOGOADMIUNIDADAPRENDIZAJE;
    }

    public boolean isDeshabilitarCampos() {
        return deshabilitarCampos;
    }

    public void setDeshabilitarCampos(boolean deshabilitarCampos) {
        this.deshabilitarCampos = deshabilitarCampos;
    }

    public boolean isDeshabilitarClaveUA() {
        return deshabilitarClaveUA;
    }

    public void setDeshabilitarClaveUA(boolean deshabilitarClaveUA) {
        this.deshabilitarClaveUA = deshabilitarClaveUA;
    }

    public boolean isDeshabilitarBtnAceptar() {
        return deshabilitarBtnAceptar;
    }

    public void setDeshabilitarBtnAceptar(boolean deshabilitarBtnAceptar) {
        this.deshabilitarBtnAceptar = deshabilitarBtnAceptar;
    }

    public boolean isMostrarListaPE() {
        return mostrarListaPE;
    }

    public void setMostrarListaPE(boolean mostrarListaPE) {
        this.mostrarListaPE = mostrarListaPE;
    }

    public boolean isMostrarListaPlan() {
        return mostrarListaPlan;
    }

    public void setMostrarListaPlan(boolean mostrarListaPlan) {
        this.mostrarListaPlan = mostrarListaPlan;
    }

    public boolean isMostrarListaArea() {
        return mostrarListaArea;
    }

    public void setMostrarListaArea(boolean mostrarListaArea) {
        this.mostrarListaArea = mostrarListaArea;
    }

    public int getUnidadAPSeleccionada() {
        return unidadAPSeleccionada;
    }

    public void setUnidadAPSeleccionada(int unidadAPSeleccionada) {
        this.unidadAPSeleccionada = unidadAPSeleccionada;
    }
    //</editor-fold>
}
