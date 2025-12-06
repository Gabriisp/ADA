package com.example.entidades;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Proyecto")
public class proyecto {
    @Id
    @Column(name = "Nombre", length = 20)
    private String nombre;

    @Column(name = "FechaInicio")
    private Date fechaInicio;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<investigador> investigadores;

    // Constructores
    public proyecto() {}

    public proyecto(String nombre, Date fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public List<investigador> getInvestigadores() { return investigadores; }
    public void setInvestigadores(List<investigador> investigadores) { this.investigadores = investigadores; }
}