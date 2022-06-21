 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import mx.avanti.siract.custom.entity.ReporteAux;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Reporte;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author Martin
 */
public class ReporteAvanceContenidoTematicoDAO extends AbstractDAO<Integer, Reporteavancecontenidotematico> {

    private Session session;
    Criteria criteria;

    public void initSession() {
        setSession(HibernateUtil.getSessionFactory().openSession());

    }

    public List<Reporteavancecontenidotematico> buscarReporteAvanceContenidoTematico(int id_profesor, int gpo_numero, int uap_clave, int subgrupo, String tipo_subgrupo, String fecha) {

        return this.executeQueryHql("select distinct a from Reporteavancecontenidotematico a "
                + "join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where "
                + "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND "
                + "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + gpo_numero + " AND "
                + "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + uap_clave + " AND "
                + "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subgrupo + " AND "
                + "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo_subgrupo + "' AND "
                + "conf.confechaInicioSemestre='" + fecha + "' order by racnumero desc");
    }

    /**
     * Metodo para encontrar un RACTs por numero y por Unidad de aprendizaje
     * imparte profesor
     *
     * @param id_unidadAprendizaje
     * @param numero de ract
     * @param proId id de profesor
     * @param gpoNum numer de grupo
     * @param tipo tipo C, T, L....
     * @param ce Ciclo Escolar
     * @param uaip Unidad de Apredizaje Imparte Profesor
     * @return Racts encontrado
     */
    public List<Reporteavancecontenidotematico> findRACTByNumberAndUAIP(int id_unidadAprendizaje, String numero, int proId, int gpoNum, String tipo, String ce, int uaip) {

        String queryString = "SELECT ract.* FROM reporteavancecontenidotematico ract "
                + "INNER JOIN unidadaprendizaje_imparte_profesor uaip "
                + "ON ract.UnidadAprendizaje_imparte_profesor_UIPid = uaip.UIPid "
                + "INNER JOIN unidadaprendizaje ua "
                + "ON uaip.UnidadAprendizaje_UAPid = ua.UAPid "
                + "INNER JOIN grupo g "
                + "ON uaip.Grupo_GPOid = g.GPOid "
                + "WHERE g.GPOnumero = :gponum "
                + "AND uaip.Profesor_PROid = :proid "
                + "AND uaip.UIPtipoSubgrupo = :tipo "
                + "AND ract.RACnumero = :ractnum "
                + "AND uaip.CicloEscolar_CESid = :cesid "
                + "AND ua.UAPClave = :uapclave ";

        Session session = HibernateUtil.getSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.addEntity(Reporteavancecontenidotematico.class);
        query.setInteger("gponum", gpoNum);
        query.setInteger("proid", proId);
        query.setString("tipo", tipo);
        query.setString("ractnum", numero);
        query.setString("cesid", ce);
        query.setInteger("uapclave", id_unidadAprendizaje);

        List<Reporteavancecontenidotematico> ract = (List<Reporteavancecontenidotematico>) query.list();
        return ract;
    }

    public List<Reporteavancecontenidotematico> buscarReporteavancecontenidotematicoNumero(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo, String fechaLimite, String ce, int idUnidadSeleccionada) {
        System.out.println("select distinct * from reporteavancecontenidotematico re inner join unidadaprendizaje_imparte_profesor u inner join unidadaprendizaje ua inner join grupo g where u.Profesor_PROid=" + id_profesor + " and u.CicloEscolar_CESid=" + ce + "  and re.RACnumero='" + numero + "'" + " and re.UnidadAprendizaje_imparte_profesor_UIPid=u.UIPid and u.UIPtipoSubgrupo='" + tipo + "'" + " and g.GPOnumero=" + id_grupo + " and ua.UAPclave=" + id_unidadAprendizaje + " and UnidadAprendizaje_imparte_profesor_UIPid=" + idUnidadSeleccionada);
        List<Reporteavancecontenidotematico> racId = this.executeQuery("select distinct * from reporteavancecontenidotematico re inner join unidadaprendizaje_imparte_profesor u inner join unidadaprendizaje ua inner join grupo g where u.Profesor_PROid=" + id_profesor + " and u.CicloEscolar_CESid=" + ce + "  and re.RACnumero='" + numero + "'" + " and re.UnidadAprendizaje_imparte_profesor_UIPid=u.UIPid and u.UIPtipoSubgrupo='" + tipo + "'" + "and u.UIPsubgrupo= '" + subGrupo + "'" + " and g.GPOnumero=" + id_grupo + " and ua.UAPclave=" + id_unidadAprendizaje + " and UnidadAprendizaje_imparte_profesor_UIPid=" + idUnidadSeleccionada);
        return racId;
    }

//    public List<Reporteavancecontenidotematico> buscarReporteavancecontenidotematico(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo){
//        return buscarReporteavancecontenidotematico(id_unidadAprendizaje, numero, id_profesor, id_grupo, subGrupo, tipo, null);
//    }
    public void initCriteria4() {
        criteria = getSession().createCriteria(Reporteavancecontenidotematico.class, "reporteavancecontenidotematico");
        criteria.createAlias("reporteavancecontenidotematico.unidadaprendizajeImparteProfesor", "unidadaprendizajeImparteProfesor");//Inner Join by default
        criteria.createAlias("unidadaprendizajeImparteProfesor.unidadaprendizaje", "unidadaprendizaje");//Inner Join by default
        criteria.createAlias("unidadaprendizajeImparteProfesor.profesor", "profesor");//Inner Join by default    
        criteria.createAlias("unidadaprendizajeImparteProfesor.grupo", "grupo");//Inner Join by default
        criteria.createAlias("unidadaprendizajeImparteProfesor.cicloescolar", "cicloescolar");//Inner Join by default
        criteria.createAlias("grupo.planestudio", "planestudio");//Inner Join by default
        criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default      
        //criteria.createAlias("profesor.", "");//Inner Join by default        
        //criteria.createAlias("unidadaprendizaje.areaconocimiento", "areaconocimiento");//Inner Join by default
        //criteria.createAlias("areaconocimiento.planestudio", "planestudio2");//Inner Join by default             
        criteria.createAlias("programaeducativo.unidadacademica", "unidadacademica");//Inner Join by default      
    }

    public List findByCriteria4(ReporteAux reporte) {
        List listaUAp = null;
        initSession();
        initCriteria4();
        System.out.println("FIND BY CRITERIA 4 SE EJECUTA CON " + reporte.op);
        //--------------------------Entregados y no entregados general--------------------------------//
        //parte del metodo que busca los entregados y no entrados en cada caso//
        if (reporte.op.equals("EntregadosYNo")) {
            if (reporte.tipo.equalsIgnoreCase("Enviado")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            }
            if (reporte.tipo.equalsIgnoreCase("Parcial")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("EnviadoYParcial")) {
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            //los siguientes condiciones con atributos sirven para filtrar con la
            //restricción si es cero o nulo lo ignora si no busca agregar la restricción
            //indicada para el filtro correspondiente o que correspondan los resultados
            //al atributo o valor indicado como restricción o igualdad de la consulta 
            if (reporte.uacclave == 0) {

            } else {
                criteria.add(Restrictions.eq("unidadacademica.uacclave", reporte.uacclave));
            }
            if (reporte.clavepe == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clavepe));
            }
            listaUAp = criteria.list();
        }
        //-----------------------Calendario de reportes----------------------------------------------//
        if (reporte.op.equals("CalendReport")) {
            criteria = getSession().createCriteria(Calendarioreporte.class, "calendarioreporte");
            listaUAp = criteria.list();
        }
        //-----------------------Calendario de reporte tiene alerta----------------------------------------------//
        if (reporte.op.equals("CalendReportTieneAlerta")) {
            criteria = getSession().createCriteria(CalendarioreporteTieneAlerta.class, "calendarioreportetienealerta");
            criteria.createAlias("calendarioreportetienealerta.calendarioreporte", "calendarioreporte");
            if (reporte.creid == 0) {

            } else {
                criteria.add(Restrictions.eq("calendarioreporte.creid", reporte.creid));
            }
            if (reporte.calnumeroReporte == 0) {

            } else {
                criteria.add(Restrictions.eq("calendarioreportetienealerta.calnumeroReporte", reporte.calnumeroReporte));
            }
            listaUAp = criteria.list();
        }
        //------------------------Area con todos------------------------------------------------------//
        if (reporte.op.equals("AreaConTodos")) {
            criteria = getSession().createCriteria(Areaconocimiento.class, "areaconocimiento");
            criteria.createAlias("areaconocimiento.planestudio", "planestudio");
            criteria.createAlias("planestudio.programaeducativo", "programaeducativo");
            if (reporte.clavepe == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clavepe));
            }
            listaUAp = criteria.list();
        }
        //------------------------ConfiguracionTodos------------------------------------------------------//
        if (reporte.op.equals("ConfiguracionTodos")) {
            criteria = getSession().createCriteria(Configuracion.class, "configuracion");
            criteria.createAlias("configuracion.alerta", "alerta");
            criteria.createAlias("configuracion.cicloescolar", "cicloescolar");

            listaUAp = criteria.list();
        }
        //------------------------CalendarioReporteTieneAlerta------------------------------------------------------//
        if (reporte.op.equals("CalendarioReporteTieneAlertaTodos")) {
            criteria = getSession().createCriteria(CalendarioreporteTieneAlerta.class, "calendarioreportetienealerta");
            criteria.createAlias("calendarioreportetienealerta.calendarioreporte", "calendarioreporte");
            criteria.createAlias("calendarioreportetienealerta.alerta", "alerta");

            listaUAp = criteria.list();
        }
        //--------------------Enviados y parciales-----------------------------------------------------------------------------//
        if (reporte.op.equals("EnviadosYParciales")) {
            criteria = getSession().createCriteria(Reporteavancecontenidotematico.class, "reporteavancecontenidotematico");
            //criteria.createAlias("reporte.reporteavancecontenidotematico", "reporteavancecontenidotematico");//Inner Join by default
            //criteria.createAlias("reporteavancecontenidotematico.unidadaprendizajeImparteProfesor", "unidadaprendizajeImparteProfesor",JoinType.RIGHT_OUTER_JOIN);//Inner Join by default
            criteria.createAlias("reporteavancecontenidotematico.unidadaprendizajeImparteProfesor", "unidadaprendizajeImparteProfesor");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.unidadaprendizaje", "unidadaprendizaje");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.profesor", "profesor");//Inner Join by default                        
            //criteria.setFetchMode("programaeducativo.profesors", FetchMode.JOIN);
            criteria.createAlias("unidadaprendizajeImparteProfesor.grupo", "grupo");//Inner Join by default
            criteria.createAlias("grupo.planestudio", "planestudio");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.cicloescolar", "cicloescolar");//Inner Join by default
//            criteria.createAlias("unidadaprendizaje.areaconocimiento", "areaconocimiento");//Inner Join by default
//            //criteria.setMaxResults(10);
//            criteria.createAlias("areaconocimiento.planestudio", "planestudio2");//Inner Join by default     
            criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default        

            //criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
            //      criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            //criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", UIPid));      
            //criteria.add(Restrictions);
            if (reporte.clave == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clave));
            }
//            if(reporte.acoclave==0){
//                
//            }else{
//            criteria.add(Restrictions.eq("areaconocimiento.acoclave",reporte.acoclave));         
//            }
            if (reporte.uapclave == 0) {
                //
            } else {
                criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", reporte.uapclave));
            }
            if (reporte.gponumero == 0) {

            } else {
                criteria.add(Restrictions.eq("grupo.gponumero", reporte.gponumero));
            }
            if (reporte.pronumeroEmpleado == 0) {

            } else {
                criteria.add(Restrictions.eq("profesor.pronumeroEmpleado", reporte.pronumeroEmpleado));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }

            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }

            if (reporte.numProfUIPid == 0) {

            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }

            listaUAp = criteria.list();

            //session.close();
            //return listaUAp;
        }
        //--------------------No entregados vacio-----------------------------------------------------------------------------//
        if (reporte.op.equals("NoEntregadoVacio")) {
            criteria = getSession().createCriteria(UnidadaprendizajeImparteProfesor.class, "unidadaprendizajeImparteProfesor");
            //criteria.createAlias("reporte.reporteavancecontenidotematico", "reporteavancecontenidotematico");//Inner Join by default
            //criteria.createAlias("reporteavancecontenidotematico.unidadaprendizajeImparteProfesor", "unidadaprendizajeImparteProfesor");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.unidadaprendizaje", "unidadaprendizaje");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.profesor", "profesor");//Inner Join by default                        
            //criteria.setFetchMode("programaeducativo.profesors", FetchMode.JOIN);
            criteria.createAlias("unidadaprendizajeImparteProfesor.grupo", "grupo");//Inner Join by default
            criteria.createAlias("grupo.planestudio", "planestudio");//Inner Join by default
            criteria.createAlias("cicloescolar", "cicloescolar");//Inner Join by default
//            criteria.createAlias("unidadaprendizaje.areaconocimiento", "areaconocimiento");//Inner Join by default
//            //criteria.setMaxResults(10);
//            criteria.createAlias("areaconocimiento.planestudio", "planestudio2");//Inner Join by default     
            criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default        

            //criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
            //      criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            //criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", UIPid));      
            //criteria.add(Restrictions);
            if (reporte.clave == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clave));
            }
//            if(reporte.acoclave==0){
//                
//            }else{
//            criteria.add(Restrictions.eq("areaconocimiento.acoclave",reporte.acoclave));         
//            }
            if (reporte.uapclave == 0) {
                //
            } else {
                criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", reporte.uapclave));
            }
            if (reporte.gponumero == 0) {

            } else {
                criteria.add(Restrictions.eq("grupo.gponumero", reporte.gponumero));
            }
            if (reporte.pronumeroEmpleado == 0) {

            } else {
                criteria.add(Restrictions.eq("profesor.pronumeroEmpleado", reporte.pronumeroEmpleado));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }

            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }

            if (reporte.numProfUIPid == 0) {

            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }

            listaUAp = criteria.list();

            //session.close();
            //return listaUAp;
        }
        //--------------------ATiempoYNo-----------------------------------------------------------------------------//
        if (reporte.op.equals("ATiempoYNo")) {
            criteria = getSession().createCriteria(Reporteavancecontenidotematico.class, "reporteavancecontenidotematico");
            //criteria.createAlias("reporte.reporteavancecontenidotematico", "reporteavancecontenidotematico");//Inner Join by default
            criteria.createAlias("reporteavancecontenidotematico.unidadaprendizajeImparteProfesor", "unidadaprendizajeImparteProfesor");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.unidadaprendizaje", "unidadaprendizaje");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.profesor", "profesor");//Inner Join by default                        
            //criteria.setFetchMode("programaeducativo.profesors", FetchMode.JOIN);
            criteria.createAlias("unidadaprendizajeImparteProfesor.grupo", "grupo");//Inner Join by default
            criteria.createAlias("grupo.planestudio", "planestudio");//Inner Join by default
            criteria.createAlias("unidadaprendizajeImparteProfesor.cicloescolar", "cicloescolar");//Inner Join by default
//            criteria.createAlias("unidadaprendizaje.areaconocimiento", "areaconocimiento");//Inner Join by default
//            //criteria.setMaxResults(10);
//            criteria.createAlias("areaconocimiento.planestudio", "planestudio2");//Inner Join by default     
            criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default        

            //criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
//quite la siguiente linea para que obtenga resultados de Enviados y Parciales
//         criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            //criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", UIPid));      
            if (reporte.tipo.equalsIgnoreCase("ATiempo")) {
                criteria.add(Restrictions.le("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("FueraTiempo")) {
                criteria.add(Restrictions.gt("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("EnFechaLimite")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }

            //criteria.add(Restrictions);
            if (reporte.clave == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clave));
            }
//            if(reporte.acoclave==0){
//                
//            }else{
//            criteria.add(Restrictions.eq("areaconocimiento.acoclave",reporte.acoclave));         
//            }
            if (reporte.uapclave == 0) {
                //
            } else {
                criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", reporte.uapclave));
            }
            if (reporte.gponumero == 0) {

            } else {
                criteria.add(Restrictions.eq("grupo.gponumero", reporte.gponumero));
            }
            if (reporte.pronumeroEmpleado == 0) {

            } else {
                criteria.add(Restrictions.eq("profesor.pronumeroEmpleado", reporte.pronumeroEmpleado));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }
            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }

            if (reporte.numProfUIPid == 0) {

            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }

            listaUAp = criteria.list();

            //session.close();
            //return listaUAp;
        }
        //------------------------ConfigSet-----------------------------------------------------------//
        if (reporte.op.equals("ConfigSet")) {
            criteria = getSession().createCriteria(Configuracion.class, "configuracion");
            criteria.createAlias("configuracion.cicloescolar", "cicloescolar");//Inner Join by default
            criteria.createAlias("configuracion.alerta", "alerta");//Inner Join by default
            //criteria.createAlias("configuracion.calendarioreportes", "calendarioreportes");//Inner Join by default
            //criteria.createAlias("alerta.calendarioreporteTieneAlertas", "calendarioreporteTieneAlerta");//Inner Join by default
            //criteria.add(Restrictions.eq("calendarioreporteTieneAlerta.calnumeroReporte",reporte.calnumeroReporte));   
            listaUAp = criteria.list();
        }
        //----------------------CompararAreaCon------------------------------------------------------//
        if (reporte.op.equals("CompararAreaCon")) {
            if (reporte.tipo.equalsIgnoreCase("ATiempo") || reporte.tipo.equalsIgnoreCase("FueraTiempo") || reporte.tipo.equalsIgnoreCase("Enviado")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            }
            if (reporte.tipo.equalsIgnoreCase("Parcial")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("EnviadoYParcial")) {
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("ATiempo")) {
                criteria.add(Restrictions.le("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("FueraTiempo")) {
                criteria.add(Restrictions.gt("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.clavepe == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clavepe));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }
//            if(reporte.acoclave == 0){
//                
//            }else{
//            criteria.add(Restrictions.eq("areaconocimiento.acoclave",reporte.acoclave));
//            }
            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }
            if (reporte.numProfUIPid == 0) {
            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }
            listaUAp = criteria.list();
        }
        //-----------------------------ProgEducatTodos------------------------------------------------//
        if (reporte.op.equals("ProgEducatTodos")) {
            criteria = getSession().createCriteria(Planestudio.class, "planestudio");
            criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default
            listaUAp = criteria.list();
        }
        //-----------------------------PAGC------------------------------------------------//
        if (reporte.op.equals("PAGC")) {
            criteria = getSession().createCriteria(UnidadaprendizajeImparteProfesor.class, "unidadaprendizajeimparteprofesor");
            criteria.createAlias("unidadaprendizajeimparteprofesor.unidadaprendizaje", "unidadaprendizaje");//Inner Join by default
            criteria.createAlias("unidadaprendizajeimparteprofesor.grupo", "grupo");//Inner Join by default
            criteria.createAlias("unidadaprendizajeimparteprofesor.profesor", "profesor");//Inner Join by default
            criteria.createAlias("grupo.planestudio", "planestudio");//Inner Join by default
            criteria.createAlias("cicloescolar", "cicloescolar");//Inner Join by default
//            criteria.createAlias("unidadaprendizaje.areaconocimiento", "areaconocimiento");//Inner Join by default
//            criteria.createAlias("areaconocimiento.planestudio", "planestudio");//Inner Join by default     
            criteria.createAlias("planestudio.programaeducativo", "programaeducativo");//Inner Join by default         
            criteria.createAlias("programaeducativo.unidadacademica", "unidadacademica");//Inner Join by default         

            listaUAp = criteria.list();
        }
        //-----------------------------CompararProfGrupo----------------------------------------------//
        if (reporte.op.equals("CompararProfGrupo")) {
            if (reporte.tipo.equalsIgnoreCase("ATiempo") || reporte.tipo.equalsIgnoreCase("FueraTiempo") || reporte.tipo.equalsIgnoreCase("Enviado")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            }
            if (reporte.tipo.equalsIgnoreCase("Parcial")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("EnviadoYParcial")) {
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("ATiempo")) {
                criteria.add(Restrictions.le("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("FueraTiempo")) {
                criteria.add(Restrictions.gt("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.clavepe == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clavepe));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }
//            if(reporte.acoclave == 0){
//                
//            }else{
//            criteria.add(Restrictions.eq("areaconocimiento.acoclave",reporte.acoclave));
//            }
            if (reporte.uapclave == 0) {
                //
            } else {
                criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", reporte.uapclave));
            }
            if (reporte.pronumeroEmpleado == 0) {

            } else {
                criteria.add(Restrictions.eq("profesor.pronumeroEmpleado", reporte.pronumeroEmpleado));
            }
            if (reporte.gponumero == 0) {

            } else {
                criteria.add(Restrictions.eq("grupo.gponumero", reporte.gponumero));
            }
            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }
            if (reporte.numProfUIPid == 0) {
            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }
            listaUAp = criteria.list();
        }
        //------------------------------------CompararProgramaEducativo------------------------------------------//
        if (reporte.op.equals("CompararProgEduc")) {
            System.out.println("FIND BY CRITERIA 4 SE EJECUTA COMPARARPRGEDUC CON " + reporte.tipo);
            if (reporte.tipo.equalsIgnoreCase("ATiempo") || reporte.tipo.equalsIgnoreCase("FueraTiempo") || reporte.tipo.equalsIgnoreCase("Enviado")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            }
            if (reporte.tipo.equalsIgnoreCase("Parcial")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("EnviadoYParcial")) {
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("ATiempo")) {
                criteria.add(Restrictions.le("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("FueraTiempo")) {
                criteria.add(Restrictions.gt("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.clavepe == 0) {

            } else {
                System.out.println("SE BUSCA QUE EL PROGRAMA EUCATIVO SEA " + reporte.clavepe);
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clavepe));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                System.out.println("SE BUSCA QUE EL PLAN DE ESTUDIO SEASEA " + reporte.pesvigencia);
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                System.out.println("SE BUSCA QUE EL CICLO ESCOLAR SEA " + reporte.cescicloEscolar);
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }
            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }
            if (reporte.numProfUIPid == 0) {
            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }
            listaUAp = criteria.list();
            System.out.println(" SE OBTUVO UN LISTA CRITERIA CON " + listaUAp.size());
        }
        //------------------------------------PorcentAvanceSolo------------------------------------------//
        if (reporte.op.equals("PorcentAvanceSolo")) {
            //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            if (reporte.tipo.equalsIgnoreCase("ATiempo")) {
                criteria.add(Restrictions.le("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("FueraTiempo")) {
                criteria.add(Restrictions.gt("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.clave == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clave));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }
            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }
            if (reporte.numProfUIPid == 0) {
            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }
            listaUAp = criteria.list();
        }
        //------------------------------------CompararUAGrupo------------------------------------------//      
        if (reporte.op.equals("CompararUAGrupo")) {
            if (reporte.tipo.equalsIgnoreCase("ATiempo") || reporte.tipo.equalsIgnoreCase("FueraTiempo") || reporte.tipo.equalsIgnoreCase("Enviado")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
            }
            if (reporte.tipo.equalsIgnoreCase("Parcial")) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("EnviadoYParcial")) {
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Enviado"));
                //criteria.add(Restrictions.eq("reporteavancecontenidotematico.racstatus", "Parcial"));
            }
            if (reporte.tipo.equalsIgnoreCase("ATiempo")) {
                criteria.add(Restrictions.le("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.tipo.equalsIgnoreCase("FueraTiempo")) {
                criteria.add(Restrictions.gt("reporteavancecontenidotematico.racfechaElaboracion", reporte.fecha1));
            }
            if (reporte.clavepe == 0) {

            } else {
                criteria.add(Restrictions.eq("programaeducativo.pedclave", reporte.clavepe));
            }
            if (reporte.pesvigencia == null || reporte.pesvigencia.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("planestudio.pesvigenciaPlan", reporte.pesvigencia));
            }
            if (reporte.cescicloEscolar == null || reporte.cescicloEscolar.equalsIgnoreCase("")) {

            } else {
                criteria.add(Restrictions.eq("cicloescolar.cescicloEscolar", reporte.cescicloEscolar));
            }
//            if(reporte.acoclave == 0){
//                
//            }else{
//            criteria.add(Restrictions.eq("areaconocimiento.acoclave",reporte.acoclave));
//            }
            if (reporte.uapclave == 0) {
                //
            } else {
                criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", reporte.uapclave));
            }
            if (reporte.gponumero == 0) {

            } else {
                criteria.add(Restrictions.eq("grupo.gponumero", reporte.gponumero));
            }
            if (reporte.numRact == 1) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "1"));
            }
            if (reporte.numRact == 2) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "2"));
            }
            if (reporte.numRact == 3) {
                criteria.add(Restrictions.eq("reporteavancecontenidotematico.racnumero", "3"));
            }
            if (reporte.numProfUIPid == 0) {
            } else {
                criteria.add(Restrictions.eq("unidadaprendizajeImparteProfesor.uipid", reporte.numProfUIPid));
            }
            listaUAp = criteria.list();
        }
        getSession().close();
        return listaUAp;
    }

    public List findFromWhereAreaCon(int uapclave) {
        //query anterior con id de configuración
        //String queryCalendarios="from Configuracion as c join c.calendarioreportes as cr where c.conid="+conid;
        String queryAreaCon = "from Areaconocimiento as a join a.unidadaprendizajes as ua where ua.uapclave='" + uapclave + "'";
        System.out.println(" ");
        System.out.println(queryAreaCon);
        List<Areaconocimiento> listaAreaCon = null;
        //Configuracion con=null;
        //ServiceLocator.getInstanceBaseDAO().setTipo(Configuracion.class);
        //con=(Configuracion) ServiceLocator.getInstanceBaseDAO().find(conid);
        listaAreaCon = new ArrayList<Areaconocimiento>();
//        List<Object> listaObjetos=executeQuery(queryCalendarios);
        List<Reporteavancecontenidotematico> listaObjetos = executeQueryHql(queryAreaCon);
        Iterator itr = listaObjetos.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            listaAreaCon.add((Areaconocimiento) obj[0]);
        }

        System.out.println(" ");

//        for(int x=0; x<listaCalendario.size(); x++){
//            System.out.println(listaCalendario.get(x).getCrefechaCorte());
//        }            
        //listaCalendario.sort(comparadorFechasCorte);//Ordena por fechas de corte
        return listaAreaCon;
//        return listaObjetos;
    }

    public List findFromWhereCalendReporte(Date confechaInicioSemestre) {
        //query anterior con id de configuración
        //String queryCalendarios="from Configuracion as c join c.calendarioreportes as cr where c.conid="+conid;
        String queryCalendarios = "from Configuracion as c join c.calendarioreportes as cr where c.confechaInicioSemestre='" + confechaInicioSemestre + "'";
        System.out.println(" ");
        System.out.println(queryCalendarios);
        List<Calendarioreporte> listaCalendario = null;
        Configuracion con = null;
        //ServiceLocator.getInstanceBaseDAO().setTipo(Configuracion.class);
        //con=(Configuracion) ServiceLocator.getInstanceBaseDAO().find(conid);
        listaCalendario = new ArrayList<Calendarioreporte>();
        List<Reporteavancecontenidotematico> listaObjetos = executeQueryHql(queryCalendarios);
        Iterator itr = listaObjetos.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            listaCalendario.add((Calendarioreporte) obj[1]);
        }

        System.out.println(" ");

        //listaCalendario.sort(comparadorFechasCorte);//Ordena por fechas de corte
        return listaCalendario;
    }

    public List findByCriteriaDetalladoCordAreaAdminProfUAprend(int uapclave) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Coordinadorareaadministrativa.class, "coordinadorareaadministrativa");
        criteria.createAlias("coordinadorareaadministrativa.profesor", "profesor");
        criteria.createAlias("coordinadorareaadministrativa.unidadaprendizaje", "unidadaprendizaje");
        //si se tiene la clave de unidad academica diferente de cero(no vacio)
        //se pone la restricción de que sea igual a esta clave
        if (uapclave != 0) {
            criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", uapclave));
        }
        List listaCAAp = criteria.list();
        session.close();
        return listaCAAp;
    }

    public List findByCriteriaDetalladoCordAreaAdminProfUAprend(int uapclave, int pedclave, int aadid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Coordinadorareaadministrativa.class, "coordinadorareaadministrativa");
        criteria.createAlias("coordinadorareaadministrativa.profesor", "profesor");
        criteria.createAlias("coordinadorareaadministrativa.unidadaprendizaje", "unidadaprendizaje");
        criteria.createAlias("coordinadorareaadministrativa.areaadministrativa", "areaadministrativa");
        criteria.createAlias("areaadministrativa.programaeducativo", "programaeducativo");
        //si se tiene la clave de unidad academica diferente de cero(no vacio)
        //se pone la restricción de que sea igual a esta clave
        if (uapclave != 0) {
            criteria.add(Restrictions.eq("unidadaprendizaje.uapclave", uapclave));
        }
        if (pedclave != 0) {
            criteria.add(Restrictions.eq("programaeducativo.pedclave", pedclave));
        }
        if (aadid != 0) {
            criteria.add(Restrictions.eq("areaadministrativa.aadid", aadid));
        }
        List listaCAAp = criteria.list();
        session.close();
        return listaCAAp;
    }

    public int countEsperadosProgramaEducativoAlterno(int idProgramaEducativo, int noReporte, List<String> selectCicloEscolarList) {
        int resultado = 0;
        //ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
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

        //resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a ","a.grupo.planestudio.programaeducativo.pedid ", String.valueOf(idProgramaEducativo) + inCicloEscolar);
        return resultado;
    }

    @Override
    public int count(String clase, String campo, String criterio) {
        int resultado = 0;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(this.getEntityClass().getName());
        String identificador = catMeta.getIdentifierPropertyName();
        // String nombre = tipo.getSimpleName();
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            String query1 = "select count( distinct a." + identificador + ") from " + clase + " where " + campo + "=" + criterio;
            System.out.println("******************************** " + query1);
            Query query = HibernateUtil.getSession().createQuery(query1);

            resultado = ((Number) query.uniqueResult()).intValue();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return resultado;
    }

    public List<Reporteavancecontenidotematico> buscarReporteavancecontenidotematico(int id_unidadAprendizaje, int id_profesor, int id_grupo, String subGrupo, String tipo, String fechaLimite) {
        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
        criterio += " conf.confechaInicioSemestre='" + fechaLimite + "'";

        return this.executeQueryHql("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
    }

    public List<Reporteavancecontenidotematico> buscarReporteavancecontenidotematicoNumero(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo, String fechaLimite) {
        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
        criterio += "racnumero='" + numero + "' AND ";
        criterio += "conf.confechaInicioSemestre='" + fechaLimite + "'";
        return this.executeQueryHql("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
    }

    public List<Reporteavancecontenidotematico> buscarReporteavancecontenidotematicoNumeroConsultas(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo, String fechaLimite) {
        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
        criterio += "racnumero='" + numero + "' ";
        return this.executeQueryHql("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
    }

    public List<Reporteavancecontenidotematico> buscarReporteavancecontenidotematicoUltimo(int id_unidadAprendizaje, int id_profesor, int id_grupo, String tipo, String subGrupo, String fechaLimite) {
        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gpoid = " + id_grupo + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
        criterio += " conf.confechaInicioSemestre='" + fechaLimite + "'";
        return this.executeQueryHql("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
    }

    public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoIncompleto(int id_unidadAprendizaje, int id_profesor, int id_grupo) {
        String criterio = "unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
//                    criterio+="unidadaprendizajeImparteProfesor.grupo.gpoid = "+id_grupo+" AND ";
        criterio += "unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND a.racstatus = 'parcial'";
        return this.findMultipleIDExtra(id_grupo, criterio, "unidadaprendizajeImparteProfesor.grupo.gponumero", "", "Reporteavancecontenidotematico");

    }

    public int countReportesDeProgramaEducativo(int idProgramaEducativo, int noReporte, String estado, List<String> selectCicloEscolarList) {
        int resultado = 0;
        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {

            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }
            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }

        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("Entregados")) {
            andEstado = " AND a.racstatus='enviado' ";
        }
        if (estado.equalsIgnoreCase("noentregados")) {
            andEstado = " AND a.racstatus='parcial' ";
        }
        if (estado.equalsIgnoreCase("entregadosatiempo")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("entregadosdespueslimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("Porcentaje de Avance Global Completo")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("Porcentaje de Avance Global Incompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }
        resultado = count("Reporteavancecontenidotematico a join "
                + "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.areaconocimientos b" + andJoin,
                "a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedid",
                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + inCicloEscolar);
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

    public int countReportesDeAreaConocimientos(int areaConId, int noReporte, String estado, String PlanId, String idCicloEscolar) {
        int resultado = 0;

        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";
        String inCicloEscolar = "";
        int x = 0;
        // la inicializo en 0 para que no queden or de mas
        String idPlan = "";
//        for (Planestudio plan : s) {
//            // idPlan.concat(" or "+plan.getPesid());
//            if(x==0){
//                idPlan="PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
//            }else{
//            idPlan = idPlan + " or PlanEstudio_PESid = " + "'" + plan.getPESid().toString() + "'";
//            }
//            x++;
//        }
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }

        System.out.println("CONSULTA Para ce-------------------------\n" + inCicloEscolar);
        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND RACnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("enviado")) {
            andEstado = " AND RACstatus='enviado' ";
        }
//        if (estado.equalsIgnoreCase("noentregados")) {
//            andEstado = " AND RACstatus!='enviado' ";
//        }
        if (estado.equalsIgnoreCase("antesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }

        //String andPeclave = "and a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//        resultado = count("Reporteavancecontenidotematico a join "
//                + "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.areaconocimientos b" + andJoin,
//                "b.acoid",
//                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + inCicloEscolar + andPeclave);
        System.out.println(noReporte);
        System.out.println(estado);
        System.out.println(areaConId);
        System.out.println(PlanId);
        System.out.println(idCicloEscolar);
        String queryBase = "select count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in\n"
                + "(select UIPid from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid in(select UAPid \n"
                + "from unidadaprendizaje where UnidadAprendizaje_UAPid in(SELECT UnidadAprendizaje_UAPid from areaconocimiento_has_unidadaprendizaje\n"
                + "where AreaConocimiento_ACOid in (SELECT ACOid from areaconocimiento where ACOid=" + areaConId + " and PlanEstudio_PESid=" + PlanId + "))) and CicloEscolar_CESid = " + idCicloEscolar + ") " + andNumeroReporte + " " + andEstado + ";";
        System.out.println(queryBase);
        resultado = this.executeCountQuery(queryBase);
        System.out.println(resultado);

        return resultado;
    }

    public int resportesEntregadosAreaConocimiento(int idProgramaEducativo, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;

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

    public int countReportesDeUnidadAprendizaje(int idProgramaEducativo, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;

        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("enviado")) {
            andEstado = " AND a.racstatus='enviado' ";
        }
        if (estado.equalsIgnoreCase("no enviado")) {
            andEstado = " AND a.racstatus!='enviado' ";
        }
        if (estado.equalsIgnoreCase("antesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }

        String andPeclave = " and a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
        resultado = count("Reporteavancecontenidotematico a " + andJoin,
                "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapid",
                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + inCicloEscolar + andPeclave);
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

    public int countReportesDeUnidadAprendizaje(int idProgramaEducativo, int noReporte, String estado, List<String> grupos, List<String> selectCicloEscolarList) {
        int resultado = 0;
        String andGrupos = " ";
        if (grupos.size() > 0) {
            andGrupos = " AND a.unidadaprendizajeImparteProfesor.grupo.gponumero IN (";
            for (String grupo : grupos) {
                andGrupos += grupo + ",";
            }
            andGrupos = andGrupos.substring(0, andGrupos.length() - 1);
            andGrupos += ")";
        }

        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";

        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("enviado")) {
            andEstado = " AND a.racstatus='enviado' ";
        }
        if (estado.equalsIgnoreCase("no enviado")) {
            andEstado = " AND a.racstatus!='enviado' ";
        }
        if (estado.equalsIgnoreCase("antesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }

        resultado = count("Reporteavancecontenidotematico a " + andJoin,
                "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapid",
                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + andGrupos + inCicloEscolar);
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

    public int countReportesDeProfesor(String idProfesor, int noReporte, String estado, String extra, List<String> selectCicloEscolarList) {
        int resultado = 0;

        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("enviado")) {
            andEstado = " AND a.racstatus='enviado' ";
        }
        if (estado.equalsIgnoreCase("no enviado")) {
            andEstado = " AND a.racstatus!='enviado' ";
        }
        if (estado.equalsIgnoreCase("antesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }

        resultado = count("Reporteavancecontenidotematico a " + andJoin,
                "a.unidadaprendizajeImparteProfesor.profesor.proid",
                "'" + String.valueOf(idProfesor) + "'" + andNumeroReporte + andEstado + extra + inCicloEscolar);

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

    public int countReportesDeAreaAdmin(int idAreaAdmin, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;

        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }

        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("enviado")) {
            andEstado = " AND a.racstatus='enviado' ";
        }
        if (estado.equalsIgnoreCase("no enviado")) {
            andEstado = " AND a.racstatus!='enviado' ";
        }
        if (estado.equalsIgnoreCase("antesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }

        String andPeclave = " and a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";

        resultado = count("Reporteavancecontenidotematico a join "
                + "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.coordinadorareaadministrativas b" + andJoin,
                "b.areaadministrativa.aadid",
                "'" + String.valueOf(idAreaAdmin) + "'" + andNumeroReporte + andEstado + inCicloEscolar + andPeclave);

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

    public List<Reporteavancecontenidotematico> executeTransformationQuery(String query, Class<Reporteavancecontenidotematico> entityClass, String... param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo para traer los RACTS por programa educativo
     *
     * @param selectProgramEducativo
     * @param ract
     * @param reporte
     * @param cicloEscolar
     * @return lista de RACTs
     */
    public List<Object[]> getReportesPrograma(List<String> selectProgramEducativo, String ract,
            String reporte, List<String> cicloEscolar) {
        List<Object[]> resultado = new ArrayList();
        String whereNoEntregados = "";
        String condicionNumeroRact = "";
        String programasEducativos = " ";
        String condicioncicloEscolar = "";
        for (String pedclave : selectProgramEducativo) {
            System.out.println("-------Agregado PEDCLAVE" + pedclave);
            programasEducativos += "'" + pedclave + "',";
        }
        programasEducativos = programasEducativos.substring(0, programasEducativos.length() - 1);
//                
        String condicioncicloEscolarResponsable = " and responsableprogramaeducativo.profesor_proid in("
                + "select min(profesor_proid) from responsableprogramaeducativo "
                + "join programaeducativo on responsableprogramaeducativo.programaeducativo_pedid=programaeducativo.pedid "
                + "where "
                + "programaeducativo.pedclave in (" + programasEducativos + ") "
                + "  group by programaeducativo.pedid)";
        for (String ciclo : cicloEscolar) {
            System.out.println("--> AGREGANDO CICLOS ESCOLARES");
            condicioncicloEscolar += "'" + ciclo + "',";
        }
        if (!condicioncicloEscolar.isEmpty()) {
            condicioncicloEscolar = condicioncicloEscolar.substring(0, condicioncicloEscolar.length() - 1);
            condicioncicloEscolar = " and cicloescolar.cescicloescolar in(" + condicioncicloEscolar + ") ";
        }

        if (!ract.equals("0") && !ract.equals("4")) {
            condicionNumeroRact = "and reporteavancecontenidotematico.RACnumero='" + ract + "' ";
        }
        String andEstado = "";
        String andJoin = "";

        if (reporte.equalsIgnoreCase("Entregados")) {
            andEstado = " AND reporteavancecontenidotematico.racstatus='enviado' ";
        }

        if (reporte.equalsIgnoreCase("Porcentaje de Avance Global Completo")) {
            andJoin = " ";
            andEstado = " AND reporteavancecontenidotematico.racstatus='enviado' AND reporteavancecontenidotematico.racavanceGlobal='100' ";
        }

        if (reporte.equalsIgnoreCase("Porcentaje de Avance Global Incompleto")) {
            andJoin = " ";
            andEstado = " AND reporteavancecontenidotematico.racstatus='enviado' AND reporteavancecontenidotematico.racavanceGlobal<'100' ";
        }
        if (reporte.equalsIgnoreCase("noentregados")) {

            String queryBusquedaEntregados = " select distinct "
                    + " unidadaprendizaje_imparte_profesor.uipid,"
                    + " unidadaprendizaje_imparte_profesor.UIPsubgrupo "
                    //                        + " concat(profeAAD.PROnombre,' ',profeAAD.PROapellidoPaterno,' ',profeAAD.PROapellidoMaterno) "//COLUMNA 19
                    + " from  unidadaprendizaje_imparte_profesor "
                    + " join profesor as profe"
                    + "  on unidadaprendizaje_imparte_profesor.Profesor_PROid= profe.PROid "
                    + ""
                    + " join grupo "
                    + " on unidadaprendizaje_imparte_profesor.Grupo_GPOid = grupo.GPOid "
                    + " join unidadaprendizaje"
                    + " on unidadaprendizaje_imparte_profesor.UnidadAprendizaje_UAPid = unidadaprendizaje.UAPid "
                    + " join cicloescolar "
                    + "on "
                    + "   unidadaprendizaje_imparte_profesor.cicloescolar_cesid=cicloescolar.cesid "
                    + condicioncicloEscolar
                    + "  join  planestudio "
                    + " on grupo.PlanEstudio_PESid= planestudio.PESid "
                    + ""
                    + "  join programaeducativo "
                    + "  on  programaeducativo.PEDid=planestudio.ProgramaEducativo_PEDid "
                    + "and programaeducativo.pedclave in (" + programasEducativos + ") "
                    + ""
                    + "  left join  (select * from \n"
                    + " areaconocimiento_has_unidadaprendizaje  \n"
                    + " join areaconocimiento   on  areaconocimiento_has_unidadaprendizaje.AreaConocimiento_ACOid=areaconocimiento.ACOid) as areasCDepe\n"
                    + "on areasCDepe.planestudio_PESid=planestudio.PESid \n"
                    + " and areasCDepe.UnidadAprendizaje_UAPid= unidadaprendizaje.UAPid\n"
                    + " \n"
                    + " left join  (select * from \n"
                    + " areaconocimiento_has_unidadaprendizaje  \n"
                    + " join areaconocimiento   on  areaconocimiento_has_unidadaprendizaje.AreaConocimiento_ACOid=areaconocimiento.ACOid\n"
                    + " group by unidadaprendizaje_UAPid  ) as areasCAlt\n"
                    + " on  areasCAlt.UnidadAprendizaje_UAPid= unidadaprendizaje.UAPid \n"
                    + " and areasCDepe.planestudio_PESid is null"
                    + ""
                    + " left join responsableprogramaeducativo "
                    + "  on responsableprogramaeducativo.ProgramaEducativo_PEDid= programaeducativo.PEDid "
                    + condicioncicloEscolarResponsable
                    + " "
                    + "  join profesor as responsable "
                    + "  on responsableprogramaeducativo.Profesor_PROid=responsable.proid "
                    + ""
                    + " left join unidadacademica "
                    + " on unidadacademica.uacid=unidadacademica_uacid "
                    + "and unidadaprendizaje_imparte_profesor.cicloescolar_cesid=cicloescolar.cesid "
                    + "  join reporteavancecontenidotematico "
                    + " on reporteavancecontenidotematico.UnidadAprendizaje_imparte_profesor_UIPid = unidadaprendizaje_imparte_profesor.UIPid "
                    + condicionNumeroRact
                    + " AND reporteavancecontenidotematico.racstatus='enviado' ";

            resultado = this.executeQueryObjects(queryBusquedaEntregados);

            if (resultado != null) {
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^RESULTADO " + resultado.size());

                for (int i = 0; i < resultado.size(); i++) {
                    whereNoEntregados += "" + resultado.get(i)[0] + ",";
                }

                whereNoEntregados = whereNoEntregados.substring(0, whereNoEntregados.length() - 1);
                whereNoEntregados = " where  unidadaprendizaje_imparte_profesor.uipid not in (" + whereNoEntregados + ") ";
            }
        }

        String query = " select distinct "
                + "  reporteavancecontenidotematico.RACid, "
                + " unidadaprendizaje.UAPclave,"
                + " profe.`PROnumeroEmpleado`, "
                + " CONCAT(profe.`proNombre`,' ',profe.proapellidopaterno,' ',profe.proapellidomaterno)  as nombreProfe,  "
                + " grupo.GPOnumero,"
                + " unidadaprendizaje_imparte_profesor.UIPtipoSubgrupo,"
                + " unidadaprendizaje_imparte_profesor.UIPsubgrupo, "
                + " reporteavancecontenidotematico.RACavanceGlobal, "
                + " reporteavancecontenidotematico.RACfechaElaboracion,  "
                + " reporteavancecontenidotematico.RACnumero, "
                + " unidadaprendizaje.UAPnombre,  "
                + " areasCDepe.`acoclave`,"
                + "areasCDepe.`aconombre`,"
                + " programaeducativo.pedclave,"
                + " programaeducativo.PEDnombre, "
                + " CONCAT(responsable.`proNombre`,' ',responsable.proapellidopaterno,' ',responsable.proapellidomaterno) as nombreResponsablePrograma, "
                + " unidadaprendizaje_imparte_profesor.UIPid ,"
                + "unidadaprendizaje.uapid,"
                + "planestudio.pesvigenciaplan, "
                + "programaeducativo.pedid, "
                + "profe.`PROid`"
                + " areasCAlt.`acoclave` as acoclaveAlternativa, " //20
                + "areasCAlt.`aconombre` as acoNombreAlternativa"//21
                //                        + " concat(profeAAD.PROnombre,' ',profeAAD.PROapellidoPaterno,' ',profeAAD.PROapellidoMaterno) "//COLUMNA 19
                + " from  unidadaprendizaje_imparte_profesor "
                + " join profesor as profe"
                + "  on unidadaprendizaje_imparte_profesor.Profesor_PROid= profe.PROid "
                + ""
                + " join grupo "
                + " on unidadaprendizaje_imparte_profesor.Grupo_GPOid = grupo.GPOid "
                + " join unidadaprendizaje"
                + " on unidadaprendizaje_imparte_profesor.UnidadAprendizaje_UAPid = unidadaprendizaje.UAPid "
                + " join cicloescolar "
                + "on "
                + "   unidadaprendizaje_imparte_profesor.cicloescolar_cesid=cicloescolar.cesid "
                + condicioncicloEscolar
                + "  join  planestudio "
                + " on grupo.PlanEstudio_PESid= planestudio.PESid "
                + ""
                + "  join programaeducativo "
                + "  on  programaeducativo.PEDid=planestudio.ProgramaEducativo_PEDid "
                + "and programaeducativo.pedclave in (" + programasEducativos + ") "
                + ""
                + "  left join  (select * from \n"
                + " areaconocimiento_has_unidadaprendizaje  \n"
                + " join areaconocimiento   on  areaconocimiento_has_unidadaprendizaje.AreaConocimiento_ACOid=areaconocimiento.ACOid) as areasCDepe\n"
                + "on areasCDepe.planestudio_PESid=planestudio.PESid \n"
                + " and areasCDepe.UnidadAprendizaje_UAPid= unidadaprendizaje.UAPid\n"
                + " \n"
                + " left join  (select * from \n"
                + " areaconocimiento_has_unidadaprendizaje  \n"
                + " join areaconocimiento   on  areaconocimiento_has_unidadaprendizaje.AreaConocimiento_ACOid=areaconocimiento.ACOid\n"
                + " group by unidadaprendizaje_UAPid  ) as areasCAlt\n"
                + " on  areasCAlt.UnidadAprendizaje_UAPid= unidadaprendizaje.UAPid \n"
                + " and areasCDepe.planestudio_PESid is null"
                + ""
                + " left join responsableprogramaeducativo "
                + "  on responsableprogramaeducativo.ProgramaEducativo_PEDid= programaeducativo.PEDid "
                + condicioncicloEscolarResponsable
                + " "
                + "  join profesor as responsable "
                + "  on responsableprogramaeducativo.Profesor_PROid=responsable.proid "
                + ""
                + " left join unidadacademica "
                + " on unidadacademica.uacid=unidadacademica_uacid "
                + "and unidadaprendizaje_imparte_profesor.cicloescolar_cesid=cicloescolar.cesid ";

        if (andEstado.trim().isEmpty()) {
            query += " left join reporteavancecontenidotematico ";
        } else {
            query += " join reporteavancecontenidotematico ";
        }

        query += " on reporteavancecontenidotematico.UnidadAprendizaje_imparte_profesor_UIPid = unidadaprendizaje_imparte_profesor.UIPid "
                + condicionNumeroRact
                + andEstado
                //                        + "left join coordinadorareaadministrativa on coordinadorareaadministrativa.unidadaprendizaje_uapid= unidadaprendizaje.UAPid  "
                //                        
                //                        + "left join areaadministrativa on coordinadorareaadministrativa.AreaAdministrativa_AADid= areaadministrativa.AADid "
                //                        + " and  areaadministrativa.programaeducativo_pedid=programaeducativo.pedid  \n"
                //                        + " and programaeducativo.PEDid= areaadministrativa.ProgramaEducativo_PEDid "
                //                        + "left join profesor as profeAAD on coordinadorareaadministrativa.Profesor_PROid= profeAAD.PROid  "
                + whereNoEntregados
                + " order by programaeducativo.PEDnombre, acoclave,unidadaprendizaje.UAPclave,nombreProfe,gponumero, UIPtipoSubgrupo, UIPSubgrupo, reporteavancecontenidotematico.RACnumero";
        System.out.println(query);
        return this.executeQueryObjects(query);
    }

    public int countReportesDeProfesor(int idProfesor, int noReporte, String estado, List<String> selectGrupo, List<String> selectUnidadAprendizaje, List<String> selectCicloEscolarList) {
        int resultado = 0;

        String clavesGrupo = "";
        String clavesUA = "";
        String extraQuery = "";

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

        extraQuery = "";
        if (!clavesGrupo.isEmpty()) {
            extraQuery += " AND  a.unidadaprendizajeImparteProfesor.grupo.gponumero IN (" + clavesGrupo + ")";
        }
        if (!clavesUA.isEmpty()) {
            extraQuery += " AND  a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave IN (" + clavesUA + ")";
        }

        String andEstado = "";
        String andNumeroReporte = "";
        String andJoin = "";
        String inCicloEscolar = "";

        if (selectCicloEscolarList.size() > 0) {
            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
            for (String cicloEscolar : selectCicloEscolarList) {
                inCicloEscolar += "'" + cicloEscolar + "',";
            }

            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
            inCicloEscolar += ")";
        }
        if (noReporte != 0 && noReporte != 4) {
            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
        }

        if (estado.equalsIgnoreCase("enviado")) {
            andEstado = " AND a.racstatus='enviado' ";
        }
        if (estado.equalsIgnoreCase("no enviado")) {
            andEstado = " AND a.racstatus!='enviado' ";
        }
        if (estado.equalsIgnoreCase("antesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
        }
        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
        }

        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
        }

        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
            andJoin = " ";
            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
        }

        resultado = count("Reporteavancecontenidotematico a " + andJoin,
                "a.unidadaprendizajeImparteProfesor.profesor.proid",
                "'" + String.valueOf(idProfesor) + "'" + andNumeroReporte + andEstado + extraQuery + inCicloEscolar);

        return resultado;
    }

    /**
     * *** Nuevos metodos para generador de reportes, revisar anteriores a ver
     * si se siguen usando
     *
     */
    /**
     * Metodo para obtener los reportes entregados para el generador de reportes
     * segun el criterio de busqueda que se establecio
     *
     * @param criterio tipo de busqueda, entregados, no entregados, porcentaje
     * completo...etc
     *
     * @param idPlan id del plan de estudios del programa educativo, puesto que
     * tambien se separa por plan de estudios en el caso del filtro de PE
     *
     * @param noRact numero de ract al que corresponde si es 4 seran todos
     *
     * @param idCicloEscolar id del ciclo escolar de la busqueda
     *
     * @param esperados el numero de esperados para calculos internos
     * @return
     */
    public int ConteoPorProgramaEducativo(String criterio, int idPlan, String noRact, int idCicloEscolar, int esperados) {

        int entregados = 0;

        // genero el query base para traer todos los entregados de todos los ract
        String queryBase = ("select  count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in \n"
                + "(select UIPid from unidadaprendizaje_imparte_profesor where Grupo_GPOid in \n"
                + "(select GPOid from grupo where PlanEstudio_PESid = " + idPlan + " ) and CicloEscolar_CESid =" + idCicloEscolar + " )");

        // si no es para todos los ract agrego validacion del numero de  ract al query
        if (!noRact.equalsIgnoreCase("4")) {
            queryBase = queryBase + " and RACnumero= '" + noRact + "' ";

        } else {
            // si es para todos multiplico los esperados x3 para el calculo posterior
            esperados *= 3;
        }

        // segun la busqueda agrego validaciones al query
        switch (criterio) {
            case "entregados":
                // estare agregando ; para que no marque error al ejecutar el query
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;
            case "noentregados":
                // para no entregados resto los esperados menos los entregados para eso ocupo la variable
                queryBase = queryBase + (" ;");
                int e = this.executeCountQuery(queryBase);
                entregados = esperados - e;
                break;
            case "entregadosynoentregados":
                // este igual trae todos
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                queryBase = queryBase + (" and RACavanceGlobal !=100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo e Incompleto":
                // este es igual de entregados, investigar si se puede eliminar esta opcion 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;
        }

        return entregados;
    }

    /**
     * *
     *
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idArea
     * @return
     */
    public int ConteoPorAreaConocimiento(String criterio, int idPrograma, String noRact, int idCicloEscolar, int esperados, int idArea) {
        System.out.println("ConteoPorAC");
        int entregados = 0;
        String queryBase = "";
        // cuando es tronco comun debe buscar todos los grupos aunque no
        // pertenezcan al tronco comun
        if (idPrograma == 37) {
            queryBase = "select count(*) from reporteavancecontenidotematico where  UnidadAprendizaje_imparte_profesor_UIPid in "
                    + "(select UIPid from  unidadaprendizaje_imparte_profesor as uaip join areaconocimiento_has_unidadaprendizaje as ahu "
                    + " on ahu.UnidadAprendizaje_UAPid = uaip.UnidadAprendizaje_UAPid and \n"
                    + "ahu.AreaConocimiento_ACOid = " + idArea + "  and uaip.CicloEscolar_CESid=" + idCicloEscolar + " ) ";
        } else {
            queryBase = "select count(*) from reporteavancecontenidotematico where  UnidadAprendizaje_imparte_profesor_UIPid in "
                    + "(select UIPid from  unidadaprendizaje_imparte_profesor as uaip join areaconocimiento_has_unidadaprendizaje as ahu "
                    + " on ahu.UnidadAprendizaje_UAPid = uaip.UnidadAprendizaje_UAPid and \n"
                    + "ahu.AreaConocimiento_ACOid = " + idArea + " and uaip.Grupo_GPOid in (select GPOid from grupo where PlanEstudio_PESid in (\n"
                    + "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid "
                    + "and pe.PEDid = " + idPrograma + " )) and uaip.CicloEscolar_CESid=" + idCicloEscolar + " ) ";
        }

        // si no es para todos los ract agrego validacion del numero de  ract al query
        if (!noRact.equalsIgnoreCase("4")) {
            queryBase = queryBase + " and RACnumero= '" + noRact + "' ";

        } else {
            // si es para todos multiplico los esperados x3 para el calculo posterior
            esperados *= 3;
        }

        // segun la busqueda agrego validaciones al query
        switch (criterio) {
            case "entregados":
                // estare agregando ; para que no marque error al ejecutar el query
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;
            case "noentregados":
                // para no entregados resto los esperados menos los entregados para eso ocupo la variable
                queryBase = queryBase + (" ;");
                int e = this.executeCountQuery(queryBase);
                entregados = esperados - e;
                break;
            case "entregadosynoentregados":
                // este igual trae todos
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                //queryBase = queryBase + (" and RACavanceGlobal !=100 ;");
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                int ent = this.executeCountQuery(queryBase);
                entregados = esperados - ent;
                break;

            case "Porcentaje de Avance Global Completo e Incompleto":
                // este es igual de entregados, investigar si se puede eliminar esta opcion 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;
        }
//        System.out.println(entregados + "¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿ entregados");
        return entregados;
    }

    /**
     * *
     * MEtodo para obtener los entregados segun el criterio por area Admin si es
     * tronco comun incluira grupos que no son de tronco comun
     *
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idArea
     * @return
     */
    public int conteoPorAreaAdministrativa(String criterio, int idPrograma, String noRact, int idCicloEscolar, int esperados, int idArea) {
        int entregados = 0;
        String queryBase = "";
        // cuando es tronco comun debe buscar todos los grupos aunque no
        // pertenezcan al tronco comun
        if (idPrograma == 37) {
            queryBase = "select count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in"
                    + " ( select uaip.UIPid from unidadaprendizaje_imparte_profesor \n"
                    + "as uaip join areaadministrativa_tiene_unidadaprendizaje as aa join coordinadorareaadministrativa as caa \n"
                    + "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.AreaAdministrativa_AADid=" + idArea + " and "
                    + "uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid and uaip.CicloEscolar_CESid=" + idCicloEscolar + " ) ";
        } else {
            queryBase = "select count(*)  from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in"
                    + " ( select uaip.UIPid from unidadaprendizaje_imparte_profesor \n"
                    + "as uaip join areaadministrativa_tiene_unidadaprendizaje as aa join coordinadorareaadministrativa as caa \n"
                    + "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.AreaAdministrativa_AADid=" + idArea + " and "
                    + "uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid and uaip.Grupo_GPOid in \n"
                    + "(select GPOid from grupo where PlanEstudio_PESid in (\n"
                    + "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid =" + idPrograma + " ))"
                    + " and uaip.CicloEscolar_CESid=" + idCicloEscolar + " ) ";
        }

        // si no es para todos los ract agrego validacion del numero de  ract al query
        if (!noRact.equalsIgnoreCase("4")) {
            queryBase = queryBase + " and RACnumero= '" + noRact + "' ";

        } else {
            // si es para todos multiplico los esperados x3 para el calculo posterior
            esperados *= 3;
        }

        // segun la busqueda agrego validaciones al query
        switch (criterio) {
            case "entregados":
                // estare agregando ; para que no marque error al ejecutar el query
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;
            case "noentregados":
                // para no entregados resto los esperados menos los entregados para eso ocupo la variable
                queryBase = queryBase + (" ;");
                int e = this.executeCountQuery(queryBase);
                entregados = esperados - e;
                break;
            case "entregadosynoentregados":
                // este igual trae todos
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Incompleto":
                //Metodo Francisco 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                int ent = this.executeCountQuery(queryBase);
                if(!noRact.equalsIgnoreCase("4")){
                    entregados = esperados - ent;
                }
                else{
                    entregados = (esperados/3) - ent;
                }
                // agrego validacion de porcenteaje incompleto al query base
//                queryBase = queryBase + (" and RACavanceGlobal !=100 ;");
//                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo e Incompleto":
                entregados = 0;
                // este es igual de entregados, investigar si se puede eliminar esta opcion 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;
        }
        return entregados;
    }

    /**
     * *
     * Metodo para traer el conteo de racts segun el criterio por unidad de
     * aprendizaje
     *
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idUnidad
     * @return
     */
    public int ConteoPorUnidadAprendizaje(String criterio, int idPrograma, String noRact, int idCicloEscolar, int esperados, int idUnidad) {
        int entregados = 0;
        String queryBase = "";
        // cuando es tronco comun debe buscar todos los grupos aunque no
        // pertenezcan al tronco comun
        if (idPrograma == 37) {
            queryBase = "select count(*)  from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in "
                    + "(select UIPid from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " + idUnidad + " \n"
                    + " and CicloEscolar_CESid =" + idCicloEscolar + " )";
        } else {
            queryBase = "select count(*)  from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in"
                    + " (select UIPid from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = " + idUnidad + " \n"
                    + " and Grupo_GPOid in (select GPOid from grupo where PlanEstudio_PESid in (select planestudio.PESid from "
                    + " programaeducativo as pe join planestudio on \n"
                    + " pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid =" + idPrograma + " )) and CicloEscolar_CESid =" + idCicloEscolar + " ) ";
        }

        // si no es para todos los ract agrego validacion del numero de  ract al query
        if (!noRact.equalsIgnoreCase("4")) {
            queryBase = queryBase + " and RACnumero= '" + noRact + "' ";

        } else {
            // si es para todos multiplico los esperados x3 para el calculo posterior
            esperados *= 3;
        }

        // segun la busqueda agrego validaciones al query
        switch (criterio) {
            case "entregados":
                // estare agregando ; para que no marque error al ejecutar el query
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;
            case "noentregados":
                // para no entregados resto los esperados menos los entregados para eso ocupo la variable
                queryBase = queryBase + (" ;");
                int e = this.executeCountQuery(queryBase);
                entregados = esperados - e;
                break;
            case "entregadosynoentregados":
                // este igual trae todos
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Incompleto":
                //Metodo Francisco
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                int ent = this.executeCountQuery(queryBase);
                entregados = esperados - ent;
                // agrego validacion de porcenteaje incompleto al query base
//                queryBase = queryBase + (" and RACavanceGlobal !=100 ;");
//                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo e Incompleto":
                entregados = 0;
                // este es igual de entregados, investigar si se puede eliminar esta opcion 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;
        }
        return entregados;

    }

    /**
     * *
     * Metodo para traer conteo de racts segun el criterio, si isAdmin is true
     * contara todos los grupos que le pertencen al profesor de lo contrario
     * solo contara los grupos del programa educativo
     *
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param isAdmin
     * @param idProfesor
     * @return
     */
    public int ConteoPorProfesor(String criterio, int idPrograma, String noRact, int idCicloEscolar, int esperados, boolean isAdmin, int idProfesor) {
        int entregados = 0;
        String queryBase = "";
        // cuando es tronco comun debe buscar todos los grupos aunque no
        // pertenezcan al tronco comun
        if (isAdmin) {
            queryBase = "select count(*)  from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in\n"
                    + "  ( select UIPid from unidadaprendizaje_imparte_profesor where Profesor_PROid = " + idProfesor + " and CicloEscolar_CESid = " + idCicloEscolar + " )";
        } else {
            queryBase = "select count(*)  from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid "
                    + "in (select UIPid from unidadaprendizaje_imparte_profesor where Profesor_PROid = " + idProfesor + " and  \n"
                    + " Grupo_GPOid in (select GPOid from grupo where PlanEstudio_PESid "
                    + "in (select planestudio.PESid from  programaeducativo as pe \n"
                    + " join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid =" + idPrograma + " )) "
                    + "and CicloEscolar_CESid =" + idCicloEscolar + " )";
        }

        // si no es para todos los ract agrego validacion del numero de  ract al query
        if (!noRact.equalsIgnoreCase("4")) {
            queryBase = queryBase + " and RACnumero= '" + noRact + "' ";

        } else {
            // si es para todos multiplico los esperados x3 para el calculo posterior
            esperados *= 3;
        }

        // segun la busqueda agrego validaciones al query
        switch (criterio) {
            case "entregados":
                // estare agregando ; para que no marque error al ejecutar el query
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;
            case "noentregados":
                // para no entregados resto los esperados menos los entregados para eso ocupo la variable
                queryBase = queryBase + (" ;");
                int e = this.executeCountQuery(queryBase);
                entregados = esperados - e;
                break;
            case "entregadosynoentregados":
                // este igual trae todos
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Incompleto":
                //Metodo Francisco
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                int ent = this.executeCountQuery(queryBase);
                entregados = esperados - ent;
                // agrego validacion de porcenteaje incompleto al query base
//                queryBase = queryBase + (" and RACavanceGlobal !=100 ;");
//                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo e Incompleto":
                entregados = 0;
                // este es igual de entregados, investigar si se puede eliminar esta opcion 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;
        }
        return entregados;

    }

    /**
     * Conteo de reporte entregados por profesor que pertenecen al area
     * administrativa que coordina el profesor
     *
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idProfesorCoordinador
     * @param idProfesor
     * @return
     */
    public int conteoPorProfesorCoordinador(String criterio, int idPrograma, String noRact, int idCicloEscolar, int esperados, int idProfesorCoordinador, int idProfesor) {

        int entregados = 0;
        String queryBase = "";
        // cuando es tronco comun debe buscar todos los grupos aunque no
        // pertenezcan al tronco comun

        queryBase = "select count(*) from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in (select uaip.UIPid from unidadaprendizaje_imparte_profesor as uaip \n"
                + "join areaadministrativa_tiene_unidadaprendizaje as aa join coordinadorareaadministrativa as caa \n"
                + "on aa.CoordinadorAdministrativa_COAid = caa.COAid and caa.Profesor_PROid=" + idProfesorCoordinador + " and uaip.UnidadAprendizaje_UAPid = aa.UnidadAprendizaje_UAPid and uaip.Grupo_GPOid in \n"
                + "(select GPOid from grupo where PlanEstudio_PESid in (\n"
                + "select planestudio.PESid from  programaeducativo as pe join planestudio on pe.PEDid = planestudio.ProgramaEducativo_PEDid and pe.PEDid =" + idPrograma + " ))\n"
                + " and uaip.Profesor_PROid = " + idProfesor + " and uaip.CicloEscolar_CESid =" + idCicloEscolar + " )";

        // si no es para todos los ract agrego validacion del numero de  ract al query
        if (!noRact.equalsIgnoreCase("4")) {
            queryBase = queryBase + " and RACnumero= '" + noRact + "' ";

        } else {
            // si es para todos multiplico los esperados x3 para el calculo posterior
            esperados *= 3;
        }
        entregados = agregarCriterioQuery(queryBase, esperados, criterio);
        return entregados;
    }

    /**
     * Este metodo agrega el criterio al query segun lo recibido y ademas lo
     * ejecuta
     *
     * @param queryBase
     * @param esperados
     * @param criterio
     * @return el conteo de esperados segun el query recibido y el criterio
     */
    public int agregarCriterioQuery(String queryBase, int esperados, String criterio) {
        int entregados = 0;
        switch (criterio) {
            case "entregados":
                // estare agregando ; para que no marque error al ejecutar el query
                queryBase = queryBase + (" ;");
                entregados = this.executeCountQuery(queryBase);
                break;
            case "noentregados":
                // para no entregados resto los esperados menos los entregados para eso ocupo la variable
                queryBase = queryBase + (" ;");
                int e = this.executeCountQuery(queryBase);
                entregados = esperados - e;
                break;
            case "entregadosynoentregados":
                // este igual trae todos
                queryBase = queryBase + (";");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo":
                // agrego validacion de porcenteaje completo al query base
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Incompleto":
                // agrego validacion de porcenteaje incompleto al query base
                queryBase = queryBase + (" and RACavanceGlobal !=100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;

            case "Porcentaje de Avance Global Completo e Incompleto":
                // este es igual de entregados, investigar si se puede eliminar esta opcion 
                queryBase = queryBase + (" and RACavanceGlobal =100 ;");
                entregados = this.executeCountQuery(queryBase);
                break;
        }
        return entregados;
    }

    /**
     * Este metodo inicia una transaccion y guarda el RACT sin el detalle
     *
     * @param obj
     */
    @Override
    public void save(Reporteavancecontenidotematico obj) {
        System.out.println("Save ----------");
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        HibernateUtil.getSession().save(obj);
    }

    /**
     * Este metodo inicia una transaccion y actualiza un RACT sin el detalle
     *
     * @param obj
     */
    @Override
    public void update(Reporteavancecontenidotematico obj) {
        System.out.println("update ----------");
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        HibernateUtil.getSession().update(obj);

    }
    

    public List<BigInteger> StoredProcedureReporteAvanceContenidoTematico(String procedure){
        System.out.println("Save ----------");
        List<BigInteger> result = null;
        try {
            HibernateUtil.getSession();
            System.out.println("[PRO]GetSession");
            HibernateUtil.beginTransaccion();
            Query miquery = HibernateUtil.getSession().createSQLQuery(procedure);
            result = miquery.list();
            return result;  
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

     
     public List<Reporteavancecontenidotematico> executeProcedureTest(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Reporteavancecontenidotematico> result;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        Query lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Reporteavancecontenidotematico.class);
         result = lQuery.list();
         return result;
    }

    /**
     * Este metodo termina una transaccion si recibe un true hace commit Si
     * recibe un false hace un rollback
     *
     * @param ban
     */
    public void endTransaction(boolean ban) {
        if (ban == false) {
            HibernateUtil.rollbackTransaction();
        }
        HibernateUtil.commitTransaction();
      HibernateUtil.closeSession();
    }
    
    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }
}
