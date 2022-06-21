/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author Adriana
 */
public class PoliticaTieneCriterioDAO extends AbstractDAO<Integer, PoliticaTieneCriterio> {
    public List<PoliticaTieneCriterio> findcriteriospolitica(int id) {
        String sql = "SELECT * FROM politica_tiene_criterio WHERE POEid="+id;
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(PoliticaTieneCriterio.class);

        return (List<PoliticaTieneCriterio>) query.list();
    }
    
}
