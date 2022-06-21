package mx.avanti.siract.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author Adriana
 */
public class UnidadAprendizajeImparteProfesorDAO extends AbstractDAO<Integer, UnidadaprendizajeImparteProfesor> {

    /**
     * Busca las Unidades Aprendizaje Imparte Profesor por Id de Profesor, Id de
     * Unidad de Aprendizaje y Ciclo Escolar
     *
     * @param proId Id del Profesor
     * @param uapId Id de la Unidad de Aprendizaje
     * @param ce Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    //*** No se usa
//    public List<UnidadaprendizajeImparteProfesor> findByProfesorUAAndCE(int proId, int uapId, String ce) {
//        String sql = "SELECT * FROM unidadaprendizaje_imparte_profesor uaip "
//                + "WHERE uaip.Profesor_PROid = :proid "
//                + "AND uaip.UnidadAprendizaje_UAPid = :uapid "
//                + "AND uaip.Cicloescolar_CESid = :cesid "
//                + "ORDER BY uaip.UIPid";
//
//        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
//        query.setInteger("proid", proId);
//        query.setInteger("uapid", uapId);
//        query.setString("cesid", ce);
//
//        return (List<UnidadaprendizajeImparteProfesor>) query.list();
//    }

    /**
     * Busca las unidades de aprendizaje que imparte profesor con una
     * determinado id de profesor, id de unidad de aprendizaje
     *
     * @param proId id del profesor
     * @param uapId id de la unidad de aprendizaje
     *
     * @return
     */
    //**No se usa
//    public List<UnidadaprendizajeImparteProfesor> findByProfesorUAAndCE(int proId, int uapId) {
//        String sql = "SELECT * FROM unidadaprendizaje_imparte_profesor uaip "
//                + "WHERE uaip.Profesor_PROid = :proid "
//                + "AND uaip.UnidadAprendizaje_UAPid = :uapid  "
//                + "ORDER BY uaip.UIPid";
//
//        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
//        query.setInteger("proid", proId);
//        query.setInteger("uapid", uapId);
//
//        return (List<UnidadaprendizajeImparteProfesor>) query.list();
//    }

    /**
     * Metodo para obtener Unidad Aprendizaje Imparte Profesor por Plan de
     * Estudio seleccionado, Id de Unidad de Aprendizaje y Ciclo Escolar
     *
     * @param PESeleccionado Programa Educativo seleccionado
     * @param idUA Id de Unidad de Aprendizaje
     * @param CE Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    //***No se usa
//    public List<UnidadaprendizajeImparteProfesor> consultaUIPParaUA(String PESeleccionado, String idUA, String CE) {
//        List<UnidadaprendizajeImparteProfesor> resultado = null;
//        resultado = this.executeQueryHql("select UIP from UnidadaprendizajeImparteProfesor UIP join "
//                + "UIP.unidadaprendizaje as ua join ua.areaconocimientos as AC join AC.planestudio.programaeducativo as PE "
//                + "join PE.planestudios as ple where PE.pedid=" + PESeleccionado + " and ua.uapid=" + idUA + " and UIP.cicloescolar.cescicloEscolar='" + CE + "'");
//        return resultado;
//    }

    public int countEsperadosProgramaEducativo(int idProgramaEducativo, int noReporte, List<String> selectCicloEscolarList) {
        int resultado = 0;

        String andEstado = "";
        String andNumeroReporte = "";

        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }
            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        resultado = count("UnidadaprendizajeImparteProfesor a ",
                "a.grupo.planestudio.programaeducativo.pedid ", String.valueOf(idProgramaEducativo) + inCicloEscolar);

        return resultado;
    }

    public int countEsperadosAreaConocimientos(int areaConId, int noReporte, String PlanId, String idCicloEscolar) {
        int resultado = 0;

        String inCicloEscolar = "";

//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//        String andPeclave = "and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//        resultado = count("UnidadaprendizajeImparteProfesor a join "
//                + "a.unidadaprendizaje.areaconocimientos b", 
//                "b.acoid ", String.valueOf(idProgramaEducativo) + inCicloEscolar + andPeclave);
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
        //String andPeclave = "and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//        resultado = count(    "UnidadaprendizajeImparteProfesor a join "
//                + "a.unidadaprendizaje.areaconocimientos b",
//                "b.acoid ", String.valueOf(idProgramaEducativo) + inCicloEscolar + andPeclave);
        String queryBase = "select count(*) from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid in(select UAPid \n" +
"from unidadaprendizaje where UnidadAprendizaje_UAPid in(SELECT UnidadAprendizaje_UAPid from areaconocimiento_has_unidadaprendizaje\n" +
"where AreaConocimiento_ACOid in (SELECT ACOid from areaconocimiento where ACOid="+areaConId+" and  PlanEstudio_PESid = "+PlanId+"))) and CicloEscolar_CESid = "+idCicloEscolar+";";
        resultado = this.executeCountQuery(queryBase);
        System.out.println(resultado + "Esperados");
        return resultado;
    }

    public int countEsperadosUnidadAprendizaje(int idProgramaEducativo, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;

        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }

        String andPeclave = " and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
        resultado = count("UnidadaprendizajeImparteProfesor a",
                "a.unidadaprendizaje.uapid ", String.valueOf(idProgramaEducativo) + inCicloEscolar + andPeclave);

        return resultado;
    }

    public int countEsperadosUnidadAprendizaje(int idProgramaEducativo, int noReporte, List<String> grupos, List<String> selectCicloEscolarList) {
        int resultado = 0;
        String andGrupos = " ";
        if (grupos.size() > 0) {
            andGrupos = " AND a.grupo.gponumero IN (";
            for (String grupo : grupos) {
                andGrupos += grupo + ",";
            }
            andGrupos = andGrupos.substring(0, andGrupos.length() - 1);
            andGrupos += ")";
        }
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }

        resultado = count("UnidadaprendizajeImparteProfesor a", 
                "a.unidadaprendizaje.uapid ","'" + String.valueOf(idProgramaEducativo) + "'" + andGrupos + inCicloEscolar);

        return resultado;
    }

    public int countEsperadosProfesor(int idProfesor, int noReporte, String extra, List<String> selectCicloEscolarList) {
        int resultado = 0;
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        resultado = count("UnidadaprendizajeImparteProfesor a",
                "a.profesor.proid",
                "'" + String.valueOf(idProfesor) + "'" + extra + inCicloEscolar);

        return resultado;
    }

    public int countEsperadosAreaAdmin(int idAreaAdmin, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;

        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }

        String andPeclave = " and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";

        resultado = count("UnidadaprendizajeImparteProfesor a join "
                + "a.unidadaprendizaje.coordinadorareaadministrativas b",
                "b.areaadministrativa.aadid ", String.valueOf(idAreaAdmin) + inCicloEscolar + andPeclave);
        return resultado;
    }

    public int countEsperadosProfesor(Integer idProfesor, int noReporte, List<String> selectGrupo, List<String> selectUnidadAprendizaje, List<String> selectCicloEscolarList) {

        int resultado = 0;
        String inCicloEscolar = "";
        String extraQuery = "";
        String clavesGrupo = "";
        String clavesUA = "";

        for (String claveGrupo : selectGrupo) {
            if (clavesGrupo.isEmpty()) {
                clavesGrupo += claveGrupo;
            } else {
                clavesGrupo += "," + claveGrupo;
            }
        }

        for (String claveUA : selectUnidadAprendizaje) {
            if (clavesUA.isEmpty()) {
                clavesUA += claveUA;
            } else {
                clavesUA += "," + claveUA;
            }
        }
        if (!clavesGrupo.isEmpty()) {
            extraQuery += " AND  a.grupo.gponumero IN (" + clavesGrupo + ")";
        }
        if (!clavesUA.isEmpty()) {
            extraQuery += " AND  a.unidadaprendizaje.uapclave IN (" + clavesUA + ")";
        }

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        resultado = count("UnidadaprendizajeImparteProfesor a",
                "a.profesor.proid",
                "'" + String.valueOf(idProfesor) + "'" + extraQuery + inCicloEscolar);

        return resultado;
    }

    // PARA SEMAFOROS
    /**
     * Con este metodo se obtienen tanto entregados como esperados de todas las
     * tablas en semaforos.
     *
     * @param tipoReporte El tipo de reporte que se selecciono
     * @param cicloEscolar El ciclo escolar seleccionado
     * @param numRACT El numero de RACT
     * @param planesEstudio Los planes de estudio de un programa educativo
     * @return
     */
    public int obtenerEnviadosOEsperados(String tipoReporte, String cicloEscolar, String numRACT, List<Planestudio> planesEstudio) {
        String planesE = "";
        int contPlanE = 0;

        if (planesEstudio != null) {
            contPlanE = planesEstudio.size() + 1;
            planesE = planesEstudio.get(0).getPESid().toString();

            for (int i = 1; i < planesEstudio.size(); i++) {
                planesE += "," + planesEstudio.get(i).getPESid();
            }
        }

        return executeCountQuery("CALL obtenerEnviadosOEsperados('" + tipoReporte + "','" + cicloEscolar + "','" + numRACT + "','" + planesE + "','" + contPlanE  + "')");
    }


    /**
     * Metodo que trae el numero de reportes enviados en todos los racts
     * dependiendo el ciclo escolar que recibe
     *
     * @param idCicloEscolar
     * @return
     */
    //*** No se usa
//    public int EnviadosPorRactGeneral(String idCicloEscolar) {
//        int respuesta = 0;
// 
//        respuesta = this.executeCountQuery("select  count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in \n"
//                + "(select UIPid from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid = " + idCicloEscolar + ");");
//
//        return respuesta;
//
//    }

    //*** No se usa
//    public int EsperadosPorRACTGeneral(String idCicloEscolar) {
//        int respuesta = 0;
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid = " + idCicloEscolar);
//        respuesta = respuesta * 3;
//        return respuesta;
//    }

    //*** No se usa
//    public int EsperadosPorRACT(String idCicloEscolar) {
//        int respuesta = 0;
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid = " + idCicloEscolar);
//
//        return respuesta;
//    }

    //*** No se usa
//    public int EnviadosPorNumeroRACTGeneral(String numRACT, String idCicloEscolar) {
//        int respuesta = 0;
//
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where UIPid in \n"
//                + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where   RACnumero= '" + numRACT + "' )\n"
//                + "and CicloEscolar_CESid = " + idCicloEscolar);
//
//        return respuesta;
//
//    }
   //Pendiente
//    public int EsperadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar) {
//        // recorre la lista de planes de estudio para contemplar que dos
//        // dos planes de estudio puedan convivir en un mismo ciclo escolar
//        int respuesta = 0;
//        // la inicializo en 0 para que no queden or de mas
//        String idPlan = " '0'";
//        for (Planestudio plan : planesEstudio) {
//            // idPlan.concat(" or "+plan.getPesid());
//            idPlan = idPlan + " or " + " PlanEstudio_PESid = '" + plan.getPESid().toString() + "'";
//        }
//
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
//                + "(select GPOid from grupo where  PlanEstudio_PESid =" + idPlan + " ) and CicloEscolar_CESid = " + idCicloEscolar);
//        respuesta = respuesta * 3;
//        return respuesta;
//
//    }
// Procedimiento
//    public int EsperadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar) {
//        int respuesta = 0;
//        // la inicializo en 0 para que no queden or de mas
//        String idPlan = " '0'";
//        for (Planestudio plan : planesEstudio) {
//            // idPlan.concat(" or "+plan.getPesid());
//            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
//        }
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
//                + "(select GPOid from grupo where  PlanEstudio_PESid =" + idPlan + " ) and CicloEscolar_CESid = " + idCicloEscolar);
//        return respuesta;
//
//    }
    
    /**
     * Metodo para encontrar el numero de reportes esperados por Unidad de 
     * Aprendizaje
     * @param planesEstudio
     * @param idCicloEscolar
     * @param grupo
     * @return 
     */
    //*** No se usa
//    public int EsperadosPorUnidadAprendizajeRACT(String planesEstudio, String idCicloEscolar, String grupo) {
//        int respuesta = 0;
//        // la inicializo en 0 para que no queden or de mas
//
//        respuesta = this.executeCountQuery( "select count(*) from unidadaprendizaje_imparte_profesor "
//                     + "where Grupo_GPOid in(select GPOid from grupo where GPOnumero =" + grupo +  " and PlanEstudio_PESid =" + planesEstudio + ") "
//                     + "and CicloEscolar_CESid = "+ idCicloEscolar +" ");
//        return respuesta;
//    }
    //*** No se usa
//    public int EsperadosPorUnidadAprendizajeNumeroRACTGeneral(String planesEstudio, String numRACT, String idCicloEscolar, String grupo) {
//        int respuesta = 0;
//
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor "
//                     + "where Grupo_GPOid in(select GPOid from grupo where GPOnumero =" + grupo +  " and PlanEstudio_PESid =" + planesEstudio + ") "
//                     + "and CicloEscolar_CESid = "+ idCicloEscolar +" and UIPid in(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero= "+ numRACT +" )");
//
//        return respuesta;
//
//    }
    //Procedimiento
//    public int EnviadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String numRact, String idCicloEscolar) {
//
//        int respuesta = 0;
//        // la inicializo en 0 para que no queden or de mas
//        String idPlan = " '0'";
//        for (Planestudio plan : planesEstudio) {
//            // idPlan.concat(" or "+plan.getPesid());
//            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
//        }
//
//         respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where \n"
//                + " Grupo_GPOid in \n"
//                + "(select GPOid from grupo where PlanEstudio_PESid = +" + idPlan + " )\n"
//                + "and UIPid in \n"
//                + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where  RACnumero= '" + numRact + " ' )\n"
//                + "and CicloEscolar_CESid = " + idCicloEscolar);
//        respuesta = respuesta;
//        return respuesta;
//    }
//Procedimiento
//    public int EnviadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar) {
//
//        int respuesta = 0;
//        // la inicializo en 0 para que no queden or de mas
//        String idPlan = " '0'";
//        for (Planestudio plan : planesEstudio) {
//            // idPlan.concat(" or "+plan.getPesid());
//            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
//        }
//
//        respuesta = this.executeCountQuery("select  count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in \n"
//                + "        (select UIPid from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
//                + "        (select GPOid from grupo where PlanEstudio_PESid = " + idPlan + " ) and CicloEscolar_CESid = " + idCicloEscolar + " );");
//        respuesta = respuesta;
//        return respuesta;
//    }
    
    
    
    //query para RACT consultas
    /**
     * trae las uaip correspondientes al area administrativa del id del profsor
     * recibido
     *
     * @param idProfCoordinador
     * @param idCiclo
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorcoordinadorAreaYCicloEscolar(int idProfCoordinador, int idCiclo) {
        List<UnidadaprendizajeImparteProfesor> lista;

        lista = this.executeQuery("select  * from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + idCiclo + "  and Grupo_GPOid in(\n"
                + "select GPOid from grupo where PlanEstudio_PESid in (\n"
                + "select PESid from planestudio where ProgramaEducativo_PEDid in (\n"
                + "select ProgramaEducativo_PEDid from areaadministrativa where AADid in (\n"
                + "select AreaAdministrativa_AADid from coordinadorareaadministrativa where Profesor_PROid = " + idProfCoordinador + " ))))\n"
                + "and UnidadAprendizaje_UAPid in  ( \n"
                + "select UnidadAprendizaje_UAPid from areaadministrativa_tiene_unidadaprendizaje where CoordinadorAdministrativa_COAid in \n"
                + "(select COAid from coordinadorareaadministrativa where Profesor_PROid = " + idProfCoordinador + " ));");
        return lista;
    }

    /**
     * Trae las uiap del area administrativa del profesor que es coordinados y
     * aparte que pertenescan al profesor ( idProfesor)
     *
     * @param idProfCoordinador
     * @param idCiclo
     * @param idProfesor
     * @return
     *///Procedimiento
//    public List<UnidadaprendizajeImparteProfesor> uaipAreaProfesor(int idProfCoordinador, int idCiclo, int idProfesor) {
//        List<UnidadaprendizajeImparteProfesor> lista;
//
//        lista = this.executeQuery("select  * from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + idCiclo + "  and Profesor_PROid = " + idProfesor + " and Grupo_GPOid in(\n"
//                + "select GPOid from grupo where PlanEstudio_PESid in (\n"
//                + "select PESid from planestudio where ProgramaEducativo_PEDid in (\n"
//                + "select ProgramaEducativo_PEDid from areaadministrativa where AADid in (\n"
//                + "select AreaAdministrativa_AADid from coordinadorareaadministrativa where Profesor_PROid = " + idProfCoordinador + "))))\n"
//                + "and UnidadAprendizaje_UAPid in  ( \n"
//                + "select UnidadAprendizaje_UAPid from areaadministrativa_tiene_unidadaprendizaje where CoordinadorAdministrativa_COAid in \n"
//                + "(select COAid from coordinadorareaadministrativa where Profesor_PROid = " + idProfCoordinador + "));");
//        return lista;
//    }
    
    /**
     * Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorPEyCE(int idCiclo, int idPE) {
        List<UnidadaprendizajeImparteProfesor> lista;

        lista = this.executeQuery("select  * from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + idCiclo + "  and Grupo_GPOid in(\n"
                + "select GPOid from grupo where PlanEstudio_PESid in (\n"
                + "select PESid from planestudio where ProgramaEducativo_PEDid = " + idPE + " ));");
        return lista;
    }

    /**
     * Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     * que pertenezcan al profesor recibido
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorProfesorPEyCE(int idCiclo, int idPE, int idProfesor) {
        List<UnidadaprendizajeImparteProfesor> lista;

        if (idPE == 37) {
            lista = this.executeQuery("select  * from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + idCiclo + "  and Profesor_PROid= " + idProfesor + " and UnidadAprendizaje_UAPid in (\n"
                    + "select UnidadAprendizaje_UAPid  from areaconocimiento_has_unidadaprendizaje where AreaConocimiento_ACOid in (\n"
                    + "select ACOid from areaconocimiento where PlanEstudio_PESid in (\n"
                    + "select PESid from planestudio where ProgramaEducativo_PEDid =37)));");
        } else {
            lista = this.executeQuery("select  * from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + idCiclo + "  and "
                    + "Profesor_PROid = " + idProfesor + " and Grupo_GPOid in(\n"
                    + "select GPOid from grupo where PlanEstudio_PESid in (\n"
                    + "select PESid from planestudio where ProgramaEducativo_PEDid = " + idPE + " ));");
        }
        return lista;
    }

    // **** querys para generar de reportes 2019
    //***No se usa
//    public int EsperadosPorAreaConocimientoRACT(String idCicloEscolar, int idAreaCon) {
//     
//        int respuesta = 0;
//        int x=0;
//
//        // ****Cambie el query para utilizar joins ademas para area de conocimiento no se ocupa el plan de estudios 
//        //Eduardo Cardona 
//        
//        //        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid in(select UAPid \n" +
//        //"from unidadaprendizaje where UnidadAprendizaje_UAPid in(SELECT UnidadAprendizaje_UAPid from areaconocimiento_has_unidadaprendizaje\n" +
//        //"where AreaConocimiento_ACOid in (SELECT ACOid from areaconocimiento where ACOid="+idAreaCon+" and "+idPlan+"))) and CicloEscolar_CESid = "+idCicloEscolar+";");
//        //        System.out.println(respuesta + "¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿");
//
//        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor as uaip inner join areaconocimiento_has_unidadaprendizaje as au on\n" +
//                                            " uaip.UnidadAprendizaje_UAPid= au.UnidadAprendizaje_UAPid and au.AreaConocimiento_ACOid= "+ idAreaCon+ " and uaip.CicloEscolar_CESid= " + idCicloEscolar);
//        return respuesta;
//
//    }

    
    /**
     * Metodo para traer las uaip de un area de conocimiento, es asi porque 
     * asi se divide el reporte por programa educativo
     * 
     * @param criterio entregados, no entregados.. etc
     * @param idProgramaEducativo
     * @param idCicloEscolar
     * @param idAreaConocimiento
     * @param noRact numero de ract
     * @param idPlan id de plan de estudios
     * @return 
     */
//    public List<UnidadaprendizajeImparteProfesor> uaipsPorAreaConocimiento(String criterio, int idProgramaEducativo, int idCicloEscolar, int idAreaConocimiento, String noRact, int idPlan) {
//        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
//
//        String queryBase;
//        // para tronco comun es diferente porque se ocupan tambien los uaips que no
//        // tienen grupo de tronco pero que si pertencen al tronco como calculo
//        if (idProgramaEducativo == 37) {
//            //if(idPlan==20){
//                queryBase = "select * from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid in (\n"
//                    + "select UnidadAprendizaje_UAPid from areaconocimiento_has_unidadaprendizaje  where AreaConocimiento_ACOid= " + idAreaConocimiento + " ) and\n"
//                    + "CicloEscolar_CESid = " + idCicloEscolar + " and Grupo_GPOid in (\n"
//                    + "select GPOid from grupo where PlanEstudio_PESid=" + idPlan + " )";
//            //}else{
////                queryBase = "select * from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid in (\n"
////                    + "select UnidadAprendizaje_UAPid from areaconocimiento_has_unidadaprendizaje  where AreaConocimiento_ACOid= " + idAreaConocimiento + " ) and\n"
////                    + "CicloEscolar_CESid = " + idCicloEscolar + ";";
////            }
//        } else {
//            queryBase = "select * from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid in (\n"
//                    + "select UnidadAprendizaje_UAPid from areaconocimiento_has_unidadaprendizaje  where AreaConocimiento_ACOid= " + idAreaConocimiento + " ) and\n"
//                    + "CicloEscolar_CESid = "+ idCicloEscolar +" and Grupo_GPOid in (\n"
//                    + "select GPOid from grupo where PlanEstudio_PESid=" + idPlan + " )";
//
//        }
//        // si no es todos los racts agrega el critrio al query
//        if (!noRact.equalsIgnoreCase("4")) {
//            uaips=agregarCriterioQuery(queryBase, criterio, noRact);
//        }else{
//            switch(criterio){
//                case "Porcentaje de Avance Global Completo":
//                // agrego validacion de porcenteaje completo al query base
//                    queryBase += " and UIPid in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
//                break;
//
//                case "Porcentaje de Avance Global Incompleto":
//                // agrego validacion de porcenteaje incompleto al query base
//                    queryBase += " and UIPid not in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
//                break;
//                
//                case "Porcentaje de Avance Global Completo e Incompleto":
//                    
//                break;
//            }
//            uaips = this.executeQuery(queryBase);
//        }
//        
//        return uaips;
//    }
    
    /**
     * Metodo para saber cuantos registros de uaip tiene un area de conocimiento en un ciclo escolar;
     * @param idPlan
     * @param idCicloEscolar
     * @return 
     */
//    public int countUaipPorAreaConocimientoYcicloEscolar(int idPlan, int idCicloEscolar){
//       return this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where  Grupo_GPOid in\n"
//                        + "(select GPOid from grupo where PlanEstudio_PESid = " + idPlan + " ) and CicloEscolar_CESid= " +idCicloEscolar +" ;");
//    }
    
    /**
     * Metodo para saber cuantos registros de uaip tiene una unidad de aprendizaje en un ciclo escolar
     * @param idPlan plan de estudios
     * @param idCicloEscolar cilo escolar
     * @param idACO area de conocimiento
     * @param idUAP unidad de aprendizaje
     * @param idGrupo grupo
     * @return 
     */
    public int countUaiPorUnidadAprendizaje(int idPlan, int idCicloEscolar,int idACO, int idUAP, int idGrupo){
        return this.executeCountQuery("SELECT COUNT(*) FROM unidadaprendizaje_imparte_profesor WHERE  Grupo_GPOid IN("
                + "SELECT GPOid FROM grupo WHERE GPOnumero= "+ idGrupo+ " AND PlanEstudio_PESid = "+ idPlan +") AND UnidadAprendizaje_UAPid = "+idUAP +";");       
    }
    
    //----------------- Eduardo 08/2019 ---------//
    
    /***
     * 
     * @param idPrograma 
     * @param idArea
     * @param idCiclo
     * @return conteo de esperados por Area de cocimiento segun el ciclo escolar
     */
    public int esperadosPorAreaConocimiento(int idPrograma,int idArea,int idCiclo){
        // si es tronco comun debera filtrar las materias que no tienen grupo
        // tronco comun
        if(idPrograma==37){
        return this.executeCountQuery("select count(*) from  unidadaprendizaje_imparte_profesor as uaip join areaconocimiento_has_unidadaprendizaje as ahu "
                                      + " on ahu.UnidadAprendizaje_UAPid = uaip.UnidadAprendizaje_UAPid and \n" +
                                        "ahu.AreaConocimiento_ACOid = " +idArea+" and uaip.CicloEscolar_CESid=" + idCiclo);
        }else{
            String query ="select count(*) from  unidadaprendizaje_imparte_profesor as uaip join areaconocimiento_has_unidadaprendizaje as ahu "
                                      + " on ahu.UnidadAprendizaje_UAPid = uaip.UnidadAprendizaje_UAPid and \n" +
                                        "ahu.AreaConocimiento_ACOid = " +idArea+" and uaip.Grupo_GPOid in (select GPOid from grupo where PlanEstudio_PESid in (\n" +
                                        "select planestudio.PESid from  programaeducativo as pe join planestudio on"
                                      + " pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid = "+idPrograma + " )) and uaip.CicloEscolar_CESid=" + idCiclo;
            return this.executeCountQuery(query);
        }
        
    }
    
    /**
     * Metodo para traer los RACT esperados por un area administrativa en cada ract individual
     * en caso de que se quiera saber por los 3 racts
     * debera multiplicarse el resultado x3
     * @param idCicloEscolar
     * @param idAreaCon
     * @param  idPrograma 
     * @return 
     */
    public int esperadosPorAreaAdministrativa(int idPrograma,int idArea,int idCiclo) {
     
        // si es tronco comun debera filtrar las materias que no tienen grupo
        // tronco comun
        if(idPrograma==37){
        return this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor as uaip join areaadministrativa_tiene_unidadaprendizaje as aa "
                    + "join coordinadorareaadministrativa as caa \n" +
                "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.AreaAdministrativa_AADid= " +idArea
                + " and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid  and uaip.CicloEscolar_CESid=" + idCiclo);
        }else{
            return this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor as uaip join areaadministrativa_tiene_unidadaprendizaje as aa "
                    + "join coordinadorareaadministrativa as caa \n" +
                "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.AreaAdministrativa_AADid= " +idArea
                + " and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid and uaip.Grupo_GPOid in \n" +
                "(select GPOid from grupo where PlanEstudio_PESid in (\n" +
                "select planestudio.PESid from  programaeducativo as pe join planestudio on"
                    + " pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid = "+idPrograma+" )) and uaip.CicloEscolar_CESid=" + idCiclo);
        }

    }
    /***
     * Metodo para traer esperados por unidad de aprendizaje segun el ciclo escolar
     * @param idUnidad
     * @param idPrograma
     * @param idCiclo
     * @return 
     */
    public int esperadosPorUnidadAprendizaje(int idUnidad, int idPrograma, int idCiclo){
        // si es tronco comun debera filtrar las materias que no tienen grupo
        // tronco comun
        if(idPrograma==37){
        return this.executeCountQuery("select count(*)  from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " +idUnidad+"  and CicloEscolar_CESid ="+idCiclo );
        }else{
            return this.executeCountQuery("select count(*)  from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " +idUnidad+" and Grupo_GPOid in (select GPOid from grupo "
                    + "where PlanEstudio_PESid in (\n" +
                    "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid =" +idPrograma+" )) "
                    + "and CicloEscolar_CESid =" + idCiclo);
        }
    }
    /***
     * Metodo para traer conteo de esperados por unidad profesor dependiendo
     * del ciclo escolar y si el usuario que consulta es Admin u otro usuario
     * @param idProfesor
     * @param idCiclo
     * @param IdPrograma
     * @param isAdmin
     * @return 
     */
    public int esperadosPorProfesor(int idProfesor, int idCiclo, int IdPrograma, boolean isAdmin){
        // separo si es Admin para que muestre todos sus grupos de casos contrario
        // solo regresa los grupos que pertecen al PE del usuario que genera
        // el reporte 
        if(isAdmin){
            return this.executeCountQuery(" select count(*)  from unidadaprendizaje_imparte_profesor where Profesor_PROid = "+idProfesor+ " and CicloEscolar_CESid = "+idCiclo);
        }else{
            return this.executeCountQuery("select count(*)  from unidadaprendizaje_imparte_profesor where Profesor_PROid = " +idProfesor+" and  Grupo_GPOid in"
                    + " (select GPOid from grupo where PlanEstudio_PESid in \n" +
                    "(select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid "
                     + "and pe.PEDid ="+IdPrograma+" )) and CicloEscolar_CESid =" + idCiclo);
                    
        }      
    }
    /**
     * Trae las unidadAprendizajeImparteProfesor segun el criterio de busqueda
     * @param criterio
     * @param idPrograma
     * @param idArea
     * @param idCiclo
     * @param noRact
     * @param idPlan
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorAreaAdministrativa(String criterio, int idPrograma,int idArea,int idCiclo, String noRact) {
        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();

        String queryBase;
         // si es tronco comun debera filtrar las materias que no tienen grupo
        // tronco comun
        if(idPrograma==37){
        queryBase=("select * from unidadaprendizaje_imparte_profesor as uaip join areaadministrativa_tiene_unidadaprendizaje as aa "
                    + "join coordinadorareaadministrativa as caa \n" +
                "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.AreaAdministrativa_AADid= " +idArea
                + " and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid  and uaip.CicloEscolar_CESid=" + idCiclo);
        }else{
            queryBase=("select * from unidadaprendizaje_imparte_profesor as uaip join areaadministrativa_tiene_unidadaprendizaje as aa "
                    + "join coordinadorareaadministrativa as caa \n" +
                "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.AreaAdministrativa_AADid= " +idArea
                + " and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid and uaip.Grupo_GPOid in \n" +
                "(select GPOid from grupo where PlanEstudio_PESid in (\n" +
                "select planestudio.PESid from  programaeducativo as pe join planestudio on"
                    + " pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid = "+idPrograma+" )) and uaip.CicloEscolar_CESid=" + idCiclo);
        }
        // si no es todos los racts agrega el critrio al query
        if (!noRact.equalsIgnoreCase("4")) {
            uaips=agregarCriterioQuery(queryBase, criterio, noRact);
        }else{
            switch(criterio){
                case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                    queryBase += " and UIPid in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;

                case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                    queryBase += " and UIPid not in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;
                
                case "Porcentaje de Avance Global Completo e Incompleto":
                    
                break;
            }
            uaips = this.executeQuery(queryBase);
        }
        
        return uaips;
    }
    /**
     * Traera la lista de uaips de aprendizaje que corresponden a una unidad
     * de aprendizaje segun el criterio recibido
     * @param criterio
     * @param idPrograma programa seleccionado
     * @param idUnidad unidad seleccionado
     * @param idCiclo ciclo escolar seleccionado 
     * @param noRact numero de ract 
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorUnidadAprendizaje(String criterio, int idPrograma,int idUnidad,int idCiclo, String noRact) {
        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();

        String queryBase;
         // si es tronco comun debera filtrar las materias que no tienen grupo
        // tronco comun
        if(idPrograma==37){
            queryBase=("select *  from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " +idUnidad+"  and CicloEscolar_CESid ="+idCiclo );
        }else{
            queryBase=("select *  from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " +idUnidad+" and Grupo_GPOid in (select GPOid from grupo "
                    + "where PlanEstudio_PESid in (\n" +
                    "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid =" +idPrograma+" )) "
                    + "and CicloEscolar_CESid =" + idCiclo);
        }
        // si no es todos los racts agrega el critrio al query
        if (!noRact.equalsIgnoreCase("4")) {
            uaips=agregarCriterioQuery(queryBase, criterio, noRact);
        }else{
            switch(criterio){
                case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                    queryBase += " and UIPid in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;

                case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                    queryBase += " and UIPid not in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;
                
                case "Porcentaje de Avance Global Completo e Incompleto":
                    
                break;
            }
            uaips = this.executeQuery(queryBase);
        }
        
        return uaips;
    }
    /**
     * Traera la lista de uaips que corresponde a un profesor y al criterio 
     * recibido
     * @param criterio
     * @param idPrograma
     * @param idProfesor
     * @param idCiclo
     * @param noRact
     * @param isAdmin
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorProfesor(String criterio, int idPrograma,int idProfesor,int idCiclo, String noRact,boolean isAdmin) {
        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();

        String queryBase;
         // si es tronco comun debera filtrar las materias que no tienen grupo
        // tronco comun
        if(isAdmin){
            queryBase=(" select *  from unidadaprendizaje_imparte_profesor where Profesor_PROid = "+idProfesor+ " and CicloEscolar_CESid = "+idCiclo);
        }else{
            queryBase=("select *  from unidadaprendizaje_imparte_profesor where Profesor_PROid = " +idProfesor+" and  Grupo_GPOid in"
                    + " (select GPOid from grupo where PlanEstudio_PESid in \n" +
                    "(select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid "
                     + "and pe.PEDid ="+idPrograma+" )) and CicloEscolar_CESid =" + idCiclo);
        }
        // si no es todos los racts agrega el critrio al query
        if (!noRact.equalsIgnoreCase("4")) {
            uaips=agregarCriterioQuery(queryBase, criterio, noRact);
        }else{
            switch(criterio){
                case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                    queryBase += " and UIPid in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;

                case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                    queryBase += " and UIPid not in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;
                
                case "Porcentaje de Avance Global Completo e Incompleto":
                    
                break;
            }
            uaips = this.executeQuery(queryBase);
        }
        
        return uaips;
    }
    /**
     * Traera la lista de uaips que corresponden a un coordinador de area
     * segun el criterio recibido
     * @param criterio
     * @param idPrograma
     * @param idProfesor
     * @param idCiclo
     * @param noRact
     * @param idProfesorCoordinador
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> uaipsPorProfesorCoordinador(String criterio, int idPrograma,int idProfesor,int idCiclo, String noRact,int idProfesorCoordinador) {
        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();

        String queryBase;

         queryBase=("select * from unidadaprendizaje_imparte_profesor as uaip join areaadministrativa_tiene_unidadaprendizaje"
                + " as aa join coordinadorareaadministrativa as caa \n" +
                "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.Profesor_PROid=" +idProfesorCoordinador+" and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid"
                + " and uaip.Grupo_GPOid in \n" +
                "(select GPOid from grupo where PlanEstudio_PESid in (\n" +
                "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = "
                                + "planestudio.ProgramaEducativo_PEDid and pe.PEDid ="+ idPrograma+ " ))\n" +
                " and uaip.Profesor_PROid = " +idProfesor+" and uaip.CicloEscolar_CESid =" + idCiclo);
        // si no es todos los racts agrega el critrio al query
        if (!noRact.equalsIgnoreCase("4")) {
            uaips=agregarCriterioQuery(queryBase, criterio, noRact);
        }else{
            switch(criterio){
                case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                    queryBase += " and UIPid in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;

                case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                    queryBase += " and UIPid not in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;
                
                case "Porcentaje de Avance Global Completo e Incompleto":
                    
                break;
            }
            uaips = this.executeQuery(queryBase);
        }
        
        return uaips;
    }
    
    public List<UnidadaprendizajeImparteProfesor> uaipsPorPlanEstudio(String criterio, int idPlan,int idCiclo, String noRact) {
        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();

        String queryBase;

         queryBase=("select * from  unidadaprendizaje_imparte_profesor where Grupo_GPOid in "
                 + "( select GPOid from grupo where PlanEstudio_PESid = "+idPlan+" )\n" +
                    " and CicloEscolar_CESid="+idCiclo);
        // si no es todos los racts agrega el critrio al query
        if (!noRact.equalsIgnoreCase("4")) {
            uaips=agregarCriterioQuery(queryBase, criterio, noRact);
        }else{
            switch(criterio){
                case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase += " and UIPid in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;

            case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                queryBase += " and UIPid not in (select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = 4 and RACavanceGlobal = 100 );";
                break;
            }
            uaips = this.executeQuery(queryBase);
        }
        
        return uaips;
    }
    
    /**
     * Esperados por profesor segun el area que coordina el usuario
     * @param idProfesor
     * @param idCiclo
     * @param IdPrograma
     * @param idProfesorCoordinador
     * @return 
     */
    public int esperadosPorProfesorCoordinador(int idProfesor, int idCiclo, int IdPrograma, int idProfesorCoordinador){
        return this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor as uaip join areaadministrativa_tiene_unidadaprendizaje"
                + " as aa join coordinadorareaadministrativa as caa \n" +
                "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.Profesor_PROid=" +idProfesorCoordinador+" and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid"
                + " and uaip.Grupo_GPOid in \n" +
                "(select GPOid from grupo where PlanEstudio_PESid in (\n" +
                "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = "
                                + "planestudio.ProgramaEducativo_PEDid and pe.PEDid ="+ IdPrograma+ " ))\n" +
                " and uaip.Profesor_PROid = " +idProfesor+" and uaip.CicloEscolar_CESid =" + idCiclo);
    }

    /**
     * Metodo generico para agregar los criterios a cualquier 
     * query que regresa la lista de unidades de aprendizaje
     * @param queryBase
     * @param criterio
     * @param noRact
     * @return 
     */
    public List<UnidadaprendizajeImparteProfesor> agregarCriterioQuery(String queryBase, String criterio, String noRact){
        List<UnidadaprendizajeImparteProfesor> uaips = new ArrayList();
        switch (criterio) {
                case "entregados":
                    
                        queryBase = queryBase + " and UIPid in \n"
                                + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = '" + noRact + "' );";
                    
                    uaips = this.executeQuery(queryBase);
                    break;

                case "noentregados":

                    queryBase = queryBase + " and UIPid not in \n"
                            + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = '" + noRact + "' );";

                    uaips = this.executeQuery(queryBase);

                    break;
                case "entregadosynoentregados":
                    uaips = this.executeQuery(queryBase);
                    break;

                case "Porcentaje de Avance Global Completo":
                    queryBase = queryBase + " and UIPid in \n"
                            + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = '" + noRact + "' and RACavanceGlobal = 100 );";
                    uaips = this.executeQuery(queryBase);
                    break;

                case "Porcentaje de Avance Global Incompleto":
                    queryBase = queryBase + " and UIPid not in \n"
                            + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = '" + noRact + "' and RACavanceGlobal = 100 );";
                    uaips = this.executeQuery(queryBase);
                    break;

                case "Porcentaje de Avance Global Completo e Incompleto":
//                    queryBase = queryBase + " and UIPid in \n"
//                            + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where RACnumero = '" + noRact + "' and  ( RACavanceGlobal != 100 or RACavanceGlobal = 100) );";
                    uaips = this.executeQuery(queryBase);
                    break;
            }
        return uaips;
    }
    
      public List<UnidadaprendizajeImparteProfesor>  executeProcedureText(String procedure){
        System.out.println("ExecuteProcedure ----------");
        List<UnidadaprendizajeImparteProfesor> result = null;
        try{
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        Query Query = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(UnidadaprendizajeImparteProfesor.class);
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
      
      public List<BigInteger>  executeProcedureTextCount(String procedure){
        System.out.println("ExecuteProcedure ----------" + procedure);
        List<BigInteger> result;
        try{
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        Query Query = HibernateUtil.getSession().createSQLQuery(procedure);
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
