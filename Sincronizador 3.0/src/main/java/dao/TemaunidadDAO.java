/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import entidades.Temaunidad;

/**
 * @author ODOSMO
 */
public class TemaunidadDAO extends AbstractDAO<Integer, Temaunidad> {

//    public void addCatalogoReportes(Temaunidad temaunidad) {
//        Transaction trns = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            trns = session.beginTransaction();
//            session.save(temaunidad);
//            session.getTransaction().commit();
//        } catch (RuntimeException e) {
//            if (trns != null) {
//                trns.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.flush();
//            session.close();
//        }
//    }
//    
//    public void updateTemaunidad(Temaunidad temaunidad) {
//        Transaction trns = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            trns = session.beginTransaction();
//            session.saveOrUpdate(temaunidad);
//            session.getTransaction().commit();
//        } catch (RuntimeException e) {
//            if (trns != null) {
//                trns.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.flush();
//            session.close();
//        }
//    }
//    
//    public void deleteTemaunidad(int idPer) {
//        Transaction trns = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            trns = session.beginTransaction();
//            Temaunidad temaunidad = (Temaunidad) session.load(Temaunidad.class, new Integer(idPer));
//            session.delete(temaunidad);
//            session.getTransaction().commit();
//        } catch (RuntimeException e) {
//            if (trns != null) {
//                trns.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.flush();
//            session.close();
//        }
//    }
//    
//    
//    public List<Temaunidad> findAllTemaunidads(){
//        List<Temaunidad> result = null;
//        HibernateUtil.getSession();
//        HibernateUtil.beingTransaccion();
//        
//        try{
//            result = (List<Temaunidad>) HibernateUtil.getSession().createQuery("from Temaunidad").list();
//            if(result == null){
//                System.out.println("nulo");
//            }
//        }catch(Exception x){
//            x.printStackTrace();
//            HibernateUtil.rollbackTransaction();
//        }finally{
//            HibernateUtil.closeSession();
//        }
//        return result;
//    }
//    
//    public Temaunidad findByTemaunidadId(int idPer) {
//        Temaunidad direccion = null;
//        
//        try {
//            HibernateUtil.getSession();
//            HibernateUtil.beingTransaccion();
//            direccion = (Temaunidad) HibernateUtil.getSession().createQuery("from Temaunidad as temaunidad where temaunidad.idTemaunidad = :id").setString("id", String.valueOf(idPer)).uniqueResult();
//        } catch (Exception x) {
//            HibernateUtil.rollbackTransaction();
//        } finally {
//            HibernateUtil.closeSession();
//        }
//        return direccion;
//    }
//
//    
//        public List<Temaunidad> findByCriteria(int idUnidad){
//        Session session = HibernateUtil.getSession();
//        
//        Criteria criteria = session.createCriteria(Temaunidad.class, "temaunidad");
//        criteria.createAlias("temaunidad.unidad", "unidad");//Inner Join by default
//        
//        criteria.add(Restrictions.eq("unidad.uniid", idUnidad));
//        criteria.setMaxResults(10);
//        List listaTemas = criteria.list();
//      //  session.close();
//        
//        return listaTemas;
//    }
//        
}
