/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.avanti.siract.custom.entity.ReporteAux;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.entity.Reporte;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Martin
 * @author Mario | Refactor
 */
public class DelegateReporteAvanceContenidoTematico implements Serializable{
    
    /**
     * Metodo para obtener todos los RACTs
     * @return lista de Racts
     */
    public List<Reporteavancecontenidotematico> findAllRACTS(){
        return ServiceLocator.getInstanceReporteAvanceContenidoTematico().findAll();
    }
    
    /**
     * Guarda un RACT a la base de datos
     * @param ract la instancia RACT a guardar
     * @return verdadero o falso dependiendo si se guardo correctamente
     */
    public Reporteavancecontenidotematico saveRACT(Reporteavancecontenidotematico ract){
        List<Reporteavancecontenidotematico> result;
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
                String fecha = dateFormat.format(ract.getRACfechaElaboracion());  
        try{
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().executeProcedureTest("CALL agregarReporteavancecontenidotematico(\'" + ract.getRACnumero() +
            "\', \'" + fecha + "\', \'" + ract.getRACavanceGlobal() + "\' , \'" + ract.getRACstatus() + "\' , \'" +
            ract.getUnidadAprendizajeimparteprofesorUIPid().getUIPid() + "\')");
            return result.get(0);
        }catch(Exception e){
            System.err.println("Error en la captura de RACT: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina un RACT de la base de datos
     * @param ract a eliminar
     * @return verdadero o falso dependiendo si se elimino correctamente
     */
    public boolean deleteRACT(Reporteavancecontenidotematico ract){
        try{
            ServiceLocator.getInstanceReporteAvanceContenidoTematico().delete(ract);
            return true;
        }catch(Exception e){
            System.err.println("Error a la hora de borrar el RACT: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina un RACT de la base de datos
     * @param ractId id del ract a eliminar
     * @return verdadero o falso dependiendo si se elimino correctamente
     */
    public boolean deleteRACTById(int ractId){
        Reporteavancecontenidotematico ract = ServiceLocator.getInstanceReporteAvanceContenidoTematico().find(ractId);
        return this.deleteRACT(ract);
    }
    
    /**
     * Actualiza un RACT de la base de datos
     * @param ract la instancia de RACT a actualizar
     * @return verdadero o falso dependiendo si se actualizo correctamente
     */
    public Reporteavancecontenidotematico updateRACT(Reporteavancecontenidotematico ract){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
                String fecha = dateFormat.format(ract.getRACfechaElaboracion());  
               List<Reporteavancecontenidotematico> result; 
        try{
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().executeProcedureTest("CALL actualizarRACT(\'" + ract.getRACid() +
            "\', \'" + fecha + "\', \'" + ract.getRACavanceGlobal() + "\' , \'" + ract.getRACstatus() + "\')");
            return result.get(0);
        }catch(Exception e){
            System.err.println("Error en la captura de RACT: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene un RACT con un id en especifico
     * @param ractId el id del RACT a buscar
     * @return ract encontrado
     */
    public Reporteavancecontenidotematico findRACTById(int ractId){
        return ServiceLocator.getInstanceReporteAvanceContenidoTematico().find(ractId);
    }
    
        /**
     * Termina una transaccion
     * @param exitoso indica si la operacion fue exitosa al llamar este metodo
     */
    public void endTransaccion(boolean exitoso){
        ServiceLocator.getInstanceReporteAvanceContenidoTematico().endTransaction(exitoso);
    }
    
 
    /**
     * Metodo para encontrar un RACTs por numero y por Unidad de aprendizaje imparte profesor
     * @param unidadAprendizaje 
     * @param numero de ract
     * @param proId id de profesor
     * @param gpoNum numer de grupo
     * @param tipo tipo C, T, L....
     * @param ce Ciclo Escolar
     * @param uaip Unidad de Apredizaje Imparte Profesor
     * @return Racts encontrado
     */
    public List<Reporteavancecontenidotematico> findRACTByNumeroYUAIP(int unidadAprendizaje, String numero, int proId, int gpoNum, String tipo, String ce, int uaip){
        return ServiceLocator.getInstanceReporteAvanceContenidoTematico().findRACTByNumberAndUAIP(unidadAprendizaje, numero, proId, gpoNum, tipo, ce, uaip);
    }
    
//    private List<Reporteavancecontenidotematico> listaReporteavancecontenidotematico;
//    private List<Coordinadorareaadministrativa> listCordAreaAdmin;
//    public DelegateReporteAvanceContenidoTematico(){
//        
//    }
//    
//     public List<Reporteavancecontenidotematico> getListaReporteavancecontenidotematico() {
//        List<Reporteavancecontenidotematico> resultado = null;
//        
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findAll();
//        return resultado;
//        
//     }
//     
//      public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumeroConsultas(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo, String ce, int idUnidadSeleccionada) {
//        //Actualmente se consulta el ciclo escolar en esta sección 
//        //De ser posible cambiar a consultar solo una vez
////        ServiceLocator.getInstanceBaseDAO().setTipo(Unidadaprendizaje.class);
//               System.out.println("ENTRE AL DELEGATE " + "clave unidad"+id_unidadAprendizaje + "reporte "+numero + "profesor" +id_profesor + "grupo " +id_grupo + "subgrupo "+subGrupo +"tipo" + tipo + "CE "+ce + "id unidad " +idUnidadSeleccionada);
//       
//
//        List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += "racnumero='" + numero + "' ";//AND ";
//        //criterio += "conf.confechaInicioSemestre='" + fechaLimite + "'";
////            resultado = (Reporteavancecontenidotematico) ServiceLocator.getInstanceBaseDAO().findMultipleIDExtra(id_grupo,criterio,"unidadaprendizajeImparteProfesor.grupo.gponumero","order by racnumero desc","Reporteavancecontenidotematico");
//        //System.out.println("DATOS " + id_unidadAprendizaje + numero + id_profesor + id_grupo + subGrupo + tipo + fechaLimite + ce + idUnidadSeleccionada);
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematicoNumeroConsultas(id_unidadAprendizaje, numero, id_profesor, id_grupo, subGrupo, tipo, ce, idUnidadSeleccionada);
//        //(List) ServiceLocator.getInstanceBaseDAO().executeQuery("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//        if (resultado == null || resultado.size() < 1) {
//            return null;
//        }
//        return resultado.get(0);
//    }
//      public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumero(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo, String ce, int idUnidadSeleccionada) {
//
//        //Actualmente se consulta el ciclo escolar en esta sección 
//        //De ser posible cambiar a consultar solo una vez
//        //ServiceLocator.getInstanceBaseDAO().setTipo(Unidadaprendizaje.class);
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        //DelegateCicloEscolar cicloescolarDelegate = new DelegateCicloEscolar();
//        //DelegateConfiguracionSistema configuracionDelegate = new DelegateConfiguracionSistema();
//        String fechaLimite = "";
//
//        //Obtener CicloEscolarActual
//        Cicloescolar auxCicloEscolar;
//        auxCicloEscolar = ServiceLocator.getInstanceCicloEscolarDAO().cicloEscolarActual();
//        //cicloescolarDelegate.cicloescolarActual();
//        ConfiguracionSistemaDelegate configuracionDelegate= new ConfiguracionSistemaDelegate();
//        if (auxCicloEscolar == null) {
//        } else {
//            Configuracion auxConfiguracion =  configuracionDelegate.buscarConfiguracion(auxCicloEscolar.getCesid());
//            //configuracionDelegate.buscarConfiguracion(auxCicloEscolar.getCesid());
//            date = auxConfiguracion.getConfechaInicioSemestre();
//            fechaLimite = sdf.format(date);
//        }
//        //Fin de consulta de ciclo escolar.
//        //129 14006103 11291 C 0
//        List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        System.out.println(id_profesor+" "+id_grupo+" "+id_unidadAprendizaje+" "+tipo+""+subGrupo+"////////////////////////////////////////////////////PARAMETROS FACADE RACT");
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += "racnumero='" + numero + "' AND "; 
////        criterio += "conf.confechaInicioSemestre='" + fechaLimite + "'";
//
//        // System.out.println("Contenido de criterio: "+"select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//        System.out.println("DATOS " + id_unidadAprendizaje + numero + id_profesor + id_grupo + subGrupo + tipo + fechaLimite + ce + idUnidadSeleccionada);
//
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematicoNumero(id_unidadAprendizaje, numero, id_profesor, id_grupo, subGrupo, tipo, fechaLimite, ce, idUnidadSeleccionada);
//        //(List) ServiceLocator.getInstanceBaseDAO().executeQuery("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//        //System.out.println("///////////////////////////////////////////**************" + criterio + "--------------" + resultado.size());
//        if (resultado == null || resultado.size() < 1) {
//            System.out.println("////////////////////////////////////////EL RESULTADO ES NULL");
//            return null;
//        }
//        return resultado.get(0);
//    }
//      
//      
//     public Reporteavancecontenidotematico buscarReporteavancecontenidotematico(int id_unidadAprendizaje, int id_profesor, int id_grupo, String subGrupo, String tipo,String ce, int idUnidadSeleccionada) {
//        
//
//        List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += " conf.confechaInicioSemestre='" + fechaLimite + "'";
////
////        resultado = (List) ServiceLocator.getInstanceBaseDAO().executeQuery("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematico(id_unidadAprendizaje, id_profesor, id_grupo, subGrupo, tipo,ce,idUnidadSeleccionada);
//
//        if (resultado == null || resultado.size() < 1) {
//            return null;
//        }
//        return resultado.get(0);
//    }
//    
//     
//     public List<Reporteavancecontenidotematico> getListaUnidadAprendisajeFFWD(String de, String campo, String criterio, String de2, String campo2, String criterio2, String order) {
//        List<Reporteavancecontenidotematico> resultado = null;
//        //ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findFromWhereDoble(de, campo, criterio, de2, campo2, criterio2, order);
//        return resultado;
//    }
//     
//     public void setListaReporteavancecontenidotematico(List<Reporteavancecontenidotematico> listaReporteavancecontenidotematico) {
//        this.listaReporteavancecontenidotematico = listaReporteavancecontenidotematico;
//     }
//     
//     public void agregarReporteavancecontenidotematico(Reporteavancecontenidotematico reporteavancecontenidotematico) {
//        Reporteavancecontenidotematico resultado = null;
//        if (reporteavancecontenidotematico.getRacid() != null) {
//           // ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//            resultado = (Reporteavancecontenidotematico) ServiceLocator.getInstanceReporteAvanceContenidoTematico().find(reporteavancecontenidotematico.getRacid());
//        }
//        if (resultado != null) {
//            reporteavancecontenidotematico.setRacid(resultado.getRacid());
//            //ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//            ServiceLocator.getInstanceReporteAvanceContenidoTematico().saveOrUpdate(reporteavancecontenidotematico);
//        } else {
//            //ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//            ServiceLocator.getInstanceReporteAvanceContenidoTematico().save(reporteavancecontenidotematico);
//        }
//    }
//    //Modificado
//     public Reporteavancecontenidotematico buscarReporteavancecontenidotematico(int id_unidadAprendizaje, int id_profesor, int id_grupo, String subGrupo, String tipo) {
//       //ServiceLocator.getInstanceBaseDAO().setTipo(Unidadaprendizaje.class);
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//       // DelegateCicloEscolar cicloescolarDelegate = new DelegateCicloEscolar();
//        //ConfiguracionSistemaDelegate configuracionDelegate = new ConfiguracionSistemaDelegate();
//        String fechaLimite = "";
//
//        //Obtener CicloEscolarActual
//        Cicloescolar auxCicloEscolar;
//        auxCicloEscolar = ServiceLocator.getInstanceCicloEscolarDAO().cicloEscolarActual();
//                //cicloescolarDelegate.cicloEscolarActual();
//        ConfiguracionSistemaDelegate delegateConfiguracion= new ConfiguracionSistemaDelegate();
//
//        if (auxCicloEscolar == null) {
//        } else {
//             Configuracion auxConfiguracion = delegateConfiguracion.buscarConfiguracion(auxCicloEscolar.getCesid());
//                    //configuracionDelegate.buscarConfiguracion(auxCicloEscolar.getCesid());
//            date = auxConfiguracion.getConfechaInicioSemestre();
//            fechaLimite = sdf.format(date);
//        }
//        
//          List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += " conf.confechaInicioSemestre='" + fechaLimite + "'";
////
////        resultado = (List) ServiceLocator.getInstanceBaseDAO().executeQuery("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//          resultado= ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematico(id_unidadAprendizaje, id_profesor, id_grupo, subGrupo, tipo, fechaLimite);
//
//        if (resultado == null || resultado.size() < 1) {
//            return null;
//        }
//        return resultado.get(0);
//    }
//     //Modificado
//     public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumero(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo) {
//        
//             //Actualmente se consulta el ciclo escolar en esta sección 
//        //De ser posible cambiar a consultar solo una vez
//        //ServiceLocator.getInstanceBaseDAO().setTipo(Unidadaprendizaje.class);
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        //DelegateCicloEscolar cicloescolarDelegate = new DelegateCicloEscolar();
//        //DelegateConfiguracionSistema configuracionDelegate = new DelegateConfiguracionSistema();
//        String fechaLimite = "";
//        ConfiguracionSistemaDelegate configuracionDelegate= new ConfiguracionSistemaDelegate();
//        //Obtener CicloEscolarActual
//        Cicloescolar auxCicloEscolar;
//        auxCicloEscolar = ServiceLocator.getInstanceCicloEscolarDAO().cicloEscolarActual();
//                //cicloescolarDelegate.cicloescolarActual();
//        if (auxCicloEscolar == null) {
//        } else {
//            Configuracion auxConfiguracion = configuracionDelegate.buscarConfiguracion(auxCicloEscolar.getCesid());
//                    //configuracionDelegate.buscarConfiguracion(auxCicloEscolar.getCesid());
//            date = auxConfiguracion.getConfechaInicioSemestre();
//            fechaLimite = sdf.format(date);
//        }
//             //Fin de consulta de ciclo escolar.
//        //129 14006103 11291 C 0
//        List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        System.out.println(id_profesor+" "+id_grupo+" "+id_unidadAprendizaje+" "+tipo+""+subGrupo+"////////////////////////////////////////////////////PARAMETROS FACADE RACT");
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += "racnumero='" + numero + "' AND "; 
////        criterio += "conf.confechaInicioSemestre='" + fechaLimite + "'";
//               
//       // System.out.println("Contenido de criterio: "+"select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//     
////            resultado = (Reporteavancecontenidotematico) ServiceLocator.getInstanceBaseDAO().findMultipleIDExtra(id_grupo,criterio,"unidadaprendizajeImparteProfesor.grupo.gponumero","order by racnumero desc","Reporteavancecontenidotematico");
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematicoNumero(id_unidadAprendizaje, numero, id_profesor, id_grupo, subGrupo, tipo, fechaLimite);
//                //(List) ServiceLocator.getInstanceBaseDAO().executeQuery("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//        //System.out.println("///////////////////////////////////////////**************" + criterio + "--------------" + resultado.size());
//        if (resultado == null || resultado.size() < 1) {
//            System.out.println("////////////////////////////////////////EL RESULTADO ES NULL");
//            return null;
//        }
//        return resultado.get(0);
//    }
//     //Modificado
//     public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoNumeroConsultas(int id_unidadAprendizaje, String numero, int id_profesor, int id_grupo, String subGrupo, String tipo) {
//            //Actualmente se consulta el ciclo escolar en esta sección 
//        //De ser posible cambiar a consultar solo una vez
////        ServiceLocator.getInstanceBaseDAO().setTipo(Unidadaprendizaje.class);
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        //CicloEscolarDelegate cicloescolarDelegate = new CicloEscolarDelegate();
//        //DelegateConfiguracionSistema configuracionDelegate = new DelegateConfiguracionSistema();
//        String fechaLimite = "";
//        ConfiguracionSistemaDelegate configuracionDelegate = new ConfiguracionSistemaDelegate();
//        //Obtener CicloEscolarActual
//        Cicloescolar auxCicloEscolar;
//        auxCicloEscolar = ServiceLocator.getInstanceCicloEscolarDAO().cicloEscolarActual();
//        if (auxCicloEscolar == null) {
//        } else {
//            Configuracion auxConfiguracion = configuracionDelegate.buscarConfiguracion(auxCicloEscolar.getCesid());
//            date = auxConfiguracion.getConfechaInicioSemestre();
//            fechaLimite = sdf.format(date);
//        }
//             //Fin de consulta de ciclo escolar.
//
//        List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gponumero = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += "racnumero='" + numero + "' ";//AND ";
//   //criterio += "conf.confechaInicioSemestre='" + fechaLimite + "'";
////            resultado = (Reporteavancecontenidotematico) ServiceLocator.getInstanceBaseDAO().findMultipleIDExtra(id_grupo,criterio,"unidadaprendizajeImparteProfesor.grupo.gponumero","order by racnumero desc","Reporteavancecontenidotematico");
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematicoNumeroConsultas(id_unidadAprendizaje, numero, id_profesor, id_grupo, subGrupo, tipo, fechaLimite);
//                //(List) ServiceLocator.getInstanceBaseDAO().executeQuery("select distinct a from Reporteavancecontenidotematico a join a.unidadaprendizajeImparteProfesor.cicloescolar.configuracions conf where " + criterio + " order by racnumero desc");
//        if (resultado == null || resultado.size() < 1) {
//            return null;
//        }
//        return resultado.get(0);
//    }
//     //Modificado
//     public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoUltimo(int id_unidadAprendizaje, int id_profesor, int id_grupo, String tipo, String subGrupo) {
//         ServiceLocator.getInstanceBaseDAO().setTipo(Unidadaprendizaje.class);
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
////        CicloEscolarDelegate cicloescolarDelegate = new CicloEscolarDelegate();
////        DelegateConfiguracionSistema configuracionDelegate = new DelegateConfiguracionSistema();
//        String fechaLimite = "";
//        ConfiguracionSistemaDelegate delegateConfiguracion= new ConfiguracionSistemaDelegate();
//        //Obtener CicloEscolarActual
//        Cicloescolar auxCicloEscolar;
//        auxCicloEscolar = ServiceLocator.getInstanceCicloEscolarDAO().cicloEscolarActual();
//        if (auxCicloEscolar == null) {
//        } else {
////            Configuracion auxConfiguracion = (Configuracion) ServiceLocator.getInstanceConfiguracion().buscarConfiguracion(auxCicloEscolar.getCesid());
//            Configuracion auxConfiguracion = delegateConfiguracion.buscarConfiguracion(auxCicloEscolar.getCesid());
//            date = auxConfiguracion.getConfechaInicioSemestre();
//            fechaLimite = sdf.format(date);
//        }
//
//        List<Reporteavancecontenidotematico> resultado = null;
////        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        String criterio = "a.unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.grupo.gpoid = " + id_grupo + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uiptipoSubgrupo = '" + tipo + "' AND ";
////        criterio += "a.unidadaprendizajeImparteProfesor.uipsubgrupo = " + subGrupo + " AND ";
////        criterio += " conf.confechaInicioSemestre='" + fechaLimite + "'";
////            resultado = (Reporteavancecontenidotematico) ServiceLocator.getInstanceBaseDAO().findMultipleIDExtra(id_grupo,criterio,"unidadaprendizajeImparteProfesor.grupo.gponumero","order by racnumero desc","Reporteavancecontenidotematico");
//        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematicoUltimo(id_unidadAprendizaje, id_profesor, id_grupo, tipo, subGrupo, fechaLimite);
//        if (resultado == null || resultado.size() < 1) {
//            return null;
//        }
//        return resultado.get(0);
//    }
//     //Modificado
//     public Reporteavancecontenidotematico buscarReporteavancecontenidotematicoIncompleto(int id_unidadAprendizaje, int id_profesor, int id_grupo) {
//         Reporteavancecontenidotematico resultado = null;
////        //ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
////        String criterio = "unidadaprendizajeImparteProfesor.profesor.proid = " + id_profesor + " AND ";
//////                    criterio+="unidadaprendizajeImparteProfesor.grupo.gpoid = "+id_grupo+" AND ";
////        criterio += "unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave = " + id_unidadAprendizaje + " AND a.racstatus = 'parcial'";
//        resultado = (Reporteavancecontenidotematico) ServiceLocator.getInstanceReporteAvanceContenidoTematico().buscarReporteavancecontenidotematicoIncompleto(id_unidadAprendizaje, id_profesor, id_grupo);
//        if (resultado == null) {
//        }
//        return resultado;
//    }
//     
     public List<Reporteavancecontenidotematico> getReporteAvanceContenidoTematico(ReporteAux reporte) {
        List<Reporteavancecontenidotematico> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findByCriteria4(reporte);
        return resultado;
    }
//     
     public List<Areaconocimiento> getAreaConocimiento(int uapclave) {
         List<Areaconocimiento> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findFromWhereAreaCon(uapclave);
        return resultado;
    }
     
     public List<Calendarioreporte> getCalendarioreporte(Date confechainiciosemestre) {
       List<Calendarioreporte> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findFromWhereCalendReporte(confechainiciosemestre);
        return resultado;
    }
     
     public List<Configuracion> getConfiguracion(ReporteAux reporte) {
        List<Configuracion> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findByCriteria4(reporte);
        return resultado;
    }
//     
     public List<CalendarioreporteTieneAlerta> getCalendarioreporteTieneAlerta(ReporteAux reporte) {
        List<CalendarioreporteTieneAlerta> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findByCriteria4(reporte);
        return resultado;
    }
//     
    public List<UnidadaprendizajeImparteProfesor> getUnidadaprendizajeImparteProfesor(ReporteAux reporte) {
        List<UnidadaprendizajeImparteProfesor> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findByCriteria4(reporte);
        return resultado;
    }
//
    public List<Coordinadorareaadministrativa> getCoordAreaAdminProfUAprend(int uapclave) {
        List<Coordinadorareaadministrativa> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findByCriteriaDetalladoCordAreaAdminProfUAprend(uapclave);
        return resultado;
    }

    public List<Coordinadorareaadministrativa> getCoordAreaAdminProfUAprend(int uapclave, int pedclave, int aadid) {
        List<Coordinadorareaadministrativa> resultado = null;
        resultado = ServiceLocator.getInstanceReporteAvanceContenidoTematico().findByCriteriaDetalladoCordAreaAdminProfUAprend(uapclave, pedclave, aadid);
        return resultado;
    }
//
//    //Agregado 02-11-2015
//    //noReporte 0=todos
//    //Modificado
    public int countReportesDeProgramaEducativo(int idProgramaEducativo, int noReporte, String estado, List<String> selectCicloEscolarList) {
        int resultado = 0;
        resultado=ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeProgramaEducativo(idProgramaEducativo, noReporte, estado, selectCicloEscolarList);
        return resultado;
    }
//    //Modificado
    public int countEsperadosProgramaEducativo(int idProgramaEducativo, int noReporte, List<String> selectCicloEscolarList) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        String andEstado = "";
//        String andNumeroReporte = "";
//
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//
//        resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a ",
//                "a.grupo.planestudio.programaeducativo.pedid ", String.valueOf(idProgramaEducativo) + inCicloEscolar);
        resultado=ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosProgramaEducativo(idProgramaEducativo, noReporte, selectCicloEscolarList);
        return resultado;
    }
//    //Modificado
    public int countReportesDeAreaConocimientos(int areaConId, int noReporte, String estado, String PlanE, String idCicloEscolar) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String andJoin = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//
//        System.out.println("CONSULTA Para ce-------------------------\n" + inCicloEscolar);
//        if (noReporte != 0 && noReporte != 4) {
//            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
//        }
//
//        if (estado.equalsIgnoreCase("enviado")) {
//            andEstado = " AND a.racstatus='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("no enviado")) {
//            andEstado = " AND a.racstatus!='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("antesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
//        }
//        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
//        }
//
//        String andPeclave = "and a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//
//        resultado = ServiceLocator.getInstanceBaseDAO().count("Reporteavancecontenidotematico a join "
//                + "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.areaconocimientos b" + andJoin,
//                "b.acoid",
//                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + inCicloEscolar + andPeclave);
        resultado= ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeAreaConocimientos(areaConId, noReporte, estado, PlanE, idCicloEscolar);
        return resultado;
    }
//    //Modificado
    public int countEsperadosAreaConocimientos(int idProgramaEducativo, int noReporte, String PlanE, String pedClave) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        String andEstado = "";
//        String andNumeroReporte = "";
//
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//        String andPeclave = "and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//        resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a join "
//                + "a.unidadaprendizaje.areaconocimientos b",
//                "b.acoid ", String.valueOf(idProgramaEducativo) + inCicloEscolar + andPeclave);
        resultado=ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosAreaConocimientos(idProgramaEducativo, noReporte, PlanE, pedClave);
        return resultado;
    }
//    //Modificado
    public int countReportesDeUnidadAprendizaje(int idProgramaEducativo, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String andJoin = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//        if (noReporte != 0 && noReporte != 4) {
//            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
//        }
//
//        if (estado.equalsIgnoreCase("enviado")) {
//            andEstado = " AND a.racstatus='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("no enviado")) {
//            andEstado = " AND a.racstatus!='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("antesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
//        }
//        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
//        }
//
//        String andPeclave = " and a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//        resultado = ServiceLocator.getInstanceBaseDAO().count("Reporteavancecontenidotematico a " + andJoin,
//                "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapid",
//                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + inCicloEscolar + andPeclave);
          resultado=ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeUnidadAprendizaje(idProgramaEducativo, noReporte, estado, selectCicloEscolarList, pedClave);
          return resultado;
    }
//    //Modificado
    public int countEsperadosUnidadAprendizaje(int idProgramaEducativo, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//
//        String andPeclave = " and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//        resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a",
//                "a.unidadaprendizaje.uapid ", String.valueOf(idProgramaEducativo) + inCicloEscolar + andPeclave);
        resultado=ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosUnidadAprendizaje(idProgramaEducativo, noReporte, selectCicloEscolarList, pedClave);
        return resultado; 
    }
//    //Modificado
    public int countReportesDeUnidadAprendizaje(int idProgramaEducativo, int noReporte, String estado, List<String> grupos, List<String> selectCicloEscolarList) {
        int resultado = 0;
//        String andGrupos = " ";
//        if (grupos.size() > 0) {
//            andGrupos = " AND a.unidadaprendizajeImparteProfesor.grupo.gponumero IN (";
//            for (String grupo : grupos) {
//                andGrupos += grupo + ",";
//            }
//            andGrupos = andGrupos.substring(0, andGrupos.length() - 1);
//            andGrupos += ")";
//        }
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String andJoin = "";
//
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//        if (noReporte != 0 && noReporte != 4) {
//            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
//        }
//
//        if (estado.equalsIgnoreCase("enviado")) {
//            andEstado = " AND a.racstatus='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("no enviado")) {
//            andEstado = " AND a.racstatus!='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("antesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
//        }
//        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
//        }
//
//        resultado = ServiceLocator.getInstanceBaseDAO().count("Reporteavancecontenidotematico a " + andJoin,
//                "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapid",
//                "'" + String.valueOf(idProgramaEducativo) + "'" + andNumeroReporte + andEstado + andGrupos + inCicloEscolar);
        resultado=ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeUnidadAprendizaje(idProgramaEducativo, noReporte, estado, grupos, selectCicloEscolarList);
        return resultado;
    }
//
    public int countEsperadosUnidadAprendizaje(int idProgramaEducativo, int noReporte, List<String> grupos, List<String> selectCicloEscolarList) {
        int resultado = 0;
//        String andGrupos = " ";
//        if (grupos.size() > 0) {
//            andGrupos = " AND a.grupo.gponumero IN (";
//            for (String grupo : grupos) {
//                andGrupos += grupo + ",";
//            }
//            andGrupos = andGrupos.substring(0, andGrupos.length() - 1);
//            andGrupos += ")";
//        }
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        String andEstado = "";
//        String andNumeroReporte = "";
//        resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a",
//                "a.unidadaprendizaje.uapid ", String.valueOf(idProgramaEducativo) + andGrupos + inCicloEscolar);
        resultado=ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosUnidadAprendizaje(idProgramaEducativo, noReporte, grupos, selectCicloEscolarList);
        return resultado;
    }
//
    public int countReportesDeProfesor(String idProfesor, int noReporte, String estado, String extra, List<String> selectCicloEscolarList) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String andJoin = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//        if (noReporte != 0 && noReporte != 4) {
//            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
//        }
//
//        if (estado.equalsIgnoreCase("enviado")) {
//            andEstado = " AND a.racstatus='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("no enviado")) {
//            andEstado = " AND a.racstatus!='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("antesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
//        }
//        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
//        }
//
//        resultado = ServiceLocator.getInstanceBaseDAO().count("Reporteavancecontenidotematico a " + andJoin,
//                "a.unidadaprendizajeImparteProfesor.profesor.proid",
//                "'" + String.valueOf(idProfesor) + "'" + andNumeroReporte + andEstado + extra + inCicloEscolar);
//        System.out.println("--------------------------------------------Reporteavancecontenidotematico a ---" + andJoin
//                + "a.unidadaprendizajeImparteProfesor.profesor.proid---"
//                + "----'" + String.valueOf(idProfesor) + "'" + andNumeroReporte + andEstado + extra + inCicloEscolar);
//        
        resultado=ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeProfesor(idProfesor, noReporte, estado, extra, selectCicloEscolarList);
        return resultado;
    }

    public int countEsperadosProfesor(int idProfesor, int noReporte, String extra, List<String> selectCicloEscolarList) {
         int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//        resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a",
//                "a.profesor.proid",
//                "'" + String.valueOf(idProfesor) + "'" + extra + inCicloEscolar);
        resultado=ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosProfesor(idProfesor, noReporte, extra, selectCicloEscolarList);
        return resultado;
    }

    public int countReportesDeAreaAdmin(int idAreaAdmin, int noReporte, String estado, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporteavancecontenidotematico.class);
//
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String andJoin = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.unidadaprendizajeImparteProfesor.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//
//        if (noReporte != 0 && noReporte != 4) {
//            andNumeroReporte = " AND a.racnumero='" + noReporte + "'";
//        }
//
//        if (estado.equalsIgnoreCase("enviado")) {
//            andEstado = " AND a.racstatus='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("no enviado")) {
//            andEstado = " AND a.racstatus!='enviado' ";
//        }
//        if (estado.equalsIgnoreCase("antesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus='enviado' AND a.racnumero=cast(e.calnumeroReporte as string) AND a.racfechaElaboracion<=d.crefechaLimite";
//        }
//        if (estado.equalsIgnoreCase("despuesFechaLimite")) {
//            andJoin = " join a.unidadaprendizajeImparteProfesor.unidadaprendizaje.cicloescolar.configuracions c join c.calendarioreportes d join d.calendarioreporteTieneAlertas e";
//            andEstado = " AND a.racstatus!='enviado' AND a.racnumero=e.calnumeroReporte AND a.racfechaElaboracion>d.crefechaLimite";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeCompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal='100'";
//        }
//
//        if (estado.equalsIgnoreCase("porcentajeIncompleto")) {
//            andJoin = " ";
//            andEstado = " AND a.racstatus='enviado' AND a.racavanceGlobal<'100'";
//        }
//
//        String andPeclave = " and a.unidadaprendizajeImparteProfesor.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//
//        resultado = ServiceLocator.getInstanceBaseDAO().count("Reporteavancecontenidotematico a join "
//                + "a.unidadaprendizajeImparteProfesor.unidadaprendizaje.coordinadorareaadministrativas b" + andJoin,
//                "b.areaadministrativa.aadid",
//                "'" + String.valueOf(idAreaAdmin) + "'" + andNumeroReporte + andEstado + inCicloEscolar + andPeclave);
        resultado=ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeAreaAdmin(idAreaAdmin, noReporte, estado, selectCicloEscolarList, pedClave);
        return resultado;
    }

    public int countEsperadosAreaAdmin(int idAreaAdmin, int noReporte, List<String> selectCicloEscolarList, String pedClave) {
        int resultado = 0;
//        ServiceLocator.getInstanceBaseDAO().setTipo(UnidadaprendizajeImparteProfesor.class);
//        String andEstado = "";
//        String andNumeroReporte = "";
//        String inCicloEscolar = "";
//
//        if (selectCicloEscolarList.size() > 0) {
//            inCicloEscolar = " and a.cicloescolar.cescicloEscolar in(";
//            for (String cicloEscolar : selectCicloEscolarList) {
//                inCicloEscolar += "'" + cicloEscolar + "',";
//            }
//
//            inCicloEscolar = inCicloEscolar.substring(0, inCicloEscolar.length() - 1);
//            inCicloEscolar += ")";
//        }
//
//        String andPeclave = " and a.grupo.planestudio.programaeducativo.pedclave='" + pedClave + "' ";
//
//        resultado = ServiceLocator.getInstanceBaseDAO().count("UnidadaprendizajeImparteProfesor a join "
//                + "a.unidadaprendizaje.coordinadorareaadministrativas b",
//                "b.areaadministrativa.aadid ", String.valueOf(idAreaAdmin) + inCicloEscolar + andPeclave);
        resultado=ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosAreaAdmin(idAreaAdmin, noReporte, selectCicloEscolarList, pedClave);
        return resultado;
    }

    public double countPorcentajeGeneralAfechaActual(int idUnidadAcademica) {
        return 0;
        
    }
//    

    /**
      * Metodo para traer los RACTS por programa educativo
      * @param programasEducativos
      * @param ract
      * @param reporte
      * @param cicloEscolar
      * @return lista de RACTs
      */
    public List<Object[]> getReportesPrograma(List<String> programasEducativos,String ract
                        ,String reporte, List<String> cicloEscolar){
        return ServiceLocator.getInstanceReporteAvanceContenidoTematico().getReportesPrograma(programasEducativos, ract, reporte, cicloEscolar);
    }
//
    public int countEsperadosProfesor(Integer idProfesor, int noReporte, List<String> selectGrupo, List<String> selectUnidadAprendizaje, List<String> selectCicloEscolarList) {
        return ServiceLocator.getInstanceUnidadAprendizajeImparteProfesor().countEsperadosProfesor(idProfesor, noReporte,selectGrupo,selectUnidadAprendizaje , selectCicloEscolarList);
        
    }

    public int countReportesDeProfesor(int idProfesor, int noReporte, String estado, List<String> selectGrupo, List<String> selectUnidadAprendizaje, List<String> selectCicloEscolarList) {
        return ServiceLocator.getInstanceReporteAvanceContenidoTematico().countReportesDeProfesor(idProfesor, noReporte, estado, selectGrupo,selectUnidadAprendizaje, selectCicloEscolarList);
        
    }
    
    
    /**
     * Metodo para obtener los reportes entregados por programa educativo
     *  para el generador de reportes
     * segun el criterio de busqueda que se establecio
     *
     * @param criterio tipo de busqueda, entregados, no entregados, porcentaje
     * completo...etc
     * 
     * @param idPlan id del plan de estudios del programa educativo, puesto que
     * tambien se separa por plan de estudios en el caso del filtro de PE
     * 
     * @param noRact numero de ract al que corresponde si es 4 seran todos 
     * 
     * @param idCicloEscolar id del ciclo escolar de la busqueda
     * 
     * @param esperados el numero de esperados para calculos internos
     * @return
     */
    public int ConteoPorProgramaEducativo(String criterio, int idPlan, String noRact, int idCicloEscolar,int esperados) {
        return ServiceLocator.getInstanceReporteAvanceContenidoTematico().ConteoPorProgramaEducativo(criterio, idPlan, noRact, idCicloEscolar, esperados);
    }
    
    
    /**
     * Metodo para traer el conteo de reportes por criterio Unidad de aprendizaje
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param idUnidad
     * @param esperados
     * @return 
     */
    public int ConteoPorUnidadAprendizaje(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados,int idUnidad) {
          List<BigInteger> result;
          int entregados = 0;
        try{
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().StoredProcedureReporteAvanceContenidoTematico("CALL conteoPorUnidadAprendizaje(\'" + criterio +
            "\', \'" + idPrograma+ "\', \'" + noRact + "\' , \'" + idCicloEscolar + "\' , \'" + idUnidad + "\' , \'" + esperados + "\')");
             entregados = Integer.parseInt(result.get(0).toString());

        return entregados;
        }catch(Exception e){
            System.err.println("Al Consultar conteo por unidad aprendizaje: " + e.getMessage());
            e.printStackTrace();
            return -1;
    }
    }  
   public int ConteoPorAreaConocimineto(String criterio, int idPlan, String noRact, int CicloEscolar, int esperados, int areaConId){
        //return ServiceLocator.getInstanceReporteAvanceContenidoTematico().ConteoPorAreaConocimiento(criterio, idPlan, noRact, CicloEscolar, esperados, areaConId);
        List<BigInteger> result = null;
        int entregados = 0;
        try{
            System.out.println("CALL conteoPorAreaConocimimento(\'" + criterio + "\', \'" + idPlan + "\', \'" + noRact + "\', \'" + CicloEscolar + "\', \'" + esperados + "\', \'" + areaConId + "\')");
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().StoredProcedureReporteAvanceContenidoTematico("CALL conteoPorAreaConocimimento(\'" + criterio + "\', \'" + idPlan + "\', \'" + noRact + "\', \'" + CicloEscolar + "\', \'" + esperados + "\', \'" + areaConId + "\')");
            entregados = Integer.parseInt(result.get(0).toString());
        }catch(Exception e){
            System.out.println("ERROR CALL en conteoPorAreaConocimiento");
            System.out.println(e);
        }
        return entregados;
    }

    /***
     * MEtodo para obtener los entregados segun el criterio por area Admin
     * si es tronco comun incluira grupos que no son de tronco comun
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idArea
     * @return 
     */
   public int conteoPorAreaAdministrativa(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados, int idArea) {
       //return ServiceLocator.getInstanceReporteAvanceContenidoTematico().conteoPorAreaAdministrativa(criterio, idPrograma, noRact, idCicloEscolar, esperados, idArea);
       List<BigInteger> result = null;
        int entregados = 0;
        try{
            //System.out.println("CALL conteoPorAreaConocimimento(\'" + criterio + "\', \'" + idPlan + "\', \'" + noRact + "\', \'" + CicloEscolar + "\', \'" + esperados + "\', \'" + areaConId + "\')");
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().StoredProcedureReporteAvanceContenidoTematico("CALL conteoPorAreaAdministrativa(\'" + criterio + "\', \'" + idPrograma + "\', \'" + noRact + "\', \'" + idCicloEscolar + "\', \'" + esperados + "\', \'" + idArea + "\')");
            entregados = Integer.parseInt(result.get(0).toString());
        }catch(Exception e){
            System.out.println(e);
        }
        return entregados;
    }
    

    /***
     * Metodo para traer conteo de racts segun el criterio, si isAdmin is true
     * contara todos los grupos que le pertencen al profesor de lo contrario
     * solo contara los grupos del programa educativo 
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param isAdmin
     * @param idProfesor
     * @return 
     */
    public int ConteoPorProfesor(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados, boolean isAdmin, int idProfesor) {
         List<BigInteger> result;
          int entregados = 0;
          int admin;
          if(isAdmin == true){
              admin= 1;
          }else{
              admin= 0;
          }
        try{
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().StoredProcedureReporteAvanceContenidoTematico("CALL conteoPorProfesor(\'" + criterio +
            "\', \'" + idPrograma+ "\', \'" + noRact + "\' , \'" + idCicloEscolar + "\' , \'" + idProfesor + "\' , \'" + esperados + "\' , \'" + admin + "\')");
             entregados = Integer.parseInt(result.get(0).toString());

        return entregados;
        }catch(Exception e){
            System.err.println("Al Consultar conteo por profesor: " + e.getMessage());
            e.printStackTrace();
            return -1;
    }
    }
    
    /**
     *  Conteo de reporte entregados por profesor que pertenecen al area administrativa
     * que coordina el profesor 
     * @param criterio
     * @param idPrograma
     * @param noRact
     * @param idCicloEscolar
     * @param esperados
     * @param idProfesorCoordinador
     * @param idProfesor
     * @return 
     */
   public int conteoPorProfesorCoordinador(String criterio, int idPrograma, String noRact, int idCicloEscolar,int esperados,int idProfesorCoordinador, int idProfesor){
       //return ServiceLocator.getInstanceReporteAvanceContenidoTematico().conteoPorProfesorCoordinador(criterio, idPrograma, noRact, idCicloEscolar, esperados, idProfesorCoordinador, idProfesor);
       List<BigInteger> result = null;
        int entregados = 0;
        try{
            //System.out.println("CALL conteoPorAreaConocimimento(\'" + criterio + "\', \'" + idPlan + "\', \'" + noRact + "\', \'" + CicloEscolar + "\', \'" + esperados + "\', \'" + areaConId + "\')");
            result = ServiceLocator.getInstanceReporteAvanceContenidoTematico().StoredProcedureReporteAvanceContenidoTematico("CALL conteoPorProfesorCoordinador(\'" + criterio + "\', \'" + idPrograma + "\', \'" + noRact + "\', \'" + idCicloEscolar + "\', \'" + esperados + "\', \'" + idProfesorCoordinador + "\', \'" + idProfesor + "\')");
            entregados = Integer.parseInt(result.get(0).toString());
        }catch(Exception e){
            System.out.println(e);
        }
        return entregados;
    }

}

