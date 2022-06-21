/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import entidades.Subpermisos;

import java.util.List;

/**
 * @author stang
 */
public class SubPermisoDAO extends AbstractDAO<Integer, Subpermisos> {


    /**
     * @param rolId     id del rol que se quiere buscar
     * @param permisoId id del permiso que se quiere buscar
     * @return Registro especifico encontrado por el id del rol y del permiso
     */
    public List<Subpermisos> findByRolAndPermiso(int rolId, int permisoId) {
        return this.executeQuery("select * from subpermisos where SPERid in ( select Subpermisos_SPERid from rol_has_permiso where Rol_ROLid = " + rolId + " and Permiso_PERid = " + permisoId + ")");
    }

}
