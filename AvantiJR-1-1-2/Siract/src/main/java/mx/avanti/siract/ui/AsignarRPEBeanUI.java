package mx.avanti.siract.ui;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.AsignarRPEBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author Javier Razo
 * @author Kevin Arizaga
 */
@ManagedBean
@ViewScoped
public class AsignarRPEBeanUI implements Serializable {

    private final int IDCATALOGOADMIASIGNARRP = 17;
    public List<Programaeducativo> programaeducativoList;
    public List<Profesor> profesorList;
    public List<Cicloescolar> listaOrdenadaCiclos;
    public boolean imprimir;
    //Declaracion de variables privadas
    private List<Profesor> listaFiltrada;
    private List<ResponsableProgramaEducativo> RPEFiltrado;
    private AsignarRPEBeanHelper asignarRPEHelper;
    private Profesor profMod;
    private String busqueda;
    private String cabecera;
    private String deshabilitar;
    private String MensajeVal;
    private String mensajeConfirmacion;
    private boolean deshabilitar1;
    private StreamedContent file;
    private boolean deshabilitarNuevaVersion;
    private String textoVersion = "";
    /**
     * Propiedad para inyectar el loginbean
     */
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;


    /**
     * Constructor de la clase para inicializar las variables
     */
    public AsignarRPEBeanUI() {
        asignarRPEHelper = new AsignarRPEBeanHelper();
        busqueda = "";
        cabecera = "";
        deshabilitar = "";
    }

    public String getTextoVersion() {
        return textoVersion;
    }

    public void setTextoVersion(String textoVersion) {
        this.textoVersion = textoVersion;
    }

    public List<Programaeducativo> getProgramaeducativoList() {
        return programaeducativoList;
    }

    public void setProgramaeducativoList(List<Programaeducativo> programaeducativoList) {
        this.programaeducativoList = programaeducativoList;
    }

    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    public boolean isDeshabilitarNuevaVersion() {
        return deshabilitarNuevaVersion;
    }

    public void setDeshabilitarNuevaVersion(boolean deshabilitarNuevaVersion) {
        this.deshabilitarNuevaVersion = deshabilitarNuevaVersion;
    }

    public int getIDCATALOGOADMIASIGNARRP() {
        return IDCATALOGOADMIASIGNARRP;
    }

    /**
     * Post Constructor donde se inicializa el rol y el usuario
     */
    @PostConstruct
    public void postConstructor() {
        asignarRPEHelper.setRolSeleccionado(loginBean.getSeleccionado());
        asignarRPEHelper.setUsuarioLogeado(loginBean.getLogueado());
        asignarRPEHelper.getCicloescolar().setCESid(0);
        asignarRPEHelper.getProgramaEducativo().setPEDid(0);
        asignarRPEHelper.getFiltroProgramaeducativo().setPEDid(0);
        asignarRPEHelper.getFiltroCicloescolar().setCESid(0);
        // filtro();
    }


    public boolean isDeshabilitar1() {
        return deshabilitar1;
    }

    //Getters y Setters de las variables
    public void setDeshabilitar1(boolean deshabilitar1) {
        this.deshabilitar1 = deshabilitar1;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

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

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Profesor> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Profesor> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public AsignarRPEBeanHelper getAsignarRPEHelper() {
        return asignarRPEHelper;
    }

    public void setAsignarRPEHelper(AsignarRPEBeanHelper asignarRPEHelper) {
        this.asignarRPEHelper = asignarRPEHelper;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<ResponsableProgramaEducativo> getRPEFiltrado() {
        return RPEFiltrado;
    }

    public void setRPEFiltrado(List<ResponsableProgramaEducativo> RPEFiltrado) {
        this.RPEFiltrado = RPEFiltrado;
    }

    public List<Cicloescolar> getListaOrdenadaCiclos() {
        return listaOrdenadaCiclos;
    }

    public void setListaOrdenadaCiclos(List<Cicloescolar> listaOrdenadaCiclos) {
        this.listaOrdenadaCiclos = listaOrdenadaCiclos;
    }

    /**
     * Mensaje que se muestra al momento de mostrar la confirmación en el cuadro de dialogo según
     * la acción realizada.
     */
    public void setMensajeConfirmacion() {
        if (cabecera.equalsIgnoreCase("Versionamiento de Responsable")) {
            mensajeConfirmacion = "¿Está seguro de clonar este registro?";
        } else if (deshabilitar.equals("true")) {
            mensajeConfirmacion = "¿Está seguro de eliminar el registro?";
        } else if (cabecera.equals("Modificar Asignación de Responsable")) {
            mensajeConfirmacion = "¿Está seguro de guardar los cambios?";
        }
        PrimeFaces.current().ajax().update("confirmacionId");
    }

    /**
     * Metodo para crear un nuevo registro
     */
    public void nuevo() {
        header(1);
        asignarRPEHelper.setRpeSeleccionado(new ResponsableProgramaEducativo(new Profesor(), new Programaeducativo(), new Cicloescolar()));
        asignarRPEHelper.setProgramaEducativo(asignarRPEHelper.getRpeSeleccionado().getProgramaeducativo());
        asignarRPEHelper.setCicloescolar(asignarRPEHelper.getRpeSeleccionado().getCicloEscolar());
        asignarRPEHelper.setProfesor(asignarRPEHelper.getRpeSeleccionado().getProfesor());
    }

    /**
     * Metodo para eliminar un registro
     */
    public void eliminar() {
        header(2);
        if (!asignarRPEHelper.getRpeSeleccionado().equals(null)) {
            establecerValores();
        }
    }

    /**
     * Metodo para modificar un registro
     */
    public void modificar() {
        header(3);
        if (!asignarRPEHelper.getRpeSeleccionado().equals(false)) {
            establecerValores();
        }
    }

    /**
     * Método para versionar un registro
     */
    public void versionar() {
        header(4);
        if (!asignarRPEHelper.getRpeSeleccionado().equals(false)) {
            establecerValores();
        }
    }

    /**
     * Método para establecer los valores de la selección del RPE en la tabla en un objeto independiente.
     * Se selecciona un RPE y se guarda el Profesor, Programa educativo y Ciclo escolar para un objeto independiente.
     */
    private void establecerValores() {
        asignarRPEHelper.setProfesor(asignarRPEHelper.buscarProfesorPorID(asignarRPEHelper.getRpeSeleccionado().getProfesor().getPROid()));
        asignarRPEHelper.setProgramaEducativo(asignarRPEHelper.buscarProgramaEducativoPorID(asignarRPEHelper.getRpeSeleccionado().getProgramaeducativo().getPEDid()));
        asignarRPEHelper.setCicloescolar(asignarRPEHelper.buscarCicloEscolarPorID(asignarRPEHelper.getRpeSeleccionado().getCicloEscolar().getCESid()));
        asignarRPEHelper.establecerValorListaCiclosRpeSeleccionado();
    }

    /**
     * Metodo para realizar las acciones de altas, bajas y modificaciones al dar clic en confirmar.
     *
     * @return Regresa un vacio
     */
    public void onClickSubmit() {
        setMensajeConfirmacion();
        if (!isFaltanCamposCompletos()) {
            switch (cabecera) {
                case "Agregar Asignación de Responsable":
                    // Hace validaciones para evitar incongruencia con los datos de programas educativos, ciclos escolares y profesores.
                    MensajeVal = asignarRPEHelper.validarAsignado();
                    if (MensajeVal.equals("nada")) {
                        agregarRPE();
                    } else {
                        mostrarMensaje(MensajeVal);
                    }
                    filtro();
                    break;
                case "Modificar Asignación de Responsable":
                    PrimeFaces.current().executeScript("PF('confirmacion').show()");
                    break;
                case "Eliminar Asignación de Responsable":
                    PrimeFaces.current().executeScript("PF('confirmacion').show()");
                    break;
                case "Versionamiento de Responsable":
                    PrimeFaces.current().executeScript("PF('confirmacion').show()");
                    break;
                default:
                    mostrarMensaje("La acción se pude realizar.");
                    filtro();
            }
        } else {
            mostrarMensaje("Favor de llenar todos los campos");
        }
    }

    /**
     * Método para agregar un nuevo responsable de programa educativo una vez hecho clic en aceptar en el primer diálogo.
     */
    private void agregarRPE() {
        //Se crea un nuevo objeto de ResposnsableProgramaEducativo con los datos ingresados en el diálogo.
        ResponsableProgramaEducativo nuevoResponsableProgramaEducativo = new ResponsableProgramaEducativo(
                asignarRPEHelper.buscarProfesorPorID(asignarRPEHelper.getProfesor().getPROid()),
                asignarRPEHelper.buscarProgramaEducativoPorID(asignarRPEHelper.getProgramaEducativo().getPEDid()),
                asignarRPEHelper.buscarCicloEscolarPorID(asignarRPEHelper.getCicloescolar().getCESid())
        );

        //Verifica si el RPE ya existe, en caso de ya tener un registro aumenta la version en 1, caso contrario crea un nuevo registro con version en 1.
        ResponsableProgramaEducativo existeRPE = ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRpeRecentVersion(asignarRPEHelper.getProfesor().getPROid(), asignarRPEHelper.getProgramaEducativo().getPEDid());
        if (!isNull(existeRPE))
            nuevoResponsableProgramaEducativo.setVersion(existeRPE.getVersion() + 1);

        //Método que sirve para eliminar el ROL del antiguo RPE, es necesario enviar el nuevo RPE para verificar si aplica eliminar el ROL.
        eliminarRolActualRPE(nuevoResponsableProgramaEducativo);

        //Guardado del nuevo responsable de programa educativo.
        asignarRPEHelper.agregarNuevoRPE(nuevoResponsableProgramaEducativo);

        mostrarMensaje("Se guardo correctamente.");
        //Se restablecen los valores de los objetos usados para prepararlos para futura agregación.
        restablecerValores();
        PrimeFaces.current().executeScript("PF('dlg').hide()");
    }

    private void eliminarRolActualRPE(ResponsableProgramaEducativo nuevoResponsableProgramaEducativo) {
        ResponsableProgramaEducativo actualRpe = asignarRPEHelper.findActualRpeByProgramaEducativo(asignarRPEHelper.getProgramaEducativo());
        if (actualRpe.getProgramaeducativo().getPEDid() == nuevoResponsableProgramaEducativo.getProgramaeducativo().getPEDid()
                && actualRpe.getProfesor().getUsuarioUSUid().getRolList().contains(ServiceFacadeLocator.getInstanceRolFacade().getRolById(6))//Busca el rol de RPE que es el numero 6
                && actualRpe.getCicloEscolar().getCEScicloEscolar().compareTo(nuevoResponsableProgramaEducativo.getCicloEscolar().getCEScicloEscolar()) < 0) {
            asignarRPEHelper.removerRolAntiguoRPE(actualRpe);
        }
    }

    private void mostrarMensaje(String message) {
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(message));
    }

    private boolean isFaltanCamposCompletos() {
        if (asignarRPEHelper.getProfesor().getPROid() == 0
                || asignarRPEHelper.getCicloescolar().getCESid() == 0
                || asignarRPEHelper.getProgramaEducativo().getPEDid() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo para la confirmacion de las acciones de eliminar o modificar.
     */
    public void Confirmacion() {
        switch (cabecera) {
            case "Modificar Asignación de Responsable":
                MensajeVal = asignarRPEHelper.validarAsignado();
                if (MensajeVal.equals("nada")) {
                    modificarRPE();
                } else {
                    mostrarMensaje(MensajeVal);
                }
                break;
            case "Eliminar Asignación de Responsable":
                eliminarRPE();
                break;
            case "Versionamiento de Responsable":
                versionarRPE();
                break;
            default:
                mostrarMensaje("No se pudo realizar la acción.");
        }

        restablecerValores();
        filtro();
    }

    /**
     * Método para modificar un Responsable de Programa Educativo después de confirmar en el cuadro de diálogo de confirmación.
     */
    private void modificarRPE() {

        if (asignarRPEHelper.getRpeSeleccionado().getCicloEscolar().getCESid() >= asignarRPEHelper.findActualRpeByProgramaEducativo(asignarRPEHelper.getProgramaEducativo()).getCicloEscolar().getCESid()) {
            ResponsableProgramaEducativo responsable = asignarRPEHelper.findActualRpeByProgramaEducativo(asignarRPEHelper.getProgramaEducativo());
            if (!esteResponsableTieneRactAsociado(responsable)) { // Verifica que el RPE que se desea modificar tiene un RACT asociado.

                //Se manda a modificar el RPE a la base de datos.
                asignarRPEHelper.modificarResponsablePEbyID(asignarRPEHelper.getProfesor(), asignarRPEHelper.getProgramaEducativo(), asignarRPEHelper.getCicloescolar());

                mostrarMensaje("Se modificó correctamente");
                PrimeFaces.current().executeScript("PF('dlg').hide()");

            } else {
                mostrarMensaje("No se puede modificar el registro porque tiene RACT asociado.");
            }
        } else {
            mostrarMensaje("No se pueden modificar registros retrospectivos.");
        }
        restablecerValores();
    }

    /**
     * Método para eliminar un Responsable de Programa Educativo después de confirmar en el cuadro de diálogo de confirmación.
     */
    private void eliminarRPE() {
        establecerValores();
        ResponsableProgramaEducativo responsable = asignarRPEHelper.getRpeSeleccionado();

        //Si el registro
        if (asignarRPEHelper.getCicloescolar().getCESid() >= asignarRPEHelper.findActualRpeByProgramaEducativo(asignarRPEHelper.getProgramaEducativo()).getCicloEscolar().getCESid()
                && !esteResponsableTieneRactAsociado(responsable)) { // Verifica que el RPE que se desea modificar tiene un RACT asociado.

            //Se elimina el ROL de RPE antes de eliminar el registro.
            asignarRPEHelper.removerRolAntiguoRPE(responsable);
            asignarRPEHelper.eliminarResponsablePEbyData(asignarRPEHelper.getProfesor(), asignarRPEHelper.getProgramaEducativo(), asignarRPEHelper.getCicloescolar(), asignarRPEHelper.getVersion());

            // Cuando se elimina el RPE más reciente se asigna el ROL de RPE al siguiente registro más reciente después del eliminado.
            asignarRPEHelper.restablecerRolAntiguoRPE(responsable.getProgramaeducativo());
            PrimeFaces.current().executeScript("PF('dlg').hide()");
            mostrarMensaje("Se eliminó correctamente");

        } else {
            mostrarMensaje("No se pueden eliminar registros retrospectivos o que tengan RACT asociado.");
        }
    }

    private void versionarRPE() {
        Integer version = ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRpeRecentVersion(asignarRPEHelper.getProfesor().getPROid(), asignarRPEHelper.getProgramaEducativo().getPEDid()).getVersion();
        ResponsableProgramaEducativo nuevaVersion = new ResponsableProgramaEducativo(asignarRPEHelper.getProfesor(), asignarRPEHelper.getProgramaEducativo(), asignarRPEHelper.getNuevoCicloEscolar(), (version + 1));
        asignarRPEHelper.agregarNuevoRPE(nuevaVersion);

        mostrarMensaje("Se creó la nueva versión correctamente.");

        restablecerValores();
        PrimeFaces.current().executeScript("PF('dlg').hide()");
    }

    /**
     * Método para verificar si el Responsable de Programa Educativo tiene un RACT asociado,
     * true: Cuando en la base de datos existe el registro. Independientemente si el RACT esta en estado "Enviado" o "Parcial".
     * false: No existe el registro en la tabla de "reporteavancecontenidotematico".
     *
     * @param responsable
     * @return
     */
    private boolean esteResponsableTieneRactAsociado(ResponsableProgramaEducativo responsable) {
        List<Reporteavancecontenidotematico> reporteAvanceRpe = new ArrayList<Reporteavancecontenidotematico>();
        for (UnidadaprendizajeImparteProfesor uip :
                responsable.getProfesor().getUnidadaprendizajeImparteProfesorList()) {
            if (asignarRPEHelper.getCicloActual().getCESid() == uip.getCicloEscolarCESid().getCESid()
                    && !uip.getReporteavancecontenidotematicoList().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para establecer la cabecera de la desicion a tomar.
     *
     * @param i variable int para la desicion
     * @return Regresa una variable String con la desicion a tomar.
     */
    public String header(int i) {
        if (i == 1) {
            cabecera = "Agregar Asignación de Responsable";
            asignarRPEHelper.mostrarVersiones(i);
            deshabilitar = "false";
            deshabilitar1 = false;
            deshabilitarNuevaVersion = true;
        }
        if (i == 2) {
            cabecera = "Eliminar Asignación de Responsable";
            textoVersion = "Versión a eliminar:";
            deshabilitar = "true";
            deshabilitar1 = false;
            deshabilitarNuevaVersion = true;
        }
        if (i == 3) {
            cabecera = "Modificar Asignación de Responsable";
            textoVersion = "Versión a modificar:";
            deshabilitar = "false";
            deshabilitar1 = false;
            deshabilitarNuevaVersion = true;
        }
        if (i == 4) {
            cabecera = "Versionamiento de Responsable";
            textoVersion = "Versión a clonar:";
            deshabilitar = "true";
            deshabilitar1 = false;
            deshabilitarNuevaVersion = false;
        }
        return cabecera;
    }

    /**
     * Metodo para la toma de la desicion
     *
     * @param i Variable int para la desicion
     * @return Regresa una variable string de la desicion tomada
     */
    public String tooltip(int i) {
        if (asignarRPEHelper.getRpeSeleccionado() == null
                || asignarRPEHelper.getRpeSeleccionado().getProfesor().getPROid() == null) {
            return "Seleccione un registro de la tabla";
        } else {
            if (i == 2) {
                return "Eliminar";
            }
            if (i == 3) {
                return "Modificar";
            }
            if (i == 4) {
                return "Versionar";
            }
        }
        return "nada";
    }

    /**
     * Metodo para deshabilidar el menu
     *
     * @return Regresa un boleano si se desabilito o no el menu
     */
    public boolean deshabilitarMenu() {
        if (asignarRPEHelper.getRpeSeleccionado() == null
                || asignarRPEHelper.getRpeSeleccionado().getRPEid() == null) {
            return true;
        }
        return false;
    }

    /**
     * Restablece los valores una vez se a realizado una acción, ya sea agregar, modificar, eliminar o versionar un registro.
     */
    public void restablecerValores() {
        RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo());
        asignarRPEHelper.setProfesor(new Profesor());
        asignarRPEHelper.getProfesor().setPROid(0);
        asignarRPEHelper.setProgramaEducativo(new Programaeducativo());
        asignarRPEHelper.setFiltroProgramaeducativo(new Programaeducativo());
        asignarRPEHelper.setFiltroCicloescolar(new Cicloescolar());
        asignarRPEHelper.getProgramaEducativo().setPEDid(0);
        asignarRPEHelper.setCicloescolar(new Cicloescolar());
        asignarRPEHelper.getCicloescolar().setCESid(0);
        asignarRPEHelper.setRpeSeleccionado(new ResponsableProgramaEducativo());
        asignarRPEHelper.getCicloEscolarVersion().setCESid(0);
        busqueda = "";
        asignarRPEHelper.getlistaVersionesRpeSeleccionado().clear();
    }

    /**
     * Metodo para cargar datos
     */
    public void filtro() {
        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        String catalogo = "Responsable de programa educativo";
        loginBean.TienePermiso(list, seleccionado, catalogo);
        listaFiltrada = asignarRPEHelper.filtrado(busqueda);
        RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo());
        listaOrdenadaCiclos = asignarRPEHelper.obtenerCiclo();
        profesorList = asignarRPEHelper.obtenerListaProfesorRolRPE();
        programaeducativoList = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        System.out.println("");
    }

    /**
     * Este método actualiza los datos con el registro más reciente al ciclo actual, para evitar estar actualizando de manera manual cada uno.
     * Estado: Depredated
     * Utilidad: Sin usó.
     */
    private void verificarNuevoCicloEscolar() {
        Cicloescolar cicloRpeMasReciente = asignarRPEHelper.ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo()).get(0).getCicloEscolar();
        if (cicloRpeMasReciente.getCESid() != asignarRPEHelper.getCicloActual().getCESid()) {
            List<ResponsableProgramaEducativo> rpeRecientes = asignarRPEHelper.filtrarRpePorCiclo(cicloRpeMasReciente);
            ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().iniciarTransaccion();
            for (ResponsableProgramaEducativo rpeActual : rpeRecientes) {
                if (rpeActual.getProfesor().getUsuarioUSUid().getRolList().contains(ServiceFacadeLocator.getInstanceRolFacade().consultaRolPorID(6))) { //El Id del rol de Responsable de Programa Educativo es el 6
                    ResponsableProgramaEducativo nuevoRpe = new ResponsableProgramaEducativo(rpeActual);
                    nuevoRpe.setCicloEscolar(asignarRPEHelper.getCicloActual());
                    ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().secureSave(nuevoRpe);
                }
            }
            ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().finalizarTransaccion();
        }
        System.out.println("No hay datos que actualizar");
    }


    /**
     * Metodo para mostrar la seleccion del responsable de programa educativo
     *
     * @return Regresa un boleando si se encontraron datos o no
     */
    public boolean mostrarSeleccionRPE() {
        if (asignarRPEHelper.getListaSeleccion() != null
                && asignarRPEHelper.getListaSeleccion().size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que filtra la lista de Responsables de Programas Educativos por ciclo. Si se desea realizar
     * una busqueda especifica por ciclo se entrará a este método.
     */
    public void filtrarRpePorCiclo() {
        asignarRPEHelper.getFiltroProgramaeducativo().setPEDid(0);
        busqueda = "";
        if (asignarRPEHelper.getFiltroCicloescolar().getCESid() != 0) {
            RPEFiltrado = asignarRPEHelper.filtrarRpePorCiclo();
        } else {
            RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo());
        }
    }

    /**
     * Método para filtrar Responsable de Programa Educativo por Programa Educativo.
     * Si se desea realizar una busqueda especificando un "Programa Educativo", se entrará
     * a este método.
     */
    public void filtrarRpePorPE() {
        busqueda = "";
        if (asignarRPEHelper.getFiltroCicloescolar().getCESid() != 0) {
            RPEFiltrado = asignarRPEHelper.filtrarRpePorCiclo();
            if (asignarRPEHelper.getFiltroProgramaeducativo().getPEDid() != 0) {
                RPEFiltrado.clear();
                List<ResponsableProgramaEducativo> listaTemporal = new ArrayList<ResponsableProgramaEducativo>();
                listaTemporal = asignarRPEHelper.filtrarRpePorPE();
                for (ResponsableProgramaEducativo rpe :
                        listaTemporal) {
                    if (rpe.getProgramaeducativo().getPEDid() == asignarRPEHelper.getFiltroProgramaeducativo().getPEDid()
                            && rpe.getCicloEscolar().getCESid() == asignarRPEHelper.getFiltroCicloescolar().getCESid()) {
                        RPEFiltrado.add(rpe);
                    }
                }
            }
        } else if (asignarRPEHelper.getFiltroCicloescolar().getCESid() == 0) {
            RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo());
            if (asignarRPEHelper.getFiltroProgramaeducativo().getPEDid() != 0) {
                RPEFiltrado.clear();
                List<ResponsableProgramaEducativo> listaTemporal = new ArrayList<ResponsableProgramaEducativo>();
                listaTemporal = asignarRPEHelper.ordenarListaPorCiclo(asignarRPEHelper.filtrarRpePorPE());
                for (ResponsableProgramaEducativo rpe :
                        listaTemporal) {
                    if (rpe.getProgramaeducativo().getPEDid().equals(asignarRPEHelper.getFiltroProgramaeducativo().getPEDid())) {
                        RPEFiltrado.add(rpe);
                    }
                }
            }
        }
    }

    /**
     * Método para filtrar Responsables de Programa Educativos por búsqueda especifica por nombre.
     */
    public void filtrarRpeProgramaEducativo() {
        busqueda = "";
        asignarRPEHelper.getFiltroCicloescolar().setCESid(0);
        if (asignarRPEHelper.getFiltroProgramaeducativo().getPEDid().equals(0)) {
            RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo());
        } else {
            RPEFiltrado = asignarRPEHelper.filtrarSinDuplicar(asignarRPEHelper.getFiltroProgramaeducativo());
        }
    }

    /**
     * Método para filtrar los Responsables de Programa Educativo por medio del nombre y/o apellidos del Responsable.
     */
    public void filtrarRpeBusquedaEspecifica() {
        String search = new String(busqueda);
        filtrarRpePorPE();
        if (!search.equals("") && !asignarRPEHelper.getFiltroProgramaeducativo().getPEDid().equals(0)) {
            RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(asignarRPEHelper.filtrarRpePorNombre(search, asignarRPEHelper.filtrarSinDuplicar(asignarRPEHelper.getFiltroProgramaeducativo())));
        } else if (search.equals("") && !asignarRPEHelper.getFiltroProgramaeducativo().getPEDid().equals(0)) {
            RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(asignarRPEHelper.filtrarRpePorNombre(search, asignarRPEHelper.filtrarSinDuplicar(asignarRPEHelper.getFiltroProgramaeducativo())));
        } else if (!search.equals("") && asignarRPEHelper.getFiltroProgramaeducativo().getPEDid().equals(0)) {
            RPEFiltrado = asignarRPEHelper.ordenarListaPorCiclo(asignarRPEHelper.filtrarRpePorNombre(search, asignarRPEHelper.filtrarSinDuplicar()));
        }
    }

    public boolean isImprimir() {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^ IMPRIMIR = " + imprimir);
        return imprimir;
    }

    /**
     * Método para añadir la información al archivo PDF.
     *
     * @author Kevin Arizaga
     */
    public void asignarDatosPDF() throws DocumentException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (RPEFiltrado.isEmpty()) { //Verifica si esta vacia la tabla, si es el caso no genera el archivo PDF.
            context.addMessage(null, new FacesMessage("", "Se necesita tener datos en la tabla"));
            restablecerValores();
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document pdf = new Document();
        PdfWriter.getInstance(pdf, baos); //Método para obtener la instancia del pdf usando ByteArray's como OutputStream.
        pdf.open();

        //Obtener la fecha actual del sistema
        String fecha = obtenerFechaActual();

        //Titulo "Universidad Autonoma de Baja California" en el PDF.
        Paragraph uabcParagraph = new Paragraph("UNIVERSIDAD AUTÓNOMA DE BAJA CALIFORNIA", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, new Color(0, 113, 65)));
        Paragraph facultad = new Paragraph("FACULTAD DE INGENIERÍA", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 113, 65)));
        Paragraph tituloParagraph = new Paragraph("Histórico de Responsable de programa educativo", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 113, 65)));
        Paragraph fechaParagraph = new Paragraph(fecha, FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 113, 65)));

        //Alineación del titulo.
        uabcParagraph.setAlignment(1);
        facultad.setAlignment(1);
        tituloParagraph.setAlignment(1);
        fechaParagraph.setAlignment(2);

        //Anadir todos los elementos anteriormente declarados al archivo PDF.
        pdf.add(uabcParagraph);
        pdf.add(facultad);
        pdf.add(Chunk.NEWLINE);
        pdf.add(tituloParagraph);
        pdf.add(fechaParagraph);
        //Agregar espacio

        //Agregar logo UABC
        try {
            Image logoUABC = Image.getInstance(getClass().getClassLoader().getResource("logo.png"));
            //Posición de la imagen
            logoUABC.setAbsolutePosition(34, 706);
            logoUABC.scaleAbsolute(60, 90);
            //Agregar imagen al pdf
            pdf.add(logoUABC);
        } catch (Exception exception) {
            System.out.println("**** NO SE ENCONTRO LA RUTA DE IMAGEN ESPECIFICADA ****");
        }

        //Agregar espacios al archivo para un mejor orden.
        pdf.add(Chunk.NEWLINE);

        //Tabla principal del documento (solo los titulos) con los "Responsables del Programa Educativo".
        PdfPTable tituloRpePdfPTable = new PdfPTable(4);
        tituloRpePdfPTable.setWidths(new int[]{200, 70, 210, 50});
        tituloRpePdfPTable.setWidthPercentage(100);

        //Declaración de los titulos de la tabla de RPE
        PdfPCell programaEducativoPdfPCell = new PdfPCell(new Paragraph("Programa educativo", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));
        PdfPCell noEmpleadoPdfPCell = new PdfPCell(new Paragraph("No. empleado", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));
        PdfPCell rpePdfPCell = new PdfPCell(new Paragraph("Responsable de programa Educativo", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));
        PdfPCell cicloEscolarPdfPCell = new PdfPCell(new Paragraph("Ciclo escolar", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new Color(67, 67, 67))));

        //Alineación de los titulos de la tabla.
        programaEducativoPdfPCell.setHorizontalAlignment(1);
        noEmpleadoPdfPCell.setHorizontalAlignment(1);
        rpePdfPCell.setHorizontalAlignment(1);
        cicloEscolarPdfPCell.setHorizontalAlignment(1);

        //Columnas de la tabla de "rpePdf
        tituloRpePdfPTable.addCell(programaEducativoPdfPCell);
        tituloRpePdfPTable.addCell(noEmpleadoPdfPCell);
        tituloRpePdfPTable.addCell(rpePdfPCell);
        tituloRpePdfPTable.addCell(cicloEscolarPdfPCell);

        //Tabla principal del documento (el contenido) con los "Responsables del Programa Educativo".
        PdfPTable contenidoRpePdfPTable = new PdfPTable(4);
        contenidoRpePdfPTable.setWidths(new int[]{200, 70, 210, 50});
        contenidoRpePdfPTable.setWidthPercentage(100);

        //Recorrer lista RPEFiltrado que contiene los datos de la tabla que se muestra en el XHTML.
        for (ResponsableProgramaEducativo rpe : RPEFiltrado) {
            contenidoRpePdfPTable.addCell(new Paragraph(rpe.getProgramaeducativo().getPEDnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            PdfPCell noEmpleadoNamePdfPCell = new PdfPCell(new Paragraph(String.valueOf(rpe.getProfesor().getPROnumeroEmpleado()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            noEmpleadoNamePdfPCell.setHorizontalAlignment(1);
            contenidoRpePdfPTable.addCell(noEmpleadoNamePdfPCell);
            contenidoRpePdfPTable.addCell(new Paragraph(rpe.getProfesor().getPROnombre() + " " +
                    rpe.getProfesor().getPROapellidoPaterno() + " " +
                    rpe.getProfesor().getPROapellidoMaterno(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            PdfPCell cicloEscolarNamePdfPCell = new PdfPCell(new Paragraph(rpe.getCicloEscolar().getCEScicloEscolar(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            cicloEscolarNamePdfPCell.setHorizontalAlignment(1);
            contenidoRpePdfPTable.addCell(cicloEscolarNamePdfPCell);
        }

        //Anadir todos los elementos anteriormente declarados al archivo PDF.
        pdf.add(tituloRpePdfPTable);
        pdf.add(contenidoRpePdfPTable);
        pdf.close();

        ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
        file = new DefaultStreamedContent(in, "application/pdf", asignarNombreArchivoPDF() + ".pdf", "UTF-8", baos.size());

        //Método para poder generar el documento para descargar.
        //writePDFToResponse(context, baos, asignarNombreArchivoPDF());
    }

    /**
     * Obtiene el archivo pdf previamente contruido en asignarDatosPDF.
     *
     * @return file
     */
    public StreamedContent getFile() {
        return file;
    }


    /**
     * Método que sirve para generar la descarga del archivoPDF usando ExternalContext, del contexto de FacesContext
     * Además de recibir FacesContext se recibé un OutputStream de ByteArrays y el nombre del archivo a generar.
     *
     * @param context
     * @param baos
     * @param fileName
     */
    private void writePDFToResponse(FacesContext context, ByteArrayOutputStream baos, String fileName) {
        ExternalContext externalContext = context.getExternalContext();
        try {
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf"); //Establecer el tipo del archivo
            externalContext.setResponseHeader("Expires", "0"); //Establecer la fecha/tiempo en que la respuesta se considera obsoleta.
            externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0"); //Se establecen "Directivas" para la cache tanto de respuesta como de peticiones.
            externalContext.setResponseHeader("Pragma", "public"); //Parece que sirve para compatibilidad con anteriores caches de versiones de HTTP
            externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf"); //Esta linea de código sirve para determinar si se descarga el archivo ó se muestra en nueva pestaña, además permite mostrar (o no) el "Save As".
            externalContext.setResponseContentLength(baos.size()); //Sirve para establecer el tamano del archivo, indica cuanto se lleva descargado del archivo, si no se coloca no se sabra el tiempo restante para completar la descarga.
            OutputStream out = externalContext.getResponseOutputStream();
            baos.writeTo(out);
            context.responseComplete();
        } catch (Exception e) {
        }
    }

    /**
     * Método para obtener el formato de la fecha actual.
     *
     * @return fecha - String que contiene la fecha del momento con el formato dd/MM/YYYY.
     */
    private String obtenerFechaActual() {
        Date date = new Date();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }

    /**
     * Método para obtener el formato de una fecha determina.
     *
     * @param date Fecha que se recibe para darle el formato.
     * @return fecha - String que contiene la fecha recibida con el formato dd/MM/YYYY.
     */
    private String obtenerFechaActual(Date date) {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }

    /**
     * Método para asignar nombre al archivo PDF.
     *
     * @return nombreArchivoPDF - Contiene el nombre del archivo más la fecha actual en el momento.
     */
    public String asignarNombreArchivoPDF() {
        String nombreArchivoPDF = "Histórico Responsable Programa Educativo " + obtenerFechaActual();
        return nombreArchivoPDF;
    }

    public void deshabilitarBotones() {
        asignarRPEHelper.setRpeSeleccionado(null);
    }

    public boolean mostrarNuevoCicloVersion() {
        if (cabecera.equals("Versionamiento de Responsable")) {
            return true;
        } else {
            return false;
        }
    }
}