package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.entidades.conferencia;
import com.example.entidades.proyecto;
import jakarta.persistence.EntityManager;

public class ConsultasJPQL {
    private EntityManager em;

    public ConsultasJPQL(EntityManager em) {
        this.em = em;
    }

    // 1. El nombre de las conferencias cuya duración sea superior a las dos horas.
    public void ejecutarConsulta1() {
        System.out.println("\n1. Conferencias con duración superior a 2 horas:");
        try {
            String jpql = "SELECT c.nombre FROM conferencia c WHERE c.numeroHoras > 2.0";
            List<String> resultados = em.createQuery(jpql, String.class).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("   No hay conferencias con duración superior a 2 horas");
            } else {
                for (String nombre : resultados) {
                    System.out.println("   - " + nombre);
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 1: " + e.getMessage());
        }
    }

    // 2. El nombre de los investigadores que participaron en la conferencia de la
    // mayor duración.
    public void ejecutarConsulta2() {
        System.out.println("\n2. Investigadores que participaron en la conferencia de mayor duración:");
        try {
            // Primero encontrar la conferencia con mayor duración
            String jpqlMax = "SELECT MAX(c.numeroHoras) FROM conferencia c";
            Double maxHoras = em.createQuery(jpqlMax, Double.class).getSingleResult();

            if (maxHoras != null) {
                String jpql = "SELECT DISTINCT i.nombreCompleto " +
                        "FROM AsistenciaConferencia a " +
                        "JOIN a.investigador i " +
                        "JOIN a.conferencia c " +
                        "WHERE c.numeroHoras = :maxHoras";

                List<String> resultados = em.createQuery(jpql, String.class)
                        .setParameter("maxHoras", maxHoras)
                        .getResultList();

                if (resultados.isEmpty()) {
                    System.out.println(
                            "   No hay investigadores en la conferencia de mayor duración (" + maxHoras + " horas)");
                } else {
                    System.out.println("   Conferencia de mayor duración: " + maxHoras + " horas");
                    for (String nombre : resultados) {
                        System.out.println("   - " + nombre);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 2: " + e.getMessage());
        }
    }

    // 3. Toda la información sobre la conferencia de menor duración.
    public void ejecutarConsulta3() {
        System.out.println("\n3. Información de la conferencia de menor duración:");
        try {
            // Primero encontrar la conferencia con menor duración
            String jpqlMin = "SELECT MIN(c.numeroHoras) FROM conferencia c";
            Double minHoras = em.createQuery(jpqlMin, Double.class).getSingleResult();

            if (minHoras != null) {
                String jpql = "SELECT c FROM conferencia c WHERE c.numeroHoras = :minHoras";
                List<conferencia> resultados = em.createQuery(jpql, conferencia.class)
                        .setParameter("minHoras", minHoras)
                        .getResultList();

                if (resultados.isEmpty()) {
                    System.out.println("   No hay conferencia con la menor duración");
                } else {
                    conferencia conf = resultados.get(0);
                    System.out.println("   Nombre: " + conf.getNombre());
                    System.out.println("   Fecha: " + conf.getFechaHoraInicio());
                    System.out.println("   Lugar: " + conf.getLugar());
                    System.out.println("   Horas: " + conf.getNumeroHoras());
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 3: " + e.getMessage());
        }
    }

    // 4. Los proyectos llevados a cabo por un determinado investigador.
    public void ejecutarConsulta4() {
        System.out.println("\n4. Proyectos del investigador 'Juan Pérez Martínez':");
        try {
            String jpql = "SELECT p.nombre " +
                    "FROM investigador i " +
                    "JOIN i.proyectos p " +
                    "WHERE i.nombreCompleto = :nombreInvestigador";

            List<String> resultados = em.createQuery(jpql, String.class)
                    .setParameter("nombreInvestigador", "Juan Pérez Martínez")
                    .getResultList();

            if (resultados.isEmpty()) {
                System.out.println("   El investigador no trabaja en ningún proyecto");
            } else {
                for (String proyecto : resultados) {
                    System.out.println("   - " + proyecto);
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 4: " + e.getMessage());
        }
    }

    // 5. El número de conferencias en las que ha participado un determinado
    // investigador.
    public void ejecutarConsulta5() {
        System.out.println("\n5. Número de conferencias del investigador 'María Ruiz Sánchez':");
        try {
            String jpql = "SELECT COUNT(a) " +
                    "FROM AsistenciaConferencia a " +
                    "JOIN a.investigador i " +
                    "WHERE i.nombreCompleto = :nombreInvestigador";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("nombreInvestigador", "María Ruiz Sánchez")
                    .getSingleResult();

            System.out.println("   Ha participado en " + count + " conferencias");
        } catch (Exception e) {
            System.out.println("   Error en consulta 5: " + e.getMessage());
        }
    }

    // 6. Dni, nombre y apellidos de los investigadores que trabajan en el proyecto
    // 4.
    public void ejecutarConsulta6() {
        System.out.println("\n6. Investigadores que trabajan en el 'Proyecto 4':");
        try {
            String jpql = "SELECT i.dni, i.nombreCompleto " +
                    "FROM investigador i " +
                    "JOIN i.proyectos p " +
                    "WHERE p.nombre = :nombreProyecto";

            List<Object[]> resultados = em.createQuery(jpql, Object[].class)
                    .setParameter("nombreProyecto", "Proyecto 4")
                    .getResultList();

            if (resultados.isEmpty()) {
                System.out.println("   No hay investigadores en este proyecto");
            } else {
                for (Object[] row : resultados) {
                    String dni = (String) row[0];
                    String nombre = (String) row[1];
                    System.out.println("   - DNI: " + dni + ", Nombre: " + nombre);
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 6: " + e.getMessage());
        }
    }

    // 7. Toda la información sobre los proyectos en los que trabajan los
    // investigadores
    // que participaron en la conferencia 5 (suponiendo que existe Conferencia 5).
    public void ejecutarConsulta7() {
        System.out.println("\n7. Proyectos de investigadores que participaron en 'Conferencia 2':");
        try {
            // Primero verificamos si existe la conferencia
            String jpqlVerificar = "SELECT COUNT(c) FROM conferencia c WHERE c.nombre = 'Conferencia 2'";
            Long existe = em.createQuery(jpqlVerificar, Long.class).getSingleResult();

            if (existe > 0) {
                String jpql = "SELECT DISTINCT p " +
                        "FROM proyecto p " +
                        "JOIN p.investigadores i " +
                        "JOIN i.asistencias a " +
                        "JOIN a.conferencia c " +
                        "WHERE c.nombre = 'Conferencia 2'";

                List<proyecto> resultados = em.createQuery(jpql, proyecto.class).getResultList();

                if (resultados.isEmpty()) {
                    System.out.println("   No hay proyectos relacionados con esta conferencia");
                } else {
                    for (proyecto p : resultados) {
                        System.out.println("   - Proyecto: " + p.getNombre());
                        System.out.println("     Fecha inicio: " + p.getFechaInicio());
                    }
                }
            } else {
                System.out.println("   La conferencia especificada no existe");
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 7: " + e.getMessage());
        }
    }

    // 8. La misma información que el anterior, pero para una conferencia
    // determinada.
    public void ejecutarConsulta8() {
        System.out.println("\n8. Proyectos de investigadores que participaron en una conferencia específica:");
        try {
            String nombreConferencia = "Conferencia 1"; // Se puede cambiar por cualquier conferencia

            // Versión con parámetro
            String jpql = "SELECT DISTINCT p " +
                    "FROM proyecto p " +
                    "JOIN p.investigadores i " +
                    "JOIN i.asistencias a " +
                    "JOIN a.conferencia c " +
                    "WHERE c.nombre = :nombreConf";

            List<proyecto> resultados = em.createQuery(jpql, proyecto.class)
                    .setParameter("nombreConf", nombreConferencia)
                    .getResultList();

            System.out.println("   Para la conferencia: " + nombreConferencia);
            if (resultados.isEmpty()) {
                System.out.println("   No hay proyectos relacionados con esta conferencia");
            } else {
                System.out.println("   Proyectos encontrados: " + resultados.size());
                for (proyecto p : resultados) {
                    System.out.println("   - " + p.getNombre());
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 8: " + e.getMessage());
        }
    }

    // 9. El dni de los investigadores que trabajen en un proyecto cuya fecha de
    // inicio
    // sea menor a una fecha dada.
    public void ejecutarConsulta9() {
        System.out.println("\n9. Investigadores en proyectos con fecha de inicio anterior a 2020-08-01:");
        try {
            Date fechaLimite = new SimpleDateFormat("yyyy-MM-dd").parse("2020-08-01");

            String jpql = "SELECT DISTINCT i.dni " +
                    "FROM investigador i " +
                    "JOIN i.proyectos p " +
                    "WHERE p.fechaInicio < :fechaLimite";

            List<String> resultados = em.createQuery(jpql, String.class)
                    .setParameter("fechaLimite", fechaLimite)
                    .getResultList();

            if (resultados.isEmpty()) {
                System.out.println("   No hay investigadores en proyectos anteriores a esa fecha");
            } else {
                System.out.println("   DNIs encontrados: " + resultados.size());
                for (String dni : resultados) {
                    System.out.println("   - " + dni);
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 9: " + e.getMessage());
        }
    }

    // 10. Toda la información sobre las conferencias en las que haya participado
    // un investigador con un apellido concreto.
    public void ejecutarConsulta10() {
        System.out.println("\n10. Conferencias en las que participaron investigadores con apellido 'Pérez':");
        try {
            String jpql = "SELECT DISTINCT c " +
                    "FROM conferencia c " +
                    "JOIN AsistenciaConferencia a ON a.conferencia = c " +
                    "JOIN a.investigador i " +
                    "WHERE i.nombreCompleto LIKE '%Pérez%'";

            List<conferencia> resultados = em.createQuery(jpql, conferencia.class).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("   No hay conferencias para investigadores con ese apellido");
            } else {
                System.out.println("   Conferencias encontradas: " + resultados.size());
                for (conferencia c : resultados) {
                    System.out.println("   - " + c.getNombre() +
                            " (Lugar: " + c.getLugar() +
                            ", Horas: " + c.getNumeroHoras() +
                            ", Fecha: " + c.getFechaHoraInicio() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("   Error en consulta 10: " + e.getMessage());
        }
    }

    // Método para ejecutar todas las consultas
    public void ejecutarTodasConsultas() {
        System.out.println("EJERCICIO 3: CONSULTAS JPQL ");
        ejecutarConsulta1();
        ejecutarConsulta2();
        ejecutarConsulta3();
        ejecutarConsulta4();
        ejecutarConsulta5();
        ejecutarConsulta6();
        ejecutarConsulta7();
        ejecutarConsulta8();
        ejecutarConsulta9();
        ejecutarConsulta10();
        System.out.println("\n EJERCICIO 3 terminado");
    }
}