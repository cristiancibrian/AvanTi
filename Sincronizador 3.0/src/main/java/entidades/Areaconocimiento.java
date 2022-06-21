/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * @author Eduardo
 */
@Entity
@Table(name = "areaconocimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Areaconocimiento.findAll", query = "SELECT a FROM Areaconocimiento a")
    , @NamedQuery(name = "Areaconocimiento.findByACOid", query = "SELECT a FROM Areaconocimiento a WHERE a.aCOid = :aCOid")
    , @NamedQuery(name = "Areaconocimiento.findByACOclave", query = "SELECT a FROM Areaconocimiento a WHERE a.aCOclave = :aCOclave")
    , @NamedQuery(name = "Areaconocimiento.findByACOnombre", query = "SELECT a FROM Areaconocimiento a WHERE a.aCOnombre = :aCOnombre")})
public class Areaconocimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ACOid")
    private Integer aCOid;
    @Basic(optional = false)
    @Column(name = "ACOclave")
    private int aCOclave;
    @Column(name = "ACOnombre")
    private String aCOnombre;
    @JoinTable(name = "coordinadorarea", joinColumns = {
        @JoinColumn(name = "AreaConocimiento_ACOid", referencedColumnName = "ACOid")}, inverseJoinColumns = {
        @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid")})
    @ManyToMany
    private List<Profesor> profesorList;
    @ManyToMany(mappedBy = "areaconocimientoList")
    private List<Unidadaprendizaje> unidadaprendizajeList;
    @JoinColumn(name = "PlanEstudio_PESid", referencedColumnName = "PESid")
    @ManyToOne
    private Planestudio planEstudioPESid;

    public Areaconocimiento() {
    }

    public Areaconocimiento(Integer aCOid) {
        this.aCOid = aCOid;
    }

    public Areaconocimiento(Integer aCOid, int aCOclave) {
        this.aCOid = aCOid;
        this.aCOclave = aCOclave;
    }

    public Integer getACOid() {
        return aCOid;
    }

    public void setACOid(Integer aCOid) {
        this.aCOid = aCOid;
    }

    public int getACOclave() {
        return aCOclave;
    }

    public void setACOclave(int aCOclave) {
        this.aCOclave = aCOclave;
    }

    public String getACOnombre() {
        return aCOnombre;
    }

    public void setACOnombre(String aCOnombre) {
        this.aCOnombre = aCOnombre;
    }

    @XmlTransient
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @XmlTransient
    public List<Unidadaprendizaje> getUnidadaprendizajeList() {
        return unidadaprendizajeList;
    }

    public void setUnidadaprendizajeList(List<Unidadaprendizaje> unidadaprendizajeList) {
        this.unidadaprendizajeList = unidadaprendizajeList;
    }

    public Planestudio getPlanEstudioPESid() {
        return planEstudioPESid;
    }

    public void setPlanEstudioPESid(Planestudio planEstudioPESid) {
        this.planEstudioPESid = planEstudioPESid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aCOid != null ? aCOid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Areaconocimiento)) {
            return false;
        }
        Areaconocimiento other = (Areaconocimiento) object;
        if ((this.aCOid == null && other.aCOid != null) || (this.aCOid != null && !this.aCOid.equals(other.aCOid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Areaconocimiento[ aCOid=" + aCOid + " ]";
    }

}
