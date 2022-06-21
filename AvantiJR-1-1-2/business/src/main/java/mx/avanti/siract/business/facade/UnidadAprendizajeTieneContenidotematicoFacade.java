package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateUnidadAprendizajeTieneContenidotematico;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;

/**
 *
 * @author Javier Razo
 */
public class UnidadAprendizajeTieneContenidotematicoFacade {

    private DelegateUnidadAprendizajeTieneContenidotematico delegate;

    public UnidadAprendizajeTieneContenidotematicoFacade() {
        delegate = new DelegateUnidadAprendizajeTieneContenidotematico();
    }

    /**
     * Metodo para obtener Contenidos Tematicos
     *
     * @return Lista tipo Unidad Aprendizaje Tiene Contenido Tematico
     */
    public List<UnidadaprendizajeTieneContenidotematico> consultaContenidoTematico() {
        return delegate.findAllUnidadaprendizajeTieneContenidotematico();
    }

    /**
     * Metodo para obtener un Contenido Tematico por Id
     *
     * @param id Id del Contenido Tematico
     * @return Objeto tipo Unidad Aprendizaje Tiene Contenido Tematico
     * encontrado
     */
    public UnidadaprendizajeTieneContenidotematico busquedaContenidoTematico(Integer id) {
        return delegate.findUnidadaprendizajeTieneContenidotematico(id);
    }

    /**
     * Método para obtener varios Contenidos Temáticos por Id con versiones
     *
     * @param id
     * @param version
     * @return
     */
    public UnidadaprendizajeTieneContenidotematico busquedaContenidosTematicos(Integer id, Integer version) {
        return delegate.findUnidadaprendizajeTieneContenidotematicoVersiones(id, version);
    }

    public UnidadaprendizajeTieneContenidotematico buscarContenidoPorIdCiclo(Integer id, Integer ciclo) {
        return delegate.findUnidadaprendizajeTieneContenidotematicoPorIdCiclo(id, ciclo);
    }

    /**
     * Metodo para agregar un Contenido Tematico
     *
     * @param contenidoTematico Objeto tipo Unidad Aprendizaje Tiene Contenido
     * Tematico
     */
    public void agregarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico) {
        delegate.saveUnidadaprendizajeTieneContenidotematico(contenidoTematico);
    }

    /**
     * Metodo para modificar un Contenido Tematico
     *
     * @param contenidoTematico Objeto tipo Unidad Aprendizaje Tiene Contenido
     * Tematico
     */
    public void modificarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico) {
        delegate.updateUnidadaprendizajeTieneContenidotematico(contenidoTematico);
    }

    /**
     * Metodo para eliminar un Contenido Tematico
     *
     * @param contenidoTematico Objeto tipo Unidad Aprendizaje Tiene Contenido
     * Tematico
     */
    public void eliminarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico) {
        delegate.deleteUnidadaprendizajeTieneContenidotematico(contenidoTematico);
    }

    public void clonarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico, String tipoGrupo) {
        delegate.clonarContenidoTematico(contenidoTematico, tipoGrupo);
    }

    public void versionarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico) {
        delegate.versionarContenidoTematico(contenidoTematico);
    }

    /**
     * Metodo para obtener el maximo ID en contenido temático.
     *
     * @return maximoID.
     */
    public Integer obtenerMaximoID() {
        return delegate.obtenerMaximoID();
    }
}
