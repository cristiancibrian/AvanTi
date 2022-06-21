package mx.avanti.siract.common;

import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author
 */
public class EntidadSemaforo {

    private String NombrePrograma;
    private int numEsperados;
    private int numEnviados;
    private int numReporte;
    private String planEstudios;
    private float porcentaje;
    private String nombreUnidad;
    private MeterGaugeChartModel semaforoPrograma;

    public EntidadSemaforo() {
    }

    public EntidadSemaforo(String NombrePrograma) {
        this.NombrePrograma = NombrePrograma;
    }

    public String getNombrePrograma() {
        return NombrePrograma;
    }

    public void setNombrePrograma(String NombrePrograma) {
        this.NombrePrograma = NombrePrograma;
    }

    public int getNumEsperados() {
        return numEsperados;
    }

    public void setNumEsperados(int numEsperados) {
        this.numEsperados = numEsperados;
    }

    public int getNumEnviados() {
        return numEnviados;
    }

    public void setNumEnviados(int numEnviados) {
        this.numEnviados = numEnviados;
    }

    public int getNumReporte() {
        return numReporte;
    }

    public void setNumReporte(int numReporte) {
        this.numReporte = numReporte;
    }

    public String getPlanEstudios() {
        return planEstudios;
    }

    public void setPlanEstudios(String planEstudios) {
        this.planEstudios = planEstudios;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public MeterGaugeChartModel getSemaforo() {
        return semaforoPrograma;
    }

    public void setSemaforo(MeterGaugeChartModel semaforo) {
        this.semaforoPrograma = semaforo;
    }
}
