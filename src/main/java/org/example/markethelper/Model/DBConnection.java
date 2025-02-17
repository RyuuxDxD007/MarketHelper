package org.example.markethelper.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Information de connection
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "toto1234";

    private static Connection con;

    public static Connection getCon() throws SQLException {
        Statement requete = null;
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database: " + URL);
            requete = con.createStatement();

            try {

                requete.executeUpdate("CREATE DATABASE MARKETHELPER");
            } catch (SQLException e) {

                System.out.println("Base de données UE1392 déjà existante.\n" + e.getMessage());
            }
        }
        return con;
    }

    public static void close() {
        if (con != null) {
            try {
                con.close();
                con = null; // Reset connection
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

}
