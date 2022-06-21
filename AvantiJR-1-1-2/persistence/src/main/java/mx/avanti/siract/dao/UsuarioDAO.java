package mx.avanti.siract.dao;

import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends AbstractDAO<Integer, Usuario> {
    private boolean secureTransaction = true;

    /**
     * Metodo que valida si un usuario ya esta registrado
     *
     * @param correo Valor que compara con los demas usuarios
     * @return Retorna verdadero en caso de que sea encontrado
     */
    public boolean validarUsuarioRepetido(String correo) {
        boolean res = false;
        Usuario us = new Usuario();
        us = this.executeProcedureOne("CALL validarUsuarioRepetido (\'" + correo + "\')");
        if (us == null) {
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public void endTransaction(boolean completo) {
        if (completo) {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        } else
            HibernateUtil.rollbackTransaction();
    }

    private List<Usuario> executeProcedureList(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Usuario> result;
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Usuario.class);
        result = lQuery.list();
        return result;
    }

    public List<Usuario> executeProcedure(String procedure){
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<Usuario> result = new ArrayList();
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Usuario.class);
            result = iQuery.list();
        }catch(HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }

    private Usuario executeProcedureOne(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        Usuario result;
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Usuario.class);
        result = (Usuario) lQuery.uniqueResult();
        return result;
    }

    public void secureSave(Usuario usuario) {
        try {
            HibernateUtil.getSession().save(usuario);
        } catch (HibernateException e) {
            setSecureTransaction(false);
        }
    }

    private void setSecureTransaction(boolean secureTransaction) {
        this.secureTransaction = secureTransaction;
    }

    public void iniciarTransaccion() {
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
    }

    public void finalizarTransaccion() {
        if (secureTransaction) {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        } else if (!secureTransaction) {
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
        }
        setSecureTransaction(true);
    }



}
