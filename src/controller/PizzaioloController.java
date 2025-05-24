package controller;

import model.Pizzaiolo;
import model.PizzaioloDAO;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PizzaioloController {
    private final PizzaioloDAO pizzaioloDAO;

    public PizzaioloController() {
        this.pizzaioloDAO = new PizzaioloDAO();
    }

    public void loadPizzaiolos(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Pizzaiolo> pizzaiolos = pizzaioloDAO.readAllPizzaiolos();
        for (Pizzaiolo p : pizzaiolos) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNom(),
                p.getPrenom(),
                p.getNombrePizzas()
            });
        }
    }

    public void addPizzaiolo(String nom, String prenom) {
        Pizzaiolo p = new Pizzaiolo(nom, prenom);
        pizzaioloDAO.addPizzaiolo(p);
    }

    public void updatePizzaiolo(int id, String nom, String prenom, int nombrePizzas) {
        Pizzaiolo p = new Pizzaiolo(id, nom, prenom, nombrePizzas);
        pizzaioloDAO.updatePizzaiolo(p);
    }

    public void deletePizzaiolo(int id) {
        pizzaioloDAO.deletePizzaiolo(id);
    }
} 