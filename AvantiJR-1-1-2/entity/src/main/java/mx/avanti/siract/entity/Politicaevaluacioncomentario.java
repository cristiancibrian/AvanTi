/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lalo_
 */
@Entity
@Table(name = "politicaevaluacioncomentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Politicaevaluacioncomentario.findAll", query = "SELECT p FROM Politicaevaluacioncomentario p")
    , @NamedQuery(name = "Politicaevaluacioncomentario.findByPECId", query = "SELECT p FROM Politicaevaluacioncomentario p WHERE p.pECId = :pECId")
    , @NamedQuery(name = "Politicaevaluacioncomentario.findByPECComentario", query = "SELECT p FROM Politicaevaluacioncomentario p WHERE p.pECComentario = :pECComentario")
    , @NamedQuery(name = "Politicaevaluacioncomentario.findByPECFechaComentario", query = "SELECT p FROM Politicaevaluacioncomentario p WHERE p.pECFechaComentario = :pECFechaComentario")})
public class Politicaevaluacioncomentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PECId")
    private Integer pECId;
    @Column(name = "PECComentario")
    private String pECComentario;
    @Column(name = "PECFechaComentario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pECFechaComentario;
    @JoinColumn(name = "Alumno_ALId", referencedColumnName = "ALId")
    @ManyToOne
    private Alumno alumnoALId;
    @JoinColumn(name = "profesor_ProId", referencedColumnName = "PROid")
    @ManyToOne
    private Profesor profesorProId;
    @JoinColumn(name = "politica_POEId", referencedColumnName = "POEId")
    @ManyToOne
    private Politicaevaluacion politicaPOEId;

    public Politicaevaluacioncomentario() {
    }

    public Politicaevaluacioncomentario(Integer pECId) {
        this.pECId = pECId;
    }

    public Integer getPECId() {
        return pECId;
    }

    public void setPECId(Integer pECId) {
        this.pECId = pECId;
    }

    public String getPECComentario() {
        return pECComentario;
    }

    public void setPECComentario(String pECComentario) {
        this.pECComentario = pECComentario;
    }

    public Date getPECFechaComentario() {
        return pECFechaComentario;
    }

    public void setPECFechaComentario(Date pECFechaComentario) {
        this.pECFechaComentario = pECFechaComentario;
    }

    public Alumno getAlumnoALId() {
        return alumnoALId;
    }

    public void setAlumnoALId(Alumno alumnoALId) {
        this.alumnoALId = alumnoALId;
    }

    public Profesor getProfesorProId() {
        return profesorProId;
    }

    public void setProfesorProId(Profesor profesorProId) {
        this.profesorProId = profesorProId;
    }

    public Politicaevaluacion getPoliticaPOEId() {
        return politicaPOEId;
    }

    public void setPoliticaPOEId(Politicaevaluacion politicaPOEId) {
        this.politicaPOEId = politicaPOEId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pECId != null ? pECId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Politicaevaluacioncomentario)) {
            return false;
        }
        Politicaevaluacioncomentario other = (Politicaevaluacioncomentario) object;
        if ((this.pECId == null && other.pECId != null) || (this.pECId != null && !this.pECId.equals(other.pECId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Politicaevaluacioncomentario[ pECId=" + pECId + " ]";
    }
    
}
