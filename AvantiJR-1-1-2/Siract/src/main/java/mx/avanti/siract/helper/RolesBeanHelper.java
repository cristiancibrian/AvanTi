/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Avanti 2018.
 */
public class RolesBeanHelper implements Serializable {

    Rol rol;
    Usuario usuario;
    private List<String> listarol = new ArrayList();
    List<Rol> rolesID = new ArrayList();

    /**
     *Este método devuelve la información traída por el método:consultarpermiso
     * que sería una lista del tipo de dato Permiso
     * @return
     */
    public List<Permiso> getPermiso() {
        return ServiceFacadeLocator.getInstancePermisoFacade().consultaGeneralPermiso();
    }

    /**
     *Este método devuelve la información traída por el método:
     * consultaGralrpermiso. Que sería una lista del tipo de dato SubPermisos
     * @return
     */
    public List<Subpermisos> getSubPermiso() {
        return ServiceFacadeLocator.getInstanceSubPermisoFacade().consultaGeneralSubpermiso();
    }

    /**
     *Este método devuelve la información traída por el método:
     * DeleteRolHasPermiso.
     * Que le permitiría eliminar mediante el uso de los parámetros enviados
     * @param clase
     * @param campo
     * @param criterio
     */
//    public void deleteRolHasPermiso(String clase, String campo, String criterio) {
//        ServiceFacadeLocator.getInstanceRolHasPermisoFacade().eliminarRolHasPermiso(clase, campo, criterio);
//    }

    /**
     *Este método devuelve un objeto ROL por medio de búsqueda única de ID.
    * @param rolid
     * @return
     */
    public Rol getRolUnico(int rolid) {
        return ServiceFacadeLocator.getInstanceRolFacade().consultaRolPorID(rolid);
    }

    /**
     *Este método ejecuta con la información traída por el método:eliminarRol
     * una eliminación por medio del atributo rol de tipo de dato Rol
     * @param rol
     */
    public void eliminarRol(Rol rol) {
        ServiceFacadeLocator.getInstanceRolFacade().eliminarRol(rol);
    }

    /**
     *Este método ejecuta una consulta general y devuelve un tipo de lista 
     * del tipo rol.
     * @return
     */
    public List<Rol> getRol() {
      return ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
    }

    /**
     * Este método ejecuta el guardado del atributo rol de tipo de dato Rol
     * @param rol
     */
    public void saveRol(Rol rol) {
        ServiceFacadeLocator.getInstanceRolFacade().agregarRol(rol);
     }

    /**
     *Este método ejecuta el gurdado del rol que tiene permiso y devuelve el 
     * el atributo rolHasPermiso de tipo de dato RolHasPermiso.
     * @param rolHasPermiso
     */
    public void saveRolHasPermiso(RolHasPermiso rolHasPermiso) {
        ServiceFacadeLocator.getInstanceRolHasPermisoFacade().agregarRolHasPermiso(rolHasPermiso);
    }

    /**
     *Este método ejecuta una actualización mediante el método: updateRol
     * y utiliza el parámetro rol que es un tipo de dato Rol
     * @param rol
     */
    public void updateRol(Rol rol) {
        ServiceFacadeLocator.getInstanceRolFacade().modificarRol(rol);
    }

    /**
     *Este método ejecuta una búsqueda específica para el Id del usuario que se
     * le envió. Devuelve una lista de tipo Permiso.
     * @param userid
     * @return
     */
//    public List<Permiso> getPermisoUser(int userid) {
//        return ServiceFacadeLocator.getInstancePermisoFacade().(userid);
//    }

    /**
     * Este método ejecuta una consulta de SubPermisos con los parámetros
     * enviados y devuelve una lista de subpermisos.
     * @param rolID
     * @param permID
     * @return
     */
//    public List<Subpermisos> getSubPermisoID(int rolID, int permID) {
//        Permiso permiso= new Permiso();
//        permiso = ServiceFacadeLocator.getInstancePermisoFacade().consultaPermisoPorID(permID);
//        return permiso.
//        return ServiceFacadeLocator.getInstanceSubPermisoFacade().ConsultaSubpermisos(rolID, permID);
//    }

    /**
     * Este método utiliza una lista de tipo Permiso llamada recibe
     * que va a recibir al usuario que tenga el rol seleccionado. De no estar
     * vacía, el método getListaPerm devuelve una lista de tipo String, con los 
     * permisos y subpermisos. De estar vacía va a devolver una consulta general
     * @param rolSelected
     * @return
     */
//    public List<String> getListaPerm(Rol rolSelected) {
//        List<Permiso> recibe = null;//Creacion de recibe 
//        listarol = new ArrayList();
//        if (rolSelected.getROLid()!= null) {
//            System.out.println("El Rol es: " + rolSelected.getROLtipo());
////            recibe = permisoDelegete.getPermisoUser(rolSelected.getRolid());
//////            recibe = ServiceFacadeLocator.getInstancePermisoFacade().getPermisoUser(rolSelected.getROLid());
////            if (!recibe.isEmpty()) {
////                for (Permiso perm : recibe) {
//////                    for (Subpermisos subp : spDel.getPermisoID(rolSelected.getRolid(), perm.getPerid())) {
//////                    for (Subpermisos subp : ServiceFacadeLocator.getInstanceSubPermisoFacade().ConsultaSubpermisos(rolSelected.getRolid(), perm.getPerid())) {
//////                        listarol.add(perm.getPertipo() + " - " + subp.getSPERtipo());
//////                    }
////                }
////            }
//      //  } else {
////            recibe = permisoDelegete.getPermiso();
////            recibe = ServiceFacadeLocator.getInstancePermisoFacade().consultarpermiso();
//            for (Permiso perm : recibe) {
//                listarol.add(perm.getPERtipo());
//            }
//       // }
//        return listarol;
//
//    }

    /**
     *Este método devuelve una lista de tipo String, es parecido al método de 
     * arriba, solo qui la lista generada es solo con subPermisos
     * @param rolSelected
     * @return List<String>
     */
    public List<String> getListaSubperm(Rol rolSelected) {
        List<Subpermisos> recibe = null;
        listarol = new ArrayList();
        if (rolSelected.getROLid()!= null) {
            System.out.println("El Rol es: " + rolSelected.getROLtipo());
//            recibe = spDel.getPermisoUser(rolSelected.getRolid());  

//            recibe =ServiceFacadeLocator.getInstanceSubPermisoFacade().ConsultaPermiso(rolSelected.getROLid());
       for (Subpermisos subp : recibe) {
               listarol.add(subp.getSPERtipo());
            }
        } else {
//            recibe = spDel.getPermiso();
            recibe =  ServiceFacadeLocator.getInstanceSubPermisoFacade().consultaGeneralSubpermiso();
            for (Subpermisos subp : recibe) {
               listarol.add(subp.getSPERtipo());
            }
        }
        return listarol;
    }
    List<Rol> listaFiltrada;

    /**
     *
     * @param busqueda
     * @return
     */
    public List<Rol> filtrado(String busqueda) {
        busqueda = busqueda.toLowerCase();
        listaFiltrada =  ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        List<Rol> ListaRoles = new ArrayList();
        if (busqueda.trim().length() > 0 ) {
            for(Rol rol : listaFiltrada){
                if(rol.getROLtipo().toLowerCase().contains(busqueda)){
                    ListaRoles.add(rol);
                }
            }
            
                listaFiltrada= ListaRoles;
            
        }
//        String[] buscar = busqueda.split(" ");
//        if (busqueda.trim().length() > 0 && buscar.length >= 1) {
//            listaFiltrada.clear();
////            for (Rol rolB : rolDelegate.getRol()) {
//            for (Rol rolB : ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol()) {
//                String rolTipo = rolB.getROLtipo().toLowerCase();
//                if (rolTipo.contains(busqueda)) {
//                    System.out.println(">>>>>>>>>>>>>>>Rol:" + rolB.getROLtipo());
//                    if (!listaFiltrada.contains(rolB)) {
//                        listaFiltrada.add(rolB);
//                    }
//                }
//            }
//        }

        return listaFiltrada;
    } 
    
    

    /**
     *Este método entrega el atributo rol de tipo de dato Rol
     * @param rol
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     *Este método devuelve el atributo usuario de tipo de dato Usuario
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     *Este método entrega el atributo usuario de tipo de dato Usuario
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    /**
     *Este método devuelve el atributo listarol de tipo de lista String.
     * @return
     */
    public List<String> getListarol() {
        return listarol;
    }

    /**
     *Este método devuelve el atributo listaRol de tipo de lista String.
     * @param listarol
     */
    public void setListarol(List<String> listarol) {
        this.listarol = listarol;
    }

    /**
     *Este método devuelve el atributo rolesID de tipo de lista Rol.
     * @return
     */
    public List<Rol> getRolesID() {
        return rolesID;
    }

    /**
     *Este método entrega el atributo rolesID de tipo de lista Rol
     * @param rolesID
     */
    public void setRolesID(List<Rol> rolesID) {
        this.rolesID = rolesID;
    }
    
    public List<Usuario> getRolesUsuario(int rolID){           
        return ServiceFacadeLocator.getInstanceUsuarioFacade().getUsuariobyRol(rolID);
    }
            
    public void ActualizarRol(Rol nuevoRol){
        ServiceFacadeLocator.getInstanceRolFacade().modificarRol(nuevoRol);
    }
    
    public void eliminarRolHasPermiso(RolHasPermiso rhp){
        ServiceFacadeLocator.getInstanceRolHasPermisoFacade().eliminarRolHasPermiso(rhp);
    }
    
    public Rol getRolPorNombre(String nombre){
        List<Rol> roles = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        Rol rolN= new Rol();
        for(Rol r: roles){
            if(r.getROLtipo().equalsIgnoreCase(nombre)){
                rolN=r;
            }
        }
        return rolN;
    }
    public Rol getRolPorID(int id){
        List<Rol> roles = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        Rol rolN= new Rol();
        for(Rol r: roles){
            if(r.getROLid() == id){
                rolN=r;
            }
        }
        return rolN;
    }
    
    public Permiso getPermisoPorId(int id){
        return ServiceFacadeLocator.getInstancePermisoFacade().consultaPermisoPorID(id);
    }
    public Subpermisos getSubPermisoPorId(int id){
        return ServiceFacadeLocator.getInstanceSubPermisoFacade().consultaSubpermisoPorID(id);
    }
    
    public List<Permiso> getPermisosDeRol(Rol rol){
        return ServiceFacadeLocator.getInstancePermisoFacade().buscarPermisosPorRol(rol.getROLid());
    }
    
    public List<Subpermisos> getSubPermisoPorRolPermID(int idRol, int idPermiso){
        return ServiceFacadeLocator.getInstanceSubPermisoFacade().buscarPorRolYPermiso(idRol, idPermiso);
    }

    public List<Rol> getRoles() {
        return ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
    }
}
