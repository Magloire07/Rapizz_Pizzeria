package model;

public class Livreur {
    private int id;
    private String nom;
    private String prenom;
    private int nombreLivraisons;
    private int dureeLivraisons;
    private int idVehicule;

    public Livreur(int id, String nom, String prenom, int nombreLivraisons, int dureeLivraisons, int idVehicule) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nombreLivraisons = nombreLivraisons;
        this.dureeLivraisons = dureeLivraisons;
        this.idVehicule = idVehicule;
    }

    public Livreur(String nom, String prenom, int idVehicule) {
        this(0, nom, prenom, 0, 0, idVehicule);
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

    public int getNombreLivraisons() {
        return nombreLivraisons;
    }
    public void setNombreLivraisons(int nombreLivraisons) {
        this.nombreLivraisons = nombreLivraisons;
    }

    public int getDureeLivraisons() {
        return dureeLivraisons;
    }
    public void setDureeLivraisons(int dureeLivraisons) {
        this.dureeLivraisons = dureeLivraisons;
    }

    public int getIdVehicule() {
        return idVehicule;
    }
    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nombreLivraisons=" + nombreLivraisons +
                ", dureeLivraisons=" + dureeLivraisons +
                ", idVehicule=" + idVehicule +
                '}';
    }
}