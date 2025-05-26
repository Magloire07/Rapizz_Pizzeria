package model;

import java.util.ArrayList;
import java.util.List;

public class PersonnelManager {
    public static class PizzaioloState {
        public String name;
        public boolean available = true;
        public Commande commandeEnCours = null;
        public PizzaioloState(String name) { this.name = name; }
    }

    public static class LivreurState {
        public String name;
        public boolean available = true;
        public Commande commandeEnCours = null;
        public LivreurState(String name) { this.name = name; }
    }

    private static PersonnelManager instance;
    private final List<PizzaioloState> pizzaiolos = new ArrayList<>();
    private final List<LivreurState> livreurs = new ArrayList<>();

    private PersonnelManager() {}

    public static PersonnelManager getInstance() {
        if (instance == null) instance = new PersonnelManager();
        return instance;
    }

    public void embaucherPizzaiolo(String name) {
        pizzaiolos.add(new PizzaioloState(name));
    }
    public void embaucherLivreur(String name) {
        livreurs.add(new LivreurState(name));
    }

    public List<PizzaioloState> getPizzaiolos() { return pizzaiolos; }
    public List<LivreurState> getLivreurs() { return livreurs; }

    public PizzaioloState getPizzaioloDisponible() {
        for (PizzaioloState p : pizzaiolos) if (p.available) return p;
        return null;
    }
    public LivreurState getLivreurDisponible() {
        for (LivreurState l : livreurs) if (l.available) return l;
        return null;
    }

    public void assignerCommandePizzaiolo(PizzaioloState p, Commande c) {
        p.available = false;
        p.commandeEnCours = c;
    }
    public void libererPizzaiolo(PizzaioloState p) {
        p.available = true;
        p.commandeEnCours = null;
    }
    public void assignerCommandeLivreur(LivreurState l, Commande c) {
        l.available = false;
        l.commandeEnCours = c;
    }
    public void libererLivreur(LivreurState l) {
        l.available = true;
        l.commandeEnCours = null;
    }

    public void resetAll() {
        // Reset all pizzaiolos
        for (PizzaioloState p : pizzaiolos) {
            p.available = true;
            p.commandeEnCours = null;
        }
        // Reset all livreurs
        for (LivreurState l : livreurs) {
            l.available = true;
            l.commandeEnCours = null;
        }
    }

    public void chargerPizzaiolosDepuisDB() {
        pizzaiolos.clear();
        model.PizzaioloDAO dao = new model.PizzaioloDAO();
        for (model.Pizzaiolo p : dao.readAllPizzaiolos()) {
            pizzaiolos.add(new PizzaioloState(p.getNom() + " " + p.getPrenom()));
        }
    }
    public void chargerLivreursDepuisDB() {
        livreurs.clear();
        model.LivreurDAO dao = new model.LivreurDAO();
        for (model.Livreur l : dao.readAllLivreurs()) {
            livreurs.add(new LivreurState(l.getNom() + " " + l.getPrenom()));
        }
    }
}