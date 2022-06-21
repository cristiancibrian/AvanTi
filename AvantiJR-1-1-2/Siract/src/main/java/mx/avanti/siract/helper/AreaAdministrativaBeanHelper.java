package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mx.avanti.siract.entity.*;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;

/**
 * @author Paco
 */
public class AreaAdministrativaBeanHelper implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">            
    private Usuario usuario;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo lista">
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Areaadministrativa> listaAreaAdFiltrada;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos String">
    private String busquedaFiltro;
    private String rol;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables primitivas">    
    private int programaEdId;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor y postConstructor">
    public AreaAdministrativaBeanHelper() {
        busquedaFiltro = "";
        programaEdId = 0;
        usuario = new Usuario();
    }
    //</editor-fold>

    /**
     * @param registro Registro de Area administrativa que contiene todo excepto
     *                 id y coordinador de area administrativa
     */
    public void agregarAreaAdministrativa(Areaadministrativa registro) {
        ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().agregarAreaAdministrativa(registro);
    }

    /**
     * @param area Registro a eliminar de Area Administrativa
     */
    public void eliminarAreaAdministrativa(Areaadministrativa area) {
        ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().eliminarAreaAdministrativa(area);
    }

    /**
     * @param areaAdministrativa
     */
    public void actualizarAreaAdministrativa(Areaadministrativa areaAdministrativa) {
        ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().modificarAreaAdministrativa(areaAdministrativa);
    }

    /**
     * @param idArea
     * @return
     */
    public Areaadministrativa buscarAreaAdministrativaPorId(int idArea) {
        return ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaAreaAdministrativaPorId(idArea);
    }

    /**
     * Método para filtrar unidad academica si es administrador o, programas
     * educativos si es cualquier otro rol.
     */
    public void filtrarPorRol() {
        Profesor profesor = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);

        if (rol.equalsIgnoreCase("Director") || rol.equalsIgnoreCase("Subdirector") || rol.equalsIgnoreCase("Administrador")) {
            listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();

            if (programaEdId == 0) {
                listaAreaAdFiltrada = ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaGeneralAreaAdministrativa();
                filtrarAreaAdPorBusqueda();
            } else {
                filtrarAreaAdPorPED();
            }
        } else if (rol.equalsIgnoreCase("Responsable de Programa Educativo") || rol.equalsIgnoreCase("Coordinador de Formación Básica")) {
            listaProgramaEducativo.clear();

            //Se aplicó una corrección en esta area, aunque el ROL de RPE no muestra el componente de Area adminitrativa.
            if (!profesor.getProfesorPerteneceProgramaeducativoList().isEmpty()) {
                for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList())
                    if (!listaProgramaEducativo.contains(rpe.getProgramaeducativo()))
                        listaProgramaEducativo.add(rpe.getProgramaeducativo());
            } else
                for (Integer x = 0; x < profesor.getProfesorPerteneceProgramaeducativoList().size(); x++)
                    if (!listaProgramaEducativo.contains(profesor.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo()))
                        listaProgramaEducativo.add(profesor.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo());

        } else if (rol.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
            if (!profesor.getCoordinadorareaadministrativaList().isEmpty()) {
                listaAreaAdFiltrada = new ArrayList<>();
                listaAreaAdFiltrada.add(profesor.getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid());
            }
        }
    }

    /**
     * Método para filtrar planes de estudio por programa educativo.
     */
    public void filtrarAreaAdPorPED() {
        listaAreaAdFiltrada = new ArrayList<>();

        for (int i = 0; i < listaProgramaEducativo.size(); i++) {
            if (listaProgramaEducativo.get(i).getPEDid() == programaEdId) {
                listaAreaAdFiltrada.addAll(listaProgramaEducativo.get(i).getAreaadministrativaList());
            }
        }

        filtrarAreaAdPorBusqueda();
    }

    /**
     * Método para filtrar unidades de aprendizaje.
     */
    public void filtrarAreaAdPorBusqueda() {
        List<Areaadministrativa> listaAreaAdFiltradaAux = new ArrayList<>();
        listaAreaAdFiltrada = ordenarListaAreaAd(listaAreaAdFiltrada);
        busquedaFiltro = busquedaFiltro.toLowerCase();

        if (busquedaFiltro.trim().length() > 0) {
            for (Areaadministrativa areaAd : listaAreaAdFiltrada) {
                if (areaAd.getAADnombre().toLowerCase().startsWith(busquedaFiltro)) {
                    listaAreaAdFiltradaAux.add(areaAd);
                }
            }

            listaAreaAdFiltrada = listaAreaAdFiltradaAux;
        }
    }

    /**
     * Metodo para ordenar una lista de unidades por su número de unidad
     *
     * @param listaAreaAd
     * @return Unidades ordenadas por numero
     */
    public List<Areaadministrativa> ordenarListaAreaAd(List<Areaadministrativa> listaAreaAd) {
        Collections.sort(listaAreaAd, (Areaadministrativa areaAd1, Areaadministrativa areaAd2)
                -> areaAd1.getProgramaEducativoPEDid().getPEDid() > areaAd2.getProgramaEducativoPEDid().getPEDid() ? 1 : -1);
        return listaAreaAd;
    }

    public boolean validarNombrerRepetido(Areaadministrativa areaAd) {
        for (Areaadministrativa aad : ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().consultaGeneralAreaAdministrativa()) {
            if (aad.getAADnombre().equals(areaAd.getAADnombre()) && aad.getProgramaEducativoPEDid().getPEDid().equals(areaAd.getProgramaEducativoPEDid().getPEDid())
                    && !aad.getAADid().equals(areaAd.getAADid())) {
                return true;
            }
        }

        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
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

    public List<Areaadministrativa> getListaAreaAdFiltrada() {
        return listaAreaAdFiltrada;
    }

    public void setListaAreaAdFiltrada(List<Areaadministrativa> listaAreaAdFiltrada) {
        this.listaAreaAdFiltrada = listaAreaAdFiltrada;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos String">
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getBusquedaFiltro() {
        return busquedaFiltro;
    }

    public void setBusquedaFiltro(String busquedaFiltro) {
        this.busquedaFiltro = busquedaFiltro;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de variables primitivas">
    public int getProgramaEdId() {
        return programaEdId;
    }

    public void setProgramaEdId(int programaEdId) {
        this.programaEdId = programaEdId;
    }
    //</editor-fold>
}
