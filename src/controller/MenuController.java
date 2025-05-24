package controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Pizza;
import model.PIzzaDAO; // Use your DAO

public class MenuController {

    private javax.swing.JPanel Nord;
    private javax.swing.JPanel Sud;
    private java.awt.Label label1;

    public void showMenuWindow() {
        Nord = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        Sud = new javax.swing.JPanel();

        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label1.setFont(new java.awt.Font("Courier 10 Pitch", 1, 36));
        label1.setText("Menu");
        Nord.add(label1);
        frame.add(Nord, BorderLayout.NORTH);

        JPanel pizzaPanel = new JPanel();
        pizzaPanel.setLayout(new BoxLayout(pizzaPanel, BoxLayout.Y_AXIS));
        List<Pizza> pizzas = getPizzaList();
        for (Pizza pizza : pizzas) {
            JPanel pizzaItemPanel = new JPanel();
            pizzaItemPanel.setLayout(new BorderLayout());

            JLabel imageLabel = new JLabel();
            if (pizza.getIconPath() != null && !pizza.getIconPath().isEmpty()) {
                imageLabel.setIcon(new ImageIcon(pizza.getIconPath()));
            }
            pizzaItemPanel.add(imageLabel, BorderLayout.WEST);

            JTextArea pizzaDetails = new JTextArea();
            pizzaDetails.setEditable(false);
            pizzaDetails.setFont(new Font("Monospaced", Font.PLAIN, 14));
            pizzaDetails.setText(
                "Name: " + pizza.getName() + "\n" +
                "Price: " + pizza.getPrice() + "€\n" +
                "Size: " + pizza.getSize()
            );
            pizzaItemPanel.add(pizzaDetails, BorderLayout.CENTER);

            pizzaPanel.add(pizzaItemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(pizzaPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.add(Sud, BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(700, 700);
        frame.setVisible(true);
    }

    /**
     * Returns the list of pizzas for the menu from the database.
     */
    private List<Pizza> getPizzaList() {
        try {
            PIzzaDAO pizzaDAO = new PIzzaDAO();
            return pizzaDAO.readAllPizzasAsObjects();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des pizzas : " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
}
 