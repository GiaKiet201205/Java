package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/qlch";
    private static final String USER = "root";
    private static final String PASSWORD = "065002";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}