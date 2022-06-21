package mx.avanti.business.delegate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.integration.ServiceLocator;
/**
 * 
 * @author Javier Razo
 */
public class DelegatePlanEstudio implements Serializable {
    
    private List<Planestudio> listaPlanEstudio;

    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }
    /**
     * Busca todos los planes de estudio y los guarda en una Lista de tipo 
     * Plan de estudio
     * @return List<Planestudio> Lista con objetos de tipo Planestudio
     */
    public List<Planestudio> findAllPlanEstudio(){
        List<Planestudio> result = null;
        result = ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL verPlan()");
        return result;
    } 
    /**
     * Guarda un Objeto de tipo Planestudio
     * @param plan  tipo Planestudio
     */
    public void savePlanEstudio(Planestudio plan){
        ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL guardarPlan(\'" + plan.getPESvigenciaPlan()+ "\', \'" + plan.getPEScreditosObligatorios()+"\',\'"+
                                                                                            plan.getPEScreditosOptativos() +"\',\'" + plan.getPEStotalCreditos() + "\',\'" + 
                                                                                            plan.getProgramaEducativoPEDid().getPEDid() + "\')"); 
    } 
     /**
    * Actualiza la informacion de un plan de estudio
    * @param plan 
    */
   public void updatePE(Planestudio plan){
       ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL actualizarPlan(\'" + plan.getPESid() + "\',\'" + plan.getPESvigenciaPlan()+ "\',\'" + plan.getPEScreditosObligatorios()+"\',\'"+
                                                                                            plan.getPEScreditosOptativos() +"\',\'" + plan.getPEStotalCreditos() + "\',\'" + 
                                                                                            plan.getProgramaEducativoPEDid().getPEDid() + "\')"); 
    }
    /**
     * Elimina un Plan de estudio
     * @param planestudio tipo Planestudio
     */
    public void deletePlanEstudio(Planestudio planestudio) {
        ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL eliminarPlan(\'" + planestudio.getPESid() + "\')");
    }
   /**
     * Busca un plan de estudio por su id  y lo regresa
     * @param id tipo int
     * @return Objeto tipo Planestudio
     */
    public Planestudio findByPlanEstudioId(int id) {
        
        List<Planestudio> result = null;
        result = ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL buscarPlanEstudioID(\'" + id + "\')");
        
            Planestudio plan = new Planestudio();
            plan = result.get(0);
            
        return plan;
        
    }
    /**
     * Busca los planes de estudio que pertenecen a un programa educativo
     * especifico y los guarda en una lista
     * @param idPrograma tipo int
     * @return List<Planestudio> Lista con Objetos tipo Planestudio 
     */
        
    public List<Planestudio> findByProgramaeducativo(int idPrograma) {
        
        
        List<Planestudio> result = null;
        result = ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL buscarPlanByPEDid(\'" + idPrograma + "\')");
        return result;
        
//        List<Planestudio> result = ServiceLocator.getInstancePlanestudio().findAll();
//        List<Planestudio> resultados = new ArrayList<>();
//        for (Planestudio resultado : result) {
//            if (resultado.getProgramaEducativoPEDid().getPEDid()==idPrograma) {
//                //System.out.println(resultado.getPESid());
//                resultados.add(resultado);
//            }
//        }
//        return resultados;
    }
    

    public List<Planestudio> findByProgramaeducativoClave(int clavePrograma){
       List<Planestudio> result = null;
       result = ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL buscarPlanByClave(\'" + clavePrograma + "\')");
        //List<Planestudio> result = ServiceLocator.getInstancePlanestudio().findByCriteriaClave(clavePrograma);
       //asd
        return result;
    }

    
        public List<Planestudio> getListaPlanesEstudioByUnidadAprendizaje(int idUa){ 
        
        List<Planestudio> resultado = null; 
        
        resultado = ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL buscarPlanByUnidad(\'" + idUa + "\')");
        //resultado = ServiceLocator.getInstancePlanestudio().findFromWhere("Planestudio", "areaconocimientoList c join c.unidadaprendizajeList","uAPid",String.valueOf(idUa)); 
 
       return resultado; 
    }
        
        public Planestudio findPlanDePE(String vigencia, int idprograma){
        List<Planestudio> result;
        result = ServiceLocator.getInstancePlanestudio().executeProcedureTest("CALL buscarPlan_PE (\'" 
                        + vigencia + "\', \'" + idprograma + "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta de Planestudio por Vigencia>");
            return null;
        }else if(result.isEmpty()){
            return new Planestudio();
        }else{
            return result.get(0);
        }
    }
}