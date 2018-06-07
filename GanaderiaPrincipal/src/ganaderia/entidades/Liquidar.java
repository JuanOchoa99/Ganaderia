/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganaderia.entidades;

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
 * @author JJ
 */
@Entity
@Table(name = "liquidar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liquidar.findAll", query = "SELECT l FROM Liquidar l")
    , @NamedQuery(name = "Liquidar.findById", query = "SELECT l FROM Liquidar l WHERE l.id = :id")
    , @NamedQuery(name = "Liquidar.findByVenta", query = "SELECT l FROM Liquidar l WHERE l.venta = :venta")})
public class Liquidar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "venta")
    private Double venta;
    @JoinColumn(name = "ida", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Agregar ida;

    public Liquidar() {
    }

    public Liquidar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Agregar getIda() {
        return ida;
    }

    public void setIda(Agregar ida) {
        this.ida = ida;
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
        if (!(object instanceof Liquidar)) {
            return false;
        }
        Liquidar other = (Liquidar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ganaderia.controladores.Liquidar[ id=" + id + " ]";
    }
    
}
