/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Permiso;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Subpermisos;
import mx.avanti.siract.entity.Usuario;

/**
 *
 * @author Axel Valenzuela. Avanti 2018.
 */
public class LoginBeanHelper implements Serializable {

    List<Rol> listaRol = new ArrayList();
    Rol rol;
    Usuario usuario;
    private List<String> listarol;
    List<Rol> rolesID = new ArrayList();

    /**
     *
     */
    public LoginBeanHelper() {
        init();

    }

    /**
     *
     * @param u
     */
    public LoginBeanHelper(Usuario u) {
        init();
        usuario = u;
    }

    /**
     *
     */
    public void init() {
//        rolDelegate = new RolDelegate();
        listarol = new ArrayList<String>();
    }

    /**
     *
     * @return
     */
    public List<Rol> getListaRol() {
//        listaRol = rolDelegate.getRol();
        listaRol = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        return listaRol;
    }

    /**
     *
     * @param listaRol
     */
    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    /**
     *
     * @param listarol
     */
    public void setListarol(List<String> listarol) {
        this.listarol = listarol;
    }

    /**
     *
     * @param u
     * @return
     */
    public List<String> getListarol(Usuario u) {
        u = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorNombre(u.getUSUusuario());
        List<Rol> recibe = null;//Creacion de recibe 

        listarol = new ArrayList();
        if (u.getUSUid() > 0) {
//            recibe = rolDelegate.getRolUser(u.getUsuid());
            recibe = u.getRolList();
        } else {
            recibe =  consultaroles();
        }
        
        for (Rol usu : recibe) {
                listarol.add(usu.getROLtipo());
        }
        
        return listarol;
        
    }

    /**
     * 
     * @return
     */
    public Usuario GuardarUsuario() {
        List<Rol> recibe;//Creacion de recibe 
        recibe = consultaroles();//Tomamos el valor que contiene rols2
        usuario.setRolList(recibe);
        return usuario;// y retornamos el objeto preparado para guardar

    }

    /**
     * Metodo que consulta los roles
     * @return regresa los roles encontrados
     */
    public List<Rol> consultaroles() {//Ciclo para consultar los roles
        List<Rol> rols;//creacion de lista rols
        List<Rol> rols2 = new ArrayList<Rol>();//creacion de lista rols2
//        rols = rolDelegate.getRol();//Consulta de roles en la tabla rol
        rols = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        for (int i = 0; i < listarol.size(); i++) {  //recorriendo el tamaño para obtener el dato de rols en cada posicion      
            for (int x = 0; x < rols.size(); x++) {// recorriendo el tamaño de rols
                if (rols.get(x).getROLtipo().equals(listarol.get(i))) {//comparacion para saber cuales roles son los que se elijen para agregar 
                    rols2.add(rols.get(x));//se prepara el objeto rols2 con la informacion de los datos que se mandaron
                }
            }
        }
        return rols2;//regresado rols2
    }

    List<Usuario> listaFiltrada;

    /**
     *
     * @param busqueda
     * @return
     */
    public List<Usuario> filtrado(String busqueda) {
        busqueda = busqueda.toLowerCase();
//        listaFiltrada = usuDel.getUsuario();
        listaFiltrada = ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
        if (busqueda.trim().length() > 0) {
            listaFiltrada.clear();
//            for (Usuario us : usuDel.getUsuario()) {
            for (Usuario us : ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios()) {
                String usuarioT = us.getUSUusuario().toLowerCase();
                if (usuarioT.startsWith(busqueda)) {
                    if (!listaFiltrada.contains(us)) {
                        listaFiltrada.add(us);
                    }
                }
//                for (Rol rolUsB : rolDelegate.getRolUser(us.getUsuid())) {
                for (Rol rolUsB : us.getRolList()) {
                    String rolTipo = rolUsB.getROLtipo().toLowerCase();
                    if (rolTipo.startsWith(busqueda)) {
//                        System.out.println("Rol para comparar" + rolUsB.getRoltipo());
                        if (!listaFiltrada.contains(us)) {
                            listaFiltrada.add(us);
                        }
                    }
                }
//                Profesor prof = profDel.profIDUs(us.getUsuid());
                Profesor prof = ServiceFacadeLocator.getInstanceProfesorFacade().buscarProfesorPorUsuario(us.getUSUid());
                String[] nombreProf;
                String nombProf;
                if (prof != null) {
                    nombProf = prof.getPROnombre() + " " + prof.getPROapellidoPaterno() + " " + prof.getPROapellidoMaterno();
                    nombProf = nombProf.toLowerCase();
                    nombreProf = nombProf.split(" ");
                    for (int i = 0; i < nombreProf.length; i++) {
                        if (nombreProf[i].startsWith(busqueda)) {
                            if (!listaFiltrada.contains(us)) {
                                listaFiltrada.add(us);
                            }
                        } else {
                            if (prof.getPROnombre().startsWith(busqueda)) {
                                if (!listaFiltrada.contains(us)) {
                                    listaFiltrada.add(us);
                                }
                            }
                        }
                    }
                    if (String.valueOf(prof.getPROnumeroEmpleado()).startsWith(busqueda)) {
                        if (!listaFiltrada.contains(us)) {
                            listaFiltrada.add(us);
                        }
                    }
                }

            }

        }
        return listaFiltrada;
    }

    /////nuevos metodos que se llaman desde el LoginBean.
    //Creados por Axel Valenzuela. Avanti 2018.

    /**
     *
     * @param rol
     * @return
     */
    public List<Rol> getObtenerRol(int rol) {
        return ServiceFacadeLocator.getInstanceRolFacade().buscarPorUsuario(rol);
    }

    /**
     *
     * @param usuario
     * @return
     */
    public Usuario getUsuario(Usuario usuario) {
        return ServiceFacadeLocator.getInstanceUsuarioFacade().login(usuario);
        
    }

    /**
     *
     * @param rolID
     * @param permID
     * @return
     */
    public List<Subpermisos> getSubPermiso(int rolID, int permID) {
        return ServiceFacadeLocator.getInstanceSubPermisoFacade().buscarPorRolYPermiso(rolID, permID);
    }


    /**
     *
     * @param rol
     * @return
     */
    public List<Permiso> getPermiso(int rol) {
        return ServiceFacadeLocator.getInstancePermisoFacade().buscarPermisosPorRol(rol);
    }
    
    public Rol getRolByRoltipo(String roltipo){
        return ServiceFacadeLocator.getInstanceRolFacade().getRolIdByRoltipo(roltipo);
    }
    
    public Rol getRolById(int rolId){
        return ServiceFacadeLocator.getInstanceRolFacade().getRolById(rolId);
    }
        
}
