package com.example.operaciones;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.entidades.libro;
import com.example.entidades.prestamo;
import com.example.entidades.usuario;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biblioteca");

        // Insertar 5 libros 
        libro libro1 = new libro(1, "Carlos", "La Sombra del Viento", "Disponible", 487);
        libro libro2 = new libro(2, "Arturo", "El Club Dumas", "Prestado", 465);
        libro libro3 = new libro(3, "Almudena", "Los pacientes", "Disponible", 832);
        libro libro4 = new libro(4, "Rosa", "La ridícula idea", "En revisión", 256);
        libro libro5 = new libro(5, "Javier", "Corazón tan blanco", "Disponible", 398);

        // Insertar 5 usuarios 
        usuario usuario1 = new usuario(1, "Antonio", "Gutiérrez", "Méndez", "Sevilla", 2.8, LocalDate.of(2019, 5, 12));
        usuario usuario2 = new usuario(2, "Isabel", "Ramírez", "Santos", "Zaragoza", 3.2, LocalDate.of(2020, 8, 22));
        usuario usuario3 = new usuario(3, "Francisco", "Navarro", "Jiménez", "Málaga", 2.1, LocalDate.of(2021, 2, 14));
        usuario usuario4 = new usuario(4, "Carmen", "Ortega", "Vargas", "Valencia", 1.9, LocalDate.of(2018, 11, 30));
        usuario usuario5 = new usuario(5, "Miguel", "Serrano", "Gil", "Bilbao", 3.5, LocalDate.of(2022, 3, 8));

        // Insertar 10 préstamos
       LocalDateTime ahora = LocalDateTime.now();

        prestamo prestamo1 = new prestamo(1, 1, ahora.minusDays(15), ahora.minusDays(8));
        prestamo prestamo2 = new prestamo(2, 2, ahora.minusDays(12), ahora.minusDays(4));
        prestamo prestamo3 = new prestamo(3, 3, ahora.minusDays(20), ahora.minusDays(12));
        prestamo prestamo4 = new prestamo(4, 4, ahora.minusDays(25), ahora.minusDays(18));
        prestamo prestamo5 = new prestamo(5, 5, ahora.minusDays(7), null);
        prestamo prestamo6 = new prestamo(6, 2, ahora.minusDays(5), null);    
        prestamo prestamo7 = new prestamo(7, 3, ahora.minusDays(14), ahora.minusDays(6));  
        prestamo prestamo8 = new prestamo(8, 4, ahora.minusDays(9), ahora.minusDays(3));  
        prestamo prestamo9 = new prestamo(9, 5, ahora.minusDays(30), ahora.minusDays(20)); 
        prestamo prestamo10 = new prestamo(10, 1, ahora.minusDays(3), null);               

        añadirl(emf.createEntityManager(), libro1);
        añadirl(emf.createEntityManager(), libro2);
        añadirl(emf.createEntityManager(), libro3);
        añadirl(emf.createEntityManager(), libro4);
        añadirl(emf.createEntityManager(), libro5);

        añadiru(emf.createEntityManager(), usuario1);
        añadiru(emf.createEntityManager(), usuario2);
        añadiru(emf.createEntityManager(), usuario3);
        añadiru(emf.createEntityManager(), usuario4);
        añadiru(emf.createEntityManager(), usuario5);

        añadirp(emf.createEntityManager(), prestamo1);
        añadirp(emf.createEntityManager(), prestamo2);
        añadirp(emf.createEntityManager(), prestamo3);
        añadirp(emf.createEntityManager(), prestamo4);
        añadirp(emf.createEntityManager(), prestamo5);
        añadirp(emf.createEntityManager(), prestamo6);
        añadirp(emf.createEntityManager(), prestamo7);
        añadirp(emf.createEntityManager(), prestamo8);
        añadirp(emf.createEntityManager(), prestamo9);
        añadirp(emf.createEntityManager(), prestamo10);

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        // Ejercicio c: Modificar un libro cambiando su estado
        libro libroModificar = em.find(libro.class, 1);
        libroModificar.setEstado("En reparación");

        // Ejercicio d: Aumentar un 25% la categoría del usuario con id=3
        usuario usuarioActualizar = em.find(usuario.class, 3);
        usuarioActualizar.setCategoria(usuarioActualizar.getCategoria() * 1.25);

        // Ejercicio e: Modificar la fecha_fin de un préstamo
        prestamo prestamoModificar = em.find(prestamo.class, 1);
        prestamoModificar.setFechaFin(LocalDateTime.now().plusDays(5));

        // Ejercicio f: Eliminar al usuario con id=5 y todos sus préstamos asociados
        em.remove(em.find(usuario.class, 5));
        for (int i = 1; i <= 10; i++) {
            prestamo prestamoEliminar = em.find(prestamo.class, i);
            if (prestamoEliminar != null && prestamoEliminar.getIdUsuario() == 5) {
                em.remove(prestamoEliminar);
            }
        }

        // Ejercicio g: Eliminar los préstamos realizados por un determinado usuario 
        for (int i = 1; i <= 10; i++) {
            prestamo prestamoUsuario = em.find(prestamo.class, i);
            if (prestamoUsuario != null && prestamoUsuario.getIdUsuario() == 2) {
                em.remove(prestamoUsuario);
            }
        }
        
        et.commit();
        em.close();
        emf.close();
    }

    public static void añadirl(EntityManager em, libro l) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(l);
        et.commit();
        em.close();
    }

    public static void añadiru(EntityManager em, usuario u) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(u);
        et.commit();
        em.close();
    }

    public static void añadirp(EntityManager em, prestamo p) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(p);
        et.commit();
        em.close();
    }
}
