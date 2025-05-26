package model;

public class Vehicule {
    private int id;
    private String marque;
    private String modele;
    private String type;
    private boolean disponible;

    public Vehicule(int id, String marque, String modele, String type, boolean disponible) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.type = type;
        this.disponible = disponible;
    }

    public Vehicule(String marque, String modele, String type, boolean disponible) {
        this(0, marque, modele, type, disponible);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }
    public void setModele(String modele) {
        this.modele = modele;
    }
 
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", type='" + type + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}