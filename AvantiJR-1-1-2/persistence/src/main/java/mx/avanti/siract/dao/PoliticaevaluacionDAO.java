/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.Articulos;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author Adriana
 */
public class PoliticaevaluacionDAO extends AbstractDAO<Integer, Politicaevaluacion> {
    
    private Session session;
    Criteria criteria;

    public void initSession() {
        setSession(HibernateUtil.getSessionFactory().openSession());

    }

    
    public List<Politicaevaluacion> findallpoliticas() {
        String sql = "SELECT * FROM politicaevaluacion";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Politicaevaluacion.class);
        
        return (List<Politicaevaluacion>) query.list();
    }

    public Politicaevaluacion findpolitica(int id) {
        String sql = "SELECT * FROM politicaevaluacion WHERE POEid=" + id;
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Politicaevaluacion.class);
        
        return (Politicaevaluacion) query.uniqueResult();
    }
    
//    public void agregarPolitica(Politicaevaluacion politicaEvaluacion) {
//        List<PoliticaTieneCriterio> listaPolCriterio = politicaEvaluacion
//        politicaEvaluacion.setPoliticaTieneCriterioList(null);
//        List<PoliticaTieneCriterio> listaPolCriterio2 = new ArrayList<>();
//        
//        try {
//            HibernateUtil.getSession();
//            HibernateUtil.beingTransaccion();
//            HibernateUtil.getSession().save(politicaEvaluacion);
//            
//            for(PoliticaTieneCriterio politicaCriterio : listaPolCriterio ){
//               politicaCriterio.setPOEId(politicaEvaluacion);
//             
//                listaPolCriterio2.add(politicaCriterio);
//                 
//            }
//            for(int x =0; x<listaPolCriterio2.size(); x++){
//                listaPolCriterio2.get(x).setPtcId(null);
//            }
//            
//            politicaEvaluacion.setPoliticaTieneCriterioList(listaPolCriterio2);
//           
//            HibernateUtil.getSession().update(politicaEvaluacion);
//            
//        } catch (HibernateException e) {
//            HibernateUtil.rollbackTransaction();
//        } finally {
//            HibernateUtil.commitTransaction();
//            HibernateUtil.closeSession();
//        }
//    }
    
      public List<Politicaevaluacion> storeProcedurePolitica(String procedure){
        System.out.println("Save ----------");
        List<Politicaevaluacion> result = null;
        try {
            HibernateUtil.getSession();
            //System.out.println("[PRO]GetSession");
            HibernateUtil.beginTransaccion();
            Query miquery = HibernateUtil.getSession().createSQLQuery(procedure)
                    .addEntity(Politicaevaluacion.class);
            result = miquery.list();
            return result;  
        } catch (HibernateException e) {
            System.out.println(e);
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }
      
    
      
     public List<Politicaevaluacion> ExecuteProcedureConsulta(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Politicaevaluacion> result;
        HibernateUtil.getSession();
        Query lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Politicaevaluacion.class);
         result = lQuery.list();
         return result;
    }
     
    public List<Criterioevaluacion> ExecuteProcedureConsultaCriterio(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Criterioevaluacion> result;
        HibernateUtil.getSession();
        Query lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Criterioevaluacion.class);
         result = lQuery.list();
         return result;
    }
    
     public List<Articulos> findAllArticulos() {
          String sql = "SELECT * FROM articulos";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Articulos.class);

        return (List<Articulos>) query.list();
    }
    
    
     
     /**
     * Este metodo termina una transaccion si recibe un true hace commit Si
     * recibe un false hace un rollback
     *
     * @param ban
     */
    public void endTransaction(boolean exitoso) {
        if (exitoso == false) {
          HibernateUtil.rollbackTransaction();
        }
        HibernateUtil.commitTransaction();
        HibernateUtil.closeSession();
    }
      
        /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }
}
