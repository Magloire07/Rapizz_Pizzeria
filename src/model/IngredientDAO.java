package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Ingredient operations.
 */
public class IngredientDAO extends BaseModel {

    /**
     * Inserts a new ingredient into the database.
     */
    public void createIngredient(String name, int quantity, double price) throws SQLException {
        String sql = "INSERT INTO Ingredient (name, quantity, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
        }
    }

    /**
     * Returns all ingredients as simple string representations.
     */
    public List<String> readAllIngredients() throws SQLException {
        List<String> ingredients = new ArrayList<>();
        String sql = "SELECT idIngredient, name, quantity, price FROM Ingredient";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ingredients.add(
                    rs.getInt("idIngredient") + ": " + rs.getString("name") +
                    " (qty=" + rs.getInt("quantity") + ", price=" + rs.getDouble("price") + ")"
                );
            }
        }
        return ingredients;
    }

    /**
     * Fetches all ingredients and maps each row to an Ingredient object.
     */
    public List<Ingredient> readAllIngredientsAsObjects() throws SQLException {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT idIngredient, name, quantity, price FROM Ingredient";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ingredient ing = new Ingredient(
                    rs.getInt("idIngredient"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                );
                list.add(ing);
            }
        }
        return list;
    }

    /**
     * Updates the name, quantity, and price of an existing ingredient.
     */
    public void updateIngredient(int id, String name, int quantity, double price) throws SQLException {
        String sql = "UPDATE Ingredient SET name = ?, quantity = ?, price = ? WHERE idIngredient = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes an ingredient by its ID.
     */
    public void deleteIngredient(int id) throws SQLException {
        String sql = "DELETE FROM Ingredient WHERE idIngredient = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
