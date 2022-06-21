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
 * @author Oscar
 */
@Entity
@Table(name = "unidadaprendizaje_tiene_contenidotematico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findAll", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u")
    , @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findByCTid", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u WHERE u.cTid = :cTid")
    , @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findByVersion", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u WHERE u.version = :version")
    , @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findByUAPhorasClaseCompletas", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u WHERE u.uAPhorasClaseCompletas = :uAPhorasClaseCompletas")
    , @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findByUAPhorasLaboratorioCompletas", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u WHERE u.uAPhorasLaboratorioCompletas = :uAPhorasLaboratorioCompletas")
    , @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findByUAPhorasTallerCompletas", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u WHERE u.uAPhorasTallerCompletas = :uAPhorasTallerCompletas")
    , @NamedQuery(name = "UnidadaprendizajeTieneContenidotematico.findByUAPhorasCampoCompletas", query = "SELECT u FROM UnidadaprendizajeTieneContenidotematico u WHERE u.uAPhorasCampoCompletas = :uAPhorasCampoCompletas")})
public class UnidadaprendizajeTieneContenidotematico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CTid")
    private Integer cTid;
    @Column(name = "version")
    private Integer version;
    @Column(name = "UAPhorasClaseCompletas")
    private Boolean uAPhorasClaseCompletas;
    @Column(name = "UAPhorasLaboratorioCompletas")
    private Boolean uAPhorasLaboratorioCompletas;
    @Column(name = "UAPhorasTallerCompletas")
    private Boolean uAPhorasTallerCompletas;
    @Column(name = "UAPhorasCampoCompletas")
    private Boolean uAPhorasCampoCompletas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidoTematicoCTid")
    private List<Practicasclinica> practicasclinicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidoTematicoCTid")
    private List<Practicataller> practicatallerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidoTematicoCTid")
    private List<Practicascampo> practicascampoList;
    @JoinColumn(name = "CicloEscolar_CESid", referencedColumnName = "CESid")
    @ManyToOne
    private Cicloescolar cicloEscolarCESid;
    @JoinColumn(name = "UnidadAprendizaje_UAPid", referencedColumnName = "UAPid")
    @ManyToOne
    private Unidadaprendizaje unidadAprendizajeUAPid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidoTematicoCTid")
    private List<Unidad> unidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidoTematicoCTid")
    private List<Practicalaboratorio> practicalaboratorioList;

    public UnidadaprendizajeTieneContenidotematico() {
    }

    public UnidadaprendizajeTieneContenidotematico(Integer cTid) {
        this.cTid = cTid;
    }

    public Integer getCTid() {
        return cTid;
    }

    public void setCTid(Integer cTid) {
        this.cTid = cTid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getUAPhorasClaseCompletas() {
        return uAPhorasClaseCompletas;
    }

    public void setUAPhorasClaseCompletas(Boolean uAPhorasClaseCompletas) {
        this.uAPhorasClaseCompletas = uAPhorasClaseCompletas;
    }

    public Boolean getUAPhorasLaboratorioCompletas() {
        return uAPhorasLaboratorioCompletas;
    }

    public void setUAPhorasLaboratorioCompletas(Boolean uAPhorasLaboratorioCompletas) {
        this.uAPhorasLaboratorioCompletas = uAPhorasLaboratorioCompletas;
    }

    public Boolean getUAPhorasTallerCompletas() {
        return uAPhorasTallerCompletas;
    }

    public void setUAPhorasTallerCompletas(Boolean uAPhorasTallerCompletas) {
        this.uAPhorasTallerCompletas = uAPhorasTallerCompletas;
    }

    public Boolean getUAPhorasCampoCompletas() {
        return uAPhorasCampoCompletas;
    }

    public void setUAPhorasCampoCompletas(Boolean uAPhorasCampoCompletas) {
        this.uAPhorasCampoCompletas = uAPhorasCampoCompletas;
    }

    @XmlTransient
    public List<Practicasclinica> getPracticasclinicaList() {
        return practicasclinicaList;
    }

    public void setPracticasclinicaList(List<Practicasclinica> practicasclinicaList) {
        this.practicasclinicaList = practicasclinicaList;
    }

    @XmlTransient
    public List<Practicataller> getPracticatallerList() {
        return practicatallerList;
    }

    public void setPracticatallerList(List<Practicataller> practicatallerList) {
        this.practicatallerList = practicatallerList;
    }

    @XmlTransient
    public List<Practicascampo> getPracticascampoList() {
        return practicascampoList;
    }

    public void setPracticascampoList(List<Practicascampo> practicascampoList) {
        this.practicascampoList = practicascampoList;
    }

    public Cicloescolar getCicloEscolarCESid() {
        return cicloEscolarCESid;
    }

    public void setCicloEscolarCESid(Cicloescolar cicloEscolarCESid) {
        this.cicloEscolarCESid = cicloEscolarCESid;
    }

    public Unidadaprendizaje getUnidadAprendizajeUAPid() {
        return unidadAprendizajeUAPid;
    }

    public void setUnidadAprendizajeUAPid(Unidadaprendizaje unidadAprendizajeUAPid) {
        this.unidadAprendizajeUAPid = unidadAprendizajeUAPid;
    }

    @XmlTransient
    public List<Unidad> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<Unidad> unidadList) {
        this.unidadList = unidadList;
    }

    @XmlTransient
    public List<Practicalaboratorio> getPracticalaboratorioList() {
        return practicalaboratorioList;
    }

    public void setPracticalaboratorioList(List<Practicalaboratorio> practicalaboratorioList) {
        this.practicalaboratorioList = practicalaboratorioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cTid != null ? cTid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadaprendizajeTieneContenidotematico)) {
            return false;
        }
        UnidadaprendizajeTieneContenidotematico other = (UnidadaprendizajeTieneContenidotematico) object;
        if ((this.cTid == null && other.cTid != null) || (this.cTid != null && !this.cTid.equals(other.cTid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico[ cTid=" + cTid + " ]";
    }
    
}
