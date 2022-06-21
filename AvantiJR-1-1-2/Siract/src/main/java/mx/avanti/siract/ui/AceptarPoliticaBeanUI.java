/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.ui;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import mx.avanti.siract.entity.Alumno;

import mx.avanti.siract.entity.Cicloescolar;

import mx.avanti.siract.helper.UsuarioBeanHelper;
import mx.avanti.siract.helper.PoliticaEvaluacionBeanHelper;

import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.entity.Profesor;

import org.primefaces.PrimeFaces;

/**
 *
 * @author Eduardo Cardona
 */
@ManagedBean
@ViewScoped
public class AceptarPoliticaBeanUI implements Serializable {


    @ManagedProperty(value = "#{loginBean}")

    private LoginBean loginBean;
    //TODO: change helper
    private PoliticaEvaluacionBeanHelper politicaevaluacionBeanHelper = null;
    private UsuarioBeanHelper usuarioBeanHelper = null;
    String fechaActual = "DD/MM/AAAA";
    private Profesor profesor;
    private String nombreCompleto;
    private String cicloEscolar;
    private int cicloEscolarId;
    private List<Politicaevaluacion> politicasPendientes;
    private int uipIdSeleccionado;
    Politicaevaluacion politicaSeleccionada;
    private String textoNombreUsuario = "";
    private boolean esAlumno = false;
    private Politicaevaluacioncomentario comentarioPolitica;
    private Alumno alumno;
    private int politicasPendientesCount =0;
    
    private boolean disableBotones = true;
    private boolean disableSelect = true;
    
    
    private final int IDCATALOGOACEPTARPOLITICA = 20;

    public int getIDCATALOGOACEPTARPOLITICA() {
        return IDCATALOGOACEPTARPOLITICA;
    }
    
    
    
    // Constructor
    public AceptarPoliticaBeanUI() {
        init();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = sdf.format(date);
        Cicloescolar cicloEscolarActual = politicaevaluacionBeanHelper.cicloEscolarActual();
        cicloEscolarId = cicloEscolarActual.getCESid();
        cicloEscolar = cicloEscolarActual.getCEScicloEscolar();
        comentarioPolitica = new Politicaevaluacioncomentario();
        // TODO: Logica para Maestro o Alumno
        
      
        
    }
    
    public void init() {
        
        politicaevaluacionBeanHelper = new PoliticaEvaluacionBeanHelper();

    }
    
    @PostConstruct
    public void postConstructor() {
       
        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            try{
                if(loginBean.getLogueado().getProfesorList().size() > 0){
                    profesor = loginBean.getLogueado().getProfesorList().get(0);
                    nombreCompleto = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
                    textoNombreUsuario = "Nombre de profesor:";
                }
                if(loginBean.getLogueado().getAlumnoList().size() >0){
                    alumno = loginBean.getLogueado().getAlumnoList().get(0);
                    nombreCompleto = alumno.getALNombre() + " " + alumno.getALApellidoPater()+ " " + alumno.getALApellidoMaterno();
                    textoNombreUsuario = "Nombre de alumno:";
                    esAlumno = true;
                }
            }catch(Exception ex){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            }
            if(esAlumno){
                politicasPendientes = politicaevaluacionBeanHelper
                    .politicasAlumno(alumno.getALId());
            }else{
                   politicasPendientes = politicaevaluacionBeanHelper
                    .getPoliticaPorResponsable(profesor.getPROid());
            }
            
            politicasPendientesCount = politicasPendientes.size();
            disableSelect= false;
            if( politicasPendientesCount <1){
                disableSelect = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin políticas pendientes", "Ninguna política por aceptar."));

            }

           
        }

        
    }
    
    
    public void onSelectPolitica(){
        disableBotones = false;
        if(uipIdSeleccionado == 0){
            politicaSeleccionada = new Politicaevaluacion();
            disableBotones = true;
        }
        comentarioPolitica = new Politicaevaluacioncomentario();
        PrimeFaces.current().ajax().update("formId:representante");
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentarPO");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:gs");
       
        
    }
      public void rechazar(){
        boolean respuesta = false;
        boolean res = false;
        if(esAlumno){
            politicaevaluacionBeanHelper.ActualizarRechazoAlumno(politicaSeleccionada);
            comentarioPolitica.setAlumnoALId(alumno);
        }else{
            politicaevaluacionBeanHelper.ActualizarRechazoResponsable(politicaSeleccionada);
            comentarioPolitica.setProfesorProId(profesor);
        }
        comentarioPolitica.setPoliticaPOEId(politicaSeleccionada);
        
       
       respuesta = politicaevaluacionBeanHelper.agregarComentarioPolitica(comentarioPolitica);
       if(respuesta){
          
          afterGuardadoExitoso("rechazada");
        
       }
        
    }
    
    public void Aceptacion(){
        boolean respuesta = false;
        
        //TODO: Validar si es alumno;
        if(esAlumno){
            respuesta = politicaevaluacionBeanHelper.AceptacionAlumno(politicaSeleccionada);
        }else{
            respuesta = politicaevaluacionBeanHelper.AceptacionRPE(politicaSeleccionada);
        }
        if(respuesta){
            afterGuardadoExitoso("guardada");
        }
        
    }
    
    
    private void afterGuardadoExitoso(String estado){
        FacesContext context = FacesContext.getCurrentInstance();
        disableBotones = true;
        politicasPendientes.remove(politicaSeleccionada);
        politicaSeleccionada = new Politicaevaluacion();
        politicasPendientesCount = politicasPendientes.size();
        if(politicasPendientesCount < 1){
            disableSelect = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin políticas pendientes", "Ninguna política por aceptar."));

        }
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentarPO");
        PrimeFaces.current().ajax().update("formId:UnidadesAprendisaje");
        PrimeFaces.current().ajax().update("formId:pendientesCoun");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:gs");
        PrimeFaces.current().ajax().update("formId:representante");
        
        
        
        
        
        
        String message = "política "+estado+" exitosamente, " + politicasPendientes.size() +" política(s) pendiente(s).";
         
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Guardado exitoso",message) );
    }
    
    private void afterError(){
         FacesContext context = FacesContext.getCurrentInstance();
        String message = "Hubo un error al guardar la política, por favor intente de nuevo.";
         
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"",message) );
    }
    
   
    
    
   
    //-----------SETTERS & GETTER ---------------//

    public String getFechaActual() {
        return fechaActual;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCicloEscolar() {
        return cicloEscolar;
    }

    public List<Politicaevaluacion> getPoliticasPendientes() {
        return politicasPendientes;
    } 

    public int getUipIdSeleccionado() {
        return uipIdSeleccionado;
    }

    public void setUipIdSeleccionado(int uipIdSeleccionado) {
        this.uipIdSeleccionado = uipIdSeleccionado;
        for(Politicaevaluacion p : politicasPendientes){
            if(p.getUIPId().getUIPid() == uipIdSeleccionado ){
                politicaSeleccionada = p;
                break;
            }
        }
    }

    public Politicaevaluacion getPoliticaSeleccionada() {
        return politicaSeleccionada;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getTextoNombreUsuario() {
        return textoNombreUsuario;
    }

    public Politicaevaluacioncomentario getComentarioPolitica() {
        return comentarioPolitica;
    }

    public void setComentarioPolitica(Politicaevaluacioncomentario comentarioPolitica) {
        this.comentarioPolitica = comentarioPolitica;
    }

    public int getPoliticasPendientesCount() {
        return politicasPendientesCount;
    }

    public boolean isDisableBotones() {
        return disableBotones;
    }

    public boolean isDisableSelect() {
        return disableSelect;
    }
    


    
    
   
    
 
}