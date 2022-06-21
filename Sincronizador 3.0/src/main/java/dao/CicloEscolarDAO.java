package dao;

import clases.AbstractDAO;
import entidades.Cicloescolar;

import java.util.List;

/**
 * @author Oscar D. Sanchez
 */
public class CicloEscolarDAO extends AbstractDAO<Integer, Cicloescolar> {

    /**
     * Metodo para regresar una lista ordenada de ciclos escolares, empezando
     * por el Ciclo del mas reciente hasta el mas viejo
     *
     * @return lista ordenada de ciclos escolares
     */
    public List<Cicloescolar> getListaOrdenada() {
        String query = "select * from cicloescolar order by cescicloEscolar DESC";

        return executeQuery(query);
    }

    public Cicloescolar getCESCicloEscolar(String ciclo) {
        Cicloescolar result = null;
        result = this.executeQueryUnique("Select * from cicloescolar where CEScicloEscolar = '" + ciclo + "' ");
        return result;
    }
}
