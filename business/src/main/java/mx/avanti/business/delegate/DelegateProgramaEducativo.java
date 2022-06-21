package mx.avanti.business.delegate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static java.util.Objects.isNull;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Alex
 */
public class DelegateProgramaEducativo {
    public void terminar(boolean completo){
        ServiceLocator.getInstanceProgramaeducativo().endTransaction(completo);
    }
    /**
     * Metodo para agregar un programa educativo 
     * @param programaEducativo con todos sus atributos y id 0 para hacer 
     * un nuevo registro 
     */
    public Programaeducativo agregarProgramaEducativo(Programaeducativo programaEducativo) {
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL guardar_PE (\'" 
                + programaEducativo.getPEDclave() + "\', \'" + 
                programaEducativo.getPEDnombre() +  "\', \'" + 
                programaEducativo.getUnidadAcademicaUACid().getUACid() + "\')");
        if(isNull(result)){//ocurrio un error en la transaccion
            System.out.println("<Error en la alta de PE>");
            return null;
        }else if(result.isEmpty()){ //se pudo realizar la transaccion pero no se devolvio nada
            return new Programaeducativo();
        }else{ //se realizo la transaccion y se devolvio algo
            return result.get(0);
        }
    }
    /**
     * Metodo para actualizar un registro de programa educativo
     * @param programaEducativo con todos sus atributos 
     */
    public Programaeducativo updateProgramaEducativo(Programaeducativo programaEducativo){
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL actualizar_PE (\'" 
                + programaEducativo.getPEDid() + "\', \'" + 
                programaEducativo.getPEDclave() + "\', \'" + 
                programaEducativo.getPEDnombre() +  "\', \'" + 
                programaEducativo.getUnidadAcademicaUACid().getUACid() + "\')");
        if(isNull(result)){  
            System.out.println("<Error en la actualización de PE>");
            return null;
        }else if(result.isEmpty()){
            return new Programaeducativo();
        }else{
            return result.get(0);
        }
    }
    /**
     * Metodo para consultar todos los registros de programas educativos en
     * la base de datos
     * @return lista de entidades de programa educativo
     */
    public List<Programaeducativo> getProgramaEducativos() {
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL verTodos_PE()");
        if(isNull(result)){
            System.out.println("<Error en la consulta general de PE>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
    
    /**
     * Consulta general de programas educativos
     * @return Lista de programas educativos
     * @deprecated 
     */
    public List<Programaeducativo> consultaPlanestudioDePEGeneral() {
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarAsignadosPlanEstudios_PE()");
        if(isNull(result)){
            System.out.println("<Error en la consulta por Plan de estudio en PE>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }
    
    /**
     * Busqueda de programa educativo por id de profesor
     * @param idProfesor Id de profesor
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> buscarProgramaEducativo(String idProfesor) {
        List<Programaeducativo> listaPE = new ArrayList();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DelegateCicloEscolar cicloescolarDelegate = new DelegateCicloEscolar();
        DelegateConfiguracion configuracionDelegate = new DelegateConfiguracion();
        String fechaLimite = "";
        //Obtener CicloEscolarActual
        Cicloescolar auxCicloEscolar;
        auxCicloEscolar = cicloescolarDelegate.getCicloEscolarActual();
        if (isNull(auxCicloEscolar)) {
        } else {
            Configuracion auxConfiguracion = configuracionDelegate.findConfiguracionByID(auxCicloEscolar.getCESid()-1);
            date = auxConfiguracion.getCONfechaInicioSemestre();
            fechaLimite = sdf.format(date);
        }
        System.out.println("Fecha limite :" + fechaLimite);
        ServiceLocator.getInstanceBaseDAO().setTipo(Programaeducativo.class);
        listaPE = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarPorProfesor_PE (\'" 
                + idProfesor+ "\')");
        if(isNull(listaPE)){
            System.out.println("<Error en la consulta de PE por profesor>");
            return null;
        }else if(listaPE.isEmpty()){
            return new ArrayList();
        }else{
            return listaPE;
        }
    }
    
    /**
     * Consulta programas educativos por rol
     * @param idProf 
     * @param rol
     * @param ce
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> buscarProgramaEducativoPorRol(String idProf, String rol, String ce, boolean esRactConsultas) {
	System.out.println("FacadeProgramaEducativo:buscarProgramaEducativoPorRol:90:Rol : "+rol +"-IdProf: "+ idProf+"-cicloEscolar: " + ce);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DelegateCicloEscolar cicloescolarDelegate = new DelegateCicloEscolar();
        DelegateConfiguracion configuracionDelegate=new DelegateConfiguracion();
        
        String fechaLimite = "";

        Cicloescolar auxCicloEscolar;
        auxCicloEscolar = cicloescolarDelegate.getCicloEscolarActual();
        if(auxCicloEscolar != null){
            Configuracion auxConfiguracion = configuracionDelegate.findConfiguracionByID(auxCicloEscolar.getCESid()-1);
            date = auxConfiguracion.getCONfechaInicioSemestre();
            fechaLimite = sdf.format(date);
        }

        List<Programaeducativo> resultado = null;
        switch (rol) {
            case "Administrador":
                resultado = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL verTodos_PE()");
                if(isNull(resultado))
                    System.out.println("<Error en la consulta general de PE>");
                break;
            case "Coordinador de Área de Conocimiento":
                resultado = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarAreaAdmin_PE (\'" 
                + idProf + "\')");
                if(isNull(resultado))
                    System.out.println("<Error en la consulta de PE por area admin.>");
                break;
            case "Responsable de Programa Educativo":
                if(esRactConsultas){
                    resultado = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarPorRPE_PE (\'" 
                    + idProf + "\')");
                    if(isNull(resultado))
                        System.out.println("<Error en la consulta de PE por RPE>");
                }else{
                    resultado = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL verTodos_PE()");
                    if(isNull(resultado))
                        System.out.println("<Error en la consulta general de PE>");
                }
                break;
            case "Profesor":
                int idProfesor = Integer.parseInt(idProf);
                int idCicloEscolar = Integer.parseInt(ce);
                resultado = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarPorProfeYCiclo (\'" 
                        + idProfesor + "\', \'" + idCicloEscolar + "\')");
                if(isNull(resultado))
                    System.out.println("Error en la cconsulta de PE por profesor y ciclo");
                break;
        }
        if(isNull(resultado))
            return null;
        else if(resultado.isEmpty()){
            return new ArrayList();
        }else{
            return resultado;
        }
    }
    
    /**
     * Metodo para eliminar un programa educativo
     * @param  programaeducativo  Programa educativo 
     */
    public Programaeducativo deleteProgramaEducativo(Programaeducativo programaeducativo) {////
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL eliminar_PE (\'" 
                + programaeducativo.getPEDid() + "\')");
        if(isNull(result)){
            System.out.println("<Error en la eliminación de PE>");
            return null;
        }else if(result.isEmpty()){
            return new Programaeducativo();
        }else{
            return result.get(0);
        }
    }

    /**
     * Metodo para buscar un programa Educativo por ID 
     * @param id Id de programa educativo
     * @return entidad de programa educativo correspondiente al id proporcionado
     */
    public Programaeducativo findProgramaEducativoByID(int id) {
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarID_PE (\'" 
                + id + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta de PE por ID>");
            return null;
        }else if(result.isEmpty()){
            return new Programaeducativo();
        }else{
            return result.get(0);
        }
    }
    
    public Programaeducativo findProgramaEducativoByNombre(String nombrePE){
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarNombre_PE (\'" 
                + nombrePE + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta de PE por Nombre>");
            return null;
        }else if(result.isEmpty()){
            return new Programaeducativo();
        }else{
            return result.get(0);
        }
    }
    
    
    /**
     * Metodo para buscar un programa Educativo por clave 
     * @param id Clave de programa educativo
     * @return entidad de programa educativo correspondiente al id proporcionado
     */
    public Programaeducativo findProgramaEducativoByClave(int id) {    
        List<Programaeducativo> programas;
        programas = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarClave_PE (\'" 
                + id + "\')");
        if(isNull(programas)){
            System.out.println("<Error en la consulta de PE por clave>");
            return null;
        }else if(programas.isEmpty()){
            return new Programaeducativo();
        }else{
            return programas.get(0);
        }
    }
    
    public Areaconocimiento findAreaConocimientoByID(int id){
        List<Areaconocimiento> result;
        result = ServiceLocator.getInstanceAreaconocimiento().executeProcedure("CALL buscarID_AreaConocimiento (\'" 
                + id + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta de pe por area de conocimiento>");
            return null;
        }else if(result.isEmpty()){
            return new Areaconocimiento();
        }else{
            return result.get(0);
        }
    }
    
    /**
     * Consulta programa educativo por clave
     * @param clave Clave de programa educativo
     * @return Programa educativo buscado por clave
     * @deprecated 
     */
    public Programaeducativo getProgramaEducativoByClave(String clave) {
        List<Programaeducativo> programas;
        programas = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarClave_PE (\'" 
                + clave + "\')");
        if(isNull(programas)){  
            System.out.println("<Error en la consulta de PE por clave>");
            return null;
        }else if(programas.isEmpty()){
            return new Programaeducativo();
        }else{
            return programas.get(0);
        }
    }
    
    /**
     * Consulta de programas educativos por clave de unidad academica
     * @param claveUnidad Clave unida academica
     * @return Lista de programas educativos filtrados por clave de unidad academica
     */
    public List<Programaeducativo> getProgramaeducativoByUnidadacademicaClave(int claveUnidad){
        List<Programaeducativo> result;
        result = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarUnidadID_PE (\'" 
                + claveUnidad + "\')");
        if(isNull(result)){   
            System.out.println("<Error en la consulta de PE por unidad>");
            return null;
        }else if(result.isEmpty()){
            return new ArrayList();
        }else{
            return result;
        }
    }  
    
    /**
    * Consulta de programa educativo de una unidad de aprendizaje
    * @param uaId Id de unidad de aprendizaje
    * @return Programa educativo de una unidad de aprendizaje
    * @deprecated 
    */
    public Programaeducativo getPEdeUnidadAprendizaje(int uaId) {
        List<Programaeducativo> listaPE = new ArrayList();
        listaPE = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarUnidadAprendizaje_PE (\'" 
                + uaId+ "\')");
        if(isNull(listaPE)){    
            System.out.println("<Error en la consulta de PE por id de unidad>");
            return null;
        }else if(listaPE.isEmpty()){
            return new Programaeducativo();
        }else{
            return listaPE.get(0);
        }
    }
     
    /**
     * Consulta de programa educativo de una unidad de aprendizaje, si perteneces a Tronco Común, retorna 
     * tronco común
     * @param uaId
     * @return Programa educativo al que pertence
     * @deprecated
     */
    public Programaeducativo getProgramaEducativoDeUnidadAprendizaje(int uaId) {
        List<Programaeducativo> listaPE = new ArrayList();
        listaPE = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarUnidadAprendizaje_PE (\'" 
                + uaId+ "\')");
        if(isNull(listaPE)){   
            System.out.println("<Error en la consulta de PE por unidad>");
            return null;
        }else if(listaPE.isEmpty()){
            return new Programaeducativo();
        }else{
            return listaPE.get(0);
        }
    }
    
    /**
     * Consulta de programa educativo de una unidad de aprendizaje, si perteneces a Tronco Común, retorna 
     * tronco común
     * @param uaId
     * @param ids
     * @return Programa educativo al que pertence
     */
    public Programaeducativo getProgramaEducativoDeUnidadAprendizajeUnico(int uaId, List<Integer> ids) {
        List<Programaeducativo> listaPE = new ArrayList();
        Programaeducativo salida = new Programaeducativo();
        listaPE = ServiceLocator.getInstanceProgramaeducativo().executeProcedure("CALL buscarUnidadAprendizaje_PE (\'" 
                + uaId+ "\')");
        if(isNull(listaPE)){
            System.out.println("<Error en la consulta de PE por unidad de aprendizaje>");
            return null;
        }
        else{
            for (Programaeducativo programaeducativo : listaPE) {
                if (!ids.contains(programaeducativo.getPEDclave())) {
                    salida = programaeducativo;
                }
            }
        }
        return salida;
    }
}
