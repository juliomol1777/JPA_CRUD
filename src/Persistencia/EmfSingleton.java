/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class EmfSingleton {
    private static EmfSingleton instancia= new EmfSingleton();
    static private final String NOMBRE_UNIDAD_PERSISTENCIA="JpaCrudPU";
    private EntityManagerFactory emf= null;
    public static EmfSingleton getInstancia(){
        return instancia;
    }
    private EmfSingleton(){
    }
    public EntityManagerFactory getEmf(){
        if(this.emf== null){
            this.emf= Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCIA);
        }
            return this.emf;
    }
}
