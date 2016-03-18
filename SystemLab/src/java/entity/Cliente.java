/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Higor Senna
 */
@Entity
@Table(catalog = "laboratorio", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByNomeCliente", query = "SELECT c FROM Cliente c WHERE c.nomeCliente = :nomeCliente"),
    @NamedQuery(name = "Cliente.findByCpfCliente", query = "SELECT c FROM Cliente c WHERE c.cpfCliente = :cpfCliente"),
    @NamedQuery(name = "Cliente.findByTelefoneCliente", query = "SELECT c FROM Cliente c WHERE c.telefoneCliente = :telefoneCliente"),
    @NamedQuery(name = "Cliente.findBySexoCliente", query = "SELECT c FROM Cliente c WHERE c.sexoCliente = :sexoCliente"),
    @NamedQuery(name = "Cliente.findByEnderecoCliente", query = "SELECT c FROM Cliente c WHERE c.enderecoCliente = :enderecoCliente"),
    @NamedQuery(name = "Cliente.findByPossuiPlanoSaude", query = "SELECT c FROM Cliente c WHERE c.possuiPlanoSaude = :possuiPlanoSaude")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idCliente;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nomeCliente;
    @Basic(optional = false)
    @Column(nullable = false, length = 14)
    private String cpfCliente;
    @Column(length = 15)
    private String telefoneCliente;
    @Basic(optional = false)
    @Column(nullable = false, length = 2)
    private String sexoCliente;
    @Basic(optional = false)
    @Column(nullable = false, length = 60)
    private String enderecoCliente;
    private Short possuiPlanoSaude;
    @JoinColumn(name = "idExame", referencedColumnName = "idExame")
    @ManyToOne(fetch = FetchType.LAZY)
    private Exame idExame;
    @JoinColumn(name = "idPlanoSaude", referencedColumnName = "idPlanoSaude")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlanoSaude idPlanoSaude;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nomeCliente, String cpfCliente, String sexoCliente, String enderecoCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
        this.sexoCliente = sexoCliente;
        this.enderecoCliente = enderecoCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public String getSexoCliente() {
        return sexoCliente;
    }

    public void setSexoCliente(String sexoCliente) {
        this.sexoCliente = sexoCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    public Short getPossuiPlanoSaude() {
        return possuiPlanoSaude;
    }

    public void setPossuiPlanoSaude(Short possuiPlanoSaude) {
        this.possuiPlanoSaude = possuiPlanoSaude;
    }

    public Exame getIdExame() {
        return idExame;
    }

    public void setIdExame(Exame idExame) {
        this.idExame = idExame;
    }

    public PlanoSaude getIdPlanoSaude() {
        return idPlanoSaude;
    }

    public void setIdPlanoSaude(PlanoSaude idPlanoSaude) {
        this.idPlanoSaude = idPlanoSaude;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
