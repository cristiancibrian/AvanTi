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
@Table(name = "practicasclinica")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Practicasclinica.findAll", query = "SELECT p FROM Practicasclinica p")
        , @NamedQuery(name = "Practicasclinica.findByPRCid", query = "SELECT p FROM Practicasclinica p WHERE p.pRCid = :pRCid")
        , @NamedQuery(name = "Practicasclinica.findByPRCnumero", query = "SELECT p FROM Practicasclinica p WHERE p.pRCnumero = :pRCnumero")
        , @NamedQuery(name = "Practicasclinica.findByPRCnombre", query = "SELECT p FROM Practicasclinica p WHERE p.pRCnombre = :pRCnombre")
        , @NamedQuery(name = "Practicasclinica.findByPRCvalorPorcentaje", query = "SELECT p FROM Practicasclinica p WHERE p.pRCvalorPorcentaje = :pRCvalorPorcentaje")
        , @NamedQuery(name = "Practicasclinica.findByPRChoras", query = "SELECT p FROM Practicasclinica p WHERE p.pRChoras = :pRChoras")})
public class Practicasclinica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRCid")
    private Integer pRCid;
    @Column(name = "PRCnumero")
    private Integer pRCnumero;
    @Column(name = "PRCnombre")
    private String pRCnombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRCvalorPorcentaje")
    private Float pRCvalorPorcentaje;
    @Column(name = "PRChoras")
    private Integer pRChoras;
    @JoinColumn(name = "ContenidoTematico_CTid", referencedColumnName = "CTid")
    @ManyToOne(optional = false)
    private UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid;

    public Practicasclinica() {
    }

    public Practicasclinica(Integer pRCid) {
        this.pRCid = pRCid;
    }

    public Integer getPRCid() {
        return pRCid;
    }

    public void setPRCid(Integer pRCid) {
        this.pRCid = pRCid;
    }

    public Integer getPRCnumero() {
        return pRCnumero;
    }

    public void setPRCnumero(Integer pRCnumero) {
        this.pRCnumero = pRCnumero;
    }

    public String getPRCnombre() {
        return pRCnombre;
    }

    public void setPRCnombre(String pRCnombre) {
        this.pRCnombre = pRCnombre;
    }

    public Float getPRCvalorPorcentaje() {
        return pRCvalorPorcentaje;
    }

    public void setPRCvalorPorcentaje(Float pRCvalorPorcentaje) {
        this.pRCvalorPorcentaje = pRCvalorPorcentaje;
    }

    public Integer getPRChoras() {
        return pRChoras;
    }

    public void setPRChoras(Integer pRChoras) {
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
        if (!(object instanceof Practicasclinica)) {
            return false;
        }
        Practicasclinica other = (Practicasclinica) object;
        if ((this.pRCid == null && other.pRCid != null) || (this.pRCid != null && !this.pRCid.equals(other.pRCid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Practicasclinica[ pRCid=" + pRCid + " ]";
    }

}
