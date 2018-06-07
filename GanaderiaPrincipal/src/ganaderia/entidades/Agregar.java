/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganaderia.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JJ
 */
@Entity
@Table(name = "agregar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agregar.findAll", query = "SELECT a FROM Agregar a")
    , @NamedQuery(name = "Agregar.findById", query = "SELECT a FROM Agregar a WHERE a.id = :id")
    , @NamedQuery(name = "Agregar.findByNumero", query = "SELECT a FROM Agregar a WHERE a.numero = :numero")
    , @NamedQuery(name = "Agregar.findByCosto", query = "SELECT a FROM Agregar a WHERE a.costo = :costo")})
public class Agregar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @Column(name = "costo")
    private double costo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ida")
    private List<Liquidar> liquidarList;

    public Agregar() {
    }

    public Agregar(Integer id) {
        this.id = id;
    }

    public Agregar(Integer id, int numero, double costo) {
        this.id = id;
        this.numero = numero;
        this.costo = costo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @XmlTransient
    public List<Liquidar> getLiquidarList() {
        return liquidarList;
    }

    public void setLiquidarList(List<Liquidar> liquidarList) {
        this.liquidarList = liquidarList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agregar)) {
            return false;
        }
        Agregar other = (Agregar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ganaderia.controladores.Agregar[ id=" + id + " ]";
    }
    
}
