package model;

import java.util.ArrayList;

public class CommandeManager {
    
    public ArrayList<Commande> getRandomCmd() {
       //TODO: Générer les commandes aléatoirement

        ArrayList<Commande> commandes = new ArrayList<>();
        commandes.add(new Commande("Alice", "Margherita", 1500.0, 1, "M"));
        commandes.add(new Commande("Bob", "Pepperoni", 2000.0, 2, "L"));
        commandes.add(new Commande("Charlie", "Vegetarian", 1800.0, 3, "S"));
        commandes.add(new Commande("Diana", "Hawaiian", 2200.0, 4, "XL"));
        return commandes;
    }
}
