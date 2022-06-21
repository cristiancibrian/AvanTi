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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author eduardocardona
 */
@Entity
@Table(name = "criterioevaluacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Criterioevaluacion.findAll", query = "SELECT c FROM Criterioevaluacion c"),
    @NamedQuery(name = "Criterioevaluacion.findByCEVId", query = "SELECT c FROM Criterioevaluacion c WHERE c.cEVId = :cEVId"),
    @NamedQuery(name = "Criterioevaluacion.findByCEVNombre", query = "SELECT c FROM Criterioevaluacion c WHERE c.cEVNombre = :cEVNombre")})
public class Criterioevaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CEVId")
    private Integer cEVId;
    @Column(name = "CEVNombre")
    private String cEVNombre;
    @OneToMany(mappedBy = "cEVId")
    private List<PoliticaTieneCriterio> politicaTieneCriterioList;
    // custom calue
    
 
    
    
    public Criterioevaluacion() {
    }

 
    
    

    public Criterioevaluacion(Integer cEVId) {
        this.cEVId = cEVId;
    }

    public Integer getCEVId() {
        return cEVId;
    }

    public void setCEVId(Integer cEVId) {
        this.cEVId = cEVId;
    }

    public String getCEVNombre() {
        return cEVNombre;
    }

    public void setCEVNombre(String cEVNombre) {
        this.cEVNombre = cEVNombre;
    }

    @XmlTransient
    public List<PoliticaTieneCriterio> getPoliticaTieneCriterioList() {
        return politicaTieneCriterioList;
    }

    public void setPoliticaTieneCriterioList(List<PoliticaTieneCriterio> politicaTieneCriterioList) {
        this.politicaTieneCriterioList = politicaTieneCriterioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cEVId != null ? cEVId.hashCode() : 0);
        return hash;
    }
    
  

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criterioevaluacion)) {
            return false;
        }
        Criterioevaluacion other = (Criterioevaluacion) object;
        if ((this.cEVId == null && other.cEVId != null) || (this.cEVId != null && !this.cEVId.equals(other.cEVId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Criterioevaluacion[ cEVId=" + cEVId + " ]";
    }
    
}
