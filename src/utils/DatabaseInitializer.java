package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitializer {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "316MIQq~+X";
    private static final String DB_NAME = "rapizz_pizzeria";
    private static final String SQL_FILE = "/home/mitia/BDD/Database/Project/Rapizz_Pizzeria/sql/CreationTables.sql";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Check if database exists
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + DB_NAME + "'");
            if (!rs.next()) {
                // Database doesn't exist, create it
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                System.out.println("Database " + DB_NAME + " created.");
            } else {
                System.out.println("Database " + DB_NAME + " already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize database.");
            return;
        }

        // Now connect to the database and execute the SQL file
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/" + DB_NAME + "?allowPublicKeyRetrieval=true&useSSL=false";
        try (Connection conn = DriverManager.getConnection(dbUrl, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = new String(Files.readAllBytes(Paths.get(SQL_FILE)));
            // Split by semicolon to execute each statement
            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("Tables created or already exist.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to create tables.");
        }
    }
}
