package controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class MenuController {
    
    
    // Variables declaration - do not modify                     
    private javax.swing.JPanel Nord;
    private javax.swing.JPanel Sud;
    private java.awt.Label label1;
    
    public void showMenuWindow() {
        Nord = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        Sud = new javax.swing.JPanel();

        // Set up the window
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title
        label1.setFont(new java.awt.Font("Courier 10 Pitch", 1, 36)); // NOI18N
        label1.setText("Menu");
        Nord.add(label1);
        frame.add(Nord, BorderLayout.NORTH);

        // Pizza data with images
        JPanel pizzaPanel = new JPanel();
        pizzaPanel.setLayout(new BoxLayout(pizzaPanel, BoxLayout.Y_AXIS));
        List<Pizza> pizzas = getPizzaList();
        for (Pizza pizza : pizzas) {
            JPanel pizzaItemPanel = new JPanel();
            pizzaItemPanel.setLayout(new BorderLayout());

            // Pizza image
            JLabel imageLabel = new JLabel(new ImageIcon(pizza.getImagePath()));
            pizzaItemPanel.add(imageLabel, BorderLayout.WEST);

            // Pizza details
            JTextArea pizzaDetails = new JTextArea();
            pizzaDetails.setEditable(false);
            pizzaDetails.setFont(new Font("Monospaced", Font.PLAIN, 14));
            pizzaDetails.setText(
                "Name: " + pizza.getName() + "\n" +
                "Price: $" + pizza.getPrice() + "\n" +
                "Ingredients: " + pizza.getIngredients()
            );
            pizzaItemPanel.add(pizzaDetails, BorderLayout.CENTER);

            pizzaPanel.add(pizzaItemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(pizzaPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Footer panel
        frame.add(Sud, BorderLayout.SOUTH);

        // Display the window
        frame.pack();
        frame.setSize(700, 700);
        frame.setVisible(true);
    }

    private List<Pizza> getPizzaList() {
        //TODO
        return Arrays.asList(
            new Pizza("Margherita", 8.5, "Tomato, Mozzarella, Basil", "/home/kokoudevops/Documents/BD/Rapizz_Pizzeria/src/images/Margherita.jpeg"),
            new Pizza("Pepperoni", 10.0, "Tomato, Mozzarella, Pepperoni", "/home/kokoudevops/Documents/BD/Rapizz_Pizzeria/src/images/Margherita.jpeg"),
            new Pizza("Vegetarian", 9.0, "Tomato, Mozzarella, Vegetables", "/home/kokoudevops/Documents/BD/Rapizz_Pizzeria/src/images/Margherita.jpeg")
        );
    }

    // Inner class to represent a Pizza
    private class Pizza {
        private String name;
        private double price;
        private String ingredients;
        private String imagePath;

        public Pizza(String name, double price, String ingredients, String imagePath) {
            this.name = name;
            this.price = price;
            this.ingredients = ingredients;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getIngredients() {
            return ingredients;
        }

        public String getImagePath() {
            return imagePath;
        }
    }
}
