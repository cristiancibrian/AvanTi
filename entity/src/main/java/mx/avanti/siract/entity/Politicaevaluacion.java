/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lalo_
 */
@Entity
@Table(name = "politicaevaluacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Politicaevaluacion.findAll", query = "SELECT p FROM Politicaevaluacion p")
    , @NamedQuery(name = "Politicaevaluacion.findByPOEId", query = "SELECT p FROM Politicaevaluacion p WHERE p.pOEId = :pOEId")
    , @NamedQuery(name = "Politicaevaluacion.findByPOECriterioExentar", query = "SELECT p FROM Politicaevaluacion p WHERE p.pOECriterioExentar = :pOECriterioExentar")
    , @NamedQuery(name = "Politicaevaluacion.findByPOEComentario", query = "SELECT p FROM Politicaevaluacion p WHERE p.pOEComentario = :pOEComentario")
    , @NamedQuery(name = "Politicaevaluacion.findByFechaCreacion", query = "SELECT p FROM Politicaevaluacion p WHERE p.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Politicaevaluacion.findByFechaAceptacionAlumno", query = "SELECT p FROM Politicaevaluacion p WHERE p.fechaAceptacionAlumno = :fechaAceptacionAlumno")
    , @NamedQuery(name = "Politicaevaluacion.findByFechaAceptacionResponsable", query = "SELECT p FROM Politicaevaluacion p WHERE p.fechaAceptacionResponsable = :fechaAceptacionResponsable")
    , @NamedQuery(name = "Politicaevaluacion.findByEstadoPolEva", query = "SELECT p FROM Politicaevaluacion p WHERE p.estadoPolEva = :estadoPolEva")})
public class Politicaevaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "POEId")
    private Integer pOEId;
    @Column(name = "POECriterioExentar")
    private String pOECriterioExentar;
    @Column(name = "POEComentario")
    private String pOEComentario;
    @Column(name = "FechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FechaAceptacionAlumno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptacionAlumno;
    @Column(name = "FechaAceptacionResponsable")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptacionResponsable;
    @Column(name = "estadoPolEva")
    private Integer estadoPolEva;
    @OneToMany(mappedBy = "pOEId")
    private List<PoliticaTieneCriterio> politicaTieneCriterioList;
    @JoinColumn(name = "Alumno_ALId", referencedColumnName = "ALId")
    @ManyToOne
    private Alumno alumnoALId;
    @JoinColumn(name = "UIPId", referencedColumnName = "UIPid")
    @ManyToOne
    private UnidadaprendizajeImparteProfesor uIPId;
    @JoinColumn(name = "Responsable_PROId", referencedColumnName = "PROid")
    @ManyToOne
    private Profesor responsablePROId;
    @OneToMany(mappedBy = "politicaPOEId")
    private List<Politicaevaluacioncomentario> politicaevaluacioncomentarioList;

    public Politicaevaluacion() {
    }

    public Politicaevaluacion(Integer pOEId) {
        this.pOEId = pOEId;
    }

    public Integer getPOEId() {
        return pOEId;
    }

    public void setPOEId(Integer pOEId) {
        this.pOEId = pOEId;
    }

    public String getPOECriterioExentar() {
        return pOECriterioExentar;
    }

    public void setPOECriterioExentar(String pOECriterioExentar) {
        this.pOECriterioExentar = pOECriterioExentar;
    }

    public String getPOEComentario() {
        return pOEComentario;
    }

    public void setPOEComentario(String pOEComentario) {
        this.pOEComentario = pOEComentario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaAceptacionAlumno() {
        return fechaAceptacionAlumno;
    }

    public void setFechaAceptacionAlumno(Date fechaAceptacionAlumno) {
        this.fechaAceptacionAlumno = fechaAceptacionAlumno;
    }

    public Date getFechaAceptacionResponsable() {
        return fechaAceptacionResponsable;
    }

    public void setFechaAceptacionResponsable(Date fechaAceptacionResponsable) {
        this.fechaAceptacionResponsable = fechaAceptacionResponsable;
    }

    public Integer getEstadoPolEva() {
        return estadoPolEva;
    }

    public void setEstadoPolEva(Integer estadoPolEva) {
        this.estadoPolEva = estadoPolEva;
    }

    @XmlTransient
    public List<PoliticaTieneCriterio> getPoliticaTieneCriterioList() {
        return politicaTieneCriterioList;
    }

    public void setPoliticaTieneCriterioList(List<PoliticaTieneCriterio> politicaTieneCriterioList) {
        this.politicaTieneCriterioList = politicaTieneCriterioList;
    }

   
    public UnidadaprendizajeImparteProfesor getUIPId() {
        return uIPId;
    }

    public void setUIPId(UnidadaprendizajeImparteProfesor uIPId) {
        this.uIPId = uIPId;
    }

   

    @XmlTransient
    public List<Politicaevaluacioncomentario> getPoliticaevaluacioncomentarioList() {
        return politicaevaluacioncomentarioList;
    }

    public void setPoliticaevaluacioncomentarioList(List<Politicaevaluacioncomentario> politicaevaluacioncomentarioList) {
        this.politicaevaluacioncomentarioList = politicaevaluacioncomentarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pOEId != null ? pOEId.hashCode() : 0);
        return hash;
    }
     public Profesor getResponsablePROId() {
        return responsablePROId;
    }

    public void setResponsablePROId(Profesor responsablePROId) {
        this.responsablePROId = responsablePROId;
    }
    
     public Alumno getAlumnoALId() {
        return alumnoALId;
    }

    public void setAlumnoALId(Alumno alumnoALId) {
        this.alumnoALId = alumnoALId;
    }


    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Politicaevaluacion)) {
            return false;
        }
        Politicaevaluacion other = (Politicaevaluacion) object;
        if ((this.pOEId == null && other.pOEId != null) || (this.pOEId != null && !this.pOEId.equals(other.pOEId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Politicaevaluacion[ pOEId=" + pOEId + " ]";
    }
    
}
