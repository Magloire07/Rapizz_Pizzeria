package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Ingredient;
import model.InventoryManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import view.OrderBoard;

public class InventoryController {

    private OrderBoard orderBoard;

    public InventoryController(OrderBoard orderBoard) {
        this.orderBoard = orderBoard;
    }

    public void showInventoryWindow() {
        JFrame inventoryFrame = new JFrame("Inventaire des Ingrédients");
        inventoryFrame.setSize(600, 400);
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inventoryFrame.setLayout(new BorderLayout());

        // Use DefaultTableModel for dynamic table population
        DefaultTableModel tableModel = new DefaultTableModel(new String[] {"Ingredient", "Quantity", "Unit Price", "Total Price"}, 0);
        JTable inventoryTable = new JTable(tableModel);

        // Populate table with data from InventoryManager
        InventoryManager inventoryManager = new InventoryManager();
        ArrayList<Ingredient> ingredients = inventoryManager.getIngredients();
        for (Ingredient ingredient : ingredients) {
            tableModel.addRow(new Object[] {
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getPrice(),
                ingredient.getQuantity() * ingredient.getPrice()
            });
        }

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        inventoryFrame.add(scrollPane, BorderLayout.CENTER);

        // Bouton Gérer le Stock
        JButton manageStockButton = new JButton("Gérer le Stock");
        manageStockButton.addActionListener(e -> {
            JFrame manageStockFrame = new JFrame("Gérer le Stock");
            manageStockFrame.setSize(400, 400);
            manageStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            manageStockFrame.setLayout(new GridLayout(5, 2, 10, 10));

            // Populate JComboBox dynamically
            JLabel ingredientLabel = new JLabel("Ingrédient:");
            JComboBox<String> ingredientComboBox = new JComboBox<>();
            for (Ingredient ingredient : ingredients) {
                ingredientComboBox.addItem(ingredient.getName());
            }

            JLabel unitPriceLabel = new JLabel("Prix Unitaire:");
            JLabel unitPriceValue = new JLabel("€0.00");

            // Update unit price when ingredient is selected
            ingredientComboBox.addActionListener(event -> {
                String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getName().equals(selectedIngredient)) {
                        unitPriceValue.setText("€" + ingredient.getPrice());
                        break;
                    }
                }
            });

            JLabel quantityLabel = new JLabel("Quantité:");
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

            JLabel accountBalanceLabel = new JLabel("Solde du Compte:");
            JLabel accountBalanceValue = new JLabel("€100.00"); // Replace with actual account balance logic

            JButton orderButton = new JButton("Commander");
            orderButton.addActionListener(orderEvent -> {
                String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
                int quantity = (int) quantitySpinner.getValue();
                double unitPrice = Double.parseDouble(unitPriceValue.getText().substring(1));
                double totalCost = quantity * unitPrice;

                // Update ingredient quantity in InventoryManager
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getName().equals(selectedIngredient)) {
                        ingredient.setQuantity(ingredient.getQuantity() - quantity);
                        inventoryManager.updateIngredient(ingredient);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(manageStockFrame,
                    "Commande passée pour " + quantity + " unités de " + selectedIngredient +
                    " au prix total de €" + totalCost + ".");
                manageStockFrame.dispose();
            });

            JButton cancelButton = new JButton("Annuler");
            cancelButton.addActionListener(cancelEvent -> manageStockFrame.dispose());

            manageStockFrame.add(ingredientLabel);
            manageStockFrame.add(ingredientComboBox);
            manageStockFrame.add(unitPriceLabel);
            manageStockFrame.add(unitPriceValue);
            manageStockFrame.add(quantityLabel);
            manageStockFrame.add(quantitySpinner);
            manageStockFrame.add(accountBalanceLabel);
            manageStockFrame.add(accountBalanceValue);
            manageStockFrame.add(orderButton);
            manageStockFrame.add(cancelButton);

            manageStockFrame.setLocationRelativeTo(null);
            manageStockFrame.setVisible(true);
        });

        // Bouton Fermer
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> inventoryFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(manageStockButton);
        buttonPanel.add(closeButton);
        inventoryFrame.add(buttonPanel, BorderLayout.SOUTH);

        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setVisible(true);
    }
}
