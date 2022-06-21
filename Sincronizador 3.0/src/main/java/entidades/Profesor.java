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
 * @author Oscar
 */
@Entity
@Table(name = "profesor")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p")
        , @NamedQuery(name = "Profesor.findByPROid", query = "SELECT p FROM Profesor p WHERE p.pROid = :pROid")
        , @NamedQuery(name = "Profesor.findByPROnumeroEmpleado", query = "SELECT p FROM Profesor p WHERE p.pROnumeroEmpleado = :pROnumeroEmpleado")
        , @NamedQuery(name = "Profesor.findByPROnombre", query = "SELECT p FROM Profesor p WHERE p.pROnombre = :pROnombre")
        , @NamedQuery(name = "Profesor.findByPROapellidoPaterno", query = "SELECT p FROM Profesor p WHERE p.pROapellidoPaterno = :pROapellidoPaterno")
        , @NamedQuery(name = "Profesor.findByPROapellidoMaterno", query = "SELECT p FROM Profesor p WHERE p.pROapellidoMaterno = :pROapellidoMaterno")
        , @NamedQuery(name = "Profesor.findByPROrfc", query = "SELECT p FROM Profesor p WHERE p.pROrfc = :pROrfc")})
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROid")
    private Integer pROid;
    @Basic(optional = false)
    @Column(name = "PROnumeroEmpleado")
    private int pROnumeroEmpleado;
    @Basic(optional = false)
    @Column(name = "PROnombre")
    private String pROnombre;
    @Basic(optional = false)
    @Column(name = "PROapellidoPaterno")
    private String pROapellidoPaterno;
    @Basic(optional = false)
    @Column(name = "PROapellidoMaterno")
    private String pROapellidoMaterno;
    @Basic(optional = false)
    @Column(name = "PROrfc")
    private String pROrfc;
    @ManyToMany(mappedBy = "profesorList")
    private List<Areaconocimiento> areaconocimientoList;
    @JoinTable(name = "profesor_pertenece_unidadacademica", joinColumns = {
            @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid")}, inverseJoinColumns = {
            @JoinColumn(name = "UnidadAcademica_UACid", referencedColumnName = "UACid")})
    @ManyToMany
    private List<Unidadacademica> unidadacademicaList;
    @JoinTable(name = "responsableprogramaeducativo", joinColumns = {
            @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid")}, inverseJoinColumns = {
            @JoinColumn(name = "ProgramaEducativo_PEDid", referencedColumnName = "PEDid")})
    @ManyToMany
    private List<Programaeducativo> programaeducativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorPROid")
    private List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorPROid")
    private List<Coordinadorareaadministrativa> coordinadorareaadministrativaList;
    @JoinColumn(name = "Usuario_USUid", referencedColumnName = "USUid")
    @ManyToOne
    private Usuario usuarioUSUid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesor")
    private List<ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList;

    public Profesor() {
    }

    public Profesor(Integer pROid) {
        this.pROid = pROid;
    }

    public Profesor(Integer pROid, int pROnumeroEmpleado, String pROnombre, String pROapellidoPaterno, String pROapellidoMaterno, String pROrfc) {
        this.pROid = pROid;
        this.pROnumeroEmpleado = pROnumeroEmpleado;
        this.pROnombre = pROnombre;
        this.pROapellidoPaterno = pROapellidoPaterno;
        this.pROapellidoMaterno = pROapellidoMaterno;
        this.pROrfc = pROrfc;
    }

    public Integer getPROid() {
        return pROid;
    }

    public void setPROid(Integer pROid) {
        this.pROid = pROid;
    }

    public int getPROnumeroEmpleado() {
        return pROnumeroEmpleado;
    }

    public void setPROnumeroEmpleado(int pROnumeroEmpleado) {
        this.pROnumeroEmpleado = pROnumeroEmpleado;
    }

    public String getPROnombre() {
        return pROnombre;
    }

    public void setPROnombre(String pROnombre) {
        this.pROnombre = pROnombre;
    }

    public String getPROapellidoPaterno() {
        return pROapellidoPaterno;
    }

    public void setPROapellidoPaterno(String pROapellidoPaterno) {
        this.pROapellidoPaterno = pROapellidoPaterno;
    }

    public String getPROapellidoMaterno() {
        return pROapellidoMaterno;
    }

    public void setPROapellidoMaterno(String pROapellidoMaterno) {
        this.pROapellidoMaterno = pROapellidoMaterno;
    }

    public String getPROrfc() {
        return pROrfc;
    }

    public void setPROrfc(String pROrfc) {
        this.pROrfc = pROrfc;
    }

    @XmlTransient
    public List<Areaconocimiento> getAreaconocimientoList() {
        return areaconocimientoList;
    }

    public void setAreaconocimientoList(List<Areaconocimiento> areaconocimientoList) {
        this.areaconocimientoList = areaconocimientoList;
    }

    @XmlTransient
    public List<Unidadacademica> getUnidadacademicaList() {
        return unidadacademicaList;
    }

    public void setUnidadacademicaList(List<Unidadacademica> unidadacademicaList) {
        this.unidadacademicaList = unidadacademicaList;
    }

    @XmlTransient
    public List<Programaeducativo> getProgramaeducativoList() {
        return programaeducativoList;
    }

    public void setProgramaeducativoList(List<Programaeducativo> programaeducativoList) {
        this.programaeducativoList = programaeducativoList;
    }

    @XmlTransient
    public List<UnidadaprendizajeImparteProfesor> getUnidadaprendizajeImparteProfesorList() {
        return unidadaprendizajeImparteProfesorList;
    }

    public void setUnidadaprendizajeImparteProfesorList(List<UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList) {
        this.unidadaprendizajeImparteProfesorList = unidadaprendizajeImparteProfesorList;
    }

    @XmlTransient
    public List<Coordinadorareaadministrativa> getCoordinadorareaadministrativaList() {
        return coordinadorareaadministrativaList;
    }

    public void setCoordinadorareaadministrativaList(List<Coordinadorareaadministrativa> coordinadorareaadministrativaList) {
        this.coordinadorareaadministrativaList = coordinadorareaadministrativaList;
    }

    public Usuario getUsuarioUSUid() {
        return usuarioUSUid;
    }

    public void setUsuarioUSUid(Usuario usuarioUSUid) {
        this.usuarioUSUid = usuarioUSUid;
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
        hash += (pROid != null ? pROid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.pROid == null && other.pROid != null) || (this.pROid != null && !this.pROid.equals(other.pROid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Profesor[ pROid=" + pROid + " ]";
    }

}
