/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

import java.io.Serializable;
import java.util.Objects;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Practicascampo;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;

/**
 *
 * @author Y
 */
public class NodoMultiClass implements Serializable, Comparable<NodoMultiClass> {

    Unidad unidad;
    Temaunidad temaUnidad;
    Subtemaunidad subTema;
    String nombre="";
    String subtemaId="";
    String numero="";
    String horas="";
    String unidadId="";
    String temaId="";
    String id="";
    Boolean horasCompletas=true;
    Boolean deReporteAnterior=false;
    Boolean parcialSelected=false;

    public Boolean getParcialSelected() {
        return parcialSelected;
    }

    public void setParcialSelected(Boolean parcialSelected) {
        this.parcialSelected = parcialSelected;
    }

    
    public Boolean isDeReporteAnterior() {
        return deReporteAnterior;
    }

    public void setDeReporteAnterior(Boolean deReporteAnterior) {
        this.deReporteAnterior = deReporteAnterior;
    }

    public Boolean isHorasCompletas() {
        return horasCompletas;
    }

    public void setHorasCompletas(Boolean horasCompletas) {
        this.horasCompletas = horasCompletas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Practicalaboratorio getPracticaL() {
        return practicaL;
    }

    public void setPracticaL(Practicalaboratorio practicaL) {
        this.practicaL = practicaL;
    }

    public Practicataller getPracticaT() {
        return practicaT;
    }

    public void setPracticaT(Practicataller practicaT) {
        this.practicaT = practicaT;
    }

    public Practicascampo getPracticaC() {
        return practicaC;
    }

    public void setPracticaC(Practicascampo practicaC) {
        this.practicaC = practicaC;
    }
    String tipo = "/";
    Practicalaboratorio practicaL;
    Practicataller practicaT;
    Practicascampo practicaC;
    
    String observaciones = "";
    String porcentajeAvance = "0";
    String sumar = "0";
    boolean impartido=false;

    public boolean isImpartido() {
        return impartido;
    }

    public void setImpartido(boolean impartido) {
        this.impartido = impartido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(String id) {
        this.subtemaId = id;
    }

    public String getHoras() {
        return horas;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(String porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public NodoMultiClass() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Temaunidad getTemaUnidad() {
        return temaUnidad;
    }

    public void setTemaUnidad(Temaunidad temaUnidad) {
        this.temaUnidad = temaUnidad;
    }

    public Subtemaunidad getSubTema() {
        return subTema;
    }

    public void setSubTema(Subtemaunidad subTema) {
        this.subTema = subTema;
    }

    public NodoMultiClass(Object obj) {
        definirNodo(obj);
    }

    public NodoMultiClass(Object obj, int perteneceAUnidad) {
        this.unidadId = String.valueOf(perteneceAUnidad);
        definirNodo(obj);
    }

    public NodoMultiClass(Object obj, int perteneceAUnidad, int perteneceATema) {
        this.unidadId = String.valueOf(perteneceAUnidad);
        this.temaId = String.valueOf(perteneceATema);
        definirNodo(obj);
    }

    public void definirNodo(Object obj) {
        if (obj instanceof Unidad) {
            unidad = (Unidad) obj;
            nombre = unidad.getUNInombre();
            horas = String.valueOf(unidad.getUNIhoras());
            porcentajeAvance = String.valueOf(unidad.getUNIvalorPorcentaje());
            unidadId = String.valueOf(unidad.getUNIid());
            numero = String.valueOf(unidad.getUNInumero());
            sumar = String.valueOf(unidad.getUNIvalorPorcentaje());
            tipo = "unidad";
             id = String.valueOf(unidad.getUNIid());
             horasCompletas=unidad.getUNIhorasCompletas();
        }
        if (obj instanceof Temaunidad) {
            temaUnidad = (Temaunidad) obj;
            nombre = temaUnidad.getTUNnombre();
            horas = String.valueOf(temaUnidad.getTUNhoras());
            porcentajeAvance = String.valueOf(temaUnidad.getTUNvalorPorcentaje());
            temaId = String.valueOf(temaUnidad.getTUNid());
            numero = String.valueOf(temaUnidad.getTUNnumero());
            sumar = String.valueOf(temaUnidad.getTUNvalorPorcentaje());
             id = String.valueOf(temaUnidad.getTUNid());
            tipo = "temaunidad";
            horasCompletas=temaUnidad.getTUNhorasCompletas();
        }
        if (obj instanceof Subtemaunidad) {
            subTema = (Subtemaunidad) obj;
            nombre = subTema.getSUTnombre();
            horas = String.valueOf(subTema.getSUThoras());
            porcentajeAvance = String.valueOf(subTema.getSUTvalorPorcentaje());
            subtemaId = String.valueOf(subTema.getSUTid());
            numero = String.valueOf(subTema.getSUTnumero());
            sumar = String.valueOf(subTema.getSUTvalorPorcentaje());
             id = String.valueOf(subTema.getSUTid());
            tipo = "subtemaunidad";

        }
        if (obj instanceof Practicalaboratorio) {

            practicaL = (Practicalaboratorio) obj;
            nombre = practicaL.getPRLnombre();
            horas = String.valueOf(practicaL.getPRLhoras());
            porcentajeAvance = String.valueOf(practicaL.getPRLvalorPorcentaje());
            unidadId = String.valueOf(practicaL.getPRLid());
            numero = String.valueOf(practicaL.getPRLnumero());
            sumar = String.valueOf(practicaL.getPRLvalorPorcentaje());
            id = String.valueOf(practicaL.getPRLid());
            tipo = "practicalaboratorio";

        }
        if (obj instanceof Practicataller) {

            practicaT = (Practicataller) obj;
            nombre = practicaT.getPRTnombre();
            horas = String.valueOf(practicaT.getPRThoras());
            porcentajeAvance = String.valueOf(practicaT.getPRTvalorPorcentaje());
            unidadId = String.valueOf(practicaT.getPRTid());
            numero = String.valueOf(practicaT.getPRTnumero());
            sumar = String.valueOf(practicaT.getPRTvalorPorcentaje());
            id = String.valueOf(practicaT.getPRTid());
            tipo = "practicataller";

        }
           if (obj instanceof Practicascampo) {
            practicaC = (Practicascampo) obj;
            nombre = practicaC.getPRCnombre();
            horas = String.valueOf(practicaC.getPRChoras());
            porcentajeAvance = String.valueOf(practicaC.getPRCvalorPorcentaje());
            unidadId = String.valueOf(practicaC.getPRCid());
            numero = String.valueOf(practicaC.getPRCnumero());
            sumar = String.valueOf(practicaC.getPRCvalorPorcentaje());
            id = String.valueOf(practicaC.getPRCid());
            tipo = "practicaCampo";
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.unidad);
        hash = 37 * hash + Objects.hashCode(this.temaUnidad);
        hash = 37 * hash + Objects.hashCode(this.subTema);
        hash = 37 * hash + Objects.hashCode(this.nombre);
        hash = 37 * hash + Objects.hashCode(this.subtemaId);
        hash = 37 * hash + Objects.hashCode(this.numero);
        hash = 37 * hash + Objects.hashCode(this.horas);
        hash = 37 * hash + Objects.hashCode(this.unidadId);
        hash = 37 * hash + Objects.hashCode(this.temaId);
        hash = 37 * hash + Objects.hashCode(this.tipo);
        hash = 37 * hash + Objects.hashCode(this.practicaL);
        hash = 37 * hash + Objects.hashCode(this.practicaT);
        hash = 37 * hash + Objects.hashCode(this.practicaC);
        hash = 37 * hash + Objects.hashCode(this.observaciones);
        hash = 37 * hash + Objects.hashCode(this.porcentajeAvance);
        hash = 37 * hash + Objects.hashCode(this.sumar);
        hash = 37 * hash + (this.impartido ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodoMultiClass other = (NodoMultiClass) obj;
        if (!Objects.equals(this.unidad, other.unidad)) {
            return false;
        }
        if (!Objects.equals(this.temaUnidad, other.temaUnidad)) {
            return false;
        }
        if (!Objects.equals(this.subTema, other.subTema)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.subtemaId, other.subtemaId)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.horas, other.horas)) {
            return false;
        }
        if (!Objects.equals(this.unidadId, other.unidadId)) {
            return false;
        }
        if (!Objects.equals(this.temaId, other.temaId)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.practicaL, other.practicaL)) {
            return false;
        }
        if (!Objects.equals(this.practicaT, other.practicaT)) {
            return false;
        }
        if (!Objects.equals(this.practicaC, other.practicaC)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.porcentajeAvance, other.porcentajeAvance)) {
            return false;
        }
        if (!Objects.equals(this.sumar, other.sumar)) {
            return false;
        }
        if (this.impartido != other.impartido) {
            return false;
        }
        return true;
    }

   


 
    @Override
    public String toString() {
        if (observaciones.isEmpty() || observaciones == null) {
            observaciones = " ";
        }

        return tipo + "-//-" + unidadId + "-//-" + temaId + "-//-" + subtemaId + "-//-" + nombre + "-//-" + sumar + "-//-" + observaciones;
    }

    @Override
    public int compareTo(NodoMultiClass document) {
        return this.getNombre().compareTo(document.getNombre());
    }

}
