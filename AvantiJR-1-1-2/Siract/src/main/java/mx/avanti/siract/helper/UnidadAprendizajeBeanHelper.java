package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;

/**
 * @author Edgar
 */
public class UnidadAprendizajeBeanHelper implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Declaracion de objetos tipo entidad">
    private Programaeducativo programaEducativo;
    private Planestudio planEstudio;
    private Areaconocimiento areaConocimiento;
    private Usuario usuario;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaracion de objetos tipo lista">
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Planestudio> listaPlanEstudio;
    private List<Areaconocimiento> listaAreaConocimiento;
    private List<Unidadaprendizaje> listaUapFiltradas;
    private List<Cicloescolar> listaCiclo;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaracion de objetos String">
    private String rolSeleccionado;
    private String busquedaFiltro;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public UnidadAprendizajeBeanHelper() {
        busquedaFiltro = "";
        usuario = new Usuario();
        programaEducativo = new Programaeducativo(0);
        planEstudio = new Planestudio(0);
        areaConocimiento = new Areaconocimiento(0);
        listaCiclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
    }
    //</editor-fold>

    /**
     * Método para agregar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo UnidadAprendizaje
     */
    public void agregarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().agregarUnidadAprendizaje(unidadAprendizaje);
    }

    /**
     * Método para eliminar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo UnidadAprendizaje
     */
    public void eliminarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().eliminarUnidadAprendizaje(unidadAprendizaje);
    }

    /**
     * Método para actualizar "Unidad de Aprendizaje".
     *
     * @param unidadAprendizaje Objeto tipo UnidadAprendizaje
     */
    public void actualizarUnidadAprendizaje(Unidadaprendizaje unidadAprendizaje) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().actualizarUnidadAprendizaje(unidadAprendizaje);
    }

    /**
     * Método que asigna la versión 1 de contenido tematico a "Unidad de
     * Aprendizaje".
     *
     * @param unidadAprendizaje
     * @return
     */
    public Unidadaprendizaje asignarContenidoTematico(Unidadaprendizaje unidadAprendizaje) {
        UnidadaprendizajeTieneContenidotematico contenidoAux = new UnidadaprendizajeTieneContenidotematico();
        List<UnidadaprendizajeTieneContenidotematico> unidadT = new ArrayList<>();

        contenidoAux.setCTid(ServiceFacadeLocator.getInstanceUnidadAprendizajeTieneContenidotematicoFacade().obtenerMaximoID());
        contenidoAux.setCicloEscolarCESid(unidadAprendizaje.getCicloEscolarCESid());
        contenidoAux.setUAPhorasCampoCompletas(Boolean.FALSE);
        contenidoAux.setUAPhorasLaboratorioCompletas(Boolean.FALSE);
        contenidoAux.setUAPhorasTallerCompletas(Boolean.FALSE);
        contenidoAux.setUAPhorasClaseCompletas(Boolean.FALSE);
        contenidoAux.setVersion(1);
        contenidoAux.setUnidadAprendizajeUAPid(unidadAprendizaje);

        unidadT.add(contenidoAux);
        unidadAprendizaje.setUnidadaprendizajeTieneContenidotematicoList(unidadT);

        return unidadAprendizaje;
    }

    /**
     * Método que busca una unidad de aprendizaje por su ID.
     *
     * @param id
     * @return
     */
    public Unidadaprendizaje buscarUnidadAprendizajePorID(int id) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(id);
    }

    /**
     * Método que busca una unidad de aprendizaje por su clave.
     *
     * @param clave
     * @return
     */
    public Unidadaprendizaje buscarUnidadAprendizajePorClave(String clave) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadaprendizajeByClave(Integer.parseInt(clave));
    }

    /**
     * Método para validar si una Unidad de Aprendizaje esta asignada a un
     * profesor.
     *
     * @param id Id de la Unidad de Aprendizaje
     * @return True si la Unidad esta asignada, False en caso de no estar
     * asignada.
     */
    public boolean validarUATieneAsignacion(int id) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(id).getUnidadaprendizajeImparteProfesorList().isEmpty();
    }

    /**
     * Método para validar que la clave de unidad de aprendizaje no este
     * repetida.
     *
     * @param unidadAPClave clave de unidad de aprendizaje
     * @return
     */
    public boolean validacionClaveUA(int unidadAPClave) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadaprendizajeByClave(unidadAPClave) != null;
    }

    /**
     * Método para consultar programas educativos
     *
     * @return Lista de programas educativos encontrados
     */
    public List<Programaeducativo> consultarProgramasEducativos() {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
    }

    /**
     * Método para obtener Programas Educativos de un Area de Conocimiento
     *
     * @param id Id del area de conocimiento
     * @return Lista de programas educativos encontrados
     */
    public Programaeducativo buscarPEDeAreaConocimiento(int id) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(id).getPlanEstudioPESid().getProgramaEducativoPEDid();
    }

    /**
     * Método para obtener Planes de Estudio por Programa Educativo
     *
     * @param id Id del Programa Educativo
     * @return Lista de Planes de Estudio encontrados
     */
    public List<Planestudio> buscarPlanPorProgramaeducativo(int id) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(id);
    }

    /**
     * Método para obtener areas de conocimiento por plan de estudio
     *
     * @param id ID del plan de estudio
     * @return Lista de areas de conocimiento encontradas
     */
    public List<Areaconocimiento> obtenerAreaMismoPlan(int id) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(id).getAreaconocimientoList();
    }

    /**
     * Método para filtrar unidad academica si es administrador o, programas
     * educativos si es cualquier otro rol.
     */
    public void filtrarPorRol() {
        Profesor profesor = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);

        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador")) {
            listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();

            if (programaEducativo.getPEDid() == 0) {
                listaUapFiltradas = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().buscarUnidadesDeAprendizaje();
                filtrarUAPPorBusqueda();
            } else {
                filtrarPlanEPorPED();
            }
        } else if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")) {
//            if (!profesor.getProgramaeducativoList().isEmpty()) {
//                listaProgramaEducativo = profesor.getProgramaeducativoList();
//                filtrarPlanEPorPED();
//            }
            if (!profesor.getResponsableProgramaEducativosList().isEmpty()) {
                List<Programaeducativo> peProgramaEducativoList = new ArrayList<Programaeducativo>();
                for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList())
                    if (!peProgramaEducativoList.contains(rpe.getProgramaeducativo()))
                        peProgramaEducativoList.add(rpe.getProgramaeducativo());
                listaProgramaEducativo = peProgramaEducativoList;
                filtrarPlanEPorPED();
            }
        } else if (rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
            if (!profesor.getAreaconocimientoList().isEmpty()) {
                planEstudio.setPESid(-1);
                listaAreaConocimiento = profesor.getAreaconocimientoList();
                filtrarUAPPorAC();
            }
        }
    }

    /**
     * Método para filtrar planes de estudio por programa educativo.
     */
    public void filtrarPlanEPorPED() {
        listaPlanEstudio = new ArrayList<>();
        planEstudio.setPESid(0);
        areaConocimiento.setACOid(0);

        if (programaEducativo.getPEDid() != 0) {
            for (int i = 0; i < listaProgramaEducativo.size(); i++) {
                if (listaProgramaEducativo.get(i).getPEDid().intValue() == programaEducativo.getPEDid().intValue()) {
                    listaPlanEstudio.addAll(listaProgramaEducativo.get(i).getPlanestudioList());
                }
            }
        } else {
            for (int i = 0; i < listaProgramaEducativo.size(); i++) {
                listaPlanEstudio.addAll(listaProgramaEducativo.get(i).getPlanestudioList());
            }
        }

        filtrarACPorPlanE();
    }

    /**
     * Método para filtrar areas de conocimiento por plan de estudio.
     */
    public void filtrarACPorPlanE() {
        listaAreaConocimiento = new ArrayList<>();
        areaConocimiento.setACOid(0);

        if (planEstudio.getPESid() != 0) {
            for (int i = 0; i < listaPlanEstudio.size(); i++) {
                if (listaPlanEstudio.get(i).getPESid().intValue() == planEstudio.getPESid().intValue()) {
                    listaAreaConocimiento.addAll(listaPlanEstudio.get(i).getAreaconocimientoList());
                }
            }
        } else {
            for (int i = 0; i < listaPlanEstudio.size(); i++) {
                listaAreaConocimiento.addAll(listaPlanEstudio.get(i).getAreaconocimientoList());
            }
        }

        filtrarUAPPorAC();
    }

    /**
     * Método para filtrar unidades de aprendizaje por areas de conocimiento.
     */
    public void filtrarUAPPorAC() {
        listaUapFiltradas = new ArrayList<>();

        if (areaConocimiento.getACOid() != 0) {
            for (int i = 0; i < listaAreaConocimiento.size(); i++) {
                if (listaAreaConocimiento.get(i).getACOid().intValue() == areaConocimiento.getACOid().intValue()) {
                    listaUapFiltradas.addAll(listaAreaConocimiento.get(i).getUnidadaprendizajeList());
                }
            }
        } else {
            for (int i = 0; i < listaAreaConocimiento.size(); i++) {
                listaUapFiltradas.addAll(listaAreaConocimiento.get(i).getUnidadaprendizajeList());
            }
        }

        filtrarUAPPorBusqueda();
    }

    /**
     * Método para filtrar unidades de aprendizaje.
     */
    public void filtrarUAPPorBusqueda() {
        List<Unidadaprendizaje> listaUAFiltradaAux = new ArrayList<>();
        busquedaFiltro = busquedaFiltro.toLowerCase();

        if (busquedaFiltro.trim().length() > 0) {
            for (Unidadaprendizaje ua : listaUapFiltradas) {
                if (ua.getUAPnombre().toLowerCase().startsWith(busquedaFiltro) || Integer.toString(ua.getUAPclave()).startsWith(busquedaFiltro)) {
                    listaUAFiltradaAux.add(ua);
                }
            }

            listaUapFiltradas = listaUAFiltradaAux;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public Planestudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(Planestudio planEstudio) {
        this.planEstudio = planEstudio;
    }

    public Areaconocimiento getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(Areaconocimiento areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo lista">
    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }

    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }

    public List<Areaconocimiento> getListaAreaConocimiento() {
        return listaAreaConocimiento;
    }

    public void setListaAreaConocimiento(List<Areaconocimiento> listaAreaConocimiento) {
        this.listaAreaConocimiento = listaAreaConocimiento;
    }

    public List<Unidadaprendizaje> getListaUapFiltradas() {
        return listaUapFiltradas;
    }

    public void setListaUapFiltradas(List<Unidadaprendizaje> listaUapFiltradas) {
        this.listaUapFiltradas = listaUapFiltradas;
    }

    public List<Cicloescolar> getListaCiclo() {
        return listaCiclo;
    }

    public void setListaCiclo(List<Cicloescolar> listaCiclo) {
        this.listaCiclo = listaCiclo;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters objetos String">
    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public String getBusquedaFiltro() {
        return busquedaFiltro;
    }

    public void setBusquedaFiltro(String busquedaFiltro) {
        this.busquedaFiltro = busquedaFiltro;
    }
    //</editor-fold>
}
