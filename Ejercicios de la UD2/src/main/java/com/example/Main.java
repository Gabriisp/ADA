package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión establecida con la base de datos.\n");

            Scanner sc = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("Tienda Menu");
                System.out.println("1. Insertar cliente (por teclado)");
                System.out.println("2. Actualizar cliente");
                System.out.println("3. Borrar cliente");
                System.out.println("4. Mostrar clientes");
                System.out.println("5. Insertar datos automáticos");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> Tienda.insertarCliente(conn);
                    case 2 -> Tienda.actualizarCliente(conn);
                    case 3 -> Tienda.borrarCliente(conn);
                    case 4 -> Tienda.mostrarClientes(conn);
                    case 5 -> insertarDatosAutomaticos(conn);
                    case 0 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println(" opción no válida.\n");
                }

            } while (opcion != 0);

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    private static void insertarDatosAutomaticos(Connection conn) {
        System.out.println("\n Insertar datos automaticos");
        Tienda.insertarCliente(conn, "333", "Juan", "Paseo de Roma 34", "333444555");
        Tienda.insertarCliente(conn, "222", "Lola", "Av. Alemania", "222333444");
        Tienda.insertarCliente(conn, "555", "María", "Avda. París, 7", "555666777");
        Tienda.insertarCliente(conn, "444", "Pedro", "Plaza de la Constitución, 1", " ");
        Tienda.insertarCliente(conn, "111", "Pepe", "Av. París", "111222333");
    }
}
