/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import entidades.Permiso;

import java.util.List;

/**
 * @author Maby_
 */
public class PermisoDAO extends AbstractDAO<Integer, Permiso> {

    /**
     * Metodo que consulta todos los permisos que cumplan con un rol especifico
     *
     * @param idRol Identificador del rol
     * @return
     */
    public List<Permiso> getPermisosRol(int idRol) {
        return this.executeQuery("select * from permiso where permiso.PERid in( select Permiso_PERid  from rol_has_permiso where Rol_ROLid = " + idRol + " )");
    }


}
