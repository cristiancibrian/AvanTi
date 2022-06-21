package dao;

import clases.AbstractDAO;
import entidades.AlumnoPerteneceGrupo;

/**
 *
 * @author Hector
 */
public class AlumnoPerteneceGrupoDAO extends AbstractDAO<Integer, AlumnoPerteneceGrupo> {

    public AlumnoPerteneceGrupo getAlumnoById(int id) {
        AlumnoPerteneceGrupo result = null;
        result = this.executeQueryUnique("Select * from alumno_pertenece_grupo where alumnoALid = " + id + ";");
        return result;
    }
}
