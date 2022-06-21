package mx.avanti.siract.helper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.*;

/**
 *
 * @author NOE_LOPEZ
 */
public class PlanEstudioBeanHelper implements Serializable {
    private Planestudio planEstudio;
    private Planestudio planEstudio2;
    private Planestudio selecPlanEstudio;
    private Programaeducativo programaeducativo;
    private Programaeducativo programaeducativo2;
    private List<Planestudio> listaFiltrada;
    private List<Planestudio> listaFiltrada2;
    private Usuario usuario2;
    private List<Programaeducativo> listaProgramaEducativo;
    private List<Planestudio> listaSeleccionPlanEstudio;
    private List<Unidadacademica> listaUnidadAcademica;
    private Profesor profesor2;
    private Usuario usuario;
    private List<Programaeducativo> listaProgEduc;
    Unidadacademica auxUA = null;
    private boolean banPlanEstudio;
    private boolean banProgramaEducativo;
    private String Mensaje;
    private String rolSeleccionado;
    private String ocultarLista;
    
    public Programaeducativo getProgramaeducativo2() {
        return programaeducativo2;
    }
    public void setProgramaeducativo2(Programaeducativo programaeducativo2) {
        this.programaeducativo2 = programaeducativo2;
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
    public void setUsuario2(Usuario usuario) {
        this.usuario2 = usuario;
    }
    public List<Unidadacademica> getListaUnidadAcademica() {
        return listaUnidadAcademica;
    }
    public void setListaUnidadAcademica(List<Unidadacademica> listaUnidadAcademica) {
        this.listaUnidadAcademica = listaUnidadAcademica;
    }
    public Planestudio getPlanEstudio2() {
        return planEstudio2;
    }
    public void setPlanEstudio2(Planestudio planEstudio2) {
        this.planEstudio2 = planEstudio2;
    }
    public List<Planestudio> getListaSeleccionPlanEstudio() {
        return listaSeleccionPlanEstudio;
    }
    public List<Planestudio> getListaFiltrada2() {
        return listaFiltrada2;
    }
    public void setListaFiltrada2(List<Planestudio> listaFiltrada2) {
        this.listaFiltrada2 = listaFiltrada2;
    }
    public void setListaSeleccionPlanEstudio(List<Planestudio> listaSeleccionPlanEstudio) {
        this.listaSeleccionPlanEstudio = listaSeleccionPlanEstudio;
    }
    public String getMensaje() {
        return Mensaje;
    }
    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }
    public boolean isBanPlanEstudio() {
        return banPlanEstudio;
    }
    public void setBanPlanEstudio(boolean banPlanEstudio) {
        this.banPlanEstudio = banPlanEstudio;
    }
    public PlanEstudioBeanHelper() {
        try {
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        planEstudio = new Planestudio();
        selecPlanEstudio = new Planestudio();
        programaeducativo = new Programaeducativo();
        programaeducativo2 = new Programaeducativo();
        programaeducativo.setPEDid(0);
        //linea agregada por noe
        //planEstudio.setProgramaeducativo(programaeducativo);
    }
    
    public String getRolSeleccionado() {
        return rolSeleccionado;
    }
    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }
    public String getOcultarLista() {
        return ocultarLista;
    }
    public void setOcultarLista(String ocultarLista) {
        this.ocultarLista = ocultarLista;
    }
    public Planestudio getPlanEstudio() {
        return planEstudio;
    }
    public void setPlanEstudio(Planestudio planEstudio) {
        this.planEstudio = planEstudio;
    }
    public Planestudio getSelecPlanEstudio() {
        return selecPlanEstudio;
    }
    public void setSelecPlanEstudio(Planestudio selecPlanEstudio) {
        this.selecPlanEstudio = selecPlanEstudio;
    }
    public Programaeducativo getProgramaeducativo() {
        return programaeducativo;
    }
    public void setProgramaeducativo(Programaeducativo programaeducativo) {
        this.programaeducativo = programaeducativo;
    }
/*
    public ProgramaEducativoDelegate getProgramaEducativoDelegate() {
        return programaEducativoDelegate;
    }

    public void setProgramaEducativoDelegate(ProgramaEducativoDelegate programaEducativoDelegate) {
        this.programaEducativoDelegate = programaEducativoDelegate;
    }
*/
    public List<Planestudio> getListaFiltrada() {
        return listaFiltrada;
    }
    public void setListaFiltrada(List<Planestudio> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }
    public List<Programaeducativo> getListaProgramaEducativo() {
        
        //return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getListaProgramaEducativo();
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
    }
    public void setListaProgramaEducativo(List<Programaeducativo> listaProgramaEducativo) {
        this.listaProgramaEducativo = listaProgramaEducativo;
    }
    public Profesor getProfesor2() {
        return profesor2;
    }
    public void setProfesor2(Profesor profesor2) {
        this.profesor2 = profesor2;
    }
   
    /**
     * Método que filtra la lista de planes de estudio por el atributo busqueda
     * @param campo tipo String (No se usa) 
     * @param busqueda tipo String
     * @return Lista con Objetos tipo Plan
     */
    public List<Planestudio> filtrado(String campo, String busqueda) {
        getUsuarioTienePE();
        filtrarPE();
        //listaFiltrada = planEstudioDelegate.getListaPlanEstudio();
        for (Planestudio planE : listaFiltrada) {
            //planE.setProgramaeducativo(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().findProgramaEducativoById(planE.getProgramaeducativo().getPedid()));
            planE.setProgramaEducativoPEDid(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(planE.getProgramaEducativoPEDid().getPEDid()));
        }
        if (busqueda.trim().length() > 0) {
            listaFiltrada.clear();
            
            for (Planestudio planE : ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio()) {
                 //for (Planestudio planE : planEstudioDelegate.getListaPlanEstudio()) {
                //planE.setProgramaeducativo(programaEducativoDelegate.findProgramaEducativoById(planE.getProgramaeducativo().getPedid()));
                planE.setProgramaEducativoPEDid(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(planE.getProgramaEducativoPEDid().getPEDid()));
                String cambioObjPlanE = planE.getPESvigenciaPlan();
                if (cambioObjPlanE.startsWith(busqueda)) {
                    listaFiltrada.add(planE);
                }
            }
        }
        return listaFiltrada;
    }
    /**
     * Valida que no se repita el plan de estudios, si encuentra el programa educativo 
     * regresa un mensaje
     * @param cabecera tipo String 
     * @return Mensaje tipo string
     */
    public String Validar(String cabecera) {
        banProgramaEducativo = true;
        banPlanEstudio = true;
        Mensaje = "";
        System.out.println("Sout 1");
        //for (Planestudio planE : planEstudioDelegate.getListaPlanEstudio()) {
        for (Planestudio planE : ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio()) {
            //Compara el campo de numero de empleado y al mismo tiempo una bandera para que 
            //en caso de que encuentre un registro identico no lo vuelva a comparar(esto aplica en los 
            //casos de que se repita un apellido o un nombre)                 
            if (cabecera.equals("Agregar Plan de Estudio") && planE.getProgramaEducativoPEDid().getPEDid() == programaeducativo.getPEDid() && planE.getPESvigenciaPlan().equalsIgnoreCase(planEstudio.getPESvigenciaPlan())) {
                System.out.println("Sout 3");
                Mensaje = Mensaje + " [ Programa Educativo ]";
                banProgramaEducativo = false;
            }
        }
        return Mensaje;
    }
    /**
     * Busca el plan de estudio seleccionado
     */
    public void seleccionarRegistro() {
        //for (Planestudio planE : planEstudioDelegate.getListaPlanEstudio()) {
        for (Planestudio planE : ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio()) {
            if (planE.getPESid().equals(selecPlanEstudio.getPESid())) {
                planEstudio = planE;
                //programaeducativo = programaEducativoDelegate.findProgramaEducativoById(planE.getProgramaeducativo().getPedid());
                programaeducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(planE.getProgramaEducativoPEDid().getPEDid());
            }
        }
    }
    /**
     * Método para eliminar un plan de estudio de la listaSeleccionPlanEstudio
     * @param id tipo int
     */
    public void eliminarDeLista(int id) {
        for (Planestudio planE : listaSeleccionPlanEstudio) {
            if (planE.getPESid().equals(id)) {
                int index = listaSeleccionPlanEstudio.indexOf(planE);
                listaSeleccionPlanEstudio.remove(index);
                break;
            }
        }
        for (Planestudio planE : listaSeleccionPlanEstudio) {
            System.out.println(planE.getPESvigenciaPlan());
        }
    }
    /**
     * Método para filtrar los Planes de estudio por Programa educativo
     */
    public void filtrarPE() {
        List<Planestudio> listaFiltrada3;
        //listaFiltrada3=planEstudioDelegate.getListaPlanEstudio();
        listaFiltrada3 = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
        try {
            listaFiltrada.clear();
        } catch (NullPointerException e) {
            System.out.println("Null Pointer");
            //listaFiltrada = planEstudioDelegate.getListaPlanEstudio();
            listaFiltrada = ServiceFacadeLocator.getInstancePlanEstudioFacade().consultaTodosPlanestudio();
            listaFiltrada.clear();
        }
        System.out.println("SOUT NO. 1");
        System.out.println("Programa educativo es: " + getProgramaeducativo2().getPEDid());
        if (getProgramaeducativo2().getPEDid()== null) {
            programaeducativo2.setPEDid(0);
        }
        System.out.println("Programa educativo es: " + getProgramaeducativo2().getPEDid());
        if (getProgramaeducativo2().getPEDid()== 0) {
            //profesor2 = profesorDeleagate.findProfesorFromUser(usuario.getUsuid());
            //profesor2 = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorFromUser(usuario.getUsuid());
            profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
            //usuario.getProfesorList();
            System.out.println(profesor2.getPROnombre()+ profesor2.getPROid());
            //System.out.println("UA encontrado: " + unidadAcademicaDelegate.getProfUAC(profesor2.getProid()).get(0).getUacnombre());
            

                //System.out.println("UA encontrado: " + ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().consultaProfUAC(profesor2.getPROid()).get(0).getUacnombre());


            for (Programaeducativo pe : listaProgramaEducativo) {
                for (Planestudio plan : listaFiltrada3) {
                    if (pe.getPEDid()== plan.getProgramaEducativoPEDid().getPEDid()) 
                        listaFiltrada.add(plan);
                    
                }
            }//                
        } else {
            //creo que la busqueda es por programa educativo
            //listaFiltrada = planEstudioDelegate.buscarPlanEstudio(programaeducativo2.getPedid());
            //listaFiltrada = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudio(programaeducativo2.getPedid());
//            listaFiltrada = ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(programaeducativo2.getPEDid()).getProgramaEducativoPEDid();
            //listaFiltrada= ServiceFacadeLocator.getInstanceProgramaEducativoFacade().findProgramaEducativoById(programaeducativo2.getPEDid()).getPlanEstudio();
            
            /***
             * segun la arregle
             */
            listaFiltrada= ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(programaeducativo2.getPEDid()).getPlanestudioList();
        }
    }
    /**
     * Usuario por Plan de estudio
     */
    public void getUsuarioTienePE() {
        System.out.println("Rol - Plan Estudio" + rolSeleccionado);
        //listaProgramaEducativo = programaEducativoDelegate.getListaProgramaEducativo();
        listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
        try {
            listaProgramaEducativo.clear();
        } catch (NullPointerException e) {

        }
        //rolMenu esta vacio
        if (rolSeleccionado.equalsIgnoreCase("Director") || rolSeleccionado.equalsIgnoreCase("Subdirector") || rolSeleccionado.equalsIgnoreCase("Administrador")) {
            ocultarLista = "true";
            System.out.println("sout 1");
            //profesor2 = profesorDeleagate.findProfesorFromUser(usuario.getUsuid());
            //profesor2 = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorFromUser(usuario.getUsuid());
            profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
            System.out.println("sout 2");
            //a partir de aqui aparece NullPointer
            System.out.println("info del PRO2: " + profesor2.getPROid());
            //listaUnidadAcademica = unidadAcademicaDelegate.getProfUAC(profesor2.getProid()); // guardar en objeto lista de unidadAcademica
            //listaUnidadAcademica = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().consultaProfUAC(profesor2.getProid());
            
            
            
            /*
            
            verificar si lleva un get(0)
            */
            listaUnidadAcademica = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getUnidadacademicaList();
            System.out.println("sout 3");
            //listaProgEduc = programaEducativoDelegate.getListaProgramaEducativo();
            listaProgEduc = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
            System.out.println("sout 4");
            for (Unidadacademica uac : listaUnidadAcademica) {
                System.out.println("sout 5");
                for (Programaeducativo pe : listaProgEduc) {
                    System.out.println("sout 6");
                    if (uac.getUACid() == pe.getUnidadAcademicaUACid().getUACid()) {
                        System.out.println("sout 7");
                        //aqui da NullPointer
                        listaProgramaEducativo.add(pe);
                        System.out.println("sout exitoso");
                    }
                }
            }
        } else if (rolSeleccionado.equalsIgnoreCase("Responsable de Programa Educativo")) {
            //profesor2 = profesorDeleagate.findProfesorFromUser(usuario.getUsuid());
            //profesor2 = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorFromUser(usuario.getUsuid());
            profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
            //listaProgramaEducativo.add(programaEducativoDelegate.findProgramaEducativoById(programaEducativoDelegate.getResponsablePE(profesor2.getProid()).get(0).getPedid()));
            //listaProgramaEducativo.add(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().findProgramaEducativoById(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getResponsablePE(profesor2.getPROid()).get(0).getPedid()));
           //if(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getProgramaeducativoList().get(0).getPEDid())==null)

            List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
            for (ResponsableProgramaEducativo rpe : ServiceFacadeLocator.getInstanceResponsableProgramaEducativoFacade().findByProfesor(profesor2))
                if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                    peProgramaeducativoList.add(rpe.getProgramaeducativo());
            listaProgramaEducativo.addAll(peProgramaeducativoList);

//           listaProgramaEducativo.add(ServiceFacadeLocator.getInstanceProgramaEducativoFacade().
//                   buscarProgramaEducativoPorID(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid())
//                           .getProgramaeducativoList().get(0).getPEDid()));
        } else {
            if (rolSeleccionado.equalsIgnoreCase("Coordinador de Área de Conocimiento")) {
                //profesor2 = profesorDeleagate.findProfesorFromUser(usuario.getUsuid());
                //profesor2 = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorFromUser(usuario.getUsuid());
                profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
                //listaProgramaEducativo = programaEducativoDelegate.getPEdeCoordinadorAreaAdmin(profesor2.getProid());
               /**
                * verificar si funciona
                */
                //listaProgramaEducativo = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getPEdeCoordinadorAreaAdmin(profesor2.getProid());
                listaProgramaEducativo.clear();
                listaProgramaEducativo.add(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor2.getPROid()).getCoordinadorareaadministrativaList().get(0).getAreaAdministrativaAADid().getProgramaEducativoPEDid());
            }
            
        
            
        }
    }
    /**
     * Eliminar plan de estudio
     * @param plan 
     */
    public void eliminarPlanEstudio(Planestudio plan){
        planEstudio.setProgramaEducativoPEDid(programaeducativo);
        plan.setProgramaEducativoPEDid(programaeducativo);
        ServiceFacadeLocator.getInstancePlanEstudioFacade().eliminarPlanEstudio(plan);
    }
    /**
     *Agregar plan de estudio 
     * @param plan 
     */
    public void agregarPlanEstudio(Planestudio plan){
        planEstudio.setProgramaEducativoPEDid(programaeducativo);
        //plan.setProgramaeducativo(programaeducativo);
        //planEstudio.setProgramaeducativo(programaeducativo);
        //ServiceFacadeLocator.getInstancePlanEstudioFacade().agregarPlanEstudio(plan);
        ServiceFacadeLocator.getInstancePlanEstudioFacade().agregarPlanEstudio(planEstudio);
    }
    /**
     * Planes asignados a grupo
     * @param id
     * @return 
     */
    // se cambia a Grupo en lugar de plan de estudio
//    public  List<Planestudio> planAsignadoGrupo(int id){
    public  List<Grupo> planAsignadoGrupo(int id){
        planEstudio.setProgramaEducativoPEDid(programaeducativo);
        //plan.setProgramaeducativo(programaeducativo);
                //lista que si funcionaba
                //ServiceFacadeLocator.getInstancePlanEstudioFacade().getPlanAsignadoGrupo(id);
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(id).getGrupoList();
    }
    /**
     * Planes por area de conocimiento
     * @param id
     * @return 
     */
    //voy a cambiar la lista de plan de estudio a area de conocimiento
    //public List<Planestudio> planAsignadoAreaConocimiento(int id){
    public List<Areaconocimiento> planAsignadoAreaConocimiento(int id){
        planEstudio.setProgramaEducativoPEDid(programaeducativo);
        //return ServiceFacadeLocator.getInstancePlanEstudioFacade().getPlanAsignadoAreaConocimiento(id);       
        // la siguiente linea se cambio
        //return ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoById(id).get        
        //profesor2 = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(usuario.getUSUid()).getProfesorList().get(0);
        //listo segu yo
        return ServiceFacadeLocator.getInstancePlanEstudioFacade().buscarPlanEstudioID(id).getAreaconocimientoList();
                
    }
    /**
     * Actualizar plan de estudio
     * @param plan 
     */
    public void updatePE(Planestudio plan){
        plan.setProgramaEducativoPEDid(programaeducativo);
        ServiceFacadeLocator.getInstancePlanEstudioFacade().actualizarPE(plan);
    }
}