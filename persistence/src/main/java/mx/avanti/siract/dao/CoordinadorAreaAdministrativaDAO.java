/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Paul Miranda
 */
public class CoordinadorAreaAdministrativaDAO extends AbstractDAO<Integer, Coordinadorareaadministrativa> {

    public boolean saveAsignacion(Coordinadorareaadministrativa obj) {
        System.out.println("Save ----------");
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().save(obj);
        } catch (HibernateException e) {
            e.printStackTrace();
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public boolean updateAsignacion(Coordinadorareaadministrativa obj) {
        System.out.println("update ----------");
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().update(obj);
        } catch (HibernateException e) {
            e.printStackTrace();
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public boolean deleteAsignacion(Coordinadorareaadministrativa obj) {
        System.out.println("Delete ----------");
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().delete(obj);
        } catch (HibernateException e) {
            e.printStackTrace();
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public boolean executeProcedureCoordinadorAreaAdministrativa(String procedure) {
        List<Coordinadorareaadministrativa> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Coordinadorareaadministrativa.class);
            result = iQuery.list();
        } catch (Exception e) {
            bandera = false;
            System.out.println(e);
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public List<Coordinadorareaadministrativa> executeProcedureCoordinadorAreaAdministrativas(String procedure) {
        List<Coordinadorareaadministrativa> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Coordinadorareaadministrativa.class);
            result = iQuery.list();
        } catch (Exception e) {
            System.out.println(e);
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return result;
        }
    }

    public void iniciarTransaccion() {
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
    }

    public boolean executeProcedure(String procedure) {
        List<Coordinadorareaadministrativa> result = null;
        boolean bandera = true;
        try {
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Coordinadorareaadministrativa.class);
            result = iQuery.list();
        } catch (Exception e) {
            System.out.println(e);
            bandera = false;
        } finally {
            return bandera;
        }
    }

    public boolean terminarTransaccion(boolean bandera) {
        if (bandera) {
            HibernateUtil.commitTransaction();
        } else {
            HibernateUtil.rollbackTransaction();
        }
        HibernateUtil.closeSession();
        return bandera;
    }

}
