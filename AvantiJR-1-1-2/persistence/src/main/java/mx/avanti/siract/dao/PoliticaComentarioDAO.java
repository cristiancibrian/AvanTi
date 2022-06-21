/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author eduardocardona
 */
public class PoliticaComentarioDAO extends AbstractDAO<Integer, Politicaevaluacioncomentario>  {
    
    private Session session;


    public void initSession() {
        setSession(HibernateUtil.getSessionFactory().openSession());

    }
    
    
    public List<Politicaevaluacioncomentario> ExecuteStoreProcedure(String procedure){
        System.out.println("Save ----------");
        List<Politicaevaluacioncomentario> result = null;
        try {
            HibernateUtil.getSession();
            //System.out.println("[PRO]GetSession");
            HibernateUtil.beginTransaccion();
            Query miquery = HibernateUtil.getSession().createSQLQuery(procedure)
                    .addEntity(Politicaevaluacioncomentario.class);
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
