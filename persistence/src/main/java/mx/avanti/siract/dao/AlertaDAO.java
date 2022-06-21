/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Alerta;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.metadata.ClassMetadata;

/**
 *
 * @author Adriana
 */
public class AlertaDAO extends AbstractDAO<Integer, Alerta> {

    /**
     * Metodo para encontrar una aleerta por su tipo
     *
     * @param tipo de alerta
     * @return Alerta encontrada
     */
    public Alerta findAlerta(String tipo) {
        Alerta obj = null;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata("Alerta");
        String nombre = "Alerta";
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            String query1 = "from " + "Alerta" + " as " + nombre.toLowerCase() + " where ALEtipo=:tipo";
            Query query = HibernateUtil.getSession().createQuery(query1);
            query.setString("tipo", tipo);
            obj = (Alerta) query.uniqueResult();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return obj;
    }

    public boolean saveAlerta(Alerta obj) {
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

    public void iniciarTransaccion() {
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

    }

    public boolean updateAlerta(Alerta obj) {
        System.out.println("Save ----------");
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

    public void endTransaccion() {
        HibernateUtil.commitTransaction();
        HibernateUtil.closeSession();
    }

    public boolean executeProcedureAlerta(String procedure) {
        List<Alerta> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Alerta.class);
            result = iQuery.list();
        } catch (Exception e) {
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public List<Alerta> executeProcedureAlertas(String procedure) {
        List<Alerta> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Alerta.class);
            result = iQuery.list();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return result;
        }
    }
}
