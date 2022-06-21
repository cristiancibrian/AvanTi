package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Javier Razo
 */
public class DelegateUnidadAprendizajeTieneContenidotematico {

    /**
     * Metodo para obtener Contenidos Tematicos
     *
     * @return Lista de Unidad Aprendizaje Tiene Contenido Tematico
     */
    public List<UnidadaprendizajeTieneContenidotematico> findAllUnidadaprendizajeTieneContenidotematico() {
        return ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findAll();
    }

    /**
     * Metodo para agregar un Contenido Tematico
     *
     * @param unidadaprendizajeTieneContenidotematico Objeto tipo Unidad
     * Aprendizaje Tiene Contenido Tematico
     */
    public void saveUnidadaprendizajeTieneContenidotematico(UnidadaprendizajeTieneContenidotematico unidadaprendizajeTieneContenidotematico) {
        ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().save(unidadaprendizajeTieneContenidotematico);
    }

    /**
     * Metodo para modificar un Contenido Tematico
     *
     * @param unidadaprendizajeTieneContenidotematico Objeto tipo Unidad
     * Aprendizaje Tiene Contenido Tematico
     */
    public void updateUnidadaprendizajeTieneContenidotematico(UnidadaprendizajeTieneContenidotematico unidadaprendizajeTieneContenidotematico) {
        ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().update(unidadaprendizajeTieneContenidotematico);
    }

    /**
     * Metodo para eliminar un Contenido Tematico
     *
     * @param unidadaprendizajeTieneContenidotematico Objeto tipo Unidad
     * Aprendizaje Tiene Contenido Tematico
     */
    public void deleteUnidadaprendizajeTieneContenidotematico(UnidadaprendizajeTieneContenidotematico unidadaprendizajeTieneContenidotematico) {
        ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().delete(unidadaprendizajeTieneContenidotematico);
    }

    /**
     * Metodo para obtener un Contenido Tematico por Id
     *
     * @param id Id del Contenido Tematico
     * @return Objeto tipo Unidad Aprendizaje Tiene Contenido Tematico
     * encontrado
     */
    public UnidadaprendizajeTieneContenidotematico findUnidadaprendizajeTieneContenidotematico(Integer id) {
        return ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findFromWhereB("cTid", "cTid", String.valueOf(id), "version DESC").get(0);
    }

    
    /**
     * Método para obtener contenido Temático por Id con varias versioens
     *
     * @param id
     * @param vers
     * @return
     */
    public UnidadaprendizajeTieneContenidotematico findUnidadaprendizajeTieneContenidotematicoVersiones(Integer id, Integer vers) {        
        return ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findFromWhereB("cTid", "cTid", String.valueOf(id) + " AND version=" + String.valueOf(vers), "version DESC").get(0);        
    }

    public UnidadaprendizajeTieneContenidotematico findUnidadaprendizajeTieneContenidotematicoPorIdCiclo(Integer id, Integer ciclo) {
        return ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().findFromWhereB("cTid", "cTid", String.valueOf(id) + " AND cicloEscolarCESid<=" + String.valueOf(ciclo), "cicloEscolarCESid DESC").get(0);
    }

    public void clonarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico, String tipoGrupo) {
        ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().clonarContenidoTematico(contenidoTematico, tipoGrupo);
    }

    public void versionarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico) {
        ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().versionarContenidoTematico(contenidoTematico);
    }

    /**
     * Metodo para obtener el maximo ID en contenido temático.
     *
     * @return maximoID.
     */
    public Integer obtenerMaximoID() {
        return ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().obtenerMaximoID();
    }
}
