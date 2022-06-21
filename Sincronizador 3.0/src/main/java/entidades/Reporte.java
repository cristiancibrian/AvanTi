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
@Table(name = "reporte")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Reporte.findAll", query = "SELECT r FROM Reporte r")
        , @NamedQuery(name = "Reporte.findByREPid", query = "SELECT r FROM Reporte r WHERE r.reportePK.rEPid = :rEPid")
        , @NamedQuery(name = "Reporte.findByUNIid", query = "SELECT r FROM Reporte r WHERE r.reportePK.uNIid = :uNIid")
        , @NamedQuery(name = "Reporte.findByTUNid", query = "SELECT r FROM Reporte r WHERE r.reportePK.tUNid = :tUNid")
        , @NamedQuery(name = "Reporte.findBySUTid", query = "SELECT r FROM Reporte r WHERE r.reportePK.sUTid = :sUTid")
        , @NamedQuery(name = "Reporte.findByPRLid", query = "SELECT r FROM Reporte r WHERE r.reportePK.pRLid = :pRLid")
        , @NamedQuery(name = "Reporte.findByPRTid", query = "SELECT r FROM Reporte r WHERE r.reportePK.pRTid = :pRTid")
        , @NamedQuery(name = "Reporte.findByPRCid", query = "SELECT r FROM Reporte r WHERE r.reportePK.pRCid = :pRCid")
        , @NamedQuery(name = "Reporte.findByREPobservacion", query = "SELECT r FROM Reporte r WHERE r.rEPobservacion = :rEPobservacion")
        , @NamedQuery(name = "Reporte.findByImpartido", query = "SELECT r FROM Reporte r WHERE r.impartido = :impartido")})
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReportePK reportePK;
    @Basic(optional = false)
    @Column(name = "REPobservacion")
    private String rEPobservacion;
    @Column(name = "Impartido")
    private Boolean impartido;
    @JoinColumn(name = "ReporteAvanceContenidoTematico_RACid", referencedColumnName = "RACid")
    @ManyToOne(optional = false)
    private Reporteavancecontenidotematico reporteAvanceContenidoTematicoRACid;

    public Reporte() {
    }

    public Reporte(ReportePK reportePK) {
        this.reportePK = reportePK;
    }

    public Reporte(ReportePK reportePK, String rEPobservacion) {
        this.reportePK = reportePK;
        this.rEPobservacion = rEPobservacion;
    }

    public Reporte(int rEPid, int uNIid, int tUNid, int sUTid, int pRLid, int pRTid, int pRCid) {
        this.reportePK = new ReportePK(rEPid, uNIid, tUNid, sUTid, pRLid, pRTid, pRCid);
    }

    public ReportePK getReportePK() {
        return reportePK;
    }

    public void setReportePK(ReportePK reportePK) {
        this.reportePK = reportePK;
    }

    public String getREPobservacion() {
        return rEPobservacion;
    }

    public void setREPobservacion(String rEPobservacion) {
        this.rEPobservacion = rEPobservacion;
    }

    public Boolean getImpartido() {
        return impartido;
    }

    public void setImpartido(Boolean impartido) {
        this.impartido = impartido;
    }

    public Reporteavancecontenidotematico getReporteAvanceContenidoTematicoRACid() {
        return reporteAvanceContenidoTematicoRACid;
    }

    public void setReporteAvanceContenidoTematicoRACid(Reporteavancecontenidotematico reporteAvanceContenidoTematicoRACid) {
        this.reporteAvanceContenidoTematicoRACid = reporteAvanceContenidoTematicoRACid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportePK != null ? reportePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.reportePK == null && other.reportePK != null) || (this.reportePK != null && !this.reportePK.equals(other.reportePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Reporte[ reportePK=" + reportePK + " ]";
    }

}
