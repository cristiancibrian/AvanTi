/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.entity.ResponsableProgramaEducativo;
import mx.avanti.siract.helper.PoliticaEvaluacionBeanHelper;
import org.primefaces.PrimeFaces;

/**
 *
 * @author eduardocardona
 */

@ManagedBean
@ViewScoped
public class ConsultarPoliticasBeanUI {
    
    @ManagedProperty(value = "#{loginBean}")

    private LoginBean loginBean;
    private PoliticaEvaluacionBeanHelper politicaevaluacionBeanHelper;
    private int IDCATALOGOCONSULTAPOLITICA = 22;
    
    // variables de interfaz
    private String cicloEscolarSeleccionado = "00";
    private List<Cicloescolar> listaCiclosEscolares;
    List<Unidadaprendizaje> unidadesAprendizaje;
    List<Programaeducativo> programasEducativos;
    String programaEducativoSeleccionado = "0";
    List<Profesor> profesores;
    Integer profesorSeleccionado;
    List<UnidadaprendizajeImparteProfesor> unidadesProfesor;
    List<Programaeducativo> programasEducativosConsultas;    
    List<Politicaevaluacion> politicasaceptadasRPE; 
    String unidadAprendizajeSeleccionada = "00";
    Boolean disable = true;
    private Boolean disable3 = false;
    String nombreCompletoProfesor = " ";
    int IdUaipSeleccionada = 0;
    Politicaevaluacion politicaSeleccionada;
    // internas
    private Profesor profesor;
    private Alumno alumno;
    String fechaActual = "DD/MM/AAAA";
    String fechaCreacion = "";
    String fechaAlumno = "";
    String fechaResponsable = "";
    String displayComentario = "none";
    String displayFirmas = "none";
    String displaybotonPDF = "none";
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
     // Constructor
    public ConsultarPoliticasBeanUI() {
        init();
     
        
      
        
    }
    
    public void init() {
        
        politicaevaluacionBeanHelper = new PoliticaEvaluacionBeanHelper();

    }
    
    @PostConstruct
    public void postConstructor() {
        
        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            if(loginBean.getLogueado().getRolList().get(0).getROLid() == 8){
              alumno = loginBean.getLogueado().getAlumnoList().get(0);
              if (alumno == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin alumno", "Este usuario no tiene ligado ningun alumno"));
            } else {
                nombreCompletoProfesor = alumno.getALNombre()+" "+alumno.getALApellidoPater()+" "+alumno.getALApellidoMaterno();
                politicasaceptadasRPE = politicaevaluacionBeanHelper.politicasaceptadasAlumno(alumno.getALId());
            }
            }else
            {
            profesor = loginBean.getLogueado().getProfesorList().get(0);
            if (profesor == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            } else {
                nombreCompletoProfesor = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
               // nombreProfesor = profesor.getPROnombre();
                //ractConsultasBeanHelper.setProfesor(profesor);
            }
            }
        }
        
    }
    
    
    public void onSelectCE() {

        //limpiar programas educativos y unidades de aprendizaje
        //refresh();
        //limpia el checkbox de unidad de aprendizaje
        if (unidadesAprendizaje != null && unidadesAprendizaje.size() > 0) {
            unidadesAprendizaje = Collections.EMPTY_LIST;
        }

        if (!cicloEscolarSeleccionado.equals("00")) {
            // para llenar los programas eduvativos del profesor.
            profesor = loginBean.getLogueado().getProfesorList().get(0);

            programasEducativos = politicaevaluacionBeanHelper.programasEducativosProfesor2(profesor, cicloEscolarSeleccionado);

            //pone el check box de Programa Educativo en "Seleccione una opcion"
            if (esProfesor() || loginBean.getSeleccionado().equals("Administrador")) {
                programaEducativoSeleccionado = "00";
            } else {
                programaEducativoSeleccionado = String.valueOf(programasEducativos.get(0).getPEDid());
            }
            if (!esProfesor()) {
                if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")) {

                    profesores = politicaevaluacionBeanHelper.getProfesoresPorCoordinadoryCE(profesor.getPROid(), cicloEscolarSeleccionado);
                    profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                    unidadesProfesor = politicaevaluacionBeanHelper.uaipPorcoordinadorAreaYCicloEscolar(profesor.getPROid(), Integer.parseInt(cicloEscolarSeleccionado));
                } else if (loginBean.getSeleccionado().equals("Responsable de Programa Educativo")) {
//                    programasEducativos= profesor.getProgramaeducativoList();

                    List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
                    for (ResponsableProgramaEducativo rpe : profesor.getResponsableProgramaEducativosList())
                        if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                            peProgramaeducativoList.add(rpe.getProgramaeducativo());
                    programasEducativos = peProgramaeducativoList;

                    programaEducativoSeleccionado = programasEducativos.get(0).getPEDid().toString();
                    profesorSeleccionado = (profesorSeleccionado == null) ? 0 : profesorSeleccionado;
                    profesores = politicaevaluacionBeanHelper.getProfesoresPorRPEYCE(profesor.getPROid(), cicloEscolarSeleccionado);
//                    unidadesProfesor = ractConsultasBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), loginBean.getLogueado().getProfesorList().get(0).getProgramaeducativoList().get(0).getPEDid());

                    peProgramaeducativoList.clear();
                    for (ResponsableProgramaEducativo rpe : loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList())
                        if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                            peProgramaeducativoList.add(rpe.getProgramaeducativo());
                    unidadesProfesor.clear();
                    for (Programaeducativo peProgramaeducativo : peProgramaeducativoList)
                        unidadesProfesor.addAll(politicaevaluacionBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), peProgramaeducativo.getPEDid()));

                } else if (loginBean.getSeleccionado().equals("Administrador")) {
                    programasEducativos = politicaevaluacionBeanHelper.traerTodosProgramasEducativos();
                }

            }
            programasEducativosConsultas = programasEducativos;
            


            profesorSeleccionado = 0;
            unidadAprendizajeSeleccionada = "00";
         

        } else {
            programaEducativoSeleccionado = "00";
            unidadAprendizajeSeleccionada = "00";
            if (!esProfesor()) {
                profesorSeleccionado = 0;
                profesores = Collections.EMPTY_LIST;
            }
        }

    }
    
    public void onSelectPE() {
        //refresh();
        // si es profesor 
        if (loginBean.getSeleccionado().equalsIgnoreCase("Profesor")) {
            System.out.println("RACTConsultasBeanUI:loginBeanEqualsProfesor:Entro" + programaEducativoSeleccionado);
            unidadesAprendizaje = politicaevaluacionBeanHelper.UnidadesAprendizajePorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado), cicloEscolarSeleccionado);
            unidadesProfesor = politicaevaluacionBeanHelper.UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado), cicloEscolarSeleccionado);
        } else if (loginBean.getSeleccionado().equals("Administrador")) {
            if (!cicloEscolarSeleccionado.equals("00")) {
                profesorSeleccionado = 0;
                profesores = politicaevaluacionBeanHelper.getProfesoresByPEYCE(programaEducativoSeleccionado, cicloEscolarSeleccionado);
                if (!programaEducativoSeleccionado.equals("00")) {
                    unidadesProfesor = politicaevaluacionBeanHelper.uaipPorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado));
                }
            }

        } else {
            if (cicloEscolarSeleccionado.equalsIgnoreCase("00")) {
                System.out.println("RACTConsultasBeanUI:else:cicloEscolarSeleccionadoEqual00:Entro");
                unidadesAprendizaje = politicaevaluacionBeanHelper.getUnidadesaprendisajePorProgramaEducativo(programaEducativoSeleccionado, loginBean.getSeleccionado(), profesor.getPROid().toString(), getCicloEscolarSeleccionado());
            } else {
                System.out.println("SALINAS ENTRO AL ELSE");
                unidadesAprendizaje = politicaevaluacionBeanHelper.getUNIdadesaprendisajeConGrupos(programaEducativoSeleccionado,
                        cicloEscolarSeleccionado, profesorSeleccionado);

            }
        }
        if (unidadesAprendizaje != null) {
            for (int x = 0; x < unidadesAprendizaje.size(); x++) {
                System.out.println("Unidades de aprendizaje: " + unidadesAprendizaje.get(x).getUAPnombre());
                System.out.println("Unidad id:" + unidadesAprendizaje.get(x).getUAPid());
            }
        }
        //setEstadoUnidadDeAprendizaje(false);

        
        disable = false;
        //fillTree();
        //enviado = true;
        setEnable(false);
    }
    
     public void onSelectPROFESOR() {
        //reinit stuff
       // refresh();
        if (loginBean.getSeleccionado().equals("Coordinador de Área de Conocimiento")) {
            unidadesProfesor = politicaevaluacionBeanHelper.uaipAreaProfesor(loginBean.getLogueado().getProfesorList().get(0).getPROid(), Integer.parseInt(cicloEscolarSeleccionado), profesorSeleccionado);
        } else if (loginBean.getSeleccionado().equals("Administrador")) {
            unidadesAprendizaje = Collections.EMPTY_LIST;
            unidadesProfesor = politicaevaluacionBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), Integer.parseInt(programaEducativoSeleccionado), profesorSeleccionado);

        } else {
//            unidadesProfesor = ractConsultasBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), profesor.getProgramaeducativoList().get(0).getPEDid(), profesorSeleccionado);
            List<Programaeducativo> peProgramaeducativoList = new ArrayList<Programaeducativo>();
            for (ResponsableProgramaEducativo rpe : loginBean.getLogueado().getProfesorList().get(0).getResponsableProgramaEducativosList())
                if (!peProgramaeducativoList.contains(rpe.getProgramaeducativo()))
                    peProgramaeducativoList.add(rpe.getProgramaeducativo());
            unidadesProfesor.clear();
            for (Programaeducativo peProgramaeducativo : peProgramaeducativoList)
                unidadesProfesor.addAll(politicaevaluacionBeanHelper.uaipPorProfesorPEyCE(Integer.parseInt(cicloEscolarSeleccionado), peProgramaeducativo.getPEDid(), profesorSeleccionado));
        }
    }
     
     
    public void onSelectUA() {
       // disableBotones = false;
        String mensaje;
        if(IdUaipSeleccionada == 0){
            politicaSeleccionada = new Politicaevaluacion();
           // disableBotones = true;
        }else{
            try{
                politicaSeleccionada = politicaevaluacionBeanHelper.getPoliticaPorUIP(IdUaipSeleccionada).get(0);
                displayFirmas= "";
                displaybotonPDF = "block";
                if(politicaSeleccionada.getPoliticaevaluacioncomentarioList().size()>0){
                    displayComentario="block";

                }else{
                    displayComentario="none";
                }
            }catch(Exception e){
                politicaSeleccionada = new Politicaevaluacion();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta política no ha sido creada."));
                fechaCreacion="DD/MM/AAAA";
                fechaAlumno="DD/MM/AAAA";
                fechaResponsable="DD/MM/AAAA";
                actualizaPolitica();
                displayComentario = "none";
                displayFirmas= "none";
                displaybotonPDF = "none";
                return;
            }
            try{
                fechaCreacion = formatter.format(politicaSeleccionada.getFechaCreacion());
            }catch(Exception ex){
                fechaCreacion = "DD/MM/AAAA";
            }
            try{ 
                fechaAlumno = formatter.format(politicaSeleccionada.getFechaAceptacionAlumno());
            }catch(Exception ex){
                fechaAlumno = "DD/MM/AAAA";
            }
            try{
                fechaResponsable = formatter.format(politicaSeleccionada.getFechaAceptacionResponsable());
            }catch(Exception ex){
                fechaResponsable= "DD/MM/AAAA";
            }
            
            switch(politicaSeleccionada.getEstadoPolEva()){
                case 0:
                    mensaje = "política guardada sin enviar";
                break;
                case 1:
                    mensaje = "política enviada a alumno para aprobación";
                break;
                case 2:
                    mensaje = "política aceptada por alumno";
                break;
                case 3:
                    mensaje = "política rechazada por alumno";
                break;
                case 4:
                    mensaje = "política aceptada por responsable";
                break;
                case 5:
                    mensaje = "política completamente aceptada";
                break;
                default:
                    mensaje = "";
                break;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje));
            
            actualizaPolitica();
        }
    
        
    }
    
    public void actualizaPolitica(){
        //comentarioPolitica = new Politicaevaluacioncomentario();
        PrimeFaces.current().ajax().update("formId:representante");
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentarPO");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:gs");
        PrimeFaces.current().ajax().update("formId:firmas");
        PrimeFaces.current().ajax().update("formId:tablaComentarios");
        PrimeFaces.current().ajax().update("formId:imprimir");
        PrimeFaces.current().ajax().update("formId:nomprof");
        

    }
    
    public boolean esProfesor() {
        String rol = loginBean.getSeleccionado();
        return rol.equalsIgnoreCase("Profesor");
    }
    
    
    
    // ------- GETTER and SETTERS ----------//

    public String getCicloEscolarSeleccionado() {
        return cicloEscolarSeleccionado;
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }
    

    public void setCicloEscolarSeleccionado(String cicloEscolarSeleccionado) {
        this.cicloEscolarSeleccionado = cicloEscolarSeleccionado;
    }
    public void setEnable(boolean enable) {
        if (programaEducativoSeleccionado.equalsIgnoreCase("00")) {
            enable = true;
            setEnable2(true);
        } else {
            enable = false;
            setEnable2(true);
        }
        setDisable(enable);
    }
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    public void setEnable2(boolean enable2) {
        if (unidadAprendizajeSeleccionada.equalsIgnoreCase("00")) {
            enable2 = true;
        } else {
            enable2 = false;
        }
        disable3 = false;
//        disable2=enviado;
    }
    public List<Cicloescolar> getListaCiclosEscolares() {
        listaCiclosEscolares = null;
        listaCiclosEscolares = politicaevaluacionBeanHelper.getAllCiclos();


        //orden de mayor a menor 
        Collections.sort(listaCiclosEscolares, new Comparator<Cicloescolar>() {
            @Override
            public int compare(Cicloescolar o1, Cicloescolar o2) {
                if (o1.getCESid() > o2.getCESid()) {
                    return -1;
                } else if (o1.getCESid() < o2.getCESid()) {
                    return 1;
                }

                return 0;

            }
        });
        return listaCiclosEscolares;
    }

    public Integer getProfesorSeleccionado() {
        return profesorSeleccionado;
    }

    public void setProfesorSeleccionado(Integer profesorSeleccionado) {
        this.profesorSeleccionado = profesorSeleccionado;
    }
     public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    

    public String getUnidadAprendizajeSeleccionada() {
        return unidadAprendizajeSeleccionada;
    }

    public void setUnidadAprendizajeSeleccionada(String unidadAprendizajeSeleccionada) {
        this.unidadAprendizajeSeleccionada = unidadAprendizajeSeleccionada;
    }

    public String getProgramaEducativoSeleccionado() {
        return programaEducativoSeleccionado;
    }

    public void setProgramaEducativoSeleccionado(String programaEducativoSeleccionado) {
        this.programaEducativoSeleccionado = programaEducativoSeleccionado;
    }

     public List<Programaeducativo> getProgramasEducativos() {

        programasEducativos = politicaevaluacionBeanHelper.getProgramaesEducativos(profesor);

        return programasEducativos;
    }

    public List<Programaeducativo> getProgramasEducativosConsultas() {
        return programasEducativosConsultas;
    }
     

    public void setProgramasEducativosConsultas(List<Programaeducativo> programasEducativosConsultas) {
        this.programasEducativosConsultas = programasEducativosConsultas;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDisable3() {
        return disable3;
    }

    public void setDisable3(Boolean disable3) {
        this.disable3 = disable3;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getNombreCompletoProfesor() {
        return nombreCompletoProfesor;
    }

    public void setNombreCompletoProfesor(String nombreCompletoProfesor) {
        this.nombreCompletoProfesor = nombreCompletoProfesor;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public int getIdUaipSeleccionada() {
        return IdUaipSeleccionada;
    }

    public void setIdUaipSeleccionada(int IdUaipSeleccionada) {
        this.IdUaipSeleccionada = IdUaipSeleccionada;
    }

    public List<Unidadaprendizaje> getUnidadesAprendizaje() {
        return unidadesAprendizaje;
    }

    public List<UnidadaprendizajeImparteProfesor> getUnidadesProfesor() {
        return unidadesProfesor;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Politicaevaluacion getPoliticaSeleccionada() {
        return politicaSeleccionada;
    }

    public void setPoliticaSeleccionada(Politicaevaluacion politicaSeleccionada) {
        this.politicaSeleccionada = politicaSeleccionada;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaAlumno() {
        return fechaAlumno;
    }

    public void setFechaAlumno(String fechaAlumno) {
        this.fechaAlumno = fechaAlumno;
    }

    public String getFechaResponsable() {
        return fechaResponsable;
    }

    public void setFechaResponsable(String fechaResponsable) {
        this.fechaResponsable = fechaResponsable;
    }

    public int getIDCATALOGOCONSULTAPOLITICA() {
        return IDCATALOGOCONSULTAPOLITICA;
    }

    public void setIDCATALOGOCONSULTAPOLITICA(int IDCATALOGOCONSULTAPOLITICA) {
        this.IDCATALOGOCONSULTAPOLITICA = IDCATALOGOCONSULTAPOLITICA;
    }

    public String getDisplayComentario() {
        return displayComentario;
    }

    public void setDisplayComentario(String displayComentario) {
        this.displayComentario = displayComentario;
    }

    public String getDisplayFirmas() {
        return displayFirmas;
    }

    public void setDisplayFirmas(String displayFirmas) {
        this.displayFirmas = displayFirmas;
    }

    public String getDisplaybotonPDF() {
        return displaybotonPDF;
    }

    public void setDisplaybotonPDF(String displaybotonPDF) {
        this.displaybotonPDF = displaybotonPDF;
    }
    
    
    
    public List<Politicaevaluacion> getPoliticasaceptadasRPE() {
        return politicasaceptadasRPE;
    }

    public void setPoliticasaceptadasRPE(List<Politicaevaluacion> politicasaceptadasRPE) {
        this.politicasaceptadasRPE = politicasaceptadasRPE;
    }
    
     public boolean esAlumno(){
        return loginBean.getSeleccionado().equalsIgnoreCase("Alumno");
    }
    public boolean esAlumno2(){
        return !loginBean.getSeleccionado().equalsIgnoreCase("Alumno");
    }
    
    public String rolLogin(){
        String rol = "";
        if(loginBean.getSeleccionado().equalsIgnoreCase("Profesor") || loginBean.getSeleccionado().equalsIgnoreCase("Alumno"))
        {
        rol = "Nombre del "+loginBean.getSeleccionado()+":";
        }
        else
        {
            rol = "Nombre del Profesor:";
        }
        return rol;
    }
    
    public String PEAlumno(){
        String pe;
        pe = "";
        return pe;
    }
    
    
    public boolean esProfesoroAlumno(){
        String rol = loginBean.getSeleccionado();
        return rol.equalsIgnoreCase("Profesor") || rol.equalsIgnoreCase("Alumno");
    }
    
    
}
