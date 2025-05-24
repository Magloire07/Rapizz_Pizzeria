package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PIzzaDAO extends BaseModel {
    public void createPizza(String nom, double prix) throws SQLException {
        String sql = "INSERT INTO Pizza (nom, prix) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setDouble(2, prix);
            stmt.executeUpdate();
        }
    }

    public List<String> readAllPizzas() throws SQLException {
        List<String> pizzas = new ArrayList<>();
        String sql = "SELECT * FROM Pizza";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pizzas.add(rs.getInt("idPizza") + ": " + rs.getString("nom") + " - " + rs.getDouble("prix"));
            }
        }
        return pizzas;
    }

    public ArrayList<Pizza> readAllPizzasAsObjects() throws SQLException {
        ArrayList<Pizza> pizzas = new ArrayList<>();
        String sql = "SELECT * FROM Pizza";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pizzas.add(new Pizza(
                    rs.getInt("idPizza"),
                    rs.getString("nom"),
                    rs.getDouble("prix"),
                    rs.getString("taille"), // If you have a 'taille' column, else use null or ""
                    rs.getString("iconPath") // If you have an image path column, else use null or ""
                ));
            }
        }
        return pizzas;
    }

    public void updatePizza(int idPizza, String nom, double prix) throws SQLException {
        String sql = "UPDATE Pizza SET nom = ?, prix = ? WHERE idPizza = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setDouble(2, prix);
            stmt.setInt(3, idPizza);
            stmt.executeUpdate();
        }
    }
 
    public void deletePizza(int idPizza) throws SQLException {
        String sql = "DELETE FROM Pizza WHERE idPizza = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPizza);
            stmt.executeUpdate();
        }
    }
}