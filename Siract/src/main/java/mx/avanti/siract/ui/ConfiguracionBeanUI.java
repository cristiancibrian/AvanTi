package mx.avanti.siract.ui;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.dao.BaseDAO;
import mx.avanti.siract.entity.Alerta;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.helper.ConfiguracionBeanHelper;
import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;


@ManagedBean
@ViewScoped
public class ConfiguracionBeanUI implements Serializable {

    private final int ID_CATALOGO_CONFIGURACION = 2;
    private ConfiguracionBeanHelper configuracionBeanHelper = null;
    private List<Cicloescolar> listaciclosEscolares;
    private Cicloescolar ciclo;
// ATRIBUTOS METODOS TODO DE BEANUI CICLO ESCOLAR

    
    /* variable para controlar los botones de eliminar y modificar */
    private String deshabilitarBoton="true";
    private String titleMod = "Seleccione un registro de la tabla";    
    private String titleElim = "Seleccione un registro de la tabla";
    
    /*variables para controlar los dialog */
    private String header;
    private String deshabilitar;
    private String deshabilitarAceptar;
    private boolean selecvisible;
    private String mensajeConfirm;
    private String mensajeRep;
    private String busqueda="";
    private List<Cicloescolar> listaFiltrada;
    
    public boolean isSelecvisible() {
        return selecvisible;
    }

    public void setSelecvisible(boolean selecvisible) {
        this.selecvisible = selecvisible;
    }

    public int getID_CATALOGO_CONFIGURACION() {
        return ID_CATALOGO_CONFIGURACION;
    }

    // FIN DE METODOS IMPLEMENTADOS
    public Cicloescolar getCiclo() {
        return ciclo;
    }

    public void setCiclo(Cicloescolar ciclo) {
        this.ciclo = ciclo;
    }
    //Estos booleanos determinan el estado de los botones de la interfaz
    private boolean botonDel = true, botonMod = true, ns = true, envio = true, botonAdd = true, botonAceptar = true, seleccionCal = true;

    //-------------------------------------------------------------------------------------------------------------Inicia componente sin lazy
    Date fechaCorte = new Date();
    Date fechaLimite = new Date();

    int diasCorte = 7, diasLimite = 7, diasAtraso = 1;
    Mensaje mensaje = new Mensaje();
    //
    Date fechaCorteM = new Date();
    Date fechaLimiteM = new Date();
    int diasCorteM = 7, diasLimiteM = 7, diasAtrasoM = 1;
    //
    Date fechaMinSemestre = new Date();
    Date fechaMaxSemestre = new Date();
    Date fechaMinCR = new Date();
    Date ultimoRACT = new Date();
    LocalDate fechaMinSemestre1;
    LocalDate fechaMaxSemestre1;
    LocalDate fechaCorte1;

    public LocalDate getCONfechaInicio() {
        return CONfechaInicio;
    }

    public void setCONfechaInicio(LocalDate CONfechaInicio) {
        this.CONfechaInicio = CONfechaInicio;
    }

    LocalDate CONfechaInicio;

    public LocalDate getFechaMinSemestre1() {
        return fechaMinSemestre1;
    }

    public void setFechaMinSemestre1(LocalDate fechaMinSemestre1) {
        this.fechaMinSemestre1 = fechaMinSemestre1;
    }

    public LocalDate getFechaMaxSemestre1() {
        return fechaMaxSemestre1;
    }

    public void setFechaMaxSemestre1(LocalDate fechaMaxSemestre1) {
        this.fechaMaxSemestre1 = fechaMaxSemestre1;
    }

    public LocalDate getFechaCorte1() {
        return fechaCorte1;
    }

    public void setFechaCorte1(LocalDate fechaCorte1) {
        this.fechaCorte1 = fechaCorte1;
    }

    public LocalDate getFechaCorteM1() {
        return fechaCorteM1;
    }

    public void setFechaCorteM1(LocalDate fechaCorteM1) {
        this.fechaCorteM1 = fechaCorteM1;
    }

    public LocalDate getFechaLimite1() {
        return fechaLimite1;
    }

    public void setFechaLimite1(LocalDate fechaLimite1) {
        this.fechaLimite1 = fechaLimite1;
    }

    public LocalDate getFechaLimiteM1() {
        return fechaLimiteM1;
    }

    public void setFechaLimiteM1(LocalDate fechaLimiteM1) {
        this.fechaLimiteM1 = fechaLimiteM1;
    }

    public LocalDate getUltimoRACT1() {
        return ultimoRACT1;
    }

    public void setUltimoRACT1(LocalDate ultimoRACT1) {
        this.ultimoRACT1 = ultimoRACT1;
    }

    LocalDate fechaCorteM1;
    LocalDate fechaLimite1;
    LocalDate fechaLimiteM1;
    LocalDate ultimoRACT1;

    boolean habilitarFechaLimite = true;
    boolean habilitarFechaLimiteM = true;
    int numeroSemanas=0;
    int ssss;
    List<Alerta> listaAlertas;

    @PostConstruct
    public void postConstructor(){
        mostrarListaOrdenada();
    }
    public List<Alerta> getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(List<Alerta> listaAlertas) {
        this.listaAlertas = listaAlertas;
    }
    public int getSsss() {
        return ssss;
    }

    public void setSsss(int ssss) {
        this.ssss = ssss;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public int getNumeroSemanas() {
        return numeroSemanas;
    }

    public void setNumeroSemanas(int numeroSemanas) {
        this.numeroSemanas = numeroSemanas;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getDiasCorte() {
        return diasCorte;
    }
    
    public void setDiasCorte(int diasCorte) {
        this.diasCorte = diasCorte;
    }

    public int getDiasLimite() {
        return diasLimite;
    }

    public void setDiasLimite(int diasLimite) {
        this.diasLimite = diasLimite;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaCorteM() {
        return fechaCorteM;
    }

    public void setFechaCorteM(Date fechaCorteM) {
        this.fechaCorteM = fechaCorteM;
    }

    public Date getFechaLimiteM() {
        return fechaLimiteM;
    }

    public void setFechaLimiteM(Date fechaLimiteM) {
        this.fechaLimiteM = fechaLimiteM;
    }

    public Date getUltimoRACT() {
        return ultimoRACT;
    }

    public void setUltimoRACT(Date ultimoRACT) {
        this.ultimoRACT = ultimoRACT;
    }

    
    public int getDiasCorteM() {
        return diasCorteM;
    }

    public void setDiasCorteM(int diasCorteM) {
        this.diasCorteM = diasCorteM;
    }

    public int getDiasLimiteM() {
        return diasLimiteM;
    }

    public void setDiasLimiteM(int diasLimiteM) {
        this.diasLimiteM = diasLimiteM;
    }

    public int getDiasAtrasoM() {
        return diasAtrasoM;
    }

    public void setDiasAtrasoM(int diasAtrasoM) {
        this.diasAtrasoM = diasAtrasoM;
    }

    public Date getFechaMinSemestre() {
        return fechaMinSemestre;
    }

    public void setFechaMinSemestre(Date fechaMinSemestre) {
        this.fechaMinSemestre = fechaMinSemestre;
    }

    public Date getFechaMaxSemestre() {
        return fechaMaxSemestre;
    }

    public void setFechaMaxSemestre(Date fechaMaxSemestre) {
        this.fechaMaxSemestre = fechaMaxSemestre;
    }

    public Date getFechaMinCR() {
        return fechaMinCR;
    }

    public void setFechaMinCR(Date fechaMinCR) {
        this.fechaMinCR = fechaMinCR;
    }

    public boolean isHabilitarFechaLimite() {
        return habilitarFechaLimite;
    }

    public void setHabilitarFechaLimite(boolean habilitarFechaLimite) {
        this.habilitarFechaLimite = habilitarFechaLimite;
    }

    public boolean isHabilitarFechaLimiteM() {
        return habilitarFechaLimiteM;
    }

    public void setHabilitarFechaLimiteM(boolean habilitarFechaLimiteM) {
        this.habilitarFechaLimiteM = habilitarFechaLimiteM;
    }

    public List<Cicloescolar> mostrarListaOrdenada(){
        listaciclosEscolares = configuracionBeanHelper.mostrarListaOrdenada();
      
        return listaciclosEscolares;
    }
    //--------------------Metodos listeners para la vista
    public void changeCicloEscolarListener(int cesID) {
        if (cesID == 0) {
            ns = true;
            envio = true;
            botonAdd = true;
            botonDel = true;
            botonMod = true;

            List<Calendarioreporte> cs = new ArrayList<Calendarioreporte>();
            configuracionBeanHelper.setCalendarioreportes(cs);

        } else {
            ns = false;
            envio = false;
            botonAdd = false;
            //Este es el método que actualiza la configuración.
            configuracionBeanHelper.findConfiguracion(cesID);
        }
        changeCalendarioReporte();

        String ciclo = configuracionBeanHelper.getCicloescolar().getCEScicloEscolar();
        int tipo = Integer.parseInt(ciclo.substring(5));
        int year = Integer.parseInt(configuracionBeanHelper.getCicloescolar().getCEScicloEscolar().substring(0, 4));

        if (tipo == 1) {
            fechaMinSemestre.setYear(year - 1900);
            fechaMaxSemestre.setYear(year - 1900);
            fechaMinSemestre.setMonth(1);
            fechaMaxSemestre.setMonth(6);
        }
        if (tipo == 2) {
            fechaMinSemestre.setYear(year - 1900);
            fechaMaxSemestre.setYear(year - 1900 + 1);
            fechaMinSemestre.setMonth(7);
            fechaMaxSemestre.setMonth(0);
        }

        fechaMinSemestre.setDate(1);
        fechaMaxSemestre.setDate(30);

        fechaMaxSemestre1 = convertToLocalDateViaInstant(fechaMaxSemestre);
        fechaMinSemestre1 = convertToLocalDateViaInstant(fechaMinSemestre);



        System.out.println("Mensajeeeee");
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void changeCalendarioReporte() {
            if (configuracionBeanHelper.getConfiguracion().getCONid()== null) {
                configuracionBeanHelper.findAllCalendariosReportes(1);
            } else {
                configuracionBeanHelper.findAllCalendariosReportes(configuracionBeanHelper.getConfiguracion().getCONid());
            }
    }

    public void guardarCalendarioReporte() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        fechaCorte = Date.from(fechaCorte1.atStartOfDay(defaultZoneId).toInstant());
        fechaLimite = Date.from(fechaLimite1.atStartOfDay(defaultZoneId).toInstant());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
         /**
          * Case 1: Se guardaron los cambios correctamente
          * Case 2: Hubo traslape en las fechas
          * Case 3: Hubo un error al registrar, ya habia un ract registrado 
          * con una fecha posterior a la que se quiere registrar
          */
            int intTraslape;
            intTraslape = configuracionBeanHelper.guardarCalendarioReporte(fechaCorte, fechaLimite, diasCorte, diasLimite, diasAtraso);
            switch (intTraslape){
                case 1:
                    growler("Se guardó correctamente ", " ");
                break;
                case 2:
                    growler("Se encontró traslape ", "Revise las fechas");
                break;
                case 3:
                    growler("Se encontró una fecha posterior a la registrada ", "Revise las fechas");
                break;
            }
            
        
        setHabilitarFechaLimite(true);
    }
    
    public void getFechaUltimoReporte(){
        
        ultimoRACT = configuracionBeanHelper.getFechaUltimoReporte();
        Calendar c = Calendar.getInstance(); 
        c.setTime(ultimoRACT);
        c.add(Calendar.DATE, 0);
        ultimoRACT = c.getTime();

        ultimoRACT1 = convertToLocalDateViaInstant(ultimoRACT);
        
        setHabilitarFechaLimite(true);
        
    }

    public void modificarCalendarioReporte() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        fechaCorteM = Date.from(fechaCorteM1.atStartOfDay(defaultZoneId).toInstant());
        fechaLimiteM = Date.from(fechaLimiteM1.atStartOfDay(defaultZoneId).toInstant());;
            if (fechaCorteM.after(fechaLimiteM)) {
                growler("La fecha de corte es mayor a limite", "Revise las fechas");
            } else {
                boolean bolTraslape;
                bolTraslape = configuracionBeanHelper.modificarCalendarioReporteOK(calendarioSeleccionado, fechaCorteM, fechaLimiteM, diasCorteM, diasLimiteM, diasAtrasoM);
                if (bolTraslape) {
                    growler("Se guardó correctamente ", " ");
                } else {
                    growler("Se encontro traslape ", "Revise las fechas");
                }
            }


        setHabilitarFechaLimiteM(true);
    }
    Calendarioreporte calendarioSeleccionado = new Calendarioreporte();

    public void eliminarCalendarioReporte() {
        //configuracionBeanHelper.eliminarCalendarioReporte();
        configuracionBeanHelper.eliminarCalendarioReporte(calendarioSeleccionado);
        calendarioSeleccionado = new Calendarioreporte();
        growler("Se eliminó correctamente ", " ");
    }

    public void modificarCalendarioReporteListener() {
        Calendarioreporte c = new Calendarioreporte();
        Date example = null, example2 = null;
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //c=configuracionBeanHelper.modificacionCalendarioReporte();
        c = configuracionBeanHelper.modificacionCalendarioReporte(calendarioSeleccionado);
        
        setFechaCorteM(c.getCREfechaCorte());
        setFechaLimiteM(c.getCREfechaLimite());
        List<CalendarioreporteTieneAlerta> ctas = new ArrayList<CalendarioreporteTieneAlerta>();
        ctas.addAll(c.getCalendarioreporteTieneAlertaList());
        for (int i = 0; i < ctas.size(); i++) {
            if (ctas.get(i).getAlerta().getALEid()== 2) {
                setDiasCorteM(ctas.get(i).getCALdias());
            }
            if (ctas.get(i).getAlerta().getALEid()== 3) {
                setDiasAtrasoM(ctas.get(i).getCALdias());
            }
            if (ctas.get(i).getAlerta().getALEid()== 4) {
                setDiasLimiteM(ctas.get(i).getCALdias());
            }
        }

        CONfechaInicio = convertToLocalDateViaInstant(configuracionBeanHelper.getConfiguracion().getCONfechaInicioSemestre());
        fechaCorteM1 = convertToLocalDateViaInstant(fechaCorteM);
        fechaLimiteM1 = convertToLocalDateViaInstant(fechaLimiteM);

    }

    public void changeMensajeListener(int menID) {
        if (menID != 0) {
            Alerta alerta = new Alerta();
            BaseDAO baseDAO = new BaseDAO();
            baseDAO.setTipo(Mensaje.class);
            mensaje = (Mensaje) baseDAO.find(menID);
            baseDAO.setTipo(Alerta.class);
            alerta = (Alerta) baseDAO.find(menID);
            mensaje.setAlertaALEid(alerta);
        } else {
            setMensaje(new Mensaje(new Alerta(), " "));
            System.out.println("Selecciona mensaje asdasdasdasdasdasdasdasd");
        }
    }

    public void guardarMensaje() {
        if (mensaje.getMENid()!= 0) {
            if (mensaje.getMENmensaje()== null || mensaje.getMENmensaje()== " " || mensaje.getMENmensaje().isEmpty()) {
                growler("Configuración de mensaje vacía", " ");
            } else {
                configuracionBeanHelper.guardarMensaje(mensaje);
                growler("Configuración de mensaje guardada", " ");
                botonAceptar = true;
                 PrimeFaces.current().ajax().update("frmCalendario:aceptarConfig");
                 PrimeFaces.current().ajax().update("frmCalendario");
            }
        }
    }
    
    public void modificarAlertas(){
        configuracionBeanHelper.findAllAlertas();
        configuracionBeanHelper.setAlertas(configuracionBeanHelper.getAlertas());
        System.out.println(configuracionBeanHelper.getAlertas().get(0).getALEcolor());
        for(Alerta a:configuracionBeanHelper.getAlertas()){
            configuracionBeanHelper.update(a);
            System.out.println(a.getALEtipo());
            System.out.println(a.getALEcolor());
        }
        growler("Configuración de alerta guardada", " ");
    }
    
    public void guardarAlertas() {
        
        configuracionBeanHelper.guardarAlertas(configuracionBeanHelper.getAlerta());
        
    }

    
    public void agregarNuevoCR() {
        
        if(configuracionBeanHelper.getCantidadRACTS()<3){
            setFechaCorte(new Date());
            setFechaLimite(new Date());
            setFechaLimite1(null);
            setFechaCorte1(null);
            setDiasCorte(7);
            setDiasLimite(7);
            setDiasAtraso(1);
            getFechaUltimoReporte();
        PrimeFaces.current().executeScript("PF('dlgcaptura').show()");
        }else{
            growler("", "Ya no es posible registrar otra fecha de reporte");
        }
    }

    public void guardarConfiguracion() {
        
        if (configuracionBeanHelper.getConfiguracion().getCONnumeroSemanas()<= 0) {
            growler("Número de semanas no puede ser negativo", " ");
        } else {
            
            configuracionBeanHelper.guardarConfiguracion();
            growler("Configuración actualizada", " ");
            botonAceptar = true;
            PrimeFaces.current().ajax().update("frmCalendario:aceptarConfig");
        }

        getFechaUltimoReporte();
    }

    //

    public void onRowSelect(SelectEvent event) {
        Calendarioreporte calendarioreporte = new Calendarioreporte();
        calendarioreporte = (Calendarioreporte) event.getObject();
        botonDel = false;
        botonMod = false;
        System.out.println(calendarioreporte.getCREid());
        calendarioSeleccionado = calendarioreporte;
    }

    public void onCloseDialogMensajes() {
        System.out.println("Cerraste mensajes :D");
        setMensaje(new Mensaje(new Alerta(), ""));
        Mensaje m = new Mensaje();
        m.setAlertaALEid(new Alerta());
        m.setMENid(0);
        m.setMENmensaje(" ");
        configuracionBeanHelper.setMensaje(m);
    }
    //-------------------------------------------------------------------------------------------------------------Termina componente sin lazy

    public ConfiguracionBeanUI() {
        init();
    }

    private void init() {
        configuracionBeanHelper = new ConfiguracionBeanHelper();
        
    }

//----Getters y Setters----//
    public ConfiguracionBeanHelper getConfiguracionBeanHelper() {
        return configuracionBeanHelper;
    }

    public void setConfiguracionBeanHelper(ConfiguracionBeanHelper configuracionBeanHelper) {
        this.configuracionBeanHelper = configuracionBeanHelper;
    }

    //Getters para los booleanos de la interfaz

    public boolean isBotonDel() {
        return botonDel;
    }

    public boolean isBotonMod() {
        return botonMod;
    }

    public boolean isNs() {
        return ns;
    }

    public boolean isEnvio() {
        return envio;
    }

    public boolean isBotonAdd() {
        return botonAdd;
    }

    public boolean isBotonAceptar() {
        return botonAceptar;
    }

    public boolean isSeleccionCal() {
        return seleccionCal;
    }
    //Getter Ciclo Escolar y metodos de Ciclo escolar.
    public String getDeshabilitarBoton() {
        return deshabilitarBoton;
    }
    
    public void setDeshabilitarBoton(String deshabilitarBoton) {
        this.deshabilitarBoton = deshabilitarBoton;
    }
    public String getTitleMod() {
        return titleMod;
    }

    public void setTitleMod(String titleMod) {
        this.titleMod = titleMod;
    }
    public String getTitleElim() {
        return titleElim;
    }
    public void setTitleElim(String titleElim) {
        this.titleElim = titleElim;
    }
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    
    public String getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(String deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public String getDeshabilitarAceptar() {
        return deshabilitarAceptar;
    }

    public void setDeshabilitarAceptar(String deshabilitarAceptar) {
        this.deshabilitarAceptar = deshabilitarAceptar;
    }

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public void setMensajeConfirm(String mensajeConfirm) {
        this.mensajeConfirm = mensajeConfirm;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    public String getMensajeRep() {
        return mensajeRep;
    }

    public void setMensajeRep(String mensajeRep) {
        this.mensajeRep = mensajeRep;
    }
    
    /**
     * Metodo que asigna un mensaje al dialog "confirmdlg"
     */
    public void setMensajeConfirm(){
        if(deshabilitar.equals("true")){
            mensajeConfirm = "¿Está seguro de eliminar el registro?";
        }else{
            mensajeConfirm = "¿Está seguro de guardar los cambios?";
        }
        PrimeFaces.current().ajax().update("confirmdlg");
    }
    /**
     * Metodo que asigna un titulo al dialog
     */
    public String dlgCabecera(int i){
        if(i == 1){
            header = "Agregar ciclo escolar";
            deshabilitar = "false";
        }
        if(i == 2){
            header = "Eliminar ciclo escolar";
            deshabilitar = "true";
        }
        if(i == 3){
            header = "Modificar ciclo escolar";
            deshabilitar = "false";
        }        
        return header;
    }
    
    /// METODOS DE LOS BOTONES Y ACCIONES 
    /*
    Testeo de nuevo
    */
  
    public void nuevo(){
        limpiarSeleccion();
        dlgCabecera(1);
        
        configuracionBeanHelper.setCicloescolar(new Cicloescolar());
        configuracionBeanHelper.setSelecCiclo(new Cicloescolar());
    }
    /*
    Testeo para limpiar seleccion2 
    */
    public void limpiarSeleccion(){
        configuracionBeanHelper.setCicloescolar(null);
        configuracionBeanHelper.setCicloescolar(new Cicloescolar());
        configuracionBeanHelper.setSelecCiclo(new Cicloescolar());   
        configuracionBeanHelper.setSelecCiclo(new Cicloescolar());   
        configuracionBeanHelper.setListaCicloSeleccionada(null);            
        botonesModElim();
    }  

    public String botonesModElim(){
        if(configuracionBeanHelper.getSelecCiclo()==null )
           return "true";
        else
            return "false";
    }
    
    public void modificar(){
        dlgCabecera(3);
        try{

                if(configuracionBeanHelper.getCicloescolar()!=null){ 
                   System.out.println("Pusiste algo");              
                } else {  
                  if(configuracionBeanHelper.getCicloescolar()==null){  
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");
                    System.out.println("No has puesto nada");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                }
        }catch(NullPointerException e){
            System.out.println("Error al modificar configuracion.");
        }      
    }
        public void eliminar(){
        dlgCabecera(2);
        try{
            if(configuracionBeanHelper.getCicloescolar()!= null){
                selecvisible=true;
            } else{
                if(configuracionBeanHelper.getCicloescolar() == null){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");    
                    FacesContext.getCurrentInstance().addMessage(null, message);        
                }
            }
            //cuando funcione hay que borrar esto
//        try{
//            if(configuracionBeanHelper.getListaCicloSeleccionada().size() == 1){
//                configuracionBeanHelper.setCicloescolar(configuracionBeanHelper.getListaCicloSeleccionada().get(0));   
//                selecvisible=true;
//            // deshabilitarAceptar = "false";
//            } else{
//                if(configuracionBeanHelper.getListaCicloSeleccionada().size() < 1){
//                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se selecciono ningun registro");         
//                    RequestContext.getCurrentInstance().showMessageInDialog(message);                
//                }else{
//                    configuracionBeanHelper.setCicloescolar(configuracionBeanHelper.getListaCicloSeleccionada().get(0));
//
//                }
//            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    /**
     * Metodo para validar si el ciclo escolar esta vacio
     * @return verdadero o falso
     */
    public boolean validacion(){
        if(configuracionBeanHelper.getCicloescolar().getCEScicloEscolar().isEmpty()){
            return true;
        }else{
            
            //Comienzan los experimentos del diablo
//            System.out.println("Comienzan los experimentos del diablo");
//            List<Cicloescolar> datos = configuracionBeanHelper.getCiclosEscolares();
//            System.out.println(datos);
//            System.out.println("Terminan los experimentos del diablo");
            return false;
        }   
    }
    
    
    public void eliminacionConfirmada(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Eliminando", "Se eliminó correctamente"));
        configuracionBeanHelper.setCicloescolar(new Cicloescolar());
        configuracionBeanHelper.setSelecCiclo(new Cicloescolar());
        PrimeFaces.current().executeScript("PF('confdlgElim').hide()");
        
        if(configuracionBeanHelper.getListaCicloSeleccionada().size() == 1){
            configuracionBeanHelper.setCicloescolar(configuracionBeanHelper.getListaCicloSeleccionada().get(0));
        }
                            
    }
    
    /**
     * Metodo para validar en base al Calendario Actual 
     * que no se agreguen ciclos que aun no existen 
     * @return verdadero o falso
     */
    public boolean validacionCiclo(){
        return configuracionBeanHelper.validacionCiclo();
    }
    public void modificacionConfirmada(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
        configuracionBeanHelper.agregarCicloEscolar(configuracionBeanHelper.getCicloescolar());
        configuracionBeanHelper.setCicloescolar(new Cicloescolar());
        configuracionBeanHelper.setSelecCiclo(new Cicloescolar());  
        PrimeFaces.current().executeScript("PF('confdlgMod').hide()");
        
    }
    
    public void confirmacionAceptada(){
        if(deshabilitar.equals("true")){
            if(configuracionBeanHelper.getCicloescolar().getUnidadaprendizajeList().size() >= 1 || configuracionBeanHelper.getCicloescolar().getUnidadaprendizajeImparteProfesorList().size() >= 1 && configuracionBeanHelper.getCicloescolar().getUnidadaprendizajeImparteProfesorList() != null){
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar este Ciclo Escolar");
                FacesContext.getCurrentInstance().addMessage(null, message);
                limpiarSeleccion();
            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("", "Se eliminó correctamente"));
                configuracionBeanHelper.eliminarCicloEscolar(configuracionBeanHelper.getCicloescolar());
                configuracionBeanHelper.setCicloescolar(new Cicloescolar());
                configuracionBeanHelper.setSelecCiclo(new Cicloescolar());
                PrimeFaces.current().executeScript("PF('confirmdlg').hide()");                        
            }
            PrimeFaces.current().executeScript("PF('dlg').show()");
        }
      
    }
    public String onClickSubmit() {
        String resultado = "";
        setMensajeConfirm();
        System.out.println("Checkpoint 1");
        if (deshabilitar.equals("true")) {
            PrimeFaces.current().executeScript("PF('confirmdlg').show()");
        } else {
            if (validacion()) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de validacion", "Favor de llenar el campo de ciclo escolar");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                mensajeRep = configuracionBeanHelper.validarRepetidos();
                if (mensajeRep.isEmpty()) {
                    mensajeRep = "vacio";
                    
                }
                if (mensajeRep.equals("vacio")) {
                    
                    if (validacionCiclo()==true) {
                        if (header.equals("Agregar ciclo escolar")) {
                            if(configuracionBeanHelper.validarContinuidad().compareTo("sivalidos")==0){
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                                configuracionBeanHelper.agregarCicloEscolar(configuracionBeanHelper.getCicloescolar());
                                configuracionBeanHelper.setCicloescolar(new Cicloescolar());
                            }else{
                               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ciclo escolar no valido; no es sucesivo.");
                                FacesContext.getCurrentInstance().addMessage(null, message);
                               limpiarSeleccion();
                            }
 
                        } else if (header.equals("Modificar ciclo escolar")) {
                            //Se pondrá la condición de repetición de ciclos
                            if(configuracionBeanHelper.getCicloescolar().getUnidadaprendizajeList().size() >= 1 || configuracionBeanHelper.getCicloescolar().getUnidadaprendizajeImparteProfesorList().size() >= 1){
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede Modificar este Ciclo Escolar");
                                FacesContext.getCurrentInstance().addMessage(null, message);
                               limpiarSeleccion();
                            }else{
                                if(configuracionBeanHelper.validarContinuidad().compareTo("sivalidos")==0){
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.addMessage(null, new FacesMessage("", "Se guardó correctamente"));
                                    configuracionBeanHelper.modificarCicloEscolar(configuracionBeanHelper.getCicloescolar());
                                    configuracionBeanHelper.setSelecCiclo(new Cicloescolar());
                                    configuracionBeanHelper.setCicloescolar(new Cicloescolar());
                                    configuracionBeanHelper.setCicloescolar(configuracionBeanHelper.getCicloescolar());
                                }else{
                                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ciclo escolar no valido; no es sucesivo.");
                                    FacesContext.getCurrentInstance().addMessage(null, message);
                                    limpiarSeleccion();
                                }
                            }
                        }
                    }
                    else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ciclo escolar no válido");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Campos repetidos en:" + mensajeRep);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }
      
        mostrarBotones();
        return resultado;
    }
    public void mostrarBotones(){
        try{
            if(configuracionBeanHelper.getCicloescolar().getCESid()> 0){
                deshabilitarBoton = "false";
                titleElim = "Eliminar";
                titleMod = "Modificar";
            }
        }catch(NullPointerException e){}
    }
   public void esconderBotones(){
        deshabilitarBoton = "true";
        titleElim = "Seleccione un registro de la tabla";
        titleMod = "Seleccione un registro de la tabla";
    }
    
    public String botonesSelect(){
        if(configuracionBeanHelper.getCicloescolar().getCESid()>0){
            return "false";
        }
        else{
            return "true";
        }
    }
    public boolean mostrarSeleccionGrupo() {
        return configuracionBeanHelper.getListaCicloSeleccionada()!= null && configuracionBeanHelper.getListaCicloSeleccionada().size() > 1;
    }
    
    public String toolTip(int i){
        if(configuracionBeanHelper.getCicloescolar()== null )
            return "Seleccione un registro de la tabla";
        else{
            if(i == 1)
                return "Eliminar";
            else if(i == 2)
                return "Modificar";
        }
        return "";
    } 
     
    public void onRowUnselect(UnselectEvent event){
        configuracionBeanHelper.setSelecCiclo(new Cicloescolar());
        configuracionBeanHelper.setSelecCiclo((Cicloescolar)event.getObject());
    }
    
    
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

//----Listeners----//
    public void habilitador(boolean estado) {
        //Botones de Eliminar y Modificar
        botonDel = estado;
        botonMod = estado;
    }

    public void habilitarAceptar(boolean estado) {
        //Afecta el estado del botón de Aceptar
        botonAceptar = estado;
    }

    //Método manejador de growls

    public void growler(String tituloGrowl, String mensajeGrowl) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, new FacesMessage(tituloGrowl, mensajeGrowl));
    }

//Alertas
//    EnvioAlertaThread envioAlertaThread=new EnvioAlertaThread();
//    public void ejecutarAlertas(boolean estado){
//       // envioAlertaThread.revisarAlertas(estado);
//        envioAlertaThread.estatusAlertas(estado);
//    }

    /**
     * @return the listaciclosEscolares
     */
    public List<Cicloescolar> getListaciclosEscolares() {
        return listaciclosEscolares;
    }

    /**
     * @param listaciclosEscolares the listaciclosEscolares to set
     */
    public void setListaciclosEscolares(List<Cicloescolar> listaciclosEscolares) {
        this.listaciclosEscolares = listaciclosEscolares;
    }
}
