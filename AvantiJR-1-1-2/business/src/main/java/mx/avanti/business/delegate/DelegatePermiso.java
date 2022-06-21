/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Permiso;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Maby_
 */
public class DelegatePermiso {
    public DelegatePermiso(){}
    /***
     * Método para guardar un registro de permiso en la base de datos
     * @param permiso Registro de permiso con todos los datos excepto ID
     */
    public void save(Permiso permiso) {
        ServiceLocator.getInstancePermisoDAO().save(permiso);
    }
    /***
     * Método para modificar un registro de la base de datos
     * @param permiso Registro de permiso completo con una modificación al 
     * original
     */
    public void update(Permiso permiso) {
        ServiceLocator.getInstancePermisoDAO().update(permiso);
    }
    /***
     * Método para consultar todos los registros de la base de datos
     * @return Lista con todos los Permisos registrados
     */
    public List<Permiso> findAll() {
        return ServiceLocator.getInstancePermisoDAO().findAll();
    }
    /***
     * Método para buscar un permiso especifico
     * @param idPermiso ID de un usuario
     * @return Lista de Permisos que posee el usuario al que le pertenece el id
     * del parametro
     */
    public Permiso find(int idPermiso) {
        return ServiceLocator.getInstancePermisoDAO().find(idPermiso);
    }
    
    /**
     * Metodo para buscar un permiso especifico
     * @param rolId Rol a buscar 
     * @return 
     */
    public List<Permiso> findByRol(int rolId){
        return ServiceLocator.getInstancePermisoDAO().getPermisosRol(rolId);
    }
}

