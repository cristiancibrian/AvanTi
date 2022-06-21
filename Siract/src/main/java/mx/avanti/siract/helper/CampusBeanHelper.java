package mx.avanti.siract.helper;
import java.io.Serializable;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Campus;
public class CampusBeanHelper implements Serializable{
    private Campus campus;
    private Campus selectedCampus;
    private List<Campus> listaFiltrada;
    private List<Campus> listaCampus;
    private boolean banCiclo;
    private String Mensaje;
     boolean ban = true;

    public List<Campus> getListaCampus() {
        return listaCampus;
    }
    public void setListaCampus(List<Campus> listaCampus) {
        this.listaCampus = listaCampus;
    }  
    public CampusBeanHelper(){
//        try{
//            this.campusDelegate = new CampusDelegate();
//        } catch(Exception ex){
//            ex.printStackTrace();
//        }
        campus = new Campus();
        selectedCampus = new Campus();
    }
    public Campus getCampus(){
        return campus;
    }    
    public void setCampus(Campus campus) {
        this.campus = campus;
    } 
    public Campus getSelectedCampus() {
        return selectedCampus;
    }  
    public void setSelectedCampus(Campus selectedCampus) {
        this.selectedCampus = selectedCampus;
    }
    public void setListaFiltrada(List<Campus> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    /**
     * Método que busca todos los campus que coincidan con busqueda y los
     * guarda en una lista.
     * @param campo tipo String (No se usa)
     * @param busqueda tipo String 
     * @return List<Campus> Lista con Objetos tipos campus
     */
    public List<Campus> filtrado(String campo, String busqueda){  
        String CambioBus =busqueda.toLowerCase();
        String CambioObjNom="";

        //listaFiltrada = campusDelegate.getListaCampus();
        listaFiltrada = ServiceFacadeLocator.getInstanceCampusFacade().TodosCampus();
        
        if(busqueda.trim().length()>0){
            listaFiltrada.clear();
            //for(Campus cam : campusDelegate.getListaCampus()){
            for(Campus cam : ServiceFacadeLocator.getInstanceCampusFacade().TodosCampus()){
                
                    CambioObjNom=cam.getCAMnombre().toLowerCase();
                    CambioObjNom=CambioObjNom.replace("á","a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
                    if(CambioObjNom.startsWith(CambioBus)){
                        listaFiltrada.add(cam);
                    }}}
        return listaFiltrada;
    }
    /**
     * Método que busca el campus seleccionado entre todos los campus
     * y lo guarda en el atributo campus
     */    
    public void seleccionarRegistro() {
        //for (Campus cam : campusDelegate.getListaCampus()) {
        for (Campus cam : ServiceFacadeLocator.getInstanceCampusFacade().TodosCampus()){
            if (cam.getCAMid().equals(selectedCampus.getCAMid())) {
                campus = cam;
            }
        }
    }
    /**
     * Método que compara un String con el nombre de cada campus
     * Si son iguales regresa "false", Si no son iguales regresa "true"
     * @param campu tipo String 
     * @return ban tipo boolean
     */
    public boolean Validar(String campu) {
        banCiclo = true;
        Mensaje = "";
        //for (Campus cam : campusDelegate.getListaCampus()){
        for (Campus cam : ServiceFacadeLocator.getInstanceCampusFacade().TodosCampus()){
            if (cam.getCAMnombre().endsWith(campu)) {
                ban = false;
                break;
            } else {
                ban = true;
            }
        }
        return ban;
    }
   /**
    * Método que guarda un objeto tipo campus
    * @param campus tipo Campus
    */ 
    public void agregarCampus(Campus campus){
        ServiceFacadeLocator.getInstanceCampusFacade().agregarCampus(campus);
    }
    /**
     * Método que elimina un objeto tipo Campus
     * @param campus 
     */
     public void eliminarCampus(Campus campus){
        ServiceFacadeLocator.getInstanceCampusFacade().eliminarCampus(campus);
    }
    /**
     * Método para buscar un campus por su Id
     * @param id
     * @return 
     */
    public Campus buscarById(int id){
        return ServiceFacadeLocator.getInstanceCampusFacade().buscarCampusById(id);
    }
    /**
     * Método para modificar la información de un campus
     * @param campus 
     */
    public void updateCampus(Campus campus){
        ServiceFacadeLocator.getInstanceCampusFacade().actualizarCampus(campus);
    }
}