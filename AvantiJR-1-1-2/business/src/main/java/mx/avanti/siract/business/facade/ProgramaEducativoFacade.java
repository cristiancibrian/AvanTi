package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateProgramaEducativo;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Programaeducativo;

/**
 *
 * @author Y
 */
public class ProgramaEducativoFacade {
    
    DelegateProgramaEducativo delegate = new DelegateProgramaEducativo();
   
    public void terminarTransaccion(boolean completa){
        delegate.terminar(completa);
    }
     
    /**
     * Metodo para agregar un programa educativo 
     * @param programaEducativo con todos sus atributos y id 0 para hacer 
     * un nuevo registro 
     */
    public Programaeducativo agregarProgramaEducativo(Programaeducativo programaEducativo) {
        return delegate.agregarProgramaEducativo(programaEducativo);
    }
    /**
     * Metodo para actualizar un registro de programa educativo
     * @param programaEducativo con todos sus atributos 
     */
    public Programaeducativo actualizarProgramaEducatico(Programaeducativo programaEducativo){
        return delegate.updateProgramaEducativo(programaEducativo);
    }
    /**
     * Metodo para consultar todos los registros de programas educativos en
     * la base de datos
     * @return lista de entidades de programa educativo
     */
    public List<Programaeducativo> buscarProgramaEducativos() {
        return delegate.getProgramaEducativos();
    }
     
    /**
     * Metodo para eliminar un programa educativo
     * @param  programaeducativo  con todos sus atributos 
     */
    public Programaeducativo eliminarProgramaEducativo(Programaeducativo programaeducativo) {
        return delegate.deleteProgramaEducativo(programaeducativo);
    }

    /**
     * Metodo para buscar un programa Educativo por ID 
     * @param id int 
     * @return entidad de programa educativo correspondiente al id proporcionado
     */
    public Programaeducativo buscarProgramaEducativoPorID(int id) {     
        return delegate.findProgramaEducativoByID(id);
    }
    
    /** COPIA
     * Metodo para buscar un programa Educativo por ID 
     * @param id int 
     * @return entidad de programa educativo correspondiente al id proporcionado
     */
    public Programaeducativo buscarProgramaEducativoPorClave(int id) {     
        return delegate.findProgramaEducativoByClave(id);
    }
    
    public Areaconocimiento buscarAreaConocimientoPorID(int id){
        return delegate.findAreaConocimientoByID(id);
    }
    
    /**
     * Consulta de programas educativos de un plan de estudio
     * @return Lista de programas educativos
     */
    public List<Programaeducativo> consultaPlanestudioDePEGeneral() {
        return delegate.consultaPlanestudioDePEGeneral();
    }
    
    /**
     * Consulta unidades de aprendizaje
     * @param de
     * @param campo 
     * @param criterio Id Profesor
     * @return Lista de ProgramaEducativo 
     */
    public List<Programaeducativo> getListaUnidadAprendizajeFFWD(String de,String campo,String criterio) {
        return delegate.buscarProgramaEducativo(criterio);
    }
    
    /**
     * Consulta Programas educativos por Rol
     * @param idProf
     * @param rol
     * @param ce
     * @param esRactConsultas
     * @return Lista de ProgramaEducativo
     */
    public List<Programaeducativo> getListaProgramaEducativoPorRol(String idProf,String rol,String ce, boolean esRactConsultas) {
        return delegate.buscarProgramaEducativoPorRol(idProf,rol,ce,esRactConsultas);
    }
    
    /**
     * Consulta Programas educativos
     * @param claveUnidad Clave de la unidad academica
     * @return Lista de programas educativos correspondientes a la unidad academica
     */
    public List<Programaeducativo> getProgramaeducativoByUnidadacademicaClave(int claveUnidad) {        
        return delegate.getProgramaeducativoByUnidadacademicaClave(claveUnidad);
    }
    
    /**
    * Obtiene ProgramaEducativo mediante Id de UnidadAprendizaje
    * @param uaId
    * @return ProgramaEducativo
    * @deprecated 
    */
    public Programaeducativo getPEdeUnAp(int uaId){        
        return delegate.getPEdeUnidadAprendizaje(uaId);
    }
    
    /**
     * Obtiene ProgramaEducativo mediante Id de UnidadAprendizaje
     * con prioridad sobre tronco común
     * @param uaId
     * @return ProgramaEducativo
     * @deprecated
     */
    public Programaeducativo getProgramaEducativoDeUnidadAprendizaje(int uaId){        
        return delegate.getProgramaEducativoDeUnidadAprendizaje(uaId);
    }
    
    /**
     * Obtiene ProgramaEducativo mediante Id de UnidadAprendizaje
     * con prioridad sobre tronco común
     * @param uaId
     * @param ids
     * @return ProgramaEducativo
     */
    public Programaeducativo getProgramaEducativoDeUnidadAprendizajeUnico(int uaId, List<Integer> ids){        
        return delegate.getProgramaEducativoDeUnidadAprendizajeUnico(uaId,ids);
    }
}

