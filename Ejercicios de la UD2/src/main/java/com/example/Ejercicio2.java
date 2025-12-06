package com.example;
import java.sql.*;

public class Ejercicio2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/empresa";
        String user = "root";
        String password = "1234";
        // Establecer la conexión
        try (Connection conexion = DriverManager.getConnection(url, user, password)) {
            empleadosMasDeTresHijos(conexion);
            departamentosSinJefe(conexion);
            empleadosSalarioEntre(conexion, 1250, 1300);
            empleadosSalarioEntreOHijos(conexion, 1250, 1300);
            departamentosSinPalabras(conexion);
            departamentosFuncionPresupuesto(conexion);
            empleadosSalarioTotal(conexion, 1300);
            contarEmpleados(conexion);
            contarDeptosYPresupuesto(conexion);
            empleadosYExtensionesDepto(conexion, 112);
            departamentosNoJefes(conexion);
            departamentosJefes(conexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void empleadosMasDeTresHijos(Connection conexion) throws SQLException {
        String sql = "select comision, nombre, salario from empleados where num_hijos > 3 order by comision, nombre";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Empleados con más de tres hijos:");
            while (rs.next()) {
                System.out.println(rs.getString("nombre") + " " + rs.getDouble("salario") + " " + rs.getDouble("comision"));
            }
        }
    }

    static void departamentosSinJefe(Connection conexion) throws SQLException {
        String sql = "select nombre from departamentos where depto_jefe is null";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Departamentos sin jefe:");
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        }
    }

    static void empleadosSalarioEntre(Connection conexion, double min, double max) throws SQLException {
        String sql = "select nombre, salario from empleados where salario between " + min + " and " + max + " order by nombre";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Empleados con salario entre " + min + " y " + max + ":");
            while (rs.next()) {
                System.out.println(rs.getString("nombre") + " " + rs.getDouble("salario"));
            }
        }
    }

    static void empleadosSalarioEntreOHijos(Connection conexion, double min, double max) throws SQLException {
        String sql = "select * from empleados where (salario between " + min + " and " + max + ") or num_hijos > 0";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Empleados con salario entre valores o con hijos:");
            while (rs.next()) {
                System.out.println(rs.getString("nombre") + " " + rs.getDouble("salario") + " " + rs.getInt("num_hijos"));
            }
        }
    }

    static void departamentosSinPalabras(Connection conexion) throws SQLException {
        String sql = "select nombre from departamentos where nombre not like '%dirección%' and nombre not like '%sector%' order by nombre";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Departamentos sin Dirección ni Sector:");
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        }
    }

    static void departamentosFuncionPresupuesto(Connection conexion) throws SQLException {
        String sql = "select nombre from departamentos where (tipo_dir = 'f' and presupuesto <= 5000) or depto_jefe is null order by nombre";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Departamentos en funciones o sin jefe:");
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        }
    }

    static void empleadosSalarioTotal(Connection conexion, double minimo) throws SQLException {
        String sql = "select cod, nombre, (salario + ifnull(comision,0)) as total from empleados where (salario + ifnull(comision,0)) > " + minimo + " order by cod";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Empleados con salario total mayor a " + minimo + ":");
            while (rs.next()) {
                System.out.println(rs.getInt("cod") + " " + rs.getString("nombre") + " " + rs.getDouble("total"));
            }
        }
    }

    static void contarEmpleados(Connection conexion) throws SQLException {
        String sql = "select count(*) as total from empleados";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("Total empleados: " + rs.getInt("total"));
            }
        }
    }

    static void contarDeptosYPresupuesto(Connection conexion) throws SQLException {
        String sql = "select count(*) as total, avg(presupuesto) as promedio from departamentos";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("Total departamentos: " + rs.getInt("total") + " Presupuesto medio: " + rs.getDouble("promedio"));
            }
        }
    }

    static void empleadosYExtensionesDepto(Connection conexion, int depto) throws SQLException {
        String sql = "select count(*) as num_empleados, count(distinct telefono) as extensiones from empleados where departamento = " + depto;
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("Depto " + depto + " Empleados: " + rs.getInt("num_empleados") + " Extensiones: " + rs.getInt("extensiones"));
            }
        }
    }

    static void departamentosNoJefes(Connection conexion) throws SQLException {
        String sql = "select numero from departamentos where numero not in (select distinct depto_jefe from departamentos where depto_jefe is not null)";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Departamentos que no son jefes:");
            while (rs.next()) {
                System.out.println(rs.getInt("numero"));
            }
        }
    }

    static void departamentosJefes(Connection conexion) throws SQLException {
        String sql = "select distinct depto_jefe as numero from departamentos where depto_jefe is not null";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Departamentos que son jefes:");
            while (rs.next()) {
                System.out.println(rs.getInt("numero"));
            }
        }
    }
}
