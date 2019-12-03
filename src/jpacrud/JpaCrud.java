/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpacrud;

import Entidades.Alumnos;
import Entidades.Paises;
import Entidades.Tipodocumentos;
import Persistencia.AlumnosJpaController;
import Persistencia.EmfSingleton;
import Persistencia.PaisesJpaController;
import Persistencia.TipodocumentosJpaController;
import Vista.formCrud;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class JpaCrud {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        formCrud ventana= new formCrud();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        // TODO code application logic here
//        EntityManagerFactory emf = EmfSingleton.getInstancia().getEmf();
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction et = em.getTransaction();
//        et.begin();
//--------------------------------------------------------------------------------------------------------------------------
//        AlumnosJpaController alumnojpa1= new AlumnosJpaController();
//        PaisesJpaController paisjpa1= new PaisesJpaController();
//        TipodocumentosJpaController tipodocjpa1= new TipodocumentosJpaController();       
//        insertamos un alumno a la base de dato usando JPA 
//        Paises pais1= new Paises("Argentina");
//        Tipodocumentos tipodoc1= new Tipodocumentos("DNI");
//        pais1.setNombre("Chile");
//        tipodoc1.setTipodocumento("LE");
//        paisjpa1.create(pais1);
//        tipodocjpa1.create(tipodoc1);
//        Alumnos alumno1 = new Alumnos("Jose", "Gomez", 24567890, "jg@mail.com", "15432567", tipodoc1, pais1);
//        alumnojpa1.create(alumno1);
//        System.out.println(tipodoc1.getTipodocumento());
//        
//--------------------------------------------------------------------------------------------------------------------------
//        alumno1.setNombre("Juan");
//        alumno1.setApellido("Perez");
//        alumno1.setCelular("234567");
//        alumno1.setDni(20345678);
//        alumno1.setEmail("juanP@mail.com");
//--------------------------------------------------------------------------------------------------------------------------        
//        em.persist(pais1);
//        em.persist(tipodoc1);
// update a la tabla pais usando JPA 
//Pais pais = new Pais(); 
//pais = em.find(Pais.class, 1); 
//pais.setNombre("ARGENTINA");
//em.merge(pais); 
// eliminar un pais usando JPA 
//Pais pais = new Pais(); 
//pais = em.find(Pais.class, 4); 
//em.remove(pais); 
//        et.commit();
//        em.close();
//        emf.close();
    }
    
}
