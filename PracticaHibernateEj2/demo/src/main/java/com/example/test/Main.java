package com.example.test;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import com.example.entidades.conferencia;
import com.example.entidades.investigador;
import com.example.entidades.proyecto;
import com.example.entidades.asistenciaconferencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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
        // ejecutarEliminaciones();

        em.close();
        emf.close();
    }

    // MÉTODOS AUXILIARES
    private static investigador crearinvestigador(String dni, String nombre, String dir, String tel, String loc) {
        investigador inv = new investigador();
        inv.setDni(dni);
        inv.setNombreCompleto(nombre);
        inv.setDireccion(dir);
        inv.setTelefono(tel);
        inv.setLocalidad(loc);
        return inv;
    }

    private static conferencia crearconferencia(String nombre, String fecha, String lugar, double horas)
            throws Exception {
        conferencia c = new conferencia();
        c.setNombre(nombre);
        // Parsear fecha según formato del enunciado
        SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
        c.setFechaHoraInicio(sdf.parse(fecha));
        c.setLugar(lugar);
        c.setNumeroHoras(horas);
        return c;
    }

    private static proyecto crearproyecto(String nombre, String fecha) throws Exception {
        proyecto p = new proyecto();
        p.setNombre(nombre);
        // Parsear fecha según formato del enunciado
        SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM yyyy");
        p.setFechaInicio(sdf.parse(fecha));
        return p;
    }

    // NUEVO MÉTODO: Crear asistencia a conferencia
    private static asistenciaconferencia crearAsistencia(investigador inv, conferencia conf,
            String fechaStr, String lugar, double horas) throws Exception {
        asistenciaconferencia asist = new asistenciaconferencia();
        asist.setInvestigador(inv);
        asist.setConferencia(conf);
        // Parsear fecha según formato del enunciado
        SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
        asist.setFechaInicio(sdf.parse(fechaStr));
        asist.setLugar(lugar);
        asist.setNumeroHoras(horas);
        return asist;
    }

    // PARTE b): INSERTAR DATOS - CORREGIDO SEGÚN ENUNCIADO
    private static void insertarDatos() throws Exception {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 1. Crear proyectos SEGÚN ENUNCIADO (con nombres exactos)
        proyecto p1 = crearproyecto("Proyecto 1", "5 de mayo 2020");
        proyecto p2 = crearproyecto("Proyecto 2", "12 de junio 2020");
        proyecto p3 = crearproyecto("Proyecto 3", "15 de agosto 2020");
        proyecto p4 = crearproyecto("Proyecto 4", "1 de noviembre 2020");
        proyecto p5 = crearproyecto("Proyecto 5", "12 de diciembre 2020");

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);

        // 2. Crear investigadores
        investigador inv1 = crearinvestigador("30487452M", "Juan Pérez Martínez", "C./ Desengaño 21", "623423523",
                "Cádiz");
        investigador inv2 = crearinvestigador("45768434R", "Luisa Puertas Soto", "C./ Falsa 123", "693543252", "Cádiz");
        investigador inv3 = crearinvestigador("45642323B", "María Ruiz Sánchez", "C./ Almiel 12", "623234523", "Cádiz");
        investigador inv4 = crearinvestigador("67534312A", "Pablo Fernández Feria", "Avd. Inventada 15", "613442323",
                "Cádiz");
        investigador inv5 = crearinvestigador("65342316R", "Sofía Luque Conde", "C/ La Virtud 1", "664123623", "Cádiz");
        investigador inv6 = crearinvestigador("67323452B", "José López", "C./ Almiel 15", "723234523", "Cádiz");
        investigador inv7 = crearinvestigador("78953321A", "Andrés Fernán Noria", "Avd. Inventada 11", "713442323",
                "Cádiz");
        investigador inv8 = crearinvestigador("98634571R", "Sofía Martín Luz", "C/ La Virtud 4", "764123623", "Cádiz");

        // 3. Asignar proyectos a investigadores SEGÚN ENUNCIADO
        // Proyecto 1: investigadores 1 y 5 (Juan y Sofía Luque)
        inv1.getProyectos().add(p1);
        p1.getInvestigadores().add(inv1);
        inv5.getProyectos().add(p1);
        p1.getInvestigadores().add(inv5);

        // Proyecto 2: investigadores 2 y 4 (Luisa y Pablo)
        inv2.getProyectos().add(p2);
        p2.getInvestigadores().add(inv2);
        inv4.getProyectos().add(p2);
        p2.getInvestigadores().add(inv4);

        // Proyecto 3: investigadores 3 y 7 (María y Andrés)
        inv3.getProyectos().add(p3);
        p3.getInvestigadores().add(inv3);
        inv7.getProyectos().add(p3);
        p3.getInvestigadores().add(inv7);

        // Proyecto 4: investigadores 6 y 8 (José y Sofía Martín)
        inv6.getProyectos().add(p4);
        p4.getInvestigadores().add(inv6);
        inv8.getProyectos().add(p4);
        p4.getInvestigadores().add(inv8);

        // 4. PERSISTIR INVESTIGADORES PRIMERO
        em.persist(inv1);
        em.persist(inv2);
        em.persist(inv3);
        em.persist(inv4);
        em.persist(inv5);
        em.persist(inv6);
        em.persist(inv7);
        em.persist(inv8);

        // 5. Crear conferencias SEGÚN ENUNCIADO (solo 4 conferencias)
        conferencia c1 = crearconferencia("Conferencia 1", "2 de noviembre de 2020", "San Fernando", 2.5);
        conferencia c2 = crearconferencia("Conferencia 2", "12 de enero de 2021", "Sevilla", 4.0);
        conferencia c3 = crearconferencia("Conferencia 3", "1 de julio de 2021", "San Fernando", 1.5);
        conferencia c4 = crearconferencia("Conferencia 4", "2 de noviembre de 2021", "Berlín", 3.0);

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);

        // 6. Crear ASISTENCIAS A CONFERENCIAS SEGÚN ENUNCIADO
        // Investigador 1 -> Conferencia 2 (Juan Pérez)
        asistenciaconferencia a1 = crearAsistencia(inv1, c2, "12 de enero de 2021", "Sevilla", 4.0);
        em.persist(a1);

        // Investigador 2 -> Conferencias 1, 3 (Luisa Puertas) - NO HAY CONFERENCIA 5
        asistenciaconferencia a2_1 = crearAsistencia(inv2, c1, "2 de noviembre de 2020", "San Fernando", 2.5);
        asistenciaconferencia a2_2 = crearAsistencia(inv2, c3, "1 de julio de 2021", "San Fernando", 1.5);
        em.persist(a2_1);
        em.persist(a2_2);

        // Investigador 3 -> Todas las conferencias (María Ruiz)
        asistenciaconferencia a3_1 = crearAsistencia(inv3, c1, "2 de noviembre de 2020", "San Fernando", 2.5);
        asistenciaconferencia a3_2 = crearAsistencia(inv3, c2, "12 de enero de 2021", "Sevilla", 4.0);
        asistenciaconferencia a3_3 = crearAsistencia(inv3, c3, "1 de julio de 2021", "San Fernando", 1.5);
        asistenciaconferencia a3_4 = crearAsistencia(inv3, c4, "2 de noviembre de 2021", "Berlín", 3.0);
        em.persist(a3_1);
        em.persist(a3_2);
        em.persist(a3_3);
        em.persist(a3_4);

        // Investigador 4 -> Conferencias 1 (Pablo Fernández) - NO HAY CONFERENCIA 5
        asistenciaconferencia a4_1 = crearAsistencia(inv4, c1, "2 de noviembre de 2020", "San Fernando", 2.5);
        em.persist(a4_1);

        // Investigador 5 -> Conferencias 1, 2, 3, 4 (Sofía Luque)
        asistenciaconferencia a5_1 = crearAsistencia(inv5, c1, "2 de noviembre de 2020", "San Fernando", 2.5);
        asistenciaconferencia a5_2 = crearAsistencia(inv5, c2, "12 de enero de 2021", "Sevilla", 4.0);
        asistenciaconferencia a5_3 = crearAsistencia(inv5, c3, "1 de julio de 2021", "San Fernando", 1.5);
        asistenciaconferencia a5_4 = crearAsistencia(inv5, c4, "2 de noviembre de 2021", "Berlín", 3.0);
        em.persist(a5_1);
        em.persist(a5_2);
        em.persist(a5_3);
        em.persist(a5_4);

        tx.commit();
    }

    // PARTE c): CONSULTAS
    private static void ejecutarConsultas() {
        // i) proyectos en los que trabaja cada investigador
        System.out.println("\n  i) Proyectos en los que trabaja cada investigador:");
        String jpql1 = "SELECT i.nombreCompleto, p.nombre FROM investigador i JOIN i.proyectos p ORDER BY i.nombreCompleto";
        List<Object[]> resultados = em.createQuery(jpql1, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }

        // ii) investigadores que trabajan en cada proyecto
        System.out.println("\n  ii) Investigadores que trabajan en cada proyecto:");
        String jpql2 = "SELECT p.nombre, i.nombreCompleto FROM proyecto p JOIN p.investigadores i ORDER BY p.nombre, i.nombreCompleto";
        resultados = em.createQuery(jpql2, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }

        // iii) investigadores que han ido a cada conferencia
        System.out.println("\n  iii) Investigadores que han ido a cada conferencia:");
        String jpql3 = "SELECT c.nombre, i.nombreCompleto FROM asistenciaconferencia a JOIN a.conferencia c JOIN a.investigador i ORDER BY c.nombre, i.nombreCompleto";
        resultados = em.createQuery(jpql3, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }

        // iv) conferencias en las que ha estado cada investigador
        System.out.println("\n  iv) Conferencias en las que ha estado cada investigador:");
        String jpql4 = "SELECT i.nombreCompleto, c.nombre FROM asistenciaconferencia a JOIN a.investigador i JOIN a.conferencia c ORDER BY i.nombreCompleto, c.nombre";
        resultados = em.createQuery(jpql4, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.println("    - " + row[0] + " -> " + row[1]);
        }
    }

    // PARTE d): ACTUALIZACIONES SEGÚN ENUNCIADO
    private static void ejecutarActualizaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("\n  i) Investigador 2 ahora solo asiste a Conferencia 2:");

            // Buscar investigador 2 (Luisa Puertas Soto)
            investigador inv2 = em.find(investigador.class, "45768434R");

            // Buscar Conferencia 2 por NOMBRE (no por ID)
            Query queryC2 = em.createQuery("SELECT c FROM conferencia c WHERE c.nombre = :nombre");
            queryC2.setParameter("nombre", "Conferencia 2");
            List<conferencia> resultadosC2 = queryC2.getResultList();
            conferencia c2 = resultadosC2.isEmpty() ? null : resultadosC2.get(0);

            if (inv2 != null && c2 != null) {
                // 1. Eliminar todas las asistencias actuales del investigador 2
                Query deleteQuery = em
                        .createQuery("DELETE FROM asistenciaconferencia a WHERE a.investigador.dni = :dni");
                deleteQuery.setParameter("dni", "45768434R");
                int eliminados = deleteQuery.executeUpdate();
                System.out.println("      Eliminadas " + eliminados + " asistencias anteriores");

                // 2. Crear nueva asistencia solo a conferencia 2
                try {
                    asistenciaconferencia nuevaAsistencia = crearAsistencia(inv2, c2, "12 de enero de 2021", "Sevilla",
                            4.0);
                    em.persist(nuevaAsistencia);
                    System.out
                            .println("      ✓ " + inv2.getNombreCompleto() + " ahora solo asiste a " + c2.getNombre());
                } catch (Exception e) {
                    System.out.println("      ✗ Error creando asistencia: " + e.getMessage());
                }
            } else {
                System.out.println("      ✗ No se encontró investigador 2 o conferencia 2");
            }

            // ii) Actualizar fecha de conferencia 4 a fecha actual
            System.out.println("\n  ii) Actualizando fecha de Conferencia 4 a fecha actual:");
            Query queryC4 = em.createQuery("SELECT c FROM conferencia c WHERE c.nombre = :nombre");
            queryC4.setParameter("nombre", "Conferencia 4");
            List<conferencia> resultadosC4 = queryC4.getResultList();
            conferencia c4 = resultadosC4.isEmpty() ? null : resultadosC4.get(0);

            if (c4 != null) {
                c4.setFechaHoraInicio(new Date());
                System.out.println("      ✓ " + c4.getNombre() + " actualizada con fecha: " + new Date());
            } else {
                System.out.println("      ✗ No se encontró Conferencia 4");
            }

            // iii) Todos los investigadores trabajan en proyecto 3
            System.out.println("\n  iii) Todos los investigadores ahora trabajan en Proyecto 3:");
            proyecto p3 = em.find(proyecto.class, "Proyecto 3");

            if (p3 != null) {
                // Obtener todos los investigadores
                List<investigador> todosInvestigadores = em.createQuery(
                        "SELECT i FROM investigador i", investigador.class).getResultList();

                // Para cada investigador: limpiar proyectos y añadir solo proyecto 3
                for (investigador inv : todosInvestigadores) {
                    // Limpiar proyectos anteriores
                    inv.getProyectos().clear();
                    // Añadir proyecto 3
                    inv.getProyectos().add(p3);
                    // Actualizar
                    em.merge(inv);
                }

                // También actualizar el lado del proyecto
                p3.getInvestigadores().clear();
                p3.getInvestigadores().addAll(todosInvestigadores);
                em.merge(p3);

                System.out.println("      ✓ " + todosInvestigadores.size() + " investigadores ahora trabajan en "
                        + p3.getNombre());
            } else {
                System.out.println("      ✗ No se encontró Proyecto 3");
            }

            tx.commit();
            System.out.println("\n  ✓ Todas las actualizaciones realizadas correctamente");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("  ✗ Error en actualizaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // PARTE e): ELIMINACIONES SEGÚN ENUNCIADO
    /*private static void ejecutarEliminaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("\n  i) Eliminando Investigador 2 (Luisa Puertas Soto):");

            // Eliminar investigador 2 directamente
            investigador inv2 = em.find(investigador.class, "45768434R");
            em.remove(inv2);
            System.out.println("Investigador 2 eliminado");

            System.out.println("\n  ii) Eliminando Proyecto 1:");
            // Eliminar proyecto 1 directamente
            proyecto p1 = em.find(proyecto.class, "Proyecto 1");
            em.remove(p1);
            System.out.println(" Proyecto 1 eliminado");

            System.out.println("\n  iii) Eliminando Conferencia 4:");
            // Buscar y eliminar Conferencia 4 directamente
            Query queryC4 = em.createQuery("SELECT c FROM conferencia c WHERE c.nombre = :nombre");
            queryC4.setParameter("nombre", "Conferencia 4");
            conferencia c4 = (conferencia) queryC4.getSingleResult();
            em.remove(c4);
            System.out.println(" Conferencia 4 eliminada");

            tx.commit();
            System.out.println("\n Todas las eliminaciones realizadas correctamente");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("  ✗ Error en eliminaciones: " + e.getMessage());
            // e.printStackTrace(); 
        }
    }*/
}