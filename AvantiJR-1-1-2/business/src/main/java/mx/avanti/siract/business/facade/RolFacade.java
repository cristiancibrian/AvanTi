/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import mx.avanti.business.delegate.DelegateRol;
import mx.avanti.siract.entity.Rol;

import java.util.List;

/**
 * @author Manzanas45
 */
public class RolFacade {

    private DelegateRol delegateRol;

    public RolFacade() {
        delegateRol = new DelegateRol();
    }

    public void terminarTransaccion(boolean completa) {
        delegateRol.terminar(completa);
    }

    /**
     * Método para guardar el registro de rol en la base de datos
     *
     * @param rol Registro de rol sin id
     */
    public void agregarRol(Rol rol) {
        delegateRol.save(rol);
    }

    /**
     * Método para modificar un registro de rol en la base de datos
     *
     * @param rol Registro completo de rol con un cambio con el original
     */
    public void modificarRol(Rol rol) {
        delegateRol.update(rol);
    }

    /**
     * Método para obtener todos los registros de la base de datos
     *
     * @return Lista con todos los roles registrados
     */
    public List<Rol> consultaGeneralRol() {
        return delegateRol.findAll();
    }

    /**
     * Método para eliminar un registro de la base de datos
     *
     * @param rol Registro completo de rol que se va a eliminar
     */
    public void eliminarRol(Rol rol) {
        if (rol != null && rol.getROLid() != null) {
            delegateRol.delete(rol);
        }
    }

    /**
     * Método para encontrar un registro especifico
     *
     * @param idRol de tipo int, Id del registro que se quiere consultar
     * @return Objeto tipo Rol
     */
    public Rol consultaRolPorID(int idRol) {
        return delegateRol.find(idRol);
    }

    /**
     * Metodo para encontrar un usuario especifico mediante su id
     *
     * @param usuarioId de tipo int, id del usuario que se va a consultar
     * @return List<Rol> Lista con Objetos tipo Rol
     */
    public List<Rol> buscarPorUsuario(int usuarioId) {
        return delegateRol.findByUser(usuarioId);
    }

    public Rol getRolIdByRoltipo(String roltipo) {
        return delegateRol.getRolIdByRoltipo(roltipo);
    }

    public Rol getRolById(int rolId) {
        return delegateRol.getRolById(rolId);
    }

}
