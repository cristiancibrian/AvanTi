/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "campus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campus.findAll", query = "SELECT c FROM Campus c")
    , @NamedQuery(name = "Campus.findByCAMid", query = "SELECT c FROM Campus c WHERE c.cAMid = :cAMid")
    , @NamedQuery(name = "Campus.findByCAMnombre", query = "SELECT c FROM Campus c WHERE c.cAMnombre = :cAMnombre")})
public class Campus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CAMid")
    private Integer cAMid;
    @Basic(optional = false)
    @Column(name = "CAMnombre")
    private String cAMnombre;
    @OneToMany(mappedBy = "campusCAMid")
    private List<Unidadacademica> unidadacademicaList;

    public Campus() {
    }

    public Campus(Integer cAMid) {
        this.cAMid = cAMid;
    }

    public Campus(Integer cAMid, String cAMnombre) {
        this.cAMid = cAMid;
        this.cAMnombre = cAMnombre;
    }

    public Integer getCAMid() {
        return cAMid;
    }

    public void setCAMid(Integer cAMid) {
        this.cAMid = cAMid;
    }

    public String getCAMnombre() {
        return cAMnombre;
    }

    public void setCAMnombre(String cAMnombre) {
        this.cAMnombre = cAMnombre;
    }

    @XmlTransient
    public List<Unidadacademica> getUnidadacademicaList() {
        return unidadacademicaList;
    }

    public void setUnidadacademicaList(List<Unidadacademica> unidadacademicaList) {
        this.unidadacademicaList = unidadacademicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cAMid != null ? cAMid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campus)) {
            return false;
        }
        Campus other = (Campus) object;
        if ((this.cAMid == null && other.cAMid != null) || (this.cAMid != null && !this.cAMid.equals(other.cAMid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Campus[ cAMid=" + cAMid + " ]";
    }
    
}
