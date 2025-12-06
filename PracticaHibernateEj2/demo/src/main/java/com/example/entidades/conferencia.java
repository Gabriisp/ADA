package com.example.entidades;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Conferencia")
public class conferencia {
    @Id
    @Column(name = "Nombre", length = 20)
    private String nombre;

    @Column(name = "FechaHoraInicio")
    private Date fechaHoraInicio;

    @Column(name = "Lugar")
    private String lugar;

    @Column(name = "NumeroHoras")
    private Integer numeroHoras;

    @ManyToMany(mappedBy = "conferencias")
    private List<investigador> investigadores;

    // Constructores
    public conferencia() {}

    public conferencia(String nombre, Date fechaHoraInicio, String lugar, Integer numeroHoras) {
        this.nombre = nombre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.lugar = lugar;
        this.numeroHoras = numeroHoras;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Date getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(Date fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    
    public Integer getNumeroHoras() { return numeroHoras; }
    public void setNumeroHoras(Integer numeroHoras) { this.numeroHoras = numeroHoras; }
    
    public List<investigador> getInvestigadores() { return investigadores; }
    public void setInvestigadores(List<investigador> investigadores) { this.investigadores = investigadores; }
}