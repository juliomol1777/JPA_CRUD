/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "tipodocumentos")
@NamedQueries({
    @NamedQuery(name = "Tipodocumentos.findAll", query = "SELECT t FROM Tipodocumentos t")})
public class Tipodocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipodocumentos")
    private Integer idtipodocumentos;
    @Basic(optional = false)
    @Column(name = "tipodocumento")
    private String tipodocumento;
    @OneToMany(mappedBy = "idtipodocumento")
    private List<Alumnos> alumnosList;

    public Tipodocumentos() {
    }

    public Tipodocumentos(Integer idtipodocumentos) {
        this.idtipodocumentos = idtipodocumentos;
    }

    public Tipodocumentos(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Integer getIdtipodocumentos() {
        return idtipodocumentos;
    }

    public void setIdtipodocumentos(Integer idtipodocumentos) {
        this.idtipodocumentos = idtipodocumentos;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public List<Alumnos> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<Alumnos> alumnosList) {
        this.alumnosList = alumnosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipodocumentos != null ? idtipodocumentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodocumentos)) {
            return false;
        }
        Tipodocumentos other = (Tipodocumentos) object;
        if ((this.idtipodocumentos == null && other.idtipodocumentos != null) || (this.idtipodocumentos != null && !this.idtipodocumentos.equals(other.idtipodocumentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "Entidades.Tipodocumentos[ idtipodocumentos=" + idtipodocumentos + " ]";
        return tipodocumento;
    }
    
}
