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
 * @author Eduardo
 */
@Entity
@Table(name = "areaadministrativa")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Areaadministrativa.findAll", query = "SELECT a FROM Areaadministrativa a")
        , @NamedQuery(name = "Areaadministrativa.findByAADid", query = "SELECT a FROM Areaadministrativa a WHERE a.aADid = :aADid")
        , @NamedQuery(name = "Areaadministrativa.findByAADnombre", query = "SELECT a FROM Areaadministrativa a WHERE a.aADnombre = :aADnombre")})
public class Areaadministrativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AADid")
    private Integer aADid;
    @Basic(optional = false)
    @Column(name = "AADnombre")
    private String aADnombre;
    @JoinColumn(name = "ProgramaEducativo_PEDid", referencedColumnName = "PEDid")
    @ManyToOne(optional = false)
    private Programaeducativo programaEducativoPEDid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaAdministrativaAADid")
    private List<Coordinadorareaadministrativa> coordinadorareaadministrativaList;

    public Areaadministrativa() {
    }

    public Areaadministrativa(Integer aADid) {
        this.aADid = aADid;
    }

    public Areaadministrativa(Integer aADid, String aADnombre) {
        this.aADid = aADid;
        this.aADnombre = aADnombre;
    }

    public Integer getAADid() {
        return aADid;
    }

    public void setAADid(Integer aADid) {
        this.aADid = aADid;
    }

    public String getAADnombre() {
        return aADnombre;
    }

    public void setAADnombre(String aADnombre) {
        this.aADnombre = aADnombre;
    }

    public Programaeducativo getProgramaEducativoPEDid() {
        return programaEducativoPEDid;
    }

    public void setProgramaEducativoPEDid(Programaeducativo programaEducativoPEDid) {
        this.programaEducativoPEDid = programaEducativoPEDid;
    }

    @XmlTransient
    public List<Coordinadorareaadministrativa> getCoordinadorareaadministrativaList() {
        return coordinadorareaadministrativaList;
    }

    public void setCoordinadorareaadministrativaList(List<Coordinadorareaadministrativa> coordinadorareaadministrativaList) {
        this.coordinadorareaadministrativaList = coordinadorareaadministrativaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aADid != null ? aADid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Areaadministrativa)) {
            return false;
        }
        Areaadministrativa other = (Areaadministrativa) object;
        if ((this.aADid == null && other.aADid != null) || (this.aADid != null && !this.aADid.equals(other.aADid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Areaadministrativa[ aADid=" + aADid + " ]";
    }

}
