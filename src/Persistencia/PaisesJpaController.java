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
import Entidades.Paises;
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
public class PaisesJpaController implements Serializable {

//    public PaisesJpaController(EntityManagerFactory emf) {
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
    public void create(Paises paises) {
        em = emf.createEntityManager();
        et = em.getTransaction();
        if (paises.getAlumnosList() == null) {
            paises.setAlumnosList(new ArrayList<Alumnos>());
        }
        
        try {
            et.begin();
            List<Alumnos> attachedAlumnosList = new ArrayList<Alumnos>();
            for (Alumnos alumnosListAlumnosToAttach : paises.getAlumnosList()) {
                alumnosListAlumnosToAttach = em.getReference(alumnosListAlumnosToAttach.getClass(), alumnosListAlumnosToAttach.getIdalumno());
                attachedAlumnosList.add(alumnosListAlumnosToAttach);
            }
            paises.setAlumnosList(attachedAlumnosList);
            em.persist(paises);
            for (Alumnos alumnosListAlumnos : paises.getAlumnosList()) {
                Paises oldIdtiponacionalidadOfAlumnosListAlumnos = alumnosListAlumnos.getIdtiponacionalidad();
                alumnosListAlumnos.setIdtiponacionalidad(paises);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
                if (oldIdtiponacionalidadOfAlumnosListAlumnos != null) {
                    oldIdtiponacionalidadOfAlumnosListAlumnos.getAlumnosList().remove(alumnosListAlumnos);
                    oldIdtiponacionalidadOfAlumnosListAlumnos = em.merge(oldIdtiponacionalidadOfAlumnosListAlumnos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paises paises) throws NonexistentEntityException, Exception {
        em = emf.createEntityManager();
        et = em.getTransaction();
        try {
            et.begin();
            Paises persistentPaises = em.find(Paises.class, paises.getIdpais());
            List<Alumnos> alumnosListOld = persistentPaises.getAlumnosList();
            List<Alumnos> alumnosListNew = paises.getAlumnosList();
            List<Alumnos> attachedAlumnosListNew = new ArrayList<Alumnos>();
            for (Alumnos alumnosListNewAlumnosToAttach : alumnosListNew) {
                alumnosListNewAlumnosToAttach = em.getReference(alumnosListNewAlumnosToAttach.getClass(), alumnosListNewAlumnosToAttach.getIdalumno());
                attachedAlumnosListNew.add(alumnosListNewAlumnosToAttach);
            }
            alumnosListNew = attachedAlumnosListNew;
            paises.setAlumnosList(alumnosListNew);
            paises = em.merge(paises);
            for (Alumnos alumnosListOldAlumnos : alumnosListOld) {
                if (!alumnosListNew.contains(alumnosListOldAlumnos)) {
                    alumnosListOldAlumnos.setIdtiponacionalidad(null);
                    alumnosListOldAlumnos = em.merge(alumnosListOldAlumnos);
                }
            }
            for (Alumnos alumnosListNewAlumnos : alumnosListNew) {
                if (!alumnosListOld.contains(alumnosListNewAlumnos)) {
                    Paises oldIdtiponacionalidadOfAlumnosListNewAlumnos = alumnosListNewAlumnos.getIdtiponacionalidad();
                    alumnosListNewAlumnos.setIdtiponacionalidad(paises);
                    alumnosListNewAlumnos = em.merge(alumnosListNewAlumnos);
                    if (oldIdtiponacionalidadOfAlumnosListNewAlumnos != null && !oldIdtiponacionalidadOfAlumnosListNewAlumnos.equals(paises)) {
                        oldIdtiponacionalidadOfAlumnosListNewAlumnos.getAlumnosList().remove(alumnosListNewAlumnos);
                        oldIdtiponacionalidadOfAlumnosListNewAlumnos = em.merge(oldIdtiponacionalidadOfAlumnosListNewAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paises.getIdpais();
                if (findPaises(id) == null) {
                    throw new NonexistentEntityException("The paises with id " + id + " no longer exists.");
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
            Paises paises;
            try {
                paises = em.getReference(Paises.class, id);
                paises.getIdpais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paises with id " + id + " no longer exists.", enfe);
            }
            List<Alumnos> alumnosList = paises.getAlumnosList();
            for (Alumnos alumnosListAlumnos : alumnosList) {
                alumnosListAlumnos.setIdtiponacionalidad(null);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
            }
            em.remove(paises);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paises> findPaisesEntities() {
        return findPaisesEntities(true, -1, -1);
    }

    public List<Paises> findPaisesEntities(int maxResults, int firstResult) {
        return findPaisesEntities(false, maxResults, firstResult);
    }

    private List<Paises> findPaisesEntities(boolean all, int maxResults, int firstResult) {
        em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paises.class));
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

    public Paises findPaises(Integer id) {
        em = emf.createEntityManager();
        try {
            return em.find(Paises.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisesCount() {
        em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paises> rt = cq.from(Paises.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
