/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Memo
 */
public class ProfesorPertenecePEDAO extends AbstractDAO<Integer, ProfesorPerteneceProgramaeducativo> {
    public void endTransaction(boolean completo){
        if(completo){
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }else
            HibernateUtil.rollbackTransaction();
    }
    
    public List<ProfesorPerteneceProgramaeducativo> executeProcedure(String procedure){
        System.out.println("ExecuteProcedure ----------");
        System.out.println("+++++++++ Ejecutamos: " + procedure);
        List<ProfesorPerteneceProgramaeducativo> result = new ArrayList();
        try{
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            Query iQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(ProfesorPerteneceProgramaeducativo.class);
            result = iQuery.list();
        }catch(HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("+++++++++ Devolvemos: " + result.toString());
        return result;
    }

}
