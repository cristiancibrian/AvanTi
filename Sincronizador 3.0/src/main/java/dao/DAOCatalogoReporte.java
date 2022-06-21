/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Catalogoreportes;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Paul Miranda
 */
public class DAOCatalogoReporte extends AbstractDAO<Integer, Catalogoreportes> {
    public void addCatalogoReportes(Catalogoreportes catalogoreportes) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(catalogoreportes);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }

    public Catalogoreportes findByCatalogoreportesId(int idPer) {
        Catalogoreportes direccion = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            direccion = (Catalogoreportes) HibernateUtil.getSession().createQuery("from Catalogoreportes as catalogoreportes where catalogoreportes.ctrid = :id").setString("id", String.valueOf(idPer)).uniqueResult();
        } catch (Exception x) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return direccion;
    }

    public void updateCatalogoreportes(Catalogoreportes catalogoreportes) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.saveOrUpdate(catalogoreportes);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }

    public void deleteCatalogoreportes(int idPer) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Catalogoreportes catalogoreportes = (Catalogoreportes) session.load(Catalogoreportes.class, new Integer(idPer));
            session.delete(catalogoreportes);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }
}
