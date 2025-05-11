package model;

public class Client {
    private String nom;
    private String prenom;
    private double solde;
    private int cagnote;

    // Constructor
    public Client(String nom, String prenom, double solde, int cagnote) {
        this.nom = nom;
        this.prenom = prenom;
        this.solde = solde;
        this.cagnote = cagnote;
    }

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getCagnote() {
        return cagnote;
    }

    public void setCagnote(int cagnote) {
        this.cagnote = cagnote;
    }

    // toString method
    @Override
    public String toString() {
        return "Nom: " + nom + "\n" +
               "Prenom: " + prenom + "\n" +
               "Solde: " + solde + "€\n" +
               "Cagnote: " + cagnote;
    }
}
