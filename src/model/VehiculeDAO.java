package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDAO extends BaseModel {
    public List<Vehicule> readAllVehicules() {
        List<Vehicule> vehicules = new ArrayList<>();
        String sql = "SELECT * FROM vehicules";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vehicule v = new Vehicule(
                        rs.getInt("id_vehicule"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getString("type"),
                        rs.getBoolean("disponible")
                );
                vehicules.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicules;
    }

    public void addVehicule(Vehicule v) {
        String sql = "INSERT INTO vehicules (marque, modele, type, disponible) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getType());
            ps.setBoolean(4, v.isDisponible());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public void updateVehicule(Vehicule v) {
        String sql = "UPDATE vehicules SET marque=?, modele=?, type=?, disponible=? WHERE id_vehicule=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getType());
            ps.setBoolean(4, v.isDisponible());
            ps.setInt(5, v.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicule(int id) {
        String sql = "DELETE FROM vehicules WHERE id_vehicule=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}