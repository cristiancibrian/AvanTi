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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")
    , @NamedQuery(name = "Rol.findByROLid", query = "SELECT r FROM Rol r WHERE r.rOLid = :rOLid")
    , @NamedQuery(name = "Rol.findByROLtipo", query = "SELECT r FROM Rol r WHERE r.rOLtipo = :rOLtipo")
    , @NamedQuery(name = "Rol.findByROLprioridad", query = "SELECT r FROM Rol r WHERE r.rOLprioridad = :rOLprioridad")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLid")
    private Integer rOLid;
    @Basic(optional = false)
    @Column(name = "ROLtipo")
    private String rOLtipo;
    @Column(name = "ROLprioridad")
    private Integer rOLprioridad;
    @JoinTable(name = "rol_tiene_usuario", joinColumns = {
        @JoinColumn(name = "Rol_ROLid", referencedColumnName = "ROLid")}, inverseJoinColumns = {
        @JoinColumn(name = "Usuario_USUid", referencedColumnName = "USUid")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private List<RolHasPermiso> rolHasPermisoList;

    public Rol() {
    }

    public Rol(Integer rOLid) {
        this.rOLid = rOLid;
    }

    public Rol(Integer rOLid, String rOLtipo) {
        this.rOLid = rOLid;
        this.rOLtipo = rOLtipo;
    }

    public Integer getROLid() {
        return rOLid;
    }

    public void setROLid(Integer rOLid) {
        this.rOLid = rOLid;
    }

    public String getROLtipo() {
        return rOLtipo;
    }

    public void setROLtipo(String rOLtipo) {
        this.rOLtipo = rOLtipo;
    }

    public Integer getROLprioridad() {
        return rOLprioridad;
    }

    public void setROLprioridad(Integer rOLprioridad) {
        this.rOLprioridad = rOLprioridad;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        hash += (rOLid != null ? rOLid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.rOLid == null && other.rOLid != null) || (this.rOLid != null && !this.rOLid.equals(other.rOLid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Rol[ rOLid=" + rOLid + " ]";
    }
    
}
