/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Query;

/**
 *
 * @author Moises
 */
public class AreaadministrativaDAO extends AbstractDAO<Integer, Areaadministrativa>{
    
       public boolean executeProcedureAreaAdministrativa(String procedure) {
        List<Areaadministrativa> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Areaadministrativa.class);
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

    public List<Areaadministrativa> executeProcedureAreaAdministrativas(String procedure) {
        List<Areaadministrativa> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Areaadministrativa.class);
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