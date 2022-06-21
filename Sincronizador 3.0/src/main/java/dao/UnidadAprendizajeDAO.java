package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Unidadaprendizaje;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;

import java.util.List;

/**
 * @author Adriana
 */
public class UnidadAprendizajeDAO extends AbstractDAO<Integer, Unidadaprendizaje> {
    /**
     * Obtiene las unidades de aprendizaje y grupos
     * por profesor, programa educativo y ciclo escolar
     *
     * @param proid            Id del Profesor
     * @param peid             Id del Programa Educativo
     * @param ce               Ciclo Escolar
     * @param coordinadorProId Id del Coordinador de Programa Educativo
     * @return
     * @author Mario
     */
    public List<Object[]> findByProfesorYPE(int proid, int peid, String ce, int coordinadorProId) {

        String sql =
                "SELECT distinct ua.*, g.*, uaip.UIPtipoSubgrupo, uaip.UIPsubgrupo, uaip.UIPid FROM unidadaprendizaje ua "
                        + "INNER JOIN unidadaprendizaje_imparte_profesor uaip on ua.UAPid = uaip.UnidadAprendizaje_UAPid "
                        + "INNER JOIN grupo g ON uaip.Grupo_GPOid = g.GPOid ";

        if (peid != 0 && peid != 37) {
            sql += "INNER JOIN planestudio pes ON g.PlanEstudio_PESid = pes.PESid "
                    + "INNER JOIN programaeducativo pe On pe.PEDid = pes.ProgramaEducativo_PEDid ";
        } else if (peid == 37) {
            sql += "INNER JOIN areaconocimiento_has_unidadaprendizaje ahu ON ahu.UnidadAprendizaje_UAPid = ua.UAPid " +
                    "INNER JOIN areaconocimiento aco ON aco.ACOid = ahu.AreaConocimiento_ACOid " +
                    "INNER JOIN planestudio pes ON aco.PlanEstudio_PESid = pes.PESid ";
        }

        if (coordinadorProId != 0) {
            sql += "INNER JOIN coordinadorareaadministrativa caa ON caa.UnidadAprendizaje_UAPid = ua.UAPid ";
        }

        sql += "WHERE uaip.CicloEscolar_CESid = :ce ";
        if (peid != 0 && peid != 37) {
            sql += "AND pe.PEDid = :peid ";
        }
        if (peid == 37) {
            sql += "AND pes.ProgramaEducativo_PEDid = :peid ";
        }
        if (proid != 0) {
            sql += "AND uaip.Profesor_PROid = :proid ";
        }
        if (coordinadorProId != 0) {
            sql += "AND caa.Profesor_PROid = :coproid ";
        }
        sql += "ORDER BY ua.UAPnombre,g.GPOnumero,uaip.UIPsubgrupo,uaip.UIPtipoSubgrupo";

        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setString("ce", ce);
        query.setInteger("peid", peid);

        if (proid != 0) {
            query.setInteger("proid", proid);
        }

        if (coordinadorProId != 0) {
            query.setInteger("coproid", coordinadorProId);
        }

        return query.list();

    }

    /**
     * Metodo utilizado para obtener una lista de unidades aprendizaje
     * basandose en un programa educativo seleccionado
     *
     * @param PESeleccionado Programa educativo seleccionado
     * @param CE             Ciclo escolar del programa educativo
     * @param rol            Rol del profesor
     * @param profId         Id del profesor
     * @return Lista de unidades de aprendizaje por programa
     * educativo
     */
    public List<Unidadaprendizaje> getListaUnidadAprendizajePorProgramaEducativo(String PESeleccionado, String CE, String rol, String profId) {
        List<Unidadaprendizaje> resultado = null;
        switch (rol) {
            case "Administrador":
                resultado = this.executeQueryHql("select distinct ua from Unidadaprendizaje ua join ua.unidadaprendizajeImparteProfesors uaip join ua.coordinadorareaadministrativas as caa join caa.areaadministrativa aa where uaip.cicloescolar.cesid=" + CE + " and aa.programaeducativo.pedid=" + PESeleccionado);
                break;

            case "Coordinador de Área de Conocimiento":
                resultado = this.executeQueryHql("select distinct ua from Unidadaprendizaje ua join ua.unidadaprendizajeImparteProfesors uaip join ua.coordinadorareaadministrativas caa join caa.areaadministrativa aa where uaip.cicloescolar.cesid=" + CE + " and aa.programaeducativo.pedid=" + PESeleccionado + " and caa.profesor.proid=" + profId);
                break;

            case "Responsable de Programa Educativo":
                break;

            case "Profesor":
                System.out.println("FacadeUnidadAprendizaje:118:Entro");
                int profesorID = Integer.parseInt(profId);
                int cicloEscolarID = Integer.parseInt(CE);
                int programaEstudioID = Integer.parseInt(PESeleccionado);
                resultado = this.executeQueryHql("select distinct ua \n"
                        + "from Unidadaprenziaje ua \n"
                        + "join ua.unidadaprendizajeImparteProfesors uaip \n"
                        + "join uaip.grupo g\n"
                        + "join g.planestudio pes\n"
                        + "join pes.programaeducativo pe\n"
                        + "where ua.uapid=uaip.unidadaprendizaje.uapid\n"
                        + "and uaip.grupo.gpoid=g.gpoid\n"
                        + "and g.planestudio.pesid=pes.planestudio.pesid"
                        + "and uaip.profesor.proid=" + profesorID
                        + "and uaip.cicloescolar.cesid=14"
                        + "and pe.pedid=;" + programaEstudioID);

        }

        return resultado;
    }


    /**
     * Obtiene las unidades de aprendizaje de un programa educativo en
     * especifico
     *
     * @return Unidades de aprendizaje de un programa educativo
     */
    public List unidadAprendizaje_PertenecePE() {
        List result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
            result = (List) HibernateUtil.getSession().createQuery("SELECT\n"
                    + "	 u.uapclave\n"
                    + "FROM\n"
                    + "	 Unidadaprendizaje as u,\n"
                    + "	Areaconocimiento as a,\n"
                    + "	Planestudio as p,\n"
                    + "	Programaeducativo as pr\n"
                    + "WHERE\n"
                    + "	  u.uapid  in  elements(a.unidadaprendizajes)\n"
                    + "AND\n"
                    + "	a.acoid in elements(u.areaconocimientos)\n"
                    + "AND\n"
                    + "	 a.planestudio=p.pesid\n"
                    + "AND\n"
                    + "	p.programaeducativo=pr.pedid").list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {

        }
        return result;
    }

    /**
     * Obtiene las Unidades de Aprendizaje por Unidad Academica
     *
     * @param idUA Id de la Unidad Academica
     * @return Lista de unidades de aprendizaje por Unidad Academica
     */
    public List unidadByUnidadAcademica(String idUA) {
        List result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        try {
            result = (List) HibernateUtil.getSession().createQuery("SELECT\n"
                    + "	 u\n"
                    + "FROM\n"
                    + "	 Unidadaprendizaje as u,\n"
                    + "	Areaconocimiento as a,\n"
                    + "	Planestudio as p,\n"
                    + "	Programaeducativo as pr,\n"
                    + " Unidadacademica as ua\n"
                    + "WHERE\n"
                    + "	  u.uAPid  in  elements(a.unidadaprendizajeList)\n"
                    + "AND\n"
                    + "	a.aCOid in elements(u.areaconocimientoList)\n"
                    + "AND\n"
                    + "	 a.planEstudioPESid=p.pESid\n"
                    + "AND\n"
                    + "	p.programaEducativoPEDid=pr.pEDid\n"
                    + "AND\n"
                    + "pr.unidadAcademicaUACid=ua.uACid").list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {

        }
        return result;
    }

    /**
     * Obtiene las Unidades de Aprendizaje que pertenecen a Programa Educativo
     *
     * @return Lista de Unidades de Aprendizaje que pertenecen a un Programa
     * Educativo
     */
    public List unidadAprendizaje_PertenecePE2() {
        List result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
            result = (List) HibernateUtil.getSession().createQuery("SELECT\n"
                    + "	 pr.pednombre\n"
                    + "FROM\n"
                    + "	 Unidadaprendizaje as u,\n"
                    + "	Areaconocimiento as a,\n"
                    + "	Planestudio as p,\n"
                    + "	Programaeducativo as pr\n"
                    + "WHERE\n"
                    + "	  u.uapid  in  elements(a.unidadaprendizajes)\n"
                    + "AND\n"
                    + "	a.acoid in elements(u.areaconocimientos)\n"
                    + "AND\n"
                    + "	 a.planestudio=p.pesid\n"
                    + "AND\n"
                    + "	p.programaeducativo=pr.pedid").list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            // HibernateUtil.closeSession();
        }
        return result;
    }

    /**
     * Metodo para buscar por Clave
     *
     * @param acoclave Clave del area de conocimientos
     * @return Lista de las Unidades de Aprendizaje que tengan una clave
     * especifica
     */
    public List findByCriteriaClave(int acoclave) {
        Session session = HibernateUtil.getSession();

        Criteria criteria = session.createCriteria(Unidadaprendizaje.class, "unidadaprendizaje");
        criteria.createAlias("unidadaprendizaje.areaconocimientos", "areaconocimientos"); //Inner Join by default

        criteria.add(Restrictions.eq("areaconocimientos.acoclave", acoclave));
        List listaUnidadesAprendizaje = criteria.list();
        return listaUnidadesAprendizaje;
    }

    /**
     * Metodo para Obtener Unidad de Aprendizaje por Id y Area de Conocimiento
     *
     * @param id    Id de la Unidad de Aprendizaje
     * @param acoId Id del Area de Conocimiento
     * @return Unidad de Aprendizaje encontrada
     */
    public Unidadaprendizaje findUap(int id, String acoId) {
        Object obj = null;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata("unidadaprendizaje");
        String nombre = "unidadaprendizaje";
        String identificador = "uAPclave";

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            obj = HibernateUtil.getSession().createQuery("from " + "unidadaprendizaje" + " as " + nombre.toLowerCase() + " where " + identificador + " = :clave").setString("clave", String.valueOf(id)).uniqueResult();

        } catch (Exception x) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
        }
        return (Unidadaprendizaje) obj;
    }

    public Unidadaprendizaje getUnidadAprendizajeByClave(int clave) {
        Unidadaprendizaje result = null;
        result = this.executeQueryUnique("Select * from Unidadaprendizaje where UAPclave = '" + clave + "' ");
        return result;
    }
}
