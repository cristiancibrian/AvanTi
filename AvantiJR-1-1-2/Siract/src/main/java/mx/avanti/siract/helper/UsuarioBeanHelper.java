/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author balta
 */
public class UsuarioBeanHelper implements Serializable {

    List<Rol> listaRol = new ArrayList();
    Rol rol;
    Usuario usuario;
    List<Rol> rolesID = new ArrayList();
    private Usuario seleccionUsuario;
    private List<Usuario> listaFiltrada;
    private List<Usuario> filtroUsuario;
    private List<String> listarol;

    public UsuarioBeanHelper() {
        init();
    }

    public UsuarioBeanHelper(Usuario u) {
        init();
        usuario = u;

    }

    public void init() {
        listarol = new ArrayList<String>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getSeleccionUsuario() {
        return seleccionUsuario;
    }

    public void setSeleccionUsuario(Usuario seleccionUsuario) {
        this.seleccionUsuario = seleccionUsuario;
    }

    public void seleccionar() {
        System.out.println("seleccion: " + seleccionUsuario.getUSUid());
    }

    /**
     * Metodo que filtra por roles la lista de usuarios
     *
     * @param rolFiltro
     * @param busqueda
     * @return
     */
    public List<Usuario> filtrado(Rol rolFiltro, String busqueda) {
        // lleno la lista con todos los usuarios
        listaFiltrada = ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
        List<Usuario> listaTemporal = new ArrayList();
        // si entro aqui significa que no hay rol seleccionado y filtrara
        // la busqueda en todos los usuarios
        if (rolFiltro.getROLid() == 0) {
            for (Usuario us : listaFiltrada) {
                if (us.getUSUusuario().contains(busqueda)) {
                    listaTemporal.add(us);
                }
            }
            listaFiltrada = listaTemporal;

        }
        // aqui si hay un rol seleccionado y solo buscara en la lista de 
        // usuarios que tengan el rol seleccionado
        else {
            Rol rol = ServiceFacadeLocator.getInstanceRolFacade().consultaRolPorID(rolFiltro.getROLid());

            listaFiltrada = rol.getUsuarioList();

            if (!busqueda.isEmpty()) {
                for (Usuario us : listaFiltrada) {
                    if (us.getUSUusuario().contains(busqueda)) {
                        listaTemporal.add(us);
                    }
                }
                listaFiltrada = listaTemporal;
            }

        }
        return listaFiltrada;
    }

    public List<Rol> getListaRol() {
        // listaRol = rolDelegate.getRol();
        listaRol = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        //// getListaRolfindFromWhere("usuarios", "usuid", "1");
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }
///////////////////// Copia de métodos de CapturaUsuarioBeanHelper/////////////////
//consulta los roles especificos para cada usuario

    /**
     * Metodo que consulta los roles que se encuentran en la lista de roles
     *
     * @param u
     * @return
     */
    public List<String> getListarol(Usuario u) {
        List<Rol> recibe = null;//Creacion de recibe 

        listarol = new ArrayList();
        if (u.getUSUid() > 0) {

            //recibe = rolDelegate.getRolUser(u.getUsuid());
            recibe = ServiceFacadeLocator.getInstanceRolFacade().buscarPorUsuario(u.getUSUid());
            for (Rol usu : recibe) {

                listarol.add(usu.getROLtipo());
            }

        } else {
            recibe = consultaroles();
            for (Rol usu : recibe) {

            }
        }
        return listarol;
    }

    public void setListarol(List<String> listarol) {
        this.listarol = listarol;
    }

    public Usuario GuardarUsuario() {
        List<Rol> recibe;//Creacion de recibe 
        recibe = consultaroles();//Tomamos el valor que contiene rols2
        usuario.setRolList(recibe);
        return usuario;// y retornamos el objeto preparado para guardar

    }

    /**
     * Metodo que consulta todos los roles
     *
     * @return
     */
    public List<Rol> consultaroles() {//Ciclo para consultar los roles
        List<Rol> rols;//creacion de lista rols
        List<Rol> rols2 = new ArrayList<Rol>();//creacion de lista rols2
        //rols = rolDelegate.getRol();//Consulta de roles en la tabla rol
        rols = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();//Consulta de roles en la tabla rol
        for (int i = 0; i < listarol.size(); i++) {  //recorriendo el tamaño para obtener el dato de rols en cada posicion      
            for (int x = 0; x < rols.size(); x++) {// recorriendo el tamaño de rols
                if (rols.get(x).getROLtipo().equals(listarol.get(i))) {//comparacion para saber cuales roles son los que se elijen para agregar 
                    rols2.add(rols.get(x));//se prepara el objeto rols2 con la informacion de los datos que se mandaron
                }
            }
        }
        return rols2;//regresado rols2
    }

    /* metodos traidos de facades para correcto llamado en bean ui.
    reestructuracion 2018 */

    public List<Usuario> getUsuarios() {

        return ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
    }

    public Profesor profIDUs(int idUsuario) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarProfesorPorUsuario(idUsuario);
    }

    public Usuario usuUnic(int usID) {


        return ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usID);
    }

    /**
     * Metodo para guardar un usuario
     *
     * @param usuario
     */
    public void saveUsuario(Usuario usuario) {
        ServiceFacadeLocator.getInstanceUsuarioFacade().agregarUsuario(usuario);
    }

    /**
     * Metodo para eliminar un usuario
     *
     * @param usuario
     */
    public void eliminarUsuario(Usuario usuario) {
        ServiceFacadeLocator.getInstanceUsuarioFacade().eliminarUsuario(usuario);
    }

    /**
     * Metodo para modificar un usuario
     *
     * @param usuario
     */
    public void updateUsuario(Usuario usuario) {
        ServiceFacadeLocator.getInstanceUsuarioFacade().actualizarUsuario(usuario);
    }

    /**
     * Metodo que consulta un profesor especificamente por su usuario
     *
     * @param idUsuario Identificador de numero de usuario
     * @return Retorna un objeto con los valores del profesor encontrado
     */
    public Profesor findProfesorByUser(int idUsuario) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarProfesorPorUsuario(idUsuario);
    }

    /**
     * Metodo que consulta un usuario especifico
     *
     * @param id Identificador del usuario
     * @return Retorna un objeto con los valores del usuario encontrado
     */
    public Usuario buscarUsuarioPorID(int id) {
        return ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(id);
    }

    /**
     * Metodo que modifica un rol
     *
     * @param rol Objeto con los valores a modificar
     */
    public void modicarRol(Rol rol) {
        ServiceFacadeLocator.getInstanceRolFacade().modificarRol(rol);
    }

    /**
     * Metodo que valida si un usuario esta repetido
     *
     * @param nombre Nombre de usuario a consultar
     * @return Retorna verdadero si se encuentra repetido
     */
    public boolean validarUsuarioRepetido(String nombre) {
        boolean res = false;
        Usuario us = new Usuario();
        us = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorNombre(nombre);
        if (isNull(us)) {
            res = false;
        } else {
            if (us.equals(new Usuario()))
                res = false;
            else
                res = true;
        }
        return res;
    }
}
