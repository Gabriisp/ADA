package com.example.entidades;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "conferencia")
public class conferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    @Column(name = "fecha_hora_inicio")
    private Date fechaHoraInicio;
    
    @Column(name = "lugar")
    private String lugar;
    
    @Column(name = "numero_horas")
    private Double numeroHoras;  
    
    @OneToMany(mappedBy = "conferencia", cascade = CascadeType.ALL)
    private Set<asistenciaconferencia> asistencias = new HashSet<>();  
    
    // Constructores
    public conferencia() {}
    
    public conferencia(String nombre, Date fechaHoraInicio, String lugar, Double numeroHoras) {
        this.nombre = nombre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.lugar = lugar;
        this.numeroHoras = numeroHoras;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Date getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(Date fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    
    public Double getNumeroHoras() { return numeroHoras; }
    public void setNumeroHoras(double horas) { this.numeroHoras = (double) horas; }
    
    public Set<asistenciaconferencia> getAsistencias() { return asistencias; }
    public void setAsistencias(Set<asistenciaconferencia> asistencias) { this.asistencias = asistencias; }
}