package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.helper.GrupoBeanHelper;
import mx.avanti.siract.helper.PlanEstudioBeanHelper;
import mx.avanti.siract.helper.ProgramaEducativoBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Oscar D. Sanchez
 */
@ManagedBean
@ViewScoped
public class GrupoBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    private GrupoBeanHelper grupoBeanHelper = null;
    private PlanEstudioBeanHelper planEstudioBeanHelper = null;
    private ProgramaEducativoBeanHelper programaEducativoBeanHelper = null;

    private String deshabilitarBoton = "true";
    private String titleMod = "Seleccione un registro de la tabla";
    private String titleElim = "Seleccione un registro de la tabla";

    private String header;
    private String deshabilitar;
    private String deshabilitarAceptar;

    private String mensajeConfirm;
    private String botonAceptar;

    private String busqueda = "";
    private List<Grupo> listaFiltrada;

    private List<Planestudio> listaPlanestudio;
    private List<Programaeducativo> listaProgramaEducativo;
    private String progEd;
    private String planEd;

    private String mensajeRep;
    private String mensajeVacio = "";

    private final int IDCATALOGOADMIGRUPO = 11;

    public int getIDCATALOGOADMIGRUPO() {
        return IDCATALOGOADMIGRUPO;
    }

    private Integer idPlanEstudio;
    private String clavePrograma;
    private int numGrupo;

    private boolean band = false;

    @PostConstruct
    public void postConstructor() {
        grupoBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
        grupoBeanHelper.setUsuario(loginBean.getLogueado());
        filtrado();
    }

    public GrupoBeanUI() {
        init();
    }

    private void init() {
        grupoBeanHelper = new GrupoBeanHelper();
        planEstudioBeanHelper = new PlanEstudioBeanHelper();
        programaEducativoBeanHelper = new ProgramaEducativoBeanHelper();
    }

    public GrupoBeanHelper getGrupoBeanHelper() {
        return grupoBeanHelper;
    }

    public Integer getIdPlanEstudio() {
        return idPlanEstudio;
    }

    public String getClavePrograma() {
        return clavePrograma;
    }

    public void setClavePrograma(String clavePrograma) {
        this.clavePrograma = clavePrograma;
    }

    public int getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(int numGrupo) {
        this.numGrupo = numGrupo;
    }

    public void setIdPlanEstudio(Integer idPlanEstudio) {
        this.idPlanEstudio = idPlanEstudio;

        grupoBeanHelper.setPlanestudio(grupoBeanHelper.AsignarPlanEstudio(idPlanEstudio));
        grupoBeanHelper.setPlanestudioD(grupoBeanHelper.AsignarPlanEstudio(idPlanEstudio));
    }

    public PlanEstudioBeanHelper getPlanEstudioBeanHelper() {
        return planEstudioBeanHelper;
    }

    public void setPlanEstudioBeanHelper(PlanEstudioBeanHelper planEstudioBeanHelper) {
        this.planEstudioBeanHelper = planEstudioBeanHelper;
    }

    public void setGrupoBeanHelper(GrupoBeanHelper grupoBeanHelper) {
        this.grupoBeanHelper = grupoBeanHelper;
    }

    public ProgramaEducativoBeanHelper getProgramaEducativoBeanHelper() {
        return programaEducativoBeanHelper;
    }

    public void setProgramaEducativoBeanHelper(ProgramaEducativoBeanHelper programaEducativoBeanHelper) {
        this.programaEducativoBeanHelper = programaEducativoBeanHelper;
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

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<Grupo> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Grupo> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Planestudio> getListaPlanestudio() {
        return listaPlanestudio;
    }

    public void setListaPlanestudio(List<Planestudio> listaPlanestudio) {
        this.listaPlanestudio = listaPlanestudio;
    }

    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
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

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public void setMensajeConfirm(String mensajeConfirm) {
        this.mensajeConfirm = mensajeConfirm;
    }

    public String getBotonAceptar() {
        return botonAceptar;
    }

    public void setBotonAceptar(String botonAceptar) {
        this.botonAceptar = botonAceptar;
    }

    public String getMensajeRep() {
        return mensajeRep;
    }

    public void setMensajeRep(String mensajeRep) {
        this.mensajeRep = mensajeRep;
    }

    public String getMensajeVacio() {
        return mensajeVacio;
    }

    public void setMensajeVacio(String mensajeVacio) {
        this.mensajeVacio = mensajeVacio;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    /**
     * Metodo para establecer mensaje de confimacion
     */
    public void setMensajeConfirm() {
        if (deshabilitar.equals("true")) {
            if (grupoBeanHelper.getGrupoAsignado()) {
                mensajeConfirm = "El grupo se encuentra asignado a una  unidad de aprendizaje y profesor.";
                botonAceptar = "false";
            } else {
                mensajeConfirm = "¿Está seguro de eliminar el registro?";
                botonAceptar = "true";
            }
        } else if (header.equals("Modificar grupo") && grupoBeanHelper.getGrupoAsignado()) {
            mensajeConfirm = "El grupo se encuentra asignado a una  unidad de aprendizaje y profesor. ¿Está seguro de guardar los cambios?";
        }
       
        PrimeFaces.current().ajax().update("confirmdlgId");

    }

    /**
     * Metodo para establecer cabecera de ventana emergente
     *
     * @param i Valor establecido anteriormente
     * @return Texto de la cabecera
     */
    public String dlgCabecera(int i) {
        if (i == 1) {
            header = "Agregar grupo";
            deshabilitar = "false";
        }
        if (i == 2) {
            header = "Eliminar grupo";
            deshabilitar = "true";
        }
        if (i == 3) {
            header = "Modificar grupo";
            deshabilitar = "false";
        }
        return header;
    }

    /**
     * Metodo para agregar un nuevo Grupo
     */
    public void nuevo() {
        limpiarSeleccion();
        dlgCabecera(1);
        grupoBeanHelper.setGrupo(new Grupo());
        grupoBeanHelper.setPlanestudioD(new Planestudio());
        grupoBeanHelper.setProgramaEducativoD(new Programaeducativo());
        cargarPlanEstudio();
    }

    /**
     * Metodo para modificar un grupo
     */
    public void modificar() {
        cargarPlanEstudio();
        dlgCabecera(3);
        try {
            if (grupoBeanHelper.getListaGpoSeleccionada().size() >= 1) {
                grupoBeanHelper.setGrupo(grupoBeanHelper.getListaGpoSeleccionada().get(0));
                grupoBeanHelper.setPlanestudioD(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
                grupoBeanHelper.setProgramaEducativoD(grupoBeanHelper.findProgramaEducativoById(grupoBeanHelper.getPlanestudioD().getProgramaEducativoPEDid().getPEDid()));
                clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativoD().getPEDid()).getPEDclave());
                numGrupo = Integer.parseInt(String.valueOf(grupoBeanHelper.getGrupo().getGPOnumero()).replace(clavePrograma, ""));
                dialogFiltrarPlan();
            } else {
                if (grupoBeanHelper.getListaGpoSeleccionada().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                  //  RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }            
            }
        } catch (NullPointerException e) {
            grupoBeanHelper.setGrupo(new Grupo());        
            grupoBeanHelper.setPlanestudioD(new Planestudio());
            grupoBeanHelper.setProgramaEducativoD(new Programaeducativo());
        }
    }

    /**
     * Metodo para eliminar un grupo
     */
    public void eliminar() {

        cargarPlanEstudio();

        dlgCabecera(2);
        try {
            if (grupoBeanHelper.getListaGpoSeleccionada().size() >= 1) {
                grupoBeanHelper.setGrupo(grupoBeanHelper.getListaGpoSeleccionada().get(0));
//                grupoBeanHelper.setPlanestudio(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
//                grupoBeanHelper.setProgramaEducativo(grupoBeanHelper.findProgramaEducativoById(grupoBeanHelper.getPlanestudio().getProgramaEducativoPEDid().getPEDid()));
                //clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativo().getPEDid()).getPEDclave());

                grupoBeanHelper.setPlanestudioD(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
                grupoBeanHelper.setProgramaEducativoD(grupoBeanHelper.findProgramaEducativoById(grupoBeanHelper.getPlanestudioD().getProgramaEducativoPEDid().getPEDid()));
                clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativoD().getPEDid()).getPEDclave());
                numGrupo = Integer.parseInt(String.valueOf(grupoBeanHelper.getGrupo().getGPOnumero()).replace(clavePrograma, ""));
                dialogFiltrarPlan();
            } else {
                if (grupoBeanHelper.getListaGpoSeleccionada().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                   // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }
//              
            }
        } catch (NullPointerException e) {
            grupoBeanHelper.setGrupo(new Grupo());
            grupoBeanHelper.setPlanestudioD(new Planestudio());
            grupoBeanHelper.setProgramaEducativoD(new Programaeducativo());
        }
    }

    /**
     * Metodo para validar campos vacios
     *
     * @return Mensaje dependiendo del campo vacio
     */
    public String validacion() {
        mensajeVacio = "";
        if (numGrupo == 0) {
            mensajeVacio = mensajeVacio + "Numero de grupo,";
        }
        if (grupoBeanHelper.getPlanestudioD().getPESid() == 0) {
            mensajeVacio = mensajeVacio + " Plan de estudio,";
        }
        return mensajeVacio;
    }

    /**
     * Metodo para realizar eliminacion en caso de confirmar
     */
    public void eliminacionConfirmada() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Eliminando", "Se eliminó correctamente"));

        grupoBeanHelper.eliminarGrupo(grupoBeanHelper.getSelecGrupo());
        grupoBeanHelper.setGrupo(new Grupo());
        grupoBeanHelper.setSelecGrupo(new Grupo());
        //RequestContext.getCurrentInstance().execute("PF('confdlgElim').hide()");
        PrimeFaces.current().executeScript("PF('confdlgElim').hide()");


        if (grupoBeanHelper.getListaGpoSeleccionada().size() == 1) {
            grupoBeanHelper.setGrupo(grupoBeanHelper.getListaGpoSeleccionada().get(0));
            grupoBeanHelper.setPlanestudio(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
            clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativo().getPEDid()).getPEDclave());
            numGrupo = Integer.parseInt(String.valueOf(grupoBeanHelper.getGrupo().getGPOnumero()).replace(clavePrograma, ""));
        }
        filtrado();
    }

    /**
     * Metodo para realizar modificacion en caso de confirmar
     */
    public void modificacionConfirmada() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Guardando", "Se guardó correctamente"));

        grupoBeanHelper.modificarGrupo(grupoBeanHelper.getGrupo());

        grupoBeanHelper.setGrupo(new Grupo());
        grupoBeanHelper.setSelecGrupo(new Grupo());
        //RequestContext.getCurrentInstance().execute("PF('confdlgMod').hide()");
        PrimeFaces.current().executeScript("PF('confdlgMod').hide()");


        filtrado();
    }

    /**
     * Metodo para realizar confirmacion
     */
    public void confirmacionAceptada() {

        if (deshabilitar.equals("true")) {
            if (botonAceptar.equals("true")) {
                esconderBotones();
                cargarPlanEstudio();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));

                grupoBeanHelper.eliminarDeLista(grupoBeanHelper.getGrupo().getGPOid());
                grupoBeanHelper.eliminarGrupo(grupoBeanHelper.getGrupo());
                grupoBeanHelper.setSelecGrupo(new Grupo());
                grupoBeanHelper.setGrupo(new Grupo());

                //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

                if (grupoBeanHelper.getListaGpoSeleccionada() != null
                        && grupoBeanHelper.getListaGpoSeleccionada().size() >= 1) {
                    grupoBeanHelper.setGrupo(grupoBeanHelper.getListaGpoSeleccionada().get(0));
                    grupoBeanHelper.setPlanestudioD(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
                    grupoBeanHelper.setProgramaEducativoD(grupoBeanHelper.findProgramaEducativoById(grupoBeanHelper.getPlanestudioD().getProgramaEducativoPEDid().getPEDid()));
                    clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativoD().getPEDid()).getPEDclave());
                    numGrupo = Integer.parseInt(String.valueOf(grupoBeanHelper.getGrupo().getGPOnumero()).replace(clavePrograma, ""));

                    //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                     PrimeFaces.current().executeScript("PF('dlg').show()");
                    dialogFiltrarPlan();

                }
            } else {
                //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                 PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
                limpiarSeleccion();
            }

        } else {
             if (botonAceptar.equals("true")) {
            
            
            
            FacesContext context = FacesContext.getCurrentInstance();
            grupoBeanHelper.getGrupo().setPlanEstudioPESid(grupoBeanHelper.AsignarPlanEstudio(grupoBeanHelper.getPlanestudioD().getPESid()));
            grupoBeanHelper.getGrupo().setGPOnumero(Integer.parseInt(clavePrograma + numGrupo));
            grupoBeanHelper.modificarGrupo(grupoBeanHelper.getGrupo());
            grupoBeanHelper.seleccionarRegistro();
            grupoBeanHelper.setListaGpoSeleccionada(grupoBeanHelper.getListaGpoSeleccionada());
            context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));
            //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
             PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
            
            
            if (grupoBeanHelper.getListaGpoSeleccionada() != null
                        && grupoBeanHelper.getListaGpoSeleccionada().size() >= 1) {
                    grupoBeanHelper.setGrupo(grupoBeanHelper.getListaGpoSeleccionada().get(0));
//                    grupoBeanHelper.setPlanestudio(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
//                    grupoBeanHelper.setProgramaEducativo(grupoBeanHelper.findProgramaEducativoById(grupoBeanHelper.getPlanestudio().getProgramaEducativoPEDid().getPEDid()));
                    grupoBeanHelper.setPlanestudioD(grupoBeanHelper.getListaGpoSeleccionada().get(0).getPlanEstudioPESid());
                    grupoBeanHelper.setProgramaEducativoD(grupoBeanHelper.findProgramaEducativoById(grupoBeanHelper.getPlanestudioD().getProgramaEducativoPEDid().getPEDid()));
                    clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativoD().getPEDid()).getPEDclave());
                    numGrupo = Integer.parseInt(String.valueOf(grupoBeanHelper.getGrupo().getGPOnumero()).replace(clavePrograma, ""));

                    //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                     PrimeFaces.current().executeScript("PF('dlg').show()");
                    dialogFiltrarPlan();

                }
            } else {
                //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                 PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
                limpiarSeleccion();
            }
            
//
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
//            grupoBeanHelper.agregarGrupo(grupoBeanHelper.getGrupo());
//            grupoBeanHelper.seleccionarRegistro();
//            grupoBeanHelper.setListaGpoSeleccionada(grupoBeanHelper.getListaGpoSeleccionada());
//
//            RequestContext.getCurrentInstance().execute("PF('dlg').show()");
//            
//            dialogFiltrarPlan();
            


        }

        filtrado();
    }

    /**
     * Ver posibilidad de cambiarlo a void Metodo para agregar o eliminar
     * dependiendo de la cabecera
     *
     * @return Cadena vacia
     */
    public String onClickSubmit() {
        String resultado = "";

        setMensajeConfirm();
       // RequestContext.getCurrentInstance().update("confirmdlg");
        PrimeFaces.current().ajax().update("confirmdlg");


        if (deshabilitar.equals("true")) {
            cargarPlanEstudio();
            //RequestContext.getCurrentInstance().execute("PF('confirmdlg').show()");
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");

            dialogFiltrarPlan();

        } else {
            if (!validacion().isEmpty()) {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Favor de llenar " + mensajeVacio);
               // RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                grupoBeanHelper.getGrupo().setPlanEstudioPESid(grupoBeanHelper.AsignarPlanEstudio(grupoBeanHelper.getPlanestudioD().getPESid()));
                grupoBeanHelper.getGrupo().setGPOnumero(Integer.parseInt(clavePrograma + String.valueOf(numGrupo)));

                mensajeRep = grupoBeanHelper.validarRepetidos();
                if (mensajeRep.isEmpty()) {
                    mensajeRep = "vacio";
                }
                if (mensajeRep.equals("vacio")) {

                    if (header.equals("Agregar grupo")) {

                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                        System.out.println("SE AGREGO UN GRUPO----------------------");
                    grupoBeanHelper.getGrupo().setPlanEstudioPESid(grupoBeanHelper.AsignarPlanEstudio(grupoBeanHelper.getPlanestudioD().getPESid()));
                        grupoBeanHelper.getGrupo().setGPOnumero(Integer.parseInt(clavePrograma + String.valueOf(numGrupo)));
                        grupoBeanHelper.agregarGrupo(grupoBeanHelper.getGrupo());

                        grupoBeanHelper.setGrupo(new Grupo());
                        grupoBeanHelper.setSelecGrupo(new Grupo());
                        grupoBeanHelper.setPlanestudioD(new Planestudio());
                        grupoBeanHelper.getProgramaEducativoD().setPEDid(0);
                        grupoBeanHelper.getPlanestudioD().setPESid(0);
                        clavePrograma = "";
                        numGrupo = 0;
                    } else if (header.equals("Modificar grupo")) {

                        if (grupoBeanHelper.getGrupoAsignado()) {

                            botonAceptar = "true";
                            //RequestContext.getCurrentInstance().update("confirmdlg");
                            PrimeFaces.current().ajax().update("confirmdlg");

                            //RequestContext.getCurrentInstance().execute("PF('confirmdlg').show()");
                            PrimeFaces.current().executeScript("PF('confirmdlg').show()");

                        } else {
                            FacesContext context = FacesContext.getCurrentInstance();

                            grupoBeanHelper.getGrupo().setPlanEstudioPESid(grupoBeanHelper.AsignarPlanEstudio(grupoBeanHelper.getPlanestudioD().getPESid()));
                            grupoBeanHelper.getGrupo().setGPOnumero(Integer.parseInt(clavePrograma + numGrupo));
                            grupoBeanHelper.modificarGrupo(grupoBeanHelper.getGrupo());
                            grupoBeanHelper.seleccionarRegistro();
                            grupoBeanHelper.setListaGpoSeleccionada(grupoBeanHelper.getListaGpoSeleccionada());
                            context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));

                            if (grupoBeanHelper.getListaGpoSeleccionada().size() == 1) {
                               // RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                                PrimeFaces.current().executeScript("PF('dlg').hide()");

                                //                                RequestContext.getCurrentInstance().update(":frmGrupo:grup");
                                //                                RequestContext.getCurrentInstance().update(":frmGrupo:growl");
                                //                                RequestContext.getCurrentInstance().update("cap");
                                //                                RequestContext.getCurrentInstance().update(":frmGrupo:seleccionados");
                                //                                RequestContext.getCurrentInstance().update(":frmGrupo:capdlg");
                                filtrado();
                            } else {
                                //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                                PrimeFaces.current().executeScript("PF('dlg').show()");

                                seleccionarRegistro();
                               // RequestContext.getCurrentInstance().update("seleccionados");
                                PrimeFaces.current().ajax().update("seleccionados");

                            }

                        }
                    }
                    filtrado();
                   // RequestContext.getCurrentInstance().update(":frmGrupo:idSelecPE");
                    PrimeFaces.current().ajax().update(":frmGrupo:idSelecPE");

                    //RequestContext.getCurrentInstance().update(":frmGrupo:idSelectPlan");
                    PrimeFaces.current().ajax().update(":frmGrupo:idSelectPlan");

                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El: " + mensajeRep + " ya existe");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }

            }
            mostrarBotones();
            filtrado();
        }

        mostrarBotones();

        return resultado;
    }

    /**
     * Metodo para llamar filtrado en Helper
     */
    public void filtrado() {
        //deshabilitarCambio();
        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        String catalogo = "Administración grupo";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        listaFiltrada = grupoBeanHelper.filtrado("Nombre", busqueda);
    }

    /**
     * Metodo para establecer botones despues de realizar alguna operacion
     */
    public void mostrarBotones() {
        try {
            if (grupoBeanHelper.getSelecGrupo().getGPOid() > 0) {
                deshabilitarBoton = "false";
                titleElim = "Eliminar";
                titleMod = "Modificar";
            }
        } catch (NullPointerException e) {
        }
    }

    /**
     * Metodo para esconder botones
     */
    public void esconderBotones() {
        deshabilitarBoton = "true";
        titleElim = "Seleccione un registro de la tabla";
        titleMod = "Seleccione un registro de la tabla";
    }

    /**
     * Metodo para habilitar botones
     *
     * @return False si hay al menos un grupo registrado, True en cualquier otro
     * caso
     */
    public String botonesSelect() {
        if (grupoBeanHelper.getSelecGrupo().getGPOid() > 0) {

            return "false";
        } else {

            return "true";
        }
    }

    /**
     * Metodo para mostrar la seleccion de grupo
     *
     * @return True si el grupo no es null y es mayor a 1, False en el caso
     * contrario
     */
    public boolean mostrarSeleccionGrupo() {
        return grupoBeanHelper.getListaGpoSeleccionada() != null && grupoBeanHelper.getListaGpoSeleccionada().size() > 1;
    }

    /**
     * Metodo para seleccionar el registro
     */
    public void seleccionarRegistro() {
        grupoBeanHelper.seleccionarRegistro();
        clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativoD().getPEDid()).getPEDclave());
        numGrupo = Integer.parseInt(String.valueOf(grupoBeanHelper.getGrupo().getGPOnumero()).replace(clavePrograma, ""));
    }

    /**
     * Metodo para limpiar la seleccion
     */
    public void limpiarSeleccion() {
        grupoBeanHelper.setListaGpoSeleccionada(null);
        grupoBeanHelper.setGrupo(new Grupo());
        grupoBeanHelper.setSelecGrupo(new Grupo());
//        grupoBeanHelper.setPlanestudio(new Planestudio());
        grupoBeanHelper.setPlanestudioD(new Planestudio());
//        grupoBeanHelper.setProgramaEducativoD(new Programaeducativo());

        clavePrograma = "";
        numGrupo = 0;
        mostrarSeleccionGrupo();
        botonesModElim();
    }

    /**
     * Metodo para habilitar botones
     *
     * @return True si la lista seleccionada no es null o menor a 1, False en
     * cualquier otro caso
     */
    public boolean botonesModElim() {
        try {
            band = grupoBeanHelper.getListaGpoSeleccionada() == null
                    || grupoBeanHelper.getListaGpoSeleccionada().isEmpty()
                    || grupoBeanHelper.getListaGpoSeleccionada().size() < 1;
        } catch (NullPointerException ex) {
            band = true;
        } finally {
            return (band);
        }
        /*
        if (grupoBeanHelper.getListaGpoSeleccionada() == null || grupoBeanHelper.getListaGpoSeleccionada().size() < 1) {
            return "true";
        } else {
            return "false";
        }
         */
    }

    public void deshabilitarCambio() {
        try {
            grupoBeanHelper.setListaGpoSeleccionada(null);
            botonesModElim();
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Metodo para devolver cadena dependiendo la operacion
     *
     * @param i Valor con la operacion que se quiere realizar
     * @return Cadena
     */
    public String toolTip(int i) {
        if (grupoBeanHelper.getListaGpoSeleccionada() == null || grupoBeanHelper.getListaGpoSeleccionada().size() < 1) {
            return "Seleccione un registro de la tabla";
        } else {
            if (i == 1) {
                return "Eliminar";
            } else if (i == 2) {
                return "Modificar";
            }
        }
        return "";
    }

    /**
     * Metodo para obtener Planes de Estudio
     *
     * @return Lista de Planes de Estudio
     */
    public List<Planestudio> cargarPlanEstudio() {
        listaPlanestudio = grupoBeanHelper.getListaPlanEstudio();
        return listaPlanestudio;
    }

    /**
     * Metodo para deseleccionar renglones
     *
     * @param event Evento tipo UnselectEvent
     */
    public void onRowUnselect(UnselectEvent event) {
        grupoBeanHelper.setSelecGrupo(new Grupo());
        grupoBeanHelper.setSelecGrupo((Grupo) event.getObject());
    }

    /**
     * Metodo para filtrar por Planes de Estudio
     */
    public void dialogFiltrarPlan() {
        if (grupoBeanHelper.getProgramaEducativoD().getPEDid() != 0) {
            listaPlanestudio = grupoBeanHelper.buscarPlanEstudio(grupoBeanHelper.getProgramaEducativoD().getPEDid());
            clavePrograma = String.valueOf(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(grupoBeanHelper.getProgramaEducativoD().getPEDid()).getPEDclave());
            //RequestContext.getCurrentInstance().update("claveP");
            PrimeFaces.current().ajax().update("claveP");

        } else {
            grupoBeanHelper.getPlanestudioD().setPESid(0);
            List<Planestudio> listaPlan = grupoBeanHelper.getListaPlanEstudio();
            List<Planestudio> listaPlan2 = null;
            try {
                listaPlanestudio.clear();
                listaPlan2.clear();
            } catch (NullPointerException e) {

                listaPlanestudio = grupoBeanHelper.getListaPlanEstudio();
                listaPlanestudio.clear();

                listaPlan2 = grupoBeanHelper.getListaPlanEstudio();
                listaPlan2.clear();
            }

            for (Programaeducativo pe : grupoBeanHelper.getListaProgramaEducativo()) {
                for (Planestudio plan : listaPlan) {

                    if (pe.getPEDid() == plan.getProgramaEducativoPEDid().getPEDid()) {
                        listaPlanestudio.add(plan);
                    }
                }
            }
        }
    }

    /**
     * Metodo para obtener Programas Educativos de Grupo
     *
     * @param plan Objeto tipo PlanEstudio
     * @return Lista de los Programas Educativos
     */
    public String listaPEDeGrupo(Planestudio plan) {
        System.out.println(plan.getPESvigenciaPlan());
        System.out.println(plan.getProgramaEducativoPEDid().getPEDid());
        String ProgEducGpo = "";
        String lista = null;
        lista = grupoBeanHelper.getListaPE(plan);
        if (lista.isEmpty()) {
            return "----";
        } else {

            return lista;
        }
    }
}
