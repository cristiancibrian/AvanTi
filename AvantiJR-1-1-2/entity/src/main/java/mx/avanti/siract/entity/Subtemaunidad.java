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
 * @author Eduardo
 */
@Entity
@Table(name = "subtemaunidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subtemaunidad.findAll", query = "SELECT s FROM Subtemaunidad s")
    , @NamedQuery(name = "Subtemaunidad.findBySUTid", query = "SELECT s FROM Subtemaunidad s WHERE s.sUTid = :sUTid")
    , @NamedQuery(name = "Subtemaunidad.findBySUTnumero", query = "SELECT s FROM Subtemaunidad s WHERE s.sUTnumero = :sUTnumero")
    , @NamedQuery(name = "Subtemaunidad.findBySUTnombre", query = "SELECT s FROM Subtemaunidad s WHERE s.sUTnombre = :sUTnombre")
    , @NamedQuery(name = "Subtemaunidad.findBySUTvalorPorcentaje", query = "SELECT s FROM Subtemaunidad s WHERE s.sUTvalorPorcentaje = :sUTvalorPorcentaje")
    , @NamedQuery(name = "Subtemaunidad.findBySUThoras", query = "SELECT s FROM Subtemaunidad s WHERE s.sUThoras = :sUThoras")})
public class Subtemaunidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUTid")
    private Integer sUTid;
    @Basic(optional = false)
    @Column(name = "SUTnumero")
    private String sUTnumero;
    @Basic(optional = false)
    @Column(name = "SUTnombre")
    private String sUTnombre;
    @Basic(optional = false)
    @Column(name = "SUTvalorPorcentaje")
    private float sUTvalorPorcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUThoras")
    private Float sUThoras;
    @JoinColumn(name = "TemaUnidad_TUNid", referencedColumnName = "TUNid")
    @ManyToOne(optional = false)
    private Temaunidad temaUnidadTUNid;

    public Subtemaunidad() {
    }

    public Subtemaunidad(Integer sUTid) {
        this.sUTid = sUTid;
    }

    public Subtemaunidad(Integer sUTid, String sUTnumero, String sUTnombre, float sUTvalorPorcentaje) {
        this.sUTid = sUTid;
        this.sUTnumero = sUTnumero;
        this.sUTnombre = sUTnombre;
        this.sUTvalorPorcentaje = sUTvalorPorcentaje;
    }

    public Integer getSUTid() {
        return sUTid;
    }

    public void setSUTid(Integer sUTid) {
        this.sUTid = sUTid;
    }

    public String getSUTnumero() {
        return sUTnumero;
    }

    public void setSUTnumero(String sUTnumero) {
        this.sUTnumero = sUTnumero;
    }

    public String getSUTnombre() {
        return sUTnombre;
    }

    public void setSUTnombre(String sUTnombre) {
        this.sUTnombre = sUTnombre;
    }

    public float getSUTvalorPorcentaje() {
        return sUTvalorPorcentaje;
    }

    public void setSUTvalorPorcentaje(float sUTvalorPorcentaje) {
        this.sUTvalorPorcentaje = sUTvalorPorcentaje;
    }

    public Float getSUThoras() {
        return sUThoras;
    }

    public void setSUThoras(Float sUThoras) {
        this.sUThoras = sUThoras;
    }

    public Temaunidad getTemaUnidadTUNid() {
        return temaUnidadTUNid;
    }

    public void setTemaUnidadTUNid(Temaunidad temaUnidadTUNid) {
        this.temaUnidadTUNid = temaUnidadTUNid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sUTid != null ? sUTid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subtemaunidad)) {
            return false;
        }
        
        Subtemaunidad other = (Subtemaunidad) object;
        return !((this.sUTid == null && other.sUTid != null) || (this.sUTid != null && !this.sUTid.equals(other.sUTid)));
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Subtemaunidad[ sUTid=" + sUTid + " ]";
    }
}
