/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateRolHasPermiso;
import mx.avanti.siract.entity.RolHasPermiso;

/**
 *
 * @author Usagi
 */
public class RolHasPermisoFacade {

    DelegateRolHasPermiso rolHasPermisoDelegate;

    public RolHasPermisoFacade() {
        rolHasPermisoDelegate = new DelegateRolHasPermiso();
    }
    
    /**
     * Método para guardar el nuevo permiso de un rol en la base de datos
     * @param rolHasPermiso Registro de rolHasPermiso
     */
    public void agregarRolHasPermiso(RolHasPermiso rolHasPermiso) {
        if (rolHasPermiso.getRolHasPermisoPK() != null) {
              rolHasPermisoDelegate.save(rolHasPermiso);
        }
    }
    /**
     * Método para obtener todos las relaciones de los roles con 
     * sus permisos de la base de datos
     * @return Lista con Objetos tipo RolHasPermiso 
     */
    public List<RolHasPermiso> consultaGeneralRolHasPermiso(){
        return rolHasPermisoDelegate.findAll();
    }
    /**
     * Método para eliminar un permiso de un rol 
     * @param rolHasPermiso de tipo RolHasPermiso
     */
    public void eliminarRolHasPermiso(RolHasPermiso rolHasPermiso) {
        rolHasPermisoDelegate.delete(rolHasPermiso);
    }
}
