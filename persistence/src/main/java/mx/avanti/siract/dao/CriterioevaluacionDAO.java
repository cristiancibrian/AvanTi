/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author Adriana
 */
public class CriterioevaluacionDAO extends AbstractDAO<Integer, Criterioevaluacion> {

   
    public List<Criterioevaluacion> findcriterios() {
        String sql = "SELECT * FROM criterioevaluacion";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Criterioevaluacion.class);

        return (List<Criterioevaluacion>) query.list();
    }
    public List<Politicaevaluacion> storeProcedureCriterioPolitica(String procedure){
        System.out.println("Save ----------");
        List<Politicaevaluacion> result = null;
        try {
            HibernateUtil.getSession();
            //System.out.println("[PRO]GetSession");
            HibernateUtil.beginTransaccion();
            Query miquery = HibernateUtil.getSession().createSQLQuery(procedure)
                    .addEntity(PoliticaTieneCriterio.class);
                    
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
}
