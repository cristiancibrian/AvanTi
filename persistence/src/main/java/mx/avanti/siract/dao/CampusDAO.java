package mx.avanti.siract.dao;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Paul Miranda
 */
public class CampusDAO extends AbstractDAO<Integer, Campus>{
    public void endTransaction(boolean completo){
        if(completo){
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }else
            HibernateUtil.rollbackTransaction();
    }


    public List<Campus> executeProcedureTest(String procedure){
        List<Campus> resultado = null;

        System.out.println("ExecuteProcedureTest------------");
        try{
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query query = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Campus.class);
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
