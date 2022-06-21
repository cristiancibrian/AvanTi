/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author Adriana
 */
public class AlumnoDAO extends AbstractDAO<Integer, Alumno> {

   
    public List<Alumno> findalumnos() {
        String sql = "SELECT * FROM alumno";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Alumno.class);

        return (List<Alumno>) query.list();
    }
    public List<String> findalumnosnombre() {
        String sql = "SELECT ALnombre FROM alumno";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);

        return (List<String>) query.list();
    }
    public List<String> findalumnoscorreo() {
        String sql = "SELECT correo FROM alumno";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);

        return (List<String>) query.list();
    }
    public Alumno findalumnopormatricula(int x) {
        String sql = "SELECT * FROM alumno WHERE ALmatricula="+x;
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.addEntity(Alumno.class);

        return (Alumno) query.uniqueResult();
    }
    public List<String> findalumnosALmatricula() {
        String sql = "SELECT findalumnosALmatricula FROM alumno";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);

        return (List<String>) query.list();
    }
    public List<Alumno> ExecuteProcedureConsultaAlumno(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<Alumno> result;
        HibernateUtil.getSession();
        Query lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(Alumno.class);
         result = lQuery.list();
         return result;
    }
}
