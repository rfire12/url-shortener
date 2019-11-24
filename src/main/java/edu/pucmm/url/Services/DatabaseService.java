package edu.pucmm.url.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static DatabaseService instance;
    private String URL = "jdbc:h2:tcp://localhost/~/url-shortener";

    private DatabaseService() {
        registerDriver();
    }

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {

        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {

        }
        return con;
    }

    public void testConnection() {
        try {
            getConnection().close();
            System.out.println("Conexión realizada con éxito!!");
        } catch (SQLException ex) {

        }
    }
}
