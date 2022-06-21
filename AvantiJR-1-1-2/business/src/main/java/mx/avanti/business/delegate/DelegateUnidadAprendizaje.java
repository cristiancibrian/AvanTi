package mx.avanti.business.delegate;

import java.io.Serializable;
import java.util.List;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author
 */
public class DelegateUnidadAprendizaje implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public DelegateUnidadAprendizaje() {
    }
    //</editor-fold>
 
    UnidadaprendizajeTieneContenidotematico UAPCT=new UnidadaprendizajeTieneContenidotematico();

    /**
     * Metodo para agregar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo unidadAprendizaje
     */
 public void agregarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        ServiceLocator.getInstanceUnidadAprendizajeDAO().save(unidadAprendizaje);
        UAPCT.setVersion(1);
        UAPCT.setCicloEscolarCESid(unidadAprendizaje.getCicloEscolarCESid());
        UAPCT.setUnidadAprendizajeUAPid(unidadAprendizaje);
        UAPCT.setUAPhorasClaseCompletas(false);
        UAPCT.setUAPhorasLaboratorioCompletas(false);
        UAPCT.setUAPhorasTallerCompletas(false);
        UAPCT.setUAPhorasCampoCompletas(false);
        ServiceLocator.getInstanceUnidadAprendizajeTieneContenidotematicoDAO().save(UAPCT);
        ServiceLocator.getInstanceProfesor().executeProcedure("{CALL siract.guardar_UnidadAprendizajePCT (\'" 
                + UAPCT.getVersion() + "\', \'" + 
                UAPCT.getCicloEscolarCESid()+  "\', \'" + 
                UAPCT.getUnidadAprendizajeUAPid() + "\', \'" + 
                UAPCT.getUAPhorasClaseCompletas() +  "\', \'" + 
                UAPCT.getUAPhorasLaboratorioCompletas() + "\', \'" + 
                UAPCT.getUAPhorasTallerCompletas() + "\', \'" + 
                UAPCT.getUAPhorasCampoCompletas() + "\')}");
    }

    /**
     * Metodo para Eliminar una Unidad de Aprendizaje
     *
     * @param unidadAprendizaje Objeto tipo unidadAprendizaje
     */
    public void eliminarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        ServiceLocator.getInstanceUnidadAprendizajeDAO().delete(unidadAprendizaje);
    }

    /**
     * Metodo para Actualizar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo unidadAprendizaje
     */
    public void actualizarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        ServiceLocator.getInstanceUnidadAprendizajeDAO().update(unidadAprendizaje);
        
    }

    /**
     * Metodo para Obtener todas las Unidades de Aprendizaje
     *
     * @return Lista de todas las Unidades de Aprendizaje
     */
    public List<Unidadaprendizaje> findAll() {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findAll();
    }

    /**
     * Metodo para Obtener una Unidad de Aprendizaje con un Id especifico
     *
     * @param idUA Id de la Unidad de Aprendizaje que se busca
     * @return Regresa la unidad de Aprendizaje encontrada
     */
    public Unidadaprendizaje findById(int idUA) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().find(idUA);
    }

    /**
     * Metodo para obtener unidades de aprendizaje por area, etapa y programa
     * educativo
     *
     * @param idArea Id del Area de Conocimiento
     * @param etapa Etapa de formacion de Unidad de Aprendizaje
     * @param idPE Id del programa educativo
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaAndPE(int idArea, String etapa, String idPE) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("Unidadaprendizaje", "areaconocimientoList", "aCOid", String.valueOf(idArea) + " AND a.uAPetapaFormacion='"
                + etapa + "' AND b.planEstudioPESid.programaEducativoPEDid.pEDid='" + idPE + "'");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Responsable
     *
     * @param idUsuario Id del usuario
     * @return Lista de unidades de aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByResponsable(int idUsuario) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("coordinadorareaadministrativaList", "profesorPROid.usuarioUSUid.uSUid", String.valueOf(idUsuario));
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa y Programa
     * educativo
     *
     * @param etapa Etapa de formacion
     * @param idPE Id del programa educativo
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPEAdmin(String etapa, String idPE) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("areaconocimientoList", "planEstudioPESid.programaEducativoPEDid.pEDid", String.valueOf(idPE) + " AND "
                + "a.uAPetapaFormacion='" + etapa + "'");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa y Programa
     * educativo
     *
     * @param etapa Etapa de formacion
     * @param idPE Id de Programa Educativo
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPE(String etapa, String idPE) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("areaconocimientoList", "planEstudioPESid.programaEducativoPEDid.pEDid", String.valueOf(idPE) + " AND "
                + "a.uAPetapaFormacion='" + etapa + "'");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Programa Educativo
     *
     * @param idPE Id del programa educativo
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPE(String idPE) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("areaconocimientoList", "planEstudioPESid.programaEducativoPEDid.pEDid", String.valueOf(idPE));
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa de Formacion
     *
     * @param etapa Etapa de formacion
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAdmin(String etapa) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findWhere("Unidadaprendizaje a join a.areaconocimientoList b", "uAPetapaFormacion", etapa, "uAPnombre");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa
     *
     * @param etapa Etapa de formacion
     * @param usuId Id del usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapa(String etapa, String usuId) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(usuId), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findWhere("Unidadaprendizaje a join a.areaconocimientoList b join b.planEstudioPESid.programaEducativoPEDid.profesorList c", "uAPetapaFormacion", etapa
                + "' and c.pROid='" + lista.get(0).getPROid().toString(), "uAPnombre");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Plan de Estudio y Etapa
     * de formacion
     *
     * @param idPlan Id del Plan de Estudio
     * @param etapa Etapa del formacion
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioAndEtapa(int idPlan, String etapa) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("areaconocimientoList", "planEstudioPESid.pESid", idPlan + " AND "
                + "a.uAPetapaFormacion='" + etapa + "'");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa
     *
     * @param etapa Etapa de Formacion
     * @param usuId Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaCAA(String etapa, String usuId) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(usuId), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findWhere("Unidadaprendizaje a join a.coordinadorareaadministrativaList c", "uAPetapaFormacion", etapa
                + "' and c.profesorPROid.pROid='" + lista.get(0).getPROid(), "uAPnombre");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Area de Conocimiento
     *
     * @param idArea Id de Area de Conocimiento
     * @param usuId Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoCAA(int idArea, int usuId) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(usuId), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findWhere("Unidadaprendizaje b join b.areaconocimientoList a join b.coordinadorareaadministrativaList c", "aCOid", idArea
                + "' and c.profesorPROid.pROid='" + lista.get(0).getPROid(), "uAPnombre");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Etapa y Programa
     * Educativo
     *
     * @param etapa Etapa de formacion
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByEtapaAndPECAA(String etapa, String idPE, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("coordinadorareaadministrativaList c join a.areaconocimientoList", "planEstudioPESid.programaEducativoPEDid.pEDid", "'"
                + String.valueOf(idPE) + "' AND a.uAPetapaFormacion = '" + etapa + "' AND c.profesorPROid.pROid = '" + lista.get(0).getPROid() + "'");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Area de Conocimiento
     *
     * @param idArea Id del Area de Conocimiento
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaConocimientoAndCAA(int idArea, String idPE, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("coordinadorareaadministrativaList c join a.areaconocimientoList", "aCOid", String.valueOf(idArea) + " AND "
                + "b.planEstudioPESid.programaEducativoPEDid.pEDid='" + idPE + "' AND c.profesorPROid.pROid='" + lista.get(0).getPROid() + "'");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Area de Conocimiento y
     * Etapa de formacion
     *
     * @param idArea Id del Area de Conocimiento
     * @param etapa Etapa de Formacion
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaCAA(int idArea, String etapa, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadaprendizaje().findFromWhere("Unidadaprendizaje", "coordinadorareaadministrativaList c join a.areaconocimientoList", "aCOid", String.valueOf(idArea)
                + " AND a.uAPetapaFormacion='" + etapa + "' AND c.profesorPROid.pROid='" + lista.get(0).getPROid() + "'");
    }

    /**
     * Metodo para Obtener Unidades de Aprendizaje por Area de Conocimiento,
     * Etapa y Programa Educativo
     *
     * @param idArea Id del Area de Conocimiento
     * @param etapa Etapa de Formacion
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByAreaAndEtapaAndPECAA(int idArea, String etapa, String idPE, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("Unidadaprendizaje", "coordinadorareaadministrativaList c join a.areaconocimientoList", "aCOid", String.valueOf(idArea)
                + " AND a.uAPetapaFormacion='" + etapa + "' AND b.planEstudioPESid.programaEducativoPEDid.pEDid='" + idPE + "' AND c.profesorPROid.pROid='" + lista.get(0).getPROid() + "'");
    }

    /**
     * Metodo para obtener unidades de aprendizaje por plan de estudio.
     *
     * @param idPlan Id del Plan de Estudio
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioCAA(int idPlan, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("coordinadorareaadministrativaList c join a.areaconocimientoList", "planEstudioPESid.pESid='"
                + idPlan + "'" + " AND c.profesorPROid.pROid", lista.get(0).getPROid().toString());
    }

    /**
     * Metodo para obtener unidades de aprendizaje por plan de estudio y etapa.
     *
     * @param idPlan
     * @param etapa
     * @param idUsuario
     * @return
     */
    public List<Unidadaprendizaje> getUnidadByPlanEstudioAndEtapaCAA(int idPlan, String etapa, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("coordinadorareaadministrativaList c join a.areaconocimientoList", "planEstudioPESid.pESid='"
                + idPlan + "'" + " AND a.uAPetapaFormacion='" + etapa + "' AND c.profesorPROid.pROid", lista.get(0).getPROid().toString());
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Programa Educativo
     *
     * @param idPE Id del Programa Educativo
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByPECAA(String idPE, int idUsuario) {
        List<Profesor> lista = ServiceLocator.getInstanceProfesor().findFromWhereB2("usuarioUSUid", "uSUid", String.valueOf(idUsuario), "pROid");

        return lista.isEmpty() ? null : ServiceLocator.getInstanceUnidadAprendizajeDAO().findFromWhere("coordinadorareaadministrativaList c join a.areaconocimientoList", "planEstudioPESid.programaEducativoPEDid.pEDid",
                String.valueOf(idPE) + " AND c.profesorPROid.pROid='" + lista.get(0).getPROid() + "'");
    }

    /**
     * Metodo para obtener las Unidades de Aprendizaje por Unidad Academica
     *
     * @param idUsuario Id del Usuario
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUnidadByUnidadAcademica(int idUsuario) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().unidadByUnidadAcademica(String.valueOf(idUsuario));
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Id
     *
     * @param id Id de la Unidad de Aprendizaje
     * @return Unidad de Aprendizaje encontrada
     */
    public Unidadaprendizaje getUnidadaprendizajeByClave(int id) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findByOneParameterUnique(Integer.toString(id), "uAPclave");
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Profesor y Programa
     * Educativo
     *
     * @param proid
     * @param peid Id del Programa Educativo
     * @param ce Ciclo Escolar
     * @param coordinadorProId Id del...
     * @return Lista de Unidades de Aprendizaje encontradas
     * @author Mario
     */
    public List<Object[]> findByProfesorYPE(int proid, int peid, String ce, int coordinadorProId) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().findByProfesorYPE(proid, peid, ce, coordinadorProId);
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje por Programa Educativo
     *
     * @param PESeleccionado Programa Educativo Seleccionado
     * @param CE Ciclo escolar
     * @param rol Rol del Usuario
     * @param profId Id del Profesor
     * @return Lista de Unidades de Aprendizaje encontradas
     * @author Mario
     */
    public List<Unidadaprendizaje> getListaUnidadAprendizajePorProgramaEducativo(String PESeleccionado, String CE, String rol, String profId) {
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().getListaUnidadAprendizajePorProgramaEducativo(PESeleccionado, CE, rol, profId);
    }
}
