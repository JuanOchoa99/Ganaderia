/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganaderia.controladores;

import ganaderia.controladores.exceptions.IllegalOrphanException;
import ganaderia.controladores.exceptions.NonexistentEntityException;
import ganaderia.entidades.Agregar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ganaderia.entidades.Liquidar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JJ
 */
public class AgregarJpaController implements Serializable {

    public AgregarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agregar agregar) {
        if (agregar.getLiquidarList() == null) {
            agregar.setLiquidarList(new ArrayList<Liquidar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Liquidar> attachedLiquidarList = new ArrayList<Liquidar>();
            for (Liquidar liquidarListLiquidarToAttach : agregar.getLiquidarList()) {
                liquidarListLiquidarToAttach = em.getReference(liquidarListLiquidarToAttach.getClass(), liquidarListLiquidarToAttach.getId());
                attachedLiquidarList.add(liquidarListLiquidarToAttach);
            }
            agregar.setLiquidarList(attachedLiquidarList);
            em.persist(agregar);
            for (Liquidar liquidarListLiquidar : agregar.getLiquidarList()) {
                Agregar oldIdaOfLiquidarListLiquidar = liquidarListLiquidar.getIda();
                liquidarListLiquidar.setIda(agregar);
                liquidarListLiquidar = em.merge(liquidarListLiquidar);
                if (oldIdaOfLiquidarListLiquidar != null) {
                    oldIdaOfLiquidarListLiquidar.getLiquidarList().remove(liquidarListLiquidar);
                    oldIdaOfLiquidarListLiquidar = em.merge(oldIdaOfLiquidarListLiquidar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agregar agregar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agregar persistentAgregar = em.find(Agregar.class, agregar.getId());
            List<Liquidar> liquidarListOld = persistentAgregar.getLiquidarList();
            List<Liquidar> liquidarListNew = agregar.getLiquidarList();
            List<String> illegalOrphanMessages = null;
            for (Liquidar liquidarListOldLiquidar : liquidarListOld) {
                if (!liquidarListNew.contains(liquidarListOldLiquidar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Liquidar " + liquidarListOldLiquidar + " since its ida field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Liquidar> attachedLiquidarListNew = new ArrayList<Liquidar>();
            for (Liquidar liquidarListNewLiquidarToAttach : liquidarListNew) {
                liquidarListNewLiquidarToAttach = em.getReference(liquidarListNewLiquidarToAttach.getClass(), liquidarListNewLiquidarToAttach.getId());
                attachedLiquidarListNew.add(liquidarListNewLiquidarToAttach);
            }
            liquidarListNew = attachedLiquidarListNew;
            agregar.setLiquidarList(liquidarListNew);
            agregar = em.merge(agregar);
            for (Liquidar liquidarListNewLiquidar : liquidarListNew) {
                if (!liquidarListOld.contains(liquidarListNewLiquidar)) {
                    Agregar oldIdaOfLiquidarListNewLiquidar = liquidarListNewLiquidar.getIda();
                    liquidarListNewLiquidar.setIda(agregar);
                    liquidarListNewLiquidar = em.merge(liquidarListNewLiquidar);
                    if (oldIdaOfLiquidarListNewLiquidar != null && !oldIdaOfLiquidarListNewLiquidar.equals(agregar)) {
                        oldIdaOfLiquidarListNewLiquidar.getLiquidarList().remove(liquidarListNewLiquidar);
                        oldIdaOfLiquidarListNewLiquidar = em.merge(oldIdaOfLiquidarListNewLiquidar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agregar.getId();
                if (findAgregar(id) == null) {
                    throw new NonexistentEntityException("The agregar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agregar agregar;
            try {
                agregar = em.getReference(Agregar.class, id);
                agregar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agregar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Liquidar> liquidarListOrphanCheck = agregar.getLiquidarList();
            for (Liquidar liquidarListOrphanCheckLiquidar : liquidarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agregar (" + agregar + ") cannot be destroyed since the Liquidar " + liquidarListOrphanCheckLiquidar + " in its liquidarList field has a non-nullable ida field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(agregar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agregar> findAgregarEntities() {
        return findAgregarEntities(true, -1, -1);
    }

    public List<Agregar> findAgregarEntities(int maxResults, int firstResult) {
        return findAgregarEntities(false, maxResults, firstResult);
    }

    private List<Agregar> findAgregarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agregar.class));
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

    public Agregar findAgregar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agregar.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgregarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agregar> rt = cq.from(Agregar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
