/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Higor Senna
 */
@Entity
@Table(name = "plano_saude", catalog = "laboratorio", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanoSaude.findAll", query = "SELECT p FROM PlanoSaude p"),
    @NamedQuery(name = "PlanoSaude.findByIdPlanoSaude", query = "SELECT p FROM PlanoSaude p WHERE p.idPlanoSaude = :idPlanoSaude"),
    @NamedQuery(name = "PlanoSaude.findByNomePlanoSaude", query = "SELECT p FROM PlanoSaude p WHERE p.nomePlanoSaude = :nomePlanoSaude"),
    @NamedQuery(name = "PlanoSaude.findByValorPlanoSaude", query = "SELECT p FROM PlanoSaude p WHERE p.valorPlanoSaude = :valorPlanoSaude")})
public class PlanoSaude implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idPlanoSaude;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nomePlanoSaude;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPlanoSaude;
    @OneToMany(mappedBy = "idPlanoSaude", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;

    public PlanoSaude() {
    }

    public PlanoSaude(Integer idPlanoSaude) {
        this.idPlanoSaude = idPlanoSaude;
    }

    public PlanoSaude(Integer idPlanoSaude, String nomePlanoSaude, BigDecimal valorPlanoSaude) {
        this.idPlanoSaude = idPlanoSaude;
        this.nomePlanoSaude = nomePlanoSaude;
        this.valorPlanoSaude = valorPlanoSaude;
    }

    public Integer getIdPlanoSaude() {
        return idPlanoSaude;
    }

    public void setIdPlanoSaude(Integer idPlanoSaude) {
        this.idPlanoSaude = idPlanoSaude;
    }

    public String getNomePlanoSaude() {
        return nomePlanoSaude;
    }

    public void setNomePlanoSaude(String nomePlanoSaude) {
        this.nomePlanoSaude = nomePlanoSaude;
    }

    public BigDecimal getValorPlanoSaude() {
        return valorPlanoSaude;
    }

    public void setValorPlanoSaude(BigDecimal valorPlanoSaude) {
        this.valorPlanoSaude = valorPlanoSaude;
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
        hash += (idPlanoSaude != null ? idPlanoSaude.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanoSaude)) {
            return false;
        }
        PlanoSaude other = (PlanoSaude) object;
        if ((this.idPlanoSaude == null && other.idPlanoSaude != null) || (this.idPlanoSaude != null && !this.idPlanoSaude.equals(other.idPlanoSaude))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PlanoSaude[ idPlanoSaude=" + idPlanoSaude + " ]";
    }
    
}
