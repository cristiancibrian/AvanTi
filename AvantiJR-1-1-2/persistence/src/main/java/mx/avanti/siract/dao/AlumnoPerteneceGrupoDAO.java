/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.AlumnoPerteneceGrupo;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
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
    
}
