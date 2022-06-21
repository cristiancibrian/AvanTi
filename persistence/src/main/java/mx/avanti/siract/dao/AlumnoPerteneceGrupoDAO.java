/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.AlumnoPerteneceGrupo;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author ferna
 */
public class AlumnoPerteneceGrupoDAO extends AbstractDAO<Integer, AlumnoPerteneceGrupo>{
   
    
    public List<AlumnoPerteneceGrupo> findalumnogrupos(){
        String sql = "SELECT * FROM alumno_pertenece_grupo";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(AlumnoPerteneceGrupo.class);
        return(List<AlumnoPerteneceGrupo>) query.list();
    }
    
    public List<AlumnoPerteneceGrupo> findalumnoporgrupos(int grupo){
        String sql = "SELECT * FROM alumno_pertenece_grupo WHERE GPOid= "+grupo;
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(AlumnoPerteneceGrupo.class);
        return(List<AlumnoPerteneceGrupo>) query.list();
    }
    
    public List<AlumnoPerteneceGrupo> executeProcedure(String procedure){
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<AlumnoPerteneceGrupo> result = new ArrayList();
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(AlumnoPerteneceGrupo.class);
            result = iQuery.list();
            //endTransaction(true);
        }catch(HibernateException e){
            System.out.println(e.getMessage());
            return null;
            //endTransaction(false);
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }
    
}
