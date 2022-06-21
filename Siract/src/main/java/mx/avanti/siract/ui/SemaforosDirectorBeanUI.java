package mx.avanti.siract.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.avanti.siract.common.EntidadSemaforo;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.helper.SemaforosDirectorBeanHelper;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author Edgar
 */
@ManagedBean
@ViewScoped
public class SemaforosDirectorBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private SemaforosDirectorBeanHelper semaforosHelper;

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">
    private Cicloescolar cicloSeleccionado;
    private EntidadSemaforo entidadSemPrincipal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo lista">
    private List<Number> listaIntervalos;
    private List<Number> listaTicks;
    private List<Programaeducativo> listaPED;
    private List<EntidadSemaforo> listaEntidadesSemPEDs;
    private List<EntidadSemaforo> listaEntidadesSemRACTs;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos String">
    private String subTitutlo;
    private String tipoReporteSelecc;
    private String idCicloSelecc;
    // Estas variables llenaran el estado del periodo del RACT segun sea el caso
    private String fecha1 = "Sin iniciar";
    private String fecha2 = "Sin iniciar";
    private String fecha3 = "Sin iniciar";
    private String numRact = "4";
    private String coloresSemaforo;
    // Las siguientes variables corresponden al titulo de las tablas de los RACT GENERAL 1, 2 y 3, la tabla principal y de cada una de las tablas de los semáforos de cada programa educativo.
    private String headerEsperadosTablaPrincipal;
    private String headerEsperadosGeneral;
    private String headerEnviadosGeneral;
    private String headerEsperadosRACT;
    private String headerEnviadosRACT;
    private String headerEsperadosPE;
    private String headerEnviadosPE;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables primitivas">
    private int multiploGeneral, multiploPE, multiploFila2, multiploFila3;
    private int esperadosTabla1;
    private int esperadosTabla2;
    private int esperadosTabla3;
    private int esperadosRACT;
    private boolean mostrarSemPEDs = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor y postConstructor">
    public SemaforosDirectorBeanUI() {
        semaforosHelper = new SemaforosDirectorBeanHelper();
        loginBean = new LoginBean();
    }

    public void init() {
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Importante",
                "La información mostrada, corresponde unicamente a los programas educativos activos en SIRACT."));
    }

    @PostConstruct
    public void postConstructor() {
        subTitutlo = "Semáforos por Programas Educativos:";
        tipoReporteSelecc = "enviados";
        listaTicks = new ArrayList<>(Arrays.asList(new Number[]{0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}));
        cicloSeleccionado = semaforosHelper.obtenerCicloEscolarActual();
        idCicloSelecc = cicloSeleccionado.getCESid().toString();

        mostrarSemaforosPrincipales();
        actualizarSemaforos();
    }
    //</editor-fold>

    /**
     * Metodo que sera llamado cuando se ingrese a semáforos y cuando se cambien
     * los parametros de consulta, ya sea el tipo de reporte, ciclo escolar o
     * numero de RACT.
     */
    public void actualizarSemaforos() {
        listaIntervalos = new ArrayList<>(Arrays.asList(establecerIntervalos()));
        coloresSemaforo = establecerColores();
        calcularMultiplos();

        esperadosRACT = semaforosHelper.obtenerEnviadosOEsperados("esperados", idCicloSelecc, "1", null);

        entidadSemPrincipal.setNumEnviados(semaforosHelper.obtenerEnviadosOEsperados(tipoReporteSelecc, idCicloSelecc, "4", null));
        entidadSemPrincipal.setNumEsperados(esperadosRACT * multiploGeneral);
        entidadSemPrincipal.getSemaforo().setValue(semaforosHelper.calcularPorcentaje(entidadSemPrincipal.getNumEsperados(), entidadSemPrincipal.getNumEnviados()));
        entidadSemPrincipal.getSemaforo().setSeriesColors(coloresSemaforo);
        entidadSemPrincipal.getSemaforo().setIntervals(listaIntervalos);

        for (int i = 0; i < 3; i++) {
            listaEntidadesSemRACTs.get(i).setNumEnviados(semaforosHelper.obtenerEnviadosOEsperados(tipoReporteSelecc, idCicloSelecc, (i + 1) + "", null));
            listaEntidadesSemRACTs.get(i).setNumEsperados(esperadosRACT);
            listaEntidadesSemRACTs.get(i).getSemaforo().setValue(semaforosHelper.calcularPorcentaje(listaEntidadesSemRACTs.get(i).getNumEsperados(), listaEntidadesSemRACTs.get(i).getNumEnviados()));
            listaEntidadesSemRACTs.get(i).getSemaforo().setSeriesColors(coloresSemaforo);
            listaEntidadesSemRACTs.get(i).getSemaforo().setIntervals(listaIntervalos);
        }

        esperadosTabla1 = esperadosRACT;
        esperadosTabla2 = esperadosRACT * multiploFila2;
        esperadosTabla3 = esperadosRACT * multiploFila3;

        if (mostrarSemPEDs) {
            mostrarSemaforosPED();
        }

        establecerFechas();
        asignarEncabezados();
    }

    /**
     * Metodo que calcula los multiplos que multiplicaran los esperados de
     * algunos semaforos.
     */
    public void calcularMultiplos() {
        multiploGeneral = 1;
        multiploPE = 1;
        multiploFila2 = 1;
        multiploFila3 = 1;

        if ((!tipoReporteSelecc.equals("enviadosCompletos")) && (!tipoReporteSelecc.equals("enviadosIncompletos"))) {
            multiploGeneral = 3;

            if (numRact.equals("4")) {
                multiploPE = 3;
            }

            multiploFila2 = 2;
            multiploFila3 = 3;
        }
    }

    /**
     * Metodo crea y muestra las principales entidades semaforos.
     */
    public void mostrarSemaforosPrincipales() {
        listaEntidadesSemRACTs = new ArrayList<>();

        MeterGaugeChartModel ractGeneral = new MeterGaugeChartModel(0, listaIntervalos, listaTicks);
        ractGeneral.setTitle("RACT GENERAL");
        ractGeneral.setShowTickLabels(true);
        ractGeneral.setLabelHeightAdjust(90);
        ractGeneral.setIntervalOuterRadius(128);

        entidadSemPrincipal = new EntidadSemaforo();
        entidadSemPrincipal.setSemaforo(ractGeneral);

        for (int i = 0; i < 3; i++) {
            EntidadSemaforo entidadSemRACT = new EntidadSemaforo();
            entidadSemRACT.setNombrePrograma((i + 1) + "");

            MeterGaugeChartModel semaforoPrincipal = new MeterGaugeChartModel(0, listaIntervalos, listaTicks);
            semaforoPrincipal.setTitle("RACT #" + (i + 1));
            semaforoPrincipal.setShowTickLabels(true);
            semaforoPrincipal.setLabelHeightAdjust(90);
            semaforoPrincipal.setIntervalOuterRadius(100);

            entidadSemRACT.setSemaforo(semaforoPrincipal);
            listaEntidadesSemRACTs.add(entidadSemRACT);
        }
    }

    /**
     * Metodo que crea y muestra las entidades semaforo de todos los programas
     * educativos.
     */
    public void mostrarSemaforosPED() {
        listaPED = semaforosHelper.consultarPEDsPorUAC(32);
        listaEntidadesSemPEDs = new ArrayList<>();

        for (Programaeducativo ped : listaPED) {
            int esperados = semaforosHelper.obtenerEnviadosOEsperados("esperados", idCicloSelecc, numRact, ped.getPlanestudioList()) * multiploPE;

            if (esperados != 0) {
                EntidadSemaforo entidadSemPED = new EntidadSemaforo(ped.getPEDnombre());
                entidadSemPED.setNumEnviados(semaforosHelper.obtenerEnviadosOEsperados(tipoReporteSelecc, idCicloSelecc, numRact, ped.getPlanestudioList()));
                entidadSemPED.setNumEsperados(esperados);

                MeterGaugeChartModel semaforoPED = new MeterGaugeChartModel(0, listaIntervalos, listaTicks);
                semaforoPED.setTitle(ped.getPEDnombre());
                semaforoPED.setSeriesColors(coloresSemaforo);
                semaforoPED.setShowTickLabels(true);
                semaforoPED.setLabelHeightAdjust(90);
                semaforoPED.setIntervalOuterRadius(100);
                semaforoPED.setValue(semaforosHelper.calcularPorcentaje(entidadSemPED.getNumEsperados(), entidadSemPED.getNumEnviados()));

                entidadSemPED.setSemaforo(semaforoPED);
                listaEntidadesSemPEDs.add(entidadSemPED);
            }
        }
    }

    /**
     * Metodo llamado cuando cambia el combo box de tipo de reporte.
     */
    public void cambioParametroTipoReporte() {
        mostrarSemPEDs = false;
        actualizarSemaforos();
    }

    /**
     * Metodo llamado cuando cambia el combo box de ciclos escolares.
     *
     */
    public void cambioParametroCiclo() {
        mostrarSemPEDs = false;
        idCicloSelecc = cicloSeleccionado.getCESid().toString();
        actualizarSemaforos();
    }

    /**
     * Metodo llamado cuando cambia el combo box del numero de RACT.
     *
     * @param numRACT Numero de RACT
     */
    public void cambioParametroRACT(String numRACT) {
        mostrarSemPEDs = true;
        numRact = numRACT;
        actualizarSemaforos();

    }

    /**
     * Aqui se llenan las fechas para los RACT y se hacen validaciones para
     * saber si estamos dentro de fechas o es anterior o posterior.
     */
    public void establecerFechas() {
        List<Date> fechas = semaforosHelper.obtenerFechas();
        Date fechaActual = new Date();

        if (fechaActual.after(fechas.get(1))) {
            fecha1 = "Terminado";
        }
        if (fechaActual.after(fechas.get(3))) {
            fecha2 = "Terminado";
        }
        if (fechaActual.after(fechas.get(5))) {
            fecha3 = "Terminado";
        }

        if (fechaActual.after(fechas.get(0)) && fechaActual.before(fechas.get(1))) {
            fecha1 = "En proceso";
            fecha2 = "Sin iniciar";
            fecha3 = "Sin iniciar";
        }
        if (fechaActual.after(fechas.get(2)) && fechaActual.before(fechas.get(3))) {
            fecha1 = "Terminado";
            fecha2 = "En proceso";
            fecha3 = "Sin iniciar";
        }
        if (fechaActual.after(fechas.get(4)) && fechaActual.before(fechas.get(5))) {
            fecha1 = "Terminado";
            fecha2 = "Terminado";
            fecha3 = "En proceso";
        }
    }

    /**
     * En este metodo se asignan los titulos de los encabezados a las tablas de
     * cada semáforo.
     */
    public void asignarEncabezados() {
        // El encabezado de cada tabla se asigna en base al tipo de reporte
        if ((tipoReporteSelecc.equals("enviados")) || (tipoReporteSelecc.equals("noEnviados"))) {
            headerEsperadosTablaPrincipal = "Esperados a enviar acumulados por RACT";

            headerEsperadosGeneral = "Esperados acumulados al finalizar el ciclo";
            headerEnviadosGeneral = "Enviados acumulados al finalizar el ciclo";

            headerEsperadosRACT = "Esperados";
            headerEnviadosRACT = "Enviados";

            headerEsperadosPE = "Esperados";
            headerEnviadosPE = "Enviados";

            // Esta condición se cumple si el tipo de reporte es "No enviados"
            if (tipoReporteSelecc.equals(("noEnviados"))) {
                headerEnviadosGeneral = "No Enviados acumulados al finalizar el ciclo";
                headerEnviadosRACT = "No Enviados";
                headerEnviadosPE = "No Enviados";
            }

            // Si es el RACT general se le concatena la palabra "Acumulados"
            if (numRact.equals("4")) {
                headerEsperadosPE += " Acumulados";
                headerEnviadosPE += " Acumulados";
            }

        } else {
            headerEsperadosTablaPrincipal = "Esperados a entregar por RACT";

            headerEsperadosGeneral = "Esperados a enviar con porcentaje completo al finalizar el ciclo";
            headerEnviadosGeneral = "Enviados con porcentaje completo al finalizar el ciclo";

            headerEsperadosRACT = "Esperados con porcentaje completo";
            headerEnviadosRACT = "Enviados con porcentaje completo";

            headerEsperadosPE = "Esperados con porcentaje completo";
            headerEnviadosPE = "Enviados con porcentaje completo";

            // Si el tipo de reporte es por porcentaje de avance global incompleto, entra aqui
            if (tipoReporteSelecc.equals(("enviadosIncompletos"))) {
                headerEnviadosGeneral = "Enviados con porcentaje incompleto al finalizar el ciclo";
                headerEnviadosRACT = "Enviados con porcentaje incompleto";
                headerEnviadosPE = "Enviados con porcentaje incompleto";
            }
        }
    }

    /**
     * Metodo que establece el orden de los colores que tendran los semaforos.
     *
     * @return Cadena con el orden de colores de los semaforos.
     */
    public String establecerColores() {
        return (!tipoReporteSelecc.equals("noEnviados") && !tipoReporteSelecc.equals("enviadosIncompletos")) ? "e21436, F7FE2E, 30930e" : "30930e, F7FE2E, e21436";
    }

    /**
     * Metodo que establece los intervalos que tendran los semaforos.
     *
     * @return arreglo de tipo "Number" que contiene los intervalos.
     */
    public Number[] establecerIntervalos() {
        return (!tipoReporteSelecc.equals("noEnviados") && !tipoReporteSelecc.equals("enviadosIncompletos")) ? new Number[]{50, 75, 100} : new Number[]{25, 50, 100};
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public SemaforosDirectorBeanHelper getSemaforosHelper() {
        return semaforosHelper;
    }

    public void setSemeforosHelper(SemaforosDirectorBeanHelper semaforosHelper) {
        this.semaforosHelper = semaforosHelper;
    }

    public EntidadSemaforo getEntidadSemPrincipal() {
        return entidadSemPrincipal;
    }

    public void setEntidadSemPrincipal(EntidadSemaforo entidadSemPrincipal) {
        this.entidadSemPrincipal = entidadSemPrincipal;
    }

    public Cicloescolar getCicloSeleccionado() {
        return cicloSeleccionado;
    }

    public void setCicloSeleccionado(Cicloescolar cicloSeleccionado) {
        this.cicloSeleccionado = cicloSeleccionado;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo lista">
    public List<EntidadSemaforo> getListaEntidadesSemPEDs() {
        return listaEntidadesSemPEDs;
    }

    public void setListaEntidadesSemPEDs(List<EntidadSemaforo> listaEntidadesSemPEDs) {
        this.listaEntidadesSemPEDs = listaEntidadesSemPEDs;
    }

    public List<EntidadSemaforo> getListaEntidadesSemRACTs() {
        return listaEntidadesSemRACTs;
    }

    public void setListaEntidadesSemRACTs(List<EntidadSemaforo> listaEntidadesSemRACTs) {
        this.listaEntidadesSemRACTs = listaEntidadesSemRACTs;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo String">
    public String getNumRact() {
        return numRact;
    }

    public void setNumRact(String numRact) {
        this.numRact = numRact;
    }

    public String getSubTitutlo() {
        return subTitutlo;
    }

    public void setSubTitutlo(String subTitutlo) {
        this.subTitutlo = subTitutlo;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    public String getFecha3() {
        return fecha3;
    }

    public void setFecha3(String fecha3) {
        this.fecha3 = fecha3;
    }

    public String getSeleccionTipoReporte() {
        return tipoReporteSelecc;
    }

    public void setSeleccionTipoReporte(String SeleccionTipoReporte) {
        this.tipoReporteSelecc = SeleccionTipoReporte;
    }

    public String getHeaderEsperadosTablaPrincipal() {
        return headerEsperadosTablaPrincipal;
    }

    public void setHeaderEsperadosTabla(String headerEsperadosTablaPrincipal) {
        this.headerEsperadosTablaPrincipal = headerEsperadosTablaPrincipal;
    }

    public String getHeaderEsperadosGeneral() {
        return headerEsperadosGeneral;
    }

    public void setHeaderEsperadosGeneral(String headerEsperadosGeneral) {
        this.headerEsperadosGeneral = headerEsperadosGeneral;
    }

    public String getHeaderEnviadosGeneral() {
        return headerEnviadosGeneral;
    }

    public void setHeaderEnviadosGeneral(String headerEnviadosGeneral) {
        this.headerEnviadosGeneral = headerEnviadosGeneral;
    }

    public String getHeaderEsperadosPE() {
        return headerEsperadosPE;
    }

    public void setHeaderEsperadosPE(String headerEsperadosPE) {
        this.headerEsperadosPE = headerEsperadosPE;
    }

    public String getHeaderEnviadosPE() {
        return headerEnviadosPE;
    }

    public void setHeaderEnviadosPE(String headerEnviadosPE) {
        this.headerEnviadosPE = headerEnviadosPE;
    }

    public String getHeaderEsperadosRACT() {
        return headerEsperadosRACT;
    }

    public void setHeaderEsperadosRACT(String headerEsperadosRACT) {
        this.headerEsperadosRACT = headerEsperadosRACT;
    }

    public String getHeaderEnviadosRACT() {
        return headerEnviadosRACT;
    }

    public void setHeaderEnviadosRACT(String headerEnviadosRACT) {
        this.headerEnviadosRACT = headerEnviadosRACT;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de variables primitivas">
    public int getEsperadosTabla1() {
        return esperadosTabla1;
    }

    public void setEsperadosTabla1(int esperadosTabla1) {
        this.esperadosTabla1 = esperadosTabla1;
    }

    public int getEsperadosTabla2() {
        return esperadosTabla2;
    }

    public void setEsperadosTabla2(int esperadosTabla2) {
        this.esperadosTabla2 = esperadosTabla2;
    }

    public int getEsperadosTabla3() {
        return esperadosTabla3;
    }

    public void setEsperadosTabla3(int esperadosTabla3) {
        this.esperadosTabla3 = esperadosTabla3;
    }

    public boolean isMostrarSemaforosPEDs() {
        return mostrarSemPEDs;
    }

    public void setMostrarSemaforosPEDs(boolean mostrarSemaforosPEDs) {
        this.mostrarSemPEDs = mostrarSemaforosPEDs;
    }
    //</editor-fold>
}
