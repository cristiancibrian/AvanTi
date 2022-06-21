/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;

import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * @author Oscar D. Sanchez
 */
public class AreaConocimientoDAO extends AbstractDAO<Integer, Areaconocimiento> {
    /**
     * Metodo para encontrar el area de conocimiento con un id de
     * ProgramaEducativo
     *
     * @param idPrograma ID por el cual se buscara el ProgramaEducativo
     * @return Regresa una lista de objetos de tipo Areaconocimiento
     */
    public List<Areaconocimiento> findByProgramaEducativo(int idPrograma) {
        return this.findByProgramaEducativo(idPrograma);
    }

    /**
     * Metodo para encontrar una clave de area de conocimiento por criteria
     *
     * @param progEduClave         Clave del programa educativo del area de conocimiento
     * @param planEstudiosVigencia Vigencia del plan de estudio del area de
     *                             conocimiento
     * @return Regresa una lista de las areas de conocimiento encontradas
     */
    public List findByCriteriaClave(int progEduClave, String planEstudiosVigencia) {
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Areaconocimiento.class,
                "areaconocimiento");
        criteria.createAlias("areaconocimiento.planestudio",
                "planestudio");//Inner Join by default
        criteria.createAlias("planestudio.programaeducativo",
                "programaeducativo");//Inner Join by default

        criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan",
                planEstudiosVigencia));
        criteria.add(Restrictions.eq("programaeducativo.pedclave",
                progEduClave));
        List listaAreas = criteria.list();

        return listaAreas;
    }

    //Método que busca un área de conocimiento por Unidad de Aprendizaje.
    public int findByUnidadAprendizaje(int valor) {
        List<Areaconocimiento> ac = executeQuery("CALL findByUnidadAprendizaje (\'" + valor + "\')");
        int regreso = 0;
        for (Areaconocimiento aco : ac) {
            regreso = aco.getACOid();
        }

        return regreso;
    }

    public Areaconocimiento buscarID_Areaconocimiento(int idAreaConocimiento) {
        List<Areaconocimiento> ac = executeQuery("CALL buscarID_Areaconocimiento (\'" + idAreaConocimiento + "\')");
        return ac.get(0);
    }

    public int EsperadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar, int idAreaCon) {
        int cantidad = 0;

        return cantidad;
    }

    public void endTransaction() {
        HibernateUtil.rollbackTransaction();
        HibernateUtil.commitTransaction();
        HibernateUtil.closeSession();
    }

    public List<Areaconocimiento> executeProcedure(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Areaconocimiento> result;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Areaconocimiento.class);
        result = iQuery.list();
        return result;
    }
}

