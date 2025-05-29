/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
        String[][] deliveries = {
            {"Paul", "Scooter", "Martin", /* date placeholder */ "", "0 minutes", "4Fromage", "10.0"},
            {"Paul", "Scooter", "Martin", /* date placeholder */ "", "1 minutes", "Margherita", "6.7"},
            {"Paul", "Scooter", "Martin", /* date placeholder */ "", "0 minutes", "Parma", "13.3"}
        };

        // Get current date and time as string
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        sb.append("  === Fiche de Livraison ===\n\n");
        for (String[] delivery : deliveries) {
            delivery[3] = currentDate; // Set current date for each delivery
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
