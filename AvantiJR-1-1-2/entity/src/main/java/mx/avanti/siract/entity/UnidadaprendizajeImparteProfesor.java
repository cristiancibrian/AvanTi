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
import javax.persistence.ManyToOne;
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
@Table(name = "unidadaprendizaje_imparte_profesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadaprendizajeImparteProfesor.findAll", query = "SELECT u FROM UnidadaprendizajeImparteProfesor u")
    , @NamedQuery(name = "UnidadaprendizajeImparteProfesor.findByUIPid", query = "SELECT u FROM UnidadaprendizajeImparteProfesor u WHERE u.uIPid = :uIPid")
    , @NamedQuery(name = "UnidadaprendizajeImparteProfesor.findByUIPtipoSubgrupo", query = "SELECT u FROM UnidadaprendizajeImparteProfesor u WHERE u.uIPtipoSubgrupo = :uIPtipoSubgrupo")
    , @NamedQuery(name = "UnidadaprendizajeImparteProfesor.findByUIPsubgrupo", query = "SELECT u FROM UnidadaprendizajeImparteProfesor u WHERE u.uIPsubgrupo = :uIPsubgrupo")})
public class UnidadaprendizajeImparteProfesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UIPid")
    private Integer uIPid;
    @Column(name = "UIPtipoSubgrupo")
    private String uIPtipoSubgrupo;
    @Column(name = "UIPsubgrupo")
    private String uIPsubgrupo;
    @JoinColumn(name = "Grupo_GPOid", referencedColumnName = "GPOid")
    @ManyToOne(optional = false)
    private Grupo grupoGPOid;
    @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid")
    @ManyToOne(optional = false)
    private Profesor profesorPROid;
    @JoinColumn(name = "UnidadAprendizaje_UAPid", referencedColumnName = "UAPid")
    @ManyToOne(optional = false)
    private Unidadaprendizaje unidadAprendizajeUAPid;
    @JoinColumn(name = "CicloEscolar_CESid", referencedColumnName = "CESid")
    @ManyToOne(optional = false)
    private Cicloescolar cicloEscolarCESid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadAprendizajeimparteprofesorUIPid")
    private List<Reporteavancecontenidotematico> reporteavancecontenidotematicoList;

    public UnidadaprendizajeImparteProfesor() {
    }

    public UnidadaprendizajeImparteProfesor(Integer uIPid) {
        this.uIPid = uIPid;
    }
    
    public UnidadaprendizajeImparteProfesor(Cicloescolar cicloescolar, Grupo grupo, Profesor profesor, Unidadaprendizaje unidadaprendizaje) {
        this.cicloEscolarCESid = cicloescolar;
        this.grupoGPOid = grupo;
        this.profesorPROid = profesor;
        this.unidadAprendizajeUAPid = unidadaprendizaje;
    }

    public Integer getUIPid() {
        return uIPid;
    }

    public void setUIPid(Integer uIPid) {
        this.uIPid = uIPid;
    }

    public String getUIPtipoSubgrupo() {
        return uIPtipoSubgrupo;
    }

    public void setUIPtipoSubgrupo(String uIPtipoSubgrupo) {
        this.uIPtipoSubgrupo = uIPtipoSubgrupo;
    }

    public String getUIPsubgrupo() {
        return uIPsubgrupo;
    }

    public void setUIPsubgrupo(String uIPsubgrupo) {
        this.uIPsubgrupo = uIPsubgrupo;
    }

    public Grupo getGrupoGPOid() {
        return grupoGPOid;
    }

    public void setGrupoGPOid(Grupo grupoGPOid) {
        this.grupoGPOid = grupoGPOid;
    }

    public Profesor getProfesorPROid() {
        return profesorPROid;
    }

    public void setProfesorPROid(Profesor profesorPROid) {
        this.profesorPROid = profesorPROid;
    }

    public Unidadaprendizaje getUnidadAprendizajeUAPid() {
        return unidadAprendizajeUAPid;
    }

    public void setUnidadAprendizajeUAPid(Unidadaprendizaje unidadAprendizajeUAPid) {
        this.unidadAprendizajeUAPid = unidadAprendizajeUAPid;
    }

    public Cicloescolar getCicloEscolarCESid() {
        return cicloEscolarCESid;
    }

    public void setCicloEscolarCESid(Cicloescolar cicloEscolarCESid) {
        this.cicloEscolarCESid = cicloEscolarCESid;
    }

    @XmlTransient
    public List<Reporteavancecontenidotematico> getReporteavancecontenidotematicoList() {
        return reporteavancecontenidotematicoList;
    }

    public void setReporteavancecontenidotematicoList(List<Reporteavancecontenidotematico> reporteavancecontenidotematicoList) {
        this.reporteavancecontenidotematicoList = reporteavancecontenidotematicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uIPid != null ? uIPid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadaprendizajeImparteProfesor)) {
            return false;
        }
        UnidadaprendizajeImparteProfesor other = (UnidadaprendizajeImparteProfesor) object;
        if ((this.uIPid == null && other.uIPid != null) || (this.uIPid != null && !this.uIPid.equals(other.uIPid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor[ uIPid=" + uIPid + " ]";
    }
    
}
