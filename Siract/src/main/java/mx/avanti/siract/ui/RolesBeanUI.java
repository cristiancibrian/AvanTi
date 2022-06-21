package mx.avanti.siract.ui;

import mx.avanti.siract.entity.*;
import mx.avanti.siract.helper.RolesBeanHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author Avanti 2018.
 * @author Refactorizado por: Kevin Arizaga.
 */
@ManagedBean
@SessionScoped
public class RolesBeanUI implements Serializable {

    private final int IDCATALOGOADMIROL = 3;
    Rol rolObj = new Rol();
    boolean nuevo = false;
    Rol rolAux = new Rol();
    int rolEntero = -1;
    private List<String> listPermisos = new ArrayList<>();
    private List<String> listaPermisosSelected = new ArrayList<>();
    private RolesBeanHelper rolBeanHelp = new RolesBeanHelper();
    private String boton;
    private String deshabilitar = "false";
    private String header;
    private String ModEl = "true";
    private String nombreRol;
    private TreeNode root;
    private String busqueda;
    //    private List<Rol> listaFiltrada = rolBeanHelp.getRolDelegate().getRol();
    private List<Rol> listaFiltrada = rolBeanHelp.getRol();

    public int getIDCATALOGOADMIROL() {
        return IDCATALOGOADMIROL;
    }

    /**
     * Inicia el Array de la lista de permisos.
     */
    public void init() {
        listPermisos = new ArrayList<>();
    }

    /**
     * Devuelve el String que indica el deshabilitado del rol
     *
     * @return deshabilitar
     */
    public String getDeshabilitar() {
        return deshabilitar;
    }

    /**
     * Le entrega el string que indica el deshabilitado del rol
     *
     * @param deshabilitar
     */
    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    /**
     * Devuelve el string que indica el encabezado
     *
     * @return header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Le entrega el string que indica el encabezado
     *
     * @param header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Le devuelve el rol auxiliar que se requiere
     *
     * @return rolAux
     */
    public Rol getRolAux() {
        return rolAux;
    }

    /**
     * Le entrega el rol auxiliar que se requiere
     *
     * @param rolAux
     */
    public void setRolAux(Rol rolAux) {
        this.rolAux = rolAux;
    }

    /**
     * El String ModEl cambia su valor a falso si es igual a Administrador. de
     * otro modo seguirá igual a true.
     *
     * @param event
     * @return ""
     */
    public String onRowSelect(SelectEvent event) {
        rolAux = (Rol) event.getObject();
        ModEl = "true";
        if (!rolAux.getROLtipo().equalsIgnoreCase("Administrador")) {
            ModEl = "false";
        }
        return "";
    }

    /**
     * El String ModEl cambia su valor a true.
     *
     * @param event
     * @return ""
     */
    public String noRowUnselect(UnselectEvent event) {
        ModEl = "true";
        return "";
    }

    /**
     * Le devuelve una lista generada con los permisos que se le añaden en la
     * condición de que el tipo de permiso sea diferente a actualizar Porcentaje
     * de avance de contenido temático y el subpermiso sea diferente a
     * eliminación.
     *
     * @return listPermisos
     */
    public List<String> getListPermisos() {
        listPermisos = new ArrayList();
        for (Permiso perm : rolBeanHelp.getPermiso()) {
            for (Subpermisos subperm : rolBeanHelp.getSubPermiso()) {
                Permiso aux = new Permiso();
                if (!perm.getPERtipo().equals("Actualizar porcentaje de contenido temático") || !subperm.getSPERtipo().equals("Eliminación")) {
                    listPermisos.add(perm.getPERtipo() + " - " + subperm.getSPERtipo());
                }
            }
        }

        return listPermisos;
    }

    /**
     * Le entrega un tipo de lista String que se llama listPermisos
     *
     * @param listPermisos
     */
    public void setListPermisos(List<String> listPermisos) {
        this.listPermisos = listPermisos;
    }

    /**
     * Le devuelve un tipo de lista String que se llama listPermisos en caso de
     * que el rolObj (que es la instancia del rol) sea diferente a null.
     *
     * @return
     */
    public List<String> getListaPermisosSelected() {
        return listaPermisosSelected;
    }

    /**
     * Este método entrega el atributo listaPermisosSelected de tipo de dato
     * String
     *
     * @param listaPermisosSelected
     */
    public void setListaPermisosSelected(List<String> listaPermisosSelected) {
        this.listaPermisosSelected = listaPermisosSelected;
    }

    /**
     * Retorna rolObj que sería la nueva instancia del rol si fuera null
     *
     * @return
     */
    public Rol getRolObj() {
        if (rolObj == null) {
            rolObj = new Rol();
        }
        return rolObj;
    }

    /**
     * Este método entrega el atributo rolObj de tipo de dato Rol
     *
     * @param rolObj
     */
    public void setRolObj(Rol rolObj) {
        this.rolObj = rolObj;
    }

    /**
     * Este método devuelve una listaFiltrada, por medio de rolBeanHelp utiliza
     * el método filtrado con el parámetro de búsqueda.
     */
    public void refrescarForma() {
        busqueda = "";
        listaFiltrada = rolBeanHelp.filtrado(busqueda);
    }

    /**
     * Este método devuelve el String de Boton.
     *
     * @return
     */
    public String getBoton() {
        return boton;
    }

    /**
     * Este método entrega el String de Boton.
     *
     * @param boton
     */
    public void setBoton(String boton) {
        this.boton = boton;
    }

    /**
     * Este metodo cambia el valor del Boton dependiendo si la lista está vacia
     *
     * @param rolPerm
     * @return
     */
    public String permStrList(Rol rolPerm) {
        List<String> lista = new ArrayList();
        return "Consultar los permisos de rol: ";
    }

    /**
     * Metodo para eliminar cuando la eliminacion fue confirmada en la
     * interfaz
     */
    public void eliminarConfirm() {
        rolObj = rolBeanHelp.getRolUnico(rolObj.getROLid());
        if (!isNull(rolObj)) {
            if (!isNull(rolObj.getUsuarioList())) {
                if (rolObj.getUsuarioList().size() >= 1) {
                    mostrarMensaje("El rol ya tiene usuarios asignados.");
                } else {
                    rolBeanHelp.eliminarRol(rolObj);
                    mostrarMensaje("El rol se eliminó correctamente.");
                    listaFiltrada = rolBeanHelp.getRol();
                }
            }
        }
        PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
        PrimeFaces.current().executeScript("PF('dlg').hide()");
    }

    /**
     * Método que verifica la existencia de un ROL.
     *
     * @return
     */
    private boolean existeRol() {
        List<Rol> rolesExistentes = rolBeanHelp.getRoles();
        for (Rol rol : rolesExistentes) {
            if (rol.getROLtipo().equalsIgnoreCase(rolObj.getROLtipo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que verifica la existencia de un ROL, excepto que descarta el ROL que se esta modificando.
     *
     * @param roLid
     * @return
     */
    private boolean existeRol(Integer roLid) {
        List<Rol> rolesExistentes = rolBeanHelp.getRoles();
        for (Rol rol : rolesExistentes) {
            if (rol.getROLtipo().equalsIgnoreCase(rolObj.getROLtipo())
                    && rol.getROLid() != roLid) {
                return true;
            }
        }
        return false;
    }

    /**
     * En este método ocurren ciertas acciones dependiendo del valor de sus
     * atributos banderas.
     * Si deshabilitar es igual a true: elimina al rol y sus permisos.
     * Si deshabilitar no es igual a true: guarda la instancia del rol
     * y le otorga permisos.
     * Así como aparecen los mensajes correspondientes.
     */
    public void onClick() {
        switch (header) {
            case "Agregar Rol":
                //Verifica si existe el rol antes de agregarlo, si ya existe no se agrega.
                if (existeRol()) {
                    mostrarMensaje("Ese nombre de rol ya existe.");
                } else {
                    //En los siguientes IF se verifica que ambos campos (nombre del rol y los permisos seleccionados) no estén vacíos.
                    if (!isCamposVacios()) {
                        //Se guarda el rol para establecerle un ID.
                        rolBeanHelp.saveRol(rolObj);
                        agregarNuevoRol();
                        mostrarMensaje("El rol se guardó correctamente.");
                    }
                }
                //Restablece los valores del campo de rol y los permisos seleccionados.
                restablecerValores();
                break;
            case "Modificar Rol":
                //Verifica si existe el rol antes de modificarlo, se ignora si mantiene el mismo nombre.
                if (!existeRol(rolObj.getROLid())) {
                    //En los siguientes IF se verifica que ambos campos (nombre del rol y los permisos seleccionados) no estén vacíos.
                    if (!isCamposVacios()) {
                        modificarRol();
                        mostrarMensaje("El rol se modificó correctamente.");
                    }
                }
                //Restablece los valores del campo de rol y los permisos seleccionados.
                restablecerValores();
                break;
            case "Eliminar Rol":
                //Manda a mostrar el cuadro de diálogo de confirmación. Posteriormente se manda a llamar el método: eliminarConfirm()
                PrimeFaces.current().executeScript("PF('confirmdlg').show()");
                break;
            default:
                mostrarMensaje("No se pudo realizar la acción.");
                break;
        }
        listaFiltrada = rolBeanHelp.getRol();
    }

    private boolean isCamposVacios() {
        if (rolObj.getROLtipo().isEmpty()) {
            if (listaPermisosSelected.isEmpty()) {
                mostrarMensaje("Favor de llenar campo de rol y seleccionar permisos.");
                return true;
            } else {
                mostrarMensaje("Favor de llenar el campo de rol.");
                return true;
            }
            //Se procede a guardar el nuevo rol.
        } else if (listaPermisosSelected.isEmpty()) {
            mostrarMensaje("Favor de seleccionar permisos.");
            return true;
        }
        return false;
    }

    /**
     * Método para modificar un ROL existente.
     */
    private void modificarRol() {

        Rol modificarRol = new Rol();
        modificarRol = rolObj;

        //Obtenemos los anteriores roles que tenia el rol.
        List<RolHasPermiso> listaPermisoRolesAnterior = rolObj.getRolHasPermisoList();

        //Obtenemos todos los valores del rol a modificar desde la base de datos.
        rolObj = rolBeanHelp.getRolUnico(modificarRol.getROLid());

        //Obtenemos los permisos y subpermisos seleccionados que luego remplazaran al rol ya existente.
        DatosRol obtenerDatosModificados = obtenerPermisosAndSubpermisos();
        //Se establecen los nuevos permisos para el rol antes de eliminar.
        //Se eliminan para evitar guardar de nuevo los mismos roles. Go to line -> 513
        rolObj.setRolHasPermisoList(obtenerDatosModificados.getListaRolHasPermisos());


        //En el primer IF: Se eliminan los permisos que se quitaron del antiguo rol si los tenía con anterioridad.
        //En el segundo IF: Si el rol mantiene algunos de los permisos asignados se elimina de la lista nueva.
        for (RolHasPermiso rol : listaPermisoRolesAnterior) {
            if (!obtenerDatosModificados.getListaRolHasPermisos().contains(rol)) {
                rolBeanHelp.eliminarRolHasPermiso(rol);
            } else if (obtenerDatosModificados.getListaRolHasPermisos().contains(rol)) {
                obtenerDatosModificados.restarPrioridad(rol.getPermiso().getPERvalor());
                obtenerDatosModificados.getListaRolHasPermisos().remove(rol);
            }
        }

        //FOR que sirve para recorrer la lista de 'RolHasPermiso', se guardan los
        //permisos y subpermisos que tiene el nuevo ROL.
        for (Integer x = 0; x < obtenerDatosModificados.getListaRolHasPermisos().size(); x++) {
            rolBeanHelp.saveRolHasPermiso(obtenerDatosModificados.getListaRolHasPermisos().get(x));
        }

        //Se establecen los valores modificados al antiguo rol.
        rolObj.setROLtipo(modificarRol.getROLtipo());
        rolObj.setROLprioridad(obtenerDatosModificados.getPrioridad());

        rolBeanHelp.updateRol(rolObj);
    }

    /**
     * Método para agregar un nuevo ROL.
     */
    private void agregarNuevoRol() {

        //Obtenemos los valores directo de la base de datos.
        rolObj = rolBeanHelp.getRolPorNombre(rolObj.getROLtipo());

        //Se establecen valores.
        List<RolHasPermiso> listaRolHasPermiso = new ArrayList<RolHasPermiso>();

        //Método para obtener los permisos y subpermisos de un rol.
        DatosRol datos = obtenerPermisosAndSubpermisos();

        //FOR que sirve para recorrer la lista de 'RolHasPermiso', se guardan los
        //permisos y subpermisos que tiene el nuevo ROL.
        for (Integer x = 0; x < datos.getListaRolHasPermisos().size(); x++) {
            rolBeanHelp.saveRolHasPermiso(datos.getListaRolHasPermisos().get(x));
        }

        //Se guardan los nuevos valores en el rol.
        rolObj.setROLtipo(rolObj.getROLtipo());
        rolObj.setRolHasPermisoList(datos.getListaRolHasPermisos());
        rolObj.setROLprioridad(datos.getPrioridad());
        //Se establece una lista nueva vacía para que no esté en NULL.
        rolObj.setUsuarioList(new ArrayList<Usuario>());

        //Se actualiza los valores en la base de datos.
        rolBeanHelp.updateRol(rolObj);

    }

    /**
     * Método para obtener los permisos y sub-permisos de un rol, retorna una sub-clase de tipo DatosRol
     * de esta manera se almacenan los datos y se guardan posteriormente.
     *
     * @return DatosRol
     */
    private DatosRol obtenerPermisosAndSubpermisos() {

        //Establecemos la prioridad del rol en cero.
        Integer prioridadDelRol = 0;

        //Creación de lista de objetos
        List<Permiso> listaTodosPermisos = rolBeanHelp.getPermiso();
        List<Subpermisos> listaTodosSubPermisos = rolBeanHelp.getSubPermiso();
        List<RolHasPermiso> listaRolHasPermiso = new ArrayList<>();

        for (String permisoNombre : listaPermisosSelected) {
            //Creación de objetos Rol
            RolHasPermisoPK permisosParaEsteRol = new RolHasPermisoPK();
            RolHasPermiso rolTienePermiso = new RolHasPermiso();

            //Array de String donde;
            //0 = nombre del permiso
            //1 = nombre del sub-permiso
            String[] permisoAndSubpermiso = permisoNombre.split(" - ");

            //For-each para obtener los permisos de los permisos seleccionados.
            for (Permiso permiso : listaTodosPermisos) {
                if (permiso.getPERtipo().equals(permisoAndSubpermiso[0])) {
                    permisosParaEsteRol.setPermisoPERid(permiso.getPERid());
                    prioridadDelRol += permiso.getPERvalor();
                }
            }

            //For-each para obtener los sub-permisos de los sub-permisos seleccionados.
            for (Subpermisos subpermisos : listaTodosSubPermisos) {
                if (subpermisos.getSPERtipo().equals(permisoAndSubpermiso[1])) {
                    permisosParaEsteRol.setSubpermisosSPERid(subpermisos.getSPERid());
                    prioridadDelRol += subpermisos.getSPERvalor();
                }
            }

            //Se establecen los siguientes valores del rol;
            //1. Permisos del rol
            //2. Sub-permisos
            //3. Prioridad
            permisosParaEsteRol.setRolROLid(rolObj.getROLid());
            rolTienePermiso.setRolHasPermisoPK(permisosParaEsteRol);
            rolTienePermiso.setPermiso(rolBeanHelp.getPermisoPorId(permisosParaEsteRol.getPermisoPERid()));
            rolTienePermiso.setSubpermisos(rolBeanHelp.getSubPermisoPorId(permisosParaEsteRol.getSubpermisosSPERid()));

            //Se agregan los roles a la lista para posteriormente guardarlos.
            listaRolHasPermiso.add(rolTienePermiso);
        }

        //Se retorna objeto tipo DatosRol donde se tienen almacenado la prioridad y la lista de roles permitidos para el nuevo rol.
        return new DatosRol(prioridadDelRol, listaRolHasPermiso);

    }

    /**
     * Método que sirve para mostrar directamente un mensaje en pantalla.
     *
     * @param mensaje
     */
    private void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
    }

    private void restablecerValores() {
        rolObj = new Rol();
        listaPermisosSelected.clear();
    }

    /**
     * Realiza las intancias de listPermisos y listaPermisosSelected
     * y los atributos header,deshabilitar,nuevo,rolObj.
     *
     * @return
     */
    public String nuevo() {
        limpiarSección();
        rolObj = null;
        listPermisos = new ArrayList();
        listaPermisosSelected = new ArrayList();
        header = "Agregar Rol";
        deshabilitar = "false";
        nuevo = true;
        return "";
    }

    /**
     * Inicializa a los atributos result3, header, deshabilitar, nombreRol,
     * y rolEntero.
     *
     * @return result3
     */
    public String eliminar() {
        String result3 = "UsuarioEliminado";
        header = "Eliminar Rol";
        deshabilitar = "true";
        nombreRol = rolObj.getROLtipo();
        rolEntero = rolObj.getROLid();

        return result3;
    }

    /**
     * Inicializa los atributos header,deshabilitar,nombreRol y de ser diferente
     * a 0 el rolEnetero, permite utilizar el método de guardar con el parámetro
     * rolObj (instancia del rol).
     *
     * @return
     */
    public void modificar() {
        header = "Modificar Rol";
        deshabilitar = "false";
        nuevo = false;
        nombreRol = rolObj.getROLtipo();
//        if (rolEntero != 0) {
////            rolBeanHelp.getRolDelegate().saveRol(rolObj);
//            rolBeanHelp.saveRol(rolObj);
//        }

        for (RolHasPermiso rhp : rolObj.getRolHasPermisoList()) {
            listaPermisosSelected.add(rhp.getPermiso().getPERtipo() + " - " + rhp.getSubpermisos().getSPERtipo());
        }

    }

    /**
     * Este método realiza instancias de permisosNod e instancias de
     * subPermisosNod como nuevos DefualtTreeNode, igual a la cantidad de listas
     * del tipo permiso que se obtienen del método getPermisoUser y le envia el
     * parámetro del ID del rol auxiliar.
     *
     * @return root del tipo TreeNode
     */
    public TreeNode getRoot() {
        try {
            // Se crea la estructura del árbol
            this.root = new DefaultTreeNode("Root Node", null);
            TreeNode permisosNod = null;
            TreeNode subpermisosNod = null;
            // Se asigna al rol el cuadrito con los que se consultan sus permisos y
            // subpermisos
            if (rolAux != null && rolAux.getROLid() != null) {
                // Se le la lista de permisos que tiene el rol
                List<Permiso> lista = rolBeanHelp.getPermisosDeRol(rolAux);
                if (!lista.isEmpty()) {
                    // Si la lista no esta vacia se acomoda cada permiso y
                    // subpermiso como nodos
                    for (Permiso perm : lista) {
                        permisosNod = new DefaultTreeNode(perm.getPERtipo(), this.root);
                        for (Subpermisos subp : rolBeanHelp.getSubPermisoPorRolPermID(rolAux.getROLid(), perm.getPERid())) {
                            subpermisosNod = new DefaultTreeNode(subp.getSPERtipo(), permisosNod);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            this.root = new DefaultTreeNode("Root Node", null);
            TreeNode permisosNod = new DefaultTreeNode("null", this.root);
            TreeNode subpermisosNod = new DefaultTreeNode("null", permisosNod);
            return this.root;
        }
        // Se actualiza literal la tabla
        PrimeFaces.current().executeScript("PF('statusDialog').hide()");
        // Se regresa toda la estructura del cuadro
        return this.root;
    }

    /**
     * Este método entrega el atributo root de tipo de dato Treenode
     *
     * @param root
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * Este método devuelve el atributo busqueda de tipo de dato String.
     *
     * @return
     */
    public String getBusqueda() {
        return busqueda;
    }

    /**
     * Este método entrega el atributo busqueda de tipo de dato String
     *
     * @param busqueda
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * Este método inicializa la listaFiltrada que es una lista de tipo Rol si
     * es que se encuentra vacía
     */
    public void filtrado() {
        System.out.println("Si entra [" + busqueda + "]");
        listaFiltrada = rolBeanHelp.filtrado(busqueda);
        if (listaFiltrada.isEmpty()) {
            listaFiltrada = new ArrayList();
        }
        System.out.println("Llego al final del método");
    }

    /**
     * Este método devuelve el atributo listaFiltrada de tipo de dato Rol.
     *
     * @return
     */
    public List<Rol> getListaFiltrada() {
        return listaFiltrada;
    }

    /**
     * Este método entrega el atributo listaFiltrada de tipo de dato rol
     *
     * @param listaFiltrada
     */
    public void setListaFiltrada(List<Rol> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    /**
     * Este método devuelve el atributo rolBeanHelp de tipo de dato
     * RolesBeanHelper.
     *
     * @return
     */
    public RolesBeanHelper getRolBeanHelp() {
        return rolBeanHelp;
    }

    /**
     * Este método entrega el atributo rolBeanHelp de tipo de dato
     * RolesBeanHelper
     *
     * @param rolBeanHelp
     */
    public void setRolBeanHelp(RolesBeanHelper rolBeanHelp) {
        this.rolBeanHelp = rolBeanHelp;
    }

    /**
     * Este método inicializa los atributos rolObj, listaPermisosSelected
     * y ModEl
     */
    public void resetForm() {
        rolObj = null;
        rolObj = new Rol();
        listaPermisosSelected = new ArrayList();
        ModEl = "true";
    }

    /**
     * Este método devuelve el atributo ModEl de tipo de dato String.
     *
     * @return
     */
    public String getModEl() {
        return ModEl;
    }

    /**
     * Este método entrega el atributo ModeEl de tipo de dato String
     *
     * @param ModEl
     */
    public void setModEl(String ModEl) {
        this.ModEl = ModEl;
    }

    /**
     * Este método hace que al momento de seleccionar un ROW en la tabla
     * y se cree un nuevo registro, no se sustituya el rol con el registro
     * nuevo ingresado.
     */
    public void limpiarSección() {
        rolAux.setROLid(null);
        rolAux.setROLtipo("");
        rolAux.setUsuarioList(null);
        rolAux.setRolHasPermisoList(null);
        rolAux.setROLprioridad(null);
    }

    /**
     * Este método valida si se debe eliminar o no el rol
     * de forma que si ya está asignado a un usuario, se espera que no lo pueda
     * eliminar y le arroje un mensaje en pantalla.
     */
    public boolean validarRolEliminado(boolean ban) {
        if (ban) {
            ban = false;
            System.out.println(" Entra a la condición de ban = true... "
                    + "Linea 551 : Esto sale en Rol permisos ID :" + rolAux.getROLid());
            if (rolBeanHelp.getRolesUsuario(rolAux.getROLid()).size() > 0) {
                System.out.println("No se puede eliminar el rol....-------------------");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("El rol ya tiene usuarios asignados", ""));
            } else {
//                    rolBeanHelp.deleteRolHasPermiso("RolHasPermiso", "rol.rolid", String.valueOf(rolEntero));
                Rol aux = rolBeanHelp.getRolUnico(rolEntero);
                rolBeanHelp.eliminarRol(aux);
                nombreRol = null;
                deshabilitar = "false";
                rolEntero = -1;
                rolObj = new Rol();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Se eliminó correctamente.", "Se eliminó correctamente."));
            }
        }
        return ban;
    }

    /**
     * este ciclo pregunta a todos los tipos de roles existentes si son iguales
     * a mayusculas y minusculas al tipo de rol dentro de rolObj y si nuevo está en true
     * de forma que se rellena rolObj y cambia la bandera a banIgual a true
     */
    public boolean validarRolExistente(boolean banIgual) {
        for (Rol rolDel : rolBeanHelp.getRol()) {
            if ((rolDel.getROLtipo().equalsIgnoreCase(rolObj.getROLtipo()))) {
                //Si encuentra el mismo tipo de rol (nombre) y se esta agregando
                if (nuevo == true) {
                    rolObj.setROLid(rolDel.getROLid());
                    rolObj.setROLprioridad(rolDel.getROLprioridad());
                    rolObj.setRolHasPermisoList(rolDel.getRolHasPermisoList());
                    System.out.println("Entre en nuevo" + rolObj.getROLid());
                }
                banIgual = true;
                break;
            }
        }

        return banIgual;
    }

    /**
     * Rellena un rolObj , pero lo hace por medio de la pregunta al rolAux
     * rolAux toma el objeto del evento que ha sido clickeado
     * Genera un rol con el mismo nombre del cual se desea modificar cuando
     * se comenta debajo de la condición.
     */
    public boolean validarRolExistente2(boolean nuevo) {
        System.out.println("Rol Auxiliar= " + rolAux.getROLtipo());
        for (Rol roles : rolBeanHelp.getRol()) {
            if (roles.getROLtipo().equalsIgnoreCase(rolAux.getROLtipo())) {
                rolObj.setROLid(roles.getROLid());
                rolObj.setROLprioridad(roles.getROLprioridad());
                rolObj.setRolHasPermisoList(roles.getRolHasPermisoList());
                System.out.println("Entre en nuevo" + rolObj.getROLtipo());
                break;
            } else {
                nuevo = true;
            }
        }

        return nuevo;
    }

    public boolean validarRolExistente3(boolean nuevo) {
        if (nuevo == true) {
            rolBeanHelp.saveRol(rolObj);
            for (Rol rolDel : rolBeanHelp.getRol()) {
                if (rolDel.getROLtipo().equals(rolObj.getROLtipo())) {
                    rolObj.setROLid(rolDel.getROLid());
                    rolObj.setROLprioridad(rolDel.getROLprioridad());
                    rolObj.setRolHasPermisoList(rolDel.getRolHasPermisoList());
                    System.out.println("Entre en nuevo" + rolObj.getROLid());
                    nuevo = false;
                    break;
                }
            }
        }
        return nuevo;
    }
}

/**
 * Clase que sirve para almacenar datos que se usa en la clase RolesBeanUI.
 * No tiene otra utilidad fuera de la clase antes mencionada.
 */
class DatosRol {
    Integer prioridad = 0;
    List<RolHasPermiso> listaRolHasPermisos;

    public DatosRol(Integer prioridad, List<RolHasPermiso> listaRolHasPermisos) {
        this.prioridad = prioridad;
        this.listaRolHasPermisos = listaRolHasPermisos;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public List<RolHasPermiso> getListaRolHasPermisos() {
        return listaRolHasPermisos;
    }

    public void setListaRolHasPermisos(List<RolHasPermiso> listaRolHasPermisos) {
        this.listaRolHasPermisos = listaRolHasPermisos;
    }

    public void restarPrioridad(int peRvalor) {
        prioridad -= peRvalor;
    }
}