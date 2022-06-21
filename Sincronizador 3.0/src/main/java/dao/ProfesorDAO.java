/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Profesor;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * @author Adriana
 */
public class ProfesorDAO extends AbstractDAO<Integer, Profesor> {

    /**
     * Obtiene los Profesores correspondientes a un Coordinador de Area
     * Administrativa y un Ciclo Escolar determinado
     *
     * @param coordinadorProId Id del Coordinador
     * @param ce               Ciclo Escolar
     * @return Lista de Profesores encontrados
     * @author Mario
     */
    public List<Profesor> findByCoordinadorYCE(int coordinadorProId, String ce) {

//        String sql = "SELECT pro.* FROM profesor pro "
//                + "WHERE pro.PROid IN ( "
//                + "SELECT pro.PROid FROM coordinadorareaadministrativa caa "
//                + "INNER JOIN unidadaprendizaje uap ON uap.UAPid = caa.UnidadAprendizaje_UAPid "
//                + "INNER JOIN unidadaprendizaje_imparte_profesor uip ON uip.UnidadAprendizaje_UAPid = uap.UAPid "
//                + "INNER JOIN profesor pro ON pro.PROid = uip.Profesor_PROid "
//                + "WHERE caa.Profesor_PROid = : " + coordinadorProId + "  "
//                + "AND uip.CicloEscolar_CESid = "+ ce +" "
//                + ") AND pro.PROid IN ( "
//                + "SELECT pro.PROid FROM coordinadorareaadministrativa caa "
//                + "INNER JOIN areaadministrativa aad ON caa.AreaAdministrativa_AADid = aad.AADid "
//                + "INNER JOIN programaeducativo pe ON pe.PEDid = aad.ProgramaEducativo_PEDid "
//                + "INNER JOIN profesor_pertene_programaeducativo ppp ON ppp.ProgramaEducativo_PEDid = pe.PEDid "
//                + "INNER JOIN profesor pro ON pro.PROid = ppp.Profesor_PROid "
//                + "WHERE caa.Profesor_PROid = : " + coordinadorProId + "  "
//                + "AND ppp.CicloEscolar_CESid = : " + ce + "  "
//                + ") ORDER BY pro.PROapellidoPaterno, pro.PROapellidoMaterno, pro.PROnombre";
//        //SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
////        sql.replace.("proid","coordinadorProId);
////        query.setString("ce", ce);
////        query.addEntity(Profesor.class);
//      
        String sql = "select * from profesor where PROid in (\n"
                + "select  Profesor_PROid from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + ce + "  and Grupo_GPOid in(\n"
                + "select GPOid from grupo where PlanEstudio_PESid in (\n"
                + "select PESid from planestudio where ProgramaEducativo_PEDid in (\n"
                + "select ProgramaEducativo_PEDid from areaadministrativa where AADid in (\n"
                + "select AreaAdministrativa_AADid from coordinadorareaadministrativa where Profesor_PROid = " + coordinadorProId + " ))))\n"
                + "and UnidadAprendizaje_UAPid in  ( \n"
                + "select UnidadAprendizaje_UAPid from areaadministrativa_tiene_unidadaprendizaje where CoordinadorAdministrativa_COAid in \n"
                + "(select COAid from coordinadorareaadministrativa where Profesor_PROid = " + coordinadorProId + " )));";
        List<Profesor> list = this.executeQuery(sql);

        return list;

    }

    /**
     * Obtiene los Profesores que pertenecen a un Programa Educativo en un Ciclo
     * Escolar determinado
     *
     * @param rpeProId Id de Profesor del Responsable del Programa Educativo
     * @param ce       Ciclo Escolar
     * @return Lista de Profesores encontrados
     * @author Mario
     */
    public List<Profesor> findByRPEYCE(int rpeProId, String ce) {
        String sql = "SELECT pro.* FROM profesor pro "
                + "INNER JOIN profesor_pertenece_programaeducativo ppp ON ppp.Profesor_PROid = pro.PROid "
                + "INNER JOIN programaeducativo ped ON ped.PEDid = ppp.ProgramaEducativo_PEDid "
                + "INNER JOIN responsableprogramaeducativo rpe ON rpe.ProgramaEducativo_PEDid = ped.PEDid "
                + "WHERE rpe.Profesor_PROid = :proid AND ppp.CicloEscolar_CESid = :ce "
                + "ORDER BY pro.PROapellidoPaterno, pro.PROapellidoMaterno, pro.PROnombre";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Profesor.class);
        query.setInteger("proid", rpeProId);
        query.setString("ce", ce);

        return (List<Profesor>) query.list();
    }

    /**
     * Obtiene los Profesores que pertenecen a un Programa Educativo en un
     * determinado Ciclo Escolar
     *
     * @param pe Id del Programa Educativo
     * @param ce Ciclo Escolar
     * @return Lista de Profesores encontrados
     * @author Mario
     */
    public List<Profesor> findByPEYCE(int pe, String ce) {
        String sql;
        if (pe != 37) {
            sql = "SELECT distinct pro.* FROM profesor pro "
                    + "INNER JOIN profesor_pertenece_programaeducativo ppp "
                    + "ON pro.PROid = ppp.Profesor_PROid "
                    + "WHERE ppp.ProgramaEducativo_PEDid = :pe "
                    + "AND ppp.CicloEscolar_CESid = :ce "
                    + "ORDER BY pro.PROapellidoPaterno, pro.PROapellidoMaterno, pro.PROnombre";
        } else {
            sql = "select distinct pro.* from profesor pro\n"
                    + "inner join unidadaprendizaje_imparte_profesor uaip\n"
                    + "on uaip.Profesor_PROid = pro.PROid\n"
                    + "inner join unidadaprendizaje ua\n"
                    + "on ua.UAPid = uaip.UnidadAprendizaje_UAPid\n"
                    + "inner join areaconocimiento_has_unidadaprendizaje ahu\n"
                    + "on ahu.UnidadAprendizaje_UAPid = ua.UAPid\n"
                    + "inner join areaconocimiento aco\n"
                    + "on aco.ACOid = ahu.AreaConocimiento_ACOid\n"
                    + "inner join planestudio pes \n"
                    + "on aco.PlanEstudio_PESid = pes.PESid\n"
                    + "WHERE pes.ProgramaEducativo_PEDid = :pe\n"
                    + "AND uaip.CicloEscolar_CESid = :ce\n"
                    + "ORDER BY pro.PROapellidoPaterno, pro.PROapellidoMaterno, pro.PROnombre";
        }
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setInteger("pe", pe);
        query.setString("ce", ce);
        query.addEntity(Profesor.class);
        List raw = query.list();
        List<Profesor> profesores = raw;
        return profesores;
    }

    /**
     * Obtiene los Profesores que pertenecen a un Programa Educativo
     *
     * @param pe Id del Programa Educativo
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> findByPE(int pe) {
        String sql;
        if (pe != 37) {
            sql = "SELECT distinct pro.* FROM profesor pro "
                    + "INNER JOIN profesor_pertenece_programaeducativo ppp "
                    + "ON pro.PROid = ppp.Profesor_PROid "
                    + "WHERE ppp.ProgramaEducativo_PEDid = :pe "
                    + "ORDER BY pro.PROapellidoPaterno, pro.PROapellidoMaterno, pro.PROnombre";
        } else {
            sql = "select distinct pro.* from profesor pro\n"
                    + "inner join unidadaprendizaje_imparte_profesor uaip\n"
                    + "on uaip.Profesor_PROid = pro.PROid\n"
                    + "inner join unidadaprendizaje ua\n"
                    + "on ua.UAPid = uaip.UnidadAprendizaje_UAPid\n"
                    + "inner join areaconocimiento_has_unidadaprendizaje ahu\n"
                    + "on ahu.UnidadAprendizaje_UAPid = ua.UAPid\n"
                    + "inner join areaconocimiento aco\n"
                    + "on aco.ACOid = ahu.AreaConocimiento_ACOid\n"
                    + "inner join planestudio pes \n"
                    + "on aco.PlanEstudio_PESid = pes.PESid\n"
                    + "WHERE pes.ProgramaEducativo_PEDid = :pe\n"
                    + "ORDER BY pro.PROapellidoPaterno, pro.PROapellidoMaterno, pro.PROnombre";
        }
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setInteger("pe", pe);
        query.addEntity(Profesor.class);
        List raw = query.list();
        List<Profesor> profesores = raw;
        return profesores;
    }
    public Profesor getProfesorByNumEmpleado(int numeroEmpleado) {
        Profesor result = null;
        result = this.executeQueryUnique("Select * from Profesor where PROnumeroEmpleado = " + numeroEmpleado + ";");
        return result;
    }
    
    public Profesor getProfesorByNumEmpleadoAndUsuarioId(int numeroEmpleado, int USUid){
        Profesor result = null;
        result = executeQueryUnique("Select * from Profesor where PROnumeroEmpleado = " + numeroEmpleado + " and Usuario_USUid = " + USUid + ";");
        return result;
    }


}   
