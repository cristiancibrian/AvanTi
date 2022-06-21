/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Eduardo
 */
@Embeddable
public class ProfesorPerteneceProgramaeducativoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Profesor_PROid")
    private int profesorPROid;
    @Basic(optional = false)
    @Column(name = "ProgramaEducativo_PEDid")
    private int programaEducativoPEDid;
    @Basic(optional = false)
    @Column(name = "CicloEscolar_CESid")
    private int cicloEscolarCESid;

    public ProfesorPerteneceProgramaeducativoPK() {
    }

    public ProfesorPerteneceProgramaeducativoPK(int profesorPROid, int programaEducativoPEDid, int cicloEscolarCESid) {
        this.profesorPROid = profesorPROid;
        this.programaEducativoPEDid = programaEducativoPEDid;
        this.cicloEscolarCESid = cicloEscolarCESid;
    }

    public int getProfesorPROid() {
        return profesorPROid;
    }

    public void setProfesorPROid(int profesorPROid) {
        this.profesorPROid = profesorPROid;
    }

    public int getProgramaEducativoPEDid() {
        return programaEducativoPEDid;
    }

    public void setProgramaEducativoPEDid(int programaEducativoPEDid) {
        this.programaEducativoPEDid = programaEducativoPEDid;
    }

    public int getCicloEscolarCESid() {
        return cicloEscolarCESid;
    }

    public void setCicloEscolarCESid(int cicloEscolarCESid) {
        this.cicloEscolarCESid = cicloEscolarCESid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) profesorPROid;
        hash += (int) programaEducativoPEDid;
        hash += (int) cicloEscolarCESid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfesorPerteneceProgramaeducativoPK)) {
            return false;
        }
        ProfesorPerteneceProgramaeducativoPK other = (ProfesorPerteneceProgramaeducativoPK) object;
        if (this.profesorPROid != other.profesorPROid) {
            return false;
        }
        if (this.programaEducativoPEDid != other.programaEducativoPEDid) {
            return false;
        }
        if (this.cicloEscolarCESid != other.cicloEscolarCESid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativoPK[ profesorPROid=" + profesorPROid + ", programaEducativoPEDid=" + programaEducativoPEDid + ", cicloEscolarCESid=" + cicloEscolarCESid + " ]";
    }
    
}
