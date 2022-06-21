package mx.avanti.siract.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.avanti.siract.entity.Permiso;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.RolHasPermiso;
import mx.avanti.siract.entity.Subpermisos;
import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.helper.LoginBeanHelper;
import mx.avanti.siract.util.MyUtil;
//import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

/**
 * LISTA DE ROLES
 * 
 * ID 1 = ADMINISTRADOR
 * ID 2 = DIRECTOR
 * ID 3 = SUBDIRECTOR
 * ID 4 = COORDINADOR AREA DE CONOCIMIENTO
 * ID 5 = COORDINADOR DE FORMACION BASICA
 * ID 6 = RESPONSABLE DE PROGRAMA EDUCATIVO
 * ID 7 = PROFESOR
 * ID 8 = ALUMNO
 * 
 * @author Misael
 */

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 8023458473606140846L;
    
    private List<Rol> roles;
    private List<Rol> listaRol = new ArrayList();
    private String rolmenu;
    private String rol;
    private Usuario logueado;
    private String seleccionado;
    private boolean permisoAlta = false;
    private boolean permisoBaja = false;
    private boolean permisoModificar = false;
    private boolean formano = true;
    private boolean TienePermiso = true;
    private boolean tienePermisoCatalogo = false;
    private boolean selectRol = true;
    boolean banderaUnavez = false;
    private int idGlobal;
    private List<Rol> obtenerListaRoles;
    private String boton = "true";
    private List<String> obtenerRoles;//aqui se guardan los roles que seleccione en la alta al usuario
    private String rolSeleccionado = "0";
    private LoginBeanHelper logBeanHelp = new LoginBeanHelper();
    private MenuModel model;
    private List<Rol> list = null;        
    
    Usuario u = new Usuario();    
    LoginBean lb;

    public List<Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public String getRolmenu() {
        return rolmenu;
    }

    public void setRolmenu(String rolmenu) {
        this.rolmenu = rolmenu;
    }

    public List<Rol> getObtenerListaRoles() {
        return obtenerListaRoles;
    }

    public void setObtenerListaRoles(List<Rol> obtenerListaRoles) {
        this.obtenerListaRoles = obtenerListaRoles;
    }

    public List<String> getObtenerRoles() {
        return obtenerRoles;
    }

    public void setObtenerRoles(List<String> obtenerRoles) {
        this.obtenerRoles = obtenerRoles;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public List<Rol> getList() {
        return list;
    }

    public void setList(List<Rol> list) {
        this.list = list;
    }

    public boolean isBanderaUnavez() {
        return banderaUnavez;
    }

    public void setBanderaUnavez(boolean banderaUnavez) {
        this.banderaUnavez = banderaUnavez;
    }

    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }

    public String getListStrRoles() {
        return listStrRoles;
    }

    public void setListStrRoles(String listStrRoles) {
        this.listStrRoles = listStrRoles;
    }                
    
    /**
     *
     * @return
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     *
     * @return
     */
    public boolean isSelectRol() {
        return selectRol;
    }

    /**
     *
     * @param selectRol
     */
    public void setSelectRol(boolean selectRol) {
        this.selectRol = selectRol;
    }

    /**
     *
     * @return
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     *
     * @return
     */
    public Usuario getLogueado() {
        return logueado;
    }

    /**
     *
     * @param logueado
     */
    public void setLogueado(Usuario logueado) {
        this.logueado = logueado;
    }

    /**
     *
     * @return
     */
    public String getRolMenu() {
        return rolmenu;
    }

    /**
     *
     * @param rolmenu
     */
    public void setRolMenu(String rolmenu) {
        this.rolmenu = rolmenu;
    }
    
    /**
     *
     * @return
     */
    public String getUsuariose() {

        return usuariose;
    }

    /**
     *
     * @param usuariose
     */
    public void setUsuariose(String usuariose) {
        this.usuariose = usuariose;
    }
    String usuariose;

    int idUsu = -1;

    Rol rolObj = new Rol();

    private String listStrRoles = "No tiene roles";

    /**
     *
     * @return
     */
    public LoginBeanHelper getLogBeanHelp() {
        return logBeanHelp;
    }
    

    // COMPONENTES DE DISENO
    DefaultMenuItem MenuRACT;
    DefaultMenuItem OpcAvanceContenido;
    DefaultMenuItem OpcConsultaRACTS;
    DefaultSubMenu MenuUsuarios;
    DefaultMenuItem MenuAvanceContenidoTematico;
    DefaultMenuItem MenuConfiguracion;
    DefaultSubMenu MenuCatalogos;
    DefaultMenuItem MenuReportes;
    DefaultMenuItem MenuSemaforos;
    DefaultMenuItem MenuPoliticasP;
    DefaultMenuItem MenuPoliticasA;
    DefaultMenuItem MenuPoliticasConsulta;
    DefaultSubMenu MenuPoliticas;
    DefaultMenuItem OpcAceptarPolitica;
    DefaultMenuItem OpcConsultaPolitica;
    DefaultMenuItem OpcAltaPolitica;
    DefaultMenuItem OpcSemaforos;
    DefaultMenuItem OpcReportes;
    DefaultMenuItem OpcUsuarios;
    DefaultMenuItem OpcRoles;
    DefaultMenuItem OpcRealizar;
    DefaultMenuItem OpcConfiguracion;
    DefaultMenuItem OpcAdmonUnidadAcademica;
    DefaultMenuItem OpcAdmonProgramaE;
    DefaultMenuItem OpcAdmonAreaC;
    DefaultMenuItem OpcAdmonUnidadAprendizaje;
    DefaultMenuItem OpcAdmonProfesores;
    DefaultMenuItem OpcAdmonContenidoT;
    DefaultMenuItem OpcAdmonGrupo;
    DefaultMenuItem OpcAsignarGrupo;
    DefaultMenuItem OpcAdmonCampus;
 // DefaultMenuItem OpcAdmonCiclo;
    DefaultMenuItem OpcAdmonPlanEstudios;
    DefaultMenuItem OpcAreaConAdm;
    DefaultMenuItem OpcCoorAreaConAdm;
    DefaultMenuItem OpcResProgEd;
 //   DefaultMenuItem OpcCriteriosProfesor;
    // FIN COMPONENTES DE DISENO

    /**
     * CONSTRUCTOR
     */
    public LoginBean() {
        super();

        if (this.logueado == null) {
            this.logueado = new Usuario();
        }
    }

    public int getIdGlobal() {
        return idGlobal;
    }

    public void setIdGlobal(int idGlobal) {
        this.idGlobal = idGlobal;
    }
    
    /**
     * Metodo para obtener la cadena del rol actual.
     * @return Cadena con el nombre del rol.
     */
    public String getSeleccionado() {
        return seleccionado;
    }

    /**
     * Metodo para establecer cadena del rol.
     * @param seleccionado
     */
    public void setSeleccionado(String seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isTienePermisoCatalogo() {
        return tienePermisoCatalogo;
    }

    public void setTienePermisoCatalogo(boolean tienePermisoCatalogo) {
        this.tienePermisoCatalogo = tienePermisoCatalogo;
    }
    
    

    /**
     *
     * @return
     */
    public String getRol() {
        return rol;
    }

    /**
     *
     * @param rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     *
     * @return
     */
    public boolean isFormano() {
        return formano;
    }

    /**
     *
     * @param formano
     */
    public void setFormano(boolean formano) {
        this.formano = formano;
    }

    /**
     *
     * @return
     */
    public boolean isResetdatos() {
        return resetdatos;
    }

    /**
     *
     * @param resetdatos
     */
    public void setResetdatos(boolean resetdatos) {
        this.resetdatos = resetdatos;
    }

    boolean resetdatos = false;

    /**
     *
     * @return
     */
    public boolean isPermisoAlta() {
        return permisoAlta;
    }

    /**
     *
     * @param permisoAlta
     */
    public void setPermisoAlta(boolean permisoAlta) {
        this.permisoAlta = permisoAlta;
    }

    /**
     *
     * @return
     */
    public boolean isPermisoBaja() {
        return permisoBaja;
    }

    /**
     *
     * @param permisoBaja
     */
    public void setPermisoBaja(boolean permisoBaja) {
        this.permisoBaja = permisoBaja;
    }

    /**
     *
     * @return
     */
    public boolean isPermisoModificar() {
        return permisoModificar;
    }

    /**
     *
     * @param permisoModificar
     */
    public void setPermisoModificar(boolean permisoModificar) {
        this.permisoModificar = permisoModificar;
    }

    /**
     * 
     * @return
     */
    public boolean isTienePermiso() {
        if (TienePermiso == true) {
            formano = false;
            setFormano(false);
        }
        return TienePermiso;
    }
    
    /**
     *
     * @return
     */
    public String getBoton() {
        return boton;
    }

    /**
     *
     * @param boton
     */
    public void setBoton(String boton) {
        this.boton = boton;
    }

    public void inicializarMenu() {
        MenuRACT = new DefaultMenuItem("Consulta avance contenido tematico");
        OpcAvanceContenido = new DefaultMenuItem("Avance contenido temático");
        OpcConsultaRACTS = new DefaultMenuItem("Consulta avance contenido temático");
        MenuRACT.setOutcome("consultaract");

        OpcAvanceContenido.setOutcome("ract");
        OpcConsultaRACTS.setOutcome("consultaract");
        MenuRACT.setRendered(false);
        MenuRACT.setIcon("fa fa-fw fa-edit");
        OpcAvanceContenido.setRendered(false);
        OpcConsultaRACTS.setRendered(false);
        OpcAvanceContenido.setIcon("fa fa-fw fa-edit");
        OpcConsultaRACTS.setIcon("fa fa-fw fa-list-alt");

        model.addElement(MenuRACT);
        model.addElement(OpcAvanceContenido);
        model.addElement(OpcConsultaRACTS);

        MenuUsuarios = new DefaultSubMenu("Usuarios");
        MenuAvanceContenidoTematico = new DefaultMenuItem("Avance contenido temático");
        MenuAvanceContenidoTematico.setOutcome("ract");
        
        MenuPoliticas = new DefaultSubMenu("Políticas de Evaluación");
        

        MenuConfiguracion = new DefaultMenuItem("Configuración del sistema");
        MenuConfiguracion.setOutcome("config");

        MenuCatalogos = new DefaultSubMenu("Mantenimiento a catálogos");

        MenuReportes = new DefaultMenuItem("Reportes");
        MenuReportes.setOutcome("reporte");

        MenuSemaforos = new DefaultMenuItem("Semáforos");
        MenuSemaforos.setOutcome("semaforos");
        
        MenuPoliticasA = new DefaultMenuItem("Aceptar políticas de evaluación");
        MenuPoliticasA.setOutcome("AceptarPolitica");
        
        MenuPoliticasP = new DefaultMenuItem("Generar políticas de evaluación");
        MenuPoliticasP.setOutcome("AltaPolEva");
        
        MenuPoliticasConsulta = new DefaultMenuItem("Consultar políticas de evaluación");
        MenuPoliticasConsulta.setOutcome("ConsultarPoliticas");

        MenuReportes.setRendered(false);
        MenuReportes.setRendered(false);
        MenuSemaforos.setRendered(false);
        MenuPoliticasP.setRendered(false);
        MenuPoliticasA.setRendered(false);
        MenuUsuarios.setRendered(false);
        MenuCatalogos.setRendered(false);
        MenuConfiguracion.setRendered(false);
        MenuPoliticasConsulta.setRendered(false);
        MenuPoliticas.setRendered(false);

        MenuSemaforos.setIcon("fa fa-fw fa-tachometer");
        MenuPoliticasP.setIcon("fa fa-fw fa-edit");
        MenuPoliticasA.setIcon("fa fa-fw fa-check-circle-o");
        MenuReportes.setIcon("fa fa-fw fa-bar-chart");
        MenuCatalogos.setIcon("fa fa-fw fa-wrench");
        MenuConfiguracion.setIcon("fa fa-fw fa-cogs");
        MenuUsuarios.setIcon("fa fa-fw fa-users");
        MenuPoliticasConsulta.setIcon("fa fa-fw fa-list-alt");
        MenuPoliticas.setIcon("fa fa-fw fa-file");

        OpcSemaforos = new DefaultMenuItem("Semaforos");
        OpcReportes = new DefaultMenuItem("Generador de reportes");
        OpcUsuarios = new DefaultMenuItem("Usuarios");
        OpcRoles = new DefaultMenuItem("Roles");
        OpcRoles.setOnclick("statusDialog.show()");
        
        OpcAltaPolitica = new DefaultMenuItem("Alta de política");
        OpcAltaPolitica.setOutcome("AltaPolEva");
        OpcConsultaPolitica = new DefaultMenuItem("Consulta de política");
        OpcConsultaPolitica.setOutcome("ConsultarPoliticas");
        OpcAceptarPolitica = new DefaultMenuItem("Aceptar política");
        OpcAceptarPolitica.setOutcome("AceptarPolitica");

        OpcRealizar = new DefaultMenuItem("Realizar");
        OpcConfiguracion = new DefaultMenuItem("Configuración");
        OpcAdmonUnidadAcademica = new DefaultMenuItem("Unidad académica");
        OpcAdmonProgramaE = new DefaultMenuItem("Programa educativo");
        OpcAdmonAreaC = new DefaultMenuItem("Área de conocimiento");
        OpcAdmonUnidadAprendizaje = new DefaultMenuItem("Unidad de aprendizaje");
        OpcAdmonProfesores = new DefaultMenuItem("Profesores");
        OpcAdmonContenidoT = new DefaultMenuItem("Contenido temático");
        OpcAdmonGrupo = new DefaultMenuItem("Grupo");
        OpcAsignarGrupo = new DefaultMenuItem("Asignar grupo");
        OpcAdmonCampus = new DefaultMenuItem("Campus");
      //OpcAdmonCiclo = new DefaultMenuItem("Ciclo escolar");
        OpcAdmonPlanEstudios = new DefaultMenuItem("Plan de estudios");

        OpcUsuarios.setOutcome("UsuariosMenu");
        OpcConfiguracion.setOutcome("config");
        OpcRoles.setOutcome("RolesMenu");
        OpcRealizar.setOutcome("ract");

        OpcSemaforos.setOutcome("semaforos");
        OpcReportes.setOutcome("reporte");
        OpcAdmonUnidadAcademica.setOutcome("UnidadAcademicaMenu");
        OpcAdmonProgramaE.setOutcome("ProgramaEducativoMenu");
        OpcAdmonAreaC.setOutcome("AreadeConocimientoMenu");
        
        OpcAdmonUnidadAprendizaje.setOutcome("AprendizajeMenu");
        OpcAdmonProfesores.setOutcome("ProfesorMenu");
        OpcAdmonContenidoT.setOutcome("ContenidoTematicoMenu");
        OpcAdmonGrupo.setOutcome("GruposMenu");
        OpcAsignarGrupo.setOutcome("AsigGruposMenu");
        OpcAdmonCampus.setOutcome("CampusMenu");
     // OpcAdmonCiclo.setOutcome("cem");
        OpcAdmonPlanEstudios.setOutcome("PlanMenu");

        OpcSemaforos.setRendered(false);
        OpcReportes.setRendered(false);
        OpcUsuarios.setRendered(false);
        OpcRoles.setRendered(false);
        OpcRealizar.setRendered(false);
        OpcConfiguracion.setRendered(false);
        OpcAdmonUnidadAcademica.setRendered(false);
        OpcAdmonProgramaE.setRendered(false);
        OpcAdmonAreaC.setRendered(false);
        OpcAdmonUnidadAprendizaje.setRendered(false);
        OpcAdmonProfesores.setRendered(false);
        OpcAdmonContenidoT.setRendered(false);
        OpcAdmonGrupo.setRendered(false);
        OpcAsignarGrupo.setRendered(false);
        OpcAdmonCampus.setRendered(false);
    //  OpcAdmonCiclo.setRendered(false);
        OpcAdmonPlanEstudios.setRendered(false);        
        
        List<MenuElement> elementos = new ArrayList<MenuElement>();
//        MenuUsuarios.addElement(OpcUsuarios);
//        MenuUsuarios.addElement(OpcRoles);        
        elementos.add(OpcUsuarios);
        elementos.add(OpcRoles);
        MenuUsuarios.setElements(elementos);
        
        elementos = new ArrayList<MenuElement>();
        elementos.add(OpcAltaPolitica);
        elementos.add(OpcConsultaPolitica);
        elementos.add(OpcAceptarPolitica);
        MenuPoliticas.setElements(elementos);



//        MenuCatalogos.addElement(OpcAdmonUnidadAcademica);
//        MenuCatalogos.addElement(OpcAdmonProgramaE);
//        MenuCatalogos.addElement(OpcAdmonAreaC);
//        MenuCatalogos.addElement(OpcAdmonUnidadAprendizaje);
//        MenuCatalogos.addElement(OpcAdmonProfesores);
//        MenuCatalogos.addElement(OpcAdmonContenidoT);
//        MenuCatalogos.addElement(OpcAdmonGrupo);
//        MenuCatalogos.addElement(OpcAsignarGrupo);
//        MenuCatalogos.addElement(OpcAdmonCampus);
//        MenuCatalogos.addElement(OpcAdmonCiclo);
//        MenuCatalogos.addElement(OpcAdmonPlanEstudios);
        elementos = new ArrayList<MenuElement>();
        elementos.add(OpcAdmonUnidadAcademica);
        elementos.add(OpcAdmonProgramaE);
        elementos.add(OpcAdmonAreaC);
        elementos.add(OpcAdmonUnidadAprendizaje);
        elementos.add(OpcAdmonProfesores);
        elementos.add(OpcAdmonContenidoT);
        elementos.add(OpcAdmonGrupo);
        elementos.add(OpcAsignarGrupo);
        elementos.add(OpcAdmonCampus);
        elementos.add(OpcAdmonPlanEstudios);
        

        OpcAreaConAdm = new DefaultMenuItem("Área administrativa");
        OpcCoorAreaConAdm = new DefaultMenuItem("Coordinador área administrativa");
       // OpcCriteriosProfesor = new DefaultMenuItem("PolÃ­ticas de evaluaciÃ³n - Profesor");
        
        OpcAreaConAdm.setOutcome("AreaAdministrativa");
        OpcAreaConAdm.setRendered(false);
        elementos.add(OpcAreaConAdm);

        OpcCoorAreaConAdm.setOutcome("CoordinadorAreaAdministrativa");
        OpcCoorAreaConAdm.setRendered(false);
        elementos.add(OpcCoorAreaConAdm);
        
     //   OpcCriteriosProfesor.setOutcome("AltaPolEva");
      //  OpcCriteriosProfesor.setRendered(false);
       // elementos.add(OpcCriteriosProfesor);

        OpcResProgEd = new DefaultMenuItem("Responsable de programa educativo");
        OpcResProgEd.setOutcome("responsableprogramaeducativo");
        OpcResProgEd.setRendered(false);
        elementos.add(OpcResProgEd);
        MenuCatalogos.setElements(elementos);
        
        model.addElement(MenuPoliticas);
        model.addElement(MenuCatalogos);
        model.addElement(MenuConfiguracion);
        model.addElement(MenuReportes);
        model.addElement(MenuUsuarios);
        model.addElement(MenuSemaforos);
    }
    
           
    /**
     * Metodo para determinar permisos, se utiliza para renderizar el mensaje
     * de que no existe permiso. O la pagina normal.
     * @param idCatalogo Id del catalogo que se renderizara.
     * @return Verdadero en caso de que se tenga permiso para entrar al
     * catalogo | Falso en cualquier otro caso.
     */
    public boolean permisoGeneral(int idCatalogo){
        System.out.println("INICIO DE PERMISO GENERAL -------");
        
        System.out.println("EL ID DEL CATALOGO ES: " + idCatalogo);
        boolean permiso = false;
        this.setFormano(true);
        
        int idRolActual = getIdGlobal();
        Rol rolAuxiliar = logBeanHelp.getRolById(idRolActual);
        System.out.println("ROL ACTUAL : " + rolAuxiliar.getROLtipo());

        // SE CREA UNA LISTA PARA OBTENER LOS PERMISOS
        List<Permiso> listaPermisos;
        listaPermisos = lb.ObtenerPermiso(rolAuxiliar);

        // SE RECORRE TODA LA LISTA DE PERMISOS
        for (Permiso permisoActual : listaPermisos) {
            System.out.println("TIENE PERMISO DE: " + permisoActual.getPERtipo());
            System.out.println("EL NUMERO DE PERMISO ES: " + permisoActual.getPERid());
            // SI TIENE PERMISO SE CAMBIAN LOS VALORES
            if(permisoActual.getPERid() == idCatalogo) {
                permiso = true;
                this.setFormano(false);
            }
        }
        
        System.out.println("FIN DE PERMISO GENERAL ----------");
        return permiso;
    }    

    /**
     * noRol retorna un boleano usado como bandera para interpretar si estÃ¡
     * vacÃ­a o no la lista de roles del usuario logueado.
     *
     * @return True si el usuario tiene roles, False si no tiene roles
     */
    public boolean noRol() {
        System.out.println("INICIO DE NOROL");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) facesContext.getExternalContext().getSession(true);
        String username = (String) sesion.getAttribute("user");
        logueado.setUSUusuario(username);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("Usuario Logueado: " + logueado.getUSUusuario());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        
        logueado = logBeanHelp.getUsuario(logueado);
        List<Rol> listaTemporal = logueado.getRolList();
        boolean bandera = true;                
        
        if (listaTemporal.isEmpty()) {
            System.out.println("USUARIO NO TIENE ROLES");
            bandera = false;
        }
        System.out.println("USUARIO TIENE ROLES");
        System.out.println("NOROLES RESULTADO: " + bandera);
        return bandera;
    }

    /**
     * Este mÃ©todo tiene el fin de conocer si el usuario tiene un rol Ãºnico.
     *
     * @return True en caso de que el usuario tenga un rol, False en
     * cualquier otro caso
     */
    public boolean unRol() {        
        System.out.println("INICIO DE UNROL");
        boolean bandera = true;
        String seleccionadoTemporal;
        List<Rol> listaTemporal;
        
        listaTemporal = logueado.getRolList();
        
        if (listaTemporal.isEmpty()) {
            bandera = false;
        } else {
            seleccionadoTemporal = listaTemporal.get(0).getROLtipo();
            this.seleccionado = seleccionadoTemporal;
            int idseleccionado = logBeanHelp.getRolByRoltipo(seleccionadoTemporal).getROLid();
            idGlobal = idseleccionado;
            RenderizarMenu(listaTemporal, idseleccionado);
        }
        System.out.println("UNROL RESULTADO: " + this.seleccionado);
        return bandera;
    }
    
    /**
     * Este mÃ©todo retorna true y selecciona el rol del menu si hay varios roles
     * Este mÃ©todo retorna falso si el usuario logueado no tiene mas de 1 rol
     *
     * @return True si tiene mas de un rol, False en cualquier otro caso
     */
    public boolean masRoles() {
        System.out.println("INICIO MAS ROLES");
        formano = false;
        List<Rol> listaTemporal;
        String seleccionadoTemporal;
        int idseleccionado;
        
        listaTemporal = logueado.getRolList();
        System.out.println("Llegue a roles");
        if (listaTemporal.size() > 1) {
            System.out.println("La lista es mayor a 1");
            seleccionadoTemporal = getRolMenu();
            idseleccionado = logBeanHelp.getRolByRoltipo(seleccionadoTemporal).getROLid();
            idGlobal = idseleccionado;
            System.out.println(getRolMenu() + " rol seleccionado directo del menu get");
            System.out.println("onclicksubtinpermiso");
            
            RenderizarMenu(listaTemporal, idseleccionado);
            this.seleccionado = seleccionadoTemporal;
            System.out.println("MASROLES RESULTADO: " + this.seleccionado + " true ");
            return true;
        } else {
            System.out.println("MASROLES RESULTADO: false");
            return false;
        }

    }

    /**
     * Este mÃ©todo es para obtener true si la lista de obtener roles es mayor a
     * 2 Este mÃ©todo es para obtener falso si no tuviera mayor a 1 rol.
     *
     * @return True si tiene mas de dos roles, False en cualquier otro caso.
     */
    public boolean masRoles2() {
        System.out.println("INICIO MASROLES2");
        List<Rol> listaTemporal = null;
        String seleccionadoTemporal = "";        
        listaTemporal = logueado.getRolList();
        int may = -10000;
        
        if (listaTemporal.size() > 1) {
            for (int i = 0; i < listaTemporal.size(); i++) {
                if (listaTemporal.get(i).getROLprioridad() > may) {
                    may = listaTemporal.get(i).getROLprioridad();
                    seleccionadoTemporal = listaTemporal.get(i).getROLtipo();
                    System.out.println("ROL DE MAYOR PRIORIDAD ES: " + listaTemporal.get(i).getROLprioridad());
                }
            }
            
            int idseleccionado = logBeanHelp.getRolByRoltipo(seleccionadoTemporal).getROLid();
            idGlobal = idseleccionado;
            RenderizarMenu(listaTemporal, idseleccionado);
            rolmenu = seleccionadoTemporal;
            this.seleccionado = seleccionadoTemporal;
            System.out.println("MASROLES2 RESULTADO: " + true);
            return true;
        } else {
            System.out.println("MASROLES2 RESULTADO: " + false);
            return false;
        }
    }

    /**
     * Metodo para loguear usuario.
     */
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) facesContext.getExternalContext().getSession(true);
        String username;
        String ruta;
        username = (String) sesion.getAttribute("user");
        Usuario logueadoActual = new Usuario();
        logueadoActual.setUSUusuario(username);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("Logueado: " + logueadoActual.getUSUusuario());
        boolean loggedIn;        
        logueadoActual = logBeanHelp.getUsuario(logueadoActual);
        
        // SE COMPARA SI HAY UN USUARIO LOGGEADO
        if (logueadoActual == null) {
//            RequestContext context = RequestContext.getCurrentInstance();
            ruta = MyUtil.basepathlogin() + "denied.jsp";
            loggedIn = false;
//            context.addCallbackParam("loggedIn", loggedIn);
  //          context.addCallbackParam("ruta", ruta);
        } else {            
            roles = logueadoActual.getRolList();
        }
        obtenerRoles = new ArrayList();
    }

       
    /**
     *
     */
    public void login() {
        System.out.println("INICIO CLASE LOGIN");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) facesContext.getExternalContext().getSession(true);
        String username = (String) sesion.getAttribute("user");        

//        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        boolean loggedIn;
        String ruta = "";
        logueado = new Usuario();
        logueado.setUSUusuario(username);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("Usuario Logueado: " + logueado.getUSUusuario());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        logueado = logBeanHelp.getUsuario(logueado);
        if (logueado != null) {
            loggedIn = true;
            init();

            if (!noRol()) {
                setSelectRol(false);
                model = new DefaultMenuModel();

                ruta = MyUtil.basepathlogin() + "index.xhtml";
                System.out.println("entre a esta seccion por eso fui redericeonado de todas foprmas");
                loggedIn = false;

                if (this.logueado == null) {
                    this.logueado = new Usuario();
                }

                ruta = MyUtil.basepathlogin() + "denied.jsp";
                System.out.println("usuario no existe");
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario no tiene asignado rol", "Usuario no tiene asignado rol");
                FacesContext.getCurrentInstance().addMessage(null, message);
//                context.addCallbackParam("loggedIn", loggedIn);

      //          context.addCallbackParam("ruta", ruta);

            } else {
                System.out.println("si estoy imprimiendo esto significa que ");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("logueado", this.logueado.getUSUusuario());
                lb = new LoginBean();
                list = null;

                list = logueado.getRolList();
                if (banderaUnavez == false) {
                    if (list.size() == 1) {
                        if(list.get(0).getROLid() == 8){
                            if(logueado.getAlumnoList().get(0).getPoliticaevaluacionList().size()==0){
                                    ruta = MyUtil.basepathlogin() + "AccesoDenegadoAlumno.html";
                                    System.out.println("no existen politicas para el alumno");
                                    try{
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
                                    }catch(IOException e){
                                        System.out.println(e);
                                    }
                            }else{
                                setSelectRol(false);
                                ruta = MyUtil.basepathlogin() + "index.xhtml";
                                permisoAlta = false;
                                permisoModificar = false;
                                permisoBaja = false;
                                onClickSubmitPermiso();
                                banderaUnavez = true;
                            }
                        }else{
                        setSelectRol(false);
                        ruta = MyUtil.basepathlogin() + "index.xhtml";
                        permisoAlta = false;
                        permisoModificar = false;
                        permisoBaja = false;
                        onClickSubmitPermiso();
                        banderaUnavez = true;
                        }
                    } else {
                        banderaUnavez = true;
                        permisoAlta = false;
                        permisoModificar = false;
                        permisoBaja = false;
                        ruta = MyUtil.basepathlogin() + "index.xhtml";
                        onClickSubmitPermiso();
                    }
                }
            }
//            context.addCallbackParam("loggedIn", loggedIn);
        //    context.addCallbackParam("ruta", ruta);
        }
        if (logueado == null) {
            ruta = MyUtil.basepathlogin() + "denied.jsp";
            System.out.println("usuario no existe");
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o ContraseÃ±a incorrecta", "Usuario o ContraseÃ±a incorrecta");
            if (this.logueado == null) {
                this.logueado = new Usuario();
            }

            FacesContext.getCurrentInstance().addMessage(null, message);
//            context.addCallbackParam("loggedIn", loggedIn);
//            context.addCallbackParam("ruta", ruta);
        }
        System.out.println(ruta);
    }

    /**
     * Cambios para renderizar el menu automaticamente al tener un solo rol
     *
     * @return
     */
    public String onClickSubmitPermiso() {
        String ruta = "inicioP";
        model = new DefaultMenuModel();

        if (roles.size() == 1) {
            unRol();

        } else {
            if (roles.size() > 1) {
                masRoles2();
                setSelectRol(true);
            }
        }

        return ruta;
    }

    /**
     *
     * @return
     */
    public String onClickSubmitPermiso2() {
        String ruta = "inicioP";
        System.out.println("onclicksubtinpermiso2");
        model = new DefaultMenuModel();
        permisoAlta = false;
        permisoModificar = false;
        permisoBaja = false;

        if (roles.size() > 1) {
            masRoles();
            setSelectRol(true);
        }
        return ruta;
    }

    /**
     *
     * Metodo para renderizar el menu dependiendo de los roles
     *
     * @param list Lista de roles
     * @param rolId Id del rol
     */
    public void RenderizarMenu(List<Rol> list, int rolId) {
        inicializarMenu();
        System.out.println("USUARIO: " + logueado.getUSUusuario());
        switch (rolId) {
            // CASO DE ADMINISTRADOR                
            case 1:
                System.out.println("CASO UNO");
                OpcConsultaRACTS.setRendered(true);
                OpcAvanceContenido.setRendered(true);
                MenuSemaforos.setRendered(true);
                OpcSemaforos.setRendered(true);
                MenuPoliticas.setRendered(true);
                break;
            // CASO DE DIRECTOR
            case 2:
                System.out.println("CASO DOS");
                OpcConsultaRACTS.setRendered(true);
                OpcAvanceContenido.setRendered(true);
                MenuSemaforos.setRendered(true);
                OpcSemaforos.setRendered(true);
                MenuPoliticas.setRendered(true);
                break;
            // CASO DE SUBDIRECTOR                 
            case 3:
                System.out.println("CASO TRES");
                OpcAvanceContenido.setRendered(true);
                MenuPoliticas.setRendered(true);
                break;
            // COORDINADOR DE AREA DE CONOCIMIENTO
            case 4:
                System.out.println("CASO CUATRO");
                OpcConsultaRACTS.setRendered(true);
                OpcAvanceContenido.setRendered(true);
                MenuUsuarios.setRendered(false);
                MenuPoliticas.setRendered(true);
                break;
            // COORDINACION DE FORMACION BASICA
            case 5:
                System.out.println("CASO CINCO");
                OpcAvanceContenido.setRendered(true);
                MenuPoliticas.setRendered(true);
                break;
            // RESPONSABLE DE PROGRAMA EDUCATIVO
            case 6:
                System.out.println("CASO SEIS");
                OpcConsultaRACTS.setRendered(true);
                OpcAvanceContenido.setRendered(true);
                MenuUsuarios.setRendered(false);
                MenuPoliticas.setRendered(true);
                break;
            // PROFESOR
            case 7:
                OpcConsultaRACTS.setRendered(true);
                OpcAvanceContenido.setRendered(true);
                model.addElement(MenuPoliticasP);
                model.addElement(MenuPoliticasConsulta);
                MenuPoliticasP.setRendered(true);
                MenuPoliticasConsulta.setRendered(true);
                break;
            // ALUMNO
            case 8:
                model.addElement(MenuPoliticasA);
                model.addElement(MenuPoliticasConsulta);
                MenuPoliticasP.setRendered(true);
                MenuPoliticasConsulta.setRendered(true);
                break;
        }

        // SE OBTIENE EL OBJETO ROL
        Rol rolAuxiliar = logBeanHelp.getRolById(rolId);
        System.out.println("ROL ACTUAL : " + rolAuxiliar.getROLtipo());

        // SE CREA UNA LISTA PARA OBTENER LOS PERMISOS
        List<Permiso> listaPermisos;
        listaPermisos = lb.ObtenerPermiso(rolAuxiliar);

        // SE RECORRE TODA LA LISTA DE PERMISOS
        for (Permiso permisoActual : listaPermisos) {
            System.out.println("TIENE PERMISO DE: " + permisoActual.getPERtipo());
            System.out.println("EL NUMERO DE PERMISO ES: " + permisoActual.getPERid());
            switch (permisoActual.getPERid()) {
                // GENERAR REPORTE
                case 1:
                    MenuReportes.setRendered(true);
                    OpcReportes.setRendered(true);
                    break;
                // CONFIGURAR SISTEMA
                case 2:
                    MenuConfiguracion.setRendered(true);
                    OpcConfiguracion.setRendered(true);
                    break;

                // ADMINISTRACION DE ROL
                case 3:
                    MenuUsuarios.setRendered(true);
                    OpcRoles.setRendered(true);
                    break;

                //ADMINISTRACION DE CUENTA DE USUARIO
                case 4:
                    MenuUsuarios.setRendered(true);
                    OpcUsuarios.setRendered(true);
                    break;

                // ADMINISTRACION DE UNIDAD ACADEMICA
                case 5:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonUnidadAcademica.setRendered(true);
                    break;

                // ADMINISTRACION DE PROGRAMA EDUCATIVO
                case 6:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonProgramaE.setRendered(true);
                    break;

                // ADMINISTRACION DE AREA DE CONOCIMIENTO
                case 7:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonAreaC.setRendered(true);
                    break;
                // ADMINISTRACION DE UNIDAD DE APRENDIZAJE
                case 8:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonUnidadAprendizaje.setRendered(true);
                    break;

                // ADMINISTRACION DE PROFESORES
                case 9:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonProfesores.setRendered(true);
                    break;

                // ADMINISTRACION DE CONTENIDO TEMATICO
                case 10:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonContenidoT.setRendered(true);
                    break;

                // ADMINISTRACION DE GRUPO
                case 11:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonGrupo.setRendered(true);
                    break;

                // ASIGNAR GRUPO
                case 12:
                    MenuCatalogos.setRendered(true);
                    OpcAsignarGrupo.setRendered(true);
                    break;

                // ACTUALIZAR PORCENTAJE DE CONTENIDO TEMATICO
                case 13:
                    OpcRealizar.setRendered(true);
                    break;
                // ADMINISTRACION DE CAMPUS
                case 14:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonCampus.setRendered(true);
                    break;

                // ADMINISTRACION DE CICLO ESCOLAR
                //case 15:
            //        MenuCatalogos.setRendered(true);
               //     OpcAdmonCiclo.setRendered(true);
              //      break;

                //ADMINISTRACION DE PLAN DE ESTUDIOS
                case 15:
                    MenuCatalogos.setRendered(true);
                    OpcAdmonPlanEstudios.setRendered(true);
                    break;

                // RESPONSABLE DE PROGRAMA EDUCATIVO
                case 16:
                    MenuCatalogos.setRendered(true);
                    OpcResProgEd.setRendered(true);
                    break;

                // ADMINISTRACION DE AREA ADMINISTRATIVA
                case 17:
                    MenuCatalogos.setRendered(true);
                    OpcAreaConAdm.setRendered(true);
                    break;

                // ADMINISTRACION DE COORDINADOR AREA
                case 18:
                    MenuCatalogos.setRendered(true);
                    OpcCoorAreaConAdm.setRendered(true);
                    break;
                    
                // ADMINISTRACION DE CRITERIOS PROFESOR
                case 21:
                    //MenuCatalogos.setRendered(true);
                    MenuPoliticasP.setRendered(true);
                   // OpcCriteriosProfesor.setRendered(true);
                    break;
                    
                // VALIDACION DE CRITERIOS ALUMNO
                case 20:
                    MenuPoliticasA.setRendered(true);
                    break;
                    
                 // VALIDACION DE consulta Politica
                case 22:
                    MenuPoliticasConsulta.setRendered(true);
                    break;
                    

            }
        }
    }

    /**
     * Este mÃ©todo usa los parÃ¡metros para definir los permisos a true
     *
     * @param list
     * @param seleccionado
     * @param catalogo
     */
    
    public void TienePermiso(List<Rol> list, String seleccionado, String catalogo) {
        permisoModificar = false;
        permisoAlta = false;
        permisoBaja = false;
        formano = true;
        tienePermisoCatalogo = false;
        for (Rol rolTemporal : list) {
            if (rolTemporal.getROLtipo().equals(seleccionado)) {
                System.out.println(seleccionado + "  #########################");
                List<Permiso> list2 = lb.ObtenerPermiso(rolTemporal);                
                for (Permiso per : list2) {
                    if (per.getPERtipo().equals(catalogo)) {
                        System.out.println(catalogo + " TENGO ESTE PERMISO ");
                        formano = false;

                        //  1 ID = CONSULTA
                        //  2 ID = MODIFICACION
                        //  3 ID = ALTAS
                        //  4 ID = ELIMINACION
                        // TODO REEMPLAZAR LO DE ABAJO CON ID EN LUGAR DE STRINGS
                        
                        List<Subpermisos> s;
                        s = logBeanHelp.getSubPermiso(rolTemporal.getROLid(), per.getPERid());
                        for (Subpermisos sub : s) {  
                            if(sub.getSPERid() == 2){
                                System.out.println("TIENE PERMISO DE MODIFICAR");
                                permisoModificar = true;
                            }                            
                            if(sub.getSPERid() == 4){
                                System.out.println("TIENE PERMISO DE ELIMINAR");
                                permisoBaja = true;
                            }                            
                            if(sub.getSPERid() == 3){
                                System.out.println("TIENE PERMISO DE ALTAS");
                                permisoAlta = true;
                            }                                                        
                        }
                    }
                }
            }
        }
        System.out.println("FINAL DE TIENE PERMISO");
    }
    
    public void verificarPermiso(List<Rol> list, int idSeleccionado, int idCatalogo) {
        permisoModificar = false;
        permisoAlta = false;
        permisoBaja = false;
        formano = true;
        tienePermisoCatalogo = false;
        for (Rol rolTemporal : list) {
            // SE COMPARA EL ID DEL ROL DEL USUARIO CON EL ROL ACTUAL
            if (rolTemporal.getROLid() == idSeleccionado) {
                System.out.println(seleccionado + "  #########################");
                List<Permiso> list2 = null;
                list2 = lb.ObtenerPermiso(rolTemporal);
                for (Permiso per : list2) {                    
                    // SE COMPARA SI TIENE PERMISO PARA ADMINISTRAR PROFESORES
                    // ID CATALOGO 9 = ADMINISTRAR PROFESORES
                    if (per.getPERid() == idCatalogo) {
                        System.out.println("ID: "+ per.getPERid()+ " " + per.getPERtipo());
                        tienePermisoCatalogo = true;
                        formano = false;

                        //  1 ID = CONSULTA
                        //  2 ID = MODIFICACION
                        //  3 ID = ALTAS
                        //  4 ID = ELIMINACION
                        
                        List<Subpermisos> s;
                        s = logBeanHelp.getSubPermiso(rolTemporal.getROLid(), per.getPERid());
                        for (Subpermisos sub : s) {  
                            if(sub.getSPERid() == 2){
                                System.out.println("TIENE PERMISO DE MODIFICAR");
                                permisoModificar = true;
                            }                            
                            if(sub.getSPERid() == 4){
                                System.out.println("TIENE PERMISO DE ELIMINAR");
                                permisoBaja = true;
                            }                            
                            if(sub.getSPERid() == 3){
                                System.out.println("TIENE PERMISO DE ALTAS");
                                permisoAlta = true;
                            }                   
                        }
                    }
                }
            }
        }
        System.out.println("TIENEPERMISOCATALOGO = " + tienePermisoCatalogo);
    }

    /**
     * Metodo que obtiene una lista con los roles
     *
     * @param rol ID del rol
     * @return Retorna lista de objetos tipo rol con los encontrados
     */
    public List<Rol> Obtenerrol(int rol) {
        List<Rol> listarol = null;
        listarol = u.getRolList();
        return listarol;
    }

    /**
     * Metodo que regresa la lista de los permisos de un rol especifico
     *
     * @param rol Variable tipo rol
     * @return Lista de los permisos del Rol
     */
    public List<Permiso> ObtenerPermiso(Rol rol) {
        List<Permiso> listapermiso = new ArrayList();
        for (RolHasPermiso rolHasPermiso : rol.getRolHasPermisoList()) {
            listapermiso.add(rolHasPermiso.getPermiso());
        }
        return listapermiso;
    }

    /**
     *
     * @return
     */
    public boolean selectedRol() {
        boolean b;
        b = true;
        boolean ver = b;
        return ver;
    }

    /**
     * Metodo que redirecciona la pagina al salir de la sesion
     *
     * @return
     */
    public String doActionSalir() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        request.getSession().setAttribute("user", null);
        request.getSession(false).invalidate();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("https://llave.uabc.edu.mx/auth/logout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public boolean visible(){
        return logueado.getRolList().get(0).getROLid() != 8;
    }

    
    
   

    /**
     *
     * @return
     */
    public String index() {
        String ruta = "inicioP";
        return ruta;
    }
}