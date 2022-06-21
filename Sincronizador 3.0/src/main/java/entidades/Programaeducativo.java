
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
@Table(name = "programaeducativo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Programaeducativo.findAll", query = "SELECT p FROM Programaeducativo p")
        , @NamedQuery(name = "Programaeducativo.findByPEDid", query = "SELECT p FROM Programaeducativo p WHERE p.pEDid = :pEDid")
        , @NamedQuery(name = "Programaeducativo.findByPEDclave", query = "SELECT p FROM Programaeducativo p WHERE p.pEDclave = :pEDclave")
        , @NamedQuery(name = "Programaeducativo.findByPEDnombre", query = "SELECT p FROM Programaeducativo p WHERE p.pEDnombre = :pEDnombre")})
public class Programaeducativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PEDid")
    private Integer pEDid;
    @Basic(optional = false)
    @Column(name = "PEDclave")
    private int pEDclave;
    @Basic(optional = false)
    @Column(name = "PEDnombre")
    private String pEDnombre;
    @ManyToMany(mappedBy = "programaeducativoList")
    private List<Profesor> profesorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaEducativoPEDid")
    private List<Areaadministrativa> areaadministrativaList;
    @OneToMany(mappedBy = "programaEducativoPEDid")
    private List<Planestudio> planestudioList;
    @JoinColumn(name = "UnidadAcademica_UACid", referencedColumnName = "UACid")
    @ManyToOne
    private Unidadacademica unidadAcademicaUACid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaeducativo")
    private List<ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList;

    public Programaeducativo() {
    }

    public Programaeducativo(Integer pEDid) {
        this.pEDid = pEDid;
    }

    public Programaeducativo(Integer pEDid, int pEDclave, String pEDnombre) {
        this.pEDid = pEDid;
        this.pEDclave = pEDclave;
        this.pEDnombre = pEDnombre;
    }

    public Integer getPEDid() {
        return pEDid;
    }

    public void setPEDid(Integer pEDid) {
        this.pEDid = pEDid;
    }

    public int getPEDclave() {
        return pEDclave;
    }

    public void setPEDclave(int pEDclave) {
        this.pEDclave = pEDclave;
    }

    public String getPEDnombre() {
        return pEDnombre;
    }

    public void setPEDnombre(String pEDnombre) {
        this.pEDnombre = pEDnombre;
    }

    @XmlTransient
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @XmlTransient
    public List<Areaadministrativa> getAreaadministrativaList() {
        return areaadministrativaList;
    }

    public void setAreaadministrativaList(List<Areaadministrativa> areaadministrativaList) {
        this.areaadministrativaList = areaadministrativaList;
    }

    @XmlTransient
    public List<Planestudio> getPlanestudioList() {
        return planestudioList;
    }

    public void setPlanestudioList(List<Planestudio> planestudioList) {
        this.planestudioList = planestudioList;
    }

    public Unidadacademica getUnidadAcademicaUACid() {
        return unidadAcademicaUACid;
    }

    public void setUnidadAcademicaUACid(Unidadacademica unidadAcademicaUACid) {
        this.unidadAcademicaUACid = unidadAcademicaUACid;
    }

    @XmlTransient
    public List<ProfesorPerteneceProgramaeducativo> getProfesorPerteneceProgramaeducativoList() {
        return profesorPerteneceProgramaeducativoList;
    }

    public void setProfesorPerteneceProgramaeducativoList(List<ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList) {
        this.profesorPerteneceProgramaeducativoList = profesorPerteneceProgramaeducativoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pEDid != null ? pEDid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programaeducativo)) {
            return false;
        }
        Programaeducativo other = (Programaeducativo) object;
        if ((this.pEDid == null && other.pEDid != null) || (this.pEDid != null && !this.pEDid.equals(other.pEDid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Programaeducativo[ pEDid=" + pEDid + " ]";
    }

}
