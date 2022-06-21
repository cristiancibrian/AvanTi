package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Planestudio;
import entidades.UnidadaprendizajeImparteProfesor;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * @author Adriana
 */
public class UnidadAprendizajeImparteProfesorDAO extends AbstractDAO<Integer, UnidadaprendizajeImparteProfesor> {

    /**
     * Busca las Unidades Aprendizaje Imparte Profesor por Id de Profesor, Id de
     * Unidad de Aprendizaje y Ciclo Escolar
     *
     * @param proId Id del Profesor
     * @param uapId Id de la Unidad de Aprendizaje
     * @param ce    Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    public List<UnidadaprendizajeImparteProfesor> findByProfesorUAAndCE(int proId, int uapId, String ce) {
        String sql = "SELECT * FROM unidadaprendizaje_imparte_profesor uaip "
                + "WHERE uaip.Profesor_PROid = :proid "
                + "AND uaip.UnidadAprendizaje_UAPid = :uapid "
                + "AND uaip.Cicloescolar_CESid = :cesid "
                + "ORDER BY uaip.UIPid";

        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setInteger("proid", proId);
        query.setInteger("uapid", uapId);
        query.setString("cesid", ce);

        return (List<UnidadaprendizajeImparteProfesor>) query.list();
    }

    /**
     * Busca las unidades de aprendizaje que imparte profesor con una
     * determinado id de profesor, id de unidad de aprendizaje
     *
     * @param proId id del profesor
     * @param uapId id de la unidad de aprendizaje
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> findByProfesorUAAndCE(int proId, int uapId) {
        String sql = "SELECT * FROM unidadaprendizaje_imparte_profesor uaip "
                + "WHERE uaip.Profesor_PROid = :proid "
                + "AND uaip.UnidadAprendizaje_UAPid = :uapid  "
                + "ORDER BY uaip.UIPid";

        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setInteger("proid", proId);
        query.setInteger("uapid", uapId);

        return (List<UnidadaprendizajeImparteProfesor>) query.list();
    }

    /**
     * Metodo para obtener Unidad Aprendizaje Imparte Profesor por Plan de
     * Estudio seleccionado, Id de Unidad de Aprendizaje y Ciclo Escolar
     *
     * @param PESeleccionado Programa Educativo seleccionado
     * @param idUA           Id de Unidad de Aprendizaje
     * @param CE             Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    public List<UnidadaprendizajeImparteProfesor> consultaUIPParaUA(String PESeleccionado, String idUA, String CE) {
        List<UnidadaprendizajeImparteProfesor> resultado = null;
        resultado = this.executeQueryHql("select UIP from UnidadaprendizajeImparteProfesor UIP join UIP.unidadaprendizaje as ua join ua.areaconocimientos as AC join AC.planestudio.programaeducativo as PE join PE.planestudios as ple where PE.pedid=" + PESeleccionado + " and ua.uapid=" + idUA + " and UIP.cicloescolar.cescicloEscolar='" + CE + "'");
        return resultado;
    }

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

    public int countEsperadosAreaConocimientos(int idProgramaEducativo, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
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
        String andPeclave = "and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
        resultado = count("UnidadaprendizajeImparteProfesor a join "
                        + "a.unidadaprendizaje.areaconocimientos b",
                "b.acoid ", String.valueOf(idProgramaEducativo) + inCicloEscolar + andPeclave);
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
                "a.unidadaprendizaje.uapid ", String.valueOf(idProgramaEducativo) + andGrupos + inCicloEscolar);

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
     * Metodo que trae el numero de reportes enviados en todos los racts
     * dependiendo el ciclo escolar que recibe
     *
     * @param idCicloEscolar
     * @return
     */
    public int EnviadosPorRactGeneral(String idCicloEscolar) {
        int respuesta = 0;

        respuesta = this.executeCountQuery("select  count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in \n"
                + "(select UIPid from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid = " + idCicloEscolar + ");");

        return respuesta;

    }

    public int EsperadosPorRACTGeneral(String idCicloEscolar) {
        int respuesta = 0;
        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid = " + idCicloEscolar);
        respuesta = respuesta * 3;
        return respuesta;
    }

    public int EsperadosPorRACT(String idCicloEscolar) {
        int respuesta = 0;
        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid = " + idCicloEscolar);

        return respuesta;
    }

    public int EnviadosPorNumeroRACTGeneral(String numRACT, String idCicloEscolar) {
        int respuesta = 0;

        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where UIPid in \n"
                + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where   RACnumero= '" + numRACT + "' )\n"
                + "and CicloEscolar_CESid = " + idCicloEscolar);

        return respuesta;

    }

    public int EsperadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar) {
        // recorre la lista de planes de estudio para contemplar que dos
        // dos planes de estudio puedan convivir en un mismo ciclo escolar
        int respuesta = 0;
        // la inicializo en 0 para que no queden or de mas
        String idPlan = " '0'";
        for (Planestudio plan : planesEstudio) {
            // idPlan.concat(" or "+plan.getPesid());
            idPlan = idPlan + " or " + " PlanEstudio_PESid = '" + plan.getPESid().toString() + "'";
        }

        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
                + "(select GPOid from grupo where  PlanEstudio_PESid =" + idPlan + " ) and CicloEscolar_CESid = " + idCicloEscolar);
        respuesta = respuesta * 3;
        return respuesta;

    }

    public int EsperadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar) {
        int respuesta = 0;
        // la inicializo en 0 para que no queden or de mas
        String idPlan = " '0'";
        for (Planestudio plan : planesEstudio) {
            // idPlan.concat(" or "+plan.getPesid());
            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
        }
        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
                + "(select GPOid from grupo where  PlanEstudio_PESid =" + idPlan + " ) and CicloEscolar_CESid = " + idCicloEscolar);
        respuesta = respuesta;
        return respuesta;

    }

    public int EnviadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String numRact, String idCicloEscolar) {

        int respuesta = 0;
        // la inicializo en 0 para que no queden or de mas
        String idPlan = " '0'";
        for (Planestudio plan : planesEstudio) {
            // idPlan.concat(" or "+plan.getPesid());
            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
        }

        respuesta = this.executeCountQuery("select count(*) from unidadaprendizaje_imparte_profesor where \n"
                + " Grupo_GPOid in \n"
                + "(select GPOid from grupo where PlanEstudio_PESid = +" + idPlan + " )\n"
                + "and UIPid in \n"
                + "(select UnidadAprendizaje_imparte_profesor_UIPid from reporteavancecontenidotematico where  RACnumero= '" + numRact + " ' )\n"
                + "and CicloEscolar_CESid = " + idCicloEscolar);
        respuesta = respuesta;
        return respuesta;
    }

    public int EnviadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar) {

        int respuesta = 0;
        // la inicializo en 0 para que no queden or de mas
        String idPlan = " '0'";
        for (Planestudio plan : planesEstudio) {
            // idPlan.concat(" or "+plan.getPesid());
            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
        }

        respuesta = this.executeCountQuery("select  count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in \n"
                + "        (select UIPid from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
                + "        (select GPOid from grupo where PlanEstudio_PESid = " + idPlan + " ) and CicloEscolar_CESid = " + idCicloEscolar + " );");
        respuesta = respuesta;
        return respuesta;
    }

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
     */
    public List<UnidadaprendizajeImparteProfesor> uaipAreaProfesor(int idProfCoordinador, int idCiclo, int idProfesor) {
        List<UnidadaprendizajeImparteProfesor> lista;

        lista = this.executeQuery("select  * from unidadaprendizaje_imparte_profesor where CicloEscolar_CESid= " + idCiclo + "  and Profesor_PROid = " + idProfesor + " and Grupo_GPOid in(\n"
                + "select GPOid from grupo where PlanEstudio_PESid in (\n"
                + "select PESid from planestudio where ProgramaEducativo_PEDid in (\n"
                + "select ProgramaEducativo_PEDid from areaadministrativa where AADid in (\n"
                + "select AreaAdministrativa_AADid from coordinadorareaadministrativa where Profesor_PROid = " + idProfCoordinador + "))))\n"
                + "and UnidadAprendizaje_UAPid in  ( \n"
                + "select UnidadAprendizaje_UAPid from areaadministrativa_tiene_unidadaprendizaje where CoordinadorAdministrativa_COAid in \n"
                + "(select COAid from coordinadorareaadministrativa where Profesor_PROid = " + idProfCoordinador + "));");
        return lista;
    }

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
    
    public UnidadaprendizajeImparteProfesor getUnidadImparteProfesorByPROidAndUAPidAndGPOidAndUIPtipo(int PROid, int UAPid, int GPOid, String UIPtipo, int CESid){
        
        UnidadaprendizajeImparteProfesor result = null;        
        result = executeQueryUnique("SELECT * FROM siract.unidadaprendizaje_imparte_profesor where Profesor_PROid = " + PROid 
                                    + " and UnidadAprendizaje_UAPid = " + UAPid + " and Grupo_GPOid = " + GPOid + " and UIPtipoSubgrupo = '"  + UIPtipo  + "' and CicloEscolar_CESid = " + CESid + ";");             
        return result;
        
    }
    
    public UnidadaprendizajeImparteProfesor getUnidadImparteProfesorByUAPidAndGPOidAndUIPSubgrupo(int UAPid, int GPOid, int UIPSubgrupo, int CESid){
        UnidadaprendizajeImparteProfesor result = null;
        result = executeQueryUnique("SELECT * FROM siract.unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " + UAPid
                                    + " and Grupo_GPOid = " + GPOid + " and UIPsubgrupo = '" + UIPSubgrupo + "' and CicloEscolar_CESid = " + CESid + ";");
        return result;
    }
}
