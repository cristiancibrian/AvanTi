/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByGPOid", query = "SELECT g FROM Grupo g WHERE g.gPOid = :gPOid")
    , @NamedQuery(name = "Grupo.findByGPOnumero", query = "SELECT g FROM Grupo g WHERE g.gPOnumero = :gPOnumero")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GPOid")
    private Integer gPOid;
    @Basic(optional = false)
    @Column(name = "GPOnumero")
    private int gPOnumero;
    @JoinColumn(name = "PlanEstudio_PESid", referencedColumnName = "PESid")
    @ManyToOne
    private Planestudio planEstudioPESid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoGPOid")
    private List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;

    public Grupo() {
    }

    public Grupo(Integer gPOid) {
        this.gPOid = gPOid;
    }

    public Grupo(Integer gPOid, int gPOnumero) {
        this.gPOid = gPOid;
        this.gPOnumero = gPOnumero;
    }

    public Integer getGPOid() {
        return gPOid;
    }

    public void setGPOid(Integer gPOid) {
        this.gPOid = gPOid;
    }

    public int getGPOnumero() {
        return gPOnumero;
    }

    public void setGPOnumero(int gPOnumero) {
        this.gPOnumero = gPOnumero;
    }

    public Planestudio getPlanEstudioPESid() {
        return planEstudioPESid;
    }

    public void setPlanEstudioPESid(Planestudio planEstudioPESid) {
        this.planEstudioPESid = planEstudioPESid;
    }

    @XmlTransient
    public List<UnidadaprendizajeImparteProfesor> getUnidadaprendizajeImparteProfesorList() {
        return unidadaprendizajeImparteProfesorList;
    }

    public void setUnidadaprendizajeImparteProfesorList(List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList) {
        this.unidadaprendizajeImparteProfesorList = unidadaprendizajeImparteProfesorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gPOid != null ? gPOid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.gPOid == null && other.gPOid != null) || (this.gPOid != null && !this.gPOid.equals(other.gPOid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Grupo[ gPOid=" + gPOid + " ]";
    }
    
}
