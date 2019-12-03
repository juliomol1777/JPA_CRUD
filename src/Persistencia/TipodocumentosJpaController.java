/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Alumnos;
import Entidades.Tipodocumentos;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author usuario
 */
public class TipodocumentosJpaController implements Serializable {

//    public TipodocumentosJpaController(EntityManagerFactory emf) {
//        this.emf = emf;
//    }
//    private EntityManagerFactory emf = null;
//
//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }
    EntityManagerFactory emf = EmfSingleton.getInstancia().getEmf();
    EntityManager em;
    EntityTransaction et;
    public void create(Tipodocumentos tipodocumentos) {
        em = emf.createEntityManager();
        et = em.getTransaction();
        if (tipodocumentos.getAlumnosList() == null) {
            tipodocumentos.setAlumnosList(new ArrayList<Alumnos>());
        }
        try {
            et.begin();
            List<Alumnos> attachedAlumnosList = new ArrayList<Alumnos>();
            for (Alumnos alumnosListAlumnosToAttach : tipodocumentos.getAlumnosList()) {
                alumnosListAlumnosToAttach = em.getReference(alumnosListAlumnosToAttach.getClass(), alumnosListAlumnosToAttach.getIdalumno());
                attachedAlumnosList.add(alumnosListAlumnosToAttach);
            }
            tipodocumentos.setAlumnosList(attachedAlumnosList);
            em.persist(tipodocumentos);
            for (Alumnos alumnosListAlumnos : tipodocumentos.getAlumnosList()) {
                Tipodocumentos oldIdtipodocumentoOfAlumnosListAlumnos = alumnosListAlumnos.getIdtipodocumento();
                alumnosListAlumnos.setIdtipodocumento(tipodocumentos);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
                if (oldIdtipodocumentoOfAlumnosListAlumnos != null) {
                    oldIdtipodocumentoOfAlumnosListAlumnos.getAlumnosList().remove(alumnosListAlumnos);
                    oldIdtipodocumentoOfAlumnosListAlumnos = em.merge(oldIdtipodocumentoOfAlumnosListAlumnos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumentos tipodocumentos) throws NonexistentEntityException, Exception {
        em = emf.createEntityManager();
        et = em.getTransaction();
        try {
            et.begin();
            Tipodocumentos persistentTipodocumentos = em.find(Tipodocumentos.class, tipodocumentos.getIdtipodocumentos());
            List<Alumnos> alumnosListOld = persistentTipodocumentos.getAlumnosList();
            List<Alumnos> alumnosListNew = tipodocumentos.getAlumnosList();
            List<Alumnos> attachedAlumnosListNew = new ArrayList<Alumnos>();
            for (Alumnos alumnosListNewAlumnosToAttach : alumnosListNew) {
                alumnosListNewAlumnosToAttach = em.getReference(alumnosListNewAlumnosToAttach.getClass(), alumnosListNewAlumnosToAttach.getIdalumno());
                attachedAlumnosListNew.add(alumnosListNewAlumnosToAttach);
            }
            alumnosListNew = attachedAlumnosListNew;
            tipodocumentos.setAlumnosList(alumnosListNew);
            tipodocumentos = em.merge(tipodocumentos);
            for (Alumnos alumnosListOldAlumnos : alumnosListOld) {
                if (!alumnosListNew.contains(alumnosListOldAlumnos)) {
                    alumnosListOldAlumnos.setIdtipodocumento(null);
                    alumnosListOldAlumnos = em.merge(alumnosListOldAlumnos);
                }
            }
            for (Alumnos alumnosListNewAlumnos : alumnosListNew) {
                if (!alumnosListOld.contains(alumnosListNewAlumnos)) {
                    Tipodocumentos oldIdtipodocumentoOfAlumnosListNewAlumnos = alumnosListNewAlumnos.getIdtipodocumento();
                    alumnosListNewAlumnos.setIdtipodocumento(tipodocumentos);
                    alumnosListNewAlumnos = em.merge(alumnosListNewAlumnos);
                    if (oldIdtipodocumentoOfAlumnosListNewAlumnos != null && !oldIdtipodocumentoOfAlumnosListNewAlumnos.equals(tipodocumentos)) {
                        oldIdtipodocumentoOfAlumnosListNewAlumnos.getAlumnosList().remove(alumnosListNewAlumnos);
                        oldIdtipodocumentoOfAlumnosListNewAlumnos = em.merge(oldIdtipodocumentoOfAlumnosListNewAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumentos.getIdtipodocumentos();
                if (findTipodocumentos(id) == null) {
                    throw new NonexistentEntityException("The tipodocumentos with id " + id + " no longer exists.");
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
        em = emf.createEntityManager();
        et = em.getTransaction();
        try {
            et.begin();
            Tipodocumentos tipodocumentos;
            try {
                tipodocumentos = em.getReference(Tipodocumentos.class, id);
                tipodocumentos.getIdtipodocumentos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumentos with id " + id + " no longer exists.", enfe);
            }
            List<Alumnos> alumnosList = tipodocumentos.getAlumnosList();
            for (Alumnos alumnosListAlumnos : alumnosList) {
                alumnosListAlumnos.setIdtipodocumento(null);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
            }
            em.remove(tipodocumentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumentos> findTipodocumentosEntities() {
        return findTipodocumentosEntities(true, -1, -1);
    }

    public List<Tipodocumentos> findTipodocumentosEntities(int maxResults, int firstResult) {
        return findTipodocumentosEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumentos> findTipodocumentosEntities(boolean all, int maxResults, int firstResult) {
        em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumentos.class));
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

    public Tipodocumentos findTipodocumentos(Integer id) {
        em = emf.createEntityManager();
        try {
            return em.find(Tipodocumentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentosCount() {
        em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumentos> rt = cq.from(Tipodocumentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
