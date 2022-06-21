package mx.avanti.siract.common;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mario
 */
public class ResultadoReporte implements Serializable {

    private int claveAreaConocimiento;
    private String areaConocimiento;
    private int claveUnidadAprendizaje;
    private String unidadAprendizaje;
    private int numEmpleadoProfesor;
    private String nombreProfesor;
    private String grupo;
    private Date fechaRACT1, fechaRACT2, fechaRACT3;
    private float avanceRACT1 = -1, avanceRACT2 = -1, avanceRACT3 = -1;
    private int claveProgramaEducativo;
    private String programaEducativo;
    private int idRACT;
    private int uaipId;
    private String nompreResponsablePE;
    private String planEstudio;
    private int totalEntregado = 0, totalEsperado = 0;
    private int totalEntregadoRACT1 = 0, totalEntregadoRACT2 = 0, totalEntregadoRACT3 = 0;
    private int areaAdminId = 0;
    private String areaAdminResp = "", areaAdminNombre = "";

    //GETTERS AND SETTERS
    private Integer unidadAprendizajeId;
    private Integer programaId;
    private Integer profesorId;

    public int getIdRACT() {
        return idRACT;
    }

    public void setIdRACT(Integer idRACT) {
        this.idRACT = (idRACT != null) ? idRACT : 0;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = (programaEducativo != null) ? programaEducativo : "?";
    }

    public int getClaveAreaConocimiento() {
        return claveAreaConocimiento;
    }

    public void setClaveAreaConocimiento(Integer claveAreaConocimiento) {
        this.claveAreaConocimiento = (claveAreaConocimiento != null) ? claveAreaConocimiento : 0;
    }

    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = (areaConocimiento != null) ? areaConocimiento : "?";
    }

    public int getClaveUnidadAprendizaje() {
        return claveUnidadAprendizaje;
    }

    public void setClaveUnidadAprendizaje(Integer claveUnidadAprendizaje) {
        this.claveUnidadAprendizaje = claveUnidadAprendizaje;
    }

    public String getUnidadAprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadAprendizaje(String unidadAprendizaje) {
        this.unidadAprendizaje = (unidadAprendizaje != null) ? unidadAprendizaje : "?";
    }

    public int getNumEmpleadoProfesor() {
        return numEmpleadoProfesor;
    }

    public void setNumEmpleadoProfesor(Integer numEmpleadoProfesor) {
        this.numEmpleadoProfesor = (numEmpleadoProfesor != null) ? numEmpleadoProfesor : 0;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = (nombreProfesor != null) ? nombreProfesor : "?";
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = (grupo != null) ? grupo : "?-?-?";
    }

    public String getNompreResponsablePE() {
        return nompreResponsablePE;
    }

    public void setNompreResponsablePE(String nompreResponsablePE) {
        this.nompreResponsablePE = nompreResponsablePE;
    }

    public String getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(String planEstudio) {
        this.planEstudio = planEstudio;
    }

    public int getClaveProgramaEducativo() {
        return claveProgramaEducativo;
    }

    public void setClaveProgramaEducativo(int claveProgramaEducativo) {
        this.claveProgramaEducativo = claveProgramaEducativo;
    }

    public Date getFechaRACT1() {
        return fechaRACT1;
    }

    public void setFechaRACT1(Date fechaRACT1) {
        this.fechaRACT1 = fechaRACT1;
    }

    public Date getFechaRACT2() {
        return fechaRACT2;
    }

    public void setFechaRACT2(Date fechaRACT2) {
        this.fechaRACT2 = fechaRACT2;
    }

    public Date getFechaRACT3() {
        return fechaRACT3;
    }

    public void setFechaRACT3(Date fechaRACT3) {
        this.fechaRACT3 = fechaRACT3;
    }

    public float getAvanceRACT1() {
        return avanceRACT1;
    }

    public void setAvanceRACT1(float avanceRACT1) {
        this.avanceRACT1 = avanceRACT1;
    }

    public float getAvanceRACT2() {
        return avanceRACT2;
    }

    public void setAvanceRACT2(float avanceRACT2) {
        this.avanceRACT2 = avanceRACT2;
    }

    public float getAvanceRACT3() {
        return avanceRACT3;
    }

    public void setAvanceRACT3(float avanceRACT3) {
        this.avanceRACT3 = avanceRACT3;
    }

    public int getUaipId() {
        return uaipId;
    }

    public void setUaipId(int uaipId) {
        this.uaipId = uaipId;
    }

    //END GETTERS AND SETTERS
    public int getTotalEntregado() {
        return totalEntregado;
    }

    public void setTotalEntregado(int totalEntregado) {
        this.totalEntregado = totalEntregado;
    }

    public int getTotalEsperado() {
        return totalEsperado;
    }

    public void setTotalEsperado(int totalEsperado) {
        this.totalEsperado = totalEsperado;
    }

    public int getTotalEntregadoRACT1() {
        return totalEntregadoRACT1;
    }

    public void setTotalEntregadoRACT1(int totalEntregadoRACT1) {
        this.totalEntregadoRACT1 = totalEntregadoRACT1;
    }

    public int getTotalEntregadoRACT2() {
        return totalEntregadoRACT2;
    }

    public void setTotalEntregadoRACT2(int totalEntregadoRACT2) {
        this.totalEntregadoRACT2 = totalEntregadoRACT2;
    }

    public int getTotalEntregadoRACT3() {
        return totalEntregadoRACT3;
    }

    public void setTotalEntregadoRACT3(int totalEntregadoRACT3) {
        this.totalEntregadoRACT3 = totalEntregadoRACT3;
    }

    public int getAreaAdminId() {
        return areaAdminId;
    }

    public void setAreaAdminId(int areaAdminId) {
        this.areaAdminId = areaAdminId;
    }

    public String getAreaAdminResp() {
        return areaAdminResp;
    }

    public void setAreaAdminResp(String areaAdminResp) {
        this.areaAdminResp = areaAdminResp;
    }

    public String getAreaAdminNombre() {
        return areaAdminNombre;
    }

    public Integer getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setAreaAdminNombre(String areaAdminNombre) {
        this.areaAdminNombre = areaAdminNombre;
    }

    void setUnidadAprendizajeId(Integer unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }
}
