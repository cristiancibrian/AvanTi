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
@Table(name = "temaunidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Temaunidad.findAll", query = "SELECT t FROM Temaunidad t")
    , @NamedQuery(name = "Temaunidad.findByTUNid", query = "SELECT t FROM Temaunidad t WHERE t.tUNid = :tUNid")
    , @NamedQuery(name = "Temaunidad.findByTUNnumero", query = "SELECT t FROM Temaunidad t WHERE t.tUNnumero = :tUNnumero")
    , @NamedQuery(name = "Temaunidad.findByTUNnombre", query = "SELECT t FROM Temaunidad t WHERE t.tUNnombre = :tUNnombre")
    , @NamedQuery(name = "Temaunidad.findByTUNvalorPorcentaje", query = "SELECT t FROM Temaunidad t WHERE t.tUNvalorPorcentaje = :tUNvalorPorcentaje")
    , @NamedQuery(name = "Temaunidad.findByTUNhoras", query = "SELECT t FROM Temaunidad t WHERE t.tUNhoras = :tUNhoras")
    , @NamedQuery(name = "Temaunidad.findByTUNhorasCompletas", query = "SELECT t FROM Temaunidad t WHERE t.tUNhorasCompletas = :tUNhorasCompletas")})
public class Temaunidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TUNid")
    private Integer tUNid;
    @Basic(optional = false)
    @Column(name = "TUNnumero")
    private String tUNnumero;
    @Basic(optional = false)
    @Column(name = "TUNnombre")
    private String tUNnombre;
    @Basic(optional = false)
    @Column(name = "TUNvalorPorcentaje")
    private float tUNvalorPorcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TUNhoras")
    private Float tUNhoras;
    @Column(name = "TUNhorasCompletas")
    private Boolean tUNhorasCompletas;
    @JoinColumn(name = "Unidad_UNIid", referencedColumnName = "UNIid")
    @ManyToOne(optional = false)
    private Unidad unidadUNIid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temaUnidadTUNid")
    private List<Subtemaunidad> subtemaunidadList;

    public Temaunidad() {
    }

    public Temaunidad(Integer tUNid) {
        this.tUNid = tUNid;
    }

    public Temaunidad(Integer tUNid, String tUNnumero, String tUNnombre, float tUNvalorPorcentaje) {
        this.tUNid = tUNid;
        this.tUNnumero = tUNnumero;
        this.tUNnombre = tUNnombre;
        this.tUNvalorPorcentaje = tUNvalorPorcentaje;
    }

    public Integer getTUNid() {
        return tUNid;
    }

    public void setTUNid(Integer tUNid) {
        this.tUNid = tUNid;
    }

    public String getTUNnumero() {
        return tUNnumero;
    }

    public void setTUNnumero(String tUNnumero) {
        this.tUNnumero = tUNnumero;
    }

    public String getTUNnombre() {
        return tUNnombre;
    }

    public void setTUNnombre(String tUNnombre) {
        this.tUNnombre = tUNnombre;
    }

    public float getTUNvalorPorcentaje() {
        return tUNvalorPorcentaje;
    }

    public void setTUNvalorPorcentaje(float tUNvalorPorcentaje) {
        this.tUNvalorPorcentaje = tUNvalorPorcentaje;
    }

    public Float getTUNhoras() {
        return tUNhoras;
    }

    public void setTUNhoras(Float tUNhoras) {
        this.tUNhoras = tUNhoras;
    }

    public Boolean getTUNhorasCompletas() {
        return tUNhorasCompletas;
    }

    public void setTUNhorasCompletas(Boolean tUNhorasCompletas) {
        this.tUNhorasCompletas = tUNhorasCompletas;
    }

    public Unidad getUnidadUNIid() {
        return unidadUNIid;
    }

    public void setUnidadUNIid(Unidad unidadUNIid) {
        this.unidadUNIid = unidadUNIid;
    }

    @XmlTransient
    public List<Subtemaunidad> getSubtemaunidadList() {
        return subtemaunidadList;
    }

    public void setSubtemaunidadList(List<Subtemaunidad> subtemaunidadList) {
        this.subtemaunidadList = subtemaunidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tUNid != null ? tUNid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Temaunidad)) {
            return false;
        }
        
        Temaunidad other = (Temaunidad) object;
        return !((this.tUNid == null && other.tUNid != null) || (this.tUNid != null && !this.tUNid.equals(other.tUNid)));
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Temaunidad[ tUNid=" + tUNid + " ]";
    }

}
