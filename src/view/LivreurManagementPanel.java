package view;
 
import controller.LivreurController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LivreurManagementPanel extends JPanel {
    private final LivreurController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nomField;
    private final JTextField prenomField;
    private final JTextField idVehiculeField;

    public LivreurManagementPanel() {
        this.controller = new LivreurController();

        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Nb Livraisons", "Durée Livraisons", "ID Véhicule"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable directly
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        nomField = new JTextField();
        prenomField = new JTextField();
        idVehiculeField = new JTextField();


        formPanel.add(new JLabel("Nom:"));
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(new JLabel("ID Véhicule:"));
        formPanel.add(nomField);
        formPanel.add(prenomField);
        formPanel.add(idVehiculeField);

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
            int idVehicule;
            try {
                idVehicule = Integer.parseInt(idVehiculeField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID Véhicule invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.addLivreur(nom, prenom, idVehicule);
            refreshTable();
            clearFields();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un livreur à modifier.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            int nombreLivraisons = (int) tableModel.getValueAt(selectedRow, 3);
            int dureeLivraisons = (int) tableModel.getValueAt(selectedRow, 4);
            int idVehicule;
            try {
                idVehicule = Integer.parseInt(idVehiculeField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID Véhicule invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.updateLivreur(id, nom, prenom, nombreLivraisons, dureeLivraisons, idVehicule);
            refreshTable();
            clearFields();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un livreur à supprimer.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            controller.deleteLivreur(id);
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
                idVehiculeField.setText(tableModel.getValueAt(selectedRow, 5).toString());
            }
        });
    }

    private void refreshTable() {
        controller.loadLivreurs(tableModel);
    }

    private void clearFields() {
        nomField.setText("");
        prenomField.setText("");
        idVehiculeField.setText("");
        table.clearSelection();
    }
}