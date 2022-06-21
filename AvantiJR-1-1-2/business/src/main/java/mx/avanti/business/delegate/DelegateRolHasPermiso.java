/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.RolHasPermiso;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Usagi
 */
public class DelegateRolHasPermiso {
    public DelegateRolHasPermiso(){}
    /**
     * Método para guardar el registro de rolHasPermiso en la base de datos
     * @param rolHasPermiso Registro de rolHasPermiso
     */
    public void save(RolHasPermiso rolHasPermiso) {
        if (rolHasPermiso.getRolHasPermisoPK() != null) {
              ServiceLocator.getInstanceRolHasPermisoDAO().save(rolHasPermiso);
        }
    }
    /**
     * Método para obtener todos los registros de la base de datos
     * @return Lista con todas las relaciones registradas
     */
    public List<RolHasPermiso> findAll(){
        return ServiceLocator.getInstanceRolHasPermisoDAO().findAll();
    }
    /**
     * Método para eliminar un permiso de un rol
     * @param rolHasPermiso objeto tipo RolHasPermiso
     */
    public void delete(RolHasPermiso rolHasPermiso) {
        ServiceLocator.getInstanceRolHasPermisoDAO().delete(rolHasPermiso);
    }
}
