/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;
import mx.avanti.siract.ui.LoginBean;

import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * @author balta
 */
public class ProfesorBeanHelper implements Serializable {

    private LoginBean loginBeanUI;
    private Profesor profesor;
    private Profesor profesor2;
    private Profesor selecProfesor;
    private Usuario usuario;
    private Usuario usuario2;

    private Programaeducativo selectPE;
    private Programaeducativo programaEducativo;
    private Programaeducativo programaEducativo2;

    private Unidadacademica unidadAcademica;
    private ProfesorPerteneceProgramaeducativo profesorPertenece;
    private ProfesorPerteneceProgramaeducativoPK profesorPerteneceId;
    private Cicloescolar cicloEscolar, cicloEscolar2;

    private List<Profesor> listaFiltrada;
    private List<Profesor> listaFiltrada2;
    private List<ProfesorPerteneceProgramaeducativo> listaFiltradaPorCiclo;

    private List<Profesor> listaSeleccionProfesores;

    private List<Programaeducativo> listaProgramaEducativo;
    private List<Programaeducativo> listaProgEduc;
    private List<Programaeducativo> programaeducativos = new ArrayList<Programaeducativo>();
    private List<ProfesorPerteneceProgramaeducativo> listaPEMod;

    private List<Cicloescolar> listaCiclo;
    private List<Cicloescolar> listaCiclo2;

    private List<Unidadacademica> listaUnidadAcademica;

    private List<String> listaSeleccionPuesto;
    private List<String> listaSeleccionPE;
    private List<String> listaPE;

    private List<Profesor> cacheListaProfesor;

    private boolean banNumEmp;
    private boolean banNom;
    private boolean banAPaterno;
    private boolean banAMaterno;
    private boolean banUs;

    private String Mensaje;
    private String rolSeleccionado;
    private String ocultarLista;

    Unidadacademica auxUA = null;

    private String mostrarNE = "false";
    private String mostrarUsu = "false";

    private boolean transaccionCompleta = false;

    public ProfesorBeanHelper() {
        profesor = new Profesor();
        profesor.setPROid(0);
        selecProfesor = new Profesor();
        usuario = new Usuario();
        usuario2 = new Usuario();
        profesorPertenece = new ProfesorPerteneceProgramaeducativo();
        profesorPerteneceId = new ProfesorPerteneceProgramaeducativoPK();

        selectPE = new Programaeducativo();
        programaEducativo = new Programaeducativo();
        programaEducativo.setPEDid(0);
        unidadAcademica = new Unidadacademica();
        cicloEscolar = new Cicloescolar();
        cicloEscolar2 = new Cicloescolar();

        listaPE = new ArrayList<String>();

        transaccionCompleta = true;
        listaCiclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
        listaCiclo2 = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
        if (isNull(listaCiclo) || isNull(listaCiclo2))
            transaccionCompleta = false;
        transaccionCompleta = true;

        cicloEscolar.setCESid(listaCiclo.get(0).getCESid());
        cicloEscolar2.setCESid(listaCiclo2.get(0).getCESid());

    }

    public ProfesorBeanHelper(Profesor profesor) {

    }

    // <editor-fold defaultstate="collapsed" desc="Getters y setters">
    public List<ProfesorPerteneceProgramaeducativo> getListaFiltradaPorCiclo() {
        return listaFiltradaPorCiclo;
    }

    public void setListaFiltradaPorCiclo(List<ProfesorPerteneceProgramaeducativo> listaFiltradaPorCiclo) {
        this.listaFiltradaPorCiclo = listaFiltradaPorCiclo;
    }

    public List<Cicloescolar> getListaCiclo() {
        return listaCiclo;
    }

    public void setListaCiclo(List<Cicloescolar> listaCiclo) {
        this.listaCiclo = listaCiclo;
    }

    public List<Cicloescolar> getListaCiclo2() {
        return listaCiclo2;
    }

    public void setListaCiclo2(List<Cicloescolar> listaCiclo2) {
        this.listaCiclo2 = listaCiclo2;
    }

    public ProfesorPerteneceProgramaeducativoPK getProfesorPerteneceId() {
        return profesorPerteneceId;
    }

    public void setProfesorPerteneceId(ProfesorPerteneceProgramaeducativoPK profesorPerteneceId) {
        this.profesorPerteneceId = profesorPerteneceId;
    }

    public Cicloescolar getCicloEscolar() {
        return cicloEscolar;
    }

    public void setCicloEscolar(Cicloescolar cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public Cicloescolar getCicloEscolar2() {
        return cicloEscolar2;
    }

    public void setCicloEscolar2(Cicloescolar cicloEscolar2) {
        this.cicloEscolar2 = cicloEscolar2;
    }

    public void setListaProgEduc(List<Programaeducativo> listaProgEduc) {
        this.listaProgEduc = listaProgEduc;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public LoginBean getLoginBeanUI() {
        return loginBeanUI;
    }

    public void setLoginBeanUI(LoginBean loginBeanUI) {
        this.loginBeanUI = loginBeanUI;
    }

    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }

    public Unidadacademica getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(Unidadacademica unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    public List<String> getListaSeleccionPuesto() {
        return listaSeleccionPuesto;
    }

    public void setListaSeleccionPuesto(List<String> listaSeleccionPuesto) {
        this.listaSeleccionPuesto = listaSeleccionPuesto;
    }

    public List<String> getListaSeleccionPE() {
        return listaSeleccionPE;
    }

    public void setListaSeleccionPE(List<String> listaSeleccionPE) {
        this.listaSeleccionPE = listaSeleccionPE;
    }

    public List<Profesor> getListaSeleccionProfesores() {
        return listaSeleccionProfesores;
    }

    public void setListaSeleccionProfesores(List<Profesor> listaSeleccionProfesores) {
        this.listaSeleccionProfesores = listaSeleccionProfesores;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Profesor getProfesor2() {
        return profesor2;
    }

    public void setProfesor2(Profesor profesor2) {
        this.profesor2 = profesor2;
    }

    public Programaeducativo getSelectPE() {
        return selectPE;
    }

    public void setSelectPE(Programaeducativo selectPE) {
        this.selectPE = selectPE;
    }

    public Profesor getSelecProfesor() {
        return selecProfesor;
    }

    public void setSelecProfesor(Profesor selecProfesor) {
        this.selecProfesor = selecProfesor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }

    public ProfesorPerteneceProgramaeducativo getProfesorPertenece() {
        return profesorPertenece;
    }

    public void setProfesorPertenece(ProfesorPerteneceProgramaeducativo profesorPertenece) {
        this.profesorPertenece = profesorPertenece;
    }

    public void setListaFiltrada(List<Profesor> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public void setListaFiltrada2(List<Profesor> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    }

    public List<Profesor> getListaFiltrada2() {
        return listaFiltrada2;
    }

    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public Programaeducativo getProgramaEducativo2() {
        return programaEducativo2;
    }

    public void setProgramaEducativo2(Programaeducativo programaEducativo2) {
        this.programaEducativo2 = programaEducativo2;
    }

    public List<ProfesorPerteneceProgramaeducativo> getListaPEMod() {
        return listaPEMod;
    }

    public void setListaPEMod(List<ProfesorPerteneceProgramaeducativo> listaPEMod) {
        this.listaPEMod = listaPEMod;
    }

    public List<String> getListaPE() {
        return listaPE;
    }

    public void setListaPE(List<String> listaPE) {
        this.listaPE = listaPE;
    }

    public List<Unidadacademica> getListaUnidadAcademica() {
        return listaUnidadAcademica;
    }

    public void setListaUnidadAcademica(List<Unidadacademica> listaUnidadAcademica) {
        this.listaUnidadAcademica = listaUnidadAcademica;
    }

    public String getOcultarLista() {
        return ocultarLista;
    }

    public void setOcultarLista(String ocultarLista) {
        this.ocultarLista = ocultarLista;
    }

    public void setTransaccion(boolean transaccionCompleta) {
        this.transaccionCompleta = transaccionCompleta;
    }

    public boolean getTransaccion() {
        return transaccionCompleta;
    }

    // </editor-fold>

    /**
     * Metodo para filtrar por Profesor
     *
     * @param busqueda Nombre del Profesor
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> filtrado(String busqueda) {
        getUsuarioTienePE();
        filtrarPE();
        filtrarPorCiclo();

        if (programaEducativo.getPEDid() != 0) {
            listaFiltrada2 = new ArrayList<>();

            List<ProfesorPerteneceProgramaeducativo> auxiliarProfesorPertenecePEducativo = new ArrayList<ProfesorPerteneceProgramaeducativo>();

            for (ProfesorPerteneceProgramaeducativo pPertenecePEducativo : listaFiltradaPorCiclo) {
                if (programaEducativo.getPEDid() == pPertenecePEducativo.getProfesorPerteneceProgramaeducativoPK().getProgramaEducativoPEDid()) {
                    auxiliarProfesorPertenecePEducativo.add(pPertenecePEducativo);
                }
            }

            for (Profesor prof : listaFiltrada) {
                for (ProfesorPerteneceProgramaeducativo auxiliarPPP : auxiliarProfesorPertenecePEducativo) {
                    if (auxiliarPPP.getProfesorPerteneceProgramaeducativoPK().getProfesorPROid() == prof.getPROid()) {
                        listaFiltrada2.add(prof);
                    }
                }
            }
        } else {
            listaFiltrada2 = listaFiltrada;
        }
        terminaTransaccion();//////
        return listaFiltrada2;
    }

    /**
     * Método para filtrar por Profesor
     *
     * @return Lista de Profesores
     */
    public List<Profesor> filtrado2() {
        getUsuarioTienePE();
        filtrarPorPE();
        filtrarPorCiclo();
        listaFiltrada2 = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
        if (isNull(listaFiltrada2))
            transaccionCompleta = false;

        if (programaEducativo.getPEDid() != 0) {
            listaFiltrada2.clear();

            List<ProfesorPerteneceProgramaeducativo> auxiliarProfesorPertenecePEducativo = new ArrayList<ProfesorPerteneceProgramaeducativo>();

            for (ProfesorPerteneceProgramaeducativo pPertenecePEducativo : listaFiltradaPorCiclo) {
                if (programaEducativo.getPEDid() == pPertenecePEducativo.getProfesorPerteneceProgramaeducativoPK().getProgramaEducativoPEDid()) {
                    auxiliarProfesorPertenecePEducativo.add(pPertenecePEducativo);
                }
            }

            for (Profesor prof : listaFiltrada) {
                for (ProfesorPerteneceProgramaeducativo auxiliarPPP : auxiliarProfesorPertenecePEducativo) {
                    if (auxiliarPPP.getProfesorPerteneceProgramaeducativoPK().getProfesorPROid() == prof.getPROid()) {
                        listaFiltrada2.add(prof);
                    }
                }
            }
        } else {
            listaFiltrada2 = listaFiltrada;
        }
        return listaFiltrada2;
    }

    public Usuario buscarUsuarioPorID(int ID) {
        Usuario salida = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(ID);
        if (isNull(salida))
            transaccionCompleta = false;
        return salida;
    }

    public Profesor buscarProfePorID(int ID) {
        Profesor salida = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(ID);
        if (isNull(salida))
            transaccionCompleta = false;
        return salida;
    }

    /**
     * Método para guardar un usuario
     *
     * @param usuario
     */
    public void saveUsuario(Usuario usuario) {
        boolean salida;
        salida = ServiceFacadeLocator.getInstanceUsuarioFacade().agregarUsuario(usuario);
        if (salida == false)
            transaccionCompleta = false;
    }

    /**
     * Método para buscar un rol por tipo o nombre.
     *
     * @param nombre Nombre de rol a consultar
     * @return Retorna el usuario encontrado
     */
    public Rol getRol(String nombre) {
        Rol salida = null;
        salida = ServiceFacadeLocator.getInstanceRolFacade().getRolIdByRoltipo(nombre);
        if (isNull(salida))
            transaccionCompleta = false;
        return salida;
    }

    /**
     * Método que valida si un usuario esta repetido
     *
     * @param nombre Nombre de usuario a consultar
     * @return Retorna verdadero si se encuentra repetido
     */
    public boolean validarUsuarioRepetido(String nombre) {
        boolean res = false;
        Usuario us = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorNombre(nombre);
        if (isNull(us)) {
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    /**
     * Método para obtener Programa Educativo del Usuario del Profesor
     */
    public void getUsuarioTienePE() {
        listaProgramaEducativo = new ArrayList<>();
        try {
            listaProgramaEducativo.clear();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador")) {
            ocultarLista = "true";
            profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
            listaUnidadAcademica = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getUnidadacademicaList();
            listaProgEduc = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
            if (isNull(profesor2) || isNull(listaUnidadAcademica) || isNull(listaProgEduc))
                transaccionCompleta = false;

            for (Unidadacademica uac : listaUnidadAcademica) {

                for (Programaeducativo pe : listaProgEduc) {

                    if (uac.getUACid() == pe.getUnidadAcademicaUACid().getUACid()) {
                        listaProgramaEducativo.add(pe);
                    }
                }
            }

        } else if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo") || rolSeleccionado.equalsIgnoreCase("Coordinador de Formación Básica")) {
            profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);

            List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
            List<Profesor> proProfesorList = new ArrayList<Profesor>();

            List<ResponsableProgramaEducativo> listaResponsableProgramaEducativoList = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getResponsableProgramaEducativosList();
            if (!listaResponsableProgramaEducativoList.isEmpty()) {
                for (ResponsableProgramaEducativo rpe : listaResponsableProgramaEducativoList)
                    if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo())
                            && !proProfesorList.contains(rpe.getProfesor())) {
                        peProgramaeducativoList.add(rpe.getProgramaeducativo());
                        proProfesorList.add(rpe.getProfesor());
                    }
            } else {
                for (Integer x = 0; x < profesor2.getProfesorPerteneceProgramaeducativoList().size(); x++)
                    if (!peProgramaeducativoList.contains(profesor2.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo())
                            && !proProfesorList.contains(profesor2.getProfesorPerteneceProgramaeducativoList().get(x).getProfesor())) {
                        peProgramaeducativoList.add(profesor2.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo());
                        proProfesorList.add(profesor2.getProfesorPerteneceProgramaeducativoList().get(x).getProfesor());
                    }
            }
            listaProgramaEducativo.addAll(peProgramaeducativoList);
            listaFiltrada = proProfesorList;
//            listaProgramaEducativo.add(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getProgramaeducativoList().get(0));
//            listaFiltrada = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(listaProgramaEducativo.get(0).getPEDid()).getProfesorList();

            if (isNull(profesor2) || isNull(listaProgramaEducativo) || isNull(listaFiltrada))
                transaccionCompleta = false;
            for (Profesor prof : listaFiltrada) {
                prof.setUsuarioUSUid(ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(prof.getUsuarioUSUid().getUSUid()));
            }
            if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")) {

                peProgramaeducativoList.clear();
                if (!profesor2.getResponsableProgramaEducativosList().isEmpty()) {
                    for (ResponsableProgramaEducativo rpe : profesor2.getResponsableProgramaEducativosList())
                        if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                            peProgramaeducativoList.add(rpe.getProgramaeducativo());
                } else {
                    for (Integer x = 0 ; x < profesor2.getProfesorPerteneceProgramaeducativoList().size() ; x++)
                        if (!peProgramaeducativoList.contains(profesor2.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo()))
                            peProgramaeducativoList.add(profesor2.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo());
                }
                programaEducativo = peProgramaeducativoList.get(0);

//                programaEducativo = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getProgramaeducativoList().get(0);
            }
        } else {
            if (rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
                profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
                listaProgramaEducativo = getPEdeCoordinadorAreaAdmin(profesor2.getPROid());

                List<Profesor> proProfesorList = new ArrayList<Profesor>();
                for (ResponsableProgramaEducativo rpe : ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(listaProgramaEducativo.get(0).getPEDid()).getResponsableProgramaEducativoList())
                    if (!proProfesorList.contains(rpe.getProfesor()))
                        proProfesorList.add(rpe.getProfesor());

                listaFiltrada = proProfesorList;

//                listaFiltrada = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(listaProgramaEducativo.get(0).getPEDid()).getProfesorList();


                if (isNull(profesor2) || isNull(listaProgramaEducativo) || isNull(listaFiltrada))
                    transaccionCompleta = false;
            }
            ocultarLista = "false";
        }

    }

    /**
     * Método para buscar Profesores
     *
     * @param busqueda Nombre del Profesor
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> filtrarTextBox(String busqueda) {
        String CambioBus = busqueda.toLowerCase();
        String CambioObj = "";
        listaFiltrada = new ArrayList<Profesor>();
        if (busqueda.trim().length() > 0) {

            for (Profesor prof : listaFiltrada2) {
                CambioObj = prof.getPROnombre().toLowerCase();
                if (CambioObj.startsWith(CambioBus)) {
                    listaFiltrada.add(prof);
                } else {
                    CambioObj = prof.getPROapellidoPaterno().toLowerCase();
                    if (CambioObj.startsWith(CambioBus)) {
                        listaFiltrada.add(prof);
                    } else {

                        String CambioObjNum = Integer.toString(prof.getPROnumeroEmpleado());
                        if (CambioObjNum.startsWith(busqueda)) {
                            listaFiltrada.add(prof);
                        } else {
                            CambioObj = prof.getUsuarioUSUid().getUSUusuario().toLowerCase();
                            if (CambioObj.startsWith(CambioBus)) {
                                listaFiltrada.add(prof);
                            }

                        }
                    }
                }
            }
        } else {
            listaFiltrada = filtrado("");
        }

        listaFiltrada = ordenarProfesores(listaFiltrada);
        listaFiltrada = eliminarDuplicados(listaFiltrada);
        return listaFiltrada;
    }

    /**
     * Método para regresar la lista ordenada por claves
     */
    public List<Profesor> ordenarProfesores(List<Profesor> profesores) {
        Collections.sort(profesores, new Comparator<Profesor>() {
            public int compare(Profesor u1, Profesor u2) {
                if (u1.getPROid() > u2.getPROid()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return profesores;
    }

    public List<ProfesorPerteneceProgramaeducativo> ordenarProfesoresPPE(List<ProfesorPerteneceProgramaeducativo> pppe) {
        Collections.sort(pppe, new Comparator<ProfesorPerteneceProgramaeducativo>() {
            public int compare(ProfesorPerteneceProgramaeducativo u1, ProfesorPerteneceProgramaeducativo u2) {
                if (u1.getProfesor().getPROid() > u2.getProfesor().getPROid()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return pppe;
    }

    public List<Profesor> eliminarDuplicados(List<Profesor> profesores) {
        List<Profesor> nuevaLista = new ArrayList<Profesor>();
        int idActual = 0;
        for (Profesor elemento : profesores) {

            if (elemento.getPROid() != idActual) {
                nuevaLista.add(elemento);
                idActual = elemento.getPROid();
            }
        }

        return nuevaLista;
    }

    /**
     * Método para filtrar datos por Programa Educativo
     */
    public void filtrarPorPE() {
        List<Profesor> listFil = null;
        try {
            listaFiltradaPorCiclo.clear();

            listFil.clear();
        } catch (NullPointerException ex) {

            listaFiltradaPorCiclo = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getListaProfesorPertenecePE();
            listaFiltradaPorCiclo.clear();

            listFil = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
            listFil.clear();
        }
        listFil.addAll(listaFiltrada);
        listaFiltrada.clear();
        int idProfesor = 0;

        listaFiltradaPorCiclo.addAll(ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getListaProfesorPertenecePE());
        for (ProfesorPerteneceProgramaeducativo ppp : listaFiltradaPorCiclo) {
            if (Objects.equals(programaEducativo.getPEDid(), ppp.getProgramaeducativo().getPEDid()) && idProfesor != ppp.getProfesor().getPROid()) {
                listaFiltrada.add(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(ppp.getProfesor().getPROid()));
            }
            idProfesor = ppp.getProfesor().getPROid();
        }
        if (isNull(listaFiltradaPorCiclo) || isNull(listaFiltrada))
            transaccionCompleta = false;
    }

    /**
     * Método para filtrar datos por Ciclo Escolar
     */
    public void filtrarPorCiclo() {
        List<Profesor> listFil = new ArrayList<>();
        listaFiltradaPorCiclo = new ArrayList<>();
        listaFiltradaPorCiclo.clear();
        listFil.addAll(listaFiltrada);
        listaFiltrada.clear();

        int idProfesor = 0;
        Cicloescolar ciclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar(cicloEscolar2.getCESid());
        if (isNull(ciclo))
            transaccionCompleta = false;
        else
            listaFiltradaPorCiclo.addAll(ciclo.getProfesorPerteneceProgramaeducativoList());

        for (ProfesorPerteneceProgramaeducativo ppp : listaFiltradaPorCiclo) {
            if (Objects.equals(cicloEscolar2.getCESid(), ppp.getCicloescolar().getCESid())
                    && idProfesor != ppp.getProfesor().getPROid()) {
                listaFiltrada.add(ppp.getProfesor());
            }
            idProfesor = ppp.getProfesor().getPROid();

        }
        System.out.println("Tamanio de lista filtrada: " + listaFiltrada.size());
        listaFiltrada = ordenarProfesores(listaFiltrada);
        listaFiltrada = eliminarDuplicados(listaFiltrada);
        terminaTransaccion();////////
    }

    /**
     * Método para eliminar un Profesor de la lista mediante id
     *
     * @param id Id del Profesor
     */
    public void eliminarDeLista(int id) {
        try {
            for (Profesor prof : listaSeleccionProfesores) {
                if (prof.getPROid().equals(id)) {
                    int index = listaSeleccionProfesores.indexOf(prof);
                    listaSeleccionProfesores.remove(index);
                    break;
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("ENTRO AL NULO DE ELIMINACION**");
        }
    }

    public String Validar() {
        banNumEmp = true;
        banNom = true;
        banAPaterno = true;
        banAMaterno = true;
        banUs = true;
        boolean bandCiclo = true;
        List<ProfesorPerteneceProgramaeducativo> profPePert;
        Mensaje = "";
        for (Profesor prof : cacheListaProfesor) {
            profPePert = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getProfesorPertenecePorProfesor(prof.getPROid());
            if (isNull(profPePert))
                transaccionCompleta = false;
            for (ProfesorPerteneceProgramaeducativo ppp : profPePert) {
                System.out.println("asdcxz " + prof.getPROid() + " cesid " + ppp.getCicloescolar().getCESid() + " PROID " + ppp.getProfesor().getPROid());
                if (Objects.equals(ppp.getCicloescolar().getCESid(), cicloEscolar2.getCESid())) {
                    bandCiclo = true;
                    break;
                } else {
                    bandCiclo = false;
                    break;
                }

            }
            if (prof.getPROnumeroEmpleado() == profesor.getPROnumeroEmpleado() && banNumEmp == true
                    && !prof.getPROid().equals(profesor.getPROid()) && bandCiclo) {
                Mensaje = Mensaje + " Numero de Empleado, ";
                banNumEmp = false;
            }
            if (prof.getPROnombre().equals(profesor.getPROnombre()) && banNom == true
                    && !prof.getPROid().equals(profesor.getPROid()) && bandCiclo) {
                banNom = false;
                if (prof.getPROapellidoPaterno().equals(profesor.getPROapellidoPaterno()) && banAPaterno == true && bandCiclo) {
                    banAPaterno = false;
                    if (prof.getPROapellidoMaterno().equals(profesor.getPROapellidoMaterno()) && banAMaterno == true && bandCiclo) {
                        Mensaje = Mensaje + "Nombre, ";
                        Mensaje = Mensaje + "Apellido Paterno, ";
                        Mensaje = Mensaje + "Apellido Materno, ";
                        banAMaterno = false;
                    }
                }
            }
            if (prof.getPROrfc().equals(profesor.getPROrfc()) && banNumEmp == true
                    && !prof.getPROid().equals(profesor.getPROid()) && bandCiclo) {
                Mensaje = Mensaje + "RFC, ";
                banNumEmp = false;
            }
            if (prof.getUsuarioUSUid().getUSUid().equals(usuario2.getUSUid()) && banUs == true
                    && !prof.getPROid().equals(profesor.getPROid()) && bandCiclo) {
                Mensaje = Mensaje + "Usuario";
                banUs = false;
            }
        }
        return Mensaje;
    }

    /**
     * Método para validar información antes de guardarla en modificar
     *
     * @return Cadena de validacion o error
     */
    public String validarInformacionProfesor() {
        banNumEmp = true;
        banNom = true;
        banAPaterno = true;
        banAMaterno = true;
        banUs = true;
        Mensaje = "";
        System.out.println("SIZE DE CACHE LISTA PROFESO " + cacheListaProfesor.size());
        for (Profesor prof : cacheListaProfesor) {
            if (prof.getPROid() != profesor.getPROnumeroEmpleado()
                    && !prof.getPROnombre().equalsIgnoreCase(profesor.getPROnombre())
                    && !prof.getPROapellidoMaterno().equalsIgnoreCase(profesor.getPROapellidoMaterno())
                    && !prof.getPROapellidoPaterno().equalsIgnoreCase(profesor.getPROapellidoPaterno())
                    && !prof.getPROrfc().equalsIgnoreCase(profesor.getPROrfc())
                    && !prof.getUsuarioUSUid().getUSUid().equals(usuario2.getUSUid())) {

                if (prof.getPROnumeroEmpleado() == profesor.getPROnumeroEmpleado()) {
                    Mensaje = Mensaje + " Numero de Empleado, ";
                    banNumEmp = false;
                }
                if (prof.getPROnombre().equals(profesor.getPROnombre())) {
                    banNom = false;
                    if (prof.getPROapellidoPaterno().equals(profesor.getPROapellidoPaterno())) {
                        banAPaterno = false;
                        if (prof.getPROapellidoMaterno().equals(profesor.getPROapellidoMaterno())) {
                            Mensaje = Mensaje + "Nombre, ";
                            Mensaje = Mensaje + "Apellido Paterno, ";
                            Mensaje = Mensaje + "Apellido Materno, ";
                            banAMaterno = false;
                        }
                    }
                } else if (prof.getPROrfc().equalsIgnoreCase(profesor.getPROrfc())) {
                    Mensaje = Mensaje + "RFC, ";
                }
                if (prof.getUsuarioUSUid().getUSUid().equals(usuario2.getUSUid())) {
                    Mensaje = Mensaje + "Usuario";
                    banUs = false;
                }
            } else {

            }
        }
        return Mensaje;
    }

    /**
     * Método para obtener Programas Educativos de Profesores
     *
     * @return Lista de Programas Educativos encontrados
     */
    public List<ProfesorPerteneceProgramaeducativo> consultaPE() {
        List<ProfesorPerteneceProgramaeducativo> pe;
        List<ProfesorPerteneceProgramaeducativo> pe2 = new ArrayList<ProfesorPerteneceProgramaeducativo>();
        pe = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getListaProfesorPertenecePE();
        if (isNull(pe))
            transaccionCompleta = false;
        for (int i = 0; i < listaSeleccionPE.size(); i++) {
            for (int x = 0; x < pe.size(); x++) {
                if (pe.get(x).getProgramaeducativo().getPEDnombre().equals(listaSeleccionPE.get(i))) {
                    pe2.add(pe.get(x));
                }
            }
        }
        return pe2;
    }

    /**
     * Método para seleccionar un programa educativo
     *
     * @deprecated
     */
    public void seleccionarPE() {
        for (Profesor prof : ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor()) {
            if (prof.getPROid().equals(selecProfesor.getPROid())) {
                profesor = prof;
                usuario = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(prof.getUsuarioUSUid().getUSUid());
            }
        }
    }

    /**
     * Método para obtener Programas Educativos de Profesor
     *
     * @param p Objeto tipo Profesor
     * @return Lista de Programas Educativos encontrados
     */
    public List<String> getListaProgramaEducativo(Profesor p) {
        List<ProfesorPerteneceProgramaeducativo> recibe = null;
        listaPE = new ArrayList();
        try {
            if (!isNull(p.getPROid())) {
                if (p.getPROid() > 0) {
                    recibe = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(p.getPROid()).getProfesorPerteneceProgramaeducativoList();
                    if (isNull(recibe))
                        transaccionCompleta = false;
                    else {
                        for (ProfesorPerteneceProgramaeducativo pe : recibe) {
                            if (Objects.equals(pe.getProfesor().getPROid(), p.getPROid())
                                    && Objects.equals(cicloEscolar2.getCESid(), pe.getCicloescolar().getCESid())) {
                                listaPE.add(pe.getProgramaeducativo().getPEDnombre());
                            }
                        }
                    }
                } else {
                    recibe = consultaPE();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return listaPE;
    }

    /**
     * Método para filtrar Programa Educativo
     */
    public void filtrarPE() {
        listaFiltrada = new ArrayList();
        if (getProgramaEducativo().getPEDid() == 0 || getProgramaEducativo().getPEDid() != 0) {
            profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
            auxUA = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getUnidadacademicaList().get(0);
            if (isNull(auxUA) || isNull(profesor2)) {
                transaccionCompleta = false;
            }
            listaFiltrada = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().buscarUnidadAcademicaPorId(auxUA.getUACid()).getProfesorList();
        } else {
//            listaFiltrada = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativo.getPEDid()).getProfesorList();
            List<Profesor> proProfesorList = new ArrayList<Profesor>();
            for (ResponsableProgramaEducativo rpe : ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaEducativo.getPEDid()).getResponsableProgramaEducativoList())
                if (!proProfesorList.contains(rpe.getProfesor()))
                    proProfesorList.add(rpe.getProfesor());
            listaFiltrada = proProfesorList;

        }
        if (isNull(listaFiltrada))
            transaccionCompleta = false;

        terminaTransaccion();//////
    }

    /**
     * Metodo para obtener Programas Educativos
     *
     * @param prof Objeto tipo Profesor
     * @return Lista de Programas Educativos encontrados
     */
    public List<String> getListaPE(Profesor prof) {
        List<Programaeducativo> recibe = null;
        listaSeleccionPE = new ArrayList();
        if (prof.getPROid() > 0) {
            List<ProfesorPerteneceProgramaeducativo> list = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getProfesorPertenecePorProfesor(prof.getPROid());
            if (isNull(list))
                transaccionCompleta = false;
            for (ProfesorPerteneceProgramaeducativo p : list) {
                recibe.add(p.getProgramaeducativo());
            }

            for (Programaeducativo pe : recibe) {
                listaSeleccionPE.add(pe.getPEDnombre());
            }
        } else {

        }
        return listaSeleccionPE;
    }

    /**
     * Método para obtener Programas Educativos de Profesor en modificación
     *
     * @param p Objeto tipo Profesor
     */
    public void getPEModProf(Profesor p) {
        listaPEMod = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getProfesorPertenecePorProfesor(p.getPROid());
        if (isNull(listaPEMod))
            transaccionCompleta = false;
        for (ProfesorPerteneceProgramaeducativo pertenece : listaPEMod) {
            setProgramaEducativo2(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(pertenece.getProgramaeducativo().getPEDid()));////
        }
    }

    public String getMostrarNE() {
        return mostrarNE;
    }

    public void setMostrarNE(String mostrarNE) {
        this.mostrarNE = mostrarNE;
    }

    public String getMostrarUsu() {
        return mostrarUsu;
    }

    public void setMostrarUsu(String mostrarUsu) {
        this.mostrarUsu = mostrarUsu;
    }

    /**
     * Método para validar número de empleado
     * Devuelve true si esta repetido
     */
    public void ValidacionNE() {
        int banderaNE = 0;
        banderaNE = profesor.getPROnumeroEmpleado();
        System.out.print("bandera bandera ");
        System.out.println(banderaNE);
        System.out.println("RESULTADO EN MOSTRAR NE: " + mostrarNE);
        mostrarNE = "false";
        if (!(banderaNE == 0)) {
            for (Profesor p : cacheListaProfesor) {
                if (banderaNE == p.getPROnumeroEmpleado()) {
                    profesor.setPROnumeroEmpleado(0);
                    mostrarNE = "true";
                    break;
                }
            }
        } else {
            System.out.println("No se entra a la validacion");
        }
    }

    /**
     * Método para validar el nombre de Usuario
     * Devuelve True si esta repetido
     */
    public void ValidacionUsu() {
        int banderaNE = 0;
        banderaNE = profesor.getUsuarioUSUid().getUSUid();
        for (Profesor p : ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor()) {
            if (banderaNE == p.getUsuarioUSUid().getUSUid()) {
                profesor.setUsuarioUSUid(p.getUsuarioUSUid());
                mostrarUsu = "true";
                break;
            } else {
                profesor = new Profesor();
                mostrarUsu = "false";
            }
        }
    }

    /**
     * Método para actualizar las listas de la memoria cache
     */
    public void actualizarListasCache() {
        cacheListaProfesor = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();
        if (isNull(cacheListaProfesor))
            transaccionCompleta = false;
    }

    /**
     * Retorna un valor tipo boleano si el profesor contiene clases impartidas
     * del ciclo actual
     *
     * @param profesor Objeto tipo Profesor
     * @return TRUE o FALSE si contiene materias asignadas
     */
    public boolean profesorImparteProgramaEducativo(Profesor profesor) {
        int ciclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual().getCESid();
        boolean ban = false;
        Profesor p = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid());
        if (isNull(p))
            transaccionCompleta = false;
        else {
            for (UnidadaprendizajeImparteProfesor pp : p.getUnidadaprendizajeImparteProfesorList()) {
                if (pp.getCicloEscolarCESid().getCESid() == ciclo) {
                    ban = true;
                }
            }
        }
        return ban;
    }

    /**
     * Elimina los registros de la tabla ProfesorPerteneceProgramaEducativo
     *
     * @param profesor Objeto tipo Profesor
     */
    public void eliminarProfesorPerteneceProgramaEducativo(Profesor profesor) {
        List<ProfesorPerteneceProgramaeducativo> lista = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getProfesorPertenecePorProfesor(profesor.getPROid());
        if (isNull(lista))
            transaccionCompleta = false;
        else {
            ProfesorPerteneceProgramaeducativo eliminado = new ProfesorPerteneceProgramaeducativo();
            for (ProfesorPerteneceProgramaeducativo auxiliar : lista) {
                eliminado = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().eliminarProfesorPertenecePE(auxiliar);
            }
            if (isNull(eliminado))
                transaccionCompleta = false;
        }
    }

    /**
     * Se obtiene los datos de un profesor en específico en la lista cache de
     * profesorBeanHelper
     *
     * @param profesor Objeto tipo Profesor
     * @return Datos del Profesor encontrados
     */
    public Profesor obtenerProfesorListaCache(Profesor profesor) {
        Profesor profesorAuxiliarMetodo = null;

        for (Profesor profesorAuxiliar : cacheListaProfesor) {
            if (profesor.getPROnumeroEmpleado() == profesorAuxiliar.getPROnumeroEmpleado()) {
                profesorAuxiliarMetodo = profesor;
                break;
            }
        }

        return profesorAuxiliarMetodo;
    }

    /**
     * Agrega a la base de datos un registro a la tabla
     * ProgramaEducativoperteneceProfesor
     *
     * @param profesorId          Id del Profesor
     * @param programaEducativoId Id del Programa Educativo
     * @param cicloEscolarId      Id del Ciclo Escolar
     */
    public void agregarProfesorProgramaEducativo(int profesorId, int programaEducativoId, int cicloEscolarId) {
        if (profesorId != 0 && programaEducativoId != 0 && cicloEscolarId != 0) {
            profesorPerteneceId.setCicloEscolarCESid(cicloEscolarId);
            profesorPerteneceId.setProfesorPROid(profesorId);
            profesorPerteneceId.setProgramaEducativoPEDid(programaEducativoId);
            profesorPertenece.setProfesorPerteneceProgramaeducativoPK(profesorPerteneceId);
            profesorPertenece.setCicloescolar(cicloEscolar);
            profesorPertenece.setProfesor(profesor);
            profesorPertenece.setProgramaeducativo(programaEducativo);
            profesorPertenece.setProfesorPerteneceProgramaeducativoPK(profesorPerteneceId);
            if (isNull(ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().agregarProfesorPertenecePE(profesorPertenece)))
                transaccionCompleta = false;
        } else {
            System.out.println("Error con los valores de parametros");
        }
    }

    /**
     * Método para terminar la transacción. Si la bandera 'transaccionCompleta'
     * es 'true' significa que no ocurrió ningún error y se completa la
     * transacción y se cierra la sesión.
     * Pero si la bandera es 'false' no se pudo completar correctamente la
     * transacción y se llama al rollback.
     */
    public void terminaTransaccion() {
        ServiceFacadeLocator.getInstanceProfesorFacade().terminarTransaccion(transaccionCompleta);
        transaccionCompleta = true;
    }

    public Cicloescolar getCicloEscolarActual() {
        Cicloescolar resultado;
        resultado = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
        if (isNull(resultado))
            transaccionCompleta = false;
        return resultado;
    }

    public Programaeducativo findProgramaEducativoById(int id) {
        Programaeducativo resultado = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id);
        if (isNull(resultado))
            transaccionCompleta = false;
        return resultado;
    }

    public Profesor agregarProfesor(Profesor profesor) {
        Profesor resultado = ServiceFacadeLocator.getInstanceProfesorFacade().agregarProfesor(profesor);
        if (isNull(resultado))
            transaccionCompleta = false;
        return resultado;
    }

    public void modificarListaUA(Profesor profesor, Unidadacademica unidad) {
        Profesor resultado = ServiceFacadeLocator.getInstanceProfesorFacade().modificarListaUA(profesor, unidad);
        if (isNull(resultado))
            transaccionCompleta = false;
    }

    public List<Usuario> getUsuarioH() {
        List<Usuario> resultado = ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
        if (isNull(resultado))
            transaccionCompleta = false;
        return resultado;
    }

    public List usuario() {
        List listaU = new ArrayList<>();
        List<Usuario> lista = ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
        if (isNull(lista))
            transaccionCompleta = false;
        else {
            for (Usuario u : lista) {
                if (u.getProfesorList().isEmpty()) {
                    listaU.add(u);
                }
            }
        }
        return listaU;
    }

    public List<Programaeducativo> getListaProgramaEducativoH() {
        List<Programaeducativo> resultado = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        if (isNull(resultado))
            transaccionCompleta = false;
        return resultado;
    }

    public List<Cicloescolar> getListaOrdenada() {
        List<Cicloescolar> resultado = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
        if (isNull(resultado))
            transaccionCompleta = false;
        return resultado;
    }

    public List profesor_PerteneceUsu() {
        List lista = new ArrayList<>();
        List<Usuario> listaOriginal = ServiceFacadeLocator.getInstanceUsuarioFacade().obtenerUsuarios();
        if (isNull(listaOriginal))
            transaccionCompleta = false;
        else {
            for (Usuario u : listaOriginal) {
                if (u.getProfesorList() != null) {
                    lista.add(u.getProfesorList());
                }
            }
        }
        return lista;
    }

    public Boolean verificarProfesorTieneAsignaciones(int id) {
        boolean ban = true;
        Profesor pr = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(id);
        if (isNull(pr))
            transaccionCompleta = false;
        else {
            if (pr.getUnidadaprendizajeImparteProfesorList().isEmpty()) {
                ban = false;
            }
        }
        return ban;
    }

    /**
     * Metodo para eliminar Profesor
     *
     * @param id Id del Profesor
     */
    public void eliminarProfesorT(int id) {
        Profesor p = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(id);
        if (isNull(p))
            transaccionCompleta = false;
        else {
            p = ServiceFacadeLocator.getInstanceProfesorFacade().eliminarProfesor(p);
            if (isNull(p))
                transaccionCompleta = false;
        }

    }

    /**
     * Método para eliminar ProfesorPerteneceProgramaEducativo
     *
     * @param profesor Objeto tipo Profesor
     * @param ciclo    Id del ciclo
     */
    public void eliminarProfesorPerteneceProgramaEducativo(Profesor profesor, int ciclo) {
        List<ProfesorPerteneceProgramaeducativo> lista = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().getProfesorPertenecePorProfesor(profesor.getPROid());
        if (isNull(lista))
            transaccionCompleta = false;
        else {
            for (ProfesorPerteneceProgramaeducativo auxiliar : lista) {
                if (auxiliar.getCicloescolar().getCESid() == ciclo) {
                    ProfesorPerteneceProgramaeducativo eliminado = ServiceFacadeLocator.getInstanceProfesorPertenecePEFacade().eliminarProfesorPertenecePE(auxiliar);
                    if (isNull(eliminado))
                        transaccionCompleta = false;
                }
            }
        }
    }

    /**
     * Método para modificar un Profesor
     *
     * @param profesor Objeto tipo Profesor
     */
    public void modificarProfesor(Profesor profesor) {
        Profesor resultado = ServiceFacadeLocator.getInstanceProfesorFacade().modificarProfesor(profesor);
        if (isNull(resultado))
            transaccionCompleta = false;
    }

    /**
     * Método para obtener Programas Educativos por Id de Area Administrativa
     *
     * @param idProfesorCoordinador Id del Profesor
     * @return Lista de Programas Educativos encontrados
     */
    public List<Programaeducativo> getPEdeCoordinadorAreaAdmin(int idProfesorCoordinador) {
        List<Programaeducativo> lista = new ArrayList<>();
        Profesor resultado = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(idProfesorCoordinador);
        if (isNull(resultado))
            transaccionCompleta = false;
        else
            lista.add(resultado.getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid());
        return lista;
    }

    /**
     * Método de validación de un usuario de la lista de usuarios disponibles
     * con el usuario actualmente seleccionado.
     */
    public boolean validaUsuario(Usuario us) {
        boolean b = false;
        try {
            if (Objects.equals(usuario2.getUSUid(), us.getUSUid())) {
                b = true;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return b;
    }
}
