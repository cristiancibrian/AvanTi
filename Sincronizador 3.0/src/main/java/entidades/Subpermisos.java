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
@Table(name = "subpermisos")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Subpermisos.findAll", query = "SELECT s FROM Subpermisos s")
        , @NamedQuery(name = "Subpermisos.findBySPERid", query = "SELECT s FROM Subpermisos s WHERE s.sPERid = :sPERid")
        , @NamedQuery(name = "Subpermisos.findBySPERtipo", query = "SELECT s FROM Subpermisos s WHERE s.sPERtipo = :sPERtipo")
        , @NamedQuery(name = "Subpermisos.findBySPERvalor", query = "SELECT s FROM Subpermisos s WHERE s.sPERvalor = :sPERvalor")})
public class Subpermisos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SPERid")
    private Integer sPERid;
    @Basic(optional = false)
    @Column(name = "SPERtipo")
    private String sPERtipo;
    @Basic(optional = false)
    @Column(name = "SPERvalor")
    private int sPERvalor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subpermisos")
    private List<RolHasPermiso> rolHasPermisoList;

    public Subpermisos() {
    }

    public Subpermisos(Integer sPERid) {
        this.sPERid = sPERid;
    }

    public Subpermisos(Integer sPERid, String sPERtipo, int sPERvalor) {
        this.sPERid = sPERid;
        this.sPERtipo = sPERtipo;
        this.sPERvalor = sPERvalor;
    }

    public Integer getSPERid() {
        return sPERid;
    }

    public void setSPERid(Integer sPERid) {
        this.sPERid = sPERid;
    }

    public String getSPERtipo() {
        return sPERtipo;
    }

    public void setSPERtipo(String sPERtipo) {
        this.sPERtipo = sPERtipo;
    }

    public int getSPERvalor() {
        return sPERvalor;
    }

    public void setSPERvalor(int sPERvalor) {
        this.sPERvalor = sPERvalor;
    }

    @XmlTransient
    public List<RolHasPermiso> getRolHasPermisoList() {
        return rolHasPermisoList;
    }

    public void setRolHasPermisoList(List<RolHasPermiso> rolHasPermisoList) {
        this.rolHasPermisoList = rolHasPermisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sPERid != null ? sPERid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subpermisos)) {
            return false;
        }
        Subpermisos other = (Subpermisos) object;
        if ((this.sPERid == null && other.sPERid != null) || (this.sPERid != null && !this.sPERid.equals(other.sPERid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Subpermisos[ sPERid=" + sPERid + " ]";
    }

}
