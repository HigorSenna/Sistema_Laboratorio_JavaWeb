/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.dao;

import entity.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Exame;
import entity.PlanoSaude;
import entity.Usuario;
import entity.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Higor Senna
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exame idExame = cliente.getIdExame();
            if (idExame != null) {
                idExame = em.getReference(idExame.getClass(), idExame.getIdExame());
                cliente.setIdExame(idExame);
            }
            PlanoSaude idPlanoSaude = cliente.getIdPlanoSaude();
            if (idPlanoSaude != null) {
                idPlanoSaude = em.getReference(idPlanoSaude.getClass(), idPlanoSaude.getIdPlanoSaude());
                cliente.setIdPlanoSaude(idPlanoSaude);
            }
            Usuario idUsuario = cliente.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cliente.setIdUsuario(idUsuario);
            }
            em.persist(cliente);
            if (idExame != null) {
                idExame.getClienteList().add(cliente);
                idExame = em.merge(idExame);
            }
            if (idPlanoSaude != null) {
                idPlanoSaude.getClienteList().add(cliente);
                idPlanoSaude = em.merge(idPlanoSaude);
            }
            if (idUsuario != null) {
                idUsuario.getClienteList().add(cliente);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            Exame idExameOld = persistentCliente.getIdExame();
            Exame idExameNew = cliente.getIdExame();
            PlanoSaude idPlanoSaudeOld = persistentCliente.getIdPlanoSaude();
            PlanoSaude idPlanoSaudeNew = cliente.getIdPlanoSaude();
            Usuario idUsuarioOld = persistentCliente.getIdUsuario();
            Usuario idUsuarioNew = cliente.getIdUsuario();
            if (idExameNew != null) {
                idExameNew = em.getReference(idExameNew.getClass(), idExameNew.getIdExame());
                cliente.setIdExame(idExameNew);
            }
            if (idPlanoSaudeNew != null) {
                idPlanoSaudeNew = em.getReference(idPlanoSaudeNew.getClass(), idPlanoSaudeNew.getIdPlanoSaude());
                cliente.setIdPlanoSaude(idPlanoSaudeNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cliente.setIdUsuario(idUsuarioNew);
            }
            cliente = em.merge(cliente);
            if (idExameOld != null && !idExameOld.equals(idExameNew)) {
                idExameOld.getClienteList().remove(cliente);
                idExameOld = em.merge(idExameOld);
            }
            if (idExameNew != null && !idExameNew.equals(idExameOld)) {
                idExameNew.getClienteList().add(cliente);
                idExameNew = em.merge(idExameNew);
            }
            if (idPlanoSaudeOld != null && !idPlanoSaudeOld.equals(idPlanoSaudeNew)) {
                idPlanoSaudeOld.getClienteList().remove(cliente);
                idPlanoSaudeOld = em.merge(idPlanoSaudeOld);
            }
            if (idPlanoSaudeNew != null && !idPlanoSaudeNew.equals(idPlanoSaudeOld)) {
                idPlanoSaudeNew.getClienteList().add(cliente);
                idPlanoSaudeNew = em.merge(idPlanoSaudeNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getClienteList().remove(cliente);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getClienteList().add(cliente);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Exame idExame = cliente.getIdExame();
            if (idExame != null) {
                idExame.getClienteList().remove(cliente);
                idExame = em.merge(idExame);
            }
            PlanoSaude idPlanoSaude = cliente.getIdPlanoSaude();
            if (idPlanoSaude != null) {
                idPlanoSaude.getClienteList().remove(cliente);
                idPlanoSaude = em.merge(idPlanoSaude);
            }
            Usuario idUsuario = cliente.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getClienteList().remove(cliente);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
