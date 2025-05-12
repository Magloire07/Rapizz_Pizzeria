/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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
        //TODO  retourne le meilleur client
        return "Client: Jane Smith\nTotal Orders: 25";
    }

    private String getWorstDeliveryPerson() {
        // TODO retourne le pire livreur
        return "Livreur: John Doe\nRetards: 5\nVéhicule: Motorbike";
    }

    private String getMostOrLeastDemandedPizza(boolean mostDemanded) {
        // TODO retourne la pizza la plus ou la moins demandée
        if (mostDemanded) {
            return "Pizza: Pepperoni\nDemandes: 50";
        } else {
            return "Pizza: Vegetarian\nDemandes: 10";
        }
    }

    private String getFavoriteIngredient() {
        // TODO retourne l'ingrédient favori
        return "Ingrédient: Mozzarella\nUtilisations: 75";
    }

    private String getUnusedVehicles() {
        // TODO retourne les véhicules jamais servis
        return "Véhicules: Bicycle, Truck";
    }

    private String getOrdersPerClient() {
        // TODO retourne le nombre de commandes par client
        return "Jane Smith: 25\nRobert Johnson: 15\nEmily Davis: 10";
    }

    private String getAverageOrders() {
        // TODO retourne la moyenne des commandes
        return "Moyenne des Commandes: 16.7";
    }

    private String getClientsAboveAverage() {
        // TODO retourne les clients au-dessus de la moyenne
        return "Clients:\n- Jane Smith (25 commandes)\n- Robert Johnson (15 commandes)";
    }
}
