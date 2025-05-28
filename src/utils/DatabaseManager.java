package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/rapizz_pizzeria?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "rapizz";
    private static final String PASSWORD = "pizza123";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
        return connection;
    }
}
