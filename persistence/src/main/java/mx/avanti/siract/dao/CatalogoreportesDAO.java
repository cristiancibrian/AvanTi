package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Catalogoreportes;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Graciela
 */
public class CatalogoreportesDAO extends AbstractDAO<Integer,Catalogoreportes>{
    
    /**
     * Metodo que agregar un catalogo de reportes
     * @param catalogoreportes Objeto con los valores a guardar
     */
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
    
    /**
     * Metodo que actualiza el catalogo de reportes
     * @param catalogoreportes Objeto con los valores a actualizar
     */
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
    
    /**
     * Metodo que elimina un catalogo de reportes
     * @param idPer Identificador con el que se eliminara el catalogo
     */
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
    
    /**
     * Metodo que consulta todos los reportes creados
     * @return Lista de objetos que contiene todos los catalogoreportes 
     * encontrados
     */
    public List<Catalogoreportes> findAllCatalogoreportess(){
        List<Catalogoreportes> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        
        try{
            result = (List<Catalogoreportes>) HibernateUtil.getSession().createQuery("from Catalogoreportes").list();
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
        
    /**
     * Metodo que realiza una busqueda especifica de un catalogo de reportes
     * @param idPer Identificador del catalogo
     * @return Retorna un objeto con los valores del catalogo de reportes
     */
    public Catalogoreportes findByCatalogoreportesId(int idPer) {
        Catalogoreportes direccion = null;
        
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            direccion = (Catalogoreportes) HibernateUtil.getSession().createQuery("from Catalogoreportes as catalogoreportes where catalogoreportes.cTRid = :id").setString("id", String.valueOf(idPer)).uniqueResult();
        } catch (Exception x) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return direccion;
    }
    
    
    public void findByCriteria(String tipoCriteria){
        
        if(tipoCriteria=="Entregados"){
            
        }
    }
}

