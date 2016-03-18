/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Cliente;
import entity.PlanoSaude;
import entity.dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Higor Senna
 */
public class PlanoSaudeJpaController implements Serializable {

    public PlanoSaudeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanoSaude planoSaude) {
        if (planoSaude.getClienteList() == null) {
            planoSaude.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : planoSaude.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            planoSaude.setClienteList(attachedClienteList);
            em.persist(planoSaude);
            for (Cliente clienteListCliente : planoSaude.getClienteList()) {
                PlanoSaude oldIdPlanoSaudeOfClienteListCliente = clienteListCliente.getIdPlanoSaude();
                clienteListCliente.setIdPlanoSaude(planoSaude);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdPlanoSaudeOfClienteListCliente != null) {
                    oldIdPlanoSaudeOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdPlanoSaudeOfClienteListCliente = em.merge(oldIdPlanoSaudeOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanoSaude planoSaude) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanoSaude persistentPlanoSaude = em.find(PlanoSaude.class, planoSaude.getIdPlanoSaude());
            List<Cliente> clienteListOld = persistentPlanoSaude.getClienteList();
            List<Cliente> clienteListNew = planoSaude.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            planoSaude.setClienteList(clienteListNew);
            planoSaude = em.merge(planoSaude);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdPlanoSaude(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    PlanoSaude oldIdPlanoSaudeOfClienteListNewCliente = clienteListNewCliente.getIdPlanoSaude();
                    clienteListNewCliente.setIdPlanoSaude(planoSaude);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdPlanoSaudeOfClienteListNewCliente != null && !oldIdPlanoSaudeOfClienteListNewCliente.equals(planoSaude)) {
                        oldIdPlanoSaudeOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdPlanoSaudeOfClienteListNewCliente = em.merge(oldIdPlanoSaudeOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planoSaude.getIdPlanoSaude();
                if (findPlanoSaude(id) == null) {
                    throw new NonexistentEntityException("The planoSaude with id " + id + " no longer exists.");
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
            PlanoSaude planoSaude;
            try {
                planoSaude = em.getReference(PlanoSaude.class, id);
                planoSaude.getIdPlanoSaude();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planoSaude with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = planoSaude.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdPlanoSaude(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(planoSaude);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanoSaude> findPlanoSaudeEntities() {
        return findPlanoSaudeEntities(true, -1, -1);
    }

    public List<PlanoSaude> findPlanoSaudeEntities(int maxResults, int firstResult) {
        return findPlanoSaudeEntities(false, maxResults, firstResult);
    }

    private List<PlanoSaude> findPlanoSaudeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanoSaude.class));
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

    public PlanoSaude findPlanoSaude(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanoSaude.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanoSaudeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanoSaude> rt = cq.from(PlanoSaude.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
