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
import mx.avanti.siract.integration.ServiceLocator;

/**
 * @author balta
 */
public class CapturaAreaConocimientoBeanHelper implements Serializable {

    //Declaracion de las variables privadas
    private Areaconocimiento areaConocimiento;
    private Areaconocimiento selecAreaconocimiento;
    private Planestudio planestudio;
    private List<Planestudio> listaPlanEstudio;
    private Programaeducativo programaeducativo;
    private List<Programaeducativo> listaFiltradaPed;
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Areaconocimiento> listaFiltrada;
    private List<Areaconocimiento> listaFiltrada2;
    private List<Areaconocimiento> listaFiltrada3;
    private List<Areaconocimiento> listaFiltrada4;
    private List<Areaconocimiento> listaSeleccionAcon;
    private boolean blnArea;
    private boolean bandera;
    private Usuario usuario;
    private Profesor profesor;
    private List<Unidadacademica> listaUnidadAcademica;
    private String rolSeleccionado;
    private String mensaje = "";
    private List<String> listaFiltrada2S;

    /**
     * Constructor de la clase e inicializacion de las variables
     */
    public CapturaAreaConocimientoBeanHelper() {
        areaConocimiento = new Areaconocimiento();
        selecAreaconocimiento = new Areaconocimiento();
        planestudio = new Planestudio();
        programaeducativo = new Programaeducativo();
    }

    //Getters y Setters de las variables
    public List<Programaeducativo> getListaProgramaEducativo() {
        return listaProgramaEducativo;
    }

    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Unidadacademica> getListaUnidadAcademica() {
        return listaUnidadAcademica;
    }

    public void setListaUnidadAcademica(List<Unidadacademica> listaUnidadAcademica) {
        this.listaUnidadAcademica = listaUnidadAcademica;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public List<Planestudio> getListaPlanEstudio() {
        return listaPlanEstudio;
    }

    public void setListaPlanEstudio(List<Planestudio> listaPlanEstudio) {
        this.listaPlanEstudio = listaPlanEstudio;
    }

    public List<String> getListaFiltrada2S() {
        return listaFiltrada2S;
    }

    public void setListaFiltrada2S(List<String> listaFiltrada2S) {
        this.listaFiltrada2S = listaFiltrada2S;
    }

    public Programaeducativo getProgramaeducativo() {
        return programaeducativo;
    }

    public void setProgramaeducativo(Programaeducativo programaeducativo) {
        this.programaeducativo = programaeducativo;
    }

    public List<Programaeducativo> getListaFiltradaPed() {
        return listaFiltradaPed;
    }

    public void setListaFiltradaPed(List<Programaeducativo> listaFiltradaPed) {
        this.listaFiltradaPed = listaFiltradaPed;
    }

    public List<Areaconocimiento> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada2(List<Areaconocimiento> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    }

    public void setListaFiltrada(List<Areaconocimiento> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public Planestudio getPlanestudio() {
        return planestudio;
    }

    public void setPlanestudio(Planestudio planestudio) {
        this.planestudio = planestudio;
    }

    public Areaconocimiento getAreaConocimiento() {
        areaConocimiento.getACOid();
        return areaConocimiento;
    }

    public void setAreaConocimiento(Areaconocimiento areaconocimiento) {
        this.areaConocimiento = areaconocimiento;
    }

    public Areaconocimiento getSelecAreaconocimiento() {
        return selecAreaconocimiento;
    }

    public List<Areaconocimiento> getListaFiltrada3() {
        return listaFiltrada3;
    }

    public void setListaFiltrada3(List<Areaconocimiento> listaFiltrada3) {
        this.listaFiltrada3 = listaFiltrada3;
    }

    public List<Areaconocimiento> getListaFiltrada4() {
        return listaFiltrada4;
    }

    public void setListaFiltrada4(List<Areaconocimiento> listaFiltrada4) {
        this.listaFiltrada4 = listaFiltrada4;
    }

    public List<Areaconocimiento> getListaSeleccionAcon() {
        return listaSeleccionAcon;
    }

    public void setListaSeleccionAcon(List<Areaconocimiento> listaSeleccionAcon) {
        this.listaSeleccionAcon = listaSeleccionAcon;
    }

    public void setSelecAreaconocimiento(Areaconocimiento selecAreaconocimiento) {
        this.selecAreaconocimiento = selecAreaconocimiento;
    }

    public Planestudio getSelecPlanEstudio() {
        return planestudio;
    }

    public void setSelecPlanEstudio(Planestudio planestudio) {
        this.planestudio = planestudio;
    }

    /**
     * Metodo para filtrar
     *
     * @param campo    El campo que se filtrara
     * @param busqueda La busqueda que se realizara
     * @return Regresa una lista de areas de conocimiento
     */
    public List<Areaconocimiento> filtrado(String campo, String busqueda) {
        String Cambus = busqueda.toLowerCase();
        String Nomarecon = "";
        String progedu = "";
        String plan = "";

        listaFiltrada = ServiceFacadeLocator.getInstanceAreaConocimientoFacade()
                .getListaAreaConocimiento();
//        for (Areaconocimiento area : listaFiltrada) {
//            try {
//                area.setPlanEstudioPESid(ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(area.getPlanEstudioPESid().getPESid()));
//                area.getPlanEstudioPESid().setProgramaEducativoPEDid(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(area.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid()));
//            } catch (Exception e) {
//            }
//        }

        if (busqueda.trim().length() > 0) {
            try {
                listaFiltrada2.clear();
            } catch (NullPointerException e) {
                listaFiltrada2 = ServiceFacadeLocator
                        .getInstanceAreaConocimientoFacade()
                        .getListaAreaConocimiento();
                listaFiltrada2.clear();
            }

            for (Areaconocimiento area : listaFiltrada) {
                try {
//                    area.setPlanEstudioPESid(ServiceFacadeLocator
//                            .getInstancePlanEstudioFacade()
//                            .buscarPlanEstudioID(area.getPlanEstudioPESid().getPESid()));
//                    area.getPlanEstudioPESid()
//                            .setProgramaEducativoPEDid(ServiceFacadeLocator
//                                    .getInstanceProgramaEducativoFacade()
//                                    .buscarProgramaEducativoPorID(area
//                                            .getPlanEstudioPESid()
//                                            .getProgramaEducativoPEDid()
//                                            .getPEDid()));
                    Nomarecon = area.getACOnombre().toLowerCase();
                    progedu = area.getPlanEstudioPESid().getProgramaEducativoPEDid()
                            .getPEDnombre();
                    plan = area.getPlanEstudioPESid().getPESvigenciaPlan();
                } catch (Exception e) {

                }
                if (campo.equalsIgnoreCase("nombre")) {
                    if (Nomarecon.startsWith(Cambus)) {
                        listaFiltrada2.add(area);
                    }
                } else {
                    if (campo.equalsIgnoreCase("Progedu")) {
                        if (progedu.equalsIgnoreCase(Cambus)) {
                            listaFiltrada2.add(area);
                        }
                    } else {
                        if (campo.equalsIgnoreCase("ProgeduNom")) {
                            String[] prognom;
                            prognom = Cambus.split("--");
                            if (Nomarecon.startsWith(prognom[0])
                                    && progedu.equalsIgnoreCase(prognom[1])) {
                                listaFiltrada2.add(area);
                            }
                        } else {
                            if (campo.equalsIgnoreCase("ProgPlan")) {
                                String[] progPlan;
                                progPlan = Cambus.split("--");
                                if (progedu.equalsIgnoreCase(progPlan[0])
                                        && plan.equalsIgnoreCase(progPlan[1])) {
                                    listaFiltrada2.add(area);
                                }
                            } else {
                                String[] todo;
                                todo = Cambus.split("--");
                                if (Nomarecon.startsWith(todo[0])
                                        && progedu.equalsIgnoreCase(todo[1])
                                        && plan.equalsIgnoreCase(todo[2])) {
                                    listaFiltrada2.add(area);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            listaFiltrada2 = listaFiltrada;
        }
        return listaFiltrada2;
    }

    /**
     * Metodo para obtener el programa educativo del usuario
     */
    public void getUsuarioTienePE() {
        listaProgramaEducativo = ServiceFacadeLocator
                .getInstanceProgramaEducativoFacade()
                .buscarProgramaEducativos();
        try {
            listaProgramaEducativo.clear();
        } catch (NullPointerException e) {
        }
        if (rolSeleccionado.equalsIgnoreCase("Director")
                || rolSeleccionado.equalsIgnoreCase("Subdirector")
                || rolSeleccionado.equalsIgnoreCase("Administrador")) {

            profesor = usuario.getProfesorList().get(0);
            listaUnidadAcademica = profesor.getUnidadacademicaList();
            listaFiltradaPed = ServiceFacadeLocator
                    .getInstanceProgramaEducativoFacade()
                    .buscarProgramaEducativos();
            for (Unidadacademica uac : listaUnidadAcademica) {
                for (Programaeducativo pe : listaFiltradaPed) {
                    if (uac.getUACid() == pe.getUnidadAcademicaUACid().getUACid()) {
                        listaProgramaEducativo.add(pe);
                    }
                }
            }
        } else {
            if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")
                    || rolSeleccionado.equalsIgnoreCase("Coordinador de Formación Básica")) {
                profesor = usuario.getProfesorList().get(0);
                listaProgramaEducativo.clear();

                // Si el profesor contiene un objeto en la lista de RPE sacará el programa educativo de ahí.
                // En caso de que la lista este vacía, sacara el programa educativo de otra lista.
                if (!profesor.getResponsableProgramaEducativosList().isEmpty()) {
                    for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList()) {
                        if (!listaProgramaEducativo.contains(rpe.getProgramaeducativo()))
                            listaProgramaEducativo.add(rpe.getProgramaeducativo());
                    }
                    programaeducativo = listaProgramaEducativo.get(0);
                } else {
                    programaeducativo = profesor.getProfesorPerteneceProgramaeducativoList().get(0).getProgramaeducativo();
                }
//                listaProgramaEducativo.add(profesor.getResponsableProgramaEducativosList().get(0).getProgramaeducativo());
//                programaeducativo = profesor.getProgramaeducativoList().get(0);
                filtrarPlanPorPE();
            } else {
                if (rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
                    profesor = usuario.getProfesorList().get(0);
                    listaProgramaEducativo.add(profesor.getCoordinadorareaadministrativaList().
                            get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid());
                    programaeducativo = listaProgramaEducativo.get(0);
                    filtrarPlanPorPE();
                }
            }
        }
    }

    /**
     * Metodo para seleccionar el registro
     */
    public void seleccionarRegistro() {
        for (Areaconocimiento area : listaSeleccionAcon) {
            if (area.getACOid().equals(selecAreaconocimiento.getACOid())) {
                try {
                    areaConocimiento = area;
                    programaeducativo = area.getPlanEstudioPESid().getProgramaEducativoPEDid();
                    filtrarPlanPorPE();
                    planestudio = area.getPlanEstudioPESid();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * Metodo para validar registros repetidos
     *
     * @param todos Variable string de los registros
     * @return Regresa una leyenda en string si ya existe o vacio si no existe
     */
    public String validarRepetidos(String todos) {
        boolean band = true;
        mensaje = "";
        String[] todo = null;
        todo = todos.split("--");
        todo[0] = todo[0].toLowerCase();
        for (Areaconocimiento area : ServiceFacadeLocator
                .getInstanceAreaConocimientoFacade()
                .getListaAreaConocimiento()) {

            try {
                area.setPlanEstudioPESid(ServiceFacadeLocator
                        .getInstancePlanEstudioFacade()
                        .buscarPlanEstudioID(area.getPlanEstudioPESid().getPESid()));
                area.getPlanEstudioPESid().setProgramaEducativoPEDid(ServiceFacadeLocator
                        .getInstanceProgramaEducativoFacade()
                        .buscarProgramaEducativoPorID(area.getPlanEstudioPESid()
                                .getProgramaEducativoPEDid().getPEDid()));
            } catch (Exception e) {
            }
            if (!area.getACOid().equals(areaConocimiento.getACOid())) {
                if (todo[0].equalsIgnoreCase(area.getACOnombre())) {
                    if (todo[1].equalsIgnoreCase(area.getPlanEstudioPESid()
                            .getProgramaEducativoPEDid().getPEDnombre())) {
                        if (todo[2].equalsIgnoreCase(area.getPlanEstudioPESid()
                                .getPESvigenciaPlan())) {
                            mensaje = " el nombre del área de conocimiento, "
                                    + "ya existe dentro del plan de estudio";
                        }
                        band = false;
                    }
                }
            }
        }
        return mensaje;
    }

    /**
     * Metodo para filtrar por programa educativo
     */
    public void filtrarPlanPorPE() {
        listaPlanEstudio = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanPorProgramaeducativo(programaeducativo.getPEDid());
    }

    /**
     * Metodo para buscar un nombre de programa educativo
     *
     * @param bus ID del programa educativo a buscar
     * @return
     */
    public String buscarNomProedu(int bus) {
        String nomprog = "";
        try {
            nomprog = ServiceFacadeLocator.getInstanceProgramaEducativoFacade()
                    .buscarProgramaEducativoPorID(bus).getPEDnombre();
        } catch (Exception e) {
        }
        return nomprog;
    }

    /**
     * Metodo para buscar la vigencia del plan de estudios
     *
     * @param bus ID del plan del estudios a buscar
     * @return
     */
    public String buscarVigPlan(int bus) {
        String vigplan = "";
        try {
            vigplan = ServiceFacadeLocator.getInstancePlanEstudioFacade()
                    .buscarPlanEstudioID(bus).getPESvigenciaPlan();
        } catch (Exception e) {
        }
        return vigplan;
    }

    /**
     * Metodo para eliminar de la lista
     *
     * @param id ID por el cual se eliminara
     */
    public void eliminarDeLista(int id) {
        for (Areaconocimiento area : listaSeleccionAcon) {
            if (area.getACOid().equals(id)) {
                int index = listaSeleccionAcon.indexOf(area);
                listaSeleccionAcon.remove(index);
                break;
            }
        }
    }

    /**
     * Metodo para agregar un area de conocimiento
     *
     * @param areaConocimiento Objeto de tipo Areaconocimiento a agregar
     */
    public void agregarAreaConocimiento(Areaconocimiento areaConocimiento) {
        areaConocimiento.setPlanEstudioPESid(planestudio);
        areaConocimiento.setACOid(areaConocimiento.getACOid());
        ServiceFacadeLocator.getInstanceAreaConocimientoFacade()
                .agregarAreaConocimiento(areaConocimiento);
    }

    /**
     * Metodo para encontrar un area de conocimiento por criterio
     *
     * @param de De a buscar
     * @param Campo El campo a buscar
     * @param criterio El criterio a buscar
     * @return
     */
//    public List<Areaconocimiento> findFromWhere(Areaconocimiento areaConocimiento,String de, String Campo, String criterio) {
//        //return ServiceFacadeLocator.getInstanceAreaConocimientoFacade()
//          //      .consultarAreasConocimiento(de, Campo, criterio);
//          List<Areaconocimiento> areas= new ArrayList();
//          if(areaConocimiento.getUnidadaprendizajeList().size()>1){
//              areas.add(areaConocimiento);
//          }else{
//              
//          }
//    }

    /**
     * Metodo para buscar un area de conocimiento por ID
     *
     * @param idAreaConocimiento
     * @return area de conocimeitno correspondiente al ID
     */
    public Areaconocimiento buscarAreaConocimientoPorID(int idAreaConocimiento) {
        return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(idAreaConocimiento);
    }

    /**
     * Metodo para eliminar un area de conocimiento
     *
     * @param areaConocimiento Objeto de tipo Areaconocimiento a eliminar
     */
    public void eliminarAreaConocimiento(Areaconocimiento areaConocimiento) {
        areaConocimiento.setPlanEstudioPESid(planestudio);
        ServiceFacadeLocator.getInstanceAreaConocimientoFacade()
                .eliminarAreaConocimiento(areaConocimiento);
    }

    /**
     * Metodo para agregar un area de conocimiento
     *
     * @param areaConocimiento Objeto de tipo Areaconocimiento a agregar
     */
    public void modificarAreaConocimiento(Areaconocimiento areaConocimiento) {
        areaConocimiento.setPlanEstudioPESid(planestudio);
        ServiceFacadeLocator.getInstanceAreaConocimientoFacade()
                .modificarAreaConocimiento(areaConocimiento);
    }
}
