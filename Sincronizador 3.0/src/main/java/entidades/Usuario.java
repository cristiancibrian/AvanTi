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
 * @author Oscar
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
        , @NamedQuery(name = "Usuario.findByUSUid", query = "SELECT u FROM Usuario u WHERE u.uSUid = :uSUid")
        , @NamedQuery(name = "Usuario.findByUSUusuario", query = "SELECT u FROM Usuario u WHERE u.uSUusuario = :uSUusuario")
        , @NamedQuery(name = "Usuario.findByUSUcontrasenia", query = "SELECT u FROM Usuario u WHERE u.uSUcontrasenia = :uSUcontrasenia")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USUid")
    private Integer uSUid;
    @Basic(optional = false)
    @Column(name = "USUusuario")
    private String uSUusuario;
    @Column(name = "USUcontrasenia")
    private String uSUcontrasenia;
    @JoinTable(name = "rol_tiene_usuario", joinColumns = {
            @JoinColumn(name = "Usuario_USUid", referencedColumnName = "USUid")}, inverseJoinColumns = {
            @JoinColumn(name = "Rol_ROLid", referencedColumnName = "ROLid")})
    @ManyToMany
    private List<Rol> rolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUSUid")
    private List<Catalogoreportes> catalogoreportesList;
    @OneToMany(mappedBy = "usuarioUSUid")
    private List<Profesor> profesorList;

    public Usuario() {
    }

    public Usuario(Integer uSUid) {
        this.uSUid = uSUid;
    }

    public Usuario(Integer uSUid, String uSUusuario) {
        this.uSUid = uSUid;
        this.uSUusuario = uSUusuario;
    }

    public Integer getUSUid() {
        return uSUid;
    }

    public void setUSUid(Integer uSUid) {
        this.uSUid = uSUid;
    }

    public String getUSUusuario() {
        return uSUusuario;
    }

    public void setUSUusuario(String uSUusuario) {
        this.uSUusuario = uSUusuario;
    }

    public String getUSUcontrasenia() {
        return uSUcontrasenia;
    }

    public void setUSUcontrasenia(String uSUcontrasenia) {
        this.uSUcontrasenia = uSUcontrasenia;
    }

    @XmlTransient
    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    @XmlTransient
    public List<Catalogoreportes> getCatalogoreportesList() {
        return catalogoreportesList;
    }

    public void setCatalogoreportesList(List<Catalogoreportes> catalogoreportesList) {
        this.catalogoreportesList = catalogoreportesList;
    }

    @XmlTransient
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uSUid != null ? uSUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.uSUid == null && other.uSUid != null) || (this.uSUid != null && !this.uSUid.equals(other.uSUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Usuario[ uSUid=" + uSUid + " ]";
    }

}
