/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.entity.Permiso;
/**
 *
 * @author Maby_
 */
public class PermisoDAO extends AbstractDAO <Integer, Permiso>{
    
    /**
     * Metodo que consulta todos los permisos que cumplan con un rol especifico
     * @param idRol Identificador del rol
     * @return 
     */
    public List<Permiso> getPermisosRol(int idRol){
        return this.executeQuery("select * from permiso where permiso.PERid in( select Permiso_PERid  from rol_has_permiso where Rol_ROLid = " + idRol + " )");
    }
    
    
}
