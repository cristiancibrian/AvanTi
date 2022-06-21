package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Oscar D. Sanchez
 */
public class DelegateCicloEscolar {
    /**
     * Metodo para agregar un ciclo escolar
     * @param cicloEscolar
     */
    public boolean saveCicloEscolar(Cicloescolar cicloEscolar){

      return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclo("CALL agregarCiclo(\'" + cicloEscolar.getCEScicloEscolar() + "\')");

    }
    /**
     * Metodo para actualizar ciclo escolar
     * @param cicloEscolar de tipo Cicloescolar con todos sus atributos
     */
    public boolean updateCicloEscolar(Cicloescolar cicloEscolar){
        return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclo("CALL modificarCiclo(\'" + cicloEscolar.getCESid() + "\' , \'" + cicloEscolar.getCEScicloEscolar() + "\')");
    }
    /**
     * Metodo para consultar todos los ciclos escolares
     * @return Lista de todos los ciclos escolares
     */
    public List<Cicloescolar> getCiclosEscolares(){
        return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclos("CALL consultarCiclos()");
    }
    /**
     * Metodo para eliminar el ciclo escolar
     * @param cicloEscolar de tipo Cicloescolar con todos sus atributos
     */
    public boolean deleteCicloEscolar(Cicloescolar cicloEscolar ){
        return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclo("CALL eliminarCiclo(\'" + cicloEscolar.getCESid() + "\')");
    }
    /**
     * Metodo para encontrar el ultimo CicloEscolar
     * @return el ultimo ciclo escolar
     */
    public Cicloescolar getCicloEscolarActual(){
        return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclos("CALL cicloActual()").get(0);
    }
    /**
     * Metodo para encontrar un ciclo escolar
     * @param id del ciclo escolar
     * @return ciclo escolar correspondiente al id
     */
     public Cicloescolar find(int id){
         return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclos("CALL buscarCiclo(\'" + id + "\')").get(0);
    }
     /**
     * Metodo para regresar una lista ordenada de ciclos escolares, empezando
     * por el Ciclo del mas reciente hasta el mas viejo
     * @return lista ordenada de ciclos escolares
     */
     public List<Cicloescolar> getListaOrdenada(){
        return ServiceLocator.getInstanceCicloEscolarDAO().executeProcedureCiclos("CALL listaOrdenadaCiclos()");
    }

     public void terminar(boolean completo){
        ServiceLocator.getInstanceCicloEscolarDAO().endTransaction(completo);
    }
}
