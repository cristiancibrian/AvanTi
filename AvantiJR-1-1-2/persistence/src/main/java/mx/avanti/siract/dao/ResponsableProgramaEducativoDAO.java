package mx.avanti.siract.dao;

import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.ResponsableProgramaEducativo;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * @author Kevin Arizaga
 */
public class ResponsableProgramaEducativoDAO extends AbstractDAO<Integer, ResponsableProgramaEducativo> {
    private boolean secureTransaction;

    /**
     * Este método realiza una busqueda en la tabla de RPE por ID
     * donde utiliza cicleEscolarID como condicion, al final retorna una lista de RPE's,
     *
     * @param cicloEscolar
     * @return Lista de ResponsableProgramaEducativo
     */
    public List<ResponsableProgramaEducativo> findRPEbyCicloEscolarID(Cicloescolar cicloEscolar) {
//        String query = "SELECT * FROM responsableprogramaeducativo WHERE CESid = " + cicloEscolar.getCESid();
//        return executeQuery(query);
        List<ResponsableProgramaEducativo> result;
        result = executeProcedure("CALL findRPEbyCicloEscolarID (\'" + cicloEscolar.getCESid() + "\')");
        return result;
    }

    public ResponsableProgramaEducativo findRPEbyCicloAndProgramaEducativoID(Integer cicloEscolarID, Integer programaEducativoID) {
//        String query = "SELECT * FROM responsableprogramaeducativo WHERE CESid = " + cicloEscolarID
//                + " AND ProgramaEducativo_PEDid = " + programaEducativoID;
//        return executeQueryUnique(query);
        ResponsableProgramaEducativo result;
        result = executeProcedureOne("CALL findRPEbyCicloAndProgramaEducativoID (\'" + cicloEscolarID +
                "\', \'" + programaEducativoID + "\')");
        return result;
    }


    public List<ResponsableProgramaEducativo> findByProfesorID(Integer profesorID) {
//        String query = "SELECT * FROM responsableprogramaeducativo WHERE Profesor_PROid = " + profesorID;
//        return executeQuery(query);
        List<ResponsableProgramaEducativo> result;
        result = executeProcedure("CALL findByProfesorID (\'" + profesorID + "\')");
        return result;
    }

    public List<ResponsableProgramaEducativo> findByProgramaEducativo(Programaeducativo programaeducativo) {
//        String query = "SELECT * FROM responsableprogramaeducativo WHERE ProgramaEducativo_PEDid = " + programaeducativo.getPEDid();
//        return executeQuery(query);
        List<ResponsableProgramaEducativo> result;
        result = executeProcedure("CALL findByProgramaEducativo (\'" + programaeducativo.getPEDid() + "\')");
        return result;
    }

    public ResponsableProgramaEducativo findRpeByData(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
//        String query = "SELECT * FROM responsableprogramaeducativo WHERE Profesor_PROid = " + profesor.getPROid() +
//                " and ProgramaEducativo_PEDid = " + programaeducativo.getPEDid() +
//                " and CESid = " + cicloescolar.getCESid();
//        return executeQueryUnique(query);
        ResponsableProgramaEducativo result;
        result = executeProcedureOne("CALL findRpeByData (\'" + profesor.getPROid() + "\', \'" + programaeducativo.getPEDid() +
                "\', \'" + cicloescolar.getCESid() + "\')");
        return result;
    }

    public List<ResponsableProgramaEducativo> findAllRpeActual() {
//        String query = "SELECT * FROM siract.responsableprogramaeducativo where cesid = (Select max(CESid) from siract.responsableprogramaeducativo)";
//        return executeQuery(query);
        List<ResponsableProgramaEducativo> result;
        result = executeProcedure("CALL findAllRpeActual");
        return result;
    }

    public List<ResponsableProgramaEducativo> findRpeByProfesorAndProgramaEducativo(Profesor profesor, Programaeducativo programaeducativo) {
//        String query = "SELECT * FROM siract.responsableprogramaeducativo WHERE Profesor_PROid = " + profesor.getPROid() +
//                " AND ProgramaEducativo_PEDid = " + programaeducativo.getPEDid();
//        return executeQuery(query);
        List<ResponsableProgramaEducativo> result;
        result = executeProcedure("CALL findRPEByProfesorAndProgramaEducativo (\'" + profesor.getPROid() + "\'," +
                "\'" + programaeducativo.getPEDid() + "\')");
        return result;
    }

    public ResponsableProgramaEducativo findRpeRecentVersion(Integer prOid, Integer peDid) {
        ResponsableProgramaEducativo result = executeProcedureOne("CALL findRpeRecentVersion (\'" + prOid + "\', \'" + peDid + "\')");
        return result;
    }

    public ResponsableProgramaEducativo findActualRpeByProgramaEducativo(Programaeducativo programaEducativo) {
        ResponsableProgramaEducativo result = executeProcedureOne("CALL findActualRpeByProgramaEducativo(\'"+ programaEducativo.getPEDid() + "\')");
        return result;
    }

    public void deleteVersion(ResponsableProgramaEducativo responsableProgramaEducativo) {
        executeProcedureOne("CALL deleteVersion(\'" + responsableProgramaEducativo.getProfesor().getPROid()
                + "\', \'" + responsableProgramaEducativo.getProgramaeducativo().getPEDid()
                + "\', \'" + responsableProgramaEducativo.getCicloEscolar().getCESid()
                + "\', \'" + responsableProgramaEducativo.getVersion() + "\')");
    }

    public void secureSave(ResponsableProgramaEducativo rpe) {
        try {
            HibernateUtil.getSession().save(rpe);
        } catch (HibernateException e) {
            setSecureTransaction(false);
        }
    }

    public void iniciarTransaccion() {
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
    }

    public void finalizarTransaccion() {
        if (secureTransaction) {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        } else if (!secureTransaction) {
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
        }
        setSecureTransaction(true);
    }

    public void setSecureTransaction(boolean secureTransaction) {
        this.secureTransaction = secureTransaction;
    }

    private List<ResponsableProgramaEducativo> executeProcedure(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        List<ResponsableProgramaEducativo> result;
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(ResponsableProgramaEducativo.class);
        result = lQuery.list();
        return result;
    }

    private ResponsableProgramaEducativo executeProcedureOne(String procedure) {
        System.out.println("ExecuteProcedure ----------");
        ResponsableProgramaEducativo result;
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(ResponsableProgramaEducativo.class);
        try {
            result = (ResponsableProgramaEducativo) lQuery.uniqueResult();
        } catch (Exception exception) {
            result = null;
        }
        return result;
    }
}