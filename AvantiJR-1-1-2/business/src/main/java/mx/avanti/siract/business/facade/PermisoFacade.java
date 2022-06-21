/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegatePermiso;
import mx.avanti.siract.entity.Permiso;

/**
 *
 * @author Maby_
 */
public class PermisoFacade {
    private DelegatePermiso delegatePermiso;
    public PermisoFacade() {
        delegatePermiso=new DelegatePermiso();
    }
    /***
     * Método para guardar un registro de permiso en la base de datos
     * @param permiso Registro de permiso con todos los datos excepto ID
     */
    public void agregarPermiso(Permiso permiso) {
        delegatePermiso.save(permiso);
    }
    /***
     * Método para modificar un registro de la base de datos
     * @param permiso Registro de permiso completo con una modificación al 
     * original
     */
    public void modificarPermiso(Permiso permiso) {
        delegatePermiso.update(permiso);
    }
    /***
     * Método para consultar todos los registros de la base de datos
     * @return Lista con todos los Permisos registrados
     */
    public List<Permiso> consultaGeneralPermiso() {
        return delegatePermiso.findAll();
    }
    /***
     * Método para buscar un permiso especifico
     * @param idPermiso ID de un usuario
     * @return Lista de Permisos que posee el usuario al que le pertenece el id
     * del parametro
     */
    public Permiso consultaPermisoPorID(int idPermiso) {
        return delegatePermiso.find(idPermiso);
    }
    
    /**
     * Metodo para buscar un permiso especifico
     * @param rol Rol a buscar
     * @return 
     */
    public List<Permiso> buscarPermisosPorRol(int rol){
        return delegatePermiso.findByRol(rol);
    }
}
