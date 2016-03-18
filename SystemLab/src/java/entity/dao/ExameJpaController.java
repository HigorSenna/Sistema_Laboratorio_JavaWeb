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
import entity.Exame;
import entity.dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Higor Senna
 */
public class ExameJpaController implements Serializable {

    public ExameJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exame exame) {
        if (exame.getClienteList() == null) {
            exame.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : exame.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            exame.setClienteList(attachedClienteList);
            em.persist(exame);
            for (Cliente clienteListCliente : exame.getClienteList()) {
                Exame oldIdExameOfClienteListCliente = clienteListCliente.getIdExame();
                clienteListCliente.setIdExame(exame);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdExameOfClienteListCliente != null) {
                    oldIdExameOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdExameOfClienteListCliente = em.merge(oldIdExameOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Exame exame) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exame persistentExame = em.find(Exame.class, exame.getIdExame());
            List<Cliente> clienteListOld = persistentExame.getClienteList();
            List<Cliente> clienteListNew = exame.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            exame.setClienteList(clienteListNew);
            exame = em.merge(exame);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdExame(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Exame oldIdExameOfClienteListNewCliente = clienteListNewCliente.getIdExame();
                    clienteListNewCliente.setIdExame(exame);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdExameOfClienteListNewCliente != null && !oldIdExameOfClienteListNewCliente.equals(exame)) {
                        oldIdExameOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdExameOfClienteListNewCliente = em.merge(oldIdExameOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exame.getIdExame();
                if (findExame(id) == null) {
                    throw new NonexistentEntityException("The exame with id " + id + " no longer exists.");
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
            Exame exame;
            try {
                exame = em.getReference(Exame.class, id);
                exame.getIdExame();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exame with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = exame.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdExame(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(exame);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Exame> findExameEntities() {
        return findExameEntities(true, -1, -1);
    }

    public List<Exame> findExameEntities(int maxResults, int firstResult) {
        return findExameEntities(false, maxResults, firstResult);
    }

    private List<Exame> findExameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exame.class));
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

    public Exame findExame(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exame.class, id);
        } finally {
            em.close();
        }
    }

    public int getExameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exame> rt = cq.from(Exame.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
