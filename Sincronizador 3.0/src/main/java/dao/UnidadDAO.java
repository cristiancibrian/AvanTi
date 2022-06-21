/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import entidades.Unidad;

/**
 * @author Axel valenzuela. Avanti 2018
 */
public class UnidadDAO extends AbstractDAO<Integer, Unidad> {
//
//    public void addUnidad(Unidad unidad) {
////        Transaction trns = null;
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        try {
////            trns = session.beginTransaction();
////            session.save(unidad);
////            session.getTransaction().commit();
////        } catch (RuntimeException e) {
////            if (trns != null) {
////                trns.rollback();
////            }
////            e.printStackTrace();
////        } finally {
////            session.flush();
////            session.close();
////        }
//        this.save(unidad);
//    }
//
//    public void updateUnidad(Unidad unidad) {
////        Transaction trns = null;
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        try {
////            trns = session.beginTransaction();
////            session.saveOrUpdate(unidad);
////            session.getTransaction().commit();
////        } catch (RuntimeException e) {
////            if (trns != null) {
////                trns.rollback();
////            }
////            e.printStackTrace();
////        } finally {
////            session.flush();
////            session.close();
////        }
//        this.saveOrUpdate(unidad);
//    }
//
//    public void deleteUnidad(int idUni) {
////        Transaction trns = null;
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        try {
////            trns = session.beginTransaction();
////            Unidad unidad = (Unidad) session.load(Unidad.class, new Integer(idUni));
////            session.delete(unidad);
////            session.getTransaction().commit();
////        } catch (RuntimeException e) {
////            if (trns != null) {
////                trns.rollback();
////            }
////            e.printStackTrace();
////        } finally {
////            session.flush();
////            session.close();
////        }
//        Unidad u = this.find(idUni);
//        if (u != null) {
//            this.delete(u);
//        }
//    }
//
//    public List<Unidad> findAllUnidads() {
////        List<Unidad> result = null;
////        HibernateUtil.getSession();
////        HibernateUtil.beingTransaccion();
////        
////        try{
////            result = (List<Unidad>) HibernateUtil.getSession().createQuery("from Unidad").list();
////            if(result == null){
////                System.out.println("nulo");
////            }
////        }catch(Exception x){
////            x.printStackTrace();
////            HibernateUtil.rollbackTransaction();
////        }finally{
////            HibernateUtil.closeSession();
////        }
//        return this.findAll();
////        return result;
//    }
//
//    public Unidad findByUnidadId(int idUni) {
////        Unidad direccion = null;
////
////        try {
////            HibernateUtil.getSession();
////            HibernateUtil.beingTransaccion();
////            direccion = (Unidad) HibernateUtil.getSession().createQuery("from Unidad as unidad where unidad.uniid = :id").setString("id", String.valueOf(idUni)).uniqueResult();
////        } catch (Exception x) {
////            HibernateUtil.rollbackTransaction();
////        } finally {
////            HibernateUtil.closeSession();
////        }
////        return direccion;
////        return super.find(idUni);\
//        return this.find(idUni);
//    }
//
//    public Unidad findByCriteria(int idUnidad) {
//        Session session = HibernateUtil.getSession();
//
//        Criteria criteria = session.createCriteria(Unidad.class);
////        criteria.createAlias("unidad.unidad", "unidad");//Inner Join by default
//
//        criteria.add(Restrictions.eq("uniid", String.valueOf(idUnidad)));
//        criteria.setMaxResults(10);
//        Unidad u = (Unidad) criteria.uniqueResult();
////        session.close();
//
//        return u;
////return this.findByCriteria(idUnidad);
////        List<String> propiedades = new ArrayList<>();
////        propiedades.add("programaeducativo.pedclave");
////        CriterioBusqueda busqueda = new CriterioBusqueda(CriterioBusqueda.TipoCriterio.Equal, propiedades, claveProgramaeducativo);
////        CriteriaUtil criteriaUtil = new CriteriaUtil(Planestudio.class);
////        ArrayList<CriterioBusqueda> lista = new ArrayList<>();
////        lista.add(busqueda);
////        return criteriaUtil.getByCriteria(lista);
//    }
//    
}
