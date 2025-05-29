package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static DBConnection dbConnection;

    private Connection connection;

    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventdb", "root", "1212");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static DBConnection getDBConnection(){
        return dbConnection == null ? dbConnection = new DBConnection() : dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
