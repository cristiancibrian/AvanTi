/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;

import mx.avanti.siract.entity.Catalogoreportes;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.helper.LoginBeanHelper;
import mx.avanti.siract.helper.UsuarioBeanHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Example fran v2
 *
 * @author kitto
 */
@ManagedBean
@ViewScoped
public class UsuariosBeanUI implements Serializable {

    private final int IDCATALOGOADMIUSUARIOS = 4;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private UsuarioBeanHelper usuarioBeanHelper = new UsuarioBeanHelper();
    private Usuario usuario = new Usuario();
    private Usuario logueado = new Usuario();
    private Usuario selectedUsuario;
    private List<Usuario> selectedUsuarios;
    private List<String> obtenerRoles;//aqui se guardan los roles que seleccione en la alta al usuario
    private List<Rol> obtenerListaRoles;
    private String boton = "true";
    private String busqueda;
    private LoginBeanHelper logBeanHelp;
    private List<Usuario> listaFiltrada;
    private boolean multiSeleccion = true;
    private boolean eliminarono = false;
    private boolean eliminaa = false;
    private int idUsu = -1;
    private String deshabilitar;
    private String header;
    private String dialogo;
    private String usuariose;
    private Rol rolFiltro; // usuario para filtrar por rol
    private List<Rol> rolesFiltro;// lista para filtrar por rol
    private String mensaje;
    private boolean confirmDialogOpen = false;

    public UsuariosBeanUI() {

        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
        selectedUsuario = new Usuario();
        init();
    }

    public boolean isConfirmDialogOpen() {
        return confirmDialogOpen;
    }

    public void setConfirmDialogOpen(boolean confirmDialogOpen) {
        this.confirmDialogOpen = confirmDialogOpen;
    }

    public int getIDCATALOGOADMIUSUARIOS() {
        return IDCATALOGOADMIUSUARIOS;
    }

    @PostConstruct
    private void init() {
        usuarioBeanHelper = new UsuarioBeanHelper();
        busqueda = "";
        logBeanHelp = new LoginBeanHelper();
        listaFiltrada = usuarioBeanHelper.getUsuarios();
        usuario = new Usuario();
        rolFiltro = new Rol();
        rolFiltro.setROLid(0);
        rolesFiltro = usuarioBeanHelper.getListaRol();
    }

    /**
     * Metodo que limpia el valor de la variable boton
     */
    public void refrescarForma2() {

        if (selectedUsuarios.size() == 1 && header.equals("Modificar usuario")) {
            System.out.println("llegue a esa zona");
        } else {
            if (selectedUsuarios.size() == 1 && header.equals("Eliminar usuario")) {
                boton = "true";
            }
        }
    }

    /**
     *
     */
    /**
     * Metodo para limpiar las variables que se utilizan en la pagina
     */
    public void refrescarForma() {

        selectedUsuario = new Usuario();
        selectedUsuario.setUSUid(0);
        usuario = new Usuario();
        setSelectedUsuarios(null);
        obtenerRoles = null;
        System.out.println("U setter null");
        boton = "true";
        busqueda = "";
        List<Rol> list = null;
        list = loginBean.getLogueado().getRolList();
        String seleccionado = loginBean.getSeleccionado();
        System.out.println(seleccionado + "ÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑ");
        String catalogo = "Administración de cuenta de usuario";
        loginBean.TienePermiso(list, seleccionado, catalogo);

        System.out.println("id usuario: " + usuario.getUSUid());
        System.out.println("id usuario: " + usuario.getUSUusuario());
    }

    /**
     * Metodo que filtra a los diferentes usuarios por rol
     */
    /**
     * Metodo que filtra la lista de usuarios a la hora de realizar busqueda
     * especifica por nombre de usuario
     */
    public void filtrado() {
        System.out.println("Si entra<<<<<<<<<<<<<<<");
        listaFiltrada = usuarioBeanHelper.filtrado(rolFiltro, busqueda);
        if (listaFiltrada.isEmpty()) {
            listaFiltrada = new ArrayList();
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Metodo que cambia el nombre de la cabecera
     *
     * @param i Valor de entrada que equivale a la opcion que entro
     * @return Regresa la cabecera con el nuevo nombre
     */
    public String cabecera(int i) {
        if (i == 1) {
            header = "Agregar usuario";
            deshabilitar = "false";
        }
        if (i == 2) {
            header = "Eliminar usuario";
            deshabilitar = "true";
        }
        if (i == 3) {
            header = "Modificar usuario";
            deshabilitar = "false";
        }
        return header;
    }

    /**
     * Metodo que prepara clases antes crear un nuevo usuario
     */
    public void nuevo() {
        System.out.println("id usuario: " + usuario.getUSUid());
        System.out.println("id usuario: " + usuario.getUSUusuario());
        multiSeleccion = false;
        idUsu = -1;
//        capturaUsuarioBeanHelper.setUsuario(u);
        usuario = new Usuario();
        obtenerRoles = new ArrayList();
        selectedUsuarios = new ArrayList();
        selectedUsuario = new Usuario();
        deshabilitar = "false";
        //logBeanHelp.getUsuDel().saveUsuario(u);
        cabecera(1);
    }

    /**
     * Metodo que prepara las clases antes de eliminar un usuario
     *
     * @return
     */
    public void eliminar() {
        cabecera(2);
        if (selectedUsuarios.size() > 1) {
            multiSeleccion = true;
            usuario = selectedUsuarios.get(0);
        } else {
            multiSeleccion = false;
        }
    }

    /**
     * Metodo que prepara las clases antes de modificar un usuario
     */
    public void modificar() {
        ////Usuario usuario = new Usuario();
        PrimeFaces.current().executeScript("PF('dlg').show()");
        System.out.println("id usuario: " + usuario.getUSUid());
        System.out.println("id usuario: " + usuario.getUSUusuario());

        if (selectedUsuarios.size() > 1) {
            multiSeleccion = true;
            usuario = selectedUsuarios.get(0);
        } else {
            multiSeleccion = false;
        }

        if (selectedUsuarios.size() == 1) {
            usuario = selectedUsuarios.get(0);
            System.out.println(usuario.getUSUid() + "<><><><>>><>>>>>><><>><>>");
            idUsu = usuario.getUSUid();
        }

        cabecera(3);
        dialogo = "confirmation.show()";
        idUsu = usuario.getUSUid();
        List<Usuario> l;
        l = selectedUsuarios;
        setBusqueda("");
    }

    /**
     * Metodo que obtiene valores de los roles
     *
     * @return
     */
    public List<String> getObtenerRoles() {

        //Se obtienen los roles del helper, si U no es nulo va a y consulta
        //especificamente del objeto guardad en u pero si u es nulo consulta
        //todos los roles guardados en la base de datos.
        if (obtenerRoles == null) {
            obtenerRoles = new ArrayList<>();
        } else {
            obtenerRoles.clear();
        }

        if (usuario != null && usuario.getRolList() != null) {
            for (Rol rol : usuario.getRolList()) {
                obtenerRoles.add(rol.getROLtipo());
            }
        }

        return obtenerRoles;
    }

    public void setObtenerRoles(List<String> obtenerRoles) {
        this.obtenerRoles = obtenerRoles;
    }

    /**
     * Metodo que mantiene el manejo de la tabla
     *
     * @param event
     * @return
     */
    public String onRowSelect(SelectEvent event) {

        boton = "false";
        usuario = new Usuario();
        usuario = selectedUsuario;
        usuario = (Usuario) event.getObject();
        usuariose = usuario.getUSUusuario();

        System.out.println(usuario.getUSUid() + "______________________-modificar");
        System.out.println(usuario.getUSUid() + "______________________-modificar");

        setUsuariose(usuariose);

        return usuariose;
    }

    /**
     * Metodo para el manejo de la tabla
     *
     * @param event
     */
    public void onRowUnselect(UnselectEvent event) {

        if (selectedUsuarios.size() > 0) {
            boton = "false";
            System.out.println("+++++++++++++");
        } else {
            boton = "true";
            System.out.println("------------");
        }
        usuario = new Usuario();
        if (selectedUsuarios.size() == 1) {
            usuario = selectedUsuarios.get(0);
            System.out.println(" fui igual a 1 ");
            System.out.println(usuario.getUSUusuario() + "______________________-modificar");
            System.out.println(usuario.getUSUid() + "______________________-modificar");
        }

    }

    /**
     * Metodo para el manejo de la tabla
     *
     * @param event
     * @return
     */
    public String onToggleselect(ToggleSelectEvent event) {

        if (boton == "false" && selectedUsuarios.size() == 0) {
            boton = "true";
            return boton;
        }
        if (boton == "true" && selectedUsuarios.size() > 0) {
            boton = "false";
            return boton;
        }
        return boton;

    }

    /**
     * Metodo que llena la columna de Profesores de la tabla
     *
     * @param usuario
     * @return
     */
    public String profDeUsu(Usuario usuario) {
        Profesor usProfesor = (usuario.getProfesorList().size() > 0) ? usuario.getProfesorList().get(0) : null;
        if (usProfesor != null) {
            return usProfesor.getPROnumeroEmpleado() + " - " + usProfesor.getPROnombre() + " " + usProfesor.getPROapellidoPaterno() + " " + usProfesor.getPROapellidoMaterno() + ".";
        } else {
            return "No tiene un profesor asignado.";
        }
    }

    /**
     * Metodo que llena la columna de Roles de la tabla
     *
     * @param usuRol
     * @return
     */
    public String rolesStrList(Usuario usuRol) {
        List<Rol> roles = usuRol.getRolList();
        String rolsUsuEsp = "";

        if (roles.isEmpty()) {
            return "No tiene roles asignados.";
        } else {
            for (int i = 0; i < roles.size(); i++) {
                if (i == (roles.size()) - 1) {
                    rolsUsuEsp += roles.get(i).getROLtipo() + ".";
                } else {
                    rolsUsuEsp += roles.get(i).getROLtipo() + ", ";
                }
            }
            return rolsUsuEsp;

        }

    }

    /**
     *
     */
    public void selectMultipleUsuario() {

        selectedUsuario = usuarioBeanHelper.usuUnic(selectedUsuario.getUSUid());
        idUsu = selectedUsuario.getUSUid();
        usuario = selectedUsuario;
        System.out.println("Usuario selected en el select " + usuario.getUSUid());

    }

    /**
     * Metodo que gestiona las altas, modificaciones y eliminaciones.
     */
    public void onClickSubmit() {
        String result = "UsuarioGuardado";
        switch (header) {
            case "Agregar usuario":
                agregarUsuario();
                break;
            case "Modificar usuario":
                modificarUsuario();
                break;
            case "Eliminar usuario":
                if (isConfirmDialogOpen()) {
                    eliminarComfirm();
                } else if (!isConfirmDialogOpen() && !selectedUsuarios.isEmpty()) {
                    PrimeFaces.current().executeScript("PF('confirmdlg').show()");
                    setConfirmDialogOpen(true);
                } else {
                    mostrarMensaje("No hay usuario para eliminar.");
                    setConfirmDialogOpen(false);
                }
                break;
            default:
                mostrarMensaje("No se pudo realizar la acción.");
        }
        listaFiltrada = usuarioBeanHelper.getUsuarios();
    }

    private void agregarUsuario() {

        //Establecemos listas nuevas para la asignación del nuevo rol.
        List<Rol> listaRoles = new ArrayList<Rol>();

        if (!isCamposVacios()) {
            if (!isUsuarioExiste()) {
                //Obtenemos los roles seleccionados.
                listaRoles = obtenerRolesSeleccion(obtenerRoles);

                //Establecemos los valores al usuario.
                usuario.setRolList(listaRoles);
                usuario.setUSUcontrasenia(DigestUtils.md5Hex(usuario.getUSUcontrasenia()));

                //Establecemos listas nuevas al usuario para que no se queden en NULL.
                usuario.setProfesorList(new ArrayList<Profesor>());
                usuario.setCatalogoreportesList(new ArrayList<Catalogoreportes>());

                //Se guarda el nuevo usuario.
                usuarioBeanHelper.saveUsuario(usuario);
                mostrarMensaje("El usuario se guardo correctamente.");
            } else {
                mostrarMensaje("Ese usuario ya existe.");
            }
        }
        restablecerValores();
    }

    private void modificarUsuario() {

        //Declaración de objetos nuevos.
        List<Usuario> listaTemporalUsuariosSeleccionados = selectedUsuarios;
        List<Rol> listaRoles = new ArrayList<Rol>();
        Usuario usuarioModificar = new Usuario();

        //Obtenemos los roles seleccionados.
        listaRoles = obtenerRolesSeleccion(obtenerRoles);

        if (listaTemporalUsuariosSeleccionados.size() == 1) {
            usuarioModificar = selectedUsuarios.get(0);
        } else {
            usuarioModificar = listaTemporalUsuariosSeleccionados.get(obtenerSeleccionado());
        }

        //Validamos si el nombre de usuario a modificar ya existe para otro usuario, si es este mismo usuario se ignora.
        if (!isUsuarioExiste(usuarioModificar.getUSUid())) {
            if (usuarioModificar.getUSUcontrasenia().equalsIgnoreCase(usuario.getUSUcontrasenia())) {
                usuarioModificar.setUSUcontrasenia(DigestUtils.md5Hex(usuario.getUSUcontrasenia()));
            }

            //Establecemos los datos al usuario a modificar.
            usuarioModificar.setUSUusuario(usuario.getUSUusuario());
            usuarioModificar.setRolList(listaRoles);

            //Se actualiza el usuario.
            usuarioBeanHelper.updateUsuario(usuarioModificar);
            mostrarMensaje("Usuario modificado correctamente.");

            usuario = usuarioModificar;
        }
        refrescarDatosModificar();
    }

    private Integer obtenerSeleccionado() {
        for (Integer x = 0; x < selectedUsuarios.size(); x++) {
            if (usuario.getUSUid().equals(selectedUsuarios.get(x).getUSUid())) {
                return x;
            }
        }
        return null;
    }

    private void restablecerValores() {
        usuario = new Usuario();
        obtenerRoles = new ArrayList<String>();
        selectedUsuarios = new ArrayList<Usuario>();
    }

    /**
     * Método que retorna un TRUE si falta escribir un nombre, una contraseña y
     * seleccionar los roles. Retorna FALSE si no falta ningun dato por escribir
     * y seleccionar roles.
     *
     * @return
     */
    private boolean isCamposVacios() {
        //Verifica si el campo de usuario esta vacío.
        if (usuario.getUSUusuario().isEmpty()) {
            //Verifica si el campo de contraseña esta vacío.
            if (usuario.getUSUcontrasenia().isEmpty()) {
                //Verifica si se seleccionaron roles.
                if (obtenerRoles.isEmpty()) {
                    mostrarMensaje("Favor de introducir un nombre de usuario, contraseña y seleccionar roles.");
                    return true;
                }
                mostrarMensaje("Favor de introducir un nombre de usuario y una contraseña.");
                return true;
            } else if (obtenerRoles.isEmpty()) {
                mostrarMensaje("Favor de introducir un nombre de usuario y seleccionar roles.");
            }
            mostrarMensaje("Favor de introducir el nombre de usuario.");
            return true;
            //El campo de usuario no esta vacio, ahora verifica si campo contraseña esta vacio y si se selecionaron roles.
        } else if (usuario.getUSUcontrasenia().isEmpty()) {
            //Verifica si se seleccionaron roles.
            if (obtenerRoles.isEmpty()) {
                mostrarMensaje("Favor de introducir una constraseña y seleccionar roles.");
                return true;
            }
            mostrarMensaje("Favor de introducir una constraseña.");
            return true;
            //Si campo de nombre y campo de contraseña no esta vacío, ahora verifica si se selecionaron roles.
        } else if (obtenerRoles.isEmpty()) {
            mostrarMensaje("Favor de seleccionar roles.");
            return true;
        }
        return false;
    }

    /**
     * Método que sirve para validar si el nombre del usuario que se quiere
     * agregar ya existe en la base de datos.
     *
     * @return
     */
    private boolean isUsuarioExiste() {
        for (Usuario us : usuarioBeanHelper.getUsuarios()) {
            if (us.getUSUusuario().equalsIgnoreCase(usuario.getUSUusuario())) {
                mostrarMensaje("Ese nombre de usuario ya existe.");
                return true;
            }
        }
        return false;
    }

    /**
     * Método que sirve para validar si el nombre de usuario a modificar ya
     * existe para otro usuario, si se mantiene el antiguo nombre se ignora.
     *
     * @param usuarioId
     * @return
     */
    private boolean isUsuarioExiste(Integer usuarioId) {
        for (Usuario us : usuarioBeanHelper.getUsuarios()) {
            if (us.getUSUusuario().equalsIgnoreCase(usuario.getUSUusuario())
                    && !us.getUSUid().equals(usuarioId)) {
                mostrarMensaje("Ese nombre de usuario ya esta en usó.");
                return true;
            }
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
         PrimeFaces.current().ajax().update("frmUsuarios:msgs");
    }

    private List<Rol> obtenerRolesSeleccion(List<String> roles) {

        List<Rol> listaRol = new ArrayList<Rol>();

        for (String rolesDelUsuario : roles) {
            for (Rol listRolAll : obtenerListaRoles) {
                if (listRolAll.getROLtipo().equalsIgnoreCase(rolesDelUsuario)) {
                    listaRol.add(listRolAll);
                }
            }
        }

        return listaRol;
    }

    private void refrescarDatosModificar() {
        usuario = usuarioBeanHelper.buscarUsuarioPorID(usuario.getUSUid());
        obtenerListaRoles = usuario.getRolList();
    }

    /**
     * Metodo que valide que no elimines a un usuario que esta logeado o que ya
     * contenga un profesor asignado
     *
     * @param usuario Objeto con los valores del usuario a validar
     * @return Mensaje de error en caso de que no cumpla
     */
    public boolean validacionEliminar(Usuario usuario) {
        boolean resultado = true;

        mensaje = "Usuario eliminado correctamente";
        if (loginBean.getLogueado().getUSUusuario().equals(usuario.getUSUusuario())) {

            mensaje = "No puedes eliminar el usuario que esta logueado";
            return false;
        }
        if (usuario.getProfesorList().size() > 0) {
            mensaje = "No puedes eliminar el usuario ya esta asignado a un profesor";
            return false;
        }
        return resultado;

    }
    ////////////// Altas Bajas Modificaciones

    /**
     * Metodo que elimina un usario
     *
     * @param usuario Objeto con los valores del usuario a eliminar
     */
    public void eliminarUsuario(Usuario usuario) {
        usuarioBeanHelper.eliminarUsuario(usuario);
    }

    public void eliminarComfirm() {

        if (selectedUsuarios.size() == 1) {
            Usuario us = selectedUsuarios.get(0);
            if (validacionEliminar(us)) {
                eliminarUsuario(us);
                mostrarMensaje(mensaje);
                selectedUsuarios.remove(us);
                setConfirmDialogOpen(false);
                restablecerValores();
            } else {
                mostrarMensaje(mensaje);
            }
        } else {
            Usuario us = usuarioBeanHelper.buscarUsuarioPorID(selectedUsuario.getUSUid());
            if (validacionEliminar(us)) {
                eliminarUsuario(us);
                mostrarMensaje(mensaje);
                selectedUsuarios.remove(us);
                usuario = selectedUsuarios.get(0);
                obtenerListaRoles = selectedUsuarios.get(0).getRolList();
            } else {
                mostrarMensaje(mensaje);
                selectedUsuarios.remove(us);
            }

        }
    }

    //////////////get and set
    public Usuario getUsuario() {
        System.out.println(usuario.getUSUcontrasenia() + "contraseñaaaa");
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Usuario> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public String getUsuariose() {

        return usuariose;
    }

    public void setUsuariose(String usuariose) {
        this.usuariose = usuariose;
    }

    public void setEliminaa(boolean eliminaa) {
        this.eliminaa = eliminaa;
    }

    ////banderas
    public void banderaeliminar() {
        eliminaa = true;
        onClickSubmit();
    }

    public boolean isEliminarono() {
        if (eliminaa == true) {

            eliminarono = true;
            setEliminarono(true);
        }

        return eliminarono;
    }

    public void setEliminarono(boolean eliminarono) {
        this.eliminarono = eliminarono;
    }

    public String getDialogo() {
        return dialogo;
    }

    public void setDialogo(String dialogo) {
        this.dialogo = dialogo;
    }

    public String getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public boolean isMultiSeleccion() {
        return multiSeleccion;
    }

    public void setMultiSeleccion(boolean multiSeleccion) {
        this.multiSeleccion = multiSeleccion;
    }

    public List<Rol> getObtenerListaRoles() {
        obtenerListaRoles = usuarioBeanHelper.getListaRol();
        return obtenerListaRoles;
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public List<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(List<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Rol getRolFiltro() {
        return rolFiltro;
    }

    public void setRolFiltro(Rol rolFiltro) {
        this.rolFiltro = rolFiltro;
    }

    public List<Rol> getRolesFiltro() {
        return rolesFiltro;
    }

    public void setRolesFiltro(List<Rol> rolesFiltro) {
        this.rolesFiltro = rolesFiltro;
    }

    public void onClickConfirm() {
        banderaeliminar();
        if (selectedUsuarios.size() > 1) {
            PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
        }
    }
}
