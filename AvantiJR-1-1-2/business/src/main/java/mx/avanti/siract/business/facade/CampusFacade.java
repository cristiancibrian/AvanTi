package mx.avanti.siract.business.facade;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegateCampus;
import mx.avanti.siract.entity.Campus;
/**
 *
 * @author Paul Miranda
 */
public class CampusFacade {

    private List<Campus> listaCampus;
    private final DelegateCampus delegateCampus;
    //private List<Campus> listaCampus;

    public CampusFacade(){
        delegateCampus = new DelegateCampus();
        listaCampus = new ArrayList<Campus>();
    }

    public void terminarTransaccion(boolean completa){
        delegateCampus.terminar(completa);
    }

    /***
     * Busca todos los Campus y los guarda en una lista tipo Campus
     * @return Lista de tipo Campus
     */
    public List<Campus> TodosCampus() {
        return delegateCampus.findAllCampus();
    }
    public void setListaCampus(List<Campus> listaCampus) {
        this.listaCampus = listaCampus;
    }
    /***
     * Recibe un objeto de tipo campus y lo guarda en la base datos
     * @param campus tipo Campus
     */
    public void agregarCampus(Campus campus){
        delegateCampus.nuevoCampus(campus);
    }
    /***
     * Recibe un objeto tipo campus y lo elimina de la base de datos
     * @param campus tipo Campus
     */
    public void eliminarCampus(Campus campus){
        delegateCampus.deleteCampus(campus);
    }
    /***
     * Recibe id tipo int y busca el Campus por ese id
     * @param id tipo int
     * @return Campus
     */
      public Campus buscarCampusById(int id){
          return delegateCampus.findCampusID(id);
    }
      /**
       * Actualiza la informacion de un registro de campus
       * @param campus tipo Campus
       */
      public void actualizarCampus(Campus campus){
          delegateCampus.updateCampus(campus);
      }
}
