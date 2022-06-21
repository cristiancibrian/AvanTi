package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 */
public class UnidadAprendizajeDAO extends AbstractDAO<Integer, Unidadaprendizaje> {

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public UnidadAprendizajeDAO() {
    }
    //</editor-fold>

    /**
     *
     * Metodo que obtiene las "Unidades de Aprendizaje" y grupos por profesor,
     * programa educativo y ciclo escolar.
     *
     * @param proid Id del Profesor
     * @param peid Id del Programa Educativo
     * @param ce Ciclo Escolar
     * @param coordinadorProId Id del Coordinador de Programa Educativo
     * @return
     */
    public List<Object[]> findByProfesorYPE(int proid, int peid, String ce, int coordinadorProId) {
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery("CALL buscarUapPorProfesorYPE(:profId, :peId, :ce, :coordId)");
        lQuery.setParameter("profId", proid).setParameter("peId", peid).setParameter("ce", ce).setParameter("coordId", coordinadorProId);

        return lQuery.list();
    }

    /**
     *
     * Metodo utilizado para obtener una lista de "Unidad de Aprendizaje"
     * basandose en un programa educativo seleccionado.
     *
     * @param PESeleccionado Programa educativo seleccionado
     * @param CE Ciclo escolar del programa educativo
     * @param rol Rol del profesor
     * @param profId Id del profesor
     * @return Lista de unidades de aprendizaje por programa educativo
     */
    public List<Unidadaprendizaje> getListaUnidadAprendizajePorProgramaEducativo(String PESeleccionado, String CE, String rol, String profId) {
        return executeProcedure("CALL obtenerUapPorPE(:rol, :pEDid, :cicloEscolarCESid, :profesorPROid)", new String[]{"rol", "pEDid", "cicloEscolarCESid", "profesorPROid"},
                PESeleccionado, CE, rol, profId);
    }

    /**
     * Metodo que obtiene las "Unidades de Aprendizaje" por "Unidad Academica".
     *
     * @param idUA Id de la Unidad Academica
     * @return Lista de unidades de aprendizaje por Unidad Academica
     */
    public List<Unidadaprendizaje> unidadByUnidadAcademica(String idUA) {
        return executeProcedure("CALL obtenerUapPorUac(:uACid)", new String[]{"uACid"}, idUA);
    }
}
