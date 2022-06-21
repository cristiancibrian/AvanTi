/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.common;

/**
 *
 * @author Francisco Reyes
 */
public class EntidadGrafica {
    
    private String planEstudios;
    private String nombre;
    private int numEsperados;
    private int numEntregados;
    private float porcentaje;
    
    public EntidadGrafica() {
    
    }

    public String getPlanEstudios() {
        return planEstudios;
    }

    public void setPlanEstudios(String planEstudios) {
        this.planEstudios = planEstudios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
}
