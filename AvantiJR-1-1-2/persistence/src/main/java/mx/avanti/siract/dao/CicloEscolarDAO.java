package mx.avanti.siract.dao;

import java.util.ArrayList;
import java.util.List;

import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.integration.ServiceLocator;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * @author Oscar D. Sanchez
 */
public class CicloEscolarDAO extends AbstractDAO<Integer, Cicloescolar> {

    /**
     * Metodo para regresar una lista ordenada de ciclos escolares, empezando
     * por el Ciclo del mas reciente hasta el mas viejo
     *
     * @return lista ordenada de ciclos escolares
     */
    public List<Cicloescolar> getListaOrdenada() {
        String query = "select * from cicloescolar order by cescicloEscolar DESC";

        return executeQuery(query);
    }

    public void endTransaction(boolean completo) {
        if (completo) {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        } else
            HibernateUtil.rollbackTransaction();
    }

    public boolean executeProcedureCiclo(String procedure) {
        List<Cicloescolar> result;
        boolean bandera = true;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Cicloescolar.class);
            result = iQuery.list();
        } catch (Exception e) {
            bandera = false;
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return bandera;
        }
    }

    public List<Cicloescolar> executeProcedureCiclos(String procedure) {
        List<Cicloescolar> result = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Cicloescolar.class);
            result = iQuery.list();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return result;
        }
    }

    public List<Cicloescolar> executeProcedure(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<Cicloescolar> result = new ArrayList();
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Cicloescolar.class);
            result = iQuery.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }

}
