/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "configuracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
    , @NamedQuery(name = "Configuracion.findByCONid", query = "SELECT c FROM Configuracion c WHERE c.cONid = :cONid")
    , @NamedQuery(name = "Configuracion.findByCONfechaInicioSemestre", query = "SELECT c FROM Configuracion c WHERE c.cONfechaInicioSemestre = :cONfechaInicioSemestre")
    , @NamedQuery(name = "Configuracion.findByCONnumeroSemanas", query = "SELECT c FROM Configuracion c WHERE c.cONnumeroSemanas = :cONnumeroSemanas")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONid")
    private Integer cONid;
    @Basic(optional = false)
    @Column(name = "CONfechaInicioSemestre")
    private Date cONfechaInicioSemestre;
    @Basic(optional = false)
    @Column(name = "CONnumeroSemanas")
    private int cONnumeroSemanas;
    @ManyToMany(mappedBy = "configuracionList")
    private List<Calendarioreporte> calendarioreporteList;
    @JoinColumn(name = "Alerta_ALEid", referencedColumnName = "ALEid")
    @ManyToOne
    private Alerta alertaALEid;
    @JoinColumn(name = "CicloEscolar_CESid", referencedColumnName = "CESid")
    @ManyToOne
    private Cicloescolar cicloEscolarCESid;

    public Configuracion() {
    }

    public Configuracion(Integer cONid) {
        this.cONid = cONid;
    }

    public Configuracion(Integer cONid, Date cONfechaInicioSemestre, int cONnumeroSemanas) {
        this.cONid = cONid;
        this.cONfechaInicioSemestre = cONfechaInicioSemestre;
        this.cONnumeroSemanas = cONnumeroSemanas;
    }

    public Integer getCONid() {
        return cONid;
    }

    public void setCONid(Integer cONid) {
        this.cONid = cONid;
    }

    public Date getCONfechaInicioSemestre() {
        return cONfechaInicioSemestre;
    }

    public void setCONfechaInicioSemestre(Date cONfechaInicioSemestre) {
        this.cONfechaInicioSemestre = cONfechaInicioSemestre;
    }

    public int getCONnumeroSemanas() {
        return cONnumeroSemanas;
    }

    public void setCONnumeroSemanas(int cONnumeroSemanas) {
        this.cONnumeroSemanas = cONnumeroSemanas;
    }

    @XmlTransient
    public List<Calendarioreporte> getCalendarioreporteList() {
        return calendarioreporteList;
    }

    public void setCalendarioreporteList(List<Calendarioreporte> calendarioreporteList) {
        this.calendarioreporteList = calendarioreporteList;
    }

    public Alerta getAlertaALEid() {
        return alertaALEid;
    }

    public void setAlertaALEid(Alerta alertaALEid) {
        this.alertaALEid = alertaALEid;
    }

    public Cicloescolar getCicloEscolarCESid() {
        return cicloEscolarCESid;
    }

    public void setCicloEscolarCESid(Cicloescolar cicloEscolarCESid) {
        this.cicloEscolarCESid = cicloEscolarCESid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cONid != null ? cONid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.cONid == null && other.cONid != null) || (this.cONid != null && !this.cONid.equals(other.cONid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Configuracion[ cONid=" + cONid + " ]";
    }
    
    public boolean isVacio(){
        if(cONid == null){
            return true;
        } else{
            return false;
        }
    }
    
}
