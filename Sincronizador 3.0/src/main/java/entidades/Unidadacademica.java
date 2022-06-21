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
@Table(name = "unidadacademica")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Unidadacademica.findAll", query = "SELECT u FROM Unidadacademica u")
        , @NamedQuery(name = "Unidadacademica.findByUACid", query = "SELECT u FROM Unidadacademica u WHERE u.uACid = :uACid")
        , @NamedQuery(name = "Unidadacademica.findByUACclave", query = "SELECT u FROM Unidadacademica u WHERE u.uACclave = :uACclave")
        , @NamedQuery(name = "Unidadacademica.findByUACnombre", query = "SELECT u FROM Unidadacademica u WHERE u.uACnombre = :uACnombre")
        , @NamedQuery(name = "Unidadacademica.findByUACtipo", query = "SELECT u FROM Unidadacademica u WHERE u.uACtipo = :uACtipo")})
public class Unidadacademica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UACid")
    private Integer uACid;
    @Basic(optional = false)
    @Column(name = "UACclave")
    private int uACclave;
    @Basic(optional = false)
    @Column(name = "UACnombre")
    private String uACnombre;
    @Column(name = "UACtipo")
    private String uACtipo;
    @ManyToMany(mappedBy = "unidadacademicaList")
    private List<Profesor> profesorList;
    @OneToMany(mappedBy = "unidadAcademicaUACid")
    private List<Programaeducativo> programaeducativoList;
    @JoinColumn(name = "Campus_CAMid", referencedColumnName = "CAMid")
    @ManyToOne
    private Campus campusCAMid;

    public Unidadacademica() {
    }

    public Unidadacademica(Integer uACid) {
        this.uACid = uACid;
    }

    public Unidadacademica(Integer uACid, int uACclave, String uACnombre) {
        this.uACid = uACid;
        this.uACclave = uACclave;
        this.uACnombre = uACnombre;
    }

    public Integer getUACid() {
        return uACid;
    }

    public void setUACid(Integer uACid) {
        this.uACid = uACid;
    }

    public int getUACclave() {
        return uACclave;
    }

    public void setUACclave(int uACclave) {
        this.uACclave = uACclave;
    }

    public String getUACnombre() {
        return uACnombre;
    }

    public void setUACnombre(String uACnombre) {
        this.uACnombre = uACnombre;
    }

    public String getUACtipo() {
        return uACtipo;
    }

    public void setUACtipo(String uACtipo) {
        this.uACtipo = uACtipo;
    }

    @XmlTransient
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @XmlTransient
    public List<Programaeducativo> getProgramaeducativoList() {
        return programaeducativoList;
    }

    public void setProgramaeducativoList(List<Programaeducativo> programaeducativoList) {
        this.programaeducativoList = programaeducativoList;
    }

    public Campus getCampusCAMid() {
        return campusCAMid;
    }

    public void setCampusCAMid(Campus campusCAMid) {
        this.campusCAMid = campusCAMid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uACid != null ? uACid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidadacademica)) {
            return false;
        }
        Unidadacademica other = (Unidadacademica) object;
        if ((this.uACid == null && other.uACid != null) || (this.uACid != null && !this.uACid.equals(other.uACid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Unidadacademica[ uACid=" + uACid + " ]";
    }

}
