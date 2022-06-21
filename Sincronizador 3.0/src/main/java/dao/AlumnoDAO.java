package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Alumno;
import org.hibernate.SQLQuery;


public class AlumnoDAO extends AbstractDAO<Integer, Alumno> {

    public Alumno getAlumnoByMatricula(int matricula) {
        Alumno result = null;
        result = this.executeQueryUnique("Select * from Alumno where ALMatricula = " + matricula + ";");
        return result;
    }
    
    public Alumno getAlumnoByMatricula(int matricula, int USUid){
        Alumno result = null;
        result = executeQueryUnique("Select * from Alumno where ALMatricula = " + matricula + " and Usuario_USUid = " + USUid + ";");
        return result;
    }
}
