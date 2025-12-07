package com.example.entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asistencia_conferencia")
public class asistenciaconferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "investigador_dni", nullable = false)
    private investigador investigador;
    
    @ManyToOne
    @JoinColumn(name = "conferencia_id", nullable = false)
    private conferencia conferencia;
    
    @Column(name = "lugar")
    private String lugar;
    
    @Column(name = "numero_horas")
    private Double numeroHoras;
    
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    
    // Constructores
    public asistenciaconferencia() {}
    
    public asistenciaconferencia(investigador investigador, conferencia conferencia, 
                                 String lugar, Double numeroHoras, Date fechaInicio) {
        this.investigador = investigador;
        this.conferencia = conferencia;
        this.lugar = lugar;
        this.numeroHoras = numeroHoras;
        this.fechaInicio = fechaInicio;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public investigador getInvestigador() { return investigador; }
    public void setInvestigador(investigador investigador) { this.investigador = investigador; }
    
    public conferencia getConferencia() { return conferencia; }
    public void setConferencia(conferencia conferencia) { this.conferencia = conferencia; }
    
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    
    public Double getNumeroHoras() { return numeroHoras; }
    public void setNumeroHoras(Double numeroHoras) { this.numeroHoras = numeroHoras; }
    
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
}