package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Permiso;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.RolHasPermiso;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.ui.CoordinadorAreaAdministrativaBeanUI;
import mx.avanti.siract.ui.LoginBean;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

public class CoordinadorAreaAdministrativaBeanHelper implements Serializable {

//    private LoginBean loginBeanUI;
    private ServiceFacadeLocator serviceFacadeLocator;

    private Profesor selecProfesor;
    private Coordinadorareaadministrativa selecCoordinadorArea;
    private Coordinadorareaadministrativa coordinadorAreaAdministrativa;
    private Unidadaprendizaje unidadApren;
    private Grupo grupo;
    private Profesor[] profesoresSeleccionados;
    private Unidadaprendizaje[] unidadSeleccionada;
    private Grupo[] grupoSeleccionado;
    private UnidadaprendizajeImparteProfesor imparteProfesor;
    private UnidadaprendizajeImparteProfesor selImparteProfesor;
    private CoordinadorAreaAdministrativaBeanUI CAABean;

    private Usuario usuario;
    Profesor coordinador = new Profesor();

    private Planestudio planEstudio;
    private Areaconocimiento areaConocimiento;
    private Programaeducativo programaEducativo;
    private UnidadaprendizajeImparteProfesor AGUAP;
    private UnidadaprendizajeImparteProfesor selecAGUAP;

    private List<Programaeducativo> listaProgEduc;
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Planestudio> listaPlanEstudio;
    private List<Areaconocimiento> listaAreaConocimiento;
    private List<Unidadaprendizaje> listaUnidadAprendizaje;
    private List<Unidadacademica> listaUnidadAcademica;
    private List<Grupo> listaGrupo;

    private List<UnidadaprendizajeImparteProfesor> listaAGUAPSeleccionada;

    List<Coordinadorareaadministrativa> listaCAASeleccionada;

    public List<Coordinadorareaadministrativa> getListaCAASeleccionada() {
        return listaCAASeleccionada;
    }

    public void setListaCAASeleccionada(List<Coordinadorareaadministrativa> listaCAASeleccionada) {
        this.listaCAASeleccionada = listaCAASeleccionada;

    }

    private boolean bandUA;
    private boolean bandProf;
    private boolean bandGpo;
    private boolean bandTipo;
    private boolean bandSubgpo;
    private boolean transaccion = true;

    private String mensaje;
    private String rolSeleccionado;
    private String uapSel = "";

    private int profID = 0;
    private int uapID = 0;

    private List<Coordinadorareaadministrativa> cs = new ArrayList<Coordinadorareaadministrativa>();
    private List<Profesor> listaProfesor = new ArrayList<Profesor>();
    private List<Unidadaprendizaje> listaUnidades = new ArrayList<Unidadaprendizaje>();
    private List<Unidadaprendizaje> listaUASel = new ArrayList<Unidadaprendizaje>();
    private List<String> areas = new ArrayList<String>();
    private Areaadministrativa area = new Areaadministrativa();
    ;
    private Profesor profesor = new Profesor();

    private Coordinadorareaadministrativa caaSeleccionada;
    private List<String> unidadesAp = new ArrayList<String>();
    private List<Areaadministrativa> areaadministrativas = new ArrayList<Areaadministrativa>();
    private List<Programaeducativo> programaeducativos = new ArrayList<Programaeducativo>();
    private List<Planestudio> planestudios = new ArrayList<Planestudio>();
    private List<Unidadaprendizaje> unidadaprendizajes = new ArrayList<Unidadaprendizaje>();
    private List<Profesor> profesors = new ArrayList<Profesor>();
    private Programaeducativo pedSeleccion = new Programaeducativo();
    private Planestudio pesSeleccion = new Planestudio();

    public CoordinadorAreaAdministrativaBeanHelper() {
        try {
            serviceFacadeLocator = new ServiceFacadeLocator();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        grupo = new Grupo();
        usuario = new Usuario();
        profesor = new Profesor();
        selecProfesor = new Profesor();
        planEstudio = new Planestudio();
        unidadApren = new Unidadaprendizaje();
        areaConocimiento = new Areaconocimiento();
        programaEducativo = new Programaeducativo();
        selecAGUAP = new UnidadaprendizajeImparteProfesor();
        imparteProfesor = new UnidadaprendizajeImparteProfesor();
        AGUAP = new UnidadaprendizajeImparteProfesor();
        selecCoordinadorArea = new Coordinadorareaadministrativa();
        coordinadorAreaAdministrativa = new Coordinadorareaadministrativa();
        listaCAASeleccionada = new ArrayList<Coordinadorareaadministrativa>();
    }

    public Coordinadorareaadministrativa getCoordinadorAreaAdministrativa() {
        return coordinadorAreaAdministrativa;
    }

    public void setCoordinadorAreaAdministrativa(Coordinadorareaadministrativa coordinadorAreaAdministrativa) {
        this.coordinadorAreaAdministrativa = coordinadorAreaAdministrativa;
    }

    public Coordinadorareaadministrativa getSelecCoordinadorArea() {
        return selecCoordinadorArea;
    }

    public void setSelecCoordinadorArea(Coordinadorareaadministrativa selecCoordinadorArea) {
        this.selecCoordinadorArea = selecCoordinadorArea;

    }

//    /**
//     * Método que permite a la página web obtener los datos
//     * @return Cookie de Sesión 
//     */
//    public LoginBean getLoginBeanUI() {
//        return loginBeanUI;
//    }
//    /***
//     * Método que permite a la página web obtener los datos
//     * @param loginBeanUI Cookie de Sesión 
//     */
//    public void setLoginBeanUI(LoginBean loginBeanUI) {
//        this.loginBeanUI = loginBeanUI;
//    }
    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return Profesor seleccionado
     */
    public Profesor getSelecProfesor() {
        return selecProfesor;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param selecProfesor Profesor Seleccionado
     */
    public void setSelecProfesor(Profesor selecProfesor) {
        this.selecProfesor = selecProfesor;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return una Unidad Aprendizaje
     */
    public Unidadaprendizaje getUnidadApren() {
        return unidadApren;
    }

    public Unidadaprendizaje buscarUnidadAprendizajeID(String id) {

        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(Integer.parseInt(id));
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param unidadApren una Unidad de Aprendizaje
     */
    public void setUnidadApren(Unidadaprendizaje unidadApren) {
        this.unidadApren = unidadApren;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return un Grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param grupo un Grupo
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return Arreglo de profesores seleccionados
     */
    public Profesor[] getProfesoresSeleccionados() {
        return profesoresSeleccionados;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param profesoresSeleccionados Arreglo de profesores seleccionados
     */
    public void setProfesoresSeleccionados(Profesor[] profesoresSeleccionados) {
        this.profesoresSeleccionados = profesoresSeleccionados;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return Arreglo de Unidades de Aprendizaje seleccionadas
     */
    public Unidadaprendizaje[] getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param unidadSeleccionada Arreglo de Unidades de Aprendizaje
     * seleccionadas
     */
    public void setUnidadSeleccionada(Unidadaprendizaje[] unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return Grupo seleccionado
     */
    public Grupo[] getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param grupoSeleccionado Grupo seleccionado
     */
    public void setGrupoSeleccionado(Grupo[] grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return Relación de las Unidades de Aprendizaje que imparten los
     * profesores
     */
    public UnidadaprendizajeImparteProfesor getImparteProfesor() {
        return imparteProfesor;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param imaparteProfesor Relación de las Unidades de Aprendizaje que
     * imparten los profesores
     */
    public void setImparteProfesor(UnidadaprendizajeImparteProfesor imaparteProfesor) {
        this.imparteProfesor = imaparteProfesor;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de las Unidades de Aprendizaje que imparten los profesores
     */
    public UnidadaprendizajeImparteProfesor getSelImparteProfesor() {
        return selImparteProfesor;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param selImparteProfesor List de las Unidades de Aprendizaje que
     * imparten los profesores
     */
    public void setSelImparteProfesor(UnidadaprendizajeImparteProfesor selImparteProfesor) {
        this.selImparteProfesor = selImparteProfesor;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de UnidadAprendijaImparteProfesor
     */
    public List<UnidadaprendizajeImparteProfesor> getListaAGUAPSeleccionada() {
        return listaAGUAPSeleccionada;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaAGUAPSeleccionada Lista de UnidadAprendijaImparteProfesor
     */
    public void setListaAGUAPSeleccionada(List<UnidadaprendizajeImparteProfesor> listaAGUAPSeleccionada) {
        this.listaAGUAPSeleccionada = listaAGUAPSeleccionada;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Objeto UnidadAprendijaImparteProfesor
     */
    public UnidadaprendizajeImparteProfesor getAGUAP() {
        return AGUAP;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param AGUAP Objeto UnidadAprendijaImparteProfesor
     */
    public void setAGUAP(UnidadaprendizajeImparteProfesor AGUAP) {
        this.AGUAP = AGUAP;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Objeto UnidadAprendijaImparteProfesor seleccionada
     */
    public UnidadaprendizajeImparteProfesor getSelecAGUAP() {
        return selecAGUAP;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param selecAGUAP Objeto UnidadAprendijaImparteProfesor seleccionada
     */
    public void setSelecAGUAP(UnidadaprendizajeImparteProfesor selecAGUAP) {
        this.selecAGUAP = selecAGUAP;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Un registro de programa educativo
     */
    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param programaEducativo Un registro de programa educativo
     */
    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> getListaProgEduc() {
        return listaProgEduc;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaProgEduc
     */
    public void setListaProgEduc(List<Programaeducativo> listaProgEduc) {
        this.listaProgEduc = listaProgEduc;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Un registro de área de conocimiento
     */
    public Areaconocimiento getAreaConocimiento() {
        return areaConocimiento;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param areaConocimiento Un registro de área de conocimiento
     */
    public void setAreaConocimiento(Areaconocimiento areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Un registro de plan de estudio
     */
    public Planestudio getPlanEstudio() {
        return planEstudio;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param planEstudio Un registro de plan de estudio
     */
    public void setPlanEstudio(Planestudio planEstudio) {
        this.planEstudio = planEstudio;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param mensaje Mensaje de error
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaProgramaEducativo Lista de programas educativos
     */
    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de planes de estudio
     */
    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaPlanEstudio Lista de planes de estudio
     */
    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista áreas de conocimiento
     */
    public List<Areaconocimiento> getListaAreaConocimiento() {
        return listaAreaConocimiento;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaAreaConocimiento Lista de áreas de conocimiento
     */
    public void setListaAreaConocimiento(List<Areaconocimiento> listaAreaConocimiento) {
        this.listaAreaConocimiento = listaAreaConocimiento;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> getListaUnidadAprendizaje() {
        return listaUnidadAprendizaje;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaUnidadAprendizaje Lista de unidades de aprendizaje
     */
    public void setListaUnidadAprendizaje(List<Unidadaprendizaje> listaUnidadAprendizaje) {
        this.listaUnidadAprendizaje = listaUnidadAprendizaje;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de grupos
     */
    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaGrupo Lista de grupos
     */
    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param rolSeleccionado Rol seleccionado
     */
    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Usuario logeado actualmente
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param usuario Usuario logeado actualmente
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Id de un profesor
     */
    public int getProfID() {
        return profID;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param profID Id de un profesor
     */
    public void setProfID(int profID) {
        this.profID = profID;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Id de una unidad de aprendizaje
     */
    public int getUapID() {
        return uapID;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param uapID Id de una unidad de aprendizaje
     */
    public void setUapID(int uapID) {
        this.uapID = uapID;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de coordinadores de área administrativa
     */
    public List<Coordinadorareaadministrativa> getCs() {
        return cs;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param cs Lista de coordinadores de área administrativa
     */
    public void setCs(List<Coordinadorareaadministrativa> cs) {
        this.cs = cs;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de profesores
     */
    public List<Profesor> getListaProfesor() {
        return listaProfesor;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaProfesor Lista de profesores
     */
    public void setListaProfesor(List<Profesor> listaProfesor) {
        this.listaProfesor = listaProfesor;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> getListaUnidades() {
        return listaUnidades;
    }

    /**
     * *
     * Método que permite a la página web obtener los datos
     *
     * @param listaUnidades Lista de unidades de aprendizaje
     */
    public void setListaUnidades(List<Unidadaprendizaje> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de unidades de arendizaje seleccionadas
     */
    public List<Unidadaprendizaje> getListaUASel() {
        return listaUASel;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param listaUASel Lista de unidades de aprendizaje seleccionadas
     */
    public void setListaUASel(List<Unidadaprendizaje> listaUASel) {
        this.listaUASel = listaUASel;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Lista de los nombres de áreas de conocimiento
     */
    public List<String> getAreas() {
        return areas;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param areas Lista de los nombres de áreas de conocimiento
     */
    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Una área administrativa
     */
    public Areaadministrativa getArea() {
        return area;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param area Una área administrativa
     */
    public void setArea(Areaadministrativa area) {
        this.area = area;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Un profesor
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param profesor Un profesor
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Registro de coordinador de área administrativa seleccionado
     */
    public Coordinadorareaadministrativa getCaaSeleccionada() {
        return caaSeleccionada;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param caaSeleccionada Registro de coordinador de área administrativa
     * seleccionado
     */
    public void setCaaSeleccionado(Coordinadorareaadministrativa caaSeleccionada) {
        this.caaSeleccionada = caaSeleccionada;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Una unidad de aprendizaje
     */
    public List<String> getUnidadesAp() {
        return unidadesAp;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param unidadesAp Una unidad de aprendizaje
     */
    public void setUnidadesAp(List<String> unidadesAp) {
        this.unidadesAp = unidadesAp;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return una lista de áreas administrativas
     */
    public List<Areaadministrativa> getAreaadministrativas() {
        return areaadministrativas;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param areaadministrativas una lista de áreas administrativas
     */
    public void setAreaadministrativas(List<Areaadministrativa> areaadministrativas) {
        this.areaadministrativas = areaadministrativas;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return una lista de programas educativos
     */
    public List<Programaeducativo> getProgramaeducativos() {
        return programaeducativos;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param programaeducativos una lista de programas educativos
     */
    public void setProgramaeducativos(List<Programaeducativo> programaeducativos) {
        this.programaeducativos = programaeducativos;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return una lista de planes de estudio
     */
    public List<Planestudio> getPlanestudios() {
        return planestudios;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param planestudios una lista de planes de estidio
     */
    public void setPlanestudios(List<Planestudio> planestudios) {
        this.planestudios = planestudios;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return el programa educativo seleccionado
     */
    public Programaeducativo getPedSeleccion() {
        return pedSeleccion;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param pedSeleccion el programa educativo seleccionado
     */
    public void setPedSeleccion(Programaeducativo pedSeleccion) {
        this.pedSeleccion = pedSeleccion;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return Plan de estudios seleccionado
     */
    public Planestudio getPesSeleccion() {
        return pesSeleccion;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param pesSeleccion plan de estudio seleccionado
     */
    public void setPesSeleccion(Planestudio pesSeleccion) {
        this.pesSeleccion = pesSeleccion;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return una lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> getUnidadaprendizajes() {
        return unidadaprendizajes;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param unidadaprendizajes una lista de unidades de aprendizaje
     */
    public void setUnidadaprendizajes(List<Unidadaprendizaje> unidadaprendizajes) {
        this.unidadaprendizajes = unidadaprendizajes;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return una lista de profesor
     */
    public List<Profesor> getProfesors() {
        return profesors;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return el nombre de la unidad aprendizaje seleccionada
     */
    public String getUapSel() {
        return uapSel;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param uapSel el nombre de la unidad aprendizaje seleccionada
     */
    public void setUapSel(String uapSel) {
        this.uapSel = uapSel;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @return un profesor
     */
    public Profesor getCoordinador() {
        return coordinador;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param coordinador un profesor
     */
    public void setCoordinador(Profesor coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Método que permite a la página web obtener los datos
     *
     * @param profesors una lista de profesores
     */
    public void setProfesors(List<Profesor> profesors) {
        this.profesors = profesors;
    }

    public boolean isTransaccion() {
        return transaccion;
    }

    public void setTransaccion(boolean transaccion) {
        this.transaccion = transaccion;
    }
    
    

    /**
     * Método que filtra la lista de Planes de estudio por el programa educativo
     * seleccionado
     */
    public void filtrarPlanPorPE() {
        List<Planestudio> listaAuxiliar = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
        listaPlanEstudio.clear();
        for (Planestudio aux : listaAuxiliar) {
            if (aux.getProgramaEducativoPEDid().getPEDid() == programaEducativo.getPEDid()) {
                listaPlanEstudio.add(aux);
            }
        }
    }

    /**
     * Método que filtra las listas de grupo y área de conocimiento por el plan
     * de estudio seleccionado
     */
    public void filtrarAreaYGpoPorPlan() {
        List<Areaconocimiento> listaAuxiliarAC = ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();
        listaAreaConocimiento.clear();
        for (Areaconocimiento aux : listaAuxiliarAC) {
            if (aux.getPlanEstudioPESid().getPESid() == planEstudio.getPESid()) {
                listaAreaConocimiento.add(aux);
            }
        }
        List<Grupo> listaAuxiliarGRP = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
        listaGrupo.clear();
        for (Grupo aux : listaAuxiliarGRP) {
            if (aux.getPlanEstudioPESid().getPESid() == planEstudio.getPESid()) {
                listaGrupo.add(aux);
            }
        }
    }

    /**
     * Método que filtra la lista de unidades de aprendizaje por el área de
     * conocimiento seleccionada
     */
    public void filtrarUAPorArea() {
        List<Unidadaprendizaje> listaAuxiliar = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().buscarUnidadesDeAprendizaje();
        listaUnidadAprendizaje.clear();
        for (Unidadaprendizaje aux : listaAuxiliar) {
            for (Areaconocimiento ac : aux.getAreaconocimientoList()) {
                if (ac.getACOid() == areaConocimiento.getACOid()) {
                    listaUnidadAprendizaje.add(aux);
                }
            }
        }
    }

    /**
     * Método para filtrar un registro por palabra clave por todos sus campos
     *
     * @param busqueda cadena que se va a usar para filtrar
     * @return
     */
    public List<Coordinadorareaadministrativa> filtrado(String busqueda) {
        List<Coordinadorareaadministrativa> listaFiltrada2 = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
        listaFiltrada2.clear();
        // Se cambia la palabra por la misma en letras mayúsculas
        String cambioBus = busqueda.toLowerCase();
        String cambioObj = "";
        // Se les asignan a todas las listas todos sus registros
        cargarTodo();
        if (programaEducativo.getPEDid() != null && !programaEducativo.getPEDid().equals(0)) {
            // Primero se filtran las áreas administrativas por el programa educativo
            List<Areaadministrativa> areaadministrativas = new ArrayList<Areaadministrativa>();
            for (Areaadministrativa aux : ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaGeneralAreaAdministrativa()) {
                if (aux.getProgramaEducativoPEDid().getPEDid() == programaEducativo.getPEDid()) {
                    areaadministrativas.add(aux);
                }
            }
            Areaadministrativa AA = new Areaadministrativa();
            // Se consulta todos los registros de coordinador de área administrativa
            cs = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
            List<Coordinadorareaadministrativa> coordinadorareaadministrativas = new ArrayList<Coordinadorareaadministrativa>();
            // Se filtran los registros de coordinador de área administrativa
            // por área administrativa
            for (Areaadministrativa area : areaadministrativas) {
                for (int i = 0; i < cs.size(); i++) {
                    if (cs.get(i).getAreaAdministrativaAADid().getAADid() == area.getAADid()) {
                        coordinadorareaadministrativas.add(cs.get(i));
                    }
                }
            }
            // Se asigna los registros de coordinador filtrados a la lista que 
            // se mostrará en la página
            setCs(coordinadorareaadministrativas);
        }
        // Se revisa si la palabra que se esta buscando se encuentra en alguno
        // de los campos que tiene coordinador de área administrativa
        if (busqueda.trim().length() > 0) {
            listaFiltrada2.clear();
            for (Coordinadorareaadministrativa caa : cs) {
                cambioObj = caa.getAreaAdministrativaAADid().getAADnombre().toLowerCase();
                if (cambioObj.startsWith(cambioBus)) {
                    listaFiltrada2.add(caa);
                } else {
                    cambioObj = caa.getProfesorPROid().getPROnombre().toLowerCase();
                    if (cambioObj.startsWith(cambioBus)) {
                        listaFiltrada2.add(caa);
                    } else {
                        cambioObj = Integer.toString(caa.getProfesorPROid().getPROnumeroEmpleado());
                        if (cambioObj.startsWith(cambioBus)) {
                            listaFiltrada2.add(caa);
                        } else {
                            cambioObj = caa.getAreaAdministrativaAADid().getProgramaEducativoPEDid().getPEDnombre().toLowerCase();
                            if (cambioObj.startsWith(cambioBus)) {
                                listaFiltrada2.add(caa);
                            } else {
                                for (Unidadaprendizaje ua : caa.getUnidadaprendizajeList()) {
                                    cambioObj = ua.getUAPnombre().toLowerCase();
                                    if (cambioObj.startsWith(cambioBus)) {
                                        listaFiltrada2.add(caa);
                                    } else {
                                        cambioObj = Integer.toString(ua.getUAPclave());
                                        if (cambioObj.startsWith(cambioBus)) {
                                            listaFiltrada2.add(caa);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Se asigna los registros de coordinador filtrados por segunda vez 
            // a la lista que se mostrará en la página
            cs = listaFiltrada2;
        }
        return listaFiltrada2;
    }

    /**
     * Se filtran los registros de unidad aprendizaje imparte profesor
     */
    public void seleccionarRegistro() {
        // Se busca el registro de UnidadAprendizaje imparte profesor
        for (UnidadaprendizajeImparteProfesor aguap : ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor()) {
            if (aguap.getUIPid().equals(selecAGUAP.getUIPid())) {
                AGUAP = aguap;
                // Se asignan los registros que contiene el objeto
                unidadApren = aguap.getUnidadAprendizajeUAPid();
                profesor = aguap.getProfesorPROid();
                grupo = aguap.getGrupoGPOid();
            }
        }
    }

    /**
     * Este método revisa que los registros de unidad imparte profesor no esten
     * repetidos
     *
     * @return
     */
    public String validarRepetidos() {
        bandUA = true;
        bandProf = true;
        bandGpo = true;
        bandTipo = true;
        bandSubgpo = true;
        mensaje = "";
        // Se revisa si el registro esta repetido
        for (UnidadaprendizajeImparteProfesor aguap : ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor()) {
            if (aguap.getGrupoGPOid().getGPOid().equals(grupo.getGPOid())
                    && aguap.getUIPsubgrupo().equals(AGUAP.getUIPtipoSubgrupo())) {
                bandGpo = false;
                // Se mostrará este mensaje si lo esta
                mensaje = "Profesor ya asignado";
            }
        }
        System.out.println("BEAN HELPER mensaje = " + mensaje);
        return mensaje;
    }

    /**
     * Este método identifica el rol que tiene el usuario y adapta el catalogo
     * solo con los registros que puede ver con el programa educativo
     */
    public void getUsuarioTienePE() {
        // Se consultan todos los registros de programa educativo que hay
        listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador")) {
            // Si el rol del usuario es director, subdirector o administrador 
            // Se obtiene el objeto Profesor que tiene el usuario
            profesor = usuario.getProfesorList().get(0);
            listaUnidadAcademica = profesor.getUnidadacademicaList();
            listaProgEduc = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
            for (Unidadacademica uac : listaUnidadAcademica) {
                for (Programaeducativo pe : listaProgEduc) {
                    if (uac.getUACid() == pe.getUnidadAcademicaUACid().getUACid()) {
                        listaProgramaEducativo.add(pe);
                    }
                }
            }
        }
    }

    /**
     * Se asignan todos los registros de Programa educativo
     */
    public void cargarPED() {
        programaeducativos = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
    }

    /**
     * Se asignan todos los registros de Plan educativo
     */
    public void cargarPES() {
        planestudios = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
    }

    /**
     * Se asignan todos los registros de Unidades aprendizajes
     */
    public void cargarUnidades() {
        unidadaprendizajes = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().buscarUnidadesDeAprendizaje();
    }

    /**
     * Se asignan todos los registros de Profesores
     */
    public void cargarProfesores() {
        profesors = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
    }

    /**
     * Este metodo agisna todos los registro de los campos que conforman un
     * coordinador de área administrativa
     */
    public void cargarTodo() {
        cargarPED();
        cargarPES();
        cargarProfesores();
        cargarUnidades();
        consultarAreasAdministrativas();
    }

    /**
     *
     */
    public void consultarAreasAdministrativas() {
        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador")) {
            // Si el rol es un administrador, director o subdirector se le 
            // permitira el acceso a todos los registros
            cs = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
        } else {
            // Se obtiene el objeto de profesor
            Profesor profesorLog = usuario.getProfesorList().get(0);
            if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")) {
                // Si el rol es responsable de programa educativo
                try {
                    // Se obtiene el programa educativo al que pertenece
                    setProgramaEducativo((profesorLog.getProfesorPerteneceProgramaeducativoList().get(0)).getProgramaeducativo());
                } catch (Exception e) {
                    // Si se genero un error se asigna el tronco comun como default    
                    setProgramaEducativo(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(37));
                }
                // Se obtienen las áreas administrativas relacionadas con ese programa
                List<Areaadministrativa> areaadministrativas = programaEducativo.getAreaadministrativaList();
                cs = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
                List<Coordinadorareaadministrativa> coordinadorareaadministrativas = new ArrayList<Coordinadorareaadministrativa>();
                // Se añaden los registros de cordinador que contienen las áreas
                // administrativas de la lista
                for (Areaadministrativa a1 : areaadministrativas) {
                    for (int i = 0; i < cs.size(); i++) {
                        if (cs.get(i).getAreaAdministrativaAADid().getAADid() == a1.getAADid()) {
                            coordinadorareaadministrativas.add(cs.get(i));
                        }
                    }
                }
                // Se añaden los registros a la tabla
                setCs(coordinadorareaadministrativas);
            } else {
                if (rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
                    try {
                        // Se obtiene el programa educativo al que pertenece
                        setProgramaEducativo(profesorLog.getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid());
                    } catch (Exception e) {
                        // Si se genero un error se asigna el tronco comun como default    
                        setProgramaEducativo(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(37));
                    }
                    // Se obtienen las áreas administrativas relacionadas con ese programa
                    List<Areaadministrativa> areaadministrativas = programaEducativo.getAreaadministrativaList();
                    cs = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
                    List<Coordinadorareaadministrativa> coordinadorareaadministrativas = new ArrayList<Coordinadorareaadministrativa>();
                    for (Areaadministrativa a1 : areaadministrativas) {
                        for (int i = 0; i < cs.size(); i++) {
                            if (cs.get(i).getAreaAdministrativaAADid().getAADid() == a1.getAADid()) {
                                coordinadorareaadministrativas.add(cs.get(i));
                            }
                        }
                    }
                    // Se actializa la tabla
                    setCs(coordinadorareaadministrativas);
                }
            }
        }
    }

    /**
     * Se consultan todos los registros de todos los campos para mostrarlos en
     * la vista
     */
    public void consultarACA() {
        cs = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
        areaadministrativas = ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaGeneralAreaAdministrativa();
        consultarProfesores();
        consultarUnidadesAp();
        consultarAreas();
    }

    /**
     * Se asignan todos los registros de profesor
     */
    public void consultarProfesores() {
        listaProfesor = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
    }

    /**
     * Se asignan todos los registros de unidad aprendizaje
     */
    public void consultarUnidadesAp() {
        listaUnidades = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().buscarUnidadesDeAprendizaje();
    }

    /**
     * Método que reacciona al click de guardar
     */
    public void onClickSubmit() {
        if (CoordinadorAreaAdministrativaBeanUI.header.equalsIgnoreCase("Asignar Coordinación Área Administrativa")) {
            // Si se esta agregando un coordinador de área administrativa
            // se crea un registro vacio de coordinador
            Coordinadorareaadministrativa c = new Coordinadorareaadministrativa();
            try {
                // Se agregan los campos que ingreso el usuario
                c.setUnidadaprendizajeList(unidadaprendizajes);
                c.setAreaAdministrativaAADid(getArea());
                c.setProfesorPROid(profesor);
                ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().agregarCoordinadorAreaAdministrativa(c);
            } catch (Exception e) {
                // Si se produce un error de muestra un mensaje al usuario
                e.printStackTrace();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Este profesor ya esta asignado al mismo grupo");
//                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
            // Se limpian los campos en la vista
            c = new Coordinadorareaadministrativa();
            setArea(new Areaadministrativa());
            unidadesAp = new ArrayList<String>();
            setProfesor(new Profesor());
            // Se actualiza la tabla
            consultarAreasAdministrativas();
        } else if (CoordinadorAreaAdministrativaBeanUI.header.equalsIgnoreCase("Modificar Asignación Área Administrativa")) {
            try {
                /* Si se esta modificando un coordinador de área administrativa
                * Se obtiene el coordinador de área administrativa que se esta
                * modificando
                 */
                Coordinadorareaadministrativa c = getCaaSeleccionada();
                // ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().eliminar(getCaaSeleccionada());
                // se agregan todos los datos nuevos al registro
                c.setProfesorPROid(getProfesor());
                c.setUnidadaprendizajeList(getUnidadaprendizajes());
                c.setAreaAdministrativaAADid(area);
                // Se actualizan los datos del registro seleccionado
                setCaaSeleccionado(c);
                // Se hace la modificación
                ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().modificarCoordinadorAreaAdministrativa(getCaaSeleccionada());
                // Se limpian todos los campos
                setProfesor(new Profesor());
                c = new Coordinadorareaadministrativa();
                // Se actualiza la tabla
                consultarAreasAdministrativas();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Este método guarda un registro de coordinador de área administrativa solo
     * con un área administrativa
     */
    public void guardar() {
        Coordinadorareaadministrativa c = new Coordinadorareaadministrativa();
        areaadministrativas = ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaGeneralAreaAdministrativa();
        c.setAreaAdministrativaAADid(areaadministrativas.get(0));
        ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().agregarCoordinadorAreaAdministrativa(c);
    }

    /**
     * Este método reacciona al click sobre un registro de la tabla
     *
     * @param event contexto de la acción del usuario
     */
    public void seleccion(SelectEvent event) {
        Coordinadorareaadministrativa c = new Coordinadorareaadministrativa();
        // Se parsea el dato que se selecciono de la tabla a un objeto de
        // coordinador de área administrativa
        c = (Coordinadorareaadministrativa) event.getObject();
        // Se ágrega el registro al objeto seleccionado
        setCaaSeleccionado(c);
    }

    /**
     * Este método reacciona al aceptar el mensaje de confirmación para eliminar
     */
    public void eliminarCAA() {
        // Se obtiene el coordinador de área administrativa que se va a eliminar
        Coordinadorareaadministrativa c = getCaaSeleccionada();
        // Se elimina el registro
        ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().eliminarCoordinadorAreaAdministrativa(c);
        // Se asigna un objeto vacio al registro seleccionado
        c = new Coordinadorareaadministrativa();
        setCaaSeleccionado(new Coordinadorareaadministrativa());
        // Se actualiza la tabla
        consultarAreasAdministrativas();
    }

    /**
     * Método que asigna todos los registros a la tabla
     */
    public void consultarAreas() {
        // Se traen todos los registros de  área administrativa
        List<Areaadministrativa> CAA = ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaGeneralAreaAdministrativa();
        areaadministrativas = new ArrayList<Areaadministrativa>();
        for (int i = 0; i < CAA.size(); i++) {
            // Se revisa registro por registro 
            if (CAA.get(i).getProgramaEducativoPEDid().getPEDid() == getProgramaEducativo().getPEDid()) // Si los programas educativos son los mismo se agrega a la 
            // lista de áreas administrativas
            {
                areaadministrativas.add(CAA.get(i));
            }
        }
    }

    /**
     * Este método es para encontrar un plan de estudio especifico
     *
     * @param idPLE ID del plan de estudio que se busca
     * @return Plan de estudio especifico
     */
    public Planestudio findByPlanEstudioId(int idPLE) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(idPLE);
    }

    /**
     * Método para eliminar un coordinador de área administrativa
     *
     * @param baja Registro que se va a eliminar
     */
    public void eliminarAsignacion(Coordinadorareaadministrativa baja) {
        if(ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().eliminarCoordinadorAreaAdministrativa(baja)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("", "Se elimino correctamente"));
            setTransaccion(true);
        } else {
            setTransaccion(false);
            PrimeFaces.current().executeScript("PF('confirmTrans').show()");
        }
    }

    /**
     * Método para buscar un registro especifico de Unidad aprendizaje
     *
     * @param idUA id de la unidad aprendizaje que se esta buscando
     * @return La unidad aprendizaje especifica
     */
    public Unidadaprendizaje findUAById(int idUA) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(idUA);
    }

    /**
     * Método para agregar un nuevo registro de coordinador de área
     * administrativa
     *
     * @param registro registro nuevo que contiene todos los campos
     */
    public void asignarAreaAdministrativa(Coordinadorareaadministrativa registro) {
            if(ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().agregarCoordinadorAreaAdministrativa(registro)){
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                setTransaccion(true);
            } else{
                PrimeFaces.current().executeScript("PF('confirmTrans').show()");
                setTransaccion(false);
            }

    }

    /**
     * Médoto para eliminar la relación de eliminar unidad de aprendizaje
     * imparte profesor
     *
     * @param baja
     */
    public void eliminarUniAprenImparteProfe(UnidadaprendizajeImparteProfesor baja) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().eliminarUniAprenImparteProfe(baja);
    }

    /**
     * Método para consultar todos los registros de programa educativo
     *
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> consultaListaProgramaEducativo() {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
    }

    /**
     * Método para consultar todos los planes de estudio que pertenecen a un
     * programa educativo
     *
     * @param idPE Id del programa educativo del que se consultaran los planes
     * de estudio
     * @return Lista de planes de estudio
     */
    public List<Planestudio> consultaListaPlanEstudioPorPE(int idPE) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(idPE);
    }

    /**
     * Método para consultar todos los registros de plan de estudio
     *
     * @return Lista de planes de estudio
     */
    public List<Planestudio> consultaListaPlanEstudio() {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
    }

    /**
     * Método para consultar los regostros de área de conocimiento
     *
     * @return Lista de áreas de conocimiento
     */
    public List<Areaconocimiento> consultaListaAreaConocimiento() {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();
    }

    /**
     * Método para consultar registros de profesores ya asignados a una unidad
     * de aprendizaje
     *
     * @return Lista de profesores
     */
    public List<Profesor> consultaListaProfesor() {
        return ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
    }

    /**
     * Método para consultar una lista de grupos
     *
     * @return Lista de grupos
     */
    public List<Grupo> consultaListaGrupo() {
        return ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
    }

    /**
     * Método para consultar todas los registros de unidad de aprendizaje
     *
     * @return Lista de unidades aprendizaje
     */
    public List<Unidadaprendizaje> consultaListaUnidadAprendizaje() {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().buscarUnidadesDeAprendizaje();
    }

    /**
     * Método para buscar un registro especifico de plan de estudio
     * @param idPLE
     * @return
     */
    public Planestudio buscarPlanEstudio(int idPLE) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(idPLE);
    }

    /**
     * Método para obtener todos los registros de profesores relacionados a un
     * programa educativo
     *
     * @param idPed ID de un programa educativo
     * @return Lista de profesores relacionados con el programa educativo al que
     * le pertenece el ID
     */
    public List<Profesor> consultaProfesorPertenecePE(int idPed) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorPE(idPed);

    }

    /**
     * Método que regresa la lista de areas de conocimiento que estan
     * relacionadas a un plan de estudios especifico
     *
     * @param idPlan Id del plan de estudios
     * @return Lista de áreas de conocimiento
     */
    public List<Areaconocimiento> getAreaMismoPlan(int idPlan) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(idPlan).getAreaconocimientoList();
    }

    /**
     * Método que regresa la lista de unidades de aprendizaje que estan
     * relacionadas a un área de conocimiento especifica
     *
     * @param idAC Id del área de conocimiento del que se va buscar la lista
     * @return Lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> getUAMismaArea(int idAC) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(idAC).getUnidadaprendizajeList();
    }

    /**
     * Método que regresa la lista de profesores que estan relacionadas con un
     * área administrativa especifica
     *
     * @param idArea Id de un área administrativa
     * @return Lista de profesores
     */
    public List<Profesor> consultaProfesorPorCoordinadorAreaAdministrativa(int idArea) {
        // Se obtienen los registros de coordinador el área encontrada
        List<Coordinadorareaadministrativa> coordinadores = ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaAreaAdministrativaPorId(idArea).getCoordinadorareaadministrativaList();
        List<Profesor> profesores = new ArrayList<Profesor>();
        for (Coordinadorareaadministrativa coordinador : coordinadores) {
            profesores.add(coordinador.getProfesorPROid());
        }
        if (profesores.size() > 0) {
            return profesores;
        } else {
            return null;
        }
    }

    /**
     * Método que regresa la lista de áreas administrativas que estan
     * relacionadas con un profesor especifico
     *
     * @param idProfresor Id del profesor
     * @return Lista de áreas administrativas
     */
    public List<Areaadministrativa> consultaAreaAdministrativaPorCoordinadorAreaAdministrativa(int idProfresor) {
        List<Coordinadorareaadministrativa> coordinadores = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(idProfresor).getCoordinadorareaadministrativaList();
        List<Areaadministrativa> areas = new ArrayList<Areaadministrativa>();
        for (Coordinadorareaadministrativa coordinador : coordinadores) {
            areas.add(coordinador.getAreaAdministrativaAADid());
        }
        return areas;
    }

    /**
     * Método que encuentra un programa educativo especifico
     *
     * @param idRPE Id del programa educativo
     * @return Programa educativo especifico
     */
    public Programaeducativo findProgramaEducativoById(int idRPE) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idRPE);
    }

    /**
     * Método que regresa todos los registros de coordinador de área
     * administrativa
     *
     * @return Lista de coordinadores de área administrativa
     */
    public List<Coordinadorareaadministrativa> consultaAsignaciones() {
        return ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
    }

    public Areaadministrativa consultarAreaadministrativa(int id) {
        return ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaAreaAdministrativaPorId(id);
    }

    public Coordinadorareaadministrativa consultarCoordinacion(int id) {
        return ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaCoordinadorAreaAdministrativaPorID(id);
    }

    public Profesor consultarProfesor(int id) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(id);
    }

    /**
     * Método para consulta un área de conocimiento especifica con el id de una
     * unidad aprendizaje que tenga y el id del programa educativo al que
     * pertenece
     *
     * @return Área de conocimiento
     */
    public Areaconocimiento consultaAreaConocimientoPorUAYPED(int uaid, int pedid) {
        Areaconocimiento ac = new Areaconocimiento();
        ac.setACOid(-1);
        for (Areaconocimiento aux : ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento()) {
            if (aux.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid() == pedid) {
                // si tiene el programa educativo que se busca
                for (Unidadaprendizaje ua : aux.getUnidadaprendizajeList()) {
                    if (ua.getUAPid() == uaid) {
                        // Si tiene la unidad aprendizaje que se busca
                        // Se asigna para regresarlo
                        ac = aux;
                        break;
                    }
                }
            }
        }
        return ac;
    }

    /**
     * Método para actualizar un registro de coordinador de área administrativa
     *
     * @param registro registro actualizado que contiene todos los campos
     */
    public void modificarAsignacionAreaAdministrativa(Coordinadorareaadministrativa registro) {
        if(ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().modificarCoordinadorAreaAdministrativa(registro)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("", "Se modificó correctamente"));
            setTransaccion(true);
        } else {
            setTransaccion(false);
            PrimeFaces.current().executeScript("PF('confirmTrans').show()");
        }
    }

    /**
     * Este método convierte la lista de permisos y subpermisos de un rol en una
     * cadena string
     *
     * @param rolSeleccionado Rol seleccionado
     * @return Lista de los permisos y subpermisos
     */
    public List<String> getListaPermisos(Rol rolSeleccionado) {
        // Se vacia la lista actual de permisos y subpermisos
        List<String> listaPermisos = new ArrayList();
        if (rolSeleccionado.getROLid() != null) {
            // Si hay un rol seleccionado se revisa los permisos que tiene
            for (RolHasPermiso RHP : rolSeleccionado.getRolHasPermisoList()) {
                listaPermisos.add(RHP.getPermiso().getPERtipo() + " - " + RHP.getSubpermisos().getSPERtipo());
            }
        } else {
            // Si no hay un rol seleccionado entonces se ponen los nombres de 
            // solo los permisos
            List<Permiso> recibe = ServiceFacadeLocator.getInstancePermisoFacade().consultaGeneralPermiso();
            for (Permiso perm : recibe) {
                listaPermisos.add(perm.getPERtipo());
            }
        }
        return listaPermisos;
    }

    /**
     * Método que busca un rol en especifico por el nombre
     *
     * @param busqueda String con la cadena que se bucará en el nombre
     * @return Rol que coinciden
     */
    public Rol buscarRolPorNombre(String busqueda) {
        // Se crea el registro que va a devolver el metodo
        Rol rol = new Rol();
        // Se revisa registro por registro si el tol contiene la palabra que se busca
        for (Rol rolRegistrado : ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol()) {
            if (rolRegistrado.getROLtipo().equalsIgnoreCase(busqueda)) {
                rol = rolRegistrado;
            }
        }
        return rol;
    }

}
