package com.example.entidades;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "prestamo")
public class prestamo {
    @Id
    @Column(name = "id_libro")
    private int idLibro;
    
    @Column(name = "id_usuario")
    private int idUsuario;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    public prestamo() {
    }
    
    public prestamo(int idLibro, int idUsuario, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    // Getters y setters
    public int getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}