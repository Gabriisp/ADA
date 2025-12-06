package com.example.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.entidades.conferencia;
import com.example.entidades.investigador;
import com.example.entidades.proyecto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Empresa");
        em = emf.createEntityManager();

        // PARTE b): INSERTAR DATOS 
        insertarDatos();
        System.out.println("Datos insertados correctamente.\n");

        // PARTE c): CONSULTAS 
        System.out.println("2. REALIZANDO CONSULTAS");
        ejecutarConsultas();

        // PARTE d): ACTUALIZACIONES 
        System.out.println("\n3. REALIZANDO ACTUALIZACIONES");
        ejecutarActualizaciones();

        // PARTE e): ELIMINACIONES 
        System.out.println("\n4. REALIZANDO ELIMINACIONES");
        //ejecutarEliminaciones();

        em.close();
        emf.close();
    }

    // MÉTODOS AUXILIARES 
    private static investigador crearinvestigador(String dni, String nombre, String dir, String tel, String loc, proyecto p) {
        investigador inv = new investigador();
        inv.setDni(dni);
        inv.setNombreCompleto(nombre);
        inv.setDireccion(dir);
        inv.setTelefono(tel);
        inv.setLocalidad(loc);
        inv.setProyecto(p);
        return inv;
    }

    private static conferencia crearconferencia(String nombre, String fecha, String lugar, int horas) throws Exception {
        conferencia c = new conferencia();
        c.setNombre(nombre);
        c.setFechaHoraInicio(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha));
        c.setLugar(lugar);
        c.setNumeroHoras(horas);
        return c;
    }

    // PARTE b): INSERTAR DATOS 
    private static void insertarDatos() throws Exception {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        // 1. Crear proyectos
        proyecto p1 = new proyecto();
        p1.setNombre("proyecto 1");
        p1.setFechaInicio(sdfDate.parse("2020-05-05"));

        proyecto p2 = new proyecto();
        p2.setNombre("proyecto 2");
        p2.setFechaInicio(sdfDate.parse("2020-06-12"));

        proyecto p3 = new proyecto();
        p3.setNombre("proyecto 3");
        p3.setFechaInicio(sdfDate.parse("2020-08-15"));

        proyecto p4 = new proyecto();
        p4.setNombre("proyecto 4");
        p4.setFechaInicio(sdfDate.parse("2020-11-01"));

        proyecto p5 = new proyecto();
        p5.setNombre("proyecto 5");
        p5.setFechaInicio(sdfDate.parse("2020-12-12"));

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);

        // 2. Crear investigadores
        investigador inv1 = crearinvestigador("30487452M", "Juan Pérez Martínez", "C./ Desengaño 21", "623423523", "Cádiz", p1);
        investigador inv2 = crearinvestigador("45768434R", "Luisa Puertas Soto", "C./ Falsa 123", "693543252", "Cádiz", p2);
        investigador inv3 = crearinvestigador("45642323B", "María Ruiz Sánchez", "C./ Almiel 12", "623234523", "Cádiz", p3);
        investigador inv4 = crearinvestigador("67534312A", "Pablo Fernández Feria", "Avd. Inventada 15", "613442323", "Cádiz", p2);
        investigador inv5 = crearinvestigador("65342316R", "Sofía Luque Conde", "C/ La Virtud 1", "664123623", "Cádiz", p1);
        investigador inv6 = crearinvestigador("67323452B", "José López", "C./ Almiel 15", "723234523", "Cádiz", p4);
        investigador inv7 = crearinvestigador("78953321A", "Andrés Fernán Noria", "Avd. Inventada 11", "713442323", "Cádiz", p3);
        investigador inv8 = crearinvestigador("98634571R", "Sofía Martín Luz", "C/ La Virtud 4", "764123623", "Cádiz", p4);

        // 3. Crear conferencias
        conferencia c1 = crearconferencia("conferencia 1", "2020-11-02 10:00:00", "San Fernando", 2);
        conferencia c2 = crearconferencia("conferencia 2", "2021-01-12 10:00:00", "Sevilla", 4);
        conferencia c3 = crearconferencia("conferencia 3", "2021-07-01 10:00:00", "San Fernando", 1);
        conferencia c4 = crearconferencia("conferencia 4", "2021-11-02 10:00:00", "Berlín", 3);
        conferencia c5 = crearconferencia("conferencia 5", "2021-12-10 10:00:00", "Madrid", 2);

        // 4. Asociar investigadores con conferencias 
        // inv1 -> c2
        List<conferencia> confInv1 = new ArrayList<>();
        confInv1.add(c2);
        inv1.setConferencias(confInv1);
        
        List<investigador> invC2 = new ArrayList<>();
        invC2.add(inv1);
        invC2.add(inv3);
        invC2.add(inv5);
        c2.setInvestigadores(invC2);

        // inv2 -> c1, c3, c5
        List<conferencia> confInv2 = new ArrayList<>();
        confInv2.add(c1);
        confInv2.add(c3);
        confInv2.add(c5);
        inv2.setConferencias(confInv2);
        
        List<investigador> invC1 = new ArrayList<>();
        invC1.add(inv2);
        invC1.add(inv3);
        invC1.add(inv4);
        invC1.add(inv5);
        c1.setInvestigadores(invC1);

        // inv3 -> todas
        List<conferencia> confInv3 = new ArrayList<>();
        confInv3.add(c1);
        confInv3.add(c2);
        confInv3.add(c3);
        confInv3.add(c4);
        confInv3.add(c5);
        inv3.setConferencias(confInv3);
        
        List<investigador> invC3 = new ArrayList<>();
        invC3.add(inv2);
        invC3.add(inv3);
        invC3.add(inv5);
        c3.setInvestigadores(invC3);

        // inv4 -> c1, c5
        List<conferencia> confInv4 = new ArrayList<>();
        confInv4.add(c1);
        confInv4.add(c5);
        inv4.setConferencias(confInv4);
        
        List<investigador> invC5 = new ArrayList<>();
        invC5.add(inv2);
        invC5.add(inv3);
        invC5.add(inv4);
        c5.setInvestigadores(invC5);

        // inv5 -> c1, c2, c3, c4
        List<conferencia> confInv5 = new ArrayList<>();
        confInv5.add(c1);
        confInv5.add(c2);
        confInv5.add(c3);
        confInv5.add(c4);
        inv5.setConferencias(confInv5);
        
        List<investigador> invC4 = new ArrayList<>();
        invC4.add(inv3);
        invC4.add(inv5);
        c4.setInvestigadores(invC4);

        // Persistir investigadores
        em.persist(inv1);
        em.persist(inv2);
        em.persist(inv3);
        em.persist(inv4);
        em.persist(inv5);
        em.persist(inv6);
        em.persist(inv7);
        em.persist(inv8);

        // Persistir conferencias
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);

        tx.commit();
    }

    // ==================== PARTE c): CONSULTAS ====================
    private static void ejecutarConsultas() {
        // i) proyectos en los que trabaja cada investigador
        System.out.println("\n  i) proyectos en los que trabaja cada investigador:");
        String jpql1 = "SELECT i.nombreCompleto, p.nombre FROM investigador i JOIN i.proyecto p";
        List<Object[]> resultados = em.createQuery(jpql1, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }

        // ii) investigadores que trabajan en cada proyecto
        System.out.println("\n  ii) investigadores que trabajan en cada proyecto:");
        String jpql2 = "SELECT p.nombre, i.nombreCompleto FROM proyecto p JOIN p.investigadores i";
        resultados = em.createQuery(jpql2, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }

        // iii) investigadores que han ido a cada conferencia
        System.out.println("\n  iii) investigadores que han ido a cada conferencia:");
        String jpql3 = "SELECT c.nombre, i.nombreCompleto FROM conferencia c JOIN c.investigadores i";
        resultados = em.createQuery(jpql3, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }

        // iv) conferencias en las que ha estado cada investigador
        System.out.println("\n  iv) conferencias en las que ha estado cada investigador:");
        String jpql4 = "SELECT i.nombreCompleto, c.nombre FROM investigador i JOIN i.conferencias c";
        resultados = em.createQuery(jpql4, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }
    }

    // ==================== PARTE d): ACTUALIZACIONES ====================
    private static void ejecutarActualizaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // i) investigador 2 ahora solo asiste a conferencia 2
        investigador inv2 = em.find(investigador.class, "45768434R");
        conferencia c2 = em.find(conferencia.class, "conferencia 2");
        if (inv2 != null && c2 != null) {
            List<conferencia> nuevasConferencias = new ArrayList<>();
            nuevasConferencias.add(c2);
            inv2.setConferencias(nuevasConferencias);
            System.out.println("i) investigador 2 ahora solo asiste a conferencia 2");
        }

        // ii) Actualizar fecha de conferencia 4 a fecha actual
        conferencia c4 = em.find(conferencia.class, "conferencia 4");
        if (c4 != null) {
            c4.setFechaHoraInicio(new Date());
            System.out.println("ii) conferencia 4 actualizada con fecha actual");
        }

        // iii) Todos los investigadores trabajan en proyecto 3
        proyecto p3 = em.find(proyecto.class, "proyecto 3");
        if (p3 != null) {
            String jpql = "UPDATE investigador i SET i.proyecto = :proyecto";
            int actualizados = em.createQuery(jpql)
                                .setParameter("proyecto", p3)
                                .executeUpdate();
            System.out.println("iii) " + actualizados + " investigadores ahora trabajan en proyecto 3");
        }

        tx.commit();
    }

    // ==================== PARTE e): ELIMINACIONES ====================
    /*  private static void ejecutarEliminaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // i) Eliminar investigador 2
        investigador inv2 = em.find(investigador.class, "45768434R");
        if (inv2 != null) {
            em.remove(inv2);
            System.out.println("i) investigador 2 eliminado");
        }

        // ii) Eliminar proyecto 1
        proyecto p1 = em.find(proyecto.class, "proyecto 1");
        if (p1 != null) {
            em.remove(p1);
            System.out.println("ii) proyecto 1 eliminado (con sus investigadores en cascada)");
        }

        // iii) Eliminar conferencia 4
        conferencia c4 = em.find(conferencia.class, "conferencia 4");
        if (c4 != null) {
            em.remove(c4);
            System.out.println("iii) conferencia 4 eliminada");
        }

        tx.commit();
    }*/
}