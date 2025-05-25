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
    private final OrderBoard mainBoard; // Add this field

    public PizzaioloManagementPanel(OrderBoard mainBoard) { // Accept OrderBoard as parameter
        this.controller = new PizzaioloController();
        this.mainBoard = mainBoard; // Store reference

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
        JButton hireButton = new JButton("Embaucher un pizzaiolo (100€)");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(hireButton);

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

        hireButton.addActionListener(e -> {
            if (mainBoard.getSolde() >= 100) {
                String nom = JOptionPane.showInputDialog(this, "Nom du pizzaiolo ?");
                String prenom = JOptionPane.showInputDialog(this, "Prénom du pizzaiolo ?");
                if (nom != null && prenom != null && !nom.isEmpty() && !prenom.isEmpty()) {
                    model.PizzaioloDAO dao = new model.PizzaioloDAO();
                    dao.addPizzaiolo(new model.Pizzaiolo(nom, prenom));
                    mainBoard.setSolde(mainBoard.getSolde() - 100);
                    mainBoard.addNotification("Nouveau pizzaiolo embauché ! -100€");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Nom ou prénom invalide.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Solde insuffisant pour embaucher un pizzaiolo !");
            }
        });

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