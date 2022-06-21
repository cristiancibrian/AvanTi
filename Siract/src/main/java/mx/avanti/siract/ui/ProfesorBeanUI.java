
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.ProfesorBeanHelper;
import mx.avanti.siract.helper.UsuarioBeanHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.PrimeFaces;

/**
 * @author balta
 */
@ManagedBean
@ViewScoped
public class ProfesorBeanUI implements Serializable {
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    private ProfesorBeanHelper profesorBeanHelper = null;
    private UsuarioBeanHelper usuarioBeanHelper = null;

    private String cabecera = "";
    private String textoBoton;
    private String deshabilitar = "false";
    private String deshabilitarNumeroP;
    private String deshabilitarProgramaE;
    private String deshabilitarUsuario;
    private String deshabilitarCicloEscolar;
    private String campo;
    private String busqueda = "";
    boolean band = false;

    private final int IDCATALOGOADMIPROF = 9;

    public int getIDCATALOGOADMIPROF() {
        return IDCATALOGOADMIPROF;
    }

    private String MensajeVal = "";
    private String mensajeConfirmacion;
    private String renderCancelar;
    private String mensajeVacio;

    private List<Profesor> listaFiltrada;

    private List<Usuario> listaFiltradaUsu;
    private List<Usuario> lista;
    private List<Usuario> listaUsuarios;

    private List<Cicloescolar> listaCicloEscolar;

    private List<Programaeducativo> listaPE;

    private List<String> listaSeleccionPuestos;
    /**
     * Lista de los programas educativos que se seleccionan en los checkbox
     */
    private List<String> obtenerListaPE;

    private List listaAux;

    private Usuario usuarioOriginal;
    private Profesor original;
    private List<Usuario> listaOriginal;

    // <editor-fold defaultstate="collapsed" desc="Getters y setters">
    public void setDeshabilitarUsuario(String deshabilitarUsuario) {
        this.deshabilitarUsuario = deshabilitarUsuario;
    }

    public String getDeshabilitarUsuario() {
        return deshabilitarUsuario;
    }

    public List<Usuario> getListaFiltradaUsu() {
        return listaFiltradaUsu;
    }

    public void setListaFiltradaUsu(List<Usuario> listaFiltradaUsu) {
        this.listaFiltradaUsu = listaFiltradaUsu;
    }

    public String getDeshabilitarNumeroP() {
        return deshabilitarNumeroP;
    }

    public void setDeshabilitarNumeroP(String deshabilitarNumeroP) {
        this.deshabilitarNumeroP = deshabilitarNumeroP;
    }

    public String getDeshabilitarProgramaE() {
        return deshabilitarProgramaE;
    }

    public void setDeshabilitarProgramaE(String deshabilitarNumeroP) {
        this.deshabilitarProgramaE = deshabilitarProgramaE;
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public ProfesorBeanUI() {
        init();
    }

    private void init() {
        profesorBeanHelper = new ProfesorBeanHelper();
        usuarioBeanHelper = new UsuarioBeanHelper();
    }

    public List<String> getListaSeleccionPuestos() {
        return listaSeleccionPuestos;
    }

    public void setListaSeleccionPuestos(List<String> listaSeleccionPuestos) {
        this.listaSeleccionPuestos = listaSeleccionPuestos;
    }

    public List<String> getObtenerListaPE() {
        return obtenerListaPE;
    }

    public void setObtenerListaPE(List<String> obtenerListaPE) {
        this.obtenerListaPE = obtenerListaPE;
    }

    public String getMensajeVal() {
        return MensajeVal;
    }

    public void setMensajeVal(String MensajeVal) {
        this.MensajeVal = MensajeVal;
    }

    public UsuarioBeanHelper getUsuarioBeanHelper() {
        return usuarioBeanHelper;
    }

    public void setUsuarioBeanHelper(UsuarioBeanHelper usuarioBeanHelper) {
        this.usuarioBeanHelper = usuarioBeanHelper;
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

    public ProfesorBeanHelper getProfesorBeanHelper() {
        return profesorBeanHelper;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getRenderCancelar() {
        return renderCancelar;
    }

    public void setRenderCancelar(String renderCancelar) {
        this.renderCancelar = renderCancelar;
    }

    public String getMensajeVacio() {
        return mensajeVacio;
    }

    public void setMensajeVacio(String mensajeVacio) {
        this.mensajeVacio = mensajeVacio;
    }

    public List<Profesor> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Profesor> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Programaeducativo> getListaPE() {
        return listaPE;
    }

    public void setListaPE(List<Programaeducativo> listaPE) {
        this.listaPE = listaPE;
    }

    public List<Cicloescolar> getListaCicloEscolar() {
        return listaCicloEscolar;
    }

    public void setListaCicloEscolar(List<Cicloescolar> listaCicloEscolar) {
        this.listaCicloEscolar = listaCicloEscolar;
    }

    public void setDeshabilitarCicloEscolar(String deshabilitarCicloEscolar) {
        this.deshabilitarCicloEscolar = deshabilitarCicloEscolar;
    }

    public String getDeshabilitarCicloEscolar() {
        return deshabilitarCicloEscolar;
    }
    // </editor-fold>

    @PostConstruct
    public void postConstructor() {
        profesorBeanHelper.setRolSeleccionado(loginBean.getSeleccionado());
        profesorBeanHelper.setUsuario(loginBean.getLogueado());

        filtrado();
        listaFiltrada = profesorBeanHelper.getListaFiltrada2();
        for(Profesor prof : listaFiltrada){
            if(prof.getUsuarioUSUid() == null){
                Usuario usu = new Usuario();
                usu =  profesorBeanHelper.findByUsuarioId(5898);
                prof.setUsuarioUSUid(usu);
                profesorBeanHelper.modificarProfesor(prof);
            }
                    
        }
    }

    /**
     * Metodo para filtrar datos por Profesor
     */
    public void filtroBusquedaProfesorEspesifico() {
        profesorBeanHelper.setListaSeleccionProfesores(null);
        listaFiltrada = profesorBeanHelper.filtrarTextBox(busqueda);
        profesorBeanHelper.terminaTransaccion();//////
    }

    /**
     * Realiza una búsqueda si el rol del usuario que ingresó a la página puede
     * ver esta información, muestra la información al usuario dependiendo del
     * ciclo escolar que se seleccionó.
     */
    public void filtroBusquedaCicloEscolar() {
        profesorBeanHelper.getProgramaEducativo().setPEDid(0);
        profesorBeanHelper.setListaSeleccionProfesores(null);
        busqueda = "";
        filtrado();
        profesorBeanHelper.terminaTransaccion();
    }

    /**
     * Realiza una búsqueada por programa educativo y valida los roles.
     */
    public void filtrado() {
        deshabilitarCambio();
        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        int idSeleccionado = loginBean.getIdGlobal();
        final int idResponsableProgramaEducativo = 6;

        String catalogo = "Administración de profesores";
        loginBean.verificarPermiso(list, idSeleccionado, IDCATALOGOADMIPROF);

        profesorBeanHelper.setTransaccion(true);
        profesorBeanHelper.actualizarListasCache();
        if (idSeleccionado == idResponsableProgramaEducativo) {
            filtrado2();
        } else {
            if (busqueda.equalsIgnoreCase("")) {
                listaFiltrada = profesorBeanHelper.filtrado("");
            } else {
                listaFiltrada = profesorBeanHelper.filtrado(busqueda);
            }
            filtroBusquedaProfesorEspesifico();
        }
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();

        profesorBeanHelper.terminaTransaccion();
    }

    /**
     * Realiza una búsqueda si el rol del usuario que ingresó a la página puede
     * ver esta información, muestra la información al usuario dependiendo de la
     * selección que hizo en el filtro de programa educativo.
     */
    public void filtrado2() {
        System.out.println("ENTRO AL FILTRADO 2.............");
        busqueda = "";
        profesorBeanHelper.setListaSeleccionProfesores(null);

        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        int idSeleccionado = loginBean.getIdGlobal();

        loginBean.verificarPermiso(list, idSeleccionado, IDCATALOGOADMIPROF);
        profesorBeanHelper.actualizarListasCache();
        listaFiltrada = profesorBeanHelper.filtrado2();

    }

    /**
     * Método para establecer cabecera de nuevo profesor y cargar datos
     */
    public void nuevo() {
        profesorBeanHelper.setTransaccion(true);
        limpiarSeleccion();
        header(1);
        obtenerListaPE.clear();
        listaFiltradaUsu = cargarUsu();
        cargarCicloEscolarActual();
        lista();
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();
        profesorBeanHelper.terminaTransaccion();
    }

    /**
     * Método para establecer cabecera de modificar profesor y cargar datos
     */
    public void modificar() {
        profesorBeanHelper.setTransaccion(true);
        obtenerListaPE.clear();
        header(3);
        listaFiltradaUsu = cargarUsu();
        lista();
        original = profesorBeanHelper.getListaSeleccionProfesores().get(0);
        usuarioOriginal = profesorBeanHelper.getListaSeleccionProfesores().get(0).getUsuarioUSUid();
        listaOriginal = cargarUsu();
        cargarCicloEscolar();
        cargarPE();
        try {
            if (profesorBeanHelper.getListaSeleccionProfesores().size() >= 1) {
                profesorBeanHelper.setProfesor(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                profesorBeanHelper.setUsuario2(profesorBeanHelper.getListaSeleccionProfesores().get(0).getUsuarioUSUid());
                profesorBeanHelper.getPEModProf(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                for (ProfesorPerteneceProgramaeducativo pe : profesorBeanHelper.getListaPEMod()) {
                    if (pe.getCicloescolar().getCESid() == profesorBeanHelper.getCicloEscolar2().getCESid()) {
                        obtenerListaPE.add(profesorBeanHelper.findProgramaEducativoById(pe.getProgramaeducativo().getPEDid()).getPEDnombre());
                    }
                }
                verificar();
            } else {
               
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningún registro");
                // RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);


            }
        } catch (NullPointerException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccionó ningún registro");
            // RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();
        profesorBeanHelper.terminaTransaccion();
    }

    /**
     * Metodo para establecer cabecera de eliminar profesor y cargar datos
     */
    public void eliminar() {
        header(2);
        obtenerListaPE = new ArrayList();
        cargarUsuario();
        cargarPE();
        lista();
        cargarCicloEscolar();
        profesorBeanHelper.setTransaccion(true);
        try {
            if (profesorBeanHelper.getListaSeleccionProfesores().size() >= 1) {
                profesorBeanHelper.setProfesor(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                profesorBeanHelper.setUsuario2(profesorBeanHelper.getListaSeleccionProfesores().get(0).getUsuarioUSUid());
                profesorBeanHelper.getPEModProf(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                for (ProfesorPerteneceProgramaeducativo pe : profesorBeanHelper.getListaPEMod()) {
                    obtenerListaPE.add(profesorBeanHelper.findProgramaEducativoById(pe.getProgramaeducativo().getPEDid()).getPEDnombre());
                }
            } else {
                //if (profesorBeanHelper.getListaSeleccionProfesores().size() < 1) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se seleccion&oacute; ning&uacute;n registro");
                //RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

                /*} else {
                    profesorBeanHelper.setProfesor(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                    profesorBeanHelper.setUsuario2(profesorBeanHelper.getListaSeleccionProfesores().get(0).getUsuarioUSUid());
                    profesorBeanHelper.getPEModProf(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                    for (ProfesorPerteneceProgramaeducativo pe : profesorBeanHelper.getListaPEMod()) {
                        obtenerListaPE.add(profesorBeanHelper.findProgramaEducativoById(pe.getProgramaeducativo().getPEDid()).getPEDnombre());
                    }
                }*/
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            profesorBeanHelper.setProfesor(new Profesor());
            profesorBeanHelper.setUsuario2(new Usuario());
        }
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();
        profesorBeanHelper.terminaTransaccion();
    }

    /**
     * Método para agregar, eliminar o modificar dependiendo el caso. Tras
     * desplegar el mensaje de confirmación y dar "Aceptar" se accede a este
     * método. Si el botón de cancelar no está visible es por que el mensaje de
     * confirmación se encarga solo de informar, pero si "Cancelar" es visible
     * y se presionó "Aceptar" significa que el usuario confirmó su decisión.
     */
    public void Confirmacion() {
        profesorBeanHelper.setTransaccion(true);
        if (deshabilitar.equals("true")) {
            if (renderCancelar.equals("true")) {
                boolean cesId = false;
                cesId = profesorBeanHelper.profesorImparteProgramaEducativo(profesorBeanHelper.getProfesor());

                if (cesId == true) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("", "No se puede eliminar el profesor debido a que tiene RACTS asociados."));
                } else {
                    Boolean ban = profesorBeanHelper.verificarProfesorTieneAsignaciones(profesorBeanHelper.getProfesor().getPROid());
                    if (ban == false) {
                        profesorBeanHelper.eliminarProfesorT(profesorBeanHelper.getProfesor().getPROid());
                        profesorBeanHelper.eliminarProfesorPerteneceProgramaEducativo(profesorBeanHelper.getProfesor());
                        listaFiltrada.remove(profesorBeanHelper.getProfesor());
                        profesorBeanHelper.eliminarDeLista(profesorBeanHelper.getProfesor().getPROid());
                        if (profesorBeanHelper.getTransaccion()) {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
                        } else
                            errorTransaccion();

                        profesorBeanHelper.terminaTransaccion();

                        profesorBeanHelper.setSelecProfesor(new Profesor());
                        profesorBeanHelper.setProfesor(new Profesor());
                        profesorBeanHelper.setUsuario2(new Usuario());

                        if (profesorBeanHelper.getListaSeleccionProfesores() != null
                                && profesorBeanHelper.getListaSeleccionProfesores().size() >= 1) {
                            profesorBeanHelper.setProfesor(profesorBeanHelper.getListaSeleccionProfesores().get(0));
                            profesorBeanHelper.setUsuario2(profesorBeanHelper.getListaSeleccionProfesores().get(0).getUsuarioUSUid());
                            //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                            PrimeFaces.current().executeScript("PF('dlg').show()");

                        } else {
                            limpiarSeleccion();
                            //RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
                            PrimeFaces.current().executeScript("PF('dlg').hide()");


                        }
                    } else {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "No se puede eliminar el profesor debido a que imparte unidades de aprendizaje."));
                    }
                }

            }
            //RequestContext.getCurrentInstance().execute("PF('confirmacion').hide()");
            PrimeFaces.current().executeScript("PF('confirmacion').hide()");

        } else {
            if (lista.size() == 0) {
                Usuario usuario = generaUsuario();
                if (profesorBeanHelper.getProfesor().getPROnombre().isEmpty() ||
                        "".equals(profesorBeanHelper.getProfesor().getPROnombre().trim())
                        || profesorBeanHelper.getProfesor().getPROapellidoPaterno().isEmpty()
                        || "".equals(profesorBeanHelper.getProfesor().getPROapellidoPaterno().trim())) {
                } else if (!usuarioBeanHelper.validarUsuarioRepetido(usuario.getUSUusuario())) {
                    profesorBeanHelper.saveUsuario(usuario);
                }
            }
            listaFiltradaUsu = cargarUsu();
            lista = listaFiltradaUsu;
            // RequestContext.getCurrentInstance().execute("PF('dlg').show()");
            PrimeFaces.current().executeScript("PF('dlg').show()");

        }
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();
        profesorBeanHelper.terminaTransaccion();
    }

    private UIComponent component;

    public UIComponent getComponent() {
        return component;
    }

    /**
     * Método para generar el de usuario con los datos del profesor actual.
     */
    private Usuario generaUsuario() {
        Usuario usuario = new Usuario();
        String username = "SC";
        username = username + profesorBeanHelper.getProfesor().getPROnombre().toUpperCase()
                + profesorBeanHelper.getProfesor().getPROapellidoPaterno().toUpperCase();
        username = username.replace(" ", "_");

        usuario.setUSUid(0);
        List<Rol> rol = new ArrayList();
        rol.add(profesorBeanHelper.getRol("Profesor"));
        usuario.setRolList(rol);

        usuario.setUSUusuario(username);
        usuario.setUSUcontrasenia(DigestUtils.md5Hex("vacante"));
        usuario.setProfesorList(new ArrayList());
        usuario.setCatalogoreportesList(new ArrayList());

        return usuario;
    }

    /**
     * Método que acciona la acción de "Agregar, Eliminar y Modificar" en los
     * datos mostrados.
     *
     * @return Cadena vacia
     */
    public String onClickSubmit() {
        setMensajeConfirmacion();
        if (cabecera.equalsIgnoreCase("Eliminar profesor")) {
            //RequestContext.getCurrentInstance().execute("PF('confirmacion').show()");
            PrimeFaces.current().executeScript("PF('confirmacion').show()");

            profesorBeanHelper.terminaTransaccion();
        } else {
            if (lista.size() == 0) {
                // RequestContext.getCurrentInstance().execute("PF('confirmacion').show()");
                PrimeFaces.current().executeScript("PF('confirmacion').show()");

            } else if (!"".equals(validarCamposVacios())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Favor de llenar: " + mensajeVacio);
                //RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                if (!cabecera.equalsIgnoreCase("Eliminar profesor") || !cabecera.equalsIgnoreCase("Agregar profesor")) {
                    MensajeVal = profesorBeanHelper.validarInformacionProfesor();
                    if (MensajeVal.isEmpty()) {
                        MensajeVal = "nada";
                    }
                } else {
                    MensajeVal = "nada";
                }
                if (MensajeVal.equals("nada")) {
                    if (cabecera.equals("Agregar profesor")) {
                        profesorBeanHelper.setTransaccion(true);

                        profesorBeanHelper.getProfesor().setUsuarioUSUid(profesorBeanHelper.getUsuario2());
                        profesorBeanHelper.getProfesor().setPROnumeroEmpleado(profesorBeanHelper.getProfesor().getPROnumeroEmpleado());
                        Profesor profesorAuxiliar = profesorBeanHelper.agregarProfesor(profesorBeanHelper.getProfesor());
                        profesorBeanHelper.setProfesor(profesorAuxiliar);
                        if (profesorBeanHelper.getTransaccion() == false)
                            errorTransaccion();
                        profesorBeanHelper.terminaTransaccion();

                        profesorBeanHelper.actualizarListasCache();

                        List<Programaeducativo> listaAuxPE = new ArrayList();
                        for (String PEDelProfesor : obtenerListaPE) {
                            for (Programaeducativo listPEAll : profesorBeanHelper.getListaProgramaEducativo()) {
                                if (listPEAll.getPEDnombre().equalsIgnoreCase(PEDelProfesor)) {
                                    profesorBeanHelper.agregarProfesorProgramaEducativo(profesorAuxiliar.getPROid(), listPEAll.getPEDid(), profesorBeanHelper.getCicloEscolar().getCESid());
                                    listaAuxPE.add(listPEAll);
                                }
                            }
                        }
//                        profesorAuxiliar.setProgramaeducativoList(listaAuxPE);
                        //El pequeño bloque de abajo (for) es una solución para lo comentado en la parte de arriba, verificar si es correcto.
                        List<ResponsableProgramaEducativo> responsableProgramaEducativoList = new ArrayList<ResponsableProgramaEducativo>();
                        for (Programaeducativo peProgramaeducativo : listaAuxPE) {
                            if (responsableProgramaEducativoList.isEmpty())
                                responsableProgramaEducativoList.add(new ResponsableProgramaEducativo(peProgramaeducativo));
                            for (Integer x = 0; x < responsableProgramaEducativoList.size(); x++)
                                if (!responsableProgramaEducativoList.get(x).getProgramaeducativo().equals(peProgramaeducativo))
                                    responsableProgramaEducativoList.add(new ResponsableProgramaEducativo(peProgramaeducativo));
                        }
                        profesorAuxiliar.setResponsableProgramaEducativosList(responsableProgramaEducativoList);

                        List<Unidadacademica> listaUnidades = new ArrayList();
                        Unidadacademica unidadAuxiliar = new Unidadacademica();

//                        for (int i = 0; i < profesorAuxiliar.getProgramaeducativoList().size(); i++) {
//                            unidadAuxiliar = profesorAuxiliar.getProgramaeducativoList().get(i).getUnidadAcademicaUACid();
//                            if (!(listaUnidades.contains(unidadAuxiliar))) {
//                                listaUnidades.add(unidadAuxiliar);
//                            }
//                        }
                        List<Unidadacademica> uniUnidadacademicaList = new ArrayList<Unidadacademica>();
                        for (ResponsableProgramaEducativo rpe : profesorAuxiliar.getResponsableProgramaEducativosList())
                            if (!uniUnidadacademicaList.contains(rpe.getProgramaeducativo().getUnidadAcademicaUACid()))
                                uniUnidadacademicaList.add(rpe.getProgramaeducativo().getUnidadAcademicaUACid());
                        listaUnidades.addAll(uniUnidadacademicaList);

                        profesorAuxiliar.setUnidadacademicaList(listaUnidades);
                        for (Unidadacademica unidad : profesorAuxiliar.getUnidadacademicaList()){
                            profesorBeanHelper.modificarListaUA(profesorAuxiliar, unidad);
                        }

                        deshabilitar = "false";
                        profesorBeanHelper.setSelecProfesor(new Profesor());
                        profesorBeanHelper.setProfesor(new Profesor());
                        profesorBeanHelper.setUsuario2(new Usuario());
                        profesorBeanHelper.setProfesorPertenece(new ProfesorPerteneceProgramaeducativo());
                        obtenerListaPE.clear();
                        listaFiltradaUsu = cargarUsu();
                        filtrado();
                        lista();
                        if (profesorBeanHelper.getTransaccion()) {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "Se agregó correctamente"));
                        } else
                            errorTransaccion();
                        profesorBeanHelper.terminaTransaccion();

                    } else {
                        if (cabecera.equals("Eliminar profesor")) {
                            //RequestContext.getCurrentInstance().execute("PF('confirmacion').show()");
                            PrimeFaces.current().executeScript("PF('confirmacion').show()");

                        } else {
                            Profesor auxiliarProfesor = profesorBeanHelper.getProfesor();
                            auxiliarProfesor.setProfesorPerteneceProgramaeducativoList(new ArrayList());
                            profesorBeanHelper.setTransaccion(true);
                            original = profesorBeanHelper.buscarProfePorID(profesorBeanHelper.getProfesor().getPROid());
                            if (validarCambios() == true) {
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "No se realizaron cambios"));
                                // RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                                PrimeFaces.current().executeScript("PF('dlg').show()");

                            } else {
                                actualizaRelacionProfesorPE();
                                profesorBeanHelper.modificarProfesor(profesorBeanHelper.getProfesor());

                                seleccionarRegistroUsuario();
                                actualizarListaPE();

                                FacesContext context = FacesContext.getCurrentInstance();
                                if (profesorBeanHelper.getTransaccion()) {
                                    context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));
                                    //RequestContext.getCurrentInstance().execute("PF('dlg').show()");
                                    PrimeFaces.current().executeScript("PF('dlg').show()");

                                } else
                                    errorTransaccion();
                            }
                            profesorBeanHelper.terminaTransaccion();
                        }
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El: " + MensajeVal + " ya existe");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }
            }
        }
        return "";
    }

    /**
     * Método para realizar el proceso de eliminar todos los PE aginados al
     * profesor en el ciclo actual para luego volver a registrar las
     * asginaciones de la lista de PE actual.
     */
    private void actualizaRelacionProfesorPE() {
        profesorBeanHelper.eliminarProfesorPerteneceProgramaEducativo(profesorBeanHelper.getProfesor(), profesorBeanHelper.getCicloEscolar2().getCESid());
        for (String PEDelProfesor : obtenerListaPE)
            for (Programaeducativo listPEAll : profesorBeanHelper.getListaProgramaEducativo()) {
                if (listPEAll.getPEDnombre().equalsIgnoreCase(PEDelProfesor)) {
                    profesorBeanHelper.agregarProfesorProgramaEducativo(profesorBeanHelper.getProfesor().getPROid(), listPEAll.getPEDid(), profesorBeanHelper.getCicloEscolar().getCESid());
                }
            }
    }

    /**
     * Método que indica si la lista de selección está vacía o no.
     *
     * @return true: Si la lista tiene al menos un elemento
     */
    public boolean mostrarSeleccionProfesores() {
        if (profesorBeanHelper.getListaSeleccionProfesores() != null && profesorBeanHelper.getListaSeleccionProfesores().size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para establecer el encabezado y deshabilitaciones para la función
     * que se va a realizar.
     *
     * @param i Variable con valor dependiendo de la función
     */
    public void header(int i) {
        switch (i) {

            case 1:
                cabecera = "Agregar profesor";
                textoBoton = "Aceptar";
                deshabilitar = "false";
                deshabilitarNumeroP = "false";
                deshabilitarCicloEscolar = "true";
                deshabilitarUsuario = "false";
                break;

            case 2:
                cabecera = "Eliminar profesor";
                textoBoton = "Aceptar";
                deshabilitar = "true";
                deshabilitarCicloEscolar = "true";
                deshabilitarNumeroP = "true";
                deshabilitarUsuario = "true";
                break;

            case 3:
                cabecera = "Modificar profesor";
                textoBoton = "Aceptar";
                deshabilitar = "false";
                deshabilitarNumeroP = "false";
                deshabilitarCicloEscolar = "true";
                deshabilitarUsuario = "false";
                break;
        }

    }

    /**
     * Método para cargar listas de Usuarios
     *
     * @return Lista de Usuarios
     */
    public List<Usuario> cargarUsuario() {
        listaUsuarios = profesorBeanHelper.getUsuarioH();
        return listaUsuarios;
    }

    /**
     * Método para cargar listas de Usuarios filtrada. Estos usuarios son los
     * que no estan asignados a algun profesor.
     *
     * @return Lista de Usuarios
     */
    public List<Usuario> cargarUsu() {
        return profesorBeanHelper.usuario();
    }

    /**
     * Método para cargar listas de Programa Educativo
     *
     * @return Lista de Programas Educativos
     */
    public List<Programaeducativo> cargarPE() {
        listaPE = profesorBeanHelper.getListaProgramaEducativo();
        return listaPE;
    }

    /**
     * Método para cargar listas de Ciclos Escolares
     *
     * @return Lista de Ciclos Escolares
     */
    public List<Cicloescolar> cargarCicloEscolar() {
        listaCicloEscolar = profesorBeanHelper.getListaOrdenada();
        profesorBeanHelper.getCicloEscolar().setCESid(profesorBeanHelper.getCicloEscolar2().getCESid());
        return listaCicloEscolar;

    }

    /**
     * Método para cargar el Ciclo Escolare actual
     *
     * @return Ciclo Escolar actual
     */
    public void cargarCicloEscolarActual() {
        listaCicloEscolar = new ArrayList();
        Cicloescolar auxiliar = profesorBeanHelper.getListaCiclo2().get(0);
        listaCicloEscolar.add(auxiliar);
        profesorBeanHelper.getCicloEscolar().setCESid(auxiliar.getCESid());

    }

    /**
     * Método para obtener Programas Educativos de Profesor
     * En las eliminaciones no se muestran los PE como una caja de selección al
     * estar los campos deshabilitados, así que los PE se muestran como una
     * cadena de texto, y este método se encarga de crear esta cadena.
     *
     * @param prof Objeto tipo Profesor
     * @return Lista de Programas Educativos encontrados
     */
    public String listaPEDeProf(Profesor prof) {
        String ProgEducProf = "";
        List<String> lista = null;
        lista = profesorBeanHelper.getListaProgramaEducativo(prof);
        if (lista.isEmpty()) {
            return "No tiene Programa Educativo asignado.";
        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (i == (lista.size()) - 1) {
                    ProgEducProf += lista.get(i) + ".";
                } else {
                    ProgEducProf += lista.get(i) + ", ";
                }
            }
            return ProgEducProf;
        }
    }

    /**
     * Método que limpia la lista de registros seleccionados
     */
    public void limpiarSeleccion() {
        profesorBeanHelper.actualizarListasCache();
        profesorBeanHelper.setListaSeleccionProfesores(new ArrayList());
        profesorBeanHelper.setUsuario2(new Usuario());
        profesorBeanHelper.setProfesor(new Profesor());
        profesorBeanHelper.setSelecProfesor(new Profesor());
    }

    /**
     * Método que se usa para establecer cabeceras de agregar, modificar o
     * eliminar
     *
     * @param i Valor que se establece dependiendo el caso
     * @return Cadena con la operación a realizar
     */
    public String tooltip(int i) {
        if (isNull(profesorBeanHelper.getListaSeleccionProfesores()) || profesorBeanHelper.getListaSeleccionProfesores().size() < 1) {
            return "Seleccione un registro de la tabla";
        } else {
            switch (i) {
                case 2:
                    return "Eliminar";
                case 3:
                    return "Modificar";
            }
        }
        return "Dato incorrecto";
    }

    /**
     * Método para habilitar y deshabilitar botones de eliminar y modificar
     *
     * @return True en el caso de que la lista este vacía, False en cualquier
     * otro caso
     */
    public boolean deshabilitarMenu() {
        try {
            band = profesorBeanHelper.getListaSeleccionProfesores().size() < 1
                    || profesorBeanHelper.getListaSeleccionProfesores().isEmpty()
                    || isNull(profesorBeanHelper.getListaSeleccionProfesores());
        } catch (NullPointerException ex) {
            band = true;
        } finally {
            return (band);
        }
    }

    /**
     * Método para limpiar elementos de la lista el momento de cambiar un
     * programa educativo
     */
    public void deshabilitarCambio() {
        try {
            profesorBeanHelper.setListaSeleccionProfesores(null);
            deshabilitarMenu();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    /**
     * Método para gestionar los mensajes de la ventana de confirmación
     */
    public void setMensajeConfirmacion() {
        if (deshabilitar.equals("true")) {
            if (!cabecera.equalsIgnoreCase("Eliminar profesor")) {
                mensajeConfirmacion = "El profesor se encuentra asignado a una unidad de aprendizaje y profesor.";
                renderCancelar = "false";
            } else {
                mensajeConfirmacion = "¿Está seguro de eliminar el registro?";
                renderCancelar = "true";
                deshabilitar = "true";
            }
        } else if (cabecera.equalsIgnoreCase("Modificar profesor")) {
            mensajeConfirmacion = "El profesor se encuentra asignado a una  unidad de aprendizaje y profesor. ¿Está seguro de guardar los cambios?";
            renderCancelar = "true";
        } else if (cabecera.equalsIgnoreCase("Agregar profesor")) {
            mensajeConfirmacion = "No hay usuarios disponibles para el nuevo profesor.\n";
            if (profesorBeanHelper.getProfesor().getPROnombre().isEmpty() || "".equals(profesorBeanHelper.getProfesor().getPROnombre().trim())
                    || profesorBeanHelper.getProfesor().getPROapellidoPaterno().isEmpty() || "".equals(profesorBeanHelper.getProfesor().getPROapellidoPaterno().trim()))
                mensajeConfirmacion = mensajeConfirmacion + "Y no se pudo crear un nuevo usuario, para generarlo llene el nombre y apellido paterno.";
            else {
                Usuario temporal = generaUsuario();
                if (!usuarioBeanHelper.validarUsuarioRepetido(temporal.getUSUusuario())) {
                    mensajeConfirmacion = mensajeConfirmacion + "Se creó un usuario temporal con el nombre: " + temporal.getUSUusuario();
                } else {
                    mensajeConfirmacion = mensajeConfirmacion + "Y no se pudo crear un nuevo usuario. "
                            + "El nombre generado: " + temporal.getUSUusuario() + " está repetido.";
                }
            }
            renderCancelar = "false";
        }
        //RequestContext.getCurrentInstance().update("confirmacionId");
        PrimeFaces.current().ajax().update("confirmacionId");

    }

    /**
     * Método para mostrar lista de Programas Educativos
     *
     * @return
     */
    public boolean mostrarListaPE() {
        if (deshabilitar.equalsIgnoreCase("true")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método para actualizar lista de Programas Educativos
     */
    public void actualizarListaPE() {
        obtenerListaPE.clear();
        System.out.println("Limpieza-----------");
        Cicloescolar cicloActual = profesorBeanHelper.getListaCiclo2().get(0);
        for (ProfesorPerteneceProgramaeducativo pe : profesorBeanHelper.getListaPEMod()) {
            if (buscarRepetido(pe) == true && pe.getCicloescolar().getCESid() == cicloActual.getCESid()) {
                obtenerListaPE.add(profesorBeanHelper.findProgramaEducativoById(pe.getProgramaeducativo().getPEDid()).getPEDnombre());
            }
        }
    }


    /**
     * Método para verificar si el PE a agregar no está en la lista de Programas
     */
    private boolean buscarRepetido(ProfesorPerteneceProgramaeducativo programa) {
        boolean b = true;
        String pe;
        for (int i = 0; i < obtenerListaPE.size(); i++) {
            pe = obtenerListaPE.get(i);
            if (pe.equals(programa.getProgramaeducativo().getPEDnombre())) {
                b = false;
            }
        }
        return b;
    }

    private void errorTransaccion() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocurrió un error durante la transacción"));
    }

    /**
     * Método para validar campos vacios
     *
     * @return Cadena con los valores vacíos detectados
     */
    public String validarCamposVacios() {
        boolean bUser = true;
        mensajeVacio = "";
        lista = cargarUsu();
        if (cabecera.equalsIgnoreCase("Modificar profesor")) {
            Profesor aux = profesorBeanHelper.buscarProfePorID(profesorBeanHelper.getProfesor().getPROid());
            lista.add(profesorBeanHelper.buscarUsuarioPorID(aux.getUsuarioUSUid().getUSUid()));
        }

        if (profesorBeanHelper.getProfesor().getPROnumeroEmpleado() == 0
                || profesorBeanHelper.getProfesor().getPROnumeroEmpleado() < 1) {
            mensajeVacio = mensajeVacio + "numero de empleado, ";
        }

        if (profesorBeanHelper.getProfesor().getPROrfc().isEmpty()) {
            mensajeVacio = mensajeVacio + "RFC, ";
        }

        if (profesorBeanHelper.getProfesor().getPROnombre().isEmpty() || "".equals(profesorBeanHelper.getProfesor().getPROnombre().trim())) {
            mensajeVacio = mensajeVacio + "nombre del profesor, ";
        }

        if (profesorBeanHelper.getProfesor().getPROapellidoPaterno().isEmpty() || "".equals(profesorBeanHelper.getProfesor().getPROapellidoPaterno().trim())) {
            mensajeVacio = mensajeVacio + "apellido paterno, ";
        }

        if (profesorBeanHelper.getProfesor().getPROapellidoMaterno().isEmpty() || "".equals(profesorBeanHelper.getProfesor().getPROapellidoMaterno().trim())) {
            mensajeVacio = mensajeVacio + "apellido materno, ";
        }
        for (Usuario us : lista) {
            if (profesorBeanHelper.validaUsuario(us))
                bUser = false;
        }
        if (bUser) {
            mensajeVacio = mensajeVacio + "usuario, ";
        }
        if (obtenerListaPE.isEmpty()) {
            mensajeVacio = mensajeVacio + "programa educativo";
        }

        return mensajeVacio;
    }

    /**
     * Método para validar el Número de empleado
     */
    public void validarClaveNE() {
        if (!cabecera.equalsIgnoreCase("Modificar profesor")) {
            listaAux = new ArrayList();
            lista();
            profesorBeanHelper.ValidacionNE();
            if (profesorBeanHelper.getMostrarNE().equalsIgnoreCase("true")) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No. de empleado existente");
                //RequestContext.getCurrentInstance().showMessageInDialog(message);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                deshabilitar = "false";
            }
        }
    }

    /**
     * Método para validar la Clave de Usuario
     */
    public void validarClaveUsuario() {
        FacesContext contex = FacesContext.getCurrentInstance();
        listaAux = new ArrayList();
        lista();
        profesorBeanHelper.setTransaccion(true);//////////
        profesorBeanHelper.ValidacionUsu();
        if (profesorBeanHelper.getMostrarUsu().equalsIgnoreCase("true")) {
            contex.addMessage("Aviso", new FacesMessage("Aviso", "usuario ya a sido asignado"));
            if (cabecera.equalsIgnoreCase("Agregar profesor")) {
                deshabilitar = "true";
            }

        } else {
            deshabilitar = "false";
        }
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();
        profesorBeanHelper.terminaTransaccion();

    }

    /**
     * Método para validar cuando no se han realizado cambios en los campos
     * durante la modificación.
     *
     * @return true si los datos originales y modificados son iguales, no se
     * cambió nada
     */
    private boolean validarCambios() {
        boolean salida = false;
        if ((original.getPROapellidoMaterno().equals(profesorBeanHelper.getProfesor().getPROapellidoMaterno())) &&
                (original.getPROapellidoPaterno().equals(profesorBeanHelper.getProfesor().getPROapellidoPaterno())) &&
                (original.getPROnombre().equals(profesorBeanHelper.getProfesor().getPROnombre())) &&
                (original.getPROnumeroEmpleado() == profesorBeanHelper.getProfesor().getPROnumeroEmpleado()) &&
                (original.getPROrfc().equals(profesorBeanHelper.getProfesor().getPROrfc())) &&
                (original.getUsuarioUSUid().getUSUid().equals(profesorBeanHelper.getProfesor().getUsuarioUSUid().getUSUid()))
        ) {
            List<Programaeducativo> l1 = new ArrayList();
            List<Programaeducativo> l2 = new ArrayList();
            for (ProfesorPerteneceProgramaeducativo elemento : original.getProfesorPerteneceProgramaeducativoList()) {
                if (elemento.getCicloescolar().getCESid() == profesorBeanHelper.getCicloEscolarActual().getCESid())
                    l1.add(elemento.getProgramaeducativo());
            }

            for (String PE : obtenerListaPE)
                for (Programaeducativo PEAll : profesorBeanHelper.getListaProgramaEducativo()) {
                    if (PEAll.getPEDnombre().equalsIgnoreCase(PE)) {
                        l2.add(PEAll);
                    }
                }
            if (l1.equals(l2))
                salida = true;
        }
        return salida;
    }

    /**
     * Método para actualizar la lista de usuarios en la ventana dependiendo de
     * la cabecera. Si es eliminacion solo deberia mostrar el usuario actual del
     * profesor, en la alta muestra los usuarios disponibles y en la
     * modificacion muestra los usuarios disponibles y el usuario actual.
     */
    public void lista() {
        if (cabecera.equalsIgnoreCase("Eliminar profesor")) {
            lista = listaUsuarios;
        } else {
            if (cabecera.equalsIgnoreCase("Modificar profesor")) {
                lista = listaFiltradaUsu;
                lista.add(profesorBeanHelper.getListaSeleccionProfesores().get(0).getUsuarioUSUid());

            } else {
                lista = listaFiltradaUsu;
            }
        }
    }

    /**
     * Método para validar RFC
     */
    public void validarRFC() {
        /*String aux = profesorBeanHelper.getProfesor().getPROrfc();
        if (!aux.matches("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}")) {
            profesorBeanHelper.getProfesor().setPROrfc("");
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Estructura de RFC invalido"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Estructura de RFC invalido"));

        }*/
    }

    /**
     * Método para validar que no exista un espacio en blanco al inicio de
     * nombre
     *
     * @param campo Cadena con nombre
     */
    public void validarInicioEspacio(String campo) {
        String aux = "";
        try {
            if (campo.equals("profnom")) {
                aux = String.valueOf(profesorBeanHelper.getProfesor().getPROnombre().charAt(0));
                if (aux.equals(" ")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Nombre inválido");
                    // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    profesorBeanHelper.getProfesor().setPROnombre("");
                    aux = "";
                }
            }
            if (campo.equals("profap")) {
                aux = String.valueOf(profesorBeanHelper.getProfesor().getPROapellidoPaterno().charAt(0));
                if (aux.equals(" ")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Apellido inválido");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    profesorBeanHelper.getProfesor().setPROapellidoPaterno("");
                    aux = "";
                }
            }
            if (campo.equals("profam")) {
                aux = String.valueOf(profesorBeanHelper.getProfesor().getPROapellidoMaterno().charAt(0));
                if (aux.equals(" ")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Apellido inválido");
                    // RequestContext.getCurrentInstance().showMessageInDialog(message);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    profesorBeanHelper.getProfesor().setPROapellidoMaterno("");
                    aux = "";
                }
            }
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("***************** NO SE INGRESO NADA EN " + campo);
        }

    }

    /**
     * @param component the component to set
     */
    public void setComponent(UIComponent component) {
        this.component = component;
    }

    /**
     * Método para seleccionar un registro de Usuario
     * Aquí se actualizan los datos actuales del profesor seleccionado
     * En la modificación se llena el cuadro de selección de PE, mientras que en
     * la eliminación los PE se listan en un cuadro.
     */
    public void seleccionarRegistroUsuario() {
        Usuario auxiliar;
        profesorBeanHelper.setTransaccion(true);//////////
        if (cabecera.equalsIgnoreCase("Eliminar profesor")) {
            for (Profesor prof : profesorBeanHelper.getListaSeleccionProfesores()) {
                if (prof.getPROid().equals(profesorBeanHelper.getSelecProfesor().getPROid())) {
                    profesorBeanHelper.setProfesor(prof);
                    profesorBeanHelper.setUsuario2(prof.getUsuarioUSUid());
                    profesorBeanHelper.getPEModProf(prof);
                    for (ProfesorPerteneceProgramaeducativo pe : profesorBeanHelper.getListaPEMod()) {
                        obtenerListaPE.add(profesorBeanHelper.findProgramaEducativoById(pe.getProgramaeducativo().getPEDid()).getPEDnombre());
                    }
                }
            }
        } else {
            if (profesorBeanHelper.getListaSeleccionProfesores().size() == 1) {
                profesorBeanHelper.setSelecProfesor(profesorBeanHelper.getListaSeleccionProfesores().get(0));
            }
            for (Profesor prof : profesorBeanHelper.getListaSeleccionProfesores()) {
                if (prof.getPROid().equals(profesorBeanHelper.getSelecProfesor().getPROid())) {
                    for (Usuario user : listaOriginal) {
                        if (user.getUSUid() == prof.getPROid()) {
                            prof.setUsuarioUSUid(user);
                        }
                    }
                    auxiliar = profesorBeanHelper.buscarUsuarioPorID(prof.getUsuarioUSUid().getUSUid());
                    if (!isNull(auxiliar))
                        prof.setUsuarioUSUid(auxiliar);

                    listaFiltradaUsu = cargarUsu();
                    lista = listaFiltradaUsu;
                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getUSUid() == profesorBeanHelper.getUsuario2().getUSUid())
                            lista.remove(i);
                    }
                    lista.add(prof.getUsuarioUSUid());

                    profesorBeanHelper.setProfesor(prof);
                    profesorBeanHelper.setUsuario2(prof.getUsuarioUSUid());
                    profesorBeanHelper.getPEModProf(profesorBeanHelper.getProfesor());

                    original = profesorBeanHelper.buscarProfePorID(prof.getPROid());
                    usuarioOriginal = prof.getUsuarioUSUid();
                    listaOriginal = cargarUsu();
                }
            }
        }
        if (profesorBeanHelper.getTransaccion() == false)
            errorTransaccion();
        profesorBeanHelper.terminaTransaccion();
    }

    /**
     * Método para verificar si el profesor tiene asignaciones y si se está
     * visualizando a si mismo para modificar. Si tiene asignaciones va
     * deshabilitar el nújmero de empleado y si esta loggeado no se podra
     * modificar el usuario por lo tanto se deshabilitará.
     */
    public void verificar() {
        if (cabecera.equals("Eliminar profesor")) {
            deshabilitarNumeroP = "true";
            deshabilitarUsuario = "true";
        } else {
            profesorBeanHelper.setTransaccion(true);
            Boolean bandera = profesorBeanHelper.verificarProfesorTieneAsignaciones(profesorBeanHelper.getProfesor().getPROid());
            if (bandera == true) {
                deshabilitarNumeroP = "true";
            } else {
                deshabilitarNumeroP = "false";
            }
            if (profesorBeanHelper.getUsuario().getUSUusuario().equals(profesorBeanHelper.getUsuario2().getUSUusuario())) {
                deshabilitarUsuario = "true";
            } else {
                deshabilitarUsuario = "false";
            }
            if (profesorBeanHelper.getTransaccion() == false)
                errorTransaccion();
            profesorBeanHelper.terminaTransaccion();
        }
    }

}