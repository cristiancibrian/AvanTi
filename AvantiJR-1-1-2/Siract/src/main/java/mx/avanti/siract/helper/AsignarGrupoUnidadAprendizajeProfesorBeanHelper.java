/*
// * To rehange this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.avanti.siract.entity.*;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.ui.AsignarGrupoUnidadAprendizajeProfesorBeanUI;

/**
 * @author Gig@B COMPUTADORAS
 * @author Adriana
 */
public class AsignarGrupoUnidadAprendizajeProfesorBeanHelper implements Serializable {

    private List<UnidadaprendizajeImparteProfesor> listaFiltrada;
    private List<UnidadaprendizajeImparteProfesor> listaFiltrada2;

    private Profesor selecProfesor;
    private Unidadaprendizaje unidadApren;
    private Grupo grupo;
    private Profesor[] profesoresSeleccionados;
    private Unidadaprendizaje[] unidadSeleccionada;
    private Grupo[] grupoSeleccionado;
    private UnidadaprendizajeImparteProfesor imparteProfesor;
    private UnidadaprendizajeImparteProfesor selImparteProfesor;

    private Usuario usuario;
    private Profesor profesor;
    private Profesor profesor2;
    private Planestudio planEstudio;
    private Planestudio planEstudioD;
    private Areaconocimiento areaConocimiento;
    private Programaeducativo programaEducativo;
    private Programaeducativo programaEducativoD;
    private UnidadaprendizajeImparteProfesor AGUAP;
    private UnidadaprendizajeImparteProfesor selecAGUAP;

    private Cicloescolar cicloEscolar;
    private Cicloescolar cicloActual;
    private Cicloescolar ciclo;
    private List<Cicloescolar> listaActual;

    Unidadacademica auxUA = null;

    private List<Programaeducativo> listaProgEduc;
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Planestudio> listaPlanEstudio;
    private List<Areaconocimiento> listaAreaConocimiento;
    private List<Unidadaprendizaje> listaUnidadAprendizaje;
    private List<Unidadacademica> listaUnidadAcademica;
    private List<Grupo> listaGrupo;
    private List<Areaconocimiento> listaACPorUA = null;
    private List<Areaconocimiento> listaACPorPlan = null;

    private List<Areaconocimiento> listaAC = null;
    private List<Cicloescolar> listaCiclo;

    private List<UnidadaprendizajeImparteProfesor> listaAGUAPSeleccionada;

    private boolean bandPE;
    private boolean bandArea;
    private boolean bandPlan;
    private boolean bandUA;
    private boolean bandProf;
    private boolean bandGpo;
    private boolean bandTipo;
    private boolean bandSubgpo;
    private boolean bandCiclo;

    private String mensaje;
    private String rolSeleccionado;

    private String ocultarPE;
    private String ocultarPlan;

    private int idAreaAdministrativa;
    private String deshabilitarUnidadApren;
    private String deshabilitarSubgrupo;

    public AsignarGrupoUnidadAprendizajeProfesorBeanHelper() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cicloEscolar = new Cicloescolar();
        ciclo = new Cicloescolar();
        grupo = new Grupo();
        usuario = new Usuario();
        profesor = new Profesor();
        selecProfesor = new Profesor();
        planEstudio = new Planestudio();
        planEstudioD = new Planestudio();
        unidadApren = new Unidadaprendizaje();
        areaConocimiento = new Areaconocimiento();
        programaEducativo = new Programaeducativo();
        programaEducativoD = new Programaeducativo();
        selecAGUAP = new UnidadaprendizajeImparteProfesor();
        imparteProfesor = new UnidadaprendizajeImparteProfesor();
        AGUAP = new UnidadaprendizajeImparteProfesor(new Cicloescolar(), new Grupo(), new Profesor(), new Unidadaprendizaje());
        listaACPorUA = new ArrayList<Areaconocimiento>();
        listaACPorPlan = new ArrayList<Areaconocimiento>();
        listaAC = new ArrayList<Areaconocimiento>();

        listaCiclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();

        programaEducativo.setPEDid(0);
        planEstudio.setPESid(0);
        programaEducativoD.setPEDid(0);
        planEstudioD.setPESid(0);
        cicloActual = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();

        cicloEscolar.setCESid(listaCiclo.get(0).getCESid());
        listaActual = new ArrayList();

        listaActual.add(cicloActual);

    }

    public Planestudio getPlanEstudioD() {
        return planEstudioD;
    }

    public void setPlanEstudioD(Planestudio planEstudioD) {
        this.planEstudioD = planEstudioD;
    }

    public Programaeducativo getProgramaEducativoD() {
        return programaEducativoD;
    }

    public void setProgramaEducativoD(Programaeducativo programaEducativoD) {
        this.programaEducativoD = programaEducativoD;
    }

    public List<Areaconocimiento> getListaACPorUA() {
        return listaACPorUA;
    }

    public void setListaACPorUA(List<Areaconocimiento> listaACPorUA) {
        this.listaACPorUA = listaACPorUA;
    }

    public List<Areaconocimiento> getListaACPorPlan() {
        return listaACPorPlan;
    }

    public void setListaACPorPlan(List<Areaconocimiento> listaACPorPlan) {
        this.listaACPorPlan = listaACPorPlan;
    }

    public List<Areaconocimiento> getListaAC() {
        return listaAC;
    }

    public void setListaAC(List<Areaconocimiento> listaAC) {
        this.listaAC = listaAC;
    }

    public List<Cicloescolar> getListaActual() {
        return listaActual;
    }

    public void setListaActual(List<Cicloescolar> listaActual) {
        this.listaActual = listaActual;
    }

    public Cicloescolar getCiclo() {
        return ciclo;
    }

    public void setCiclo(Cicloescolar ciclo) {
        this.ciclo = ciclo;
    }

    public int getIdAreaAdministrativa() {
        return idAreaAdministrativa;
    }

    public void setIdAreaAdministrativa(int idAreaAdministrativa) {
        this.idAreaAdministrativa = idAreaAdministrativa;
    }

    public String getOcultarPE() {
        return ocultarPE;
    }

    public void setOcultarPE(String ocultarPE) {
        this.ocultarPE = ocultarPE;
    }

    public String getOcultarPlan() {
        return ocultarPlan;
    }

    public void setOcultarPlan(String ocultarPlan) {
        this.ocultarPlan = ocultarPlan;
    }

    public Cicloescolar getCicloEscolar() {
        return cicloEscolar;
    }

    public void setCicloEscolar(Cicloescolar cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public Cicloescolar getCicloActual() {
        return cicloActual;
    }

    public void setCicloActual(Cicloescolar cicloActual) {
        this.cicloActual = cicloActual;
    }

    public List<Programaeducativo> getListaProgEduc() {
        return listaProgEduc;
    }

    public void setListaProgEduc(List<Programaeducativo> listaProgEduc) {
        this.listaProgEduc = listaProgEduc;
    }

    public List<Cicloescolar> getListaCiclo() {
        return listaCiclo;
    }

    public void setListaCiclo(List<Cicloescolar> listaCiclo) {
        this.listaCiclo = listaCiclo;
    }

    public Profesor getSelecProfesor() {
        return selecProfesor;
    }

    public void setSelecProfesor(Profesor selecProfesor) {
        this.selecProfesor = selecProfesor;
    }

    public Unidadaprendizaje getUnidadApren() {
        return unidadApren;
    }

    public void setUnidadApren(Unidadaprendizaje unidadApren) {
        this.unidadApren = unidadApren;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getDeshabilitarSubgrupo() {
        return deshabilitarSubgrupo;
    }

    public void setDeshabilitarSubgrupo(String deshabilitarSubgrupo) {
        this.deshabilitarSubgrupo = deshabilitarSubgrupo;
    }

    public Profesor[] getProfesoresSeleccionados() {
        return profesoresSeleccionados;
    }

    public void setProfesoresSeleccionados(Profesor[] profesoresSeleccionados) {
        this.profesoresSeleccionados = profesoresSeleccionados;
    }

    public Unidadaprendizaje[] getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(Unidadaprendizaje[] unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public Grupo[] getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Grupo[] grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public UnidadaprendizajeImparteProfesor getImparteProfesor() {
        return imparteProfesor;
    }

    public void setImparteProfesor(UnidadaprendizajeImparteProfesor imaparteProfesor) {
        this.imparteProfesor = imaparteProfesor;
    }

    public UnidadaprendizajeImparteProfesor getSelImparteProfesor() {
        return selImparteProfesor;
    }

    public void setSelImparteProfesor(UnidadaprendizajeImparteProfesor selImparteProfesor) {
        this.selImparteProfesor = selImparteProfesor;
    }

    public List<UnidadaprendizajeImparteProfesor> getListaAGUAPSeleccionada() {
        return listaAGUAPSeleccionada;
    }

    public void setListaAGUAPSeleccionada(List<UnidadaprendizajeImparteProfesor> listaAGUAPSeleccionada) {
        this.listaAGUAPSeleccionada = listaAGUAPSeleccionada;
    }

    public UnidadaprendizajeImparteProfesor getAGUAP() {
        return AGUAP;
    }

    public void setAGUAP(UnidadaprendizajeImparteProfesor AGUAP) {
        this.AGUAP = AGUAP;
    }

    public UnidadaprendizajeImparteProfesor getSelecAGUAP() {
        return selecAGUAP;
    }

    public void setSelecAGUAP(UnidadaprendizajeImparteProfesor selecAGUAP) {
        this.selecAGUAP = selecAGUAP;
    }

    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
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

    public Areaconocimiento getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(Areaconocimiento areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public Planestudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(Planestudio planEstudio) {
        this.planEstudio = planEstudio;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public void setListaAreaConocimiento(List<Areaconocimiento> listaAreaConocimiento) {
        this.listaAreaConocimiento = listaAreaConocimiento;
    }

    public void setListaUnidadAprendizaje(List<Unidadaprendizaje> listaUnidadAprendizaje) {
        this.listaUnidadAprendizaje = listaUnidadAprendizaje;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setListaFiltrada(List<UnidadaprendizajeImparteProfesor> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<UnidadaprendizajeImparteProfesor> getListaFiltrada2() {
        return listaFiltrada2;
    }

    public void setListaFiltrada2(List<UnidadaprendizajeImparteProfesor> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    }

    public String getDeshabilitarUnidadApren() {
        return deshabilitarUnidadApren;
    }

    public void setDeshabilitarUnidadApren(String deshabilitarUnidadApren) {
        this.deshabilitarUnidadApren = deshabilitarUnidadApren;
    }

    public List<UnidadaprendizajeImparteProfesor> filtrado(String campo, String busqueda) {
        getUsuarioTienePE();
        String cambioBus = busqueda.toLowerCase();
        String cambioObj = "";

        listaFiltrada = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor();

        if (busqueda.trim().length() > 0) {
            listaFiltrada.clear();

            for (UnidadaprendizajeImparteProfesor uaip : ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor()) {
                uaip.setGrupoGPOid(ServiceFacadeLocator.getInstancGrupoFacade().buscarGrupo(uaip.getGrupoGPOid().getGPOid()));
                uaip.setProfesorPROid(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(uaip.getProfesorPROid().getPROid()));
                uaip.setUnidadAprendizajeUAPid(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(uaip.getUnidadAprendizajeUAPid().getUAPid()));

                cambioObj = uaip.getUIPtipoSubgrupo().toLowerCase();
                if (cambioObj.startsWith(cambioBus)) {
                    listaFiltrada.add(uaip);
                } else {
                    cambioObj = uaip.getUIPsubgrupo().toLowerCase();
                    if (cambioObj.startsWith(cambioBus)) {
                        listaFiltrada.add(uaip);
                    } else {
                        cambioObj = uaip.getProfesorPROid().getPROnombre().toLowerCase();
                        if (cambioObj.startsWith(cambioBus)) {
                            listaFiltrada.add(uaip);
                        } else {
                            cambioObj = uaip.getProfesorPROid().getPROapellidoPaterno().toLowerCase();
                            if (cambioObj.startsWith(cambioBus)) {
                                listaFiltrada.add(uaip);
                            } else {
                                cambioObj = uaip.getProfesorPROid().getPROapellidoMaterno().toLowerCase();
                                if (cambioObj.startsWith(cambioBus)) {
                                    listaFiltrada.add(uaip);
                                } else {
                                    String cambioObjNum = Integer.toString(uaip.getProfesorPROid().getPROnumeroEmpleado());
                                    if (cambioObjNum.startsWith(busqueda)) {
                                        listaFiltrada.add(uaip);
                                    } else {
                                        cambioObj = uaip.getUnidadAprendizajeUAPid().getUAPnombre().toLowerCase();
                                        if (cambioObj.startsWith(cambioBus)) {
                                            listaFiltrada.add(uaip);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }
        }

        return listaFiltrada;
    }

    public void seleccionarRegistro(String c) {

        //System.out.println("id uaip " + getAGUAP().getUIPid() + " getlista selec " + getListaAGUAPSeleccionada().get(0).getUIPid() + " select " + selecAGUAP.getUIPid());
        UnidadaprendizajeImparteProfesor aguap;

        if (getListaAGUAPSeleccionada().size() == 1) {
            aguap = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(getAGUAP().getUIPid());

            if (aguap.getReporteavancecontenidotematicoList().size() > 0) {

                deshabilitarUnidadApren = "true";

            } else {
                deshabilitarUnidadApren = "false";

            }

        } else {

            aguap = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(selecAGUAP.getUIPid());
            //desabilitarUnidadApren="false";
            if (c.equals("Modificar grupo, unidad aprendizaje y profesor")) {
                aguap = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(selecAGUAP.getUIPid());
                if (aguap.getReporteavancecontenidotematicoList().size() > 0) {

                    deshabilitarUnidadApren = "true";
                    deshabilitarSubgrupo = "true";

                } else {
                    deshabilitarUnidadApren = "false";
                    deshabilitarSubgrupo = "false";
                }
            }
//       if(c.equals( "Eliminar grupo, unidad aprendizaje y profesor") ){
//            aguap = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(selecAGUAP.getUIPid()); 
//           System.out.println("holii");
//       
//               }

        }
        AGUAP = aguap;
        grupo = aguap.getGrupoGPOid();
        profesor = aguap.getProfesorPROid();
        unidadApren = aguap.getUnidadAprendizajeUAPid();
        cicloEscolar = aguap.getCicloEscolarCESid();
        planEstudio = findByPlanEstudioId(grupo.getPlanEstudioPESid().getPESid());
        programaEducativo = findProgramaEducativoById(planEstudio.getProgramaEducativoPEDid().getPEDid());
        planEstudioD = findByPlanEstudioId(grupo.getPlanEstudioPESid().getPESid());
        programaEducativoD = findProgramaEducativoById(planEstudioD.getProgramaEducativoPEDid().getPEDid());
        listaAC.clear();

        listaACPorUA = getAreaPorUA(unidadApren.getUAPid());
        listaACPorPlan = getAreaMismoPlan(planEstudio.getPESid());

        for (int x = 0; x < listaACPorUA.size(); x++) {
            System.out.println("Lista por Unidad Apren" + listaACPorUA.get(x));
            for (int y = 0; y < listaACPorPlan.size(); y++) {
                System.out.println("Lista por Plan Estudio" + listaACPorPlan.get(y));
                if (listaACPorUA.get(x).getACOid().equals(listaACPorPlan.get(y).getACOid())) {
                    areaConocimiento = listaACPorUA.get(x);
                    break;
                }

            }
        }

    }

    public void eliminarDeLista(int id) {
        try {
            for (UnidadaprendizajeImparteProfesor aguap : listaAGUAPSeleccionada) {
                if (aguap.getUIPid().equals(id)) {
                    int index = listaAGUAPSeleccionada.indexOf(aguap);
                    listaAGUAPSeleccionada.remove(index);
                    break;
                }
            }
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Metodo para validar datos repetidos
     *
     * @return Cadena con mensaje de advertencia en caso de estar repetidos
     */
    public String validarRepetidos() {
        bandPE = true;
        bandPlan = true;
        bandArea = true;
        bandUA = true;
        bandProf = true;
        bandGpo = true;
        bandTipo = true;
        bandSubgpo = true;
        bandCiclo = true;
        mensaje = "";

        List<UnidadaprendizajeImparteProfesor> lista = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(AGUAP.getProfesorPROid().getPROid()).getUnidadaprendizajeImparteProfesorList();

        for (UnidadaprendizajeImparteProfesor aguap : lista) {
            if (aguap.getGrupoGPOid().getGPOid().equals(grupo.getGPOid()) && bandGpo == true
                    && aguap.getUIPsubgrupo().equals(AGUAP.getUIPsubgrupo()) && bandSubgpo == true
                    //&& aguap.getProfesorPROid().getPROid().equals(AGUAP.getProfesorPROid().getPROid()) && bandProf == true
                    && aguap.getUnidadAprendizajeUAPid().getUAPid().equals(AGUAP.getUnidadAprendizajeUAPid().getUAPid()) && bandUA == true
                    && aguap.getCicloEscolarCESid().getCESid().equals(AGUAP.getCicloEscolarCESid().getCESid()) && bandCiclo == true
                    && aguap.getUIPtipoSubgrupo().equals(AGUAP.getUIPtipoSubgrupo()) && bandTipo == true) {
                System.out.println("\n\n\n\n Grupo" + aguap.getGrupoGPOid().getGPOid());
                System.out.println("\n\n\n\n Subgrupo" + aguap.getUIPsubgrupo());
                System.out.println("\n\n\n\n Profesor" + aguap.getProfesorPROid());
                System.out.println("\n\n\n\n UnidadAprendizaje" + aguap.getUnidadAprendizajeUAPid().getUAPid());
                System.out.println("\n\n\n\n CicloEscolar" + aguap.getCicloEscolarCESid());
                System.out.println("\n\n\n\n Tipo" + aguap.getUIPtipoSubgrupo());
                bandGpo = false;
                bandSubgpo = false;
                bandProf = false;
                bandUA = false;
                bandCiclo = false;
                System.out.println("primer if = " + unidadApren.getUAPid());

                bandTipo = false;
                System.out.println("cuarto IF");

                mensaje = "Profesor ya asignado";

            }
        }
        System.out.println("BEAN HELPER mensaje = " + mensaje);
        return mensaje;
    }

    public String validarTipo() {
        List<UnidadaprendizajeImparteProfesor> lista = ServiceFacadeLocator.getInstanceProfesorFacade().
                findProfesorById(AGUAP.getProfesorPROid().getPROid()).getUnidadaprendizajeImparteProfesorList();

        List<Profesor> lista2 = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();

        for (Profesor prof : lista2) {
            lista = prof.getUnidadaprendizajeImparteProfesorList();

            for (UnidadaprendizajeImparteProfesor materiasprof : lista) {
                switch (materiasprof.getUIPtipoSubgrupo()) {
                    case "C":
                        if (materiasprof.getUIPsubgrupo().equals(AGUAP.getUIPsubgrupo())
                                && materiasprof.getUnidadAprendizajeUAPid().equals(AGUAP.getUnidadAprendizajeUAPid())
                                && materiasprof.getGrupoGPOid().equals(AGUAP.getGrupoGPOid()) && materiasprof.getUIPtipoSubgrupo().equals(AGUAP.getUIPtipoSubgrupo())) {
                            return "C";
                        }
                        break;

                    case "T":
                        if (materiasprof.getUIPsubgrupo().equals(AGUAP.getUIPsubgrupo())
                                && materiasprof.getUnidadAprendizajeUAPid().equals(AGUAP.getUnidadAprendizajeUAPid())
                                && materiasprof.getGrupoGPOid().equals(AGUAP.getGrupoGPOid()) && materiasprof.getUIPtipoSubgrupo().equals(AGUAP.getUIPtipoSubgrupo())) {
                            return "T";
                        }
                        break;

                    case "L":
                        if (materiasprof.getUIPsubgrupo().equals(AGUAP.getUIPsubgrupo())
                                && materiasprof.getUnidadAprendizajeUAPid().equals(AGUAP.getUnidadAprendizajeUAPid())
                                && materiasprof.getGrupoGPOid().equals(AGUAP.getGrupoGPOid()) && materiasprof.getUIPtipoSubgrupo().equals(AGUAP.getUIPtipoSubgrupo())) {
                            return "L";
                        }
                        break;
                }
            }
        }
        return "si";
    }

    public boolean validarTipo2() {
        boolean sita = false;
        List<UnidadaprendizajeImparteProfesor> lista = ServiceFacadeLocator.getInstanceProfesorFacade().
                findProfesorById(AGUAP.getProfesorPROid().getPROid()).getUnidadaprendizajeImparteProfesorList();

        List<Profesor> lista2 = ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();

        for (Profesor prof : lista2) {
            lista = prof.getUnidadaprendizajeImparteProfesorList();

            for (UnidadaprendizajeImparteProfesor materiasprof : lista) {

                if (materiasprof.getUIPsubgrupo().equals(AGUAP.getUIPsubgrupo())
                        && materiasprof.getUnidadAprendizajeUAPid().equals(AGUAP.getUnidadAprendizajeUAPid())
                        && materiasprof.getGrupoGPOid().equals(AGUAP.getGrupoGPOid())
                        && materiasprof.getUIPtipoSubgrupo().equals(AGUAP.getUIPtipoSubgrupo())
                        && materiasprof.getCicloEscolarCESid().equals(AGUAP.getCicloEscolarCESid())) {
                    System.out.println("n\n\n\n\n");
                    System.out.println("#@#@#@#@#@#@#@##@#@#@#@#@#@# SUBGRUPO" + materiasprof.getUIPsubgrupo());
                    System.out.println("#@#@#@#@#@#@#@##@#@#@#@#@#@# UNIDAD APRENDIZAJE" + materiasprof.getUnidadAprendizajeUAPid());
                    System.out.println("#@#@#@#@#@#@#@##@#@#@#@#@#@# GRUPO" + materiasprof.getGrupoGPOid());
                    System.out.println("#@#@#@#@#@#@#@##@#@#@#@#@#@# TIPO SUBGRUPO" + materiasprof.getUIPtipoSubgrupo());
                    System.out.println("#@#@#@#@#@#@#@##@#@#@#@#@#@# TIPO SUBGRUPO" + materiasprof.getCicloEscolarCESid());

                    return true;

                }

            }
        }
        return false;
    }

    /**
     * Metodo para saber si un usuario tiene un programa educativo
     */
    public void getUsuarioTienePE() {
        listaProgramaEducativo = new ArrayList<>();
        try {
            listaProgramaEducativo.clear();
        } catch (NullPointerException e) {
        }
        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador") || rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo") || rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {

            profesor = findProfesorFromUser(usuario.getUSUid());

            listaUnidadAcademica = consultaProfUAC(profesor.getPROid());
            listaProgEduc = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
            if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")) {
                listaProgramaEducativo.clear();


//                listaProgramaEducativo.add(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid()).getProgramaeducativoList().get(0));
//                programaEducativo = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid()).getProgramaeducativoList().get(0);

                profesor = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid());
                List<ResponsableProgramaEducativo> responsableProgramaEducativoList = profesor.getResponsableProgramaEducativosList();

                if (!responsableProgramaEducativoList.isEmpty()) {
                    for (ResponsableProgramaEducativo rpe : responsableProgramaEducativoList) {
                        if (!listaProgramaEducativo.contains(rpe.getProgramaeducativo())) {
                            listaProgramaEducativo.add(rpe.getProgramaeducativo());
                        }
                    }
                }else {
                    for (Integer x = 0 ; x < profesor.getProfesorPerteneceProgramaeducativoList().size() ; x++)
                        if (!listaProgramaEducativo.contains(profesor.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo()))
                            listaProgramaEducativo.add(profesor.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo());
                }
                programaEducativo = listaProgramaEducativo.get(0);

                listaPlanEstudio = buscarPlanEstudio(programaEducativo.getPEDid());
            } else if (rolSeleccionado.equals("Coordinador de Área de Conocimiento")) {

                idAreaAdministrativa = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid()).getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid().getAADid();
            } else {
                for (Unidadacademica uac : listaUnidadAcademica) {
                    for (Programaeducativo pe : listaProgEduc) {
                        try {
                            if (uac.getUACid() == pe.getUnidadAcademicaUACid().getUACid()) {
                                listaProgramaEducativo.add(pe);
                            }
                        } catch (NullPointerException ex) {

                        }
                    }
                }
            }
            ocultarPE = "true";
        } else {
            ocultarPE = "false";
        }
    }

    /**
     * Metodo para filtrar programas educativos
     */
    public void filtrarPE() {
        try {
            listaFiltrada.clear();
        } catch (NullPointerException e) {

        }

        if (getProgramaEducativo().getPEDid() == 0) {

            profesor2 = findProfesorFromUser(usuario.getUSUid());

            auxUA = consultaProfUAC(profesor2.getPROid()).get(0);
        }

    }

    public List<UnidadaprendizajeImparteProfesor> filtrado() {
        getUsuarioTienePE();
        filtroPorCiclo();
        System.out.println("Programa educativo" + programaEducativo.getPEDid());
        System.out.println("Programa educativoD" + programaEducativoD.getPEDid());
        if (rolSeleccionado.equals("Coordinador de Área de Conocimiento")) {
            filtrarPorAreaAdministrativa();
            ocultarPE = "false";
        }

        if (programaEducativo.getPEDid() != 0 && programaEducativo.getPEDid() != null) {
            filtrarPorPE();

            if (rolSeleccionado.equals("Responsable de Programa Educativo")) {
                ocultarPE = "false";
            }
            if (planEstudio.getPESid() != null) {

                if (planEstudio.getPESid() != 0) {
                    filtrarPorPlan();
                    ocultarPlan = "true";
                }
            }

            ocultarPlan = "true";
        } else if (programaEducativoD.getPEDid() != 0 && programaEducativoD.getPEDid() != null) {
            filtrarPorPEE();

            if (rolSeleccionado.equals("Responsable de Programa Educativo")) {
                ocultarPE = "false";
            }
            if (planEstudioD.getPESid() != null) {

                if (planEstudioD.getPESid() != 0) {
                    filtrarPorPlan();
                    ocultarPlan = "true";
                }
            }

            ocultarPlan = "true";
        } else {
            try {
                ocultarPlan = "false";
                planEstudio.setPESid(0);
                planEstudioD.setPESid(0);
                listaPlanEstudio.clear();
            } catch (Exception e) {
            }
        }

        return listaFiltrada;
    }

    /**
     * Metodo para filtrar por Ciclo Escolar
     */
    public void filtroPorCiclo() {
        listaFiltrada = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar(cicloEscolar.getCESid()).getUnidadaprendizajeImparteProfesorList();

    }

    /**
     * Metodo para filtrar por Programa Educativo
     */
    public void filtrarPorPE() {
        listaFiltrada = ServiceFacadeLocator.getInstanceAsignarGrupoUnidadAprendizajeProfesorFacade().getAsignacionPorCesPE(cicloEscolar.getCESid(), programaEducativo.getPEDid());

        listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaEducativo.getPEDid());

    }

    public void filtrarPorPEE() {
        listaFiltrada = ServiceFacadeLocator.getInstanceAsignarGrupoUnidadAprendizajeProfesorFacade().getAsignacionPorCesPE(cicloEscolar.getCESid(), programaEducativoD.getPEDid());

        listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaEducativoD.getPEDid());

    }
// public void mostarListas() {
//        //listaFiltrada = ServiceFacadeLocator.getInstanceAsignarGrupoUnidadAprendizajeProfesorFacade().getAsignacionPorCesPE(cicloEscolar.getCESid(), programaEducativo.getPEDid());
//
//        listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
//        listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
//    }

    /**
     * Metodo para filtrar por Plan de Estudio
     */
    public void filtrarPorPlan() {
        if (planEstudio.getPESid() != 0) {
            listaFiltrada = ServiceFacadeLocator.getInstanceAsignarGrupoUnidadAprendizajeProfesorFacade().getAsignacionPorCesPlan(cicloEscolar.getCESid(), planEstudio.getPESid());
        } else {
            filtrarPorPE();
        }
    }

    /**
     * Metodo para filtrar por Area Administrativa
     */
    public void filtrarPorAreaAdministrativa() {
        listaFiltrada = asignacionesPorAreaAdministrativa(idAreaAdministrativa, cicloEscolar.getCESid());
    }

    /**
     * Metodo para filtrar Grupo por Plan de Estudios
     */
    public void filtrarGpoPorPlan() {

        try {
            listaFiltrada.clear();
        } catch (NullPointerException e) {
            listaFiltrada = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor();
            listaFiltrada.clear();
        }
        try {
            listaGrupo.clear();
        } catch (NullPointerException e) {
            listaGrupo = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            listaGrupo.clear();
        }
        if (planEstudio.getPESid() != 0) {

            listaGrupo = getGpoMismoPlan(planEstudio.getPESid());

        } else {
            if (planEstudio.getPESid() == 0) {
                for (Planestudio plan : listaPlanEstudio) {

                    listaGrupo.addAll(getGpoMismoPlan(plan.getPESid()));
                }
            }
        }

    }

    /**
     * Metodo para filtrar por Grupo
     */
    public void filtrarPorGrupo() {
        List<UnidadaprendizajeImparteProfesor> listaAux = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor();
        try {
            listaFiltrada.clear();
            listaAux.clear();
        } catch (NullPointerException e) {
            listaFiltrada = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor();
            listaAux = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().getListaUnidadaprendizajeImparteProfesor();
            listaFiltrada.clear();
            listaAux.clear();
        }

        for (Grupo grup : listaGrupo) {
            listaAux.addAll(ServiceFacadeLocator.getInstancGrupoFacade().buscarGrupo(grup.getGPOid()).getUnidadaprendizajeImparteProfesorList());

        }
        for (Coordinadorareaadministrativa caa : ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa()) {
            for (UnidadaprendizajeImparteProfesor uaip : listaAux) {

                if (caa.getUnidadaprendizajeList().get(0).getUAPid().equals(uaip.getUnidadAprendizajeUAPid().getUAPid())) {
                    listaFiltrada.add(uaip);
                    System.out.println("\n\n\n " + uaip.getUnidadAprendizajeUAPid().getUAPid());
                } else {

                }
            }
        }
    }

    /**
     * Metodo para filtrar por Profesor, Tipo de grupo y Subgrupo.
     *
     * @param busqueda Campo con el dato a buscar
     * @return Lista de UnidadesAprendizajeImparteProfesor encontradas
     */
    public List<UnidadaprendizajeImparteProfesor> filtrarTextbox(String busqueda) {

        String cambioBus = busqueda.toLowerCase();
        String cambioObj = "";
        if (busqueda.trim().length() > 0) {
            try {
                listaFiltrada2 = new ArrayList<UnidadaprendizajeImparteProfesor>();
            } catch (Exception e) {

            }

            for (UnidadaprendizajeImparteProfesor uaip : listaFiltrada) {
                cambioObj = uaip.getUIPtipoSubgrupo().toLowerCase();
                if (cambioObj.startsWith(cambioBus)) {
                    listaFiltrada2.add(uaip);
                } else {
                    cambioObj = uaip.getUIPsubgrupo().toLowerCase();
                    if (cambioObj.startsWith(cambioBus)) {
                        listaFiltrada2.add(uaip);
                    } else {
                        cambioObj = uaip.getProfesorPROid().getPROnombre().toLowerCase();
                        if (cambioObj.startsWith(cambioBus)) {
                            listaFiltrada2.add(uaip);
                        } else {
                            cambioObj = uaip.getProfesorPROid().getPROapellidoPaterno().toLowerCase();
                            if (cambioObj.startsWith(cambioBus)) {
                                listaFiltrada2.add(uaip);
                            } else {
                                cambioObj = uaip.getProfesorPROid().getPROapellidoMaterno().toLowerCase();
                                if (cambioObj.startsWith(cambioBus)) {
                                    listaFiltrada2.add(uaip);
                                } else {
                                    String cambioObjNum = Integer.toString(uaip.getProfesorPROid().getPROnumeroEmpleado());
                                    if (cambioObjNum.startsWith(busqueda)) {
                                        listaFiltrada2.add(uaip);
                                    } else {
                                        cambioObjNum = Integer.toString(uaip.getUnidadAprendizajeUAPid().getUAPclave());
                                        if (cambioObjNum.startsWith(busqueda)) {
                                            listaFiltrada2.add(uaip);
                                        } else {
                                            cambioObj = uaip.getUnidadAprendizajeUAPid().getUAPnombre().toLowerCase();
                                            if (cambioObj.startsWith(busqueda)) {
                                                listaFiltrada2.add(uaip);
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }
        } else {
            listaFiltrada2 = filtrado();
        }
        return listaFiltrada2;
    }

    public void asociarDatosConsulta() {
        for (UnidadaprendizajeImparteProfesor uaip : listaFiltrada) {
            uaip.setGrupoGPOid((ServiceFacadeLocator.getInstancGrupoFacade().buscarGrupo(uaip.getGrupoGPOid().getGPOid())));
            uaip.setProfesorPROid(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(uaip.getProfesorPROid().getPROid()));
            uaip.setUnidadAprendizajeUAPid(ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(uaip.getUnidadAprendizajeUAPid().getUAPid()));
            uaip.setCicloEscolarCESid(ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar(uaip.getCicloEscolarCESid().getCESid()));
        }
    }

    /**
     * Obtener Area Conocimiento por Unidad de Aprendizaje
     *
     * @param uaid Id de Unidad Apredizaje
     * @return Lista de Area de Conocimientos encontradas
     */
    public List<Areaconocimiento> getAreaPorUA(int uaid) {
        Unidadaprendizaje unidadA = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(uaid);

        return unidadA.getAreaconocimientoList();

    }

    /**
     * Obtiene Reporte de Avance de Unidad Aprendizaje Imparte Profesor
     *
     * @param id Id de la Unidad de Aprendizaje
     * @return Lista de Reportes de Avance encontrados
     */
    public List<Reporteavancecontenidotematico> getReporteUAIP(int id) {

        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(id).getReporteavancecontenidotematicoList();

    }

    /**
     * Agrega una asignacion unidad aprendizaje imparte profesor
     *
     * @param uaip Objeto tipo UnidadAprendizajeImparteProfesor
     */
    public void agregarUniAprenImparteProfe(UnidadaprendizajeImparteProfesor uaip) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().agregarUniAprenImparteProfe(uaip);

    }

    /**
     * Eliminar Unidad Aprendizaje imparte profesor
     *
     * @param uaip Unidad Aprendizaje Imparte Profesor a eliminar
     */
    public void eliminarUniAprenImparteProfe(UnidadaprendizajeImparteProfesor uaip) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().eliminarUniAprenImparteProfe(uaip);
    }

    /**
     * Obtiene lista de Programa Educativos
     *
     * @return Lista de Programas Educativos
     */
    public List<Programaeducativo> getListaProgramaEducativo() {

        if (rolSeleccionado.equals("Responsable de Programa Educativo")) {
            return listaProgramaEducativo;
        } else {

            return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        }
    }

    /**
     * Metodo para obtener Areas de Conocimientos
     *
     * @return Lista de areas de conocimientos
     */
    public List<Areaconocimiento> getListaAreaConocimiento() {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().getListaAreaConocimiento();
    }

    /**
     * Metodo para obtener Unidades de Aprendizaje
     *
     * @return Lista de unidades de aprendizaje
     */
    public List<Unidadaprendizaje> getListaUnidadAprendizaje() {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().buscarUnidadesDeAprendizaje();
    }

    /**
     * Metodo para obtener Profesores
     *
     * @return Lista de Profesores
     */
    public List<Profesor> getListaProfesor() {

        return ServiceFacadeLocator.getInstanceProfesorFacade().getListaProfesor();

    }

    /**
     * Metodo para obtener Grupos
     *
     * @return Lista de Grupos
     */
    public List<Grupo> getListaGrupo() {
        return ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
    }

    /**
     * Metodo para obtener Plan de Estudio por Id
     *
     * @param idPE Id del Plan de Estudio
     * @return Plan de Estudio encontrado
     */
    public List<Planestudio> buscarPlanEstudio(int idPE) {

        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idPE).getPlanestudioList();
    }

    /**
     * Metodo para obtener Profesores que pertenecen a un Programa Educativo
     *
     * @param id Id del Programa Educativo
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> findProfesorPertenecePE(int id) {
        List<ProfesorPerteneceProgramaeducativo> lista = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id).getProfesorPerteneceProgramaeducativoList();
        List<Profesor> p = new ArrayList<>();
        for (ProfesorPerteneceProgramaeducativo pr : lista) {
            if (pr.getCicloescolar().getCESid() == cicloEscolar.getCESid()) {
                p.add(pr.getProfesor());
            }
        }
        return p;
    }

    /**
     * Metodo para obtener Area de Conocimientos de un Plan de Estudios
     *
     * @param id Id del Plan de Estudios
     * @return Lista de Areas de Conocimientos encontrados
     */
    public List<Areaconocimiento> getAreaMismoPlan(int id) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(id).getAreaconocimientoList();
    }

    /**
     * Obtiene Grupos de un Plan de Estudios
     *
     * @param id Id del Plan de Estudios
     * @return Lista de Grupos encontrados
     */
    public List<Grupo> getGpoMismoPlan(int id) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(id).getGrupoList();
    }

    /**
     * Obtiene Unidades de Aprendizaje de un Area de Conocimiento
     *
     * @param id Id del Area de Conocimiento
     * @return Lista de Unidades de Aprendizaje encontradas
     */
    public List<Unidadaprendizaje> getUAMismaArea(int id) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(id).getUnidadaprendizajeList();
    }

    /**
     * Buscar Grupo por Id
     *
     * @param idGrupo Id de Grupo
     * @return Grupo encontrado
     */
    public Grupo buscarGrupo(int idGrupo) {
        return ServiceFacadeLocator.getInstancGrupoFacade().buscarGrupo(idGrupo);
    }

    /**
     * Buscar Unidad Aprendizaje por Id
     *
     * @param idUA Id de la Unidad Aprendizaje
     * @return Unidad Aprendizaje encontrada
     */
    public Unidadaprendizaje findUnidadAprendizaje(int idUA) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(idUA);

    }

    /**
     * Metodo para obtener un Plan de Estudios por Id
     *
     * @param id Id del Plan de Estudios
     * @return Plan de Estudios encontrado
     */
    public Planestudio findByPlanEstudioId(int id) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(id);
    }

    /**
     * Metodo para buscar un Programa Educativo por Id
     *
     * @param id Id del Programa Educativo
     * @return Programa Educativo encontrado
     */
    public Programaeducativo findProgramaEducativoById(int id) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id);
    }

    /**
     * Metodo para actualizar una Unidad de Aprendizaje Imparte Profesor
     *
     * @param uaip Objeto tipo UnidadAprendizajeImparteProfesor
     */
    public void actualizarUnidadAprendizajeImparteProfesor(UnidadaprendizajeImparteProfesor uaip) {
        ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().actualizarUnidadAprendizajeImparteProfesor(uaip);
    }

    /**
     * Metodo para obtener los tipos de clase que puede tener una unidad de
     * aprendizaje es decir C,T,L,CL,PC
     *
     * @param id Id de Unidad de Aprendizaje
     * @return Lista de String con las letras del tipo : C,L,T,CL,PC
     */
    public List<String> getTipoDeUA(int id) {

        Unidadaprendizaje ua = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(id);
        ArrayList<String> tipos = new ArrayList<String>();
        //if (ua.getUAPhorasClase() != 0 || ua.getUAPhorasClaseCompletas() == null) {
        if (ua.getUAPhorasClase() != 0 || ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasClaseCompletas() == null) {
            tipos.add("C");
        }
        if (ua.getUAPhorasTaller() != 0) {
            tipos.add("T");
        }
        if (ua.getUAPhorasLaboratorio() != 0) {
            tipos.add("L");
        }

        if (ua.getUAPhorasClinica() != 0) {
            tipos.add("CL");
        }

        if (ua.getUAPhorasCampo() != 0) {
            tipos.add("PC");
        }
        return tipos;
    }

    /**
     * Metodo para obetener los profesores que pertenecen a un programa
     * educativo
     *
     * @param idPe Id del programa educativo
     * @return Lista de Profesores encontrados
     */
    public List<Profesor> getProfesorPorPE(int idPe) {
//        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idPe).getProfesorList();

        List<Profesor> profesorList = new ArrayList<Profesor>();

        // For-each que sirve para traer todos los objetos de la lista de ResponsableProgramaEducativoList,
        // posteriormente se extraen los profesores para guardarlos en una lista de profesores, se verifica que
        // no haya duplicidad.
        for (ResponsableProgramaEducativo rpe : ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(idPe).getResponsableProgramaEducativoList()) {
            if (!profesorList.contains(rpe.getProfesor())) {
                profesorList.add(rpe.getProfesor());
            }
        }
        return profesorList;
    }

    /**
     * Buscar un profesor por su usuario
     *
     * @param idUsuario Id del Usuario
     * @return Profesor encontrado
     */
    public Profesor findProfesorFromUser(int idUsuario) {
        return ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(idUsuario).getProfesorList().get(0);
    }

    /**
     * Metodo para obtener las unidades academicas a las que pertecene un
     * profesor
     *
     * @param idPro Id del profesor
     * @return Lista de unidad academica a la que pertece el profesor
     */
    public List<Unidadacademica> consultaProfUAC(int idPro) {

        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(idPro).getUnidadacademicaList();
    }

    /**
     * Metodo para obtener asignaciones por Area Adminsitrativa
     *
     * @param idAreaAdministrativa Id del Area Administrativa
     * @param idCicloE             Id del Ciclo Escolar
     * @return Lista de Asignaciones por Area Administrativa
     */
    public List<UnidadaprendizajeImparteProfesor> asignacionesPorAreaAdministrativa(int idAreaAdministrativa, int idCicloE) {
        List<Coordinadorareaadministrativa> lista = ServiceFacadeLocator.getInstanceCoordinadorAreaAdministrativaFacade().consultaGeneralCoordinadorAreaAdministrativa();
        Profesor profe = new Profesor();
        for (Coordinadorareaadministrativa co : lista) {
            if (co.getAreaAdministrativaAADid().getAADid() == idAreaAdministrativa) {
                profe = co.getProfesorPROid();
            }
        }

        List<UnidadaprendizajeImparteProfesor> listaProfes = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profe.getPROid()).getUnidadaprendizajeImparteProfesorList();
        List<UnidadaprendizajeImparteProfesor> listaUA = new ArrayList<>();
        for (UnidadaprendizajeImparteProfesor ua : listaProfes) {
            if (ua.getCicloEscolarCESid().getCESid() == idCicloE) {
                listaUA.add(ua);
            }
        }
        return listaUA;
    }

}
