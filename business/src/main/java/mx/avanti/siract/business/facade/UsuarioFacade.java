package mx.avanti.siract.business.facade;

import mx.avanti.business.delegate.DelegateUsuario;
import mx.avanti.siract.entity.Usuario;

import java.util.List;

/**
 * @author Axel Valenzuela. Avanti 2018.
 */
public class UsuarioFacade {
    private final DelegateUsuario delegateUsuario;

    public UsuarioFacade() {
        delegateUsuario = new DelegateUsuario();
    }

    public void terminarTransaccion(boolean completa) {
        delegateUsuario.terminar(completa);
    }

    /**
     * Metodo que agrega un usuario
     *
     * @param usuario Objeto con los valores del usuario a registrar
     */
    public boolean agregarUsuario(Usuario usuario) {
       return delegateUsuario.save(usuario);
    }

    /**
     * Metodo para consultar usuarios
     *
     * @return Retorna una lista de objetos tipo usuario con todos los que
     * encontro
     */
    public List<Usuario> obtenerUsuarios() {
        return delegateUsuario.findAll();
    }

    /**
     * Metodo de busqueda especifica de usuario
     *
     * @param usuarioName Nombre del usuario a buscar
     * @return retorna una objeto con los valores del usuario
     */
    public Usuario buscarUsuarioPorNombre(String usuarioName) {
        return delegateUsuario.findByNameUnique(usuarioName);
    }

    /**
     * Metodo para eliminar un usuario
     *
     * @param usuario Objeto que contiene los valores de usuario a eliminar
     */
    public void eliminarUsuario(Usuario usuario) {
        delegateUsuario.delete(usuario);
    }

    /**
     * Metodo que buscar un usuario por su nombre de usuario
     *
     * @param usuario Objeto que contiene todos los valores del usuario a buscar
     * @return Retorna un objeto con los valores de usuario encontrado
     */
    public Usuario login(Usuario usuario) {
        return this.buscarUsuarioPorNombre(usuario.getUSUusuario());
    }

    /**
     * Metodo que busca un usuario por su id
     *
     * @param id Identificador del usuario
     * @return Objeto con los valores de usuario encontrado
     */
    public Usuario buscarUsuarioPorId(int id) {
        return delegateUsuario.findById(id);
    }

    /**
     * Metodo que consulta todos los usuarios
     *
     * @return Lista tipo string con los nombres de todos los usuarios
     */
    public List<String> getListaUsuarios() {
        return delegateUsuario.getNames();
    }

    /**
     * Metodo que busca un usuario por su rol
     *
     * @param rolid Identificador de un id
     * @return Regresa lista de objetos con los valores de los usuarios
     * encontrados
     */
    public List<Usuario> getUsuariobyRol(int rolid) {
        List<Usuario> listaUsuario = null;
        return listaUsuario = delegateUsuario.findUsuarioByRol(rolid);
    }

    /**
     * Metodo que actualiza la informacion de un usuario
     *
     * @param usuario Objeto con los valores del usuario a modificar
     */
    public void actualizarUsuario(Usuario usuario) {
        delegateUsuario.update(usuario);
    }


}
