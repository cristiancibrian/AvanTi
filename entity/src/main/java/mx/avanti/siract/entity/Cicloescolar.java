/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "cicloescolar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cicloescolar.findAll", query = "SELECT c FROM Cicloescolar c")
    , @NamedQuery(name = "Cicloescolar.findByCESid", query = "SELECT c FROM Cicloescolar c WHERE c.cESid = :cESid")
    , @NamedQuery(name = "Cicloescolar.findByCEScicloEscolar", query = "SELECT c FROM Cicloescolar c WHERE c.cEScicloEscolar = :cEScicloEscolar")})
public class Cicloescolar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CESid")
    private Integer cESid;
    @Column(name = "CEScicloEscolar")
    private String cEScicloEscolar;
    @OneToMany(mappedBy = "cicloEscolarCESid")
    private List<Configuracion> configuracionList;
    @OneToMany(mappedBy = "cicloEscolarCESid")
    private List<Unidadaprendizaje> unidadaprendizajeList;
    @OneToMany(mappedBy = "cicloEscolarCESid")
    private List<UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cicloEscolarCESid")
    private List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cicloescolar")
    private List<ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList;
    @OneToMany(mappedBy = "cicloescolar", cascade = CascadeType.ALL)
    private List<ResponsableProgramaEducativo> responsableProgramaEducativos;

    public Cicloescolar() {
    }

    public Cicloescolar(Integer cESid) {
        this.cESid = cESid;
    }

    public Integer getCESid() {
        return cESid;
    }

    public void setCESid(Integer cESid) {
        this.cESid = cESid;
    }

    public String getCEScicloEscolar() {
        return cEScicloEscolar;
    }

    public void setCEScicloEscolar(String cEScicloEscolar) {
        this.cEScicloEscolar = cEScicloEscolar;
    }

    @XmlTransient
    public List<Configuracion> getConfiguracionList() {
        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    @XmlTransient
    public List<Unidadaprendizaje> getUnidadaprendizajeList() {
        return unidadaprendizajeList;
    }

    public void setUnidadaprendizajeList(List<Unidadaprendizaje> unidadaprendizajeList) {
        this.unidadaprendizajeList = unidadaprendizajeList;
    }

    @XmlTransient
    public List<UnidadaprendizajeTieneContenidotematico> getUnidadaprendizajeTieneContenidotematicoList() {
        return unidadaprendizajeTieneContenidotematicoList;
    }

    public void setUnidadaprendizajeTieneContenidotematicoList(List<UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList) {
        this.unidadaprendizajeTieneContenidotematicoList = unidadaprendizajeTieneContenidotematicoList;
    }

    @XmlTransient
    public List<UnidadaprendizajeImparteProfesor> getUnidadaprendizajeImparteProfesorList() {
        return unidadaprendizajeImparteProfesorList;
    }

    public void setUnidadaprendizajeImparteProfesorList(List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList) {
        this.unidadaprendizajeImparteProfesorList = unidadaprendizajeImparteProfesorList;
    }

    @XmlTransient
    public List<ProfesorPerteneceProgramaeducativo> getProfesorPerteneceProgramaeducativoList() {
        return profesorPerteneceProgramaeducativoList;
    }

    public void setProfesorPerteneceProgramaeducativoList(List<ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList) {
        this.profesorPerteneceProgramaeducativoList = profesorPerteneceProgramaeducativoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cESid != null ? cESid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cicloescolar)) {
            return false;
        }
        Cicloescolar other = (Cicloescolar) object;
        if ((this.cESid == null && other.cESid != null) || (this.cESid != null && !this.cESid.equals(other.cESid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Cicloescolar[ cESid=" + cESid + " ]";
    }
    
}
