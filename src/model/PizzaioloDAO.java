package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaioloDAO extends BaseModel {
    public List<Pizzaiolo> readAllPizzaiolos() {
        List<Pizzaiolo> pizzaiolos = new ArrayList<>();
        String sql = "SELECT * FROM pizzaiolos";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pizzaiolo p = new Pizzaiolo(
                        rs.getInt("id_pizzaiolo"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("nombre_pizzas")
                );
                pizzaiolos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzaiolos;
    }

    public void addPizzaiolo(Pizzaiolo p) {
        String sql = "INSERT INTO pizzaiolos (nom, prenom, nombre_pizzas) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setInt(3, p.getNombrePizzas());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePizzaiolo(Pizzaiolo p) {
        String sql = "UPDATE pizzaiolos SET nom=?, prenom=?, nombre_pizzas=? WHERE id_pizzaiolo=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setInt(3, p.getNombrePizzas());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePizzaiolo(int id) {
        String sql = "DELETE FROM pizzaiolos WHERE id_pizzaiolo=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 