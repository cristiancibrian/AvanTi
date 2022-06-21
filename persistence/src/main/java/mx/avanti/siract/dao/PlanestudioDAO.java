/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author Moises
 */
public class PlanestudioDAO {
    public void addCatalogoReportes(Planestudio planestudio) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(planestudio);
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
    
    public void updatePlanestudio(Planestudio planestudio) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.saveOrUpdate(planestudio);
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
    
    public void deletePlanestudio(int idPer) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Planestudio planestudio = (Planestudio) session.load(Planestudio.class, new Integer(idPer));
            session.delete(planestudio);
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
    
    
    public List<Planestudio> findAllPlanestudios(){
        List<Planestudio> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        
        try{
            result = (List<Planestudio>) HibernateUtil.getSession().createQuery("from Planestudio").list();
            if(result == null){
                System.out.println("nulo");
            }
        }catch(Exception x){
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        }finally{
            HibernateUtil.closeSession();
        }
        return result;
    }
    
    public Planestudio findByPlanestudioId(int idPer) {
        Planestudio direccion = null;
        
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            direccion = (Planestudio) HibernateUtil.getSession().createQuery("from Planestudio as planestudio where planestudio.idPlanestudio = :id").setString("id", String.valueOf(idPer)).uniqueResult();
        } catch (Exception x) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return direccion;
    }

    
        public List findByCriteria(int idProgramaeducativo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Criteria criteria = session.createCriteria(Planestudio.class, "planestudio");
        criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default
        
        criteria.add(Restrictions.eq("programaeducativo.pedid", idProgramaeducativo));
        criteria.setMaxResults(10);
        List listaPlanes = criteria.list();
        session.close();
        
        return listaPlanes;
    }
        
        public List<Planestudio> executeProcedureTest(String procedure){
            List<Planestudio> resultado = null;
        System.out.println("ExecuteProcedureTest------------");
        try{        
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query query = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Planestudio.class);
            resultado = query.list();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return resultado;
     }
       
}