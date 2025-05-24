package view;

import controller.PizzaioloController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PizzaioloManagementPanel extends JPanel {
    private final PizzaioloController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nomField;
    private final JTextField prenomField;

    public PizzaioloManagementPanel() {
        this.controller = new PizzaioloController();

        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Nb Pizzas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table not directly editable
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        nomField = new JTextField();
        prenomField = new JTextField();

        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(prenomField);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton refreshButton = new JButton("Rafraîchir");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Add components to main panel
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        refreshTable();

        // Button actions
        addButton.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            double coutEmbauche = 100.0; // Exemple de coût
            OrderBoard mainBoard = (OrderBoard) SwingUtilities.getWindowAncestor(this);
            if (mainBoard.getSolde() < coutEmbauche) {
                JOptionPane.showMessageDialog(this, "Solde insuffisant pour embaucher.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainBoard.setSolde(mainBoard.getSolde() - coutEmbauche);
            mainBoard.addNotification("-" + coutEmbauche + "€ (embauche)");
            controller.addPizzaiolo(nom, prenom);
            refreshTable();
            clearFields();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un pizzaiolo à modifier.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            int nombrePizzas = (int) tableModel.getValueAt(selectedRow, 3);
            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.updatePizzaiolo(id, nom, prenom, nombrePizzas);
            refreshTable();
            clearFields();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un pizzaiolo à supprimer.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            controller.deletePizzaiolo(id);
            refreshTable();
            clearFields();
        });

        refreshButton.addActionListener(e -> refreshTable());

        // Table row selection to fill fields
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                nomField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                prenomField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            }
        });
    }
 
    private void refreshTable() {
        controller.loadPizzaiolos(tableModel);
    }

    private void clearFields() {
        nomField.setText("");
        prenomField.setText("");
        table.clearSelection();
    }
}