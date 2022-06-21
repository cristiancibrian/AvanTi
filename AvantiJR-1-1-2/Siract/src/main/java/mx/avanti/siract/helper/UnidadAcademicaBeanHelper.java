package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import mx.avanti.business.delegate.CampusDelegate;
//import mx.avanti.business.delegate.UnidadAcademicaDelegate;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadacademica;

public class UnidadAcademicaBeanHelper implements Serializable{
    private Unidadacademica unidadacademica;
    private Unidadacademica selecUnidadAcademica;
    private Campus campus;
    private Campus campus2;
    private List<Unidadacademica> listaFiltrada;
    private List<Unidadacademica> listaFiltrada2;
    private List<Unidadacademica> listaFiltrada3;
    private List<Unidadacademica> listaSeleccionUA;
    private boolean banCiclo;
    private String Mensaje;
    boolean ban = true;
    private int idCampusFiltrado=0;
    
    public List<Unidadacademica> getListaFiltrada3() {
        return listaFiltrada3;
    }
    public void setListaFiltrada3(List<Unidadacademica> listaFiltrada3) {
        this.listaFiltrada3 = listaFiltrada3;
    }
    public List<Unidadacademica> getListaFiltrada2() {
        return listaFiltrada2;
    }
    public void setListaFiltrada2(List<Unidadacademica> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    } 
    public List<Unidadacademica> getListaSeleccionUA() {
        return listaSeleccionUA;
    }
    public void setListaSeleccionUA(List<Unidadacademica> listaSeleccionUA) {
        this.listaSeleccionUA = listaSeleccionUA;
    }
    public Campus getCampus2() {
        return campus2;
    }
    public void setCampus2(Campus campus2) {
        this.campus2 = campus2;
    }
    public UnidadAcademicaBeanHelper(){
        unidadacademica = new Unidadacademica();
        selecUnidadAcademica = new Unidadacademica();
        campus = new Campus();
        campus2 = new Campus();
//        campusDelegate.setListaCampus(null);
    }
    public List<Unidadacademica> getListaFiltrada() {
        return listaFiltrada;
    }
    public Unidadacademica getUnidadacademica() {
        return unidadacademica;
    }
    public void setUnidadacademica(Unidadacademica unidadacademica) {
        this.unidadacademica = unidadacademica;
    }
    public Campus getCampus() {
        return campus;
    }
    public void setCampus(Campus campus) {
        this.campus = campus;
    }
    public Unidadacademica getSelecUnidadAcademica() {
        return selecUnidadAcademica;
    }
    public void setSelecUnidadAcademica(Unidadacademica selecUnidadAcademica) {
        this.selecUnidadAcademica = selecUnidadAcademica;
    }
    public void setListaFiltrada(List<Unidadacademica> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    /***
     * Método para filtar la lista de Unidades academicas por el parametro Busqueda
     * @param busqueda tipo String
     * @return  Lista con Objetos tipo Unidadacademica
     */
    public List<Unidadacademica> filtrado(String busqueda) {
        String CambioBus = busqueda.toLowerCase();
        String CambioObjNom = "";
        String campusFiltro="";
        if(idCampusFiltrado>0){
            listaFiltrada.clear();
            if(idCampusFiltrado==1){
                campusFiltro="mexicali";
            }else{
                if(idCampusFiltrado==2){
                    campusFiltro="tijuana";
                }else{
                    if(idCampusFiltrado==3){
                        campusFiltro="ensenada";
                    }
                }
            }
            listaFiltrada2 = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
            ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
            for (Unidadacademica ua : listaFiltrada2) {
                if(busqueda.trim().length()>0){                      
                    String CambioObjNum = Integer.toString(ua.getUACclave());
                    if (CambioObjNum.contains(busqueda)
                            && campusFiltro.equalsIgnoreCase(ua.getCampusCAMid().getCAMnombre())){
                        listaFiltrada.add(ua);
                    }else{
                            CambioObjNom = ua.getUACnombre().toLowerCase();
                            if(CambioObjNom.contains(busqueda)
                                    &&campusFiltro.equalsIgnoreCase(ua.getCampusCAMid().getCAMnombre())){
                                listaFiltrada.add(ua);
                            }
                    }
                }else{
                    if(campusFiltro.equalsIgnoreCase(ua.getCampusCAMid().getCAMnombre())){
                        listaFiltrada.add(ua);
                    }
                }
            }
        }else{
                
        listaFiltrada2 = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);

//        for(Unidadacademica uac : listaFiltrada2){
//            uac.setCampusCAMid(ServiceFacadeLocator.getInstanceCampusFacade().buscarCampusById(uac.getCampusCAMid().getCAMid()));
//            }
        
        if (busqueda.trim().length() > 0) {
            listaFiltrada.clear();
            
            for (Unidadacademica ua : listaFiltrada2){

                String CambioObjNum = Integer.toString(ua.getUACclave());
                if (CambioObjNum.contains(busqueda)){
                    listaFiltrada.add(ua);
                }else{
                        CambioObjNom = ua.getUACnombre().toLowerCase();
                        if(CambioObjNom.contains(busqueda)){
                            listaFiltrada.add(ua);
                        }
                }
            }
        }else{
            listaFiltrada = listaFiltrada2;
        }
        }
        return listaFiltrada;
    }
    /***
     * Método para filtar listaFiltrada2 por el parametro busqueda
     * @param busqueda tipo int 
     * @return Lista con Objetos tipo Unidadacademica
     */
    public List<Unidadacademica> filtrado2(int busqueda) {
        String CambioObjNom = "";
        idCampusFiltrado=busqueda;
        listaFiltrada2 = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);

//        for (Unidadacademica uac : listaFiltrada2) {
//            uac.setCampusCAMid(ServiceFacadeLocator.getInstanceCampusFacade().buscarCampusById(uac.getCampusCAMid().getCAMid()));
//            }        
        if (busqueda > 0) {
            listaFiltrada.clear();
            
            for (Unidadacademica ua : listaFiltrada2) {
                    if(ua.getCampusCAMid().getCAMid()==busqueda){
                        listaFiltrada.add(ua);
                    }
            }
        }else{
            listaFiltrada = listaFiltrada2;
        }
        return listaFiltrada;
    }
    /**
     * Método para Obtener la unidadAcademica Seleccionada, la guarda en el atributo
     * unidadacademica
     */
    public void seleccionarRegistro() {
        List<Unidadacademica> unidadAca = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
        for (Unidadacademica ua : unidadAca) {
            if (ua.getUACid().equals(selecUnidadAcademica.getUACid())) {
                unidadacademica = ua;
                campus=ServiceFacadeLocator.getInstanceCampusFacade().buscarCampusById(ua.getCampusCAMid().getCAMid());
            }
        }
    }
    /***
     * Método para validar  si ya existe una unidadAcademica
     * @param ua2
     * @return ban tipo boolean
     */
    public boolean Validar(String ua2, int id) {
        ban = true;
        Mensaje = "";
        List<Unidadacademica> unidadAca = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
        for (Unidadacademica uac : unidadAca) {
            String clave=Integer.toString(uac.getUACclave()); 
            if (clave.equals(ua2) && unidadacademica.getUACid() != uac.getUACid()) {
                System.out.println(id+"   -   "+uac.getUACid());
                ban=false;
                break;
            }else{
                banCiclo = true;
            }
        }
        return ban;
    }
    /***
     * Método para eliminar una Unidad de Aprendizaje de la lista
     * listaSeleccionUA
     * @param id tipo int
     */
    public void eliminarDeLista(int id) {
        for (Unidadacademica uniac : listaSeleccionUA) {
            if (uniac.getUACid().equals(id)) {
                int index = listaSeleccionUA.indexOf(uniac);
                listaSeleccionUA.remove(index);
                break;
            }
        }
    }
   /**
    * Método para agregar una Unidad Academica
    */
    public void agregarUnidadAcademica(){
        unidadacademica.setUACtipo("1");
        unidadacademica.setCampusCAMid(consultaCampusPorID(unidadacademica.getCampusCAMid().getCAMid()));
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().agregarUnidadAcademica(unidadacademica);
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
    }
    /**
     * Método para eliminar una Unidad Academica
     * @param baja tipo Unidadacademica
     */
    public void eliminarUnidadAcademica(Unidadacademica baja){
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().eliminarUnidadAcademica(baja);
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
    }
    /**
     * Método para obtener una lista de los programas educativos de una 
     * Unidad academica
     * @param idAC tipo int
     * @return Lista con Objetos tipo ProgramaEducativo
     */
    public List<Programaeducativo> getUAasignado(int idAC){
        //List<Unidadacademica> lista= new ArrayList();
        
        return unidadacademica.getProgramaeducativoList();
        // return ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().getUAasignado(idAC);
    }
    /***
     * Método para consultar un campus por su Id
     * @param idCampus tipo int
     * @return Objeto tipo Campus
     */
    public Campus consultaCampusPorID(int idCampus){
        return ServiceFacadeLocator.getInstanceCampusFacade().buscarCampusById(idCampus);      
    }
    /***
     * Método para consultar Todos los campus
     * @return Lista con Objetos tipo campus
     */
    public List<Campus> consultaCampus(){
        return ServiceFacadeLocator.getInstanceCampusFacade().TodosCampus();      
    }
    /***
     * Método para Buscar Unidades Academias
     * @return Lista con objeto tipo Unidadacademica
     */
    public List<Unidadacademica> getListaUAGeneral(){
        List<Unidadacademica> unidadAca = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
        return unidadAca;
    }
    /**
     * Método para obtener los profesores que le pertenecen a un coordinador de 
     * área administrativa que esta asociado a un area administrativa
     * 
     * @param idArea ID de un Área Administrativa
     * @return Regresa una lista de los profesores encontrados
     */
    public List<Profesor> getProfesoresPorUnidadAcademica(){
       return unidadacademica.getProfesorList();
        // return ServiceFacadeLocator.getInstanceProfesorFacade().consultaProfesorPorUnidadAcademica(String.valueOf(unidadacademica.getUACid()));
    }
    /**
     * Método para actualizar la información de una unidad Academica
     */
    public void modificarUnidadAcademica(){
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().actualizarUnidadAcademica(unidadacademica);
        ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().terminarTransaccion(true);
    }
}