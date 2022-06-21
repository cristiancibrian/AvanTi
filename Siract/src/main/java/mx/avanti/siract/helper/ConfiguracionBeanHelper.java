/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

///HELPER 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.management.timer.Timer;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Alerta;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.CalendarioreporteTieneAlertaPK;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Mensaje;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Samanta Rdgz Refactor Eduardo Cardona
 */
public class ConfiguracionBeanHelper implements Serializable {

    private Configuracion configuracion, config;
    public Cicloescolar cicloescolar;
    private Cicloescolar selecCiclo;
    private Mensaje mensaje;
    private Alerta alertaConf;//S
    private Alerta alerta;//JC
    private Calendarioreporte calendario;
    private Calendarioreporte seleccionCalendario;
    private Configuracion seleccionConfiguracion;
    private List<Cicloescolar> ciclosEscolares;
    private List<Calendarioreporte> calendarioreportes;
    private List<Mensaje> mensajes;
    private List<Alerta> alertas;
    private String mensajece;
    private boolean blnciclo;
    private List<Cicloescolar> listaFiltrada;
    private List<Cicloescolar> listaCicloSeleccionada;

    //-------------------------
    public ConfiguracionBeanHelper() {

        cicloescolar = new Cicloescolar();
        configuracion = new Configuracion();
        config = new Configuracion();
        configuracion.setCONid(0);
        configuracion.setAlertaALEid(new Alerta());
        configuracion.setCicloEscolarCESid(new Cicloescolar());
        mensaje = new Mensaje();
        calendario = new Calendarioreporte();
        seleccionCalendario = new Calendarioreporte();
        seleccionConfiguracion = new Configuracion();
        alerta = new Alerta();
        ciclosEscolares = findAllCiclosEscolares();
        mensajes = findAllMensajes();
        alertas = findAllAlertas();
        selecCiclo = new Cicloescolar();
    }

    // INICIO DE METODOS DE CICLO ESCOLAR 
    public void agregarCicloEscolar(Cicloescolar cicloEscolar) {
        ServiceFacadeLocator.getInstanceCicloEscolarFacade().agregarCicloEscolar(cicloEscolar);
    }

    public void eliminarCicloEscolar(Cicloescolar cicloEscolar) {
        ServiceFacadeLocator.getInstanceCicloEscolarFacade().eliminarCicloEscolar(cicloEscolar);
    }

    public void modificarCicloEscolar(Cicloescolar cicloescolar) {
        ServiceFacadeLocator.getInstanceCicloEscolarFacade().updateCicloEscolar(cicloescolar);
    }

    /**
     * Metodo para validar en base al Calendario Actual que no se agreguen
     * ciclos que aun no existen
     *
     * @return verdadero o falso
     */
    // metodo que se va requerir cambiar a configuracion...
    public boolean validacionCiclo() {
        boolean ban = true;
        String string = cicloescolar.getCEScicloEscolar();
        String[] parts = string.split("-");
        String part1 = parts[0];
        String part2 = parts[1];

        //Comienzan los experimentos del diablo
        System.out.println("Lista ordenada:" + ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada());
        //Terminan los experimentos del diablo
        int parte1Int = Integer.parseInt(part1);
        int parte2Int = Integer.parseInt(part2);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (parte1Int > 2000 && parte1Int <= year) {
            if (parte2Int > 0 && parte2Int < 6 && parte2Int != 3) {
                ban = true;
            } else {
                ban = false;
            }
        } else {
            ban = false;
        }
        return ban;

    }

    /**
     * Metodo para validar que no existan ciclos escolares repetidos
     *
     * @return verdadero o falso
     */
    // otro metodo que se va requerir mover a configuracion
    public String validarRepetidos() {
        blnciclo = true;
        mensajece = "";
        String cicloMaximo = "";
        for (Cicloescolar ciclo : ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares()) {

            if (ciclo.getCEScicloEscolar().compareTo(cicloMaximo) > 0) {  //Comparador
                cicloMaximo = ciclo.getCEScicloEscolar();

            }

            if (ciclo.getCEScicloEscolar().equals(cicloescolar.getCEScicloEscolar()) && !ciclo.getCESid().equals(cicloescolar.getCESid())) {
                mensajece = mensajece + "[Ciclo Escolar]";
                blnciclo = false;
                break;
            }

        }

        return mensajece;
    }

    public String validarContinuidad() {
        mensajece = "";
        String cicloMaximo = "";
        for (Cicloescolar ciclo : ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares()) {

            if (ciclo.getCEScicloEscolar().compareTo(cicloMaximo) > 0) {
                cicloMaximo = ciclo.getCEScicloEscolar();

            }

            String cicloSeleccionado = cicloescolar.getCEScicloEscolar();
            System.out.println("Ciclo: " + cicloescolar.getCEScicloEscolar());
            System.out.println("Ciclo maximó: " + cicloMaximo);

            String[] partsS = cicloSeleccionado.split("-");
            String part1S = partsS[0];
            String part2S = partsS[1];

            String[] partsM = cicloMaximo.split("-");
            String part1M = partsM[0];
            String part2M = partsM[1];

            System.out.println("==================");
            System.out.println("SE IMPRIME CICLO");
            System.out.println(partsS);
            System.out.println(partsM);
            System.out.println("==================");
            if (part2M.compareTo("1") == 0) {
                System.out.println("Es 1. Debe seguir 4 o 2");
                if (part2S.compareTo("4") == 0 || part2S.compareTo("2") == 0) {
                    mensajece = "sivalidos";
                } else {
                    mensajece = "novalidos";
                }
            }
            if (part2M.compareTo("2") == 0) {
                System.out.println("Es 2. Debe seguir 5 o 1");
                if (part2S.compareTo("5") == 0 || part2S.compareTo("1") == 0) {
                    mensajece = "sivalidos";
                } else {
                    mensajece = "novalidos";
                }
            }
            if (part2M.compareTo("4") == 0) {
                System.out.println("Es 4. Debe seguir 2");
                if (part2S.compareTo("2") == 0) {
                    mensajece = "sivalidos";
                } else {
                    mensajece = "novalidos";
                }
            }
            if (part2M.compareTo("5") == 0) {
                System.out.println("Es 5. Debe seguir 1");
                if (part2S.compareTo("1") == 0) {
                    mensajece = "sivalidos";
                } else {
                    mensajece = "novalidos";
                }
            }

        }
        return mensajece;
    }

    /**
     * Metodo para buscar la cadena ingresada en tiempo real
     *
     * @param campo este parametro no se utiliza en el metodo
     * @param busqueda
     * @return lista Filtrada
     */
    public List<Cicloescolar> filtrado(String campo, String busqueda) {
        listaFiltrada = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
        if (busqueda.trim().length() > 0) {
            listaFiltrada.clear();
            for (Cicloescolar ciclo : ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada()) {
                String CambioObjNum = ciclo.getCEScicloEscolar();
                if (CambioObjNum.startsWith(busqueda)) {
                    listaFiltrada.add(ciclo);
                }
            }
        }
        return listaFiltrada;
    }

    public void eliminarDeLista(int id) {
        for (Cicloescolar ciclo : listaCicloSeleccionada) {
            if (ciclo.getCESid().equals(id)) {
                int index = listaCicloSeleccionada.indexOf(ciclo);
                listaCicloSeleccionada.remove(index);
                break;
            }
        }
    }

    // geter y setter
    public Cicloescolar getSelecCiclo() {
        return selecCiclo;
    }

    public void setSelecCiclo(Cicloescolar selecCiclo) {
        this.selecCiclo = selecCiclo;
    }

    public String getMensajece() {
        return mensajece;
    }

    public void setMensajece(String mensajece) {
        this.mensajece = mensajece;
    }

    public boolean isBlnciclo() {
        return blnciclo;
    }

    public void setBlnciclo(boolean blnciclo) {
        this.blnciclo = blnciclo;
    }

    public List<Cicloescolar> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Cicloescolar> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public List<Cicloescolar> getListaCicloSeleccionada() {
        return listaCicloSeleccionada;
    }

    public void setListaCicloSeleccionada(List<Cicloescolar> listaCicloSeleccionada) {
        this.listaCicloSeleccionada = listaCicloSeleccionada;
    }

    // FIN DE METODOS CICLO ESCOLAR 
    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Cicloescolar getCicloescolar() {
        return cicloescolar;
    }

    public Cicloescolar juasjuas() {
        cicloescolar = getSelecCiclo();
        return cicloescolar;
    }

    public void setCicloescolar(Cicloescolar cicloescolar) {
        this.cicloescolar = cicloescolar;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public Alerta getAlertaCorte() {
        return alertaConf;
    }

    public void setAlertaCorte(Alerta alertaCorte) {
        this.alertaConf = alertaCorte;
    }

    public Calendarioreporte getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendarioreporte calendario) {
        this.calendario = calendario;
    }

    public Calendarioreporte getSeleccionCalendario() {
        return seleccionCalendario;
    }

    public Configuracion getSeleccionConfiguracion() {
        return seleccionConfiguracion;
    }

    public void setSeleccionCalendario(Calendarioreporte seleccionCalendario) {
        this.seleccionCalendario = seleccionCalendario;
    }

    public List<Cicloescolar> getCiclosEscolares() {
        return ciclosEscolares;
    }

    public void setCiclosEscolares(List<Cicloescolar> ciclosEscolares) {
        this.ciclosEscolares = ciclosEscolares;
    }

    public List<Calendarioreporte> getCalendarioreportes() {
        return calendarioreportes;
    }

    public void setCalendarioreportes(List<Calendarioreporte> calendarioreportes) {
        this.calendarioreportes = calendarioreportes;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }

    /**
     * metodo para obtener los ciclos escolares en una Lista ordenada
     *
     * @return Lista ordenada de ciclos escolares
     */
    public List<Cicloescolar> mostrarListaOrdenada() {
        ciclosEscolares = ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
        return ciclosEscolares;
    }

    //-----------------------------------------------------------------------Metodos componente sin lazy
    public List<Cicloescolar> findAllCiclosEscolares() { // ok
        ciclosEscolares = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares();
        //cicloEscolarDelegate.getListaCicloEscolar();
        List<Cicloescolar> csord = new ArrayList<Cicloescolar>();
        System.out.println(ciclosEscolares.get(1).getCEScicloEscolar().substring(0, 4));
        int v1 = 0, v2 = 0;
        Cicloescolar auxCES = new Cicloescolar();
        for (int i = 0; i < ciclosEscolares.size(); i++) {
            for (int k = 0; k < ciclosEscolares.size() - 1; k++) {
                v1 = Integer.parseInt(ciclosEscolares.get(k).getCEScicloEscolar().substring(0, 4) + "" + Integer.parseInt(ciclosEscolares.get(k).getCEScicloEscolar().substring(5)));
                v2 = Integer.parseInt(ciclosEscolares.get(k + 1).getCEScicloEscolar().substring(0, 4) + "" + Integer.parseInt(ciclosEscolares.get(k + 1).getCEScicloEscolar().substring(5)));

                if (v2 > v1) {
                    auxCES = ciclosEscolares.get(k + 1);
                    ciclosEscolares.set(k + 1, ciclosEscolares.get(k));
                    ciclosEscolares.set(k, auxCES);
                }
            }
        }

        return ciclosEscolares;
    }

    public Configuracion findConfiguracion(int id) { // ok
        cicloescolar = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar(id);
        //cicloEscolarDelegate.BuscarAlertaPorTipo(id);
        if (cicloescolar.getConfiguracionList().size() < 1) {
            //    configuracionDelegate.bConfPorCiclo(cicloescolar.getCesid())==null){
            configuracion = new Configuracion();

            configuracion.setAlertaALEid(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("General"));
            configuracion.setCicloEscolarCESid(cicloescolar);
            configuracion.setCONid(0);
            configuracion.setCONnumeroSemanas(16);
            configuracion.setCONfechaInicioSemestre(new Date());
        } else {
            configuracion = new Configuracion();
            configuracion = cicloescolar.getConfiguracionList().get(0);

        }
        calendarioreportes = new ArrayList<Calendarioreporte>();
        return configuracion;
    }

    public List<Calendarioreporte> findAllCalendariosReportes(int id) { // ok
        if (configuracion.getCONid() == 0 || configuracion.getCONid() == null || configuracion == null) {
            System.out.println("configuracion vacia");
            configuracion = new Configuracion();
            configuracion.setAlertaALEid(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("General"));

            configuracion.setCicloEscolarCESid(cicloescolar);
        } else {
            Configuracion confi = ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarConfiguracionPorID(id);
            calendarioreportes = new ArrayList<Calendarioreporte>();
            calendarioreportes = confi.getCalendarioreporteList();
            //calendarioReporteDelegate.getCon_cre(id);
        }
        return calendarioreportes;
    }

    public Date getFechaUltimoReporte() {
        Date ultimoReporte = new Date();
        List<Calendarioreporte> cs = calendarioreportes;
        System.out.println(cs.size());
        if (cs.size() == 0) {
            ultimoReporte = configuracion.getCONfechaInicioSemestre();
            //ultimoReporte = ultimoReporte;
        } else {
            for (int i = 0; i < cs.size(); i++) {
                ultimoReporte = cs.get(i).getCREfechaLimite();
            }
        }
        return ultimoReporte;
    }

    public int getCantidadRACTS() {
        List<Calendarioreporte> cs = calendarioreportes;
        return cs.size();
    }

    public Date getFechaInicioSem() {
        Date inicioSemestre = new Date();

        inicioSemestre = configuracion.getCONfechaInicioSemestre();

        return inicioSemestre;
    }

    public int guardarCalendarioReporte(Date fechaCorte, Date fechaLimite, int diasCorte, int diasLimite, int diasAtraso) { // ok
        List<Calendarioreporte> cs = calendarioreportes;

        boolean bandera = true;
        int intBandera = 0;
        for (int i = 0; i < cs.size(); i++) {
            if (fechaCorte.compareTo(cs.get(i).getCREfechaCorte()) < 0) {
                intBandera = 3;
                bandera = false;
                break;
            }
            if (((fechaCorte.equals(cs.get(i).getCREfechaCorte()) || fechaCorte.after(cs.get(i).getCREfechaCorte())) && (fechaCorte.equals(cs.get(i).getCREfechaLimite()) || fechaCorte.before(cs.get(i).getCREfechaLimite()))) || (fechaLimite.equals(cs.get(i).getCREfechaCorte()) || fechaLimite.after(cs.get(i).getCREfechaCorte())) && (fechaLimite.equals(cs.get(i).getCREfechaLimite()) || fechaLimite.before(cs.get(i).getCREfechaLimite()))) {
                intBandera = 2;
                bandera = false;
                System.out.println(bandera + " ----corte comparacion");
                break;
            }
            if (fechaCorte.before(cs.get(i).getCREfechaCorte()) && fechaLimite.after(cs.get(i).getCREfechaLimite())) {
                intBandera = 2;
                bandera = false;
                break;
            }
            if (fechaCorte.equals(configuracion.getCONfechaInicioSemestre()) || fechaLimite.equals(configuracion.getCONfechaInicioSemestre())) {
                intBandera = 2;
                bandera = false;
                break;
            }
            System.out.println("fecha corte= " + fechaCorte.getTime() + " - fecha limite= " + fechaLimite.getTime());
            System.out.println("Fecha Corte BD= " + cs.get(i).getCREfechaCorte().getTime() + " - Fecha Limite BD= " + cs.get(i).getCREfechaLimite().getTime());
        }
        System.out.println(bandera + " ----SAlio del ciclo de traslapes");
        if (bandera) {
            Calendarioreporte c = new Calendarioreporte();

            //HashSet setCalendarios=new HashSet();
            CalendarioreporteTieneAlerta calendarioreporteTieneAlertaC = new CalendarioreporteTieneAlerta();
            CalendarioreporteTieneAlerta calendarioreporteTieneAlertaL = new CalendarioreporteTieneAlerta();
            CalendarioreporteTieneAlerta calendarioreporteTieneAlertaA = new CalendarioreporteTieneAlerta();
            // se crea calendarioReporte
            c.setCREid(0);
            c.setCREfechaCorte(fechaCorte);
            c.setCREfechaLimite(fechaLimite);
            /*agregare el la configuracion al calendario reporte para la tabla
            puente en la bd*/
            List<Configuracion> listaConfi = new ArrayList();
            listaConfi.add(configuracion);
            c.setConfiguracionList(listaConfi);
            // se guarda calendarioReporte
            ServiceFacadeLocator.getInstanceCalendarioReporteFacade().saveCalendarioReporte(c);
//            
            //actualizo la configuracion para tener la lista de calendario reportes actualizada
            configuracion = ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarConfiguracionPorID(configuracion.getCONid());
            System.out.println(c.getCREid() + " id calendario reporte");

            calendarioreporteTieneAlertaC.setCalendarioreporte(c);
            calendarioreporteTieneAlertaC.setAlerta(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-corte"));

            calendarioreporteTieneAlertaC.setCALdias(diasCorte);
            // actualizo la lista por el que se caba de guardar

            /*se guarda el calendario alerta de precorte*/
            calendarioreporteTieneAlertaC.setCALnumeroReporte(configuracion.getCalendarioreporteList().size());
            CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaIdC = new CalendarioreporteTieneAlertaPK(c.getCREid(), ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-corte").getALEid());
            calendarioreporteTieneAlertaC.setCalendarioreporteTieneAlertaPK(calendarioreporteTieneAlertaIdC);
            ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().GuardarCalendarioreporteTieneAlerta(calendarioreporteTieneAlertaC);

            /*se guarda el calendario alerta de prelimite*/
            calendarioreporteTieneAlertaL.setCalendarioreporte(c);
            calendarioreporteTieneAlertaL.setAlerta(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-limite"));

            calendarioreporteTieneAlertaL.setCALdias(diasLimite);
            calendarioreporteTieneAlertaL.setCALnumeroReporte(configuracion.getCalendarioreporteList().size());
            CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaIdL = new CalendarioreporteTieneAlertaPK(c.getCREid(), ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-limite").getALEid());
            calendarioreporteTieneAlertaL.setCalendarioreporteTieneAlertaPK(calendarioreporteTieneAlertaIdL);
            ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().GuardarCalendarioreporteTieneAlerta(calendarioreporteTieneAlertaL);

            /*se guarda el calendario alerta de atraso*/
            calendarioreporteTieneAlertaA.setCalendarioreporte(c);
            calendarioreporteTieneAlertaA.setAlerta(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Atraso"));
            calendarioreporteTieneAlertaA.setCALdias(diasAtraso);
            calendarioreporteTieneAlertaA.setCALnumeroReporte(configuracion.getCalendarioreporteList().size());
            CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaIdA = new CalendarioreporteTieneAlertaPK(c.getCREid(), ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Atraso").getALEid());
            calendarioreporteTieneAlertaA.setCalendarioreporteTieneAlertaPK(calendarioreporteTieneAlertaIdA);
            ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().GuardarCalendarioreporteTieneAlerta(calendarioreporteTieneAlertaA);


            /* actualizo de nuevo configuracion por la actualizacion de las 
             alertas  en sus calendarioReporte */
            configuracion = ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarConfiguracionPorID(configuracion.getCONid());
            findAllCalendariosReportes(configuracion.getCONid());
            c = new Calendarioreporte();
            return 1;
        } else {
            return intBandera;
        }
    }

    public void update(Alerta alerta) {
        ServiceFacadeLocator.getInstanceAlertaFacade().ActualizarAlerta(alerta);
    }

    public void eliminarCalendarioReporte(Calendarioreporte cr) { //Correcto // ok
        Calendarioreporte c = new Calendarioreporte();
        c = null;
        int asd = 0;
        while (c == null) {
            System.out.println("estoy aqui . . . aun: " + cr.getCREid());
            c = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().find(cr.getCREid());

            asd++;
            if (asd == 100) {
                break;
            }
        }
        System.out.println("sali");
        ServiceFacadeLocator.getInstanceCalendarioReporteFacade().eliminarCalendarioReporte(c);

        c = new Calendarioreporte();
        findAllCalendariosReportes(configuracion.getCONid());
    }

    public Calendarioreporte modificacionCalendarioReporte(Calendarioreporte cr) {//nuevo, bueno? // ok
        System.out.println("LO QUE CONTIENE LA VARIABLE cr en la clase ConfiguracionBeanHelper:" + cr.getCREid());
        Calendarioreporte c = new Calendarioreporte();

        c = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().find(cr.getCREid());

        return c;
    }

    public List<Mensaje> findAllMensajes() { // ok
        return ServiceFacadeLocator.getInstanceMensajeFacade().getMensajes();
    }

    public void guardarMensaje(Mensaje mensaje) { // ok
        ServiceFacadeLocator.getInstanceMensajeFacade().saveMensaje(mensaje);
    }

    public List<Alerta> findAllAlertas() { // ok
        return ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertas();

    }

    public void guardarAlertas(Alerta alerta) { // ok
        ServiceFacadeLocator.getInstanceAlertaFacade().guardarAlerta(alerta);
        System.out.println(alerta.getALEtipo() + "------------" + alerta.getALEid());

    }

    public void guardarConfiguracion() { // ok

        FacesContext contexto = FacesContext.getCurrentInstance();
        if (configuracion.getCONid() == null) {

            ServiceFacadeLocator.getInstanceConfiguracionFacade().GuardarConfiguracion(configuracion);
            contexto.addMessage(null, new FacesMessage("Guardado exitoso", ""));
            PrimeFaces.current().ajax().update("frmCicloEscolar:growl");
            PrimeFaces.current().executeScript("location.reload()");

        } else {
      
            ServiceFacadeLocator.getInstanceConfiguracionFacade().ActualizarConfiguracion(configuracion);
        }
    }

    public boolean modificarCalendarioReporteOK(Calendarioreporte cr, Date fechaCorte, Date fechaLimite, int diasCorte, int diasLimite, int diasAtraso) {//nuevo, bueno?!
        List<Calendarioreporte> cs = calendarioreportes;
        boolean bandera = true;
        for (int i = 0; i < cs.size(); i++) {
            if (getSeleccionCalendario().getCREid() == cs.get(i).getCREid()) {
                System.out.println("No me comparo conmigo mismo :D");
            } else {
                if (((fechaCorte.equals(cs.get(i).getCREfechaCorte()) || fechaCorte.after(cs.get(i).getCREfechaCorte())) && (fechaCorte.equals(cs.get(i).getCREfechaLimite()) || fechaCorte.before(cs.get(i).getCREfechaLimite()))) || (fechaLimite.equals(cs.get(i).getCREfechaCorte()) || fechaLimite.after(cs.get(i).getCREfechaCorte())) && (fechaLimite.equals(cs.get(i).getCREfechaLimite()) || fechaLimite.before(cs.get(i).getCREfechaLimite()))) {
                    bandera = false;
                    break;
                }
                if (fechaCorte.before(cs.get(i).getCREfechaCorte()) && fechaLimite.after(cs.get(i).getCREfechaLimite())) {
                    bandera = false;
                    break;
                }
                if (fechaCorte.equals(configuracion.getCONfechaInicioSemestre()) || fechaLimite.equals(configuracion.getCONfechaInicioSemestre())) {
                    bandera = false;
                    break;
                }
            }
        }
        if (bandera) {
            Calendarioreporte c = new Calendarioreporte();
            c = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().find(cr.getCREid());

            c.setCREfechaCorte(fechaCorte);
            c.setCREfechaLimite(fechaLimite);

            CalendarioreporteTieneAlerta calendarioreporteTieneAlertaC = new CalendarioreporteTieneAlerta();
            CalendarioreporteTieneAlerta calendarioreporteTieneAlertaL = new CalendarioreporteTieneAlerta();
            CalendarioreporteTieneAlerta calendarioreporteTieneAlertaA = new CalendarioreporteTieneAlerta();

            ServiceFacadeLocator.getInstanceCalendarioReporteFacade().modificarCalendarioReporte(c);

            System.out.println(c.getCREid() + " id del Calendario reporte");
            calendarioreporteTieneAlertaC.setCalendarioreporte(c);
            calendarioreporteTieneAlertaC.setAlerta(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-corte"));
            calendarioreporteTieneAlertaC.setCALdias(diasCorte);
            System.out.println("Alerta Pre-corte : DIAS DE CORTE " + diasCorte);
            calendarioreporteTieneAlertaC.setCALnumeroReporte(c.getCalendarioreporteTieneAlertaList().get(0).getCALnumeroReporte());
            CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaIdC = new CalendarioreporteTieneAlertaPK(c.getCREid(), ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-corte").getALEid());
            calendarioreporteTieneAlertaC.setCalendarioreporteTieneAlertaPK(calendarioreporteTieneAlertaIdC);

            ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().ActualizarCalendarioreporteTieneAlerta(calendarioreporteTieneAlertaC);

            calendarioreporteTieneAlertaL.setCalendarioreporte(c);
            calendarioreporteTieneAlertaL.setAlerta(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-limite"));
            calendarioreporteTieneAlertaL.setCALdias(diasLimite);
            System.out.println("Alerta Pre-limite : DIAS LIMITE = " + diasLimite);

            calendarioreporteTieneAlertaL.setCALnumeroReporte(c.getCalendarioreporteTieneAlertaList().get(0).getCALnumeroReporte());
            CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaIdL = new CalendarioreporteTieneAlertaPK(c.getCREid(), ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Pre-limite").getALEid());
            calendarioreporteTieneAlertaL.setCalendarioreporteTieneAlertaPK(calendarioreporteTieneAlertaIdL);

            ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().ActualizarCalendarioreporteTieneAlerta(calendarioreporteTieneAlertaL);

            calendarioreporteTieneAlertaA.setCalendarioreporte(c);
            calendarioreporteTieneAlertaA.setAlerta(ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Atraso"));
            calendarioreporteTieneAlertaA.setCALdias(diasAtraso);
            System.out.println("Alerta atraso : Dias Atraso " + diasAtraso);

            calendarioreporteTieneAlertaA.setCALnumeroReporte(c.getCalendarioreporteTieneAlertaList().get(0).getCALnumeroReporte());
            CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaIdA = new CalendarioreporteTieneAlertaPK(c.getCREid(), ServiceFacadeLocator.getInstanceAlertaFacade().BuscarAlertaPorTipo("Atraso").getALEid());
            calendarioreporteTieneAlertaA.setCalendarioreporteTieneAlertaPK(calendarioreporteTieneAlertaIdA);

            ServiceFacadeLocator.getInstanceCalendarioreporteTieneAlertaFacade().ActualizarCalendarioreporteTieneAlerta(calendarioreporteTieneAlertaA);

            findAllCalendariosReportes(configuracion.getCONid());
            return true;
        } else {
            return false;
        }
    }
}
