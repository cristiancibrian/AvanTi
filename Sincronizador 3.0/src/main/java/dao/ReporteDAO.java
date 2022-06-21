/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Reporte;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * @author Alex
 */
public class ReporteDAO extends AbstractDAO<Integer, Reporte> {
    /*
    /**
     * Metodo agregado para validar si una unidad es seleccionada en el ultimo 
     * ract debido a que antes se perdian dichas unidades de ract 2 a ract 3
     * 
     * @param idNodo el nodo seleccionado 
     * @param tipoNodo si es unidad, Tema, Subtema, practicas
     * @param reporteActual que reporte esas buscando
     * @param idUaip
     * @return Lista de los reportes de unidad correspondientes al id ordenados
     * de mas viejo a mas reciente 
     *
    public List<Reporte> UltimoReporteUnidad( int idNodo, String tipoNodo,int reporteActual, int idUaip){
        return this.executeQuery("select distinct * " +
                "from reporte r inner join reporteavancecontenidotematico re where r.TUNid=0 " +
                "and r.SUTid=0 and re.UnidadAprendizaje_imparte_profesor_UIPid="+idUaip+ " and r.UNIid= " +idNodo+
                " order by REPid desc");
    }
    
    /**
     * Metodo agregado para validar si una TemaUnidad es seleccionada 
     * en el ultimo ract debido a que antes se perdian dichos 
     * TemaUnidad de ract 2 a ract 3
     * 
     * @param idNodo el nodo seleccionado 
     * @param tipoNodo si es unidad, Tema, Subtema, practicas
     * @param reporteActual que reporte esas buscando
     * @param idUaip
     * @return Lista de los reportes de unidad correspondientes al id ordenados
     * 
    public List<Reporte> UltimoReporteTemaUnidad(int idNodo, String tipoNodo,int reporteActual, int idUaip ){
        return this.executeQuery("select distinct *  "
           +"from reporte r inner join reporteavancecontenidotematico re where r.TUNid="+ idNodo +
            " and r.SUTid=0 and re.UnidadAprendizaje_imparte_profesor_UIPid= " + idUaip+
            " order by REPid desc");
    }
    
    /**
     * Metodo agregado para validar si una SubTemaUnidad es seleccionada 
     * en el ultimo ract debido a que antes se perdian dichos 
     * SubTemaUnidad de ract 2 a ract 3
     * 
     * @param idNodo el nodo seleccionado 
     * @param tipoNodo si es unidad, Tema, Subtema, practicas
     * @param reporteActual que reporte esas buscando
     * @param idUaip
     * @return Lista de los reportes de unidad correspondientes al id ordenados
     * 
    public List<Reporte> UltimoReporteSubTemaUnidad(int idNodo, String tipoNodo,int reporteActual, int idUaip ){
       return this.executeQuery("select distinct * "
           + "from reporte r inner join reporteavancecontenidotematico re where " +
            " r.SUTid= "+ idNodo +  " and re.UnidadAprendizaje_imparte_profesor_UIPid=" +idUaip +
            " order by REPid desc");
    }
    
    /**
     * Metodo agregado para validar si una PracticaTaller es seleccionada 
     * en el ultimo ract debido a que antes se perdian dichos 
     * PracticaTaller de ract 2 a ract 3
     * 
     * @param idNodo el nodo seleccionado 
     * @param tipoNodo si es unidad, Tema, Subtema, practicas
     * @param reporteActual que reporte esas buscando
     * @param idUaip
     * @return Lista de los reportes de unidad correspondientes al id ordenados
     * 
    public List<Reporte> UltimoPracticasTaller(int idNodo, String tipoNodo,int reporteActual, int idUaip ){
        return this.executeQuery("select distinct * "+
                " from reporte r inner join reporteavancecontenidotematico re where " +
                 " r.PRTid="+idNodo +" and re.UnidadAprendizaje_imparte_profesor_UIPid=" + idUaip +
                  " order by REPid desc");
    }
    
    /**
     * Metodo agregado para validar si una PracticaCampo es seleccionada 
     * en el ultimo ract debido a que antes se perdian dichos 
     * PracticaCampo de ract 2 a ract 3
     * 
     * @param idNodo el nodo seleccionado 
     * @param tipoNodo si es unidad, Tema, Subtema, practicas
     * @param reporteActual que reporte esas buscando
     * @param idUaip
     * @return Lista de los reportes de unidad correspondientes al id ordenados
     * 
    public List<Reporte> UltimoReportePracticasCampo( int idNodo, String tipoNodo,int reporteActual, int idUaip){
        return this.executeQuery("select distinct * "+
            " from reporte r inner join reporteavancecontenidotematico re where " +
            " r.PRCid="+idNodo +" and re.UnidadAprendizaje_imparte_profesor_UIPid=" + idUaip +
            " order by REPid desc");  
    }
    
       /**
     * Metodo agregado para validar si una PracticaLab es seleccionada 
     * en el ultimo ract debido a que antes se perdian dichos 
     * PracticaLab de ract 2 a ract 3
     * 
     * @param idNodo el nodo seleccionado 
     * @param tipoNodo si es unidad, Tema, Subtema, practicas
     * @param reporteActual que reporte esas buscando
     * @param idUaip id de la unidad de aprendizaje
     * @return Lista de los reportes de unidad correspondientes al id ordenados
     * 
    public List<Reporte> UltimoReportePracticasLaboratorio( int idNodo, String tipoNodo,int reporteActual, int idUaip){
        return this.executeQuery("select distinct * "+
            " from reporte r inner join reporteavancecontenidotematico re where " +
            " r.PRLid="+idNodo +" and re.UnidadAprendizaje_imparte_profesor_UIPid=" + idUaip +
             " order by REPid desc");
    }
    
    /**
     * Metodo para buscar los reportes correspendientes al id del ract
     * para hacer validaciones de guardado; no se uso el find porque todos los
     * reportes del mismo ract tienen el mismo id entonces opte por usar un 
     * query 
     * @param id del ract correspondiente 
     * @return 
     *
    public List<Reporte> consultarReportesPorID(int id){
        return this.executeQuery("select * from siract.reporte where REPid = " + id);
    }
    
    

    */

    /**
     * Obtiene los reportes actuales y anteriores de
     * una unidad aprendizaje imparte profesor
     * @param tipoGrupo el tipo de grupo (C, T, L)
     * @param ce ciclo escolar
     * @param ractNumero numero del ract
     * @param uipId id de unidad de aprendizaje imparte profesor
     * @return
     */

    /**
     * Metodo que retorna los ultimos reportes actuales y anteriores
     *
     * @param tipoGrupo
     * @param ce
     * @param ractNumero
     * @param uipId
     * @return
     */
    public List<Reporte> getLatest(String tipoGrupo, String ce, String ractNumero, int uipId) {
        //configuracion inicial del query
        String sql = "SELECT rep.* FROM reporte rep "
                + "INNER JOIN %s "
                + "INNER JOIN reporteavancecontenidotematico ract ON ract.RACid = rep.ReporteAvanceContenidoTematico_RACid "
                + "INNER JOIN unidadaprendizaje_imparte_profesor uaip ON uaip.UIPid = ract.UnidadAprendizaje_imparte_profesor_UIPid "
                + "WHERE uaip.CicloEscolar_CESid = :cesid "
                + "AND uaip.UIPid = :uapid "
                + "AND CONVERT(ract.RACnumero, UNSIGNED INTEGER) <=:ractnumero "
                + "AND ract.RACstatus = 'Enviado' "
                + "ORDER BY %s ";

        String joinTipoGrupo = "";
        String orderBy = "";
        //configuracion del query dependiendo tipo de grupo
        switch (tipoGrupo) {
            case "C": //Clase
                joinTipoGrupo = "unidad uni ON uni.UNIid = rep.UNIid";
                orderBy = "uni.UNInumero, rep.UNIid, rep.TUNid, rep.SUTid, ract.RACnumero";
                break;
            case "T": //Taller
                joinTipoGrupo = "practicataller taller ON taller.PRTid = rep.PRTid";
                orderBy = "taller.PRTnumero, ract.RACnumero";
                break;
            case "L": //Laboratorio
                joinTipoGrupo = "practicalaboratorio lab ON lab.PRLid = rep.PRLid";
                orderBy = "lab.PRLnumero, ract.RACnumero";
                break;
            case "P":
                break;
        }

        sql = String.format(sql, joinTipoGrupo, orderBy);
        SQLQuery sqlQuery = HibernateUtil.getSession().createSQLQuery(sql);
        sqlQuery.addEntity(Reporte.class);
        sqlQuery.setString("cesid", ce);
        sqlQuery.setInteger("uapid", uipId);
        sqlQuery.setInteger("ractnumero", Integer.parseInt(ractNumero) + 1);

        // List<Reporte> lista = this.executeQuery(sqlQuery.toString());
        return (List<Reporte>) sqlQuery.list();
        // return lista;
    }

    /**
     * ??????
     */
    public void doSomething() {

    }
}
