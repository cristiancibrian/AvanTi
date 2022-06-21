/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.integration.ServiceLocator;

import java.util.List;

/**
 * @author Alex
 */
public class DelegateRol {

    public DelegateRol() {
    }

    public void terminar(boolean completo) {
        ServiceLocator.getInstanceRol().endTransaction(completo);
    }

    /**
     * Método para guardar el registro de rol en la base de datos
     *
     * @param rol Registro de rol sin id
     */
    public void save(Rol rol) {
        ServiceLocator.getInstanceRol().save(rol);
    }

    /**
     * Método para modificar un registro de rol en la base de datos
     *
     * @param rol Registro completo de rol con un cambio con el original
     */
    public void update(Rol rol) {
        ServiceLocator.getInstanceRol().update(rol);
    }

    /**
     * Método para obtener todos los registros de rol de la base de datos
     *
     * @return Lista con todos los roles registrados
     */
    public List<Rol> findAll() {
        return ServiceLocator.getInstanceRol().findAll();
    }

    /**
     * Método para eliminar un registro de la base de datos
     *
     * @param rol Registro completo de rol que se va a eliminar
     */
    public void delete(Rol rol) {
        if (rol != null && rol.getROLid() != null) {
            ServiceLocator.getInstanceRol().delete(rol);
        }
    }

    /**
     * Método que busca un rol especifico por su id
     *
     * @param idRol de tipo int, Id del registro que se quiere consultar
     * @return Objeto tipo Rol
     */
    public Rol find(int idRol) {
        return ServiceLocator.getInstanceRol().find(idRol);
    }

    /**
     * Busca todos los roles de un usuario por el id del usuario
     *
     * @param userId tipo int, id del usuario
     * @return Lista con Objetos de tipo rol
     */
    public List<Rol> findByUser(int userId) {
        return ServiceLocator.getInstanceRol().findFromWhere("usuarios",
                "usuid", String.valueOf(userId));
    }

    public Rol getRolIdByRoltipo(String roltipo) {
        return ServiceLocator.getInstanceRol().getRolIdByRoltipo(roltipo);
    }

    public Rol getRolById(int rolId) {
        return ServiceLocator.getInstanceRol().getRolById(rolId);
    }

}
