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
public class CalendarioreporteTieneAlertaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CalendarioReporte_CREid")
    private int calendarioReporteCREid;
    @Basic(optional = false)
    @Column(name = "Alerta_ALEid")
    private int alertaALEid;

    public CalendarioreporteTieneAlertaPK() {
    }

    public CalendarioreporteTieneAlertaPK(int calendarioReporteCREid, int alertaALEid) {
        this.calendarioReporteCREid = calendarioReporteCREid;
        this.alertaALEid = alertaALEid;
    }

    public int getCalendarioReporteCREid() {
        return calendarioReporteCREid;
    }

    public void setCalendarioReporteCREid(int calendarioReporteCREid) {
        this.calendarioReporteCREid = calendarioReporteCREid;
    }

    public int getAlertaALEid() {
        return alertaALEid;
    }

    public void setAlertaALEid(int alertaALEid) {
        this.alertaALEid = alertaALEid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) calendarioReporteCREid;
        hash += (int) alertaALEid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalendarioreporteTieneAlertaPK)) {
            return false;
        }
        CalendarioreporteTieneAlertaPK other = (CalendarioreporteTieneAlertaPK) object;
        if (this.calendarioReporteCREid != other.calendarioReporteCREid) {
            return false;
        }
        if (this.alertaALEid != other.alertaALEid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.CalendarioreporteTieneAlertaPK[ calendarioReporteCREid=" + calendarioReporteCREid + ", alertaALEid=" + alertaALEid + " ]";
    }
    
}
