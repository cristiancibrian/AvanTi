/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "practicalaboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Practicalaboratorio.findAll", query = "SELECT p FROM Practicalaboratorio p")
    , @NamedQuery(name = "Practicalaboratorio.findByPRLid", query = "SELECT p FROM Practicalaboratorio p WHERE p.pRLid = :pRLid")
    , @NamedQuery(name = "Practicalaboratorio.findByPRLnumero", query = "SELECT p FROM Practicalaboratorio p WHERE p.pRLnumero = :pRLnumero")
    , @NamedQuery(name = "Practicalaboratorio.findByPRLnombre", query = "SELECT p FROM Practicalaboratorio p WHERE p.pRLnombre = :pRLnombre")
    , @NamedQuery(name = "Practicalaboratorio.findByPRLvalorPorcentaje", query = "SELECT p FROM Practicalaboratorio p WHERE p.pRLvalorPorcentaje = :pRLvalorPorcentaje")
    , @NamedQuery(name = "Practicalaboratorio.findByPRLhoras", query = "SELECT p FROM Practicalaboratorio p WHERE p.pRLhoras = :pRLhoras")})
public class Practicalaboratorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRLid")
    private Integer pRLid;
    @Basic(optional = false)
    @Column(name = "PRLnumero")
    private int pRLnumero;
    @Basic(optional = false)
    @Column(name = "PRLnombre")
    private String pRLnombre;
    @Basic(optional = false)
    @Column(name = "PRLvalorPorcentaje")
    private float pRLvalorPorcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRLhoras")
    private Float pRLhoras;
    @JoinColumn(name = "ContenidoTematico_CTid", referencedColumnName = "CTid")
    @ManyToOne(optional = false)
    private UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid;

    public Practicalaboratorio() {
    }

    public Practicalaboratorio(Integer pRLid) {
        this.pRLid = pRLid;
    }

    public Practicalaboratorio(Integer pRLid, int pRLnumero, String pRLnombre, float pRLvalorPorcentaje) {
        this.pRLid = pRLid;
        this.pRLnumero = pRLnumero;
        this.pRLnombre = pRLnombre;
        this.pRLvalorPorcentaje = pRLvalorPorcentaje;
    }

    public Integer getPRLid() {
        return pRLid;
    }

    public void setPRLid(Integer pRLid) {
        this.pRLid = pRLid;
    }

    public int getPRLnumero() {
        return pRLnumero;
    }

    public void setPRLnumero(int pRLnumero) {
        this.pRLnumero = pRLnumero;
    }

    public String getPRLnombre() {
        return pRLnombre;
    }

    public void setPRLnombre(String pRLnombre) {
        this.pRLnombre = pRLnombre;
    }

    public float getPRLvalorPorcentaje() {
        return pRLvalorPorcentaje;
    }

    public void setPRLvalorPorcentaje(float pRLvalorPorcentaje) {
        this.pRLvalorPorcentaje = pRLvalorPorcentaje;
    }

    public Float getPRLhoras() {
        return pRLhoras;
    }

    public void setPRLhoras(Float pRLhoras) {
        this.pRLhoras = pRLhoras;
    }

    public UnidadaprendizajeTieneContenidotematico getContenidoTematicoCTid() {
        return contenidoTematicoCTid;
    }

    public void setContenidoTematicoCTid(UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid) {
        this.contenidoTematicoCTid = contenidoTematicoCTid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pRLid != null ? pRLid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Practicalaboratorio)) {
            return false;
        }
        Practicalaboratorio other = (Practicalaboratorio) object;
        if ((this.pRLid == null && other.pRLid != null) || (this.pRLid != null && !this.pRLid.equals(other.pRLid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Practicalaboratorio[ pRLid=" + pRLid + " ]";
    }
    
}
