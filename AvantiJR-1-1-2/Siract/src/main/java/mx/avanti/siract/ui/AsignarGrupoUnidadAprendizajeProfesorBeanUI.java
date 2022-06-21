/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.helper.AsignarGrupoUnidadAprendizajeProfesorBeanHelper;
import org.primefaces.PrimeFaces;


/**
 *
 * @author Gig@B COMPUTADORAS
 */
@ManagedBean
@ViewScoped
public class AsignarGrupoUnidadAprendizajeProfesorBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private AsignarGrupoUnidadAprendizajeProfesorBeanHelper AGUAPHelper;
    private List<UnidadaprendizajeImparteProfesor> listaFiltrada;

    private List<Programaeducativo> listaPE;
    private List<Unidadaprendizaje> listaUA;
    private List<Areaconocimiento> listaAC;
    private List<Planestudio> listaPlan;
    private List<Profesor> listaProfesor;
    private List<Grupo> listaGrupo;
    private List<String> listaTipo;
    private List<Areaconocimiento> listaACPorUA;
    private List<Areaconocimiento> listaACPorPlan;

    private String header;
    private String deshabilitar;
    private String deshabilitarMenu = "false";

    private String deshabilitarModificar = "false";
    private String deshabilitarAceptar;

    private String busqueda = "";
    private String mensajeConfirm;
    private String mensajeConfirmRegistroIncompleto;
    private String mensajeValRe;
    private String datosRegistro;

    private String deshabilitarBoton;
    private String deshabilitarCiclo;
    private String deshabilitarPlan;

    private String mensajeVal;
    private String mensajeVacio;
    private String renderCancelar;
    private boolean band = false;
    private Cicloescolar temp;

    private Profesor auxProfesor;
    private Cicloescolar auxCicloEscolar;
    private Programaeducativo auxProgramaEducativo;
    private UnidadaprendizajeImparteProfesor auxAGUAP;
    private Areaconocimiento auxareaConocimiento;
    private Grupo auxGrupo;
    private Unidadaprendizaje auxUnidadApren;
    private Planestudio auxPlanEstudio;

    private List<Cicloescolar> auxCiclo = new ArrayList();
    private List<Cicloescolar> auxCiclo2 = new ArrayList();

    public List<Cicloescolar> getAuxCiclo() {
        return auxCiclo;
    }

    public void setAuxCiclo(List<Cicloescolar> auxCiclo) {
        this.auxCiclo = auxCiclo;
    }

    public List<Cicloescolar> getAuxCiclo2() {
        return auxCiclo2;
    }

    public void setAuxCiclo2(List<Cicloescolar> auxCiclo2) {
        this.auxCiclo2 = auxCiclo2;
    }

    private int IDCATALOGOADMIASIGNARGRUPO = 12;

    public int getIDCATALOGOADMIASIGNARGRUPO() {
        return IDCATALOGOADMIASIGNARGRUPO;
    }

    int numero = 0;

    public AsignarGrupoUnidadAprendizajeProfesorBeanUI() {
        init();
    }

    private void init() {
        AGUAPHelper = new AsignarGrupoUnidadAprendizajeProfesorBeanHelper();
    }

    public List<UnidadaprendizajeImparteProfesor> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<UnidadaprendizajeImparteProfesor> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public AsignarGrupoUnidadAprendizajeProfesorBeanHelper getAGUAPHelper() {
        return AGUAPHelper;
    }

    public void setAGUAPHelper(AsignarGrupoUnidadAprendizajeProfesorBeanHelper AGUAPHelper) {
        this.AGUAPHelper = AGUAPHelper;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDeshabilitarCiclo() {
        return deshabilitarCiclo;
    }

    public void setDeshabilitarCiclo(String deshabilitarCiclo) {
        this.deshabilitarCiclo = deshabilitarCiclo;
    }

    public String getDeshabilitarPlan() {
        return deshabilitarPlan;
    }

    public void setDeshabilitarPlan(String deshabilitarPlan) {
        this.deshabilitarPlan = deshabilitarPlan;
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

    public String getDeshabilitarAceptar() {
        return deshabilitarAceptar;
    }

    public void setDeshabilitarAceptar(String deshabilitarAceptar) {
        this.deshabilitarAceptar = deshabilitarAceptar;
    }

    public String getDeshabilitarMenu() {
        return deshabilitarMenu;
    }

    public void setDeshabilitarMenu(String deshabilitarMenu) {
        this.deshabilitarMenu = deshabilitarMenu;
    }

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public String getMensajeConfirmRegistroIncompleto() {
        return mensajeConfirmRegistroIncompleto;
    }

    public void setMensajeConfirmRegistroIncompleto(String mensajeConfirmRegistroIncompleto) {
        this.mensajeConfirmRegistroIncompleto = mensajeConfirmRegistroIncompleto;
    }

    public String getDatosRegistro() {
        return datosRegistro;
    }

    public void setDatosRegistro(String datosRegistro) {
        this.datosRegistro = datosRegistro;
    }

    public List<Areaconocimiento> getListaACPorUA() {
        return listaACPorUA;
    }

    public void setListaACPorUA(List<Areaconocimiento> listaACPorUA) {
        this.listaACPorUA = listaACPorUA;
    }

    public List<Areaconocimiento> getListaACPorPlan() {
        return listaACPorPlan;
    }

    public void setListaACPorPlan(List<Areaconocimiento> listaACPorPlan) {
        this.listaACPorPlan = listaACPorPlan;
    }

    public void setMensajeConfirm() {
        if (deshabilitar.equals("true") && AGUAPHelper.getDeshabilitarSubgrupo().equals("true")) {

            if (AGUAPHelper.getReporteUAIP(AGUAPHelper.getAGUAP().getUIPid()).size() > 0) {
                mensajeConfirm = "La asignación de grupo ya tiene un RACT.";
                renderCancelar = "false";
            } else {
                mensajeConfirm = "¿Está seguro de eliminar el registro?";
                renderCancelar = "true";
            }
        } else {
            mensajeConfirm = "¿Está seguro de guardar los cambios?";
        }
        //RequestContext.getCurrentInstance().update("confirmdlgId");
        PrimeFaces.current().ajax().update("confirmdlgId");

    }

    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }

    public void setDeshabilitarBoton(String deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
    }

    public String getMensajeVal() {
        return mensajeVal;
    }

    public void setMensajeVal(String mensajeVal) {
        this.mensajeVal = mensajeVal;
    }

    public String getMensajeValRe() {
        return mensajeValRe;
    }

    public void setMensajeValRe(String mensajeValRe) {
        this.mensajeValRe = mensajeValRe;
    }

    public String getMensajeVacio() {
        return mensajeVacio;
    }

    public String getRenderCancelar() {
        return renderCancelar;
    }

    public void setRenderCancelar(String renderCancelar) {
        this.renderCancelar = renderCancelar;
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

    public List<Unidadaprendizaje> getListaUA() {
        return listaUA;
    }

    public List<Programaeducativo> getListaPE() {
        return listaPE;
    }

    public void setListaPE(List<Programaeducativo> listaPE) {
        this.listaPE = listaPE;
    }

    public void setListaUA(List<Unidadaprendizaje> listaUA) {
        this.listaUA = listaUA;
    }

    public List<Profesor> getListaProfesor() {
        return listaProfesor;
    }

    public void setListaProfesor(List<Profesor> listaProfesor) {
        this.listaProfesor = listaProfesor;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public List<Areaconocimiento> getListaAC() {
        return listaAC;
    }

    public void setListaAC(List<Areaconocimiento> listaAC) {
        this.listaAC = listaAC;
    }

    public List<Planestudio> getListaPlan() {
        return listaPlan;
    }

    public void setListaPlan(List<Planestudio> listaPlan) {
        this.listaPlan = listaPlan;
    }

    public List<String> getListaTipo() {
        return listaTipo;
    }

    public void setListaTipo(List<String> listaTipo) {
        this.listaTipo = listaTipo;
    }

    public Cicloescolar getTemp() {
        return temp;
    }

    public void setTemp(Cicloescolar temp) {
        this.temp = temp;
    }

    @PostConstruct
    public void postConstructor() {
        AGUAPHelper.setRolSeleccionado(loginBean.getSeleccionado());
        AGUAPHelper.setUsuario(loginBean.getLogueado());
        System.out.println("rol desde el BeanUI: " + loginBean.getSeleccionado());
        System.out.println("id del usuario desde login " + loginBean.getLogueado().getUSUid());
    }

    /**
     * Metodo para crear una asignacion
     */
    public void nuevo() {
        limpiarSeleccion();
        dlgCabecera(1);

        auxCiclo = AGUAPHelper.getListaActual();

        AGUAPHelper.setAGUAP(new UnidadaprendizajeImparteProfesor(new Cicloescolar(), new Grupo(), new Profesor(), new Unidadaprendizaje()));
        AGUAPHelper.setProgramaEducativo(new Programaeducativo());
        AGUAPHelper.setProgramaEducativoD(new Programaeducativo());
        AGUAPHelper.setPlanEstudio(new Planestudio());
        AGUAPHelper.setPlanEstudioD(new Planestudio());
        AGUAPHelper.setAreaConocimiento(new Areaconocimiento());
        AGUAPHelper.setUnidadApren(new Unidadaprendizaje());
        AGUAPHelper.setProfesor(new Profesor());
        AGUAPHelper.setGrupo(new Grupo());
        cargaDeListas();
    }

    /**
     * Metodo para establecer cabecera, cargar listas y modificar
     */
    public void modificar() {

        dlgCabecera(3);
        cargaDeListas();

        boolean ban = false;
        //  AGUAPHelper.mostarListas();
        auxCiclo = AGUAPHelper.getListaCiclo();

        try {

            if (AGUAPHelper.getListaAGUAPSeleccionada().size() >= 1) {

                AGUAPHelper.setAGUAP(AGUAPHelper.getListaAGUAPSeleccionada().get(0));
                AGUAPHelper.setGrupo(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getGrupoGPOid());
                AGUAPHelper.setProfesor(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getProfesorPROid());
                AGUAPHelper.setUnidadApren(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getUnidadAprendizajeUAPid());
                AGUAPHelper.getAGUAP().setCicloEscolarCESid(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getCicloEscolarCESid());
                AGUAPHelper.setPlanEstudio(AGUAPHelper.findByPlanEstudioId(AGUAPHelper.getGrupo().getPlanEstudioPESid().getPESid()));
                AGUAPHelper.setProgramaEducativo(AGUAPHelper.findProgramaEducativoById(AGUAPHelper.getPlanEstudio().getProgramaEducativoPEDid().getPEDid()));
                AGUAPHelper.setPlanEstudioD(AGUAPHelper.findByPlanEstudioId(AGUAPHelper.getGrupo().getPlanEstudioPESid().getPESid()));
                AGUAPHelper.setProgramaEducativoD(AGUAPHelper.findProgramaEducativoById(AGUAPHelper.getPlanEstudioD().getProgramaEducativoPEDid().getPEDid()));

                listaAC.clear();

                listaACPorUA = AGUAPHelper.getAreaPorUA(AGUAPHelper.getUnidadApren().getUAPid());
                listaACPorPlan = AGUAPHelper.getAreaMismoPlan(AGUAPHelper.getPlanEstudio().getPESid());

                for (int x = 0; x < listaACPorUA.size(); x++) {
                    System.out.println("Lista por Unidad Apren" + listaACPorUA.get(x));
                    for (int y = 0; y < listaACPorPlan.size(); y++) {
                        System.out.println("Lista por Plan Estudio" + listaACPorPlan.get(y));
                        if (listaACPorUA.get(x).getACOid().equals(listaACPorPlan.get(y).getACOid())) {
                            AGUAPHelper.setAreaConocimiento(listaACPorUA.get(x));
                            ban = true;
                            System.out.println("" + ban);
                            break;
                        }

                    }
                }
                filtrarListas();
            } else {
                if (AGUAPHelper.getListaAGUAPSeleccionada().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }

            }

            //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
            PrimeFaces.current().executeScript("PF('dlg').show()");


        } catch (Exception e) {

            AGUAPHelper.setAGUAP(new UnidadaprendizajeImparteProfesor());
            AGUAPHelper.setUnidadApren(new Unidadaprendizaje());
            AGUAPHelper.setProfesor(new Profesor());
            AGUAPHelper.setGrupo(new Grupo());
            AGUAPHelper.setProgramaEducativo(new Programaeducativo());
            AGUAPHelper.setPlanEstudio(new Planestudio());
            AGUAPHelper.getPlanEstudio().setPESid(0);
            AGUAPHelper.getProgramaEducativo().setPEDid(0);
            AGUAPHelper.setAreaConocimiento(new Areaconocimiento());

        }

    }

    //Metodo que verifica si el ultimo registro seleccionado esta incompleto
    public void ultimo() {
        try {
            int f = AGUAPHelper.getListaAGUAPSeleccionada().size() - 1;
            auxProfesor = new Profesor();
            auxCicloEscolar = new Cicloescolar();
            auxProgramaEducativo = new Programaeducativo();
            auxAGUAP = new UnidadaprendizajeImparteProfesor();
            auxareaConocimiento = new Areaconocimiento();
            auxGrupo = new Grupo();
            auxUnidadApren = new Unidadaprendizaje();
            auxPlanEstudio = new Planestudio();

            auxAGUAP = AGUAPHelper.getListaAGUAPSeleccionada().get(f);
            auxGrupo = AGUAPHelper.getListaAGUAPSeleccionada().get(f).getGrupoGPOid();
            System.out.println("############\n\n\n\n\n\n" + auxGrupo);
            auxProfesor = AGUAPHelper.getListaAGUAPSeleccionada().get(f).getProfesorPROid();
            System.out.println("############" + auxProfesor);
            auxUnidadApren = AGUAPHelper.getListaAGUAPSeleccionada().get(f).getUnidadAprendizajeUAPid();
            System.out.println("############" + auxUnidadApren);
            auxCicloEscolar = AGUAPHelper.getListaAGUAPSeleccionada().get(f).getCicloEscolarCESid();
            System.out.println("############" + auxCicloEscolar);
            auxPlanEstudio = AGUAPHelper.findByPlanEstudioId(auxGrupo.getPlanEstudioPESid().getPESid());
            System.out.println("############" + auxPlanEstudio);
            auxProgramaEducativo = AGUAPHelper.findProgramaEducativoById(auxPlanEstudio.getProgramaEducativoPEDid().getPEDid());
            System.out.println("############" + auxProgramaEducativo);

            listaACPorUA = AGUAPHelper.getAreaPorUA((int) auxUnidadApren.getUAPid());
            listaACPorPlan = AGUAPHelper.getAreaMismoPlan(auxPlanEstudio.getPESid());
            System.out.println("Lista por Unidad Apren" + listaACPorUA);
            System.out.println("Lista por Plan Estudio" + listaACPorPlan);
            for (int x = 0; x < listaACPorUA.size(); x++) {
                System.out.println("Lista por Unidad Apren" + listaACPorUA.get(x));
                for (int y = 0; y < listaACPorPlan.size(); y++) {
                    System.out.println("Lista por Plan Estudio" + listaACPorPlan.get(y));
                    if (listaACPorUA.get(x).getACOid().equals(listaACPorPlan.get(y).getACOid())) {
                        System.out.println("Imprimir lista" + listaACPorUA.get(x).getACOid() + listaACPorPlan.get(y).getACOid());
                        auxareaConocimiento = listaACPorUA.get(x);
                        boolean ban = true;
                        break;
                    }

                }
            }

        } catch (Exception e) {
           // RequestContext.getCurrentInstance().execute("PF('confirmdlgCV').show()");
            PrimeFaces.current().executeScript("PF('confirmdlgCV').show()");


        }

        if (auxareaConocimiento.getACOnombre() == null) {
           // RequestContext.getCurrentInstance().execute("PF('confirmdlgCV').show()");
            PrimeFaces.current().executeScript("PF('confirmdlgCV').show()");

            if (AGUAPHelper.getListaAGUAPSeleccionada().size() == 1) {
                AGUAPHelper.getListaAGUAPSeleccionada().remove(0);
            } else {
                AGUAPHelper.getListaAGUAPSeleccionada().remove(AGUAPHelper.getListaAGUAPSeleccionada().size() - 1);
            }

        } else {
         //   RequestContext.getCurrentInstance().update(":frmAGUAP:showEliminarButton, :frmAGUAP:showModificarButton");
            PrimeFaces.current().ajax().update(":frmAGUAP:showEliminarButton, :frmAGUAP:showModificarButton");

        }

    }

    /**
     * Metodo para establecer cabecera, cargar listas y eliminar
     */
    public void eliminar() {
        dlgCabecera(2);
        cargaDeListas();

        auxCiclo = AGUAPHelper.getListaCiclo();

        try {
            if (AGUAPHelper.getListaAGUAPSeleccionada().size() >= 1) {
                AGUAPHelper.setAGUAP(AGUAPHelper.getListaAGUAPSeleccionada().get(0));
                AGUAPHelper.setGrupo(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getGrupoGPOid());
                AGUAPHelper.setProfesor(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getProfesorPROid());
                AGUAPHelper.setUnidadApren(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getUnidadAprendizajeUAPid());
                AGUAPHelper.getAGUAP().setCicloEscolarCESid(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getCicloEscolarCESid());
                AGUAPHelper.setPlanEstudio(AGUAPHelper.findByPlanEstudioId(AGUAPHelper.getGrupo().getPlanEstudioPESid().getPESid()));
                AGUAPHelper.setProgramaEducativo(AGUAPHelper.findProgramaEducativoById(AGUAPHelper.getPlanEstudio().getProgramaEducativoPEDid().getPEDid()));
                AGUAPHelper.setPlanEstudioD(AGUAPHelper.findByPlanEstudioId(AGUAPHelper.getGrupo().getPlanEstudioPESid().getPESid()));
                AGUAPHelper.setProgramaEducativoD(AGUAPHelper.findProgramaEducativoById(AGUAPHelper.getPlanEstudioD().getProgramaEducativoPEDid().getPEDid()));

                listaAC.clear();

                listaACPorUA = AGUAPHelper.getAreaPorUA(AGUAPHelper.getUnidadApren().getUAPid());
                listaACPorPlan = AGUAPHelper.getAreaMismoPlan(AGUAPHelper.getPlanEstudio().getPESid());

                for (int x = 0; x < listaACPorUA.size(); x++) {
                    System.out.println("Lista por Unidad Apren" + listaACPorUA.get(x));
                    for (int y = 0; y < listaACPorPlan.size(); y++) {
                        System.out.println("Lista por Plan Estudio" + listaACPorPlan.get(y));
                        if (listaACPorUA.get(x).getACOid().equals(listaACPorPlan.get(y).getACOid())) {
                            AGUAPHelper.setAreaConocimiento(listaACPorUA.get(x));
                            break;
                        }

                    }
                }
                filtrarListas();
            } else {
                if (AGUAPHelper.getListaAGUAPSeleccionada().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                   // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }
            }
        } catch (NullPointerException e) {

            AGUAPHelper.setAGUAP(new UnidadaprendizajeImparteProfesor());
            AGUAPHelper.setUnidadApren(new Unidadaprendizaje());
            AGUAPHelper.setProfesor(new Profesor());
            AGUAPHelper.setGrupo(new Grupo());
            AGUAPHelper.setPlanEstudio(new Planestudio());
            AGUAPHelper.setProgramaEducativo(new Programaeducativo());
            AGUAPHelper.setAreaConocimiento(new Areaconocimiento());
        }
    }

    // falta mucho por terminar
    public String onClickSubmit() {
        System.out.println("jijijiji");
        String resultado = "";
        setMensajeConfirm();

        if (deshabilitar.equals("true") && AGUAPHelper.getDeshabilitarSubgrupo().equals("true")) {
            cargaDeListas();
            //RequestContext.getCurrentInstance().execute("PF('confirmdlg').show()");
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");

            filtrarPlanYProfPorPEE();

        } else {

            if (validarCamposVacios().length() > 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Favor de llenar: " + mensajeVacio);
               // RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);


            } else {

                mensajeVal = AGUAPHelper.validarRepetidos();
                System.out.println("BEANUI mensajeVal = " + mensajeVal);

                if (mensajeVal.isEmpty()) {
                    mensajeVal = "vacio";
                }

                if (mensajeVal.equals("vacio")) {

                    if (header.equals("Agregar grupo, unidad aprendizaje y profesor")) {
                        if (!AGUAPHelper.getAGUAP().getUIPsubgrupo().equals("0") && AGUAPHelper.getAGUAP().getUIPtipoSubgrupo().equals("C")) {
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este subgrupo no coincide con su tipo");
                           // RequestContext.getCurrentInstance().showMessageInDialog(message);
                            FacesContext.getCurrentInstance().addMessage(null, message);


                        } else {
                            AGUAPHelper.getAGUAP().setUnidadAprendizajeUAPid(AGUAPHelper.findUnidadAprendizaje(AGUAPHelper.getUnidadApren().getUAPid()));
                            AGUAPHelper.getAGUAP().setGrupoGPOid(AGUAPHelper.buscarGrupo(AGUAPHelper.getGrupo().getGPOid()));
                            mensajeVal = AGUAPHelper.validarRepetidos();
                            if (mensajeVal.isEmpty()) {
                                mensajeVal = "vacio";
                            }

                            boolean mensaje2 = AGUAPHelper.validarTipo2();
                            if (mensaje2 == (false)) {
                                if (mensajeVal.equals("vacio")) {
                                    AGUAPHelper.agregarUniAprenImparteProfe(AGUAPHelper.getAGUAP());
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                                    AGUAPHelper.setAGUAP(new UnidadaprendizajeImparteProfesor(new Cicloescolar(), new Grupo(), new Profesor(), new Unidadaprendizaje()));
                                    AGUAPHelper.setSelecAGUAP(new UnidadaprendizajeImparteProfesor());
                                    AGUAPHelper.setGrupo(new Grupo());
                                    AGUAPHelper.getAGUAP().getProfesorPROid().setPROid(0);

                                    AGUAPHelper.setUnidadApren(new Unidadaprendizaje());
                                    AGUAPHelper.setProgramaEducativo(new Programaeducativo());
                                    AGUAPHelper.getProgramaEducativo().setPEDid(0);
                                    AGUAPHelper.setPlanEstudio((new Planestudio()));
                                    AGUAPHelper.setProgramaEducativoD(new Programaeducativo());
                                    AGUAPHelper.getProgramaEducativoD().setPEDid(0);
                                    AGUAPHelper.setPlanEstudioD((new Planestudio()));
                                    AGUAPHelper.setAreaConocimiento(new Areaconocimiento());
                                } else {
                                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Este profesor ya está asignado al mismo grupo");
                                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                                    FacesContext.getCurrentInstance().addMessage(null, message);


                                }
                            } else {
                                FacesMessage mensajeValidacion = null;
                                switch (AGUAPHelper.getAGUAP().getUIPtipoSubgrupo()) {
                                    case "C":
                                        mensajeValidacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ya hay un profesor asignado en esta clase");
                                        //RequestContext.getCurrentInstance().showMessageInDialog(mensajeValidacion);
                                        FacesContext.getCurrentInstance().addMessage(null, mensajeValidacion);


                                        break;

                                    case "T":
                                        mensajeValidacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ya hay un profesor asignado en este taller");
                                      //  RequestContext.getCurrentInstance().showMessageInDialog(mensajeValidacion);
                                        FacesContext.getCurrentInstance().addMessage(null, mensajeValidacion);


                                        break;

                                    case "L":
                                        mensajeValidacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ya hay un profesor asignado en este laboratorio");
                                       // RequestContext.getCurrentInstance().showMessageInDialog(mensajeValidacion);
                                        FacesContext.getCurrentInstance().addMessage(null, mensajeValidacion);


                                        break;
                                }

                            }
                        }

                    } else if (header.equals("Modificar grupo, unidad aprendizaje y profesor")) {
                        if (AGUAPHelper.getReporteUAIP(AGUAPHelper.getAGUAP().getUIPid()).size() > 0) {
                            if (!AGUAPHelper.getAGUAP().getUIPsubgrupo().equals("0") && AGUAPHelper.getAGUAP().getUIPtipoSubgrupo().equals("C")) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este subgrupo no coincide con su tipo");
                                //RequestContext.getCurrentInstance().showMessageInDialog(message);
                                FacesContext.getCurrentInstance().addMessage(null, message);


                            } else {
                                FacesContext context = FacesContext.getCurrentInstance();
                                AGUAPHelper.actualizarUnidadAprendizajeImparteProfesor(AGUAPHelper.getAGUAP());
                                AGUAPHelper.seleccionarRegistro("Modificar grupo, unidad aprendizaje y profesor");
                                AGUAPHelper.setListaAGUAPSeleccionada(AGUAPHelper.getListaAGUAPSeleccionada());

                                //AGUAPHelper.getProgramaEducativo().setPEDid(0);
                                //AGUAPHelper.setPlanEstudio((new Planestudio()));
                                System.out.println("#####################" + AGUAPHelper.getAGUAP());
                                System.out.println("#####################" + AGUAPHelper.getListaAGUAPSeleccionada());
                                System.out.println("#####################" + AGUAPHelper.getProgramaEducativo().getPEDid());
                                System.out.println("#####################" + AGUAPHelper.getPlanEstudio());
                                context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));

                                if (AGUAPHelper.getListaAGUAPSeleccionada().size() == 1) {
                                   // RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                                    PrimeFaces.current().executeScript("PF('dlg').hide()");

                                }
                            }
                        } else if (!AGUAPHelper.getAGUAP().getUIPsubgrupo().equals("0") && AGUAPHelper.getAGUAP().getUIPtipoSubgrupo().equals("C")) {
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este subgrupo no coincide con su tipo");
                            //RequestContext.getCurrentInstance().showMessageInDialog(message);
                            FacesContext.getCurrentInstance().addMessage(null, message);


                        } else {
                            AGUAPHelper.getAGUAP().setUnidadAprendizajeUAPid(AGUAPHelper.findUnidadAprendizaje(AGUAPHelper.getUnidadApren().getUAPid()));
                            AGUAPHelper.getAGUAP().setGrupoGPOid(AGUAPHelper.buscarGrupo(AGUAPHelper.getGrupo().getGPOid()));
                            mensajeVal = AGUAPHelper.validarRepetidos();
                            if (mensajeVal.isEmpty()) {
                                mensajeVal = "vacio";
                            }

                            boolean mensaje2 = AGUAPHelper.validarTipo2();
                            if (mensaje2 == (false)) {
                                if (mensajeVal.equals("vacio")) {
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    AGUAPHelper.actualizarUnidadAprendizajeImparteProfesor(AGUAPHelper.getAGUAP());
                                    AGUAPHelper.seleccionarRegistro("Modificar grupo, unidad aprendizaje y profesor");
                                    AGUAPHelper.setListaAGUAPSeleccionada(AGUAPHelper.getListaAGUAPSeleccionada());

                                    //AGUAPHelper.getProgramaEducativo().setPEDid(0);
                                    //AGUAPHelper.setPlanEstudio((new Planestudio()));
                                    System.out.println("#####################" + AGUAPHelper.getAGUAP());
                                    System.out.println("#####################" + AGUAPHelper.getListaAGUAPSeleccionada());
                                    System.out.println("#####################" + AGUAPHelper.getProgramaEducativo().getPEDid());
                                    System.out.println("#####################" + AGUAPHelper.getPlanEstudio());
                                    context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));

                                    if (AGUAPHelper.getListaAGUAPSeleccionada().size() == 1) {
                                      //  RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                                        PrimeFaces.current().executeScript("PF('dlg').hide()");

                                    }
                                } else {
                                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Este profesor ya está asignado al mismo grupo");
                                   // RequestContext.getCurrentInstance().showMessageInDialog(message);
                                    FacesContext.getCurrentInstance().addMessage(null, message);

                                    // filtrarListas2();
                                }
                            } else {
                                FacesMessage mensajeValidacion = null;
                                switch (AGUAPHelper.getAGUAP().getUIPtipoSubgrupo()) {
                                    case "C":
                                        mensajeValidacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ya hay un profesor asignado en esta clase");
                                       // RequestContext.getCurrentInstance().showMessageInDialog(mensajeValidacion);
                                        FacesContext.getCurrentInstance().addMessage(null, mensajeValidacion);

                                        break;

                                    case "T":
                                        mensajeValidacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ya hay un profesor asignado en este taller");
                                       // RequestContext.getCurrentInstance().showMessageInDialog(mensajeValidacion);
                                        FacesContext.getCurrentInstance().addMessage(null, mensajeValidacion);

                                        break;

                                    case "L":
                                        mensajeValidacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ya hay un profesor asignado en este laboratorio");
                                        //RequestContext.getCurrentInstance().showMessageInDialog(mensajeValidacion);
                                        FacesContext.getCurrentInstance().addMessage(null, mensajeValidacion);

                                        break;
                                }

                            }
                        }
//                        else {
//                            FacesContext context = FacesContext.getCurrentInstance();
//                            AGUAPHelper.actualizarUnidadAprendizajeImparteProfesor(AGUAPHelper.getAGUAP());
//                            AGUAPHelper.seleccionarRegistro("Modificar grupo, unidad aprendizaje y profesor");
//                            AGUAPHelper.setListaAGUAPSeleccionada(AGUAPHelper.getListaAGUAPSeleccionada());
//
//                            //AGUAPHelper.getProgramaEducativo().setPEDid(0);
//                            //AGUAPHelper.setPlanEstudio((new Planestudio()));
//                            System.out.println("#####################" + AGUAPHelper.getAGUAP());
//                            System.out.println("#####################" + AGUAPHelper.getListaAGUAPSeleccionada());
//                            System.out.println("#####################" + AGUAPHelper.getProgramaEducativo().getPEDid());
//                            System.out.println("#####################" + AGUAPHelper.getPlanEstudio());
//                            context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));
//
//                            if (AGUAPHelper.getListaAGUAPSeleccionada().size() == 1) {
//                                RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
//                            }
//                        }

                    }

                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Este profesor ya está asignado al mismo grupo");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);


                }

            }
            if (header.equals("Agregar grupo, unidad aprendizaje y profesor")) {
                filtro();
            } else {
                System.out.println("NOMBRE#####################\n\n\n\n" + AGUAPHelper.getAGUAP());
                System.out.println("#####################" + AGUAPHelper.getListaAGUAPSeleccionada());
                System.out.println("#####################" + AGUAPHelper.getProgramaEducativo().getPEDid());
                System.out.println("PLAN#####################" + AGUAPHelper.getPlanEstudio());
                filtrarListas2();
                filtro();
            }
        }

        System.out.println("jssjjs");

        return resultado;
    }

    public void confirmacionAceptada() {
        cargaDeListas();
        if (deshabilitar.equals("true")) {
            if (renderCancelar.equals("true")) {

                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null, new FacesMessage("", "se eliminó correctamente"));
                AGUAPHelper.eliminarDeLista(AGUAPHelper.getAGUAP().getUIPid());
                AGUAPHelper.eliminarUniAprenImparteProfe(AGUAPHelper.getAGUAP());
                AGUAPHelper.setSelecAGUAP(new UnidadaprendizajeImparteProfesor(new Cicloescolar(), new Grupo(), new Profesor(), new Unidadaprendizaje()));
                AGUAPHelper.setAGUAP(new UnidadaprendizajeImparteProfesor(new Cicloescolar(), new Grupo(), new Profesor(), new Unidadaprendizaje()));
                AGUAPHelper.getProgramaEducativo().setPEDid(0);
                AGUAPHelper.setPlanEstudio(new Planestudio());
                AGUAPHelper.getProgramaEducativoD().setPEDid(0);
                AGUAPHelper.setPlanEstudioD(new Planestudio());
                AGUAPHelper.setAreaConocimiento(new Areaconocimiento());
                //RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");


                if (AGUAPHelper.getListaAGUAPSeleccionada() != null
                        && AGUAPHelper.getListaAGUAPSeleccionada().size() >= 1) {
                    AGUAPHelper.setAGUAP(AGUAPHelper.getListaAGUAPSeleccionada().get(0));
                    AGUAPHelper.setGrupo(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getGrupoGPOid());
                    AGUAPHelper.setProfesor(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getProfesorPROid());
                    AGUAPHelper.setUnidadApren(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getUnidadAprendizajeUAPid());
                    AGUAPHelper.getAGUAP().setCicloEscolarCESid(AGUAPHelper.getListaAGUAPSeleccionada().get(0).getCicloEscolarCESid());
                    AGUAPHelper.setPlanEstudio(AGUAPHelper.findByPlanEstudioId(AGUAPHelper.getGrupo().getPlanEstudioPESid().getPESid()));
                    AGUAPHelper.setProgramaEducativo(AGUAPHelper.findProgramaEducativoById(AGUAPHelper.getPlanEstudio().getProgramaEducativoPEDid().getPEDid()));
                    AGUAPHelper.setPlanEstudioD(AGUAPHelper.findByPlanEstudioId(AGUAPHelper.getGrupo().getPlanEstudioPESid().getPESid()));
                    AGUAPHelper.setProgramaEducativoD(AGUAPHelper.findProgramaEducativoById(AGUAPHelper.getPlanEstudioD().getProgramaEducativoPEDid().getPEDid()));

                    listaAC.clear();

                    listaACPorUA = AGUAPHelper.getAreaPorUA(AGUAPHelper.getUnidadApren().getUAPid());
                    listaACPorPlan = AGUAPHelper.getAreaMismoPlan(AGUAPHelper.getPlanEstudio().getPESid());

                    for (int x = 0; x < listaACPorUA.size(); x++) {
                        System.out.println("Lista por Unidad Apren" + listaACPorUA.get(x));
                        for (int y = 0; y < listaACPorPlan.size(); y++) {
                            System.out.println("Lista por Plan Estudio" + listaACPorPlan.get(y));
                            if (listaACPorUA.get(x).getACOid().equals(listaACPorPlan.get(y).getACOid())) {
                                AGUAPHelper.setAreaConocimiento(listaACPorUA.get(x));
                                break;
                            }

                        }
                    }

                    filtrarListas();

                   // RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                    PrimeFaces.current().executeScript("PF('dlg').show()");

                }
            } else {
               // RequestContext.getCurrentInstance().execute("PF('confirmdlg').hide()");
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");

                limpiarSeleccion();
            }

            filtro();
        }
    }

    /**
     * Metodo para filtrar Roles
     */
    public void filtro() {
        //AGUAPHelper.setListaAGUAPSeleccionada(null); 
        //deshabilitarCambioElim();
        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        System.out.println(seleccionado + " ENTRO A FILTROOOO");
        String catalogo = "Asignar grupo";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        listaFiltrada = AGUAPHelper.filtrado();
    }

    /**
     * Metodo para filtrar TextBox
     */
    public void filtroTextbox() {
        deshabilitarCambioElim();
        listaFiltrada = AGUAPHelper.filtrarTextbox(busqueda);
    }

    /**
     * Metodo para establecer cabecera dependiendo de la operacion
     *
     * @param i Valor dependiendo de la operacion
     * @return Cadena con operacion a realizar
     */
    public String toolTip(int i) {
        if (AGUAPHelper.getListaAGUAPSeleccionada() == null || AGUAPHelper.getListaAGUAPSeleccionada().size() < 1) {
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
     * Metodo para activar boton de eliminacion
     *
     * @return True si no se ha seleccionado nada, False en el caso contrario
     */
    public boolean botonesModElim() {
        try {
            band = AGUAPHelper.getListaAGUAPSeleccionada() == null
                    || AGUAPHelper.getListaAGUAPSeleccionada().size() < 1
                    || AGUAPHelper.getListaAGUAPSeleccionada().isEmpty();
        } catch (NullPointerException ex) {
            System.out.println("NULO DE BOTON ELIMINAR**");
            band = true;
        } finally {
            System.out.println("BANDERA: " + band);
            return (band);
        }
        /*
        if (AGUAPHelper.getListaAGUAPSeleccionada() == null || 
            AGUAPHelper.getListaAGUAPSeleccionada().size() < 1) {
            return "true";
        } else {
            return "false";
        }*/
    }

    public void deshabilitarCambioElim() {
        try {
            AGUAPHelper.setListaAGUAPSeleccionada(null);
            botonesModElim();
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Metodo para establecer cabecera de agregar, eliminar o modificar
     *
     * @param i Variable con el valor dependiendo el caso
     * @return Cadena con el header
     */
    public String dlgCabecera(int i) {
        if (i == 1) {
            header = "Agregar grupo, unidad aprendizaje y profesor";
            deshabilitar = "false";
            AGUAPHelper.setDeshabilitarSubgrupo("false");
            deshabilitarCiclo = "false";
            deshabilitarPlan = "false";
            AGUAPHelper.setDeshabilitarUnidadApren("false");

        }
        if (i == 2) {
            header = "Eliminar grupo, unidad aprendizaje y profesor";
            deshabilitar = "true";
            AGUAPHelper.setDeshabilitarSubgrupo("true");
            deshabilitarCiclo = "true";
            deshabilitarPlan = "true";
            AGUAPHelper.setDeshabilitarUnidadApren("false");
            AGUAPHelper.setDeshabilitarUnidadApren("true");

        }
        if (i == 3) {
            header = "Modificar grupo, unidad aprendizaje y profesor";
            deshabilitar = "false";
            deshabilitarCiclo = "true";
            AGUAPHelper.setAGUAP(AGUAPHelper.getListaAGUAPSeleccionada().get(0));
            if (AGUAPHelper.getReporteUAIP(AGUAPHelper.getAGUAP().getUIPid()).size() > 0) {

                AGUAPHelper.setDeshabilitarSubgrupo("true");
                AGUAPHelper.setDeshabilitarUnidadApren("true");
                deshabilitarPlan = "true";

            } else {
                AGUAPHelper.setDeshabilitarSubgrupo("false");
                AGUAPHelper.setDeshabilitarUnidadApren("false");
                deshabilitarPlan = "false";
            }

        }

        return header;
    }

    /**
     * Metodo para cargar listas de Programa Educativo
     *
     * @return Lista de Programas Educativos
     */
    public List<Programaeducativo> cargarPE() {
        listaPE = AGUAPHelper.getListaProgramaEducativo();
        return listaPE;
    }

    /**
     * Metodo para cargar listas de Plan de Estudio
     *
     * @return Lista de Plan de Estudios
     */
    public List<Planestudio> cargarPlan() {

        listaPlan = AGUAPHelper.getListaPlanEstudio();
        return listaPlan;
    }

    /**
     * Metodo para cargar listas de Area de Conocimiento
     *
     * @return Lista de Area de Conocimiento
     */
    public List<Areaconocimiento> cargarAC() {
        listaAC = AGUAPHelper.getListaAreaConocimiento();
        return listaAC;
    }

    /**
     * Metodo para cargar listas de Unidad de Aprendizaje
     *
     * @return Lista de Unidad de Aprendizaje
     */
    public List<Unidadaprendizaje> cargarUA() {
        listaUA = AGUAPHelper.getListaUnidadAprendizaje();
        return listaUA;
    }

    /**
     * Metodo para cargar listas de Profesores
     *
     * @return Lista de Profesores
     */
    public List<Profesor> cargarProfesor() {
        listaProfesor = AGUAPHelper.getListaProfesor();
        return listaProfesor;
    }

    /**
     * Metodo para cargar listas de Grupo
     *
     * @return Lista de Grupo
     */
    public List<Grupo> cargarGrupo() {
        listaGrupo = AGUAPHelper.getListaGrupo();
        return listaGrupo;
    }

    public boolean mostrarSeleccionAGUAP() {
        return AGUAPHelper.getListaAGUAPSeleccionada() != null && AGUAPHelper.getListaAGUAPSeleccionada().size() > 1;
    }

    /**
     * Metodo para filtrar Plan de Estudio y Profesor por Programa Educativo
     */
    public void filtrarPlanYProfPorPE() {
        listaPlan = AGUAPHelper.buscarPlanEstudio(AGUAPHelper.getProgramaEducativo().getPEDid());

        listaProfesor = AGUAPHelper.findProfesorPertenecePE(AGUAPHelper.getProgramaEducativo().getPEDid());
    }

    public void filtrarPlanYProfPorPEE() {
        listaPlan = AGUAPHelper.buscarPlanEstudio(AGUAPHelper.getProgramaEducativoD().getPEDid());

        listaProfesor = AGUAPHelper.findProfesorPertenecePE(AGUAPHelper.getProgramaEducativoD().getPEDid());
    }

    /**
     * Metodo para filtrar Area de Conocimiento y Grupo por Plan de Estudio
     */
    public void filtrarAreaYGpoPorPlan() {

        listaAC = AGUAPHelper.getAreaMismoPlan(AGUAPHelper.getPlanEstudioD().getPESid());
        listaGrupo = AGUAPHelper.getGpoMismoPlan(AGUAPHelper.getPlanEstudioD().getPESid());
    }

    /**
     * Metodo para filtrar Unidad de Aprendizaje por Area de Conocimiento
     */
    public void filtrarUAPorArea() {
        listaUA = AGUAPHelper.getUAMismaArea(AGUAPHelper.getAreaConocimiento().getACOid());
    }

    /**
     * Metodo para filtrar Tipo de Grupo por Unidad de Aprendizaje
     */
    public void filtrarTipoPorUA() {
        try {
            listaTipo = AGUAPHelper.getTipoDeUA(AGUAPHelper.getUnidadApren().getUAPid());
        } catch (NullPointerException ex) {
            System.out.println("PASO POR NULO DE FILTRARTIPOPORUA");
        }
    }

    /**
     * Metodo para cargar todas las listas
     */
    public void cargaDeListas() {
        cargarPE();
        cargarPlan();
        cargarAC();
        cargarUA();
        cargarProfesor();
        cargarGrupo();
    }

    /**
     * Metodo para filtrar todas las listas
     */
    public void filtrarListas() {
        filtrarPlanYProfPorPE();

        filtrarAreaYGpoPorPlan();
        filtrarUAPorArea();
        filtrarTipoPorUA();

    }

    public void filtrarListas2() {

        filtrarPlanYProfPorPEE();
        filtrarAreaYGpoPorPlan();
        filtrarUAPorArea();
        filtrarTipoPorUA();

    }

    /**
     * Metodo para limpiar la seleccion
     */
    public void limpiarSeleccion() {
        AGUAPHelper.setListaAGUAPSeleccionada(null);
        AGUAPHelper.setAGUAP(new UnidadaprendizajeImparteProfesor(new Cicloescolar(), new Grupo(), new Profesor(), new Unidadaprendizaje()));
        AGUAPHelper.setUnidadApren(new Unidadaprendizaje());
        AGUAPHelper.getAGUAP().getProfesorPROid().setPROid(0);

        AGUAPHelper.setGrupo(new Grupo());
        AGUAPHelper.setProgramaEducativo(new Programaeducativo());
        AGUAPHelper.setPlanEstudio(new Planestudio());
        AGUAPHelper.setProgramaEducativoD(new Programaeducativo());
        AGUAPHelper.setPlanEstudioD(new Planestudio());
        AGUAPHelper.setAreaConocimiento(new Areaconocimiento());
        AGUAPHelper.getPlanEstudio().setPESid(0);
        AGUAPHelper.getProgramaEducativo().setPEDid(0);
        AGUAPHelper.getPlanEstudioD().setPESid(0);
        AGUAPHelper.getProgramaEducativoD().setPEDid(0);

    }

    /**
     * Metodo para saber si un tipo de grupo tiene un subgrupo Regresa True si
     * tiene, False en caso contrario
     */
    public void tipoTieneSubgrupo() {
        if (AGUAPHelper.getAGUAP().getUIPtipoSubgrupo().equals("C")) {
            AGUAPHelper.getAGUAP().setUIPsubgrupo("0");
            AGUAPHelper.setDeshabilitarSubgrupo("true");
        } else {
            AGUAPHelper.getAGUAP().setUIPsubgrupo("");
            AGUAPHelper.setDeshabilitarSubgrupo("false");
        }
    }

    /**
     * Metodo para saber si un subgrupo pertenece a un tipo de grupo
     */
    public void subgrupoTieneTipo() {
        if (AGUAPHelper.getAGUAP().getUIPsubgrupo().equals("0")) {
            AGUAPHelper.getAGUAP().setUIPtipoSubgrupo("C");
        }
    }

    /**
     * Metodo para validar campos vacios
     *
     * @return Cadena con mensaje vacio localizado
     */
    public String validarCamposVacios() {
        mensajeVacio = "";

        if (AGUAPHelper.getProgramaEducativoD().getPEDid() == 0) {
            mensajeVacio = mensajeVacio + "Programa educativo, ";
        }

        if (AGUAPHelper.getPlanEstudioD().getPESid() == 0) {
            mensajeVacio = mensajeVacio + "Plan de estudio, ";
        }

        if (AGUAPHelper.getAreaConocimiento().getACOid() == 0) {
            mensajeVacio = mensajeVacio + "Área de conocimiento, ";
        }

        if (AGUAPHelper.getUnidadApren().getUAPid() == 0) {
            mensajeVacio = mensajeVacio + "Unidad aprendizaje, ";
        }

        if (AGUAPHelper.getAGUAP().getProfesorPROid().getPROid() == 0) {
            mensajeVacio = mensajeVacio + "Profesor, ";
        }

        if (AGUAPHelper.getGrupo().getGPOid() == 0) {
            mensajeVacio = mensajeVacio + "Grupo, ";
        }

        if (AGUAPHelper.getAGUAP().getUIPsubgrupo().isEmpty()) {
            mensajeVacio = mensajeVacio + "Subgrupo, ";
        }

        if (AGUAPHelper.getAGUAP().getUIPtipoSubgrupo().equals("0")) {
            mensajeVacio = mensajeVacio + "Tipo";
        }

        return mensajeVacio;
    }

}
