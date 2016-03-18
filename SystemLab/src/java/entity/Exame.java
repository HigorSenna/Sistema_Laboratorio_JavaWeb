/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Higor Senna
 */
@Entity
@Table(catalog = "laboratorio", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exame.findAll", query = "SELECT e FROM Exame e"),
    @NamedQuery(name = "Exame.findByIdExame", query = "SELECT e FROM Exame e WHERE e.idExame = :idExame"),
    @NamedQuery(name = "Exame.findByPrecoExame", query = "SELECT e FROM Exame e WHERE e.precoExame = :precoExame"),
    @NamedQuery(name = "Exame.findByDescricaoExame", query = "SELECT e FROM Exame e WHERE e.descricaoExame = :descricaoExame"),
    @NamedQuery(name = "Exame.findByTempoJejumExame", query = "SELECT e FROM Exame e WHERE e.tempoJejumExame = :tempoJejumExame"),
    @NamedQuery(name = "Exame.findByDataEntregaExame", query = "SELECT e FROM Exame e WHERE e.dataEntregaExame = :dataEntregaExame")})
public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idExame;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoExame;
    @Column(length = 400)
    private String descricaoExame;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String tempoJejumExame;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEntregaExame;
    @OneToMany(mappedBy = "idExame", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;

    public Exame() {
    }

    public Exame(Integer idExame) {
        this.idExame = idExame;
    }

    public Exame(Integer idExame, BigDecimal precoExame, String tempoJejumExame, Date dataEntregaExame) {
        this.idExame = idExame;
        this.precoExame = precoExame;
        this.tempoJejumExame = tempoJejumExame;
        this.dataEntregaExame = dataEntregaExame;
    }

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public BigDecimal getPrecoExame() {
        return precoExame;
    }

    public void setPrecoExame(BigDecimal precoExame) {
        this.precoExame = precoExame;
    }

    public String getDescricaoExame() {
        return descricaoExame;
    }

    public void setDescricaoExame(String descricaoExame) {
        this.descricaoExame = descricaoExame;
    }

    public String getTempoJejumExame() {
        return tempoJejumExame;
    }

    public void setTempoJejumExame(String tempoJejumExame) {
        this.tempoJejumExame = tempoJejumExame;
    }

    public Date getDataEntregaExame() {
        return dataEntregaExame;
    }

    public void setDataEntregaExame(Date dataEntregaExame) {
        this.dataEntregaExame = dataEntregaExame;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExame != null ? idExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exame)) {
            return false;
        }
        Exame other = (Exame) object;
        if ((this.idExame == null && other.idExame != null) || (this.idExame != null && !this.idExame.equals(other.idExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Exame[ idExame=" + idExame + " ]";
    }
    
}
