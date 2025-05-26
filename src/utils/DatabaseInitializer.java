package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.BaseModel; // Ajoute cette importation
import java.util.Map;
import java.util.HashMap;

public class DatabaseInitializer extends BaseModel {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "316MIQq~+X";
    private static final String DB_NAME = "rapizz_pizzeria";
    private static final String SQL_FILE = "/home/mitia/BDD/Database/Project/Rapizz_Pizzeria/sql/CreationTables.sql";

    public static void initialize() {
        try (Connection conn = new DatabaseInitializer().getConnection()) {
            // Ensure Composer table exists FIRST
            Statement stCreate = conn.createStatement();
            stCreate.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Composer (" +
                "idPizza INT, " +
                "idIngredient INT, " +
                "PRIMARY KEY (idPizza, idIngredient), " +
                "FOREIGN KEY (idPizza) REFERENCES Pizza(idPizza), " +
                "FOREIGN KEY (idIngredient) REFERENCES Ingredient(idIngredient)" +
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
                int vehiculeId = 1;
                if (rs.next()) {
                    vehiculeId = rs.getInt(1);
                }

                // Livreurs (après véhicules)
                if (isTableEmpty(conn, "livreurs")) {
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

            // Pizzas de base
            if (isTableEmpty(conn, "Pizza")) {
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Pizza (nom, prix, taille, iconPath) VALUES (?, ?, ?, ?)");
                // Margherita
                ps.setString(1, "Margherita");
                ps.setDouble(2, 8.0);
                ps.setString(3, "M");
                ps.setString(4, "Margherita.jpeg");
                ps.executeUpdate();
                // 4 Fromages
                ps.setString(1, "4 Fromages");
                ps.setDouble(2, 10.0);
                ps.setString(3, "M");
                ps.setString(4, "4Fromages.jpg");
                ps.executeUpdate();
                // Diavola
                ps.setString(1, "Diavola");
                ps.setDouble(2, 11.0);
                ps.setString(3, "M");
                ps.setString(4, "Diavola.jpeg");
                ps.executeUpdate();
                // Parma
                ps.setString(1, "Parma");
                ps.setDouble(2, 12.0);
                ps.setString(3, "M");
                ps.setString(4, "Parma.jpeg");
                ps.executeUpdate();
            }
            // Ajoute ce bloc pour forcer la réinitialisation
            else {
                Statement st = conn.createStatement();
                // Delete dependent rows first
                st.executeUpdate("DELETE FROM Commande");
                st.executeUpdate("DELETE FROM Composer");
                st.executeUpdate("DELETE FROM Pizza");
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Pizza (nom, prix, taille, iconPath) VALUES (?, ?, ?, ?)");
                // Margherita
                ps.setString(1, "Margherita");
                ps.setDouble(2, 8.0);
                ps.setString(3, "M");
                ps.setString(4, "Margherita.jpeg");
                ps.executeUpdate();
                // 4 Fromages
                ps.setString(1, "4 Fromages");
                ps.setDouble(2, 10.0);
                ps.setString(3, "M");
                ps.setString(4, "4Fromages.jpg");
                ps.executeUpdate();
                // Diavola
                ps.setString(1, "Diavola");
                ps.setDouble(2, 11.0);
                ps.setString(3, "M");
                ps.setString(4, "Diavola.jpeg");
                ps.executeUpdate();
                // Parma
                ps.setString(1, "Parma");
                ps.setDouble(2, 12.0);
                ps.setString(3, "M");
                ps.setString(4, "Parma.jpeg");
                ps.executeUpdate();

                // Après (re)insertion des pizzas, toujours reset Composer
                Statement st2 = conn.createStatement();
                st2.executeUpdate("DELETE FROM Composer");

                // Récupère les id des pizzas et ingrédients
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
