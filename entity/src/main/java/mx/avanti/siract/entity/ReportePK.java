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
public class ReportePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "REPid")
    private int rEPid;
    @Basic(optional = false)
    @Column(name = "UNIid")
    private int uNIid;
    @Basic(optional = false)
    @Column(name = "TUNid")
    private int tUNid;
    @Basic(optional = false)
    @Column(name = "SUTid")
    private int sUTid;
    @Basic(optional = false)
    @Column(name = "PRLid")
    private int pRLid;
    @Basic(optional = false)
    @Column(name = "PRTid")
    private int pRTid;
    @Basic(optional = false)
    @Column(name = "PRCid")
    private int pRCid;

    public ReportePK() {
    }

    public ReportePK(int rEPid, int uNIid, int tUNid, int sUTid, int pRLid, int pRTid, int pRCid) {
        this.rEPid = rEPid;
        this.uNIid = uNIid;
        this.tUNid = tUNid;
        this.sUTid = sUTid;
        this.pRLid = pRLid;
        this.pRTid = pRTid;
        this.pRCid = pRCid;
    }

    public int getREPid() {
        return rEPid;
    }

    public void setREPid(int rEPid) {
        this.rEPid = rEPid;
    }

    public int getUNIid() {
        return uNIid;
    }

    public void setUNIid(int uNIid) {
        this.uNIid = uNIid;
    }

    public int getTUNid() {
        return tUNid;
    }

    public void setTUNid(int tUNid) {
        this.tUNid = tUNid;
    }

    public int getSUTid() {
        return sUTid;
    }

    public void setSUTid(int sUTid) {
        this.sUTid = sUTid;
    }

    public int getPRLid() {
        return pRLid;
    }

    public void setPRLid(int pRLid) {
        this.pRLid = pRLid;
    }

    public int getPRTid() {
        return pRTid;
    }

    public void setPRTid(int pRTid) {
        this.pRTid = pRTid;
    }

    public int getPRCid() {
        return pRCid;
    }

    public void setPRCid(int pRCid) {
        this.pRCid = pRCid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rEPid;
        hash += (int) uNIid;
        hash += (int) tUNid;
        hash += (int) sUTid;
        hash += (int) pRLid;
        hash += (int) pRTid;
        hash += (int) pRCid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportePK)) {
            return false;
        }
        ReportePK other = (ReportePK) object;
        if (this.rEPid != other.rEPid) {
            return false;
        }
        if (this.uNIid != other.uNIid) {
            return false;
        }
        if (this.tUNid != other.tUNid) {
            return false;
        }
        if (this.sUTid != other.sUTid) {
            return false;
        }
        if (this.pRLid != other.pRLid) {
            return false;
        }
        if (this.pRTid != other.pRTid) {
            return false;
        }
        if (this.pRCid != other.pRCid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.ReportePK[ rEPid=" + rEPid + ", uNIid=" + uNIid + ", tUNid=" + tUNid + ", sUTid=" + sUTid + ", pRLid=" + pRLid + ", pRTid=" + pRTid + ", pRCid=" + pRCid + " ]";
    }
    
}
