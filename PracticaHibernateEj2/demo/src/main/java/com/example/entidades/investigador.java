package com.example.entidades;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "investigador")
public class investigador {
    @Id
    @Column(name = "dni", length = 9)
    private String dni;
    
    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Column(name = "localidad")
    private String localidad;
    
    // RELACIÓN CAMBIADA: ManyToMany con Proyecto
    @ManyToMany
    @JoinTable(
        name = "investigador_proyecto",
        joinColumns = @JoinColumn(name = "investigador_dni"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_nombre")
    )
    private Set<proyecto> proyectos = new HashSet<>();
    
    // RELACIÓN CAMBIADA: OneToMany con AsistenciaConferencia
    @OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL)
    private Set<asistenciaconferencia> asistenciasConferencias = new HashSet<>();
    
    // Constructores
    public investigador() {}
    
    public investigador(String dni, String nombreCompleto) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
    }
    
    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }
    
    public Set<proyecto> getProyectos() { return proyectos; }
    public void setProyectos(Set<proyecto> proyectos) { this.proyectos = proyectos; }
    
    public Set<asistenciaconferencia> getAsistenciasConferencias() { return asistenciasConferencias; }
    public void setAsistenciasConferencias(Set<asistenciaconferencia> asistenciasConferencias) { 
        this.asistenciasConferencias = asistenciasConferencias; 
    }
}