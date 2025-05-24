package model;

public class Commande {
    private String nomClient;
    private String nomPizza;
    private double soldeClient;
    private int numeroCommande;
    private String taille;

    // Constructor
    public Commande(String nomClient, String nomPizza, double soldeClient, int numeroCommande, String taille) {
        this.nomClient = nomClient;
        this.nomPizza = nomPizza;
        this.soldeClient = soldeClient;
        this.numeroCommande = numeroCommande;
        this.taille = taille;
    }

    // Getters and Setters
    public String getNomClient() {
        return nomClient;
    }
 
    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNomPizza() {
        return nomPizza;
    }

    public void setNomPizza(String nomPizza) {
        this.nomPizza = nomPizza;
    }

    public double getSoldeClient() {
        return soldeClient;
    }

    public void setSoldeClient(double soldeClient) {
        this.soldeClient = soldeClient;
    }

    public int getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(int numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    // toString method
    @Override
    public String toString() {
        return "Client : " + nomClient + "\n"
             + "Pizza  : " + nomPizza + "\n"
             + "Solde  : " + String.format("%.2f", soldeClient) + "€\n"
             + "Commande N°: " + String.format("%04d", numeroCommande) + "\n"
             + "Taille : " + (taille != null ? taille : "Non spécifiée");
    }
}
