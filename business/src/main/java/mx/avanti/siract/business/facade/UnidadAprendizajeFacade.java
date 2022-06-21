package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateUnidadAprendizaje;
import mx.avanti.siract.entity.Unidadaprendizaje;

/**
 *
 * @author
 */
public class UnidadAprendizajeFacade {

    //<editor-fold defaultstate="collapsed" desc="Declaracion de objetos tipo entidad">
    DelegateUnidadAprendizaje delegateUnidadAprendizaje;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public UnidadAprendizajeFacade() {
        delegateUnidadAprendizaje = new DelegateUnidadAprendizaje();
    }
    //</editor-fold>

    /**
     * Método para Agregar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo UnidadAprendizaje
     */
    public void agregarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        delegateUnidadAprendizaje.agregarUnidadAprendizaje(unidadAprendizaje);
    }

    /**
     * Método para Eliminar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo UnidadAprendizaje
     */
    public void eliminarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        delegateUnidadAprendizaje.eliminarUnidadAprendizaje(unidadAprendizaje);
    }
    
    public List<Unidadaprendizaje> unidadesdealumno(int id, int ce){
        return delegateUnidadAprendizaje.getlistaunidadaprendizajealumno(id, ce);
    }

    /**
     * Método para actualizar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo UnidadAprendizaje
     */
    public void actualizarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        delegateUnidadAprendizaje.actualizarUnidadAprendizaje(unidadAprendizaje);
    }

    /**
     * Método para obtener todas las Unidades de Aprendizaje.
     *
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> buscarUnidadesDeAprendizaje() {
        return delegateUnidadAprendizaje.findAll();
    }

    /**
     * Método para buscar Unidades de Aprendizaje por Id.
     *
     * @param idUnidadAprendazaje Id de la Unidad de Aprendizaje
     * @return Unidad de aprendizaje encontrada
     */
    public Unidadaprendizaje getUnidadAprendazajeById(int idUnidadAprendazaje) {
        return delegateUnidadAprendizaje.findById(idUnidadAprendazaje);
    }

    /**
     * Método que obtiene las Unidades de Aprendizaje y Grupos por Profesor,
     * Programa Educativo y Ciclo Escolar.
     *
     * @param proid Id del Profesor
     * @param peid Id del Programa Educativo
     * @param ce Ciclo escolar
     * @param coordinadorProId Id del Coordinador de Programa Educativo
     * @return Lista de Unidades de Aprendizaje Encontradas
     */
    public List<Object[]> buscarPorProfesorYProgramaEducativo(int proid, int peid, String ce, int coordinadorProId) {
        return delegateUnidadAprendizaje.findByProfesorYPE(proid, peid, ce, coordinadorProId);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Programa Educativo.
     *
     * @param PESeleccionado Programa Educativo Seleccionado
     * @param CE Ciclo escolar
     * @param rol Rol del Usuario
     * @param profId Id del Profesor
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> consultaUnidadesAprendizajePorProgramaEducativo(String PESeleccionado, String CE, String rol, String profId) {
        return delegateUnidadAprendizaje.getListaUnidadAprendizajePorProgramaEducativo(PESeleccionado, CE, rol, profId);
    }

    /**
     * Método para obtener Unidad de Aprendizaje por Area, Etapa de Formacion y
     * Programa Educativo.
     *
     * @param idArea Id del Area de Conocimiento
     * @param etapa Etapa de Formacion
     * @param idPE Id del Programa Educativo
     * @return Lista de Unidades de Aprendizaje por Area de Conocmiento, Etapa y
     * Programa Educativo
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaAndPE(int idArea, String etapa, String idPE) {
        return delegateUnidadAprendizaje.getUnidadByAreaAndEtapaAndPE(idArea, etapa, idPE);
    }

    /**
     * Método para obetener Unidades de Aprendizaje por Responsable.
     *
     * @param idUsuario Id del Usuario
     * @return Lista de Unidad de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByResponsable(int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByResponsable(idUsuario);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Etapa y Programa
     * Educativo.
     *
     * @param etapa Etapa de Formacion
     * @param idPE Id del Programa Educativo
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPEAdmin(String etapa, String idPE) {
        return delegateUnidadAprendizaje.getUnidadByEtapaAndPEAdmin(etapa, idPE);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Programa Educativo.
     *
     * @param idPE Id de Programa Educativo
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPE(String idPE) {
        return delegateUnidadAprendizaje.getUnidadByPE(idPE);
    }

    /**
     * Método por Unidades de Aprendizaje por Etapa de Formacion.
     *
     * @param etapa Etapa de Formacion
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAdmin(String etapa) {
        return delegateUnidadAprendizaje.getUnidadByEtapaAdmin(etapa);
    }
    
    public List<Unidadaprendizaje> getlistaunidadesalumno(int id, int ce){
        return delegateUnidadAprendizaje.getlistaunidadaprendizajealumno(id, ce);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Etapa de Formacion.
     *
     * @param etapa Etapa de Formacion
     * @param usuId Id de profesor
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapa(String etapa, String usuId) {
        return delegateUnidadAprendizaje.getUnidadByEtapa(etapa, usuId);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Plan de Estudio y Etapa.
     *
     * @param idPlan Id de Plan de Estudios
     * @param etapa Etapa de Formacion
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioAndEtapa(int idPlan, String etapa) {
        return delegateUnidadAprendizaje.getUnidadByPlanEstudioAndEtapa(idPlan, etapa);
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa de Formacion.
     *
     * @param etapa Etapa de Formacion
     * @param usuId Id del Profesor
     * @return Lista de Unidad de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaCAA(String etapa, String usuId) {
        return delegateUnidadAprendizaje.getUnidadByEtapaCAA(etapa, usuId);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Area de Conocimiento.
     *
     * @param idArea Id del Area de Conocimiento
     * @param usuId Id del Profesor
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoCAA(int idArea, int usuId) {
        return delegateUnidadAprendizaje.getUnidadByAreaConocimientoCAA(idArea, usuId);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Etapa y Programa
     * Educativo.
     *
     * @param etapa Etapa de Formacion
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPECAA(String etapa, String idPE, int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByEtapaAndPECAA(etapa, idPE, idUsuario);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Area de Conocimiento.
     *
     * @param idArea Id del Area de Conocimiento
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoAndCAA(int idArea, String idPE, int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByAreaConocimientoAndCAA(idArea, idPE, idUsuario);
    }

    /**
     * Método para obtener Unidad de Aprendizaje por Area de Conocimiento y
     * Etapa de Formacion.
     *
     * @param idArea Id del Area de Conocimiento
     * @param etapa Etapa de Formacion
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaCAA(int idArea, String etapa, int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByAreaAndEtapaCAA(idArea, etapa, idUsuario);
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa de Formacion y
     * Programa Educativo.
     *
     * @param idArea Id del Area de Conocimiento
     * @param etapa Etapa de Formacion
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaAndPECAA(int idArea, String etapa, String idPE, int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByAreaAndEtapaAndPECAA(idArea, etapa, idPE, idUsuario);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Plan de Estudio.
     *
     * @param idPlan Id de Plan de Estudios
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioCAA(int idPlan, int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByPlanEstudioCAA(idPlan, idUsuario);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Plan de Estudio y Etapa.
     *
     * @param idPlan
     * @param etapa
     * @param usuId
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioAndEtapaCAA(int idPlan, String etapa, int usuId) {
        return delegateUnidadAprendizaje.getUnidadByPlanEstudioAndEtapaCAA(idPlan, etapa, usuId);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Programa Educativo.
     *
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPECAA(String idPE, int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByPECAA(idPE, idUsuario);
    }

    /**
     * Método para obtener Unidades de Aprendizaje por Unidad Academica.
     *
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByUnidadAcademica(int idUsuario) {
        return delegateUnidadAprendizaje.getUnidadByUnidadAcademica(idUsuario);
    }

    /**
     * Metodo para obtener una Unidad de Aprendizaje por Id.
     *
     * @param id Id de la Unidad de Aprendizaje
     * @return Unidad de Aprendizaje encontrada
     */
    public Unidadaprendizaje getUnidadaprendizajeByClave(int id) {
        return delegateUnidadAprendizaje.getUnidadaprendizajeByClave(id);
    }
}
