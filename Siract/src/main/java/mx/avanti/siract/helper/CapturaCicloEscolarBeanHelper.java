package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Cicloescolar;
/**
 * 
 * @author Oscar D. Sanchez
 */
public class CapturaCicloEscolarBeanHelper implements Serializable{
    private Cicloescolar cicloescolar;
    private Cicloescolar selecCiclo;
    private List<Cicloescolar> listaFiltrada;
    private List<Cicloescolar> listaCicloSeleccionada;
    private boolean blnciclo;
    private String mensaje;
    
    public CapturaCicloEscolarBeanHelper(){
        cicloescolar = new Cicloescolar();
        selecCiclo = new Cicloescolar();
    }
    
    /**
     * Metodo para agregar un ciclo escolar
     * @param cicloEscolar de tipo cicloEscolar con todos sus atributos
     */
    public void agregarCicloEscolar(Cicloescolar cicloEscolar){
        ServiceFacadeLocator.getInstanceCicloEscolarFacade().agregarCicloEscolar(cicloEscolar);
    }
    /**
     * Metodo para eliminar un ciclo escolar
     * @param cicloEscolar de tipo CicloEscolar con todos sus atributos
     */
    public void eliminarCicloEscolar(Cicloescolar cicloEscolar){
        ServiceFacadeLocator.getInstanceCicloEscolarFacade().eliminarCicloEscolar(cicloEscolar);
    } 
    /**
     * Metodo para modificar un CicloEscolar
     * @param cicloescolar de tipo cicloEscolar con todos sus atributos
     */
    public void modificarCicloEscolar(Cicloescolar cicloescolar){
        ServiceFacadeLocator.getInstanceCicloEscolarFacade().updateCicloEscolar(cicloescolar);
    }
    
    /**
     * Metodo para buscar la cadena ingresada en tiempo real
     * @param campo este parametro no se utiliza en el metodo
     * @param busqueda 
     * @return lista Filtrada
     */
    public List<Cicloescolar> filtrado(String campo, String busqueda) {
        listaFiltrada = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();         
        if (busqueda.trim().length() > 0) {
            listaFiltrada.clear();
            for(Cicloescolar ciclo : ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada()){    
                String CambioObjNum = ciclo.getCEScicloEscolar();
                if (CambioObjNum.startsWith(busqueda)) {
                    listaFiltrada.add(ciclo);
                }
            }
        }
        return listaFiltrada;
    }

    /**
     * Metodo para validar en base al Calendario Actual 
     * que no se agreguen ciclos que aun no existen 
     * @return verdadero o falso
     */
    public boolean validacionCiclo() { 
        boolean ban=true;
        String string = cicloescolar.getCEScicloEscolar();
        String[] parts = string.split("-");
        String part1 = parts[0]; 
        String part2 = parts[1];
        int parte1Int = Integer.parseInt(part1);
        int parte2Int = Integer.parseInt(part2);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if(parte1Int>2000 && parte1Int<=year){
            if(parte2Int>0 && parte2Int<6 && parte2Int!=3){
                 ban=true;
            }else{
                ban=false;
            }
         }else{
             ban=false;
         }
        return ban;
     
     }
    
    /**
     * Metodo para eliminar un ciclo de la lista
     * @param id del ciclo a buscar
     */
    public void eliminarDeLista(int id){
        for(Cicloescolar ciclo:listaCicloSeleccionada){
            if(ciclo.getCESid().equals(id)){
                int index = listaCicloSeleccionada.indexOf(ciclo);
                listaCicloSeleccionada.remove(index);
                break;
            }
        }
    }
    
    /**
     * Metodo para validar que no existan ciclos escolares repetidos
     * @return verdadero o falso
     */
    public String validarRepetidos() {
        blnciclo = true;
        mensaje = "";
        for (Cicloescolar ciclo : ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares()){
            if (ciclo.getCEScicloEscolar().equals(cicloescolar.getCEScicloEscolar()) && !ciclo.getCESid().equals(cicloescolar.getCESid())) {
                mensaje = mensaje + "[Ciclo Escolar]";
                blnciclo= false;
                break;
            } 
        }
        return mensaje;
    }

    public Cicloescolar getCicloescolar() {
        return cicloescolar;
    }

    public void setCicloescolar(Cicloescolar cicloescolar) {
        this.cicloescolar = cicloescolar;
    }

    public Cicloescolar getSelecCiclo() {
        return selecCiclo;
    }

    public void setSelecCiclo(Cicloescolar selecCiclo) {
        this.selecCiclo = selecCiclo;
    }

    public List<Cicloescolar> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Cicloescolar> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Cicloescolar> getListaCicloSeleccionada() {
        return listaCicloSeleccionada;
    }

    public void setListaCicloSeleccionada(List<Cicloescolar> listaCicloSeleccionada) {
        this.listaCicloSeleccionada = listaCicloSeleccionada;
    }

    public boolean isBlnciclo() {
        return blnciclo;
    }

    public void setBlnciclo(boolean blnciclo) {
        this.blnciclo = blnciclo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}