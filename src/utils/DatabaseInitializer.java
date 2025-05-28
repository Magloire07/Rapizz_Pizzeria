package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.BaseModel;
import java.util.Map;
import java.util.HashMap;

public class DatabaseInitializer extends BaseModel {
    
    private static final String URL = "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "rapizz";
    private static final String PASSWORD = "pizza123";
    private static final String DB_NAME = "rapizz_pizzeria";

    public static void initialize() {
        try {
            // Step 1: Create database if not exists
            try (Connection conn = java.sql.DriverManager.getConnection(URL, USER, PASSWORD)) {
                Statement st = conn.createStatement();
                st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            } catch (Exception e) {
                System.err.println("Erreur lors de la connexion à MySQL ou lors de la création de la base de données.");
                System.err.println("Vérifiez le nom d'utilisateur, le mot de passe et les droits d'accès MySQL.");
                e.printStackTrace();
                return;
            }

            // Step 2: Connect to the created database
            String dbUrl = "jdbc:mysql://localhost:3306/" + DB_NAME + "?allowPublicKeyRetrieval=true&useSSL=false";
            try (Connection conn = java.sql.DriverManager.getConnection(dbUrl, USER, PASSWORD)) {
                Statement stDrop = conn.createStatement();
                // Drop tables if they exist to avoid column mismatch
                stDrop.executeUpdate("DROP TABLE IF EXISTS livreurs");
                stDrop.executeUpdate("DROP TABLE IF EXISTS pizzaiolos");
                // You may also want to drop other tables if you change their structure
                // stDrop.executeUpdate("DROP TABLE IF EXISTS ...");

                Statement stCreate = conn.createStatement();

                // --- CREATE ALL TABLES BEFORE USING THEM ---
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Client (" +
                    "idClient INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100), " +
                    "prenom VARCHAR(100), " +
                    "adresse VARCHAR(255), " +
                    "solde DOUBLE, " +
                    "cagnote INT" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS pizzaiolos (" +
                    "id_pizzaiolo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100), " +
                    "prenom VARCHAR(100), " +
                    "nombre_pizzas INT" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS vehicules (" +
                    "id_vehicule INT AUTO_INCREMENT PRIMARY KEY, " +
                    "marque VARCHAR(100), " +
                    "modele VARCHAR(100), " +
                    "type VARCHAR(50), " +
                    "disponible BOOLEAN" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS livreurs (" +
                    "id_livreur INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100), " +
                    "prenom VARCHAR(100), " +
                    "nombre_livraisons INT, " +
                    "duree_livraisons INT, " +
                    "id_vehicule INT, " +
                    "FOREIGN KEY (id_vehicule) REFERENCES vehicules(id_vehicule)" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Pizza (" +
                    "idPizza INT PRIMARY KEY, " +
                    "nom VARCHAR(100), " +
                    "prix DOUBLE, " +
                    "taille VARCHAR(5), " +
                    "iconPath VARCHAR(255)" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Ingredient (" +
                    "idIngredient INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "quantity INT, " +
                    "price DOUBLE" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Composer (" +
                    "idPizza INT, " +
                    "idIngredient INT, " +
                    "PRIMARY KEY (idPizza, idIngredient), " +
                    "FOREIGN KEY (idPizza) REFERENCES Pizza(idPizza), " +
                    "FOREIGN KEY (idIngredient) REFERENCES Ingredient(idIngredient)" +
                    ")"
                );
                stCreate.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Commande (" +
                    "idCommande INT AUTO_INCREMENT PRIMARY KEY, " +
                    "idClient INT, " +
                    "idPizza INT, " +
                    "dateCommande DATETIME, " +
                    "taille VARCHAR(5), " +
                    "FOREIGN KEY (idClient) REFERENCES Client(idClient), " +
                    "FOREIGN KEY (idPizza) REFERENCES Pizza(idPizza)" +
                    ")"
                );

                // Clients
                if (isTableEmpty(conn, "Client")) {
                    PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Client (nom, prenom, adresse, solde, cagnote) VALUES (?, ?, ?, ?, ?)");
                    for (int i = 1; i <= 5; i++) {
                        ps.setString(1, "Client" + i);
                        ps.setString(2, "Prenom" + i);
                        ps.setString(3, "Adresse " + i);
                        ps.setDouble(4, 100 + i * 10);
                        ps.setInt(5, 0);
                        ps.executeUpdate();
                    }
                }
                // Pizzaiolos
                if (isTableEmpty(conn, "pizzaiolos")) {
                    PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO pizzaiolos (nom, prenom, nombre_pizzas) VALUES (?, ?, ?)");
                    ps.setString(1, "Mario");
                    ps.setString(2, "Rossi");
                    ps.setInt(3, 0);
                    ps.executeUpdate();
                }
                // Véhicules (doit être AVANT les livreurs)
                int vehiculeId = -1;
                if (isTableEmpty(conn, "vehicules")) {
                    PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO vehicules (marque, modele, type, disponible) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(1, "Peugeot");
                    ps.setString(2, "208");
                    ps.setString(3, "Scooter");
                    ps.setBoolean(4, true);
                    ps.executeUpdate();

                    // Récupère l'id généré pour l'utiliser pour le livreur
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        vehiculeId = rs.getInt(1);
                    }
                } else {
                    // Si déjà présent, prend le premier id existant
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT id_vehicule FROM vehicules LIMIT 1");
                    if (rs.next()) {
                        vehiculeId = rs.getInt("id_vehicule");
                    }
                }

                // Livreurs (après véhicules)
                if (vehiculeId != -1 && isTableEmpty(conn, "livreurs")) {
                    PreparedStatement psLiv = conn.prepareStatement(
                        "INSERT INTO livreurs (nom, prenom, nombre_livraisons, duree_livraisons, id_vehicule) VALUES (?, ?, ?, ?, ?)"
                    );
                    psLiv.setString(1, "Paul");
                    psLiv.setString(2, "Martin");
                    psLiv.setInt(3, 0);
                    psLiv.setInt(4, 0);
                    psLiv.setInt(5, vehiculeId);
                    psLiv.executeUpdate();
                }

                // Ingrédients de base
                if (isTableEmpty(conn, "Ingredient")) {
                    PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Ingredient (name, quantity, price) VALUES (?, ?, ?)");
                    ps.setString(1, "Tomate");
                    ps.setInt(2, 50);
                    ps.setDouble(3, 1.0);
                    ps.executeUpdate();
                    ps.setString(1, "Mozzarella");
                    ps.setInt(2, 50);
                    ps.setDouble(3, 1.5);
                    ps.executeUpdate();
                    ps.setString(1, "Jambon");
                    ps.setInt(2, 30);
                    ps.setDouble(3, 2.0);
                    ps.executeUpdate();
                    ps.setString(1, "4 Fromages");
                    ps.setInt(2, 20);
                    ps.setDouble(3, 2.5);
                    ps.executeUpdate();
                    ps.setString(1, "Salami");
                    ps.setInt(2, 20);
                    ps.setDouble(3, 2.0);
                    ps.executeUpdate();
                    ps.setString(1, "Parma");
                    ps.setInt(2, 15);
                    ps.setDouble(3, 2.8);
                    ps.executeUpdate();
                }

                // Pizzas de base (avec id imposé)
                if (isTableEmpty(conn, "Pizza")) {
                    PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Pizza (idPizza, nom, prix, taille, iconPath) VALUES (?, ?, ?, ?, ?)");
                    ps.setInt(1, 571);
                    ps.setString(2, "Margherita");
                    ps.setDouble(3, 8.0);
                    ps.setString(4, "M");
                    ps.setString(5, "Margherita.jpeg");
                    ps.executeUpdate();

                    ps.setInt(1, 572);
                    ps.setString(2, "4 Fromages");
                    ps.setDouble(3, 10.0);
                    ps.setString(4, "M");
                    ps.setString(5, "4Fromages.jpg");
                    ps.executeUpdate();

                    ps.setInt(1, 573);
                    ps.setString(2, "Diavola");
                    ps.setDouble(3, 11.0);
                    ps.setString(4, "M");
                    ps.setString(5, "Diavola.jpeg");
                    ps.executeUpdate();

                    ps.setInt(1, 574);
                    ps.setString(2, "Parma");
                    ps.setDouble(3, 12.0);
                    ps.setString(4, "M");
                    ps.setString(5, "Parma.jpeg");
                    ps.executeUpdate();
                }

                // Remplissage de Composer si vide
                if (isTableEmpty(conn, "Composer")) {
                    // Récupère les id des pizzas et ingrédients
                    Statement st2 = conn.createStatement();
                    ResultSet rsPizza = st2.executeQuery("SELECT idPizza, nom FROM Pizza");
                    Map<String, Integer> pizzaIds = new HashMap<>();
                    while (rsPizza.next()) {
                        pizzaIds.put(rsPizza.getString("nom"), rsPizza.getInt("idPizza"));
                    }
                    ResultSet rsIng = st2.executeQuery("SELECT idIngredient, name FROM Ingredient");
                    Map<String, Integer> ingIds = new HashMap<>();
                    while (rsIng.next()) {
                        ingIds.put(rsIng.getString("name"), rsIng.getInt("idIngredient"));
                    }

                    PreparedStatement psComposer = conn.prepareStatement(
                        "INSERT INTO Composer (idPizza, idIngredient) VALUES (?, ?)");

                    // Margherita: Tomate + Mozzarella
                    psComposer.setInt(1, pizzaIds.get("Margherita"));
                    psComposer.setInt(2, ingIds.get("Tomate"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("Margherita"));
                    psComposer.setInt(2, ingIds.get("Mozzarella"));
                    psComposer.executeUpdate();

                    // 4 Fromages: Tomate + Mozzarella + 4 Fromages
                    psComposer.setInt(1, pizzaIds.get("4 Fromages"));
                    psComposer.setInt(2, ingIds.get("Tomate"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("4 Fromages"));
                    psComposer.setInt(2, ingIds.get("Mozzarella"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("4 Fromages"));
                    psComposer.setInt(2, ingIds.get("4 Fromages"));
                    psComposer.executeUpdate();

                    // Diavola: Tomate + Mozzarella + Salami
                    psComposer.setInt(1, pizzaIds.get("Diavola"));
                    psComposer.setInt(2, ingIds.get("Tomate"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("Diavola"));
                    psComposer.setInt(2, ingIds.get("Mozzarella"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("Diavola"));
                    psComposer.setInt(2, ingIds.get("Salami"));
                    psComposer.executeUpdate();

                    // Parma: Tomate + Mozzarella + Parma + Jambon
                    psComposer.setInt(1, pizzaIds.get("Parma"));
                    psComposer.setInt(2, ingIds.get("Tomate"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("Parma"));
                    psComposer.setInt(2, ingIds.get("Mozzarella"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("Parma"));
                    psComposer.setInt(2, ingIds.get("Parma"));
                    psComposer.executeUpdate();
                    psComposer.setInt(1, pizzaIds.get("Parma"));
                    psComposer.setInt(2, ingIds.get("Jambon"));
                    psComposer.executeUpdate();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isTableEmpty(Connection conn, String table) throws Exception {
        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM " + table);
        rs.next();
        return rs.getInt(1) == 0;
    }
}
