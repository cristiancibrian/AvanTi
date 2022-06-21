/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.io.Serializable;
import java.util.List;
//import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Subpermisos;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author kitto
 */
public class DelegateSubPermiso implements Serializable {

    public DelegateSubPermiso(){}
    /**
     * Método para obtener todos los registros de la base de datos
     * @return lista con todos los subpermisos registrados
     */
    public List<Subpermisos> findAll() {
        return ServiceLocator.getInstanceSubPermisoDAO().findAll();
    }
    /**
     * Método para encontrar un registro especifico
     * @param idSubpermiso Id del registro que se quiere consultar
     * @return Registro especifico de subpermiso
     */
    public Subpermisos find(int idSubpermiso) {
        return ServiceLocator.getInstanceSubPermisoDAO().find(idSubpermiso);
    }
    /**
     * Metodo para encontrar un registro a partir del id de rol y del permiso
     * @param rolId id del rol que se quiere consultar
     * @param permisoId id del permiso que se quiere consultar
     * @return Registro expecifico encontrado por el id del rol y del permiso
     */
    public List<Subpermisos> findByRolAndPermiso(int rolId, int permisoId){
        return ServiceLocator
                .getInstanceSubPermisoDAO()
                .findByRolAndPermiso(rolId, permisoId);
    }
}
