package mx.avanti.siract.business.facade;
import java.util.List;
import mx.avanti.business.delegate.DelegatePlanEstudio;
import mx.avanti.siract.entity.Planestudio;
/**
 *
 * @author NOE_LOPEZ
 */
public class PlanestudioFacade {

    DelegatePlanEstudio delegate = new DelegatePlanEstudio();
    /**
     *Guardar un objeto tipo Planestudio
     * @param planEstudio tipo Planestudio
     */
    public void agregarPlanEstudio(Planestudio planEstudio) {
        delegate.savePlanEstudio(planEstudio);
    }
    /**
     * Devuelve una lista con todos los planes de estudio registrados
     * @return List<Planestudio> Lista con Objetos tipo Planestudio
     */
    public List<Planestudio> consultaTodosPlanestudio() {
        return delegate.findAllPlanEstudio();
    }
    /**
     * Busca un Plan de Estudio especifico por su id
     * @param idPlanEstudio tipo int
     * @return Objeto tipo Planestudio
     */
    public Planestudio buscarPlanEstudioID(int idPlanEstudio) {
        return delegate.findByPlanEstudioId(idPlanEstudio);
    }
    /**
     * Elimina un plan de estudio especifico
     * @param planestudio de tipo Planestudio
     */
    public void eliminarPlanEstudio(Planestudio planestudio) {
        delegate.deletePlanEstudio(planestudio);
    }
    /**
     * Busca un plan de estudio por el id del programa educativo regresa 
     * una lista con los planes de estudio encontrados
     * @param idPrograma tipo int
     * @return List<Planestudio> lista con Objetos tipo Planestudio
     */
    public List<Planestudio> buscarPlanPorProgramaeducativo(int idPrograma) {
        //return delegate.getPlanEstudioPE(idPrograma);
        return delegate.findByProgramaeducativo(idPrograma);

    }
    /**
     * Actualiza la informacion de un plan de estudio
     * @param plan objeto tipo Planestudio
     */
    public void actualizarPE(Planestudio plan){
        delegate.updatePE(plan);
    }
    
    /**

     * Buscar programa educativo por clave
     * @param clavePrograma
     * @return 
     */
    public List<Planestudio> findByProgramaeducativoClave(int clavePrograma) {
        return delegate.findByProgramaeducativoClave(clavePrograma);
    }

    
    /**
     * @param idUa id de unidad de aprendizaje
     * @return  lista de planes de estudio correspondiente al id de la unidad de aprendizaje
     */
    public List<Planestudio> getListaPlanesEstudioByUnidadAprendizaje(int idUa) {
        
       return delegate.getListaPlanesEstudioByUnidadAprendizaje(idUa);
    }
}