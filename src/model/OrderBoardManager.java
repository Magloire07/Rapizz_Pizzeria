package model;

import java.util.ArrayList;

public class OrderBoardManager {
    
    public float getSolde() {
        //TODO: Récupérer le solde de la base de données
        return 100.0f; // Example value
    }
    public ArrayList<Pizza> getListPizza() {
        //TODO: Récupérer la liste des pizzas de la base de données
        //Exemple d'ajout de pizzas pour la démonstration
        ArrayList<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margherita", 8.99, "Medium", "Margherita.jpeg"));
        pizzas.add(new Pizza(2, "Parma", 9.99, "Large", "parma.jpeg"));
        pizzas.add(new Pizza(1, "Margherita", 8.99, "Medium", "Margherita.jpeg"));

        return pizzas;
    }
}
