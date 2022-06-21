package dao;

import clases.AbstractDAO;
import entidades.UnidadaprendizajeTieneContenidotematico;

/**
 * @author kevin
 */
public class UnidadAprendizajeTieneContenidotematicoDAO extends AbstractDAO<Integer, UnidadaprendizajeTieneContenidotematico> {
    
    public UnidadaprendizajeTieneContenidotematico getUnidadAprendizajeContenidoTematicoByClave(int clave) {
        
        UnidadaprendizajeTieneContenidotematico result = null;
        result = this.executeQueryUnique("Select * from unidadaprendizaje_tiene_contenidotematico where UnidadAprendizaje_UAPid = '" + clave + "' ");
        return result;
        
    }
    
}