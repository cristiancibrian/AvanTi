/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Ruben Vallez
 */
@Entity
@Table(name = "articulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByIdarticulos", query = "SELECT a FROM Articulos a WHERE a.idarticulos = :idarticulos")
    , @NamedQuery(name = "Articulos.findByNumeroArticulo", query = "SELECT a FROM Articulos a WHERE a.numeroArticulo = :numeroArticulo")
    , @NamedQuery(name = "Articulos.findByContenido", query = "SELECT a FROM Articulos a WHERE a.contenido = :contenido")})
public class Articulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarticulos")
    private Integer idarticulos;
    @Basic(optional = false)
    @Column(name = "numeroArticulo")
    private int numeroArticulo;
    @Basic(optional = false)
    @Column(name = "contenido")
    private String contenido;
    @JoinColumn(name = "CESId", referencedColumnName = "CESid")
    @ManyToOne
    private Cicloescolar cESId;

    public Articulos() {
    }

    public Articulos(Integer idarticulos) {
        this.idarticulos = idarticulos;
    }

    public Articulos(Integer idarticulos, int numeroArticulo, String contenido) {
        this.idarticulos = idarticulos;
        this.numeroArticulo = numeroArticulo;
        this.contenido = contenido;
    }
    public Cicloescolar getCESId() {
        return cESId;
    }

    public void setCESId(Cicloescolar cESId) {
        this.cESId = cESId;
    }


    public Integer getIdarticulos() {
        return idarticulos;
    }

    public void setIdarticulos(Integer idarticulos) {
        this.idarticulos = idarticulos;
    }

    public int getNumeroArticulo() {
        return numeroArticulo;
    }

    public void setNumeroArticulo(int numeroArticulo) {
        this.numeroArticulo = numeroArticulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarticulos != null ? idarticulos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.idarticulos == null && other.idarticulos != null) || (this.idarticulos != null && !this.idarticulos.equals(other.idarticulos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.avanti.siract.entity.Articulos[ idarticulos=" + idarticulos + " ]";
    }
    
}
