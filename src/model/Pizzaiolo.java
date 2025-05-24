package model;

public class Pizzaiolo {
    private int id;
    private String nom;
    private String prenom;
    private int nombrePizzas;

    public Pizzaiolo(int id, String nom, String prenom, int nombrePizzas) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nombrePizzas = nombrePizzas;
    }

    public Pizzaiolo(String nom, String prenom) {
        this(0, nom, prenom, 0);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public int getNombrePizzas() {
        return nombrePizzas;
    }
    public void setNombrePizzas(int nombrePizzas) {
        this.nombrePizzas = nombrePizzas;
    }

    @Override
    public String toString() {
        return "Pizzaiolo{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nombrePizzas=" + nombrePizzas +
                '}';
    }
}