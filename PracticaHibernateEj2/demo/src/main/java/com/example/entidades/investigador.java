package com.example.entidades;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Investigador")
public class investigador {
    @Id
    @Column(name = "DNI", length = 9)
    private String dni;

    @Column(name = "NombreCompleto", nullable = false)
    private String nombreCompleto;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Telefono", length = 9)
    private String telefono;

    @Column(name = "Localidad")
    private String localidad;

    @ManyToOne
    @JoinColumn(name = "nombre_proyecto", nullable = false)
    private proyecto proyecto;

    @ManyToMany
    @JoinTable(
        name = "investigador_conferencia",
        joinColumns = @JoinColumn(name = "dni_investigador"),
        inverseJoinColumns = @JoinColumn(name = "nombre_conferencia")
    )
    private List<conferencia> conferencias;

    // Constructores
    public investigador() {}

    public investigador(String dni, String nombreCompleto, proyecto proyecto) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.proyecto = proyecto;
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
    
    public proyecto getProyecto() { return proyecto; }
    public void setProyecto(proyecto proyecto) { this.proyecto = proyecto; }
    
    public List<conferencia> getConferencias() { return conferencias; }
    public void setConferencias(List<conferencia> conferencias) { this.conferencias = conferencias; }
}