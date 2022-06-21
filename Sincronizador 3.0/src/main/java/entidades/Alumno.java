/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hector
 */
@Entity
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")
    , @NamedQuery(name = "Alumno.findByALId", query = "SELECT a FROM Alumno a WHERE a.aLId = :aLId")
    , @NamedQuery(name = "Alumno.findByALNombre", query = "SELECT a FROM Alumno a WHERE a.aLNombre = :aLNombre")
    , @NamedQuery(name = "Alumno.findByALApellidoPater", query = "SELECT a FROM Alumno a WHERE a.aLApellidoPater = :aLApellidoPater")
    , @NamedQuery(name = "Alumno.findByALApellidoMaterno", query = "SELECT a FROM Alumno a WHERE a.aLApellidoMaterno = :aLApellidoMaterno")
    , @NamedQuery(name = "Alumno.findByALMatricula", query = "SELECT a FROM Alumno a WHERE a.aLMatricula = :aLMatricula")
    , @NamedQuery(name = "Alumno.findByALCorreo", query = "SELECT a FROM Alumno a WHERE a.aLCorreo = :aLCorreo")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ALId")
    private Integer aLId;
    @Column(name = "ALNombre")
    private String aLNombre;
    @Column(name = "ALApellidoPater")
    private String aLApellidoPater;
    @Column(name = "ALApellidoMaterno")
    private String aLApellidoMaterno;
    @Column(name = "ALMatricula")
    private String aLMatricula;
    @Column(name = "ALCorreo")
    private String aLCorreo;
    @OneToMany(mappedBy = "alumnoALId")
    @JoinColumn(name = "Usuario_USUid", referencedColumnName = "USUid")
    @ManyToOne
    private Usuario usuarioUSUid;
      @OneToMany(mappedBy = "alumnoALId")
    private List<AlumnoPerteneceGrupo> alumnoPerteneceGrupoList;

    public Alumno() {
    }

    public Alumno(Integer aLId) {
        this.aLId = aLId;
    }

    public Integer getALId() {
        return aLId;
    }

    public void setALId(Integer aLId) {
        this.aLId = aLId;
    }

    public String getALNombre() {
        return aLNombre;
    }

    public void setALNombre(String aLNombre) {
        this.aLNombre = aLNombre;
    }

    public String getALApellidoPater() {
        return aLApellidoPater;
    }

    public void setALApellidoPater(String aLApellidoPater) {
        this.aLApellidoPater = aLApellidoPater;
    }

    public String getALApellidoMaterno() {
        return aLApellidoMaterno;
    }

    public void setALApellidoMaterno(String aLApellidoMaterno) {
        this.aLApellidoMaterno = aLApellidoMaterno;
    }

    public String getALMatricula() {
        return aLMatricula;
    }

    public void setALMatricula(String aLMatricula) {
        this.aLMatricula = aLMatricula;
    }

    public String getALCorreo() {
        return aLCorreo;
    }

    public void setALCorreo(String aLCorreo) {
        this.aLCorreo = aLCorreo;
    }
    
    public Usuario getUsuarioUSUid() {
        return usuarioUSUid;
    }

    public void setUsuarioUSUid(Usuario usuarioUSUid) {
        this.usuarioUSUid = usuarioUSUid;
    }

    @XmlTransient
    public List<AlumnoPerteneceGrupo> getAlumnoPerteneceGrupoList() {
        return alumnoPerteneceGrupoList;
    }

    public void setAlumnoPerteneceGrupoList(List<AlumnoPerteneceGrupo> alumnoPerteneceGrupoList) {
        this.alumnoPerteneceGrupoList = alumnoPerteneceGrupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aLId != null ? aLId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.aLId == null && other.aLId != null) || (this.aLId != null && !this.aLId.equals(other.aLId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Alumno[ aLId=" + aLId + " ]";
    }
    
}
