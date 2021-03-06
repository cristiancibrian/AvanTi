/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.CoordinadorAreaAdministrativaBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class CoordinadorAreaAdministrativaBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String noUnidadesAprendizaje = "0";

    private final int IDCATALOGOADMICOORDAREADMIN = 19;

    public int getIDCATALOGOADMICOORDAREADMIN() {
        return IDCATALOGOADMICOORDAREADMIN;
    }

    private CoordinadorAreaAdministrativaBeanHelper CoordinadorAreaAdministrativaBeanHelper;
    private CoordinadorAreaAdministrativaBeanHelper CAAHelper;
    private List<Coordinadorareaadministrativa> listaFiltrada;
    Coordinadorareaadministrativa auxCAA;
    Coordinadorareaadministrativa CAAseleccionado;

    private List<Programaeducativo> listaPE;
    private List<Unidadaprendizaje> listaUA;
    private List<Areaconocimiento> listaAC;
    private List<Planestudio> listaPlan;
    private List<Profesor> listaProfesor;
    private List<Grupo> listaGrupo;
    private Unidadaprendizaje u;

    public static String header;
    private String deshabilitar;
    private String busqueda = "";
    private String mensajeConfirm;
    private String deshabilitarBoton;
    private String deshabilitarSubgrupo;
    private String mensajeVal;
    public String nomImg;
    private String etiqueta = "Seleccione unidad de aprendizaje";

    boolean bolSelPED = false;
    boolean botones = true;
    private boolean rpeAsignado = true;
    public Boolean imprimir;
    boolean mostrarPE = false;

    Date date = new Date();

    @PostConstruct
    public void postConstructor() {
        CAAHelper.setUsuario(loginBean.getLogueado());
        CAAHelper.setRolSeleccionado(loginBean.getSeleccionado());
        System.out.println("rol " + loginBean.getSeleccionado());
        System.out.println("usuario id " + loginBean.getLogueado().getUSUid());
        if (loginBean.getSeleccionado().equalsIgnoreCase("Responsable de Programa Educativo")) {
            mostrarPE = true;
            try {
                Profesor p = CAAHelper.getUsuario().getProfesorList().get(0);

                List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
                for (ResponsableProgramaEducativo rpe : p.getResponsableProgramaEducativosList())
                    if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                        peProgramaeducativoList.add(rpe.getProgramaeducativo());
                CAAHelper.setPedSeleccion(peProgramaeducativoList.get(0));

//                CAAHelper.setPedSeleccion(p.getProgramaeducativoList().get(0));
            } catch (Exception e) {
                CAAHelper.setPedSeleccion(CAAHelper.findProgramaEducativoById(13));
                rpeAsignado = false;
            }
        }
        tienePermisos();
    }

    /**
     * M??todo que hace tareas que son necesarias hacer antes de desplegar la
     * pagina
     */
    public CoordinadorAreaAdministrativaBeanUI() {
        init();
    }

    /**
     * M??todo que instancia las listas de coordinador y ??rea administrativa
     */
    private void init() {
        CoordinadorAreaAdministrativaBeanHelper = new CoordinadorAreaAdministrativaBeanHelper();
        CAAHelper = new CoordinadorAreaAdministrativaBeanHelper();
        auxCAA = new Coordinadorareaadministrativa();
    }

    // ---------------------- METODOS ----------------------//
    /**
     *
     * M??todo que se ejecuta cuando se selecciona un registro de la tabla
     *
     * @param event Informaci??n del archivo seleccionado
     */
    public void seleccion(SelectEvent event) {
        // Se habilitan los bot??nes de eliminar y modificar
        setBotones(true);
    }

    /**
     * M??todo para preparar la informaci??n necesaria para que el usuario agregue
     * un coordinador de ??rea administrativa
     */
    public void nuevo() {
        // Se limpia cualquier registro que se haya seleccionado
        limpiarSeleccion();
        CAAHelper.setUnidadesAp(new ArrayList<String>());
        auxCAA = new Coordinadorareaadministrativa();
        auxCAA.setUnidadaprendizajeList(new ArrayList<Unidadaprendizaje>());
        // Se asigna el titulo de agregar al cuadro de dialogo
        dlgCabecera(1);
        // Se limpian las listas de elecci??n
        CAAHelper.setAGUAP(new UnidadaprendizajeImparteProfesor());
        CAAHelper.setPlanEstudio(new Planestudio());
        CAAHelper.setAreaConocimiento(new Areaconocimiento());
        CAAHelper.setUnidadApren(new Unidadaprendizaje());
        CAAHelper.setProfesor(new Profesor());
        CAAHelper.setGrupo(new Grupo());
        CAAHelper.setSelecCoordinadorArea(new Coordinadorareaadministrativa());
        // Se verifica el alcance del rol
        if (loginBean.getSeleccionado().equalsIgnoreCase("Director")
                || loginBean.getSeleccionado().equalsIgnoreCase("Subdirector")
                || loginBean.getSeleccionado().equalsIgnoreCase("Administrador")) {
            CAAHelper.setProgramaEducativo(new Programaeducativo());
        }
        // Se consigue y asigna la informaci??n de las listas
        cargarPE();
        cargarPlan();
        cargarAC();
        cargarUA();
        cargarGrupo();
        cargarProfesor();
        // Si el alcance solo es permite ver su programa educativo se asigna de
        // una vez
        if (CAAHelper.getProgramaEducativo().getPEDclave() != 0) {
            filtrarPlanYProfPorPE();
        }
    }

    /**
     * M??todo para preparar la informaci??n necesaria para que el usuario
     * modifique un coordinador de ??rea administrativa
     */
    public void modificar() {
        auxCAA = new Coordinadorareaadministrativa();
        //auxCAA.setUnidadaprendizajeList(new ArrayList<Unidadaprendizaje>());
        // Se asigna el titulo de agregar al cuadro de dialogo
        dlgCabecera(3);
        // Se consigue y asigna la informaci??n de las listas
        cargarUA();
        cargarProfesor();
        cargarPE();
        cargarAC();
        cargarGrupo();
        try {
            // Si solo se selecciono a 1 registro entra en este if donde toma el primero de la lista que es el unico que esta
            if (CAAHelper.getListaCAASeleccionada().size() >= 1) {
                // Se asigna la informaci??n del registro seleccionado
                CAAHelper.setProfesor(CAAHelper.getListaCAASeleccionada().get(0).getProfesorPROid());
                for (Unidadaprendizaje uap : CAAHelper.getListaCAASeleccionada().get(0).getUnidadaprendizajeList()) {
                    CAAHelper.getUnidadesAp().add(uap.getUAPid().toString());
                }
                if (loginBean.getSeleccionado().equalsIgnoreCase("Director") || loginBean.getSeleccionado().equalsIgnoreCase("Subdirector") || loginBean.getSeleccionado().equalsIgnoreCase("Administrador")) {
                    CAAHelper.setProgramaEducativo(CAAHelper.findProgramaEducativoById(CAAHelper.getListaCAASeleccionada().get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid()));
                } else {
                    CAAHelper.setProgramaEducativo(CAAHelper.getProgramaEducativo());
                }
                // Asignando el programa educativo ahora se pueden consultar las ??reas
                CAAHelper.consultarAreas();
                CAAHelper.setArea(CAAHelper.getListaCAASeleccionada().get(0).getAreaAdministrativaAADid());
                // Se asigna tambien el ??rea de conocimiento ya conociendo el programa educativo
                CAAHelper.setAreaConocimiento(CAAHelper.consultaAreaConocimientoPorUAYPED(CAAHelper.getListaCAASeleccionada().get(0).getUnidadaprendizajeList().get(0).getUAPid(), CAAHelper.getProgramaEducativo().getPEDid()));
                // Se asigna los planes de estudio conociendo el Progama educativo
                cargarPlanPorPE((CAAHelper.findByPlanEstudioId(CAAHelper.getAreaConocimiento().getPlanEstudioPESid().getPESid())).getProgramaEducativoPEDid().getPEDid());
                CAAHelper.setPlanEstudio(CAAHelper.findByPlanEstudioId(CAAHelper.getAreaConocimiento().getPlanEstudioPESid().getPESid()));
                auxCAA.setUnidadaprendizajeList(CAAHelper.getListaCAASeleccionada().get(0).getUnidadaprendizajeList());
                filtrarListas();
            } else {
                //Si por alguna razon puede mofificar sin que haya seleccionado algun registro manda el siguiente mensaje de error
                if (CAAHelper.getListaCAASeleccionada().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");              
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            PrimeFaces.current().executeScript("PF('dlg').show()");
        } catch (NullPointerException e) {
            // Si sucede un error se asigna todo como vacio
            e.printStackTrace();
            CAAHelper.setAGUAP(new UnidadaprendizajeImparteProfesor());
            CAAHelper.setUnidadApren(new Unidadaprendizaje());
            CAAHelper.setProfesor(new Profesor());
            CAAHelper.setGrupo(new Grupo());
        }
        cargaDeListas();
        filtrarListas();
        System.out.println("qeiofnwoquefnwerf");
    }

    /**
     * M??todo para preparar la informaci??n necesaria para que el usuario elimine
     * un coordinador de ??rea administrativa
     */
    public void eliminar() {
        // Se asigna el titulo de agregar al cuadro de dialogo
        dlgCabecera(2);
        // Se consultan las ??reas administrativas
        CAAHelper.consultarAreas();
        // Se consigue y asigna la informaci??n de las listas
        cargaDeListas();
        try {
            // Pregunta si solo se selecciono un registro, si es asi toma el primero y unico de la lista
            if (CAAHelper.getListaCAASeleccionada().size() >= 1) {
                // Se asigna la informaci??n del registro seleccionado
                CAAHelper.setProfesor(CAAHelper.getListaCAASeleccionada().get(0).getProfesorPROid());
                for (Unidadaprendizaje uap : CAAHelper.getListaCAASeleccionada().get(0).getUnidadaprendizajeList()) {
                    CAAHelper.getUnidadesAp().add(uap.getUAPid().toString());
                }
                if (loginBean.getSeleccionado().equalsIgnoreCase("Director") || loginBean.getSeleccionado().equalsIgnoreCase("Subdirector") || loginBean.getSeleccionado().equalsIgnoreCase("Administrador")) {
                    CAAHelper.setProgramaEducativo(CAAHelper.findProgramaEducativoById(CAAHelper.getListaCAASeleccionada().get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid()));
                } else {
                    CAAHelper.setProgramaEducativo(CAAHelper.getProgramaEducativo());
                }
                // Asignando el programa educativo ahora se pueden consultar las ??reas
                CAAHelper.consultarAreas();
                CAAHelper.setArea(CAAHelper.getListaCAASeleccionada().get(0).getAreaAdministrativaAADid());
                // Se asigna tambien el ??rea de conocimiento ya conociendo el programa educativo
                CAAHelper.setAreaConocimiento(CAAHelper.consultaAreaConocimientoPorUAYPED(CAAHelper.getListaCAASeleccionada().get(0).getUnidadaprendizajeList().get(0).getUAPid(), CAAHelper.getProgramaEducativo().getPEDid()));
                // Se asigna los planes de estudio conociendo el Progama educativo
                cargarPlanPorPE((CAAHelper.findByPlanEstudioId(CAAHelper.getAreaConocimiento().getPlanEstudioPESid().getPESid())).getProgramaEducativoPEDid().getPEDid());
                CAAHelper.setPlanEstudio(CAAHelper.findByPlanEstudioId(CAAHelper.getAreaConocimiento().getPlanEstudioPESid().getPESid()));
                noUnidadesAprendizaje = CAAHelper.getListaCAASeleccionada().get(0).getUnidadaprendizajeList().size() + " ";
                filtrarListas();
            } else {
                //Si por alguna razon puede mofificar sin que haya seleccionado algun registro manda el siguiente mensaje de error
                if (CAAHelper.getListaCAASeleccionada().size() < 1) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
//            cargarProfesor();
        } catch (NullPointerException e) {
            e.printStackTrace();
            // Si sucede un error se asigna todo como vacio
            CAAHelper.setAGUAP(new UnidadaprendizajeImparteProfesor());
            CAAHelper.setUnidadApren(new Unidadaprendizaje());
            CAAHelper.setProfesor(new Profesor());
            CAAHelper.setGrupo(new Grupo());
        }
        cargaDeListas();
        filtrarListas();
    }

    /**
     * *
     * Atributo que habilita y desabilita los botones de eliminar y modificar
     *
     * @return true o false
     */
    public String botonesModElim() {
        if (CAAHelper.getListaCAASeleccionada() == null || CAAHelper.getListaCAASeleccionada().size() < 1 || CAAHelper.getListaCAASeleccionada().isEmpty() == true) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * M??todo que muestra el mensaje de confirmaci??n al eliminar
     *
     */
    public void confirmacion() {
        PrimeFaces.current().executeScript("PF('confirmdlg').show()");
    }

    /**
     * M??todo que se ejecuta al aceptar la corfirmaci??n de eliminaci??n
     *
     */
    public void eliminarCAA() {
        //Se pregunta si solo se selecciono un registro para trabajar con el
        if (CAAHelper.getListaCAASeleccionada().size() == 1) {
            // Se actualiza la tabla
            filtro();
            // Se crea el objeto con el registro que se va a eliminar tomando el unico de la lista
            Coordinadorareaadministrativa c = (Coordinadorareaadministrativa) CAAHelper.getListaCAASeleccionada().get(0);
            // Se elimina
            CAAHelper.eliminarAsignacion(c);
            PrimeFaces.current().executeScript("PF('wconfEli').hide()");
            if (CAAHelper.isTransaccion()) {
                // Se limpia la selecci??n
                c = new Coordinadorareaadministrativa();
                setCAAseleccionado(new Coordinadorareaadministrativa());
                CAAHelper.consultarAreasAdministrativas();
                CAAHelper.getListaCAASeleccionada().clear();
            }

        } else {
            // Se actualiza la tabla
            filtro();

            Coordinadorareaadministrativa c = new Coordinadorareaadministrativa();

            //Es un for que ayuda a recorrer la lista
            for (int i = 0; i < CAAHelper.getListaCAASeleccionada().size(); i++) {
                //Se pregunta si el nombre del area seleccionada y el profesor responsable del area se encuentran en un registro de la lista
                if (CAAHelper.getArea().getAADnombre().equals(CAAHelper.getListaCAASeleccionada().get(i).getAreaAdministrativaAADid().getAADnombre()) && CAAHelper.getProfesor().getPROrfc().equals(CAAHelper.getListaCAASeleccionada().get(i).getProfesorPROid().getPROrfc())) {
                    //Si encontro un registro que cumpla la condicion, lo guarda en el objeto c
                    c = (Coordinadorareaadministrativa) CAAHelper.getListaCAASeleccionada().get(i);
                    //Toma la posicion donde estaba y lo elimina de la lista
                    CAAHelper.getListaCAASeleccionada().remove(i);
                    break;
                }
            }

            CAAHelper.eliminarAsignacion(c);
            if (CAAHelper.isTransaccion()) {
                CAAHelper.consultarAreasAdministrativas();

                boolean mostrarDialog = false;
                //Este if pregunta si la lista seleccionada no esta vacia, para asi asignar el siguiente registro
                //Despues de eliminar el deseado 
                if (!CAAHelper.getListaCAASeleccionada().isEmpty()) {
                    CAAHelper.setSelecCoordinadorArea(CAAHelper.consultarCoordinacion(CAAHelper.getListaCAASeleccionada().get(0).getCOAid()));
                    onChangeCAA();
                    mostrarDialog = true;
                } else {
                    CAAHelper.getListaCAASeleccionada().clear();
                    PrimeFaces.current().executeScript("PF('wconfEli').hide()");
                }
            }
        }
        PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
    }

    /**
     * *
     * Este m??todo se ejecuta cuando se da aceptar por primera vez en el cuadro
     * de dialogo
     */
    public void onClickSubmit() {
        filtro();
        boolean noExiste = true;
        String repetido = "";
        if (validarCamposVacios()) {
            // Si hay campos que no a llenado el usuario se le pide que los llene
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Favor de llenar todos los campos vacios");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            if (CoordinadorAreaAdministrativaBeanUI.header.equalsIgnoreCase("Asignar coordinaci??n ??rea administrativa")) {
                // Se crea un objeto de coordinador para juntar toda la 
                // informaci??n del registro
                Coordinadorareaadministrativa c = new Coordinadorareaadministrativa();
                Unidadaprendizaje u = new Unidadaprendizaje();
                boolean bandera = true;
                //Lista de todas las asignaciones de coordinador de ??reas administrativas registradas
                List<Coordinadorareaadministrativa> cs = CAAHelper.getCs();
                // ??reas administrativas a las que pertenece el profesor
                List<Areaadministrativa> list = CAAHelper.consultaAreaAdministrativaPorCoordinadorAreaAdministrativa(CAAHelper.getProfesor().getPROid());
                if (!list.isEmpty()) {
                    //Si el profesor tiene ??reas administrativas se vuelve false
                    bandera = false;
                }
                if (!bandera) {
                    // Si no paso todos los filtros se muestra un mensaje de correcci??n
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Un profesor no puede coordinar m??s de un ??rea");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    boolean bandera2 = true;
                    //Si paso todos los filtros se recorre la lista de las unidades de aprendizaje seleccionadas
                    for (int q = 0; q < CAAHelper.getUnidadesAp().size(); q++) {
                        try {
                            // Pocisi??n del ciclo
                            // Recorre la lista de todas las asignaciones ya asignadas
                            for (int i = 0; i < cs.size(); i++) {
                                // Compara si los programas educativos son iguales
                                if (CAAHelper.getProgramaEducativo().getPEDid() == cs.get(i).getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid()) {
                                    // Compara si las unidades de aprendizaje son iguales
                                    for (Unidadaprendizaje ua : cs.get(i).getUnidadaprendizajeList()) {
                                        if (Integer.parseInt(CAAHelper.getUnidadesAp().get(q)) == ua.getUAPid()) {
                                            // Compara si las ??reas administrativas son diferentes
                                            if (!(cs.get(i).getAreaAdministrativaAADid().getAADid().compareTo(CAAHelper.getArea().getAADid()) == 0)) {
                                                bandera2 = false;
                                                FacesContext context = FacesContext.getCurrentInstance();
                                                context.addMessage(null, new FacesMessage("", "La unidad ( " + ua.getUAPnombre() + " ) ya se encuentra asignada"));
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // Si ocurre un error inesperado se le notifica al
                            // usuario
                            e.printStackTrace();
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro esta repetido");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                        }
                        // Se limpian las variables
                        c = new Coordinadorareaadministrativa();
                        u = new Unidadaprendizaje();
                    }
                    if (bandera2) {
                        // Si no se repite la unidad de aprendizaje
                        // se agrega el coordinador
                        c.setAreaAdministrativaAADid(CAAHelper.getArea());
                        c.setProfesorPROid(CAAHelper.getProfesor());
                        c.setUnidadaprendizajeList(actualizarCoordUnidadesAP());

                        List<Coordinadorareaadministrativa> caa = CAAHelper.getCs();
                        for (Coordinadorareaadministrativa coordaa : caa) {
                            for (Unidadaprendizaje u3 : c.getUnidadaprendizajeList()) {
                                for (Unidadaprendizaje u2 : coordaa.getUnidadaprendizajeList()) {
                                    if ((u3.getUAPid()).equals(u2.getUAPid()) && !(c.getProfesorPROid().equals(coordaa.getProfesorPROid()))) {
                                        noExiste = false;
                                        repetido = u3.getUAPnombre();
                                    }
                                }
                            }
                        }
                        // Se le notifica al usuario
                        PrimeFaces.current().executeScript("PF('dlg').hide()");
                        if (noExiste) {
                            // Se registra el coordinador
                            CAAHelper.asignarAreaAdministrativa(c);

                        } else {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("", "La unidad ( " + repetido + " ) ya se encuentra asignada"));
                        }
                    }
                }

                // Se limpia la selecci??n
                CAAHelper.setArea(new Areaadministrativa());
                CAAHelper.setUnidadesAp(new ArrayList<String>());
                CAAHelper.setProfesor(new Profesor());
                CAAHelper.consultarAreasAdministrativas();
            } else if (CoordinadorAreaAdministrativaBeanUI.header.equalsIgnoreCase("Modificar asignaci??n ??rea administrativa")) {
                // Si se esta modificando un registro
                int i = 0;
                try {
                    Coordinadorareaadministrativa c = new Coordinadorareaadministrativa();
                    //Verifica si se selecciono uno o varios registros
                    if (CAAHelper.getListaCAASeleccionada().size() == 1) {
                        //Si es uno selecciona el unico registro que hay 
                        c = (Coordinadorareaadministrativa) CAAHelper.getListaCAASeleccionada().get(0);
                    } else {
                        // c = (Coordinadorareaadministrativa) CAAHelper.getSelecCoordinadorArea();
                        //Si son varios pregunta si el nombre del area y el coordinador se encuentran en algun registro de la lista
                        for (i = 0; i < CAAHelper.getListaCAASeleccionada().size(); i++) {
                            if (CAAHelper.getArea().getAADnombre().equals(CAAHelper.getListaCAASeleccionada().get(i).getAreaAdministrativaAADid().getAADnombre()) && CAAHelper.getProfesor().getPROrfc().equals(CAAHelper.getListaCAASeleccionada().get(i).getProfesorPROid().getPROrfc())) {
                                //Si se encontro, se guarda en c
                                c = (Coordinadorareaadministrativa) CAAHelper.getListaCAASeleccionada().get(i);
                                break;
                            }
                        }

                    }

                    c.setAreaAdministrativaAADid(CAAHelper.consultarAreaadministrativa(CAAHelper.getArea().getAADid()));
                    // c.setAreaAdministrativaAADid(CAAHelper.getArea());
                    c.setProfesorPROid(CAAHelper.consultarProfesor(CAAHelper.getProfesor().getPROid()));
                    c.setUnidadaprendizajeList(actualizarCoordUnidadesAP());

                    List<Coordinadorareaadministrativa> caa = CAAHelper.getCs();
                    for (Coordinadorareaadministrativa coordaa : caa) {
                        for (Unidadaprendizaje u3 : c.getUnidadaprendizajeList()) {
                            for (Unidadaprendizaje u2 : coordaa.getUnidadaprendizajeList()) {
                                if ((u3.getUAPid()).equals(u2.getUAPid())) {
                                    if (!(Objects.equals(coordaa.getCOAid(), c.getCOAid()))) {
                                        noExiste = false;
                                        repetido = u3.getUAPnombre();
                                    }
                                }
                            }
                        }
                    }
                    if (noExiste) {
                        CAAHelper.modificarAsignacionAreaAdministrativa(c);
                        setCAAseleccionado(c);
                    } else {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "La unidad ( " + repetido + " ) ya se encuentra asignada"));
                    }

                    if (CAAHelper.isTransaccion()) {
                        setCAAseleccionado(c);
                        CAAHelper.consultarAreasAdministrativas();
                        if (CAAHelper.getListaCAASeleccionada().size() == 1) {
                            PrimeFaces.current().executeScript("PF('dlg').hide()");
                            limpiarSeleccion();
                            c = new Coordinadorareaadministrativa();
                        } else {
                            CAAHelper.setSelecCoordinadorArea(CAAHelper.consultarCoordinacion(c.getCOAid()));
                            onChangeCAA();
                        }
                    }

                } catch (Exception e) {
                    // Si ocurre un error se imprime y se le notifica al usuario
                    e.printStackTrace();
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("", "Ocurrio un error inesperado"));

                }
            }
        }

    }

    /**
     * Este m??todo se ejecuta cuando se acepta el cuadro de confirmaci??n al
     * eliminar
     */
    public void confirmacionAceptada() {
        filtro();
        if (deshabilitar.equals("true")) {
            // Se elimina el registro
            CAAHelper.eliminarUniAprenImparteProfe(CAAHelper.getAGUAP());
            CAAHelper.setSelecAGUAP(new UnidadaprendizajeImparteProfesor());
            CAAHelper.setAGUAP(new UnidadaprendizajeImparteProfesor());
            // Se notifica al usuario
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Eliminando", "se elimin?? correctamente"));
            mostrarSeleccionCAA();
            PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
            
            if (CAAHelper.getListaAGUAPSeleccionada().size() >= 1) {
                CAAHelper.setAGUAP(CAAHelper.getListaAGUAPSeleccionada().get(0));
                CAAHelper.setGrupo(CAAHelper.getListaAGUAPSeleccionada().get(0).getGrupoGPOid());
                CAAHelper.setProfesor(CAAHelper.getListaAGUAPSeleccionada().get(0).getProfesorPROid());
                CAAHelper.setUnidadApren(CAAHelper.getListaAGUAPSeleccionada().get(0).getUnidadAprendizajeUAPid());
                PrimeFaces.current().executeScript("PF('PF('dlg').show()");
            }
        }
    }

    /**
     *
     * M??todo para filtrar los registros de la tabla
     */
    public void filtro() {
        tienePermisos();
        if (loginBean.getSeleccionado().equalsIgnoreCase("Director") || loginBean.getSeleccionado().equalsIgnoreCase("Subdirector") || loginBean.getSeleccionado().equalsIgnoreCase("Administrador")) {
            bolSelPED = true;
        }
        listaFiltrada = CAAHelper.filtrado(busqueda);
        if (CAAHelper.getCs().size() != 0) {
            imprimir = true;
        } else {
            imprimir = false;
        }
    }

    /**
     *
     *
     * @param i
     * @return
     */
    public String toolTip(int i) {
        if (CAAHelper.getListaAGUAPSeleccionada() == null || CAAHelper.getListaAGUAPSeleccionada().size() < 1) {
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
     * M??todo para asignar el titulo al cuadro de dialogo
     *
     * @param i Opci??n
     * @return titulo del cuadro
     */
    public String dlgCabecera(int i) {
        if (i == 1) {
            header = "Asignar coordinaci??n ??rea administrativa";
            deshabilitar = "false";
            deshabilitarSubgrupo = "false";
        }
        if (i == 2) {
            header = "Eliminar coordinaci??n de unidad de aprendizaje";
            deshabilitar = "true";
            deshabilitarSubgrupo = "true";
        }
        if (i == 3) {
            header = "Modificar asignaci??n ??rea administrativa";
            deshabilitar = "false";
            deshabilitarSubgrupo = "false";
        }
        return header;
    }

    /**
     *
     * M??todo que devuelve los programas educativos que aparecen en al menos un
     * registro de coordinador de ??rea administrativa
     *
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> cargarPE() {
        listaPE = CAAHelper.consultaListaProgramaEducativo();
        return listaPE;
    }

    /**
     * M??todo para consultar todos los registros de plan de estudio
     *
     * @return Lista de planes de estudio
     */
    public List<Planestudio> cargarPlan() {
        listaPlan = CAAHelper.consultaListaPlanEstudio();
        return listaPlan;
    }

    /**
     * M??todo que obtiene los planes de estudio que estan relacionados con el
     * programa educativo que tenga el id que mandas como parametro
     *
     * @param idPE ID de un programa educativo
     * @return Lista de planes de estudio relacionados a ese programa educativo
     */
    public List<Planestudio> cargarPlanPorPE(int idPE) {
        listaPlan = CAAHelper.consultaListaPlanEstudioPorPE(idPE);
        return listaPlan;
    }

    /**
     *
     * M??todo que consulta todos los registros de ??rea de conocimiento
     *
     * @return Lista de ??reas de conocimiento
     */
    public List<Areaconocimiento> cargarAC() {
        listaAC = CAAHelper.consultaListaAreaConocimiento();
        return listaAC;
    }

    /**
     * M??todo que consulta todos los registros de unidades de aprendizaje
     *
     * @return Lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> cargarUA() {
        listaUA = CAAHelper.consultaListaUnidadAprendizaje();
        return listaUA;
    }

    /**
     *
     * M??todo que consulta todos los registros de profesores
     *
     * @return Lista de profesores
     */
    public List<Profesor> cargarProfesor() {
        listaProfesor = CAAHelper.consultaListaProfesor();
        return listaProfesor;
    }

    /**
     *
     * M??todo que consulta todos los registros de grupos
     *
     * @return Lista de grupos
     */
    public List<Grupo> cargarGrupo() {
        listaGrupo = CAAHelper.consultaListaGrupo();
        return listaGrupo;
    }

    /**
     *
     * M??todo que muestra si esta seleccionada alguna relacion de
     * UnidadAprendijaImparteProfesor
     *
     * @return true o false
     */
    public boolean mostrarSeleccionAGUAP() {
        return CAAHelper.getListaAGUAPSeleccionada() != null && CAAHelper.getListaAGUAPSeleccionada().size() > 0;
    }

    /**
     *
     * M??todo que asigna al cuadro de dialogo los profesores por el programa
     * educativo
     */
    public void filtrarPlanYProfPorPE() {
        listaPlan = CAAHelper.consultaListaPlanEstudioPorPE(CAAHelper.getProgramaEducativo().getPEDid());
        listaProfesor = CAAHelper.consultaProfesorPertenecePE(CAAHelper.getProgramaEducativo().getPEDid());
        CAAHelper.consultarAreas();
    }

    /**
     *
     * M??todo que filtra las ??reas de conocimiento por un plan de estudio
     */
    public void filtrarAreaPorPlan() {
        listaAC = CAAHelper.getAreaMismoPlan(CAAHelper.getPlanEstudio().getPESid());
    }

    /**
     *
     * M??todo que filtra las unidades de aprendizaje por un ??rea de conocimiento
     */
    public void filtrarUAPorArea() {
        System.out.println("\n\n\n Area Conocimiento: " + CAAHelper.getAreaConocimiento().getACOid() + " " + CAAHelper.getAreaConocimiento().getACOnombre());
        listaUA = CAAHelper.getUAMismaArea(CAAHelper.getAreaConocimiento().getACOid());
    }

    /**
     *
     * M??todo que carga todas las listas del cuadro de dialogo con todos los
     * registros que tiene cada campo
     */
    public void cargaDeListas() {
        cargarPE();
        cargarPlan();
        cargarAC();
        cargarUA();
        cargarProfesor();
    }

    /**
     *
     * M??todo que filtra todas las listas de una sola vez
     */
    public void filtrarListas() {
        filtrarPlanYProfPorPE();
        filtrarAreaPorPlan();
        filtrarUAPorArea();
    }

    /**
     *
     * M??todo para vaciar cada objeto que se usa para saber que registros estan
     * seleccionados
     */
    public void limpiarSeleccion() {
        CAAHelper.setListaCAASeleccionada(null);
        CAAHelper.setCoordinadorAreaAdministrativa(new Coordinadorareaadministrativa());
        CAAHelper.setProgramaEducativo(new Programaeducativo());
        CAAHelper.setCaaSeleccionado(new Coordinadorareaadministrativa());
        CAAHelper.setUnidadesAp(new ArrayList<String>());
        CAAHelper.setProfesor(new Profesor());
        CAAHelper.setArea(new Areaadministrativa());
        CAAHelper.setPlanEstudio(new Planestudio());
        CAAHelper.setAreaConocimiento(new Areaconocimiento());
        CAAHelper.setSelecCoordinadorArea(new Coordinadorareaadministrativa());
        auxCAA = new Coordinadorareaadministrativa();
        mostrarSeleccionCAA();
        botonesModElim();
        System.out.println("PRUEBAAAA");
    }

    /**
     *
     * M??todo para obtener informaci??n sobre las relaciones entre un profesor y
     * una unidad de aprendizaje
     */
    public void tipoTieneSubgrupo() {
        if (CAAHelper.getAGUAP().getUIPtipoSubgrupo().equals("C")) {
            CAAHelper.getAGUAP().setUIPsubgrupo("0");
            deshabilitarSubgrupo = "true";
        } else {
            CAAHelper.getAGUAP().setUIPsubgrupo("");
            deshabilitarSubgrupo = "false";
        }
    }

    /**
     * M??todo para asignar subgrupo en default
     *
     */
    public void subgrupoTieneTipo() {
        if (CAAHelper.getAGUAP().getUIPsubgrupo().equals("0")) {
            CAAHelper.getAGUAP().setUIPtipoSubgrupo("C");
        }
    }

    /**
     * M??todo para validar que todos los campos del registro o modificaci??n de
     * un coordiador no esten vacios antes de ejecutar la acci??n
     *
     * @return
     */
    public boolean validarCamposVacios() {
        if (auxCAA.getUnidadaprendizajeList().isEmpty()
                || CAAHelper.getProfesor().getPROid() == 0
                || CAAHelper.getArea().getAADid() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Este m??todo revisa los permisos del rol que tiene el usuario y actualiza
     * sus alcances
     */
    public void tienePermisos() {
        String roltipo = loginBean.getSeleccionado();
        loginBean.setTienePermisoCatalogo(false);
        loginBean.setPermisoAlta(false);
        loginBean.setPermisoBaja(false);
        loginBean.setPermisoModificar(false);
        loginBean.setFormano(false);
        Rol rol = CAAHelper.buscarRolPorNombre(roltipo);

        List<String> lista = CAAHelper.getListaPermisos(rol);
        for (String permiso : lista) {
            if (permiso.equalsIgnoreCase("Administraci??n de coordinador ??rea - Consulta")) {
                loginBean.setTienePermisoCatalogo(true);
                loginBean.setFormano(false);
            }
            if (permiso.equalsIgnoreCase("Administraci??n de coordinador ??rea - Modificaci??n")) {
                loginBean.setPermisoModificar(true);
            }
            if (permiso.equalsIgnoreCase("Administraci??n de coordinador ??rea - Altas")) {
                loginBean.setPermisoAlta(true);
            }
            if (permiso.equalsIgnoreCase("Administraci??n de coordinador ??rea - Eliminaci??n")) {
                loginBean.setPermisoBaja(true);
            }
        }
        if (!loginBean.isTienePermisoCatalogo()) {
            if (listaFiltrada != null) {
                listaFiltrada.clear();
            }
        }
    }

    /**
     * M??todo para obtener el string que se mostrara en la columna de nombres de
     * las unidades de aprendizaje de la tabla
     *
     * @param listaUA Lista de unidades relacionadas con el registro de
     * coordinador
     * @return String con todos los nombres de las unidades de aprendizaje
     */
    public String listarNombresUA(List<Unidadaprendizaje> listaUA) {
        String nombresOrdenados = "";
        // Se agrega nombre por nombre de cada unidad aprendizaje con un for
        for (Unidadaprendizaje UA : listaUA) {
            nombresOrdenados = nombresOrdenados + " - ( " + UA.getUAPclave() + " ) " + UA.getUAPnombre() + " -";
        }
        // Si no hay unidades se notifica con este mensaje
        if (nombresOrdenados.isEmpty()) {
            nombresOrdenados = "No hay unidades asignadas";
        }
        return nombresOrdenados;
    }

    /**
     * M??todo para obtener el string que se mostrara en la columna de nombres de
     * las unidades de aprendizaje de la tabla
     *
     * @param listaUA Lista de unidades relacionadas con el registro de
     * coordinador
     * @return String con todos los nombres de las unidades de aprendizaje
     */
    public String listarNombresUAPDF(List<Unidadaprendizaje> listaUA) {
        String nombresOrdenados = "";
        // Se agrega nombre por nombre de cada unidad aprendizaje con un for
        for (Unidadaprendizaje UA : listaUA) {
            nombresOrdenados = nombresOrdenados + " - ( " + UA.getUAPclave() + " ) " + UA.getUAPnombre() + " - \n";
        }
        // Si no hay unidades se notifica con este mensaje
        if (nombresOrdenados.isEmpty()) {
            nombresOrdenados = "No hay unidades asignadas";
        }
        return nombresOrdenados;
    }

    /**
     * sirve para saber si se debe mostrar el panel de seleccion multiple en el
     * xhtml
     *
     * @return boolean
     */
    public boolean mostrarSeleccionCAA() {
        return CAAHelper.getListaCAASeleccionada() != null && CAAHelper.getListaCAASeleccionada().size() > 0;
    }

    /**
     * Metodo que se llama al cambiar de un registro a otro cuando se haya hecho
     * una seleccion multiple
     */
    public void onChangeCAA() {
        //Se limpian todos los objetos antes de llenarlos
        CAAHelper.setCoordinadorAreaAdministrativa(new Coordinadorareaadministrativa());
        CAAHelper.setProgramaEducativo(new Programaeducativo());
        CAAHelper.setUnidadesAp(new ArrayList<String>());
        CAAHelper.setProfesor(new Profesor());
        CAAHelper.setArea(new Areaadministrativa());
        CAAHelper.setPlanEstudio(new Planestudio());
        CAAHelper.setAreaConocimiento(new Areaconocimiento());
        auxCAA = new Coordinadorareaadministrativa();

        //Se busca el coordinador de area administrativa que se haya seleccionado y se guarda 
        CAAHelper.setCoordinadorAreaAdministrativa(ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaCoordinadorAreaAdministrativaPorID(CAAHelper.getSelecCoordinadorArea().getCOAid()));
        //Se busca el profesor utilizando al coordinador de area administrativa que se guardo anteriormente
        CAAHelper.setProfesor(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(CAAHelper.getCoordinadorAreaAdministrativa().getProfesorPROid().getPROid()));
        //Se crea una lista con todas las unidades de aprendizaje 
        for (Unidadaprendizaje uap : CAAHelper.getCoordinadorAreaAdministrativa().getUnidadaprendizajeList()) {
            CAAHelper.getUnidadesAp().add(uap.getUAPid().toString());
        }
        if (loginBean.getSeleccionado().equalsIgnoreCase("Director") || loginBean.getSeleccionado().equalsIgnoreCase("Subdirector") || loginBean.getSeleccionado().equalsIgnoreCase("Administrador")) {
            CAAHelper.setProgramaEducativo(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(CAAHelper.getCoordinadorAreaAdministrativa().getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDid()));
        } else {
            CAAHelper.setProgramaEducativo(CAAHelper.getProgramaEducativo());
        }
        // Asignando el programa educativo ahora se pueden consultar las ??reas
        CAAHelper.consultarAreas();
        CAAHelper.setArea(CAAHelper.getCoordinadorAreaAdministrativa().getAreaAdministrativaAADid());
        // Se asigna tambien el ??rea de conocimiento ya conociendo el programa educativo
        CAAHelper.setAreaConocimiento(CAAHelper.consultaAreaConocimientoPorUAYPED(CAAHelper.getCoordinadorAreaAdministrativa().getUnidadaprendizajeList().get(0).getUAPid(), CAAHelper.getProgramaEducativo().getPEDid()));
        // Se asigna los planes de estudio conociendo el Progama educativo
        cargarPlanPorPE((CAAHelper.findByPlanEstudioId(CAAHelper.getAreaConocimiento().getPlanEstudioPESid().getPESid())).getProgramaEducativoPEDid().getPEDid());
        CAAHelper.setPlanEstudio(CAAHelper.findByPlanEstudioId(CAAHelper.getAreaConocimiento().getPlanEstudioPESid().getPESid()));
        noUnidadesAprendizaje = CAAHelper.getCoordinadorAreaAdministrativa().getUnidadaprendizajeList().size() + " ";
        auxCAA.setUnidadaprendizajeList(CAAHelper.getCoordinadorAreaAdministrativa().getUnidadaprendizajeList());
        //Se cargan las listas en base a los datos llenados anteriormente
        cargaDeListas();
        filtrarListas();
        cargarProfesor();
        System.out.println("   " + CAAHelper.getProfesor().getPROnombre() + CAAHelper.getProfesor().getPROapellidoMaterno());
    }

    /**
     * Metodo para actualizar las unidades de aprendizaje de un profesor
     *
     * @return
     */
    public List<Unidadaprendizaje> actualizarCoordUnidadesAP() {
        List<Unidadaprendizaje> listaUAAUX = auxCAA.getUnidadaprendizajeList();

        for (int i = 0; i < listaUA.size(); i++) {
            if (!CAAHelper.getUnidadesAp().contains(listaUA.get(i).getUAPid().toString())) {
                listaUAAUX.remove(listaUA.get(i));
            } else if (CAAHelper.getUnidadesAp().contains(listaUA.get(i).getUAPid().toString()) && !listaUAAUX.contains(listaUA.get(i))) {
                listaUAAUX.add(listaUA.get(i));
            }
        }

        return listaUAAUX;
    }

    public void metodoPrueba() {
        PrimeFaces.current().ajax().update("capdlg:unidadAp");
        actualizarCoordUnidadesAP();
        CAAHelper.getUnidadesAp().clear();
        for (int x = 0; x < auxCAA.getUnidadaprendizajeList().size(); x++) {
            CAAHelper.getUnidadesAp().add(auxCAA.getUnidadaprendizajeList().get(x).getUAPid().toString());
        }
    }

    /**
     * Ruta de la imagen del PDF
     *
     * @return
     */
    public String imagenPDF() {
        if (imprimir == true) {
            nomImg = "imagenes/pdf3.png";
        }
        if (imprimir == false) {
            nomImg = "imagenes/pdf3false.png";
        }
        return nomImg;
    }

    /**
     * Creacion del PDF
     *
     * @param document
     */
    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        //Sentencias para evitar duplicacion de tabla en PDF
        if (document != null) {
            pdf = new Document();
            pdf.setPageSize(PageSize.A4.rotate());
        }
    }

    /**
     * Genera el PDF
     *
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
        Paragraph UABC = new Paragraph("Universidad Aut??noma de Baja California", FontFactory.getFont(FontFactory.TIMES, 22, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph facultad = new Paragraph("Facultad de Ingenier??a Mexicali", FontFactory.getFont(FontFactory.TIMES, 22, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph titulo = new Paragraph("Coordinaciones de ??rea administrativa", FontFactory.getFont(FontFactory.TIMES, 18, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        Paragraph fecha = new Paragraph(fecha(), FontFactory.getFont(FontFactory.TIMES, 16, com.lowagie.text.Font.BOLD, new Color(0, 113, 65)));
        //alineacion de titulos
        UABC.setAlignment(Element.ALIGN_CENTER);
        facultad.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setIndentationRight(73);
        Paragraph esp = new Paragraph(" ");
        pdf.add(UABC);
        pdf.add(facultad);
        //se agrega la imagen del logo de UABC
        try {
            Image imagenLogo = Image.getInstance(getClass().getClassLoader().getResource("logo.png"));
            //Posicion de imagen (Horizontal, Vertical)
            imagenLogo.setAbsolutePosition(100f, 410f);
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

        pdftablecont.addCell(new Paragraph("??rea administrativa", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Programa educativo", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("No. empleado", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Coordinador de ??rea", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.addCell(new Paragraph("Unidad de aprendizaje", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
        pdftablecont.setHorizontalAlignment(15);

        float[] columnWidthsss3 = new float[]{10f, 10f, 7f, 10f, 13f};
        pdftablecont.setWidths(columnWidthsss3);
        pdf.add(pdftablecont);
        PdfPTable pdftablecont2 = new PdfPTable(5);
        //filas que contendra la tabla
        for (Coordinadorareaadministrativa item : CAAHelper.getCs()) {
            //se llena la tabla por filas
            pdftablecont2.addCell(new Paragraph(item.getAreaAdministrativaAADid().getAADnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            pdftablecont2.addCell(new Paragraph(item.getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDnombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            pdftablecont2.addCell(new Paragraph(String.valueOf(item.getProfesorPROid().getPROnumeroEmpleado()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            pdftablecont2.addCell(new Paragraph(item.getProfesorPROid().getPROnombre() + " " + item.getProfesorPROid().getPROapellidoPaterno() + " " + item.getProfesorPROid().getPROapellidoMaterno(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            pdftablecont2.addCell(new Paragraph(listarNombresUAPDF(item.getUnidadaprendizajeList()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
        }

        float[] columnWidthsss4 = new float[]{10f, 10f, 7f, 10f, 13f};
        pdftablecont2.setWidths(columnWidthsss4);
        pdf.add(pdftablecont2);
    }

    /**
     * Retorna el nombre para generar el pdf
     *
     * @return nombre tipo String
     */
    public String nombre() {
        String nombre = "Coordinadores_Area_Administrativa " + fecha();
        return nombre;
    }

    /**
     * Obtiene la fecha del sistema y da formato
     *
     * @return fecha tipo String
     */
    public String fecha() {
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        String fecha = formatoFecha.format(date);
        return fecha;
    }

    // ---------------------- GETTERS Y SETTERS ---------------------- //
    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param etiqueta Etiqueta
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Representa si un responsable de programa educativo tiene un
     * programa educatio (Si no lo tiene falla la logica)
     */
    public boolean isRpeAsignado() {
        return rpeAsignado;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param rpeAsignado Representa si un responsable de programa educativo
     * tiene un programa educatio (Si no lo tiene falla la logica)
     */
    public void setRpeAsignado(boolean rpeAsignado) {
        this.rpeAsignado = rpeAsignado;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Informaci??n de la sesi??n
     */
    public LoginBean getLoginBean() {
        return loginBean;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param loginBean Informaci??n de la sesi??n
     */
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return true o false
     */
    public boolean isMostrarPE() {
        return mostrarPE;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param mostrarPE true o false
     */
    public void setMostrarPE(boolean mostrarPE) {
        this.mostrarPE = mostrarPE;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de coordinadores filtrada
     */
    public List<Coordinadorareaadministrativa> getListaFiltrada() {
        return listaFiltrada;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaFiltrada Lista de coordinadores filtrada
     */
    public void setListaFiltrada(List<Coordinadorareaadministrativa> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Objeto de la clase helper
     */
    public CoordinadorAreaAdministrativaBeanHelper getCoordinadorAreaAdministrativaBeanHelper() {
        return CoordinadorAreaAdministrativaBeanHelper;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param CoordinadorAreaAdministrativaBeanHelper Objeto de la clase helper
     */
    public void setCoordinadorAreaAdministrativaBeanHelper(CoordinadorAreaAdministrativaBeanHelper CoordinadorAreaAdministrativaBeanHelper) {
        this.CoordinadorAreaAdministrativaBeanHelper = CoordinadorAreaAdministrativaBeanHelper;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Objeto de la clase helper
     */
    public CoordinadorAreaAdministrativaBeanHelper getCAAHelper() {
        return CAAHelper;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param CAAHelper objeto de la clase helper
     */
    public void setAAAPHelper(CoordinadorAreaAdministrativaBeanHelper CAAHelper) {
        this.CAAHelper = CAAHelper;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Variable que guarda la informaci??n del registro que el usuario
     * busca
     */
    public String getBusqueda() {
        return busqueda;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param busqueda Variable que guarda la informaci??n del registro que el
     * usuario busca
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Titulo del cuadro de dialogo
     */
    public String getHeader() {
        return header;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param header Titulo del cuadro de dialogo
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return true o false
     */
    public String getDeshabilitar() {
        return deshabilitar;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param deshabilitar true o false
     */
    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Mensaje de comfirmaci??n
     */
    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     */
    public void setMensajeConfirm() {
        if (deshabilitar.equals("true") && deshabilitarSubgrupo.equals("true")) {
            // Si esta desabilitada la modificaci??n de registro entonces
            // se esta eliminando
            mensajeConfirm = "??Est?? seguro de eliminar el registro?";
        } else {
            // Si no es modificaci??n
            mensajeConfirm = "??Est?? seguro de guardar los cambios?";
        }
        // Se actualiza el cuadro de confirmaci??n
        PrimeFaces.current().ajax().update("confirmdlgId");
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return true o false
     */
    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param deshabilitarBoton true o false
     */
    public void setDeshabilitarBoton(String deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return true o false
     */
    public String getDeshabilitarSubgrupo() {
        return deshabilitarSubgrupo;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param deshabilitarSubgrupo true o false
     */
    public void setDeshabilitarSubgrupo(String deshabilitarSubgrupo) {
        this.deshabilitarSubgrupo = deshabilitarSubgrupo;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Mensaje de validaci??n
     */
    public String getMensajeVal() {
        return mensajeVal;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param mensajeVal Mensaje de validaci??n
     */
    public void setMensajeVal(String mensajeVal) {
        this.mensajeVal = mensajeVal;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de unidades de aprendisaje
     */
    public List<Unidadaprendizaje> getListaUA() {
        return listaUA;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de Programas educativos
     */
    public List<Programaeducativo> getListaPE() {
        return listaPE;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaPE Lista de Programas educativos
     */
    public void setListaPE(List<Programaeducativo> listaPE) {
        this.listaPE = listaPE;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaUA Lista de Unidades de aprendizaje
     */
    public void setListaUA(List<Unidadaprendizaje> listaUA) {
        this.listaUA = listaUA;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de profesores
     */
    public List<Profesor> getListaProfesor() {
        return listaProfesor;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaProfesor Lista de profesores
     */
    public void setListaProfesor(List<Profesor> listaProfesor) {
        this.listaProfesor = listaProfesor;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de grupos
     */
    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaGrupo Lista de grupos
     */
    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de ??reas de conocimiento
     */
    public List<Areaconocimiento> getListaAC() {
        return listaAC;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaAC Lista de ??reas de conocimiento
     */
    public void setListaAC(List<Areaconocimiento> listaAC) {
        this.listaAC = listaAC;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Lista de planes de estudio
     */
    public List<Planestudio> getListaPlan() {
        return listaPlan;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param listaPlan lista de planes de estudio
     */
    public void setListaPlan(List<Planestudio> listaPlan) {
        this.listaPlan = listaPlan;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Una unidad aprendizaje
     */
    public Unidadaprendizaje getU() {
        return u;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param u Una unidad aprendizaje
     */
    public void setU(Unidadaprendizaje u) {
        this.u = u;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return habilita o desabilita los botones de modificar y eliminar
     */
    public boolean isBotones() {
        if (rpeAsignado == false) {
            botones = true;
        }
        return botones;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param botones habilita o desabilita los botones de modificar y eliminar
     */
    public void setBotones(boolean botones) {
        this.botones = botones;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return Coordinador seleccionado en la tabla
     */
    public Coordinadorareaadministrativa getCAAseleccionado() {
        return CAAseleccionado;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param CAAseleccionado Coordinador seleccionado en la tabla
     */
    public void setCAAseleccionado(Coordinadorareaadministrativa CAAseleccionado) {
        this.CAAseleccionado = CAAseleccionado;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @return true o false
     */
    public boolean getBolSelPed() {
        return bolSelPED;
    }

    /**
     * M??todo que permite a la p??gina web obtener los datos
     *
     * @param bolSelPED true o false
     */
    public void setBolSelPED(boolean bolSelPED) {
        this.bolSelPED = bolSelPED;
    }

    public String getNoUnidadesAprendizaje() {
        return noUnidadesAprendizaje;
    }

    public void setNoUnidadesAprendizaje(String noUnidadesAprendizaje) {
        this.noUnidadesAprendizaje = noUnidadesAprendizaje;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(Boolean imprimir) {
        this.imprimir = imprimir;
    }

    public String getNomImg() {
        return nomImg;
    }

    public void setNomImg(String nomImg) {
        this.nomImg = nomImg;
    }

}
