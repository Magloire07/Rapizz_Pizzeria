/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Client;
import model.ClientDAO;
import model.Commande;
import model.CommandeDAO;
import model.Pizza;
import model.PIzzaDAO;
import model.Ingredient;
import model.IngredientDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;


public class DashboardController {
    public void showDash() {
        // Set up the window
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Grid layout with 3 columns
        frame.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns with spacing

        // Panel for each category
        frame.add(createPanel("Meilleur Client", getBestClient()));
        frame.add(createPanel("Plus Mauvais Livreur", getWorstDeliveryPerson()));
        frame.add(createPanel("Pizza La Plus Demandée", getMostOrLeastDemandedPizza(true)));
        frame.add(createPanel("Pizza La Moins Demandée", getMostOrLeastDemandedPizza(false)));
        frame.add(createPanel("Ingrédient Favori", getFavoriteIngredient()));
        frame.add(createPanel("Véhicules Jamais Servis", getUnusedVehicles()));
        frame.add(createPanel("Nombre de Commandes par Client", getOrdersPerClient()));
        frame.add(createPanel("Moyenne des Commandes", getAverageOrders()));
        frame.add(createPanel("Clients au-dessus de la Moyenne", getClientsAboveAverage()));
        frame.add(createPanel("Statistiques Générales", getGeneralStats()));

        // Display the window
        frame.pack();
        frame.setSize(900, 600); // Adjusted size for grid layout
        frame.setVisible(true);
    }

    private JPanel createPanel(String title, String content) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier 10 Pitch", Font.BOLD, 16));
        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        return panel;
    }

    private String getBestClient() {
        try {
            ClientDAO clientDAO = new ClientDAO();
            List<Client> clients = clientDAO.readAllClientsAsObjects();
            Client best = null;
            int maxOrders = 0;
            for (Client c : clients) {
                if (c.getCagnote() > maxOrders) {
                    maxOrders = c.getCagnote();
                    best = c;
                }
            }
            if (best != null) {
                return "Client: " + best.getNom() + " " + best.getPrenom() + "\nTotal Orders: " + maxOrders;
            }
            return "Aucun client trouvé";
        } catch (Exception e) {
            return "Erreur lors de la récupération des clients";
        }
    }

    private String getWorstDeliveryPerson() {
        // No LivreurDAO/model in your folder, so return a placeholder
        return "Aucune donnée sur les livreurs.";
    }

    private String getMostOrLeastDemandedPizza(boolean mostDemanded) {
        // Count pizza orders using CommandeDAO and PIzzaDAO
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            PIzzaDAO pizzaDAO = new PIzzaDAO();
            List<Commande> commandes = commandeDAO.readAllCommandesAsObjects();
            List<Pizza> pizzas = pizzaDAO.readAllPizzasAsObjects();

            // Count orders per pizza name
            java.util.Map<String, Integer> pizzaCount = new java.util.HashMap<>();
            for (Commande c : commandes) {
                pizzaCount.put(c.getNomPizza(), pizzaCount.getOrDefault(c.getNomPizza(), 0) + 1);
            }
 
            String resultPizza = null;
            int resultCount = mostDemanded ? -1 : Integer.MAX_VALUE;
            for (Pizza p : pizzas) {
                int count = pizzaCount.getOrDefault(p.getName(), 0);
                if ((mostDemanded && count > resultCount) || (!mostDemanded && count < resultCount)) {
                    resultCount = count;
                    resultPizza = p.getName();
                }
            }
            if (resultPizza != null) {
                return "Pizza: " + resultPizza + "\nDemandes: " + resultCount;
            }
            return "Aucune pizza trouvée";
        } catch (Exception e) {
            return "Erreur lors de la récupération des pizzas";
        }
    }

    private String getFavoriteIngredient() {
        // No usage statistics in IngredientDAO, so return the ingredient with the highest quantity
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            List<Ingredient> ingredients = ingredientDAO.readAllIngredientsAsObjects();
            Ingredient favorite = null;
            int maxQty = -1;
            for (Ingredient ing : ingredients) {
                if (ing.getQuantity() > maxQty) {
                    maxQty = ing.getQuantity();
                    favorite = ing;
                }
            }
            if (favorite != null) {
                return "Ingrédient: " + favorite.getName() + "\nQuantité: " + maxQty;
            }
            return "Aucun ingrédient trouvé";
        } catch (Exception e) {
            return "Erreur lors de la récupération des ingrédients";
        }
    }

    private String getUnusedVehicles() {
        // No VehicleDAO/model in your folder, so return a placeholder
        return "Aucune donnée sur les véhicules.";
    }

    private String getOrdersPerClient() {
        // Count orders per client using CommandeDAO
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            List<Commande> commandes = commandeDAO.readAllCommandesAsObjects();
            java.util.Map<String, Integer> clientCount = new java.util.HashMap<>();
            for (Commande c : commandes) {
                clientCount.put(c.getNomClient(), clientCount.getOrDefault(c.getNomClient(), 0) + 1);
            }
            StringBuilder sb = new StringBuilder();
            for (var entry : clientCount.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return sb.length() > 0 ? sb.toString() : "Aucune commande trouvée";
        } catch (Exception e) {
            return "Erreur lors de la récupération des commandes";
        }
    }

    private String getAverageOrders() {
        // Average number of orders per client
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            List<Commande> commandes = commandeDAO.readAllCommandesAsObjects();
            java.util.Set<String> clients = new java.util.HashSet<>();
            for (Commande c : commandes) {
                clients.add(c.getNomClient());
            }
            if (clients.isEmpty()) return "Moyenne des Commandes: 0";
            double avg = (double) commandes.size() / clients.size();
            return "Moyenne des Commandes: " + String.format("%.2f", avg);
        } catch (Exception e) {
            return "Erreur lors du calcul de la moyenne";
        }
    }

    private String getClientsAboveAverage() {
        // Clients with more than average number of orders
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            List<Commande> commandes = commandeDAO.readAllCommandesAsObjects();
            java.util.Map<String, Integer> clientCount = new java.util.HashMap<>();
            for (Commande c : commandes) {
                clientCount.put(c.getNomClient(), clientCount.getOrDefault(c.getNomClient(), 0) + 1);
            }
            if (clientCount.isEmpty()) return "Clients: Aucun";
            double avg = (double) commandes.size() / clientCount.size();
            StringBuilder sb = new StringBuilder("Clients:\n");
            for (var entry : clientCount.entrySet()) {
                if (entry.getValue() > avg) {
                    sb.append("- ").append(entry.getKey()).append(" (").append(entry.getValue()).append(" commandes)\n");
                }
            }
            return sb.length() > 8 ? sb.toString() : "Clients: Aucun au-dessus de la moyenne";
        } catch (Exception e) {
            return "Erreur lors de la récupération des clients";
        }
    }

    private String getGeneralStats() {
        // Example: you can adapt this to show more stats
        return "Commandes livrées: " + /* your logic here */ "\nSatisfaction: " + /* your logic here */ "%";
    }
}
