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
@Table(name = "alerta")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Alerta.findAll", query = "SELECT a FROM Alerta a")
        , @NamedQuery(name = "Alerta.findByALEid", query = "SELECT a FROM Alerta a WHERE a.aLEid = :aLEid")
        , @NamedQuery(name = "Alerta.findByALEtipo", query = "SELECT a FROM Alerta a WHERE a.aLEtipo = :aLEtipo")
        , @NamedQuery(name = "Alerta.findByALEcolor", query = "SELECT a FROM Alerta a WHERE a.aLEcolor = :aLEcolor")})
public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ALEid")
    private Integer aLEid;
    @Basic(optional = false)
    @Column(name = "ALEtipo")
    private String aLEtipo;
    @Column(name = "ALEcolor")
    private String aLEcolor;
    @OneToMany(mappedBy = "alertaALEid")
    private List<Configuracion> configuracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alerta")
    private List<CalendarioreporteTieneAlerta> calendarioreporteTieneAlertaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alertaALEid")
    private List<Mensaje> mensajeList;

    public Alerta() {
    }

    public Alerta(Integer aLEid) {
        this.aLEid = aLEid;
    }

    public Alerta(Integer aLEid, String aLEtipo) {
        this.aLEid = aLEid;
        this.aLEtipo = aLEtipo;
    }

    public Integer getALEid() {
        return aLEid;
    }

    public void setALEid(Integer aLEid) {
        this.aLEid = aLEid;
    }

    public String getALEtipo() {
        return aLEtipo;
    }

    public void setALEtipo(String aLEtipo) {
        this.aLEtipo = aLEtipo;
    }

    public String getALEcolor() {
        return aLEcolor;
    }

    public void setALEcolor(String aLEcolor) {
        this.aLEcolor = aLEcolor;
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

    @XmlTransient
    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    public void setMensajeList(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aLEid != null ? aLEid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alerta)) {
            return false;
        }
        Alerta other = (Alerta) object;
        if ((this.aLEid == null && other.aLEid != null) || (this.aLEid != null && !this.aLEid.equals(other.aLEid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Alerta[ aLEid=" + aLEid + " ]";
    }

}
