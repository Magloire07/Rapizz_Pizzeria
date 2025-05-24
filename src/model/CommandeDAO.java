package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO extends BaseModel {
    public void createCommande(int idClient, String dateCommande) throws SQLException {
        String sql = "INSERT INTO Commande (idClient, dateCommande) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            stmt.setString(2, dateCommande);
            stmt.executeUpdate();
        }
    }

    public List<String> readAllCommandes() throws SQLException {
        List<String> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                commandes.add(rs.getInt("idCommande") + ": Client " + rs.getInt("idClient") + " - " + rs.getString("dateCommande"));
            }
        }
        return commandes;
    }

    public void updateCommande(int idCommande, int idClient, String dateCommande) throws SQLException {
        String sql = "UPDATE Commande SET idClient = ?, dateCommande = ? WHERE idCommande = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            stmt.setString(2, dateCommande);
            stmt.setInt(3, idCommande);
            stmt.executeUpdate();
        }
    }

    public void deleteCommande(int idCommande) throws SQLException {
        String sql = "DELETE FROM Commande WHERE idCommande = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            stmt.executeUpdate();
        }
    }

    public ArrayList<Commande> readAllCommandesAsObjects() throws SQLException {
        ArrayList<Commande> commandes = new ArrayList<>();
        String sql = "SELECT c.idCommande, cl.nom AS nomClient, p.nom AS nomPizza, cl.solde AS soldeClient, c.idCommande AS numeroCommande, c.taille " +
                     "FROM Commande c " +
                     "JOIN Client cl ON c.idClient = cl.idClient " +
                     "JOIN Pizza p ON c.idPizza = p.idPizza"; // Adjust join/columns as per your schema
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                commandes.add(new Commande(
                    rs.getString("nomClient"),
                    rs.getString("nomPizza"),
                    rs.getDouble("soldeClient"),
                    rs.getInt("numeroCommande"),
                    rs.getString("taille")
                ));
            }
        }
        return commandes;
    }
}