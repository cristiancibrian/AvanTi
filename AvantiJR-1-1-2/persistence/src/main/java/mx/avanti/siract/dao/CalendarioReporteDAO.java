/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;
import java.util.List;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
/**
 *
 * @author NOE_LOPEZ
 */
public class CalendarioReporteDAO extends AbstractDAO<Integer, Calendarioreporte>{
        
    public boolean saveOrUpdateCalendario(Calendarioreporte obj) {
        System.out.println("SaveOrUpdate ----------");
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().saveOrUpdate(obj);

        } catch (HibernateException e) {
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }


    public boolean saveCalendario(Calendarioreporte obj) {
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


    public boolean updateCalendario(Calendarioreporte obj) {
        System.out.println("update ----------");
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().update(obj);

        } catch (HibernateException e) {
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }
    

    public boolean deleteCalendario(Calendarioreporte obj) {
        System.out.println("Delete ----------");
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().delete(obj);

        } catch (HibernateException e) {
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }
    
        public boolean executeProcedureCalendario(String procedure) {
        List<Calendarioreporte> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Calendarioreporte.class);
            result = iQuery.list();
            System.out.println("SE COMPLETO");
        } catch (Exception e) {
            System.out.println("ERROOOOOOOOOOR> " + e);
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public List<Calendarioreporte> executeProcedureCalendarios(String procedure) {
        List<Calendarioreporte> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Calendarioreporte.class);
            result = iQuery.list();
            System.out.println("SE COMPLETO");
        } catch (Exception e) {
            System.out.println("ERROOOOOOOOOOR> " + e);
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return result;
        }
    }
}
