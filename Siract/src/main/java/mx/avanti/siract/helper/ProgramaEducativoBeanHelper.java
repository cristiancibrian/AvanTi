package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Campus;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Usuario;

/**
 * 
 * @author Javier Razo
 */
public class ProgramaEducativoBeanHelper implements Serializable {

    private Programaeducativo programaEducativo;
    private Programaeducativo selecProgramaEducativo;
    private Unidadacademica unidadacademica;
    private Unidadacademica filtroUAC;
    private List<Programaeducativo> listaFiltrada;
    private List<Programaeducativo> listaSeleccionPe;
    private boolean banPe;
    private String mensaje;
    private String rolSeleccionado;

    private Usuario usuario;
    private String ocultarLista;
    private Profesor profesor;
    private List<Unidadacademica> listaUnidadAcademica;
    private List<Programaeducativo> listaFiltrada2;
    
    private boolean transaccionCompleta = false;
    
    public ProgramaEducativoBeanHelper() {
        programaEducativo = new Programaeducativo();
        selecProgramaEducativo = new Programaeducativo();
        unidadacademica = new Unidadacademica();
        filtroUAC = new Unidadacademica();
        filtroUAC.setUACid(0);
    }

    // <editor-fold defaultstate="collapsed" desc="Getters y setters">
    
    public Unidadacademica getFiltroUAC() {
        return filtroUAC;
    }

    public void setFiltroUAC(Unidadacademica filtroUAC) {
        this.filtroUAC = filtroUAC;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getOcultarLista() {
        return ocultarLista;
    }

    public void setOcultarLista(String ocultarLista) {
        this.ocultarLista = ocultarLista;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Unidadacademica> getListaUnidadAcademica() {
        return listaUnidadAcademica;
    }

    public void setListaUnidadAcademica(List<Unidadacademica> listaUnidadAcademica) {
        this.listaUnidadAcademica = listaUnidadAcademica;
    }

    public List<Programaeducativo> getListaFiltrada2() {
        return listaFiltrada2;
    }

    public void setListaFiltrada2(List<Programaeducativo> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    }

     public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public Programaeducativo getSelecProgramaEducativo() {
        return selecProgramaEducativo;
    }

    public void setSelecProgramaEducativo(Programaeducativo selecProgramaEducativo) {
        this.selecProgramaEducativo = selecProgramaEducativo;
    }

    public Unidadacademica getUnidadacademica() {
        return unidadacademica;
    }

    public void setUnidadacademica(Unidadacademica unidadacademica) {
        this.unidadacademica = unidadacademica;
    }

    public List<Programaeducativo> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Programaeducativo> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Programaeducativo> getListaSeleccionPe() {
        return listaSeleccionPe;
    }

    public void setListaSeleccionPe(List<Programaeducativo> listaSeleccionPe) {
        this.listaSeleccionPe = listaSeleccionPe;
    }

    public boolean isBanPe() {
        return banPe;
    }

    public void setBanPe(boolean banPe) {
        this.banPe = banPe;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public void setTransaccion(boolean transaccionCompleta){
        this.transaccionCompleta = transaccionCompleta;
    }
    
    public boolean getTransaccion(){
        return transaccionCompleta;
    }
     // </editor-fold>

    /**
     * Método de filtrado de PE que busca por texto  inicial
     * @param busqueda
     * @return Lista de PE que comiencen con el texto a buscar
     */
    public List<Programaeducativo> filtrado(String busqueda) {
        getUsuarioTienePE();
        if (busqueda.trim().length() > 0) {
            listaFiltrada2.clear();
            boolean letras = false; //Bandera para saber si se escribieron letras
            try {  //Si genera una excepción significa que son letras
                int numero = Integer.parseInt(busqueda);
            } catch (Exception ex) {
                letras = true;
            }                       //
            for (Programaeducativo pro : listaFiltrada) {
                Unidadacademica unidad = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(pro.getUnidadAcademicaUACid().getUACid());
                if(isNull(unidad))
                    transaccionCompleta = false;
                
                pro.setUnidadAcademicaUACid(unidad);
                
                if(!letras){  //Si no son letras buscamos por clave                                               
                    if(Integer.toString(pro.getPEDclave()).startsWith(busqueda)){
                        listaFiltrada2.add(pro);
                    }
                } 
                else {  //Si son letras, buscamos por nombre o por unidad académica
                    if(pro.getPEDnombre().toLowerCase().contains(busqueda.toLowerCase())){
                        listaFiltrada2.add(pro);
                    }
                    else if(pro.getUnidadAcademicaUACid() != null){ //Nos aseguramos de que no sea nulo para poder buscar por unidad académica
                        if(pro.getUnidadAcademicaUACid().getUACnombre().toLowerCase().contains(busqueda.toLowerCase())){
                                listaFiltrada2.add(pro);
                        }
                    }
                }
            }
        } else {
            listaFiltrada2 = listaFiltrada;
        }
        for (Programaeducativo pro : listaFiltrada2) {
            Unidadacademica unidad = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(pro.getUnidadAcademicaUACid().getUACid());
            if(isNull(unidad)){
                transaccionCompleta = false;
            }
            pro.setUnidadAcademicaUACid(unidad);
        }

        return listaFiltrada2;
    }

    /**
     * Método para selccionar un registro que coincida con el ID del Programa 
     * educativo seleccionado
     */
    public void seleccionarRegistro() {
        List<Programaeducativo> lista = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        if(isNull(lista)){
            transaccionCompleta = false;
            lista = new ArrayList();
        }
        for(Programaeducativo pro : lista){
            if (pro.getPEDid().equals(selecProgramaEducativo.getPEDid())) {
                programaEducativo = pro;
                unidadacademica = pro.getUnidadAcademicaUACid();
            }
        }
    }

    public void seleccionarRegistroRefactoreado() {
        Programaeducativo salida = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(selecProgramaEducativo.getPEDid());
        if(isNull(salida))
            transaccionCompleta = false;
        else{
            programaEducativo = salida;
            unidadacademica = programaEducativo.getUnidadAcademicaUACid();
        }
    }
    
    
    /**
     * Método para eliminar un programa educativo por Id
     * @param id 
     */
    public void eliminarDeLista(int id) {
        for (Programaeducativo pro : listaSeleccionPe) {
            if (pro.getPEDid().equals(id)) {
                int index = listaSeleccionPe.indexOf(pro);
                listaSeleccionPe.remove(index);
                break;
            }
        }
    }

    /**
     * Método para validar repetición en PE por Clave y Nombre
     * @return Mensaje en donde se encontró la repetición
     */
    public String validarRepetidos() {
        banPe = true;
        mensaje = "";
        int contadorMensaje = 0;
        List<Programaeducativo> lista = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        if(isNull(lista))
            transaccionCompleta = false;
        else{
            for (Programaeducativo pro : lista) {
                if (pro.getPEDclave() == programaEducativo.getPEDclave()
                        && !pro.getPEDid().equals(programaEducativo.getPEDid())) {
                    mensaje += "El campo clave ya existe";
                    contadorMensaje++;
                    banPe = false;
                }
                if (pro.getPEDnombre().equals(programaEducativo.getPEDnombre())
                        && !pro.getPEDid().equals(programaEducativo.getPEDid())
                        && pro.getUnidadAcademicaUACid().getUACid().equals(unidadacademica.getUACid())){//Objects.equals(pro.getUnidadAcademicaUACid().getUACid(), unidadacademica.getUACid())) {
                    mensaje += "El campo nombre ya existe";
                    contadorMensaje++;
                    banPe = false;
                }

            }
            if (contadorMensaje == 2){
                mensaje = "Los campos clave y nombre ya existen";
            }
        }
        return mensaje;
    }

   
    /**
     * Método que obtiene el PE de un Usuario
     */
    public void getUsuarioTienePE() {
        System.out.println("Rol: "+rolSeleccionado);
        try {
            if(!isNull(listaFiltrada))
                listaFiltrada.clear();
            if(!isNull(listaFiltrada2))
                listaFiltrada2.clear();
            listaFiltrada = new ArrayList();
            listaFiltrada2 = new ArrayList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (rolSeleccionado.equalsIgnoreCase("Director") 
                || rolSeleccionado.equalsIgnoreCase("Subdirector")
                || rolSeleccionado.equalsIgnoreCase("RESPONSABLE DE PROGRAMA EDUCATIVO")
                || rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")
                || rolSeleccionado.equalsIgnoreCase("Coordinador de Formación Básica")) {
            ocultarLista = "true";
            
            profesor = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
            listaUnidadAcademica = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid()).getUnidadacademicaList();
            if(isNull(profesor) || isNull(listaUnidadAcademica))
                transaccionCompleta = false;
            else{
                for (Unidadacademica uac : listaUnidadAcademica) {
                    listaFiltrada = uac.getProgramaeducativoList();
                }
            }
        } else {
            if (rolSeleccionado.equalsIgnoreCase("Administrador")) {
                profesor = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
                listaUnidadAcademica = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
                if(isNull(profesor) || isNull(listaUnidadAcademica))
                    transaccionCompleta = false;
                
                if(filtroUAC.getUACid()!= 0){
                    listaFiltrada = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(filtroUAC.getUACid()).getProgramaeducativoList();
                }else{
                    listaFiltrada = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
                }
            } else {
                ocultarLista = "false";
                listaFiltrada = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
            }
            
            if(isNull(listaFiltrada))
                transaccionCompleta = false;
        }
    }
    
    /**
     * Método para obtener la Unidad Académica de un profesor
     * @return Unidad academica
     */
    public Unidadacademica UAbyProf(){
        return profesor.getUnidadacademicaList().get(0);
    }

    /**
     * Método para eliminar Programa Educativo
     * @param programaEducativo Programa Educativo a eliminar
     */
    public Programaeducativo eliminarProgramaEducativo(Programaeducativo programaEducativo) {
        Programaeducativo salida = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().eliminarProgramaEducativo(programaEducativo);
        if(isNull(salida))
            transaccionCompleta = false;
        return salida;
    }

    /**
     * Método para agregar un Programa Educativo
     * @param programaEducativo  Programa Educativo a agregar
     */
    public void agregarProgramaEducativo(Programaeducativo programaEducativo) {
        Programaeducativo salida = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().agregarProgramaEducativo(programaEducativo);
        if(isNull(salida))
            transaccionCompleta = false;
    }
    
    /**
     * Método para modificar un programa educativo
     * @param programaEducativo  Programa Educativo a modificar
     */
    public void modificarProgramaEducativo(Programaeducativo programaEducativo){
        Programaeducativo salida = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().actualizarProgramaEducatico(programaEducativo);
        if(isNull(salida))
            transaccionCompleta = false;
    }

    /**
     * Método para consultar Unidades Academicas
     * @return Lista de UA
     */
    public List<Unidadacademica> consultaUnidadAcademica() {
        List<Unidadacademica> salida = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();
        if(isNull(salida))
            transaccionCompleta = false;
        return salida;
    }
    
    /**
     * Método para obtener una unidad académimca por id
     * @param id Id de unidad académica a buscar
     * @return 
     */
    public Unidadacademica unidadadAcademicaById(int id){
        Unidadacademica salida = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(id);
        if(isNull(salida))
            transaccionCompleta = false;
        return salida;
    }
    
    /**
     * Valida que el programa educativo no este relacionado para su modificación
     * @param programaeducativo Programa educativo a validar
     * @return 
     */
    public boolean validarModificacion(Programaeducativo programaeducativo){
        if(programaeducativo.getPlanestudioList().isEmpty()
           && programaeducativo.getAreaadministrativaList().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Valida que el programa educativo no este relacionado para su eliminación
     * @param programaeducativo Programa educativo a validar
     * @return 
     */
    public boolean validarEliminacion(Programaeducativo programaeducativo){
        List<Profesor> lista = ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorPE(programaeducativo.getPEDid());
        if(isNull(lista)){
            transaccionCompleta = false;
            lista = new ArrayList();
        }
        if(programaeducativo.getPlanestudioList().isEmpty()
                && programaeducativo.getAreaadministrativaList().isEmpty()
                && lista.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Método que consulta todos los campus
     * @return Lista de todos los campus
     */
    public List<Campus> consultaCampus(){
        List<Campus> salida = ServiceFacadeLocator.getInstanceCampusFacade().TodosCampus();
        if(isNull(salida))
            transaccionCompleta = false;
        return salida;
    }
    
    /**
     * Método para buscar un campus por Id
     * @param id Id de campus a buscar
     * @return Campus buscado
     */
    public Campus buscarCampus(int id){
        Campus salida = ServiceFacadeLocator.getInstanceCampusFacade().buscarCampusById(id);
        if(isNull(salida))
            transaccionCompleta = false;
        return salida;
    }
    
    /**
     * Método que consulta todos los programas educativos de un campus
     * @param id Id de campus
     * @return Lista de programas educativos por campus
     */
    public List<Programaeducativo> programasEducativosByCampus(int id){
        List<Programaeducativo> programasEducativos = new ArrayList<>();
        //Por cada unidad acedmica del campus consultado, agrega los PED a la lista
        for (Unidadacademica unidadAcademica : buscarCampus(id).getUnidadacademicaList()) {
            programasEducativos.addAll(unidadAcademica.getProgramaeducativoList());
        }
        return programasEducativos;
    }
    
    /**
     * Método para ordenar programas educativos por de manera ascendente por clave
     * @param programasEducativos
     * @return Lista de programas educativos ordenados de manera ascendente clave
     */
    public List<Programaeducativo> ordenarProgramasEducativos(List<Programaeducativo> programasEducativos){     
        Collections.sort(programasEducativos, new Comparator<Programaeducativo>() {
            public int compare(Programaeducativo ped1, Programaeducativo ped2) {
                if(ped1.getPEDclave() > ped2.getPEDclave()){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
        return programasEducativos;
    }
    
    public String insertarSaltoLinea(int maxSize, String texto){
        String cadena = "";
        int posicionCorte = 0;
        
        if(texto.length() > maxSize){
            for (int i = 0; i < maxSize; i++) {
                if(texto.charAt(i) == ' '){
                    posicionCorte = i;
                }
            }
            cadena += texto.substring(0, posicionCorte);
            cadena += "\n";
            cadena += texto.substring(posicionCorte+1, texto.length());
        }else{
            cadena = texto;
        }
        return cadena;
    }
    
    public Programaeducativo buscarPrograma(int id){
        Programaeducativo salida = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id);
        if(isNull(salida))
            transaccionCompleta = false;
        return salida;
    }
    
    /**
     * Método para terminar la transacción. Si la bandera 'transaccionCompleta'
     * es 'true' significa que no ocurrió ningún error y se completa la
     * transacción y se cierra la sesión.
     * Pero si la bandera es 'false' no se pudo completar correctamente la
     * transacción y se llama al rollback.
     */
    public void terminaTransaccion(){
        ServiceFacadeLocator.getInstanceProgramaEducativoFacade().terminarTransaccion(transaccionCompleta);
        transaccionCompleta = true;
    }
}