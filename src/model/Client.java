package model;

public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String adresse;      // ← new field
    private double solde;
    private int cagnote;

    // Full constructor—include idClient (for reading from DB)
    public Client(int idClient, String nom, String prenom, String adresse, double solde, int cagnote) {
        this.idClient = idClient;
        this.nom      = nom;
        this.prenom   = prenom;
        this.adresse  = adresse;
        this.solde    = solde;
        this.cagnote  = cagnote;
    }

    // Constructor for creating new clients (no id yet)
    public Client(String nom, String prenom, String adresse, double solde, int cagnote) {
        this(0, nom, prenom, adresse, solde, cagnote);
    }

    // Getters & setters
    public int getId() {
        return idClient;
    }
    public void setId(int id) {
        this.idClient = id;
    }

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

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    @Override
    public String toString() {
        return "Client{" +
               "id=" + idClient +
               ", nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", adresse='" + adresse + '\'' +
               ", solde=" + solde +
               ", cagnote=" + cagnote +
               '}';
    }
}
