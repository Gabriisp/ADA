package com.example;
import java.sql.*;

public class Ejercicio4 {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/BD_EJERCICIO4";
        String user = "root";
        String password = "1234";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            llamarTrabajadoresPorTarifa(con, 10, 12);
            llamarOficiosPorEdificio(con, 312);
            llamarTrabajadorYSupervisor(con);
            llamarTrabajadoresOficinas(con);
            llamarTotalDiasActividad(con, "FONTANERO", 312);
            llamarTiposOficios(con);
            llamarTarifaMaxPorSupervisor(con);
            llamarTarifaMaxSupervisorMasDeUnTrabajador(con);
            llamarTarifaMenorPromedio(con);
            llamarTarifaMenorPromedioOficio(con);
            llamarTarifaMenorPromedioSupervisor(con);
            llamarSupervisoresTarifaPorEncima(con, 15);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void llamarTrabajadoresPorTarifa(Connection con, double min, double max) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call trabajadores_por_tarifa(?,?)}")) {
            cs.setDouble(1, min);
            cs.setDouble(2, max);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDouble("tarifa"));
        }
    }

    static void llamarOficiosPorEdificio(Connection con, int idEd) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call oficios_por_edificio(?)}")) {
            cs.setInt(1, idEd);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("oficio"));
        }
    }

    static void llamarTrabajadorYSupervisor(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call trabajador_y_supervisor()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("trabajador") + " : " + rs.getString("supervisor"));
        }
    }

    static void llamarTrabajadoresOficinas(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call trabajadores_oficinas()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("nombre"));
        }
    }

    static void llamarTotalDiasActividad(Connection con, String oficio, int idEd) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call total_dias_actividad(?,?)}")) {
            cs.setString(1, oficio);
            cs.setInt(2, idEd);
            ResultSet rs = cs.executeQuery();
            if(rs.next()) System.out.println("Total días: " + rs.getInt("total_dias"));
        }
    }

    static void llamarTiposOficios(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call tipos_oficios()}")) {
            ResultSet rs = cs.executeQuery();
            if(rs.next()) System.out.println("Número de oficios: " + rs.getInt("num_oficios"));
        }
    }

    static void llamarTarifaMaxPorSupervisor(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call tarifa_max_por_supervisor()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println("Supervisor " + rs.getInt("ID_SUPV") + " max tarifa: " + rs.getDouble("max_tarifa"));
        }
    }

    static void llamarTarifaMaxSupervisorMasDeUnTrabajador(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call tarifa_max_supervisor_mas_de_un_trabajador()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println("Supervisor " + rs.getInt("ID_SUPV") + " max tarifa: " + rs.getDouble("max_tarifa"));
        }
    }

    static void llamarTarifaMenorPromedio(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call tarifa_menor_promedio()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDouble("tarifa"));
        }
    }

    static void llamarTarifaMenorPromedioOficio(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call tarifa_menor_promedio_oficio()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDouble("tarifa"));
        }
    }

    static void llamarTarifaMenorPromedioSupervisor(Connection con) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call tarifa_menor_promedio_supervisor()}")) {
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDouble("tarifa"));
        }
    }

    static void llamarSupervisoresTarifaPorEncima(Connection con, double valor) throws SQLException {
        try (CallableStatement cs = con.prepareCall("{call supervisores_tarifa_por_encima(?)}")) {
            cs.setDouble(1, valor);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) System.out.println("Supervisor: " + rs.getInt("ID_SUPV"));
        }
    }
}
