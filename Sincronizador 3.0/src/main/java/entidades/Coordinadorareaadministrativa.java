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
@Table(name = "coordinadorareaadministrativa")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Coordinadorareaadministrativa.findAll", query = "SELECT c FROM Coordinadorareaadministrativa c")
        , @NamedQuery(name = "Coordinadorareaadministrativa.findByCOAid", query = "SELECT c FROM Coordinadorareaadministrativa c WHERE c.cOAid = :cOAid")})
public class Coordinadorareaadministrativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COAid")
    private Integer cOAid;
    @JoinTable(name = "areaadministrativa_tiene_unidadaprendizaje", joinColumns = {
            @JoinColumn(name = "CoordinadorAdministrativa_COAid", referencedColumnName = "COAid")}, inverseJoinColumns = {
            @JoinColumn(name = "UnidadAprendizaje_UAPid", referencedColumnName = "UAPid")})
    @ManyToMany
    private List<Unidadaprendizaje> unidadaprendizajeList;
    @JoinColumn(name = "AreaAdministrativa_AADid", referencedColumnName = "AADid")
    @ManyToOne(optional = false)
    private Areaadministrativa areaAdministrativaAADid;
    @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid")
    @ManyToOne(optional = false)
    private Profesor profesorPROid;

    public Coordinadorareaadministrativa() {
    }

    public Coordinadorareaadministrativa(Integer cOAid) {
        this.cOAid = cOAid;
    }

    public Integer getCOAid() {
        return cOAid;
    }

    public void setCOAid(Integer cOAid) {
        this.cOAid = cOAid;
    }

    @XmlTransient
    public List<Unidadaprendizaje> getUnidadaprendizajeList() {
        return unidadaprendizajeList;
    }

    public void setUnidadaprendizajeList(List<Unidadaprendizaje> unidadaprendizajeList) {
        this.unidadaprendizajeList = unidadaprendizajeList;
    }

    public Areaadministrativa getAreaAdministrativaAADid() {
        return areaAdministrativaAADid;
    }

    public void setAreaAdministrativaAADid(Areaadministrativa areaAdministrativaAADid) {
        this.areaAdministrativaAADid = areaAdministrativaAADid;
    }

    public Profesor getProfesorPROid() {
        return profesorPROid;
    }

    public void setProfesorPROid(Profesor profesorPROid) {
        this.profesorPROid = profesorPROid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cOAid != null ? cOAid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinadorareaadministrativa)) {
            return false;
        }
        Coordinadorareaadministrativa other = (Coordinadorareaadministrativa) object;
        if ((this.cOAid == null && other.cOAid != null) || (this.cOAid != null && !this.cOAid.equals(other.cOAid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Coordinadorareaadministrativa[ cOAid=" + cOAid + " ]";
    }

}
