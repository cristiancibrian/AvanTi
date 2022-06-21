/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "planestudio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planestudio.findAll", query = "SELECT p FROM Planestudio p")
    , @NamedQuery(name = "Planestudio.findByPESid", query = "SELECT p FROM Planestudio p WHERE p.pESid = :pESid")
    , @NamedQuery(name = "Planestudio.findByPESvigenciaPlan", query = "SELECT p FROM Planestudio p WHERE p.pESvigenciaPlan = :pESvigenciaPlan")
    , @NamedQuery(name = "Planestudio.findByPEScreditosObligatorios", query = "SELECT p FROM Planestudio p WHERE p.pEScreditosObligatorios = :pEScreditosObligatorios")
    , @NamedQuery(name = "Planestudio.findByPEScreditosOptativos", query = "SELECT p FROM Planestudio p WHERE p.pEScreditosOptativos = :pEScreditosOptativos")
    , @NamedQuery(name = "Planestudio.findByPEStotalCreditos", query = "SELECT p FROM Planestudio p WHERE p.pEStotalCreditos = :pEStotalCreditos")})
public class Planestudio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PESid")
    private Integer pESid;
    @Basic(optional = false)
    @Column(name = "PESvigenciaPlan")
    private String pESvigenciaPlan;
    @Basic(optional = false)
    @Column(name = "PEScreditosObligatorios")
    private int pEScreditosObligatorios;
    @Basic(optional = false)
    @Column(name = "PEScreditosOptativos")
    private int pEScreditosOptativos;
    @Basic(optional = false)
    @Column(name = "PEStotalCreditos")
    private int pEStotalCreditos;
    @OneToMany(mappedBy = "planEstudioPESid")
    private List<Grupo> grupoList;
    @OneToMany(mappedBy = "planEstudioPESid")
    private List<Areaconocimiento> areaconocimientoList;
    @JoinColumn(name = "ProgramaEducativo_PEDid", referencedColumnName = "PEDid")
    @ManyToOne
    private Programaeducativo programaEducativoPEDid;

    public Planestudio() {
    }

    public Planestudio(Integer pESid) {
        this.pESid = pESid;
    }

    public Planestudio(Integer pESid, String pESvigenciaPlan, int pEScreditosObligatorios, int pEScreditosOptativos, int pEStotalCreditos) {
        this.pESid = pESid;
        this.pESvigenciaPlan = pESvigenciaPlan;
        this.pEScreditosObligatorios = pEScreditosObligatorios;
        this.pEScreditosOptativos = pEScreditosOptativos;
        this.pEStotalCreditos = pEStotalCreditos;
    }

    public Integer getPESid() {
        return pESid;
    }

    public void setPESid(Integer pESid) {
        this.pESid = pESid;
    }

    public String getPESvigenciaPlan() {
        return pESvigenciaPlan;
    }

    public void setPESvigenciaPlan(String pESvigenciaPlan) {
        this.pESvigenciaPlan = pESvigenciaPlan;
    }

    public int getPEScreditosObligatorios() {
        return pEScreditosObligatorios;
    }

    public void setPEScreditosObligatorios(int pEScreditosObligatorios) {
        this.pEScreditosObligatorios = pEScreditosObligatorios;
    }

    public int getPEScreditosOptativos() {
        return pEScreditosOptativos;
    }

    public void setPEScreditosOptativos(int pEScreditosOptativos) {
        this.pEScreditosOptativos = pEScreditosOptativos;
    }

    public int getPEStotalCreditos() {
        return pEStotalCreditos;
    }

    public void setPEStotalCreditos(int pEStotalCreditos) {
        this.pEStotalCreditos = pEStotalCreditos;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<Areaconocimiento> getAreaconocimientoList() {
        return areaconocimientoList;
    }

    public void setAreaconocimientoList(List<Areaconocimiento> areaconocimientoList) {
        this.areaconocimientoList = areaconocimientoList;
    }

    public Programaeducativo getProgramaEducativoPEDid() {
        return programaEducativoPEDid;
    }

    public void setProgramaEducativoPEDid(Programaeducativo programaEducativoPEDid) {
        this.programaEducativoPEDid = programaEducativoPEDid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pESid != null ? pESid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planestudio)) {
            return false;
        }
        Planestudio other = (Planestudio) object;
        if ((this.pESid == null && other.pESid != null) || (this.pESid != null && !this.pESid.equals(other.pESid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Planestudio[ pESid=" + pESid + " ]";
    }
    
}
