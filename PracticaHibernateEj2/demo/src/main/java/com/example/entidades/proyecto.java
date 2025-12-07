package com.example.entidades;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "proyecto")
public class proyecto {
    @Id
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    
    // OneToMany bidireccional
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<investigador> investigadores = new HashSet<>();
    
    // Constructores
    public proyecto() {}
    
    public proyecto(String nombre, Date fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Set<investigador> getInvestigadores() {
        return investigadores;
    }

    public void setInvestigadores(Set<investigador> investigadores) {
        this.investigadores = investigadores;
    }
    
   

    
    
}