package com.example.myproperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/myPropertyDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Mart2506sql";

    public DBConnection() {
    }

    //Connexion a la BD
    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("successful connection !!!");
        } catch (SQLException e) {
            System.out.println("connexion error !!!");
            e.printStackTrace();
        }
        return connection;
    }
}
