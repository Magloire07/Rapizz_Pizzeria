package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Ingredient;
import model.InventoryManager;
import model.OrderBoardManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

        DefaultTableModel tableModel = new DefaultTableModel(new String[] {"Ingredient", "Quantity", "Unit Price", "Total Price"}, 0);
        JTable inventoryTable = new JTable(tableModel);

        InventoryManager inventoryManager = new InventoryManager();
        List<Ingredient> ingredients = inventoryManager.getIngredients();
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

        JButton manageStockButton = new JButton("Gérer le Stock");
        manageStockButton.addActionListener(e -> {
            JFrame manageStockFrame = new JFrame("Gérer le Stock");
            manageStockFrame.setSize(400, 400);
            manageStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            manageStockFrame.setLayout(new GridLayout(5, 2, 10, 10));

            JLabel ingredientLabel = new JLabel("Ingrédient:");
            JComboBox<String> ingredientComboBox = new JComboBox<>();
            for (Ingredient ingredient : ingredients) {
                ingredientComboBox.addItem(ingredient.getName());
            }

            JLabel unitPriceLabel = new JLabel("Prix Unitaire:");
            JLabel unitPriceValue = new JLabel("€0.00");

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
            // Use real account balance
            OrderBoardManager orderBoardManager = new OrderBoardManager();
            JLabel accountBalanceValue = new JLabel("€" + String.format("%.2f", orderBoardManager.getSolde()));

            JButton orderButton = new JButton("Commander");
            orderButton.addActionListener(orderEvent -> {
                String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
                int quantity = (int) quantitySpinner.getValue();
                double unitPrice = Double.parseDouble(unitPriceValue.getText().substring(1));
                double totalCost = quantity * unitPrice;

                // Add quantity to ingredient (ordering new stock)
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getName().equals(selectedIngredient)) {
                        ingredient.setQuantity(ingredient.getQuantity() + quantity);
                        inventoryManager.updateIngredient(ingredient);
                        break;
                    }
                }

                // Refresh inventory table after order
                List<Ingredient> updatedIngredients = inventoryManager.getIngredients();
                tableModel.setRowCount(0); // Clear table
                for (Ingredient ingredient : updatedIngredients) {
                    tableModel.addRow(new Object[] {
                        ingredient.getName(),
                        ingredient.getQuantity(),
                        ingredient.getPrice(),
                        ingredient.getQuantity() * ingredient.getPrice()
                    });
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
