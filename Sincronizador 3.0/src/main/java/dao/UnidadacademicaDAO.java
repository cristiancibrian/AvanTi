package dao;

import clases.AbstractDAO;
import entidades.Unidadacademica;

/**
 * @author Axel Valenzuela. Avanti
 */
public class UnidadacademicaDAO extends AbstractDAO<Integer, Unidadacademica> {

    public Unidadacademica getUnidadAcademicaByClave(String clave) {
        Unidadacademica result = null;
        result = this.executeQueryUnique("Select * from Unidadacademica where UACclave = '" + clave + "' ");
        return result;
    }

    public Unidadacademica getUnidadAcademicaByID(int id) {
        Unidadacademica result = null;
        result = this.executeQueryUnique("Select * from Unidadacademica where UACid = '" + id + "' ");
        return result;
    }
    
    public Unidadacademica getUnidadAcademicaByClaveAndCAMid(String clave, int CAMid){
        Unidadacademica result = null;
        result = this.executeQueryUnique("Select * from Unidadacademica where UACclave = '" + clave + "' and Campus_CAMid = '" + CAMid + "'; ");
        return result;
    }
}
