package com.example;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Ejercicio3 {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/empresa";
        String user = "root";
        String password = "1234";

        try (Connection conexion = DriverManager.getConnection(url, user, password)) {
            for (int hijos = 1; hijos <= 5; hijos++) empleadosPorHijos(conexion, hijos);
            empleadosPorSalario(conexion, 1200, 1300);
            empleadosPorSalario(conexion, 1300, 1400);
            departamentosPorPalabra(conexion, "Comercial");
            departamentosPorPalabra(conexion, "Recursos");
            centroPorNumero(conexion, 101);
            centroPorNumero(conexion, 102);
            empleadoPorNombre(conexion, "Juan");
            empleadoPorNombre(conexion, "María");
            empleadosCumpleMes(conexion, 1);
            empleadosCumpleMes(conexion, 11);
            empleadosSalarioTotal(conexion, 1300);
            empleadosSalarioTotal(conexion, 1500);
            empleadosYExtensionesPorDepto(conexion);
            empleadosPorDeptoSalario(conexion, 1300);
            empleadosPorDeptoSalario(conexion, 1500);
            empleadosPorEdadIngreso(conexion, 20);
            empleadosPorEdadIngreso(conexion, 30);
            empleadosPorEdadIngreso(conexion, 40);
            empleadosPorAntiguedadYSueldo(conexion, 3, 1300);
            empleadosPorAntiguedadYSueldo(conexion, 5, 1500);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void empleadosPorHijos(Connection con, int hijos) throws SQLException {
        String sql = "select comision, nombre, salario from empleados where num_hijos > ? order by comision, nombre";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hijos);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDouble("salario") + " " + rs.getDouble("comision"));
        }
    }

    static void empleadosPorSalario(Connection con, double min, double max) throws SQLException {
        String sql = "select nombre, salario from empleados where salario between ? and ? order by nombre";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDouble("salario"));
        }
    }

    static void departamentosPorPalabra(Connection con, String palabra) throws SQLException {
        String sql = "select nombre from departamentos where nombre like ? order by nombre";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + palabra + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getString("nombre"));
        }
    }

    static void centroPorNumero(Connection con, int numero) throws SQLException {
        String sql = "select * from centros where numero = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getInt("numero") + " " + rs.getString("nombre") + " " + rs.getString("direccion"));
        }
    }

    static void empleadoPorNombre(Connection con, String nombre) throws SQLException {
        String sql = "select * from empleados where nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getInt("cod") + " " + rs.getString("nombre") + " " + rs.getDouble("salario") + " " + rs.getDate("fecha_ingreso"));
        }
    }

    static void empleadosCumpleMes(Connection con, int mes) throws SQLException {
        String sql = "select nombre, fecha_nacimiento from empleados where month(fecha_nacimiento) = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mes);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getString("nombre") + " " + rs.getDate("fecha_nacimiento"));
        }
    }

    static void empleadosSalarioTotal(Connection con, double minimo) throws SQLException {
        String sql = "select cod, nombre, (salario + ifnull(comision,0)) as total from empleados where (salario + ifnull(comision,0)) > ? order by cod";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, minimo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println(rs.getInt("cod") + " " + rs.getString("nombre") + " " + rs.getDouble("total"));
        }
    }

    static void empleadosYExtensionesPorDepto(Connection con) throws SQLException {
        String sql = "select departamento, count(*) as num_empleados, count(distinct telefono) as extensiones from empleados group by departamento";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println("Depto " + rs.getInt("departamento") + ": " + rs.getInt("num_empleados") + " empleados, " + rs.getInt("extensiones") + " extensiones");
        }
    }

    static void empleadosPorDeptoSalario(Connection con, double valor) throws SQLException {
        String sql = "select departamento, nombre, salario from empleados where salario > ? order by departamento, nombre";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, valor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) System.out.println("Depto " + rs.getInt("departamento") + ": " + rs.getString("nombre") + " " + rs.getDouble("salario"));
        }
    }

    static void empleadosPorEdadIngreso(Connection con, int edadMin) throws SQLException {
        String sql = "select nombre, fecha_nacimiento, fecha_ingreso from empleados";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            LocalDate hoy = LocalDate.now();
            while (rs.next()) {
                LocalDate nacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
                LocalDate ingreso = rs.getDate("fecha_ingreso").toLocalDate();
                int edad = Period.between(nacimiento, ingreso).getYears();
                if (edad > edadMin) System.out.println(rs.getString("nombre") + " " + rs.getDate("fecha_nacimiento") + " " + rs.getDate("fecha_ingreso") + " Edad ingreso: " + edad);
            }
        }
    }

    static void empleadosPorAntiguedadYSueldo(Connection con, int años, double sueldoMax) throws SQLException {
        String sql = "select nombre, fecha_ingreso, salario from empleados";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            LocalDate hoy = LocalDate.now();
            while (rs.next()) {
                LocalDate ingreso = rs.getDate("fecha_ingreso").toLocalDate();
                int antiguedad = Period.between(ingreso, hoy).getYears();
                if (antiguedad > años && rs.getDouble("salario") < sueldoMax) System.out.println(rs.getString("nombre") + " " + rs.getDate("fecha_ingreso") + " " + rs.getDouble("salario") + " Antiguedad: " + antiguedad);
            }
        }
    }
}
