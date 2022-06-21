/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Eduardo
 */
@Embeddable
public class RolHasPermisoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Rol_ROLid")
    private int rolROLid;
    @Basic(optional = false)
    @Column(name = "Permiso_PERid")
    private int permisoPERid;
    @Basic(optional = false)
    @Column(name = "Subpermisos_SPERid")
    private int subpermisosSPERid;

    public RolHasPermisoPK() {
    }

    public RolHasPermisoPK(int rolROLid, int permisoPERid, int subpermisosSPERid) {
        this.rolROLid = rolROLid;
        this.permisoPERid = permisoPERid;
        this.subpermisosSPERid = subpermisosSPERid;
    }

    public int getRolROLid() {
        return rolROLid;
    }

    public void setRolROLid(int rolROLid) {
        this.rolROLid = rolROLid;
    }

    public int getPermisoPERid() {
        return permisoPERid;
    }

    public void setPermisoPERid(int permisoPERid) {
        this.permisoPERid = permisoPERid;
    }

    public int getSubpermisosSPERid() {
        return subpermisosSPERid;
    }

    public void setSubpermisosSPERid(int subpermisosSPERid) {
        this.subpermisosSPERid = subpermisosSPERid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rolROLid;
        hash += (int) permisoPERid;
        hash += (int) subpermisosSPERid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolHasPermisoPK)) {
            return false;
        }
        RolHasPermisoPK other = (RolHasPermisoPK) object;
        if (this.rolROLid != other.rolROLid) {
            return false;
        }
        if (this.permisoPERid != other.permisoPERid) {
            return false;
        }
        if (this.subpermisosSPERid != other.subpermisosSPERid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.RolHasPermisoPK[ rolROLid=" + rolROLid + ", permisoPERid=" + permisoPERid + ", subpermisosSPERid=" + subpermisosSPERid + " ]";
    }
    
}
