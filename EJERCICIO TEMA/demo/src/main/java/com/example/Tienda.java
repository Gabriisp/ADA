package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tienda {

    public static void insertarCliente(Connection conn, String password, String usuario, String direccion, String telefono) {
        String sql = "INSERT INTO clientes (password, usuario, direccion, telefono) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, password);
            ps.setString(2, usuario);
            ps.setString(3, direccion);
            ps.setString(4, telefono);

            ps.executeUpdate();
            System.out.println("Cliente insertado: " + usuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
