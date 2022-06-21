/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Eduardo
 */
@Entity
@Table(name = "practicataller")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Practicataller.findAll", query = "SELECT p FROM Practicataller p")
        , @NamedQuery(name = "Practicataller.findByPRTid", query = "SELECT p FROM Practicataller p WHERE p.pRTid = :pRTid")
        , @NamedQuery(name = "Practicataller.findByPRTnumero", query = "SELECT p FROM Practicataller p WHERE p.pRTnumero = :pRTnumero")
        , @NamedQuery(name = "Practicataller.findByPRTnombre", query = "SELECT p FROM Practicataller p WHERE p.pRTnombre = :pRTnombre")
        , @NamedQuery(name = "Practicataller.findByPRTvalorPorcentaje", query = "SELECT p FROM Practicataller p WHERE p.pRTvalorPorcentaje = :pRTvalorPorcentaje")
        , @NamedQuery(name = "Practicataller.findByPRThoras", query = "SELECT p FROM Practicataller p WHERE p.pRThoras = :pRThoras")})
public class Practicataller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRTid")
    private Integer pRTid;
    @Basic(optional = false)
    @Column(name = "PRTnumero")
    private int pRTnumero;
    @Basic(optional = false)
    @Column(name = "PRTnombre")
    private String pRTnombre;
    @Basic(optional = false)
    @Column(name = "PRTvalorPorcentaje")
    private float pRTvalorPorcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRThoras")
    private Float pRThoras;
    @JoinColumn(name = "ContenidoTematico_CTid", referencedColumnName = "CTid")
    @ManyToOne(optional = false)
    private UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid;

    public Practicataller() {
    }

    public Practicataller(Integer pRTid) {
        this.pRTid = pRTid;
    }

    public Practicataller(Integer pRTid, int pRTnumero, String pRTnombre, float pRTvalorPorcentaje) {
        this.pRTid = pRTid;
        this.pRTnumero = pRTnumero;
        this.pRTnombre = pRTnombre;
        this.pRTvalorPorcentaje = pRTvalorPorcentaje;
    }

    public Integer getPRTid() {
        return pRTid;
    }

    public void setPRTid(Integer pRTid) {
        this.pRTid = pRTid;
    }

    public int getPRTnumero() {
        return pRTnumero;
    }

    public void setPRTnumero(int pRTnumero) {
        this.pRTnumero = pRTnumero;
    }

    public String getPRTnombre() {
        return pRTnombre;
    }

    public void setPRTnombre(String pRTnombre) {
        this.pRTnombre = pRTnombre;
    }

    public float getPRTvalorPorcentaje() {
        return pRTvalorPorcentaje;
    }

    public void setPRTvalorPorcentaje(float pRTvalorPorcentaje) {
        this.pRTvalorPorcentaje = pRTvalorPorcentaje;
    }

    public Float getPRThoras() {
        return pRThoras;
    }

    public void setPRThoras(Float pRThoras) {
        this.pRThoras = pRThoras;
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
        hash += (pRTid != null ? pRTid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Practicataller)) {
            return false;
        }
        Practicataller other = (Practicataller) object;
        if ((this.pRTid == null && other.pRTid != null) || (this.pRTid != null && !this.pRTid.equals(other.pRTid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Practicataller[ pRTid=" + pRTid + " ]";
    }

}
