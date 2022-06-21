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
@Table(name = "profesor_pertenece_programaeducativo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ProfesorPerteneceProgramaeducativo.findAll", query = "SELECT p FROM ProfesorPerteneceProgramaeducativo p")
        , @NamedQuery(name = "ProfesorPerteneceProgramaeducativo.findByProfesorPROid", query = "SELECT p FROM ProfesorPerteneceProgramaeducativo p WHERE p.profesorPerteneceProgramaeducativoPK.profesorPROid = :profesorPROid")
        , @NamedQuery(name = "ProfesorPerteneceProgramaeducativo.findByProgramaEducativoPEDid", query = "SELECT p FROM ProfesorPerteneceProgramaeducativo p WHERE p.profesorPerteneceProgramaeducativoPK.programaEducativoPEDid = :programaEducativoPEDid")
        , @NamedQuery(name = "ProfesorPerteneceProgramaeducativo.findByCicloEscolarCESid", query = "SELECT p FROM ProfesorPerteneceProgramaeducativo p WHERE p.profesorPerteneceProgramaeducativoPK.cicloEscolarCESid = :cicloEscolarCESid")})
public class ProfesorPerteneceProgramaeducativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProfesorPerteneceProgramaeducativoPK profesorPerteneceProgramaeducativoPK;
    @JoinColumn(name = "CicloEscolar_CESid", referencedColumnName = "CESid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cicloescolar cicloescolar;
    @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Profesor profesor;
    @JoinColumn(name = "ProgramaEducativo_PEDid", referencedColumnName = "PEDid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Programaeducativo programaeducativo;

    public ProfesorPerteneceProgramaeducativo() {
    }

    public ProfesorPerteneceProgramaeducativo(ProfesorPerteneceProgramaeducativoPK profesorPerteneceProgramaeducativoPK) {
        this.profesorPerteneceProgramaeducativoPK = profesorPerteneceProgramaeducativoPK;
    }

    public ProfesorPerteneceProgramaeducativo(int profesorPROid, int programaEducativoPEDid, int cicloEscolarCESid) {
        this.profesorPerteneceProgramaeducativoPK = new ProfesorPerteneceProgramaeducativoPK(profesorPROid, programaEducativoPEDid, cicloEscolarCESid);
    }

    public ProfesorPerteneceProgramaeducativoPK getProfesorPerteneceProgramaeducativoPK() {
        return profesorPerteneceProgramaeducativoPK;
    }

    public void setProfesorPerteneceProgramaeducativoPK(ProfesorPerteneceProgramaeducativoPK profesorPerteneceProgramaeducativoPK) {
        this.profesorPerteneceProgramaeducativoPK = profesorPerteneceProgramaeducativoPK;
    }

    public Cicloescolar getCicloescolar() {
        return cicloescolar;
    }

    public void setCicloescolar(Cicloescolar cicloescolar) {
        this.cicloescolar = cicloescolar;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Programaeducativo getProgramaeducativo() {
        return programaeducativo;
    }

    public void setProgramaeducativo(Programaeducativo programaeducativo) {
        this.programaeducativo = programaeducativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profesorPerteneceProgramaeducativoPK != null ? profesorPerteneceProgramaeducativoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfesorPerteneceProgramaeducativo)) {
            return false;
        }
        ProfesorPerteneceProgramaeducativo other = (ProfesorPerteneceProgramaeducativo) object;
        if ((this.profesorPerteneceProgramaeducativoPK == null && other.profesorPerteneceProgramaeducativoPK != null) || (this.profesorPerteneceProgramaeducativoPK != null && !this.profesorPerteneceProgramaeducativoPK.equals(other.profesorPerteneceProgramaeducativoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo[ profesorPerteneceProgramaeducativoPK=" + profesorPerteneceProgramaeducativoPK + " ]";
    }

}
