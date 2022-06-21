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
@Table(name = "rol_has_permiso")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "RolHasPermiso.findAll", query = "SELECT r FROM RolHasPermiso r")
        , @NamedQuery(name = "RolHasPermiso.findByRolROLid", query = "SELECT r FROM RolHasPermiso r WHERE r.rolHasPermisoPK.rolROLid = :rolROLid")
        , @NamedQuery(name = "RolHasPermiso.findByPermisoPERid", query = "SELECT r FROM RolHasPermiso r WHERE r.rolHasPermisoPK.permisoPERid = :permisoPERid")
        , @NamedQuery(name = "RolHasPermiso.findBySubpermisosSPERid", query = "SELECT r FROM RolHasPermiso r WHERE r.rolHasPermisoPK.subpermisosSPERid = :subpermisosSPERid")})
public class RolHasPermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolHasPermisoPK rolHasPermisoPK;
    @JoinColumn(name = "Permiso_PERid", referencedColumnName = "PERid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Permiso permiso;
    @JoinColumn(name = "Rol_ROLid", referencedColumnName = "ROLid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "Subpermisos_SPERid", referencedColumnName = "SPERid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Subpermisos subpermisos;

    public RolHasPermiso() {
    }

    public RolHasPermiso(RolHasPermisoPK rolHasPermisoPK) {
        this.rolHasPermisoPK = rolHasPermisoPK;
    }

    public RolHasPermiso(int rolROLid, int permisoPERid, int subpermisosSPERid) {
        this.rolHasPermisoPK = new RolHasPermisoPK(rolROLid, permisoPERid, subpermisosSPERid);
    }

    public RolHasPermisoPK getRolHasPermisoPK() {
        return rolHasPermisoPK;
    }

    public void setRolHasPermisoPK(RolHasPermisoPK rolHasPermisoPK) {
        this.rolHasPermisoPK = rolHasPermisoPK;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Subpermisos getSubpermisos() {
        return subpermisos;
    }

    public void setSubpermisos(Subpermisos subpermisos) {
        this.subpermisos = subpermisos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolHasPermisoPK != null ? rolHasPermisoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolHasPermiso)) {
            return false;
        }
        RolHasPermiso other = (RolHasPermiso) object;
        if ((this.rolHasPermisoPK == null && other.rolHasPermisoPK != null) || (this.rolHasPermisoPK != null && !this.rolHasPermisoPK.equals(other.rolHasPermisoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.RolHasPermiso[ rolHasPermisoPK=" + rolHasPermisoPK + " ]";
    }

}
