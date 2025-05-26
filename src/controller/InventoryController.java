package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Ingredient;
import model.InventoryManager;
import model.OrderBoardManager;
import model.Pizza;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.OrderBoard;

public class InventoryController {

    private OrderBoard orderBoard;

    // Simule le stock d'ingrédients (nom -> quantité)
    private static Map<String, Integer> stock = new HashMap<>();

    static {
        // Initialisation fictive du stock
        stock.put("Tomate", 20);
        stock.put("Mozzarella", 20);
        stock.put("Jambon", 10);
        stock.put("Champignon", 10);
        stock.put("Chèvre", 10);
        stock.put("4 Fromages", 10);
        // Ajoute d'autres ingrédients selon tes pizzas
    }

    // Map pizzaName -> ingrédients nécessaires (nom -> quantité)
    private Map<String, Map<String, Integer>> recettePizzas = new HashMap<>();

    private final OrderBoard mainBoard;

    public InventoryController(OrderBoard orderBoard) {
        this.mainBoard = orderBoard;
        // Margherita
        Map<String, Integer> margherita = new HashMap<>();
        margherita.put("Tomate", 1);
        margherita.put("Mozzarella", 2);
        recettePizzas.put("Margherita", margherita);

        // 4 Fromages
        Map<String, Integer> fromages4 = new HashMap<>();
        fromages4.put("Tomate", 1);
        fromages4.put("Mozzarella", 1);
        fromages4.put("4 Fromages", 2);
        recettePizzas.put("4 Fromages", fromages4);

        // Diavola
        Map<String, Integer> diavola = new HashMap<>();
        diavola.put("Tomate", 1);
        diavola.put("Mozzarella", 1);
        diavola.put("Salami", 2);
        recettePizzas.put("Diavola", diavola);

        // Parma
        Map<String, Integer> parma = new HashMap<>();
        parma.put("Tomate", 1);
        parma.put("Mozzarella", 1);
        parma.put("Parma", 2);
        parma.put("Jambon", 1);
        recettePizzas.put("Parma", parma);

        // ...add more recipes if needed...
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
            JLabel accountBalanceValue = new JLabel("€" + String.format("%.2f", mainBoard.getSolde()));

            JButton orderButton = new JButton("Commander");
            orderButton.addActionListener(orderEvent -> {
                String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
                int quantity = (int) quantitySpinner.getValue();
                double unitPrice = Double.parseDouble(unitPriceValue.getText().substring(1));
                double totalCost = quantity * unitPrice;

                // Vérifie le solde avant achat
                if (this.mainBoard.getSolde() < totalCost) {
                    JOptionPane.showMessageDialog(manageStockFrame, "Solde insuffisant pour réapprovisionner.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.mainBoard.setSolde(this.mainBoard.getSolde() - totalCost);
                this.mainBoard.addNotification("-" + totalCost + "€ (réapprovisionnement)");

                // Ajoute la quantité à l'ingrédient
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

    // Vérifie si le stock est suffisant pour la pizza
    public boolean hasEnoughIngredients(Pizza pizza) {
        Map<String, Integer> recette = recettePizzas.get(pizza.getName());
        if (recette == null) return false;

        InventoryManager inventoryManager = new InventoryManager();
        List<Ingredient> ingredients = inventoryManager.getIngredients();

        Map<String, Integer> stockActuel = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            stockActuel.put(ingredient.getName(), ingredient.getQuantity());
        }

        for (Map.Entry<String, Integer> entry : recette.entrySet()) {
            int enStock = stockActuel.getOrDefault(entry.getKey(), 0);
            if (enStock < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private static final int STOCK_BAS_SEUIL = 5;

    // À appeler après chaque deduction
    public void checkLowStock() {
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            if (entry.getValue() <= STOCK_BAS_SEUIL) {
                orderBoard.addNotification("Stock bas pour l'ingrédient : " + entry.getKey());
            }
        }
    }

    // Modifie deductIngredients pour appeler checkLowStock
    public void deductIngredients(Pizza pizza) {
        Map<String, Integer> recette = recettePizzas.get(pizza.getName());
        if (recette == null) return;

        InventoryManager inventoryManager = new InventoryManager();
        List<Ingredient> ingredients = inventoryManager.getIngredients();

        for (Map.Entry<String, Integer> entry : recette.entrySet()) {
            String ingredientName = entry.getKey();
            int quantite = entry.getValue();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getName().equals(ingredientName)) {
                    ingredient.setQuantity(Math.max(0, ingredient.getQuantity() - quantite));
                    inventoryManager.updateIngredient(ingredient);
                    break;
                }
            }
        }
        checkLowStock();
    }

    // (Optionnel) Pour afficher le stock actuel
    public Map<String, Integer> getStock() {
        InventoryManager inventoryManager = new InventoryManager();
        List<Ingredient> ingredients = inventoryManager.getIngredients();
        Map<String, Integer> stockActuel = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            stockActuel.put(ingredient.getName(), ingredient.getQuantity());
        }
        return stockActuel;
    }
}
