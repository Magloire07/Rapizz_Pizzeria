package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class LivreurDAO extends BaseModel {

    public List<Livreur> readAllLivreurs() {
        List<Livreur> livreurs = new ArrayList<>();
        String sql = "SELECT * FROM livreurs";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livreur l = new Livreur(
                        rs.getInt("id_livreur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("nombre_livraisons"),
                        rs.getInt("duree_livraisons"),
                        rs.getInt("id_vehicule")
                );
                livreurs.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livreurs;
    }
 
    public void addLivreur(Livreur l) {
        String sql = "INSERT INTO livreurs (nom, prenom, nombre_livraisons, duree_livraisons, id_vehicule) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getNom());
            ps.setString(2, l.getPrenom());
            ps.setInt(3, l.getNombreLivraisons());
            ps.setInt(4, l.getDureeLivraisons());
            ps.setInt(5, l.getIdVehicule());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du livreur : veillez consulter la liste  des ID des véhicules disponibles " );
        }
    }

    public void updateLivreur(Livreur l) {
        String sql = "UPDATE livreurs SET nom=?, prenom=?, nombre_livraisons=?, duree_livraisons=?, id_vehicule=? WHERE id_livreur=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getNom());
            ps.setString(2, l.getPrenom());
            ps.setInt(3, l.getNombreLivraisons());
            ps.setInt(4, l.getDureeLivraisons());
            ps.setInt(5, l.getIdVehicule());
            ps.setInt(6, l.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLivreur(int id) {
        String sql = "DELETE FROM livreurs WHERE id_livreur=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignLivreurToDelivery(int livreurId, int livraisonId) {
        String sql = "UPDATE livraison SET id_livreur=? WHERE id_livraison=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, livreurId);
            ps.setInt(2, livraisonId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}