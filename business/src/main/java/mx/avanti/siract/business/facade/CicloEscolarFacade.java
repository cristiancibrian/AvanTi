package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegateCicloEscolar;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Cicloescolar;

/**
 * 
 * @author Oscar D. Sanchez
 */
public class CicloEscolarFacade {
    
    private final DelegateCicloEscolar delegateCicloEscolar;
    
    public CicloEscolarFacade(){
        delegateCicloEscolar = new DelegateCicloEscolar();
    }  
    /***
     * Recibe un id de tipo entero y busca un Ciclo escolar por ese id, el id 
     * tiene que existir
     * @param id del ciclo escolar a buscar
     * @return Ciclo escolar encontrado
     */
    public Cicloescolar buscarCicloEscolar(int id){
        return delegateCicloEscolar.find(id);
    }
    /**
     * Metodo para buscar todos los ciclos escolares
     * @return Lista con todos los ciclos escolares
     */
    public List<Cicloescolar> buscarCiclosEscolares(){  
        return delegateCicloEscolar.getCiclosEscolares();
    }  
    /***
     * Recibe un objeto tipo Cicloescolar y lo guarda en la base de datos
     * @param cicloescolar 
     */
    public boolean agregarCicloEscolar(Cicloescolar cicloescolar){
        return delegateCicloEscolar.saveCicloEscolar(cicloescolar);
    }
    /***
     * Recibe un objeto de tipo Cicloescolar y lo elimina de la base de datos
     * @param cicloEscolar que se desea eliminar
     */
    public boolean eliminarCicloEscolar(Cicloescolar cicloEscolar){
        return delegateCicloEscolar.deleteCicloEscolar(cicloEscolar);
    } 
    /***
     * Regresa el ciclo escolar actual
     * @return ultimo ciclo escolar registrado
     */
    public Cicloescolar cicloescolarActual(){
        return delegateCicloEscolar.getCicloEscolarActual();
    }
    /**
     * Metodo para actualizar el cicloEscolar
     * @param cicloescolar que se desea modificar
     */
    public boolean updateCicloEscolar(Cicloescolar cicloescolar){
        return delegateCicloEscolar.updateCicloEscolar(cicloescolar);
    }
    
    /**
      * Metodo para traer los ciclos escolares ordenados de mayor a menor
      * @return 
      */
     public List<Cicloescolar> getListaOrdenada(){
        return delegateCicloEscolar.getListaOrdenada();
    }

    public void terminarTransaccion(boolean completa){        
            delegateCicloEscolar.terminar(completa);        
        }
}