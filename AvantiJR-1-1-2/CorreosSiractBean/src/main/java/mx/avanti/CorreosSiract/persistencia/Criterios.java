/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.CorreosSiract.persistencia;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.dao.CicloEscolarDAO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Eduardo
 */
public class Criterios {

    BaseDAO baseDAO = new BaseDAO();
    
    
    public List<CalendarioreporteTieneAlerta> findAllCTA(int creId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(CalendarioreporteTieneAlerta.class);
        criteria.add(Restrictions.eq("calendarioreporte.creid", creId));
        List<CalendarioreporteTieneAlerta> ctas = criteria.list();
        session.close();
        return ctas;
    }

    public Cicloescolar cesCriteria(String ciclo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Cicloescolar.class);
        criteria.add(Restrictions.eq("cicloescolar.CEScicloEscolar",  ciclo));
        System.out.println(String.valueOf(ciclo));
        
        Cicloescolar c = (Cicloescolar) criteria.uniqueResult();
        //Cicloescolar c = (Cicloescolar) baseDAO.cicloEscolarActual();
        session.close();
        return c;
    }

    public Configuracion conCriteria(int cicloId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Configuracion.class);
        criteria.add(Restrictions.eq("cicloescolar.cesid", cicloId));
        Configuracion configuracion = (Configuracion) criteria.uniqueResult();
        session.close();
        return configuracion;
    }

    public List<Calendarioreporte> findAllCre(int conId) {
        List<Calendarioreporte> calendarioreportes = new ArrayList<Calendarioreporte>();
        String sql = "select * from calendarioreporte where CREid in (select CalendarioReporte_CREid from configuracion_tiene_calendarioreporte where Configuracion_CONid = " + conId + ");";
        Session session = HibernateUtil.getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Calendarioreporte.class);
        calendarioreportes = query.list();
        session.close();
        return calendarioreportes;
    }

    public Mensaje menCriteria(int menid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Mensaje.class);
        criteria.add(Restrictions.eq("menid", menid));
        Mensaje mensaje = (Mensaje) criteria.uniqueResult();
        session.close();
        return mensaje;
    }

    public Usuario usuCriteria(int usuid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("usuid", usuid));
        Usuario usuario = (Usuario) criteria.uniqueResult();
        session.close();
        return usuario;
    }

    public Programaeducativo pedCriteria(int pedid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Programaeducativo.class);
        criteria.add(Restrictions.eq("pedid", pedid));
        Programaeducativo ped = (Programaeducativo) criteria.uniqueResult();
        session.close();
        return ped;
    }

    public List<Profesor> proByPedCriteria(Programaeducativo ped) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Profesor.class, "profesor");
        criteria.createAlias("profesor.profesorPerteneceProgramaeducativos", "programaeducativo");
        criteria.add(Restrictions.eq("profesor.proid", ped.getPEDid()));
        List<Profesor> listaProfesor = criteria.list();
        session.close();
        return listaProfesor;
    }

    public List<Profesor> consultaProfPE(int peid) {
        List<Profesor> listaPE = null;
        baseDAO.setTipo(Profesor.class);
        listaPE = baseDAO.findFromWhere("programaeducativos", "pedid", String.valueOf(peid));
        return listaPE;
    }

    public List<Profesor> proByUAC(int pedid) {
        List<Profesor> listaPE = null;
        baseDAO.setTipo(Profesor.class);
        listaPE = baseDAO.findFromWhere("unidadacademicas", "uacid", String.valueOf(pedid));
        return listaPE;
    }

    public List<Profesor> findProfesorPertenecePE(int pedid) {
        List<Profesor> result = null;
        baseDAO.setTipo(Profesor.class);
        result = baseDAO.findFromWhere("profesorPerteneceProgramaeducativos", "programaeducativo.pedid", String.valueOf(pedid));
        return result;
    }

    public List<Profesor> findProfesorPertenecePEQuery() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Profesor> result = null;
        baseDAO.setTipo(Profesor.class);
        //String asd = "select * from profesor where proid in (SELECT Profesor_PROid FROM siract.profesor_pertenece_programaeducativo where (ProgramaEducativo_PEDid = 26 or ProgramaEducativo_PEDid = 37) and CicloEscolar_CESid=17)";
//         String asd = "select * from profesor where PROid in(select Profesor_PROid from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid \n" +
//"in(SELECT UnidadAprendizaje_UAPid FROM siract.areaconocimiento_has_unidadaprendizaje where AreaConocimiento_ACOid \n" +
//"in(SELECT ACOid FROM siract.areaconocimiento where PlanEstudio_PESid = 18 or PlanEstudio_PESid = 7 or PlanEstudio_PESid = 19\n" +
//")) and CicloEscolar_CESid = 18);";
String asd = "select * from profesor where PROid in (\n" +
"select distinct uaip.Profesor_PROid from unidadaprendizaje_imparte_profesor as uaip join unidadaprendizaje_tiene_contenidotematico as uatct \n" +
"on uaip.UnidadAprendizaje_UAPid = uatct.UnidadAprendizaje_UAPid and \n" +
"((uaip.UIPtipoSubgrupo = 'PC' and uatct.UAPhorasCampoCompletas = 1 ) or\n" +
"(uaip.UIPtipoSubgrupo = 'C' and uatct.UAPhorasClaseCompletas = 1 )or\n" +
"(uaip.UIPtipoSubgrupo = 'L' and uatct.UAPhorasLaboratorioCompletas = 1 )or\n" +
"(uaip.UIPtipoSubgrupo = 'T' and uatct.UAPhorasTallerCompletas = 1)) and uaip.CicloEscolar_CESid = 19 and uaip.Grupo_GPOid in \n" +
"(select grupo.GPOid from grupo where PlanEstudio_PESid = 18 or PlanEstudio_PESid = 7 or PlanEstudio_PESid = 19\n" +
" or PlanEstudio_PESid = 10 or PlanEstudio_PESid = 20)\n" +
" )";

/*YA NO SIRVE ES DE LA VERSION DE SIRACT VIEJA LA OLDIE, la raiz. 
         String asd = "select * from profesor where PROid in(\n" +
"select Profesor_PROid from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid\n" +
" in (select  UnidadAprendizaje_UAPid from unidadaprendizaje_imparte_profesor where\n" +
" ( UIPtipoSubgrupo='C'  and  UnidadAprendizaje_UAPid in\n" +
" (Select  UAPid from  unidadaprendizaje where UAPhorasClaseCompletas =1 )\n" +
"  or UIPtipoSubgrupo='L' and  UnidadAprendizaje_UAPid in\n" +
" (Select  UAPid from  unidadaprendizaje where UAPhorasLaboratorioCompletas =1 )\n" +
" or UIPtipoSubgrupo='T' and  UnidadAprendizaje_UAPid in\n" +
" (Select  UAPid from  unidadaprendizaje where UAPhorasTallerCompletas =1 ))) and\n" +
" UnidadAprendizaje_UAPid\n" +
" in(SELECT UnidadAprendizaje_UAPid FROM siract.areaconocimiento_has_unidadaprendizaje where AreaConocimiento_ACOid\n" +
" in(SELECT ACOid FROM siract.areaconocimiento where PlanEstudio_PESid = 18 or PlanEstudio_PESid = 7 or PlanEstudio_PESid = 19\n" +
" or PlanEstudio_PESid = 10\n" +
" )) and CicloEscolar_CESid = 18)"; */
        Query query = session.createSQLQuery(asd).addEntity(Profesor.class);
        result = query.list();
        return result;
    }

    public List<Profesor> findProfesorRACTSiQuery(int ractNumero, String fechaInical, String fechaFinal, int cicloEscolar) { //trae los profesores que si entregaron el ract
        System.out.println(ractNumero+" - "+fechaInical+" - "+fechaFinal+" - "+cicloEscolar);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UnidadaprendizajeImparteProfesor> result = null;
        List<Profesor> resultado = new ArrayList<Profesor>();
        baseDAO.setTipo(UnidadaprendizajeImparteProfesor.class);
        String asd = "Select * from unidadaprendizaje_imparte_profesor where (uipid not in (SELECT unidadaprendizaje_imparte_profesor_uipid FROM siract.reporteavancecontenidotematico where ((RACnumero = '"+ractNumero+"') and (RACfechaElaboracion between '"+fechaInical+"' and '"+fechaFinal+"')) and RACstatus='enviado') and CicloEscolar_CESid="+cicloEscolar+" and (Grupo_GPOid in (select gpoid from grupo where PlanEstudio_PESid = 7 or PlanEstudio_PESid = 18 or PlanEstudio_PESid = 10))) order by Profesor_PROid;";
        //Query query = session.createSQLQuery(asd).addEntity(UnidadaprendizajeImparteProfesor.class);
        //result = query.list();
        result = ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().executeQuery(asd);
        for (UnidadaprendizajeImparteProfesor uip : result) {
            resultado.add(uip.getProfesorPROid());
        }
        Set<Profesor> hs = new HashSet<>();
        hs.addAll(resultado);
        resultado.clear();
        resultado.addAll(hs);
        System.out.println("entregaron: " + resultado.size());
        return resultado;
    }

//    public List<Profesor> findProfesorRACTSiQuery() { //trae los profesores que si entregaron el ract
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<UnidadaprendizajeImparteProfesor> result = null;
//        List<Profesor> resultado = new ArrayList<Profesor>();
//        baseDAO.setTipo(UnidadaprendizajeImparteProfesor.class);
//        String asd = "Select * from unidadaprendizaje_imparte_profesor where (uipid not in (SELECT unidadaprendizaje_imparte_profesor_uipid FROM siract.reporteavancecontenidotematico where ((RACnumero = '1') and (RACfechaElaboracion between '2017-02-10' and '2017-03-30')) and RACstatus='enviado') and CicloEscolar_CESid=14 and (Grupo_GPOid in (select gpoid from grupo where PlanEstudio_PESid = 7 or PlanEstudio_PESid = 18))) order by Profesor_PROid;";
//        Query query = session.createSQLQuery(asd).addEntity(UnidadaprendizajeImparteProfesor.class);
//        result = query.list();
//        for (UnidadaprendizajeImparteProfesor uip : result) {
//            resultado.add(uip.getProfesor());
//        }
//        Set<Profesor> hs = new HashSet<>();
//        hs.addAll(resultado);
//        resultado.clear();
//        resultado.addAll(hs);
//        System.out.println("entregaron: " + resultado.size());
//        return resultado;
//    }
    
    public Profesor proCriteria(int numEmp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Profesor.class);
        criteria.add(Restrictions.eq("pronumeroEmpleado", numEmp));
        Profesor p = (Profesor) criteria.uniqueResult();
        return p;
    }
//SELECT * FROM siract.profesor_pertenece_programaeducativo where (ProgramaEducativo_PEDid = 26 or ProgramaEducativo_PEDid = 37) and CicloEscolar_CESid=13;
}
