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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eduardocardona
 */
@Entity
@Table(name = "alumno_pertenece_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlumnoPerteneceGrupo.findAll", query = "SELECT a FROM AlumnoPerteneceGrupo a"),
    @NamedQuery(name = "AlumnoPerteneceGrupo.findByAptId", query = "SELECT a FROM AlumnoPerteneceGrupo a WHERE a.aptId = :aptId"),
    @NamedQuery(name = "AlumnoPerteneceGrupo.findByUIPId", query = "SELECT a FROM AlumnoPerteneceGrupo a WHERE a.uIPId = :uIPId")})
public class AlumnoPerteneceGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AptId")
    private Integer aptId;
    @JoinColumn(name = "alumnoALId", referencedColumnName = "ALId")
    @ManyToOne
    private Alumno alumnoALId;
    @JoinColumn(name = "UIPId", referencedColumnName = "UIPid")
    @ManyToOne
    private UnidadaprendizajeImparteProfesor uIPId;

    public AlumnoPerteneceGrupo() {
    }

    public AlumnoPerteneceGrupo(Integer aptId) {
        this.aptId = aptId;
    }

    public Integer getAptId() {
        return aptId;
    }

    public void setAptId(Integer aptId) {
        this.aptId = aptId;
    }

   

    public Alumno getAlumnoALId() {
        return alumnoALId;
    }

    public void setAlumnoALId(Alumno alumnoALId) {
        this.alumnoALId = alumnoALId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aptId != null ? aptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlumnoPerteneceGrupo)) {
            return false;
        }
        AlumnoPerteneceGrupo other = (AlumnoPerteneceGrupo) object;
        if ((this.aptId == null && other.aptId != null) || (this.aptId != null && !this.aptId.equals(other.aptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.AlumnoPerteneceGrupo[ aptId=" + aptId + " ]";
    }
    
    public UnidadaprendizajeImparteProfesor getUIPId() {
        return uIPId;
    }

    public void setUIPId(UnidadaprendizajeImparteProfesor uIPId) {
        this.uIPId = uIPId;
    }
    
}
