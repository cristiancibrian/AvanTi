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
@Table(name = "practicascampo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Practicascampo.findAll", query = "SELECT p FROM Practicascampo p")
        , @NamedQuery(name = "Practicascampo.findByPRCid", query = "SELECT p FROM Practicascampo p WHERE p.pRCid = :pRCid")
        , @NamedQuery(name = "Practicascampo.findByPRCnumero", query = "SELECT p FROM Practicascampo p WHERE p.pRCnumero = :pRCnumero")
        , @NamedQuery(name = "Practicascampo.findByPRCnombre", query = "SELECT p FROM Practicascampo p WHERE p.pRCnombre = :pRCnombre")
        , @NamedQuery(name = "Practicascampo.findByPRCvalorPorcentaje", query = "SELECT p FROM Practicascampo p WHERE p.pRCvalorPorcentaje = :pRCvalorPorcentaje")
        , @NamedQuery(name = "Practicascampo.findByPRChoras", query = "SELECT p FROM Practicascampo p WHERE p.pRChoras = :pRChoras")})
public class Practicascampo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRCid")
    private Integer pRCid;
    @Basic(optional = false)
    @Column(name = "PRCnumero")
    private int pRCnumero;
    @Basic(optional = false)
    @Column(name = "PRCnombre")
    private String pRCnombre;
    @Basic(optional = false)
    @Column(name = "PRCvalorPorcentaje")
    private float pRCvalorPorcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRChoras")
    private Float pRChoras;
    @JoinColumn(name = "ContenidoTematico_CTid", referencedColumnName = "CTid")
    @ManyToOne(optional = false)
    private UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid;

    public Practicascampo() {
    }

    public Practicascampo(Integer pRCid) {
        this.pRCid = pRCid;
    }

    public Practicascampo(Integer pRCid, int pRCnumero, String pRCnombre, float pRCvalorPorcentaje) {
        this.pRCid = pRCid;
        this.pRCnumero = pRCnumero;
        this.pRCnombre = pRCnombre;
        this.pRCvalorPorcentaje = pRCvalorPorcentaje;
    }

    public Integer getPRCid() {
        return pRCid;
    }

    public void setPRCid(Integer pRCid) {
        this.pRCid = pRCid;
    }

    public int getPRCnumero() {
        return pRCnumero;
    }

    public void setPRCnumero(int pRCnumero) {
        this.pRCnumero = pRCnumero;
    }

    public String getPRCnombre() {
        return pRCnombre;
    }

    public void setPRCnombre(String pRCnombre) {
        this.pRCnombre = pRCnombre;
    }

    public float getPRCvalorPorcentaje() {
        return pRCvalorPorcentaje;
    }

    public void setPRCvalorPorcentaje(float pRCvalorPorcentaje) {
        this.pRCvalorPorcentaje = pRCvalorPorcentaje;
    }

    public Float getPRChoras() {
        return pRChoras;
    }

    public void setPRChoras(Float pRChoras) {
        this.pRChoras = pRChoras;
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
        hash += (pRCid != null ? pRCid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Practicascampo)) {
            return false;
        }
        Practicascampo other = (Practicascampo) object;
        if ((this.pRCid == null && other.pRCid != null) || (this.pRCid != null && !this.pRCid.equals(other.pRCid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Practicascampo[ pRCid=" + pRCid + " ]";
    }

}
