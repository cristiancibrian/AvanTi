/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateSubPermiso;
import mx.avanti.siract.entity.Subpermisos;

/**
 *
 * @author kitto
 */
public class SubPermisoFacade {
    
   DelegateSubPermiso subPermisoDelegate;
    
    public SubPermisoFacade(){
        subPermisoDelegate = new DelegateSubPermiso();
    }
    
    /**
     * Método para obtener todos los registros de la base de datos
     * @return lista con todos los subpermisos registrados
     */
    public List<Subpermisos> consultaGeneralSubpermiso() {
        return subPermisoDelegate.findAll();
    }
    /**
     * Método para encontrar un registro especifico
     * @param idSubpermiso Id del registro que se quiere consultar
     * @return Registro especifico de subpermiso
     */
    public Subpermisos consultaSubpermisoPorID(int idSubpermiso) {
        return subPermisoDelegate.find(idSubpermiso);
    }
    /**
     * Metodo para encontrar un registro por id de rol y permiso
     * @param rolId id del rol que se quiere consultar
     * @param permisoId id del permiso que se quiere consultar
     * @return  Registro especifico encontrado
     */
    public List<Subpermisos> buscarPorRolYPermiso(int rolId, int permisoId){
        return subPermisoDelegate.findByRolAndPermiso(rolId, permisoId);
    }
    
}
