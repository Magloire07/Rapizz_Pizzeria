package view;

import controller.VehiculeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VehiculeManagementPanel extends JPanel {
    private final VehiculeController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField marqueField;
    private final JTextField modeleField;
    private final JTextField typeField;
    private final JCheckBox disponibleCheckBox;

    public VehiculeManagementPanel() {
        this.controller = new VehiculeController();

        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Marque", "Modèle", "Type", "Disponible"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table not directly editable
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 8, 5, 5));
        marqueField = new JTextField();
        modeleField = new JTextField();
        typeField = new JTextField();
        disponibleCheckBox = new JCheckBox("Disponible");

        formPanel.add(new JLabel("Marque:"));
        formPanel.add(marqueField);
        formPanel.add(new JLabel("Modèle:"));
        formPanel.add(modeleField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(disponibleCheckBox);

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
            String marque = marqueField.getText().trim();
            String modele = modeleField.getText().trim();
            String type = typeField.getText().trim();
            boolean disponible = disponibleCheckBox.isSelected();
            if (marque.isEmpty() || modele.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.addVehicule(marque, modele, type, disponible);
            refreshTable();
            clearFields();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un véhicule à modifier.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String marque = marqueField.getText().trim();
            String modele = modeleField.getText().trim();
            String type = typeField.getText().trim();
            boolean disponible = disponibleCheckBox.isSelected();
            if (marque.isEmpty() || modele.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.updateVehicule(id, marque, modele, type, disponible);
            refreshTable();
            clearFields();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un véhicule à supprimer.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            controller.deleteVehicule(id);
            refreshTable();
            clearFields();
        });

        refreshButton.addActionListener(e -> refreshTable());

        // Table row selection to fill fields
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                marqueField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                modeleField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                typeField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                disponibleCheckBox.setSelected((boolean) tableModel.getValueAt(selectedRow, 4));
            }
        });
    }
 
    private void refreshTable() {
        controller.loadVehicules(tableModel);
    }

    private void clearFields() {
        marqueField.setText("");
        modeleField.setText("");
        typeField.setText("");
        disponibleCheckBox.setSelected(false);
        table.clearSelection();
    }
}