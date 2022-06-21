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
import java.util.Date;
import java.util.List;

/**
 * @author Eduardo
 */
@Entity
@Table(name = "calendarioreporte")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Calendarioreporte.findAll", query = "SELECT c FROM Calendarioreporte c")
        , @NamedQuery(name = "Calendarioreporte.findByCREid", query = "SELECT c FROM Calendarioreporte c WHERE c.cREid = :cREid")
        , @NamedQuery(name = "Calendarioreporte.findByCREfechaCorte", query = "SELECT c FROM Calendarioreporte c WHERE c.cREfechaCorte = :cREfechaCorte")
        , @NamedQuery(name = "Calendarioreporte.findByCREfechaLimite", query = "SELECT c FROM Calendarioreporte c WHERE c.cREfechaLimite = :cREfechaLimite")})
public class Calendarioreporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CREid")
    private Integer cREid;
    @Basic(optional = false)
    @Column(name = "CREfechaCorte")
    @Temporal(TemporalType.DATE)
    private Date cREfechaCorte;
    @Basic(optional = false)
    @Column(name = "CREfechaLimite")
    @Temporal(TemporalType.DATE)
    private Date cREfechaLimite;
    @JoinTable(name = "configuracion_tiene_calendarioreporte", joinColumns = {
            @JoinColumn(name = "CalendarioReporte_CREid", referencedColumnName = "CREid")}, inverseJoinColumns = {
            @JoinColumn(name = "Configuracion_CONid", referencedColumnName = "CONid")})
    @ManyToMany
    private List<Configuracion> configuracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calendarioreporte")
    private List<CalendarioreporteTieneAlerta> calendarioreporteTieneAlertaList;

    public Calendarioreporte() {
    }

    public Calendarioreporte(Integer cREid) {
        this.cREid = cREid;
    }

    public Calendarioreporte(Integer cREid, Date cREfechaCorte, Date cREfechaLimite) {
        this.cREid = cREid;
        this.cREfechaCorte = cREfechaCorte;
        this.cREfechaLimite = cREfechaLimite;
    }

    public Integer getCREid() {
        return cREid;
    }

    public void setCREid(Integer cREid) {
        this.cREid = cREid;
    }

    public Date getCREfechaCorte() {
        return cREfechaCorte;
    }

    public void setCREfechaCorte(Date cREfechaCorte) {
        this.cREfechaCorte = cREfechaCorte;
    }

    public Date getCREfechaLimite() {
        return cREfechaLimite;
    }

    public void setCREfechaLimite(Date cREfechaLimite) {
        this.cREfechaLimite = cREfechaLimite;
    }

    @XmlTransient
    public List<Configuracion> getConfiguracionList() {
        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    @XmlTransient
    public List<CalendarioreporteTieneAlerta> getCalendarioreporteTieneAlertaList() {
        return calendarioreporteTieneAlertaList;
    }

    public void setCalendarioreporteTieneAlertaList(List<CalendarioreporteTieneAlerta> calendarioreporteTieneAlertaList) {
        this.calendarioreporteTieneAlertaList = calendarioreporteTieneAlertaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cREid != null ? cREid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendarioreporte)) {
            return false;
        }
        Calendarioreporte other = (Calendarioreporte) object;
        if ((this.cREid == null && other.cREid != null) || (this.cREid != null && !this.cREid.equals(other.cREid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Calendarioreporte[ cREid=" + cREid + " ]";
    }

}
