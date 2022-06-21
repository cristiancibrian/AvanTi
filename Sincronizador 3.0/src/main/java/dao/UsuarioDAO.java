package dao;

import clases.AbstractDAO;
import entidades.Usuario;

public class UsuarioDAO extends AbstractDAO<Integer, Usuario> {

    /**
     * Metodo que valida si un usuario ya esta registrado
     *
     * @param correo Valor que compara con los demas usuarios
     * @return Retorna verdadero en caso de que sea encontrado
     */
    public boolean validarUsuarioRepetido(String correo) {
        boolean res = false;
        Usuario us = new Usuario();
        us = this.executeQueryUnique("select * from usuario where USUusuario = '" + correo + "'");
        if (us == null) {
            res = false;
        } else {
            res = true;
        }
        return res;
    }
    
    /*
    public void addUsuario(Usuario usuario) {
        this.save(usuario);
    }

    public void updateUsuario(Usuario usuario) {
        this.update(usuario);
    }

    public void deleteUsuario(int idPer) {
        Usuario u = this.find(idPer);
        if (u != null) {
            this.delete(u);
        }
    }

    public List<Usuario> findAllUsuario() {
        return this.findAll();
    }

    public Usuario findByUsuarioId(int idPer) {
        return this.find(idPer);
    }

    public Usuario login(Usuario usuario) {
        Usuario user = this.findByUsuario(usuario);
//        Usuario user = this.find(usuario.getUsuid());
        System.out.println(user);
        if (user != null) {
            System.out.println("no estoy vacio c:");
        }
        return user;
    }
    
    public Usuario findByUsuario(Usuario usuario) {
        Usuario user = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            user = (Usuario) HibernateUtil.getSession().createQuery("from Usuario as usuario where usuario.usuusuario = :nombre").setString("nombre", String.valueOf(usuario.getUSUusuario())).uniqueResult();
            System.out.println(user);
        } catch (Exception x) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }
    */

    public Usuario getUsuarioByUSUusuario(String usuario) {
        Usuario result = null;
        result = this.executeQueryUnique("Select * from Usuario where USUusuario = '" + usuario + "'");
        return result;
    }
    
    public Usuario getUsuarioByUSUid(int USUid){
        Usuario result = null;
        result = this.executeQueryUnique("Select * from Usuario where USUid = " + USUid + ";");
        return result;
    }
    
    
    
}
