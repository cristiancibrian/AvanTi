/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import entidades.ProfesorPerteneceProgramaeducativo;

/**
 * @author Memo
 */
public class ProfesorPertenecePEDAO extends AbstractDAO<Integer, ProfesorPerteneceProgramaeducativo> {

    public ProfesorPerteneceProgramaeducativo getProfesorPertenecePEDAOByCicloEscolar(int PROid, int CESid){
        
        ProfesorPerteneceProgramaeducativo result = null;
        result = executeQueryUnique("Select * from profesor_pertenece_programaeducativo where "
                                        +"Profesor_PROid = " + PROid + " and CicloEscolar_CESid = " + CESid + ";");
        return result;
    }
    
    public ProfesorPerteneceProgramaeducativo getProfesorPertenecePEDAOByPROidAndPEDidAndCESid(int PROid, int PEDid, int CESid){
        
        ProfesorPerteneceProgramaeducativo result = null;
        result = executeQueryUnique("Select * from profesor_pertenece_programaeducativo where " + "Profesor_PROid = " + PROid + " and ProgramaEducativo_PEDid = " + PEDid +
                                    " and CicloEscolar_CESid = " + CESid + ";");
        
        return result;
        
    }

    
    
}

