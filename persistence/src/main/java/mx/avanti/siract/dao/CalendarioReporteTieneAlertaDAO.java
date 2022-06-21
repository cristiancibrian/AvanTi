/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;
import java.util.List;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Query;
/**
 *
 * @author NOE_LOPEZ
 */
public class CalendarioReporteTieneAlertaDAO extends AbstractDAO<Integer, CalendarioreporteTieneAlerta>{
    public List<CalendarioreporteTieneAlerta> getCalendariosFechaActual(String idCalendarioreporte){
        return this.findFromWhere("CalendarioreporteTieneAlerta","calendarioreporte", "creid", idCalendarioreporte);
    }
    
    public List<CalendarioreporteTieneAlerta> findCreAle(int creid) {
        String query = "Select * from Calendarioreporte_tiene_alerta where Calendarioreporte_CREid=" + creid + " order by Alerta_ALEid";
        return this.executeQuery(query);
        
    }

     
     public List<CalendarioreporteTieneAlerta> findCreAle(int creid, int aleid) {
        List<CalendarioreporteTieneAlerta> obj = null;
       // String nombre = tipo.getSimpleName();
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            String query1 = "from " + this.getEntityClass().getCanonicalName() + " as " + this.getEntityClass().getName().toLowerCase() + " where "
                    + "Calendarioreporte_CREid=:creid and Alerta_ALEid=:aleid";
            Query query = HibernateUtil.getSession().createQuery(query1);
            query.setInteger("creid", creid);//ID de calendario
            query.setInteger("aleid", aleid);//ID de alerta
            obj = (List<CalendarioreporteTieneAlerta>) query.uniqueResult();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return obj;
    }
     
     public boolean executeProcedureCalendarioreporteTieneAlerta(String procedure) {
        List<CalendarioreporteTieneAlerta> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(CalendarioreporteTieneAlerta.class);
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

    public List<CalendarioreporteTieneAlerta> executeProcedureCalendarioreporteTieneAlertas(String procedure) {
        List<CalendarioreporteTieneAlerta> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(CalendarioreporteTieneAlerta.class);
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