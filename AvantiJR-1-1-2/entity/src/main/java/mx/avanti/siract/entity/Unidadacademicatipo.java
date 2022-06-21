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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "unidadacademicatipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidadacademicatipo.findAll", query = "SELECT u FROM Unidadacademicatipo u")
    , @NamedQuery(name = "Unidadacademicatipo.findByUACTid", query = "SELECT u FROM Unidadacademicatipo u WHERE u.uACTid = :uACTid")
    , @NamedQuery(name = "Unidadacademicatipo.findByUACTtipo", query = "SELECT u FROM Unidadacademicatipo u WHERE u.uACTtipo = :uACTtipo")})
public class Unidadacademicatipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UACTid")
    private Integer uACTid;
    @Basic(optional = false)
    @Column(name = "UACTtipo")
    private String uACTtipo;

    public Unidadacademicatipo() {
    }

    public Unidadacademicatipo(Integer uACTid) {
        this.uACTid = uACTid;
    }

    public Unidadacademicatipo(Integer uACTid, String uACTtipo) {
        this.uACTid = uACTid;
        this.uACTtipo = uACTtipo;
    }

    public Integer getUACTid() {
        return uACTid;
    }

    public void setUACTid(Integer uACTid) {
        this.uACTid = uACTid;
    }

    public String getUACTtipo() {
        return uACTtipo;
    }

    public void setUACTtipo(String uACTtipo) {
        this.uACTtipo = uACTtipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uACTid != null ? uACTid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidadacademicatipo)) {
            return false;
        }
        Unidadacademicatipo other = (Unidadacademicatipo) object;
        if ((this.uACTid == null && other.uACTid != null) || (this.uACTid != null && !this.uACTid.equals(other.uACTid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Unidadacademicatipo[ uACTid=" + uACTid + " ]";
    }
    
}
