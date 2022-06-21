package mx.avanti.business.delegate;

import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.integration.ServiceLocator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel valenzuela .Avanti 2018.
 * @author Mario | Refactor
 * @author Kevin Arizaga (2020-2/2021-2) | Refactor
 */
public class DelegateUsuario implements Serializable {
    public void terminar(boolean completo){
        ServiceLocator.getInstanceUsuario().endTransaction(completo);
    }

    /**
     * Metodo que obtiene todos los reportes registrados
     *
     * @return Retorna lista de objetos tipo usuario con todos los valores encontrados
     */
    public List<Usuario> findAll() {
        return ServiceLocator.getInstanceUsuario().findAll();
    }

    /**
     * Metodo que registra un nuevo usuario a la base de datos
     *
     * @param usuario el usuario a registrar
     * @return verdadero si se pudo registrar al usuario o falso de no ser asi
     */
    public boolean save(Usuario usuario) {
        try {
            ServiceLocator.getInstanceUsuario().save(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo que actualiza un usuario registrado
     *
     * @param usuario el usuario a actualizar con sus nuevos valores
     * @return verdadero si se pudo actualizar al usuario o falso de no ser asi
     */
    public boolean update(Usuario usuario) {
        try {
            ServiceLocator.getInstanceUsuario().update(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo que elimina un usuario de la base de datos
     *
     * @param usuario la instancia del usuario a eliminar
     * @return verdadero si se pudo eliminar al usaurio o falso de no ser asi
     */
    public boolean delete(Usuario usuario) {
        try {
            ServiceLocator.getInstanceUsuario().delete(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo que elimina un usuario de la base de datos
     *
     * @param usuarioId el id del usuario a eliminar
     * @return verdadero si se pudo eliminar al usaurio o falso de no ser asi
     */
    public boolean delete(int usuarioId) {
        Usuario usuario = ServiceLocator.getInstanceUsuario().find(usuarioId);
        return delete(usuario);
    }

    /**
     * Metodo que regresa el usuario con un id especifico
     *
     * @param usuarioId el id del usuario a buscar
     * @return Retorna un objeto de tipo usuario con los valores encontrados
     */
    public Usuario findById(int usuarioId) {
        return ServiceLocator.getInstanceUsuario().find(usuarioId);
    }

    /**
     * Metodo que obtiene solo los nombres de todos los usaurios
     *
     * @return Retorna lista de Strings con los nombres encontrados
     */
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (Usuario u : findAll()) {
            names.add(u.getUSUusuario());
        }
        return names;
    }

    /**
     * Obtiene todos los usuarios que tengan un nombre de usuario en especifico
     *
     * @param nombre el nombre de usuario a buscar
     * @return Retorna lista de objetos tipo usuarios encontrados
     */
    public List<Usuario> findByName(String nombre) {
        return ServiceLocator.getInstanceUsuario().findByOneParameter(nombre, "uSUusuario");
    }

    /**
     * Obtiene el primer usuario que tenga un nombre de usuario en especifico
     *
     * @param nombre el nombre de usuario a buscar
     * @return Retorna objeto tipo usuario con los valores del usuario econtrado
     */
    public Usuario findByNameUnique(String nombre) {
        return ServiceLocator.getInstanceUsuario().findByOneParameterUnique(nombre, "uSUusuario");
    }

    /**
     * Obtiene todos los usuarios con un rol en especifico
     *
     * @param rolId id del rol
     * @return Retorna lista de objetos tipo usuario con los usuarios que cumplan
     * con el rol
     */
    public List<Usuario> findByRol(int rolId) {
        List<Usuario> usuariosConRol = new ArrayList<>();
        for (Usuario u : findAll()) {
            for (Rol r : u.getRolList()) {
                if (r.getROLid() == rolId) {
                    usuariosConRol.add(u);
                }
            }
        }
        return usuariosConRol;
    }

    /**
     * Metodo que busca un usuario por su rol
     *
     * @param rolid id del rol a buscar
     * @return Retorna lista de objetos con los usuarios encontrados
     */
    public List<Usuario> findUsuarioByRol(int rolid) {
        return ServiceLocator.getInstanceUsuario().findFromWhere("rols", "rolid", String.valueOf(rolid));
    }
}
