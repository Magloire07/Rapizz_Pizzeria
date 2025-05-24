package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Client operations.
 */
public class ClientDAO extends BaseModel {

    /**
     * Inserts a new client into the database.
     */
    public void createClient(String nom, String prenom, String adresse, double solde, int cagnote) throws SQLException {
        String sql = "INSERT INTO Client (nom, prenom, adresse, solde, cagnote) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, adresse);
            stmt.setDouble(4, solde);
            stmt.setInt(5, cagnote);
            stmt.executeUpdate();
        }
    }

    /**
     * Returns all clients as simple string representations.
     */
    public List<String> readAllClients() throws SQLException {
        List<String> clients = new ArrayList<>();
        String sql = "SELECT idClient, nom, prenom, adresse, solde, cagnote FROM Client";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(
                    rs.getInt("idClient") + ": " + rs.getString("nom") + " " + rs.getString("prenom") +
                    " - " + rs.getString("adresse") +
                    " (solde=" + rs.getDouble("solde") + ", cagnote=" + rs.getInt("cagnote") + ")"
                );
            }
        }
        return clients;
    }

    /**
     * Returns all clients as Client objects.
     */
    public List<Client> readAllClientsAsObjects() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT idClient, nom, prenom, adresse, solde, cagnote FROM Client";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Client c = new Client(
                    rs.getInt("idClient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getDouble("solde"),
                    rs.getInt("cagnote")
                );
                clients.add(c);
            }
        }
        return clients;
    }

    /**
     * Updates an existing client's details.
     */
    public void updateClient(int idClient, String nom, String prenom, String adresse, double solde, int cagnote) throws SQLException {
        String sql = "UPDATE Client SET nom = ?, prenom = ?, adresse = ?, solde = ?, cagnote = ? WHERE idClient = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, adresse);
            stmt.setDouble(4, solde);
            stmt.setInt(5, cagnote);
            stmt.setInt(6, idClient);
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a client by its ID.
     */
    public void deleteClient(int idClient) throws SQLException {
        String sql = "DELETE FROM Client WHERE idClient = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            stmt.executeUpdate();
        }
    }
}
