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
@Table(name = "calendarioreporte_tiene_alerta")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CalendarioreporteTieneAlerta.findAll", query = "SELECT c FROM CalendarioreporteTieneAlerta c")
        , @NamedQuery(name = "CalendarioreporteTieneAlerta.findByCalendarioReporteCREid", query = "SELECT c FROM CalendarioreporteTieneAlerta c WHERE c.calendarioreporteTieneAlertaPK.calendarioReporteCREid = :calendarioReporteCREid")
        , @NamedQuery(name = "CalendarioreporteTieneAlerta.findByAlertaALEid", query = "SELECT c FROM CalendarioreporteTieneAlerta c WHERE c.calendarioreporteTieneAlertaPK.alertaALEid = :alertaALEid")
        , @NamedQuery(name = "CalendarioreporteTieneAlerta.findByCALdias", query = "SELECT c FROM CalendarioreporteTieneAlerta c WHERE c.cALdias = :cALdias")
        , @NamedQuery(name = "CalendarioreporteTieneAlerta.findByCALnumeroReporte", query = "SELECT c FROM CalendarioreporteTieneAlerta c WHERE c.cALnumeroReporte = :cALnumeroReporte")})
public class CalendarioreporteTieneAlerta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaPK;
    @Basic(optional = false)
    @Column(name = "CALdias")
    private int cALdias;
    @Column(name = "CALnumeroReporte")
    private Integer cALnumeroReporte;
    @JoinColumn(name = "Alerta_ALEid", referencedColumnName = "ALEid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alerta alerta;
    @JoinColumn(name = "CalendarioReporte_CREid", referencedColumnName = "CREid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Calendarioreporte calendarioreporte;

    public CalendarioreporteTieneAlerta() {
    }

    public CalendarioreporteTieneAlerta(CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaPK) {
        this.calendarioreporteTieneAlertaPK = calendarioreporteTieneAlertaPK;
    }

    public CalendarioreporteTieneAlerta(CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaPK, int cALdias) {
        this.calendarioreporteTieneAlertaPK = calendarioreporteTieneAlertaPK;
        this.cALdias = cALdias;
    }

    public CalendarioreporteTieneAlerta(int calendarioReporteCREid, int alertaALEid) {
        this.calendarioreporteTieneAlertaPK = new CalendarioreporteTieneAlertaPK(calendarioReporteCREid, alertaALEid);
    }

    public CalendarioreporteTieneAlertaPK getCalendarioreporteTieneAlertaPK() {
        return calendarioreporteTieneAlertaPK;
    }

    public void setCalendarioreporteTieneAlertaPK(CalendarioreporteTieneAlertaPK calendarioreporteTieneAlertaPK) {
        this.calendarioreporteTieneAlertaPK = calendarioreporteTieneAlertaPK;
    }

    public int getCALdias() {
        return cALdias;
    }

    public void setCALdias(int cALdias) {
        this.cALdias = cALdias;
    }

    public Integer getCALnumeroReporte() {
        return cALnumeroReporte;
    }

    public void setCALnumeroReporte(Integer cALnumeroReporte) {
        this.cALnumeroReporte = cALnumeroReporte;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public Calendarioreporte getCalendarioreporte() {
        return calendarioreporte;
    }

    public void setCalendarioreporte(Calendarioreporte calendarioreporte) {
        this.calendarioreporte = calendarioreporte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calendarioreporteTieneAlertaPK != null ? calendarioreporteTieneAlertaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalendarioreporteTieneAlerta)) {
            return false;
        }
        CalendarioreporteTieneAlerta other = (CalendarioreporteTieneAlerta) object;
        if ((this.calendarioreporteTieneAlertaPK == null && other.calendarioreporteTieneAlertaPK != null) || (this.calendarioreporteTieneAlertaPK != null && !this.calendarioreporteTieneAlertaPK.equals(other.calendarioreporteTieneAlertaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.CalendarioreporteTieneAlerta[ calendarioreporteTieneAlertaPK=" + calendarioreporteTieneAlertaPK + " ]";
    }

}
