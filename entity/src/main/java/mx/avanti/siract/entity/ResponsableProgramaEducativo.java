package mx.avanti.siract.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Kevin Arizaga
 */
@Entity
@Table(name = "responsableprogramaeducativo")
public class ResponsableProgramaEducativo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String TABLE_NAME = "responsableprogramaeducativo";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RPEid")
    private Integer RPEid;

    @ManyToOne
    @JoinColumn(name = "Profesor_PROid", referencedColumnName = "PROid")
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "ProgramaEducativo_PEDid", referencedColumnName = "PEDid")
    private Programaeducativo programaeducativo;

    @ManyToOne
    @JoinColumn(name = "CESid", referencedColumnName = "CESid")
    private Cicloescolar cicloescolar;

    @Column(name = "version")
    private Integer version;

    public ResponsableProgramaEducativo() {
        profesor = new Profesor();
        programaeducativo = new Programaeducativo();
        cicloescolar = new Cicloescolar();
    }

    /**
     * Explicación del siguiente método:
     * Método que recibe solamente RPEid y establece su valor.
     *
     * @param RPEid
     */
    public ResponsableProgramaEducativo(Integer RPEid) {
        this.RPEid = RPEid;
    }

    /**
     * Método que sirve para copiar los atributos de otro objeto de ResponsableProgramaEduacativo
     *
     * @param responsableProgramaEducativo
     */
    public ResponsableProgramaEducativo(ResponsableProgramaEducativo responsableProgramaEducativo) {
        this.profesor = responsableProgramaEducativo.getProfesor();
        this.programaeducativo = responsableProgramaEducativo.getProgramaeducativo();
        this.cicloescolar = responsableProgramaEducativo.getCicloEscolar();
    }

    /**
     * Explicación del siguiente método:
     * Método que recibe todos las variables para establecer sus valores, exceptó la version.
     *
     * @param profesor
     * @param programaeducativo
     * @param cicloescolar
     */
    public ResponsableProgramaEducativo(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
        this.profesor = profesor;
        this.programaeducativo = programaeducativo;
        this.cicloescolar = cicloescolar;
        version = 1;
    }

    public ResponsableProgramaEducativo(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar, Integer version) {
        this.profesor = profesor;
        this.programaeducativo = programaeducativo;
        this.cicloescolar = cicloescolar;
        this.version = version;
    }

    public ResponsableProgramaEducativo(Profesor profesor) {
        this.profesor = profesor;
        this.programaeducativo = new Programaeducativo();
        this.cicloescolar = new Cicloescolar();
    }

    public ResponsableProgramaEducativo(Programaeducativo programaeducativo) {
        this.profesor = new Profesor();
        this.programaeducativo = programaeducativo;
        this.cicloescolar = new Cicloescolar();
    }

    public ResponsableProgramaEducativo(Cicloescolar cicloescolar) {
        this.profesor = new Profesor();
        this.programaeducativo = new Programaeducativo();
        this.cicloescolar = cicloescolar;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRPEid() {
        return RPEid;
    }

    public void setRPEid(Integer RPEid) {
        this.RPEid = RPEid;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Programaeducativo getProgramaeducativo() {
        return programaeducativo;
    }

    public void setProgramaeducativo(Programaeducativo programaeducativo) {
        this.programaeducativo = programaeducativo;
    }

    public Cicloescolar getCicloEscolar() {
        return cicloescolar;
    }

    public void setCicloEscolar(Cicloescolar cicloescolar) {
        this.cicloescolar = cicloescolar;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (RPEid != null ? RPEid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof ResponsableProgramaEducativo) && (RPEid != null)
                ? RPEid.equals(((ResponsableProgramaEducativo) object).RPEid)
                : (object == this);
    }


    @Override
    public String toString() {
        return "mx.avanti.siract.entity.ResponsableProgramaEducativo[ RPEid =" + RPEid + " ]";
    }

}
