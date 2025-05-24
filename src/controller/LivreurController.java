package controller;

import model.Livreur;
import model.LivreurDAO;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LivreurController {
    private final LivreurDAO livreurDAO;

    public LivreurController() {
        this.livreurDAO = new LivreurDAO();
    }

    public void loadLivreurs(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Livreur> livreurs = livreurDAO.readAllLivreurs();
        for (Livreur l : livreurs) {
            tableModel.addRow(new Object[]{
                l.getId(),
                l.getNom(),
                l.getPrenom(),
                l.getNombreLivraisons(),
                l.getDureeLivraisons(),
                l.getIdVehicule()
            });
        }
    }

    public void addLivreur(String nom, String prenom, int idVehicule) {
        Livreur l = new Livreur(nom, prenom, idVehicule);
        livreurDAO.addLivreur(l);
    }

    public void updateLivreur(int id, String nom, String prenom, int nombreLivraisons, int dureeLivraisons, int idVehicule) {
        Livreur l = new Livreur(id, nom, prenom, nombreLivraisons, dureeLivraisons, idVehicule);
        livreurDAO.updateLivreur(l);
    }

    public void deleteLivreur(int id) {
        livreurDAO.deleteLivreur(id);
    }

    public void assignLivreurToDelivery(int livreurId, int livraisonId) {
        livreurDAO.assignLivreurToDelivery(livreurId, livraisonId);
    }
}