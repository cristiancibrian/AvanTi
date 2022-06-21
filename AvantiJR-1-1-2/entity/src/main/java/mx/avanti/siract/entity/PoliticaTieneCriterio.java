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
@Table(name = "politica_tiene_criterio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoliticaTieneCriterio.findAll", query = "SELECT p FROM PoliticaTieneCriterio p")
    ,
    @NamedQuery(name = "PoliticaTieneCriterio.findByPorcentaje", query = "SELECT p FROM PoliticaTieneCriterio p WHERE p.porcentaje = :porcentaje")
    ,
    @NamedQuery(name = "PoliticaTieneCriterio.findByPtcId", query = "SELECT p FROM PoliticaTieneCriterio p WHERE p.ptcId = :ptcId")})
public class PoliticaTieneCriterio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Porcentaje")
    private Integer porcentaje;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PtcId")
    private Integer ptcId;
    @JoinColumn(name = "POEId", referencedColumnName = "POEId")
    @ManyToOne
    private Politicaevaluacion pOEId;
    @JoinColumn(name = "CEVId", referencedColumnName = "CEVId")
    @ManyToOne
    private Criterioevaluacion cEVId;
    @Basic(optional = false)
    @Column(name = "NExamen")
    private Integer nExamen;

    public PoliticaTieneCriterio() {
    }

    public PoliticaTieneCriterio(Integer ptcId) {
        this.ptcId = ptcId;
    }

 
    public Integer getPtcId() {
        return ptcId;
    }

    public void setPtcId(Integer ptcId) {
        this.ptcId = ptcId;
    }

    public Politicaevaluacion getPOEId() {
        return pOEId;
    }

    public void setPOEId(Politicaevaluacion pOEId) {
        this.pOEId = pOEId;
    }

    public Criterioevaluacion getCEVId() {
        return cEVId;
    }

    public void setCEVId(Criterioevaluacion cEVId) {
        this.cEVId = cEVId;
    }

    public Integer getnExamen() {
        return nExamen;
    }

    public void setnExamen(Integer nExamen) {
        this.nExamen = nExamen;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ptcId != null ? ptcId.hashCode() : 0);
        return hash;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    
    public Integer getNExamen() {
        return nExamen;
    }

    public void setNExamen(Integer nExamen) {
        this.nExamen = nExamen;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoliticaTieneCriterio)) {
            return false;
        }
        PoliticaTieneCriterio other = (PoliticaTieneCriterio) object;
        if ((this.ptcId == null && other.ptcId != null) || (this.ptcId != null && !this.ptcId.equals(other.ptcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.PoliticaTieneCriterio[ ptcId=" + ptcId + " ]";
    }

}
