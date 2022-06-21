/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Areaconocimiento;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

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
    
    public Areaconocimiento findByACOnombreAndPESid(String ACOnombre, int PESid){
        
        Areaconocimiento result = null;
        result = executeQueryUnique("SELECT * FROM areaconocimiento where ACOnombre = \"" + ACOnombre + "\" and PlanEstudio_PESid = " + PESid + ";");        
        return result;
    }
    
    
}
