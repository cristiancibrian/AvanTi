package mx.avanti.siract.dao;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 * 
 * @author Javier Razo
 */
public class ProgramaeducativoDAO extends AbstractDAO<Integer, Programaeducativo>{
    /**
     * Consulta de ProgramaEducativo(PE) por ProfesorPerteneceProgramaEducativo(PPPE)
     * @param idProfesor
     * @param idCicloEscolar
     * @return Lista de ProgramaEducativo
     */
    public List<Programaeducativo> findByIdProfesor(int idProfesor, int idCicloEscolar) {
        String hql = "Select distinct pe from Programaeducativo pe join pe.profesorPerteneceProgramaeducativos ppp join ppp.id ppid where ppid.profesorProid="+idProfesor+" and ppid.cicloEscolarCesid="+idCicloEscolar;
        return this.executeQueryHql(hql);
    }

    public List<Programaeducativo> findByNamePE(String name){
        String hql = "select * from Programaeducativo where PEDnombre='"+name+"'";
        return this.executeQueryHql(hql);
    }
    
    /**
     * Consulta de Programa educativo por AreaAdministrativa
     * @param idProf
     * @return Lista de ProgramaEducativo
     */
    public List<Programaeducativo> findAllByAreaAdministrativa(String idProf) {
        return this.executeQueryHql("select distinct pe from Programaeducativo pe join pe.areaadministrativas aa join aa.coordinadorareaadministrativas caa join caa.profesor p where p.proid=" + idProf);
    }

    /**
     * Consulta de Programa educativo por Responsable de Programa Educativo
     * @param rpeProId
     * @return  Lista de ProgramaEducativo
     */
    public List<Programaeducativo> findByRPE(String rpeProId){
        int proId = Integer.parseInt(rpeProId);
        String sql = String.format("SELECT ped.* FROM programaeducativo ped\n" +
                                   "INNER JOIN responsableprogramaeducativo rpe\n" +
                                   "ON rpe.ProgramaEducativo_PEDid = ped.PEDid\n" +
                                   "WHERE rpe.Profesor_PROid = %d", proId);
        return this.executeQuery(sql);
    }
 
        /**
         * Consulta programa educativo por clave de UnidadAcedemica
         * @param claveUnidadacademica
         * @return Lista de programa educativos de unidad academica
         */
    public List findByCriteriaClave(int claveUnidadacademica){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Criteria criteria = session.createCriteria(Programaeducativo.class, "programaeducativo");
        criteria.createAlias("programaeducativo.unidadacademica", "unidadacademica");//Inner Join by default
        
        criteria.add(Restrictions.eq("unidadacademica.uacclave", claveUnidadacademica));
        List listaProgramas = criteria.list();
        session.close();
        
        return listaProgramas;
    }
    
    public void endTransaction(boolean completo){
        if(completo){
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }else
            HibernateUtil.rollbackTransaction();
    }
    
    public List<Programaeducativo> executeProcedure(String procedure){
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<Programaeducativo> result = new ArrayList();
        try{
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Programaeducativo.class);
            result = iQuery.list();
        }catch(HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }
}