package mx.avanti.siract.helper;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.ui.UsuariosBeanUI;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Razo
 * @author Kevin Arizaga
 */
public class AsignarRPEBeanHelper implements Serializable {

    private final Integer RPE_ROL_ID = 6;
    private List<Profesor> listaFiltrada;
    private List<Profesor> listaSeleccion;
    private List<Profesor> listaProfesoresTabla;
    private List<Programaeducativo> listaPE;
    private List<Usuario> listaUsuarios;
    private List<Rol> rolRPE;
    private List<Unidadacademica> listaUnidadAcademica;
    private List<Cicloescolar> listaCicloEscolar;
    private ArrayList<Profesor> listaProfesordlg;
    private Cicloescolar cicloActual;
    private Usuario usuarioLogeado;
    private UsuariosBeanUI user = new UsuariosBeanUI();
    private Profesor profesorLog;
    private String rolSeleccionado;
    private int idRPEMod;
    private int idPEMod;
    private List<ResponsableProgramaEducativo> RPEFiltrado;
    private ResponsableProgramaEducativo responsableProgramaEducativo;
    private ResponsableProgramaEducativo rpeSeleccionado;
    private Cicloescolar cicloescolar;
    private Cicloescolar filtroCicloescolar;
    private Profesor profesor;
    private Programaeducativo programaEducativo;
    private Programaeducativo filtroProgramaeducativo;
    private List<Integer> listaVersionesRpeSeleccionado;
    private Cicloescolar cicloEscolarVersion;
    private Integer version;
    private Cicloescolar nuevoCicloEscolar;

    public AsignarRPEBeanHelper() {
        responsableProgramaEducativo = new ResponsableProgramaEducativo();
        programaEducativo = new Programaeducativo();
        profesor = new Profesor();
        listaProfesordlg = new ArrayList<Profesor>();
        cicloActual = ServiceFacadeLocator.getInstanceCicloEscolarFacade()
                .cicloescolarActual();
        cicloescolar = new Cicloescolar();
        RPEFiltrado = new ArrayList<ResponsableProgramaEducativo>();
        listaVersionesRpeSeleccionado = new ArrayList<Integer>();
        cicloEscolarVersion = new Cicloescolar();
        nuevoCicloEscolar = new Cicloescolar();
        filtroCicloescolar = new Cicloescolar();
        filtroProgramaeducativo = new Programaeducativo();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters y setters">


    public void setFiltroProgramaeducativo(Programaeducativo filtroProgramaeducativo) {
        this.filtroProgramaeducativo = filtroProgramaeducativo;
    }

    public void setFiltroCicloescolar(Cicloescolar filtroCicloescolar) {
        this.filtroCicloescolar = filtroCicloescolar;
    }

    public Programaeducativo getFiltroProgramaeducativo() {
        return filtroProgramaeducativo;
    }

    public Cicloescolar getFiltroCicloescolar() {
        return filtroCicloescolar;
    }

    public Cicloescolar getNuevoCicloEscolar() {
        return nuevoCicloEscolar;
    }

    public void setNuevoCicloEscolar(Cicloescolar nuevoCicloEscolar) {
        this.nuevoCicloEscolar = nuevoCicloEscolar;
    }

    public Cicloescolar getCicloEscolarVersion() {
        return cicloEscolarVersion;
    }

    public void setCicloEscolarVersion(Cicloescolar cicloEscolarVersion) {
        this.cicloEscolarVersion = cicloEscolarVersion;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ResponsableProgramaEducativo getRpeSeleccionado() {
        return rpeSeleccionado;
    }

    public void setRpeSeleccionado(ResponsableProgramaEducativo rpeSeleccionado) {
        this.rpeSeleccionado = rpeSeleccionado;
    }

    public Cicloescolar getCicloescolar() {
        return cicloescolar;
    }

    public void setCicloescolar(Cicloescolar cicloescolar) {
        this.cicloescolar = cicloescolar;
    }

    public int getIdRPEMod() {
        return idRPEMod;
    }

    public void setIdRPEMod(int idRPEMod) {
        this.idRPEMod = idRPEMod;
    }

    public int getIdPEMod() {
        return idPEMod;
    }

    public void setIdPEMod(int idPEMod) {
        this.idPEMod = idPEMod;
    }

    public ResponsableProgramaEducativo getResponsableProgramaEducativo() {
        return responsableProgramaEducativo;
    }

    public void setResponsableProgramaEducativo(ResponsableProgramaEducativo responsableProgramaEducativo) {
        this.responsableProgramaEducativo = responsableProgramaEducativo;
    }


    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public List<Profesor> getListaSeleccion() {
        return listaSeleccion;
    }

    public void setListaSeleccion(List<Profesor> listaSeleccion) {
        this.listaSeleccion = listaSeleccion;
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public List<Profesor> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Profesor> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Profesor> getListaProfesoresTabla() {
        return listaProfesoresTabla;
    }

    public void setListaProfesoresTabla(List<Profesor> listaProfesoresTabla) {
        this.listaProfesoresTabla = listaProfesoresTabla;
    }

    public ArrayList<Profesor> getListaProfesordlg() {
        return listaProfesordlg;
    }

    public void setListaProfesordlg(ArrayList<Profesor> listaProfesordlg) {
        this.listaProfesordlg = listaProfesordlg;
    }

    public List<Programaeducativo> getListaPE() {
        return listaPE;
    }

    public void setListaPE(List<Programaeducativo> listaPE) {
        this.listaPE = listaPE;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Rol> getRolRPE() {
        return rolRPE;
    }

    public void setRolRPE(List<Rol> rolRPE) {
        this.rolRPE = rolRPE;
    }

    public List<ResponsableProgramaEducativo> getRPEFiltrado() {
        return RPEFiltrado;
    }

    public void setRPEFiltrado(List<ResponsableProgramaEducativo> RPEFiltrado) {
        this.RPEFiltrado = RPEFiltrado;
    }

    public List<Cicloescolar> getListaCicloEscolar() {
        return listaCicloEscolar;
    }

    public void setListaCicloEscolar(List<Cicloescolar> listaCicloEscolar) {
        this.listaCicloEscolar = listaCicloEscolar;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Metodo para filtrar
     *
     * @param busqueda Variable string por la cual se buscara
     * @return Regresa una lista de profesores encontrados
     */
    public List<Profesor> filtrado(String busqueda) {
        usuariosRPE();
        rpeByProf();
        listaFiltrada = listaProfesoresTabla;
        listaPE = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        return listaFiltrada;
    }

    public void filtrarPorRPE() {
        RPEFiltrado.clear();
        RPEFiltrado.add(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRPEById(rpeSeleccionado.getRPEid()));
        RPEFiltrado.addAll(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo());
    }

    /**
     * Metodo para obtener el usuario responsable del programa educativo
     */
    public void usuariosRPE() {
        rolRPE = ServiceFacadeLocator.getInstanceRolFacade().consultaGeneralRol();
        listaProfesordlg = new ArrayList();
        for (Rol rol : rolRPE) {
            if (rol.getROLtipo().equalsIgnoreCase("Responsable de Programa " + "Educativo")) {
                listaUsuarios = rol.getUsuarioList();
                break;
            }
        }
        listaProfesordlg.clear();
        for (Usuario usu : listaUsuarios) {
            if (usu.getProfesorList().size() >= 1) {
                listaProfesordlg.add(usu.getProfesorList().get(0));
            }
        }
    }

    /**
     * Metodo para encontrar los responsable de programa educativo por profesor
     */
    public void rpeByProf() {
        listaProfesoresTabla = new ArrayList();
        List<Programaeducativo> listaPE = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        for (Programaeducativo pe : listaPE) {
            if (pe.getResponsableProgramaEducativoList().size() >= 1) {
                //sentencia anterior listaProfesoresTabla.addAll(ServiceFacadeLocator.getInstanceProfesorFacade().getRPEbyPE(pe.getPEDid()));
                listaProfesoresTabla.add(pe.getResponsableProgramaEducativoList().get(0).getProfesor());
            }
        }
        for (Profesor prof : listaProfesoresTabla) {
            System.out.println(prof.getPROnombre());
        }
    }

    /**
     * Metodo para obtener programa educativo por reponsable de programa
     * educativo
     *
     * @param rpeid ID del responsable de programa educativo a buscar
     * @return Regresa un string del nombre del programa educativo
     */
    public List<ResponsableProgramaEducativo> peByRPE(int rpeid) {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findByPE(programaEducativo);
    }

    /**
     * Metodo para obtener el programa educativo por responsable de programa
     * educativo
     *
     * @param rprofid ID del responsable de programa educativo a buscar
     * @return Regresa un objeto de tipo Programaeducativo
     */
    public Programaeducativo peByRPEobj(int rprofid) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(rprofid).getResponsableProgramaEducativosList().get(0).getProgramaeducativo();
    }

    public List<Cicloescolar> obtenerCiclo() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
    }

    /**
     * Metodo para validar el profesor
     *
     * @return Regresa una variable string con una leyenda de ya existente o no
     * perteneciente
     */
    public String Validar() {
        String mensaje = "";

        List<ProfesorPerteneceProgramaeducativo> prof11 = new ArrayList<>();
        prof11.clear();

        prof11.addAll(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(responsableProgramaEducativo.getProfesor().getPROid()).getProfesorPerteneceProgramaeducativoList());
        for (ProfesorPerteneceProgramaeducativo profesorPertenecePE : prof11) {
            if (profesorPertenecePE.getProgramaeducativo().getPEDid() != programaEducativo.getPEDid() && profesorPertenecePE.getCicloescolar().getCESid() == cicloActual.getCESid()) {
                mensaje = "Este profesor no pertenece a este Programa educativo";
                return mensaje;
            }
        }
        return mensaje;
    }

    /**
     * Metodo para eliminar de la lista
     *
     * @param id ID por el cual se eliminara
     */
    public void eliminarDeLista(int id) {
        for (Profesor prof : listaSeleccion) {
            if (prof.getPROid().equals(id)) {
                int index = listaSeleccion.indexOf(prof);
                listaSeleccion.remove(index);
                break;
            }
        }
    }

    /**
     * Metedo para buscar un Profesor por Id
     *
     * @param IdProfesor
     * @return Profesor
     */
    public Profesor buscarProfesorPorID(int IdProfesor) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(IdProfesor);
    }

    /**
     * Método para obtener el ciclo actual de Ciclo Escolar
     *
     * @return
     */
    public Cicloescolar getCicloActual() {
        return cicloActual;
    }

    /**
     * Metodo para agregar un profesor
     *
     * @param profesor
     */
    public void agregarProfesor(Profesor profesor) {
        ServiceFacadeLocator.getInstanceProfesorFacade().agregarProfesor(profesor);
    }

    /**
     * Metodo para busqueda de programa educativo por Id
     *
     * @param id
     * @return Programa educativo
     */
    public Programaeducativo buscarProgramaEducativoPorID(int id) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id);
    }

    /**
     * Método para buscar un ciclo escolar por medio de su ID.
     *
     * @param id
     * @return Objeto de tipo Ciclo Escolar.
     */
    public Cicloescolar buscarCicloEscolarPorID(int id) {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar(id);
    }

    /**
     * Método para modificar al Responsable de Programa Educativo y eliminar el rol considerando la condición.
     *
     * @param profesor
     * @param programaeducativo
     * @param cicloescolar
     */
    public void modificarResponsablePEbyID(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
        ResponsableProgramaEducativo viejoResponsableProgramaEducativo = ServiceFacadeLocator
                .getInstanceResponsableProgramaEducativoFacade()
                .findRPEbyProfesorProgramaEducativoAndCicloescolar(
                        rpeSeleccionado.getProfesor(),
                        rpeSeleccionado.getProgramaeducativo(),
                        rpeSeleccionado.getCicloEscolar());
        if (!profesor.getPROid().equals(viejoResponsableProgramaEducativo.getProfesor().getPROid())) {
            removerRolAntiguoRPE(viejoResponsableProgramaEducativo);
        }
        if (!profesor.getUsuarioUSUid().getRolList().contains(ServiceFacadeLocator.getInstanceRolFacade().getRolById(RPE_ROL_ID))
                && cicloescolar.getCESid() == cicloActual.getCESid()) {
            Usuario usuario = profesor.getUsuarioUSUid();
            usuario.getRolList().add(ServiceFacadeLocator.getInstanceRolFacade().getRolById(RPE_ROL_ID));
            ServiceFacadeLocator.getInstanceUsuarioFacade().actualizarUsuario(usuario);
        }
        viejoResponsableProgramaEducativo.setProfesor(profesor);
        viejoResponsableProgramaEducativo.setProgramaeducativo(programaeducativo);
        viejoResponsableProgramaEducativo.setCicloEscolar(cicloescolar);
        ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().modificarRPE(viejoResponsableProgramaEducativo);
    }

    /**
     * Método para eliminar al Responsable de Programa Educativo por medio del ID del RPE.
     * Estado: Por el momento esta sin uso.
     *
     * @param responsableProgramaEducativo
     */
    public void eliminarResponsablePEbyID(ResponsableProgramaEducativo responsableProgramaEducativo) {
        ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().eliminarRPE(responsableProgramaEducativo);
    }

    /**
     * Método para eliminar al Responsable de Programa Educativo por medio de Profesor, Programa Educativo y Ciclo Escolar.
     * Estado: Por el momento no esta en uso.
     *
     * @param profesor
     * @param programaeducativo
     * @param cicloescolar
     */
    public void eliminarResponsablePEbyData(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
        ResponsableProgramaEducativo responsableProgramaEducativo = buscarRPEporDatos(profesor, programaeducativo, cicloescolar);
        ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().eliminarRPE(responsableProgramaEducativo);
    }

    /**
     * Método para eliminar al Responsable de Programa Educativo por medio de Profesor, Programa Educativo y Ciclo Escolar.
     * La diferencia con el método de arriba es que este especifica la versión a eliminar.
     * Estado: Es el que se utiliza al momento de eliminar en RPE.
     *
     * @param profesor
     * @param programaEducativo
     * @param cicloescolar
     * @param version
     */
    public void eliminarResponsablePEbyData(Profesor profesor, Programaeducativo programaEducativo, Cicloescolar cicloescolar, Integer version) {
        ResponsableProgramaEducativo responsableProgramaEducativo = new ResponsableProgramaEducativo(profesor, programaEducativo, cicloescolar, version);
        ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().eliminarRpeVersion(responsableProgramaEducativo);
    }

    /**
     * Método que busca al Responsable de Programa Educativo por medio de las entidades Profesor, Programa Educativo y Ciclo Escolar.
     *
     * @param profesor
     * @param programaeducativo
     * @param cicloescolar
     * @return Responsable Educativo especificado con los datos enviados.
     */
    public ResponsableProgramaEducativo buscarRPEporDatos(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRPEbyProfesorProgramaEducativoAndCicloescolar(profesor, programaeducativo, cicloescolar);
    }

    /**
     * Método para validar las asignaciones al momento de agregar.
     * Estado: No tiene buen uso por el momento.
     *
     * @return cadena con el mensaje a mostrar, si es nada no encontro asignaciones
     */
    public String validarAsignado() {
        List<ResponsableProgramaEducativo> allRPEinDataBase = ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo();
        Programaeducativo programa = buscarProgramaEducativoPorID(programaEducativo.getPEDid());
        Profesor profe = buscarProfesorPorID(profesor.getPROid());
        for (ResponsableProgramaEducativo rpe : allRPEinDataBase) {
            if (rpe.getProfesor().getPROid() == profesor.getPROid()
                    && rpe.getProgramaeducativo().getPEDid() == programaEducativo.getPEDid()
                    && rpe.getCicloEscolar().getCESid() != cicloescolar.getCESid()) {
                return "nada";
            }
            if (rpe.getProfesor().getPROid() != profe.getPROid()
                    && rpe.getProgramaeducativo().getPEDid() == programaEducativo.getPEDid()
                    && rpe.getCicloEscolar().getCESid() != cicloescolar.getCESid()) {
                return "nada";
            }
            if (rpe.getProfesor().getPROid() == profesor.getPROid()
                    && rpe.getProgramaeducativo().getPEDid() == programaEducativo.getPEDid()
                    && rpe.getCicloEscolar().getCESid() == cicloescolar.getCESid()) {
                return "Este profesor ya es responsable de este programa educativo en el ciclo actual";
            }
            if (rpe.getProgramaeducativo().getPEDid() == programaEducativo.getPEDid()
                    && rpe.getCicloEscolar().getCESid() == cicloActual.getCESid()) {
                return "Este programa educativo ya tiene un responsable asignado en el ciclo actual";
            }
            if (rpe.getProfesor().getPROid() == profe.getPROid()
                    && rpe.getCicloEscolar().getCESid() == cicloescolar.getCESid()) {
                return "Este profesor ya es responsable de otro programa educativo en el ciclo actual";
            }
        }
        return "nada";
    }

    /**
     * Método para agregar un nuevo Responsable de Programa Educativo a la base de datos.
     *
     * @param nuevoResponsableProgramaEducativo
     */
    public void agregarNuevoRPE(ResponsableProgramaEducativo nuevoResponsableProgramaEducativo) {
        ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().agregarRPE(nuevoResponsableProgramaEducativo);
    }

    /**
     * Método para obtener la lista de los profesores que tienen el ROL de Responsable Programa Educativo.
     * El ID del RPE es 6
     *
     * @return Lista con profesores que tienen el ROL de Responsable de Programa Educativo.
     */
    public List<Profesor> obtenerListaProfesorRolRPE() {
        Rol rol = ServiceFacadeLocator.getInstanceRolFacade().getRolById(RPE_ROL_ID);
        List<Profesor> profesorRolRPE = new ArrayList<Profesor>();
        List<Profesor> profesorList = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
        for (Profesor profesor : profesorList) {
            try {
                if (profesor.getUsuarioUSUid().getRolList().contains(rol))
                    profesorRolRPE.add(profesor);
            } catch (Exception e) {
            }
        }
        return profesorRolRPE;
    }

    /**
     * Método para ordenar una lista de Responsable de Programa Educativo por ciclo.
     * Estado: Está en uso, sin embargo se planea realizar una mejora a este método para
     * implementar la ordenación por medio de los Collections.
     *
     * @param lista
     * @return
     */
    public List<ResponsableProgramaEducativo> ordenarListaPorCiclo(List<ResponsableProgramaEducativo> lista) {
        List<ResponsableProgramaEducativo> listaOrdenada = null;
        try {
            listaOrdenada = new ArrayList<ResponsableProgramaEducativo>();
            Integer cicloMenor = lista.get(0).getCicloEscolar().getCESid();
            Integer cicloMayor = cicloActual.getCESid();
            for (ResponsableProgramaEducativo rpe :
                    lista) {
                if (cicloMenor > rpe.getCicloEscolar().getCESid()) {
                    cicloMenor = rpe.getCicloEscolar().getCESid();
                }
            }
            for (Integer x = cicloMayor; x >= cicloMenor; x--) {
                for (ResponsableProgramaEducativo rpe :
                        lista) {
                    if (rpe.getCicloEscolar().getCESid() == x) {
                        listaOrdenada.add(rpe);
                    }
                }
            }
        } catch (Exception exception) {
            return lista;
        }
        return listaOrdenada;
    }

    /**
     * Método para encontrar un Responsable de Programa Educativo por medio de Programa Educativo y Ciclo Escolar.
     *
     * @param ciclo
     * @param programaEducativo
     * @return Objeto de tipo Responsable de Programa Educativo.
     */
    public ResponsableProgramaEducativo findRPEbyProgramaEducativoAndCicloescolar(Cicloescolar ciclo, Programaeducativo programaEducativo) {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRPEbyProgramaEducativoAndCicloescolar(ciclo, programaEducativo);
    }

    /**
     * Método para filtrar una lista por un Ciclo Escolar especificado.
     *
     * @return Lista de Responsable de Programa Educativo filtrados por el Ciclo Escolar especificado.
     */
    public List<ResponsableProgramaEducativo> filtrarRpePorCiclo() {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRpeByCicloEscolar(filtroCicloescolar);
    }

    /**
     * Método para filtrar Responsable de Programa Educativo por medio del Ciclo Escolar.
     * La diferencia con el método anterior es que este recibe un objeto de Ciclo Escolar.
     *
     * @param ciclo
     * @return Lista de Responsable de Programa Educativo filtrados por el Ciclo Escolar especificado.
     */
    public List<ResponsableProgramaEducativo> filtrarRpePorCiclo(Cicloescolar ciclo) {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRpeByCicloEscolar(ciclo);
    }

    /**
     * Método para filtrar una lista de Responsable de Programa Educativo por medio de Programa Educativo.
     *
     * @return Lista de Responsable de Programa Educativo filtrado por el Programa Educativo especificado.
     */
    public List<ResponsableProgramaEducativo> filtrarRpePorPE() {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findByPE(programaEducativo);
    }

    /**
     * Método para filtrar una lista de Responsable de Programa Educativo por medio del nombre del Responsable.
     *
     * @param nombre
     * @param filtrado
     * @return Lista de Responsable de Programa Educativo filtrado por el nombre del RPE.
     */
    public List<ResponsableProgramaEducativo> filtrarRpePorNombre(String nombre, List<ResponsableProgramaEducativo> filtrado) {
        List<ResponsableProgramaEducativo> listaRpePorNombre = new ArrayList<ResponsableProgramaEducativo>();
        for (ResponsableProgramaEducativo rpe :
                filtrado) {
            if (StringUtils.containsIgnoreCase(rpe.getProfesor().getPROnombre(), nombre)
                    || StringUtils.containsIgnoreCase(rpe.getProfesor().getPROapellidoPaterno(), nombre)
                    || StringUtils.containsIgnoreCase(rpe.getProfesor().getPROapellidoMaterno(), nombre)) {
                listaRpePorNombre.add(rpe);
            }
        }
        return listaRpePorNombre;
    }

    /**
     * Método para remover el ROL de un RPE que está siendo sustituido.
     *
     * @param actualRpe
     */
    public void removerRolAntiguoRPE(ResponsableProgramaEducativo actualRpe) {
        Usuario usuario = actualRpe.getProfesor().getUsuarioUSUid();
        List<Rol> actualesRoles = usuario.getRolList();
        actualesRoles.remove(ServiceFacadeLocator.getInstanceRolFacade().getRolById(RPE_ROL_ID)); //Remueve el rol de RPE.
        ServiceFacadeLocator.getInstanceUsuarioFacade().actualizarUsuario(usuario);
    }

    /**
     * Método para restablecer el ROL a un RPE si se elimina al RPE más reciente/actual.
     *
     * @param peDid
     */
    public void restablecerRolAntiguoRPE(Programaeducativo peDid) {
        ResponsableProgramaEducativo restablecerRPE = findActualRpeByProgramaEducativo(peDid);
        if (!restablecerRPE.getProfesor().getUsuarioUSUid().getRolList().contains(ServiceFacadeLocator.getInstanceRolFacade().getRolById(RPE_ROL_ID))) {
            Usuario usuario = restablecerRPE.getProfesor().getUsuarioUSUid();
            usuario.getRolList().add(ServiceFacadeLocator.getInstanceRolFacade().getRolById(RPE_ROL_ID));
            ServiceFacadeLocator.getInstanceUsuarioFacade().actualizarUsuario(usuario);
        }
    }

    /**
     * Método para filtrar una lista de Responsable de Programas Educativos donde se especifica el Programa Educativo y se evita tener duplicados.
     * Estado: En uso, sin embargo se planea realizar una mejora a este método para evitar realizar un ordenación inversa dos veces.
     * @param programaeducativo
     * @return Lista de Responsable de Programa Educativo ordenado y sin duplicar.
     */
    public List<ResponsableProgramaEducativo> filtrarSinDuplicar(Programaeducativo programaeducativo) {
        List<ResponsableProgramaEducativo> listaRPE = invertirOrdenLista(ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findByPE(filtroProgramaeducativo)));
        List<ResponsableProgramaEducativo> listaFiltradaSinDuplicar = new ArrayList<ResponsableProgramaEducativo>();
        listaFiltradaSinDuplicar.add(listaRPE.get(0));
        for (ResponsableProgramaEducativo rpe :
                listaRPE) {
            if (listaFiltradaSinDuplicar.get(listaFiltradaSinDuplicar.size() - 1).getProfesor().getPROid() != rpe.getProfesor().getPROid()) {
                listaFiltradaSinDuplicar.add(rpe);
            }
        }
        return invertirOrdenLista(listaFiltradaSinDuplicar);
    }

    /**
     * Método para filtrar una lista de Responsable de Programas Educativos donde se evita tener duplicados.
     * Estado: En uso, sin embargo se planea realizar una mejora a este método para evitar realizar un ordenación inversa dos veces.
     * @return Lista de Responsable de Programa Educativo ordenado y sin duplicar.
     */
    public List<ResponsableProgramaEducativo> filtrarSinDuplicar() {
        List<ResponsableProgramaEducativo> listaRPE = invertirOrdenLista(ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().getListaResponsableProgramaEducativo()));
        List<ResponsableProgramaEducativo> listaFiltradaSinDuplicar = new ArrayList<ResponsableProgramaEducativo>();
        listaFiltradaSinDuplicar.add(listaRPE.get(0));
        for (ResponsableProgramaEducativo rpe :
                listaRPE) {
            if (listaFiltradaSinDuplicar.get(listaFiltradaSinDuplicar.size() - 1).getProfesor().getPROid() != rpe.getProfesor().getPROid()) {
                listaFiltradaSinDuplicar.add(rpe);
            }
        }
        return invertirOrdenLista(listaFiltradaSinDuplicar);
    }

    /**
     * Método para invertir el orden que se tiene con los datos de una lista.
     * @param ordenarListaPorCiclo
     * @return Lista de Responsable de Programa Educativo invertida.
     */
    private List<ResponsableProgramaEducativo> invertirOrdenLista(List<ResponsableProgramaEducativo> ordenarListaPorCiclo) {
        List<ResponsableProgramaEducativo> nuevaListaOrdenada = new ArrayList<>();
        for (int i = ordenarListaPorCiclo.size() - 1; i >= 0; i--) {
            nuevaListaOrdenada.add(ordenarListaPorCiclo.get(i));
        }
        return nuevaListaOrdenada;
    }

    public List<Integer> getlistaVersionesRpeSeleccionado() {
        return listaVersionesRpeSeleccionado;
    }

    /**
     * Método para restablecer los valores de una lista.
     */
    public void establecerValorListaCiclosRpeSeleccionado() {
        listaVersionesRpeSeleccionado.clear();
        for (ResponsableProgramaEducativo rpe :
                ordenarListaPorCiclo(ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findByProfesor(rpeSeleccionado.getProfesor()))) {
            listaVersionesRpeSeleccionado.add(rpe.getVersion());
        }
    }

    /**
     * Método para verificar si es necesario mostrar la version en el cuadro de dialogo.
     * Tiene una función especifica al momento de agregar un nuevo RPE donde no se muestra la caja de texto para seleccionar la version.
     * Estado: En uso.
     * @return true: si se esta modificando, eliminando o versionando. false: si se esta agregando un nuevo rpe.
     */
    public boolean mostrarVersiones() {
        if (listaVersionesRpeSeleccionado.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para seleccionar al RPE con la version especifica. Tiene un uso específico cuando se
     * desea cambiar de version en el cuadro de diálogo de modificación, eliminación y versionamiento.
     */
    public void seleccionarRegistroVersion() {
        List<ResponsableProgramaEducativo> responsableProgramaEducativos = ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findRpeByProfesorAndProgramaEducativo(profesor, programaEducativo);
        for (ResponsableProgramaEducativo rpe :
                responsableProgramaEducativos) {
            if (rpe.getVersion().equals(version)) {
                cicloescolar = rpe.getCicloEscolar();
                profesor = rpe.getProfesor();
                programaEducativo = rpe.getProgramaeducativo();
            }
        }
    }

    public void seleccionarVersionModificar() {
        version = rpeSeleccionado.getVersion();
    }

    /**
     * Método para saber si se debe mostrar las versiones de un registro.
     * Solamente envia false cuando existe solo una version del registro.
     * @param i
     * @return true si hay más de una version, false: si hay solo un registro con una version.
     */
    public boolean mostrarVersiones(int i) {
        if (listaVersionesRpeSeleccionado.size() > 1 || i == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para encontrar el RPE actual por medio de Programa Educativo.
     * @param programaEducativo
     * @return Objeto de tipo Responsable de Programa Educativo.
     */
    public ResponsableProgramaEducativo findActualRpeByProgramaEducativo(Programaeducativo programaEducativo) {
        return ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findActualRpeByProgramaEducativo(programaEducativo);
    }
}