package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;

import mx.avanti.business.delegate.DelegateUnidadaprendizajeImparteProfesor;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;

/**
 *
 * @author Eduardo
 */
public class UnidadAprendizajeImparteProfesorFacade {

    private final DelegateUnidadaprendizajeImparteProfesor delegate;
    private List<UnidadaprendizajeImparteProfesor> listaUnidad;

    public UnidadAprendizajeImparteProfesorFacade() {
        listaUnidad = new ArrayList<UnidadaprendizajeImparteProfesor>();
        delegate = new DelegateUnidadaprendizajeImparteProfesor();

    }

    /**
     * Metodo para agregar una asignacion Unidad Aprendizaje Imparte Profesor
     *
     * @param uaImaparteProfesor Objeto tipo Unidad Aprendizaje Imparte Profesor
     */ // PROCEDIMIENTO
    public void agregarUniAprenImparteProfe(UnidadaprendizajeImparteProfesor uaImaparteProfesor) {
        delegate.save(uaImaparteProfesor);
    }

    /**
     * Metodo para eliminar una Unidad Aprendizaje Imparte Profesor
     *
     * @param imaparteProfesor objeto tipo Unidad Aprendizaje Imparte Profesor
     */// PROCEDIMIENTO
    public void eliminarUniAprenImparteProfe(UnidadaprendizajeImparteProfesor imaparteProfesor) {
        delegate.delete(imaparteProfesor);
    }

    /**
     * Metodo para obtener todas las Unidades Aprendizaje Imparte Profesor
     *
     * @return Lista tipo Unidad Aprendizaje Imparte Profesor
     */// PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> getListaUnidadaprendizajeImparteProfesor() {
        return delegate.findAll();
    }

    /**
     * Metodo para actualizar una Unidad Aprendizaje Imparte Profesor
     *
     * @param uaip Objeto tipo Unidad Aprendizaje Imparte Profesor
     */// PROCEDIMIENTO
    public void actualizarUnidadAprendizajeImparteProfesor(UnidadaprendizajeImparteProfesor uaip) {
        delegate.update(uaip);
    }

    /**
     * Metodo para obtener una Unidad Aprendizaje Imparte Profesor por Id
     *
     * @param idUAIP Id de la Unidad Aprendizaje Imparte Profesor
     * @return Unidad Aprendizaje Imparte Profesor encontrada
     */// PROCEDIMIENTO
    public UnidadaprendizajeImparteProfesor findUnidadAprendizajeImparteProfesorByID(int idUAIP) {
        return delegate.findById(idUAIP);
    }

    /**
     * Metodo para obtener Unidad Aprendizaje Imparte Profesor por Id del
     * Profesor, Id de la Unidad de Aprendizaje y Ciclo Escolar
     *
     * @param proId Id del Profesor
     * @param uapId Id de la Unidad de Aprendizaje
     * @param ce Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    // *** // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> buscarPorProfesorUAyCE(int proId, int uapId, String ce) {
        return delegate.findByProfesorUAAndCE(proId, uapId, ce);
    }

    /**
     *
     * @param de
     * @param campo
     * @param criterio
     * @param order
     * @param ce
     * @return
     */
    // *** // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> consultaFFW(String de, String campo, String criterio, String order, String ce) {
        List<UnidadaprendizajeImparteProfesor> resultado = null;
        System.out.println("ORDER: " + order);
        resultado = delegate.consultaFFW(de, campo, criterio, order, ce);

        return resultado;
    }

    /**
     * Metodo para obtener Unidad Aprendizaje Imparte Profesor por Programa
     * Educativo seleccionado, Id de la Unidad de Aprendizaje y Ciclo Escolar
     *
     * @param PESeleccionado Programa Educativo seleccionado
     * @param idUA Id de la Unidad de Aprendizaje
     * @param CE Ciclo Escolar
     * @return Lista de Unidad Aprendizaje Imparte Profesor encontradas
     */
    // ***// PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> consultaUIPParaUA(String PESeleccionado, String idUA, String CE) {
        return delegate.consultaUIPParaUA(PESeleccionado, idUA, CE);
    }

    // *** // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> buscarPorProfesorUA(int profId, int uapId) {
        return delegate.findByProfesorUA(profId, uapId);
    }

    // PARA SEMAFOROS
    /**
     * Con este metodo se obtienen tanto entregados como esperados de todas las
     * tablas en semaforos.
     *
     * @param tipoReporte El tipo de reporte que se selecciono
     * @param cicloEscolar El ciclo escolar seleccionado
     * @param numRACT El numero de RACT
     * @param planesEstudio Los planes de estudio de un programa educativo
     * @return
     */
    // Semaforos ya se realizo //Generador //Semaforos // PENDIENTE
    public int obtenerEnviadosOEsperados(String tipoReporte, String cicloEscolar, String numRACT, List<Planestudio> planesEstudio) {
        return delegate.obtenerEnviadosOEsperados(tipoReporte, cicloEscolar, numRACT, planesEstudio);
    }

    // ***
//    public int enviadosPorRactGeneral(String  idCicloEscolar){
//        int respuesta=0;
//
//       respuesta= delegate.enviadosPorRactGeneral(idCicloEscolar);
//
//        return respuesta;
//
//    }
    // ***
//    public int esperadosPorRACTGeneral(String idCicloEscolar){
//       int respuesta=0;
//       respuesta= delegate.esperadosPorRACTGeneral(idCicloEscolar);
//       return respuesta;
//    }
    // ***
//    public int esperadosPorRACT(String idCicloEscolar){
//       int respuesta=0;
//       respuesta= delegate.esperadosPorRACT(idCicloEscolar);
//       return respuesta;
//    }
    // ***
//    public int enviadosPorNumeroRACTGeneral(String numRACT,String idCicloEscolar){
//        int respuesta=0;
//
//       respuesta= delegate.enviadosPorNumeroRACTGeneral(numRACT, idCicloEscolar);
//        return respuesta;
//
//    }
    ////Generador // PROCEDIMIENTO 
    public int esperadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar) {
        int respuesta = 0;

        respuesta = delegate.esperadosPorProgramaEducativoGeneral(planesEstudio, idCicloEscolar);
        return respuesta;

    }

    // //Generador // PROCEDIMIENTO
    public int esperadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar) {
        int respuesta = 0;

        respuesta = delegate.esperadosPorProgramaEducativoRACT(planesEstudio, idCicloEscolar);
        return respuesta;

    }

//    /**
//     * Metodo para encontrar el numero de reportes esperados por Unidad de
//     * Aprendizaje
//     *
//     * @param planesEstudio
//     * @param idCicloEscolar
//     * @param grupo
//     * @return
//     */

//    public int EsperadosPorUnidadAprendizajeRACT(String planesEstudio, String idCicloEscolar, String grupo) {
//         return delegate.EsperadosPorUnidadAprendizajeRACT(planesEstudio, idCicloEscolar, grupo);
//     }

//    public int esperadosPorAreaConocimientoRACT( String idCicloEscolar, int idAreaCon){
//        int respuesta=0;
//        respuesta = delegate.esperadosPorAreaConocimientoRACT( idCicloEscolar, idAreaCon);
//        return respuesta;
//    }
    //Generador //PROCEDIMIENTO
    public int enviadosPorProgramaEducativoRACT(List<Planestudio> planesEstudio, String idCicloEscolar, String numRact) {

        return delegate.enviadosPorProgramaEducativoRACT(planesEstudio, numRact, idCicloEscolar);
    }

    // *** 
//    public int EsperadosPorUnidadAprendizajeNumeroRACTGeneral(String planesEstudio, String numRACT, String idCicloEscolar, String grupo){
//        
//        return delegate.EsperadosPorUnidadAprendizajeNumeroRACTGeneral(planesEstudio, numRACT, idCicloEscolar, grupo);
//    }
    //Generador // PROCEDIMIENTO
    public int enviadosPorProgramaEducativoGeneral(List<Planestudio> planesEstudio, String idCicloEscolar) {

        int respuesta = 0;

        respuesta = delegate.enviadosPorProgramaEducativoGeneral(planesEstudio, idCicloEscolar);
        return respuesta;
    }

    /**
     * MEtodo para traer las unidades de aprendizaje que pertecen a un area
     * administrativa que coordina el profesor recibido
     *
     * @param idProfCoordinador
     * @param idCiclo
     * @return
     */
    // Consultas // PENDIENTE //KEVIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipPorcoordinadorAreaYCicloEscolar(int idProfCoordinador, int idCiclo) {
        return delegate.uaipPorcoordinadorAreaYCicloEscolar(idProfCoordinador, idCiclo);
    }

    /**
     * Trae las uiap del area administrativa del profesor que es coordinados y
     * aparte que pertenescan al profesor ( idProfesor)
     *
     * @param idProfCoordinador
     * @param idCiclo
     * @param idProfesor
     * @return
     */
    //Consultas // PROCEDIMIENTO // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipAreaProfesor(int idProfCoordinador, int idCiclo, int idProfesor) {
        return delegate.uaipAreaProfesor(idProfCoordinador, idCiclo, idProfesor);
    }

    /**
     * Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    //Consultas // PENDIENTE // KEVIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipPorPEyCE(int idCiclo, int idPE) {
        return delegate.uaipPorPEyCE(idCiclo, idPE);
    }

    /**
     * Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     * que pertenezcan al profesor recibido
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    //Consultas // PENDIENTE // KEVIN //PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipPorProfesorPEyCE(int idCiclo, int idPE, int idProfesor) {
        return delegate.uaipPorProfesorPEyCE(idCiclo, idPE, idProfesor);
    }

    /**
     * Metodo para traer las uaip de un area de conocimiento, es asi porque asi
     * se divide el reporte por programa educativo
     *
     * @param criterio entregados, no entregados.. etc
     * @param idProgramaEducativo
     * @param idCicloEscolar
     * @param idAreaConocimiento
     * @param noRact numero de ract
     * @param idPlan id de plan de estudios
     * @return
     */
    //Generador // PENDIENTE // CHIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipsPorAreaConocimiento(String criterio, int idProgramaEducativo, int idCicloEscolar, int idAreaConocimiento, String noRact, int idPlan) {
        return delegate.uaipsPorAreaConocimiento(criterio, idProgramaEducativo, idCicloEscolar, idAreaConocimiento, noRact, idPlan);

    }

    /**
     * Metodo para saber cuantos registros de uaip tiene un area de conocimiento
     * en un ciclo escolar;
     *
     * @param idPlan
     * @param idCicloEscolar
     * @return
     */
    //Generador // PENDIENTE // PROCEDIMIENTO
    public int countUaipPorAreaConocimientoYcicloEscolar(int idPlan, int idCicloEscolar) {
        return delegate.countUaipPorAreaConocimientoYcicloEscolar(idPlan, idCicloEscolar);
    }
//    
//    /**
//     * Metodo para saber cuantos registros de uaip tiene una unidad de aprendizaje en un ciclo escolar
//     * @param idPlan plan de estudios
//     * @param idCicloEscolar cilo escolar
//     * @param idACO area de conocimiento
//     * @param idUAP unidad de aprendizaje
//     * @param idGrupo grupo
//     * @return 
//     */
//    public int countUaiPorUnidadAprendizaje(int idPlan, int idCicloEscolar,int idACO, int idUAP, int idGrupo){
//        return delegate.countUaiPorUnidadAprendizaje(idPlan,idCicloEscolar,idACO,idUAP,idGrupo);
//    }

    /**
     * *
     *
     * @param idPrograma
     * @param idArea
     * @param idCiclo
     * @return conteo de esperados por Area de cocimiento segun el ciclo escolar
     */
    //Generador // PENDIENTE // ##
    public int esperadosPorAreaConocimiento(int idPrograma, int idArea, int idCiclo) {
        return delegate.esperadosPorAreaConocimiento(idPrograma, idArea, idCiclo);

    }

    /**
     * Metodo para traer los RACT esperados por un area administrativa en cada
     * ract individual en caso de que se quiera saber por los 3 racts debera
     * multiplicarse el resultado x3
     *
     * @param idCicloEscolar
     * @param idAreaCon
     * @param idPrograma
     * @return
     */
    //Generador // PENDIENTE // ##
    public int esperadosPorAreaAdministrativa(int idPrograma, int idArea, int idCiclo) {
        return delegate.esperadosPorAreaAdministrativa(idPrograma, idArea, idCiclo);

    }

    /**
     * *
     * Metodo para traer esperados por unidad de aprendizaje segun el ciclo
     * escolar
     *
     * @param idUnidad
     * @param idPrograma
     * @param idCiclo
     * @return
     */
    //Generador // PENDIENTE ##
    public int esperadosPorUnidadAprendizaje(int idUnidad, int idPrograma, int idCiclo) {
        return delegate.esperadosPorUnidadAprendizaje(idUnidad, idPrograma, idCiclo);
    }

    /**
     * *
     * Metodo para traer conteo de esperados por unidad profesor dependiendo del
     * ciclo escolar y si el usuario que consulta es Admin u otro usuario
     *
     * @param idProfesor
     * @param idCiclo
     * @param IdPrograma
     * @param isAdmin
     * @return
     */
    //Generador // PENDIENTE //KEVIN // PROCEDIMIENTO
    public int esperadosPorProfesor(int idProfesor, int idCiclo, int IdPrograma, boolean isAdmin) {
        return delegate.esperadosPorProfesor(idProfesor, idCiclo, IdPrograma, isAdmin);
    }

    /**
     * Esperados por profesor segun el area que coordina el usuario
     *
     * @param idProfesor
     * @param idCiclo
     * @param IdPrograma
     * @param idProfesorCoordinador idProfesor del que es coordinador
     * @return
     */
    //Generador PENDIENTE // KEVIN // PROCEDIMIENTO
    public int esperadosPorProfesorCoordinador(int idProfesor, int idCiclo, int IdPrograma, int idProfesorCoordinador) {
        return delegate.esperadosPorProfesorCoordinador(idProfesor, idCiclo, IdPrograma, idProfesorCoordinador);
    }

    /**
     * Trae las unidadAprendizajeImparteProfesor segun el criterio de busqueda
     *
     * @param criterio
     * @param idPrograma
     * @param idArea
     * @param idCiclo
     * @param noRact
     * @param idPlan
     * @return
     */
    //Generador // PENDIENTE // CHIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipsPorAreaAdministrativa(String criterio, int idPrograma, int idArea, int idCiclo, String noRact) {

        return delegate.uaipsPorAreaAdministrativa(criterio, idPrograma, idArea, idCiclo, noRact);
    }

    /**
     * Traera la lista de uaips de aprendizaje que corresponden a una unidad de
     * aprendizaje segun el criterio recibido
     *
     * @param criterio
     * @param idPrograma programa seleccionado
     * @param idUnidad unidad seleccionado
     * @param idCiclo ciclo escolar seleccionado
     * @param noRact numero de ract
     * @return
     */
    //Generador // PENDIENTE // CHIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipsPorUnidadAprendizaje(String criterio, int idPrograma, int idUnidad, int idCiclo, String noRact) {
        return delegate.uaipsPorUnidadAprendizaje(criterio, idPrograma, idUnidad, idCiclo, noRact);
    }

    /**
     * Traera la lista de uaips que corresponde a un profesor y al criterio
     * recibido
     *
     * @param criterio
     * @param idPrograma
     * @param idProfesor
     * @param idCiclo
     * @param noRact
     * @param isAdmin
     * @return
     */
    //Generador // PEDIENTE //CHIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipsPorProfesor(String criterio, int idPrograma, int idProfesor, int idCiclo, String noRact, boolean isAdmin) {
        return delegate.uaipsPorProfesor(criterio, idPrograma, idProfesor, idCiclo, noRact, isAdmin);
    }

    /**
     * Traera la lista de uaips que corresponden a un coordinador de area segun
     * el criterio recibido
     *
     * @param criterio
     * @param idPrograma
     * @param idProfesor
     * @param idCiclo
     * @param noRact
     * @param idProfesorCoordinador
     * @return
     */
    //Generador // PENDIENTE // CHIN // PROCEDIMIENTO
    public List<UnidadaprendizajeImparteProfesor> uaipsPorProfesorCoordinador(String criterio, int idPrograma, int idProfesor, int idCiclo, String noRact, int idProfesorCoordinador) {
        return delegate.uaipsPorProfesorCoordinador(criterio, idPrograma, idProfesor, idCiclo, noRact, idProfesorCoordinador);

    }

    //Generador // PENDIENTE // KEVIN
    public List<UnidadaprendizajeImparteProfesor> uaipsPorPlanEstudio(String criterio, int idPlan, int idCiclo, String noRact) {
        return delegate.uaipsPorPlanEstudio(criterio, idPlan, idCiclo, noRact);
    }
    
    
    public List<UnidadaprendizajeImparteProfesor> buscarPorProfesorYProgramaEducativo2(int proid,int peid, String ce, int coordinadorProId){
        return delegate.findByProfesorYPE2(proid, peid, ce, coordinadorProId);
    }
}