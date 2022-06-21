package mx.avanti.siract.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "unidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidad.findAll", query = "SELECT u FROM Unidad u")
    , @NamedQuery(name = "Unidad.findByUNIid", query = "SELECT u FROM Unidad u WHERE u.uNIid = :uNIid")
    , @NamedQuery(name = "Unidad.findByUNInumero", query = "SELECT u FROM Unidad u WHERE u.uNInumero = :uNInumero")
    , @NamedQuery(name = "Unidad.findByUNInombre", query = "SELECT u FROM Unidad u WHERE u.uNInombre = :uNInombre")
    , @NamedQuery(name = "Unidad.findByUNIvalorPorcentaje", query = "SELECT u FROM Unidad u WHERE u.uNIvalorPorcentaje = :uNIvalorPorcentaje")
    , @NamedQuery(name = "Unidad.findByUNIhoras", query = "SELECT u FROM Unidad u WHERE u.uNIhoras = :uNIhoras")
    , @NamedQuery(name = "Unidad.findByUNIhorasCompletas", query = "SELECT u FROM Unidad u WHERE u.uNIhorasCompletas = :uNIhorasCompletas")})
public class Unidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UNIid")
    private Integer uNIid;
    @Column(name = "UNInumero")
    private Integer uNInumero;
    @Column(name = "UNInombre")
    private String uNInombre;
    @Basic(optional = false)
    @Column(name = "UNIvalorPorcentaje")
    private float uNIvalorPorcentaje;
    @Basic(optional = false)
    @Column(name = "UNIhoras")
    private float uNIhoras;
    @Column(name = "UNIhorasCompletas")
    private Boolean uNIhorasCompletas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadUNIid")
    private List<Temaunidad> temaunidadList;
    @JoinColumn(name = "ContenidoTematico_CTid", referencedColumnName = "CTid")
    @ManyToOne(optional = false)
    private UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid;

    public Unidad() {
    }

    public Unidad(Integer uNIid) {
        this.uNIid = uNIid;
    }

    public Unidad(Integer uNIid, int uNInumero, String uNInombre, float uNIvalorPorcentaje) {
        this.uNIid = uNIid;
        this.uNInumero = uNInumero;
        this.uNInombre = uNInombre;
        this.uNIvalorPorcentaje = uNIvalorPorcentaje;
    }

    public Integer getUNIid() {
        return uNIid;
    }

    public void setUNIid(Integer uNIid) {
        this.uNIid = uNIid;
    }

    public Integer getUNInumero() {
        return uNInumero;
    }

    public void setUNInumero(Integer uNInumero) {
        this.uNInumero = uNInumero;
    }

    public String getUNInombre() {
        return uNInombre;
    }

    public void setUNInombre(String uNInombre) {
        this.uNInombre = uNInombre;
    }

    public float getUNIvalorPorcentaje() {
        return uNIvalorPorcentaje;
    }

    public void setUNIvalorPorcentaje(float uNIvalorPorcentaje) {
        this.uNIvalorPorcentaje = uNIvalorPorcentaje;
    }

    public float getUNIhoras() {
        return uNIhoras;
    }

    public void setUNIhoras(float uNIhoras) {
        this.uNIhoras = uNIhoras;
    }

    public Boolean getUNIhorasCompletas() {
        return uNIhorasCompletas;
    }

    public void setUNIhorasCompletas(Boolean uNIhorasCompletas) {
        this.uNIhorasCompletas = uNIhorasCompletas;
    }

    @XmlTransient
    public List<Temaunidad> getTemaunidadList() {
        return temaunidadList;
    }

    public void setTemaunidadList(List<Temaunidad> temaunidadList) {
        this.temaunidadList = temaunidadList;
    }

    public UnidadaprendizajeTieneContenidotematico getContenidoTematicoCTid() {
        return contenidoTematicoCTid;
    }

    public void setContenidoTematicoCTid(UnidadaprendizajeTieneContenidotematico contenidoTematicoCTid) {
        this.contenidoTematicoCTid = contenidoTematicoCTid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uNIid != null ? uNIid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidad)) {
            return false;
        }
        
        Unidad other = (Unidad) object;
        return !((this.uNIid == null && other.uNIid != null) || (this.uNIid != null && !this.uNIid.equals(other.uNIid)));
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Unidad[ uNIid=" + uNIid + " ]";
    }
}
