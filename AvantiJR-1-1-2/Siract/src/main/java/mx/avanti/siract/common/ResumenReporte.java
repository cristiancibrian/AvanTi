package mx.avanti.siract.common;

import java.text.DecimalFormat;

/**
 *
 * @author EduardoCardona <>
 */
public class ResumenReporte {

    private String nombreProgramaEducativo;
    private String PlanEstudio;
    private String nombreProfesor;
    private String nombreUnidadAprendizaje;
    private String nombreAreaConocimiento;
    private String nombreAreaAdministrativa;
    private String nombreSegunCriterio;
    private int numEsperados;
    private int numEntregados;
    private double porcentaje;
    int idAreaConociminto;
    int idProgramaEducativo;
    int idProfesor;
    int idUnidadAprendizaje;
    int idAreaAdministrativa;

    public ResumenReporte() {
    }

    public double calcularPorcentaje() {
        double porcent = (numEntregados * 100);
        if (numEsperados == 0) {
            porcentaje = 0;
        } else {
            porcentaje = porcent / numEsperados;
            DecimalFormat df = new DecimalFormat("#.00");
            porcentaje = Double.parseDouble(df.format(porcentaje));
        }
        return porcentaje;
    }

    /**
     * metodo para calcular el porcentaje segun las cariables de entragados y
     * esperados
     */
    public void calcular() {
        double porcent = (numEntregados * 100);
        if (numEsperados == 0) {
            porcentaje = 0;
        } else {
            porcentaje = porcent / numEsperados;
            DecimalFormat df = new DecimalFormat("#.00");
            porcentaje = Double.parseDouble(df.format(porcentaje));
        }

    }

    //------------------ getter y setter ------------//
    public String getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }

    public String getPlanEstudio() {
        return PlanEstudio;
    }

    public void setPlanEstudio(String PlanEstudio) {
        this.PlanEstudio = PlanEstudio;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getNombreUnidadAprendizaje() {
        return nombreUnidadAprendizaje;
    }

    public void setNombreUnidadAprendizaje(String nombreUnidadAprendizaje) {
        this.nombreUnidadAprendizaje = nombreUnidadAprendizaje;
    }

    public String getNombreAreaConocimiento() {
        return nombreAreaConocimiento;
    }

    public void setNombreAreaConocimiento(String nombreAreaConocimiento) {
        this.nombreAreaConocimiento = nombreAreaConocimiento;
    }

    public String getNombreAreaAdministrativa() {
        return nombreAreaAdministrativa;
    }

    public void setNombreAreaAdministrativa(String nombreAreaAdministrativa) {
        this.nombreAreaAdministrativa = nombreAreaAdministrativa;
    }

    public int getNumEsperados() {
        return numEsperados;
    }

    public void setNumEsperados(int numEsperados) {
        this.numEsperados = numEsperados;
    }

    public int getNumEntregados() {
        return numEntregados;
    }

    public void setNumEntregados(int numEntregados) {
        this.numEntregados = numEntregados;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombreSegunCriterio() {
        return nombreSegunCriterio;
    }

    public void setNombreSegunCriterio(String nombreSegunCriterio) {
        this.nombreSegunCriterio = nombreSegunCriterio;
    }

    public int getIdAreaConociminto() {
        return idAreaConociminto;
    }

    public void setIdAreaConociminto(int idAreaConociminto) {
        this.idAreaConociminto = idAreaConociminto;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdUnidadAprendizaje() {
        return idUnidadAprendizaje;
    }

    public void setIdUnidadAprendizaje(int idUnidadAprendizaje) {
        this.idUnidadAprendizaje = idUnidadAprendizaje;
    }

    public int getIdAreaAdministrativa() {
        return idAreaAdministrativa;
    }

    public void setIdAreaAdministrativa(int idAreaAdministrativa) {
        this.idAreaAdministrativa = idAreaAdministrativa;
    }
}
