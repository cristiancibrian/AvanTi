/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author ODOSMO
 */
public class GrupoDAO extends AbstractDAO<Integer, Grupo> {

    /**
     * Metodo para obtener Grupos por Unidad de Aprendizaje
     *
     * @param UAClave Clave de la Unidad de Aprendizaje
     * @param PEId Id del Programa Educativo
     * @param CE Ciclo escolar
     * @return Lista de Grupos encontrados
     */
    public List<Grupo> consultaGruposporUA(int UAClave, String PEId, String CE) {
        List<Grupo> resultado = null;

        resultado = this.executeQueryHql("select distinct a from Grupo a join a.unidadaprendizajeImparteProfesors b join a.planestudio pe join pe.programaeducativo proe where b.unidadaprendizaje.uapclave=" + UAClave + " and b.cicloescolar.cesid=" + CE + " and proe.pedid=" + PEId);
        return resultado;
    }
    
    public List<Grupo>  executeProcedureText(String procedure){
        System.out.println("ExecuteProcedure ----------");
        List<Grupo> result = null;
        try{
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        Query Query = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Grupo.class);
        result= Query.list();
        }catch(HibernateException e){
            HibernateUtil.rollbackTransaction();
            return null;
        }
       finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();   
        }
        return result;
       
    }
    
}
