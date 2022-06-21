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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "unidadaprendizaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidadaprendizaje.findAll", query = "SELECT u FROM Unidadaprendizaje u")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPid", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPid = :uAPid")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPclave", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPclave = :uAPclave")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPnombre", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPnombre = :uAPnombre")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPetapaFormacion", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPetapaFormacion = :uAPetapaFormacion")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPcreditos", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPcreditos = :uAPcreditos")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPhorasClase", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPhorasClase = :uAPhorasClase")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPhorasLaboratorio", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPhorasLaboratorio = :uAPhorasLaboratorio")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPhorasTaller", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPhorasTaller = :uAPhorasTaller")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPhorasClinica", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPhorasClinica = :uAPhorasClinica")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPhorasCampo", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPhorasCampo = :uAPhorasCampo")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPhorasExtraClase", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPhorasExtraClase = :uAPhorasExtraClase")
    , @NamedQuery(name = "Unidadaprendizaje.findByUAPtipoCaracter", query = "SELECT u FROM Unidadaprendizaje u WHERE u.uAPtipoCaracter = :uAPtipoCaracter")})
public class Unidadaprendizaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UAPid")
    private Integer uAPid;
    @Basic(optional = false)
    @Column(name = "UAPclave")
    private int uAPclave;
    @Basic(optional = false)
    @Column(name = "UAPnombre")
    private String uAPnombre;
    @Basic(optional = false)
    @Column(name = "UAPetapaFormacion")
    private String uAPetapaFormacion;
    @Basic(optional = false)
    @Column(name = "UAPcreditos")
    private int uAPcreditos;
    @Column(name = "UAPhorasClase")
    private Integer uAPhorasClase;
    @Column(name = "UAPhorasLaboratorio")
    private Integer uAPhorasLaboratorio;
    @Column(name = "UAPhorasTaller")
    private Integer uAPhorasTaller;
    @Column(name = "UAPhorasClinica")
    private Integer uAPhorasClinica;
    @Column(name = "UAPhorasCampo")
    private Integer uAPhorasCampo;
    @Column(name = "UAPhorasExtraClase")
    private Integer uAPhorasExtraClase;
    @Basic(optional = false)
    @Column(name = "UAPtipoCaracter")
    private String uAPtipoCaracter;
    @ManyToMany(mappedBy = "unidadaprendizajeList")
    private List<Coordinadorareaadministrativa> coordinadorareaadministrativaList;
    @JoinTable(name = "areaconocimiento_has_unidadaprendizaje", joinColumns = {
        @JoinColumn(name = "UnidadAprendizaje_UAPid", referencedColumnName = "UAPid")}, inverseJoinColumns = {
        @JoinColumn(name = "AreaConocimiento_ACOid", referencedColumnName = "ACOid")})
    @ManyToMany
    private List<Areaconocimiento> areaconocimientoList;
    @JoinColumn(name = "CicloEscolar_CESid", referencedColumnName = "CESid")
    @ManyToOne
    private Cicloescolar cicloEscolarCESid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadAprendizajeUAPid")
    private List<UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadAprendizajeUAPid")
    private List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;

    public Unidadaprendizaje() {
    }

    public Unidadaprendizaje(Integer uAPid) {
        this.uAPid = uAPid;
    }

    public Unidadaprendizaje(Cicloescolar cicloEscolarCESid, Integer uAPid, int uAPclave, String uAPnombre, String uAPetapaFormacion,
            int uAPcreditos, Integer uAPhorasClase, Integer uAPhorasLaboratorio, Integer uAPhorasTaller, Integer uAPhorasClinica,
            Integer uAPhorasCampo, Integer uAPhorasExtraClase, String uAPtipoCaracter, Boolean uAPhorasClaseCompletas,
            Boolean uAPhorasLaboratorioCompletas, Boolean uAPhorasTallerCompletas, Boolean uAPhorasCampoCompletas,
            List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList,
            List<UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList,
            List<Areaconocimiento> areaconocimientoList, Unidadaprendizaje unidadAprendizajeUAPid,
            List<Coordinadorareaadministrativa> coordinadorareaadministrativaList) {
        this.cicloEscolarCESid = cicloEscolarCESid;
        this.uAPid = uAPid;
        this.uAPclave = uAPclave;
        this.uAPnombre = uAPnombre;
        this.uAPetapaFormacion = uAPetapaFormacion;
        this.uAPcreditos = uAPcreditos;
        this.uAPhorasClase = uAPhorasClase;
        this.uAPhorasLaboratorio = uAPhorasLaboratorio;
        this.uAPhorasTaller = uAPhorasTaller;
        this.uAPhorasClinica = uAPhorasClinica;
        this.uAPhorasCampo = uAPhorasCampo;
        this.uAPhorasExtraClase = uAPhorasExtraClase;
        this.uAPtipoCaracter = uAPtipoCaracter;
        this.coordinadorareaadministrativaList = coordinadorareaadministrativaList;
        this.areaconocimientoList = areaconocimientoList;
        this.unidadaprendizajeTieneContenidotematicoList = unidadaprendizajeTieneContenidotematicoList;
        this.unidadaprendizajeImparteProfesorList = unidadaprendizajeImparteProfesorList;
    }

    public Unidadaprendizaje(Cicloescolar cicloEscolarCESid, Integer uAPid, int uAPclave, String uAPnombre, String uAPetapaFormacion, int uAPcreditos,
            String uAPtipoCaracter, List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList,
            List<UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList,
            List<Areaconocimiento> areaconocimientoList, Unidadaprendizaje unidadAprendizajeUAPid,
            List<Coordinadorareaadministrativa> coordinadorareaadministrativaList) {
        this.cicloEscolarCESid = cicloEscolarCESid;
        this.uAPid = uAPid;
        this.uAPclave = uAPclave;
        this.uAPnombre = uAPnombre;
        this.uAPetapaFormacion = uAPetapaFormacion;
        this.uAPcreditos = uAPcreditos;
        this.uAPtipoCaracter = uAPtipoCaracter;
        this.coordinadorareaadministrativaList = coordinadorareaadministrativaList;
        this.areaconocimientoList = areaconocimientoList;
        this.unidadaprendizajeTieneContenidotematicoList = unidadaprendizajeTieneContenidotematicoList;
        this.unidadaprendizajeImparteProfesorList = unidadaprendizajeImparteProfesorList;
    }

    public Unidadaprendizaje(Integer uAPid, int uAPclave, String uAPnombre, String uAPetapaFormacion, int uAPcreditos, String uAPtipoCaracter) {
        this.uAPid = uAPid;
        this.uAPclave = uAPclave;
        this.uAPnombre = uAPnombre;
        this.uAPetapaFormacion = uAPetapaFormacion;
        this.uAPcreditos = uAPcreditos;
        this.uAPtipoCaracter = uAPtipoCaracter;
    }

    public Unidadaprendizaje(Unidadaprendizaje unidadaprendizaje) {
        this.cicloEscolarCESid = unidadaprendizaje.cicloEscolarCESid;
        this.uAPid = unidadaprendizaje.uAPid;
        this.uAPclave = unidadaprendizaje.uAPclave;
        this.uAPnombre = unidadaprendizaje.uAPnombre;
        this.uAPetapaFormacion = unidadaprendizaje.uAPetapaFormacion;
        this.uAPcreditos = unidadaprendizaje.uAPcreditos;
        this.uAPhorasClase = unidadaprendizaje.uAPhorasClase;
        this.uAPhorasLaboratorio = unidadaprendizaje.uAPhorasLaboratorio;
        this.uAPhorasTaller = unidadaprendizaje.uAPhorasTaller;
        this.uAPhorasClinica = unidadaprendizaje.uAPhorasClinica;
        this.uAPhorasCampo = unidadaprendizaje.uAPhorasCampo;
        this.uAPhorasExtraClase = unidadaprendizaje.uAPhorasExtraClase;
        this.uAPtipoCaracter = unidadaprendizaje.uAPtipoCaracter;
        this.coordinadorareaadministrativaList = unidadaprendizaje.coordinadorareaadministrativaList;
        this.areaconocimientoList = unidadaprendizaje.areaconocimientoList;
        this.unidadaprendizajeTieneContenidotematicoList = unidadaprendizaje.unidadaprendizajeTieneContenidotematicoList;
        this.unidadaprendizajeImparteProfesorList = unidadaprendizaje.unidadaprendizajeImparteProfesorList;
    }

    public Integer getUAPid() {
        return uAPid;
    }

    public void setUAPid(Integer uAPid) {
        this.uAPid = uAPid;
    }

    public int getUAPclave() {
        return uAPclave;
    }

    public void setUAPclave(int uAPclave) {
        this.uAPclave = uAPclave;
    }

    public String getUAPnombre() {
        return uAPnombre;
    }

    public void setUAPnombre(String uAPnombre) {
        this.uAPnombre = uAPnombre;
    }

    public String getUAPetapaFormacion() {
        return uAPetapaFormacion;
    }

    public void setUAPetapaFormacion(String uAPetapaFormacion) {
        this.uAPetapaFormacion = uAPetapaFormacion;
    }

    public int getUAPcreditos() {
        return uAPcreditos;
    }

    public void setUAPcreditos(int uAPcreditos) {
        this.uAPcreditos = uAPcreditos;
    }

    public Integer getUAPhorasClase() {
        return uAPhorasClase;
    }

    public void setUAPhorasClase(Integer uAPhorasClase) {
        this.uAPhorasClase = uAPhorasClase;
    }

    public Integer getUAPhorasLaboratorio() {
        return uAPhorasLaboratorio;
    }

    public void setUAPhorasLaboratorio(Integer uAPhorasLaboratorio) {
        this.uAPhorasLaboratorio = uAPhorasLaboratorio;
    }

    public Integer getUAPhorasTaller() {
        return uAPhorasTaller;
    }

    public void setUAPhorasTaller(Integer uAPhorasTaller) {
        this.uAPhorasTaller = uAPhorasTaller;
    }

    public Integer getUAPhorasClinica() {
        return uAPhorasClinica;
    }

    public void setUAPhorasClinica(Integer uAPhorasClinica) {
        this.uAPhorasClinica = uAPhorasClinica;
    }

    public Integer getUAPhorasCampo() {
        return uAPhorasCampo;
    }

    public void setUAPhorasCampo(Integer uAPhorasCampo) {
        this.uAPhorasCampo = uAPhorasCampo;
    }

    public Integer getUAPhorasExtraClase() {
        return uAPhorasExtraClase;
    }

    public void setUAPhorasExtraClase(Integer uAPhorasExtraClase) {
        this.uAPhorasExtraClase = uAPhorasExtraClase;
    }

    public String getUAPtipoCaracter() {
        return uAPtipoCaracter;
    }

    public void setUAPtipoCaracter(String uAPtipoCaracter) {
        this.uAPtipoCaracter = uAPtipoCaracter;
    }

    @XmlTransient
    public List<Coordinadorareaadministrativa> getCoordinadorareaadministrativaList() {
        return coordinadorareaadministrativaList;
    }

    public void setCoordinadorareaadministrativaList(List<Coordinadorareaadministrativa> coordinadorareaadministrativaList) {
        this.coordinadorareaadministrativaList = coordinadorareaadministrativaList;
    }

    @XmlTransient
    public List<Areaconocimiento> getAreaconocimientoList() {
        return areaconocimientoList;
    }

    public void setAreaconocimientoList(List<Areaconocimiento> areaconocimientoList) {
        this.areaconocimientoList = areaconocimientoList;
    }

    public Cicloescolar getCicloEscolarCESid() {
        return cicloEscolarCESid;
    }

    public void setCicloEscolarCESid(Cicloescolar cicloEscolarCESid) {
        this.cicloEscolarCESid = cicloEscolarCESid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uAPid != null ? uAPid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidadaprendizaje)) {
            return false;
        }

        Unidadaprendizaje other = (Unidadaprendizaje) object;

        return !((this.uAPid == null && other.uAPid != null) || (this.uAPid != null && !this.uAPid.equals(other.uAPid)));
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Unidadaprendizaje[ uAPid=" + uAPid + " ]";
    }
}
