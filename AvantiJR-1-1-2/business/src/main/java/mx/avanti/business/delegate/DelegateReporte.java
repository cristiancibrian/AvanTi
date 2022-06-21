/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Reporte;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Alex
 * @author Mario | Refactor
 */
public class DelegateReporte {
    
    /**
     * Obtiene todos los reportes
     * @return 
     */
    public List<Reporte> findAll(){
        return ServiceLocator.getInstanceReporte().findAll();
    }
    
    /**
     * Guarda el reporte en la base de datos
     * @param r la instancia del reporte a guardar
     * @return 
     */
    public boolean save(Reporte r){
        List<Reporte> result;
        int impartido = r.getImpartido() ? 1 : 0;
        try{
            result = ServiceLocator.getInstanceReporte().executeProcedureTest("CALL agregarReporte(\'" + r.getReportePK().getREPid() +
            "\', \'" + r.getReportePK().getUNIid() + "\', \'" + r.getReportePK().getTUNid() + "\' , \'" + r.getReportePK().getSUTid() + "\' , \'" +
            r.getReportePK().getPRLid() + "\' , \'" + r.getReportePK().getPRTid()  + "\' , \'" +  r.getReportePK().getPRCid() + "\' , \'" + r.getReporteAvanceContenidoTematicoRACid().getRACid()
                   + "\' , \'" + r.getREPobservacion() + "\' , \'" + impartido + "\')");
            return true;
        }catch(Exception e){
            System.err.println("Error en la captura de reporte: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Borra el reporte de la base de datos
     * @param r la instancia del reporte a borrar
     * @return 
     */
    public boolean delete(Reporte r){
        List<Reporte> result;
        int impartido = r.getImpartido() ? 1 : 0;
        try{
            result = ServiceLocator.getInstanceReporte().executeProcedureTest("CALL eliminarReporte(\'" + r.getReportePK().getREPid() +
            "\', \'" + r.getReportePK().getUNIid() + "\', \'" + r.getReportePK().getTUNid() + "\' , \'" + r.getReportePK().getSUTid() + "\' , \'" +
            r.getReportePK().getPRLid() + "\' , \'" + r.getReportePK().getPRTid()  + "\' , \'" +  r.getReportePK().getPRCid() + "\' , \'" + r.getReporteAvanceContenidoTematicoRACid().getRACid()
                   + "\' , \'" + r.getREPobservacion() + "\' , \'" + impartido + "\')");
            return true;
        }catch(Exception e){
            System.err.println("Error en la hora de borrar el reporte: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Borra el reporte de la base de datos
     * @param repId el id del reporte a borrar
     * @return 
     */
    public boolean deleteById(int repId){
        Reporte r = ServiceLocator.getInstanceReporte().find(repId);
        return this.delete(r);
    }
    
    /**
     * Obntiene un reporte con un id en especifico
     * @param repId el id del reporte a buscar
     * @return 
     */
    public Reporte findById(int repId){
        return ServiceLocator.getInstanceReporte().find(repId);
    }
    
    /**
     * Actualiza el reporte de la base de datos
     * @param r la instancia del reporte a actualizar
     * @return 
     */
    public boolean update(Reporte r){
          List<Reporte> result;
        int impartido = r.getImpartido() ? 1 : 0;
        try{
            result = ServiceLocator.getInstanceReporte().executeProcedureTest("CALL actualizarReporte(\'" + r.getReportePK().getREPid() +
            "\', \'" + r.getReportePK().getUNIid() + "\', \'" + r.getReportePK().getTUNid() + "\' , \'" + r.getReportePK().getSUTid() + "\' , \'" +
            r.getReportePK().getPRLid() + "\' , \'" + r.getReportePK().getPRTid()  + "\' , \'" +  r.getReportePK().getPRCid() + "\' , \'" + r.getReporteAvanceContenidoTematicoRACid().getRACid()
                   + "\' , \'" + r.getREPobservacion() + "\' , \'" + impartido + "\')");
            return true;
        }catch(Exception e){
            System.err.println("Error al actualizar el reporte: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene los reportes actuales y anteriores
     * de una unidad aprendizaje imparte profesor
     * @param tipoGrupo el tipo de grupo (C, L, T)
     * @param ce ciclo escolar
     * @param ractNumero el numero del ract
     * @param uip_id id unidadaprendizaje_imparte_profesor
     * @return
     */
    public List<Reporte> getLatest(String tipoGrupo, String ce, String ractNumero, int uip_id){
         List<Reporte> result;
         try{
            result = ServiceLocator.getInstanceReporte().executeProcedureTest("CALL consultarReportesActualesYAnteriores(\'" + tipoGrupo +
            "\', \'" + ce + "\', \'" + uip_id + "\' , \'" + ractNumero +"\')");
            return result;
        }catch(Exception e){
            System.err.println("Error al actualizar el reporte: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
  
    /**
     * Obtiene el reporte que tenga un nodo en especifico
     * @param idNodo el nodo del reporte a buscar
     * @param tipoNodo el tipo de nodo
     * @param ractId el id del ract
     * @return Reporte consultado
     */
    public Reporte findByRACTAndNode(int idNodo, String tipoNodo, int ractId){
        return this.findByRACTAndNode(idNodo, tipoNodo, ractId, 0);
    }
    
    /**
     * Buscar los reportes anteriores usando el puente
     * de unidad de aprendizaje imparte profesor
     * @param idNodo
     * @param tipoNodo
     * @param ractId
     * @param uipId
     * @return 
     */
    public Reporte findByRACTAndNode(int idNodo, String tipoNodo, int ractId, int uipId) {
        Reporte resultado = null;
        if (idNodo != 0) {
           
            String deReporte = "";
            String clase = "Reporte";
             if (ractId == 0) {
                return null;
            }
             
            if(uipId == 0){
                deReporte = "reporteavancecontenidotematico.racid =" + ractId;
            }else{
                deReporte = "reporteavancecontenidotematico.racid!="+ractId+" AND reporteavancecontenidotematico.unidadaprendizajeImparteProfesor.uipid =" + uipId;
            }
            
            
            //ESTA PARTE CAUSA CONFLICTOS AL GUARDAR, DEBIDO AL TIPO DE ID DEL OBJHETO REPORTE
            switch (tipoNodo) {
                case "Unidad":
                    //SE AÃ‘ADE AND CUANDO HAY CONDICIONES ANTES DEL REPORTE
//                    if (id_reporte != 0) {
//                        deReporte = " AND " + deReporte;
//                    }
                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, "id.tunid = 0 AND id.sutid=0 AND " + deReporte, "id.uniid", "", clase);
                    break;
                case "Tema":
//                    if (id_reporte != 0) {
//                        deReporte = " AND " + deReporte;
//                    }
                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, "id.sutid = 0 AND " + deReporte, "id.tunid", "", clase);
                    break;
                case "Subtema":
                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.sutid", "", clase);
                    break;
                case "practicaTaller":
                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.prtid", "", clase);

                    break;
                case "practicaLaboratorio":
                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.prlid", "", clase);

                    break;
                case "PracticaClase":
                    break;
                      case "PracticaCampo":
                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.prcid", "", clase);
                    break;
                    
                    
            }
        }
        
        return resultado;
   
    }

    /**
     * Método para obtener los reportes dependiendo de una práctica de laboratorio
     * @param pRLid
     * @return 
     */
    public List<Reporte> findByPRLid(int pRLid){
        return ServiceLocator.getInstanceReporte().executeQuery("SELECT * FROM reporte WHERE PRLid = "+pRLid);
    }
    
    /**
     * Método para obtener los reportes dependiendo de una práctica de taller
     * @param pRTid
     * @return 
     */
    public List<Reporte> findByPRTid(int pRTid){
        return ServiceLocator.getInstanceReporte().executeQuery("SELECT * FROM reporte WHERE PRTid = "+pRTid);
    }
    
    /**
     * Método para obtener los reportes dependiendo de una unidad
     * @param uNIid
     * @return 
     */
    public List<Reporte> findByUNIid(int uNIid){
        return ServiceLocator.getInstanceReporte().executeQuery("SELECT * FROM reporte WHERE UNIid = "+uNIid);
    }
    
    /**
     * Método para obtener todos los reportes que tiene una misma unidad de aprendizaje, independientemente
     * del profesor asignado
     * @param uIPid id de la unidad de aprendizaje
     * @param tiposubgrupo
     * @return
     */
    public List<Reporte> consultarUltimosReportesPorUnidad(int uIPid, String tiposubgrupo){
        return ServiceLocator.getInstanceReporte().executeQuery("select * from reporte where ReporteAvanceContenidoTematico_RACid in"
                                                            + "(select RACid from reporteavancecontenidotematico where UnidadAprendizaje_imparte_profesor_UIPid in "
                                                            + "(select  UIPid from unidadaprendizaje_imparte_profesor where UnidadAprendizaje_UAPid = "
                                                            + "(select UAPid from unidadaprendizaje where UAPid = "+uIPid+") and CicloEscolar_CESid = "
                                                                    + "(select max(CESid) from cicloescolar) AND UIPtipoSubgrupo = \""+tiposubgrupo+"\"));");
    }
//    
//    
//    /**
//     * 
//     * @param idNodo
//     * @param tipoNodo
//     * @param reporteActual
//     * @param idUaip
//     * @return Reporte consultado
//     */ 
//    public Reporte consultaCheckBoxReportesAnteriores(int idNodo, String tipoNodo,int reporteActual, int idUaip) {
//        Reporte resultado = null;
//        if (idNodo != 0) {
//            //ServiceLocator.getInstanceBaseDAO().setTipo(Reporte.class);
//            String deReporte = "";
//            String clase = "Reporte";
//             if (idUaip == 0) {
//                return null;
//            }
////            if (id_reporte != 0) {
//             System.out.println("reporteavancecontenidotematico.racid!="+reporteActual);
//                deReporte = "reporteavancecontenidotematico.racid!="+reporteActual+" AND reporteavancecontenidotematico.unidadaprendizajeImparteProfesor.uipid =" + idUaip;
////            }
//            
//            //ESTA PARTE CAUSA CONFLICTOS AL GUARDAR, DEBIDO AL TIPO DE ID DEL OBJHETO REPORTE
//            switch (tipoNodo) {
//                case "Unidad":
//                    //SE AÑADE AND CUANDO HAY CONDICIONES ANTES DEL REPORTE
////                    if (id_reporte != 0) {
////                        deReporte = " AND " + deReporte;
////                    }
//                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, "id.tunid = 0 AND id.sutid=0 AND " + deReporte, "id.uniid", "", clase);
//                    break;
//                case "Tema":
////                    if (id_reporte != 0) {
////                        deReporte = " AND " + deReporte;
////                    }
//                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, "id.sutid = 0 AND " + deReporte, "id.tunid", "", clase);
//                    break;
//                case "Subtema":
//                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.sutid", "", clase);
//                    break;
//                case "practicaTaller":
//                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.prtid", "", clase);
//
//                    break;
//                case "practicaLaboratorio":
//                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.prlid", "", clase);
//
//                    break;
//                case "PracticaClase":
//                    break;
//                      case "PracticaCampo":
//                    resultado = (Reporte) ServiceLocator.getInstanceReporte().findMultipleIDExtra(idNodo, deReporte, "id.prcid", "", clase);
//                    break;
//                    
//                    
//            }
//        }
//        return resultado;
//   
//    }
//    
//    /**
//     * 
//     * @param idReporteAvance 
//     */
//    public void eliminaReportesDeReporteDeAvanceContenidoTematico(int idReporteAvance) {
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporte.class);
//        ServiceLocator.getInstanceBaseDAO().deleteWhere("Reporte","repobservacion=\'\' AND reporteavancecontenidotematico.racid", String.valueOf(idReporteAvance));
//        ServiceLocator.getInstanceBaseDAO().updateWhere("Reporte","impartido", "0", "reporteavancecontenidotematico.racid", String.valueOf(idReporteAvance));
//    }
//    
//    /**
//     * 
//     * @param idReporteAvance 
//     */
//    public void updateDeReporteDeAvanceContenidoTematico(int idReporteAvance) {
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporte.class);
//        ServiceLocator.getInstanceBaseDAO().updateWhere("Reporte","impartido", "0", "reporteavancecontenidotematico.racid", String.valueOf(idReporteAvance)+" AND repobservacion!=''");
//    }
//    
//    /**
//     * 
//     * @param reporte 
//     */
//    public void eliminar(Reporte reporte) {
//        //ServiceLocator.getInstanceBaseDAO().setTipo(Reporte.class);
//        ServiceLocator.getInstanceReporte().delete(reporte);
//    }
//    
//    /**
//     * 
//     * @param idReporteAvance
//     * @return Lista de reporte consultada 
//     */
//    public List<Reporte>  consultaReportesDeReporteDeAvanceContenidoTematico(int idReporteAvance) {
//        ServiceLocator.getInstanceBaseDAO().setTipo(Reporte.class);
//        
//        
//         List<Reporte> resultado = null;
//        resultado = ServiceLocator.getInstanceBaseDAO().findFromWhereBExtra("reporteavancecontenidotematico", "racid", String.valueOf(idReporteAvance),"id","Reporte");
//        return resultado;
//        
//        
//    }
//    /**
//     *  Este metodo ya lo utiliza anteriormente para verifivar los reportes
//     * anteriores , se modifico para traer el mas reciente  y vericar si 
//     * esta impartido o no.
//     * @param idNodo id del tema , unidad subtemas o practicas
//     * @param tipoNodo que tipo es
//     * @param reporteActual a que reporte pertenece
//     * @param idUaip id de la unidad de aprendizaje
//     * @return lista de los reportes correspondientes al id, ordenada de mas
//     * viejo a mas nuevo
//     */
//    public List<Reporte> consultaCheckBoxReportesAnterioresNuevo (int idNodo, String tipoNodo,int reporteActual, int idUaip){
//        
//        List<Reporte> resultado = null;
//        
//        if (idNodo != 0) {
//            //ServiceLocator.getInstanceBaseDAO().setTipo(Reporte.class);
//            String deReporte = "";
//            String clase = "Reporte";
//             if (idUaip == 0) {
//                return null;
//            }      
//            
//            //case para realizar el query de busqueda de ultimo ract realizado por tipo de nodo 
//            switch (tipoNodo) {
//                case "Unidad":
//                    resultado= ServiceLocator.getInstanceReporte().UltimoReporteUnidad(idNodo, tipoNodo, reporteActual, idUaip);
//
//                    break;
//                case "Tema":
//                    resultado= ServiceLocator.getInstanceReporte().UltimoReporteTemaUnidad(idNodo, tipoNodo, reporteActual, idUaip);
//                            
//                    
//                    break;
//                case "Subtema":
//                    resultado = ServiceLocator.getInstanceReporte().UltimoReporteSubTemaUnidad(idNodo, tipoNodo, reporteActual, idUaip);
//                            
//                    break;
//                case "practicaTaller":
//                        resultado = ServiceLocator.getInstanceReporte().UltimoPracticasTaller(idNodo, tipoNodo, reporteActual, idUaip);
//                                
//                    break;
//                case "practicaLaboratorio":
//                        resultado = ServiceLocator.getInstanceReporte().UltimoReportePracticasLaboratorio(idNodo, tipoNodo, reporteActual, idUaip);
//                                
//                    break;
//                case "PracticaClase":
//                    break;
//                case "PracticaCampo":
//                        resultado = ServiceLocator.getInstanceReporte().UltimoReportePracticasCampo(idNodo, tipoNodo, reporteActual, idUaip);
//                                             
//                break;
//                    
//                    
//            }
//        
//       
//        }
//        return resultado;
//        
//        
//                
//    }
//    
}
