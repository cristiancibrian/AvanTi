package mx.avanti.siract.common;

import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;

/**
 * @author Edgar
 */
public class NodoCT {

    private int id;
    private int posicion;
    private int tipo;
    private String nombre;
    private String numero;
    private double horas;
    private double sumaHoras;
    private double porcentajeAvance;
    private boolean horasCompletas;
    private Object contenidoTem;

    public NodoCT() {
    }

    public NodoCT(int id) {
        this.id = id;
    }

    public NodoCT(Object contenidoTem, int tipo) {
        this.contenidoTem = contenidoTem;
        this.tipo = tipo;
    }

    public NodoCT(NodoCT nuevoNodo) {
        this.contenidoTem = nuevoNodo.getContenidoTem();
        this.id = nuevoNodo.getId();
        this.posicion = nuevoNodo.getPosicion();
        this.numero = nuevoNodo.getNumero();
        this.nombre = nuevoNodo.getNombre();
        this.tipo = nuevoNodo.getTipo();
        this.horas = nuevoNodo.getHoras();
        this.sumaHoras = nuevoNodo.getSumaHoras();
        this.porcentajeAvance = nuevoNodo.getPorcentajeAvance();
        this.horasCompletas = nuevoNodo.isHorasCompletas();
    }

    public NodoCT(Unidad unidad, int posicion) {
        this.contenidoTem = unidad;
        this.id = unidad.getUNIid();
        this.posicion = posicion;
        this.numero = unidad.getUNInumero().toString();
        this.nombre = unidad.getUNInombre();
        this.tipo = 0;
        this.horas = unidad.getUNIhoras();
        this.porcentajeAvance = unidad.getUNIvalorPorcentaje();
    }

    public NodoCT(Temaunidad tema, int posicion) {
        this.contenidoTem = tema;
        this.id = tema.getTUNid();
        this.posicion = posicion;
        this.numero = tema.getTUNnumero();
        this.nombre = tema.getTUNnombre();
        this.tipo = 1;
        this.horas = tema.getTUNhoras();
        this.porcentajeAvance = tema.getTUNvalorPorcentaje();
    }

    public NodoCT(Subtemaunidad subTema, int posicion) {
        this.contenidoTem = subTema;
        this.id = subTema.getSUTid();
        this.posicion = posicion;
        this.numero = subTema.getSUTnumero();
        this.nombre = subTema.getSUTnombre();
        this.tipo = 2;
        this.horas = subTema.getSUThoras();
        this.porcentajeAvance = subTema.getSUTvalorPorcentaje();
    }

    public NodoCT(Practicalaboratorio practica, int posicion) {
        this.contenidoTem = practica;
        this.id = practica.getPRLid();
        this.posicion = posicion;
        this.numero = String.valueOf(practica.getPRLnumero());
        this.nombre = practica.getPRLnombre();
        this.tipo = 3;
        this.horas = practica.getPRLhoras();
        this.porcentajeAvance = practica.getPRLvalorPorcentaje();
    }

    public NodoCT(Practicataller taller, int posicion) {
        this.contenidoTem = taller;
        this.id = taller.getPRTid();
        this.posicion = posicion;
        this.numero = String.valueOf(taller.getPRTnumero());
        this.nombre = taller.getPRTnombre();
        this.tipo = 4;
        this.horas = taller.getPRThoras();
        this.porcentajeAvance = taller.getPRTvalorPorcentaje();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getHoras() {
        return horas;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

    public double getSumaHoras() {
        return sumaHoras;
    }

    public void setSumaHoras(double sumaHoras) {
        this.sumaHoras = sumaHoras;
    }

    public double getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public boolean isHorasCompletas() {
        return horasCompletas;
    }

    public void setHorasCompletas(boolean horasCompletas) {
        this.horasCompletas = horasCompletas;
    }

    public Object getContenidoTem() {
        return contenidoTem;
    }

    public void setContenidoTem(Object contenidoTem) {
        this.contenidoTem = contenidoTem;
    }
}
