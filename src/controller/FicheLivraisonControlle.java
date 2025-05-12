/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.*;


public class FicheLivraisonControlle {
    
    public void showFicheLivraison() {
        // Set up the window
        JFrame frame = new JFrame("Fiche de Livraison");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Delivery details
        JTextArea deliveryDetailsArea = new JTextArea();
        deliveryDetailsArea.setEditable(false);
        deliveryDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        deliveryDetailsArea.setText(getDeliveryDetails());
        JScrollPane scrollPane = new JScrollPane(deliveryDetailsArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Display the window
        frame.pack();
        frame.setSize(500, 700);
        frame.setVisible(true);
    }

    private String getDeliveryDetails() {
        // Example data for three delivery persons, replace with actual data retrieval logic
        StringBuilder sb = new StringBuilder();
      // DOTO
        String[][] deliveries = {
            {"John Doe", "Motorbike", "Jane Smith", "2023-10-01T18:30", "10 minutes", "Pepperoni", "10.0"},
            {"Alice Brown", "Bicycle", "Robert Johnson", "2023-10-02T12:15", "No delay", "Margherita", "8.5"},
            {"Michael Green", "Car", "Emily Davis", "2023-10-03T20:45", "5 minutes", "Vegetarian", "9.0"}
        };

        sb.append("  === Fiche de Livraison ===\n\n");
        for (String[] delivery : deliveries) {
            sb.append(String.format(
                " ----------------------------------------\n" +
                " Nom du livreur     : %s\n" +
                " Type de véhicule   : %s\n" +
                " Nom du client      : %s\n" +
                " Date de la commande: %s\n" +
                " Retard éventuel    : %s\n" +
                " Nom de la pizza    : %s\n" +
                " Prix de base       : $%.2f\n" +
                " ----------------------------------------\n\n",
                delivery[0], delivery[1], delivery[2], delivery[3], delivery[4], delivery[5], Double.parseDouble(delivery[6])
            ));
        }

        sb.append("  === Fin de la Fiche ===");
        return sb.toString();
    }
}
