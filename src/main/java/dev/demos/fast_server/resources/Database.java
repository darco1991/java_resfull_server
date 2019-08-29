package dev.demos.fast_server.resources;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static String tempo = System.getProperty("java.io.tmpdir");
    public static String url = "jdbc:sqlite:" + tempo + "/fast_server.db";

    public static void iniciar() {
        System.out.println("Inicializando base de datos...");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error con el driver sqlite...");
            System.out.println(e.getMessage());
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created or connected.");
                String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n"
                        + "    cedula integer PRIMARY KEY,\n"
                        + "    nombre text NOT NULL,\n"
                        + "    apellido text,\n"
                        + "    email text,\n"
                        + "    telefono text\n"
                        + ");";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
