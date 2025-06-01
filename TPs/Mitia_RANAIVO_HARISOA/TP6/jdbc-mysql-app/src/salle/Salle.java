package salle;

public class Salle {
    private String nSalle;
    private String nomSalle;
    private int nbPoste;
    private String indIP;

    public Salle(String nSalle, String nomSalle, int nbPoste, String indIP) {
        this.nSalle = nSalle;
        this.nomSalle = nomSalle;
        this.nbPoste = nbPoste;
        this.indIP = indIP;
    }

    public String getNSalle() { return nSalle; }
    public void setNSalle(String nSalle) { this.nSalle = nSalle; }

    public String getNomSalle() { return nomSalle; }
    public void setNomSalle(String nomSalle) { this.nomSalle = nomSalle; }

    public int getNbPoste() { return nbPoste; }
    public void setNbPoste(int nbPoste) { this.nbPoste = nbPoste; }

    public String getIndIP() { return indIP; }
    public void setIndIP(String indIP) { this.indIP = indIP; }

    @Override
    public String toString() {
        return "Salle{" +
                "nSalle='" + nSalle + '\'' +
                ", nomSalle='" + nomSalle + '\'' +
                ", nbPoste=" + nbPoste +
                ", indIP='" + indIP + '\'' +
                '}';
    }
}