package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Planestudio;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author NOE_LOPEZ
 */
public class PlanestudioDAO extends AbstractDAO<Integer, Planestudio> {
    //
//    public List findByCriteria(int idProgramaeducativo) {
//        Session session = HibernateUtil.getSession();//.getSessionFactory().openSession();
//
//        Criteria criteria = session.createCriteria(Planestudio.class, "planestudio");
//        criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default
//
//        criteria.add(Restrictions.eq("programaeducativo.pedid", idProgramaeducativo));
//        criteria.setMaxResults(10);
//        List listaPlanes = criteria.list();
//        //session.close();
//
//        return listaPlanes;
//    }
//
//    public void addCatalogoReportes(Planestudio planestudio) {
////        Transaction trns = null;
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        try {
////            trns = session.beginTransaction();
////            session.save(planestudio);
////            session.getTransaction().commit();
////        } catch (RuntimeException e) {
////            if (trns != null) {
////                trns.rollback();
////            }
////            e.printStackTrace();
////        } finally {
////            session.flush();
////            session.close();
////        }
//        this.save(planestudio);
//    }
//
//    public void updatePlanestudio(Planestudio planestudio) {
////        Transaction trns = null;
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        try {
////            trns = session.beginTransaction();
////            session.saveOrUpdate(planestudio);
////            session.getTransaction().commit();
////        } catch (RuntimeException e) {
////            if (trns != null) {
////                trns.rollback();
////            }
////            e.printStackTrace();
////        } finally {
////            session.flush();
////            session.close();
////        }
//        this.saveOrUpdate(planestudio);
//    }
//
//    public void deletePlanestudio(int idPer) {
////        Transaction trns = null;
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        try {
////            trns = session.beginTransaction();
////            Planestudio planestudio = (Planestudio) session.load(Planestudio.class, new Integer(idPer));
////            session.delete(planestudio);
////            session.getTransaction().commit();
////        } catch (RuntimeException e) {
////            if (trns != null) {
////                trns.rollback();
////            }
////            e.printStackTrace();
////        } finally {
////            session.flush();
////            session.close();
////        }
//        Planestudio p = this.find(idPer);
//        if (p != null) {
//            this.delete(p);
//        }
//    }
//
//    public List<Planestudio> findAllPlanestudios() {
//        List<Planestudio> result = null;
//        HibernateUtil.getSession();
//        HibernateUtil.beingTransaccion();
//
//        try{
//            result = (List<Planestudio>) HibernateUtil.getSession().createQuery("from Planestudio").list();
//            if(result == null){
//                System.out.println("nulo");
//            }
//        }catch(Exception x){
//            x.printStackTrace();
//            HibernateUtil.rollbackTransaction();
//        }finally{
//            HibernateUtil.closeSession();
//        }
//        return result;
//        return this.findAll();
//    }
//
//    public Planestudio findByPlanestudioId(int idPer) {
////        Planestudio direccion = null;
////        
////        try {
////            HibernateUtil.getSession();
////            HibernateUtil.beingTransaccion();
////            direccion = (Planestudio) HibernateUtil.getSession().createQuery("from Planestudio as planestudio where planestudio.idPlanestudio = :id").setString("id", String.valueOf(idPer)).uniqueResult();
////        } catch (Exception x) {
////            HibernateUtil.rollbackTransaction();
////        } finally {
////            HibernateUtil.closeSession();
////        }
////        return direccion;
//        return this.find(idPer);
//    }
//
    public List findByCriteriaClave(int claveProgramaeducativo) {
        Session session = HibernateUtil.getSession();

        Criteria criteria = session.createCriteria(Planestudio.class, "planestudio");
        criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default

        criteria.add(Restrictions.eq("programaeducativo.pedclave", claveProgramaeducativo));
        List listaPlanes = criteria.list();
        //session.close();
        return listaPlanes;
        /*
              List<String> propiedades=new ArrayList<>();
                propiedades.add("programaeducativo.pedclave");
               CriterioBusqueda busqueda=new CriterioBusqueda(CriterioBusqueda.TipoCriterio.Equal, propiedades,claveProgramaeducativo );
               CriteriaUtil criteriaUtil=new CriteriaUtil(Planestudio.class);
               ArrayList<CriterioBusqueda> lista=new ArrayList<>();
               lista.add(busqueda);
              return criteriaUtil.getByCriteria(lista);
         */
    }

    //
//    public List<Planestudio> buscarPlanEstudioByProgramaEducativo(String idPE) {
//        //return ServiceFacadeLocator.getInstanceFacadePlanEstudio().buscarPlanEstudioByProgramaEducativo(idPE);
//        //List<Planestudio> resultado = null;
//        //ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        //resultado = ServiceLocator.getInstanceBaseDAO().findFromWhereB("programaeducativo", "pedid",String.valueOf(idPE),"pesvigenciaPlan");
//        return this.findFromWhereB("programaeducativo", "pedid", String.valueOf(idPE), "pesvigenciaPlan");
//    }
//
//    public List<Planestudio> getPlanEstudioPE(int pedid) {
//        /*List<Planestudio> result = null;
//        result = ServiceLocator.getInstancePlanestudio().findByCriteria(pedid);
//        return result;*/
//        //return ServiceFacadeLocator.getInstanceFacadePlanEstudio().getProfMismoPE(pedid);
//        /*List<Planestudio> listaPE = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        listaPE = ServiceLocator.getInstanceBaseDAO().findFromWhere("unidadacademicas", "uacid", String.valueOf(pedid));*/
//        return this.findFromWhere("unidadacademicas", "uacid", String.valueOf(pedid));
//    }
//
//    public List<Planestudio> getPlanMismoPE(int pedid) {
//        //return ServiceLocator.getInstancePlanestudio()
//        //return ServiceFacadeLocator.getInstanceFacadePlanEstudio().getPlanMismoPE(pedid);
//        /*List<Planestudio> listaPlan = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        listaPlan = ServiceLocator.getInstanceBaseDAO().findFromWhere("programaeducativos", "pedid", String.valueOf(pedid));*/
//        return this.findFromWhere("programaeducativos", "pedid", String.valueOf(pedid));
//    }
//
//    public List<Planestudio> getPlanAsignadoGrupo(int pedid) {
//        //return ServiceFacadeLocator.getInstanceFacadePlanEstudio().getPlanAsignadoGrupo(pedid);
//        /*List<Planestudio> listaPlan = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Grupo.class);
//        listaPlan = ServiceLocator.getInstanceBaseDAO().findFromWhereB("planestudio","pesid", String.valueOf(pedid),"gpoid");*/
//        return this.findFromWhereB("planestudio", "pesid", String.valueOf(pedid), "gpoid");
//    }
//
//    public List<Planestudio> getPlanAsignadoAreaConocimiento(int pedid) {
//        //return ServiceFacadeLocator.getInstanceFacadePlanEstudio().getPlanAsignadoAreaConocimiento(pedid);
//        /*List<Planestudio> listaPlan = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Areaconocimiento.class);
//        listaPlan = ServiceLocator.getInstanceBaseDAO().findFromWhereB("planestudio","pesid", String.valueOf(pedid),"acoid");*/
//        return this.findFromWhereB("planestudio", "pesid", String.valueOf(pedid), "acoid");
//    }
//
//    public List<Planestudio> buscarPlanEstudioDeAreaConocimiento(String idAco) {
//        //return ServiceFacadeLocator.getInstanceFacadePlanEstudio().buscarPlanEstudioDeAreaConocimiento(idAco);
//        /*List<Planestudio> resultado = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        resultado = ServiceLocator.getInstanceBaseDAO().findFromWhere("Planestudio","areaconocimientos", "acoid", idAco);*/
//        return this.findFromWhere("Planestudio", "areaconocimientos", "acoid", idAco);
//    }
//
//    public List<Planestudio> buscarPlanEstudioByUnidadAcademica(int idUa) {
//        /*List<Planestudio> resultado = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        resultado = ServiceLocator.getInstanceBaseDAO().findFromWhereB("programaeducativo.unidadacademica", "uacid",String.valueOf(idUa),"pesvigenciaPlan");*/
//        return this.findFromWhereB("programaeducativo.unidadacademica", "uacid", String.valueOf(idUa), "pesvigenciaPlan");
//    }
//
//    public List<Planestudio> buscarPlanEstudioByUnidadAprendizae(int idUa) {
//        /*List<Planestudio> resultado = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        resultado = ServiceLocator.getInstanceBaseDAO().findFromWhere("Planestudio", "areaconocimientos c join c.unidadaprendizajes","uapclave",String.valueOf(idUa));*/
//        return this.findFromWhere("Planestudio", "areaconocimientos c join c.unidadaprendizajes", "uapclave", String.valueOf(idUa));
//    }
//    public List<Planestudio> buscarPlanEstudio(String idPuesto) {
//        /*List<Planestudio> resultado = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        resultado = ServiceLocator.
//                getInstanceBaseDAO().findPlanesWherePuesto("responsableprogramaeducativos","pedid" , "pueid", idPuesto);*/
//        return this.findPlanesWherePuesto("responsableprogramaeducativos", "pedid", "pueid", idPuesto);
//    }
//    public List<Planestudio> buscarPlanEstudio(int pedid) {
//        /*List<Planestudio> resultado = null;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Planestudio.class);
//        resultado = ServiceLocator.getInstanceBaseDAO().findPlanesWherePE(pedid);*/
//        return this.findPlanesWherePE(pedid);
//    }

    /**
     * Funcionamiento:
     * Este método busca en la base de datos si existe un Plan de estudios,
     * esto lo hace buscandolo por medio de su vigencia.
     *
     * Problema:
     * Este método espera regresar un SOLO plan de estudio buscandolo por su vigencia, sin embargo
     * cuando existen plandes de estudios con vigencias IGUALES devuelve un valor NULL porque intenta
     * guardar los multiples Planes de Estudio la variable result. Probablemente este método
     * no esta planteado correctamente.
     *
     * Posible solución:
     * Hacer que esté método retorne una LISTA de objetos tipo PlanEstudio.
     *      
     * @param vigencia
     * @return Objeto de tipo PlanEstudio
     */
    public Planestudio getPlanByPESVigencia(String vigencia) {
        Planestudio result = null;
        result = this.executeQueryUnique("SELECT * FROM planestudio where pesvigenciaplan = '" + vigencia + "';");
        System.out.println("RESULTADO: " + result);
        return result;
    }

    public Planestudio getPlanById(int id) {
        Planestudio result = null;
        result = this.executeQueryUnique("SELECT * FROM planestudio where PESid = '" + id + "';");
//            System.out.println("RESULTADO: " + result);
        return result;
    }
    
    /**
     * Funcionamiento: 
     * Este método realiza una búsqueda en la base de datos si existe un plan de estudios,
     * esto lo hace buscando por medio de su vigencia y el ID del programa educativo
     * al que está asociado.
     * 
     * * Solución:
     * Se crea un nuevo método donde se realiza una búsqueda un poco más específica, el objetivo
     * de esta búsqueda es retornar el plan de estudio que cumpla con la vigencia y el ID del
     * programa educativo al que está asociado, de esta manera nos traerá como resultado un solo
     * registro el cual posteriormente registraremos o actualizaremos según su existencia.
     * 
     * @param pedid
     * @param vigencia
     * @return 
     */
    
    public Planestudio getPlanByPEDidAndPESVigencia(String vigencia, int pedid){
        
        Planestudio result = null;
        result = this.executeQueryUnique("SELECT * FROM planestudio where PESvigenciaPlan = '" + vigencia + "' and ProgramaEducativo_PEDid = " + pedid + ";");       
        
        return result;       
    }

}