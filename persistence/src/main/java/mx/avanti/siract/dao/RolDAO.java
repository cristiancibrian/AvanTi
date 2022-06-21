/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 */
public class RolDAO extends AbstractDAO<Integer, Rol> {

    public Rol getRolIdByRoltipo(String roltipo) {
        Rol result = null;
        result = this.executeProcedureOne("CALL getRolIdByRolTipo (\'" + roltipo + "\')");
        return result;
    }

    public Rol getRolById(int rolId) {
        Rol result = null;
        result = this.executeProcedureOne("CALL getRolByID (\'" + rolId + "\')");
        return result;
    }

    /**
     * Método que ejecuta una instrucción de proceso almacenado, donde el retorno de valor sera una lista de entidades.
     * Este es útil cuando se necesita obtener más de una entidad en la base de datos.
     *
     * @param procedure
     * @return Lista de roles
     */
    private List<Rol> executeProcedureList(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Rol> result;
        HibernateUtil.getSession();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Rol.class);
        result = lQuery.list();
        HibernateUtil.closeSession();
        return result;
    }

    /**
     * Método que ejecuta una instrucción de proceso almacenado, donde el retorno de valor sera un valor unico.
     * Este es últil cuando se necesita obtener solamente una entidad de la base de datos.
     *
     * @param procedure
     * @return Entidad rol
     */
    private Rol executeProcedureOne(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        Rol result;
        HibernateUtil.getSession();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Rol.class);
        result = (Rol) lQuery.uniqueResult();
        HibernateUtil.closeSession();
        return result;
    }

    public void endTransaction(boolean completo) {
        if (completo) {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        } else
            HibernateUtil.rollbackTransaction();
    }

    public List<Rol> executeProcedure(String procedure){
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<Rol> result = new ArrayList();
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Rol.class);
            result = iQuery.list();
        }catch(HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }
}
