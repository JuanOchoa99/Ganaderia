/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganaderia.controladores;

import ganaderia.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ganaderia.entidades.Agregar;
import ganaderia.entidades.Liquidar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JJ
 */
public class LiquidarJpaController implements Serializable {

    public LiquidarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Liquidar liquidar) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agregar ida = liquidar.getIda();
            if (ida != null) {
                ida = em.getReference(ida.getClass(), ida.getId());
                liquidar.setIda(ida);
            }
            em.persist(liquidar);
            if (ida != null) {
                ida.getLiquidarList().add(liquidar);
                ida = em.merge(ida);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Liquidar liquidar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Liquidar persistentLiquidar = em.find(Liquidar.class, liquidar.getId());
            Agregar idaOld = persistentLiquidar.getIda();
            Agregar idaNew = liquidar.getIda();
            if (idaNew != null) {
                idaNew = em.getReference(idaNew.getClass(), idaNew.getId());
                liquidar.setIda(idaNew);
            }
            liquidar = em.merge(liquidar);
            if (idaOld != null && !idaOld.equals(idaNew)) {
                idaOld.getLiquidarList().remove(liquidar);
                idaOld = em.merge(idaOld);
            }
            if (idaNew != null && !idaNew.equals(idaOld)) {
                idaNew.getLiquidarList().add(liquidar);
                idaNew = em.merge(idaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = liquidar.getId();
                if (findLiquidar(id) == null) {
                    throw new NonexistentEntityException("The liquidar with id " + id + " no longer exists.");
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
            Liquidar liquidar;
            try {
                liquidar = em.getReference(Liquidar.class, id);
                liquidar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The liquidar with id " + id + " no longer exists.", enfe);
            }
            Agregar ida = liquidar.getIda();
            if (ida != null) {
                ida.getLiquidarList().remove(liquidar);
                ida = em.merge(ida);
            }
            em.remove(liquidar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Liquidar> findLiquidarEntities() {
        return findLiquidarEntities(true, -1, -1);
    }

    public List<Liquidar> findLiquidarEntities(int maxResults, int firstResult) {
        return findLiquidarEntities(false, maxResults, firstResult);
    }

    private List<Liquidar> findLiquidarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Liquidar.class));
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

    public Liquidar findLiquidar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Liquidar.class, id);
        } finally {
            em.close();
        }
    }

    public int getLiquidarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Liquidar> rt = cq.from(Liquidar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
