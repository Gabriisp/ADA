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

        System.out.println("=== EJERCICIO 2 ===");

        // PARTE b): INSERTAR DATOS
        System.out.println("\n1. INSERTANDO DATOS:");
        insertarDatos();

        // PARTE c): CONSULTAS
        System.out.println("\n2. REALIZANDO CONSULTAS:");
        ejecutarConsultas();

        // PARTE d): ACTUALIZACIONES
        System.out.println("\n3. REALIZANDO ACTUALIZACIONES:");
        ejecutarActualizaciones();

        // PARTE e): ELIMINACIONES
        System.out.println("\n4. REALIZANDO ELIMINACIONES:");
        ejecutarEliminaciones();


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
        c.setFechaHoraInicio(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
        c.setLugar(lugar);
        c.setNumeroHoras(horas);
        return c;
    }

    private static proyecto crearproyecto(String nombre, String fecha) throws Exception {
        proyecto p = new proyecto();
        p.setNombre(nombre);
        p.setFechaInicio(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
        return p;
    }

    private static asistenciaconferencia crearasistencia(investigador inv, conferencia conf,
            String fechaStr, String lugar, double horas) throws Exception {
        asistenciaconferencia asist = new asistenciaconferencia();
        asist.setInvestigador(inv);
        asist.setConferencia(conf);
        asist.setFechaInicio(new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr));
        asist.setLugar(lugar);
        asist.setNumeroHoras(horas);
        return asist;
    }

    // PARTE b): INSERTAR DATOS

    private static void insertarDatos() throws Exception {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("Creando proyectos...");
            // 1. Crear proyectos
            proyecto p1 = crearproyecto("Proyecto 1", "2020-05-05");
            proyecto p2 = crearproyecto("Proyecto 2", "2020-06-12");
            proyecto p3 = crearproyecto("Proyecto 3", "2020-08-15");
            proyecto p4 = crearproyecto("Proyecto 4", "2020-11-01");
            proyecto p5 = crearproyecto("Proyecto 5", "2020-12-12");

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);

            System.out.println("Creando investigadores...");
            // 2. Crear investigadores
            investigador inv1 = crearinvestigador("30487452M", "Juan Pérez Martínez", "C./ Desengaño 21", "623423523",
                    "Cádiz");
            investigador inv2 = crearinvestigador("45768434R", "Luisa Puertas Soto", "C./ Falsa 123", "693543252",
                    "Cádiz");
            investigador inv3 = crearinvestigador("45642323B", "María Ruiz Sánchez", "C./ Almiel 12", "623234523",
                    "Cádiz");
            investigador inv4 = crearinvestigador("67534312A", "Pablo Fernández Feria", "Avd. Inventada 15",
                    "613442323", "Cádiz");
            investigador inv5 = crearinvestigador("65342316R", "Sofía Luque Conde", "C/ La Virtud 1", "664123623",
                    "Cádiz");
            investigador inv6 = crearinvestigador("67323452B", "José López", "C./ Almiel 15", "723234523", "Cádiz");
            investigador inv7 = crearinvestigador("78953321A", "Andrés Fernán Noria", "Avd. Inventada 11", "713442323",
                    "Cádiz");
            investigador inv8 = crearinvestigador("98634571R", "Sofía Martín Luz", "C/ La Virtud 4", "764123623",
                    "Cádiz");

            System.out.println("Asignando proyectos a investigadores...");
            // 3. Asignar proyectos a investigadores (N:1)
            // Proyecto 1: investigadores 1 y 5
            inv1.setProyecto(p1);
            inv5.setProyecto(p1);

            // Proyecto 2: investigadores 2 y 4
            inv2.setProyecto(p2);
            inv4.setProyecto(p2);

            // Proyecto 3: investigadores 3 y 7
            inv3.setProyecto(p3);
            inv7.setProyecto(p3);

            // Proyecto 4: investigadores 6 y 8
            inv6.setProyecto(p4);
            inv8.setProyecto(p4);

    

            // 4. Persistir investigadores
            em.persist(inv1);
            em.persist(inv2);
            em.persist(inv3);
            em.persist(inv4);
            em.persist(inv5);
            em.persist(inv6);
            em.persist(inv7);
            em.persist(inv8);

            System.out.println("Creando conferencias...");
            // 5. Crear conferencias
            conferencia c1 = crearconferencia("Conferencia 1", "2020-11-02", "San Fernando", 2.5);
            conferencia c2 = crearconferencia("Conferencia 2", "2021-01-12", "Sevilla", 4.0);
            conferencia c3 = crearconferencia("Conferencia 3", "2021-07-01", "San Fernando", 1.5);
            conferencia c4 = crearconferencia("Conferencia 4", "2021-11-02", "Berlín", 3.0);

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);

            System.out.println("Registrando asistencias a conferencias...");
            // 6. Crear asistencias a conferencias según enunciado
            // Investigador 1 -> Conferencia 2
            asistenciaconferencia a1 = crearasistencia(inv1, c2, "2021-01-12", "Sevilla", 4.0);
            em.persist(a1);

            // Investigador 2 -> Conferencias 1, 3
            asistenciaconferencia a2_1 = crearasistencia(inv2, c1, "2020-11-02", "San Fernando", 2.5);
            asistenciaconferencia a2_2 = crearasistencia(inv2, c3, "2021-07-01", "San Fernando", 1.5);
            em.persist(a2_1);
            em.persist(a2_2);

            // Investigador 3 -> Todas las conferencias (1, 2, 3, 4)
            asistenciaconferencia a3_1 = crearasistencia(inv3, c1, "2020-11-02", "San Fernando", 2.5);
            asistenciaconferencia a3_2 = crearasistencia(inv3, c2, "2021-01-12", "Sevilla", 4.0);
            asistenciaconferencia a3_3 = crearasistencia(inv3, c3, "2021-07-01", "San Fernando", 1.5);
            asistenciaconferencia a3_4 = crearasistencia(inv3, c4, "2021-11-02", "Berlín", 3.0);
            em.persist(a3_1);
            em.persist(a3_2);
            em.persist(a3_3);
            em.persist(a3_4);

            // Investigador 4 -> Conferencia 1
            asistenciaconferencia a4_1 = crearasistencia(inv4, c1, "2020-11-02", "San Fernando", 2.5);
            em.persist(a4_1);

            // Investigador 5 -> Conferencias 1, 2, 3, 4
            asistenciaconferencia a5_1 = crearasistencia(inv5, c1, "2020-11-02", "San Fernando", 2.5);
            asistenciaconferencia a5_2 = crearasistencia(inv5, c2, "2021-01-12", "Sevilla", 4.0);
            asistenciaconferencia a5_3 = crearasistencia(inv5, c3, "2021-07-01", "San Fernando", 1.5);
            asistenciaconferencia a5_4 = crearasistencia(inv5, c4, "2021-11-02", "Berlín", 3.0);
            em.persist(a5_1);
            em.persist(a5_2);
            em.persist(a5_3);
            em.persist(a5_4);

            tx.commit();
            System.out.println("Datos insertados correctamente\n");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error insertando datos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // PARTE c): CONSULTAS

    private static void ejecutarConsultas() {
        try {
            // i) proyectos en los que trabaja cada investigador
            System.out.println("\n  i) Proyectos en los que trabaja cada investigador:");
            String jpql1 = "SELECT i.nombreCompleto, i.proyecto.nombre FROM investigador i ORDER BY i.nombreCompleto";
            List<Object[]> resultados = em.createQuery(jpql1, Object[].class).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("    No hay datos");
            } else {
                for (Object[] row : resultados) {
                    String proyecto = row[1] != null ? (String) row[1] : "Sin proyecto";
                    System.out.println("    - " + row[0] + " -> " + proyecto);
                }
            }

            // ii) investigadores que trabajan en cada proyecto
            System.out.println("\n  ii) Investigadores que trabajan en cada proyecto:");
            String jpql2 = "SELECT p.nombre, i.nombreCompleto FROM proyecto p LEFT JOIN p.investigadores i ORDER BY p.nombre, i.nombreCompleto";
            resultados = em.createQuery(jpql2, Object[].class).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("    No hay datos");
            } else {
                for (Object[] row : resultados) {
                    System.out.println("    - " + row[0] + " -> " + (row[1] != null ? row[1] : "Sin investigadores"));
                }
            }

            // iii) investigadores que han ido a cada conferencia
            System.out.println("\n  iii) Investigadores que han ido a cada conferencia:");
            String jpql3 = "SELECT c.nombre, i.nombreCompleto FROM asistenciaconferencia a JOIN a.conferencia c JOIN a.investigador i ORDER BY c.nombre, i.nombreCompleto";
            resultados = em.createQuery(jpql3, Object[].class).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("    No hay datos");
            } else {
                for (Object[] row : resultados) {
                    System.out.println("    - " + row[0] + " -> " + row[1]);
                }
            }

            // iv) conferencias en las que ha estado cada investigador
            System.out.println("\n  iv) Conferencias en las que ha estado cada investigador:");
            String jpql4 = "SELECT i.nombreCompleto, c.nombre FROM asistenciaconferencia a JOIN a.investigador i JOIN a.conferencia c ORDER BY i.nombreCompleto, c.nombre";
            resultados = em.createQuery(jpql4, Object[].class).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("    No hay datos");
            } else {
                for (Object[] row : resultados) {
                    System.out.println("    - " + row[0] + " -> " + row[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en consultas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // PARTE d): ACTUALIZACIONES

    private static void ejecutarActualizaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("\n  i) Investigador 2 ahora solo asiste a Conferencia 2:");

            // Buscar investigador 2 (Luisa Puertas Soto)
            investigador inv2 = em.find(investigador.class, "45768434R");

            // Buscar Conferencia 2 por nombre
            Query queryC2 = em.createQuery("SELECT c FROM conferencia c WHERE c.nombre = :nombre");
            queryC2.setParameter("nombre", "Conferencia 2");
            List<conferencia> resultadosC2 = queryC2.getResultList();
            conferencia c2 = resultadosC2.isEmpty() ? null : resultadosC2.get(0);

            if (inv2 != null && c2 != null) {
                // Eliminar todas las asistencias actuales del investigador 2
                Query deleteQuery = em
                        .createQuery("DELETE FROM asistenciaconferencia a WHERE a.investigador.dni = :dni");
                deleteQuery.setParameter("dni", "45768434R");
                int eliminadas = deleteQuery.executeUpdate();
                System.out.println("    Eliminadas " + eliminadas + " asistencias anteriores");

                asistenciaconferencia nuevaasistencia = crearasistencia(inv2, c2, "2021-01-12", "Sevilla", 4.0);
                em.persist(nuevaasistencia);
                System.out.println(" " + inv2.getNombreCompleto() + " ahora solo asiste a " + c2.getNombre());
            } else {
                System.out.println(" No se encontró investigador 2 o conferencia 2");
            }

            // ii) Actualizar fecha de conferencia 4 a fecha actual
            System.out.println("\n  ii) Actualizando fecha de Conferencia 4 a fecha actual:");
            Query queryC4 = em.createQuery("SELECT c FROM conferencia c WHERE c.nombre = :nombre");
            queryC4.setParameter("nombre", "Conferencia 4");
            List<conferencia> resultadosC4 = queryC4.getResultList();
            conferencia c4 = resultadosC4.isEmpty() ? null : resultadosC4.get(0);

            if (c4 != null) {
                c4.setFechaHoraInicio(new Date());
                em.merge(c4);
                System.out.println(" " + c4.getNombre() + " actualizada con fecha actual: " + new Date());
            } else {
                System.out.println(" No se encontró conferencia 4");
            }

            // iii) Todos los investigadores trabajan en proyecto 3
            System.out.println("\n  iii) Todos los investigadores ahora trabajan en Proyecto 3:");

            // Buscar proyecto 3
            proyecto p3 = em.find(proyecto.class, "Proyecto 3");

            if (p3 != null) {
                // Obtener todos los investigadores
                List<investigador> todosInvestigadores = em.createQuery(
                        "SELECT i FROM investigador i", investigador.class).getResultList();

                int asignados = 0;
                for (investigador inv : todosInvestigadores) {
                    inv.setProyecto(p3);
                    em.merge(inv);
                    asignados++;
                }

                System.out.println(" " + asignados + " investigadores ahora trabajan en " + p3.getNombre());
            } else {
                System.out.println(" No se encontró proyecto 3");
            }

            tx.commit();
            System.out.println("\n Todas las actualizaciones realizadas correctamente");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("  Error en actualizaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // PARTE e): ELIMINACIONES

    private static void ejecutarEliminaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("\n  i) Eliminando investigador 2 (Luisa Puertas Soto):");
            investigador inv2 = em.find(investigador.class, "45768434R");
            if (inv2 != null) {
                Query deleteAsistencias = em
                        .createQuery("DELETE FROM asistenciaconferencia a WHERE a.investigador.dni = :dni");
                deleteAsistencias.setParameter("dni", "45768434R");
                int asistenciasEliminadas = deleteAsistencias.executeUpdate();
                System.out.println("    Eliminadas " + asistenciasEliminadas + " asistencias");

                em.remove(inv2);
                System.out.println("Investigador 2 eliminado correctamente");
            } else {
                System.out.println("Investigador 2 no encontrado (tal vez ya fue eliminado)");
            }

            System.out.println("\n  ii) Eliminando proyecto 1:");
            proyecto p1 = em.find(proyecto.class, "Proyecto 1");
            if (p1 != null) {
                List<investigador> investigadoresP1 = em.createQuery(
                        "SELECT i FROM investigador i WHERE i.proyecto.nombre = :proyectoNombre", investigador.class)
                        .setParameter("proyectoNombre", "Proyecto 1")
                        .getResultList();

                for (investigador inv : investigadoresP1) {
                    inv.setProyecto(null);
                    em.merge(inv);
                }
                System.out.println("Desasociados " + investigadoresP1.size() + " investigadores");

                em.remove(p1);
                System.out.println("Proyecto 1 eliminado correctamente");
            } else {
                System.out.println("Proyecto 1 no encontrado (tal vez ya fue eliminado)");
            }

            System.out.println("\n  iii) Eliminando conferencia 4:");
            Query queryC4 = em.createQuery("SELECT c FROM conferencia c WHERE c.nombre = 'Conferencia 4'");
            List<conferencia> resultadosC4 = queryC4.getResultList();
            if (!resultadosC4.isEmpty()) {
                conferencia c4 = resultadosC4.get(0);

                Query deleteAsistenciasC4 = em
                        .createQuery("DELETE FROM asistenciaconferencia a WHERE a.conferencia.id = :id");
                deleteAsistenciasC4.setParameter("id", c4.getId());
                int asistenciasEliminadasC4 = deleteAsistenciasC4.executeUpdate();
                System.out.println("    Eliminadas " + asistenciasEliminadasC4 + " asistencias");

                em.remove(c4);
                System.out.println("Conferencia 4 eliminada correctamente");
            } else {
                System.out.println("Conferencia 4 no encontrada (tal vez ya fue eliminada)");
            }

            tx.commit();
            System.out.println("\nTodas las eliminaciones realizadas correctamente");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error en eliminaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
