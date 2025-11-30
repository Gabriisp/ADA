package com.example;

import java.sql.*;
import java.util.Scanner;

public class Tienda {

    public static void insertarCliente(Connection conn, String password, String usuario, String direccion, String telefono) {
        String sql = "INSERT INTO clientes (password, usuario, direccion, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, usuario);
            ps.setString(3, direccion);
            ps.setString(4, telefono);
            ps.executeUpdate();
            System.out.println("Cliente insertado automáticamente: " + usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertarCliente(Connection conn) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n Insertar cliente");
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        insertarCliente(conn, password, usuario, direccion, telefono);
    }

    public static void actualizarCliente(Connection conn) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n Actualizar cliente");
        System.out.print("Usuario del cliente a actualizar: ");
        String usuario = sc.nextLine();
        System.out.print("Nueva dirección: ");
        String nuevaDireccion = sc.nextLine();
        System.out.print("Nuevo teléfono: ");
        String nuevoTelefono = sc.nextLine();

        String sql = "UPDATE clientes SET direccion = ?, telefono = ? WHERE usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevaDireccion);
            ps.setString(2, nuevoTelefono);
            ps.setString(3, usuario);

            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("Cliente actualizado");
            else
                System.out.println("No se encontró el usuario");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void borrarCliente(Connection conn) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n BORRAR CLIENTE ");
        System.out.print("Usuario del cliente a borrar: ");
        String usuario = sc.nextLine();

        String sql = "DELETE FROM clientes WHERE usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("Cliente eliminado");
            else
                System.out.println("No se encontró el usuario");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarClientes(Connection conn) {
        String sql = "SELECT * FROM clientes";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n Lista de Clientes");
            while (rs.next()) {
                System.out.printf("Usuario: %s | Password: %s | Dirección: %s | Teléfono: %s%n",
                        rs.getString("usuario"),
                        rs.getString("password"),
                        rs.getString("direccion"),
                        rs.getString("telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
