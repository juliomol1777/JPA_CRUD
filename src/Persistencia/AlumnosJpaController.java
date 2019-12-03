/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Alumnos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Tipodocumentos;
import Entidades.Paises;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author usuario
 */
public class AlumnosJpaController implements Serializable {

//    public AlumnosJpaController(EntityManagerFactory emf) {
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
    
    public void create(Alumnos alumnos) {
         em= emf.createEntityManager();
         et= em.getTransaction();
        try {
            et.begin();
            Tipodocumentos idtipodocumento = alumnos.getIdtipodocumento();
            if (idtipodocumento != null) {
                idtipodocumento = em.getReference(idtipodocumento.getClass(), idtipodocumento.getIdtipodocumentos());
                alumnos.setIdtipodocumento(idtipodocumento);
            }
            Paises idtiponacionalidad = alumnos.getIdtiponacionalidad();
            if (idtiponacionalidad != null) {
                idtiponacionalidad = em.getReference(idtiponacionalidad.getClass(), idtiponacionalidad.getIdpais());
                alumnos.setIdtiponacionalidad(idtiponacionalidad);
            }
            em.persist(alumnos);
            if (idtipodocumento != null) {
                idtipodocumento.getAlumnosList().add(alumnos);
                idtipodocumento = em.merge(idtipodocumento);
            }
            if (idtiponacionalidad != null) {
                idtiponacionalidad.getAlumnosList().add(alumnos);
                idtiponacionalidad = em.merge(idtiponacionalidad);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumnos alumnos) throws NonexistentEntityException, Exception {
        em = emf.createEntityManager();
        et = em.getTransaction();
        try {
            et.begin();
            Alumnos persistentAlumnos = em.find(Alumnos.class, alumnos.getIdalumno());
            Tipodocumentos idtipodocumentoOld = persistentAlumnos.getIdtipodocumento();
            Tipodocumentos idtipodocumentoNew = alumnos.getIdtipodocumento();
            Paises idtiponacionalidadOld = persistentAlumnos.getIdtiponacionalidad();
            Paises idtiponacionalidadNew = alumnos.getIdtiponacionalidad();
            if (idtipodocumentoNew != null) {
                idtipodocumentoNew = em.getReference(idtipodocumentoNew.getClass(), idtipodocumentoNew.getIdtipodocumentos());
                alumnos.setIdtipodocumento(idtipodocumentoNew);
            }
            if (idtiponacionalidadNew != null) {
                idtiponacionalidadNew = em.getReference(idtiponacionalidadNew.getClass(), idtiponacionalidadNew.getIdpais());
                alumnos.setIdtiponacionalidad(idtiponacionalidadNew);
            }
            alumnos = em.merge(alumnos);
            if (idtipodocumentoOld != null && !idtipodocumentoOld.equals(idtipodocumentoNew)) {
                idtipodocumentoOld.getAlumnosList().remove(alumnos);
                idtipodocumentoOld = em.merge(idtipodocumentoOld);
            }
            if (idtipodocumentoNew != null && !idtipodocumentoNew.equals(idtipodocumentoOld)) {
                idtipodocumentoNew.getAlumnosList().add(alumnos);
                idtipodocumentoNew = em.merge(idtipodocumentoNew);
            }
            if (idtiponacionalidadOld != null && !idtiponacionalidadOld.equals(idtiponacionalidadNew)) {
                idtiponacionalidadOld.getAlumnosList().remove(alumnos);
                idtiponacionalidadOld = em.merge(idtiponacionalidadOld);
            }
            if (idtiponacionalidadNew != null && !idtiponacionalidadNew.equals(idtiponacionalidadOld)) {
                idtiponacionalidadNew.getAlumnosList().add(alumnos);
                idtiponacionalidadNew = em.merge(idtiponacionalidadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumnos.getIdalumno();
                if (findAlumnos(id) == null) {
                    throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.");
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
            Alumnos alumnos;
            try {
                alumnos = em.getReference(Alumnos.class, id);
                alumnos.getIdalumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.", enfe);
            }
            Tipodocumentos idtipodocumento = alumnos.getIdtipodocumento();
            if (idtipodocumento != null) {
                idtipodocumento.getAlumnosList().remove(alumnos);
                idtipodocumento = em.merge(idtipodocumento);
            }
            Paises idtiponacionalidad = alumnos.getIdtiponacionalidad();
            if (idtiponacionalidad != null) {
                idtiponacionalidad.getAlumnosList().remove(alumnos);
                idtiponacionalidad = em.merge(idtiponacionalidad);
            }
            em.remove(alumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumnos> findAlumnosEntities() {
        return findAlumnosEntities(true, -1, -1);
    }

    public List<Alumnos> findAlumnosEntities(int maxResults, int firstResult) {
        return findAlumnosEntities(false, maxResults, firstResult);
    }

    private List<Alumnos> findAlumnosEntities(boolean all, int maxResults, int firstResult) {
        em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumnos.class));
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

    public Alumnos findAlumnos(Integer id) {
        em = emf.createEntityManager();
        try {
            return em.find(Alumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnosCount() {
        em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumnos> rt = cq.from(Alumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
