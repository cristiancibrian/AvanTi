/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.CalendarioreporteTieneAlerta;
import org.hibernate.Query;

import java.util.List;

/**
 * @author NOE_LOPEZ
 */
public class CalendarioReporteTieneAlertaDAO extends AbstractDAO<Integer, CalendarioreporteTieneAlerta> {
    public List<CalendarioreporteTieneAlerta> getCalendariosFechaActual(String idCalendarioreporte) {
        //return ServiceFacadeLocator.getFacadeCalendarioreporteTieneAlerta().getCalendariosFechaActual(idCalendarioreporte);
        /*ServiceLocator.getInstanceBaseDAO().setTipo(CalendarioreporteTieneAlerta.class);
        return ServiceLocator.getInstanceBaseDAO().findFromWhere("CalendarioreporteTieneAlerta","calendarioreporte", "creid", idCalendarioreporte);*/
        return this.findFromWhere("CalendarioreporteTieneAlerta", "calendarioreporte", "creid", idCalendarioreporte);
    }

    public List<CalendarioreporteTieneAlerta> findCreAle(int creid) {
        String query = "Select * from Calendarioreporte_tiene_alerta where Calendarioreporte_CREid=" + creid + " order by Alerta_ALEid";
        return this.executeQuery(query);

    }
//     public List<CalendarioreporteTieneAlerta> findCreAle(int creid) {
//        List<CalendarioreporteTieneAlerta> result = null;
//        HibernateUtil.getSession();
//        HibernateUtil.beingTransaccion();
//        
//        try {
//            String query1 = "from " + this.getEntityClass().getName() + " where "
//                    + "Calendarioreporte_CREid=:creid order by Alerta_ALEid";
//            Query query = HibernateUtil.getSession().createQuery(query1);
//            query.setInteger("creid", creid);
//            result = (List<CalendarioreporteTieneAlerta>) query.list();
//            if (result == null) {
//                System.out.println("nulo");
//            }
//        } catch (Exception x) {
//            x.printStackTrace();
//            HibernateUtil.rollbackTransaction();
//        } finally {
//            HibernateUtil.closeSession();
//        }
//        return result;
//    }

    public List<CalendarioreporteTieneAlerta> findCreAle(int creid, int aleid) {
        List<CalendarioreporteTieneAlerta> obj = null;
        // String nombre = tipo.getSimpleName();
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
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
}