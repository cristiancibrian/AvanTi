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
@Table(name = "catalogoreportes")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Catalogoreportes.findAll", query = "SELECT c FROM Catalogoreportes c")
        , @NamedQuery(name = "Catalogoreportes.findByCTRid", query = "SELECT c FROM Catalogoreportes c WHERE c.cTRid = :cTRid")
        , @NamedQuery(name = "Catalogoreportes.findByCTRnombre", query = "SELECT c FROM Catalogoreportes c WHERE c.cTRnombre = :cTRnombre")})
public class Catalogoreportes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CTRid")
    private Integer cTRid;
    @Basic(optional = false)
    @Column(name = "CTRnombre")
    private String cTRnombre;
    @Lob
    @Column(name = "CTRconsultaQuery")
    private String cTRconsultaQuery;
    @JoinColumn(name = "Usuario_USUid", referencedColumnName = "USUid")
    @ManyToOne(optional = false)
    private Usuario usuarioUSUid;

    public Catalogoreportes() {
    }

    public Catalogoreportes(Integer cTRid) {
        this.cTRid = cTRid;
    }

    public Catalogoreportes(Integer cTRid, String cTRnombre) {
        this.cTRid = cTRid;
        this.cTRnombre = cTRnombre;
    }

    public Integer getCTRid() {
        return cTRid;
    }

    public void setCTRid(Integer cTRid) {
        this.cTRid = cTRid;
    }

    public String getCTRnombre() {
        return cTRnombre;
    }

    public void setCTRnombre(String cTRnombre) {
        this.cTRnombre = cTRnombre;
    }

    public String getCTRconsultaQuery() {
        return cTRconsultaQuery;
    }

    public void setCTRconsultaQuery(String cTRconsultaQuery) {
        this.cTRconsultaQuery = cTRconsultaQuery;
    }

    public Usuario getUsuarioUSUid() {
        return usuarioUSUid;
    }

    public void setUsuarioUSUid(Usuario usuarioUSUid) {
        this.usuarioUSUid = usuarioUSUid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cTRid != null ? cTRid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catalogoreportes)) {
            return false;
        }
        Catalogoreportes other = (Catalogoreportes) object;
        if ((this.cTRid == null && other.cTRid != null) || (this.cTRid != null && !this.cTRid.equals(other.cTRid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Catalogoreportes[ cTRid=" + cTRid + " ]";
    }

}
