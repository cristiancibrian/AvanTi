/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;

/**
 * @author Oscar D. Sanchez
 */
public class GrupoBeanHelper implements Serializable {

    private List<Planestudio> listaPlanes;
    private Grupo grupo;
    private Programaeducativo programaEducativo;
    private Grupo selecGrupo;
    private Planestudio planestudio;
    private Programaeducativo programaEducativoD;
    private Grupo selecGrupoD;
    private Planestudio planestudioD;
    private Usuario usuario;
    private Profesor profesor;
    private Unidadacademica auxUA = null;
    private Integer idPlanEstudios;
    private List<Grupo> listaFiltrada;
    private List<Grupo> listaFiltrada2;
    private List<Grupo> listaGpoSeleccionada;
    private List<Planestudio> listaPlanEstudio;
    private List<Unidadacademica> listaUnidadAcademica;
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Programaeducativo> listaProgEduc;
    private boolean blnGrupo;
    private boolean blnPlan;
    private boolean blnPE;
    private String mensaje;
    private String rolSeleccionado;
    private String mostrarListaPlan = "true";
    private String listaPE;
    private String renderPlan;
    private String renderPE;

    /**
     * Constructor de clase
     */
    public GrupoBeanHelper() {
        grupo = new Grupo();
        usuario = new Usuario();
        selecGrupo = new Grupo();
        planestudio = new Planestudio();
        planestudio.setPESid(0);
        programaEducativo = new Programaeducativo();
        programaEducativo.setPEDid(0);

        planestudioD = new Planestudio();
        planestudioD.setPESid(0);
        programaEducativoD = new Programaeducativo();
        programaEducativoD.setPEDid(0);
    }

    /**
     * Metodo para busqueda en tiempo real de Grupo
     *
     * @param campo    No se usa
     * @param busqueda Numero del Grupo
     * @return Lista de Grupos encontrados
     */
    public List<Grupo> filtrado(String campo, String busqueda) {
        getUsuarioTienePE();
        filtrarPlanPorPE();
        filtrarGpoPorPlan();

        // BUSQUEDA TIEMPO REAL
        if (!busqueda.isEmpty()) {
            List<Grupo> lista;
            listaFiltrada2 = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            for (int x = 0; x < listaFiltrada.size(); x++) {

                if (String.valueOf(listaFiltrada.get(x).getGPOnumero()).startsWith(busqueda)) {
                    System.out.println("------- ENTRO AL ADD ---------");
                    listaFiltrada2.add(listaFiltrada.get(x));
                }
                if (String.valueOf(listaFiltrada.get(x).getGPOnumero()).equalsIgnoreCase(busqueda)) {
                    System.out.println("---- ENTRO AL SEGUNDO ADD ----");
                    listaFiltrada2.clear();
                    listaFiltrada2.add(listaFiltrada.get(x));
                }
            }
        } else {
            listaFiltrada2 = listaFiltrada;
        }
        return listaFiltrada2;
    }

    /**
     * Metodo para filtrar Grupos por Plan de Estudio
     */
    public void filtrarPlanPorPED() {
        try {
            listaPlanEstudio.clear();
        } catch (NullPointerException e) {
            listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
        }

        if (programaEducativoD.getPEDid() == 0) {
            renderPE = "true";
            mostrarListaPlan = "true";
            planestudioD.setPESid(0);
            List<Planestudio> listaPlan = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
            List<Grupo> listaGpo = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            try {
                listaFiltrada.clear();
            } catch (NullPointerException e) {
                listaFiltrada = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
                listaFiltrada.clear();
            }

            for (Programaeducativo pe : listaProgramaEducativo) {
                for (Planestudio plan : listaPlan) {
                    if (pe.getPEDid() == plan.getProgramaEducativoPEDid().getPEDid()) {
                        listaPlanEstudio.add(plan);
                    }
                }
            }
            for (Planestudio plan : listaPlanEstudio) {
                for (Grupo grup : listaGpo) {
                    if (plan.getPESid() == grup.getPlanEstudioPESid().getPESid()) {
                        listaFiltrada.add(grup);
                    }
                }
            }
        } else {
            renderPE = "false";
            mostrarListaPlan = "false";
            listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaEducativoD.getPEDid());
        }

    }

    public void filtrarPlanPorPE() {
        try {
            listaPlanEstudio.clear();
        } catch (NullPointerException e) {
            listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
        }

        if (programaEducativo.getPEDid() == 0) {
            renderPE = "true";
            mostrarListaPlan = "true";
            planestudio.setPESid(0);
            List<Planestudio> listaPlan = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
            List<Grupo> listaGpo = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            try {
                listaFiltrada.clear();
            } catch (NullPointerException e) {
                listaFiltrada = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
                listaFiltrada.clear();
            }

            for (Programaeducativo pe : listaProgramaEducativo) {
                for (Planestudio plan : listaPlan) {
                    if (pe.getPEDid() == plan.getProgramaEducativoPEDid().getPEDid()) {
                        listaPlanEstudio.add(plan);
                    }
                }
            }
            for (Planestudio plan : listaPlanEstudio) {
                for (Grupo grup : listaGpo) {
                    if (plan.getPESid() == grup.getPlanEstudioPESid().getPESid()) {
                        listaFiltrada.add(grup);
                    }
                }
            }
        } else {
            renderPE = "false";
            mostrarListaPlan = "false";
            listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaEducativo.getPEDid());
        }

    }

    /**
     * Metodo para filtrar Grupo por Plan de Estudio
     */
    public void filtrarGpoPorPlanD() {
        try {
            listaFiltrada.clear();
            System.out.println("PLAN DE ESTUDIO ID: " + planestudioD.getPESid());
        } catch (NullPointerException e) {
            listaFiltrada = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            listaFiltrada.clear();
        }
        if (planestudioD.getPESid() != 0) {
            renderPlan = "false";
            listaFiltrada.clear();

            // LISTA PARA OBTENER PLANES POR ID DE PROGRAMA EDUCATIVO
            listaPlanes = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaEducativoD.getPEDid());
            // OBTENER ENTIDAD DE PLAN SELECCIONADO EN LA LISTA
            Planestudio aux = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(planestudioD.getPESid());
            // VIGENCIA QUE SE BUSCA YA FILTRADA
            //System.out.println("VIGENCIA BUSCADA: " + aux.getPESvigenciaPlan());

            List<Grupo> listaGrupos = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            for (Planestudio plan : listaPlanEstudio) {
                for (Grupo grup : listaGrupos) {
                    if (plan.getPESid().equals(grup.getPlanEstudioPESid().getPESid())) {
                        if (plan.getPESvigenciaPlan().equals(aux.getPESvigenciaPlan())) {
                            listaFiltrada.add(grup);
                        }
                    }
                }
            }
        } else {
            // CASO PARA TOPOLOGIA
            if (planestudioD.getPESid() == 0) {
                renderPlan = "false";
                listaFiltrada.clear();

                List<Grupo> listaGrupos = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
                for (Planestudio plan : listaPlanEstudio) {
                    for (Grupo grup : listaGrupos) {
                        if (plan.getPESid().equals(grup.getPlanEstudioPESid().getPESid())) {
                            listaFiltrada.add(grup);
                        }
                    }
                }
            }
        }
    }

    public void filtrarGpoPorPlan() {
        try {
            listaFiltrada.clear();
            System.out.println("PLAN DE ESTUDIO ID: " + planestudio.getPESid());
        } catch (NullPointerException e) {
            listaFiltrada = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            listaFiltrada.clear();
        }
        if (planestudio.getPESid() != 0) {
            renderPlan = "false";
            listaFiltrada.clear();

            // LISTA PARA OBTENER PLANES POR ID DE PROGRAMA EDUCATIVO
            listaPlanes = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaEducativo.getPEDid());
            // OBTENER ENTIDAD DE PLAN SELECCIONADO EN LA LISTA
            Planestudio aux = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(planestudio.getPESid());
            // VIGENCIA QUE SE BUSCA YA FILTRADA
            //System.out.println("VIGENCIA BUSCADA: " + aux.getPESvigenciaPlan());

            List<Grupo> listaGrupos = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
            for (Planestudio plan : listaPlanEstudio) {
                for (Grupo grup : listaGrupos) {
                    if (plan.getPESid().equals(grup.getPlanEstudioPESid().getPESid())) {
                        if (plan.getPESvigenciaPlan().equals(aux.getPESvigenciaPlan())) {
                            listaFiltrada.add(grup);
                        }
                    }
                }
            }
        } else {
            // CASO PARA TOPOLOGIA
            if (planestudio.getPESid() == 0) {
                renderPlan = "false";
                listaFiltrada.clear();

                List<Grupo> listaGrupos = ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo();
                for (Planestudio plan : listaPlanEstudio) {
                    for (Grupo grup : listaGrupos) {
                        if (plan.getPESid().equals(grup.getPlanEstudioPESid().getPESid())) {
                            listaFiltrada.add(grup);
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo para verificar si el Usuario tiene un Programa Educativo
     */
    public void getUsuarioTienePE() {
        listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        try {
            listaProgramaEducativo.clear();
        } catch (NullPointerException e) {
        }
        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador")) {
            profesor = usuario.getProfesorList().get(0);
            listaUnidadAcademica = profesor.getUnidadacademicaList();
            listaProgEduc = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
            for (Unidadacademica uac : listaUnidadAcademica) {
                for (Programaeducativo pe : listaProgEduc) {
                    if (uac.getUACid() == pe.getUnidadAcademicaUACid().getUACid()) {
                        listaProgramaEducativo.add(pe);
                    }
                }
            }
        } else {
            if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")
                    || rolSeleccionado.equalsIgnoreCase("Coordinador de Formación Básica")) {
                profesor = usuario.getProfesorList().get(0);

                List<Programaeducativo> poProgramaeducativoList = new ArrayList<Programaeducativo>();

                if (!profesor.getResponsableProgramaEducativosList().isEmpty()) {
                    for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList())
                        if (!poProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                            poProgramaeducativoList.add(rpe.getProgramaeducativo());
//                listaProgramaEducativo = profesor.getProgramaeducativoList();
                } else {
                    for (Integer x = 0; x < profesor.getProfesorPerteneceProgramaeducativoList().size(); x++)
                        if (!poProgramaeducativoList.contains(profesor.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo()))
                            poProgramaeducativoList.add(profesor.getProfesorPerteneceProgramaeducativoList().get(x).getProgramaeducativo());
                }
                listaProgramaEducativo = poProgramaeducativoList;
                programaEducativo = listaProgramaEducativo.get(0);

            } else {

                if (rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
                    profesor = usuario.getProfesorList().get(0);
                    listaProgramaEducativo.add(profesor.getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid());
                    programaEducativo = listaProgramaEducativo.get(0);
                }
            }
        }
    }

    /**
     * Metodo para obtener los datos del registro seleccionado del datatable
     */
    public void seleccionarRegistro() {
        for (Grupo gpo : ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo()) {
            if (gpo.getGPOid().equals(selecGrupo.getGPOid())) {
                grupo = gpo;
                planestudioD = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(gpo.getPlanEstudioPESid().getPESid());
                programaEducativoD = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(getPlanestudioD().getProgramaEducativoPEDid().getPEDid());
            }
        }
    }

    /**
     * Metodo para elimnar el registro de la lista seleccionada
     *
     * @param id Id del Grupo
     */
    public void eliminarDeLista(int id) {
        try {
            for (Grupo gpo : listaGpoSeleccionada) {
                if (gpo.getGPOid().equals(id)) {
                    int index = listaGpoSeleccionada.indexOf(gpo);
                    listaGpoSeleccionada.remove(index);
                    break;
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("NULO DE ELIMINACION******");
        }
    }

    /**
     * Metodo para validar si el registro es repetido
     *
     * @return Mensaje con el resultado
     */
    public String validarRepetidos() {
        blnGrupo = true;
        blnPlan = true;
        blnPE = true;
        mensaje = "";

        System.out.println("imprimiendo PE para datos repetidos " + programaEducativo.getPEDid());
        for (Grupo gpo : ServiceFacadeLocator.getInstancGrupoFacade().consultarListaGrupo()) {

            if (gpo.getGPOnumero() == grupo.getGPOnumero() && blnGrupo == true
                    && (gpo.getPlanEstudioPESid().equals(grupo.getPlanEstudioPESid()))) {
                blnGrupo = false;
                blnPlan = false;
                blnPE = false;
                System.out.println(gpo.getGPOnumero() + "-" + grupo.getGPOnumero() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                mensaje = mensaje + "Grupo, Plan de estudio";
                break;
            }
        }
        return mensaje;
    }

    /**
     * Metodo para obtener Programa Educativo
     *
     * @param plan Objeto tipo Programa Educativo
     * @return El nombre del Programa Educativo
     */
    public String getListaPE(Planestudio plan) {
        Programaeducativo recibe = null;
        try {
            if (plan.getPESid() > 0) {
                recibe = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(plan.getProgramaEducativoPEDid().getPEDid());
                System.out.println(recibe);
                listaPE = recibe.getPEDnombre();
            } else {
            }
        } catch (NullPointerException e) {
        }
        return listaPE;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Grupo getSelecGrupo() {
        return selecGrupo;
    }

    public void setSelecGrupo(Grupo selecGrupo) {
        this.selecGrupo = selecGrupo;
    }

    public Planestudio getPlanestudio() {

        return planestudio;
    }

    public void setPlanestudio(Planestudio planestudio) {
        this.planestudio = planestudio;
    }

    public Programaeducativo getProgramaEducativoD() {
        return programaEducativoD;
    }

    public void setProgramaEducativoD(Programaeducativo programaEducativoD) {
        this.programaEducativoD = programaEducativoD;
    }

    public Planestudio getPlanestudioD() {
        return planestudioD;
    }

    public void setPlanestudioD(Planestudio planestudioD) {
        this.planestudioD = planestudioD;
    }

    public void setListaFiltrada(List<Grupo> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Grupo> getListaGpoSeleccionada() {
        return listaGpoSeleccionada;
    }

    public void setListaGpoSeleccionada(List<Grupo> listaGpoSeleccionada) {
        this.listaGpoSeleccionada = listaGpoSeleccionada;
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

    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }

    public List<Unidadacademica> getListaUnidadAcademica() {
        return listaUnidadAcademica;
    }

    public void setListaUnidadAcademica(List<Unidadacademica> listaUnidadAcademica) {
        this.listaUnidadAcademica = listaUnidadAcademica;
    }

    public Programaeducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(Programaeducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }

    public String getMostrarListaPlan() {
        return mostrarListaPlan;
    }

    public void setMostrarListaPlan(String mostrarListaPlan) {
        this.mostrarListaPlan = mostrarListaPlan;
    }

    public List<Grupo> getListaFiltrada2() {
        return listaFiltrada2;
    }

    public void setListaFiltrada2(List<Grupo> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    }

    public String getListaPE() {
        return listaPE;
    }

    public void setListaPE(String listaPE) {
        this.listaPE = listaPE;
    }

    public String getRenderPlan() {
        return renderPlan;
    }

    public void setRenderPlan(String renderPlan) {
        this.renderPlan = renderPlan;
    }

    public String getRenderPE() {
        return renderPE;
    }

    public void setRenderPE(String renderPE) {
        this.renderPE = renderPE;
    }

    /**
     * Metodo para obtener Grupos Asignados
     *
     * @return True si una Unidad de Aprendizaje es impartida por un Profesor,
     * False en cualquier otro caso
     */
    public boolean getGrupoAsignado() {
        boolean bandera = true;
        if (grupo.getUnidadaprendizajeImparteProfesorList().size() < 1 || grupo.getUnidadaprendizajeImparteProfesorList() == null) {
            bandera = false;
        }
        return bandera;
    }

    /**
     * Metodo para modificar un Grupo
     *
     * @param grupo Objeto tipo Grupo
     */
    public void modificarGrupo(Grupo grupo) {
        ServiceFacadeLocator.getInstancGrupoFacade().modificarGrupo(grupo);
    }

    /**
     * Metodo para agregar un Grupo
     *
     * @param grupo Objeto tipo Grupo
     */
    public void agregarGrupo(Grupo grupo) {
        ServiceFacadeLocator.getInstancGrupoFacade().agregarGrupo(grupo);
    }

    /**
     * Metodo para eliminar un Grupo
     *
     * @param grupo Objeto tipo Grupo
     */
    public void eliminarGrupo(Grupo grupo) {
        ServiceFacadeLocator.getInstancGrupoFacade().eliminarGrupo(grupo);
    }

    /**
     * Metodo para asignar un Plan de Estudio a un Grupo
     *
     * @param idPlan Id del Plan de Estudio
     * @return
     */
    public Planestudio AsignarPlanEstudio(Integer idPlan) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(idPlan);
    }

    /**
     * Metodo para buscar un Plan de Estudio por Id
     *
     * @param pedid Id del Plan de Estudio
     * @return
     */
    public List<Planestudio> buscarPlanEstudio(int pedid) {
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(pedid);
    }

    /**
     * Metodo para obtener un Programa Educativo por Id
     *
     * @param id Id del Programa Educativo
     * @return Programa Educativo encontrado
     */
    public Programaeducativo findProgramaEducativoById(int id) {
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id);
    }

    /**
     * @deprecated
     */
    public void prueba() {
        System.err.println("Valor cambiado");
    }
}
