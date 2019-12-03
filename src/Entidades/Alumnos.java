/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "alumnos")
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a")})
public class Alumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Idalumno")
    private Integer idalumno;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "dni")
    private int dni;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "celular")
    private String celular;
    @JoinColumn(name = "idtipodocumento", referencedColumnName = "idtipodocumentos")
    @ManyToOne
    private Tipodocumentos idtipodocumento;
    @JoinColumn(name = "idtiponacionalidad", referencedColumnName = "idpais")
    @ManyToOne
    private Paises idtiponacionalidad;

    public Alumnos() {
    }

    public Alumnos(Integer idalumno) {
        this.idalumno = idalumno;
    }

    public Alumnos(String nombre, String apellido, int dni, String email, String celular, Tipodocumentos idtipodocumento, Paises idtiponacionalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.idtipodocumento = idtipodocumento;
        this.idtiponacionalidad = idtiponacionalidad;
    }

    public Integer getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(Integer idalumno) {
        this.idalumno = idalumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Tipodocumentos getIdtipodocumento() {
        return idtipodocumento;
    }
    
    public void setIdtipodocumento(Tipodocumentos idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    public Paises getIdtiponacionalidad() {
        return idtiponacionalidad;
    }
    
    public void setIdtiponacionalidad(Paises idtiponacionalidad) {
        this.idtiponacionalidad = idtiponacionalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalumno != null ? idalumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumnos)) {
            return false;
        }
        Alumnos other = (Alumnos) object;
        if ((this.idalumno == null && other.idalumno != null) || (this.idalumno != null && !this.idalumno.equals(other.idalumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Alumnos[ idalumno=" + idalumno + " ]";
    }
    
}
