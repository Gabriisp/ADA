package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            if (conn != null) {
                System.out.println("Conexión establecida correctamente");

                Tienda.insertarCliente(conn, "333", "Juan", "Paseo de Roma 34", "333444555");
                Tienda.insertarCliente(conn, "222", "Lola", "Av. Alemania", "222333444");
                Tienda.insertarCliente(conn, "555", "María", "Avda. París, 7", "555666777");
                Tienda.insertarCliente(conn, "444", "Pedro", "Plaza de la Constitución, 1", " ");
                Tienda.insertarCliente(conn, "111", "Pepe", "Av. París", "111222333");

            }

        } catch (SQLException e) {
            System.out.println(" Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }
}
