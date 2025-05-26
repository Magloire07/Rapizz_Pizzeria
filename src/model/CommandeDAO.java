package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                     "JOIN Pizza p ON c.idPizza = p.idPizza";
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

    public void genererCommandeAleatoire() throws SQLException {
        Connection conn = getConnection();
        Random rand = new Random();

        // 1. Récupérer tous les clients existants
        List<Integer> clientIds = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT idClient FROM Client")) {
            while (rs.next()) {
                clientIds.add(rs.getInt("idClient"));
            }
        }

        int idClient;
        // 2. Parfois créer un nouveau client
        if (clientIds.isEmpty() || rand.nextBoolean()) {
            // Créer un nouveau client
            String nom = "Client" + (rand.nextInt(1000));
            String prenom = "Prenom" + (rand.nextInt(1000));
            String adresse = "Adresse" + (rand.nextInt(100));
            double solde = 100 + rand.nextInt(500);
            int cagnote = rand.nextInt(10);
            String sqlClient = "INSERT INTO Client (nom, prenom, adresse, solde, cagnote) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlClient, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, adresse);
                stmt.setDouble(4, solde);
                stmt.setInt(5, cagnote);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    idClient = rs.getInt(1);
                }
            }
        } else {
            // Prendre un client existant au hasard
            idClient = clientIds.get(rand.nextInt(clientIds.size()));
        }

        // 3. Récupérer une pizza aléatoire
        int idPizza = -1;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT idPizza FROM Pizza")) {
            List<Integer> pizzaIds = new ArrayList<>();
            while (rs.next()) {
                pizzaIds.add(rs.getInt("idPizza"));
            }
            if (!pizzaIds.isEmpty()) {
                idPizza = pizzaIds.get(rand.nextInt(pizzaIds.size()));
            }
        }

        // 4. Taille aléatoire
        String[] tailles = {"S", "M", "L"};
        String taille = tailles[rand.nextInt(tailles.length)];

        // 5. Insérer la commande
        String sqlCommande = "INSERT INTO Commande (idClient, idPizza, dateCommande, taille) VALUES (?, ?, NOW(), ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCommande)) {
            stmt.setInt(1, idClient);
            stmt.setInt(2, idPizza);
            stmt.setString(3, taille);
            stmt.executeUpdate();
        }
    }

    public static void clearAllCommandes() throws SQLException {
        try (Connection conn = new CommandeDAO().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Commande");
        }
    }
}