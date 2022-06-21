package mx.avanti.siract.dao;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Axel Valenzuela. Avanti
 */
public class UnidadacademicaDAO extends AbstractDAO<Integer, Unidadacademica> {
    public void endTransaction(boolean completo){
        if(completo){
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }else
            HibernateUtil.rollbackTransaction();
    }
    
    public List<Unidadacademica> executeProcedure(String procedure){
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<Unidadacademica> result = new ArrayList();
        try{
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Unidadacademica.class);
            result = iQuery.list();
        }catch(HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }
}
