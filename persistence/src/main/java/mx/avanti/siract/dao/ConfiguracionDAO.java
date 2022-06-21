/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.metadata.ClassMetadata;

/**
 *
 * @author Martin
 */
public class ConfiguracionDAO extends AbstractDAO<Integer, Configuracion> {

    public boolean executeProcedureConfiguracion(String procedure) {
        List<Configuracion> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Configuracion.class);
            result = iQuery.list();
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

    public List<Configuracion> executeProcedureConfiguraciones(String procedure) {
        List<Configuracion> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Configuracion.class);
            result = iQuery.list();
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
